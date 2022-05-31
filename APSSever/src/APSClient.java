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
	private DataOutputStream dous; //문자열 및 수 데이터 출력 스트림
	private DataInputStream dins; //문자열 및 수 데이터 입력 스트림
	private ObjectOutputStream oous; //Object 객체 출력 스트림
	private Socket socket; //Client의 소켓
	private ParkDBConnection dbc = new ParkDBConnection(); //DB연결 객체
	private File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일 객체
	
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
	
	private void insertDB() { //DB에 데이터 삽입
		try {
			String carNum = dins.readUTF(); //차번호 받아오기
			String place = dins.readUTF(); //공간번호 받아오기
			
			dbc.data_insert(carNum, place); //DB에 데이터 삽입
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteDB() { //DB에서 데이터 삭제
		try {
			String carNum = dins.readUTF(); //차번호 받아오기
			
			dbc.data_delete(carNum); //DB에서 데이터 삭제
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getTableDB() { //DB에서 고객 테이블 정보 추출해서 전송
		String[][] clientTableValue = dbc.getTable(); //DB에서 고객 테이블 정보 추출
		try {
			oous.writeObject(clientTableValue); //Client에게 데이터 전송
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void isSetting() { //파일이 존재하는지 전송
		try {
			dous.writeBoolean(f.exists());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getSetting() { //관리자 데이터 설정 정보 전송
		try {
			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
        	List<String> list = new ArrayList<String>(); //읽어들인 관리자 데이터 파일의 내용을 저장하기 위한 리스트 생성
        	String line = null; //관리자 데이터 파일을 읽어들이기 위한 변수
        	
        	while((line = br.readLine()) != null) { //관리자 데이터 파일이 null이 아닐 때까지 읽어들임
        		list.add(line); //읽어들인 내용을 리스트에 저장
        	}
        	
        	int ListSize = list.size(); //리스트에 저장된 객체의 수를 리턴
        	String arr[] = list.toArray(new String[ListSize]); //리스트에 저장된 객체와 함께 배열로 변환함
        	
        	//값 전송
        	for(int i = 0; i < 7; i++) {
        		dous.writeUTF(arr[i].split(":")[1]);
        	}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setSetting() { //관리자 데이터 설정 정보 변경
		try {
			String[] settingValue = new String[7];
			for(int i = 0; i < 7; i++) {
				settingValue[i] = dins.readUTF();
			}
			String str = ("가로 값:"+settingValue[0] + "\n세로 값:"+settingValue[1] + "\n시간당 주차 비용:"+settingValue[2] + "\nID:"+settingValue[3] + "\nPW:"+settingValue[4]
					+ "\n장애인 전용 주차 구역:"+settingValue[5] + "\n주차 불가 구역:"+settingValue[6]);
    	
			OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
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
				
				if(command.equals("insert")) { //데이터 삽입
					insertDB();
				}else if(command.equals("delete")) { //데이터 삭제
					deleteDB();
				}else if(command.equals("getTable")) { //고객 테이블 값 전송
					getTableDB();
				}else if(command.equals("isSetting")) { //고객 테이블 값 전송
					isSetting();
				}else if(command.equals("getSetting")) { //관리자 설정 값 전송
					getSetting();
				}else if(command.equals("setSetting")) { //관리자 설정 값 전송
					setSetting();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}
}
