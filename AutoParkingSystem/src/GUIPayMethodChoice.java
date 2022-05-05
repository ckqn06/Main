

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

public class GUIPayMethodChoice extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cashButton = new JButton("�������� ����"); // �������� ���� ��ư
    private JButton cardButton = new JButton("ī��� ����"); // ī��� ���� ��ư
    
    private Font font = new Font("���", Font.BOLD, 40);

    private JLabel feeLabel = new JLabel("�� ������ �� 15000�� ��ŭ"); // �����ؾ� �� ���� �����Ѵ�.
    private JLabel feeLabel2 = new JLabel("�����ؾ� �մϴ�");
    private JLabel choiceLabel = new JLabel("���Ͻô� ���������� �����Ͻʽÿ�");

    private JTextField carNumText = new JTextField(); // ���� ��ȣ �Է� â

    GUIPayMethodChoice(){ // ȭ�� �⺻ ����
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

        cashButton.setLocation(150, 730);
        cashButton.setSize(300, 100);
        cashButton.setFont(font);
        
        cardButton.setLocation(570, 730);
        cardButton.setSize(280, 100);
        cardButton.setFont(font);

        feeLabel.setLocation(50, 250);
        feeLabel.setSize(900, 100);
        feeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel.setFont(font.deriveFont(50f));
        
        feeLabel2.setLocation(50, 305);
        feeLabel2.setSize(900, 100);
        feeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel2.setFont(font.deriveFont(50f));

        choiceLabel.setLocation(45, 450);
        choiceLabel.setSize(900, 100);
        choiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choiceLabel.setFont(font.deriveFont(50f));


        p.add(cashButton);
        p.add(cardButton);
        p.add(feeLabel);
        p.add(feeLabel2);
        p.add(choiceLabel);
    }

    private void eventListner() { // ��ư Ŭ�� �̺�Ʈ ����
    	cashButton.addActionListener(new ActionListener() { // �������� ���� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayCash();  // ���� ���� ȭ������
        	}		
            	             
        });
        
    	cardButton.addActionListener(new ActionListener() { // ī��� ���� ��ư Ŭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayCard();  // ī�� ���� ȭ������
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayMethodChoice();
	}
}
