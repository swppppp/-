import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * TCP/IP 기반의 Socket 프로그래밍 원리
 * @author 박시원
 */
public class SocketExample {
//	public static final String domain = "www.naver.com";
	public static final String domain = "localhost";
//	public static final String domain = "192.168.0.125"; //호준쓰ip
//	public static final String domain = "192.168.0.149"; //욱쓰ip
	
	public static final int port = 7777;

	public static void main(String[] args) {

		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			socket = new Socket(domain, port);
			System.out.println("서버와 연결됨.");
			
			//바이트스트림 얻어옴
			in = socket.getInputStream();
			out = socket.getOutputStream();
//			out.write(10);
//			System.out.println("서버에 데이터 전송");
//			int data = in.read();
//			System.out.println("서버로부터 에코된 데이터: "+data);
			
			//문자스트림쓰기
			PrintWriter pw = new PrintWriter(out);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			Scanner scanner = new Scanner(System.in);
			while(true) {
				
				String inputMsg = scanner.nextLine();
				pw.println(inputMsg);
				pw.flush();
				if(inputMsg.equalsIgnoreCase("quit")) {
					break;
				}
				String serverMessage = br.readLine();
				System.out.println("서버로부터 에코된 메세지: "+serverMessage);
			}
		} catch (IOException e) {
			System.out.println("서버연결 불가");
		}finally {
			try {
//				out.close();
//				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
