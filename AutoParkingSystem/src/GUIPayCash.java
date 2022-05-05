

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

public class GUIPayCash extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("���� ���"); // ���� ��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //  �����ϱ� ��ư
    
    private Font font = new Font("���", Font.BOLD, 50);

    private JLabel cashLabel = new JLabel("�� ���Ա��� ������ �־��ֽʽÿ�");

    private JTextField cashText = new JTextField(); // ���� �Է� â

    GUIPayCash(){ // ȭ�� �⺻ ����
        this.setTitle("���� ���� ȭ��");
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

        cashLabel.setLocation(50, 300);
        cashLabel.setSize(900, 100);
        cashLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cashLabel.setFont(font);

        cashText.setLocation(45, 410);
        cashText.setSize(900, 100);
        cashText.setFont(font);


        p.add(cancleButton);
        p.add(payButton);
        p.add(cashLabel);
        p.add(cashText);
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
            	if(true) { // �ùٸ� ���� �ԷµǾ��� ���
                    int cash = Integer.parseInt(cashText.getText());  // ���� �Է��� ���� ��
                    
                    if(true) { // ������ ����� ��� cash >= �����ϴ� ��
                    	if(false) { // ������ �����ϴ� ������ ���� ��� cash > �����ϴ� ��
                    		JOptionPane.showMessageDialog(null, "�Ž����� 1000���� �����߽��ϴ�");  // �Ž����� ����� ���� �ִ´�.
                    	}
                        	
                    	JOptionPane.showMessageDialog(null, "������ �Ϸ�ƽ��ϴ�");
                    	
                    	// ������ ������ ���õ� ��� ����
                    	
                    	dispose();
                    	
                    	new GUIMain();
                    }else
                    	JOptionPane.showMessageDialog(null, "�����Ͻ� ������ ������ ���� ��뺸�� �����ϴ�");
            	}else 
                	JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���"); // ���� ���� ���� �ԷµǾ��� ��
        	}            	             
        });

    }
	
	
	public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayCash();
	}
}
