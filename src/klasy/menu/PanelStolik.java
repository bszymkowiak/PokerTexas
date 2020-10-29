package klasy.menu;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelStolik extends JPanel {
    private File imageFile;
    private BufferedImage image;
    private Stolik stolik;
    private JButton fold;
    private JButton check;
    private JButton bet;
    private PanelStolik me;
    private Image obraz;
    private Image gracz0k1;
    private Image gracz0k2;
    private Image gracz1k1;
    private Image gracz1k2;
    private Image gracz2k1;
    private Image gracz2k2;
    private Image gracz3k1;
    private Image gracz3k2;
    private Image gracz4k1;
    private Image gracz4k2;
    private Image gracz5k1;
    private Image gracz5k2;
    Image kartaF1;
    Image kartaF2;
    Image kartaF3;
    Image kartaT;
    Image kartaR;


    private Rozgrywka rozgrywka = new Rozgrywka();

    public Image zapiszObraz(int i) {

        Kolor kolorKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci(numerKartyWReku, kolorKartyWReku);
    }

    private Image zwrocZdjecieKartyOKolorzeIWartosci(Wartosc numerKarty, Kolor kolor) {
        return new ImageIcon("zdjecia\\karty\\" + kolor.toString().toLowerCase() + "\\" + numerKarty.getWartosc() + ".jpg").getImage();
    }

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

        check = new JButton(new ImageIcon("zdjecia\\check.jpg"));
        check.setBounds(1576, 930, 134, 80);
        check.setBackground(Color.GRAY);
        check.setFont(new Font("SansSerif", Font.BOLD, 25));
        check.setBorder(obramowanie);
        add(check);

        bet = new JButton(new ImageIcon("zdjecia\\bet.jpg"));
        bet.setBounds(1730, 930, 134, 80);
        bet.setBackground(Color.GRAY);
        bet.setFont(new Font("SansSerif", Font.BOLD, 25));
        bet.setBorder(obramowanie);
        add(bet);

        JSlider pasekPuli = new JSlider();
        pasekPuli.setBounds(1576, 890, 134, 30);
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


        gracz0k1 = new ImageIcon(zapiszObraz(0)).getImage();
        gracz0k2 = new ImageIcon(zapiszObraz(1)).getImage();
        gracz1k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz1k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz2k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz2k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz3k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz3k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz4k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz4k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz5k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        gracz5k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
        kartaF1 = new ImageIcon(zapiszObraz(2)).getImage();
        kartaF2 = new ImageIcon(zapiszObraz(3)).getImage();
        kartaF3 = new ImageIcon(zapiszObraz(4)).getImage();
        kartaR = new ImageIcon(zapiszObraz(5)).getImage();
        kartaT = new ImageIcon(zapiszObraz(6)).getImage();


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
        g.setColor(Color.WHITE);
        var mess1 = "No Limit Poker Texas";
        g.drawString(mess1, 855, 300);
        var mess2 = "Ciemne: 10$ / 20$ ";
        g.drawString(mess2, 875, 325);

        // Trzeba dodać warunki
        g.setColor(Color.WHITE);
        if (rozgrywka.getLiczbaGraczy() == 2) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawImage(gracz0k1, 1375, 731, null);
            g.drawImage(gracz0k2, 1449, 731, null);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
            dodajKartyStol(g);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);

        } else if (rozgrywka.getLiczbaGraczy() == 3) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawString(rozgrywka.getGracze().get(2).getNick(), 70, 525);
            g.drawImage(gracz0k1, 1375, 731, null);
            g.drawImage(gracz0k2, 1449, 731, null);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
            g.drawImage(gracz2k1, 60, 387, null);
            g.drawImage(gracz2k2, 134, 387, null);
            dodajKartyStol(g);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 115, 562);

        } else if (rozgrywka.getLiczbaGraczy() == 4) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawString(rozgrywka.getGracze().get(2).getNick(), 70, 525);
            g.drawString(rozgrywka.getGracze().get(3).getNick(), 425, 183);
            g.drawImage(gracz0k1, 1375, 731, null);
            g.drawImage(gracz0k2, 1449, 731, null);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
            g.drawImage(gracz2k1, 60, 387, null);
            g.drawImage(gracz2k2, 134, 387, null);
            g.drawImage(gracz3k1, 405, 45, null);
            g.drawImage(gracz3k2, 479, 45, null);
            dodajKartyStol(g);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 115, 562);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(3).getIloscZetonow()), 460, 220);

        } else if (rozgrywka.getLiczbaGraczy() == 5) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawString(rozgrywka.getGracze().get(2).getNick(), 70, 525);
            g.drawString(rozgrywka.getGracze().get(3).getNick(), 425, 183);
            g.drawString(rozgrywka.getGracze().get(4).getNick(), 1400, 183);
            g.drawImage(gracz0k1, 1375, 731, null);
            g.drawImage(gracz0k2, 1449, 731, null);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
            g.drawImage(gracz2k1, 60, 387, null);
            g.drawImage(gracz2k2, 134, 387, null);
            g.drawImage(gracz3k1, 405, 45, null);
            g.drawImage(gracz3k2, 479, 45, null);
            g.drawImage(gracz4k1, 1375, 45, null);
            g.drawImage(gracz4k2, 1449, 45, null);
            dodajKartyStol(g);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 115, 562);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(3).getIloscZetonow()), 460, 220);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(4).getIloscZetonow()), 1430, 220);

        } else if (rozgrywka.getLiczbaGraczy() == 6) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawString(rozgrywka.getGracze().get(2).getNick(), 70, 525);
            g.drawString(rozgrywka.getGracze().get(3).getNick(), 425, 183);
            g.drawString(rozgrywka.getGracze().get(4).getNick(), 1400, 183);
            g.drawString(rozgrywka.getGracze().get(5).getNick(), 1750, 525);
            g.drawImage(zapiszObraz(0), 1375, 731, null);
            g.drawImage(zapiszObraz(1), 1449, 731, null);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
            g.drawImage(gracz2k1, 60, 387, null);
            g.drawImage(gracz2k2, 134, 387, null);
            g.drawImage(gracz3k1, 405, 45, null);
            g.drawImage(gracz3k2, 479, 45, null);
            g.drawImage(gracz4k1, 1375, 45, null);
            g.drawImage(gracz4k2, 1449, 45, null);
            g.drawImage(gracz5k1, 1720, 387, null);
            g.drawImage(gracz5k2, 1794, 387, null);
            dodajKartyStol(g);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 115, 562);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(3).getIloscZetonow()), 460, 220);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(4).getIloscZetonow()), 1430, 220);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(5).getIloscZetonow()), 1775, 562);

//


//            g.drawImage(kartaF1, 777, 487, null);
//            g.drawImage(kartaF2, 851, 487, null);
//            g.drawImage(kartaF3, 925, 487, null);
//            g.drawImage(kartaT, 999, 487, null);
//            g.drawImage(kartaR, 1073, 487, null);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 1430, 220);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 460, 220);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(3).getIloscZetonow()), 115, 562);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(4).getIloscZetonow()), 1775, 562);
//            g.drawString(String.valueOf(rozgrywka.getGracze().get(5).getIloscZetonow()), 460, 907);


        }


    }

    public void dodajKartyStol(Graphics g) {

        g.drawImage(kartaF1, 777, 487, null);
        g.drawImage(kartaF2, 851, 487, null);
        g.drawImage(kartaF3, 925, 487, null);
        g.drawImage(kartaT, 999, 487, null);
        g.drawImage(kartaR, 1073, 487, null);

    }
}

