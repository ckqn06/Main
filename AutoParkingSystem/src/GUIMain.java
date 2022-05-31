import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel(); //고객 테이블을 위한 보조 프레임 생성
    public JPanel p2 = new JPanel(); //주차 공간 테이블을 위한 보조 프레임 생성
    
    private Font font = new Font("맑은 고딕", Font.BOLD, 25); //폰트 설정
    
    //화면에 보여주는 메시지 설정
    private JLabel emptyLabel = new JLabel(": 빈 공간");
    private JLabel whiteLabel = new JLabel("흰색");
    private JLabel existLabel = new JLabel(": 찬 공간");
    private JLabel redLabel = new JLabel("빨간색");
    private JLabel handicapLabel = new JLabel(": 장애인 전용");
    private JLabel greenLabel = new JLabel("초록색");
    private JLabel noParkLabel = new JLabel(": 주차 불가");
    private JLabel blackLabel = new JLabel("검은색");
    private JLabel clientLabel = new JLabel("-현재 이용 중인 고객");
    private JLabel searchLabel = new JLabel("-차량 데이터 검색");
    private JLabel carNumLabel = new JLabel("차량 번호로 검색:");
    private JLabel placeNumLabel = new JLabel("위치 번호로 검색:");
    
    private JTextField carNumText = new JTextField(); //차량 번호 입력 창
    private JTextField placeNumText = new JTextField(); //위치 번호 입력 창
    
    private JButton parkButton = new JButton("주차하기"); //주차하기 버튼
    private JButton payButton = new JButton("결제하기"); //결제하기 버튼
    private JButton searchButton = new JButton("검색"); //검색 버튼
    private JButton adminButton = new JButton("관리자 설정"); //관리자 설정 버튼
    private JButton quitButton = new JButton("시스템 종료"); //시스템 종료 버튼
    
    private String[] header = {"차량번호", "주차 시간", "현재 위치", "비용"}; //고객 테이블의 헤더 생성
    private String[][] rows = {}; //고객 테이블의 열(세로 줄) 생성
    private TableModel tableModel = new DefaultTableModel(rows, header); //생성한 헤더와 열을 하나의 테이블로 정의함
    private JTable clientTable = new JTable(tableModel); //하나로 정의한 테이블로 고객 테이블을 생성
    
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //셀 가운데 정렬을 위한 요소

    private JScrollPane clientPane; //고객 테이블의 스크롤바 생성
    
    private ServerConnection sct = new ServerConnection(); //서버 연결 객체
    private UpdateClientTable uct; //고객 테이블의 데이터 갱신을 위한 스레드
    
    private int width, height, tpay = 0; //가로, 세로, 시간당 주차 비용 값을 0으로 초기화

    GUIMain(){ //화면 기본 설정
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        setLocationRelativeTo(null);
        uct = new UpdateClientTable(this, clientTable); //스레드에서 고객 테이블의 데이터를 갱신시킴
        uct.start(); //스레드 시작
    }
    
    public static int diffTime(String parkTime) { //주차 시간을 구하기 위한 시간 차이 계산
    	Calendar cal = Calendar.getInstance(); //Calendar 객체를 이용하여 현재 날짜, 요일, 시간 정보를 가져옴
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //"년-월-일 시:분:초"로 시간 포맷 설정
    	try {
    		Date parkDate = format.parse(parkTime); //받아온 주차 시간을 Date 타입으로 전환
    		cal.setTime(parkDate); //가져온 시간 정보에 설정한 포맷을 적용
    		//현재 시간에서 고객이 주차를 시작한 시간을 뺀 값인 주차 시간(ms단위)을 구한 뒤, 이를 다시 분 단위로 계산 
    		long diffTime = (System.currentTimeMillis() - cal.getTimeInMillis()) / 1000 / 60;
    		return (int)diffTime; //계산한 주차 시간을 반환
    	}catch(Exception e) { 
    		System.out.println(e.getMessage());
    		return -1;
    	}
    }

    public void formDesign() { //각 GUI 객체 설정
    	try { //관리자 데이터 파일에서 가로, 세로, 시간당 주차 비용 값이 적힌 텍스트를 읽어들임
        	String[] settingData = sct.getSetting();
        	
        	width = Integer.parseInt(settingData[0]);
        	height = Integer.parseInt(settingData[1]);
        	tpay = Integer.parseInt(settingData[2]);
        } catch(Exception e) { //예외 처리
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p, BorderLayout.EAST); //고객 테이블을 위한 보조 프레임을 동쪽에 배치
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238)); //배경색을 회색으로 설정
        p.setPreferredSize(new Dimension(500, 800)); //보조 프레임의 폭과 너비를 설정

        this.add(p2, BorderLayout.WEST); //주차 공간 테이블을 위한 보조 프레임을 서쪽에 배치
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190)); //배경색을 파란색으로 설정
        p2.setPreferredSize(new Dimension(500, 800));
        
        emptyLabel.setLocation(100, 20);
        emptyLabel.setSize(400, 100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));

        whiteLabel.setLocation(40, 20);
        whiteLabel.setSize(400, 100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));
        whiteLabel.setForeground(Color.white); //텍스트의 색상을 설정

        existLabel.setLocation(100, 60);
        existLabel.setSize(400, 100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));

        redLabel.setLocation(15, 60);
        redLabel.setSize(400, 100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));
        redLabel.setForeground(Color.red);
        
        handicapLabel.setLocation(305, 20);
        handicapLabel.setSize(400, 100);
        handicapLabel.setVerticalAlignment(SwingConstants.TOP);
        handicapLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));

        greenLabel.setLocation(220, 20);
        greenLabel.setSize(400, 100);
        greenLabel.setVerticalAlignment(SwingConstants.TOP);
        greenLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));
        greenLabel.setForeground(Color.green);
        
        noParkLabel.setLocation(330, 60);
        noParkLabel.setSize(400, 100);
        noParkLabel.setVerticalAlignment(SwingConstants.TOP);
        noParkLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));

        blackLabel.setLocation(245, 60);
        blackLabel.setSize(400, 100);
        blackLabel.setVerticalAlignment(SwingConstants.TOP);
        blackLabel.setFont(new Font("맑은 고딕", Font.BOLD, 27));
        blackLabel.setForeground(Color.black);
        
        clientLabel.setLocation(30, 80);
        clientLabel.setSize(400, 100);
        clientLabel.setFont(font.deriveFont(25f));
        
        searchLabel.setLocation(30, 350);
        searchLabel.setSize(400, 100);
        searchLabel.setFont(font.deriveFont(25f));
        
        carNumLabel.setLocation(30, 410);
        carNumLabel.setSize(400, 100);
        carNumLabel.setFont(font.deriveFont(25f));
        
        placeNumLabel.setLocation(30, 520);
        placeNumLabel.setSize(400, 100);
        placeNumLabel.setFont(font.deriveFont(25f));
        
        carNumText.setLocation(30, 490);
        carNumText.setSize(400, 50);
        carNumText.setFont(font);
        
        placeNumText.setLocation(30, 600);
        placeNumText.setSize(400, 50);
        placeNumText.setFont(font);
        
        parkButton.setLocation(30, 40);
        parkButton.setSize(170, 50);
        parkButton.setFont(font);
        
        payButton.setLocation(300, 40);
        payButton.setSize(170, 50);
        payButton.setFont(font);
        
        searchButton.setLocation(310, 375);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 690);
        adminButton.setSize(170, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(300, 690);
        quitButton.setSize(170, 50);
        quitButton.setFont(font);
        
        String[][] clientTableValue = sct.getTableData(); //DB파일에 저장된 고객 테이블의 값을 불러옴
        
        clientTable.setRowHeight(30);
        //고객 테이블 내부 데이터를 다루기 위해서 DefaultTableModel을 불러옴
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        
        for(int line = 0; line < clientTableValue.length; line++) {
        	int diffTime = diffTime(clientTableValue[line][1]); //고객 테이블의 2번째 열에 주차 시간을 저장시킴
        	int pay = ((diffTime/15 + 1) * (tpay/4))/10; //주차 시간을 통해 주차 비용 계산
        	String parkTime = "" + (diffTime / 60)+"시간 " + (diffTime % 60)+"분"; //계산한 주차 시간(분 단위)을 시간, 분으로 표시
        	
        	//고객 테이블의 열에 차량 번호, 주차 시간, 위치 번호, 주차 비용을 추가
        	clientModel.addRow(new Object[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], pay*10+" 원"});
        }
        
        TableColumnModel tcm = clientTable.getColumnModel(); //정렬할 테이블의 ColumnModel을 가져옴
        
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); //테이블의 셀을 가운데 정렬함
        for (int i = 0; i < tcm.getColumnCount(); i++) { //테이블의 셀을 가운데 정렬함
        	tcm.getColumn(i).setCellRenderer(dtcr); 
        } 
        
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(25, 160);
        clientPane.setSize(450, 205);

        p.add(clientLabel);
        p.add(searchLabel);
        p.add(carNumLabel);
        p.add(placeNumLabel);
        p.add(carNumText);
        p.add(placeNumText);      
        p.add(parkButton);
        p.add(payButton);
        p.add(searchButton);
        p.add(adminButton);
        p.add(quitButton);
        p.add(clientPane);
        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(handicapLabel);
        p2.add(greenLabel);
        p2.add(noParkLabel);
        p2.add(blackLabel);
    }

    private void eventListner() { //버튼 클릭 이벤트 설정
        parkButton.addActionListener(new ActionListener() { //주차하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData(); //DB파일 내의 고객 테이블을 가져옴
        		
        		//테이블의 행 값이 주차 공간 테이블의 가로*세로 값과 동일하다면 현재 주차장은 가득참
        		if(clientTableValue.length == width * height) {
        			JOptionPane.showMessageDialog(null, "현재 주차장이 가득 차서 주차가 불가능합니다");
        		}else { //line의 값이 주차 공간의 가로*세로 값보다 작다면 현재 주차장은 여유 공간이 존재
        			uct.interrupt(); //스레드(UpdateClientTable)에 인터럽트를 걸음(데이터 갱신을 중지시킴)
        			dispose();
            		new GUIParking(); //주차하기 화면으로 이동
        		}
        	}
        });
        
        payButton.addActionListener(new ActionListener() { //결제하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIPayCarChoice(tpay); //결제하기 화면으로 이동
        	}
        });
        
        searchButton.addActionListener(new ActionListener() { //검색 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = sct.getTableData();
        		boolean isExist = false; //일치하는 차가 있는지 확인하는 변수
        		
        		for(int line = 0; line < clientTableValue.length; line++) {
        			if(clientTableValue[line][0].equals(carNumText.getText()) && clientTableValue[line][2].equals(placeNumText.getText()))
        				isExist = true; //고객 테이블의 행을 읽어 고객이 차량/위치 번호 입력 창에 입력한 값과 동일한 값이 고객 테이블에 존재한다면
        			else if(clientTableValue[line][0].equals(carNumText.getText()) && placeNumText.getText().equals(""))
        				isExist = true; //차량 번호 입력 창에 입력한 값과 동일한 값이 고객 테이블에 존재하지만 위치 번호는 없다면
        			else if(clientTableValue[line][2].equals(placeNumText.getText()) && carNumText.getText().equals(""))
        				isExist = true; //위치 번호 입력 창에 입력한 값과 동일한 값이 고객 테이블에 존재하지만 차량 번호는 없다면
        			
        			if (isExist) { //고객이 차량/위치 번호 입력 창에 입력한 값과 동일한 값이 고객 테이블에 존재한다면
        				int diffTime = diffTime(clientTableValue[line][1]); //해당 값을 가진 차량의 주차 시간을 구함
        				int pay = ((diffTime/15 + 1) * (tpay/4))/10;
        	        	String parkTime = "" + (diffTime / 60)+"시간 " + (diffTime % 60)+"분";
        	        	
        	        	uct.interrupt();
        				dispose();
        				//검색한 차량의 차량번호, 주차 시간, 현재 위치, 주차 비용 값과 함께 검색 결과 화면으로 이동
                		new GUISearch(new String[]{clientTableValue[line][0], parkTime, clientTableValue[line][2], ""+pay*10+" 원"});
                		return;
        			}
        		}
        		JOptionPane.showMessageDialog(null, "해당 차량 혹은 위치 번호에 해당하는 차량이 없습니다"); //고객 테이블에 동일한 값이 존재하지 않는다면
        	}
        });
        
        adminButton.addActionListener(new ActionListener() { //관리자 설정 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(2); //관리자 로그인 화면으로 이동
        	}
        });
        
        quitButton.addActionListener(new ActionListener() { //시스템 종료 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(3); //시스템 종료 화면으로 이동
        	}
        });
    }

    public static void main(String[] args) { //실행 테스트를 위한 코드
        new GUIMain();
    }
}