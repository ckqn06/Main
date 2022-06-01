import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class APSClient extends Thread{
	private Socket socket; //Client의 소켓
	private DataInputStream dins; //데이터 입력 스트림
	private DataOutputStream dous; //데이터 출력 스트림
	private ObjectOutputStream oous; //Object 객체 출력 스트림
	private ParkDBConnection dbc = new ParkDBConnection(); //DB연결 객체
	private File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일 객체
	
	public APSClient(Socket socket) { //서버로부터 소켓을 받아온다
		this.socket = socket;
		
		try { //받아온 소켓을 통해 데이터를 입출력함
			dous = new DataOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			oous = new ObjectOutputStream(socket.getOutputStream());
		} catch(Exception e){ //예외 처리
			System.out.println(e.getMessage());
		}
		
	}
	
	private void insertDB() { //DB에 데이터 삽입을 위한 메소드
		try {
			String carNum = dins.readUTF(); //입력 스트림으로부터 받아온 차량 번호를 저장함
			String place = dins.readUTF(); //입력 스트림으로부터 받아온 위치 번호를 저장함
			
			dbc.data_insert(carNum, place); //입력받은 차량 번호와 위치 번호를 DB에 삽입
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteDB() { //DB에서 데이터 삭제를 위한 메소드
		try {
			String carNum = dins.readUTF();
			
			dbc.data_delete(carNum); //입력받은 차량 번호와 관련된 데이터를 DB에서 삭제
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getTableDB() { //DB에서 고객 테이블 데이터 추출을 위한 메소드
		String[][] clientTableValue = dbc.getTable(); //DB에서 고객 테이블 값을 추출
		
		try {
			oous.writeObject(clientTableValue); //추출한 고객 테이블 값을 Client에게 데이터 전송
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void isSetting() { //관리자 데이터 파일의 존재 여부를 확인하기 위한 메소드
		try {
			dous.writeBoolean(f.exists()); //관리자 데이터 파일이 존재함을 확인함
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getSetting() { //관리자 데이터 파일에서 값을 받아오기 위한 메소드
		try {
			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt")); //관리자 데이터 파일을 읽어들임
        	List<String> list = new ArrayList<String>(); //읽어들인 관리자 데이터 파일의 내용을 저장하기 위한 리스트 생성
        	String line = null; //관리자 데이터 파일을 읽어들이기 위한 변수
        	
        	while((line = br.readLine()) != null) { //관리자 데이터 파일이 null이 아닐 때까지 읽어들임
        		list.add(line); //읽어들인 내용을 리스트에 저장
        	}
        	
        	int ListSize = list.size(); //리스트에 저장된 객체의 수를 리턴
        	String arr[] = list.toArray(new String[ListSize]); //리스트에 저장된 객체와 함께 배열로 변환함
        	
        	//배열에 저장된 값(가로, 세로, 시간당 주차 비용, 관리자 ID/PW, 장애인 전용/주차 불가 구역)을 적음
        	//저장된 값을 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 출력
        	for(int i = 0; i < 7; i++) {
        		dous.writeUTF(arr[i].split(":")[1]);
        	}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setSetting() { //관리자 데이터 파일에 값을 설정하기 위한 메소드
		try {
			String[] settingValue = new String[7]; //값을 설정하기 위한 String 배열 생성
			
			for(int i = 0; i < 7; i++) {
				settingValue[i] = dins.readUTF(); //관리자 데이터 파일에 작성할 데이터를 입력받고, 이를 settingValue에 저장
			}
			
			//settingValue를 관리자 데이터 파일에 각각 지정하여 값을 설정함
			String str = ("가로 값:"+settingValue[0] + "\n세로 값:"+settingValue[1] + "\n시간당 주차 비용:"+settingValue[2] + "\nID:"+settingValue[3]
					+ "\nPW:"+settingValue[4] + "\n장애인 전용 주차 구역:"+settingValue[5] + "\n주차 불가 구역:"+settingValue[6]);
    	
			OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
        	byte[] by = str.getBytes();
			os.write(by);
			os.close();		
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() { //스레드 실행 시 수행되는 메소드
		String command; //SQL 명령어를 저장하기 위한 변수
		
		while(true) {
			try {
				command = dins.readUTF(); //입력 스트림으로부터 받아온 SQL 명령어를 UTF-8 형식으로 읽음
				
				if(command.equals("insert")) { //SQL 명렁어가 'DB에 데이터 삽입'인 경우
					insertDB();
				} else if(command.equals("delete")) { //SQL 명렁어가 'DB에서 데이터 삭제'인 경우
					deleteDB();
				} else if(command.equals("getTable")) { //SQL 명렁어가 'DB에서 고객 테이블 데이터 추출'인 경우
					getTableDB();
				} else if(command.equals("isSetting")) { //SQL 명렁어가 '관리자 데이터 파일의 존재 여부 확인'인 경우
					isSetting();
				} else if(command.equals("getSetting")) { //SQL 명렁어가 '관리자 데이터 파일에서 값 받아오기'인 경우
					getSetting();
				} else if(command.equals("setSetting")) { //SQL 명렁어가 '관리자 데이터 파일의 값 설정'인 경우
					setSetting();
				}
			} catch(Exception e) { //예외 발생시 반복문 정지
				System.out.println(e.getMessage());
				break;
			}
		}
	}
}