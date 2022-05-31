import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

	//ȭ�鿡 �����ִ� �޽��� ����
    private JLabel carNumLabel = new JLabel("�� ������ ���� ��ȣ�� �Է��Ͻʽÿ�.");
    private JLabel exampleLabel = new JLabel("(����. 12��3456)");
    private JLabel placeLabel = new JLabel("�� ������ ��ġ ��ȣ�� �Է��Ͻʽÿ�.");

    private JTextField carNumText = new JTextField(); //���� ��ȣ �Է� â
    private JTextField placeText = new JTextField(); //��ġ ��ȣ �Է� â
    
    private JButton cancleButton = new JButton("���"); //��� ��ư
    private JButton parkingButton = new JButton("����"); //���� ��ư
    
    private File f = new File("������ ������ ����.txt"); //������ ������ ����

    GUIParking(){ //ȭ�� �⺻ ����
        this.setTitle("���� ���� ���� �ý���");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() { //�� GUI ��ü ����
        this.add(p);
        p.setLayout(null);

        carNumLabel.setLocation(40, 80);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);
        
        exampleLabel.setLocation(215, 135);
        exampleLabel.setSize(900, 100);
        exampleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        exampleLabel.setFont(new Font("���", Font.PLAIN, 30));
        
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
        p.add(exampleLabel);
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
            	new GUIMain(); //���� ȭ������ �̵�
        	}		             
        });
        
        parkingButton.addActionListener(new ActionListener() { //���� ��ư Ŭ���� ����
            public void actionPerformed(ActionEvent e) {
            	ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
            	String[][] clientTableValue = dbc.getTable(); //DB���� ���� �� ���̺��� ������
            	
            	try {
            		BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
                	List<String> list = new ArrayList<String>(); //�о���� ������ ������ ������ ������ �����ϱ� ���� ����Ʈ ����
                	String setting = null; //������ ������ ������ �о���̱� ���� ����
                	
                	while((setting = br.readLine()) != null) { //������ ������ ������ null�� �ƴ� ������ �о����
                		list.add(setting); //�о���� ������ ����Ʈ�� ����
                	}
                	
                	int ListSize = list.size(); //����Ʈ�� ����� ��ü�� ���� ����
                	String arr[] = list.toArray(new String[ListSize]); //����Ʈ�� ����� ��ü�� �Բ� �迭�� ��ȯ��
                	
                	//�迭�� n��°�� ����� �����/���� �Ұ� ���� ��ȣ�� ������ �����ϱ� ���� ����
                	String noParkStr = arr[6];
                	
                	String[] noParks = noParkStr.split(":")[1].split(",");
                	
                	//checkString �޼ҵ忡�� ����/��ġ ��ȣ �Է� â�� �Էµ� ���� �ùٸ��� �ʰ� �Էµ� ���� Ȯ�εƴٸ�
                	if(!checkString(carNumText.getText(), placeText.getText())) {
                		JOptionPane.showMessageDialog(null, "�ùٸ� ���� �Է����ּ���");
                		return;
            		} 
                	
                	for(int line = 0; line < clientTableValue.length; line++) {
                		//��ġ ��ȣ �Է� â�� �Է��� ���� ������ ��ȣ�� �� ���̺� �����Ѵٸ�
                		if(clientTableValue[line][2].equals(placeText.getText())) {
                            JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� �̹� �����ϰ� �ִ� �����Դϴ�"); 
                            return; //�ش� ��ġ ��ȣ�� ��ȯ
                        //���� ��ȣ �Է� â�� �Է��� ���� ������ ��ȣ�� �� ���̺� �����Ѵٸ�
                    	}else if(clientTableValue[line][0].equals(carNumText.getText())) {
                    		JOptionPane.showMessageDialog(null, "�ش� ���� ��ȣ�� �̹� �ִ� �����Դϴ�"); 
                            return; //�ش� ���� ��ȣ�� ��ȯ
                    	}
                		
                		for(int i = 0; i < noParks.length; i++) {
                			//��ġ ��ȣ �Է� â�� �Է��� ���� ������ ��ȣ�� ���� �Ұ� �������� �����Ǿ��ִٸ�
                			if(noParks[i].equals(placeText.getText())) {
                				JOptionPane.showMessageDialog(null, "�ش� ��ġ ��ȣ�� ���� �Ұ� �����Դϴ�"); 
                                return;
                			}
                		}
                	}
                	//�� ���̺� ������ ����/��ġ ��ȣ�� �������� �ʴ� ��� �Էµ� ����/��ġ ��ȣ�� DB���Ͽ� ����
                	dbc.data_insert(carNumText.getText(), placeText.getText());
                	
                	dispose();
                	new GUIMain();
            	}catch(Exception e1) {
            		System.out.println(e1.getMessage());
            	}
        	}		        	             
        });
    }
    
    //����/��ġ ��ȣ �Է� â�� �Էµ� ���� �ùٸ� ������ Ȯ���ϴ� �޼ҵ�
    private boolean checkString(String carNum, String place) {
    	try { //������ ������ ���Ͽ��� ����, ���� ���� ���� �ؽ�Ʈ�� �о����
    		BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));
    		
    		String widthStr = br.readLine();
        	String heightStr = br.readLine();
        	
        	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
    		
        	String str = carNumText.getText();
        	char check = str.charAt(2);
        	
    		if(carNumText.getText().length() != 7) //���� ��ȣ �Է� â���� �Է¹��� ���� 7�ڸ��� �ƴϸ�
    			return false; //�ùٸ��� ���� ������ �ν��Ͽ� false ��ȯ
    		
    		//���� ��ȣ �Է� â���� �Է¹��� ���� 7�ڸ�����, 3��° �ڸ��� ���ڶ��
    		if(carNumText.getText().length() == 7 && check >= 48 && check <= 57)
    			return false;
    		
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
}