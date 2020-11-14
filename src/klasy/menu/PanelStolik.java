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
import java.io.File;
import java.io.IOException;

public class PanelStolik extends JPanel implements ActionListener {
    private File imageFile;
    private BufferedImage image;
    private Stolik stolik;
    private JButton fold;
    private JButton check;
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

    Border obramowanie = BorderFactory.createEmptyBorder();

    private Rozgrywka rozgrywka;

    int temp = 0;
    int betB;
    private JTextField wpisPuli;
    int iloscMoichZetonow;
    JTextField mojeZetony;
    int pula;

    public PanelStolik(Stolik stolik) {


        super();

        me = this;
        this.stolik = stolik;
        setLayout(null);


        setRozgrywka(stolik.getRozgrywka());

        rozgrywka.setLiczbaGraczy(stolik.getRozgrywka().getLiczbaGraczy());

        getRozgrywka().dodajGraczy(stolik.getRozgrywka().getIloscZetonow());


        getRozgrywka().getGracze().get(0).setNick(stolik.getImieGracza());

        dodajTlo();
        dodajPrzyciski(obramowanie);


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
        var mess2 = "Ciemne: " + rozgrywka.getMalyBlind() + "$ / " + rozgrywka.getDuzyBlind() + "$ ";
        g.drawString(mess2, 875, 325);

        dodajKartyStolFlop(g);
        dodajKartyStolTurn(g);
        dodajKartyStolRiver(g);

        g.setColor(Color.WHITE);

        dodaniePolGraczyZeWzgleduNaIchIlosc(g);
        dodajZetonSmallBlind(g);
        dodajZetonBibBlind(g);
        dodajZetonDealer(g);




    }

    private void dodajPrzyciski(Border obramowanie) {

        wpisPuli = new JTextField();
        wpisPuli.setBounds(1730, 890, 134, 30);
        wpisPuli.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(wpisPuli);

        fold = new JButton(new ImageIcon("zdjecia\\fold.jpg"));
        fold.setBounds(1422, 930, 134, 80);
        fold.setBackground(Color.GRAY);
        fold.setFont(new Font("SansSerif", Font.BOLD, 25));
        fold.setBorder(obramowanie);
        add(fold);
        fold.setVisible(true);
        przyciskFoldAkcja();

        check = new JButton(new ImageIcon("zdjecia\\check.jpg"));
        check.setBounds(1576, 930, 134, 80);
        check.setBackground(Color.GRAY);
        check.setFont(new Font("SansSerif", Font.BOLD, 25));
        check.setBorder(obramowanie);
        add(check);
        check.setVisible(true);
        przyciskCheckAkcja();

        bet = new JButton(new ImageIcon("zdjecia\\bet.jpg"));
        bet.setBounds(1730, 930, 134, 80);
        bet.setBackground(Color.GRAY);
        bet.setFont(new Font("SansSerif", Font.BOLD, 25));
        bet.setBorder(obramowanie);
        add(bet);
        bet.setVisible(true);
        przyciskBetAkcja();

        graj = new JButton(new ImageIcon("zdjecia\\graj2.jpg"));
        graj.setBounds(877, 686, 243, 83);
        graj.setBorder(obramowanie);
        add(graj);
        graj.addActionListener(this);

        mojeZetony = new JTextField(iloscMoichZetonow);
//        mojeZetony.setBounds(1576, 890, 134, 30);
//        mojeZetony.setFont( new Font ("SansSerif", Font.BOLD, 18) );
        add(mojeZetony);

        JButton lobby = new JButton("LOBBY");
        lobby.setBackground(Color.GRAY);
        lobby.setBounds(20, 20, 100, 40);
        lobby.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(lobby);

    }

    private void przyciskBetAkcja() {
        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                betB = Integer.parseInt(wpisPuli.getText());
//                mojeZetony.setText( String.valueOf(rozgrywka.getGracze().get( 0 ).getIloscZetonow()- betB ));
                rozgrywka.getGracze().get(0).setIloscZetonow(rozgrywka.getGracze().get(0).getIloscZetonow() - betB);
                pula += betB;
                repaint();
            }
        });
    }

    private void przyciskCheckAkcja() {
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                if (kartaF1 == null) {
//
//                    checkPrzedFlop();
//
//                } else if (kartaT == null) {
//                    checkPrzedTurn();
//                } else if (kartaR == null) {
//                    checkPrzedRiver();
//                }

                if (kartaF1 == null) {

                    boolean licytacjaWGrze = true;

                    while (licytacjaWGrze) {

                        int wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonow();

                        for (Gracz g : rozgrywka.getGracze()) {
                            if (g.getPulaZetonow() >= wartoscTmp) {
                                wartoscTmp = g.getPulaZetonow();
                            }
                        }

                        rozgrywka.getGracze().get(0).setIloscZetonow(rozgrywka.getGracze().get(0).getIloscZetonow() - wartoscTmp + rozgrywka.getGracze().get(0).getPulaZetonow());


                        for (int i = 1; i <= rozgrywka.getPobierzBlind(); i++) {
                            System.out.println(rozgrywka.getGracze().get(i).getNick() + " ");
                            rozgrywka.ruchGracza(i);
                            repaint();
                        }

                        for (int i = rozgrywka.getPobierzBlind()+1 ; i < rozgrywka.getGracze().size(); i++) {
                            if (rozgrywka.getGracze().get(i).getPulaZetonow() >= wartoscTmp) {
                                wartoscTmp = rozgrywka.getGracze().get(i).getPulaZetonow();
                            }
                        }

                        for (int i = 0; i <= rozgrywka.getPobierzBlind() - 1; i++) {
                            if (rozgrywka.getGracze().get(i).getPulaZetonow() >= wartoscTmp) {
                                wartoscTmp = rozgrywka.getGracze().get(i).getPulaZetonow();
                            }
                        }

                        if(rozgrywka.getGracze().get(rozgrywka.getPobierzBlind()).getPulaZetonow() == wartoscTmp){

                            kartaF1 = new ImageIcon(zapiszObrazDlaKartStol(0)).getImage();
                            kartaF2 = new ImageIcon(zapiszObrazDlaKartStol(1)).getImage();
                            kartaF3 = new ImageIcon(zapiszObrazDlaKartStol(2)).getImage();

                            licytacjaWGrze = false;

                        } else {

                            for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++){
                                rozgrywka.ruchGracza(i);
                            }

                            licytacjaWGrze = false;
                        }

                        repaint();

                    };


                }


            }
        });
    }

    private void checkPrzedFlop() {

        System.out.println("CHECK PRZED FLOP");

        int wartoscTmp = rozgrywka.getGracze().get(0).getPulaZetonow();

        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getPulaZetonow() >= wartoscTmp) {
                wartoscTmp = g.getPulaZetonow();
            }
        }


        for (Gracz g : rozgrywka.getGracze()) {
            if (g.getPulaZetonow() <= wartoscTmp) {
                g.setPulaZetonow(wartoscTmp);
                g.setIloscZetonow(g.getIloscZetonow() - wartoscTmp + g.getBlind());
            }
            System.out.println(g.getNick() + "       " + g.getPulaZetonow());
        }

        kartaF1 = new ImageIcon(zapiszObrazDlaKartStol(0)).getImage();
        kartaF2 = new ImageIcon(zapiszObrazDlaKartStol(1)).getImage();
        kartaF3 = new ImageIcon(zapiszObrazDlaKartStol(2)).getImage();

        repaint();

    }

    private void checkPrzedTurn() {

        kartaT = new ImageIcon(zapiszObrazDlaKartStol(3)).getImage();
        repaint();

    }

    private void checkPrzedRiver() {

        kartaR = new ImageIcon(zapiszObrazDlaKartStol(4)).getImage();
        repaint();
    }

    private void przyciskFoldAkcja() {
        fold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Gracz g : rozgrywka.getGracze()) {
                    g.setPulaZetonow(0);
                    g.setBlind(0);
                }

                rozgrywka.setTaliaKart(new TaliaKart());

                rozgrywka.usunKartyZReki();
                rozgrywka.usunKartyStol();

                kartaF1 = null;
                kartaF2 = null;
                kartaF3 = null;
                kartaT = null;
                kartaR = null;

                rozgrywka.rozdajKartyDoReki(rozgrywka.getTaliaKart());

                gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();
                gracz0k2 = new ImageIcon(zapiszObrazDlaGraczy(1)).getImage();

                rozgrywka.rozdajBlind();
                dodajZdjecieSmallBlind();

                for (Gracz g : rozgrywka.getGracze()) {
                    if (g.getIloscZetonow() < 0) {
                        rozgrywka.getGracze().remove(g);
                        rozgrywka.setLiczbaGraczy(rozgrywka.getLiczbaGraczy() - 1);
                    }
                }

                rozgrywka.rozdajFlop();
                rozgrywka.rozdajTurn();
                rozgrywka.rozdajRiver();

                repaint();

            }

        });

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

    private Image zwrocZdjecieKartyOKolorzeIWartosci(Wartosc numerKarty, Kolor kolor) {
        return new ImageIcon("zdjecia\\karty\\" + kolor.toString().toLowerCase() + "\\" + numerKarty.getWartosc() + ".jpg").getImage();
    }

    private void dodajZetonSmallBlind(Graphics g) {
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

    private ImageIcon dodajZdjecieDealer() {
        dealer = new ImageIcon("zdjecia\\dealer.jpg").getImage();
        return new ImageIcon(dealer);
    }

    private void dodajZdjeciaKartGraczy() {

//            int delay = 2000;//specify the delay for the timer
//
//            Timer timer = new Timer( delay, e -> {
//                gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();
//            } );
//            timer.setRepeats( false );//make sure the timer only runs once
//            timer.start();
//            repaint();
//
//            Timer timer2 = new Timer( delay, e -> {
//                gracz1k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
//            } );
//            timer2.setRepeats( false );//make sure the timer only runs once
//            timer2.start();
//            repaint();

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
            gracz0k1 = new ImageIcon(zapiszObrazDlaGraczy(0)).getImage();
            gracz1k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz2k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz3k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz4k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz5k1 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz0k2 = new ImageIcon(zapiszObrazDlaGraczy(1)).getImage();
            gracz1k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz2k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz3k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz4k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();
            gracz5k2 = new ImageIcon("zdjecia\\Green_back.jpg").getImage();

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

        getRozgrywka().rozdajKartyDoReki(rozgrywka.getTaliaKart());

        dodajZdjeciaKartGraczy();

        getRozgrywka().rozdajFlop();
        getRozgrywka().rozdajTurn();
        getRozgrywka().rozdajRiver();

        rozgrywka.rozdajBlind();
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

        System.out.println(rozgrywka.getPobierzBlind());

        System.out.println("KTO BLIND " + rozgrywka.getPobierzBlind());

        for (int i = rozgrywka.getPobierzBlind() + 1; i < rozgrywka.getGracze().size(); i++){
            rozgrywka.ruchGracza(i);
        }

        repaint();


    }
}

