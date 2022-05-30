import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayMethodChoice extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 50); //폰트 설정

	//화면에 보여주는 메시지
    private JLabel feeLabel = new JLabel();
    private JLabel feeLabel2 = new JLabel("결제해야 합니다.");
    private JLabel choiceLabel = new JLabel("원하시는 결제 수단을 선택하십시오.");

    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    
    private JButton cashButton = new JButton("현금으로 결제"); //현금으로 결제 버튼
    private JButton cardButton = new JButton("카드로 결제"); //카드로 결제 버튼
    
    private String carNum; //고객의 차량 번호를 저장하기 위한 변수
    private int pay; //고객이 지불해야 할 돈을 저장하기 위한 변수

    GUIPayMethodChoice(String carNum, int pay){ //화면 기본 설정
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

        feeLabel.setText("▶ 고객님은 총 " + pay + "원 만큼"); //고객이 지불해야 할 돈을 표시함
        feeLabel.setLocation(50, 200);
        feeLabel.setSize(900, 100);
        feeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel.setFont(font);
        
        feeLabel2.setLocation(50, 275);
        feeLabel2.setSize(900, 100);
        feeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel2.setFont(font);

        choiceLabel.setLocation(45, 430);
        choiceLabel.setSize(900, 100);
        choiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choiceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
        
        cashButton.setLocation(130, 620);
        cashButton.setSize(300, 80);
        cashButton.setFont(font.deriveFont(40f));
        
        cardButton.setLocation(570, 620);
        cardButton.setSize(280, 80);
        cardButton.setFont(font.deriveFont(40f));

        p.add(feeLabel);
        p.add(feeLabel2);
        p.add(choiceLabel);
        p.add(cashButton);
        p.add(cardButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
    	cashButton.addActionListener(new ActionListener() { //현금으로 결제 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	//고객의 차량 번호와 고객이 지불해야 할 돈과 함께 현금으로 결제 화면으로 이동
            	new GUIPayCash(carNum, pay);
        	}		             
        });
        
    	cardButton.addActionListener(new ActionListener() { //카드로 결제 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	//고객의 차량 번호와 고객이 지불해야 할 돈과 함께 카드로 결제 화면으로 이동
            	new GUIPayCard(carNum, pay);
        	}			             
        });
    }
}