package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpMultiChatClient {
	
	public static void main(String[] args) {
		new TcpMultiChatClient().clientStart();
	}
	
	// 클라이언트 시작 메서드
	private void clientStart() {
		Socket socket = null;
		try {
			String serverIp = "localhost";
			socket = new Socket(serverIp, 7777);   // 서버에 접속하기
			
			System.out.println("서버와 연결되었습니다.");
			System.out.println();
			
			// 메시지 전송용 쓰레드 생성 및 실행
			ClientSender sender = new ClientSender(socket);
			
			// 메시지 수신용 쓰레드 생성 및 실행
			ClientReceiver receiver = new ClientReceiver(socket);
			
			sender.start();
			receiver.start();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	} // 클라이언트 시작 메서드 끝...
	
	// ----------------------------------
	
	// 메시지 전송용 쓰레드
	class ClientSender extends Thread{
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;
		private String name;  // 접속자 이름
		
		private Scanner scan;
		
		// 생성자
		public ClientSender(Socket socket) {
			this.socket = socket;
			scan = new Scanner(System.in);
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				if(dos!=null) {
					// 처음 프로그램이 시작되면 자신의 대화명(이름)을 서버로 전송하고
					// 대화명의 중복여부를 feedback으로 받아서 확인한다.
					System.out.print("대화명 : ");
					String name = scan.nextLine();
					
					while(true) {
						dos.writeUTF(name);  // 대화명 전송
						
						String feedBack = dis.readUTF();  // 대화명 중복여부를 응답으로 받는다.
						
						if("이름중복".equals(feedBack)) {  // 대화명이 중복되면...
							System.out.println(name + "은 대화명이 중복됩니다.");
							System.out.println("다른 대화명을 입력하세요.");
							System.out.print("대화명 : ");
							name = scan.nextLine();
						}else {  // 중복되지 않으면...
							this.name = name;
							System.out.println(name + " 이름으로 대화방에 입장했습니다.");
							break;   // 반복문 탈출
						}
					} // while문 끝..
					
				}  // if 끝..
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}  // 생성자 끝...
		
		@Override
		public void run() {
			try {
				while(dos!=null) {
					// 키보드로 입력한 메시지를 서버로 전송한다.
					dos.writeUTF("[" + name + "] " + scan.nextLine() );
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}  // run()메서드 끝..
		
	}  // 전송용 쓰레드 끝...
	
	
	// 메시지 수신용 쓰레드
	class ClientReceiver extends Thread{
		private Socket socket;
		private DataInputStream dis;
		
		// 생성자
		public ClientReceiver(Socket socket) {
			this.socket = socket;
			
			try {
				dis = new DataInputStream(socket.getInputStream());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} // 생성자 끝..
		
		@Override
		public void run() {
			try {
				while(dis!=null) {
					// 서버로부터 받은 메시지를 화면에 출력한다.
					System.out.println(dis.readUTF());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	

}








