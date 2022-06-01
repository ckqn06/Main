import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ParkDBConnection {
    private Connection conn; //DB Ŀ�ؼ� ���� ��ü
    private static final String USERNAME = "user"; //DBMS(�����ͺ��̽� ���� �ý���)�� �����ϱ� ���� ���̵�
    private static final String PASSWORD = "1234"; //DBMS(�����ͺ��̽� ���� �ý���)�� �����ϱ� ���� ��й�ȣ
    private static final String URL = "jdbc:mysql://localhost:3306/ParkDB?useUnicode=true&characterEncoding=utf8"; //JDBC ����̹� �ּ�

	private Statement stmt; //���� �۾��� �����ϱ� ���� ��ü
	private ResultSet rs; //������ ������ ������� �����ϴ� ��ü
    
    public ParkDBConnection() { //DB ���� ���θ� �˱� ���� �޼ҵ�
        try {
            System.out.println("������"); //DB���� ������ �õ������� Ȯ��
            Class.forName("com.mysql.jdbc.Driver"); //JDBC ����̹� ���� ���� Ȯ��
            System.out.println("success"); //JDBC ����̹��� ������ �ƴٸ� success ���
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); //JDBC ����̹� �ּ�, ���̵�, ��й�ȣ�� ���� DB�� ������ �õ�
            System.out.println("success"); //DB�� ���������� ������ �ƴٸ� success ���
            stmt = conn.createStatement(); //DB�� SQL���� ������ ���� SQLServerStatement ��ü ����
        } catch (Exception e) { //���� �߿� ������ �߻��ߴٸ� Failed�� ���� �޽����� ����ϰ� ���� ó��
            System.out.println("Failed");
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
    
    public void data_insert(String car_number, String Park_Zone_Num) { //DB���Ͽ� �� ������ �Է��ϱ� ���� �޼ҵ�
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"��-��-�� ��:��:��"�� �ð� ���� ����
    		String Time = format.format(System.currentTimeMillis()); //���� �ð��� ���� �̸� ������ ������ ����
    		//ParkDB DB���� ���� ParkGuest ���̺� ���� ��ȣ, ���� �ð�, ��ġ ��ȣ �����͸� ������
    		String Query = "INSERT INTO ParkGuest VALUE (\"" +car_number+ "\", \"" +Time+ "\", \"" +Park_Zone_Num+ "\")";
			stmt.executeUpdate(Query); //SQL��(���� �۾�)�� ������
    	} catch (Exception e) { //�Է��� ���������� ó������ �ʴ´ٸ� ���� ó��
    		System.out.println(e.getMessage());
    		e.getStackTrace();
    	}
    }
    
    public void data_delete(String car_number) { //DB���� ���� ����� �� ������ �����ϱ� ���� �޼ҵ�
    	//ParkDB DB���� ���� ParkGuest ���̺� ���� ��ȣ �����͸� ������
    	String Query = "DELETE FROM ParkGuest WHERE CarNumber = " + "'" + car_number + "'";
    	try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) { //������ ���������� ó������ �ʴ´ٸ� ���� ó��
			e.printStackTrace();
		} 
    }
    
    public String[][] getTable(){ //2���� �迭 ���̺� ���� �������� ���� �޼ҵ�
    	ArrayList<String[]> clientTableValue = new ArrayList<String[]>(); //�� ���̺��� �����͸� �����ϱ� ���� ArrayList ����
    	String Query = "SELECT * FROM ParkGuest"; //ParkDB ���� ParkGuest ���̺� ����� ��� �����͸� ������

    	try {
			rs = stmt.executeQuery(Query);
			
			while(rs.next()) { //��Ұ� �������� ������ ���� ��Ҹ� ������
				//ParGuest ���̺� ����� �����͸� String������ �޾ƿ� (���� ���� ��ȣ, ���� �ð�, ��ġ ��ȣ�� �޾ƿ�)
				clientTableValue.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3)});
	    	}	
		} catch (SQLException e) { //���̺� ���� ���������� ���������� �ʴ´ٸ� ���� ó��
			e.printStackTrace();
		}
    	//�� ���̺��� �����Ͱ� ����� ArrayList�� �迭�� ��ȯ�� ��, ����� ���� ��ȯ��
    	return clientTableValue.toArray(new String[clientTableValue.size()][]);
    }
}