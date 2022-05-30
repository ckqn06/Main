import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private JButton CancleButton = new JButton("취소"); //취소 버튼
    
    private String ID, PW; //관리자 ID, 비밀번호를 저장하는 변수
    
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

            	//배열의 n번째에 저장된 관리자 ID, 비밀번호, 장애인/주차 불가 구역 번호의 내용을 저장하기 위한 변수
            	String IDStr = arr[3];
            	String PWStr = arr[4];
            	
            	//배열에 저장된 텍스트를 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
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
        
        SettingButton.setLocation(360, 650);
        SettingButton.setSize(280, 80);
        SettingButton.setFont(font);
        
        if(!f.exists()) { //만약 관리자 데이터 파일이 존재하지 않는다면 (= 데이터 파일을 새로 생성하는 경우)
        	SettingButton.setText("설정"); //설정 버튼의 텍스트를 "설정 변경"에서 "설정"으로 변경
        }
        
        if(f.exists()) {
        	SettingButton.setLocation(540, 650);
        	SettingButton.setSize(230, 80);
        	
        	CancleButton.setLocation(230, 650);
            CancleButton.setSize(160, 80);
            CancleButton.setFont(font);
        	p.add(CancleButton);
        }
        
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

        			if(1<=width && width<=10 && 1<=height && height <= 15) { //가로, 세로 값이 범위 내의 값이며
        				if(f.exists()) { //관리자 데이터 파일이 존재한다면
        					OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
        					
            				//파일에 가로/세로 값, 시간당 주차 비용 값을 적어놓음
        					String str = ("가로 값:"+width + "\n세로 값:"+height + "\n시간당 주차 비용:"+pay + "\nID:"+ID
    						+ "\nPW:"+PW + "\n장애인 전용 주차 구역:0\n주차 불가 구역:0"); //설정 변경 시 오류 방지를 위해 장애인 전용 주차 구역과 주차 불가 구역 값 초기화
                			byte[] by = str.getBytes();
                			os.write(by);
                				
                			JOptionPane.showMessageDialog(null, "변경하신 설정 값이 정상적으로 적용됐습니다");
                			dispose(); 
                			new GUIMain(); //메인 화면으로 이동
        				} else { //관리자 데이터 파일이 존재하지 않는다면
        					OutputStream os = new FileOutputStream(f);
        					
            				String str = ("가로 값:"+width + "\n세로 값:"+height + "\n시간당 주차 비용:"+pay 
            						+ "\nID:admin" + "\nPW:park123" + "\n장애인 전용 주차 구역:0" + "\n주차 불가 구역:0");
                			byte[] by = str.getBytes();
                			os.write(by);
                				
                			JOptionPane.showMessageDialog(null, "입력하신 설정 값이 정상적으로 적용됐습니다");
                			dispose(); 
                			new GUIMain();
        				}
            		} else { //가로, 세로 값이 범위를 벗어났을 때 실행
            			JOptionPane.showMessageDialog(null, "범위 값 안의 값을 입력해주세요");
            		}
    			} catch(Exception e1) { //가로/세로, 시간당 주차 비용 입력 창에 정수형이 입력되지 않았다면
    				JOptionPane.showMessageDialog(null, "올바른 값을 입력해주세요");
    			}
    		}
    	});
    	
    	CancleButton.addActionListener(new ActionListener() { //메인화면으로 버튼 클릭 시 실행
    		public void actionPerformed(ActionEvent e) {
    			dispose();
    			new GUIAdmin(); //관리자 화면으로 이동
    		}
    	});
    }
    public static void main(String[] args) {
		new GUIAdminSetting();
	}
}