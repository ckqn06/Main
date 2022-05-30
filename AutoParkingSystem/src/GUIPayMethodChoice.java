import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayMethodChoice extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 50); //��Ʈ ����

	//ȭ�鿡 �����ִ� �޽���
    private JLabel feeLabel = new JLabel();
    private JLabel feeLabel2 = new JLabel("�����ؾ� �մϴ�.");
    private JLabel choiceLabel = new JLabel("���Ͻô� ���� ������ �����Ͻʽÿ�.");

    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    
    private JButton cashButton = new JButton("�������� ����"); //�������� ���� ��ư
    private JButton cardButton = new JButton("ī��� ����"); //ī��� ���� ��ư
    
    private String carNum; //���� ���� ��ȣ�� �����ϱ� ���� ����
    private int pay; //���� �����ؾ� �� ���� �����ϱ� ���� ����

    GUIPayMethodChoice(String carNum, int pay){ //ȭ�� �⺻ ����
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

        feeLabel.setText("�� ������ �� " + pay + "�� ��ŭ"); //���� �����ؾ� �� ���� ǥ����
        feeLabel.setLocation(50, 200);
        feeLabel.setSize(900, 100);
        feeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel.setFont(font);
        
        feeLabel2.setLocation(50, 275);
        feeLabel2.setSize(900, 100);
        feeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel2.setFont(font);

        choiceLabel.setLocation(45, 430);
        choiceLabel.setSize(900, 100);
        choiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choiceLabel.setFont(new Font("���� ���", Font.PLAIN, 40));
        
        cashButton.setLocation(130, 620);
        cashButton.setSize(300, 80);
        cashButton.setFont(font.deriveFont(40f));
        
        cardButton.setLocation(570, 620);
        cardButton.setSize(280, 80);
        cardButton.setFont(font.deriveFont(40f));

        p.add(feeLabel);
        p.add(feeLabel2);
        p.add(choiceLabel);
        p.add(cashButton);
        p.add(cardButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
    	cashButton.addActionListener(new ActionListener() { //�������� ���� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	//���� ���� ��ȣ�� ���� �����ؾ� �� ���� �Բ� �������� ���� ȭ������ �̵�
            	new GUIPayCash(carNum, pay);
        	}		             
        });
        
    	cardButton.addActionListener(new ActionListener() { //ī��� ���� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	//���� ���� ��ȣ�� ���� �����ؾ� �� ���� �Բ� ī��� ���� ȭ������ �̵�
            	new GUIPayCard(carNum, pay);
        	}			             
        });
    }
}