import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
	
	private void login() { //로그인을 위해 필요한 데이터 전송
		if(f.exists()) { //파일이 있다면
			try {
				BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt")); //파일 연결 객체
					
				for (int i = 0; i < 5; i++) br.readLine(); // 필요없는 가로, 세로 등 필요 없는 값 무시
	        	String IDStr = br.readLine(); //ID 정보 가져오기(예: ID:admin)
	        	String passwordStr = br.readLine(); //비밀번호 정보 가져오기(예: password:park123)
	        	
	        	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
	        	String ID = IDStr.split(":")[1];
	        	String password = passwordStr.split(":")[1];
	        	
	        	//값 전송
	        	dous.writeUTF(IDStr);
	        	dous.writeUTF(passwordStr);
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void getSetting() { //관리자 데이터 설정 정보 전송
		try {
			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
				
			String widthStr = br.readLine(); //가로 정보 가져오기(예: 가로:10)
        	String heightStr = br.readLine(); //세로 정보 가져오기(예: 세로:15)
        	String payStr = br.readLine(); //비용 정보 가져오기(예: 비용:10000)
        	String handicapStr = br.readLine(); //장애인 전용 주차 구역 정보 가져오기(예: 장애인 전용 주차 구역:A1,A2,A3)
        	String noParkStr = br.readLine(); //주차 불가 구역 정보 가져오기(예: 주차 불가 구역:B2,B3,B4)
        	
        	br.close(); //버퍼를 닫음
        	
        	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
        	int pay = Integer.parseInt(payStr.split(":")[1]);
        	String[] handicap = handicapStr.split(":")[1].split(",");
        	String[] noPark = noParkStr.split(":")[1].split(",");
        	
        	//값 전송
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
				
				if(command.equals("insert")) { //데이터 삽입
					insertDB();
				}else if(command.equals("delete")) { //데이터 삭제
					deleteDB();
				}else if(command.equals("getTable")) { //고객 테이블 값 전송
					getTableDB();
				}else if(command.equals("login")) { //로그인 데이터 값 전송
					login();
				}else if(command.equals("getSetting")) { //관리자 설정 값 전송
					getSetting();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
