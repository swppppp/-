package kr.or.kosta.chat.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import kr.or.kosta.chat.common.Protocol;

/**
 * 클라이언트의 데이터 수신 및 처리
 * @author 박시원
 */
public class Client extends Thread {

	boolean running; //현재 상태
	private Socket socket;
	//메세지 송수신에 필요한 스트림 선언
	private BufferedReader in;
	private PrintWriter out;
	
	
	/**클라이언트 식별자*/
	private String nickName = "손님"; 
	
	ChatSever chatServer;
		
		
	public Client(Socket socket, ChatSever chatSever) throws IOException {
		this.socket = socket;
		this.chatServer = chatSever;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true); //auto flush(두번째 인자 true)
		running = true;
	}
	/** setter, getter*/
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Socket getSocket() {
		return socket;
	}

	
	
	//메세지 수신
	public void recieveMessage() {
		while(running) {
			String clientMessage = null;
			try {
				clientMessage = in.readLine();
				process(clientMessage);
				System.out.println("[Debug]: 클라이언트에서 수신한 메세지: "+clientMessage);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
//			out.flush(); //오토플러슁 되어있음
		}
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
	/**
	 * 클라이언트의 메세지를 파싱하여 서비스 제공
	 * @param message
	 */
	public void process(String message) {
		//클라이언트가받은 메세지 처리(수신 메소드에서 필요한 절차들)
		String[] tokens = message.split(Protocol.DELEMETER);
		int protocol = Integer.parseInt(tokens[0]);
		nickName = tokens[1];
		
		switch (protocol) {
		case Protocol.CONNECT:
			//닉네임 중복체크
			if(chatServer.isExitstNickName(nickName)) {
				sendMessage(Protocol.CONNECT_RESULT+Protocol.DELEMETER +nickName+Protocol.DELEMETER+"FAIL");
			}else {
				chatServer.addClient(this);
				System.out.println("debug: 접속 클라이언트 수: "+chatServer.getClientCount());
				sendMessage(Protocol.CONNECT_RESULT+Protocol.DELEMETER+nickName+Protocol.DELEMETER+"SUCCESS");
			}
			break;
		
		case Protocol.MULTI_CHAT:
			if(message!=null) chatServer.sendAllMessage(message);
			break;
			
		case Protocol.DISCONNECT:
			try {
				chatServer.removeClient(this, message);
				setRunning(false); //->running이 false면 while문 밖의 소켓닫는 부분으로 감
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	public void sendMessage(String message) {
		out.println(message);
	}
	
	@Override
	public void run() {
		recieveMessage();
	}
}
