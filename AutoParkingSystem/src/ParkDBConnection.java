
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


 
public class ParkDBConnection {
    private Connection conn; //DB 커넥션 연결 객체
    private static final String USERNAME = "user";//DBMS접속 시 아이디
    private static final String PASSWORD = "1234";//DBMS접속 시 비밀번호
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
    		String query2 = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ ")"; //쿼리문
			stmt.executeUpdate(query2); //쿼리 실행
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.getStackTrace();
    	}
    }
    public void data_delete(String car_number) {
    	String Query = "DELETE FROM ParkGuest WHERE CarNumber = " + car_number;
    	try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    public String[][] getTable(){ //2중 배열 테이블값을 가져오기
    	String[][] a = new String[150][3]; //가로 4(값)와 세로 150개를 만들기
    	String Query = "SELECT * FROM ParkGuest"; 
    	int i = 0;
    	try {
			rs = stmt.executeQuery(Query); //쿼리 실행
			while(rs.next()) {
	    		for(int j = 0; j <3; j++) {
	    			a[i][j] = rs.getString(j + 1); //테이블 값 저장
	    		}
	    		i++;
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return a;
    }
}
 