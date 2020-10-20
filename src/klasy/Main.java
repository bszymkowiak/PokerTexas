package klasy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Stolik extends JPanel {

    DefaultListModel<String> model;
    JTextField input;
    private BufferedImage image;

    public Stolik() {
        super();
        setLayout(null);


        File imageFile = new File("src\\poker\\zdjecia\\stol.jpg");

        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
        Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(dimension);


        JButton fold = new JButton("FOLD" );
        fold.setBounds(1422,930, 134,80);
        fold.setBackground(Color.GRAY);
        fold.setFont(new Font ("SansSerif", Font.BOLD, 25));
        add(fold);

        JButton check = new JButton("CHECK" );
        check.setBounds(1576,930, 134,80);
        check.setBackground(Color.GRAY);
        check.setFont(new Font ("SansSerif", Font.BOLD, 25));
        add(check);

        JButton bet = new JButton("BET" );
        bet.setBounds(1730,930, 134,80);
        bet.setBackground(Color.GRAY);
        bet.setFont(new Font ("SansSerif", Font.BOLD, 25));
        add(bet);

        JSlider pasekPuli = new JSlider();
        pasekPuli.setBounds(1422,890,288, 30);
        pasekPuli.setBackground(Color.GRAY);
        add(pasekPuli);

        JList wpisPuli = new  JList();
        wpisPuli.setBounds(1730,890, 134,30);
        add(wpisPuli);

        JList gracz1 = new JList();
        gracz1.setBounds(1270,80,250,95);
        gracz1.setBackground(SystemColor.DARK_GRAY);
        add(gracz1);

        JList gracz2 = new JList();
        gracz2.setBounds(1530,440,250,95);
        gracz2.setBackground(SystemColor.DARK_GRAY);
        add(gracz2);

        JList gracz3 = new JList();
        gracz3.setBounds(1450,700,250,95);
        gracz3.setBackground(SystemColor.DARK_GRAY);
        add(gracz3);

        JList gracz4 = new JList();
        gracz4.setBounds(995,830,250,95);
        gracz4.setBackground(SystemColor.DARK_GRAY);
        add(gracz4);

        JList gracz5 = new JList();
        gracz5.setBounds(650,830,250,95);
        gracz5.setBackground(SystemColor.DARK_GRAY);
        add(gracz5);

        JList gracz6 = new JList();
        gracz6.setBounds(220,700,250,95);
        gracz6.setBackground(SystemColor.DARK_GRAY);
        add(gracz6);

        JList gracz7 = new JList();
        gracz7.setBounds(135,440,250,95);
        gracz7.setBackground(SystemColor.DARK_GRAY);
        add(gracz7);

        JList gracz8 = new JList();
        gracz8.setBounds(400,80,250,95);
        gracz8.setBackground(SystemColor.DARK_GRAY);
        add(gracz8);

        JButton lobby = new JButton("LOBBY");
        lobby.setBackground(Color.GRAY);
        lobby.setBounds(20,20,100,40);
        lobby.setFont(new Font ("SansSerif", Font.BOLD, 18));
        add(lobby);





//        JButton allIN = new JButton("ALL IN" );
//        allIN.setBounds(1730,930, 134,80);
//        allIN.setBackground(Color.RED);
//        allIN.setFont(new Font ("SansSerif", Font.BOLD, 25));
//        add(allIN);


    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1920,1080);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,0,0,this);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD,20);
        g.setFont(f);
        var mess1 = "No Limit Poker Texas";
        g.drawString(mess1,  855,300);
        var mess2 = "Ciemne: 10$ / 20$ ";
        g.drawString(mess2,  875,325);


    }
}

public class Main {

    public static void main(String[] args) {

        //        JFrame window = new JFrame("Poker Texas");
//        window.setVisible(true);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.add(new Stolik());
//
//        window.pack();

        Rozgrywka rozgrywka = new Rozgrywka();

//        rozgrywka.dodajGraczy();
//        System.out.println(rozgrywka.getGracze().size());
//        System.out.println(rozgrywka.getGracze());

        System.out.println(rozgrywka.getTaliaKart().getTaliaKart().size());
        rozgrywka.dodajGraczy();
        rozgrywka.rozdajKartyDoReki();
        System.out.println(rozgrywka.getTaliaKart().getTaliaKart().size());

        rozgrywka.wyswietlGraczy();
    }
}
