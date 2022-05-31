import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIParking extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

	//화면에 보여주는 메시지 설정
    private JLabel carNumLabel = new JLabel("▶ 주차할 차량 번호를 입력하십시오.");
    private JLabel exampleLabel = new JLabel("(예시. 12가3456)");
    private JLabel placeLabel = new JLabel("▶ 주차할 위치 번호를 입력하십시오.");

    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    private JTextField placeText = new JTextField(); //위치 번호 입력 창
    
    private JButton cancleButton = new JButton("취소"); //취소 버튼
    private JButton parkingButton = new JButton("주차"); //주차 버튼
    
    private File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일

    GUIParking(){ //화면 기본 설정
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() { //각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);

        carNumLabel.setLocation(40, 80);
        carNumLabel.setSize(900, 100);
        carNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carNumLabel.setFont(font);
        
        exampleLabel.setLocation(215, 135);
        exampleLabel.setSize(900, 100);
        exampleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        exampleLabel.setFont(new Font("고딕", Font.PLAIN, 30));
        
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
        p.add(exampleLabel);
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
            	new GUIMain(); //메인 화면으로 이동
        	}		             
        });
        
        parkingButton.addActionListener(new ActionListener() { //주차 버튼 클릭시 실행
            public void actionPerformed(ActionEvent e) {
            	ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
            	String[][] clientTableValue = dbc.getTable(); //DB파일 내의 고객 테이블을 가져옴
            	
            	try {
            		BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
                	List<String> list = new ArrayList<String>(); //읽어들인 관리자 데이터 파일의 내용을 저장하기 위한 리스트 생성
                	String setting = null; //관리자 데이터 파일을 읽어들이기 위한 변수
                	
                	while((setting = br.readLine()) != null) { //관리자 데이터 파일이 null이 아닐 때까지 읽어들임
                		list.add(setting); //읽어들인 내용을 리스트에 저장
                	}
                	
                	int ListSize = list.size(); //리스트에 저장된 객체의 수를 리턴
                	String arr[] = list.toArray(new String[ListSize]); //리스트에 저장된 객체와 함께 배열로 변환함
                	
                	//배열의 n번째에 저장된 장애인/주차 불가 구역 번호의 내용을 저장하기 위한 변수
                	String noParkStr = arr[6];
                	
                	String[] noParks = noParkStr.split(":")[1].split(",");
                	
                	//checkString 메소드에서 차량/위치 번호 입력 창에 입력된 값이 올바르지 않게 입력된 것이 확인됐다면
                	if(!checkString(carNumText.getText(), placeText.getText())) {
                		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
                		return;
            		} 
                	
                	for(int line = 0; line < clientTableValue.length; line++) {
                		//위치 번호 입력 창에 입력한 값과 동일한 번호가 고객 테이블에 존재한다면
                		if(clientTableValue[line][2].equals(placeText.getText())) {
                            JOptionPane.showMessageDialog(null, "해당 위치 번호는 이미 차지하고 있는 공간입니다"); 
                            return; //해당 위치 번호를 반환
                        //차량 번호 입력 창에 입력한 값과 동일한 번호가 고객 테이블에 존재한다면
                    	}else if(clientTableValue[line][0].equals(carNumText.getText())) {
                    		JOptionPane.showMessageDialog(null, "해당 차량 번호는 이미 있는 차량입니다"); 
                            return; //해당 차량 번호를 반환
                    	}
                		
                		for(int i = 0; i < noParks.length; i++) {
                			//위치 번호 입력 창에 입력한 값과 동일한 번호가 주차 불가 공간으로 설정되어있다면
                			if(noParks[i].equals(placeText.getText())) {
                				JOptionPane.showMessageDialog(null, "해당 위치 번호는 주차 불가 공간입니다"); 
                                return;
                			}
                		}
                	}
                	//고객 테이블에 동일한 차량/위치 번호가 존재하지 않는 경우 입력된 차량/위치 번호를 DB파일에 저장
                	dbc.data_insert(carNumText.getText(), placeText.getText());
                	
                	dispose();
                	new GUIMain();
            	}catch(Exception e1) {
            		System.out.println(e1.getMessage());
            	}
        	}		        	             
        });
    }
    
    //차량/위치 번호 입력 창에 입력된 값이 올바른 값인지 확인하는 메소드
    private boolean checkString(String carNum, String place) {
    	try { //관리자 데이터 파일에서 가로, 세로 값이 적힌 텍스트를 읽어들임
    		BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
    		
    		String widthStr = br.readLine();
        	String heightStr = br.readLine();
        	
        	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
        	int width = Integer.parseInt(widthStr.split(":")[1]);
        	int height = Integer.parseInt(heightStr.split(":")[1]);
    		
        	String str = carNumText.getText();
        	char check = str.charAt(2);
        	
    		if(carNumText.getText().length() != 7) //차량 번호 입력 창에서 입력받은 값이 7자리가 아니면
    			return false; //올바르지 않은 값으로 인식하여 false 반환
    		
    		//차량 번호 입력 창에서 입력받은 값이 7자리지만, 3번째 자리가 숫자라면
    		if(carNumText.getText().length() == 7 && check >= 48 && check <= 57)
    			return false;
    		
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
}