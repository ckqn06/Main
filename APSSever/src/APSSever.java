import java.net.ServerSocket;
import java.net.Socket;

public class APSSever {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8080); //�������� ����
			
			while(true) {
				Socket socket = ss.accept(); //Client�� ������ ��ٸ�
				APSClient ac = new APSClient(socket); //������ Ŭ���̾�Ʈ�� ���� Thread ��ü ���� 
				ac.start(); //Thread ����
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
