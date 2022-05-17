import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPayCard extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

    private JLabel cardLabel = new JLabel("▶ 결제하실 카드 번호를 입력하십시오.");

    private JTextField cardText = new JTextField(); //카드 번호 입력 창
    
    private JButton cancleButton = new JButton("결제 취소"); //결제 취소 버튼
    private JButton payButton = new JButton("결제하기"); //결제하기 버튼

    GUIPayCard(){ //화면 기본 설정
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

        cardLabel.setLocation(30, 250);
        cardLabel.setSize(900, 100);
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setFont(font);

        cardText.setLocation(150, 360);
        cardText.setSize(670, 80);
        cardText.setFont(font);

        cancleButton.setLocation(140, 620);
        cancleButton.setSize(250, 80);
        cancleButton.setFont(font);
        
        payButton.setLocation(580, 620);
        payButton.setSize(250, 80);
        payButton.setFont(font);
        
        p.add(cardLabel);
        p.add(cardText);
        p.add(cancleButton);
        p.add(payButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { //결제 취소 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new GUIPayMethodChoice(); //결제 수단 선택 화면으로
        	}		  	             
        });
        
        payButton.addActionListener(new ActionListener() { //결제하기 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	try { //올바른 카드 번호일 경우
            		int card_num = Integer.parseInt(cardText.getText());
            		
            		JOptionPane.showMessageDialog(null, "결제가 완료됐습니다");
            		
                	//결제한 차량과 관련된 기록 삭제
                	dispose();
                	new GUIMain(); //메인화면으로
            	} catch (Exception e1) { //숫자 외의 값이 입력되었을 때
            		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
            	}
            }
        });
    }
	
	public static void main(String[] args) { //실행 테스트를 위한 코드
		new GUIPayCard();
	}
}