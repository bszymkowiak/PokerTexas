package klasy.menu;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.BazaDanych;
import klasy.Gracz;
import klasy.Rozgrywka;
import klasy.karty.TaliaKart;
import klasy.menu.panelKoncowy.PanelKoncowy;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelStolik extends JPanel implements ActionListener {
    private File imageFile;
    private BufferedImage image;
    private Stolik stolik;
    private JButton fold;
    private JButton check;
    private JButton bet;
    private JButton graj;
    private JButton nastepnyGracz;

    private JTextArea historia;
    private PanelStolik me;
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
    private Image smallBlind;
    private Image bigBlind;
    private Image dealer;
    private int wartoscTmp;

    private boolean pierwszyObrot = true;
    private boolean licytacjaWGrze;
    private boolean czyZostalJedenGracz;
    private boolean pierwszyObrotTurn;
    private boolean pierwszyObrotRiver;
    private boolean pierwszyObrotOstatnia;
    private boolean isLicytacjaWGrzeFlop;
    private boolean kartyFlop;
    private boolean kartaTurn;
    private boolean kartaRiver;
    private boolean drugaLicytacja;
    private boolean isLicytacjaWGrzeTurn;
    private boolean isLicytacjaWGrzeRiver;
    private boolean isLicytacjaWGrzeOstatnia;


    boolean pierwszaTura = true;
    private int ruchGraczaTmp;


    String kolorRewers;

    Border obramowanie = BorderFactory.createEmptyBorder();

    private Rozgrywka rozgrywka;

    int betB;
    int iloscMoichZetonow;
    JTextField mojeZetony;
    private int iloscRuchowNaFlopie;
    private int iloscRuchowNaTurn;
    private int iloscGraczyWGrze;
    private int iloscRuchowNaRiver;
    private int iloscRuchowOstatnia;
    private int numerGracza;

    private Gracz naszGracz;

    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
    private boolean graczeZKartami;

    private void dodajPrzyciski(Border obramowanie) {

        fold = new JButton(new ImageIcon("zdjecia\\fold.jpg"));
        fold.setBounds(1422, 930, 134, 80);
        fold.setBackground(Color.GRAY);
        fold.setFont(new Font("SansSerif", Font.BOLD, 25));
        fold.setBorder(obramowanie);
        add(fold);
        fold.setVisible(false);
        przyciskFoldAkcja();

        check = new JButton(new ImageIcon("zdjecia\\check.jpg"));
        check.setBounds(1576, 930, 134, 80);
        check.setBackground(Color.GRAY);
        check.setFont(new Font("SansSerif", Font.BOLD, 25));
        check.setBorder(obramowanie);
        add(check);
        check.setVisible(false);

        bet = new JButton(new ImageIcon("zdjecia\\bet.jpg"));
        bet.setBounds(1730, 930, 134, 80);
        bet.setBackground(Color.GRAY);
        bet.setFont(new Font("SansSerif", Font.BOLD, 25));
        bet.setBorder(obramowanie);
        add(bet);
        bet.setVisible(false);
        przyciskBetAkcja();

        graj = new JButton(new ImageIcon("zdjecia\\graj2.jpg"));
        graj.setBounds(877, 686, 243, 83);
        graj.setBorder(obramowanie);
        add(graj);
        graj.setVisible(false);
        graj.addActionListener(this);

        historia = new JTextArea();
        historia.setBounds( 10,840, 355,195);
        historia.setBorder(null);
        historia.setFont(new Font("SansSerif", Font.BOLD, 13));
        historia.setEditable(false);
        historia.setOpaque(false);
        historia.setForeground(Color.WHITE);


        JScrollPane scroll = new JScrollPane(historia);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10,840, 355,195);
        scroll.setBorder(null);

        add(scroll);

        nastepnyGracz = new JButton(new ImageIcon("zdjecia\\ruchGracza.jpg"));
        nastepnyGracz.setBounds(798, 930, 324, 87);
        nastepnyGracz.setBackground(Color.GRAY);
        nastepnyGracz.setFont(new Font("SansSerif", Font.BOLD, 25));
        nastepnyGracz.setBorder(obramowanie);
        add(nastepnyGracz);
        nastepnyGracz.setVisible(false);
        ruchGracza(nastepnyGracz);

        przyciskCheckAkcja();


        mojeZetony = new JTextField(iloscMoichZetonow);

        add(mojeZetony);

    }

    private void ruchGracza(JButton nastepnyGracz) {
        nastepnyGracz.addActionListener(e -> {

            System.out.println("POCZATEK PRZYCISKU RUCHGRACZA" + pierwszyObrot);
            if (!kartyFlop) {

                if (pierwszyObrot) {

                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0) {
                        rozgrywka.ruchGracza(ruchGraczaTmp);
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        historia.append(rozgrywka.doHistorii);
                        if (ruchGraczaTmp == rozgrywka.getPobierzBlind()) {
                            pierwszyObrot = false;
                        }
                        repaint();
                    }

                    ruchGraczaTmp++;


                    if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                        ruchGraczaTmp = 0;
                    }

                    if (ruchGraczaTmp == 0) {
                        System.out.println("czy jestem tutaj");
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);

                        ruchGraczaTmp = 0;

                    }

                } else {

                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0 && rozgrywka.getGracze().
                            get(ruchGraczaTmp).getPulaZetonowGracza() < wartoscTmp) {

                        rozgrywka.ruchGracza(ruchGraczaTmp);

                    }

                }




            }



            System.out.println("KONIEC PRZYCISKU RUCHGRACZA" + pierwszyObrot);

        });
    }

    public PanelStolik(Stolik stolik) {


        super();

        me = this;
        this.stolik = stolik;
        setLayout(null);

        kolorRewers = stolik.kolorRewers;

        setRozgrywka(stolik.getRozgrywka());

        rozgrywka.setLiczbaGraczy(stolik.getRozgrywka().getLiczbaGraczy());

        getRozgrywka().dodajGraczy(stolik.getRozgrywka().getIloscZetonow());

        getRozgrywka().getGracze().get(0).setNick(stolik.getImieGracza());

        dodajTlo();
        dodajPrzyciski(obramowanie);

        getRozgrywka().rozdajKartyDoReki(rozgrywka.getTaliaKart());

        dodajZdjeciaKartGraczy();

        rozgrywka.getStoper().start();

        rozgrywka.setCzasCiemnych(stolik.getRozgrywka().getCzasCiemnych());

        System.out.println(rozgrywka.getCzasCiemnych());

    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }

    public Image zapiszObrazDlaKartStol(int i) {

        Kolor kolorKartyWReku = rozgrywka.getKartyStol().get(i).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getKartyStol().get(i).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci(numerKartyWReku, kolorKartyWReku);

    }

    public Image zapiszObrazDlaGraczy(int i) {

        Kolor kolorKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci(numerKartyWReku, kolorKartyWReku);
    }

    private Image zwrocZdjecieKartyOKolorzeIWartosci(Wartosc numerKarty, Kolor kolor) {
        return new ImageIcon("zdjecia\\karty\\" + kolor.toString().toLowerCase() + "\\" + numerKarty.getWartosc() + ".jpg").getImage();
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
        var mess1 = "Limit Poker Texas";
        g.drawString(mess1, 874, 300);
        var mess2 = "Ciemne: " + rozgrywka.getMalyBlind() + "$ / " + rozgrywka.getDuzyBlind() + "$ ";
        g.drawString(mess2, 875, 325);
        var pula = "Pula: " + rozgrywka.getPulaGlowna() + "$";
        g.drawString(pula, 900, 465);

        dodajKartyStolFlop(g);
        dodajKartyStolTurn(g);
        dodajKartyStolRiver(g);

        g.setColor(Color.WHITE);

        dodaniePolGraczyZeWzgleduNaIchIlosc(g);

        dodanieKartGraczyIZetonow(g);

        dodajZetonSmallBlind(g);
        dodajZetonBibBlind(g);
        dodajZetonDealer(g);

        if (rozgrywka.getGracze().size() == 1) {
            stolik.window.dispose();
            new PanelKoncowy(me);
        }

    }

    private void dodanieKartGraczyIZetonow(Graphics g) {

        if (rozgrywka.getGracze().size() == 2) {
            if (rozgrywka.getGracze().get(0).getKartyWRece().size() == 0) {

                gracz0k1 = null;
                gracz0k2 = null;


                repaint();
            }
            if (rozgrywka.getGracze().get(1).getKartyWRece().size() == 0) {

                gracz1k1 = new ImageIcon().getImage();
                gracz1k2 = new ImageIcon().getImage();

                repaint();

            }
            dodajIloscPostawionychZetonowGracza0(g);
            dodajIloscPostawionychZetonowGracza1(g);
        } else if (rozgrywka.getGracze().size() == 3) {
            if (rozgrywka.getGracze().get(0).getKartyWRece().size() == 0) {

                gracz0k1 = null;
                gracz0k2 = null;


                repaint();
            }
            if (rozgrywka.getGracze().get(1).getKartyWRece().size() == 0) {

                gracz1k1 = new ImageIcon().getImage();

                gracz1k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(2).getKartyWRece().size() == 0) {

                gracz2k1 = new ImageIcon().getImage();
                gracz2k2 = new ImageIcon().getImage();

                repaint();


            }
            dodajIloscPostawionychZetonowGracza0(g);
            dodajIloscPostawionychZetonowGracza1(g);
            dodajIloscPostawionychZetonowGracza2(g);
        } else if (rozgrywka.getGracze().size() == 4) {
            if (rozgrywka.getGracze().get(0).getKartyWRece().size() == 0) {

                gracz0k1 = null;
                gracz0k2 = null;


                repaint();
            }
            if (rozgrywka.getGracze().get(1).getKartyWRece().size() == 0) {

                gracz1k1 = new ImageIcon().getImage();
                gracz1k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(2).getKartyWRece().size() == 0) {

                gracz2k1 = new ImageIcon().getImage();
                gracz2k2 = new ImageIcon().getImage();

                repaint();


            }
            if (rozgrywka.getGracze().get(3).getKartyWRece().size() == 0) {

                gracz3k1 = new ImageIcon().getImage();
                gracz3k2 = new ImageIcon().getImage();

                repaint();

            }
            dodajIloscPostawionychZetonowGracza0(g);
            dodajIloscPostawionychZetonowGracza1(g);
            dodajIloscPostawionychZetonowGracza2(g);
            dodajIloscPostawionychZetonowGracza3(g);
        } else if (rozgrywka.getGracze().size() == 5) {
            if (rozgrywka.getGracze().get(0).getKartyWRece().size() == 0) {

                gracz0k1 = null;
                gracz0k2 = null;


                repaint();
            }
            if (rozgrywka.getGracze().get(1).getKartyWRece().size() == 0) {

                gracz1k1 = new ImageIcon().getImage();
                gracz1k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(2).getKartyWRece().size() == 0) {

                gracz2k1 = new ImageIcon().getImage();
                gracz2k2 = new ImageIcon().getImage();

                repaint();


            }
            if (rozgrywka.getGracze().get(3).getKartyWRece().size() == 0) {

                gracz3k1 = new ImageIcon().getImage();
                gracz3k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(4).getKartyWRece().size() == 0) {

                gracz4k1 = new ImageIcon().getImage();
                gracz4k2 = new ImageIcon().getImage();

                repaint();

            }
            dodajIloscPostawionychZetonowGracza0(g);
            dodajIloscPostawionychZetonowGracza1(g);
            dodajIloscPostawionychZetonowGracza2(g);
            dodajIloscPostawionychZetonowGracza3(g);
            dodajIloscPostawionychZetonowGracza4(g);

        } else if (rozgrywka.getGracze().size() == 6) {
            if (rozgrywka.getGracze().get(0).getKartyWRece().size() == 0) {

                gracz0k1 = null;
                gracz0k2 = null;


                repaint();
            }
            if (rozgrywka.getGracze().get(1).getKartyWRece().size() == 0) {

                gracz1k1 = new ImageIcon().getImage();
                gracz1k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(2).getKartyWRece().size() == 0) {

                gracz2k1 = new ImageIcon().getImage();
                gracz2k2 = new ImageIcon().getImage();

                repaint();


            }
            if (rozgrywka.getGracze().get(3).getKartyWRece().size() == 0) {

                gracz3k1 = new ImageIcon().getImage();
                gracz3k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(4).getKartyWRece().size() == 0) {

                gracz4k1 = new ImageIcon().getImage();
                gracz4k2 = new ImageIcon().getImage();

                repaint();

            }
            if (rozgrywka.getGracze().get(5).getKartyWRece().size() == 0) {

                gracz5k1 = new ImageIcon().getImage();
                gracz5k2 = new ImageIcon().getImage();

                repaint();

            }
            dodajIloscPostawionychZetonowGracza0(g);
            dodajIloscPostawionychZetonowGracza1(g);
            dodajIloscPostawionychZetonowGracza2(g);
            dodajIloscPostawionychZetonowGracza3(g);
            dodajIloscPostawionychZetonowGracza4(g);
            dodajIloscPostawionychZetonowGracza5(g);
        }
            }

    private void przyciskCheckAkcja() {

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                naszGracz = rozgrywka.getGracze().get(0);

                if (!kartyFlop) {
                    naszRuchCheck(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                }
            }
        });




    }

    private void przyciskBetAkcja() {
        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (naszGracz.getKartyWRece().size() != 0) {

                    wartoscTmp = 0;

                    if (!kartyFlop) {

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                                    naszRuchBet( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                repaint();

                            }

                    } else if (!kartaTurn && kartyFlop) {

                        System.out.println("WCHODZE DO KARTA T ");

                        licytacjaWGrze = true;
                        while (licytacjaWGrze) {

                            if(iloscRuchowNaTurn<2){
                                pierwszyObrotTurn=false;
                            }else{
                                pierwszyObrotTurn=true;
                            }

                            if(pierwszyObrotTurn==true ){
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoBigBlindDoNas();



                                }else if (rozgrywka.getPobierzBlind()== 1) {

                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoSmallBlindDoNas();

                                }else {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }

                            }
                            else if (pierwszyObrotTurn==false) {

                                    naszRuchBet( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();

                                repaint();
                            }
                        }
                    } else if (!kartaRiver && kartaTurn && kartyFlop) {

                        System.out.println("WCHODZE DO KARTA R ");

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if(iloscRuchowNaRiver<1){
                                pierwszyObrotRiver=false;
                            }else{
                                pierwszyObrotRiver=true;
                            }

                            if(pierwszyObrotRiver==true ){
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoBigBlindDoNas();


                                }else if (rozgrywka.getPobierzBlind() ==1) {

                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoSmallBlindDoNas();

                                }else {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();

                                }
                            }
                            else if (pierwszyObrotRiver==false) {


                                naszRuchBet( naszGracz );
                                sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();



                                repaint();
                                pierwszyObrot = false;
                            }
                        }

                    } else if (kartaRiver && kartaTurn && kartyFlop) {

                        System.out.println("Juz ostatnia karta na stole ");

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {
                            if(iloscRuchowOstatnia<1){
                                pierwszyObrotOstatnia=false;
                            }else{
                                pierwszyObrotOstatnia=true;
                            }
                            if(pierwszyObrotOstatnia==true ) {
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoBigBlindDoNas();
                                }else if (rozgrywka.getPobierzBlind() == 1) {
                                    naszRuchBet(naszGracz);
                                    ruchGraczyPoSmallBlindDoNas();

                                }else{
                                    naszRuchBet(naszGracz);
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                }
                            }else if(pierwszyObrotOstatnia==false){
                                naszRuchBet(naszGracz);
                                sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                rozgrywka.sprawdzanieKart();
                                historia.append( rozgrywka.wygranaGracza );
                            }



                        }

                    }
                    repaint();
                }
            }
        });
    }

    private void przyciskFoldAkcja() {
        fold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gracz naszGracz = rozgrywka.getGracze().get(0);
                if (naszGracz.getKartyWRece().size() != 0) {

                    System.out.println(pierwszyObrot + "    wartosc pierwszego obrotuuuuu");
                    System.out.println(pierwszyObrotOstatnia + "    wartosc pierwszego obrotuuuuu  OSTATNIA");

                    wartoscTmp = 0;

                    if (kartaF1 == null) {

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if(iloscRuchowNaFlopie<1){
                                pierwszyObrot=false;
                            }else{
                                pierwszyObrot=true;
                            }
                            if (pierwszyObrot==true) {
                                if(rozgrywka.getPobierzBlind()==5){
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoNasDoBigBling();
                                }else if(rozgrywka.getPobierzBlind()==0){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } else{
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoNasDoBigBling();
                                }
                                repaint();




                            }else if(pierwszyObrot==false){
                                naszRuchFold( naszGracz );
                                sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                            }
                        }

                        repaint();
                    } else if (kartaT == null && kartaF1 != null) {

                        System.out.println("WCHODZE DO KARTA T ");

                        licytacjaWGrze = true;
                        while (licytacjaWGrze) {

                            System.out.println(pierwszyObrotTurn + "    wartosc pierwszego obrotuuuuu TURN");
                            if(iloscRuchowNaTurn<2){
                                pierwszyObrotTurn=false;
                            }else{
                                pierwszyObrotTurn=true;
                            }
                            System.out.println(pierwszyObrotTurn + "    wartosc pierwszego obrotuuuuu TURN");

                            if(pierwszyObrotTurn==true ){
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoBigBlindDoNas();



                                }else if (rozgrywka.getPobierzBlind()== 1) {

                                    naszRuchCheck(naszGracz);
                                    ruchGraczyPoSmallBlindDoNas();

                                }else if(rozgrywka.getPobierzBlind()==2){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if(rozgrywka.getPobierzBlind()==5){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if(rozgrywka.getPobierzBlind()==3){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if(rozgrywka.getPobierzBlind()==4){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }

                            }
                            else if (pierwszyObrotTurn==false) {
                                if (rozgrywka.getPobierzBlind() == 1) {

                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();

                                }else if(rozgrywka.getPobierzBlind()==0){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if (rozgrywka.getPobierzBlind() == 2) {
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if (rozgrywka.getPobierzBlind() ==3){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if (rozgrywka.getPobierzBlind() ==4){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }else if (rozgrywka.getPobierzBlind() ==5){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                }

                                repaint();
                            }
                        }
                    } else if (kartaR == null && kartaT != null && kartaF1 != null) {

                        System.out.println("WCHODZE DO KARTA R ");

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            System.out.println(pierwszyObrotRiver + "    wartosc pierwszego obrotuuuuu  RIVER");
                            if(iloscRuchowNaRiver<1){
                                pierwszyObrotRiver=false;
                            }else{
                                pierwszyObrotRiver=true;
                            }
                            System.out.println(pierwszyObrotRiver + "    wartosc pierwszego obrotuuuuu  RIVER");

                            if(pierwszyObrotRiver==true ){
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoBigBlindDoNas();


                                }else if (rozgrywka.getPobierzBlind() ==1) {

                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoSmallBlindDoNas();

                                }else if(rozgrywka.getPobierzBlind()==5){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();

                                }else if(rozgrywka.getPobierzBlind()==2){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();
                                }else if(rozgrywka.getPobierzBlind()==3){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();
                                }else if(rozgrywka.getPobierzBlind()==4){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();
                                }
                            }
                            else if (pierwszyObrotRiver==false) {


                                naszRuchFold( naszGracz );
                                sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();



                                repaint();
                                pierwszyObrot = false;
                            }
                        }

                    } else if (kartaR != null && kartaT != null && kartaF1 != null) {

                        System.out.println("Juz ostatnia karta na stole ");

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {
                            if(iloscRuchowOstatnia<2){
                                pierwszyObrotOstatnia=false;
                            }else{
                                pierwszyObrotOstatnia=true;
                            }
                            if(pierwszyObrotOstatnia==true ) {
                                if (rozgrywka.getPobierzBlind() == 0) {
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoBigBlindDoNas();
                                }else if (rozgrywka.getPobierzBlind() == 1) {
                                    naszRuchFold( naszGracz );
                                    ruchGraczyPoSmallBlindDoNas();

                                }else if(rozgrywka.getPobierzBlind()==5){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();

                                }else if(rozgrywka.getPobierzBlind()==2){
                                    naszRuchFold( naszGracz );
                                    sprawdzenieIPokazanieKart();
                                }else if(rozgrywka.getPobierzBlind()==3){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                }else if(rozgrywka.getPobierzBlind()==4){
                                    naszRuchFold( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                }
                            }else if(pierwszyObrotOstatnia==false){
                                naszRuchFold( naszGracz );
                                sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
//                                rozgrywka.sprawdzanieKart();
                                historia.append( rozgrywka.wygranaGracza );
                                repaint();
                            }



                        }

                    }

                }
            }
        });
    }


    private void ruchGraczyPoBigBlindDoNas()  {

        if (!kartyFlop) {
//            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && rozgrywka.getGracze().get( i ).getPulaZetonowGracza() <wartoscTmp) {
                        if ( i ==5 && rozgrywka.getKtoBlind() == 5 && rozgrywka.getGracze().get(5).getKartyWRece().size() != 0) {
                            rozgrywka.ruchGracza( i );
//                            System.out.println("to jest kurwa tutaj ruch graczy po big blind do nas");
                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                            repaint();
                        } else {
                            rozgrywka.ruchGracza(i);
//                            System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                            historia.append( rozgrywka.doHistorii );
                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                            repaint();
                        }
                    }
                }

            pierwszyObrot = false;
            licytacjaWGrze = false;
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        } else if (!kartaTurn && kartyFlop) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0) {
                        if (i == 5) {
                            if (rozgrywka.getGracze().get(5).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza(5);
//                                System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                                historia.append( rozgrywka.doHistorii );
                                iloscRuchowNaTurn--;
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza(i);
//                            System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                            historia.append( rozgrywka.doHistorii );
                            iloscRuchowNaTurn--;
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrotTurn = false;
            licytacjaWGrze = false;
            repaint();

            sprawdzeniePlusEwWylozenieKartNaStolTURN();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();



        } else if (!kartaRiver && kartaTurn && kartyFlop) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0) {
                        if (i == 5) {
                            if (rozgrywka.getGracze().get(5).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza(i);
//                                System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                                historia.append( rozgrywka.doHistorii );
                                iloscRuchowNaRiver--;
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza(i);
//                            System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                            historia.append( rozgrywka.doHistorii );
                            iloscRuchowNaRiver--;
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrotRiver = false;
            licytacjaWGrze = false;

            sprawdzeniePlusEwWylozenieKartNaStolRiver();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();


        } else if (kartaRiver && kartaTurn && kartyFlop) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0) {
                        if (i == 5) {
                            if (rozgrywka.getGracze().get(5).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza(i);
//                                System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                                historia.append( rozgrywka.doHistorii );
                                iloscRuchowOstatnia--;
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza(i);
//                            System.out.println("ruch gracze w ruchu graczy po big blind do nas");
                            historia.append( rozgrywka.doHistorii );
                            iloscRuchowOstatnia--;
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrotOstatnia = false;
            licytacjaWGrze = false;
            repaint();

            sprawdzenieIPokazanieKart();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        }

    }

    private void ruchGraczyPoNasDoBigBling(){
        for(int i = 1; i <= rozgrywka.getPobierzBlind(); i++){

            if(!kartyFlop){
                rozgrywka.ruchGracza( i );
                historia.append( rozgrywka.doHistorii );
//                System.out.println("ruch graczy po nas do bb");
                iloscRuchowNaFlopie--;
                if(iloscRuchowNaFlopie<1)
                    pierwszyObrot=false;
            }
            if(kartyFlop && !kartaTurn) {
                rozgrywka.ruchGracza( i );
                historia.append( rozgrywka.doHistorii );
//                System.out.println("ruch graczy po nas do bb");
                iloscRuchowNaTurn--;
                if(iloscRuchowNaTurn<1)
                    pierwszyObrotTurn=false;
            }
            if(!kartaRiver && kartaTurn && kartyFlop){
                rozgrywka.ruchGracza( i );
                historia.append( rozgrywka.doHistorii );
//                System.out.println("ruch graczy po nas do bb");
                iloscRuchowNaRiver--;
                if(iloscRuchowNaRiver<1)
                    pierwszyObrotRiver=false;
            }
        }
        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        if(czyZostalJedenGracz==false)
            ruchGraczyPoBigBlindDoNas();
        repaint();
    }

    private void ruchGraczyPoSmallBlindDoNas() {
        if(kartyFlop){
            pierwszyObrot=true;
        }

        if (rozgrywka.getPobierzBlind() == 0 ) {
            for (int i = 5; i <rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(5).getKartyWRece().size() != 0)  {
                    rozgrywka.ruchGracza(5);
                    historia.append( rozgrywka.doHistorii );
//                    System.out.println("pierwszy if w ruchach graczy po small blind");
                    if(!kartyFlop){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartyFlop && !kartaTurn) {
                        iloscRuchowNaTurn--;
                    }
                    if(!kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
            }

        }else if (rozgrywka.getPobierzBlind() == 2 ) {
            for (int i = 1; i <rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0)  {
                    rozgrywka.ruchGracza(i);
                    historia.append( rozgrywka.doHistorii );
                    System.out.println("pierwszy if w ruchach graczy po small blind =2");
                    if(!kartyFlop){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartyFlop && !kartaTurn) {
                        iloscRuchowNaTurn--;
                    }
                    if(!kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
            }

        }else if (rozgrywka.getPobierzBlind() == 3 ) {
            for (int i = 2; i <rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0)  {
                    rozgrywka.ruchGracza(i);
                    historia.append( rozgrywka.doHistorii );
//                    System.out.println("pierwszy if w ruchach graczy po small blind =3");
                    if(!kartyFlop){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartyFlop && !kartaTurn) {
                        iloscRuchowNaTurn--;
                    }
                    if(!kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
            }

        }
        else if (rozgrywka.getPobierzBlind() == 4  ) {
            for (int i = 3; i <rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0)  {
                    rozgrywka.ruchGracza(i);
                    historia.append( rozgrywka.doHistorii );
//                    System.out.println("pierwszy if w ruchach graczy po small blind =4");
                    if(kartaF1==null){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartaF1!=null && kartaT==null) {
                        iloscRuchowNaTurn--;
                    }
                    if(kartaR==null && kartaT!=null && kartaF1!=null){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaR!=null && kartaT!=null && kartaF1!=null){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
            }

        }else if (rozgrywka.getPobierzBlind() == 5 ) {
            for (int i = 4; i <rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0)  {
                    rozgrywka.ruchGracza(i);
                    historia.append( rozgrywka.doHistorii );
//                    System.out.println("pierwszy if w ruchach graczy po small blind pobierz blind =5");
                    if(!kartyFlop){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartyFlop && !kartaTurn) {
                        iloscRuchowNaTurn--;
                    }
                    if(!kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
            }

        } else if (rozgrywka.getPobierzBlind() == 1) {
            for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 ) {
                    rozgrywka.ruchGracza(i);
                    historia.append( rozgrywka.doHistorii );
//                    System.out.println("trzeci if w ruchach graczy po small blind =1");

                    if(!kartyFlop){
                        iloscRuchowNaFlopie--;
                    }
                    if(kartyFlop && !kartaTurn) {
                        iloscRuchowNaTurn--;
                    }
                    if(!kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowNaRiver--;
                    }
                    if(kartaRiver && kartaTurn && kartyFlop){
                        iloscRuchowOstatnia--;
                    }
                    repaint();
                }
                if(i==5){
                    if(!kartaTurn){
                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                    }else if(kartaTurn && !kartaRiver){
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                    }else if(kartaTurn & kartaRiver){
                        sprawdzenieIPokazanieKart();
                    }
                }
                repaint();
            }


        }sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        repaint();

        licytacjaWGrze=false;

    }

    private void sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart() {
        iloscGraczyWGrze = rozgrywka.getGracze().size();

        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getKartyWRece().size() == 0) {
                iloscGraczyWGrze--;
            }
        }
        if (iloscGraczyWGrze == 1) {
            czyZostalJedenGracz = true;
            System.out.println("został jeden gracz. Koniec rozdania");
            dodajZetonyGraczyDoPuli();

            for (Gracz d : rozgrywka.getGracze()) {
                if (d.getKartyWRece().size() != 0) {
                    historia.append(d.getNick() + " wygrał i zdobył: " + rozgrywka.getPulaGlowna());
                    d.setIloscZetonow( d.getIloscZetonow() + rozgrywka.getPulaGlowna() );
                    repaint();
                }
            }

            koniecRozdania();
//            noweRozdanie();
            repaint();
        }else
            czyZostalJedenGracz=false;
        repaint();
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP()  {
        Gracz naszGracz = rozgrywka.getGracze().get(0);
        isLicytacjaWGrzeFlop=true;
        drugaLicytacja=true;
                if (!kartyFlop) {

//            for (int i = 0; i < (rozgrywka.getPobierzBlind()+1); i++) {
//                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
//                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0){
//                if (i == 0) {
//                    if(pierwszyObrot==false) {
//                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                    }
//                } else {
//                    if (kartaF1 == null ) {
//                        if(rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp ) {
//                            rozgrywka.ruchGracza( i );
//                            historia.append( rozgrywka.doHistorii );
//                            iloscRuchowNaFlopie--;
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                            System.out.println( i + "    sprawdzenie po naszym ruchu");
//                            repaint();
//                        }else if((rozgrywka.getPobierzBlind()-1)==0 && pierwszyObrot==true && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp){
//                            rozgrywka.ruchGracza( i );
//                            historia.append( rozgrywka.doHistorii );
//                            iloscRuchowNaFlopie--;
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//                            System.out.println( i );
//                            repaint();
//                        }else if(i == rozgrywka.getPobierzBlind() && pierwszyObrot){
//                            rozgrywka.ruchGracza( i );
//                        }
//                    }
//
//                    if (kartaF1 == null && !pierwszyObrot) {
//                        if(rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp){
//                            rozgrywka.ruchGracza( i );
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//                            historia.append( rozgrywka.doHistorii );
//                            System.out.println(" gdzie to sie kurwa robi");
//                            repaint();
//                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                        }
//
//
//                    } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
//                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                        repaint();
//
//                    }
//                }
//            }
                    while (isLicytacjaWGrzeFlop){
                        for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                            if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                                if (i == 0) {
                                    if (rozgrywka.getPobierzBlind() == 0) {
                                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                                    }

                                } else {

                                    if (pierwszyObrot) {

//                            if(i == rozgrywka.getPobierzBlind()){
//                                if((rozgrywka.getGracze().get(i).getPulaZetonowGracza() ==wartoscTmp )) {
//                                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                                    repaint();
//                                }else
//                                    rozgrywka.ruchGracza( i );
//                                System.out.println("ruch raczy po naszym ruchu i ruchy graczy flop");
//                                iloscRuchowNaFlopie--;
//                                repaint();
//                            }else{
                                        rozgrywka.ruchGracza( i );
                                        historia.append( rozgrywka.doHistorii );
//                                        System.out.println( "ruch raczy po naszym ruchu i ruchy graczy flop pierwszy obrot true" );
                                        iloscRuchowNaFlopie--;
                                        repaint();

//                                if (iloscRuchowNaFlopie < 1) {
//                                    pierwszyObrot = false;
//                                }


                                    } else if (pierwszyObrot == false) {
                                        if ((rozgrywka.getGracze().get( i ).getPulaZetonowGracza() == wartoscTmp)) {
                                            if (rozgrywka.getPobierzBlind()==1){
                                                rozgrywka.ruchGracza( 1 );
                                            }
                                        } else {
                                            rozgrywka.ruchGracza( i );
                                            historia.append( rozgrywka.doHistorii );
                                            iloscRuchowNaFlopie--;
//                                            System.out.println( "ruch raczy po naszym ruchu i ruchy graczy flop pierwszy obrot false" );
                                            repaint();
                                        }

                                    }
                                }

                                if (i == rozgrywka.getPobierzBlind() && pierwszyObrot)
                                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                                    isLicytacjaWGrzeFlop=false;


                            }
                        }
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                        repaint();
                    }

                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    if(iloscRuchowNaFlopie<1) {
                        pierwszyObrot = false;
                    }
                }

        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        repaint();
        if(drugaLicytacja) {
            if (!kartyFlop) {
                for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && rozgrywka.getGracze().get( i ).getPulaZetonowGracza() < wartoscTmp) {

                        rozgrywka.ruchGracza( i );
                        historia.append( rozgrywka.doHistorii );
//                        System.out.println( "czyzby to bylo tutaj" );

                        iloscRuchowNaFlopie--;

                        repaint();
                        if (iloscRuchowNaFlopie < 1) {
                            pierwszyObrot = false;
                        }

                    } else {
                        drugaLicytacja = false;
                    }

                }
                sprawdzeniePlusEwWylozenieKartNaStolFLOP();
            }
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        licytacjaWGrze = false;

    }


    private void sprawdzeniePoNaszymRuchuIRuchyGraczyTURN() {
        Gracz naszGracz = rozgrywka.getGracze().get(0);

        if (!kartaTurn) {

            while(isLicytacjaWGrzeTurn) {
                for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        if (i == 0) {

                        } else {

                            if (pierwszyObrotTurn == true) {

                                if (i == (rozgrywka.getPobierzBlind() - 1)) {
                                    if ((rozgrywka.getGracze().get( (rozgrywka.getPobierzBlind() - 1) ).getPulaZetonowGracza() == wartoscTmp)) {
                                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                        System.out.println( "czy to po tym sie cos robi nie tak 1" );
                                    } else {
                                        rozgrywka.ruchGracza( i );
                                        historia.append( rozgrywka.doHistorii );
                                        iloscRuchowNaTurn--;
                                        if (iloscRuchowNaTurn < 1) {
                                            pierwszyObrotTurn = false;
                                        }
                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                    }
                                } else {
                                    rozgrywka.ruchGracza( i );
                                    historia.append( rozgrywka.doHistorii );
                                    iloscRuchowNaTurn--;
                                    if (iloscRuchowNaTurn < 1) {
                                        pierwszyObrotTurn = false;

                                    }
                                    repaint();
                                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                }

                            }
                        }

                        if (pierwszyObrotTurn == false) {
                            if (rozgrywka.getGracze().get( i ).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza( i );
                                historia.append( rozgrywka.doHistorii );
                                iloscRuchowNaTurn--;
                                if (iloscRuchowNaTurn < 1) {
                                    pierwszyObrotTurn = false;
                                }
                                sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                System.out.println( "czy to po tym sie cos robi nie tak 2" );
                            } else {
                                sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                isLicytacjaWGrzeTurn=false;
                                System.out.println( "czy to po tym sie cos robi nie tak 3" );
                            }
                        } else if (pierwszyObrotTurn && i == rozgrywka.getPobierzBlind()) {
                            rozgrywka.ruchGracza( i );
                            historia.append( rozgrywka.doHistorii );
                            iloscRuchowNaTurn--;
                            if (iloscRuchowNaTurn < 1) {
                                pierwszyObrotTurn = false;
                            }
                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
                            System.out.println( "czy to po tym sie cos robi nie tak 4" );
                        }

                    }
                }
                sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                if (iloscRuchowNaTurn < 1) {
                    pierwszyObrotTurn = false;
                }
            }
        }

        if (!kartaTurn) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 ) {
                    if(!pierwszyObrotTurn && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp) {
                        rozgrywka.ruchGracza( i );
                        historia.append( rozgrywka.doHistorii );
                        iloscRuchowNaTurn--;
                        repaint();
                        if (iloscRuchowNaTurn < 1) {
                            pierwszyObrotTurn = false;
                        }

                    }else if (pierwszyObrotTurn){
                        rozgrywka.ruchGracza( i );
                        iloscRuchowNaTurn--;
                        repaint();
                        if (iloscRuchowNaTurn < 1) {
                            pierwszyObrotTurn = false;
                        }
                    }else if (!pierwszyObrotTurn && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()==wartoscTmp){

                    }
                }
            }sprawdzeniePlusEwWylozenieKartNaStolTURN();

        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        licytacjaWGrze = false;
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyRiver() //throws InterruptedException, SQLException, ClassNotFoundException
    {
        Gracz naszGracz = rozgrywka.getGracze().get(0);

        if (!kartaRiver) {
            while(isLicytacjaWGrzeRiver) {
                for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        if (i == 0) {
                        } else {

                            if (pierwszyObrotRiver == true) {

                                if (i == (rozgrywka.getPobierzBlind() - 1)) {
                                    if ((rozgrywka.getGracze().get( (rozgrywka.getPobierzBlind() - 1) ).getPulaZetonowGracza() == wartoscTmp)) {
                                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                                    } else {
                                        rozgrywka.ruchGracza( i );
                                        historia.append( rozgrywka.doHistorii );
                                        iloscRuchowNaRiver--;
                                        if (iloscRuchowNaRiver < 1) {
                                            pierwszyObrotRiver = false;
                                        }
                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                    }
                                } else {
                                    rozgrywka.ruchGracza( i );
                                    historia.append( rozgrywka.doHistorii );
                                    iloscRuchowNaRiver--;
                                    if (iloscRuchowNaRiver < 1) {
                                        pierwszyObrotRiver = false;
                                    }
                                    repaint();
                                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                }

                            }
                        }

                        if (pierwszyObrotRiver == false) {
                            if (rozgrywka.getGracze().get( i ).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza( i );
                                historia.append( rozgrywka.doHistorii );
                                iloscRuchowNaRiver--;
                                if (iloscRuchowNaRiver < 1) {
                                    pierwszyObrotRiver = false;
                                }
                                sprawdzeniePlusEwWylozenieKartNaStolRiver();
                            } else {
                                sprawdzeniePlusEwWylozenieKartNaStolRiver();
                                isLicytacjaWGrzeRiver=false;
                            }
                        } else if (pierwszyObrotRiver && i == rozgrywka.getPobierzBlind()) {
                            rozgrywka.ruchGracza( i );
                            historia.append( rozgrywka.doHistorii );
                            iloscRuchowNaRiver--;
                            if (iloscRuchowNaRiver < 1) {
                                pierwszyObrotRiver = false;
                            }
                            sprawdzeniePlusEwWylozenieKartNaStolRiver();
                        }

                    }
                }
                sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                if (iloscRuchowNaRiver < 1) {
                    pierwszyObrotRiver = false;
                }
            }
        }

        if (!kartaRiver) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 ) {
                    if(!pierwszyObrotTurn && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp) {
                        rozgrywka.ruchGracza( i );
                        historia.append( rozgrywka.doHistorii );
                        repaint();
                        iloscRuchowNaRiver--;
                        if(iloscRuchowNaRiver<1){
                            pierwszyObrotRiver=false;
                        }

                    }else if (pierwszyObrotTurn){
                        rozgrywka.ruchGracza( i );
                        repaint();
                        iloscRuchowNaRiver--;
                        if(iloscRuchowNaRiver<1){
                            pierwszyObrotRiver=false;
                        }
                    }else if (!pierwszyObrotTurn && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()==wartoscTmp){

                    }
                }
            }sprawdzeniePlusEwWylozenieKartNaStolRiver();

        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        licytacjaWGrze = false;
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja()  {
        Gracz naszGracz = rozgrywka.getGracze().get(0);
        if (kartyFlop && kartaTurn && kartaRiver) {

            while (isLicytacjaWGrzeOstatnia) {
                for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                    if (i == 0) {
                    } else {

                        if (pierwszyObrotOstatnia == true) {
                            if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                                if (i == (rozgrywka.getPobierzBlind() - 1)) {
                                    if ((rozgrywka.getGracze().get( (rozgrywka.getPobierzBlind() - 1) ).getPulaZetonowGracza() == wartoscTmp)) {
                                        sprawdzenieIPokazanieKart();
                                    } else
                                        rozgrywka.ruchGracza( i );
//                                System.out.println("ruch raczy po naszym ruchu i ruchy graczy ostatnia");
                                    iloscRuchowOstatnia--;
                                    if (iloscRuchowOstatnia < 1) {
                                        pierwszyObrotOstatnia = false;
                                    }
                                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                } else {
                                    rozgrywka.ruchGracza( i );
//                                System.out.println("ruch raczy po naszym ruchu i ruchy graczy ostatnia");
                                    iloscRuchowOstatnia--;
                                    if (iloscRuchowOstatnia < 1) {
                                        pierwszyObrotOstatnia = false;
                                    }
                                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                                    repaint();
                                }
                            }
                        }


                        if (!pierwszyObrotOstatnia) {
                            iloscRuchowOstatnia--;
                            if (iloscRuchowOstatnia < 1) {
                                pierwszyObrotOstatnia = false;
                            }
                            sprawdzenieIPokazanieKart();

                        } else if (pierwszyObrotOstatnia && i == rozgrywka.getPobierzBlind()) {
                            iloscRuchowOstatnia--;
                            if (iloscRuchowOstatnia < 1) {
                                pierwszyObrotOstatnia = false;
                            }
                            sprawdzenieIPokazanieKart();
                            isLicytacjaWGrzeOstatnia=false;
                        }
                    }
                }
                sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            }
        }

        if (kartyFlop && kartaTurn && kartaRiver) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && i != 0 && kartaR == null && rozgrywka.getGracze().get( i ).getPulaZetonowGracza()<wartoscTmp) {
                    rozgrywka.ruchGracza(i);
//                    System.out.println("ruch raczy po naszym ruchu i ruchy graczy ostatnia");
                    iloscRuchowOstatnia--;
                    if (iloscRuchowOstatnia < 1) {
                        pierwszyObrotOstatnia = false;
                    }
                    repaint();
                    sprawdzenieIPokazanieKart();
                }
            }sprawdzenieIPokazanieKart();
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        licytacjaWGrze = false;

    }

    private void naszRuchBet(Gracz naszGracz) {
        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        rozgrywka.setLineBaza("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + rozgrywka.getGracze().get(0).getNick() + " wykonał/a bet.");

        try {
            new BazaDanych(rozgrywka.getMe());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        historia.append( rozgrywka.getGracze().get( 0 ).getNick() + " zrobił bet \n" );
        System.out.println("Zrobiłem Bet");
        if (kartaF1 == null) {
            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowNaFlopie--;


        } else if (kartaT == null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowNaTurn--;

        } else if (kartaR == null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowNaRiver--;
        } else if (kartaR != null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowOstatnia--;
        }


    }

    private void naszRuchCheck(Gracz naszGracz) {

        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        rozgrywka.setLineBaza("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + rozgrywka.getGracze().get(0).getNick() + " wykonał/a check.");

        try {
            new BazaDanych(rozgrywka.getMe());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        historia.append( rozgrywka.getGracze().get( 0 ).getNick() + " check.\n" );
        System.out.println("ZROBILEM ruch");

        if (!kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                licytacjaWGrze = false;
                repaint();
            }

            iloscRuchowNaFlopie--;



        } else if (!kartaTurn   && kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowNaTurn--;
            System.out.println(iloscRuchowNaTurn + "  po wcisnieciu check");




        } else if (!kartaRiver && kartaTurn && kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowNaRiver--;
            System.out.println(iloscRuchowNaRiver + "  po wcisnieciu check");
        } else if (kartaRiver && kartaTurn && kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                licytacjaWGrze = false;
                repaint();
            }
            iloscRuchowOstatnia--;
        }

    }

    private void naszRuchFold(Gracz naszGracz) {

        historia.append(rozgrywka.getGracze().get(0).getNick() + " fold.");

        if (rozgrywka.getGracze().get( 0 ).getKartyWRece().size()!=0) {
            wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( ("yyyy-MM-dd HH:mm:ss") );

            rozgrywka.setLineBaza( "[" + LocalDateTime.now().format( dateTimeFormatter ) + "] " + rozgrywka.getGracze().get( 0 ).getNick() + " wykonał fold." );

            rozgrywka.getGracze().get( 0 ).getKartyWRece().removeAll( rozgrywka.getGracze().get( 0 ).getKartyWRece() );

            try {
                new BazaDanych( rozgrywka.getMe() );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            historia.append( rozgrywka.getGracze().get( 0 ).getNick() + " zrobił FOLD\n" );
            System.out.println( "ZROBILEM ruch" );

            bet.setVisible( false );
            check.setVisible( false );

            if (kartaF1 == null) {
                iloscRuchowNaFlopie--;
            }
            if (kartaT == null && kartaF1 != null) {
                iloscRuchowNaTurn--;
            }
            if (kartaR == null && kartaT != null && kartaF1 == null) {
                iloscRuchowNaRiver--;
            }
            if (kartaR != null && kartaT != null && kartaF1 == null) {
                iloscRuchowOstatnia--;
            }
            repaint();
            licytacjaWGrze=false;
        }
            if (kartaF1==null){
                System.out.println("zrobiłem fold przed flopem");
            }else if (kartaT == null) {
                System.out.println("zrobiłem fold przed flopem");
            }
            else if (kartaR == null) {
                System.out.println("zrobiłem fold przed flopem");
            }
            else if (kartaR != null && kartaT != null && kartaF1 == null) {
                System.out.println("zrobiłem fold przed flopem");
            }
        }

    private void naszRuchTURN(Gracz naszGracz) {

        if (kartaT == null) {

            System.out.println("ZROBILEM ruch");

            wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);

                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {

            }
//            else if(naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0 && !pierwszyObrot) {
//                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                licytacjaWGrze = false;
//            }

//            if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
//                System.out.println( "SPRAWDZENIE RAZ pierwszy obrot" );
//                ruchGraczyPoBigBlindDoNas();
//            }else if(!pierwszyObrot){
//                System.out.println("to juz nie jest pierwszy orót");
//            }

//            pierwszyObrot=false;
        }
    }

    private void naszRuchPoWylozeniuPiecuKArtNaStol(Gracz naszGracz) {

        if (kartaR == null) {
            System.out.println(pierwszyObrot + "   pierwszy obrot wartosc");
            System.out.println("ZROBILEM RUCH");

            wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                licytacjaWGrze = false;
                repaint();
            }

            if (!pierwszyObrot) {
                System.out.println("SPRAWDZENIE RAZ to moze jest tutaj kurde");
                sprawdzenieIPokazanieKart();
            } else if (!pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
                sprawdzenieIPokazanieKart();
                System.out.println("o co tu chodzi");
            } else if (pierwszyObrot) {
                pierwszyObrot = false;
//                sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                System.out.println("cos tu nie gra");

            }

        }
        pierwszyObrot = false;
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolFLOP()  {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {


            for (Gracz g : rozgrywka.getGracze()) {
                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow()>0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                        pierwszyObrot=false;
                    }
                }
            }
//            if(!czyGraczeWlozyliTakaSamaIloscZetonowDoPuli){
//                if(!kartyFlop){
//                    for(int i = (rozgrywka.getPobierzBlind()+1); i <rozgrywka.getGracze().size();i++ ){
//                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
//                        if(rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && rozgrywka.getGracze().get( i ).getPulaZetonowGracza() < wartoscTmp){
//                            rozgrywka.ruchGracza( i );
//                            System.out.println("jak to się kurwa tutaj wzielo?");
//                            iloscRuchowNaFlopie--;
//                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                        }
//                    }
//                }
//            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (!czyZostalJedenGracz) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.print("POKAZUJE KARTY");
                    historia.append( "Pokazuje karty flop\n");

                    kartaF1 = new ImageIcon(zapiszObrazDlaKartStol(0)).getImage();
                    kartaF2 = new ImageIcon(zapiszObrazDlaKartStol(1)).getImage();
                    kartaF3 = new ImageIcon(zapiszObrazDlaKartStol(2)).getImage();
                    System.out.println(kartyFlop + " sprawdzenie wartosci kartyFlop w sprawdzeniu po naszym ruchu");


                    dodajZetonyGraczyDoPuli();

                    kartyFlop = true;
                    System.out.println(kartyFlop + " sprawdzenie wartosci kartyFlop w sprawdzeniu po naszym ruchu");


                    repaint();

                    pierwszyObrotTurn = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    isLicytacjaWGrzeFlop=false;
                    drugaLicytacja=false;

                    if(rozgrywka.getPobierzBlind()==0){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
                        ruchGraczyPoSmallBlindDoNas();

                    }else if(rozgrywka.getPobierzBlind()==5){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
                        ruchGraczyPoSmallBlindDoNas();

                    }else if(rozgrywka.getPobierzBlind()==4){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
                        ruchGraczyPoSmallBlindDoNas();

                    }else if(rozgrywka.getPobierzBlind()==3){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
                        ruchGraczyPoSmallBlindDoNas();

                    }else if(rozgrywka.getPobierzBlind()==2){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
                        ruchGraczyPoSmallBlindDoNas();

                    }else if(rozgrywka.getPobierzBlind()==1){
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowNaTurn=iloscGraczyWGrze;
                        if(iloscRuchowNaTurn>1)
                            pierwszyObrotTurn=true;
//                        ruchGraczyPoSmallBlindDoNas();

                    }

                }isLicytacjaWGrzeFlop=false;
            }
        }

        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        repaint();
        licytacjaWGrze=false;



    }

    private void sprawdzeniePlusEwWylozenieKartNaStolTURN() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow()>0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    }
                }
            }




            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (!czyZostalJedenGracz) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.println("POKAZUJE KARTY");
                    System.out.println("to tutaj cos sie odpierdala");
                    historia.append( "Pokazuje karte Turn\n");
                    kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();

                    dodajZetonyGraczyDoPuli();
                    kartaTurn = true;

                    repaint();

                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    iloscRuchowNaRiver=iloscGraczyWGrze;
                    if(iloscRuchowNaRiver>1) {
                        pierwszyObrotRiver = true;
                    }

                    if ((rozgrywka.getPobierzBlind()-1) != 0) {

                        ruchGraczyPoSmallBlindDoNas();
//                        try {
//
//                        } catch (InterruptedException interruptedException) {
//                            interruptedException.printStackTrace();
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
        }

        repaint();
        licytacjaWGrze = false;
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolRiver() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow()>0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (!czyZostalJedenGracz) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.println("POKAZUJE KARTY");
                    historia.append( "Pokazuje karte River\n");
                    kartaR = new ImageIcon(zapiszObrazDlaKartStol(4)).getImage();

                    dodajZetonyGraczyDoPuli();
                    kartaRiver = true;
                    repaint();

                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    iloscRuchowOstatnia=iloscGraczyWGrze;
                    if(iloscRuchowOstatnia<1)
                        pierwszyObrotOstatnia=false;

                    if ((rozgrywka.getPobierzBlind()-1) != 0) {

                        ruchGraczyPoSmallBlindDoNas();
                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//                        try {
//
//                        } catch (InterruptedException interruptedException) {
//                            interruptedException.printStackTrace();
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
                    }
                }

            }
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        repaint();
        licytacjaWGrze = false;
    }

    private void sprawdzenieIPokazanieKart() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow()>0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
//                        System.out.println("Sprawdzenie " + g.getNick());
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
                if (!czyZostalJedenGracz) {
                    dodajZetonyGraczyDoPuli();
                    repaint();
                    System.out.println("Gracze pokazuja karty");
                    rozgrywka.sprawdzanieKart();
                    historia.append( rozgrywka.wygranaGracza );

                    for (Gracz g : rozgrywka.getGracze()) {
                        if (g.getKartyWRece().size() != 0) {
//                            odsłońZdjeęciaKartGracza1( 1);
//                            odsłońZdjeęciaKartGracza2( 2 );

                        }
                    }

                    repaint();
                    koniecRozdania();

                    pierwszyObrot = false;
                    licytacjaWGrze = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                } else if (czyZostalJedenGracz) {

                    koniecRozdania();
//                    noweRozdanie();
                }

            }

        }
    }

    private int najwyzszaWartoscPostawionychZetonowGracza(int wartoscTmp) {

        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getPulaZetonowGracza() >= wartoscTmp && g.getKartyWRece().size() != 0) {
                wartoscTmp = g.getPulaZetonowGracza();
            }
        }
        return wartoscTmp;
    }


    public int getNumerGracza() {
        return numerGracza;
    }

    public void setNumerGracza(int numerGracza) {
        this.numerGracza = numerGracza;
    }

    private int numerGracza(){
        int n;
        n=rozgrywka.getPobierzBlind()+1;
        numerGracza=n;
        return numerGracza;
    }

    private void dodaniePolGraczyZeWzgleduNaIchIlosc(Graphics g) {

        if (rozgrywka.getLiczbaGraczy() == 2) {
            g.drawString(rozgrywka.getGracze().get(0).getNick(), 1400, 870);
            g.drawImage(gracz0k1, 1375, 731, null);
            g.drawImage(gracz0k2, 1449, 731, null);
            g.drawString(rozgrywka.getGracze().get(1).getNick(), 425, 870);
            g.drawImage(gracz1k1, 405, 731, null);
            g.drawImage(gracz1k2, 479, 731, null);
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
            g.drawImage(gracz5k1, 1720, 387, null);
            g.drawImage(gracz5k2, 1794, 387, null);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(0).getIloscZetonow()), 1430, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(1).getIloscZetonow()), 460, 907);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(2).getIloscZetonow()), 115, 562);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(3).getIloscZetonow()), 460, 220);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(4).getIloscZetonow()), 1430, 220);
            g.drawString(String.valueOf(rozgrywka.getGracze().get(5).getIloscZetonow()), 1775, 562);
        }

    }

    private void dodajKartyStolFlop(Graphics g) {

        g.drawImage(kartaF1, 777, 487, null);
        g.drawImage(kartaF2, 851, 487, null);
        g.drawImage(kartaF3, 925, 487, null);

    }

    private void dodajKartyStolTurn(Graphics g) {
        g.drawImage(kartaT, 999, 487, null);
    }

    private void dodajKartyStolRiver(Graphics g) {
        g.drawImage(kartaR, 1073, 487, null);
    }

    private void dodajZetonSmallBlind(Graphics g) {

        if (rozgrywka.getGracze().size() == 2) {

            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(smallBlind, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 1 == 1) {
                g.drawImage(smallBlind, 670, 790, null);
            }
        } else if (rozgrywka.getGracze().size() == 3) {

            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(smallBlind, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 1) {
                g.drawImage(smallBlind, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 2 == 2) {
                g.drawImage(smallBlind, 320, 480, null);
            }
        } else if (rozgrywka.getGracze().size() == 4) {

            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(smallBlind, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 1) {
                g.drawImage(smallBlind, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 2) {
                g.drawImage(smallBlind, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() + 3 == 3) {
                g.drawImage(smallBlind, 650, 260, null);

            }
        } else if (rozgrywka.getGracze().size() == 5) {
            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(smallBlind, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 1) {
                g.drawImage(smallBlind, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 2) {
                g.drawImage(smallBlind, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 3) {
                g.drawImage(smallBlind, 650, 260, null);
            } else if (rozgrywka.getPobierzBlind() + 4 == 4) {
                g.drawImage(smallBlind, 1200, 260, null);
            }

        } else if (rozgrywka.getGracze().size() == 6) {

            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(smallBlind, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 1) {
                g.drawImage(smallBlind, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 2) {
                g.drawImage(smallBlind, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 3) {
                g.drawImage(smallBlind, 650, 260, null);
            } else if (rozgrywka.getPobierzBlind() - 1 == 4) {
                g.drawImage(smallBlind, 1200, 260, null);
            } else if (rozgrywka.getPobierzBlind() + 5 == 5) {
                g.drawImage(smallBlind, 1570, 480, null);
            }
        }


    }

    private ImageIcon dodajZdjecieSmallBlind() {
        smallBlind = new ImageIcon("zdjecia\\sb.jpg").getImage();
        return new ImageIcon(smallBlind);
    }

    private void dodajZetonBibBlind(Graphics g) {

        if (rozgrywka.getPobierzBlind() == 0) {
            g.drawImage(bigBlind, 1200, 790, null);
        } else if (rozgrywka.getPobierzBlind() == 1) {
            g.drawImage(bigBlind, 670, 790, null);
        } else if (rozgrywka.getPobierzBlind() == 2) {
            g.drawImage(bigBlind, 320, 480, null);
        } else if (rozgrywka.getPobierzBlind() == 3) {
            g.drawImage(bigBlind, 650, 260, null);
        } else if (rozgrywka.getPobierzBlind() == 4) {
            g.drawImage(bigBlind, 1200, 260, null);
        } else if (rozgrywka.getPobierzBlind() == 5) {
            g.drawImage(bigBlind, 1570, 480, null);
        }
    }

    private ImageIcon dodajZdjecieBigBlind() {
        bigBlind = new ImageIcon("zdjecia\\bb.jpg").getImage();
        return new ImageIcon(bigBlind);
    }

    private void dodajZetonDealer(Graphics g) {

        if (rozgrywka.getGracze().size() == 2) {

            if (rozgrywka.getPobierzBlind() - 1 == 0) {
                g.drawImage(dealer, 1150, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 1 == 1) {
                g.drawImage(dealer, 720, 790, null);
            }
        } else if (rozgrywka.getGracze().size() == 3) {

            if (rozgrywka.getPobierzBlind() - 2 == 0) {
                g.drawImage(dealer, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 1 == 1) {
                g.drawImage(dealer, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 1 == 2) {
                g.drawImage(dealer, 320, 480, null);
            }
        } else if (rozgrywka.getGracze().size() == 4) {

            if (rozgrywka.getPobierzBlind() - 2 == 0) {
                g.drawImage(dealer, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 1) {
                g.drawImage(dealer, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() + 2 == 2) {
                g.drawImage(dealer, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() + 2 == 3) {
                g.drawImage(dealer, 650, 260, null);

            }
        } else if (rozgrywka.getGracze().size() == 5) {
            if (rozgrywka.getPobierzBlind() - 2 == 0) {
                g.drawImage(dealer, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 1) {
                g.drawImage(dealer, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 2) {
                g.drawImage(dealer, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() + 3 == 3) {
                g.drawImage(dealer, 650, 260, null);
            } else if (rozgrywka.getPobierzBlind() + 3 == 4) {
                g.drawImage(dealer, 1200, 260, null);
            }

        } else if (rozgrywka.getGracze().size() == 6) {

            if (rozgrywka.getPobierzBlind() - 2 == 0) {
                g.drawImage(dealer, 1200, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 1) {
                g.drawImage(dealer, 670, 790, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 2) {
                g.drawImage(dealer, 320, 480, null);
            } else if (rozgrywka.getPobierzBlind() - 2 == 3) {
                g.drawImage(dealer, 650, 260, null);
            } else if (rozgrywka.getPobierzBlind() + 4 == 4) {
                g.drawImage(dealer, 1200, 260, null);
            } else if (rozgrywka.getPobierzBlind() + 4 == 5) {
                g.drawImage(dealer, 1570, 480, null);
            }
        }


    }

    private ImageIcon dodajZdjecieDealer() {
        dealer = new ImageIcon("zdjecia\\dealer.jpg").getImage();
        return new ImageIcon(dealer);
    }

    private void dodajIloscPostawionychZetonowGracza0(Graphics g) {
        if (rozgrywka.getGracze().get(0).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(0).getPulaZetonowGracza() + betB) + "$";
            g.drawString(iloscZetonowPostawionych, 1215, 710);
        }
    }

    private void dodajIloscPostawionychZetonowGracza1(Graphics g) {
        if (rozgrywka.getGracze().get(1).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(1).getPulaZetonowGracza()) + "$";
            g.drawString(iloscZetonowPostawionych, 680, 710);
        }
    }

    private void dodajIloscPostawionychZetonowGracza2(Graphics g) {
        if (rozgrywka.getGracze().get(2).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(2).getPulaZetonowGracza()) + "$";
            g.drawString(iloscZetonowPostawionych, 450, 520);
        }
    }

    private void dodajIloscPostawionychZetonowGracza3(Graphics g) {
        if (rozgrywka.getGracze().get(3).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(3).getPulaZetonowGracza()) + "$";
            g.drawString(iloscZetonowPostawionych, 680, 360);
        }
    }

    private void dodajIloscPostawionychZetonowGracza4(Graphics g) {
        if (rozgrywka.getGracze().get(4).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(4).getPulaZetonowGracza()) + "$";
            g.drawString(iloscZetonowPostawionych, 1215, 360);
        }
    }

    private void dodajIloscPostawionychZetonowGracza5(Graphics g) {
        if (rozgrywka.getGracze().get(5).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get(5).getPulaZetonowGracza()) + "$";
            g.drawString(iloscZetonowPostawionych, 1420, 520);
        }
    }



    private void noweRozdanie() {




        if (rozgrywka.getPobierzBlind() != 5) {
            nastepnyGracz.setVisible(true);
        }

        getRozgrywka().rozdajFlop();
        getRozgrywka().rozdajTurn();
        getRozgrywka().rozdajRiver();
        repaint();

        rozgrywka.rozdajBlind();
//        rozgrywka.setKtoBlind(0);

        dodajZdjecieSmallBlind();
        dodajZdjecieBigBlind();

        ruchGraczaTmp = rozgrywka.getPobierzBlind() + 1;
        if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
            ruchGraczaTmp = 0;
            check.setVisible(true);
            nastepnyGracz.setVisible(false);
            bet.setVisible(true);
            fold.setVisible(true);
        }

        if (ruchGraczaTmp != 0) {
            check.setVisible(false);
            fold.setVisible(false);
            bet.setVisible(false);
            nastepnyGracz.setVisible(true);
        }

        dodajZdjecieDealer();

        pierwszyObrot = true;
        pierwszyObrotTurn = true;
        pierwszyObrotRiver = true;
        pierwszyObrotOstatnia = true;
        kartyFlop = false;
        kartaTurn = false;
        kartaRiver = false;

        System.out.println((ruchGraczaTmp) + " - ten gracz będzie robił ruch");

    }

    private void koniecRozdania() {

        repaint();
        rozgrywka.setPulaGlowna(0);
        for (Gracz g : rozgrywka.getGracze()) {
            g.setPulaZetonowGracza(0);
            g.setBlind(0);
        }

        rozgrywka.setTaliaKart(new TaliaKart());

        rozgrywka.usunKartyZReki();
        repaint();
        rozgrywka.usunKartyStol();
        repaint();
        for(Gracz g : rozgrywka.getGracze()){
            if(g.getIloscZetonow()<=0){
                rozgrywka.getGracze().remove( g );
                rozgrywka.setLiczbaGraczy( rozgrywka.getLiczbaGraczy()-1);
                repaint();
            }
        }

        kartaF1 = null;
        kartaF2 = null;
        kartaF3 = null;
        kartaT = null;
        kartaR = null;
        kartyFlop = false;
        kartaTurn = false;
        kartaRiver = false;
        repaint();

        nastepnyGracz.setVisible(false);
        graj.setVisible( true );



        pierwszaTura = false;

        repaint();

    }

    public void dodajZetonyGraczyDoPuli() {

        for (Gracz g : rozgrywka.getGracze()) {
            rozgrywka.setPulaGlowna(rozgrywka.getPulaGlowna() + g.getPulaZetonowGracza());
            g.setPulaZetonowGracza(0);
        }
    }

    private void dodajZdjeciaKartGraczy() {

        int czas = 200;

        gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();

        Thread tGracz1k1 = new Thread() {
            public void run() {

                try {
                    Thread.sleep(czas);
                } catch (InterruptedException ignored) {
                }

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {

                                gracz1k1 = new ImageIcon(kolorRewers).getImage();
                                repaint();

                                Thread tGracz2k1 = new Thread() {
                                    public void run() {

                                        try {
                                            Thread.sleep(czas);
                                        } catch (InterruptedException ignored) {
                                        }

                                        SwingUtilities.invokeLater(
                                                new Runnable() {
                                                    public void run() {

                                                        gracz2k1 = new ImageIcon(kolorRewers).getImage();
                                                        repaint();

                                                        Thread tGracz3k1 = new Thread() {
                                                            public void run() {

                                                                try {
                                                                    Thread.sleep(czas);
                                                                } catch (InterruptedException ignored) {
                                                                }

                                                                SwingUtilities.invokeLater(
                                                                        new Runnable() {
                                                                            public void run() {

                                                                                gracz3k1 = new ImageIcon(kolorRewers).getImage();
                                                                                repaint();

                                                                                Thread tGracz4k1 = new Thread() {
                                                                                    public void run() {

                                                                                        try {
                                                                                            Thread.sleep(czas);
                                                                                        } catch (InterruptedException ignored) {
                                                                                        }

                                                                                        SwingUtilities.invokeLater(
                                                                                                new Runnable() {
                                                                                                    public void run() {

                                                                                                        gracz4k1 = new ImageIcon(kolorRewers).getImage();
                                                                                                        repaint();

                                                                                                        Thread tGracz5k1 = new Thread() {
                                                                                                            public void run() {

                                                                                                                try {
                                                                                                                    Thread.sleep(czas);
                                                                                                                } catch (InterruptedException ignored) {
                                                                                                                }

                                                                                                                SwingUtilities.invokeLater(
                                                                                                                        new Runnable() {
                                                                                                                            public void run() {

                                                                                                                                gracz5k1 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                repaint();

                                                                                                                                Thread tGracz0k2 = new Thread() {
                                                                                                                                    public void run() {

                                                                                                                                        try {
                                                                                                                                            Thread.sleep(czas);
                                                                                                                                        } catch (InterruptedException ignored) {
                                                                                                                                        }

                                                                                                                                        SwingUtilities.invokeLater(
                                                                                                                                                new Runnable() {
                                                                                                                                                    public void run() {

                                                                                                                                                        gracz0k2 = new ImageIcon(zapiszObrazDlaGraczy(1)).getImage();
                                                                                                                                                        repaint();

                                                                                                                                                        Thread tGracz1k2 = new Thread() {
                                                                                                                                                            public void run() {

                                                                                                                                                                try {
                                                                                                                                                                    Thread.sleep(czas);
                                                                                                                                                                } catch (InterruptedException ignored) {
                                                                                                                                                                }

                                                                                                                                                                SwingUtilities.invokeLater(
                                                                                                                                                                        new Runnable() {
                                                                                                                                                                            public void run() {

                                                                                                                                                                                gracz1k2 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                                                                repaint();

                                                                                                                                                                                Thread tGracz2k2 = new Thread() {
                                                                                                                                                                                    public void run() {

                                                                                                                                                                                        try {
                                                                                                                                                                                            Thread.sleep(czas);
                                                                                                                                                                                        } catch (InterruptedException ignored) {
                                                                                                                                                                                        }

                                                                                                                                                                                        SwingUtilities.invokeLater(
                                                                                                                                                                                                new Runnable() {
                                                                                                                                                                                                    public void run() {

                                                                                                                                                                                                        gracz2k2 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                                                                                        repaint();

                                                                                                                                                                                                        Thread tGracz3k2 = new Thread() {
                                                                                                                                                                                                            public void run() {

                                                                                                                                                                                                                try {
                                                                                                                                                                                                                    Thread.sleep(czas);
                                                                                                                                                                                                                } catch (InterruptedException ignored) {
                                                                                                                                                                                                                }

                                                                                                                                                                                                                SwingUtilities.invokeLater(
                                                                                                                                                                                                                        new Runnable() {
                                                                                                                                                                                                                            public void run() {

                                                                                                                                                                                                                                gracz3k2 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                                                                                                                repaint();
                                                                                                                                                                                                                                Thread tGracz4k2 = new Thread() {
                                                                                                                                                                                                                                    public void run() {

                                                                                                                                                                                                                                        try {
                                                                                                                                                                                                                                            Thread.sleep(czas);
                                                                                                                                                                                                                                        } catch (InterruptedException ignored) {
                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                        SwingUtilities.invokeLater(
                                                                                                                                                                                                                                                new Runnable() {
                                                                                                                                                                                                                                                    public void run() {

                                                                                                                                                                                                                                                        gracz4k2 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                                                                                                                                        repaint();

                                                                                                                                                                                                                                                        Thread tGracz5k2 = new Thread() {
                                                                                                                                                                                                                                                            public void run() {

                                                                                                                                                                                                                                                                try {
                                                                                                                                                                                                                                                                    Thread.sleep(czas);
                                                                                                                                                                                                                                                                } catch (InterruptedException ignored) {
                                                                                                                                                                                                                                                                }

                                                                                                                                                                                                                                                                SwingUtilities.invokeLater(
                                                                                                                                                                                                                                                                        new Runnable() {
                                                                                                                                                                                                                                                                            public void run() {

                                                                                                                                                                                                                                                                                gracz5k2 = new ImageIcon(kolorRewers).getImage();
                                                                                                                                                                                                                                                                                graj.setVisible(true);
                                                                                                                                                                                                                                                                                repaint();

                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                );
                                                                                                                                                                                                                                                            }


                                                                                                                                                                                                                                                        };
                                                                                                                                                                                                                                                        tGracz5k2.start();
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                        );
                                                                                                                                                                                                                                    }


                                                                                                                                                                                                                                };
                                                                                                                                                                                                                                tGracz4k2.start();
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                );
                                                                                                                                                                                                            }


                                                                                                                                                                                                        };
                                                                                                                                                                                                        tGracz3k2.start();
                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                        );
                                                                                                                                                                                    }


                                                                                                                                                                                };
                                                                                                                                                                                tGracz2k2.start();
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                );
                                                                                                                                                            }


                                                                                                                                                        };
                                                                                                                                                        tGracz1k2.start();

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                        );
                                                                                                                                    }


                                                                                                                                };
                                                                                                                                tGracz0k2.start();
                                                                                                                            }
                                                                                                                        }
                                                                                                                );
                                                                                                            }


                                                                                                        };
                                                                                                        tGracz5k1.start();
                                                                                                    }
                                                                                                }
                                                                                        );
                                                                                    }


                                                                                };
                                                                                tGracz4k1.start();
                                                                            }
                                                                        }
                                                                );
                                                            }


                                                        };
                                                        tGracz3k1.start();
                                                    }
                                                }
                                        );
                                    }


                                };
                                tGracz2k1.start();

                            }
                        }
                );
            }


        };
        tGracz1k1.start();
        repaint();

    }



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
    public String toString() {
        return "PanelStolik{" +
                "rozgrywka=" + rozgrywka +
                "} " + super.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        graj.setVisible(false);

        if (!pierwszaTura) {
            getRozgrywka().rozdajKartyDoReki(rozgrywka.getTaliaKart());
            gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();
            gracz0k2 = new ImageIcon(zapiszObrazDlaGraczy(1)).getImage();
            gracz1k1 = new ImageIcon(kolorRewers).getImage();
            gracz1k2 = new ImageIcon(kolorRewers).getImage();
            gracz2k1 = new ImageIcon(kolorRewers).getImage();
            gracz2k2 = new ImageIcon(kolorRewers).getImage();
            gracz3k1 = new ImageIcon(kolorRewers).getImage();
            gracz3k2 = new ImageIcon(kolorRewers).getImage();
            gracz4k1 = new ImageIcon(kolorRewers).getImage();
            gracz4k2 = new ImageIcon(kolorRewers).getImage();
            gracz5k1 = new ImageIcon(kolorRewers).getImage();
            gracz5k2 = new ImageIcon(kolorRewers).getImage();



        }

        noweRozdanie();

    }

}