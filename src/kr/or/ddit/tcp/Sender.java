package kr.or.ddit.tcp;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

// 이 클래스는 소켓을 통해서 메시지를 보내는 역할을 담당한다.
public class Sender extends Thread{
	private Socket socket;
	private DataOutputStream dos;
	private String name;
	private Scanner scan;
	
	// 생성자
	public Sender(Socket socket) {
		super();
		this.socket = socket;
		
		scan = new Scanner(System.in);
		
		System.out.print("이름 입력 : ");
		name = scan.nextLine();
		
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	public void run() {
		while(dos!=null) {
			try {
				dos.writeUTF( name + " : " + scan.nextLine());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
}










