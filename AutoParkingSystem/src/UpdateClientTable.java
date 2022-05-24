import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class UpdateClientTable extends Thread{ //1�и��� �� ���̺� �����͸� �����ϴ� ������
	private GUIMain main;
	
	private ParkDBConnection dbc = new ParkDBConnection(); //�����ͺ��̽� ���� ��ü
	
	private JTable placeView; //���� ���� ���̺�
	private JTable clientTable; //���� ���� ���̺�	
	private JScrollPane placePane;
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //�� ��� ������ ���� ���
    
    private Font font = new Font("���� ���", Font.BOLD, 25); //��Ʈ ����
	
    private int width, height, pay;
	
	public UpdateClientTable(GUIMain main, JTable clientTable) {
		this.main = main;
		this.clientTable = clientTable;
		try {
			BufferedReader br = new BufferedReader(new FileReader("������ ������ ����.txt"));

	    	String widthStr = br.readLine();  //������ ���Ͽ��� ���ڿ� ����
	    	String heightStr = br.readLine();
	    	String payStr = br.readLine();
	    	
	    	width = Integer.parseInt(widthStr.split(":")[1]); //�� ����
	    	height = Integer.parseInt(heightStr.split(":")[1]);
	    	pay = Integer.parseInt(payStr.split(":")[1]);
		}catch(Exception e) {}
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(10000);
			
			while(!this.isInterrupted()) {
				main.p2.remove(main.placePane);
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
		        
		        main.p2.add(placePane);
		        
		        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
				
				int row = clientTable.getRowCount();
				for(int i = 0; i < row; i++) { //row ��ü ����
					clientModel.removeRow(0);
				}
				int i = 0;
				while(clientTableValue[i][0] != null) {
		        	int diffTime = GUIMain.diffTime(clientTableValue[i][1]);
		        	String parkTime = "" + (diffTime / 60) + "�ð� " + (diffTime % 60) + "��";
		        	clientModel.addRow(new Object[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], ((diffTime/15 + 1) * (pay/4))}); //���̺� �� �߰�
		        	i++;
		        }
				Thread.sleep(60000); // 1�� ���
			}
		}catch(Exception e) {
			
		}
		
		
	}
}
