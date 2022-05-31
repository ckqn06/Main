import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class UpdateClientTable extends Thread{ //�� ���̺��� �����͸� �ֱ������� �����ϱ� ���� ������
	private Font font = new Font("���� ���", Font.BOLD, 25); //��Ʈ ����
	private GUIMain main; //���̺��� ��ġ�� ���� ȭ���� ����Ŵ
	
	private JTable placeView; //���� ���� ���̺� ����
	private JTable clientTable; //�� ���̺� ����
	private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���
	
	private JScrollPane placePane; //���� ���� ���̺��� ��ũ�ѹ� ����
	
    private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
    
    private int width, height, tpay; //����, ����, �ð��� ���� ����� ���� ����
    private int VerticalScrollBarMax = 0; //���� ��ũ���� �ִ� ũ��
    private int HorizontalScrollBarMax = 0; //���� ��ũ���� �ִ� ũ��
    private int VerticalScrollBar = 0; //���� ��ũ���� ��ġ
    private int HorizontalScrollBar = 0; //���� ��ũ���� ��ġ
    
    private String[] handicaps, noParks; //����� ���� ���� ����, ���� �Ұ� ������ ���� ����
	
	public UpdateClientTable(GUIMain main, JTable clientTable) {
		this.main = main;
		this.clientTable = clientTable;
		try {
			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));

	    	String widthStr = br.readLine();  //������ ���Ͽ��� ���ڿ� ����
	    	String heightStr = br.readLine();
	    	String payStr = br.readLine();
	    	
	    	for(int i = 0; i < 2; i++)
	    		br.readLine(); //�ʿ� ���� ������
	    	String handicapStr = br.readLine();
	    	String noParkStr = br.readLine();
	    	
	    	width = Integer.parseInt(widthStr.split(":")[1]); //�� ����
	    	height = Integer.parseInt(heightStr.split(":")[1]);
	    	tpay = Integer.parseInt(payStr.split(":")[1]);
	    	handicaps = handicapStr.split(":")[1].split(",");
	    	noParks = noParkStr.split(":")[1].split(",");
		}catch(Exception e) {}
	}
	
	@Override
	public void run() { //�����带 ����
		try {
			Thread.sleep(100); //�����尡 ó�� ����Ǿ� ����ϱ���� �ణ�� ��� �ð��� ����
			
			while(!this.isInterrupted()) { //�����尡 ���ͷ�Ʈ �ɸ��� ������ �� ���̺��� �����͸� ������
				if(placePane != null) { //���� ȭ���� ���� ���� ���̺��� ��ũ���� �����Ѵٸ�
					main.p2.remove(placePane); //���� ȭ���� ���� ���� ���̺��� ��ũ�ѹٸ� ������
					VerticalScrollBarMax = placePane.getVerticalScrollBar().getMaximum();
					HorizontalScrollBarMax = placePane.getHorizontalScrollBar().getMaximum();
					VerticalScrollBar = placePane.getVerticalScrollBar().getValue(); //���� ���� ��ũ���� ��ġ�� ������
			        HorizontalScrollBar = placePane.getHorizontalScrollBar().getValue(); //���� ���� ��ũ���� ��ġ�� ������
				}
				
				String[][] clientTableValue = dbc.getTable(); //DB���Ͽ� ����� �� ���̺��� ���� �ҷ���
		       
				placeView = new JTable(height, width) { //���� ���� ���̺��� ��� ���� ������ ������ ���Ͽ� ���� ����/���� ����ŭ ����
		        	@Override
		        	//���� ���� ������ ���� javax.swing.JTable prepareRenderer() �޼ҵ带 �������̵��Ͽ� ���̺��� ���� �������ǵ��� ��
		        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        		JComponent cell = (JComponent) super.prepareRenderer(renderer, row, column);
		        		
		        		for(int line = 0; line < clientTableValue.length; line++) {
		        			//���� ���� ���̺� �����ϴ� ��ġ ��ȣ �߿� �� ���̺� ����� ��ġ ��ȣ�� ������ ���� �����Ѵٸ�
		        			if(clientTableValue[line][2].equals(placeView.getValueAt(row, column).toString())) {
		        				cell.setBackground(Color.RED); //�ش� ��(��ġ ��ȣ)�� ����� ���������� �����Ͽ� ������ �������� ǥ��
		        				return cell; //�ش� ���� ��ȯ
		        			}
		        		}
		        		
	        			//���� �Ұ� �������� ������ ���̶��
	        			for(int i = 0; i < noParks.length; i++) {
	        				if(noParks[i].equals(placeView.getValueAt(row, column).toString())) {
	        					cell.setBackground(Color.BLACK); //�ش� ��(��ġ ��ȣ)�� ����� ���������� �����Ͽ� ���� �Ұ� �������� ǥ��
	        					return cell;
	        				}
	        			}
		        		
		        		//����� ���� ���� �������� ������ ���̶��
	        			for(int i = 0; i < handicaps.length; i++) { 
	        				if(handicaps[i].equals(placeView.getValueAt(row, column).toString())) {
	        					cell.setBackground(Color.GREEN); //�ش� ��(��ġ ��ȣ)�� ����� �ʷϻ����� �����Ͽ� ����� ���� �������� ǥ��
	        					return cell;
	        				}
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
		        for(int i=0; i<height; i++) { //������ ������ ���Ͽ��� �޾ƿ� ����, ���� �� ��ŭ �ݺ�
		        	for(int j=0; j<width; j++) {
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
		        placePane.getVerticalScrollBar().setMaximum(VerticalScrollBarMax);
		        placePane.getHorizontalScrollBar().setMaximum(HorizontalScrollBarMax);
		        placePane.getVerticalScrollBar().setValue(VerticalScrollBar);
		        placePane.getHorizontalScrollBar().setValue(HorizontalScrollBar);
		        
		        main.p2.add(placePane); //�۾��� �Ϸ�Ǹ� ���� ȭ���� ���� ���� ���̺��� ��ũ�ѹٸ� �ٽ� ������Ŵ
		        
		        //�� ���̺� ���� �����͸� �ٷ�� ���ؼ� DefaultTableModel�� �ҷ���
		        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
				
				int row = clientTable.getRowCount(); //DB���Ͽ� ������ �� ���̺��� �� ���� �������� ���� ���� ����
				
				for(int i=0; i<row; i++) { //DB���Ͽ��� ������ �� ���̺��� �� �߿��� �ʿ����(�����Ͱ� ���� ����ִ�) ���� ������
					clientModel.removeRow(0);
				}
				
				for(int line = 0; line < clientTableValue.length; line++) {
					int diffTime = GUIMain.diffTime(clientTableValue[line][1]); //�� ���̺��� 2��° ���� ���� �ð��� �����Ŵ
					int pay = ((diffTime/15 + 1) * (tpay/4))/10; //���� �ð��� ���� ���� ��� ���
		        	String parkTime = "" + (diffTime / 60)+"�ð� " + (diffTime % 60)+"��"; //����� ���� �ð�(�� ����)�� �ð�, ������ ǥ��
		        	//�� ���̺��� ���� ���� ��ȣ, ���� �ð�, ��ġ ��ȣ, �ð��� ���� ����� �߰�
		        	clientModel.addRow(new Object[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], pay*10+" ��"});
				}
				
				Thread.sleep(2000); //�����带 �ٽ� ���Ž�Ű����� 2�ʸ� ����Ŵ
			}
		} catch(Exception e) { //���ͷ�Ʈ �߻��� ���ͷ�Ʈ �߻� �޽����� ���
			System.out.println("���ͷ�Ʈ �߻�");
		}
	}
}