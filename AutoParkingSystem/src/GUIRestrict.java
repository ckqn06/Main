import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIRestrict extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

	//화면에 보여주는 메시지 설정
	private JLabel TopLabel = new JLabel("▶ 이곳은 특수 주차 공간을 설정하는 화면입니다.");
	private JLabel detailLabel = new JLabel("<html>(설정할 주차 공간이 2개 이상인 경우 콤마(,)로 구분해주세요)</html>");
    private JLabel handicapLabel = new JLabel("장애인 전용 공간 :");
    private JLabel noParkLabel = new JLabel("주차 불가 공간 :");

    private JTextField handicapText = new JTextField(); //장애인 전용 공간 입력 창
    private JTextField noParkText = new JTextField(); //주차 불가 공간 입력 창
    
    private JButton AddButton = new JButton("추가"); //추가 버튼
    private JButton DeleteButton = new JButton("제거"); //제거 버튼
    private JButton CancleButton = new JButton("취소"); //취소 버튼
    
    private String width, height, pay, ID, PW; //가로, 세로, 관리자 ID, 비밀번호를 저장하는 변수
    private String[] currentHandi, currentNoPark; //관리자 데이터 파일에 저장된 장애인 전용/주차 불가 공간 값을 저장하는 변수
    
    private ServerConnection sct = new ServerConnection(); //서버 연결 객체

    GUIRestrict(){ //화면 기본 설정
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() { //각 GUI 객체 설정
    	try {
    		if(sct.isSetting()) { //관리자 데이터 파일에서 텍스트를 읽어들임
    			String[] settingData = sct.getSetting(); //관리자 데이터 파일에서 값을 읽어들임
            	
            	//배열의 n번째에 저장된 가로, 세로, 시간당 주차 비용, 관리자 ID/PW, 장애인 전용/주차 불가 공간 번호의 값을 저장하기 위한 변수
            	width = settingData[0];
            	height = settingData[1];
            	pay = settingData[2];
            	ID = settingData[3];
            	PW = settingData[4];
            	//저장된 값을 split() 메서드를 이용해 ","를 기준으로 문자열을 나눈 뒤, 추출한 값을 출력
            	currentHandi = settingData[5].split(",");
            	currentNoPark = settingData[6].split(",");
    		}
        } catch(Exception e) { //예외 처리
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p);
        p.setLayout(null);

        TopLabel.setLocation(30, 120);
        TopLabel.setSize(900, 100);
        TopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TopLabel.setFont(font);
        
        detailLabel.setLocation(95, 125);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("고딕", Font.PLAIN, 30));
        
        handicapLabel.setLocation(50, 290);
        handicapLabel.setSize(400, 100);
        handicapLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        handicapLabel.setFont(font);

        noParkLabel.setLocation(50, 460);
        noParkLabel.setSize(400, 100);
        noParkLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        noParkLabel.setFont(font);
        
        handicapText.setSize(350, 100);
        handicapText.setLocation(470, 290);
        handicapText.setFont(font);

        noParkText.setSize(350, 100);
        noParkText.setLocation(470, 460);
        noParkText.setFont(font);
        
        AddButton.setLocation(650, 630);
        AddButton.setSize(160, 80);
        AddButton.setFont(font);
        
        DeleteButton.setLocation(400, 630);
        DeleteButton.setSize(160, 80);
        DeleteButton.setFont(font);
        
        CancleButton.setLocation(150, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel);
        p.add(detailLabel);
        p.add(handicapLabel);
        p.add(noParkLabel);
        p.add(handicapText);
        p.add(noParkText);
        p.add(AddButton);
        p.add(DeleteButton);
        p.add(CancleButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정    	
    	AddButton.addActionListener(new ActionListener() { //설정 버튼 클릭 시 실행
    		public void actionPerformed(ActionEvent e) {
    			try {
                	String[][] clientTableValue = sct.getTableData(); //서버를 통해 DB파일 내의 고객 테이블을 가져옴
                	String[] handicaps = handicapText.getText().split(","); //장애인 전용 공간 입력 창에 입력한 값을 저장하기 위한 변수
                	String[] noParks = noParkText.getText().split(","); //주차 불가 공간 입력 창에 입력한 값을 저장하기 위한 변수
                	int settingOp; //현재 입력 창에 입력된 값의 유형을 저장하는 변수
                	
                	//checkString 메소드에서 장애인 전용 주차 공간, 주차 불가 공간 입력 창에 둘 다 공백이 입력됐다면
                	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
                		JOptionPane.showMessageDialog(null, "값을 입력해주세요");
                		return;
                	}
                	
                	settingOp = checkSetting(handicaps, noParks); //현재 입력 창에 입력된 값이 어떤 유형인지 파악
                	
                	if(settingOp == -1) { //입력된 값이 올바르지 않은 값이라면
                		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
                		return;
                	}
      
                	String[] settingData = {}; //관리자 데이터 파일에 변경된 값을 저장하기 위한 배열 생성
                	
                	if(settingOp == 1) { //장애인 전용/주차 불가 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 장애인 전용/주차 불가 공간 값 중에서 공통된 값이 존재하는 경우
                		for(int i = 0; i < handicaps.length; i++) //장애인 전용 공간 입력 창에 입력한 값과
                			for(int j = 0; j < currentHandi.length; j++) { //관리자 데이터 파일에 저장된 장애인 전용 공간 값을 읽어들임
                				if(handicaps[i].equals(currentHandi[j])) { //읽어들인 각각의 값 중에서 공통된 값이 존재하는 경우
                					JOptionPane.showMessageDialog(null, "이미 장애인 전용 공간으로 설정된 값이 있습니다");
                					return; //해당 값을 반환
                				}
                			}
                		for(int i = 0; i < noParks.length; i++) //주차 불가 공간 입력 창에 입력한 값과
                			for(int j = 0; j < currentNoPark.length; j++) { //관리자 데이터 파일에 저장된 주차 불가 공간 값을 읽어들임
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "이미 주차 불가 공간으로 설정된 값이 있습니다");
                					return;
                				}
                			}
        				//관리자 데이터 파일에 변경된 장애인 전용/주차 불가 공간의 값을 저장시킴
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi)+","+String.join(",", handicaps),
                				String.join(",", currentNoPark)+","+String.join(",", noParks)};
        			
                	}else if(settingOp == 2) { //장애인 전용 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 장애인 전용 공간 값 중에서 공통된 값이 존재하는 경우
                		for(int i = 0; i < handicaps.length; i++)
                			for(int j = 0; j < currentHandi.length; j++) {
                				if(handicaps[i].equals(currentHandi[j])) {
                					JOptionPane.showMessageDialog(null, "이미 장애인 전용 공간으로 설정된 값이 있습니다");
                					return;
                				}
                			}
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi)+","+String.join(",", handicaps),
                				String.join(",", currentNoPark)};
                	
                	}else if(settingOp == 3) { //주차 불가 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 주차 불가 공간 값 중에서 공통된 값이 존재하는 경우
                		for(int i = 0; i < noParks.length; i++)
                			for(int j = 0; j < currentNoPark.length; j++) {
                				if(noParks[i].equals(currentNoPark[j])) {
                					JOptionPane.showMessageDialog(null, "이미 주차 불가 공간으로 설정된 값이 있습니다");
                					return;
                				}
                			}
                		settingData = new String[]{width, height, pay, ID, PW, String.join(",", currentHandi), String.join(",", currentNoPark)
                				+"," + ""+String.join(",", noParks)};
                	}
                	sct.setSetting(settingData); //서버와 연결하여 관리자 데이터 파일에 변경된 값을 설정
        			
        			JOptionPane.showMessageDialog(null, "설정하신 특수 주차 공간이 정상적으로 적용됐습니다");
        			dispose(); 
        			new GUIMain(); //메인 화면으로 이동
    			} catch(Exception e1) { //예외 처리
    				System.out.println(e1.getMessage());
    	        	e1.printStackTrace();
    			}
    		}
    	});
    	
    	DeleteButton.addActionListener(new ActionListener() { //제거 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
            	String[] handicaps = handicapText.getText().split(",");
            	String[] noParks = noParkText.getText().split(",");
            	int settingOp;
            	
            	if(handicapText.getText().equals("") && noParkText.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "값을 입력해주세요");
            		return;
            	}
            	
            	settingOp = checkSetting(handicaps, noParks);
            	
            	if(settingOp == -1) {
            		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
            		return;
            	}
            	
            	String[] settingData = {};
            	
            	if(settingOp == 1) { //장애인 전용/주차 불가 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 장애인 전용/주차 불가 공간 값 중에서 공통된 값이 존재하는 경우
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false; //장애인 전용 공간 입력 창에 입력한 값이 주차 공간 테이블에 이미 설정되었던 값인지 확인하는 불리언
            			
            			for(int j = 0; j < currentHandi.length; j++) {
            				if(handicaps[i].equals(currentHandi[j])) {
            					isExist = true; //장애인 전용 공간이 존재함을 확인
            				}
            			}
            			if(!isExist) { //해당 공간이 존재하지 않는 경우 제거를 하지 못함
        					JOptionPane.showMessageDialog(null, "장애인 전용 주차 공간으로 설정되어 있지 않은 값이 있습니다");
        					return;
        				}
            		}
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false; //주차 불가 공간 입력 창에 입력한 값이 주차 공간 테이블에 이미 설정되었던 값인지 확인하는 불리언
            			
            			for(int j = 0; j < currentNoPark.length; j++) {
            				if(noParks[i].equals(currentNoPark[j])) {
            					isExist = true; //주차 불가 공간이 존재함을 확인
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "주차 불가 공간으로 설정되어 있지 않은 값이 있습니다");
        					return;
        				}
            		}
            		
            		String finalHandicap = "0"; //삭제되고 남은 공간은 0으로 설정
            		for(int i = 1; i < currentHandi.length; i++) {
            			Boolean isExist = false; //장애인 전용 공간 입력 창에 입력한 값이 주차 공간 테이블에 이미 설정되었던 값인지 확인하는 불리언
            			
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true; //장애인 전용 공간 입력 창에 입력한 값이 주차 공간 테이블에서 정상적으로 삭제됨을 불리언
            				}
            			}
            			if(!isExist) { //장애인 전용 공간 입력 창에 입력 창에 입력하지 않은 값은 그대로 설정을 유지
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
            		
            		String finalNoPark = "0";
            		for(int i = 1; i < currentNoPark.length; i++) {
            			Boolean isExist = false; //주차 불가 공간 입력 창에 입력한 값이 주차 공간 테이블에 이미 설정되었던 값인지 확인하는 불리언
            			
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true; //주차 불가 공간 입력 창에 입력한 값이 주차 공간 테이블에서 정상적으로 삭제됨을 확인
            				}
            			}
            			if(!isExist) { //주차 불가 공간 입력 창에 입력 창에 입력하지 않은 값은 그대로 설정을 유지
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, finalHandicap, finalNoPark};
            	
            	}else if(settingOp == 2) { //장애인 전용 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 장애인 전용 공간 값 중에서 공통된 값이 존재하는 경우
            		for(int i = 0; i < handicaps.length; i++) {
            			Boolean isExist = false;
            			
            			for(int j = 0; j < currentHandi.length; j++) {
            				if(handicaps[i].equals(currentHandi[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "장애인 전용 주차 공간으로 설정되어 있지 않은 값이 있습니다");
        					return;
        				}
            		}
            		
            		String finalHandicap = "0";
            		for(int i = 1; i < currentHandi.length; i++) {
            			Boolean isExist = false;
            			
            			for(int j = 0; j < handicaps.length; j++) {
            				if(currentHandi[i].equals(handicaps[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalHandicap += ","+currentHandi[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, finalHandicap, String.join(",", currentNoPark)};
            	
            	}else if(settingOp == 3) { //주차 불가 공간 입력 창에 입력된 값과 관리자 데이터 파일에 저장된 주차 불가 공간 값 중에서 공통된 값이 존재하는 경우
            		for(int i = 0; i < noParks.length; i++){
            			Boolean isExist = false;
            			
            			for(int j = 0; j < currentNoPark.length; j++) {
            				if(noParks[i].equals(currentNoPark[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
        					JOptionPane.showMessageDialog(null, "주차 불가 공간으로 설정되어 있지 않은 값이 있습니다");
        					return;
        				}
            		}
            		
            		String finalNoPark = "0";
            		for(int i = 1; i < currentNoPark.length; i++) {
            			Boolean isExist = false;
            			
            			for(int j = 0; j < noParks.length; j++) {
            				if(currentNoPark[i].equals(noParks[j])) {
            					isExist = true;
            				}
            			}
            			if(!isExist) {
            				finalNoPark += ","+currentNoPark[i];
        				}
            		}
            		settingData = new String[] {width, height, pay, ID, PW, String.join(",", currentHandi), finalNoPark};
            	}
            	sct.setSetting(settingData);
    			
    			JOptionPane.showMessageDialog(null, "설정하신 특수 주차 공간이 정상적으로 적용됐습니다");
    			dispose(); 
    			new GUIMain();
        	}
        });
    	
    	CancleButton.addActionListener(new ActionListener() { //취소 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIAdmin(); //관리자 화면으로 이동
        	}
        });
    }
    
    //차량/위치 번호 입력 창에 입력된 값이 올바른 값인지 확인하는 메소드
    private boolean checkString(String place) {
    	try { //관리자 데이터 파일에서 가로, 세로 값이 적힌 텍스트를 읽어들임
    		String[] settingData = sct.getSetting();
    		
        	int width = Integer.parseInt(settingData[0]);
        	int height = Integer.parseInt(settingData[1]);
    		
    		//위치 번호 입력 창에서 입력받은 값의 첫 번째 자리가 알파벳(대문자)이며, 주차 공간 테이블의 세로 값 안에 있으며
    		if((place.charAt(0) >= 65 && place.charAt(0) < 65 + height)) { 
    			if(place.length() == 2) { //입력받은 값이 두 자리일때(알파벳 포함)
    				//두 번째 자리가 주차 공간 테이블의 가로 값 안에 있다면
    				if(place.charAt(1) >= 49 && place.charAt(1) < 49 + width)
    					return true; //올바른 값으로 인식하여 true 반환
    			}else if(place.length() == 3) //입력받은 값이 세 자리일때(알파벳 포함)
    				//입력받은 값의 두 번째와 세 번째 자리의 값이 합쳐 10이 된다면 true 반환(가로줄은 최대 10이기 때문)
    				if (("" + place.charAt(1) + place.charAt(2)).equals("10"))
    					return true;
    		}else //주차 공간 테이블에 존재하지 않는 값이라면 false 반환
    			return false;
    	} catch(Exception e) { //그 외의 값이 입력된 경우 false 반환
    		return false;
    	}
    	return false; //기본은 false 반환
    }
    
    private int checkSetting(String[] handicaps, String[] noParks) { //입력 창에 입력된 값이 어떤 형식인지 확인하는 메소드
    	Boolean checkHandi = true; //입력 창에 입력된 장애인 전용 공간을 확인하는 불리언
    	Boolean checkNoPark = true; //입력 창에 입력된 주차 불가 공간을 확인하는 불리언
    	
    	for(int i = 0; i < handicaps.length; i++)
			if(!checkString(handicaps[i])) { //checkString 메소드에서 장애인 전용 공간 입력 창에 올바르지 않은 값을 입력한 경우
				checkHandi = false; //false 반환
				break;
			}
    	
    	for(int i = 0; i < noParks.length; i++)
    		if(!checkString(noParks[i]) ){ //checkString 메소드에서 주차 불가 공간 입력 창에 올바르지 않은 값을 입력한 경우
    			checkNoPark = false;
				break;
    		}
    	
    	if(checkHandi&& checkNoPark) //둘 다 올바른 값을 입력한 경우
    		return 1;
    	//장애인 전용 공간 입력 창에는 올바른 값을 입력했으나 주차 불가 공간 입력 창에는 공백인 경우
    	else if (checkHandi&& noParkText.getText().equals(""))
    		return 2;
    	//주차 불가 공간 입력 창에는 올바른 값을 입력했으나 장애인 전용 공간 입력 창에는 공백인 경우
    	else if (handicapText.getText().equals("") && checkNoPark)
    		return 3;
    	
    	return -1; //올바르지 않은 값이 입력된 경우
    }
}