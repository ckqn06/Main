import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

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
            	ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
            	String[][] clientTableValue = dbc.getTable();
            	
            	if(!checkString(carNumText.getText(), placeText.getText())) { //올바른 값이 아니라면
            		JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
            		return;
        		} 
            	
            	int i = 0;
            	while(clientTableValue[i][0] != null) {
            		if(clientTableValue[i][2].equals(placeText.getText())) { //해당하는 위치 번호가 이미 차 있거나 해당 차량이 이미 있는 경우
                        JOptionPane.showMessageDialog(null, "해당 위치 번호는 이미 차지하고 있는 공간입니다"); 
                        return;
                	}else if(clientTableValue[i][0].equals(carNumText.getText())) {
                		JOptionPane.showMessageDialog(null, "해당 차량 번호는 이미 있는 차량입니다"); 
                        return;
                	}
            		i++;
            	}
            	dbc.data_insert(carNumText.getText(), placeText.getText()); //데이터베이스에 차량 추가
            	dispose();
            	new GUIMain();  //메인화면으로
        	}		        	             
        });
    }
    
    private boolean checkString(String carNum, String place) { //올바른 값인지 확인하는 메소드
    	try {
    		BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));
    		
    		String widthStr = br.readLine();  //데이터 파일에서 문자열 추출
        	String heightStr = br.readLine();
        	
        	int width = Integer.parseInt(widthStr.split(":")[1]); //값 추출
        	int height = Integer.parseInt(heightStr.split(":")[1]);
        	
    		Integer.parseInt(carNumText.getText()); //숫자인지 확인
    		if(carNumText.getText().length() != 4)   //네자리 수인지 확인
        		return false;
    		if((place.charAt(0) >= 65 && place.charAt(0) < 65 + height)) { //범위안의 알파벳인지 확인
    			if(place.length() == 2) { //한 자리 수일 때 
    				if(place.charAt(1) >= 49 && place.charAt(1) < 49 + width) //범위안의 수인지 확인
    					return true;
    			}else if(place.length() == 3) //두 자리 수일 때
    				if (("" + place.charAt(1) + place.charAt(2)).equals("10")) //10인지 확인
    					return true;
    		}else
    			return false;   		
    	}catch(Exception e) {
    		return false;
    	}
    	return false;
    }
	
	public static void main(String[] args) { //실행 테스트를 위한 코드
		new GUIParking();
	}
}