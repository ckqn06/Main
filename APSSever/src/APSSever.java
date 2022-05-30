import java.net.ServerSocket;
import java.net.Socket;

public class APSSever {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8080); //서버소켓 생성
			
			while(true) {
				Socket socket = ss.accept(); //Client의 접속을 기다림
				APSClient ac = new APSClient(socket); //접속한 클라이언트를 위한 Thread 객체 생성 
				ac.start(); //Thread 시작
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
