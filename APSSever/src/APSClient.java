import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class APSClient extends Thread{
	private Socket socket; //Client�� ����
	private DataInputStream dins; //������ �Է� ��Ʈ��
	private DataOutputStream dous; //������ ��� ��Ʈ��
	private ObjectOutputStream oous; //Object ��ü ��� ��Ʈ��
	private ParkDBConnection dbc = new ParkDBConnection(); //DB���� ��ü
	private File f = new File("������ ������ ����.txt"); //������ ������ ���� ��ü
	
	public APSClient(Socket socket) { //�����κ��� ������ �޾ƿ´�
		this.socket = socket;
		
		try { //�޾ƿ� ������ ���� �����͸� �������
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oous = new ObjectOutputStream(socket.getOutputStream());
		} catch(Exception e){ //���� ó��
			System.out.println(e.getMessage());
		}
		
	}
	
	private void insertDB() { //DB�� ������ ������ ���� �޼ҵ�
		try {
			String carNum = dins.readUTF(); //�Է� ��Ʈ�����κ��� �޾ƿ� ���� ��ȣ�� ������
			String place = dins.readUTF(); //�Է� ��Ʈ�����κ��� �޾ƿ� ��ġ ��ȣ�� ������
			
			dbc.data_insert(carNum, place); //�Է¹��� ���� ��ȣ�� ��ġ ��ȣ�� DB�� ����
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteDB() { //DB���� ������ ������ ���� �޼ҵ�
		try {
			String carNum = dins.readUTF();
			
			dbc.data_delete(carNum); //�Է¹��� ���� ��ȣ�� ���õ� �����͸� DB���� ����
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getTableDB() { //DB���� �� ���̺� ������ ������ ���� �޼ҵ�
		String[][] clientTableValue = dbc.getTable(); //DB���� �� ���̺� ���� ����
		
		try {
			oous.writeObject(clientTableValue); //������ �� ���̺� ���� Client���� ������ ����
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void isSetting() { //������ ������ ������ ���� ���θ� Ȯ���ϱ� ���� �޼ҵ�
		try {
			dous.writeBoolean(f.exists()); //������ ������ ������ �������� Ȯ����
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getSetting() { //������ ������ ���Ͽ��� ���� �޾ƿ��� ���� �޼ҵ�
		try {
			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt")); //������ ������ ������ �о����
        	List<String> list = new ArrayList<String>(); //�о���� ������ ������ ������ ������ �����ϱ� ���� ����Ʈ ����
        	String line = null; //������ ������ ������ �о���̱� ���� ����
        	
        	while((line = br.readLine()) != null) { //������ ������ ������ null�� �ƴ� ������ �о����
        		list.add(line); //�о���� ������ ����Ʈ�� ����
        	}
        	
        	int ListSize = list.size(); //����Ʈ�� ����� ��ü�� ���� ����
        	String arr[] = list.toArray(new String[ListSize]); //����Ʈ�� ����� ��ü�� �Բ� �迭�� ��ȯ��
        	
        	//�迭�� ����� ��(����, ����, �ð��� ���� ���, ������ ID/PW, ����� ����/���� �Ұ� ����)�� ����
        	//����� ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� ���
        	for(int i = 0; i < 7; i++) {
        		dous.writeUTF(arr[i].split(":")[1]);
        	}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setSetting() { //������ ������ ���Ͽ� ���� �����ϱ� ���� �޼ҵ�
		try {
			String[] settingValue = new String[7]; //���� �����ϱ� ���� String �迭 ����
			
			for(int i = 0; i < 7; i++) {
				settingValue[i] = dins.readUTF(); //������ ������ ���Ͽ� �ۼ��� �����͸� �Է¹ް�, �̸� settingValue�� ����
			}
			
			//settingValue�� ������ ������ ���Ͽ� ���� �����Ͽ� ���� ������
			String str = ("���� ��:"+settingValue[0] + "\n���� ��:"+settingValue[1] + "\n�ð��� ���� ���:"+settingValue[2] + "\nID:"+settingValue[3]
					+ "\nPW:"+settingValue[4] + "\n����� ���� ���� ����:"+settingValue[5] + "\n���� �Ұ� ����:"+settingValue[6]);
    	
			OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
        	byte[] by = str.getBytes();
			os.write(by);
			os.close();		
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() { //������ ���� �� ����Ǵ� �޼ҵ�
		String command; //SQL ��ɾ �����ϱ� ���� ����
		
		while(true) {
			try {
				command = dins.readUTF(); //�Է� ��Ʈ�����κ��� �޾ƿ� SQL ��ɾ UTF-8 �������� ����
				
				if(command.equals("insert")) { //SQL ��� 'DB�� ������ ����'�� ���
					insertDB();
				} else if(command.equals("delete")) { //SQL ��� 'DB���� ������ ����'�� ���
					deleteDB();
				} else if(command.equals("getTable")) { //SQL ��� 'DB���� �� ���̺� ������ ����'�� ���
					getTableDB();
				} else if(command.equals("isSetting")) { //SQL ��� '������ ������ ������ ���� ���� Ȯ��'�� ���
					isSetting();
				} else if(command.equals("getSetting")) { //SQL ��� '������ ������ ���Ͽ��� �� �޾ƿ���'�� ���
					getSetting();
				} else if(command.equals("setSetting")) { //SQL ��� '������ ������ ������ �� ����'�� ���
					setSetting();
				}
			} catch(Exception e) { //���� �߻��� �ݺ��� ����
				System.out.println(e.getMessage());
				break;
			}
		}
	}
}