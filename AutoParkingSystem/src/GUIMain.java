import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel(); //�� ���̺��� ���� ���� ������ ����
    public JPanel p2 = new JPanel(); //���� ���� ���̺��� ���� ���� ������ ����
    
    private Font font = new Font("���� ���", Font.BOLD, 25); //��Ʈ ����
    
    //ȭ�鿡 �����ִ� �޽��� ����
    private JLabel emptyLabel = new JLabel(": �� ����");
    private JLabel whiteLabel = new JLabel("���");
    private JLabel existLabel = new JLabel(": �� ����");
    private JLabel redLabel = new JLabel("������");
    private JLabel handicapLabel = new JLabel(": ����� ����");
    private JLabel greenLabel = new JLabel("�ʷϻ�");
    private JLabel noParkLabel = new JLabel(": ���� �Ұ�");
    private JLabel blackLabel = new JLabel("������");
    private JLabel clientLabel = new JLabel("-���� �̿� ���� ��");
    private JLabel searchLabel = new JLabel("-���� ������ �˻�");
    private JLabel carNumLabel = new JLabel("���� ��ȣ�� �˻�:");
    private JLabel placeNumLabel = new JLabel("��ġ ��ȣ�� �˻�:");
    
    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    private JTextField placeNumText = new JTextField(); //��ġ ��ȣ �Է� â
    
    private JButton parkButton = new JButton("�����ϱ�"); //�����ϱ� ��ư
    private JButton payButton = new JButton("�����ϱ�"); //�����ϱ� ��ư
    private JButton searchButton = new JButton("�˻�"); //�˻� ��ư
    private JButton adminButton = new JButton("������ ����"); //������ ���� ��ư
    private JButton quitButton = new JButton("�ý��� ����"); //�ý��� ���� ��ư
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺��� ��� ����
    private String[][] rows = {}; //�� ���̺��� ��(���� ��) ����
    private TableModel tableModel = new DefaultTableModel(rows, header); //������ ����� ���� �ϳ��� ���̺�� ������
    private JTable clientTable = new JTable(tableModel); //�ϳ��� ������ ���̺�� �� ���̺��� ����
    
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���

    private JScrollPane clientPane; //�� ���̺��� ��ũ�ѹ� ����
    
    private ServerConnection sct = new ServerConnection(); //���� ���� ��ü
    private UpdateClientTable uct; //�� ���̺��� ������ ������ ���� ������
    
    private int width, height, tpay = 0; //����, ����, �ð��� ���� ��� ���� 0���� �ʱ�ȭ

    GUIMain(){ //ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
        uct = new UpdateClientTable(this, clientTable); //�����忡�� �� ���̺��� �����͸� ���Ž�Ŵ
        uct.start(); //������ ����
    }
    
    public static int diffTime(String parkTime) { //���� �ð��� ���ϱ� ���� �ð� ���� ���
    	Calendar cal = Calendar.getInstance(); //Calendar ��ü�� �̿��Ͽ� ���� ��¥, ����, �ð� ������ ������
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"��-��-�� ��:��:��"�� �ð� ���� ����
    	try {
    		Date parkDate = format.parse(parkTime); //�޾ƿ� ���� �ð��� Date Ÿ������ ��ȯ
    		cal.setTime(parkDate); //������ �ð� ������ ������ ������ ����
    		//���� �ð����� ���� ������ ������ �ð��� �� ���� ���� �ð�(ms����)�� ���� ��, �̸� �ٽ� �� ������ ��� 
    		long diffTime = (System.currentTimeMillis() - cal.getTimeInMillis()) / 1000 / 60;
    		return (int)diffTime; //����� ���� �ð��� ��ȯ
    	}catch(Exception e) { 
    		System.out.println(e.getMessage());
    		return -1;
    	}
    }

    public void formDesign() { //�� GUI ��ü ����
    	try { //������ ������ ���Ͽ��� ����, ����, �ð��� ���� ��� ���� ���� �ؽ�Ʈ�� �о����
        	String[] settingData = sct.getSetting();
        	
        	width = Integer.parseInt(settingData[0]);
        	height = Integer.parseInt(settingData[1]);
        	tpay = Integer.parseInt(settingData[2]);
        } catch(Exception e) { //���� ó��
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p, BorderLayout.EAST); //�� ���̺��� ���� ���� �������� ���ʿ� ��ġ
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238)); //������ ȸ������ ����
        p.setPreferredSize(new Dimension(500, 800)); //���� �������� ���� �ʺ� ����

        this.add(p2, BorderLayout.WEST); //���� ���� ���̺��� ���� ���� �������� ���ʿ� ��ġ
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190)); //������ �Ķ������� ����
        p2.setPreferredSize(new Dimension(500, 800));
        
        emptyLabel.setLocation(100, 20);
        emptyLabel.setSize(400, 100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(new Font("���� ���", Font.BOLD, 27));

        whiteLabel.setLocation(40, 20);
        whiteLabel.setSize(400, 100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(new Font("���� ���", Font.BOLD, 27));
        whiteLabel.setForeground(Color.white); //�ؽ�Ʈ�� ������ ����

        existLabel.setLocation(100, 60);
        existLabel.setSize(400, 100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(new Font("���� ���", Font.BOLD, 27));

        redLabel.setLocation(15, 60);
        redLabel.setSize(400, 100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(new Font("���� ���", Font.BOLD, 27));
        redLabel.setForeground(Color.red);
        
        handicapLabel.setLocation(305, 20);
        handicapLabel.setSize(400, 100);
        handicapLabel.setVerticalAlignment(SwingConstants.TOP);
        handicapLabel.setFont(new Font("���� ���", Font.BOLD, 27));

        greenLabel.setLocation(220, 20);
        greenLabel.setSize(400, 100);
        greenLabel.setVerticalAlignment(SwingConstants.TOP);
        greenLabel.setFont(new Font("���� ���", Font.BOLD, 27));
        greenLabel.setForeground(Color.green);
        
        noParkLabel.setLocation(330, 60);
        noParkLabel.setSize(400, 100);
        noParkLabel.setVerticalAlignment(SwingConstants.TOP);
        noParkLabel.setFont(new Font("���� ���", Font.BOLD, 27));

        blackLabel.setLocation(245, 60);
        blackLabel.setSize(400, 100);
        blackLabel.setVerticalAlignment(SwingConstants.TOP);
        blackLabel.setFont(new Font("���� ���", Font.BOLD, 27));
        blackLabel.setForeground(Color.black);
        
        clientLabel.setLocation(30, 80);
        clientLabel.setSize(400, 100);
        clientLabel.setFont(font.deriveFont(25f));
        
        searchLabel.setLocation(30, 350);
        searchLabel.setSize(400, 100);
        searchLabel.setFont(font.deriveFont(25f));
        
        carNumLabel.setLocation(30, 410);
        carNumLabel.setSize(400, 100);
        carNumLabel.setFont(font.deriveFont(25f));
        
        placeNumLabel.setLocation(30, 520);
        placeNumLabel.setSize(400, 100);
        placeNumLabel.setFont(font.deriveFont(25f));
        
        carNumText.setLocation(30, 490);
        carNumText.setSize(400, 50);
        carNumText.setFont(font);
        
        placeNumText.setLocation(30, 600);
        placeNumText.setSize(400, 50);
        placeNumText.setFont(font);
        
        parkButton.setLocation(30, 40);
        parkButton.setSize(170, 50);
        parkButton.setFont(font);
        
        payButton.setLocation(300, 40);
        payButton.setSize(170, 50);
        payButton.setFont(font);
        
        searchButton.setLocation(310, 375);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 690);
        adminButton.setSize(170, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(300, 690);
        quitButton.setSize(170, 50);
        quitButton.setFont(font);
        
        String[][] clientTableValue = sct.getTableData(); //DB���Ͽ� ����� �� ���̺��� ���� �ҷ���
        
        clientTable.setRowHeight(30);
        //�� ���̺� ���� �����͸� �ٷ�� ���ؼ� DefaultTableModel�� �ҷ���
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        
        for(int line = 0; line < clientTableValue.length; line++) {
        	int diffTime = diffTime(clientTableValue[line][1]); //�� ���̺��� 2��° ���� ���� �ð��� �����Ŵ
        	int pay = ((diffTime/15 + 1) * (tpay/4))/10; //���� �ð��� ���� ���� ��� ���
        	String parkTime = "" + (diffTime / 60)+"�ð� " + (diffTime % 60)+"��"; //����� ���� �ð�(�� ����)�� �ð�, ������ ǥ��
        	
        	//�� ���̺��� ���� ���� ��ȣ, ���� �ð�, ��ġ ��ȣ, ���� ����� �߰�
        	clientModel.addRow(new Object[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], pay*10+" ��"});
        }
        
        TableColumnModel tcm = clientTable.getColumnModel(); //������ ���̺��� ColumnModel�� ������
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //���̺��� ���� ��� ������
        for (int i = 0; i < tcm.getColumnCount(); i++) { //���̺��� ���� ��� ������
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 
        
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(25, 160);
        clientPane.setSize(450, 205);

        p.add(clientLabel);
        p.add(searchLabel);
        p.add(carNumLabel);
        p.add(placeNumLabel);
        p.add(carNumText);
        p.add(placeNumText);      
        p.add(parkButton);
        p.add(payButton);
        p.add(searchButton);
        p.add(adminButton);
        p.add(quitButton);
        p.add(clientPane);
        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(handicapLabel);
        p2.add(greenLabel);
        p2.add(noParkLabel);
        p2.add(blackLabel);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        parkButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData(); //DB���� ���� �� ���̺��� ������
        		
        		//���̺��� �� ���� ���� ���� ���̺��� ����*���� ���� �����ϴٸ� ���� �������� ������
        		if(clientTableValue.length == width * height) {
        			JOptionPane.showMessageDialog(null, "���� �������� ���� ���� ������ �Ұ����մϴ�");
        		}else { //line�� ���� ���� ������ ����*���� ������ �۴ٸ� ���� �������� ���� ������ ����
        			uct.interrupt(); //������(UpdateClientTable)�� ���ͷ�Ʈ�� ����(������ ������ ������Ŵ)
        			dispose();
            		new GUIParking(); //�����ϱ� ȭ������ �̵�
        		}
        	}
        });
        
        payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIPayCarChoice(tpay); //�����ϱ� ȭ������ �̵�
        	}
        });
        
        searchButton.addActionListener(new ActionListener() { //�˻� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData();
        		boolean isExist = false; //��ġ�ϴ� ���� �ִ��� Ȯ���ϴ� ����
        		
        		for(int line = 0; line < clientTableValue.length; line++) {
        			if(clientTableValue[line][0].equals(carNumText.getText()) && clientTableValue[line][2].equals(placeNumText.getText()))
        				isExist = true; //�� ���̺��� ���� �о� ���� ����/��ġ ��ȣ �Է� â�� �Է��� ���� ������ ���� �� ���̺� �����Ѵٸ�
        			else if(clientTableValue[line][0].equals(carNumText.getText()) && placeNumText.getText().equals(""))
        				isExist = true; //���� ��ȣ �Է� â�� �Է��� ���� ������ ���� �� ���̺� ���������� ��ġ ��ȣ�� ���ٸ�
        			else if(clientTableValue[line][2].equals(placeNumText.getText()) && carNumText.getText().equals(""))
        				isExist = true; //��ġ ��ȣ �Է� â�� �Է��� ���� ������ ���� �� ���̺� ���������� ���� ��ȣ�� ���ٸ�
        			
        			if (isExist) { //���� ����/��ġ ��ȣ �Է� â�� �Է��� ���� ������ ���� �� ���̺� �����Ѵٸ�
        				int diffTime = diffTime(clientTableValue[line][1]); //�ش� ���� ���� ������ ���� �ð��� ����
        				int pay = ((diffTime/15 + 1) * (tpay/4))/10;
        	        	String parkTime = "" + (diffTime / 60)+"�ð� " + (diffTime % 60)+"��";
        	        	
        	        	uct.interrupt();
        				dispose();
        				//�˻��� ������ ������ȣ, ���� �ð�, ���� ��ġ, ���� ��� ���� �Բ� �˻� ��� ȭ������ �̵�
                		new GUISearch(new String[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], ""+pay*10+" ��"});
                		return;
        			}
        		}
        		JOptionPane.showMessageDialog(null, "�ش� ���� Ȥ�� ��ġ ��ȣ�� �ش��ϴ� ������ �����ϴ�"); //�� ���̺� ������ ���� �������� �ʴ´ٸ�
        	}
        });
        
        adminButton.addActionListener(new ActionListener() { //������ ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(2); //������ �α��� ȭ������ �̵�
        	}
        });
        
        quitButton.addActionListener(new ActionListener() { //�ý��� ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(3); //�ý��� ���� ȭ������ �̵�
        	}
        });
    }

    public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
        new GUIMain();
    }
}