
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


 
public class ParkDBConnection {
    private Connection conn; //DB Ŀ�ؼ� ���� ��ü
    private static final String USERNAME = "user";//DBMS���� �� ���̵�
    private static final String PASSWORD = "1234";//DBMS���� �� ��й�ȣ
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
    		String query2 = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ ")"; //������
			stmt.executeUpdate(query2); //���� ����
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
    public String[][] getTable(){ //2�� �迭 ���̺��� ��������
    	String[][] a = new String[150][3]; //���� 4(��)�� ���� 150���� �����
    	String Query = "SELECT * FROM ParkGuest"; 
    	int i = 0;
    	try {
			rs = stmt.executeQuery(Query); //���� ����
			while(rs.next()) {
	    		for(int j = 0; j <3; j++) {
	    			a[i][j] = rs.getString(j + 1); //���̺� �� ����
	    		}
	    		i++;
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return a;
    }
}
 