

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
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
	
    private JLabel cashLabel = new JLabel("�� ���Ա��� ������ �־��ֽʽÿ�.");

    private JTextField cashText = new JTextField(); //���� �Է� â
    
    private JButton cancleButton = new JButton("���� ���"); //���� ��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //�����ϱ� ��ư

    GUIPayCash(){ //ȭ�� �⺻ ����
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

        cashLabel.setLocation(30, 250);
        cashLabel.setSize(900, 100);
        cashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cashLabel.setFont(font);

        cashText.setLocation(250, 360);
        cashText.setSize(450, 80);
        cashText.setFont(font);
        
        cancleButton.setLocation(140, 620);
        cancleButton.setSize(250, 80);
        cancleButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);

        p.add(cashLabel);
        p.add(cashText);
        p.add(cancleButton);
        p.add(payButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { //���� ��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new GUIPayMethodChoice(); //���� ���� ���� ȭ������
        	}		
        });
        
        payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	try { //�ùٸ� ���� �ԷµǾ��� ���
            		int cash = Integer.parseInt(cashText.getText()); //���� �Է��� ���� ��
            		
                    if(true) { //������ ����� ��� cash >= �����ϴ� ��
                    	if(false) { //������ �����ϴ� ������ ���� ��� cash > �����ϴ� ��
                    		JOptionPane.showMessageDialog(null, "�Ž����� 1000���� �����߽��ϴ�"); //�Ž����� ���
                    	}
                        	
                    	JOptionPane.showMessageDialog(null, "������ �Ϸ�ƽ��ϴ�");
                    	
                    	// ������ ������ ���õ� ��� ����
                    	
                    	dispose();
                    	new GUIMain();
                    } else {
                    	JOptionPane.showMessageDialog(null, "�����Ͻ� ������ ������ ���� ��뺸�� �����ϴ�");
                    }
            	} catch (Exception e1) { //���� ���� ���� �ԷµǾ��� ��
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            	} 
            }
        });
    }
	
	public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayCash();
	}
}