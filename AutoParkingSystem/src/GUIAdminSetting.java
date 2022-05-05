
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

public class GUIAdminSetting extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton SettingButton = new JButton("���� ����"); // ���� ��ư
    private JButton goMainButton = new JButton("����ȭ������"); // ����ȭ������ ��ư
    
    private Font font = new Font("����", Font.BOLD, 50);

    private JLabel placeLabel = new JLabel("�� ���� ���� ����");
    private JLabel detailLabel = new JLabel("<html>(�ּ� ���� 1ĭ/���� 1ĭ<br/>�ִ� ���� 10ĭ ���� 15ĭ ���� ����)</html>");
    private JLabel widthLabel = new JLabel("���� :");
    private JLabel heightLabel = new JLabel("���� :");
    private JLabel payLabel = new JLabel("�� �ð��� ���� ��� ����");

    private JTextField widthText = new JTextField(); // ���� �Է� â
    private JTextField heightText = new JTextField(); // ���� �Է� â
    private JTextField payText = new JTextField(); // ��� �Է� â

    GUIAdminSetting(){  // ȭ�� �⺻ ����
        this.setTitle("������ ���� ȭ��");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // �� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        SettingButton.setLocation(240, 780);
        SettingButton.setSize(280, 100);
        SettingButton.setFont(font);
        
        goMainButton.setLocation(550, 780);
        goMainButton.setSize(400, 100);
        goMainButton.setFont(font);
        
        placeLabel.setLocation(100, 70);
        placeLabel.setSize(600, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        detailLabel.setLocation(100, 100);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("����", Font.PLAIN, 40));
        
        widthLabel.setLocation(120, 280);
        widthLabel.setSize(500, 100);
        widthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        widthLabel.setFont(font);
        
        heightLabel.setLocation(120, 410);
        heightLabel.setSize(500, 100);
        heightLabel.setHorizontalAlignment(SwingConstants.LEFT);
        heightLabel.setFont(font);
        
        payLabel.setLocation(100, 540);
        payLabel.setSize(800, 100);
        payLabel.setHorizontalAlignment(SwingConstants.LEFT);
        payLabel.setFont(font);

        widthText.setLocation(300, 280);
        widthText.setSize(400, 100);
        widthText.setFont(font);

        heightText.setLocation(300, 410);
        heightText.setSize(400, 100);
        heightText.setFont(font);
        
        payText.setLocation(110, 640);
        payText.setSize(400, 100);
        payText.setFont(font);


        p.add(SettingButton);
        p.add(goMainButton);
        p.add(placeLabel);
        p.add(detailLabel);
        p.add(widthLabel);
        p.add(heightLabel);
        p.add(payLabel);
        p.add(widthText);
        p.add(heightText);
        p.add(payText);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
    		SettingButton.addActionListener(new ActionListener() { // ���� ��ư Ŭ�� �� ����
    			 public void actionPerformed(ActionEvent e) {
    				 if(false) { // ���� �����忡 ���� �����Ǿ������� ����
    					 JOptionPane.showMessageDialog(null, "���� ������ �����Ͽ� ���� ������ �Ұ����մϴ�");
    				 }
    				 
    				 if(true) { // ���ڸ� ������ �ִ��� Ȯ��
    					 if(true) { // ���� ���� ������ Ȯ��
    						 // ���� ���Ͽ� ����, ����, ��� �� �Է�
    						 // ����ȭ�鿡 �ݿ�
        	                 JOptionPane.showMessageDialog(null, "�����Ͻ� ���� ���� ���������� ����ƽ��ϴ�"); // ���� ������ ����� �� ����
    						 
    						 dispose(); 
    						 
    						 new GUIMain();  // ����ȭ������
    					 } else
        	                 JOptionPane.showMessageDialog(null, "���� �� ���� ���� �Է����ּ���"); // ���� ������ ����� �� ����

    				 } else 
    	                 JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���"); // ���� �̿��� ���� ���� ���� �� ����
    			 }
    		});
    		
    		goMainButton.addActionListener(new ActionListener() { // ����ȭ������ ��ư Ŭ�� �� ����
   			 public void actionPerformed(ActionEvent e) {
   				 dispose();
   				 
   				 new GUIMain(); //����ȭ������
   			 }
   		});
        

    }
    
    public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIAdminSetting();
	}
}