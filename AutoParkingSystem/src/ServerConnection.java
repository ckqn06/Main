import java.io.*;
import java.net.Socket;

public class ServerConnection {
	private Socket socket; //ServerConnection의 소켓
	private DataInputStream dins; //데이터 입력 스트림
	private DataOutputStream dous; //데이터 출력 스트림
	private ObjectInputStream oins; //Object 객체 입력 스트림
	
	public ServerConnection(){ //서버와 소켓 연결
		try {
			socket = new Socket("localhost", 5555); //소켓 생성, IP 번호는 localhost, 포트 번호는 5555로 설정
			//연결된 소켓을 통해 데이터를 입출력함
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oins = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e) { //예외 처리
			System.out.println(e.getMessage());
		}
	}
	
	public void insertData(String carNum, String place) { //DB에 데이터 삽입을 위한 메소드
		try { //차량 번호와 위치 번호를 DB에 삽입
			dous.writeUTF("insert");
			dous.writeUTF(carNum);
			dous.writeUTF(place);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteData(String carNum) { //DB에서 데이터 삭제를 위한 메소드
		try { //차량 번호를 DB에서 삭제
			dous.writeUTF("delete");
			dous.writeUTF(carNum);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String[][] getTableData() { //DB에서 고객 테이블 데이터 추출을 위한 메소드
		try { //DB에서 고객 테이블 데이터를 읽어들임
			dous.writeUTF("getTable");
			return (String[][])oins.readObject(); //읽어들인 객체를 반환
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[][] {}; //빈 객체를 반환
		}
	}
	
	public Boolean isSetting() { //관리자 데이터 파일의 존재 여부를 확인하기 위한 메소드
		try {
			dous.writeUTF("isSetting"); //관리자 데이터 파일이 존재하는지 확인
			return dins.readBoolean(); //존재하는지의 여부를 반환
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false; //예외가 발생한다면 false 반환
		}
	}
	
	public String[] getSetting() { //관리자 데이터 파일에서 값을 받아오기 위한 메소드
		try {
			dous.writeUTF("getSetting"); 
			String[] settingValue = new String[7]; //받아들인 관리자 데이터 파일의 값을 배열에 저장
			
			for(int i = 0; i < 7; i++) //배열에서 값을 읽어들임
				settingValue[i] = dins.readUTF();
			
			return settingValue; //읽어들인 값을 반환
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new String[] {};
		}
	}
	
	public void setSetting(String[] settingValue) { //관리자 데이터 파일에 값을 설정하기 위한 메소드
		try {
			dous.writeUTF("setSetting"); 
			for(int i = 0; i < 7; i++) //배열에 저장된 값을 설정함
				dous.writeUTF(settingValue[i]);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}