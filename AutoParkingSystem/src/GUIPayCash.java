

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUIPayCash extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("결제 취소"); // 결제 취소 버튼
    private JButton payButton = new JButton("결제하기"); //  결제하기 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel cashLabel = new JLabel("▶ 투입구에 현금을 넣어주십시오");

    private JTextField cashText = new JTextField(); // 현금 입력 창

    GUIPayCash(){ // 화면 기본 설정
        this.setTitle("현금 결제 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        cancleButton.setLocation(150, 780);
        cancleButton.setSize(300, 100);
        cancleButton.setFont(font);
        
        payButton.setLocation(550, 780);
        payButton.setSize(300, 100);
        payButton.setFont(font);

        cashLabel.setLocation(50, 300);
        cashLabel.setSize(900, 100);
        cashLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cashLabel.setFont(font);

        cashText.setLocation(45, 410);
        cashText.setSize(900, 100);
        cashText.setFont(font);


        p.add(cancleButton);
        p.add(payButton);
        p.add(cashLabel);
        p.add(cashText);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { // 결제 취소 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayMethodChoice();  // 결제 수단 선택 화면으로
        	}		
            	             
        });
        
        payButton.addActionListener(new ActionListener() { // 결제하기 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(true) { // 올바른 값이 입력되었을 경우
                    int cash = Integer.parseInt(cashText.getText());  // 고객이 입력한 현금 값
                    
                    if(true) { // 현금이 충분한 경우 cash >= 내야하는 돈
                    	if(false) { // 현금이 내야하는 돈보다 많은 경우 cash > 내야하는 돈
                    		JOptionPane.showMessageDialog(null, "거스름돈 1000원을 배출했습니다");  // 거스름돈 계산후 값을 넣는다.
                    	}
                        	
                    	JOptionPane.showMessageDialog(null, "결제가 완료됐습니다");
                    	
                    	// 결제한 차량과 관련된 기록 삭제
                    	
                    	dispose();
                    	
                    	new GUIMain();
                    }else
                    	JOptionPane.showMessageDialog(null, "투입하신 현금이 결제할 주차 비용보다 적습니다");
            	}else 
                	JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요"); // 숫자 외의 값잉 입력되었을 때
        	}            	             
        });

    }
	
	
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIPayCash();
	}
}
