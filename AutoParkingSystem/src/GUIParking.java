

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

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("���"); // ��� ��ư
    private JButton parkingButton = new JButton("����"); // ���� ��ư
    
    private Font font = new Font("���", Font.BOLD, 50);

    private JLabel carNumLabel = new JLabel("�� ������ ���� ��ȣ�� �Է��Ͻʽÿ�");
    private JLabel placeLabel = new JLabel("�� ������ ��ġ ��ȣ�� �Է��Ͻʽÿ�");

    private JTextField carNumText = new JTextField(); // ���� ��ȣ �Է� â
    private JTextField placeText = new JTextField(); // ��ġ ��ȣ �Է� â

    GUIParking(){ // ȭ�� �⺻ ����
        this.setTitle("�����ϱ� ȭ��");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // �� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        cancleButton.setLocation(200, 780);
        cancleButton.setSize(200, 100);
        cancleButton.setFont(font);
        
        parkingButton.setLocation(600, 780);
        parkingButton.setSize(200, 100);
        parkingButton.setFont(font);

        carNumLabel.setLocation(50, 160);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        carNumLabel.setFont(font);
        
        placeLabel.setLocation(50, 410);
        placeLabel.setSize(900, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        carNumText.setLocation(45, 270);
        carNumText.setSize(900, 100);
        carNumText.setFont(font);
        
        placeText.setLocation(45, 520);
        placeText.setSize(900, 100);
        placeText.setFont(font);


        p.add(cancleButton);
        p.add(parkingButton);
        p.add(carNumLabel);
        p.add(placeLabel);
        p.add(carNumText);
        p.add(placeText);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { // ��� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIMain();
        	}		
            	             
        });
        
        parkingButton.addActionListener(new ActionListener() { // ���� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(false) { // �ش��ϴ� ��ġ ��ȣ�� �̹� �� �ִ� ���
                    JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� �̹� �����ϰ� �ִ� �����Դϴ�"); 
            	}else { // ���������� ������ �Ϸ��� ���
            		// �� ���̺� �ش� ������ �߰���Ű�� ����ȭ�鿡 �ٷ� �ݿ��Ѵ�.
            		
                	dispose();
                	
                	new GUIMain();  // ����ȭ������
            	}
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIParking();
	}
}
