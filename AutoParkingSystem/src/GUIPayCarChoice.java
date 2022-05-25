import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCarChoice extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

    private JLabel carNumLabel = new JLabel("�� ������ ������ȣ�� �Է��Ͻʽÿ�.");

    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    
    private JButton cancleButton = new JButton("���"); //��� ��ư
    private JButton checkButton = new JButton("�Է�"); //�Է� ��ư
    
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    
    private int pay;

    GUIPayCarChoice(int pay){ //ȭ�� �⺻ ����
    	this.pay = pay;
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

        carNumLabel.setLocation(20, 250);
        carNumLabel.setSize(920, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);

        carNumText.setLocation(160, 360);
        carNumText.setSize(660, 80);
        carNumText.setFont(font);

        cancleButton.setLocation(190, 620);
        cancleButton.setSize(200, 80);
        cancleButton.setFont(font);
        
        checkButton.setLocation(600, 620);
        checkButton.setSize(200, 80);
        checkButton.setFont(font);

        p.add(carNumLabel);
        p.add(carNumText);
        p.add(cancleButton);
        p.add(checkButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { //��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();  	
            	new GUIMain(); //����ȭ������
        	}		        	             
        });
        
        checkButton.addActionListener(new ActionListener() { //�Է� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	String[][] clientTableValue = dbc.getTable();
            	int i = 0;
            	while(clientTableValue[i][0] != null) {
            		if(clientTableValue[i][0].equals(carNumText.getText())) { //������ ������ ���
            			int diffTime = GUIMain.diffTime(clientTableValue[i][1]);
            			dispose();
                    	new GUIPayMethodChoice(clientTableValue[i][0], (diffTime/15 + 1) * (pay/4)); //���� ���� ���� ȭ������
                    	return;
            		}
            	}
            	JOptionPane.showMessageDialog(null, "�ش� ������ȣ�� �������� �̿����� ���� �����Դϴ�"); //�ش��ϴ� ������ȣ�� ���� ���
        	}		       	             
        });
    }
	
	public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
		new GUIPayCarChoice(10000);
	}
}