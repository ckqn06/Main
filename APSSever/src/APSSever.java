import java.net.ServerSocket;
import java.net.Socket;

public class APSSever {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(5555); //���� ����, ��Ʈ ��ȣ�� 5555�� ������
			
			while(true) {
				Socket socket = ss.accept(); //Client�� ������ ��ٸ�
				APSClient ac = new APSClient(socket); //���ӵ� Client�� ���� ������ ��ü ���� 
				ac.start(); //������ ����
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}