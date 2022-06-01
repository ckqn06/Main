import java.net.ServerSocket;
import java.net.Socket;

public class APSSever {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(5555); //소켓 생성, 포트 번호는 5555로 지정함
			
			while(true) {
				Socket socket = ss.accept(); //Client의 접속을 기다림
				APSClient ac = new APSClient(socket); //접속된 Client를 위한 스레드 객체 생성 
				ac.start(); //스레드 시작
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}