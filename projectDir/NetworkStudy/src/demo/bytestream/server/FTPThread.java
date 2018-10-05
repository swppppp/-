package demo.bytestream.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 원격클라이언트(Socket)에게 파일 서비스
 * @author 김기정
 */
public class FTPThread extends Thread {
	private static final String FILE_DIRECTORY = "C:/KOSTA187/설치프로그램";
	
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	
	public FTPThread(Socket socket) throws IOException{
		this.socket = socket;
		// 바이트스트림 생성
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}
	
	/** File Directory의 다운로드 가능한 파일 목록 전송 */
	private void sendFileNames() throws IOException{
		//"파일명1,파일명2" CSV 텍스트로 전송
		File directory = new File(FILE_DIRECTORY);
		// 파일디렉토리가 존재하지 않을 경우 디렉토리 생성
		if(!directory.exists()){
			directory.mkdir();
		}
		
		File[] list = directory.listFiles();
		StringBuilder sb = new StringBuilder(); //비동기    //스트링버퍼랑 다른점은 동기/비동기(마치 like 벡터랑 어레이리스트..)
		for (File file : list) {
			String name = file.getName();
			sb.append(name + "("+file.length()+"byte),"); //파일이름+파일크기
		}
		out.writeUTF(sb.toString());	//파일주기전에 파일명을 보여주는거여서 utf로 쓴다
		
		//--------------------------------------------------------------
		
		//#2. 다운로드 받고자 하는 파일명 수신
		String downFileName = in.readUTF();
		
		//#3. 파일 전송
		sendFile(downFileName);
	}
	
	public void sendFile(String fileName) throws IOException{
		File file = new File(FILE_DIRECTORY, fileName);
		long fileSize = file.length();
		
		// 다운로드 받고자 하는 파일 사이즈 출력(클라이언트 다운로드 상태 출력 시 사용)---->알림창떠서 progress보는거
		out.writeLong(fileSize);
		
		// 파일 전송
		FileInputStream fin = null;
		try{
			fin = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int count = 0;
			while((count=fin.read(buffer)) != -1){
				out.write(buffer, 0, count);
			}
		}finally{
			if(fin != null) fin.close();
		}
	}
	
	
	public void run(){
		try {
			//#1. 접속시 제일 먼저 다운로드 가능한 파일 목록 전송
			sendFileNames();			
		} catch (IOException e) {
			System.out.println("네트워크 장애로 파일을 전송할 수 없습니다.");
		} finally{
			try{
				if(in != null)     in.close();
				if(out != null)    out.close();
				if(socket != null) socket.close();
				
			}catch(IOException e){}	
		}
	}
}
