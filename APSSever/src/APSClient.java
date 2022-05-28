import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class APSClient extends Thread{
	private DataOutputStream dous; //���ڿ� �� �� ������ ��� ��Ʈ��
	private DataInputStream dins; //���ڿ� �� �� ������ �Է� ��Ʈ��
	private ObjectOutputStream oous; //Object ��ü ��� ��Ʈ��
	private Socket socket; //Client�� ����
	private ParkDBConnection dbc = new ParkDBConnection(); //DB���� ��ü
	private File f = new File("������ ������ ����.txt"); //������ ������ ���� ��ü
	
	public APSClient(Socket socket) {
		this.socket = socket;
		try {
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oous = new ObjectOutputStream(socket.getOutputStream());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	private void insertDB() { //DB�� ������ ����
		try {
			String carNum = dins.readUTF(); //����ȣ �޾ƿ���
			String place = dins.readUTF(); //������ȣ �޾ƿ���
			
			dbc.data_insert(carNum, place); //DB�� ������ ����
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteDB() { //DB���� ������ ����
		try {
			String carNum = dins.readUTF(); //����ȣ �޾ƿ���
			
			dbc.data_delete(carNum); //DB���� ������ ����
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getTableDB() { //DB���� �� ���̺� ���� �����ؼ� ����
		String[][] clientTableValue = dbc.getTable(); //DB���� �� ���̺� ���� ����
		try {
			oous.writeObject(clientTableValue); //Client���� ������ ����
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void login() { //�α����� ���� �ʿ��� ������ ����
		if(f.exists()) { //������ �ִٸ�
			try {
				BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt")); //���� ���� ��ü
					
				for (int i = 0; i < 5; i++) br.readLine(); // �ʿ���� ����, ���� �� �ʿ� ���� �� ����
	        	String IDStr = br.readLine(); //ID ���� ��������(��: ID:admin)
	        	String passwordStr = br.readLine(); //��й�ȣ ���� ��������(��: password:park123)
	        	
	        	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
	        	String ID = IDStr.split(":")[1];
	        	String password = passwordStr.split(":")[1];
	        	
	        	//�� ����
	        	dous.writeUTF(IDStr);
	        	dous.writeUTF(passwordStr);
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void getSetting() { //������ ������ ���� ���� ����
		try {
			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
				
			String widthStr = br.readLine(); //���� ���� ��������(��: ����:10)
        	String heightStr = br.readLine(); //���� ���� ��������(��: ����:15)
        	String payStr = br.readLine(); //��� ���� ��������(��: ���:10000)
        	String handicapStr = br.readLine(); //����� ���� ���� ���� ���� ��������(��: ����� ���� ���� ����:A1,A2,A3)
        	String noParkStr = br.readLine(); //���� �Ұ� ���� ���� ��������(��: ���� �Ұ� ����:B2,B3,B4)
        	
        	br.close(); //���۸� ����
        	
        	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
        	int pay = Integer.parseInt(payStr.split(":")[1]);
        	String[] handicap = handicapStr.split(":")[1].split(",");
        	String[] noPark = noParkStr.split(":")[1].split(",");
        	
        	//�� ����
        	dous.writeInt(width);
        	dous.writeInt(height);
        	dous.writeInt(pay);
        	oous.writeObject(handicap);
        	oous.writeObject(noPark);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		String command;
		while(true) {
			try {
				command = dins.readUTF();
				
				if(command.equals("insert")) { //������ ����
					insertDB();
				}else if(command.equals("delete")) { //������ ����
					deleteDB();
				}else if(command.equals("getTable")) { //�� ���̺� �� ����
					getTableDB();
				}else if(command.equals("login")) { //�α��� ������ �� ����
					login();
				}else if(command.equals("getSetting")) { //������ ���� �� ����
					getSetting();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
