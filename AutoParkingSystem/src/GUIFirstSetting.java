
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

public class GUIFirstSetting extends JFrame{
	private JPanel p = new JPanel();
    private JButton SettingButton = new JButton("설정"); // 설정 버튼
    Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel placeLabel = new JLabel("▶ 주차 공간 설정");
    private JLabel detailLabel = new JLabel("<html>(최소 가로 1칸/세로 1칸<br/>최대 가로 10칸 세로 15칸 설정 가능)</html>");
    private JLabel widthLabel = new JLabel("가로 :");
    private JLabel heightLabel = new JLabel("세로 :");
    private JLabel payLabel = new JLabel("▶ 시간당 주차 비용 설정");

    private JTextField widthText = new JTextField(); // 가로 입력 창
    private JTextField heightText = new JTextField(); // 세로 입력 창
    private JTextField payText = new JTextField(); // 비용 입력 창

    GUIFirstSetting(){  // 화면 기본 설정
        this.setTitle("첫 설정 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        SettingButton.setLocation(400, 780);
        SettingButton.setSize(200, 100);
        SettingButton.setFont(font);
        
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
    				 if(true) { // 숫자만 쓰여져 있는지 확인
    					 if(true) { // 범위안에 수인지 확인
    						 // 설정 파일에 가로, 세로, 비용 값 입력
    						 dispose(); 
    						 
    						 new GUIMain();
    					 } else
        	                 JOptionPane.showMessageDialog(null, "범위 갑 안의 값을 입력해주세요"); // 값이 범위를 벗어났을 때 실행

    				 } else 
    	                 JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요"); // 숫자 이외의 값이 쓰여 있을 때 실행
    			 }
    		});
        

    }
    
    public static void main(String[] args) {
		new GUIFirstSetting();
	}
}
