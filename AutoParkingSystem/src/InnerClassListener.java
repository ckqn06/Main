

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
 
public class InnerClassListener extends JFrame {
    InnerClassListener() {
        setTitle("Ding.wav");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        JButton btn = new JButton("Action");
        btn.addActionListener(new MyActionListener());
        add(btn);
    }
 
    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("Action"))
                b.setText("�׼�");
            else
                b.setText("Action");
            // InnerClassListener�� ����� JFrame�� ��� ȣ��
            setTitle(b.getText()); // JFrame.setTitle() ȣ��
        }
    }
 
    public static void main(String[] args) {
        new InnerClassListener();
    }
}