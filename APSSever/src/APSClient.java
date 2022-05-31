import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
	
	private void isSetting() { //������ �����ϴ��� ����
		try {
			dous.writeBoolean(f.exists());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getSetting() { //������ ������ ���� ���� ����
		try {
			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
        	List<String> list = new ArrayList<String>(); //�о���� ������ ������ ������ ������ �����ϱ� ���� ����Ʈ ����
        	String line = null; //������ ������ ������ �о���̱� ���� ����
        	
        	while((line = br.readLine()) != null) { //������ ������ ������ null�� �ƴ� ������ �о����
        		list.add(line); //�о���� ������ ����Ʈ�� ����
        	}
        	
        	int ListSize = list.size(); //����Ʈ�� ����� ��ü�� ���� ����
        	String arr[] = list.toArray(new String[ListSize]); //����Ʈ�� ����� ��ü�� �Բ� �迭�� ��ȯ��
        	
        	//�� ����
        	for(int i = 0; i < 7; i++) {
        		dous.writeUTF(arr[i].split(":")[1]);
        	}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setSetting() { //������ ������ ���� ���� ����
		try {
			String[] settingValue = new String[7];
			for(int i = 0; i < 7; i++) {
				settingValue[i] = dins.readUTF();
			}
			String str = ("���� ��:"+settingValue[0] + "\n���� ��:"+settingValue[1] + "\n�ð��� ���� ���:"+settingValue[2] + "\nID:"+settingValue[3] + "\nPW:"+settingValue[4]
					+ "\n����� ���� ���� ����:"+settingValue[5] + "\n���� �Ұ� ����:"+settingValue[6]);
    	
			OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
        	byte[] by = str.getBytes();
			os.write(by);
			os.close();
			
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
				}else if(command.equals("isSetting")) { //�� ���̺� �� ����
					isSetting();
				}else if(command.equals("getSetting")) { //������ ���� �� ����
					getSetting();
				}else if(command.equals("setSetting")) { //������ ���� �� ����
					setSetting();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}
}
