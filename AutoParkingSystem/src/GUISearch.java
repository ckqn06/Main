import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
    
    private JLabel label = new JLabel("�� �˻��Ͻ� ������ �����Դϴ�."); //ȭ�鿡 �����ִ� �޽���
    
    private JButton backButton = new JButton("���ư���"); //���ư��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //�����ϱ� ��ư
    
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
        
        label.setLocation(30, 180);
        label.setSize(900, 100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        
        backButton.setLocation(160, 620);
        backButton.setSize(250, 80);
        backButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);
        
        //�� ���̺� ���� �����͸� �ٷ�� ���ؼ� DefaultTableModel�� �ҷ���
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        clientModel.addRow(clientValue); //���̺� �˻��� ��� �� �߰�
        
        clientTable.setRowHeight(55);
        clientTable.getTableHeader().setFont(new Font("���", Font.PLAIN, 30));
        clientTable.setFont(new Font("���", Font.PLAIN, 30));
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(45, 320);
        clientPane.setSize(900, 104);
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //���̺��� ���� ��� ������
        TableColumnModel tcm = clientTable.getColumnModel(); //������ ���̺��� ColumnModel�� ������
        
        for (int i = 0; i < tcm.getColumnCount(); i++) { //���̺��� ���� ��� ������
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 

        p.add(label);
        p.add(backButton);
        p.add(payButton);
        p.add(clientPane);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
    	backButton.addActionListener(new ActionListener() { //���ư��� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //���� ȭ������ �̵�
        	}
        });
    	
    	payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		int result = JOptionPane.showConfirmDialog(null, "�ش� �������� ������ �����Ͻðڽ��ϱ�?", "���� ����", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) { //Yes�� ������ ���
        			String carNum = clientValue[0]; //��� ���̺� ����� ���� ��ȣ�� �����ϴ� ����
        			String won = clientValue[3].replace(" ��", ""); //��� ���̺� ����� ���� ��뿡�� "��"�� ������ ���� �����ϴ� ����
        			int pay = Integer.parseInt(won); //������ ���� ��� ���� int������ ��ȯ
        			
        			dispose();
        			new GUIPayMethodChoice(carNum, pay); //��� ���̺� ����� ���� ��ȣ�� ���� ���� �Բ� ���� ���� ȭ������ �̵�
        		}
        	}
        });
    }
}