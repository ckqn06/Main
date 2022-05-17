import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class GUIFirst extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 45); //��Ʈ ����

    private JLabel logoLabel = new JLabel("���� ���� ���� �ý���"); //ȭ�� �ΰ�
    private JLabel IDLabel = new JLabel("������ ID :"); //ID �޽���
    private JLabel passwordLabel = new JLabel("��й�ȣ :"); //��й�ȣ �޽���

    private JTextField IDText = new JTextField(); //ID �Է� â
    private JTextField passwordText = new JTextField(); //��й�ȣ �Է� â
    
    private JButton loginButton = new JButton("�α���"); //�α��� ��ư

    File f = new File("C://Server/������ ������ ����.txt");

    GUIFirst(){ //ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
    }

    private void formDesign() { //�� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        logoLabel.setLocation(100, 100);
        logoLabel.setSize(800, 100);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setFont(font.deriveFont(70f));
        
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
        
        loginButton.setLocation(400, 620);
        loginButton.setSize(200, 100);
        loginButton.setFont(font);

        p.add(logoLabel);
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
        p.add(loginButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        loginButton.addActionListener(new ActionListener() { //�α��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	//ID�� ��й�ȣ�� �´ٸ�
            	if(IDText.getText().equals("admin") && passwordText.getText().equals("park123")) {
            		if(f.exists()) {
            			dispose();
            			new GUIMain();
                    } else {
                    	JOptionPane.showMessageDialog(null, "���� �ý��ۿ� ������ ������ ������ �������� �ʽ��ϴ�.");
                    	dispose();
                    	new GUIAdminSetting();
                    }
            	} else { //ID�� ��й�ȣ�� Ʋ���ٸ�
            		JOptionPane.showMessageDialog(null, "������ ID Ȥ�� ��й�ȣ�� Ʋ�Ƚ��ϴ�");
            	}
        	}		 	             
        });
    }
    
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIFirst();
	}
}