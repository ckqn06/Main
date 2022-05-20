import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


 
public class ParkDBConnection {
    private Connection conn; //DB 커넥션 연결 객체
    private static final String USERNAME = "root";//DBMS접속 시 아이디
    private static final String PASSWORD = "Opensource202024**";//DBMS접속 시 비밀번호
    private static final String URL = "jdbc:mysql://localhost:9001/ParkDB";//DBMS접속할 db명

	private Statement stmt;
	private ResultSet rs;
    
    public ParkDBConnection() {
        try {
            System.out.println("생성자");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); //DB연결
            System.out.println("success");
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Failed");
            System.out.println(e.getMessage());
            e.getStackTrace();
        
        }
    }
    public void data_insert(String car_number, String Park_Zone_Num) { //고객 테이블 데이터베이스 입력
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //시간 포맷 설정
    		String Time = format.format(System.currentTimeMillis());
    		String query2 = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ "\", 1000)"; //쿼리문
			stmt.executeUpdate(query2); //쿼리 실행
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.getStackTrace();
    	}
    }
}
 