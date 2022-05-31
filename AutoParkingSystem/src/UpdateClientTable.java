import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class UpdateClientTable extends Thread{ //고객 테이블의 데이터를 주기적으로 갱신하기 위한 스레드
	private Font font = new Font("맑은 고딕", Font.BOLD, 25); //폰트 설정
	private GUIMain main; //테이블이 위치한 메인 화면을 가리킴
	
	private JTable placeView; //주차 공간 테이블 생성
	private JTable clientTable; //고객 테이블 생성
	private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //셀 가운데 정렬을 위한 요소
	
	private JScrollPane placePane; //주차 공간 테이블의 스크롤바 생성
	
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    
    private int width, height, tpay; //가로, 세로, 시간당 주차 비용을 위한 변수
    private int VerticalScrollBarMax = 0; //가로 스크롤의 최대 크기
    private int HorizontalScrollBarMax = 0; //세로 스크롤의 최대 크기
    private int VerticalScrollBar = 0; //세로 스크롤의 위치
    private int HorizontalScrollBar = 0; //가로 스크롤의 위치
    
    private String[] handicaps, noParks; //장애인 전용 주차 구역, 주차 불가 구역을 위한 변수
	
	public UpdateClientTable(GUIMain main, JTable clientTable) {
		this.main = main;
		this.clientTable = clientTable;
		try {
			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));

	    	String widthStr = br.readLine();  //데이터 파일에서 문자열 추출
	    	String heightStr = br.readLine();
	    	String payStr = br.readLine();
	    	
	    	for(int i = 0; i < 2; i++)
	    		br.readLine(); //필요 없는 데이터
	    	String handicapStr = br.readLine();
	    	String noParkStr = br.readLine();
	    	
	    	width = Integer.parseInt(widthStr.split(":")[1]); //값 추출
	    	height = Integer.parseInt(heightStr.split(":")[1]);
	    	tpay = Integer.parseInt(payStr.split(":")[1]);
	    	handicaps = handicapStr.split(":")[1].split(",");
	    	noParks = noParkStr.split(":")[1].split(",");
		}catch(Exception e) {}
	}
	
	@Override
	public void run() { //스레드를 실행
		try {
			Thread.sleep(100); //스레드가 처음 실행되어 기능하기까지 약간의 대기 시간을 가짐
			
			while(!this.isInterrupted()) { //스레드가 인터럽트 걸리기 전까지 고객 테이블의 데이터를 갱신함
				if(placePane != null) { //메인 화면의 주차 공간 테이블에서 스크롤이 존재한다면
					main.p2.remove(placePane); //메인 화면의 주차 공간 테이블에서 스크롤바를 제거함
					VerticalScrollBarMax = placePane.getVerticalScrollBar().getMaximum();
					HorizontalScrollBarMax = placePane.getHorizontalScrollBar().getMaximum();
					VerticalScrollBar = placePane.getVerticalScrollBar().getValue(); //현재 세로 스크롤의 위치를 가져옴
			        HorizontalScrollBar = placePane.getHorizontalScrollBar().getValue(); //현재 가로 스크롤의 위치를 가져옴
				}
				
				String[][] clientTableValue = dbc.getTable(); //DB파일에 저장된 고객 테이블의 값을 불러옴
		       
				placeView = new JTable(height, width) { //주차 공간 테이블의 행과 열을 관리자 데이터 파일에 적힌 가로/세로 값만큼 생성
		        	@Override
		        	//셀의 색상 변경을 위해 javax.swing.JTable prepareRenderer() 메소드를 오버라이딩하여 테이블의 셀이 렌더링되도록 함
		        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        		JComponent cell = (JComponent) super.prepareRenderer(renderer, row, column);
		        		
		        		for(int line = 0; line < clientTableValue.length; line++) {
		        			//주차 공간 테이블에 존재하는 위치 번호 중에 고객 테이블에 저장된 위치 번호와 동일한 값이 존재한다면
		        			if(clientTableValue[line][2].equals(placeView.getValueAt(row, column).toString())) {
		        				cell.setBackground(Color.RED); //해당 셀(위치 번호)의 배경을 빨간색으로 지정하여 주차된 공간임을 표시
		        				return cell; //해당 셀을 반환
		        			}
		        		}
		        		
	        			//주차 불가 구역으로 설정된 값이라면
	        			for(int i = 0; i < noParks.length; i++) {
	        				if(noParks[i].equals(placeView.getValueAt(row, column).toString())) {
	        					cell.setBackground(Color.BLACK); //해당 셀(위치 번호)의 배경을 검은색으로 지정하여 주차 불가 공간임을 표시
	        					return cell;
	        				}
	        			}
		        		
		        		//장애인 전용 주차 구역으로 설정된 값이라면
	        			for(int i = 0; i < handicaps.length; i++) { 
	        				if(handicaps[i].equals(placeView.getValueAt(row, column).toString())) {
	        					cell.setBackground(Color.GREEN); //해당 셀(위치 번호)의 배경을 초록색으로 지정하여 장애인 전용 공간임을 표시
	        					return cell;
	        				}
	        			}
		        		
		        		cell.setBackground(Color.white); //동일한 위치 번호가 없다면 해당 셀의 배경을 흰색으로 지정하여 빈 공간임을 표시 
		        		return cell;
		        	}
		        };
		        placeView.setRowHeight(87); //테이블의 열 높이 설정
		        placeView.setTableHeader(null); //테이블의 헤더를 null로 설정 (= 헤더를 없앰)
		        placeView.setEnabled(false); //셀의 클릭 기능을 비활성화함
		        placeView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //셀의 크기 조정
		        placeView.setFont(font);
		        placeView.setBackground(Color.white); //셀의 기본 배경색은 흰색으로 지정
		        
		        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //테이블의 셀을 가운데 정렬함
		        for(int i=0; i<height; i++) { //관리자 데이터 파일에서 받아온 가로, 세로 값 만큼 반복
		        	for(int j=0; j<width; j++) {
		        		placeView.getColumnModel().getColumn(j).setPreferredWidth(87); //셀 너비 설정
		            	placeView.getColumnModel().getColumn(j).setCellRenderer(dtcr);
		        		placeView.setValueAt("" + (char)(65+i) + (j+1), i, j); //주차 공간 테이블의 각 셀에 위치 번호를 부여함
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
		        
		        main.p2.add(placePane); //작업이 완료되면 메인 화면의 주차 공간 테이블에서 스크롤바를 다시 생성시킴
		        
		        //고객 테이블 내부 데이터를 다루기 위해서 DefaultTableModel을 불러옴
		        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
				
				int row = clientTable.getRowCount(); //DB파일에 생성된 고객 테이블의 열 수를 가져오기 위한 변수 생성
				
				for(int i=0; i<row; i++) { //DB파일에서 가져온 고객 테이블의 열 중에서 필요없는(데이터가 없어 비어있는) 열은 삭제함
					clientModel.removeRow(0);
				}
				
				for(int line = 0; line < clientTableValue.length; line++) {
					int diffTime = GUIMain.diffTime(clientTableValue[line][1]); //고객 테이블의 2번째 열에 주차 시간을 저장시킴
					int pay = ((diffTime/15 + 1) * (tpay/4))/10; //주차 시간을 통해 주차 비용 계산
		        	String parkTime = "" + (diffTime / 60)+"시간 " + (diffTime % 60)+"분"; //계산한 주차 시간(분 단위)을 시간, 분으로 표시
		        	//고객 테이블의 열에 차량 번호, 주차 시간, 위치 번호, 시간당 주차 비용을 추가
		        	clientModel.addRow(new Object[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], pay*10+" 원"});
				}
				
				Thread.sleep(2000); //스레드를 다시 갱신시키기까지 2초를 대기시킴
			}
		} catch(Exception e) { //인터럽트 발생시 인터럽트 발생 메시지를 출력
			System.out.println("인터럽트 발생");
		}
	}
}