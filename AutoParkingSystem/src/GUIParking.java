

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

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton cancleButton = new JButton("취소"); // 취소 버튼
    private JButton parkingButton = new JButton("주차"); // 주차 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel carNumLabel = new JLabel("▶ 주차할 차량 번호를 입력하십시오");
    private JLabel placeLabel = new JLabel("▶ 주차할 위치 번호를 입력하십시오");

    private JTextField carNumText = new JTextField(); // 차량 번호 입력 창
    private JTextField placeText = new JTextField(); // 위치 번호 입력 창

    GUIParking(){ // 화면 기본 설정
        this.setTitle("주차하기 화면");
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
        
        parkingButton.setLocation(600, 780);
        parkingButton.setSize(200, 100);
        parkingButton.setFont(font);

        carNumLabel.setLocation(50, 160);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        carNumLabel.setFont(font);
        
        placeLabel.setLocation(50, 410);
        placeLabel.setSize(900, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        carNumText.setLocation(45, 270);
        carNumText.setSize(900, 100);
        carNumText.setFont(font);
        
        placeText.setLocation(45, 520);
        placeText.setSize(900, 100);
        placeText.setFont(font);


        p.add(cancleButton);
        p.add(parkingButton);
        p.add(carNumLabel);
        p.add(placeLabel);
        p.add(carNumText);
        p.add(placeText);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { // 취소 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	
            	new GUIMain();
        	}		
            	             
        });
        
        parkingButton.addActionListener(new ActionListener() { // 주차 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(false) { // 해당하는 위치 번호가 이미 차 있는 경우
                    JOptionPane.showMessageDialog(null, "해당 위치 번호는 이미 차지하고 있는 공간입니다"); 
            	}else { // 정상적으로 주차를 완료한 경우
            		// 고객 테이블에 해당 차량을 추가시키고 메인화면에 바로 반영한다.
            		
                	dispose();
                	
                	new GUIMain();  // 메인화면으로
            	}
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIParking();
	}
}
