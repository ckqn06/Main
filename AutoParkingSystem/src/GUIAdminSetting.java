
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

public class GUIAdminSetting extends JFrame{
	private JPanel p = new JPanel();
	
    private JButton SettingButton = new JButton("설정 변경"); // 설정 버튼
    private JButton goMainButton = new JButton("메인화면으로"); // 메인화면으로 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel placeLabel = new JLabel("▶ 주차 공간 설정");
    private JLabel detailLabel = new JLabel("<html>(최소 가로 1칸/세로 1칸<br/>최대 가로 10칸 세로 15칸 설정 가능)</html>");
    private JLabel widthLabel = new JLabel("가로 :");
    private JLabel heightLabel = new JLabel("세로 :");
    private JLabel payLabel = new JLabel("▶ 시간당 주차 비용 설정");

    private JTextField widthText = new JTextField(); // 가로 입력 창
    private JTextField heightText = new JTextField(); // 세로 입력 창
    private JTextField payText = new JTextField(); // 비용 입력 창

    GUIAdminSetting(){  // 화면 기본 설정
        this.setTitle("관리자 설정 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        SettingButton.setLocation(240, 780);
        SettingButton.setSize(280, 100);
        SettingButton.setFont(font);
        
        goMainButton.setLocation(550, 780);
        goMainButton.setSize(400, 100);
        goMainButton.setFont(font);
        
        placeLabel.setLocation(100, 70);
        placeLabel.setSize(600, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        detailLabel.setLocation(100, 100);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("고딕", Font.PLAIN, 40));
        
        widthLabel.setLocation(120, 280);
        widthLabel.setSize(500, 100);
        widthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        widthLabel.setFont(font);
        
        heightLabel.setLocation(120, 410);
        heightLabel.setSize(500, 100);
        heightLabel.setHorizontalAlignment(SwingConstants.LEFT);
        heightLabel.setFont(font);
        
        payLabel.setLocation(100, 540);
        payLabel.setSize(800, 100);
        payLabel.setHorizontalAlignment(SwingConstants.LEFT);
        payLabel.setFont(font);

        widthText.setLocation(300, 280);
        widthText.setSize(400, 100);
        widthText.setFont(font);

        heightText.setLocation(300, 410);
        heightText.setSize(400, 100);
        heightText.setFont(font);
        
        payText.setLocation(110, 640);
        payText.setSize(400, 100);
        payText.setFont(font);


        p.add(SettingButton);
        p.add(goMainButton);
        p.add(placeLabel);
        p.add(detailLabel);
        p.add(widthLabel);
        p.add(heightLabel);
        p.add(payLabel);
        p.add(widthText);
        p.add(heightText);
        p.add(payText);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
    		SettingButton.addActionListener(new ActionListener() { // 설정 버튼 클릭 시 실행
    			 public void actionPerformed(ActionEvent e) {
    				 if(false) { // 현재 주차장에 차가 주차되어있으면 실행
    					 JOptionPane.showMessageDialog(null, "현재 고객이 존재하여 설정 변경이 불가능합니다");
    				 }
    				 
    				 if(true) { // 숫자만 쓰여져 있는지 확인
    					 if(true) { // 범위 안의 수인지 확인
    						 // 설정 파일에 가로, 세로, 비용 값 입력
    						 // 메인화면에 반영
        	                 JOptionPane.showMessageDialog(null, "변경하신 설정 값이 정상적으로 적용됐습니다"); // 값이 범위를 벗어났을 때 실행
    						 
    						 dispose(); 
    						 
    						 new GUIMain();  // 메인화면으로
    					 } else
        	                 JOptionPane.showMessageDialog(null, "범위 갑 안의 값을 입력해주세요"); // 값이 범위를 벗어났을 때 실행

    				 } else 
    	                 JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요"); // 숫자 이외의 값이 쓰여 있을 때 실행
    			 }
    		});
    		
    		goMainButton.addActionListener(new ActionListener() { // 메인화면으로 버튼 클릭 시 실행
   			 public void actionPerformed(ActionEvent e) {
   				 dispose();
   				 
   				 new GUIMain(); //메인화면으로
   			 }
   		});
        

    }
    
    public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIAdminSetting();
	}
}
