package klasy.menu;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.BazaDanych;
import klasy.Gracz;
import klasy.Rozgrywka;
import klasy.karty.Karta;
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
import java.util.Comparator;

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

    private Image kartaF1;
    private Image kartaF2;
    private Image kartaF3;
    private Image kartaT;
    private Image kartaR;
    private Image smallBlind;
    private Image bigBlind;
    private Image dealer;
    private Image ruch;

    private int wartoscTmp;

    private boolean pierwszyObrot;
    private boolean czyZostalJedenGracz;
    private boolean pierwszyObrotTurn;
    private boolean pierwszyObrotRiver;
    private boolean pierwszyObrotOstatnia;
    private boolean kartyFlop;
    private boolean kartaTurn;
    private boolean kartaRiver;

    boolean pierwszaTura = true;
    private int ruchGraczaTmp;

    private final String kolorRewers;

    private final Border obramowanie = BorderFactory.createEmptyBorder();

    private Rozgrywka rozgrywka;

    private int betB;
    private int iloscMoichZetonow;
    private JTextField mojeZetony;
    private int iloscRuchowNaFlopie;
    private int iloscRuchowNaTurn;
    private int iloscGraczyWGrze;
    private int iloscRuchowNaRiver;
    private int iloscRuchowOstatnia;
    private int iloscGraczyNaAllIn;

    private Gracz naszGracz;

    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = false;
    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = false;
    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = false;
    private boolean czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia = false;
    private boolean koniecGry;

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
        historia.setBounds(10, 840, 355, 195);
        historia.setBorder(null);
        historia.setFont(new Font("SansSerif", Font.BOLD, 13));
        historia.setEditable(false);
        historia.setOpaque(false);
        historia.setForeground(Color.WHITE);


        JScrollPane scroll = new JScrollPane(historia);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 840, 355, 195);
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

            naszGracz = rozgrywka.getGracze().get(0);
            wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

            if (!kartyFlop) {

                if (pierwszyObrot) {

                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        historia.append(rozgrywka.doHistorii);
                        repaint();
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

                        if (ruchGraczaTmp == rozgrywka.getPobierzBlind()) {
                            pierwszyObrot = false;
                            sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                        }
                        repaint();
                    }


                } else {

                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0 && rozgrywka.getGracze().get(ruchGraczaTmp).getPulaZetonowGracza() < wartoscTmp) {

                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        historia.append(rozgrywka.doHistorii);
                        repaint();
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();


                    }


//                    if (naszGracz.getKartyWRece().size() == 0) {
//                        fold.setVisible(false);
//                        check.setVisible(false);
//                        bet.setVisible(false);
//                        nastepnyGracz.setVisible(true);
//                    }

                }

                if (!kartyFlop) {
                    ruchGraczaTmp++;
                    if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                        ruchGraczaTmp = 0;
                    }
                }


            } else if (!kartaTurn) {

                if (pierwszyObrotTurn) {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        historia.append(rozgrywka.doHistorii);
                        repaint();
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

                        iloscRuchowNaTurn--;
                        if (iloscRuchowNaTurn < 1) {
                            pierwszyObrotTurn = false;
                            sprawdzeniePlusEwWylozenieKartNaStolTURN();
                        }
                    }

                } else {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0 && rozgrywka.getGracze().get(ruchGraczaTmp).getPulaZetonowGracza() < wartoscTmp) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        historia.append(rozgrywka.doHistorii);
                        repaint();
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                    }
//                    else {
//                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
//                    }
                }

                if (!kartaTurn) {
                    ruchGraczaTmp++;
                    if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                        ruchGraczaTmp = 0;
                    }
                }

//                ruchGraczaTmp = nrRuchuGracza;
//                rozgrywka.ruchGracza( ruchGraczaTmp );
            } else if (!kartaRiver) {

                if (pierwszyObrotRiver) {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        repaint();
                        historia.append(rozgrywka.doHistorii);
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

                        iloscRuchowNaRiver--;
                        if (iloscRuchowNaRiver < 1) {
                            pierwszyObrotRiver = false;
                            sprawdzeniePlusEwWylozenieKartNaStolRiver();
                        }
                    }

                } else {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0 && rozgrywka.getGracze().get(ruchGraczaTmp).getPulaZetonowGracza() < wartoscTmp) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        historia.append(rozgrywka.doHistorii);
                        repaint();
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                    }
//                    else {
//                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
//                    }
                }

                if (!kartaRiver) {
                    ruchGraczaTmp++;
                    if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                        ruchGraczaTmp = 0;
                    }
                }

//                ruchGraczaTmp = nrRuchuGracza;
//                rozgrywka.ruchGracza( ruchGraczaTmp );
            } else {

                if (pierwszyObrotOstatnia) {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        repaint();
                        historia.append(rozgrywka.doHistorii);
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                        iloscRuchowOstatnia--;
                        if (iloscRuchowOstatnia < 1) {
                            pierwszyObrotOstatnia = false;
                            sprawdzenieIPokazanieKart();
                        }
                    }

                } else {
                    if (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() != 0 && rozgrywka.getGracze().get(ruchGraczaTmp).getPulaZetonowGracza() < wartoscTmp) {
                        try {
                            rozgrywka.ruchGracza(ruchGraczaTmp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                        repaint();
                        historia.append(rozgrywka.doHistorii);
//                        sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

                        sprawdzenieIPokazanieKart();
                    }
//                    else {
//                        sprawdzenieIPokazanieKart();
//                    }
                }

                if (kartyFlop && kartaTurn && kartaRiver) {
                    ruchGraczaTmp++;
                    if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                        ruchGraczaTmp = 0;
                    }
                }

//                ruchGraczaTmp = nrRuchuGracza;
//                rozgrywka.ruchGracza( ruchGraczaTmp );
            }

            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

            if (!koniecGry) {

                if (!czyZostalJedenGracz) {
                    pominiecieGraczyBezKart(nastepnyGracz);

                    if (ruchGraczaTmp == 0) {
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);

                        ruchGraczaTmp = 0;

                    }

                    sprawdzenieCzyGraczMaKartyIWszedlAllIn();
                }

            }


        });
    }

    private void pominiecieGraczyBezKart(JButton nastepnyGracz) {
        while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {

            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                ruchGraczaTmp = 0;
            }

            System.out.println("Pominelismy gracza " + ruchGraczaTmp);
            ruchGraczaTmp++;

            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                ruchGraczaTmp = 0;
            }

            if(rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
                if (!kartyFlop) {
                    iloscRuchowNaFlopie--;
                    System.out.println("ILOSC RUCHOW NA Flopie : " + iloscRuchowNaFlopie);
                } else if (!kartaTurn) {
                    iloscRuchowNaTurn--;
                    System.out.println("ILOSC RUCHOW NA TURN : " + iloscRuchowNaTurn);
                } else if (!kartaRiver) {
                    iloscRuchowNaRiver--;
                    System.out.println("ILOSC RUCHOW NA River : " + iloscRuchowNaRiver);
                }

                if (kartyFlop && kartaTurn && kartaRiver) {
                    iloscRuchowOstatnia--;
                    System.out.println("ILOSC RUCHOW ostatnia : " + iloscRuchowOstatnia);
                }
            }

            if (ruchGraczaTmp == 0 && naszGracz.getKartyWRece().size() != 0) {
                nastepnyGracz.setVisible(false);
                bet.setVisible(true);
                fold.setVisible(true);
                check.setVisible(true);
            }

        }
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

    public Image zapiszObrazDlaNaszegoGracza(int i) {

        Kolor kolorKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getGracze().get(0).getKartyWRece().get(i).getWartosc();

        return zwrocZdjecieKartyOKolorzeIWartosci(numerKartyWReku, kolorKartyWReku);
    }

    public Image zapiszObrazDlaGraczy(int j, int i) {

        Kolor kolorKartyWReku = rozgrywka.getGracze().get(j).getKartyWRece().get(i).getKolor();
        Wartosc numerKartyWReku = rozgrywka.getGracze().get(j).getKartyWRece().get(i).getWartosc();

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
        dodajZnacznikRuchu( g );

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
                    if (!kartyFlop) {
                        ruchGraczaTmp++;

                    }

                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();


                    if (!pierwszyObrot) {
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                    }


                } else if (!kartaTurn) {
                    naszRuchCheck(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowNaTurn < 1) {
                        pierwszyObrotTurn = false;
                    }

                    if (!pierwszyObrotTurn) {
                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                        if (kartaTurn && ruchGraczaTmp == 0) {
                            nastepnyGracz.setVisible(true);
                            fold.setVisible(false);
                            bet.setVisible(false);
                            check.setVisible(false);
                            repaint();
                        }

                    }

                } else if (!kartaRiver) {
                    naszRuchCheck(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowNaRiver < 1) {
                        pierwszyObrotRiver = false;
                    }

                    if (!pierwszyObrotRiver) {
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                        if (kartaRiver && ruchGraczaTmp == 0) {
                            nastepnyGracz.setVisible(true);
                            fold.setVisible(false);
                            bet.setVisible(false);
                            check.setVisible(false);
                            repaint();
                        }
                    }

                } else {
                    naszRuchCheck(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowOstatnia < 1) {
                        pierwszyObrotOstatnia = false;
                    }

                    if (!pierwszyObrotOstatnia) {
                        sprawdzenieIPokazanieKart();
                    }

                }

                if (!koniecGry) {
                    pominiecieGraczyBezKart(nastepnyGracz);
                    sprawdzenieCzyGraczMaKartyIWszedlAllIn();

                    if (ruchGraczaTmp == 0) {
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);

                        ruchGraczaTmp = 0;

                    }
                }


            }
        });


    }

    private void przyciskBetAkcja() {
        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                naszGracz = rozgrywka.getGracze().get(0);

                if (!kartyFlop) {
                    naszRuchBet(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    if (!kartyFlop) {
                        ruchGraczaTmp++;

                    }

                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();


                    if (!pierwszyObrot) {
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                    }


                } else if (!kartaTurn) {
                    naszRuchBet(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowNaTurn < 1) {
                        pierwszyObrotTurn = false;
                    }

                    if (!pierwszyObrotTurn) {
                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                        if (kartaTurn && ruchGraczaTmp == 0) {
                            nastepnyGracz.setVisible(true);
                            fold.setVisible(false);
                            bet.setVisible(false);
                            check.setVisible(false);
                            repaint();
                        }

                    }

                } else if (!kartaRiver) {
                    naszRuchBet(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowNaRiver < 1) {
                        pierwszyObrotRiver = false;
                    }

                    if (!pierwszyObrotRiver) {
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                        if (kartaRiver && ruchGraczaTmp == 0) {
                            nastepnyGracz.setVisible(true);
                            fold.setVisible(false);
                            bet.setVisible(false);
                            check.setVisible(false);
                            repaint();
                        }
                    }

                } else {
                    naszRuchBet(naszGracz);
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    ruchGraczaTmp++;
                    nastepnyGracz.setVisible(true);
                    fold.setVisible(false);
                    bet.setVisible(false);
                    check.setVisible(false);
                    repaint();

                    if (iloscRuchowOstatnia < 1) {
                        pierwszyObrotOstatnia = false;
                    }

                    if (!pierwszyObrotOstatnia) {
                        sprawdzenieIPokazanieKart();
                    }

                }

                if (!koniecGry) {
                    pominiecieGraczyBezKart(nastepnyGracz);
                    sprawdzenieCzyGraczMaKartyIWszedlAllIn();

                    if (ruchGraczaTmp == 0) {
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);

                        ruchGraczaTmp = 0;

                    }
                }


            }
        });
    }

    private void przyciskFoldAkcja() {
        fold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                naszGracz = rozgrywka.getGracze().get(0);

                ruchGraczaTmp++;
                naszRuchFold(naszGracz);
                sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                nastepnyGracz.setVisible(true);
                fold.setVisible(false);
                bet.setVisible(false);
                check.setVisible(false);
                repaint();

                if (!kartyFlop) {

                    if (!pierwszyObrot) {
                        sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                    }

                }

                if (kartyFlop && !kartaTurn) {

                    if (!pierwszyObrotTurn) {
                        sprawdzeniePlusEwWylozenieKartNaStolTURN();
                    }

                }

                if (kartyFlop && kartaTurn && !kartaRiver) {

                    if (!pierwszyObrotRiver) {
                        sprawdzeniePlusEwWylozenieKartNaStolRiver();
                    }

                }

                if (kartyFlop && kartaTurn && kartaRiver) {

                    if (!pierwszyObrotOstatnia) {
                        sprawdzenieIPokazanieKart();
                    }

                }
                repaint();

            }
        });

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
                    d.setIloscZetonow(d.getIloscZetonow() + rozgrywka.getPulaGlowna());
                    repaint();
                }
            }

            koniecRozdania();

//            noweRozdanie();
            repaint();
        } else if (iloscGraczyWGrze > 1) {
            czyZostalJedenGracz = false;
        }
        repaint();
    }

    private void naszRuchBet(Gracz naszGracz) {

//        naszGracz = rozgrywka.getGracze().get(0);

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
        historia.append(rozgrywka.getGracze().get(0).getNick() + " zrobił bet \n");
        System.out.println("Zrobiłem Bet");

        if (!kartyFlop) {
            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
            }
            if (rozgrywka.getPobierzBlind() == 0) {
                pierwszyObrot = false;
            }


//                while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                    System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                    ruchGraczaTmp++;
//
//                }


        } else if (!kartaTurn) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
            }
            iloscRuchowNaTurn--;
            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                ruchGraczaTmp = 0;
            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }

        } else if (!kartaRiver) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
            }
            iloscRuchowNaRiver--;
            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                ruchGraczaTmp = 0;
            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }
        }
        if (kartyFlop && kartaTurn && kartaRiver) {

            if (naszGracz.getPulaZetonowGracza() <= wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - (wartoscTmp + rozgrywka.getDuzyBlind()) + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza((wartoscTmp + rozgrywka.getDuzyBlind()));
                repaint();
            }
            iloscRuchowOstatnia--;
            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
                ruchGraczaTmp = 0;
            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }
        }



//        check.setVisible(false);
//        bet.setVisible(false);
//        fold.setVisible(false);
//        nastepnyGracz.setVisible(true);
//        ruchGraczaTmp++;

    }

    private void naszRuchCheck(Gracz naszGracz) {

        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        rozgrywka.setLineBaza("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + rozgrywka.getGracze().get(0).getNick() + " wykonał/a check.");

        try {
            new BazaDanych( rozgrywka.getMe() );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        historia.append(rozgrywka.getGracze().get(0).getNick() + " check.\n");
        System.out.println("ZROBILEM ruch");

        if (!kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                repaint();
            }

            if (rozgrywka.getPobierzBlind() == 0) {
                pierwszyObrot = false;
//                sprawdzeniePlusEwWylozenieKartNaStolFLOP();
            }

//            if (!kartyFlop) {
//
//                while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                    System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                    ruchGraczaTmp++;
//
//                }
//            }

        } else if (!kartaTurn) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                repaint();
            }
            iloscRuchowNaTurn--;
//            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
//                ruchGraczaTmp = 0;
//            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }
        } else if (!kartaRiver) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                repaint();
            }
            iloscRuchowNaRiver--;
//            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
//                ruchGraczaTmp = 0;
//            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }
        }
        if (kartaRiver && kartaTurn && kartyFlop) {

            if (naszGracz.getPulaZetonowGracza() < wartoscTmp) {
                naszGracz.setIloscZetonow(naszGracz.getIloscZetonow() - wartoscTmp + naszGracz.getPulaZetonowGracza());
                naszGracz.setPulaZetonowGracza(wartoscTmp);
                repaint();
            }
            iloscRuchowOstatnia--;

//            if (ruchGraczaTmp == rozgrywka.getGracze().size()) {
//                ruchGraczaTmp = 0;
//            }
//            while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {
//
//                System.out.println("Pominelismy gracza " + ruchGraczaTmp);
//                ruchGraczaTmp++;
//
//            }


        }


    }

    private void naszRuchFold(Gracz naszGracz) {

        historia.append(rozgrywka.getGracze().get(0).getNick() + " fold.\n");

        wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonowGracza();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        rozgrywka.setLineBaza("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + rozgrywka.getGracze().get(0).getNick() + " wykonał fold.");

        rozgrywka.getGracze().get(0).getKartyWRece().removeAll(rozgrywka.getGracze().get(0).getKartyWRece());

//        try {
//            new BazaDanych(rozgrywka.getMe());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


        if (!kartyFlop) {
            iloscRuchowNaFlopie--;
        }
        if (!kartaTurn && kartyFlop) {
            iloscRuchowNaTurn--;
        }
        if (kartaTurn && kartyFlop && !kartaRiver) {
            iloscRuchowNaRiver--;
        }
        if (kartaTurn && kartyFlop && kartaRiver) {
            iloscRuchowOstatnia--;
        }

        repaint();


        if (!kartyFlop) {

            if (rozgrywka.getPobierzBlind() == 0) {
                pierwszyObrot = false;
            }
            ruchGraczaTmp = 0;

        }

        while (rozgrywka.getGracze().get(ruchGraczaTmp).getKartyWRece().size() == 0 || rozgrywka.getGracze().get(ruchGraczaTmp).getIloscZetonow() == 0) {

            System.out.println("Pominelismy gracza " + ruchGraczaTmp);
            ruchGraczaTmp++;

        }


    }

    private void sprawdzeniePlusEwWylozenieKartNaStolFLOP() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop) {


            for (Gracz g : rozgrywka.getGracze()) {
                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow() > 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = false;
                        pierwszyObrot = false;
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
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop) {

                    System.out.println("POKAZUJE KARTY");
                    historia.append("Pokazuje karty flop\n");

                    kartaF1 = new ImageIcon(zapiszObrazDlaKartStol(0)).getImage();
                    kartaF2 = new ImageIcon(zapiszObrazDlaKartStol(1)).getImage();
                    kartaF3 = new ImageIcon(zapiszObrazDlaKartStol(2)).getImage();


                    dodajZetonyGraczyDoPuli();

                    kartyFlop = true;

                    repaint();

                    pierwszyObrotTurn = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = false;


                    if (rozgrywka.getPobierzBlind() == 0) {
                        ruchGraczaTmp = rozgrywka.getGracze().size() - 1;
                    } else {
                        ruchGraczaTmp = rozgrywka.getPobierzBlind() - 1;
                    }

                    pominiecieGraczyBezKart(nastepnyGracz);

                    System.out.println(ruchGraczaTmp + "  ten gracz powninien wukonać teraz swój ruch");
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    iloscRuchowNaTurn = iloscGraczyWGrze;

                    if ((rozgrywka.getPobierzBlind() - 1) == 0) {
                        ruchGraczaTmp = 0;
                    }

                    wartoscTmp = 0;

                    if (ruchGraczaTmp == 0) {
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);

                        ruchGraczaTmp = 0;

                    }
                }
            }
            czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = false;

        }
        repaint();
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolTURN() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow() > 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = false;
                    }
                }
            }

            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            System.out.println("sprawdzam czy gracze wlozyli taka sama ilosc zetonow do puli : " + czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn);
            if (!czyZostalJedenGracz) {
                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn) {

                    System.out.println("POKAZUJE KARTY");
                    historia.append("Pokazuje karte Turn\n");
                    kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();

                    dodajZetonyGraczyDoPuli();

                    kartaTurn = true;

                    repaint();

                    pierwszyObrotRiver = true;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = false;


                    if (rozgrywka.getPobierzBlind() == 0) {
                        ruchGraczaTmp = rozgrywka.getGracze().size() - 1;
                    } else {
                        ruchGraczaTmp = rozgrywka.getPobierzBlind() - 1;
                    }


                    pominiecieGraczyBezKart(nastepnyGracz);

                    System.out.println(ruchGraczaTmp + "  ten gracz powninien wukonać teraz swój ruch");
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    iloscRuchowNaRiver = iloscGraczyWGrze;

                    if ((rozgrywka.getPobierzBlind() - 1) == 0) {
                        ruchGraczaTmp = 0;
                    }

                    wartoscTmp = 0;

                    if (ruchGraczaTmp == 0) {
                        System.out.println("czy jestem tutaj");
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);
                        System.out.println(" nie no kurwa to sie pokazuje tutaj  sie wyswietla pokazanie naszych przyciskow ruchu");

                        ruchGraczaTmp = 0;

                    }

                }
            }
            czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = false;
        }

        repaint();
    }

    private void sprawdzeniePlusEwWylozenieKartNaStolRiver() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow() > 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = false;
                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();

            if (!czyZostalJedenGracz) {

                if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver) {

                    System.out.println("POKAZUJE KARTY");
                    historia.append("Pokazuje karte River\n");
                    kartaR = new ImageIcon(zapiszObrazDlaKartStol(4)).getImage();

                    dodajZetonyGraczyDoPuli();
                    kartaRiver = true;
                    repaint();
                    pierwszyObrotOstatnia = true;

                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = false;

                    if (rozgrywka.getPobierzBlind() == 0) {
                        ruchGraczaTmp = rozgrywka.getGracze().size() - 1;
                    } else {
                        ruchGraczaTmp = rozgrywka.getPobierzBlind() - 1;
                    }

//                        try {
//
//                        } catch (InterruptedException interruptedException) {
//                            interruptedException.printStackTrace();
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
                    pominiecieGraczyBezKart(nastepnyGracz);


                    System.out.println(ruchGraczaTmp + "  ten gracz powninien wukonać teraz swój ruch");
                    sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
                    iloscRuchowOstatnia = iloscGraczyWGrze;

                    if ((rozgrywka.getPobierzBlind() - 1) == 0) {
                        ruchGraczaTmp = 0;
                    }

                    wartoscTmp = 0;

                    if (ruchGraczaTmp == 0) {
                        System.out.println("czy jestem tutaj");
                        fold.setVisible(true);
                        check.setVisible(true);
                        bet.setVisible(true);
                        nastepnyGracz.setVisible(false);
                        System.out.println(" nie no kurwa to sie pokazuje tutaj  sie wyswietla pokazanie naszych przyciskow ruchu");

                        ruchGraczaTmp = 0;

                    }

                }


            }

            czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = false;
        }

        repaint();
    }

    private void sprawdzenieIPokazanieKart() {

        wartoscTmp = najwyzszaWartoscPostawionychZetonowGracza(wartoscTmp);

        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia = true;

        while (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia) {

            for (Gracz g : rozgrywka.getGracze()) {

                if (g.getKartyWRece().size() != 0 && g.getIloscZetonow() > 0) {

                    if (g.getPulaZetonowGracza() != wartoscTmp && g.getKartyWRece().size() != 0) {
//                        System.out.println("Sprawdzenie " + g.getNick());
                        czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia = false;

                    }
                }
            }
            sprawdzenieCzyZostalJedenGraczPrzedPokazaniemKart();
            if (czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia) {
                if (!czyZostalJedenGracz) {
                    dodajZetonyGraczyDoPuli();
                    repaint();
                    System.out.println( "Gracze pokazuja karty" );

                    if(rozgrywka.getGracze().size() >=2) {
                        if (rozgrywka.getGracze().get( 1 ).getKartyWRece().size() != 0) {
                            gracz1k1 = new ImageIcon( zapiszObrazDlaGraczy( 1, 0 ) ).getImage();
                            gracz1k2 = new ImageIcon( zapiszObrazDlaGraczy( 1, 1 ) ).getImage();
                        }
                    }

                    if(rozgrywka.getGracze().size() >= 3) {
                        if (rozgrywka.getGracze().get( 2 ).getKartyWRece().size() != 0) {
                            gracz2k1 = new ImageIcon( zapiszObrazDlaGraczy( 2, 0 ) ).getImage();
                            gracz2k2 = new ImageIcon( zapiszObrazDlaGraczy( 2, 1 ) ).getImage();
                        }
                    }

                    if(rozgrywka.getGracze().size() >= 4) {
                        if (rozgrywka.getGracze().get( 3 ).getKartyWRece().size() != 0) {
                            gracz3k1 = new ImageIcon( zapiszObrazDlaGraczy( 3, 0 ) ).getImage();
                            gracz3k2 = new ImageIcon( zapiszObrazDlaGraczy( 3, 1 ) ).getImage();
                        }
                    }

                    if(rozgrywka.getGracze().size() >= 5) {
                        if (rozgrywka.getGracze().get( 4 ).getKartyWRece().size() != 0) {
                            gracz4k1 = new ImageIcon( zapiszObrazDlaGraczy( 4, 0 ) ).getImage();
                            gracz4k2 = new ImageIcon( zapiszObrazDlaGraczy( 4, 1 ) ).getImage();
                        }
                    }

                    if(rozgrywka.getGracze().size() >= 6) {
                        if (rozgrywka.getGracze().get( 5 ).getKartyWRece().size() != 0) {
                            gracz5k1 = new ImageIcon( zapiszObrazDlaGraczy( 5, 0 ) ).getImage();
                            gracz5k2 = new ImageIcon( zapiszObrazDlaGraczy( 5, 1 ) ).getImage();
                        }
                    }

                    rozgrywka.sprawdzanieKart();
                    historia.append(rozgrywka.doHistorii);

                    nastepnyGracz.setVisible(false);
                    graj.setVisible(true);

                    repaint();


                    pierwszyObrot = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliFlop = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliTurn = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliRiver = false;
                    czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia = false;
                    koniecGry = true;

                }

            }
            czyGraczeWlozyliTakaSamaIloscZetonowDoPuliOstatnia = false;

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

    private void pokaznieKrotyGraczWykonujeRuch( int i , Graphics g){

        if (ruchGraczaTmp == 0) {
            System.out.println("dupa");
        }

    }

    private void sprawdzenieCzyGraczMaKartyIWszedlAllIn(){
        iloscGraczyNaAllIn = 0;

        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getKartyWRece().size() != 0 && g.getIloscZetonow() == 0) {
                iloscGraczyNaAllIn++;
            }
        }
        if ((iloscGraczyNaAllIn +1 ) == iloscGraczyWGrze) {
            System.out.println("zaden gracz nie powinien zrobić ruchu");
            nastepnyGracz.setVisible(false);
            bet.setVisible(false);
            fold.setVisible(false);
            check.setVisible(false);

            if (!kartyFlop) {

                sprawdzeniePlusEwWylozenieKartNaStolFLOP();
                sprawdzeniePlusEwWylozenieKartNaStolTURN();
                sprawdzeniePlusEwWylozenieKartNaStolRiver();
                sprawdzenieIPokazanieKart();

            } else if (!kartaTurn) {

                sprawdzeniePlusEwWylozenieKartNaStolTURN();
                sprawdzeniePlusEwWylozenieKartNaStolRiver();
                sprawdzenieIPokazanieKart();

            } else if (!kartaRiver) {

                sprawdzeniePlusEwWylozenieKartNaStolRiver();
                sprawdzenieIPokazanieKart();

            }

            if (kartyFlop && kartaTurn && kartaRiver){

                sprawdzenieIPokazanieKart();

            }
        }
        repaint();
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

    private void dodajZnacznikRuchu (Graphics g) {

        if (ruchGraczaTmp == 0) {

                g.drawImage(ruch, 1292, 849, null);

        } else if (ruchGraczaTmp == 1) {

                g.drawImage(ruch, 564, 849, null);

        } else if (ruchGraczaTmp == 2) {

                g.drawImage(ruch, 217, 506, null);

        } else if (ruchGraczaTmp == 3) {

                g.drawImage(ruch, 565, 162, null);


        } else if (ruchGraczaTmp == 4) {

                g.drawImage(ruch, 1291, 162, null);

        }else if (ruchGraczaTmp == 5) {

                g.drawImage(ruch, 1639, 506, null);

        }

    }

    private ImageIcon ktoryGraczRobiRuch (){

            ruch = new ImageIcon( "zdjecia\\znacznikRuchu2.jpg").getImage();
            return new ImageIcon(ruch);

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
        ktoryGraczRobiRuch();


        pierwszyObrot = true;
        pierwszyObrotTurn = true;
        pierwszyObrotRiver = true;
        pierwszyObrotOstatnia = true;
        kartyFlop = false;
        kartaTurn = false;
        kartaRiver = false;
        iloscRuchowNaFlopie = rozgrywka.getGracze().size();

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        rozgrywka.setSprawdzJakiUkladRaz(true);

        rozgrywka.sprawdzamIleGraczyMaDanyUklad(comparatorKartaWartosc);

        koniecGry = false;

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
        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getIloscZetonow() <= 0) {
                rozgrywka.getGracze().remove(g);
                rozgrywka.setLiczbaGraczy(rozgrywka.getLiczbaGraczy() - 1);
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
        graj.setVisible(true);

        koniecGry = true;
//        czyZostalJedenGracz = false;

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

        gracz0k1 = new ImageIcon(zapiszObrazDlaNaszegoGracza(0)).getImage();

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

                                                                                                                                                        gracz0k2 = new ImageIcon(zapiszObrazDlaNaszegoGracza(1)).getImage();
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

        koniecRozdania();

        graj.setVisible(false);

        if (!pierwszaTura) {
            getRozgrywka().rozdajKartyDoReki(rozgrywka.getTaliaKart());
            gracz0k1 = new ImageIcon(zapiszObrazDlaNaszegoGracza(0)).getImage();
            gracz0k2 = new ImageIcon(zapiszObrazDlaNaszegoGracza(1)).getImage();
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