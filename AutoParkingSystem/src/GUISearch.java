import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정
    
    private JLabel label = new JLabel("▶ 검색하신 차량의 정보입니다."); //화면에 보여주는 메시지
    
    private JButton backButton = new JButton("돌아가기"); //돌아가기 버튼
    private JButton payButton = new JButton("결제하기"); //결제하기 버튼
    
    private String[] header = {"차량번호", "주차 시간", "현재 위치", "비용"}; //고객 테이블의 헤더 생성
    private String[][] rows = {}; //고객 테이블의 열(세로 줄) 생성
    private TableModel tableModel = new DefaultTableModel(rows, header); //생성한 헤더와 열을 하나의 테이블로 정의함
    private JTable clientTable = new JTable(tableModel); //하나로 정의한 테이블로 고객 테이블을 생성

    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //셀 가운데 정렬을 위한 요소
    private JScrollPane clientPane; //고객 테이블의 스크롤바 생성
    
    private String[] clientValue; //테이블에 표시할 값을 저장하기 위한 변수

    GUISearch(String[] clientValue){ //화면 기본 설정
    	this.clientValue = clientValue;
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() {	
    	this.add(p);
        p.setLayout(null);
        
        label.setLocation(30, 180);
        label.setSize(900, 100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        
        backButton.setLocation(160, 620);
        backButton.setSize(250, 80);
        backButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);
        
        //고객 테이블 내부 데이터를 다루기 위해서 DefaultTableModel을 불러옴
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        clientModel.addRow(clientValue); //테이블에 검색된 결과 값 추가
        
        clientTable.setRowHeight(55);
        clientTable.getTableHeader().setFont(new Font("고딕", Font.PLAIN, 30));
        clientTable.setFont(new Font("고딕", Font.PLAIN, 30));
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(45, 320);
        clientPane.setSize(900, 104);
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //테이블의 셀을 가운데 정렬함
        TableColumnModel tcm = clientTable.getColumnModel(); //정렬할 테이블의 ColumnModel을 가져옴
        
        for (int i = 0; i < tcm.getColumnCount(); i++) { //테이블의 셀을 가운데 정렬함
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 

        p.add(label);
        p.add(backButton);
        p.add(payButton);
        p.add(clientPane);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
    	backButton.addActionListener(new ActionListener() { //돌아가기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //메인 화면으로 이동
        	}
        });
    	
    	payButton.addActionListener(new ActionListener() { //결제하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		int result = JOptionPane.showConfirmDialog(null, "해당 차량으로 결제를 진행하시겠습니까?", "결제 진행", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) { //Yes를 선택할 경우
        			String carNum = clientValue[0]; //결과 테이블에 저장된 차량 번호를 저장하는 변수
        			String won = clientValue[3].replace(" 원", ""); //결과 테이블에 저장된 주차 비용에서 "원"을 제외한 값을 저장하는 변수
        			int pay = Integer.parseInt(won); //추출한 주차 비용 값을 int형으로 변환
        			
        			dispose();
        			new GUIPayMethodChoice(carNum, pay); //결과 테이블에 저장된 차량 번호와 주차 비용과 함께 결제 수단 화면으로 이동
        		}
        	}
        });
    }
}