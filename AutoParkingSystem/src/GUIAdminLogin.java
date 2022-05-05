
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUIAdminLogin extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton loginButton = new JButton("�α���"); // �α��� ��ư
    private JButton BackButton = new JButton("���ư���"); // ���ư��� ��ư
    
    private Font font = new Font("����", Font.BOLD, 50);

    private JLabel IDLabel = new JLabel("������ ID :");
    private JLabel passwordLabel = new JLabel("��й�ȣ :");
    private JLabel logoLabel = new JLabel("������ �α���");

    private JTextField IDText = new JTextField(); // ID �Է� â
    private JTextField passwordText = new JTextField(); // ��й�ȣ �Է� â

    GUIAdminLogin(){ // ȭ�� �⺻ ����
        this.setTitle("������ �α��� ȭ��");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // �� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        loginButton.setLocation(400, 780);
        loginButton.setSize(200, 100);
        loginButton.setFont(font);
        
        BackButton.setLocation(650, 780);
        BackButton.setSize(250, 100);
        BackButton.setFont(font);
        
        logoLabel.setLocation(100, 160);
        logoLabel.setSize(800, 100);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setFont(font.deriveFont(70f));

        IDLabel.setLocation(0, 380);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(font);

        passwordLabel.setLocation(0, 550);
        passwordLabel.setSize(400, 100);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(font);

        IDText.setSize(400, 100);
        IDText.setLocation(450, 380);
        IDText.setFont(font);

        passwordText.setSize(400, 100);
        passwordText.setLocation(450, 550);
        passwordText.setFont(font);


        p.add(loginButton);
        p.add(BackButton);
        p.add(logoLabel);
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
        loginButton.addActionListener(new ActionListener() { // �α��� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(IDText.getText().equals("admin")  // ID�� ��й�ȣ�� ������ ����
            			&& passwordText.getText().equals("park123")) {
            		dispose();
            		
            		new GUIAdminSetting();
            	}else 
                    JOptionPane.showMessageDialog(null, "������ ID Ȥ�� ��й�ȣ�� Ʋ�Ƚ��ϴ�"); // ID�� ��й�ȣ�� Ʋ���� ����
        	}		
            	             
        });
        
        BackButton.addActionListener(new ActionListener() { // ���ư��� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIMain();
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIAdminLogin();
	}
}