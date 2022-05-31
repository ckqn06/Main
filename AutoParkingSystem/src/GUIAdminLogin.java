import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIAdminLogin extends JFrame{
	private JPanel p = new JPanel(); //보조 프레임 설정
	private Font font = new Font("맑은 고딕", Font.BOLD, 45); //폰트 설정

    private JLabel logoLabel = new JLabel("무인 주차 관리 시스템"); //화면 로고
    private JLabel IDLabel = new JLabel("관리자 ID :"); //ID 메시지
    private JLabel passwordLabel = new JLabel("비밀번호 :"); //비밀번호 메시지

    private JTextField IDText = new JTextField(); //ID 입력 창
    private JTextField passwordText = new JTextField(); //비밀번호 입력 창
    
    private JButton loginButton = new JButton("로그인"); //로그인 버튼
    private JButton BackButton = new JButton("돌아가기"); //돌아가기 버튼
    
    private int op; //실행 경로 확인을 위한 변수
    private String ID, PW; //ID, 비밀번호 확인을 위한 변수
    
    private ServerConnection sct = new ServerConnection(); //서버 연결 객체

    GUIAdminLogin(int op){ //화면 기본 설정, op=1(첫 로그인), op=2(관리자 설정 로그인), op=3(종료하기)
    	this.op = op;
        this.setTitle("무인 주차 관리 시스템"); //창 타이틀 바 설정
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼을 눌러 화면 종료
        this.formDesign(); //각 GUI 객체 설정
        this.eventListner(); //이벤트를 처리함
        this.setSize(1000,800); //화면 크기 설정
        this.setVisible(true); //창을 화면에 보여줄 것임을 설정
        setLocationRelativeTo(null); //창을 화면 가운데에 배치
    }

    private void formDesign() { //각 GUI 객체 설정
    	try {
    		if(sct.isSetting()) { //관리자 데이터 파일에서 텍스트를 읽어들임
            	String[] settingData = sct.getSetting();
            	
            	ID = settingData[3]; //배열의 4번째에 저장된 ID의 내용을 저장하기 위한 변수
            	PW = settingData[4]; //배열의 5번째에 저장된 비밀번호의 내용을 저장하기 위한 변수
    		}
        } catch(Exception e) { //예외 처리
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p); //보조 프레임 생성
        p.setLayout(null); //보조 프레임의 레이아웃 설정
        
        logoLabel.setLocation(100, 100); //위치 설정
        logoLabel.setSize(800, 100); //크기 설정
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER); //정렬 위치
        logoLabel.setFont(font.deriveFont(70f)); //텍스트 폰트 설정
        
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

        if(op != 1) { //첫 로그인이 아니라면 로그인 버튼을 오른쪽으로 이동 (= 돌아가기 버튼이 생성됨)
        	loginButton.setLocation(600, 620);
            loginButton.setSize(250, 100);
            
            if(op == 3) //메인 화면에서 시스템 종료 버튼을 클릭한 경우
            	loginButton.setText("종료하기"); //로그인 버튼의 텍스트를 "로그인"에서 "종료하기"로 변경
        
        }else { //첫 로그인이라면 로그인 버튼을 왼쪽으로 이동 (= 돌아가기 버튼이 없어짐)
        	loginButton.setLocation(400, 620); 
            loginButton.setSize(200, 100);
        }
        loginButton.setFont(font);
        
        BackButton.setLocation(160, 620);
        BackButton.setSize(250, 100);
        BackButton.setFont(font);

        p.add(logoLabel); //화면 로고를 보조 프레임에 추가함
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
            	
            	if(sct.isSetting()) { //관리자 데이터 파일이 지정 경로에 존재하면서
            		//관리자 데이터 파일에 설정된 아이디와 비밀번호가 ID/비밀번호 입력 창에 입력한 값과 동일하다면
            		if(IDText.getText().equals(ID) && passwordText.getText().equals(PW)) {
            			if(op == 1) { //시스템을 실행시켜 첫 로그인을 진행하는 경우
            				dispose(); //현재 보여주고 있는 창(프레임)을 닫음
                			new GUIMain(); //메인 화면으로 이동
                		} else if(op == 2) { //관리자 설정 버튼을 통해 온 경우
                			dispose();
                			new GUIAdmin(); //관리자 설정 화면으로 이동
                		} else { //종료하기 버튼을 통해 온 경우
                			int result = JOptionPane.showConfirmDialog(null, "정말로 시스템을 종료하시겠습니까?", "시스템 종료", JOptionPane.YES_NO_OPTION);
                    		if(result == JOptionPane.YES_OPTION) //Yes를 선택할 경우
                    			System.exit(0); //시스템을 종료시킴
                		}
            		} else { //ID 또는 비밀번호가 틀리다면
            			JOptionPane.showMessageDialog(null, "관리자 ID 혹은 비밀번호가 틀렸습니다");
            		}
            		
            	} else { //관리자 데이터 파일이 지정 경로에 존재하지 않으면서
            		if(IDText.getText().equals("admin") && passwordText.getText().equals("park123")) { //Default ID와 비밀번호가 맞다면
            			if(op == 1) {
                			JOptionPane.showMessageDialog(null, "현재 시스템에 관리자 데이터 파일이 존재하지 않습니다.");
                        	dispose();
                        	new GUIAdminSetting(); //관리자 데이터 파일 값 변경 화면으로 이동
                		} else if(op == 2) {
                			dispose();
                			new GUIAdmin();
                		} else { 
                			int result = JOptionPane.showConfirmDialog(null, "정말로 시스템을 종료하시겠습니까?", "시스템 종료", JOptionPane.YES_NO_OPTION);
                    		if(result == JOptionPane.YES_OPTION)
                    			System.exit(0);
                		}
            		} else {
            			JOptionPane.showMessageDialog(null, "관리자 ID 혹은 비밀번호가 틀렸습니다");
            		}
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
    
    public static void main(String[] args) {
		new GUIAdminLogin(1); //시스템을 실행시킬 시, 첫 로그인 화면으로 실행
	}
}