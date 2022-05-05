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

public class GUIFirst extends JFrame{
	private JPanel p = new JPanel();
    private JButton loginButton = new JButton("로그인"); // 로그인 버튼
    Font font = new Font("고딕", Font.BOLD, 50);

    private JLabel IDLabel = new JLabel("관리자 ID :");
    private JLabel passwordLabel = new JLabel("비밀번호 :");
    private JLabel logoLabel = new JLabel("무인 주차 관리 시스템");

    private JTextField IDText = new JTextField(); // ID 입력 창
    private JTextField passwordText = new JTextField(); // 비밀번호 입력 창

    GUIFirst(){ // 화면 기본 설정
        this.setTitle("첫 화면");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {  // 각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        loginButton.setLocation(400, 780);
        loginButton.setSize(200, 100);
        loginButton.setFont(font);
        
        logoLabel.setLocation(100, 160);
        logoLabel.setSize(800, 100);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setFont(font.deriveFont(70f));

        IDLabel.setLocation(0, 380);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(font);

        passwordLabel.setLocation(0, 550);
        passwordLabel.setSize(400, 100);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(font);

        IDText.setSize(400, 100);
        IDText.setLocation(450, 380);
        IDText.setFont(font);

        passwordText.setSize(400, 100);
        passwordText.setLocation(450, 550);
        passwordText.setFont(font);


        p.add(loginButton);
        p.add(logoLabel);
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
    }

    private void eventListner() { // 버튼 클릭 이벤트 설정
        loginButton.addActionListener(new ActionListener() { // 로그인 버튼 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(IDText.getText().equals("admin")  // ID와 비밀번호가 맞으면 실행
            			&& passwordText.getText().equals("park123")) {
            		dispose();
            		
            		if(false) // 관리자 설정 파일이 있으면 실행
            			new GUIMain();
            		else { // 관리자 설정 파일이 없으면 실행
            			JOptionPane.showMessageDialog(null, "현재 시스템에 관리자 데이터 파일이 존재하지 않습니다");

        				new GUIFirstSetting();
            		}
            	}else 
                    JOptionPane.showMessageDialog(null, "관리자 ID 혹은 비밀번호가 틀렸습니다"); // ID와 비밀번호가 틀리면 실행
        	}		
            	             
        });

    }
	
	
	public static void main(String[] args) {
		new GUIFirst();
	}
}
