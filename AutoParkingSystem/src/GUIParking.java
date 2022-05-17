import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

    private JLabel carNumLabel = new JLabel("▶ 주차할 차량 번호를 입력하십시오.");
    private JLabel placeLabel = new JLabel("▶ 주차할 위치 번호를 입력하십시오.");

    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    private JTextField placeText = new JTextField(); //위치 번호 입력 창
    
    private JButton cancleButton = new JButton("취소"); //취소 버튼
    private JButton parkingButton = new JButton("주차"); //주차 버튼

    GUIParking(){ //화면 기본 설정
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

        carNumLabel.setLocation(40, 120);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);
        
        placeLabel.setLocation(40, 360);
        placeLabel.setSize(900, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeLabel.setFont(font);

        carNumText.setLocation(160, 220);
        carNumText.setSize(650, 80);
        carNumText.setFont(font);
        
        placeText.setLocation(160, 470);
        placeText.setSize(650, 80);
        placeText.setFont(font);
        
        cancleButton.setLocation(200, 620);
        cancleButton.setSize(200, 80);
        cancleButton.setFont(font);
        
        parkingButton.setLocation(600, 620);
        parkingButton.setSize(200, 80);
        parkingButton.setFont(font);

        p.add(carNumLabel);
        p.add(placeLabel);
        p.add(carNumText);
        p.add(placeText);
        p.add(cancleButton);
        p.add(parkingButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        cancleButton.addActionListener(new ActionListener() { //취소 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();        	
            	new GUIMain();
        	}		             
        });
        
        parkingButton.addActionListener(new ActionListener() { //주차 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	if(false) { //해당하는 위치 번호가 이미 차 있는 경우, 위치 번호가 없는 경우
                    JOptionPane.showMessageDialog(null, "해당 위치 번호는 이미 차지하고 있는 공간입니다"); 
            	} else { //정상적으로 주차를 완료한 경우
            		//고객 테이블에 해당 차량을 추가시키고 메인화면에 바로 반영한다. 		
                	dispose();
                	new GUIMain();  //메인화면으로
            	}
        	}		        	             
        });
    }
	
	public static void main(String[] args) { //실행 테스트를 위한 코드
		new GUIParking();
	}
}