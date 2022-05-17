import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUISearch extends JFrame {
    private JPanel p = new JPanel();
    private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정
    
    private JLabel label = new JLabel("▶ 검색하신 차량의 정보입니다.");
    
    private JButton Button = new JButton("확인"); //확인 버튼
    
    private String[] header = {"차량번호", "주차 시간", "현재 위치", "비용"}; //고객 테이블 헤더
    private String[][] rows = {};
    private JTable clientTable = new JTable(rows, header); //고객 정보 테이블

    private JScrollPane clientPane = new JScrollPane(clientTable);

    GUISearch(){
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
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
        
        clientPane.setLocation(250, 320);
        clientPane.setSize(450, 200);

        p.add(label);
        p.add(Button);
        p.add(clientPane);
    }

    private void eventListner() {
        Button.addActionListener(new ActionListener() { //주차하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain();  //메인 화면으로
        	}
        });
    }

    public static void main(String[] args) { //실행 테스트를 위한 코드
        new GUISearch();
    }
}