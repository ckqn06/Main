import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel();
    public JPanel p2 = new JPanel();
    
    private Font font = new Font("맑은 고딕", Font.BOLD, 25); //폰트 설정
    
    private JLabel emptyLabel = new JLabel("= 빈 공간");
    private JLabel whiteLabel = new JLabel("흰색");
    private JLabel existLabel = new JLabel("= 찬 공간");
    private JLabel redLabel = new JLabel("빨간색");
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
    
    private String[] header = {"차량번호", "주차 시간", "현재 위치", "비용"}; //고객 테이블 헤더
    private String[][] rows = {};
    private TableModel tableModel = new DefaultTableModel(rows, header);
    public JTable placeView; //주차 공간 테이블
    private JTable clientTable = new JTable(tableModel); //고객 정보 테이블
    private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //셀 가운데 정렬을 위한 요소

    public JScrollPane placePane;
    private JScrollPane clientPane;
    
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    private UpdateClientTable uct; //고객 테이블 갱신 스레드
    
    private int width = 0, height = 0, pay = 0;

    GUIMain(){
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000, 800);
        this.setVisible(true);
        uct = new UpdateClientTable(this, clientTable); 
        uct.start();
    }

    public void formDesign() {
    	try {
        	BufferedReader br = new BufferedReader(new FileReader("관리자 데이터 파일.txt"));

        	String widthStr = br.readLine();  //데이터 파일에서 문자열 추출
        	String heightStr = br.readLine();
        	String payStr = br.readLine();
        	
        	width = Integer.parseInt(widthStr.split(":")[1]); //값 추출
        	height = Integer.parseInt(heightStr.split(":")[1]);
        	pay = Integer.parseInt(payStr.split(":")[1]);
        
        	br.close();
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    	
        this.add(p, BorderLayout.EAST);
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238));
        p.setPreferredSize(new Dimension(500, 800));

        this.add(p2, BorderLayout.WEST);
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190));
        p2.setPreferredSize(new Dimension(500, 800));
        
        emptyLabel.setLocation(330, 20);
        emptyLabel.setSize(400, 100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));

        whiteLabel.setLocation(260, 20);
        whiteLabel.setSize(400, 100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        whiteLabel.setForeground(Color.white);

        existLabel.setLocation(330, 60);
        existLabel.setSize(400, 100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));

        redLabel.setLocation(230, 60);
        redLabel.setSize(400, 100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        redLabel.setForeground(Color.red);
        
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
        
        searchButton.setLocation(350, 375);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 690);
        adminButton.setSize(170, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(300, 690);
        quitButton.setSize(170, 50);
        quitButton.setFont(font);
        
        String[][] clientTableValue = dbc.getTable(); //데이터베이스에서 고객 테이블 값 가져오기
        placeView = new JTable(height, width) {
        	@Override
        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) { // 주차중인 공간 색깔 바꾸기
        		JComponent cell = (JComponent) super.prepareRenderer(renderer, row, column);
        		int k = 0;
        		while(clientTableValue[k][0] != null) {
        			if(clientTableValue[k][2].equals(placeView.getValueAt(row, column).toString())) {
        				cell.setBackground(Color.RED);
        				return cell;
        			}
        			k++;
        		}
        		cell.setBackground(Color.white);
        		return cell;
        	}
        };
        placeView.setRowHeight(87);
        placeView.setTableHeader(null);
        placeView.setEnabled(false); 
        placeView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        placeView.setFont(font);
        placeView.setBackground(Color.white);
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		placeView.getColumnModel().getColumn(j).setPreferredWidth(87);  //너비 설정
            	placeView.getColumnModel().getColumn(j).setCellRenderer(dtcr); //셀 가운데 정렬
        		placeView.setValueAt("" + (char)(65+i) + (j+1), i, j); //각 셀에 값 넣기
        	}
        }		        
        
        placePane = new JScrollPane(placeView);
        placePane.setLocation(17, 130);
        placePane.setSize(450, 539);
        placePane.getViewport().setBackground(new Color(113, 135, 190));
        placePane.setBorder(BorderFactory.createEmptyBorder());
        
        clientTable.setRowHeight(40);
        DefaultTableModel clientModel = (DefaultTableModel) clientTable.getModel();
        int i = 0;
        while(clientTableValue[i][0] != null) {
        	int diffTime = diffTime(clientTableValue[i][1]);
        	String parkTime = "" + (diffTime / 60) + "시간 " + (diffTime % 60) + "분";
        	clientModel.addRow(new Object[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], ((diffTime/15 + 1) * (pay/4))}); //테이블에 값 추가
        	i++;
        }
        
        clientPane = new JScrollPane(clientTable);
        clientPane.setLocation(25, 160);
        clientPane.setSize(450, 200);

        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(placePane);
        
        p.add(clientLabel);
        p.add(clientPane);
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
    }

    private void eventListner() {
        parkButton.addActionListener(new ActionListener() { //주차하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable();
        		int row = 0;
        		while(clientTableValue[row][0] != null) //고객 테이블 행 수 확인
        			row++;
        		if(row == width * height) { //주차장이 가득 차 있다면
        			JOptionPane.showMessageDialog(null, "현재 주차장이 가득 차서 주차가 불가능합니다");
        		}else {
        			uct.interrupt();
        			dispose();
            		new GUIParking(); //주차하기 화면으로
        		}
        	}
        });
        
        payButton.addActionListener(new ActionListener() { //결제하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIPayCarChoice(pay); //결제할 차량 번호 입력 화면으로 
        	}
        });
        
        searchButton.addActionListener(new ActionListener() { //검색 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable();
        		int i = 0;
        		while(clientTableValue[i][0] != null) {
        			if(clientTableValue[i][0].equals(carNumText.getText()) || clientTableValue[i][2].equals(placeNumText.getText())) { //고객 테이블의 점보와 입력창에 적힌 번호를 비교하여 맞으면 결과값을 메시지로 반환
        				int diffTime = diffTime(clientTableValue[i][1]);
        	        	String parkTime = "" + (diffTime / 60) + "시간 " + (diffTime % 60) + "분";
        	        	uct.interrupt();
        				dispose();
                		new GUISearch(new String[]{clientTableValue[i][0], parkTime, clientTableValue[i][2], "" + ((diffTime/15 + 1) * (pay/4))}); //검색한 차량 정보 화면으로
                		return;
        			}
        			i++;
        		}
        		JOptionPane.showMessageDialog(null, "해당 번호나 위치에 해당하는 차량이 없습니다");
        	}
        });
        
        adminButton.addActionListener(new ActionListener() { //관리자 설정 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(2); //관리자 로그인 화면으로
        	}
        });
        
        quitButton.addActionListener(new ActionListener() { //시스템 종료 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		uct.interrupt();
        		dispose();
        		new GUIAdminLogin(3); //시스템 종료 화면으로
        	}
        });
    }
    
    public static int diffTime(String parkTime) { // 시간 차이 계산
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //시간 포맷 설정
    	try {
    		Date parkDate = format.parse(parkTime); //주차시간을 Date 타입으로 전환
    		cal.setTime(parkDate); //캘린더 시간 설정
    		long diffTime = (System.currentTimeMillis() - cal.getTimeInMillis()) / 1000 / 60; //시간 차이를 분 단위로 계산
    		return (int)diffTime;
    	}catch(Exception e) { 
    		System.out.println(e.getMessage());
    		return -1;
    	}
    }

    public static void main(String[] args) { //실행 테스트를 위한 코드
        new GUIMain();
    }
}