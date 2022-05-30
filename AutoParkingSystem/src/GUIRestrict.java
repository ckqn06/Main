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
    
    private JButton SettingButton = new JButton("설정"); //설정 버튼
    private JButton CancleButton = new JButton("취소"); //취소 버튼
    
    private String width, height, pay, ID, PW; //가로, 세로, 관리자 ID, 비밀번호를 저장하는 변수
    
    File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일

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
    		if(f.exists()) { //관리자 데이터 파일에서 텍스트를 읽어들임
    			BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
            	List<String> list = new ArrayList<String>(); //읽어들인 관리자 데이터 파일의 내용을 저장하기 위한 리스트 생성
            	String line = null; //관리자 데이터 파일을 읽어들이기 위한 변수
            	
            	while((line = br.readLine()) != null) { //관리자 데이터 파일이 null이 아닐 때까지 읽어들임
            		list.add(line); //읽어들인 내용을 리스트에 저장
            	}
            	
            	int ListSize = list.size(); //리스트에 저장된 객체의 수를 리턴
            	String arr[] = list.toArray(new String[ListSize]); //리스트에 저장된 객체와 함께 배열로 변환함
            	
            	//배열의 n번째에 저장된 가로, 세로, 시간당 주차 비용, 장애인/주차 불가 구역 번호의 내용을 저장하기 위한 변수
            	String widthStr = arr[0];
            	String heightStr = arr[1];
            	String payStr = arr[2];
            	String IDStr = arr[3];
            	String PWStr = arr[4];
            	
            	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
            	width = widthStr.split(":")[1];
            	height = heightStr.split(":")[1];
            	pay = payStr.split(":")[1];
            	ID = IDStr.split(":")[1];
            	PW = PWStr.split(":")[1];
            	
            	br.close(); //버퍼를 닫음
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
        
        SettingButton.setLocation(550, 630);
        SettingButton.setSize(160, 80);
        SettingButton.setFont(font);
        
        CancleButton.setLocation(250, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel);
        p.add(detailLabel);
        p.add(handicapLabel);
        p.add(noParkLabel);
        p.add(handicapText);
        p.add(noParkText);
        p.add(SettingButton);
        p.add(CancleButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정    	
    	SettingButton.addActionListener(new ActionListener() { //설정 버튼 클릭 시 실행
    		public void actionPerformed(ActionEvent e) {
    			try {
    				ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
                	String[][] clientTableValue = dbc.getTable(); //DB파일 내의 고객 테이블을 가져옴
                	int line = 0; //고객 테이블의 행 수를 확인하기 위한 변수 생성
                	
                	//checkString 메소드에서 차량/위치 번호 입력 창에 입력된 값이 올바르지 않게 입력된 것이 확인됐다면
                	if(!checkString(handicapText.getText()) || !checkString(noParkText.getText())) {
                		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
                		return;
            		} 
                	
    				while(clientTableValue[line][0] != null) { //차량 번호가 null이 아닐 때까지 반복
                		//위치 번호 입력 창에 입력한 값과 동일한 번호가 고객 테이블에 존재한다면
                		if(clientTableValue[line][2].equals(handicapText.getText()) || clientTableValue[line][2].equals(noParkText.getText())) {
                            JOptionPane.showMessageDialog(null, "해당 위치 번호는 이미 차지하고 있는 공간입니다"); 
                            return; //해당 위치 번호를 반환
                    	}
                		line++; //동일한 값이 존재하지 않는다면 line의 값을 증가시켜 다음 행을 탐색
                	}
    				
        			if(handicapText.getText().equals("") && noParkText.getText().equals("")) { //ID/PW 입력 창에 아무 값도 입력하지 않은 경우
            			JOptionPane.showMessageDialog(null, "관리자 ID 및 비밀번호는 최소 1자리 이상이여야 합니다");
            		} else { //
            			OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
        				
        				//파일에 변경한 관리자 ID, 비밀번호를 적어놓음
        				String str = ("가로 값:"+width + "\n세로 값:"+height + "\n시간당 주차 비용:"+pay + "\nID:"+ID + "\nPW:"+PW
        						+ "\n장애인 전용 주차 구역:"+handicapText.getText() + "\n주차 불가 구역:"+noParkText.getText());
            			byte[] by = str.getBytes();
            			os.write(by);
            			
            			JOptionPane.showMessageDialog(null, "설정하신 특수 주차 공간이 정상적으로 적용됐습니다");
            			dispose(); 
            			new GUIMain(); //메인 화면으로 이동
            		}
    			} catch(Exception e1) { //예외 처리
    				System.out.println(e1.getMessage());
    	        	e1.printStackTrace();
    			}
    		}
    	});
    	
    	CancleButton.addActionListener(new ActionListener() { //취소 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIAdmin(); //관리자 설정 화면으로 이동
        	}
        });
    }
    
    //차량/위치 번호 입력 창에 입력된 값이 올바른 값인지 확인하는 메소드
    private boolean checkString(String place) {
    	try { //관리자 데이터 파일에서 가로, 세로 값이 적힌 텍스트를 읽어들임
    		BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
    		
    		String widthStr = br.readLine();
        	String heightStr = br.readLine();
        	
        	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
    		
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
    
    public static void main(String[] args) {
		new GUIRestrict();
	}
}