import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain extends JFrame {
    private JPanel p = new JPanel();
    private JPanel p2 = new JPanel();
    private JButton locationSearch = new JButton("±â»ç´Ô");
    private JButton numberSearch = new JButton("¼Õ´Ô");
    Font font = new Font("°íµñ", Font.BOLD, 30);

    private JLabel emptyLabel = new JLabel("= ºó °ø°£");
    private JLabel whiteLabel = new JLabel("Èò»ö");
    private JLabel existLabel = new JLabel("= Âù °ø°£");
    private JLabel redLabel = new JLabel("»¡°£»ö");

    DefaultTableModel dtm = new DefaultTableModel(8, 5);
    JTable placeView = new JTable(dtm);

    JScrollPane placePane = new JScrollPane(placeView);

    GUIMain(){
        this.setTitle("¹«ÀÎ ÁÖÂ÷ °ü¸® ½Ã½ºÅÛ");
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

        this.add(p2, BorderLayout.WEST);
        p2.setLayout(null);
        p2.setBackground(new Color(113, 135, 190));
        p2.setPreferredSize(new Dimension(500, 1000));

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

        placeView.setRowHeight(40);

        placePane.setLocation(25, 130);
        placePane.setSize(450, 700);

        p2.add(emptyLabel);
        p2.add(whiteLabel);
        p2.add(existLabel);
        p2.add(redLabel);
        p2.add(placePane);
    }

    private void eventListner() {
        /*
        driver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(driver.getText() == "±â»ç´Ô"){
                    label.setText("±â»ç´Ô ¹öÆ° Å¬¸¯µÊ");
                    dispose();
                }else{
                    label.setText("");
                }
            }
        });

         */
    }


    public static void main(String[] args) {
        new GUIMain();
    }
}