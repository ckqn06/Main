import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerConnection {
	private Socket socket;
	private DataOutputStream dous;
	private DataInputStream dins;
	private ObjectInputStream oins;
	
	public ServerConnection(){
		try {
			socket = new Socket("localhost", 8080);
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oins = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insertData(String carNum, String place) {
		try {
			dous.writeUTF("insert");
			dous.writeUTF(carNum);
			dous.writeUTF(place);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void deleteData(String carNum) {
		try {
			dous.writeUTF("delete");
			dous.writeUTF(carNum);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public String[][] getTableData() {
		try {
			dous.writeUTF("getTable");
			return (String[][])oins.readObject();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[][] {};
		}
		
	}
	
	public Boolean isSetting() {
		try {
			dous.writeUTF("isSetting");
			return dins.readBoolean();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public String[] getSetting() {
		try {
			dous.writeUTF("getSetting");
			String[] settingValue = new String[7];
			
			for(int i = 0; i < 7; i++)
				settingValue[i] = dins.readUTF();
			
			return settingValue;
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[] {};
		}
		
	}
	
	public void setSetting(String[] settingValue) {
		try {
			dous.writeUTF("setSetting");
			for(int i = 0; i < 7; i++)
				dous.writeUTF(settingValue[i]);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
