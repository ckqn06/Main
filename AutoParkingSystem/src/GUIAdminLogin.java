import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class GUIAdminLogin extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 45); //폰트 설정

    private JLabel logoLabel = new JLabel("무인 주차 관리 시스템"); //화면 로고
    private JLabel IDLabel = new JLabel("관리자 ID :"); //ID 메시지
    private JLabel passwordLabel = new JLabel("비밀번호 :"); //비밀번호 메시지

    private JTextField IDText = new JTextField(); //ID 입력 창
    private JTextField passwordText = new JTextField(); //비밀번호 입력 창
    
    private JButton loginButton = new JButton("로그인"); //로그인 버튼
    private JButton BackButton = new JButton("돌아가기"); // 돌아가기 버튼
    
    private int op; //실행 경로 확인 숫자
    File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일

    GUIAdminLogin(int op){ //화면 기본 설정, op=1(첫 로그인), op=2(관리자 설정 로그인), op=3(종료하기)
    	this.op = op;
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

        if(op != 1) { //첫 로그인이 아니라면 로그인 버튼을 오른쪽으로 이동
        	loginButton.setLocation(600, 620);
            loginButton.setSize(250, 100);
            if(op == 3) //메인 화면에서 시스템 종료 버튼을 클릭한 경우
            	loginButton.setText("종료하기"); //로그인이 아니라 종료하기로 텍스트를 변경
        }else { //첫 로그인이라면 로그인 버튼을 중앙으로 이동
        	loginButton.setLocation(400, 620); 
            loginButton.setSize(200, 100);
        }
        loginButton.setFont(font);
        
        BackButton.setLocation(160, 620);
        BackButton.setSize(250, 100);
        BackButton.setFont(font);

        p.add(logoLabel);
        p.add(IDLabel);
        p.add(passwordLabel);
        p.add(IDText);
        p.add(passwordText);
        p.add(loginButton);
        
        if(op != 1) //첫 로그인이 아니라면 돌아가기 버튼 생성
        	p.add(BackButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        loginButton.addActionListener(new ActionListener() { //로그인 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	//ID와 비밀번호가 맞다면
            	if(IDText.getText().equals("admin") && passwordText.getText().equals("park123")) {
            		if(op == 1) { //만약 첫 로그인이라면
            			if(f.exists()) {
                			dispose();
                			new GUIMain();
                        } else {
                        	JOptionPane.showMessageDialog(null, "현재 시스템에 관리자 데이터 파일이 존재하지 않습니다.");
                        	dispose();
                        	new GUIAdminSetting();
                        }
            		}else if (op == 2) { //관리자 설정 로그인이라면
            			dispose();
            			new GUIAdminSetting();
            		}else{ //종료하기 화면이라면
                		int result = JOptionPane.showConfirmDialog(null, "정말로 시스템을 종료하시겠습니까?", "시스템 종료", JOptionPane.YES_NO_OPTION);
                		if(result == JOptionPane.YES_OPTION)  //Yes를 선택할 경우
                			System.exit(0);
                	}
            			
                    
            	} else { //ID와 비밀번호가 틀리다면
            		JOptionPane.showMessageDialog(null, "관리자 ID 혹은 비밀번호가 틀렸습니다");
            	}
        	}		 	             
        });
        
        BackButton.addActionListener(new ActionListener() { //돌아가기 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new GUIMain();
        	}		    	             
        });
    }
    
    public static void main(String[] args) { //실행 테스트를 위한 코드
		new GUIAdminLogin(1); //처음 로그인 화면으로 실행
	}
}