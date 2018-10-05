package kr.or.kosta.chat.server;
/**
 * chatting server
 * @author 박시원
 *
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import kr.or.kosta.chat.common.Protocol;

public class ChatSever {
	
	//포트값 상수처리
	public static final int PORT = 7777;
	
	//서버에 필요한 인스턴스변수
	private boolean running;
	private ServerSocket serverSocket;
	private Hashtable<String, Client> clients;
	
	//생성자->startUp 기능으로

	public boolean isRunning() {
		return running;
	}
	public Hashtable<String, Client> getClients() {
		return clients;
	}
	
	//메소드
	/**
	 * starting point
	 * @throws IOException
	 */
	public void startUp() throws IOException {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch(Exception e) {
			throw new IOException("["+PORT+"] 포트 충돌로 ChatServer 구동 불가");
		}
		running = true;
		clients = new Hashtable<String, Client>();
		System.out.println("BTS["+PORT+"] ChatServer start!");
		
		while(running) {
			try {
				Socket socket = serverSocket.accept();
				Client client = new Client(socket, this);
			
				client.start();
				System.out.println("#### ["+socket.getInetAddress()+"]님께서 서버에 연결하셨습니다. ####");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void shutDown() {
		
	}
	
	public void addClient(Client client) {
		clients.put(client.getNickName(), client);
	}
	
	public int getClientCount() {
		return clients.size();
	}
	
	
	public boolean isExitstNickName(String nickName) {
		return clients.containsKey(nickName);
	}
	
	public void removeClient(Client client, String message) throws IOException{
		clients.remove(client.getNickName());
		sendAllMessage(message);
	}
	
	public void sendAllMessage(String message) {
		Enumeration<Client> e =clients.elements();
		while (e.hasMoreElements()) {
			Client client = e.nextElement();
			System.out.println(client + "------------확인12222");
			client.sendMessage(message);
		}
	}
	
}
