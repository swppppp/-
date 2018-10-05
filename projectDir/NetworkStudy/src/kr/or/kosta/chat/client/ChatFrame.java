package kr.or.kosta.chat.client;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kr.or.kosta.chat.common.Protocol;

public class ChatFrame extends Frame {
	
	private ChatClient chatClient; //화면에 보이지 않으므로 private로 선언
	private String nickName;
	
	Label nickNameL;
	TextField nickNameTF, inputTF;
	Button connectB, sendB;
	TextArea messageTA;
	List userList;
	
	
	//메뉴생성
	MenuBar menuBar;
	Menu menu;
	MenuItem newMI, exitMI;
	
	public ChatFrame() {
		this("No name");
	}
	public ChatFrame(String title) {
		super(title);
		nickNameL = new Label("대화명");
		nickNameTF = new TextField();
		inputTF = new TextField();
		connectB = new Button("연결"); 
		sendB = new Button("전송");
		messageTA = new TextArea();
		userList = new List();
		userList.add("트레비");
		userList.add("아이셔");
		userList.add("엠엔엠즈");
		
		menuBar = new MenuBar();
		menu = new Menu("File");
		newMI = new MenuItem("New");
		exitMI = new MenuItem("end");
	}
	
	public ChatClient getChatClient() {
		return chatClient;
	}
	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	//화면 배치
	public void setContents() {
		
		Panel northP = new Panel();  //화면 채울라고 레이아웃매니저를 border로 변경(판넬은 원래 flowLayout)
		northP.setLayout(new BorderLayout());
		northP.add(nickNameL, BorderLayout.WEST);
		northP.add(nickNameTF, BorderLayout.CENTER);
		northP.add(connectB, BorderLayout.EAST);
		
		//메뉴아이템
		setMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(newMI);
		newMI.setShortcut(new MenuShortcut(KeyEvent.VK_N));//단축키 설정 표시만
		menu.addSeparator();
		menu.add(exitMI);
		exitMI.setShortcut(new MenuShortcut(KeyEvent.VK_X));
		
		
		Panel southP = new Panel();
		southP.setLayout(new BorderLayout());
		southP.add(inputTF, BorderLayout.CENTER);
		southP.add(sendB, BorderLayout.EAST);
		
		add(northP, BorderLayout.NORTH);
		add(messageTA, BorderLayout.CENTER);
		add(userList, BorderLayout.EAST);
		add(southP, BorderLayout.SOUTH);
		
		setLocation(100, 100);
		
//		setColorAll(Color.cyan);
	}

	public void setCenter() {
//		Runtime.getRuntime().exec(command);	//팩토리 메소드(객체 new로 생성x)
		Toolkit.getDefaultToolkit().beep(); //소리나는거
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println(dim); //dim값 출력
		
		int x = (dim.width - getSize().width)/2;
		int y = (dim.height-getSize().height)/2;
		setLocation(x, y);
	}
	
	//서버와 연결
	public void connect() {
		
		nickName = nickNameTF.getText();
		try {
			chatClient.connectServer();
			//챗클라이언트에서 닉네임인지, 메세지인지, 채팅종료인지 구분해야함
			//구분자(ex. 입장: [1000 + ☆☆ + 닉네임] 이런식으로..
			//			메세지: [2000 + 닉네임 + 전송메세지]
			//			 종료: [3000 + ☆☆ + 닉네임]
			//====>프로토콜 설계...상수처리!(인터페이스로 구현하는게 좋음)
			
			//최초 연결 메세지 전송
			chatClient.sendMessage(Protocol.CONNECT + Protocol.DELEMETER + nickName);
//			appendMessage("@@ ChatServer와 연결되었습니다. @@"); //다른 곳에 보였다 사라지게 만드는거 생각
			chatClient.receiveMessage();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "연결 실패", JOptionPane.ERROR_MESSAGE);
		}
		//이거 메소드로 따로 빼주자
	}
	
	public void connectEnable(boolean flag) {
			nickNameTF.setEnabled(flag);
			connectB.setEnabled(flag);
	}
	
	
	
	//메세지를 챗클라이언트 보냄
	public void sendMessage() {
		String message = inputTF.getText();
		//입력값이 없을 때,
		if(message == null || message.trim().length() == 0) {
			return;
		}
		inputTF.setText("");
		////메세지를 서버로 보내주는 코드
		chatClient.sendMessage(Protocol.MULTI_CHAT + Protocol.DELEMETER + nickName + Protocol.DELEMETER + message);
	}
	
	public void appendMessage(String message) {
		messageTA.append(message+"\n");
	}
	
	public void finish() {
		chatClient.sendMessage(Protocol.DISCONNECT+Protocol.DELEMETER+nickName);
		chatClient.stopClient();
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	public void addUserList(String userNick) {
		userList.add(userNick);
	}
	
	
	public void eventRegist() {
		//닉네임 등록후 서버 연결
		nickNameTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		connectB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				//연결되고나면 TF비활성화 || 버튼을 종료버튼으로
			}
		});
		
		
		//이름없는 지역 내부클래스
		new WindowAdapter() {
		};
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		
		inputTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				sendMessage();
			}
		});
		
		sendB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				
			}
		});
		
		sendB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				appendMessage(inputTF.getText());
			}
		});
		
		userList.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String name = userList.getSelectedItem();
//					JOptionPane.showMessageDialog(null, name+"님 선택", "알림", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		});
		
		exitMI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finish();
			}
		});
	}
	
}
