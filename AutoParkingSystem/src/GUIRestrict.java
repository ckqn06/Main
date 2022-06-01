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
    private String[] currentHandi, currentNoPark; //������ ������ ���Ͽ� ����� ����� ����/���� �Ұ� ���� ���� �����ϴ� ����
    
    private ServerConnection sct = new ServerConnection(); //���� ���� ��ü

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
    		if(sct.isSetting()) { //������ ������ ���Ͽ��� �ؽ�Ʈ�� �о����
    			String[] settingData = sct.getSetting(); //������ ������ ���Ͽ��� ���� �о����
            	
            	//�迭�� n��°�� ����� ����, ����, �ð��� ���� ���, ������ ID/PW, ����� ����/���� �Ұ� ���� ��ȣ�� ���� �����ϱ� ���� ����
            	width = settingData[0];
            	height = settingData[1];
            	pay = settingData[2];
            	ID = settingData[3];
            	PW = settingData[4];
            	//����� ���� split() �޼��带 �̿��� ","�� �������� ���ڿ��� ���� ��, ������ ���� ���
            	currentHandi = settingData[5].split(",");
            	currentNoPark = settingData[6].split(",");
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
                	String[][] clientTableValue = sct.getTableData(); //������ ���� DB���� ���� �� ���̺��� ������
                	String[] handicaps = handicapText.getText().split(","); //����� ���� ���� �Է� â�� �Է��� ���� �����ϱ� ���� ����
                	String[] noParks = noParkText.getText().split(","); //���� �Ұ� ���� �Է� â�� �Է��� ���� �����ϱ� ���� ����
                	int settingOp; //���� �Է� â�� �Էµ� ���� ������ �����ϴ� ����
                	
                	//checkString �޼ҵ忡�� ����� ���� ���� ����, ���� �Ұ� ���� �Է� â�� �� �� ������ �Էµƴٸ�
                	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
                		JOptionPane.showMessageDialog(null, "���� �Է����ּ���");
                		return;
                	}
                	
                	settingOp = checkSetting(handicaps, noParks); //���� �Է� â�� �Էµ� ���� � �������� �ľ�
                	
                	if(settingOp == -1) { //�Էµ� ���� �ùٸ��� ���� ���̶��
                		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
                		return;
                	}
      
                	String[] settingData = {}; //������ ������ ���Ͽ� ����� ���� �����ϱ� ���� �迭 ����
                	
                	if(settingOp == 1) { //����� ����/���� �Ұ� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ����� ����/���� �Ұ� ���� �� �߿��� ����� ���� �����ϴ� ���
                		for(int i = 0; i < handicaps.length; i++) //����� ���� ���� �Է� â�� �Է��� ����
                			for(int j = 0; j < currentHandi.length; j++) { //������ ������ ���Ͽ� ����� ����� ���� ���� ���� �о����
                				if(handicaps[i].equals(currentHandi[j])) { //�о���� ������ �� �߿��� ����� ���� �����ϴ� ���
                					JOptionPane.showMessageDialog(null, "�̹� ����� ���� �������� ������ ���� �ֽ��ϴ�");
                					return; //�ش� ���� ��ȯ
                				}
                			}
                		for(int i = 0; i < noParks.length; i++) //���� �Ұ� ���� �Է� â�� �Է��� ����
                			for(int j = 0; j < currentNoPark.length; j++) { //������ ������ ���Ͽ� ����� ���� �Ұ� ���� ���� �о����
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ���� �Ұ� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
        				//������ ������ ���Ͽ� ����� ����� ����/���� �Ұ� ������ ���� �����Ŵ
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi)+","+String.join(",", handicaps),
                				String.join(",", currentNoPark)+","+String.join(",", noParks)};
        			
                	}else if(settingOp == 2) { //����� ���� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ����� ���� ���� �� �߿��� ����� ���� �����ϴ� ���
                		for(int i = 0; i < handicaps.length; i++)
                			for(int j = 0; j < currentHandi.length; j++) {
                				if(handicaps[i].equals(currentHandi[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ����� ���� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi)+","+String.join(",", handicaps),
                				String.join(",", currentNoPark)};
                	
                	}else if(settingOp == 3) { //���� �Ұ� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ���� �Ұ� ���� �� �߿��� ����� ���� �����ϴ� ���
                		for(int i = 0; i < noParks.length; i++)
                			for(int j = 0; j < currentNoPark.length; j++) {
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "�̹� ���� �Ұ� �������� ������ ���� �ֽ��ϴ�");
                					return;
                				}
                			}
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi), String.join(",", currentNoPark)
                				+"," + ""+String.join(",", noParks)};
                	}
                	sct.setSetting(settingData); //������ �����Ͽ� ������ ������ ���Ͽ� ����� ���� ����
        			
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
            	String[] handicaps = handicapText.getText().split(",");
            	String[] noParks = noParkText.getText().split(",");
            	int settingOp;
            	
            	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "���� �Է����ּ���");
            		return;
            	}
            	
            	settingOp = checkSetting(handicaps, noParks);
            	
            	if(settingOp == -1) {
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            		return;
            	}
            	
            	String[] settingData = {};
            	
            	if(settingOp == 1) { //����� ����/���� �Ұ� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ����� ����/���� �Ұ� ���� �� �߿��� ����� ���� �����ϴ� ���
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false; //����� ���� ���� �Է� â�� �Է��� ���� ���� ���� ���̺� �̹� �����Ǿ��� ������ Ȯ���ϴ� �Ҹ���
            			
            			for(int j = 0; j < currentHandi.length; j++) {
            				if(handicaps[i].equals(currentHandi[j])) {
            					isExist = true; //����� ���� ������ �������� Ȯ��
            				}
            			}
            			if(!isExist) { //�ش� ������ �������� �ʴ� ��� ���Ÿ� ���� ����
        					JOptionPane.showMessageDialog(null, "����� ���� ���� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false; //���� �Ұ� ���� �Է� â�� �Է��� ���� ���� ���� ���̺� �̹� �����Ǿ��� ������ Ȯ���ϴ� �Ҹ���
            			
            			for(int j = 0; j < currentNoPark.length; j++) {
            				if(noParks[i].equals(currentNoPark[j])) {
            					isExist = true; //���� �Ұ� ������ �������� Ȯ��
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "���� �Ұ� �������� �����Ǿ� ���� ���� ���� �ֽ��ϴ�");
        					return;
        				}
            		}
            		
            		String finalHandicap = "0"; //�����ǰ� ���� ������ 0���� ����
            		for(int i = 1; i < currentHandi.length; i++) {
            			Boolean isExist = false; //����� ���� ���� �Է� â�� �Է��� ���� ���� ���� ���̺� �̹� �����Ǿ��� ������ Ȯ���ϴ� �Ҹ���
            			
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true; //����� ���� ���� �Է� â�� �Է��� ���� ���� ���� ���̺��� ���������� �������� �Ҹ���
            				}
            			}
            			if(!isExist) { //����� ���� ���� �Է� â�� �Է� â�� �Է����� ���� ���� �״�� ������ ����
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
            		
            		String finalNoPark = "0";
            		for(int i = 1; i < currentNoPark.length; i++) {
            			Boolean isExist = false; //���� �Ұ� ���� �Է� â�� �Է��� ���� ���� ���� ���̺� �̹� �����Ǿ��� ������ Ȯ���ϴ� �Ҹ���
            			
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true; //���� �Ұ� ���� �Է� â�� �Է��� ���� ���� ���� ���̺��� ���������� �������� Ȯ��
            				}
            			}
            			if(!isExist) { //���� �Ұ� ���� �Է� â�� �Է� â�� �Է����� ���� ���� �״�� ������ ����
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, finalHandicap, finalNoPark};
            	
            	}else if(settingOp == 2) { //����� ���� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ����� ���� ���� �� �߿��� ����� ���� �����ϴ� ���
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false;
            			
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
            		
            		String finalHandicap = "0";
            		for(int i = 1; i < currentHandi.length; i++) {
            			Boolean isExist = false;
            			
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, finalHandicap, String.join(",", currentNoPark)};
            	
            	}else if(settingOp == 3) { //���� �Ұ� ���� �Է� â�� �Էµ� ���� ������ ������ ���Ͽ� ����� ���� �Ұ� ���� �� �߿��� ����� ���� �����ϴ� ���
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false;
            			
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
            		
            		String finalNoPark = "0";
            		for(int i = 1; i < currentNoPark.length; i++) {
            			Boolean isExist = false;
            			
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, String.join(",", currentHandi), finalNoPark};
            	}
            	sct.setSetting(settingData);
    			
    			JOptionPane.showMessageDialog(null, "�����Ͻ� Ư�� ���� ������ ���������� ����ƽ��ϴ�");
    			dispose(); 
    			new GUIMain();
        	}
        });
    	
    	CancleButton.addActionListener(new ActionListener() { //��� ��ư Ŭ�� �� ����
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIAdmin(); //������ ȭ������ �̵�
        	}
        });
    }
    
    //����/��ġ ��ȣ �Է� â�� �Էµ� ���� �ùٸ� ������ Ȯ���ϴ� �޼ҵ�
    private boolean checkString(String place) {
    	try { //������ ������ ���Ͽ��� ����, ���� ���� ���� �ؽ�Ʈ�� �о����
    		String[] settingData = sct.getSetting();
    		
        	int width = Integer.parseInt(settingData[0]);
        	int height = Integer.parseInt(settingData[1]);
    		
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
    
    private int checkSetting(String[] handicaps, String[] noParks) { //�Է� â�� �Էµ� ���� � �������� Ȯ���ϴ� �޼ҵ�
    	Boolean checkHandi = true; //�Է� â�� �Էµ� ����� ���� ������ Ȯ���ϴ� �Ҹ���
    	Boolean checkNoPark = true; //�Է� â�� �Էµ� ���� �Ұ� ������ Ȯ���ϴ� �Ҹ���
    	
    	for(int i = 0; i < handicaps.length; i++)
			if(!checkString(handicaps[i])) { //checkString �޼ҵ忡�� ����� ���� ���� �Է� â�� �ùٸ��� ���� ���� �Է��� ���
				checkHandi = false; //false ��ȯ
				break;
			}
    	
    	for(int i = 0; i < noParks.length; i++)
    		if(!checkString(noParks[i]) ){ //checkString �޼ҵ忡�� ���� �Ұ� ���� �Է� â�� �ùٸ��� ���� ���� �Է��� ���
    			checkNoPark = false;
				break;
    		}
    	
    	if(checkHandi&& checkNoPark) //�� �� �ùٸ� ���� �Է��� ���
    		return 1;
    	//����� ���� ���� �Է� â���� �ùٸ� ���� �Է������� ���� �Ұ� ���� �Է� â���� ������ ���
    	else if (checkHandi&& noParkText.getText().equals(""))
    		return 2;
    	//���� �Ұ� ���� �Է� â���� �ùٸ� ���� �Է������� ����� ���� ���� �Է� â���� ������ ���
    	else if (handicapText.getText().equals("") && checkNoPark)
    		return 3;
    	
    	return -1; //�ùٸ��� ���� ���� �Էµ� ���
    }
}