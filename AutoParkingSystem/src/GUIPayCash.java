import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCash extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정
	
    private JLabel cashLabel = new JLabel("▶ 투입구에 현금을 넣어주십시오."); //화면에 보여주는 메시지

    private JTextField cashText = new JTextField(); //현금 입력 창
    
    private JButton cancleButton = new JButton("결제 취소"); //결제 취소 버튼
    private JButton payButton = new JButton("결제하기"); //결제하기 버튼
    
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    
    private String carNum; //고객의 차량 번호를 저장하기 위한 변수
    private int pay; //고객이 지불해야 할 돈을 저장하기 위한 변수

    GUIPayCash(String carNum, int pay){ //화면 기본 설정
    	this.carNum = carNum;
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

        cashLabel.setLocation(30, 250);
        cashLabel.setSize(900, 100);
        cashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cashLabel.setFont(font);

        cashText.setLocation(250, 360);
        cashText.setSize(450, 80);
        cashText.setFont(font);
        
        cancleButton.setLocation(140, 620);
        cancleButton.setSize(250, 80);
        cancleButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);

        p.add(cashLabel);
        p.add(cashText);
        p.add(cancleButton);
        p.add(payButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { //결제 취소 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new GUIPayMethodChoice(carNum, pay); //고객의 차량 번호와 고객이 지불해야 할 돈과 함께 결제 수단 선택 화면으로 이동
        	}		
        });
        
        payButton.addActionListener(new ActionListener() { //결제하기 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	try {
            		int cash = Integer.parseInt(cashText.getText()); //현금 입력 창에서 입력받은 값을 정수로 받아옴
            		
                    if(cash >= pay) { //현금(cash)이 내야하는 돈(pay)과 동일하며
                    	if(cash > pay) { //현금(cash)이 내야하는 돈(pay)보다 많은 경우 거스름돈을 배출
                    		JOptionPane.showMessageDialog(null, "거스름돈 " + (cash - pay) + "원을 배출했습니다"); //거스름돈 계산
                    	}
                        	
                    	JOptionPane.showMessageDialog(null, "결제가 완료됐습니다"); //결제가 완료되면 메시지 출력
                    	
                    	dbc.data_delete(carNum); //결제를 완료한 차량 번호와 관련된 데이터를 DB파일에서 제거
                    	dispose();
                    	new GUIMain();
                    } else { //현금(cash)이 내야하는 돈(pay)보다 적은 경우
                    	JOptionPane.showMessageDialog(null, "투입하신 현금이 결제할 주차 비용보다 적습니다");
                    }
            	} catch (Exception e1) { //현금 입력 창에 정수 외의 값이 입력된 경우 예외 발생
            		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
            	} 
            }
        });
    }
}