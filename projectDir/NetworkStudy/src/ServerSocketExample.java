import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 클라이언트 연결을 수신하기위한 서버소켓
 * @author 박시원
 *
 */
public class ServerSocketExample {

	public static final int port = 7777;
	
	public static void main(String[] args) {
		boolean running = true;
		
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println(port+"포트에서 서버 실행");
			
			while(running) {
				Socket socket = serverSocket.accept(); //클라이언트 정보가 들어있음
				InetAddress ia = socket.getInetAddress();
				System.out.println(ia+"클라이언트가 연결해옴");
				
				//스레드 불러옴
				Client client = new Client(socket);
				client.start();
				
				
//								
//				////////////////////////////////////////////////////
//				//클라이언트에 있어야 할 코드들(메세지 수신 && 에코)
//				InputStream in = socket.getInputStream();
//				OutputStream out = socket.getOutputStream();
//				
////				int data = in.read();
////				System.out.println("클라이언트 수신 데이터: "+data);
////				out.write(data); //에코서버; 온거 그대로 돌려보내
//				
//				PrintWriter pw = new PrintWriter(out);
//				BufferedReader br = new BufferedReader(new InputStreamReader(in));
//				boolean stop = false;
//				while(!stop) {
//					String clientMessage = br.readLine();
//					System.out.println("클라이언트에서 수신한 메세지: "+clientMessage);
//					if(clientMessage.equalsIgnoreCase("quit")) {
//						break;
//					}
//					pw.println(clientMessage);
//					pw.flush();
//				}
//				
////				out.close();
////				in.close();
//				socket.close();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
