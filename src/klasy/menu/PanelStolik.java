package klasy.menu;
import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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
    Rozgrywka rozgrywka = new Rozgrywka();

    public PanelStolik(Stolik stolik) {

        super();
        me = this;
        this.stolik = stolik;
        setLayout(null);
        Border obramowanie = BorderFactory.createEmptyBorder();

        rozgrywka.setLiczbaGraczy(stolik.getLiczbaGraczy());

        rozgrywka.symulacja();

        dodajTlo();
        dodajPrzyciski(obramowanie);
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
        pasekPuli.setBackground(Color.GRAY);
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
        JList gracz1 = new JList();
        gracz1.setBounds(1270, 80, 250, 95);
        gracz1.setBackground(SystemColor.DARK_GRAY);
        add(gracz1);

        JList gracz2 = new JList();
        gracz2.setBounds(1530, 440, 250, 95);
        gracz2.setBackground(SystemColor.DARK_GRAY);
        add(gracz2);

        JList gracz3 = new JList();
        gracz3.setBounds(1450, 700, 250, 95);
        gracz3.setBackground(SystemColor.DARK_GRAY);
        add(gracz3);

        JList gracz4 = new JList();
        gracz4.setBounds(995, 830, 250, 95);
        gracz4.setBackground(SystemColor.DARK_GRAY);
        add(gracz4);

        JList gracz5 = new JList();
        gracz5.setBounds(650, 830, 250, 95);
        gracz5.setBackground(SystemColor.DARK_GRAY);
        add(gracz5);

        JList gracz6 = new JList();
        gracz6.setBounds(220, 700, 250, 95);
        gracz6.setBackground(SystemColor.DARK_GRAY);
        add(gracz6);

        JList gracz7 = new JList();
        gracz7.setBounds(135, 440, 250, 95);
        gracz7.setBackground(SystemColor.DARK_GRAY);
        add(gracz7);

        JList gracz8 = new JList();
        gracz8.setBounds(400, 80, 250, 95);
        gracz8.setBackground(SystemColor.DARK_GRAY);
        add(gracz8);
    }

    private void dodajTlo() {

        imageFile = new File("zdjecia\\stol nowy.jpg");

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
        var mess1 = "No Limit Poker Texas";
        g.drawString(mess1, 855, 300);
        var mess2 = "Ciemne: 10$ / 20$ ";
        g.drawString(mess2, 875, 325);


    }


    @Override
    public void actionPerformed(ActionEvent e) {



    }
}

