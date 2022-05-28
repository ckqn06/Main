import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정
    
    private JLabel label = new JLabel("▶ 검색하신 차량의 정보입니다."); //화면에 보여주는 메시지
    
    private JButton Button = new JButton("확인"); //확인 버튼
    
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
        
        label.setLocation(30, 150);
        label.setSize(900, 100);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        
        Button.setLocation(370, 620);
        Button.setSize(200, 80);
        Button.setFont(font);
        
        //고객 테이블 내부 데이터를 다루기 위해서 DefaultTableModel을 불러옴
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        clientModel.addRow(clientValue); //테이블에 검색된 결과 값 추가
        
        clientTable.setRowHeight(55);
        clientTable.setFont(font.deriveFont(35f));
        clientTable.getTableHeader().setFont(font);
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(45, 300);
        clientPane.setSize(900, 116);
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //테이블의 셀을 가운데 정렬함
        TableColumnModel tcm = clientTable.getColumnModel(); //정렬할 테이블의 ColumnModel을 가져옴
        
        for (int i = 0; i < tcm.getColumnCount(); i++) { //테이블의 셀을 가운데 정렬함
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 

        p.add(label);
        p.add(Button);
        p.add(clientPane);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        Button.addActionListener(new ActionListener() { //확인 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //메인 화면으로 이동
        	}
        });
    }
}