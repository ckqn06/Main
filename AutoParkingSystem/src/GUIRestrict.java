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
    
    private JButton AddButton = new JButton("�߰�"); //�߰� ��ư
    private JButton DeleteButton = new JButton("����"); //���� ��ư
    private JButton CancleButton = new JButton("���"); //��� ��ư
    
    private String width, height, pay, ID, PW; //����, ����, ������ ID, ��й�ȣ�� �����ϴ� ����
    private String[] currentHandi, currentNoPark;
    
    private File f = new File("������ ������ ����.txt"); //������ ������ ����

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
            	String HandiStr = arr[5];
            	String noParkStr = arr[6];
            	
            	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
            	width = widthStr.split(":")[1];
            	height = heightStr.split(":")[1];
            	pay = payStr.split(":")[1];
            	ID = IDStr.split(":")[1];
            	PW = PWStr.split(":")[1];
            	currentHandi = HandiStr.split(":")[1].split(",");
            	currentNoPark = noParkStr.split(":")[1].split(",");
            	
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
        
        AddButton.setLocation(650, 630);
        AddButton.setSize(160, 80);
        AddButton.setFont(font);
        
        DeleteButton.setLocation(400, 630);
        DeleteButton.setSize(160, 80);
        DeleteButton.setFont(font);
        
        CancleButton.setLocation(150, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel);
        p.add(detailLabel);
        p.add(handicapLabel);
        p.add(noParkLabel);
        p.add(handicapText);
        p.add(noParkText);
        p.add(AddButton);
        p.add(DeleteButton);
        p.add(CancleButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����    	
    	AddButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
    				ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
                	String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
                	String[] handicaps = handicapText.getText().split(",");
                	String[] noParks = noParkText.getText().split(",");
                	int settingOp;
                	
                	//checkString �޼ҵ忡�� ����� ���� ���� ����, ���� �Ұ� ���� â�� �Էµ� ���� �ùٸ��� �ʰ� �Էµ� ���� Ȯ�εƴٸ�
                	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
                		JOptionPane.showMessageDialog(null, "���� �Է����ּ���");
                		return;
                	}
                	
                	settingOp = checkSetting(handicaps, noParks); //���� �Է� â�� �Էµ� ���� � �������� �ľ�
                	
                	if(settingOp == -1) {
                		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
                		return;
                	}
                	
                	String str = "";
                	if(settingOp == 1) {
                		for(int i = 0; i < handicaps.length; i++)
                			for(int j = 0; j < currentHandi.length; j++) {
                				if(handicaps[i].equals(currentHandi[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ����� ���� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
                		for(int i = 0; i < noParks.length; i++)
                			for(int j = 0; j < currentNoPark.length; j++) {
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ���� �Ұ� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
        				//���Ͽ� ������ ���� �������
        				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
        						+ "\n����� ���� ���� ����:"+String.join(",", currentHandi)+","+String.join(",", handicaps) + "\n���� �Ұ� ����:"+String.join(",", currentNoPark)+","+String.join(",", noParks));
                	}else if(settingOp == 2) {
                		for(int i = 0; i < handicaps.length; i++)
                			for(int j = 0; j < currentHandi.length; j++) {
                				if(handicaps[i].equals(currentHandi[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ����� ���� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
        				
        				//���Ͽ� ������ ���� �������
        				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
        						+ "\n����� ���� ���� ����:"+String.join(",", currentHandi)+","+String.join(",", handicaps) + "\n���� �Ұ� ����:"+String.join(",", currentNoPark));
                	}else if(settingOp == 3) {
                		for(int i = 0; i < noParks.length; i++)
                			for(int j = 0; j < currentNoPark.length; j++) {
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ���� �Ұ� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
        				
        				//���Ͽ� ������ ���� �������
        				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
        						+ "\n����� ���� ���� ����:"+String.join(",", currentHandi) + "\n���� �Ұ� ����:"+String.join(",", currentNoPark)+","+String.join(",", noParks));
                	}
                	OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
                	byte[] by = str.getBytes();
        			os.write(by);
        			os.close();
        			
        			JOptionPane.showMessageDialog(null, "�����Ͻ� Ư�� ���� ������ ���������� ����ƽ��ϴ�");
        			dispose(); 
        			new GUIMain(); //���� ȭ������ �̵�
    			} catch(Exception e1) { //���� ó��
    				System.out.println(e1.getMessage());
    	        	e1.printStackTrace();
    			}
    		}
    	});
    	
    	DeleteButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
            	String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
            	String[] handicaps = handicapText.getText().split(",");
            	String[] noParks = noParkText.getText().split(",");
            	int settingOp;
            	
            	//checkString �޼ҵ忡�� ����� ���� ���� ����, ���� �Ұ� ���� â�� �Էµ� ���� �ùٸ��� �ʰ� �Էµ� ���� Ȯ�εƴٸ�
            	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "���� �Է����ּ���");
            		return;
            	}
            	
            	settingOp = checkSetting(handicaps, noParks); //���� �Է� â�� �Էµ� ���� � �������� �ľ�
            	
            	if(settingOp == -1) {
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            		return;
            	}
            	
            	String str = "";
            	if(settingOp == 1) {
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false; //���� ����� ���� ���� ���� ������ �ִ� ������ Ȯ���ϴ� ����
            			for(int j = 0; j < currentHandi.length; j++) {
            				if(handicaps[i].equals(currentHandi[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "����� ���� ���� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false; //���� ���� �Ұ� ���� ������ �ִ� ������ Ȯ���ϴ� ����
            			for(int j = 0; j < currentNoPark.length; j++) {
            				if(noParks[i].equals(currentNoPark[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "���� �Ұ� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		
            		String finalHandicap = "0"; //�����ǰ� ���� ���� ��
            		for(int i = 0; i < currentHandi.length; i++) {
            			Boolean isExist = false; //���� �����Ǿ�� �ϴ� ������ Ȯ��
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
            		String finalNoPark = "0"; //�����ǰ� ���� ���� ��
            		for(int i = 0; i < currentNoPark.length; i++) {
            			Boolean isExist = false; //���� �����Ǿ�� �ϴ� ������ Ȯ��
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		
            		
    				//���Ͽ� ������ ���� �������
    				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
    						+ "\n����� ���� ���� ����:"+finalHandicap + "\n���� �Ұ� ����:"+finalNoPark);
            	}else if(settingOp == 2) {
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false; //���� ����� ���� ���� ���� ������ �ִ� ������ Ȯ���ϴ� ����
            			for(int j = 0; j < currentHandi.length; j++) {
            				if(handicaps[i].equals(currentHandi[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "����� ���� ���� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		
            		String finalHandicap = "0"; //�����ǰ� ���� ���� ��
            		for(int i = 0; i < currentHandi.length; i++) {
            			Boolean isExist = false; //���� �����Ǿ�� �ϴ� ������ Ȯ��
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
    				
    				//���Ͽ� ������ ���� �������
    				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
    						+ "\n����� ���� ���� ����:"+finalHandicap + "\n���� �Ұ� ����:"+String.join(",", currentNoPark));
            	}else if(settingOp == 3) {
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false; //���� ���� �Ұ� ���� ������ �ִ� ������ Ȯ���ϴ� ����
            			for(int j = 0; j < currentNoPark.length; j++) {
            				if(noParks[i].equals(currentNoPark[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "���� �Ұ� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		
            		String finalNoPark = "0"; //�����ǰ� ���� ���� ��
            		for(int i = 0; i < currentNoPark.length; i++) {
            			Boolean isExist = false; //���� �����Ǿ�� �ϴ� ������ Ȯ��
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		
    				//���Ͽ� ������ ���� �������
    				str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+ID + "\nPW:"+PW
    						+ "\n����� ���� ���� ����:"+String.join(",", currentHandi) + "\n���� �Ұ� ����:"+finalNoPark);
            	}
            	try {
            		OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
                	byte[] by = str.getBytes();
        			os.write(by);
        			os.close();
            	}catch(Exception e2) {
            		System.out.println(e2.getMessage());
            	} 
            	
    			
    			JOptionPane.showMessageDialog(null, "�����Ͻ� Ư�� ���� ������ ���������� ����ƽ��ϴ�");
    			dispose(); 
    			new GUIMain(); //���� ȭ������ �̵�
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
    
    private int checkSetting(String[] handicaps, String[] noParks) { //�Է� â�� �Էµ� ���� � �������� �ľ�
    	Boolean checkHandi = true;
    	Boolean checkNoPark = true;
    	
    	for(int i = 0; i < handicaps.length; i++)
			if(!checkString(handicaps[i])) {
				checkHandi = false;
				break;
			}
    	for(int i = 0; i < noParks.length; i++)
    		if(!checkString(noParks[i]) ){
    			
    			checkNoPark = false;
				break;
    		}
    	
    	if(checkHandi&& checkNoPark) //�Ѵ� �ùٸ� ���� ��
    		return 1;
    	else if (checkHandi&& noParkText.getText().equals("")) //�ϳ��� �ùٸ� ���̰� �ϳ��� ������ ��
    		return 2;
    	else if (handicapText.getText().equals("") && checkNoPark) //�ϳ��� �ùٸ� ���̰� �ϳ��� ������ ��
    		return 3;
    	
    	return -1; //�ùٸ��� ���� ���� ��
    }
    
    public static void main(String[] args) {
		new GUIRestrict();
	}
}