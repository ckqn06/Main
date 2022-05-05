import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel();
    private JPanel p2 = new JPanel();
    
    private JButton parkButton = new JButton("주차하기");  // 주차하기 버튼
    private JButton payButton = new JButton("결제하기");  // 결제하기 버튼
    private JButton searchButton = new JButton("검색");  // 검색 버튼
    private JButton adminButton = new JButton("관리자 설정");  // 관리자 설정 버튼
    private JButton quitButton = new JButton("시스템 종료");  // 시스템 종료 버튼
    
    private Font font = new Font("고딕", Font.BOLD, 30);
    
    private JTextField carNumText = new JTextField(); // 차량 번호 입력 창
    private JTextField placeNumText = new JTextField();   // 위치 번호 입력 창

    private JLabel emptyLabel = new JLabel("= 빈 공간");
    private JLabel whiteLabel = new JLabel("흰색");
    private JLabel existLabel = new JLabel("= 찬 공간");
    private JLabel redLabel = new JLabel("빨간색");
    private JLabel clientLabel = new JLabel("- 현재 이용 중인 고객");
    private JLabel searchLabel = new JLabel("- 차량 데이터 검색");
    private JLabel carNumLabel = new JLabel("차량 번호로 검색:");
    private JLabel placeNumLabel = new JLabel("위치 번호로 검색:");

    private String[] header = {"차량번호", "주차 시간", "현재 위치", "비용"};  // 고객 테이블 헤더
    private String[][] rows = {};
    private DefaultTableModel dtm = new DefaultTableModel(8, 5);
    private JTable placeView = new JTable(dtm);  // 차량 위치 현환 테이블
    private JTable clientTable = new JTable(rows, header);  // 고객 정보 테이블

    private JScrollPane placePane = new JScrollPane(placeView);
    private JScrollPane clientPane = new JScrollPane(clientTable);

    GUIMain(){
        this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,1000);
        this.setVisible(true);
    }

    private void formDesign() {
        this.add(p, BorderLayout.EAST);
        p.setLayout(null);
        p.setBackground(new Color(238, 238, 238));
        p.setPreferredSize(new Dimension(500, 1000));

        this.add(p2, BorderLayout.WEST);
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190));
        p2.setPreferredSize(new Dimension(500, 1000));
        
        parkButton.setLocation(30, 40);
        parkButton.setSize(170, 50);
        parkButton.setFont(font);
        
        payButton.setLocation(300, 40);
        payButton.setSize(170, 50);
        payButton.setFont(font);
        
        searchButton.setLocation(350, 505);
        searchButton.setSize(120, 50);
        searchButton.setFont(font);
        
        adminButton.setLocation(20, 870);
        adminButton.setSize(220, 50);
        adminButton.setFont(font);
        
        quitButton.setLocation(260, 870);
        quitButton.setSize(220, 50);
        quitButton.setFont(font);

        emptyLabel.setLocation(330,10);
        emptyLabel.setSize(400,100);
        emptyLabel.setVerticalAlignment(SwingConstants.TOP);
        emptyLabel.setFont(font);

        whiteLabel.setLocation(260,10);
        whiteLabel.setSize(400,100);
        whiteLabel.setVerticalAlignment(SwingConstants.TOP);
        whiteLabel.setFont(font);
        whiteLabel.setForeground(Color.white);

        existLabel.setLocation(330,50);
        existLabel.setSize(400,100);
        existLabel.setVerticalAlignment(SwingConstants.TOP);
        existLabel.setFont(font);

        redLabel.setLocation(230,50);
        redLabel.setSize(400,100);
        redLabel.setVerticalAlignment(SwingConstants.TOP);
        redLabel.setFont(font);
        redLabel.setForeground(Color.red);
        
        clientLabel.setLocation(30,100);
        clientLabel.setSize(400,100);
        clientLabel.setFont(font.deriveFont(35f));
        
        searchLabel.setLocation(30,480);
        searchLabel.setSize(400,100);
        searchLabel.setFont(font.deriveFont(35f));
        
        carNumLabel.setLocation(30,530);
        carNumLabel.setSize(400,100);
        carNumLabel.setFont(font.deriveFont(35f));
        
        placeNumLabel.setLocation(30,670);
        placeNumLabel.setSize(400,100);
        placeNumLabel.setFont(font.deriveFont(35f));
        
        carNumText.setLocation(30, 610);
        carNumText.setSize(400, 70);
        carNumText.setFont(font);
        
        placeNumText.setLocation(30, 750);
        placeNumText.setSize(400, 70);
        placeNumText.setFont(font);

        placeView.setRowHeight(40);

        placePane.setLocation(17, 130);
        placePane.setSize(450, 700);
        placePane.getViewport().setBackground(new Color(113, 135, 190));
        placePane.setBorder(BorderFactory.createEmptyBorder());
        
        clientTable.setRowHeight(40);
        
        clientPane.setLocation(25, 180);
        clientPane.setSize(450, 250);
        
        
        
        p.add(parkButton);
        p.add(payButton);
        p.add(searchButton);
        p.add(adminButton);
        p.add(quitButton);
        p.add(clientPane);
        p.add(clientLabel);
        p.add(searchLabel);
        p.add(carNumLabel);
        p.add(placeNumLabel);
        p.add(carNumText);
        p.add(placeNumText);

        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(placePane);
    }

    private void eventListner() {
        parkButton.addActionListener(new ActionListener() {  // 주차하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIParking();  // 주차하기 화면으로
        	}
        });
        
        payButton.addActionListener(new ActionListener() {  // 결제하기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIPayCarChoice();  // 결제할 차량 번호 입력 화면으로 
        	}
        });
        
        searchButton.addActionListener(new ActionListener() {  // 검색 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		// 고객 테이블의 점보와 입력창에 적힌 번호를 비교하여 맞으면 결과값을 메시지로 반환
        	}
        });
        
        adminButton.addActionListener(new ActionListener() {  // 관리자 설정 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		
        		new GUIAdminLogin();  // 관리자 로그인 화면으로
        	}
        });
        
        quitButton.addActionListener(new ActionListener() {  // 시스템 종료 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		int result = JOptionPane.showConfirmDialog(null, "정말로 시스템을 종료하시겠습니까?", "시스템 종료", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {  // Yes를 선택할 경우
        			System.exit(0);
        		}
        	}
        });
    }


    public static void main(String[] args) { // 실행 테스트를 위한 코드
        new GUIMain();
    }
}