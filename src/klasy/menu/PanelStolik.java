package klasy.menu;
import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelStolik extends JPanel implements ActionListener{
    private File imageFile;
    private BufferedImage image;
    private Stolik stolik;
    private JButton fold;
    private JButton check;
    private JButton bet;
    private PanelStolik me;
    Image gracz1k1;
    Image gracz1k2;
    Image gracz2k1;
    Image gracz2k2;
    Image gracz3k1;
    Image gracz3k2;
    Image gracz4k1;
    Image gracz4k2;
    Image gracz5k1;
    Image gracz5k2;
    Image gracz6k1;
    Image gracz6k2;
    Image kartaF1;
    Image kartaF2;
    Image kartaF3;
    Image kartaT;
    Image kartaR;

    private Rozgrywka rozgrywka = new Rozgrywka();

    public PanelStolik(Stolik stolik) {

        super();
        me = this;
        this.stolik = stolik;
        setLayout(null);
        Border obramowanie = BorderFactory.createEmptyBorder();

        rozgrywka.setLiczbaGraczy(stolik.getLiczbaGraczy());

        rozgrywka.dodajGraczy();

        rozgrywka.getGracze().get(0).setNick(stolik.getImieGracza());


        rozgrywka.symulacja();



        dodajTlo();
        dodajPrzyciski(obramowanie);
//        dodajImionaGraczy();
        dodajPolaGraczy();


//        JButton allIN = new JButton("ALL IN" );
//        allIN.setBounds(1730,930, 134,80);
//        allIN.setBackground(Color.RED);
//        allIN.setFont(new Font ("SansSerif", Font.BOLD, 25));
//        add(allIN);


    }

    private void dodajPrzyciski(Border obramowanie) {
        fold = new JButton(new ImageIcon("zdjecia\\fold.jpg"));
        fold.setBounds(1422, 930, 134, 80);
        fold.setBackground(Color.GRAY);
        fold.setFont(new Font("SansSerif", Font.BOLD, 25));
        fold.setBorder(obramowanie);
        add(fold);

        check = new JButton( new ImageIcon("zdjecia\\check.jpg"));
        check.setBounds(1576, 930, 134, 80);
        check.setBackground(Color.GRAY);
        check.setFont(new Font("SansSerif", Font.BOLD, 25));
        check.setBorder(obramowanie);
        add(check);

        bet = new JButton( new ImageIcon("zdjecia\\bet.jpg"));
        bet.setBounds(1730, 930, 134, 80);
        bet.setBackground(Color.GRAY);
        bet.setFont(new Font("SansSerif", Font.BOLD, 25));
        bet.setBorder(obramowanie);
        add(bet);

        JSlider pasekPuli = new JSlider();
        pasekPuli.setBounds(1422, 890, 288, 30);
        pasekPuli.setBackground(Color.DARK_GRAY);
        add(pasekPuli);

        JList wpisPuli = new JList();
        wpisPuli.setBounds(1730, 890, 134, 30);
        add(wpisPuli);

        JButton lobby = new JButton("LOBBY");
        lobby.setBackground(Color.GRAY);
        lobby.setBounds(20, 20, 100, 40);
        lobby.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(lobby);
    }

    private void dodajPolaGraczy() {


        gracz1k1 = new ImageIcon("zdjecia\\QC.jpg").getImage();
        gracz1k2 = new ImageIcon("zdjecia\\QD.jpg").getImage();
        gracz2k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz2k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz3k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz3k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz4k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz4k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz5k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz5k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz6k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz6k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaF1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaF2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaF3 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaR = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaT = new ImageIcon("zdjecia\\Green_back.jpg").getImage();


//        JList gracz2 = new JList();
//        gracz2.setBounds(1530, 440, 250, 95);
//        add(gracz2);
//
//        JList gracz3 = new JList();
//        gracz3.setBounds(1450, 700, 250, 95);
//        add(gracz3);
//
//        JList gracz4 = new JList();
//        gracz4.setBounds(995, 830, 250, 95);
//
//        add(gracz4);
//
//        JList gracz5 = new JList();
//        gracz5.setBounds(650, 830, 250, 95);
//
//        add(gracz5);
//
//        JList gracz6 = new JList();
//        gracz6.setBounds(220, 700, 250, 95);
//        add(gracz6);

    }

//    private void dodajImionaGraczy(){
//
//        JFormattedTextField gracz1 = new JFormattedTextField();
//        gracz1.setText(rozgrywka.getGracze().get(1).getNick());
//        gracz1.setBounds(1270, 80, 250, 50);
//        add(gracz1);
//
//    }

    private void dodajTlo() {

        imageFile = new File("zdjecia\\stol_final.jpg");

        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1920, 1080);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        g.setFont(f);
        g.setColor( Color.WHITE );
        var mess1 = "No Limit Poker Texas";
        g.drawString(mess1, 855, 300);
        var mess2 = "Ciemne: 10$ / 20$ ";
        g.drawString(mess2, 875, 325);

        // Trzeba dodać warunki
        g.setColor(Color.WHITE);
        g.drawString(rozgrywka.getGracze().get(4).getNick(), 1750, 525);
        g.drawString(rozgrywka.getGracze().get(1).getNick(), 1400, 183);
        g.drawString(rozgrywka.getGracze().get(2).getNick(),1400, 870);
        g.drawString(rozgrywka.getGracze().get(3).getNick(),70, 525);
        g.drawString(rozgrywka.getGracze().get(0).getNick(),425, 183);
        g.drawString(rozgrywka.getGracze().get(5).getNick(),425, 870);
        g.drawImage(gracz1k1, 405,45,null);
        g.drawImage(gracz1k2, 479,45,null);
        g.drawImage(gracz2k1, 1375,45,null);
        g.drawImage(gracz2k2, 1449,45,null);
        g.drawImage(gracz3k1, 1375,731,null);
        g.drawImage(gracz3k2, 1449,731,null);
        g.drawImage(gracz4k1, 60,387,null);
        g.drawImage(gracz4k2, 134,387,null);
        g.drawImage(gracz5k1, 1720,387,null);
        g.drawImage(gracz5k2, 1794,387,null);
        g.drawImage(gracz6k1, 405,731,null);
        g.drawImage(gracz6k2, 479,731,null);
        g.drawImage( kartaF1, 777,487,null);
        g.drawImage( kartaF2, 851,487,null);
        g.drawImage( kartaF3, 925,487,null);
        g.drawImage( kartaT, 999,487,null);
        g.drawImage( kartaR, 1073,487,null);





    }


    @Override
    public void actionPerformed(ActionEvent e) {



    }
}

