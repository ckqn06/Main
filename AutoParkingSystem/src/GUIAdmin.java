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
	
	private JButton dataButton = new JButton("������ ������ ���� �� ����"); //������ ������ ���� �� ���� ȭ������ �̵� ��ư
    private JButton restrictButton = new JButton("Ư�� ���� ���� ����"); //Ư�� ���� ���� ���� ȭ������ �̵� ��ư
    private JButton changeButton = new JButton("������ ���� ID/PW ����"); //������ ���� ID/PW ���� ȭ������ �̵� ��ư
    private JButton gomainButton = new JButton("���� ȭ������ ���ư���"); //���� ȭ������ ���ư��� ��ư
    
    private ServerConnection sct = new ServerConnection(); //���� ���� ��ü
    
	GUIAdmin() { //ȭ�� �⺻ ����
		this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
	}
	
	private void formDesign() {	//�� GUI ��ü ����
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
		dataButton.addActionListener(new ActionListener() { //������ ������ ���� �� ���� ȭ������ �̵� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData(); //������ ���� DB���� ���� �� ���̺��� ������
        		
        		//�� ���̺� �ƹ��� ���� ���ٸ�(= ���� �����忡 ���� �������� �ʴ´ٸ�)
    			if(clientTableValue.length == 0) {
        				dispose(); 
        				new GUIAdminSetting(); //������ ������ ���� �� ���� ȭ������ �̵�
        		}else { //�� ���̺� ���� ����� ���� Ȯ�εȴٸ�(= ���� �����忡 ���� �����Ѵٸ�) ���� ������ ����
        			JOptionPane.showMessageDialog(null, "���� ���� �����Ͽ� ���� ������ �Ұ����մϴ�");
        		}
        	}
        });
		
		restrictButton.addActionListener(new ActionListener() { //Ư�� ���� ���� ���� ȭ������ �̵� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData();
        		
    			if(clientTableValue.length == 0) {
        				dispose(); 
        				new GUIRestrict(); //Ư�� ���� ���� ���� ȭ������ �̵�
        		}else {
        			JOptionPane.showMessageDialog(null, "���� ���� �����Ͽ� ���� ������ �Ұ����մϴ�");
        		}
        	}
        });
		
		changeButton.addActionListener(new ActionListener() { //������ ���� ID/PW ���� ȭ������ �̵� ��ư Ŭ�� �� ����
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
}