import java.io.*;
import java.net.Socket;

public class ServerConnection {
	private Socket socket; //ServerConnection�� ����
	private DataInputStream dins; //������ �Է� ��Ʈ��
	private DataOutputStream dous; //������ ��� ��Ʈ��
	private ObjectInputStream oins; //Object ��ü �Է� ��Ʈ��
	
	public ServerConnection(){ //������ ���� ����
		try {
			socket = new Socket("localhost", 5555); //���� ����, IP ��ȣ�� localhost, ��Ʈ ��ȣ�� 5555�� ����
			//����� ������ ���� �����͸� �������
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oins = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e) { //���� ó��
			System.out.println(e.getMessage());
		}
	}
	
	public void insertData(String carNum, String place) { //DB�� ������ ������ ���� �޼ҵ�
		try { //���� ��ȣ�� ��ġ ��ȣ�� DB�� ����
			dous.writeUTF("insert");
			dous.writeUTF(carNum);
			dous.writeUTF(place);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteData(String carNum) { //DB���� ������ ������ ���� �޼ҵ�
		try { //���� ��ȣ�� DB���� ����
			dous.writeUTF("delete");
			dous.writeUTF(carNum);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String[][] getTableData() { //DB���� �� ���̺� ������ ������ ���� �޼ҵ�
		try { //DB���� �� ���̺� �����͸� �о����
			dous.writeUTF("getTable");
			return (String[][])oins.readObject(); //�о���� ��ü�� ��ȯ
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[][] {}; //�� ��ü�� ��ȯ
		}
	}
	
	public Boolean isSetting() { //������ ������ ������ ���� ���θ� Ȯ���ϱ� ���� �޼ҵ�
		try {
			dous.writeUTF("isSetting"); //������ ������ ������ �����ϴ��� Ȯ��
			return dins.readBoolean(); //�����ϴ����� ���θ� ��ȯ
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false; //���ܰ� �߻��Ѵٸ� false ��ȯ
		}
	}
	
	public String[] getSetting() { //������ ������ ���Ͽ��� ���� �޾ƿ��� ���� �޼ҵ�
		try {
			dous.writeUTF("getSetting"); 
			String[] settingValue = new String[7]; //�޾Ƶ��� ������ ������ ������ ���� �迭�� ����
			
			for(int i = 0; i < 7; i++) //�迭���� ���� �о����
				settingValue[i] = dins.readUTF();
			
			return settingValue; //�о���� ���� ��ȯ
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[] {};
		}
	}
	
	public void setSetting(String[] settingValue) { //������ ������ ���Ͽ� ���� �����ϱ� ���� �޼ҵ�
		try {
			dous.writeUTF("setSetting"); 
			for(int i = 0; i < 7; i++) //�迭�� ����� ���� ������
				dous.writeUTF(settingValue[i]);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}