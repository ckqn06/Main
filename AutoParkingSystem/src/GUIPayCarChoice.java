

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

public class GUIPayCarChoice extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("���"); // ��� ��ư
    private JButton checkButton = new JButton("�Է�"); // �Է� ��ư
    
    private Font font = new Font("���", Font.BOLD, 50);

    private JLabel carNumLabel = new JLabel("�� ������ ������ȣ�� �Է��Ͻʽÿ�");

    private JTextField carNumText = new JTextField(); // ���� ��ȣ �Է� â

    GUIPayCarChoice(){ // ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� ȭ��");
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
        
        checkButton.setLocation(600, 780);
        checkButton.setSize(200, 100);
        checkButton.setFont(font);

        carNumLabel.setLocation(50, 300);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        carNumLabel.setFont(font);

        carNumText.setLocation(45, 410);
        carNumText.setSize(900, 100);
        carNumText.setFont(font);


        p.add(cancleButton);
        p.add(checkButton);
        p.add(carNumLabel);
        p.add(carNumText);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { // ��� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIMain();  // ����ȭ������
        	}		
            	             
        });
        
        checkButton.addActionListener(new ActionListener() { // �Է� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(false) { // �ش��ϴ� ������ȣ�� ���� ���
                    JOptionPane.showMessageDialog(null, "�ش� ������ȣ�� �������� �̿����� �ʴ� �����Դϴ�"); 
            	}else {
                	dispose();
                	
                	new GUIPayMethodChoice();  // ���� ���� ���� ȭ������
            	}
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayCarChoice();
	}
}
