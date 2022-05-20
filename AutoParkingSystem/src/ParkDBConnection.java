import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


 
public class ParkDBConnection {
    private Connection conn; //DB Ŀ�ؼ� ���� ��ü
    private static final String USERNAME = "root";//DBMS���� �� ���̵�
    private static final String PASSWORD = "Opensource202024**";//DBMS���� �� ��й�ȣ
    private static final String URL = "jdbc:mysql://localhost:9001/ParkDB";//DBMS������ db��

	private Statement stmt;
	private ResultSet rs;
    
    public ParkDBConnection() {
        try {
            System.out.println("������");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); //DB����
            System.out.println("success");
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Failed");
            System.out.println(e.getMessage());
            e.getStackTrace();
        
        }
    }
    public void data_insert(String car_number, String Park_Zone_Num) { //�� ���̺� �����ͺ��̽� �Է�
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //�ð� ���� ����
    		String Time = format.format(System.currentTimeMillis());
    		String query2 = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ "\", 1000)"; //������
			stmt.executeUpdate(query2); //���� ����
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.getStackTrace();
    	}
    }
}
 