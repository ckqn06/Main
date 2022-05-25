import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

    private JLabel carNumLabel = new JLabel("�� ������ ���� ��ȣ�� �Է��Ͻʽÿ�.");
    private JLabel placeLabel = new JLabel("�� ������ ��ġ ��ȣ�� �Է��Ͻʽÿ�.");

    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    private JTextField placeText = new JTextField(); //��ġ ��ȣ �Է� â
    
    private JButton cancleButton = new JButton("���"); //��� ��ư
    private JButton parkingButton = new JButton("����"); //���� ��ư

    GUIParking(){ //ȭ�� �⺻ ����
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

        carNumLabel.setLocation(40, 120);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);
        
        placeLabel.setLocation(40, 360);
        placeLabel.setSize(900, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeLabel.setFont(font);

        carNumText.setLocation(160, 220);
        carNumText.setSize(650, 80);
        carNumText.setFont(font);
        
        placeText.setLocation(160, 470);
        placeText.setSize(650, 80);
        placeText.setFont(font);
        
        cancleButton.setLocation(200, 620);
        cancleButton.setSize(200, 80);
        cancleButton.setFont(font);
        
        parkingButton.setLocation(600, 620);
        parkingButton.setSize(200, 80);
        parkingButton.setFont(font);

        p.add(carNumLabel);
        p.add(placeLabel);
        p.add(carNumText);
        p.add(placeText);
        p.add(cancleButton);
        p.add(parkingButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����
        cancleButton.addActionListener(new ActionListener() { //��� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	dispose();        	
            	new GUIMain();
        	}		             
        });
        
        parkingButton.addActionListener(new ActionListener() { //���� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
            	String[][] clientTableValue = dbc.getTable();
            	
            	if(!checkString(carNumText.getText(), placeText.getText())) { //�ùٸ� ���� �ƴ϶��
            		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
            		return;
        		} 
            	
            	int i = 0;
            	while(clientTableValue[i][0] != null) {
            		if(clientTableValue[i][2].equals(placeText.getText())) { //�ش��ϴ� ��ġ ��ȣ�� �̹� �� �ְų� �ش� ������ �̹� �ִ� ���
                        JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� �̹� �����ϰ� �ִ� �����Դϴ�"); 
                        return;
                	}else if(clientTableValue[i][0].equals(carNumText.getText())) {
                		JOptionPane.showMessageDialog(null, "�ش� ���� ��ȣ�� �̹� �ִ� �����Դϴ�"); 
                        return;
                	}
            		i++;
            	}
            	dbc.data_insert(carNumText.getText(), placeText.getText()); //�����ͺ��̽��� ���� �߰�
            	dispose();
            	new GUIMain();  //����ȭ������
        	}		        	             
        });
    }
    
    private boolean checkString(String carNum, String place) { //�ùٸ� ������ Ȯ���ϴ� �޼ҵ�
    	try {
    		BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
    		
    		String widthStr = br.readLine();  //������ ���Ͽ��� ���ڿ� ����
        	String heightStr = br.readLine();
        	
        	int width = Integer.parseInt(widthStr.split(":")[1]); //�� ����
        	int height = Integer.parseInt(heightStr.split(":")[1]);
        	
    		Integer.parseInt(carNumText.getText()); //�������� Ȯ��
    		if(carNumText.getText().length() != 4)   //���ڸ� ������ Ȯ��
        		return false;
    		if((place.charAt(0) >= 65 && place.charAt(0) < 65 + height)) { //�������� ���ĺ����� Ȯ��
    			if(place.length() == 2) { //�� �ڸ� ���� �� 
    				if(place.charAt(1) >= 49 && place.charAt(1) < 49 + width) //�������� ������ Ȯ��
    					return true;
    			}else if(place.length() == 3) //�� �ڸ� ���� ��
    				if (("" + place.charAt(1) + place.charAt(2)).equals("10")) //10���� Ȯ��
    					return true;
    		}else
    			return false;   		
    	}catch(Exception e) {
    		return false;
    	}
    	return false;
    }
	
	public static void main(String[] args) { //���� �׽�Ʈ�� ���� �ڵ�
		new GUIParking();
	}
}