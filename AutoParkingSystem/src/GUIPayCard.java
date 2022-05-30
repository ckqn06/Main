import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCard extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

    private JLabel cardLabel = new JLabel("�� �����Ͻ� ī�� ��ȣ�� �Է��Ͻʽÿ�."); //ȭ�鿡 �����ִ� �޽���

    private JTextField cardText = new JTextField(); //ī�� ��ȣ �Է� â
    
    private JButton cancleButton = new JButton("���� ���"); //���� ��� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //�����ϱ� ��ư
    
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    
    private String carNum; //���� ���� ��ȣ�� �����ϱ� ���� ����
    private int pay; //���� �����ؾ� �� ���� �����ϱ� ���� ����

    GUIPayCard(String carNum, int pay){ //ȭ�� �⺻ ����
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

        cardLabel.setLocation(30, 250);
        cardLabel.setSize(900, 100);
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setFont(font);

        cardText.setLocation(150, 360);
        cardText.setSize(670, 80);
        cardText.setFont(font);

        cancleButton.setLocation(140, 620);
        cancleButton.setSize(250, 80);
        cancleButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);
        
        p.add(cardLabel);
        p.add(cardText);
        p.add(cancleButton);
        p.add(payButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { //���� ��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	//���� ���� ��ȣ�� ���� �����ؾ� �� ���� �Բ� ���� ���� ���� ȭ������ �̵�
            	new GUIPayMethodChoice(carNum, pay);
        	}		  	             
        });
        
        payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	try {
            		//ī�� ��ȣ �Է� â���� �Է¹��� ���� ������ �޾ƿ�
            		int card_num = Integer.parseInt(cardText.getText());
            		
            		//ī�� ��ȣ �Է� â�� ������ ���� �Էµ� ��� ������ �Ϸ���
            		JOptionPane.showMessageDialog(null, "������ �Ϸ�ƽ��ϴ�");
            		
                	dbc.data_delete(carNum); //������ �Ϸ��� ���� ��ȣ�� ���õ� �����͸� DB���Ͽ��� ����
            		dispose();
                	new GUIMain(); //���� ȭ������ �̵�
            	} catch (Exception e1) { //ī�� ��ȣ �Է� â�� ���� ���� ���� �Էµ� ��� ���� �߻�
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            	}
            }
        });
    }
}