package kr.or.kosta.chat.client;
/**
 * 서버와의 통신 대행역할
 * @author 박시원
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import kr.or.kosta.chat.common.Protocol;

public class ChatClient {
	public static final String SERVER = "127.0.0.1";
	public static final int PORT = 7777;
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private boolean running;
	
	private ChatFrame chatFrame;
	
	public ChatClient(ChatFrame chatFrame) {
		this.chatFrame = chatFrame;
	}
	
	/**
	 * 소켓연결
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connectServer() throws Exception {
		try {
			socket = new Socket(SERVER, PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true); //true->auto flush
			running = true;
		}catch(Exception e) {
			throw new Exception("서버를 찾을 수 없습니다..:(");
		}
	}
	
	public void stopClient() {
		//소켓 닫아줌
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
	
	public void sendMessage(String message) {
		if(out!=null) out.println(message);
	}
	
	public void receiveMessage() {
		//서버쪽 메세지 받는 메세지
		//별도의 스레드가 되어야 하는 메소드->익명처리
		new Thread() {
			@Override
			public void run() {
				while(running) {
					String serverMessage = null;
					try {
						serverMessage = in.readLine();
						System.out.println("--------------");
						System.out.println("[Debug]: received by client: "+serverMessage);
						process(serverMessage);
						
					} catch (IOException e) {
						System.out.println("네트워크가 단절되었습니다.");
						
						break;
					}
				}
					
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e) {}
				}
			}
		}.start();
		
	}
	
	public void process(String message) {
		//메세지 토큰 만들어서
		String[] tokens = message.split(Protocol.DELEMETER);
		int protocol = Integer.parseInt(tokens[0]);
		String nickName = tokens[1];
		
		switch (protocol) {
		case Protocol.CONNECT_RESULT:
			String result = tokens[2];
			if(result.equalsIgnoreCase("SUCCESS")) {
				//연결메세지
				chatFrame.appendMessage("###"+nickName+"님이 연결하였습니다.###");
				chatFrame.connectEnable(false);
				chatFrame.addUserList(nickName);
			}else {
				JOptionPane.showMessageDialog(null, "이미 사용중인 대화명 입니다.\n다른 대화명을 사용하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case Protocol.MULTI_CHAT:
			String chatMessage = tokens[2];
			chatFrame.appendMessage("["+nickName+"]: "+chatMessage);
			break;
		case Protocol.DISCONNECT:
			chatFrame.appendMessage("["+nickName+"]: "+ "퇴장하셨습니다.");
			break;
			
		default:
			break;
		}
	}
	
}
