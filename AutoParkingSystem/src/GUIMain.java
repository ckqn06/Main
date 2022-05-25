import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel();
    public JPanel p2 = new JPanel();
    
    private Font font = new Font("���� ���", Font.BOLD, 25); //��Ʈ ����
    
    private JLabel emptyLabel = new JLabel("= �� ����");
    private JLabel whiteLabel = new JLabel("���");
    private JLabel existLabel = new JLabel("= �� ����");
    private JLabel redLabel = new JLabel("������");
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
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺� ���
    private String[][] rows = {};
    private TableModel tableModel = new DefaultTableModel(rows, header);
    public JTable placeView; //���� ���� ���̺�
    private JTable clientTable = new JTable(tableModel); //�� ���� ���̺�
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���

    public JScrollPane placePane;
    private JScrollPane clientPane;
    
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    private UpdateClientTable uct; //�� ���̺� ���� ������
    
    private int width = 0, height = 0, pay = 0;

    GUIMain(){
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        uct = new UpdateClientTable(this, clientTable); 
        uct.start();
    }

    public void formDesign() {
    	try {
        	BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));

        	String widthStr = br.readLine();  //������ ���Ͽ��� ���ڿ� ����
        	String heightStr = br.readLine();
        	String payStr = br.readLine();
        	
        	width = Integer.parseInt(widthStr.split(":")[1]); //�� ����
        	height = Integer.parseInt(heightStr.split(":")[1]);
        	pay = Integer.parseInt(payStr.split(":")[1]);
        
        	br.close();
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p, BorderLayout.EAST);
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238));
        p.setPreferredSize(new Dimension(500, 800));

        this.add(p2, BorderLayout.WEST);
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190));
        p2.setPreferredSize(new Dimension(500, 800));
        
        emptyLabel.setLocation(330, 20);
        emptyLabel.setSize(400, 100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(new Font("���� ���", Font.BOLD, 30));

        whiteLabel.setLocation(260, 20);
        whiteLabel.setSize(400, 100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(new Font("���� ���", Font.BOLD, 30));
        whiteLabel.setForeground(Color.white);

        existLabel.setLocation(330, 60);
        existLabel.setSize(400, 100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(new Font("���� ���", Font.BOLD, 30));

        redLabel.setLocation(230, 60);
        redLabel.setSize(400, 100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(new Font("���� ���", Font.BOLD, 30));
        redLabel.setForeground(Color.red);
        
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
        
        searchButton.setLocation(350, 375);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 690);
        adminButton.setSize(170, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(300, 690);
        quitButton.setSize(170, 50);
        quitButton.setFont(font);
        
        String[][] clientTableValue = dbc.getTable(); //�����ͺ��̽����� �� ���̺� �� ��������
        placeView = new JTable(height, width) {
        	@Override
        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) { // �������� ���� ���� �ٲٱ�
        		JComponent cell = (JComponent) super.prepareRenderer(renderer, row, column);
        		int k = 0;
        		while(clientTableValue[k][0] != null) {
        			if(clientTableValue[k][2].equals(placeView.getValueAt(row, column).toString())) {
        				cell.setBackground(Color.RED);
        				return cell;
        			}
        			k++;
        		}
        		cell.setBackground(Color.white);
        		return cell;
        	}
        };
        placeView.setRowHeight(87);
        placeView.setTableHeader(null);
        placeView.setEnabled(false); 
        placeView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        placeView.setFont(font);
        placeView.setBackground(Color.white);
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		placeView.getColumnModel().getColumn(j).setPreferredWidth(87);  //�ʺ� ����
            	placeView.getColumnModel().getColumn(j).setCellRenderer(dtcr); //�� ��� ����
        		placeView.setValueAt("" + (char)(65+i) + (j+1), i, j); //�� ���� �� �ֱ�
        	}
        }		        
        
        placePane = new JScrollPane(placeView);
        placePane.setLocation(17, 130);
        placePane.setSize(450, 539);
        placePane.getViewport().setBackground(new Color(113, 135, 190));
        placePane.setBorder(BorderFactory.createEmptyBorder());
        
        clientTable.setRowHeight(40);
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        int i = 0;
        while(clientTableValue[i][0] != null) {
        	int diffTime = diffTime(clientTableValue[i][1]);
        	String parkTime = "" + (diffTime / 60) + "�ð� " + (diffTime % 60) + "��";
        	clientModel.addRow(new Object[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], ((diffTime/15 + 1) * (pay/4))}); //���̺� �� �߰�
        	i++;
        }
        
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(25, 160);
        clientPane.setSize(450, 200);

        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(placePane);
        
        p.add(clientLabel);
        p.add(clientPane);
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
    }

    private void eventListner() {
        parkButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable();
        		int row = 0;
        		while(clientTableValue[row][0] != null) //�� ���̺� �� �� Ȯ��
        			row++;
        		if(row == width * height) { //�������� ���� �� �ִٸ�
        			JOptionPane.showMessageDialog(null, "���� �������� ���� ���� ������ �Ұ����մϴ�");
        		}else {
        			uct.interrupt();
        			dispose();
            		new GUIParking(); //�����ϱ� ȭ������
        		}
        	}
        });
        
        payButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIPayCarChoice(pay); //������ ���� ��ȣ �Է� ȭ������ 
        	}
        });
        
        searchButton.addActionListener(new ActionListener() { //�˻� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable();
        		int i = 0;
        		while(clientTableValue[i][0] != null) {
        			if(clientTableValue[i][0].equals(carNumText.getText()) || clientTableValue[i][2].equals(placeNumText.getText())) { //�� ���̺��� ������ �Է�â�� ���� ��ȣ�� ���Ͽ� ������ ������� �޽����� ��ȯ
        				int diffTime = diffTime(clientTableValue[i][1]);
        	        	String parkTime = "" + (diffTime / 60) + "�ð� " + (diffTime % 60) + "��";
        	        	uct.interrupt();
        				dispose();
                		new GUISearch(new String[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], "" + ((diffTime/15 + 1) * (pay/4))}); //�˻��� ���� ���� ȭ������
                		return;
        			}
        			i++;
        		}
        		JOptionPane.showMessageDialog(null, "�ش� ��ȣ�� ��ġ�� �ش��ϴ� ������ �����ϴ�");
        	}
        });
        
        adminButton.addActionListener(new ActionListener() { //������ ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(2); //������ �α��� ȭ������
        	}
        });
        
        quitButton.addActionListener(new ActionListener() { //�ý��� ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(3); //�ý��� ���� ȭ������
        	}
        });
    }
    
    public static int diffTime(String parkTime) { // �ð� ���� ���
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //�ð� ���� ����
    	try {
    		Date parkDate = format.parse(parkTime); //�����ð��� Date Ÿ������ ��ȯ
    		cal.setTime(parkDate); //Ķ���� �ð� ����
    		long diffTime = (System.currentTimeMillis() - cal.getTimeInMillis()) / 1000 / 60; //�ð� ���̸� �� ������ ���
    		return (int)diffTime;
    	}catch(Exception e) { 
    		System.out.println(e.getMessage());
    		return -1;
    	}
    }

    public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
        new GUIMain();
    }
}