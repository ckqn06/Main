

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

public class GUIPayCarChoice extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("취소"); // 취소 버튼
    private JButton checkButton = new JButton("입력"); // 입력 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel carNumLabel = new JLabel("▶ 결제할 차량번호를 입력하십시오");

    private JTextField carNumText = new JTextField(); // 차량 번호 입력 창

    GUIPayCarChoice(){ // 화면 기본 설정
        this.setTitle("결제 차량 선택 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        cancleButton.setLocation(200, 780);
        cancleButton.setSize(200, 100);
        cancleButton.setFont(font);
        
        checkButton.setLocation(600, 780);
        checkButton.setSize(200, 100);
        checkButton.setFont(font);

        carNumLabel.setLocation(50, 300);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        carNumLabel.setFont(font);

        carNumText.setLocation(45, 410);
        carNumText.setSize(900, 100);
        carNumText.setFont(font);


        p.add(cancleButton);
        p.add(checkButton);
        p.add(carNumLabel);
        p.add(carNumText);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { // 취소 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIMain();  // 메인화면으로
        	}		
            	             
        });
        
        checkButton.addActionListener(new ActionListener() { // 입력 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(false) { // 해당하는 차량번호가 없을 경우
                    JOptionPane.showMessageDialog(null, "해당 차량번호는 주차장을 이용하지 않는 차량입니다"); 
            	}else {
                	dispose();
                	
                	new GUIPayMethodChoice();  // 결제 수단 선택 화면으로
            	}
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIPayCarChoice();
	}
}
