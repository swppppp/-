import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
	
	
	public Client(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true); //auto flush(두번째 인자 true)
		running = true;
	}
	
	//메세지 수신
	public void recieve() {
		while(running) {
			String clientMessage = null;
			try {
				clientMessage = in.readLine();
				System.out.println("클라이언트에서 수신한 메세지: "+clientMessage);
				if(clientMessage.equalsIgnoreCase("quit")) {
					break;
				}
				out.println(clientMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			out.flush(); //오토플러슁 되어있음
		}
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	public void run() {
		recieve();
	}
}
