import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ParkDBConnection {
    private Connection conn; //DB 커넥션 연결 객체
    private static final String USERNAME = "user"; //DBMS(데이터베이스 관리 시스템)에 접속하기 위한 아이디
    private static final String PASSWORD = "1234"; //DBMS(데이터베이스 관리 시스템)에 접속하기 위한 비밀번호
    private static final String URL = "jdbc:mysql://localhost:3306/ParkDB?useUnicode=true&characterEncoding=utf8"; //JDBC 드라이버 주소

	private Statement stmt; //쿼리 작업을 실행하기 위한 객체
	private ResultSet rs; //쿼리를 실행한 결과값을 저장하는 객체
    
    public ParkDBConnection() { //DB 연결 여부를 알기 위한 메소드
        try {
            System.out.println("생성자"); //DB와의 연결이 시도됐음을 확인
            Class.forName("com.mysql.jdbc.Driver"); //JDBC 드라이버 연결 여부 확인
            System.out.println("success"); //JDBC 드라이버와 연결이 됐다면 success 출력
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); //JDBC 드라이버 주소, 아이디, 비밀번호를 통해 DB와 연결을 시도
            System.out.println("success"); //DB와 정상적으로 연결이 됐다면 success 출력
            stmt = conn.createStatement(); //DB로 SQL문을 보내기 위한 SQLServerStatement 개체 생성
        } catch (Exception e) { //연결 중에 문제가 발생했다면 Failed와 오류 메시지를 출력하고 예외 처리
            System.out.println("Failed");
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
    
    public void data_insert(String car_number, String Park_Zone_Num) { //DB파일에 고객 정보를 입력하기 위한 메소드
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"년-월-일 시:분:초"로 시간 포맷 설정
    		String Time = format.format(System.currentTimeMillis()); //현재 시간을 구해 이를 설정한 포맷을 적용
    		//ParkDB DB파일 내의 ParkGuest 테이블에 차량 번호, 주차 시간, 위치 번호 데이터를 삽입함
    		String Query = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ "\")";
			stmt.executeUpdate(Query); //SQL문(쿼리 작업)을 실행함
    	} catch (Exception e) { //입력이 정상적으로 처리되지 않는다면 예외 처리
    		System.out.println(e.getMessage());
    		e.getStackTrace();
    	}
    }
    
    public void data_delete(String car_number) { //DB파일 내에 저장된 고객 정보를 삭제하기 위한 메소드
    	//ParkDB DB파일 내의 ParkGuest 테이블에 차량 번호 데이터를 삭제함
    	String Query = "DELETE FROM ParkGuest WHERE CarNumber = " + "'" + car_number + "'";
    	try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) { //삭제가 정상적으로 처리되지 않는다면 예외 처리
			e.printStackTrace();
		} 
    }
    
    public String[][] getTable(){ //2차원 배열 테이블 값을 가져오기 위한 메소드
    	ArrayList<String[]> clientTableValue = new ArrayList<String[]>(); //고객 테이블의 데이터를 저장하기 위한 ArrayList 생성
    	String Query = "SELECT * FROM ParkGuest"; //ParkDB 내의 ParkGuest 테이블에 저장된 모든 데이터를 선택함

    	try {
			rs = stmt.executeQuery(Query);
			
			while(rs.next()) { //요소가 남아있을 때까지 다음 요소를 선택함
				//ParGuest 테이블에 저장된 데이터를 String형으로 받아옴 (각각 차량 번호, 주차 시간, 위치 번호를 받아옴)
				clientTableValue.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3)});
	    	}	
		} catch (SQLException e) { //테이블 값이 정상적으로 가져와지지 않는다면 예외 처리
			e.printStackTrace();
		}
    	//고객 테이블의 데이터가 저장된 ArrayList를 배열로 변환한 뒤, 저장된 값을 반환함
    	return clientTableValue.toArray(new String[clientTableValue.size()][]);
    }
}