import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIRestrict extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

	//ȭ�鿡 �����ִ� �޽��� ����
	private JLabel TopLabel = new JLabel("�� �̰��� Ư�� ���� ������ �����ϴ� ȭ���Դϴ�.");
	private JLabel detailLabel = new JLabel("<html>(������ ���� ������ 2�� �̻��� ��� �޸�(,)�� �������ּ���)</html>");
    private JLabel handicapLabel = new JLabel("����� ���� ���� :");
    private JLabel noParkLabel = new JLabel("���� �Ұ� ���� :");

    private JTextField handicapText = new JTextField(); //����� ���� ���� �Է� â
    private JTextField noParkText = new JTextField(); //���� �Ұ� ���� �Է� â
    
    private JButton SettingButton = new JButton("����"); //���� ��ư
    private JButton CancleButton = new JButton("���"); //��� ��ư
    
    private String width, height, pay, ID, PW; //����, ����, ������ ID, ��й�ȣ�� �����ϴ� ����
    
    File f = new File("������ ������ ����.txt"); //������ ������ ����

    GUIRestrict(){ //ȭ�� �⺻ ����
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
            	
            	//�迭�� n��°�� ����� ����, ����, �ð��� ���� ���, �����/���� �Ұ� ���� ��ȣ�� ������ �����ϱ� ���� ����
            	String widthStr = arr[0];
            	String heightStr = arr[1];
            	String payStr = arr[2];
            	String IDStr = arr[3];
            	String PWStr = arr[4];
            	
            	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
            	width = widthStr.split(":")[1];
            	height = heightStr.split(":")[1];
            	pay = payStr.split(":")[1];
            	ID = IDStr.split(":")[1];
            	PW = PWStr.split(":")[1];
            	
            	br.close(); //���۸� ����
    		}
        } catch(Exception e) { //���� ó��
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p);
        p.setLayout(null);

        TopLabel.setLocation(30, 120);
        TopLabel.setSize(900, 100);
        TopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TopLabel.setFont(font);
        
        detailLabel.setLocation(95, 125);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("���", Font.PLAIN, 30));
        
        handicapLabel.setLocation(50, 290);
        handicapLabel.setSize(400, 100);
        handicapLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        handicapLabel.setFont(font);

        noParkLabel.setLocation(50, 460);
        noParkLabel.setSize(400, 100);
        noParkLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        noParkLabel.setFont(font);
        
        handicapText.setSize(350, 100);
        handicapText.setLocation(470, 290);
        handicapText.setFont(font);

        noParkText.setSize(350, 100);
        noParkText.setLocation(470, 460);
        noParkText.setFont(font);
        
        SettingButton.setLocation(550, 630);
        SettingButton.setSize(160, 80);
        SettingButton.setFont(font);
        
        CancleButton.setLocation(250, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel);
        p.add(detailLabel);
        p.add(handicapLabel);
        p.add(noParkLabel);
        p.add(handicapText);
        p.add(noParkText);
        p.add(SettingButton);
        p.add(CancleButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����    	
    	SettingButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
    				ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
                	String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
                	int line = 0; //�� ���̺��� �� ���� Ȯ���ϱ� ���� ���� ����
                	
                	//checkString �޼ҵ忡�� ����/��ġ ��ȣ �Է� â�� �Էµ� ���� �ùٸ��� �ʰ� �Էµ� ���� Ȯ�εƴٸ�
                	if(!checkString(handicapText.getText()) || !checkString(noParkText.getText())) {
                		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
                		return;
            		} 
                	
    				while(clientTableValue[line][0] != null) { //���� ��ȣ�� null�� �ƴ� ������ �ݺ�
                		//��ġ ��ȣ �Է� â�� �Է��� ���� ������ ��ȣ�� �� ���̺� �����Ѵٸ�
                		if(clientTableValue[line][2].equals(handicapText.getText()) || clientTableValue[line][2].equals(noParkText.getText())) {
                            JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� �̹� �����ϰ� �ִ� �����Դϴ�"); 
                            return; //�ش� ��ġ ��ȣ�� ��ȯ
                    	}
                		line++; //������ ���� �������� �ʴ´ٸ� line�� ���� �������� ���� ���� Ž��
                	}
    				
        			if(handicapText.getText().equals("") && noParkText.getText().equals("")) { //ID/PW �Է� â�� �ƹ� ���� �Է����� ���� ���
            			JOptionPane.showMessageDialog(null, "������ ID �� ��й�ȣ�� �ּ� 1�ڸ� �̻��̿��� �մϴ�");
            		} else { //
            			OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
        				
        				//���Ͽ� ������ ������ ID, ��й�ȣ�� �������
        				String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
        						+ "\n����� ���� ���� ����:"+handicapText.getText() + "\n���� �Ұ� ����:"+noParkText.getText());
            			byte[] by = str.getBytes();
            			os.write(by);
            			
            			JOptionPane.showMessageDialog(null, "�����Ͻ� Ư�� ���� ������ ���������� ����ƽ��ϴ�");
            			dispose(); 
            			new GUIMain(); //���� ȭ������ �̵�
            		}
    			} catch(Exception e1) { //���� ó��
    				System.out.println(e1.getMessage());
    	        	e1.printStackTrace();
    			}
    		}
    	});
    	
    	CancleButton.addActionListener(new ActionListener() { //��� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIAdmin(); //������ ���� ȭ������ �̵�
        	}
        });
    }
    
    //����/��ġ ��ȣ �Է� â�� �Էµ� ���� �ùٸ� ������ Ȯ���ϴ� �޼ҵ�
    private boolean checkString(String place) {
    	try { //������ ������ ���Ͽ��� ����, ���� ���� ���� �ؽ�Ʈ�� �о����
    		BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
    		
    		String widthStr = br.readLine();
        	String heightStr = br.readLine();
        	
        	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
    		
    		//��ġ ��ȣ �Է� â���� �Է¹��� ���� ù ��° �ڸ��� ���ĺ�(�빮��)�̸�, ���� ���� ���̺��� ���� �� �ȿ� ������
    		if((place.charAt(0) >= 65 && place.charAt(0) < 65 + height)) { 
    			if(place.length() == 2) { //�Է¹��� ���� �� �ڸ��϶�(���ĺ� ����)
    				//�� ��° �ڸ��� ���� ���� ���̺��� ���� �� �ȿ� �ִٸ�
    				if(place.charAt(1) >= 49 && place.charAt(1) < 49 + width)
    					return true; //�ùٸ� ������ �ν��Ͽ� true ��ȯ
    			}else if(place.length() == 3) //�Է¹��� ���� �� �ڸ��϶�(���ĺ� ����)
    				//�Է¹��� ���� �� ��°�� �� ��° �ڸ��� ���� ���� 10�� �ȴٸ� true ��ȯ(�������� �ִ� 10�̱� ����)
    				if (("" + place.charAt(1) + place.charAt(2)).equals("10"))
    					return true;
    		}else //���� ���� ���̺� �������� �ʴ� ���̶�� false ��ȯ
    			return false;
    	} catch(Exception e) { //�� ���� ���� �Էµ� ��� false ��ȯ
    		return false;
    	}
    	return false; //�⺻�� false ��ȯ
    }
    
    public static void main(String[] args) {
		new GUIRestrict();
	}
}