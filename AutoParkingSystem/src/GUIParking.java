import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

    private JLabel carNumLabel = new JLabel("�� ������ ���� ��ȣ�� �Է��Ͻʽÿ�.");
    private JLabel placeLabel = new JLabel("�� ������ ��ġ ��ȣ�� �Է��Ͻʽÿ�.");

    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    private JTextField placeText = new JTextField(); //��ġ ��ȣ �Է� â
    
    private JButton cancleButton = new JButton("���"); //��� ��ư
    private JButton parkingButton = new JButton("����"); //���� ��ư

    GUIParking(){ //ȭ�� �⺻ ����
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

        carNumLabel.setLocation(40, 120);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);
        
        placeLabel.setLocation(40, 360);
        placeLabel.setSize(900, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeLabel.setFont(font);

        carNumText.setLocation(160, 220);
        carNumText.setSize(650, 80);
        carNumText.setFont(font);
        
        placeText.setLocation(160, 470);
        placeText.setSize(650, 80);
        placeText.setFont(font);
        
        cancleButton.setLocation(200, 620);
        cancleButton.setSize(200, 80);
        cancleButton.setFont(font);
        
        parkingButton.setLocation(600, 620);
        parkingButton.setSize(200, 80);
        parkingButton.setFont(font);

        p.add(carNumLabel);
        p.add(placeLabel);
        p.add(carNumText);
        p.add(placeText);
        p.add(cancleButton);
        p.add(parkingButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { //��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();        	
            	new GUIMain();
        	}		             
        });
        
        parkingButton.addActionListener(new ActionListener() { //���� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	if(false) { //�ش��ϴ� ��ġ ��ȣ�� �̹� �� �ִ� ���, ��ġ ��ȣ�� ���� ���
                    JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� �̹� �����ϰ� �ִ� �����Դϴ�"); 
            	} else { //���������� ������ �Ϸ��� ���
            		//�� ���̺� �ش� ������ �߰���Ű�� ����ȭ�鿡 �ٷ� �ݿ��Ѵ�. 		
                	dispose();
                	new GUIMain();  //����ȭ������
            	}
        	}		        	             
        });
    }
	
	public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
		new GUIParking();
	}
}