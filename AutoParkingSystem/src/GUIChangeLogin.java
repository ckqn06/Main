import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIChangeLogin extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("���� ���", Font.BOLD, 40); //��Ʈ ����

	//ȭ�鿡 �����ִ� �޽��� ����
	private JLabel TopLabel_1 = new JLabel("�� �̰��� ������ ID�� ��й�ȣ��");
	private JLabel TopLabel_2 = new JLabel("�����ϴ� ȭ���Դϴ�.");
	private JLabel detailLabel = new JLabel("<html>(ID/PW�� 1�� �̻� �� ���� ����)</html>");
    private JLabel IDLabel = new JLabel("������ ID :");
    private JLabel PWLabel = new JLabel("��й�ȣ :");

    private JTextField IDText = new JTextField(); //ID �Է� â
    private JTextField PWText = new JTextField(); //��й�ȣ �Է� â
    
    private JButton ChangeButton = new JButton("����"); //���� ��ư
    private JButton CancleButton = new JButton("���"); //��� ��ư
    
    private String width, height, pay, handicap, noPark; //����, ����, �ð��� ���� ���, �����/���� �Ұ� ���� ��ȣ�� �����ϴ� ����
    
    File f = new File("������ ������ ����.txt"); //������ ������ ����

    GUIChangeLogin(){ //ȭ�� �⺻ ����
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
            	String handicapStr = arr[5];
            	String noParkStr = arr[6];
            	
            	//�о���� �ؽ�Ʈ���� split() �޼��带 �̿��� ":"�� �������� ���ڿ��� ���� ��, ������ ���� �� ������ ����
            	width = widthStr.split(":")[1];
            	height = heightStr.split(":")[1];
            	pay = payStr.split(":")[1];
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

        TopLabel_1.setLocation(30, 50);
        TopLabel_1.setSize(900, 100);
        TopLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        TopLabel_1.setFont(font);
        
        TopLabel_2.setLocation(230, 110);
        TopLabel_2.setSize(900, 100);
        TopLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        TopLabel_2.setFont(font);
        
        detailLabel.setLocation(180, 115);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("���", Font.PLAIN, 30));
        
        IDLabel.setLocation(0, 290);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(new Font("���� ���", Font.BOLD, 45));

        PWLabel.setLocation(0, 460);
        PWLabel.setSize(400, 100);
        PWLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PWLabel.setFont(new Font("���� ���", Font.BOLD, 45));
        
        IDText.setSize(400, 100);
        IDText.setLocation(450, 290);
        IDText.setFont(font);

        PWText.setSize(400, 100);
        PWText.setLocation(450, 460);
        PWText.setFont(font);
        
        ChangeButton.setLocation(550, 630);
        ChangeButton.setSize(160, 80);
        ChangeButton.setFont(font);
        
        CancleButton.setLocation(250, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel_1);
        p.add(TopLabel_2);
        p.add(detailLabel);
        p.add(IDLabel);
        p.add(PWLabel);
        p.add(IDText);
        p.add(PWText);
        p.add(ChangeButton);
        p.add(CancleButton);
    }

    private void eventListner() { //��ư Ŭ�� �̺�Ʈ ����    	
    	ChangeButton.addActionListener(new ActionListener() { //���� ��ư Ŭ�� �� ����
    		public void actionPerformed(ActionEvent e) {
    			try {
        			if(IDText.getText().equals("") || PWText.getText().equals("")) { //ID/PW �Է� â�� �ƹ� ���� �Է����� ���� ���
            			JOptionPane.showMessageDialog(null, "������ ID �� ��й�ȣ�� �ּ� 1�ڸ� �̻��̿��� �մϴ�");
            		} else { //
            			OutputStream os = new FileOutputStream(f); //���Ͽ� �ؽ�Ʈ�� �Է��ϱ� ���� ��� ��Ʈ�� ����
        				
        				//���Ͽ� ������ ������ ID, ��й�ȣ�� �������
        				String str = ("���� ��:"+width + "\n���� ��:"+height + "\n�ð��� ���� ���:"+pay + "\nID:"+IDText.getText()
        						+ "\nPW:"+PWText.getText() + "\n����� ���� ���� ����:"+handicap + "\n���� �Ұ� ����:"+noPark);
            			byte[] by = str.getBytes();
            			os.write(by);
            			
            			JOptionPane.showMessageDialog(null, "�����Ͻ� ������ ID �� ��й�ȣ�� ���������� ����ƽ��ϴ�");
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
}