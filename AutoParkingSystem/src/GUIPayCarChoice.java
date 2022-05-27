import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCarChoice extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

    private JLabel carNumLabel = new JLabel("▶ 결제할 차량번호를 입력하십시오."); //화면에 보여주는 메시지 설정

    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    
    private JButton cancleButton = new JButton("취소"); //취소 버튼
    private JButton checkButton = new JButton("입력"); //입력 버튼
    
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    private int pay; //고객이 지불해야 할 돈을 저장하기 위한 변수

    GUIPayCarChoice(int pay){ //화면 기본 설정
    	this.pay = pay;
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
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
            	new GUIMain(); //메인 화면으로 이동
        	}		        	             
        });
        
        checkButton.addActionListener(new ActionListener() { //입력 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	String[][] clientTableValue = dbc.getTable(); //DB파일에 저장된 고객 테이블의 값을 불러옴
            	int line = 0; //고객 테이블의 행 수를 확인하기 위한 변수 생성
            	
            	while(clientTableValue[line][0] != null) { //차량 번호가 null이 아닐 때까지 반복
            		//고객 테이블에 존재하는 차량 번호 중에 차량 번호 입력 창에서 입력한 값과 동일한 값이 존재한다면
            		if(clientTableValue[line][0].equals(carNumText.getText())) {
            			int diffTime = GUIMain.diffTime(clientTableValue[line][1]); //해당 값을 가진 차량의 주차 시간을 구함
            			
            			dispose();
            			//구한 주차 시간으로 시간당 주차 비용을 구한 뒤, 차량 번호와 고객이 지불해야 할 돈과 함께 결제 수단 화면으로 이동
                    	new GUIPayMethodChoice(clientTableValue[line][0], (diffTime/15 + 1) * (pay/4));
                    	return;
            		}
            		
            		else { //고객 테이블에 저장된 차량 번호 중 차량 번호 입력 창에서 입력한 값과 동일한 값이 존재하지 않는다면 
            			JOptionPane.showMessageDialog(null, "해당 차량번호는 주차장을 이용하지 않은 차량입니다");
            			return;
            		}
            	}
        	}		       	             
        });
    }
}