import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIAdminLogin extends JFrame{
	private JPanel p = new JPanel(); //���� ������ ����
	private Font font = new Font("���� ���", Font.BOLD, 45); //��Ʈ ����

    private JLabel logoLabel = new JLabel("���� ���� ���� �ý���"); //ȭ�� �ΰ�
    private JLabel IDLabel = new JLabel("������ ID :"); //ID �޽���
    private JLabel passwordLabel = new JLabel("��й�ȣ :"); //��й�ȣ �޽���

    private JTextField IDText = new JTextField(); //ID �Է� â
    private JTextField passwordText = new JTextField(); //��й�ȣ �Է� â
    
    private JButton loginButton = new JButton("�α���"); //�α��� ��ư
    private JButton BackButton = new JButton("���ư���"); //���ư��� ��ư
    
    private int op; //���� ��� Ȯ���� ���� ����
    private String ID, PW; //ID, ��й�ȣ Ȯ���� ���� ����
    
    private ServerConnection sct = new ServerConnection(); //���� ���� ��ü

    GUIAdminLogin(int op){ //ȭ�� �⺻ ����, op=1(ù �α���), op=2(������ ���� �α���), op=3(�����ϱ�)
    	this.op = op;
        this.setTitle("���� ���� ���� �ý���"); //â Ÿ��Ʋ �� ����
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x��ư�� ���� ȭ�� ����
        this.formDesign(); //�� GUI ��ü ����
        this.eventListner(); //�̺�Ʈ�� ó����
        this.setSize(1000,800); //ȭ�� ũ�� ����
        this.setVisible(true); //â�� ȭ�鿡 ������ ������ ����
        setLocationRelativeTo(null); //â�� ȭ�� ����� ��ġ
    }

    private void formDesign() { //�� GUI ��ü ����
    	try {
    		if(sct.isSetting()) { //������ ������ ���Ͽ��� �ؽ�Ʈ�� �о����
            	String[] settingData = sct.getSetting();
            	
            	ID = settingData[3]; //�迭�� 4��°�� ����� ID�� ������ �����ϱ� ���� ����
            	PW = settingData[4]; //�迭�� 5��°�� ����� ��й�ȣ�� ������ �����ϱ� ���� ����
    		}
        } catch(Exception e) { //���� ó��
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p); //���� ������ ����
        p.setLayout(null); //���� �������� ���̾ƿ� ����
        
        logoLabel.setLocation(100, 100); //��ġ ����
        logoLabel.setSize(800, 100); //ũ�� ����
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER); //���� ��ġ
        logoLabel.setFont(font.deriveFont(70f)); //�ؽ�Ʈ ��Ʈ ����
        
        IDLabel.setLocation(0, 300);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(font);

        passwordLabel.setLocation(0, 470);
        passwordLabel.setSize(400, 100);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(font);
        
        IDText.setSize(400, 100);
        IDText.setLocation(450, 300);
        IDText.setFont(font);

        passwordText.setSize(400, 100);
        passwordText.setLocation(450, 470);
        passwordText.setFont(font);

        if(op != 1) { //ù �α����� �ƴ϶�� �α��� ��ư�� ���������� �̵� (= ���ư��� ��ư�� ������)
        	loginButton.setLocation(600, 620);
            loginButton.setSize(250, 100);
            
            if(op == 3) //���� ȭ�鿡�� �ý��� ���� ��ư�� Ŭ���� ���
            	loginButton.setText("�����ϱ�"); //�α��� ��ư�� �ؽ�Ʈ�� "�α���"���� "�����ϱ�"�� ����
        
        }else { //ù �α����̶�� �α��� ��ư�� �������� �̵� (= ���ư��� ��ư�� ������)
        	loginButton.setLocation(400, 620); 
            loginButton.setSize(200, 100);
        }
        loginButton.setFont(font);
        
        BackButton.setLocation(160, 620);
        BackButton.setSize(250, 100);
        BackButton.setFont(font);

        p.add(logoLabel); //ȭ�� �ΰ� ���� �����ӿ� �߰���
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
        p.add(loginButton);
        
        if(op != 1) //ù �α����� �ƴ϶�� ���ư��� ��ư ����
        	p.add(BackButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        loginButton.addActionListener(new ActionListener() { //�α��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	
            	if(sct.isSetting()) { //������ ������ ������ ���� ��ο� �����ϸ鼭
            		//������ ������ ���Ͽ� ������ ���̵�� ��й�ȣ�� ID/��й�ȣ �Է� â�� �Է��� ���� �����ϴٸ�
            		if(IDText.getText().equals(ID) && passwordText.getText().equals(PW)) {
            			if(op == 1) { //�ý����� ������� ù �α����� �����ϴ� ���
            				dispose(); //���� �����ְ� �ִ� â(������)�� ����
                			new GUIMain(); //���� ȭ������ �̵�
                		} else if(op == 2) { //������ ���� ��ư�� ���� �� ���
                			dispose();
                			new GUIAdmin(); //������ ���� ȭ������ �̵�
                		} else { //�����ϱ� ��ư�� ���� �� ���
                			int result = JOptionPane.showConfirmDialog(null, "������ �ý����� �����Ͻðڽ��ϱ�?", "�ý��� ����", JOptionPane.YES_NO_OPTION);
                    		if(result == JOptionPane.YES_OPTION) //Yes�� ������ ���
                    			System.exit(0); //�ý����� �����Ŵ
                		}
            		} else { //ID �Ǵ� ��й�ȣ�� Ʋ���ٸ�
            			JOptionPane.showMessageDialog(null, "������ ID Ȥ�� ��й�ȣ�� Ʋ�Ƚ��ϴ�");
            		}
            		
            	} else { //������ ������ ������ ���� ��ο� �������� �����鼭
            		if(IDText.getText().equals("admin") && passwordText.getText().equals("park123")) { //Default ID�� ��й�ȣ�� �´ٸ�
            			if(op == 1) {
                			JOptionPane.showMessageDialog(null, "���� �ý��ۿ� ������ ������ ������ �������� �ʽ��ϴ�.");
                        	dispose();
                        	new GUIAdminSetting(); //������ ������ ���� �� ���� ȭ������ �̵�
                		} else if(op == 2) {
                			dispose();
                			new GUIAdmin();
                		} else { 
                			int result = JOptionPane.showConfirmDialog(null, "������ �ý����� �����Ͻðڽ��ϱ�?", "�ý��� ����", JOptionPane.YES_NO_OPTION);
                    		if(result == JOptionPane.YES_OPTION)
                    			System.exit(0);
                		}
            		} else {
            			JOptionPane.showMessageDialog(null, "������ ID Ȥ�� ��й�ȣ�� Ʋ�Ƚ��ϴ�");
            		}
            	}
        	}		 	             
        });
        
        BackButton.addActionListener(new ActionListener() { //���ư��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new GUIMain();
        	}		    	             
        });
    }
    
    public static void main(String[] args) {
		new GUIAdminLogin(1); //�ý����� �����ų ��, ù �α��� ȭ������ ����
	}
}