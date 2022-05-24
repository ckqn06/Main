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

public class UpdateClientTable extends Thread{ //1분마다 고객 테이블 데이터를 갱신하는 스레드
	private GUIMain main;
	
	private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
	
	private JTable placeView; //주차 공간 테이블
	private JTable clientTable; //주차 공간 테이블	
	private JScrollPane placePane;
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //셀 가운데 정렬을 위한 요소
    
    private Font font = new Font("맑은 고딕", Font.BOLD, 25); //폰트 설정
	
    private int width, height, pay;
	
	public UpdateClientTable(GUIMain main, JTable clientTable) {
		this.main = main;
		this.clientTable = clientTable;
		try {
			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));

	    	String widthStr = br.readLine();  //데이터 파일에서 문자열 추출
	    	String heightStr = br.readLine();
	    	String payStr = br.readLine();
	    	
	    	width = Integer.parseInt(widthStr.split(":")[1]); //값 추출
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
				String[][] clientTableValue = dbc.getTable(); //데이터베이스에서 고객 테이블 값 가져오기
		        placeView = new JTable(height, width) {
		        	@Override
		        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) { // 주차중인 공간 색깔 바꾸기
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
		        		placeView.getColumnModel().getColumn(j).setPreferredWidth(87);  //너비 설정
		            	placeView.getColumnModel().getColumn(j).setCellRenderer(dtcr); //셀 가운데 정렬
		        		placeView.setValueAt("" + (char)(65+i) + (j+1), i, j); //각 셀에 값 넣기
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
				for(int i = 0; i < row; i++) { //row 전체 삭제
					clientModel.removeRow(0);
				}
				int i = 0;
				while(clientTableValue[i][0] != null) {
		        	int diffTime = GUIMain.diffTime(clientTableValue[i][1]);
		        	String parkTime = "" + (diffTime / 60) + "시간 " + (diffTime % 60) + "분";
		        	clientModel.addRow(new Object[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], ((diffTime/15 + 1) * (pay/4))}); //테이블에 값 추가
		        	i++;
		        }
				Thread.sleep(60000); // 1분 대기
			}
		}catch(Exception e) {
			
		}
		
		
	}
}
