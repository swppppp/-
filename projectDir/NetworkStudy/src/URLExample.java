import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

public class URLExample {

	public static void main(String[] args) {

//		String urlString = "http://www.daum.net:80/index.html";
		String urlString = "https://news.v.daum.net/v/20180907095347180?rcmd=rn";
		
		try {
			URL url = new URL(urlString);
			System.out.println(url.getProtocol());
			System.out.println(url.getHost());

			//바이트스트림으로 읽어냄
			InputStream in = url.openStream(); //new FileInputStream만든거랑 같은거다
			
//			System.out.println(in);
//			System.out.println(in.read());
			
			/*
			byte[] buffer = new byte[1024];  //한 패킷단위사이즈씩 읽어들이기
			int count =0;
			while((count=in.read(buffer))!=-1)	{
				String text = new String(buffer, 0, count);
				System.out.println(text);
				
			}
			*/
			
			//문자스트림으로 읽어냄
			//in이 바이트스트림. br생성할땐 리더가 매개변수!. 브릿지스트림으로 바이트스트림을 리더로 바꿔줘서 생성
			//한글이 포함되어있으면 깨질 가능성이 있어요ㅠㅠ 왜냐면 남의컴에있는파일을 읽어온 거여서 인코딩방식이 다를거야
			//inputStreamReader에서 인코딩방식도 같이 매개변수로 줄 수 있어!!
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String txt = null;
			while((txt=br.readLine())!=null) {
				System.out.println(txt);
			}
			
			
			
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "서버를 찾을 수 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
