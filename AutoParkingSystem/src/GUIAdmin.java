import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.io.*;

public class GUIAdmin extends JFrame{
	private JPanel p = new JPanel();
	private Font font = new Font("맑은 고딕", Font.BOLD, 40); //폰트 설정
	
	private JLabel Label = new JLabel("▶ 이곳은 관리자 전용 화면입니다."); //화면에 보여주는 메시지
	
	private JButton dataButton = new JButton("관리자 데이터 파일 값 변경"); //관리자 데이터 파일 값 변경 버튼
    private JButton restrictButton = new JButton("주차 제한 구역 설정"); //주차 제한 구역 설정 버튼
    private JButton changeButton = new JButton("관리자 계정 ID/PW 변경"); //관리자 계정 ID/PW 변경 버튼
    private JButton gomainButton = new JButton("메인 화면으로 돌아가기"); //메인 화면으로 돌아가기 버튼
	
    private ParkDBConnection dbc = new ParkDBConnection(); //데이터베이스 연결 객체
    
	GUIAdmin() {
		this.setTitle("무인 주차 관리 시스템");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formDesign();
        this.eventListner();
        this.setSize(1000,800);
        this.setVisible(true);
        setLocationRelativeTo(null);
	}
	
	private void formDesign() {	
    	this.add(p);
        p.setLayout(null);
        
        Label.setLocation(30, 40);
        Label.setSize(900, 100);
        Label.setHorizontalAlignment(SwingConstants.CENTER);
        Label.setFont(font);
        
        dataButton.setLocation(185, 160);
        dataButton.setSize(600, 80);
        dataButton.setFont(font);
        
        restrictButton.setLocation(185, 310);
        restrictButton.setSize(600, 80);
        restrictButton.setFont(font);
        
        changeButton.setLocation(185, 460);
        changeButton.setSize(600, 80);
        changeButton.setFont(font);
        
        gomainButton.setLocation(185, 610);
        gomainButton.setSize(600, 80);
        gomainButton.setFont(font);

        p.add(Label);
        p.add(dataButton);
        p.add(restrictButton);
        p.add(changeButton);
        p.add(gomainButton);
    }
	
	private void eventListner() { //버튼 클릭 이벤트 설정
		dataButton.addActionListener(new ActionListener() { //관리자 데이터 파일 값 변경 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		String[][] clientTableValue = dbc.getTable(); //DB파일 내의 고객 테이블을 가져옴
        		
    			if(clientTableValue[0][0] == null) { //현재 주차장에 차가 주차되어있지 않은지 확인
        				dispose(); 
        				new GUIAdminSetting(); //관리자 데이터 파일 값 변경 화면으로 이동
        		}else {
        			JOptionPane.showMessageDialog(null, "현재 고객이 존재하여 설정 변경이 불가능합니다");
        		}
        	}
        });
		
		restrictButton.addActionListener(new ActionListener() { //주차 제한 구역 설정 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //메인 화면으로 이동
        	}
        });
		
		changeButton.addActionListener(new ActionListener() { //관리자 계정 ID/PW 변경 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIChangeLogin(); //관리자 계정 ID/PW 변경 화면으로 이동
        	}
        });
		
		gomainButton.addActionListener(new ActionListener() { //메인 화면으로 돌아가기 버튼 클릭 시 실행
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new GUIMain(); //메인 화면으로 이동
        	}
        });
    }
	
	public static void main(String[] args) {
		new GUIAdmin();
	}
}