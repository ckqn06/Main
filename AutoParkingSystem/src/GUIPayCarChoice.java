import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCarChoice extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

    private JLabel carNumLabel = new JLabel("▶ 결제할 차량번호를 입력하십시오.");

    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    
    private JButton cancleButton = new JButton("취소"); //취소 버튼
    private JButton checkButton = new JButton("입력"); //입력 버튼
    
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    
    private int pay;

    GUIPayCarChoice(int pay){ //화면 기본 설정
    	this.pay = pay;
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
    }

    private void formDesign() { //각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        carNumLabel.setLocation(20, 250);
        carNumLabel.setSize(920, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);

        carNumText.setLocation(160, 360);
        carNumText.setSize(660, 80);
        carNumText.setFont(font);

        cancleButton.setLocation(190, 620);
        cancleButton.setSize(200, 80);
        cancleButton.setFont(font);
        
        checkButton.setLocation(600, 620);
        checkButton.setSize(200, 80);
        checkButton.setFont(font);

        p.add(carNumLabel);
        p.add(carNumText);
        p.add(cancleButton);
        p.add(checkButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { //취소 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();  	
            	new GUIMain(); //메인화면으로
        	}		        	             
        });
        
        checkButton.addActionListener(new ActionListener() { //입력 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	String[][] clientTableValue = dbc.getTable();
            	int i = 0;
            	while(clientTableValue[i][0] != null) {
            		if(clientTableValue[i][0].equals(carNumText.getText())) { //차량이 존재할 경우
            			int diffTime = GUIMain.diffTime(clientTableValue[i][1]);
            			dispose();
                    	new GUIPayMethodChoice(clientTableValue[i][0], (diffTime/15 + 1) * (pay/4)); //결제 수단 선택 화면으로
                    	return;
            		}
            	}
            	JOptionPane.showMessageDialog(null, "해당 차량번호는 주차장을 이용하지 않은 차량입니다"); //해당하는 차량번호가 없는 경우
        	}		       	             
        });
    }
	
	public static void main(String[] args) { //실행 테스트를 위한 코드
		new GUIPayCarChoice(10000);
	}
}