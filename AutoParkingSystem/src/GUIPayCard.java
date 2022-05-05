

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

public class GUIPayCard extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("���� ���"); // ���� ��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //  �����ϱ� ��ư
    
    private Font font = new Font("���", Font.BOLD, 50);

    private JLabel cardLabel = new JLabel("�� �����Ͻ� ī�� ��ȣ�� �Է��Ͻʽÿ�");

    private JTextField cardText = new JTextField(); // ī�� ��ȣ �Է� â

    GUIPayCard(){ // ȭ�� �⺻ ����
        this.setTitle("ī�� ���� ȭ��");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // �� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        cancleButton.setLocation(150, 780);
        cancleButton.setSize(300, 100);
        cancleButton.setFont(font);
        
        payButton.setLocation(550, 780);
        payButton.setSize(300, 100);
        payButton.setFont(font);

        cardLabel.setLocation(50, 300);
        cardLabel.setSize(900, 100);
        cardLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cardLabel.setFont(font.deriveFont(48f));

        cardText.setLocation(45, 410);
        cardText.setSize(900, 100);
        cardText.setFont(font);


        p.add(cancleButton);
        p.add(payButton);
        p.add(cardLabel);
        p.add(cardText);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { // ���� ��� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayMethodChoice();  // ���� ���� ���� ȭ������
        	}		
            	             
        });
        
        payButton.addActionListener(new ActionListener() { // �����ϱ� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(true){ // �ùٸ� ī�� ��ȣ�� ���
            		JOptionPane.showMessageDialog(null, "������ �Ϸ�ƽ��ϴ�");
                	
                	// ������ ������ ���õ� ��� ����
                	
                	dispose();
                	
                	new GUIMain();  // ����ȭ������
            	}else
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���"); // ���� ���� ���� �ԷµǾ��� ��
        	}            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayCard();
	}
}
