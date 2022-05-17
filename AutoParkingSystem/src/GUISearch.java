import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
    
    private JLabel label = new JLabel("�� �˻��Ͻ� ������ �����Դϴ�.");
    
    private JButton Button = new JButton("Ȯ��"); //Ȯ�� ��ư
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺� ���
    private String[][] rows = {};
    private JTable clientTable = new JTable(rows, header); //�� ���� ���̺�

    private JScrollPane clientPane = new JScrollPane(clientTable);

    GUISearch(){
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
        new GUISearch();
    }
}