import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUIChangeLogin extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정

	//화면에 보여주는 메시지 설정
	private JLabel TopLabel_1 = new JLabel("▶ 이곳은 관리자 ID와 비밀번호를");
	private JLabel TopLabel_2 = new JLabel("변경하는 화면입니다.");
	private JLabel detailLabel = new JLabel("<html>(ID/PW는 1자 이상 외 제한 없음)</html>");
    private JLabel IDLabel = new JLabel("관리자 ID :");
    private JLabel PWLabel = new JLabel("비밀번호 :");

    private JTextField IDText = new JTextField(); //ID 입력 창
    private JTextField PWText = new JTextField(); //비밀번호 입력 창
    
    private JButton ChangeButton = new JButton("변경"); //변경 버튼
    private JButton CancleButton = new JButton("취소"); //취소 버튼
    
    private String width, height, pay, handicap, noPark; //가로, 세로, 시간당 주차 비용, 장애인/주차 불가 구역 번호를 저장하는 변수
    
    File f = new File("관리자 데이터 파일.txt"); //관리자 데이터 파일

    GUIChangeLogin(){ //화면 기본 설정
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
            	String handicapStr = arr[5];
            	String noParkStr = arr[6];
            	
            	//읽어들인 텍스트에서 split() 메서드를 이용해 ":"를 기준으로 문자열을 나눈 뒤, 추출한 값을 각 변수에 대입
            	width = widthStr.split(":")[1];
            	height = heightStr.split(":")[1];
            	pay = payStr.split(":")[1];
            	handicap = handicapStr.split(":")[1];
            	noPark = noParkStr.split(":")[1];
            	
            	br.close(); //버퍼를 닫음
    		}
        } catch(Exception e) { //예외 처리
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p);
        p.setLayout(null);

        TopLabel_1.setLocation(30, 50);
        TopLabel_1.setSize(900, 100);
        TopLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        TopLabel_1.setFont(font);
        
        TopLabel_2.setLocation(230, 110);
        TopLabel_2.setSize(900, 100);
        TopLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        TopLabel_2.setFont(font);
        
        detailLabel.setLocation(180, 115);
        detailLabel.setSize(1000, 200);
        detailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailLabel.setFont(new Font("고딕", Font.PLAIN, 30));
        
        IDLabel.setLocation(0, 290);
        IDLabel.setSize(400, 100);
        IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDLabel.setFont(new Font("맑은 고딕", Font.BOLD, 45));

        PWLabel.setLocation(0, 460);
        PWLabel.setSize(400, 100);
        PWLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PWLabel.setFont(new Font("맑은 고딕", Font.BOLD, 45));
        
        IDText.setSize(400, 100);
        IDText.setLocation(450, 290);
        IDText.setFont(font);

        PWText.setSize(400, 100);
        PWText.setLocation(450, 460);
        PWText.setFont(font);
        
        ChangeButton.setLocation(550, 630);
        ChangeButton.setSize(160, 80);
        ChangeButton.setFont(font);
        
        CancleButton.setLocation(250, 630);
        CancleButton.setSize(160, 80);
        CancleButton.setFont(font);
        
        p.add(TopLabel_1);
        p.add(TopLabel_2);
        p.add(detailLabel);
        p.add(IDLabel);
        p.add(PWLabel);
        p.add(IDText);
        p.add(PWText);
        p.add(ChangeButton);
        p.add(CancleButton);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정    	
    	ChangeButton.addActionListener(new ActionListener() { //설정 버튼 클릭 시 실행
    		public void actionPerformed(ActionEvent e) {
    			try {
        			if(IDText.getText().equals("") || PWText.getText().equals("")) { //ID/PW 입력 창에 아무 값도 입력하지 않은 경우
            			JOptionPane.showMessageDialog(null, "관리자 ID 및 비밀번호는 최소 1자리 이상이여야 합니다");
            		} else { //
            			OutputStream os = new FileOutputStream(f); //파일에 텍스트를 입력하기 위한 출력 스트림 생성
        				
        				//파일에 변경한 관리자 ID, 비밀번호를 적어놓음
        				String str = ("가로 값:"+width + "\n세로 값:"+height + "\n시간당 주차 비용:"+pay + "\nID:"+IDText.getText()
        						+ "\nPW:"+PWText.getText() + "\n장애인 전용 주차 구역:"+handicap + "\n주차 불가 구역:"+noPark);
            			byte[] by = str.getBytes();
            			os.write(by);
            			
            			JOptionPane.showMessageDialog(null, "변경하신 관리자 ID 및 비밀번호가 정상적으로 적용됐습니다");
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
}