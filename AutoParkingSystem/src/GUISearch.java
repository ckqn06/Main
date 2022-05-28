import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
    
    private JLabel label = new JLabel("�� �˻��Ͻ� ������ �����Դϴ�."); //ȭ�鿡 �����ִ� �޽���
    
    private JButton Button = new JButton("Ȯ��"); //Ȯ�� ��ư
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺��� ��� ����
    private String[][] rows = {}; //�� ���̺��� ��(���� ��) ����
    private TableModel tableModel = new DefaultTableModel(rows, header); //������ ����� ���� �ϳ��� ���̺�� ������
    private JTable clientTable = new JTable(tableModel); //�ϳ��� ������ ���̺�� �� ���̺��� ����

    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���
    private JScrollPane clientPane; //�� ���̺��� ��ũ�ѹ� ����
    
    private String[] clientValue; //���̺� ǥ���� ���� �����ϱ� ���� ����

    GUISearch(String[] clientValue){ //ȭ�� �⺻ ����
    	this.clientValue = clientValue;
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
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
        
        //�� ���̺� ���� �����͸� �ٷ�� ���ؼ� DefaultTableModel�� �ҷ���
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        clientModel.addRow(clientValue); //���̺� �˻��� ��� �� �߰�
        
        clientTable.setRowHeight(55);
        clientTable.setFont(font.deriveFont(35f));
        clientTable.getTableHeader().setFont(font);
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(45, 300);
        clientPane.setSize(900, 116);
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //���̺��� ���� ��� ������
        TableColumnModel tcm = clientTable.getColumnModel(); //������ ���̺��� ColumnModel�� ������
        
        for (int i = 0; i < tcm.getColumnCount(); i++) { //���̺��� ���� ��� ������
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 

        p.add(label);
        p.add(Button);
        p.add(clientPane);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        Button.addActionListener(new ActionListener() { //Ȯ�� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //���� ȭ������ �̵�
        	}
        });
    }
}