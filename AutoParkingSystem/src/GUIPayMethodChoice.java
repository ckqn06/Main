

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

public class GUIPayMethodChoice extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cashButton = new JButton("현금으로 결제"); // 현금으로 결제 버튼
    private JButton cardButton = new JButton("카드로 결제"); // 카드로 결제 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 40);

    private JLabel feeLabel = new JLabel("▶ 고객님은 총 15000원 만큼"); // 결제해야 할 값을 적용한다.
    private JLabel feeLabel2 = new JLabel("결제해야 합니다");
    private JLabel choiceLabel = new JLabel("원하시는 결제수단을 선택하십시오");

    private JTextField carNumText = new JTextField(); // 차량 번호 입력 창

    GUIPayMethodChoice(){ // 화면 기본 설정
        this.setTitle("결제 수단 선택 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        cashButton.setLocation(150, 730);
        cashButton.setSize(300, 100);
        cashButton.setFont(font);
        
        cardButton.setLocation(570, 730);
        cardButton.setSize(280, 100);
        cardButton.setFont(font);

        feeLabel.setLocation(50, 250);
        feeLabel.setSize(900, 100);
        feeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel.setFont(font.deriveFont(50f));
        
        feeLabel2.setLocation(50, 305);
        feeLabel2.setSize(900, 100);
        feeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        feeLabel2.setFont(font.deriveFont(50f));

        choiceLabel.setLocation(45, 450);
        choiceLabel.setSize(900, 100);
        choiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choiceLabel.setFont(font.deriveFont(50f));


        p.add(cashButton);
        p.add(cardButton);
        p.add(feeLabel);
        p.add(feeLabel2);
        p.add(choiceLabel);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
    	cashButton.addActionListener(new ActionListener() { // 현금으로 결제 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayCash();  // 현금 결제 화면으로
        	}		
            	             
        });
        
    	cardButton.addActionListener(new ActionListener() { // 카드로 결제 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIPayCard();  // 카드 결제 화면으로
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIPayMethodChoice();
	}
}
