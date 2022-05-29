import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.io.*;

public class GUIAdmin extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
	
	private JLabel Label = new JLabel("�� �̰��� ������ ���� ȭ���Դϴ�."); //ȭ�鿡 �����ִ� �޽���
	
	private JButton dataButton = new JButton("������ ������ ���� �� ����"); //������ ������ ���� �� ���� ��ư
    private JButton restrictButton = new JButton("���� ���� ���� ����"); //���� ���� ���� ���� ��ư
    private JButton changeButton = new JButton("������ ���� ID/PW ����"); //������ ���� ID/PW ���� ��ư
    private JButton gomainButton = new JButton("���� ȭ������ ���ư���"); //���� ȭ������ ���ư��� ��ư
	
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    
	GUIAdmin() {
		this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
	}
	
	private void formDesign() {	
    	this.add(p);
        p.setLayout(null);
        
        Label.setLocation(30, 40);
        Label.setSize(900, 100);
        Label.setHorizontalAlignment(SwingConstants.CENTER);
        Label.setFont(font);
        
        dataButton.setLocation(185, 160);
        dataButton.setSize(600, 80);
        dataButton.setFont(font);
        
        restrictButton.setLocation(185, 310);
        restrictButton.setSize(600, 80);
        restrictButton.setFont(font);
        
        changeButton.setLocation(185, 460);
        changeButton.setSize(600, 80);
        changeButton.setFont(font);
        
        gomainButton.setLocation(185, 610);
        gomainButton.setSize(600, 80);
        gomainButton.setFont(font);

        p.add(Label);
        p.add(dataButton);
        p.add(restrictButton);
        p.add(changeButton);
        p.add(gomainButton);
    }
	
	private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
		dataButton.addActionListener(new ActionListener() { //������ ������ ���� �� ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
        		
    			if(clientTableValue[0][0] == null) { //���� �����忡 ���� �����Ǿ����� ������ Ȯ��
        				dispose(); 
        				new GUIAdminSetting(); //������ ������ ���� �� ���� ȭ������ �̵�
        		}else {
        			JOptionPane.showMessageDialog(null, "���� ���� �����Ͽ� ���� ������ �Ұ����մϴ�");
        		}
        	}
        });
		
		restrictButton.addActionListener(new ActionListener() { //���� ���� ���� ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //���� ȭ������ �̵�
        	}
        });
		
		changeButton.addActionListener(new ActionListener() { //������ ���� ID/PW ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIChangeLogin(); //������ ���� ID/PW ���� ȭ������ �̵�
        	}
        });
		
		gomainButton.addActionListener(new ActionListener() { //���� ȭ������ ���ư��� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //���� ȭ������ �̵�
        	}
        });
    }
	
	public static void main(String[] args) {
		new GUIAdmin();
	}
}