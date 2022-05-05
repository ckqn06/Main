

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

public class GUIPayCard extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("결제 취소"); // 결제 취소 버튼
    private JButton payButton = new JButton("결제하기"); //  결제하기 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel cardLabel = new JLabel("▶ 결제하실 카드 번호를 입력하십시오");

    private JTextField cardText = new JTextField(); // 카드 번호 입력 창

    GUIPayCard(){ // 화면 기본 설정
        this.setTitle("카드 결제 화면");
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

        cardLabel.setLocation(50, 300);
        cardLabel.setSize(900, 100);
        cardLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cardLabel.setFont(font.deriveFont(48f));

        cardText.setLocation(45, 410);
        cardText.setSize(900, 100);
        cardText.setFont(font);


        p.add(cancleButton);
        p.add(payButton);
        p.add(cardLabel);
        p.add(cardText);
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
            	if(true){ // 올바른 카드 번호일 경우
            		JOptionPane.showMessageDialog(null, "결제가 완료됐습니다");
                	
                	// 결제한 차량과 관련된 기록 삭제
                	
                	dispose();
                	
                	new GUIMain();  // 메인화면으로
            	}else
            		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요"); // 숫자 외의 값잉 입력되었을 때
        	}            	             
        });

    }
	
	
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIPayCard();
	}
}
