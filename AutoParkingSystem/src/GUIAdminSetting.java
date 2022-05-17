import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUIAdminSetting extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

    private JLabel placeLabel = new JLabel("�� ���� ���� ����");
    private JLabel detailLabel = new JLabel("<html>(�ּ� ���� 1ĭ/���� 1ĭ<br/>�ִ� ���� 10ĭ/���� 15ĭ ���� ����)</html>");
    private JLabel widthLabel = new JLabel("���� :");
    private JLabel heightLabel = new JLabel("���� :");
    private JLabel payLabel = new JLabel("�� �ð��� ���� ��� ����");

    private JTextField widthText = new JTextField(); //���� �Է� â
    private JTextField heightText = new JTextField(); //���� �Է� â
    private JTextField payText = new JTextField(); //��� �Է� â
    
    private JButton SettingButton = new JButton("���� ����"); //���� ��ư
    private JButton goMainButton = new JButton("����"); //���� ȭ�� ��ư
    
    File f = new File("������ ������ ����.txt");

    GUIAdminSetting(){ //ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
    }

    private void formDesign() { //�� GUI ��ü ����
        this.add(p);
        p.setLayout(null);
        
        placeLabel.setLocation(100, 20);
        placeLabel.setSize(600, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        detailLabel.setLocation(100, 50);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("���", Font.PLAIN, 30));
        
        widthLabel.setLocation(120, 205);
        widthLabel.setSize(500, 100);
        widthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        widthLabel.setFont(font);
        
        heightLabel.setLocation(120, 315);
        heightLabel.setSize(500, 100);
        heightLabel.setHorizontalAlignment(SwingConstants.LEFT);
        heightLabel.setFont(font);
        
        payLabel.setLocation(100, 430);
        payLabel.setSize(800, 100);
        payLabel.setHorizontalAlignment(SwingConstants.LEFT);
        payLabel.setFont(font);

        widthText.setLocation(270, 220);
        widthText.setSize(400, 80);
        widthText.setFont(font);

        heightText.setLocation(270, 330);
        heightText.setSize(400, 80);
        heightText.setFont(font);
        
        payText.setLocation(110, 530);
        payText.setSize(400, 80);
        payText.setFont(font);
        
        SettingButton.setLocation(400, 650);
        SettingButton.setSize(280, 80);
        SettingButton.setFont(font);
        
        goMainButton.setLocation(400, 650);
        goMainButton.setSize(300, 80);
        goMainButton.setFont(font);

        p.add(placeLabel);
        p.add(detailLabel);
        p.add(widthLabel);
        p.add(heightLabel);
        p.add(payLabel);
        p.add(widthText);
        p.add(heightText);
        p.add(payText);
        
        if(f.exists()) {
        	p.add(SettingButton);
        } else {
        	p.add(goMainButton);
        	try {
        		f.createNewFile();
        	}catch(Exception e) {
        		System.out.println(e.getMessage());
        	}
        }
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
    	goMainButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
    				int width = Integer.parseInt(widthText.getText());
        			int height = Integer.parseInt(heightText.getText());
        			int pay = Integer.parseInt(payText.getText());

        			if(1<=width && width<=10 && 1<=height && height <= 15) { //���� ���� ������ Ȯ��
        				OutputStream os = new FileOutputStream(f);
        				String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð� �� ���� ���:"+pay);
        				byte[] by = str.getBytes();
        				os.write(by);			
        				
        				JOptionPane.showMessageDialog(null, "���� ���� ���������� ����ƽ��ϴ�");
        				dispose(); 
        				new GUIMain(); //����ȭ������
        			} else { //���� ������ ����� �� ����
        				JOptionPane.showMessageDialog(null, "���� �� ���� ���� �Է����ּ���");
        			}
    			} catch(Exception e1) {
    				System.out.println(e1.getMessage());
    				JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
    			}
    		}
    	});
    	
    	SettingButton.addActionListener(new ActionListener() { //���� ���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
    				int width = Integer.parseInt(widthText.getText());
        			int height = Integer.parseInt(heightText.getText());
        			int pay = Integer.parseInt(payText.getText());

        			if(1<=width && width<=10 && 1<=height && height <= 15) { //���� ���� ������ Ȯ��
        				OutputStream os = new FileOutputStream(f);
        				String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð� �� ���� ���:"+pay);
        				System.out.println(str);
        				byte[] by = str.getBytes();
        				os.write(by);
        				
        				JOptionPane.showMessageDialog(null, "�����Ͻ� ���� ���� ���������� ����ƽ��ϴ�");
        				dispose(); 
        				new GUIMain(); //����ȭ������
        			} else { //���� ������ ����� �� ����
        				JOptionPane.showMessageDialog(null, "���� �� ���� ���� �Է����ּ���");
        			}
        			
        			if(false) { //���� �����忡 ���� �����Ǿ������� ����
        				JOptionPane.showMessageDialog(null, "���� ���� �����Ͽ� ���� ������ �Ұ����մϴ�");
        			}
    			} catch(Exception e1) {
    				JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
    			}
    		}
    	});
    }
}