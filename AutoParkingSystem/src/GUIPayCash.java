import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCash extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����
	
    private JLabel cashLabel = new JLabel("�� ���Ա��� ������ �־��ֽʽÿ�."); //ȭ�鿡 �����ִ� �޽���

    private JTextField cashText = new JTextField(); //���� �Է� â
    
    private JButton cancleButton = new JButton("���� ���"); //���� ��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //�����ϱ� ��ư
    
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    
    private String carNum; //���� ���� ��ȣ�� �����ϱ� ���� ����
    private int pay; //���� �����ؾ� �� ���� �����ϱ� ���� ����

    GUIPayCash(String carNum, int pay){ //ȭ�� �⺻ ����
    	this.carNum = carNum;
    	this.pay = pay;
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
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
            	new GUIPayMethodChoice(carNum, pay); //���� ���� ��ȣ�� ���� �����ؾ� �� ���� �Բ� ���� ���� ���� ȭ������ �̵�
        	}		
        });
        
        payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	try {
            		int cash = Integer.parseInt(cashText.getText()); //���� �Է� â���� �Է¹��� ���� ������ �޾ƿ�
            		
                    if(cash >= pay) { //����(cash)�� �����ϴ� ��(pay)�� �����ϸ�
                    	if(cash > pay) { //����(cash)�� �����ϴ� ��(pay)���� ���� ��� �Ž������� ����
                    		JOptionPane.showMessageDialog(null, "�Ž����� " + (cash - pay) + "���� �����߽��ϴ�"); //�Ž����� ���
                    	}
                        	
                    	JOptionPane.showMessageDialog(null, "������ �Ϸ�ƽ��ϴ�"); //������ �Ϸ�Ǹ� �޽��� ���
                    	
                    	dbc.data_delete(carNum); //������ �Ϸ��� ���� ��ȣ�� ���õ� �����͸� DB���Ͽ��� ����
                    	dispose();
                    	new GUIMain();
                    } else { //����(cash)�� �����ϴ� ��(pay)���� ���� ���
                    	JOptionPane.showMessageDialog(null, "�����Ͻ� ������ ������ ���� ��뺸�� �����ϴ�");
                    }
            	} catch (Exception e1) { //���� �Է� â�� ���� ���� ���� �Էµ� ��� ���� �߻�
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            	} 
            }
        });
    }
}