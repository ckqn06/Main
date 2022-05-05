import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel();
    private JPanel p2 = new JPanel();
    
    private JButton parkButton = new JButton("�����ϱ�");  // �����ϱ� ��ư
    private JButton payButton = new JButton("�����ϱ�");  // �����ϱ� ��ư
    private JButton searchButton = new JButton("�˻�");  // �˻� ��ư
    private JButton adminButton = new JButton("������ ����");  // ������ ���� ��ư
    private JButton quitButton = new JButton("�ý��� ����");  // �ý��� ���� ��ư
    
    private Font font = new Font("���", Font.BOLD, 30);
    
    private JTextField carNumText = new JTextField(); // ���� ��ȣ �Է� â
    private JTextField placeNumText = new JTextField();   // ��ġ ��ȣ �Է� â

    private JLabel emptyLabel = new JLabel("= �� ����");
    private JLabel whiteLabel = new JLabel("���");
    private JLabel existLabel = new JLabel("= �� ����");
    private JLabel redLabel = new JLabel("������");
    private JLabel clientLabel = new JLabel("- ���� �̿� ���� ��");
    private JLabel searchLabel = new JLabel("- ���� ������ �˻�");
    private JLabel carNumLabel = new JLabel("���� ��ȣ�� �˻�:");
    private JLabel placeNumLabel = new JLabel("��ġ ��ȣ�� �˻�:");

    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"};  // �� ���̺� ���
    private String[][] rows = {};
    private DefaultTableModel dtm = new DefaultTableModel(8, 5);
    private JTable placeView = new JTable(dtm);  // ���� ��ġ ��ȯ ���̺�
    private JTable clientTable = new JTable(rows, header);  // �� ���� ���̺�

    private JScrollPane placePane = new JScrollPane(placeView);
    private JScrollPane clientPane = new JScrollPane(clientTable);

    GUIMain(){
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {
        this.add(p, BorderLayout.EAST);
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238));
        p.setPreferredSize(new Dimension(500, 1000));

        this.add(p2, BorderLayout.WEST);
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190));
        p2.setPreferredSize(new Dimension(500, 1000));
        
        parkButton.setLocation(30, 40);
        parkButton.setSize(170, 50);
        parkButton.setFont(font);
        
        payButton.setLocation(300, 40);
        payButton.setSize(170, 50);
        payButton.setFont(font);
        
        searchButton.setLocation(350, 505);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 870);
        adminButton.setSize(220, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(260, 870);
        quitButton.setSize(220, 50);
        quitButton.setFont(font);

        emptyLabel.setLocation(330,10);
        emptyLabel.setSize(400,100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(font);

        whiteLabel.setLocation(260,10);
        whiteLabel.setSize(400,100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(font);
        whiteLabel.setForeground(Color.white);

        existLabel.setLocation(330,50);
        existLabel.setSize(400,100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(font);

        redLabel.setLocation(230,50);
        redLabel.setSize(400,100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(font);
        redLabel.setForeground(Color.red);
        
        clientLabel.setLocation(30,100);
        clientLabel.setSize(400,100);
        clientLabel.setFont(font.deriveFont(35f));
        
        searchLabel.setLocation(30,480);
        searchLabel.setSize(400,100);
        searchLabel.setFont(font.deriveFont(35f));
        
        carNumLabel.setLocation(30,530);
        carNumLabel.setSize(400,100);
        carNumLabel.setFont(font.deriveFont(35f));
        
        placeNumLabel.setLocation(30,670);
        placeNumLabel.setSize(400,100);
        placeNumLabel.setFont(font.deriveFont(35f));
        
        carNumText.setLocation(30, 610);
        carNumText.setSize(400, 70);
        carNumText.setFont(font);
        
        placeNumText.setLocation(30, 750);
        placeNumText.setSize(400, 70);
        placeNumText.setFont(font);

        placeView.setRowHeight(40);

        placePane.setLocation(17, 130);
        placePane.setSize(450, 700);
        placePane.getViewport().setBackground(new Color(113, 135, 190));
        placePane.setBorder(BorderFactory.createEmptyBorder());
        
        clientTable.setRowHeight(40);
        
        clientPane.setLocation(25, 180);
        clientPane.setSize(450, 250);
        
        
        
        p.add(parkButton);
        p.add(payButton);
        p.add(searchButton);
        p.add(adminButton);
        p.add(quitButton);
        p.add(clientPane);
        p.add(clientLabel);
        p.add(searchLabel);
        p.add(carNumLabel);
        p.add(placeNumLabel);
        p.add(carNumText);
        p.add(placeNumText);

        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(placePane);
    }

    private void eventListner() {
        parkButton.addActionListener(new ActionListener() {  // �����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIParking();  // �����ϱ� ȭ������
        	}
        });
        
        payButton.addActionListener(new ActionListener() {  // �����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIPayCarChoice();  // ������ ���� ��ȣ �Է� ȭ������ 
        	}
        });
        
        searchButton.addActionListener(new ActionListener() {  // �˻� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		// �� ���̺��� ������ �Է�â�� ���� ��ȣ�� ���Ͽ� ������ ������� �޽����� ��ȯ
        	}
        });
        
        adminButton.addActionListener(new ActionListener() {  // ������ ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIAdminLogin();  // ������ �α��� ȭ������
        	}
        });
        
        quitButton.addActionListener(new ActionListener() {  // �ý��� ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		int result = JOptionPane.showConfirmDialog(null, "������ �ý����� �����Ͻðڽ��ϱ�?", "�ý��� ����", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {  // Yes�� ������ ���
        			System.exit(0);
        		}
        	}
        });
    }


    public static void main(String[] args) { // ���� �׽�Ʈ�� ���� �ڵ�
        new GUIMain();
    }
}