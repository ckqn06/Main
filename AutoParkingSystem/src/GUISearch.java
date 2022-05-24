import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
    
    private JLabel label = new JLabel("�� �˻��Ͻ� ������ �����Դϴ�.");
    
    private JButton Button = new JButton("Ȯ��"); //Ȯ�� ��ư
    
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺� ���
    private String[][] rows = {};
    private TableModel tableModel = new DefaultTableModel(rows, header);
    private JTable clientTable = new JTable(tableModel); //�� ���� ���̺�

    private JScrollPane clientPane;
    
    private String[] clientValue;

    GUISearch(String[] clientValue){
    	this.clientValue = clientValue;
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
    }

    private void formDesign() {	
    	this.add(p);
        p.setLayout(null);
        
        label.setLocation(30, 150);
        label.setSize(900, 100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        
        Button.setLocation(370, 620);
        Button.setSize(200, 80);
        Button.setFont(font);
        
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        clientModel.addRow(clientValue); //���̺� �� �߰�
        
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(250, 320);
        clientPane.setSize(450, 200);

        p.add(label);
        p.add(Button);
        p.add(clientPane);
    }

    private void eventListner() {
        Button.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain();  //���� ȭ������
        	}
        });
    }

    public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
        new GUISearch(new String[]{"1111", "2003-12-04 12:22:34", "A1", "10000"});
    }
}