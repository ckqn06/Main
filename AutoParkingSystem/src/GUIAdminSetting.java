import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIAdminSetting extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

	//ȭ�鿡 �����ִ� �޽��� ����
    private JLabel placeLabel = new JLabel("�� ���� ���� ����");
    private JLabel detailLabel = new JLabel("<html>(�ּ� ���� 1ĭ/���� 1ĭ<br/>�ִ� ���� 10ĭ/���� 15ĭ ���� ����)</html>");
    private JLabel widthLabel = new JLabel("���� :");
    private JLabel heightLabel = new JLabel("���� :");
    private JLabel payLabel = new JLabel("�� �ð��� ���� ��� ����");

    private JTextField widthText = new JTextField(); //���� �Է� â
    private JTextField heightText = new JTextField(); //���� �Է� â
    private JTextField payText = new JTextField(); //�ð��� ���� ��� �Է� â
    
    private JButton SettingButton = new JButton("���� ����"); //���� ��ư
    private JButton CancleButton = new JButton("���"); //��� ��ư
    
    private String ID, PW, handicap, noPark; //������ ID, ��й�ȣ, �����/���� �Ұ� ���� ��ȣ�� �����ϴ� ����
    
    File f = new File("������ ������ ����.txt"); //������ ������ ����

    GUIAdminSetting(){ //ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() { //�� GUI ��ü ����
    	try {
    		if(f.exists()) { //������ ������ ���Ͽ��� �ؽ�Ʈ�� �о����
    			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
            	List<String> list = new ArrayList<String>(); //�о���� ������ ������ ������ ������ �����ϱ� ���� ����Ʈ ����
            	String line = null; //������ ������ ������ �о���̱� ���� ����
            	
            	while((line = br.readLine()) != null) { //������ ������ ������ null�� �ƴ� ������ �о����
            		list.add(line); //�о���� ������ ����Ʈ�� ����
            	}
            	
            	int ListSize = list.size(); //����Ʈ�� ����� ��ü�� ���� ����
            	String arr[] = list.toArray(new String[ListSize]); //����Ʈ�� ����� ��ü�� �Բ� �迭�� ��ȯ��

            	//�迭�� n��°�� ����� ������ ID, ��й�ȣ, �����/���� �Ұ� ���� ��ȣ�� ������ �����ϱ� ���� ����
            	String IDStr = arr[3];
            	String PWStr = arr[4];
            	String handicapStr = arr[5];
            	String noParkStr = arr[6];
            	
            	//�迭�� ����� �ؽ�Ʈ�� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
            	ID = IDStr.split(":")[1];
            	PW = PWStr.split(":")[1];
            	handicap = handicapStr.split(":")[1];
            	noPark = noParkStr.split(":")[1];

    			br.close(); //���۸� ����
    		}
        } catch(Exception e) { //���� ó��
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p);
        p.setLayout(null);
        
        placeLabel.setLocation(100, 20);
        placeLabel.setSize(600, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        detailLabel.setLocation(100, 50);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("���", Font.PLAIN, 30));
        
        widthLabel.setLocation(120, 205);
        widthLabel.setSize(500, 100);
        widthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        widthLabel.setFont(font);
        
        heightLabel.setLocation(120, 315);
        heightLabel.setSize(500, 100);
        heightLabel.setHorizontalAlignment(SwingConstants.LEFT);
        heightLabel.setFont(font);
        
        payLabel.setLocation(100, 430);
        payLabel.setSize(800, 100);
        payLabel.setHorizontalAlignment(SwingConstants.LEFT);
        payLabel.setFont(font);

        widthText.setLocation(270, 220);
        widthText.setSize(400, 80);
        widthText.setFont(font);

        heightText.setLocation(270, 330);
        heightText.setSize(400, 80);
        heightText.setFont(font);
        
        payText.setLocation(110, 530);
        payText.setSize(400, 80);
        payText.setFont(font);
        
        SettingButton.setLocation(360, 650);
        SettingButton.setSize(280, 80);
        SettingButton.setFont(font);
        
        if(!f.exists()) { //���� ������ ������ ������ �������� �ʴ´ٸ� (= ������ ������ ���� �����ϴ� ���)
        	SettingButton.setText("����"); //���� ��ư�� �ؽ�Ʈ�� "���� ����"���� "����"���� ����
        }
        
        if(f.exists()) {
        	SettingButton.setLocation(540, 650);
        	SettingButton.setSize(230, 80);
        	
        	CancleButton.setLocation(230, 650);
            CancleButton.setSize(160, 80);
            CancleButton.setFont(font);
        	p.add(CancleButton);
        }
        
        p.add(placeLabel);
        p.add(detailLabel);
        p.add(widthLabel);
        p.add(heightLabel);
        p.add(payLabel);
        p.add(widthText);
        p.add(heightText);
        p.add(payText);
        p.add(SettingButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����    	
    	SettingButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
    				int width = Integer.parseInt(widthText.getText()); //���� �Է� â���� �Է¹��� ���� ������ �޾ƿ�
        			int height = Integer.parseInt(heightText.getText()); //���� �Է� â���� �Է¹��� ���� ������ �޾ƿ�
        			int pay = Integer.parseInt(payText.getText()); //�ð��� ���� ��� �Է� â���� �Է¹��� ���� ������ �޾ƿ�
        			ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
        			String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������

        			if(1<=width && width<=10 && 1<=height && height <= 15) { //����, ���� ���� ���� ���� ���̸�
        				if(f.exists()) { //������ ������ ������ �����Ѵٸ�
        					OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
        					
            				//���Ͽ� ����/���� ��, �ð��� ���� ��� ���� �������
        					String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID
    						+ "\nPW:"+PW + "\n����� ���� ���� ����:"+handicap + "\n���� �Ұ� ����:"+noPark);
                			byte[] by = str.getBytes();
                			os.write(by);
                				
                			JOptionPane.showMessageDialog(null, "�����Ͻ� ���� ���� ���������� ����ƽ��ϴ�");
                			dispose(); 
                			new GUIMain(); //���� ȭ������ �̵�
        				} else { //������ ������ ������ �������� �ʴ´ٸ�
        					OutputStream os = new FileOutputStream(f);
        					
            				String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay 
            						+ "\nID:admin" + "\nPW:park123" + "\n����� ���� ���� ����:0" + "\n���� �Ұ� ����:0");
                			byte[] by = str.getBytes();
                			os.write(by);
                				
                			JOptionPane.showMessageDialog(null, "�Է��Ͻ� ���� ���� ���������� ����ƽ��ϴ�");
                			dispose(); 
                			new GUIMain();
        				}
            		} else { //����, ���� ���� ������ ����� �� ����
            			JOptionPane.showMessageDialog(null, "���� �� ���� ���� �Է����ּ���");
            		}
    			} catch(Exception e1) { //����/����, �ð��� ���� ��� �Է� â�� �������� �Էµ��� �ʾҴٸ�
    				JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
    			}
    		}
    	});
    	
    	CancleButton.addActionListener(new ActionListener() { //����ȭ������ ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			dispose();
    			new GUIAdmin(); //������ ȭ������ �̵�
    		}
    	});
    }
    public static void main(String[] args) {
		new GUIAdminSetting();
	}
}