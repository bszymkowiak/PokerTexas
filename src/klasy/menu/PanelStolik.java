package klasy.menu;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.Gracz;
import klasy.Rozgrywka;
import klasy.karty.TaliaKart;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PanelStolik extends JPanel implements ActionListener {
    private File imageFile;
    private BufferedImage image;
    private Stolik stolik;
    private JButton fold;
    private JButton check;
    private JButton call;
    private JButton bet;
    private JButton graj;
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
    private Image smallBlind;
    private Image bigBlind;
    private Image dealer;
    int pulaGlowna;
    int wpisDoPuli;
    private int wartoscTmp;

    private boolean pierwszyObrot = true;
    private boolean licytacjaWGrze;
    private boolean pierwszeRuchyGraczyPoFold = true;
    private String zwyciezca = "";
    private int iloscGraczyZKartami;
    private boolean czyZostalJedenGracz;


    Border obramowanie = BorderFactory.createEmptyBorder();

    private Rozgrywka rozgrywka;

    int betB;
    private JTextField wpisPuli;
    int iloscMoichZetonow;
    JTextField mojeZetony;

    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

    public PanelStolik(Stolik stolik) {


        super();

        me = this;
        this.stolik = stolik;
        setLayout( null );


        setRozgrywka( stolik.getRozgrywka() );

        rozgrywka.setLiczbaGraczy( stolik.getRozgrywka().getLiczbaGraczy() );

        getRozgrywka().dodajGraczy( stolik.getRozgrywka().getIloscZetonow() );

        getRozgrywka().getGracze().get( 0 ).setNick( stolik.getImieGracza() );

        dodajTlo();
        dodajPrzyciski( obramowanie );

    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }


    public Image zapiszObrazDlaKartStol(int i) {

        Kolor kolorKartyWReku = rozgrywka.getKartyStol().get( i ).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getKartyStol().get( i ).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );

    }

    public Image zapiszObrazDlaGraczy(int i) {

        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 0 ).getKartyWRece().get( i ).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 0 ).getKartyWRece().get( i ).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
    }

    private Image zwrocZdjecieKartyOKolorzeIWartosci(Wartosc numerKarty, Kolor kolor) {
        return new ImageIcon( "zdjecia\\karty\\" + kolor.toString().toLowerCase() + "\\" + numerKarty.getWartosc() + ".jpg" ).getImage();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension( 1920, 1080 );
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
        var pula = "Pula: " + pulaGlowna + "$";
        g.drawString(pula, 900, 465);


        dodajKartyStolFlop(g);
        dodajKartyStolTurn(g);
        dodajKartyStolRiver(g);

        g.setColor(Color.WHITE);

        dodaniePolGraczyZeWzgleduNaIchIlosc(g);

        dodanieKartGraczyIZetonow(g);

        dodajZetonSmallBlind( g );
        dodajZetonBibBlind( g );
        dodajZetonDealer( g );




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
        ;
    }

    private void dodajPrzyciski(Border obramowanie) {

//        wpisPuli = new JTextField();
//        wpisPuli.setBounds( 1730, 890, 134, 30 );
//        wpisPuli.setFont( new Font( "SansSerif", Font.BOLD, 18 ) );
//        add( wpisPuli );

        fold = new JButton( new ImageIcon( "zdjecia\\fold.jpg" ) );
        fold.setBounds( 1422, 930, 134, 80 );
        fold.setBackground( Color.GRAY );
        fold.setFont( new Font( "SansSerif", Font.BOLD, 25 ) );
        fold.setBorder( obramowanie );
        add( fold );
        fold.setVisible( true );
        przyciskFoldAkcja();

        check = new JButton( new ImageIcon( "zdjecia\\check.jpg" ) );
        check.setBounds( 1576, 930, 134, 80 );
        check.setBackground( Color.GRAY );
        check.setFont( new Font( "SansSerif", Font.BOLD, 25 ) );
        check.setBorder( obramowanie );
        add( check );
        check.setVisible( true );
        przyciskCheckAkcja();

        bet = new JButton( new ImageIcon( "zdjecia\\bet.jpg" ) );
        bet.setBounds( 1730, 930, 134, 80 );
        bet.setBackground( Color.GRAY );
        bet.setFont( new Font( "SansSerif", Font.BOLD, 25 ) );
        bet.setBorder( obramowanie );
        add( bet );
        bet.setVisible( true );
        przyciskBetAkcja();

        graj = new JButton( new ImageIcon( "zdjecia\\graj2.jpg" ) );
        graj.setBounds( 877, 686, 243, 83 );
        graj.setBorder( obramowanie );
        add( graj );
        graj.addActionListener( this );

        mojeZetony = new JTextField( iloscMoichZetonow );
//        mojeZetony.setBounds(1576, 890, 134, 30);
//        mojeZetony.setFont( new Font ("SansSerif", Font.BOLD, 18) );
        add( mojeZetony );

//        JButton lobby = new JButton( "LOBBY" );
//        lobby.setBackground( Color.GRAY );
//        lobby.setBounds( 20, 20, 100, 40 );
//        lobby.setFont( new Font( "SansSerif", Font.BOLD, 18 ) );
//        add( lobby );

    }

    private void przyciskBetAkcja() {
        bet.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gracz naszGracz = rozgrywka.getGracze().get( 0 );
                if (naszGracz.getKartyWRece().size() != 0) {

                    System.out.println( pierwszyObrot + "    wartosc pierwszego obrotuuuuu" );

                    wartoscTmp = 0;

                    System.out.println( pierwszyObrot + "pierwszy obrot" );


                    if (kartaF1 == null) {

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {
                            //                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

//                        if (pierwszyObrot) {
//
//                            System.out.println("PIERWSZY OBRÓT +  " + pierwszyObrot);
//
//                        }
//                        else {
//
//                            System.out.println("KOLEJNY OBRÓT");
//
//                            ruchGraczyPoBigBlindDoNas();
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (pierwszyObrot == true) {
//                                    naszRuchBet( naszGracz );
//
//                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
//
//                                } else {
//                                    if (naszGracz.getKartyWRece().size() != 0) {
//                                    }
//                                }
//
//                            } else if (rozgrywka.getKtoBlind() == 0) {
//                                if (pierwszyObrot = true) {
//                                    naszRuchBet( naszGracz );
//                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
//                                }
//                                else if(pierwszyObrot=false){
//                                    naszRuchBet( naszGracz );
//                                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                                }
//                            }
                            if (rozgrywka.getPobierzBlind() == 0) {
                                naszRuchBet( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            } else if (rozgrywka.getKtoBlind() == 0) {
                                naszRuchBet( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            } else {
                                naszRuchBet( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            }
                        }
                        repaint();

                    } else if (kartaT == null && kartaF1 != null) {

                        System.out.println( "WCHODZE DO KARTA T " );

                        licytacjaWGrze = true;
                        while (licytacjaWGrze) {

                            if (rozgrywka.getKtoBlind() == 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchTURN( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                                naszRuchBet( naszGracz );
                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchBet( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                    }
                                }

                            }else{
                                if (pierwszyObrot == true) {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                } else if (pierwszyObrot == false) {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                    }
                                }
                            }
                            repaint();
                        }
                    } else if (kartaR == null && kartaT != null && kartaF1 != null) {

                        System.out.println( "WCHODZE DO KARTA R " );

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if (rozgrywka.getPobierzBlind() != 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na River" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchRiver( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchBet( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchBet( naszGracz );
                                    sprawdzeniePlusEwWylozenieKartNaStolRiver();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        try {
                                            sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        } catch (ClassNotFoundException classNotFoundException) {
                                            classNotFoundException.printStackTrace();
                                        }
                                    }
                                }
                            }
                            repaint();
                        }


//                else if (kartaT == null) {
//
//
//                    if (kartaT == null) {
//
//                        System.out.println("ZROBILEM RUCH");
//
//                        int dealer = rozgrywka.getKtoBlind() -1 ;
//
//                        if (rozgrywka.getKtoBlind() == 0) {
//                            dealer = 5;
//                        }
//
//                        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
//
//                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
//
//
//                        if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
//                            naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
//                            naszGracz.setPulaZetonowGracza(wartoscTmp);
//                            licytacjaWGrze = false;
//                            repaint();
//                        }
//
//                        if (!pierwszyObrot) {
//                            System.out.println("SPRAWDZENIE RAZ");
//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                        } else if (pierwszyObrot && dealer == 0) {
//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            pierwszyObrot = false;
//                        } else if (pierwszyObrot && dealer != 0 && rozgrywka.getKtoBlind() != 0 && rozgrywka.getPobierzBlind() != 0 ) {
//
//                            for (int i = 1; i < dealer; i++) {
//                                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza(i);
//                                    if (i == dealer) {
//                                        rozgrywka.ruchGracza(i);
//                                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                                        pierwszyObrot = false;
//                                    }
//                                }
//                            }
//                        }
//                    }
                    } else if (kartaR != null && kartaT != null && kartaF1 != null) {

                        System.out.println( "Juz ostatnia karta na stole " );

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if (rozgrywka.getPobierzBlind() != 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na River" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchRiver( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchBet( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchBet( naszGracz );
                                    sprawdzenieIPokazanieKart();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        try {
                                            sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        } catch (ClassNotFoundException classNotFoundException) {
                                            classNotFoundException.printStackTrace();
                                        }
                                    }
                                }
                            }
                            repaint();
                        }

                    }
                }
            }
        } );
    }

    private void przyciskCheckAkcja() {
        check.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Gracz naszGracz = rozgrywka.getGracze().get( 0 );
                if (naszGracz.getKartyWRece().size() != 0) {
                    System.out.println( pierwszyObrot + "    wartosc pierwszego obrotuuuuu" );

                    wartoscTmp = 0;


                    if (kartaF1 == null) {

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

//                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

//                        if (pierwszyObrot) {
//
//                            System.out.println("PIERWSZY OBRÓT +  " + pierwszyObrot);
//
//                        }
//                        else {
//
//                            System.out.println("KOLEJNY OBRÓT");
//
//                            ruchGraczyPoBigBlindDoNas();
//                        }

                            if (rozgrywka.getPobierzBlind() == 0) {
                                naszRuchCheck( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            } else if (rozgrywka.getKtoBlind() == 0) {
                                naszRuchCheck( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            } else {
                                naszRuchCheck( naszGracz );
                                try {
                                    sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            }
                        }
                        repaint();
                    } else if (kartaT == null && kartaF1 != null) {

                        System.out.println( "WCHODZE DO KARTA T " );

                        licytacjaWGrze = true;
                        while (licytacjaWGrze) {

                            if (rozgrywka.getPobierzBlind() != 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchTURN( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                                naszRuchCheck( naszGracz );
                                try {
                                    ruchGraczyPoBigBlindDoNas();
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException classNotFoundException) {
                                    classNotFoundException.printStackTrace();
                                }
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchCheck( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchCheck( naszGracz );
                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
                                    }
                                }

                            }
                            repaint();
                        }
                    } else if (kartaR == null && kartaT != null && kartaF1 != null) {

                        System.out.println( "WCHODZE DO KARTA R " );

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if (rozgrywka.getPobierzBlind() != 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na River" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchRiver( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchCheck( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchCheck( naszGracz );
                                    sprawdzeniePlusEwWylozenieKartNaStolRiver();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        try {
                                            sprawdzeniePoNaszymRuchuIRuchyGraczyRiver();
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        } catch (ClassNotFoundException classNotFoundException) {
                                            classNotFoundException.printStackTrace();
                                        }
                                    }
                                }
                            }
                            repaint();
                        }


//                else if (kartaT == null) {
//
//
//                    if (kartaT == null) {
//
//                        System.out.println("ZROBILEM RUCH");
//
//                        int dealer = rozgrywka.getKtoBlind() -1 ;
//
//                        if (rozgrywka.getKtoBlind() == 0) {
//                            dealer = 5;
//                        }
//
//                        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
//
//                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
//
//
//                        if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
//                            naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
//                            naszGracz.setPulaZetonowGracza(wartoscTmp);
//                            licytacjaWGrze = false;
//                            repaint();
//                        }
//
//                        if (!pierwszyObrot) {
//                            System.out.println("SPRAWDZENIE RAZ");
//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                        } else if (pierwszyObrot && dealer == 0) {
//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            pierwszyObrot = false;
//                        } else if (pierwszyObrot && dealer != 0 && rozgrywka.getKtoBlind() != 0 && rozgrywka.getPobierzBlind() != 0 ) {
//
//                            for (int i = 1; i < dealer; i++) {
//                                if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza(i);
//                                    if (i == dealer) {
//                                        rozgrywka.ruchGracza(i);
//                                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                                        pierwszyObrot = false;
//                                    }
//                                }
//                            }
//                        }
//                    }
                    } else if (kartaR != null && kartaT != null && kartaF1 != null) {

                        System.out.println( "Juz ostatnia karta na stole " );

                        licytacjaWGrze = true;

                        while (licytacjaWGrze) {

                            if (rozgrywka.getPobierzBlind() != 0) {
//                            if (pierwszyObrot) {
//
//                            System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//                                System.out.println( "Kolejny obrot Na Turn" );
//
//                                ruchGraczyPoSmallBlindDoNas();
//                            }

//                            if (pierwszyObrot) {
//
//                                System.out.println( "zrobilem ruch gracza od small blind" );
//
//
//                            } else {
//
//                                System.out.println( "Kolejny obrot Na River" );
//
//                            }
//
//                            if (naszGracz.getKartyWRece().size() != 0) {
//                                naszRuchRiver( naszGracz );
////                                sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//
//                            }
//                        if( naszGracz.getKartyWRece().size() != 0 ) {
//                            naszRuchTURN( naszGracz );
//                            sprawdzeniePoNaszymRuchuIRuchyGraczyTURN();
//                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
//
//                        }


//                            if (rozgrywka.getPobierzBlind() == 0) {
//                                if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
//                                    rozgrywka.ruchGracza( 5 );
//                                    System.out.println( "pierwszy obrot + pobierz blind = 0" );
//
//                                }
//                            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size()-1; i++) {
//                                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
//                                    rozgrywka.ruchGracza( i );
//                                    System.out.println(" ruch gracza " + rozgrywka.getGracze().get( i ).getNick() );
//                                    if (!pierwszyObrot) {
//                                        System.out.println( "else w pierwszym obrocie na turn dupa dupa" );
//                                    }
//                                    repaint();
//                                }
//                            }

//                        }

//                            if (pierwszyObrot) {


//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            }


//                        if (naszGracz.getKartyWRece().size() != 0) {
//
////                            if (kartaT == null && kartaF1 != null) {
////
////                                System.out.println("ZROBILEM RUCH");
////
////                                wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
////
////                                wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////
////                                if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
////                                    naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
////                                    naszGracz.setPulaZetonowGracza(wartoscTmp);
////                                    licytacjaWGrze = false;
////                                    repaint();
////                                }
////
////
////                                if (pierwszyObrot) {
////
////
////                                    System.out.println("SPRAWDZENIE RAZ");
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
//////                                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////
////                                } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
////
////                                    wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
////
////                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
////
////                                    while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                        System.out.println("czy wszedlem do petelki");
////
////            System.out.println("SPRAWDZAMY CZY GRACZE WŁOŻYLI TAKĄ SAMĄ ILOŚĆ ŻETONÓW DO PULI");
////            System.out.println("Najwyższa postawiona ilość żetonów: " + wartoscTmp);
////
////                                        for (Gracz g : rozgrywka.getGracze()) {
////
////                                            if (g.getKartyWRece().size() != 0) {
////
////                                                if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
////                        System.out.println("Sprawdzenie " + g.getNick());
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////
////                                                }
////                                            }
////                                        }
////
////                                        System.out.println("czy wszedlem do petelki i pokazuje czygraczewlozylitakasamailosczetonow " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuli);
////
////                                        if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
////
////                                            System.out.println("POKAZUJE KARTY");
////                                            kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
////
////                                            dodajZetonyGraczyDoPuli();
////
////                                            repaint();
////
////                                            licytacjaWGrze = false;
////                                            czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                        }
////
////                                    }
////                                }
////                            }
////
////                            System.out.println("Sprawdzam dwa turn");
////
////                            if (kartaT == null && kartaF1 != null) {
////
//////                                sprawdzenieIlosciGraczyNaFlopTurnRiver();
////
////
////                                for (int i = 0; i < rozgrywka.getGracze().size(); i++) {
////                                    if (i == 0) {
////
////                                    } else {
////
////                                        if(kartaT == null) {
////                                            System.out.println( "A moze jestem tutaj a moze kurwa jestem tutaj" );
////                                            System.out.println( pierwszyObrot );
////                                            rozgrywka.ruchGracza( i );
////                                            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
////                                            if (i == rozgrywka.getKtoBlind()) {
////                                                if (czyZostalJedenGracz == false) {
////                                                    System.out.println( "sprawdziłem.zostalo wiecej niz jeden gracz" );
////                                                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                                } else if (czyZostalJedenGracz == true) {
////                                                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
////                                                    licytacjaWGrze = false;
////                                                    System.out.println( "Koniec rozdania. Rozdanie wygrał " );
////                                                }
////
////                                                repaint();
////                                            }
////                                        }
////
////                                        if (kartaT == null && !pierwszyObrot) {
////                                            System.out.println("Sprawdzam trzy turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////
////                                        } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                            System.out.println("sprawdzam cztery turn");
////                                            pierwszyObrot = false;
////                                        }
////                                    }
////                                }
////                            }
////
////                            if (kartaT == null) {
////                                for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
////                                    if (rozgrywka.getGracze().get(i).getKartyWRece().size() != 0 && kartaT == null && i != 0) {
////
////                                        rozgrywka.ruchGracza(i);
////                                        repaint();
////                                        if(!pierwszyObrot) {
////                                            System.out.println("sprawdzam piec turn");
////                                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
////                                        }
////
////                                    }
////                                }
////                                licytacjaWGrze = false;
////                            }
////
////                        }
//
//                        System.out.println("KONIEC KOLEJKI na flopie");
//
//
//
//
//                    }

                                // od small blind zaczynamy ruchy, a pozniej tak samo, tylko ze warunkiem bedzie kartaT == null
                            } else if (rozgrywka.getPobierzBlind() == 0) {
                                if (pierwszyObrot == true) {
                                    naszRuchCheck( naszGracz );
                                    try {
                                        ruchGraczyPoBigBlindDoNas();
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    } catch (ClassNotFoundException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                } else if (pierwszyObrot == false) {
                                    naszRuchCheck( naszGracz );
                                    sprawdzenieIPokazanieKart();
                                    if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli == false) {
                                        try {
                                            sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        } catch (ClassNotFoundException classNotFoundException) {
                                            classNotFoundException.printStackTrace();
                                        }
                                    }
                                }
                            }
                            repaint();
                        }

                    }
                }
            }
        } );
    }

    private void przyciskFoldAkcja() {
        fold.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pulaGlowna = 0;
                for (Gracz g : rozgrywka.getGracze()) {
                    g.setPulaZetonowGracza( 0 );
                    g.setBlind( 0 );
                }

                rozgrywka.setTaliaKart( new TaliaKart() );

                rozgrywka.usunKartyZReki();
                rozgrywka.usunKartyStol();

                kartaF1 = null;
                kartaF2 = null;
                kartaF3 = null;
                kartaT = null;
                kartaR = null;


                rozgrywka.rozdajKartyDoReki( rozgrywka.getTaliaKart() );

                gracz0k1 = new ImageIcon( zapiszObrazDlaGraczy( 0 ) ).getImage();
                gracz0k2 = new ImageIcon( zapiszObrazDlaGraczy( 1 ) ).getImage();

                rozgrywka.rozdajBlind();
                dodajZdjecieSmallBlind();


                for (Gracz g : rozgrywka.getGracze()) {
                    if (g.getIloscZetonow() < 0) {
                        rozgrywka.getGracze().remove( g );
                        rozgrywka.setLiczbaGraczy( rozgrywka.getLiczbaGraczy() - 1 );
                    }
                }

                rozgrywka.rozdajFlop();
                rozgrywka.rozdajTurn();
                rozgrywka.rozdajRiver();


                repaint();

                for (Gracz g : rozgrywka.getGracze()) {
                    System.out.println( g.getNick() + "            " + g.getBlind() );
                }


                betB = 0;

                repaint();

            }

        } );

    }

//    private void sprawdzenieIlosciGraczyNaFlopTurnRiver() {
//
//        iloscGraczyZKartami = 0;
//        if (rozgrywka.getPobierzBlind() == 0) {
//            for (Gracz g : rozgrywka.getGracze()) {
//                if (g.getKartyWRece().size() != 0 && kartaF1 == null) {
//                    iloscGraczyZKartami++;
//                }
//            }
//
//        }
//    }

    private void ruchGraczyPoBigBlindDoNas() throws InterruptedException, SQLException, ClassNotFoundException {
        if (kartaF1 == null) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        if (rozgrywka.getKtoBlind() == 5 && rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                            repaint();
                        } else {
                            rozgrywka.ruchGracza( i );
                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                            repaint();
                        }
                    }
                }
            }
            pierwszyObrot = false;
            licytacjaWGrze = false;
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        } else if (kartaT == null && kartaF1 != null) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                        if (i == 5) {
                            if (rozgrywka.getGracze().get( 5 ).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza( i );
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza( i );
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrot = false;
            licytacjaWGrze = false;
            sprawdzeniePlusEwWylozenieKartNaStolTURN();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();


        } else if (kartaR == null && kartaT != null && kartaF1 != null) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                        if (i == 5) {
                            if (rozgrywka.getGracze().get( 5 ).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza( i );
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza( i );
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrot = false;
            licytacjaWGrze = false;
            sprawdzeniePlusEwWylozenieKartNaStolRiver();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        } else if (kartaR != null && kartaT != null && kartaF1 != null) {
            if (rozgrywka.getPobierzBlind() == 0) {
                for (int i = (rozgrywka.getPobierzBlind() + 1); i < rozgrywka.getGracze().size(); i++) {
                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0) {
                        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
                        if (i == 5) {
                            if (rozgrywka.getGracze().get( 5 ).getPulaZetonowGracza() < wartoscTmp) {
                                rozgrywka.ruchGracza( i );
                                repaint();
                            }
                        } else {
                            rozgrywka.ruchGracza( i );
                            repaint();
                            System.out.println();
                        }
                    }
                }
            }
            pierwszyObrot = false;
            licytacjaWGrze = false;
            sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        }

    }


    private void ruchGraczyPoSmallBlindDoNas() throws InterruptedException, SQLException, ClassNotFoundException {

        if (rozgrywka.getPobierzBlind() == 0 && pierwszyObrot) {
            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0) {
                    rozgrywka.ruchGracza( i );
                    repaint();
                }
            }
//        }else if( rozgrywka.getPobierzBlind() == 0 && !pierwszyObrot) {
//            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size(); i++) {
//                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0) {
//                    rozgrywka.ruchGracza( i );
//                    repaint();
//                }
//            }
        } else if (rozgrywka.getPobierzBlind() != 0) {
            for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0) {
                    rozgrywka.ruchGracza( i );
                    repaint();
                }
            }
        } else if (rozgrywka.getKtoBlind() == 0) {
            for (int i = rozgrywka.getPobierzBlind(); i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0) {
                    rozgrywka.ruchGracza( i );
                    repaint();
                }
            }

        }
//        else {
//
//            if (rozgrywka.getPobierzBlind() == 0 && pierwszyObrot) {
//                rozgrywka.ruchGracza( 5 );
//                repaint();
//
//            } else {
//
//                for (int i = rozgrywka.getKtoBlind(); i < rozgrywka.getGracze().size(); i++) {
//                    if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0) {
//                        rozgrywka.ruchGracza( i );
//                        if (!pierwszyObrot && kartaT == null) {
//                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                            repaint();
//                        } else if (!pierwszyObrot && kartaR == null) {
//                            sprawdzeniePlusEwWylozenieKartNaStolRiver();
//                        } else if (!pierwszyObrot && kartaR != null) {
//                            sprawdzenieIPokazanieKart();
//                        }
//                        repaint();
//                    }
//                }
//            }
//        }


//    licytacjaWGrze= false;
//        pierwszyObrot=true;

    }

    private void sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart() {
        int iloscGraczyWGrze = rozgrywka.getGracze().size();

        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getKartyWRece().size() == 0) {
                iloscGraczyWGrze--;
            }
        }
        if (iloscGraczyWGrze == 1) {
            czyZostalJedenGracz = true;
            System.out.println( "został jeden gracz. Koniec rozdania" );

            koniecRozdania();
//            noweRozdanie();
            repaint();
        }
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyFLOP() throws InterruptedException, SQLException, ClassNotFoundException {

        if (kartaF1 == null) {

            for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                if (i == 0) {


                } else {
                    if (kartaF1 == null) {
                        try {
                            rozgrywka.ruchGracza( i );
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println( i );
                        repaint();
                    }

                    if (kartaF1 == null && !pierwszyObrot) {

                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();

                    } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
//                        System.out.println("SPRAWDZAM PO PIERWSZYM OBROCIE DLA OSTATNIEGO GRACZA");
                        pierwszyObrot = true;
                    }
                }
            }
        }

        if (kartaF1 == null) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaF1 == null) {

                    try {
                        rozgrywka.ruchGracza( i );
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    repaint();

                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                }
            }
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        pierwszyObrot = true;
        licytacjaWGrze = false;
    }


    private void sprawdzeniePoNaszymRuchuIRuchyGraczyTURN() {

        if (kartaT == null) {

            for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                if (i == 0) {
                } else {

                    if (kartaT == null) {
                        try {
                            rozgrywka.ruchGracza( i );
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }

                    if (kartaT == null && !pierwszyObrot) {

                        sprawdzeniePlusEwWylozenieKartNaStolTURN();

                    } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
                        pierwszyObrot = true;
                    }
                }
            }
        }

        if (kartaT == null) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && kartaT == null) {
                    try {
                        rozgrywka.ruchGracza( i );
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    repaint();
                    sprawdzeniePlusEwWylozenieKartNaStolTURN();
                }
            }
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        pierwszyObrot = true;
        licytacjaWGrze = false;
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyRiver() throws InterruptedException, SQLException, ClassNotFoundException {

        if (kartaR == null) {

            for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                if (i == 0) {
                } else {

                    if (kartaR == null) {
                        try {
                            rozgrywka.ruchGracza( i );
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }

                    if (kartaR == null && !pierwszyObrot) {
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();

                    } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
                        pierwszyObrot = true;
                    }
                }
            }
        }

        if (kartaR == null && rozgrywka.getPobierzBlind() == 0) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0 && kartaR == null) {
                    rozgrywka.ruchGracza( i );
                    repaint();
                    sprawdzeniePlusEwWylozenieKartNaStolRiver();
                }
            }
        }
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        pierwszyObrot = true;
        licytacjaWGrze = false;
    }

    private void sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja() throws InterruptedException, SQLException, ClassNotFoundException {
        Gracz naszGracz = rozgrywka.getGracze().get( 0 );
        if (kartaF1 != null && kartaT != null && kartaR != null) {

            for (int i = 0; i <= rozgrywka.getPobierzBlind(); i++) {
                if (i == 0) {
                } else {

                    if (kartaR != null) {
                        rozgrywka.ruchGracza( i );
                        repaint();
                    }

                    if (kartaR != null && !pierwszyObrot) {
                        sprawdzenieIPokazanieKart();

                    } else if (pierwszyObrot && i == rozgrywka.getPobierzBlind()) {
                        pierwszyObrot = true;
                    }
                }
            }
        }

        if (kartaR != null && rozgrywka.getPobierzBlind() == 0) {
            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
                if (rozgrywka.getGracze().get( i ).getKartyWRece().size() != 0 && i != 0 && kartaR == null) {
                    rozgrywka.ruchGracza( i );
                    repaint();
                    sprawdzenieIPokazanieKart();
                }
            }
        }
        licytacjaWGrze = false;
        sprawdzenieIPokazanieKart();
    }

    private void naszRuchBet(Gracz naszGracz) {
        wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );

        System.out.println( "Zrobiłem Bet" );
        if (kartaF1 == null) {
            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( (wartoscTmp + rozgrywka.getDuzyBlind()) );
                repaint();
                licytacjaWGrze = false;
                repaint();
            }
            if (!pierwszyObrot) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                pierwszyObrot = true;
            } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else if (kartaT == null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( (wartoscTmp + rozgrywka.getDuzyBlind()) );
                repaint();
                licytacjaWGrze = false;
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }

        } else if (kartaR == null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( (wartoscTmp + rozgrywka.getDuzyBlind()) );
                repaint();
                licytacjaWGrze = false;
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }
        } else if (kartaR != null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( (wartoscTmp + rozgrywka.getDuzyBlind()) );
                repaint();
                licytacjaWGrze = false;
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }
        }
//        betB = Integer.parseInt( wpisPuli.getText() );
//        rozgrywka.getGracze().get( 0 ).setIloscZetonow( rozgrywka.getGracze().get( 0 ).getIloscZetonow() - betB );
//        pulaGlowna += betB;
//
//        try {
//            wpisDoPuli = Integer.parseInt( wpisPuli.getText() );
//        } catch (NumberFormatException f) {
//            JOptionPane.showMessageDialog( null, "Wpisz liczbe żetonów, które chcesz postawić!", "WPodaj ilość żetonów", JOptionPane.INFORMATION_MESSAGE );
//        }
//        repaint();

    }

    private void naszRuchCheck(Gracz naszGracz) {
        wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );
        System.out.println( "ZROBILEM ruch" );
        if (kartaF1 == null) {


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                licytacjaWGrze = false;
                repaint();
            }


            if (!pierwszyObrot) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                pierwszyObrot = true;
            } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (kartaT == null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }

        } else if (kartaR == null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }
        } else if (kartaR != null && kartaT != null && kartaF1 != null) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                repaint();
            } else if (naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
            }
        }

    }

    private void naszRuchFLOP(Gracz naszGracz) {

        if (kartaF1 == null) {

            System.out.println( "ZROBILEM ruch" );

            wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                licytacjaWGrze = false;
                repaint();
            }


            if (!pierwszyObrot) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                pierwszyObrot = true;
            } else if (pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
                try {
                    sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void naszRuchTURN(Gracz naszGracz) {

        if (kartaT == null) {

            System.out.println( "ZROBILEM ruch" );

            wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );

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


//    private void naszRuchRiver(Gracz naszGracz) {
//
//        if (kartaR == null) {
//
//            System.out.println("ZROBILEM RUCH");
//
//            wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();
//
//            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);
//
//            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
//                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
//                naszGracz.setPulaZetonowGracza( wartoscTmp );
//                licytacjaWGrze = false;
//                repaint();
//            } else if(naszGracz.getPulaZetonowGracza() == wartoscTmp && rozgrywka.getPobierzBlind() == 0) {
//            }
//        }
//    }

    private void naszRuchPoWylozeniuPiecuKArtNaStol(Gracz naszGracz) {

        if (kartaR == null) {
            System.out.println( pierwszyObrot + "   pierwszy obrot wartosc" );
            System.out.println( "ZROBILEM RUCH" );

            wartoscTmp = rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza();

            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );


            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow( naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza() );
                naszGracz.setPulaZetonowGracza( wartoscTmp );
                licytacjaWGrze = false;
                repaint();
            }

            if (!pierwszyObrot) {
                System.out.println( "SPRAWDZENIE RAZ to moze jest tutaj kurde" );
                sprawdzenieIPokazanieKart();
            } else if (!pierwszyObrot && rozgrywka.getPobierzBlind() == 0) {
                sprawdzenieIPokazanieKart();
                System.out.println( "o co tu chodzi" );
            } else if (pierwszyObrot) {
                pierwszyObrot = false;
//                sprawdzeniePoNaszymRuchuIRuchyGraczyOstatniaLicytacja();
                System.out.println( "cos tu nie gra" );

            }

        }
        pierwszyObrot = false;
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolFLOP() throws InterruptedException, SQLException, ClassNotFoundException {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;
        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {


            for (Gracz g : rozgrywka.getGracze()) {
                if (g.getKartyWRece().size() != 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    }
                }
            }

            if (czyZostalJedenGracz == false) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.print( "POKAZUJE KARTY" );
                    kartaF1 = new ImageIcon( zapiszObrazDlaKartStol( 0 ) ).getImage();
                    kartaF2 = new ImageIcon( zapiszObrazDlaKartStol( 1 ) ).getImage();
                    kartaF3 = new ImageIcon( zapiszObrazDlaKartStol( 2 ) ).getImage();

                    dodajZetonyGraczyDoPuli();

                    repaint();

                    pierwszyObrot = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                    if (rozgrywka.getKtoBlind() != 0) {
                        ruchGraczyPoSmallBlindDoNas();
                    }

                }
            }
        }

    }

    private void sprawdzeniePlusEwWylozenieKartNaStolTURN() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (czyZostalJedenGracz == false) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.println( "POKAZUJE KARTY" );
                    kartaT = new ImageIcon( zapiszObrazDlaKartStol( 3 ) ).getImage();

                    dodajZetonyGraczyDoPuli();

                    repaint();

                    pierwszyObrot = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                    if (rozgrywka.getKtoBlind() != 0) {
                        try {
                            ruchGraczyPoSmallBlindDoNas();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
            }
        }
        licytacjaWGrze = false;
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolRiver() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (czyZostalJedenGracz == false) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

                    System.out.println( "POKAZUJE KARTY" );
                    kartaR = new ImageIcon( zapiszObrazDlaKartStol( 4 ) ).getImage();

                    dodajZetonyGraczyDoPuli();

                    repaint();

                    pierwszyObrot = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                    if (rozgrywka.getKtoBlind() != 0) {
                        try {
                            ruchGraczyPoSmallBlindDoNas();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;
            }
        }
        licytacjaWGrze = false;
    }

    private void sprawdzenieIPokazanieKart() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza( wartoscTmp );

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
//                        System.out.println("Sprawdzenie " + g.getNick());
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuli) {
                if (czyZostalJedenGracz == false) {
                    System.out.println( "Gracze pokazuja karty" );
//                    rozgrywka.sprawdzanieKart();

                    for (Gracz g : rozgrywka.getGracze()) {
                        if (g.getKartyWRece().size() != 0) {
//                            odsłońZdjeęciaKartGracza1( 1);
//                            odsłońZdjeęciaKartGracza2( 2 );

                        }
                    }

                    dodajZetonyGraczyDoPuli();

                    repaint();

                    pierwszyObrot = false;
                    licytacjaWGrze = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuli = false;

                } else if (czyZostalJedenGracz == true) {
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
//
//    private void checkPrzedTurn() {
//
//        kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
//        repaint();
//
//    }
//
//    private void checkPrzedRiver() {
//
//        kartaR = new ImageIcon(zapiszObrazDlaKartStol(4)).getImage();

//        repaint();

//    }


    private void dodaniePolGraczyZeWzgleduNaIchIlosc(Graphics g) {

        if (rozgrywka.getLiczbaGraczy() == 2) {
            g.drawString( rozgrywka.getGracze().get( 0 ).getNick(), 1400, 870 );
            g.drawImage( gracz0k1, 1375, 731, null );
            g.drawImage( gracz0k2, 1449, 731, null );
            g.drawString( rozgrywka.getGracze().get( 1 ).getNick(), 425, 870 );
            g.drawImage( gracz1k1, 405, 731, null );
            g.drawImage( gracz1k2, 479, 731, null );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 0 ).getIloscZetonow() ), 1430, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 1 ).getIloscZetonow() ), 460, 907 );


        } else if (rozgrywka.getLiczbaGraczy() == 3) {

            g.drawString( rozgrywka.getGracze().get( 0 ).getNick(), 1400, 870 );
            g.drawString( rozgrywka.getGracze().get( 1 ).getNick(), 425, 870 );
            g.drawString( rozgrywka.getGracze().get( 2 ).getNick(), 70, 525 );
            g.drawImage( gracz0k1, 1375, 731, null );
            g.drawImage( gracz0k2, 1449, 731, null );
            g.drawImage( gracz1k1, 405, 731, null );
            g.drawImage( gracz1k2, 479, 731, null );
            g.drawImage( gracz2k1, 60, 387, null );
            g.drawImage( gracz2k2, 134, 387, null );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 0 ).getIloscZetonow() ), 1430, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 1 ).getIloscZetonow() ), 460, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 2 ).getIloscZetonow() ), 115, 562 );

        } else if (rozgrywka.getLiczbaGraczy() == 4) {
            g.drawString( rozgrywka.getGracze().get( 0 ).getNick(), 1400, 870 );
            g.drawString( rozgrywka.getGracze().get( 1 ).getNick(), 425, 870 );
            g.drawString( rozgrywka.getGracze().get( 2 ).getNick(), 70, 525 );
            g.drawString( rozgrywka.getGracze().get( 3 ).getNick(), 425, 183 );
            g.drawImage( gracz0k1, 1375, 731, null );
            g.drawImage( gracz0k2, 1449, 731, null );
            g.drawImage( gracz1k1, 405, 731, null );
            g.drawImage( gracz1k2, 479, 731, null );
            g.drawImage( gracz2k1, 60, 387, null );
            g.drawImage( gracz2k2, 134, 387, null );
            g.drawImage( gracz3k1, 405, 45, null );
            g.drawImage( gracz3k2, 479, 45, null );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 0 ).getIloscZetonow() ), 1430, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 1 ).getIloscZetonow() ), 460, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 2 ).getIloscZetonow() ), 115, 562 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 3 ).getIloscZetonow() ), 460, 220 );


        } else if (rozgrywka.getLiczbaGraczy() == 5) {
            g.drawString( rozgrywka.getGracze().get( 0 ).getNick(), 1400, 870 );
            g.drawString( rozgrywka.getGracze().get( 1 ).getNick(), 425, 870 );
            g.drawString( rozgrywka.getGracze().get( 2 ).getNick(), 70, 525 );
            g.drawString( rozgrywka.getGracze().get( 3 ).getNick(), 425, 183 );
            g.drawString( rozgrywka.getGracze().get( 4 ).getNick(), 1400, 183 );
            g.drawImage( gracz0k1, 1375, 731, null );
            g.drawImage( gracz0k2, 1449, 731, null );
            g.drawImage( gracz1k1, 405, 731, null );
            g.drawImage( gracz1k2, 479, 731, null );
            g.drawImage( gracz2k1, 60, 387, null );
            g.drawImage( gracz2k2, 134, 387, null );
            g.drawImage( gracz3k1, 405, 45, null );
            g.drawImage( gracz3k2, 479, 45, null );
            g.drawImage( gracz4k1, 1375, 45, null );
            g.drawImage( gracz4k2, 1449, 45, null );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 0 ).getIloscZetonow() ), 1430, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 1 ).getIloscZetonow() ), 460, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 2 ).getIloscZetonow() ), 115, 562 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 3 ).getIloscZetonow() ), 460, 220 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 4 ).getIloscZetonow() ), 1430, 220 );

        } else if (rozgrywka.getLiczbaGraczy() == 6) {

            g.drawString( rozgrywka.getGracze().get( 0 ).getNick(), 1400, 870 );
            g.drawString( rozgrywka.getGracze().get( 1 ).getNick(), 425, 870 );
            g.drawString( rozgrywka.getGracze().get( 2 ).getNick(), 70, 525 );
            g.drawString( rozgrywka.getGracze().get( 3 ).getNick(), 425, 183 );
            g.drawString( rozgrywka.getGracze().get( 4 ).getNick(), 1400, 183 );
            g.drawString( rozgrywka.getGracze().get( 5 ).getNick(), 1750, 525 );
            g.drawImage( gracz0k1, 1375, 731, null );
            g.drawImage( gracz0k2, 1449, 731, null );
            g.drawImage( gracz1k1, 405, 731, null );
            g.drawImage( gracz1k2, 479, 731, null );
            g.drawImage( gracz2k1, 60, 387, null );
            g.drawImage( gracz2k2, 134, 387, null );
            g.drawImage( gracz3k1, 405, 45, null );
            g.drawImage( gracz3k2, 479, 45, null );
            g.drawImage( gracz4k1, 1375, 45, null );
            g.drawImage( gracz4k2, 1449, 45, null );
            g.drawImage( gracz5k1, 1720, 387, null );
            g.drawImage( gracz5k2, 1794, 387, null );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 0 ).getIloscZetonow() ), 1430, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 1 ).getIloscZetonow() ), 460, 907 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 2 ).getIloscZetonow() ), 115, 562 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 3 ).getIloscZetonow() ), 460, 220 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 4 ).getIloscZetonow() ), 1430, 220 );
            g.drawString( String.valueOf( rozgrywka.getGracze().get( 5 ).getIloscZetonow() ), 1775, 562 );
        }

    }

    private void dodajKartyStolFlop(Graphics g) {

        g.drawImage( kartaF1, 777, 487, null );
        g.drawImage( kartaF2, 851, 487, null );
        g.drawImage( kartaF3, 925, 487, null );

    }

    private void dodajKartyStolTurn(Graphics g) {
        g.drawImage( kartaT, 999, 487, null );
    }

    private void dodajKartyStolRiver(Graphics g) {
        g.drawImage( kartaR, 1073, 487, null );
    }

    private void dodajZetonSmallBlind(Graphics g) {
        if (rozgrywka.getPobierzBlind() - 1 == 0) {
            g.drawImage( smallBlind, 1200, 790, null );
        } else if (rozgrywka.getPobierzBlind() - 1 == 1) {
            g.drawImage( smallBlind, 670, 790, null );
        } else if (rozgrywka.getPobierzBlind() - 1 == 2) {
            g.drawImage( smallBlind, 320, 480, null );
        } else if (rozgrywka.getPobierzBlind() - 1 == 3) {
            g.drawImage( smallBlind, 650, 260, null );
        } else if (rozgrywka.getPobierzBlind() - 1 == 4) {
            g.drawImage( smallBlind, 1200, 260, null );
        } else if (rozgrywka.getPobierzBlind() + 5 == 5) {
            g.drawImage( smallBlind, 1570, 480, null );
        }
    }

    private ImageIcon dodajZdjecieSmallBlind() {
        smallBlind = new ImageIcon( "zdjecia\\sb.jpg" ).getImage();
        return new ImageIcon( smallBlind );
    }

    private void dodajZetonBibBlind(Graphics g) {
        if (rozgrywka.getPobierzBlind() == 0) {
            g.drawImage( bigBlind, 1200, 790, null );
        } else if (rozgrywka.getPobierzBlind() == 1) {
            g.drawImage( bigBlind, 670, 790, null );
        } else if (rozgrywka.getPobierzBlind() == 2) {
            g.drawImage( bigBlind, 320, 480, null );
        } else if (rozgrywka.getPobierzBlind() == 3) {
            g.drawImage( bigBlind, 650, 260, null );
        } else if (rozgrywka.getPobierzBlind() == 4) {
            g.drawImage( bigBlind, 1200, 260, null );
        } else if (rozgrywka.getPobierzBlind() == 5) {
            g.drawImage( bigBlind, 1570, 480, null );
        }
    }

    private ImageIcon dodajZdjecieBigBlind() {
        bigBlind = new ImageIcon( "zdjecia\\bb.jpg" ).getImage();
        return new ImageIcon( bigBlind );
    }

    private void dodajZetonDealer(Graphics g) {
        if (rozgrywka.getPobierzBlind() - 2 == 0) {
            g.drawImage( dealer, 1200, 790, null );
        } else if (rozgrywka.getPobierzBlind() - 2 == 1) {
            g.drawImage( dealer, 670, 790, null );
        } else if (rozgrywka.getPobierzBlind() - 2 == 2) {
            g.drawImage( dealer, 320, 480, null );
        } else if (rozgrywka.getPobierzBlind() - 2 == 3) {
            g.drawImage( dealer, 650, 260, null );
        } else if (rozgrywka.getPobierzBlind() + 4 == 4) {
            g.drawImage( dealer, 1200, 260, null );
        } else if (rozgrywka.getPobierzBlind() + 4 == 5) {
            g.drawImage( dealer, 1570, 480, null );
        }
    }

    private ImageIcon dodajZdjecieDealer() {
        dealer = new ImageIcon( "zdjecia\\dealer.jpg" ).getImage();
        return new ImageIcon( dealer );
    }

    private void dodajIloscPostawionychZetonowGracza0(Graphics g) {
        if (rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 0 ).getPulaZetonowGracza() + betB) + "$";
            g.drawString( iloscZetonowPostawionych, 1215, 710 );
        }
    }

    private void dodajIloscPostawionychZetonowGracza1(Graphics g) {
        if (rozgrywka.getGracze().get( 1 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 1 ).getPulaZetonowGracza()) + "$";
            g.drawString( iloscZetonowPostawionych, 680, 710 );
        }
    }

    private void dodajIloscPostawionychZetonowGracza2(Graphics g) {
        if (rozgrywka.getGracze().get( 2 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 2 ).getPulaZetonowGracza()) + "$";
            g.drawString( iloscZetonowPostawionych, 450, 520 );
        }
    }

    private void dodajIloscPostawionychZetonowGracza3(Graphics g) {
        if (rozgrywka.getGracze().get( 3 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 3 ).getPulaZetonowGracza()) + "$";
            g.drawString( iloscZetonowPostawionych, 680, 360 );
        }
    }

    private void dodajIloscPostawionychZetonowGracza4(Graphics g) {
        if (rozgrywka.getGracze().get( 4 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 4 ).getPulaZetonowGracza()) + "$";
            g.drawString( iloscZetonowPostawionych, 1215, 360 );
        }
    }

    private void dodajIloscPostawionychZetonowGracza5(Graphics g) {
        if (rozgrywka.getGracze().get( 5 ).getPulaZetonowGracza() > 0) {
            var iloscZetonowPostawionych = " " + (rozgrywka.getGracze().get( 5 ).getPulaZetonowGracza()) + "$";
            g.drawString( iloscZetonowPostawionych, 1420, 520 );
        }
    }

    private void noweRozdanie() {

        getRozgrywka().rozdajKartyDoReki( rozgrywka.getTaliaKart() );

        dodajZdjeciaKartGraczy();

        getRozgrywka().rozdajFlop();
        getRozgrywka().rozdajTurn();
        getRozgrywka().rozdajRiver();

        rozgrywka.rozdajBlind();
//        rozgrywka.setKtoBlind(0);
        dodajZdjecieSmallBlind();
        dodajZdjecieBigBlind();


//        JButton button = new JButton();
//        button.setBounds(877, 686, 243, 83);
//        button.setBorder(obramowanie);
//        add(button);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                System.out.println("KRUSASDSADASD");
//                button.setVisible(false);
//
//            }
//        });

        dodajZdjecieDealer();

        System.out.println( rozgrywka.getPobierzBlind() );

        System.out.println( "Pobierz blind :" + rozgrywka.getPobierzBlind() );
        System.out.println( "KTO BLIND " + rozgrywka.getKtoBlind() );
        pierwszyObrot = true;


        for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++) {
            try {
                rozgrywka.ruchGracza( i );
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            repaint();
        }

//        dodajZetonyGraczaDoPuli();
        if (rozgrywka.getPobierzBlind() == 0) {
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
        }
        repaint();

    }

    private void koniecRozdania() {


        pulaGlowna = 0;
        for (Gracz g : rozgrywka.getGracze()) {
            g.setPulaZetonowGracza( 0 );
            g.setBlind( 0 );
        }

        rozgrywka.setTaliaKart( new TaliaKart() );

        rozgrywka.usunKartyZReki();
        rozgrywka.usunKartyStol();

        kartaF1 = null;
        kartaF2 = null;
        kartaF3 = null;
        kartaT = null;
        kartaR = null;

        graj.setVisible( true );

        repaint();

    }

    public void dodajZetonyGraczyDoPuli() {

        for (Gracz g : rozgrywka.getGracze()) {
            pulaGlowna += g.getPulaZetonowGracza();
            g.setPulaZetonowGracza( 0 );
        }
    }

    private void dodajZdjeciaKartGraczy() {

        //sprawdzic sobie na spokojnie jak sie uzywa timera

//
//        int delay = 2000; //milliseconds
//        ActionListener taskPerformer = new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();
//                repaint();
//            }
//        };
//        new Timer(delay, taskPerformer).start();
//
//
//        ActionListener taskPerformer1 = new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                gracz1k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
//                repaint();
//            }
//        };
//        new Timer(delay, taskPerformer1).start();
        gracz0k1 = new ImageIcon( zapiszObrazDlaGraczy( 0 ) ).getImage();
        gracz1k1 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz2k1 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz3k1 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz4k1 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz5k1 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz0k2 = new ImageIcon( zapiszObrazDlaGraczy( 1 ) ).getImage();
        gracz1k2 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz2k2 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz3k2 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz4k2 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();
        gracz5k2 = new ImageIcon( "zdjecia\\Green_back.jpg" ).getImage();

    }


//    public Image odsłońZdjeęciaKartGracza1(int i) {
//        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 1 ).getKartyWRece().get( i ).getKolor();
//        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 1 ).getKartyWRece().get( i ).getWartosc();
//
//        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
//    }
//    public Image odsłońZdjeęciaKartGracza2(int i) {
//        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 2 ).getKartyWRece().get( i ).getKolor();
//        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 2 ).getKartyWRece().get( i ).getWartosc();
//
//        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
//    }
//    public Image odsłońZdjeęciaKartGracza3(int i) {
//        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 3 ).getKartyWRece().get( i ).getKolor();
//        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 3 ).getKartyWRece().get( i ).getWartosc();
//
//        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
//    }
//    public Image odsłońZdjeęciaKartGracza4(int i) {
//        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 4 ).getKartyWRece().get( i ).getKolor();
//        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 4 ).getKartyWRece().get( i ).getWartosc();
//
//        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
//    }
//    public Image odsłońZdjeęciaKartGracza5(int i) {
//        Kolor kolorKartyWReku = rozgrywka.getGracze().get( 5 ).getKartyWRece().get( i ).getKolor();
//        Wartosc numerKartyWReku = rozgrywka.getGracze().get( 5 ).getKartyWRece().get( i ).getWartosc();
//
//        return zwrocZdjecieKartyOKolorzeIWartosci( numerKartyWReku, kolorKartyWReku );
//    }


    private void dodajTlo() {

        imageFile = new File( "zdjecia\\stol_final.jpg" );

        try {
            image = ImageIO.read( imageFile );
        } catch (IOException e) {
            System.err.println( "Blad odczytu obrazka" );
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

        graj.setVisible( false );

        noweRozdanie();


    }

}