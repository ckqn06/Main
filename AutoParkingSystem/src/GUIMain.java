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
    
    private String[] header = {"������ȣ", "���� �ð�", "���� ��ġ", "���"}; //�� ���̺��� ��� ����
    private String[][] rows = {}; //�� ���̺��� ��(���� ��) ����
    private TableModel tableModel = new DefaultTableModel(rows, header); //������ ����� ���� �ϳ��� ���̺�� ������
    private JTable clientTable = new JTable(tableModel); //�ϳ��� ������ ���̺�� �� ���̺��� ����
    
    public JTable placeView; //���� ���� ���̺� ����
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���

    public JScrollPane placePane; //���� ���� ���̺��� ��ũ�ѹ� ����
    private JScrollPane clientPane; //�� ���̺��� ��ũ�ѹ� ����
    
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    private UpdateClientTable uct; //�� ���̺��� ������ ������ ���� ������
    
    private int width = 0, height = 0, pay = 0; //����, ����, �ð��� ���� ��� ���� 0���� �ʱ�ȭ

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
        	BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));

        	String widthStr = br.readLine();
        	String heightStr = br.readLine();
        	String payStr = br.readLine();
        	
        	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
        	width = Integer.parseInt(widthStr.split(":")[1]);
        	height = Integer.parseInt(heightStr.split(":")[1]);
        	pay = Integer.parseInt(payStr.split(":")[1]);
        
        	br.close(); //���۸� ����
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
        
        emptyLabel.setLocation(330, 20);
        emptyLabel.setSize(400, 100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(new Font("���� ���", Font.BOLD, 30));

        whiteLabel.setLocation(260, 20);
        whiteLabel.setSize(400, 100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(new Font("���� ���", Font.BOLD, 30));
        whiteLabel.setForeground(Color.white); //�ؽ�Ʈ�� ������ ����

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
        
        searchButton.setLocation(310, 375);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 690);
        adminButton.setSize(170, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(300, 690);
        quitButton.setSize(170, 50);
        quitButton.setFont(font);
        
        String[][] clientTableValue = dbc.getTable(); //DB���Ͽ� ����� �� ���̺��� ���� �ҷ���
        
        placeView = new JTable(height, width) { //���� ���� ���̺��� ��� ���� ������ ������ ���Ͽ� ���� ����/���� ����ŭ ����
        	@Override
        	//���� ���� ������ ���� javax.swing.JTable prepareRenderer() �޼ҵ带 �������̵��Ͽ� ���̺��� ���� �������ǵ��� ��
        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        		JComponent cell = (JComponent) super.prepareRenderer(renderer, row, column);
        		int line = 0; //�� ���̺��� �� ���� Ȯ���ϱ� ���� ���� ����
        		
        		while(clientTableValue[line][0] != null) { //���� ��ȣ�� null�� �ƴ� ������ �ݺ�
        			//���� ���� ���̺� �����ϴ� ��ġ ��ȣ �߿� �� ���̺� ����� ��ġ ��ȣ�� ������ ���� �����Ѵٸ�
        			if(clientTableValue[line][2].equals(placeView.getValueAt(row, column).toString())) {
        				cell.setBackground(Color.RED); //�ش� ��(��ġ ��ȣ)�� ����� ���������� �����Ͽ� ������ �������� ǥ��
        				return cell; //�ش� ���� ��ȯ
        			}
        			line++; //������ ���� �������� �ʴ´ٸ� line�� ���� �������� ���� ���� Ž��
        		}
        		cell.setBackground(Color.white); //������ ��ġ ��ȣ�� ���ٸ� �ش� ���� ����� ������� �����Ͽ� �� �������� ǥ�� 
        		return cell;
        	}
        };
        placeView.setRowHeight(87); //���̺��� �� ���� ����
        placeView.setTableHeader(null); //���̺��� ����� null�� ���� (= ����� ����)
        placeView.setEnabled(false); //���� Ŭ�� ����� ��Ȱ��ȭ��
        placeView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //���� ũ�� ����
        placeView.setFont(font);
        placeView.setBackground(Color.white); //���� �⺻ ������ ������� ����
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //���̺��� ���� ��� ������
        for(int i = 0; i < height; i++) { //������ ������ ���Ͽ��� �޾ƿ� ����, ���� �� ��ŭ �ݺ�
        	for(int j = 0; j < width; j++) {
        		placeView.getColumnModel().getColumn(j).setPreferredWidth(87); //�� �ʺ� ����
            	placeView.getColumnModel().getColumn(j).setCellRenderer(dtcr);
        		placeView.setValueAt("" + (char)(65+i) + (j+1), i, j); //���� ���� ���̺��� �� ���� ��ġ ��ȣ�� �ο���
        	}
        }	
        
        placePane = new JScrollPane(placeView);
        placePane.setLocation(17, 130);
        placePane.setSize(450, 539);
        placePane.getViewport().setBackground(new Color(113, 135, 190));
        placePane.setBorder(BorderFactory.createEmptyBorder());
        
        clientTable.setRowHeight(30);
        //�� ���̺� ���� �����͸� �ٷ�� ���ؼ� DefaultTableModel�� �ҷ���
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        int line = 0;
        
        while(clientTableValue[line][0] != null) {
        	int diffTime = diffTime(clientTableValue[line][1]); //�� ���̺��� 2��° ���� ���� �ð��� �����Ŵ
        	String parkTime = "" + (diffTime / 60)+"�ð� " + (diffTime % 60)+"��"; //����� ���� �ð�(�� ����)�� �ð�, ������ ǥ��
        	//�� ���̺��� ���� ���� ��ȣ, ���� �ð�, ��ġ ��ȣ, �ð��� ���� ����� �߰�
        	clientModel.addRow(new Object[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], ((diffTime/15 + 1) * (pay/4)) + " ��"});
        	line++;
        }
        
        TableColumnModel tcm = clientTable.getColumnModel(); //������ ���̺��� ColumnModel�� ������
        
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
        p2.add(placePane);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        parkButton.addActionListener(new ActionListener() { //�����ϱ� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
        		int line = 0;
        		
        		while(clientTableValue[line][0] != null) {
        			line++; //���� �������� �� ������ �࿡ line�� ��ġ��Ŵ
        		}
        		
        		if(line == width * height) { //line�� ���� ���� ���� ���̺��� ����*���� ���� �����ϴٸ� ���� �������� ������
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
        		new GUIPayCarChoice(pay); //�����ϱ� ȭ������ �̵�
        	}
        });
        
        searchButton.addActionListener(new ActionListener() { //�˻� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable();
        		int line = 0;
        		boolean isExist = false; //��ġ�ϴ� ���� �ִ��� Ȯ���ϴ� ����
        		
        		
        		while(clientTableValue[line][0] != null) {
        			//�� ���̺��� ���� �о� ���� ����/��ġ ��ȣ �Է� â�� �Է��� ���� ������ ���� �� ���̺� �����Ѵٸ�
        			if(clientTableValue[line][0].equals(carNumText.getText()) && clientTableValue[line][2].equals(placeNumText.getText())) //�� ��ȣ�� ��ġ�� ��ġ�� ���
        				isExist = true;
        			else if(clientTableValue[line][0].equals(carNumText.getText()) && placeNumText.getText().equals("")) //�� ��ȣ�� ��ġ�ϰ� ��ġ�� �Է����� �ʾ��� ���
        				isExist = true;
        			else if(clientTableValue[line][2].equals(placeNumText.getText()) && carNumText.getText().equals("")) //��ġ�� ��ġ�ϰ� ����ȣ�� �Է����� �ʾ��� ���
        				isExist = true;
        			
        			if (isExist) { //��ġ�ϴ� ���� �ִٸ�
        				int diffTime = diffTime(clientTableValue[line][1]); //�ش� ���� ���� ������ ���� �ð��� ����
        	        	String parkTime = "" + (diffTime / 60)+"�ð� " + (diffTime % 60)+"��";
        	        	
        	        	uct.interrupt();
        				dispose();
        				//�˻��� ������ ������ȣ, ���� �ð�, ���� ��ġ, �ð��� ���� ��� ���� �Բ� �˻� ��� ȭ������ �̵�
                		new GUISearch(new String[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], "" + ((diffTime/15 + 1) * (pay/4)) + " ��"});
                		return;
        			}
        			line++;
        		}
        		JOptionPane.showMessageDialog(null, "�ش� ��ȣ�� ��ġ�� �ش��ϴ� ������ �����ϴ�");
        	}
        });
        
        adminButton.addActionListener(new ActionListener() { //������ ���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
        		
        		if(clientTableValue[0][0] == null) { //�� ���̺� ���� ���� �������� �ʴ´ٸ�
        			uct.interrupt();
            		dispose();
            		new GUIAdminLogin(2); //������ �α��� ȭ������ �̵�
    			} else {
    				JOptionPane.showMessageDialog(null, "���� ���� �����Ͽ� ���� ������ �Ұ����մϴ�");
    			}
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