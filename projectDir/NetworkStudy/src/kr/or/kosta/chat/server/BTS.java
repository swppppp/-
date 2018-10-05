package kr.or.kosta.chat.server;

import java.io.IOException;
/**
 * 서버구동을 위한 애플리케이션
 * @author 박시원
 *
 */
public class BTS {

	public static void main(String[] args) {

		ChatSever chatSever = new ChatSever();
		
		try {
			chatSever.startUp();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
