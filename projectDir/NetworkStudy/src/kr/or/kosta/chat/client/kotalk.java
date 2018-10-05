package kr.or.kosta.chat.client;
/**
 * 채팅창 구동 애플리케이션
 * @author 박시원
 *
 */
public class kotalk {

	public static void main(String[] args) {
		
		ChatFrame frame = new ChatFrame("::: kotalk :::");
		frame.setContents();
		frame.setSize(400, 500);
		frame.setCenter();
		frame.eventRegist();
		frame.setVisible(true);
		
		ChatClient chatClient = new ChatClient(frame);
		
		frame.setChatClient(chatClient);

	}


}
