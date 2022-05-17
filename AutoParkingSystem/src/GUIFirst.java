import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class GUIFirst extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 45); //폰트 설정

    private JLabel logoLabel = new JLabel("무인 주차 관리 시스템"); //화면 로고
    private JLabel IDLabel = new JLabel("관리자 ID :"); //ID 메시지
    private JLabel passwordLabel = new JLabel("비밀번호 :"); //비밀번호 메시지

    private JTextField IDText = new JTextField(); //ID 입력 창
    private JTextField passwordText = new JTextField(); //비밀번호 입력 창
    
    private JButton loginButton = new JButton("로그인"); //로그인 버튼

    File f = new File("C://Server/관리자 데이터 파일.txt");

    GUIFirst(){ //화면 기본 설정
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

        logoLabel.setLocation(100, 100);
        logoLabel.setSize(800, 100);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setFont(font.deriveFont(70f));
        
        IDLabel.setLocation(0, 300);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(font);

        passwordLabel.setLocation(0, 470);
        passwordLabel.setSize(400, 100);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(font);
        
        IDText.setSize(400, 100);
        IDText.setLocation(450, 300);
        IDText.setFont(font);

        passwordText.setSize(400, 100);
        passwordText.setLocation(450, 470);
        passwordText.setFont(font);
        
        loginButton.setLocation(400, 620);
        loginButton.setSize(200, 100);
        loginButton.setFont(font);

        p.add(logoLabel);
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
        p.add(loginButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        loginButton.addActionListener(new ActionListener() { //로그인 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	//ID와 비밀번호가 맞다면
            	if(IDText.getText().equals("admin") && passwordText.getText().equals("park123")) {
            		if(f.exists()) {
            			dispose();
            			new GUIMain();
                    } else {
                    	JOptionPane.showMessageDialog(null, "현재 시스템에 관리자 데이터 파일이 존재하지 않습니다.");
                    	dispose();
                    	new GUIAdminSetting();
                    }
            	} else { //ID와 비밀번호가 틀리다면
            		JOptionPane.showMessageDialog(null, "관리자 ID 혹은 비밀번호가 틀렸습니다");
            	}
        	}		 	             
        });
    }
    
	public static void main(String[] args) { // 실행 테스트를 위한 코드
		new GUIFirst();
	}
}