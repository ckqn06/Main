import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUIAdminSetting extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

	//화면에 보여주는 메시지 설정
    private JLabel placeLabel = new JLabel("▶ 주차 공간 설정");
    private JLabel detailLabel = new JLabel("<html>(최소 가로 1칸/세로 1칸<br/>최대 가로 10칸/세로 15칸 설정 가능)</html>");
    private JLabel widthLabel = new JLabel("가로 :");
    private JLabel heightLabel = new JLabel("세로 :");
    private JLabel payLabel = new JLabel("▶ 시간당 주차 비용 설정");

    private JTextField widthText = new JTextField(); //가로 입력 창
    private JTextField heightText = new JTextField(); //세로 입력 창
    private JTextField payText = new JTextField(); //시간당 주차 비용 입력 창
    
    private JButton SettingButton = new JButton("설정 변경"); //설정 버튼
    
    File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일

    GUIAdminSetting(){ //화면 기본 설정
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    private void formDesign() { //각 GUI 객체 설정
        this.add(p);
        p.setLayout(null);
        
        placeLabel.setLocation(100, 20);
        placeLabel.setSize(600, 100);
        placeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        placeLabel.setFont(font);

        detailLabel.setLocation(100, 50);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("고딕", Font.PLAIN, 30));
        
        widthLabel.setLocation(120, 205);
        widthLabel.setSize(500, 100);
        widthLabel.setHorizontalAlignment(SwingConstants.LEFT);
        widthLabel.setFont(font);
        
        heightLabel.setLocation(120, 315);
        heightLabel.setSize(500, 100);
        heightLabel.setHorizontalAlignment(SwingConstants.LEFT);
        heightLabel.setFont(font);
        
        payLabel.setLocation(100, 430);
        payLabel.setSize(800, 100);
        payLabel.setHorizontalAlignment(SwingConstants.LEFT);
        payLabel.setFont(font);

        widthText.setLocation(270, 220);
        widthText.setSize(400, 80);
        widthText.setFont(font);

        heightText.setLocation(270, 330);
        heightText.setSize(400, 80);
        heightText.setFont(font);
        
        payText.setLocation(110, 530);
        payText.setSize(400, 80);
        payText.setFont(font);
        
        if(f.exists()) {
        	SettingButton.setLocation(180, 650);
        }
        
        if(!f.exists()) { //만약 관리자 데이터 파일이 존재하지 않는다면 (= 데이터 파일을 새로 생성하는 경우)
        	SettingButton.setText("설정"); //설정 버튼의 텍스트를 "설정 변경"에서 "설정"으로 변경
        }
        SettingButton.setLocation(360, 650);
        SettingButton.setSize(280, 80);
        SettingButton.setFont(font);
        
        p.add(placeLabel);
        p.add(detailLabel);
        p.add(widthLabel);
        p.add(heightLabel);
        p.add(payLabel);
        p.add(widthText);
        p.add(heightText);
        p.add(payText);
        p.add(SettingButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정    	
    	SettingButton.addActionListener(new ActionListener() { //설정 버튼 클릭 시 실행
    		public void actionPerformed(ActionEvent e) {
    			try {
    				int width = Integer.parseInt(widthText.getText()); //가로 입력 창에서 입력받은 값을 정수로 받아옴
        			int height = Integer.parseInt(heightText.getText()); //세로 입력 창에서 입력받은 값을 정수로 받아옴
        			int pay = Integer.parseInt(payText.getText()); //시간당 주차 비용 입력 창에서 입력받은 값을 정수로 받아옴
        			ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
        			String[][] clientTableValue = dbc.getTable(); //DB파일 내의 고객 테이블을 가져옴

        			if(1<=width && width<=10 && 1<=height && height <= 15) { //가로, 세로 값이 범위 내의 값이라면
        				OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
            				
        				//파일에 가로/세로 값, 시간당 주차 비용 값을 적어놓음
        				String str = ("가로 값:"+width + "\n세로 값:"+height + "\n시간당 주차 비용:"+pay);
            			byte[] by = str.getBytes();
            			os.write(by);
            				
            			JOptionPane.showMessageDialog(null, "변경하신 설정 값이 정상적으로 적용됐습니다");
            			dispose(); 
            			new GUIMain(); //메인 화면으로 이동
            		} else { //가로, 세로 값이 범위를 벗어났을 때 실행
            			JOptionPane.showMessageDialog(null, "범위 값 안의 값을 입력해주세요");
            		}
    			} catch(Exception e1) { //가로/세로, 시간당 주차 비용 입력 창에 정수형이 입력되지 않았다면
    				JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
    			}
    		}
    	});
    }
}