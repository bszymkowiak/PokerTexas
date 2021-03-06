package klasy.menu;

import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

public class MenuPoczatkowePanel extends JPanel {

    private File imageFile;
    private BufferedImage image;
    private JButton graj;
    private MenuPoczatkowe menuPoczatkowe;
    private JTextField imieGr;

    ButtonGroup grupa = new ButtonGroup();

    private String imieGracza;

    private Rozgrywka rozgrywka;
    JComboBox<Object> iloscPrzeciwnikowCombo;
    JComboBox<Object> iloscZetonowStart;
    JComboBox<Object> czasCiemnych;
    private MenuPoczatkowePanel me;
    String kolorRewers = "";


    public MenuPoczatkowePanel(MenuPoczatkowe menuPoczatkowe) {

        super();
        me = this;
        this.menuPoczatkowe = menuPoczatkowe;
        setLayout(null);
        Border obramowanie = BorderFactory.createEmptyBorder();
        var sansbold20 = new Font("SansSerif", Font.BOLD, 20);
        setRozgrywka(menuPoczatkowe.getRozgrywka());

        dodajTlo();
        dodajPoleImie(obramowanie, sansbold20);
        dodajListeIloscPrzeciwnikow(obramowanie, sansbold20);
        dodajListeIloscZetonowNaStart(obramowanie, sansbold20);
        dodajListeCzasuCiemnych(obramowanie, sansbold20);
        dodajPrzyciski(menuPoczatkowe, obramowanie);

    }

    public MenuPoczatkowePanel() {

    }

    private void dodajTlo() {
        imageFile = new File("zdjecia\\tlo.jpg");

        try {
            image = ImageIO.read(imageFile);
        } catch (
                IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }


    }

    private void dodajPoleImie(Border obramowanie, Font sansbold20) {

        imieGr = new JTextField("imię:", 20);
        imieGr.setBackground(Color.LIGHT_GRAY);
        imieGr.setBounds(1005, 209, 300, 25);
        imieGr.setFont(sansbold20);
        imieGr.setBorder(obramowanie);
        add(imieGr);
        imieGr.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == 1) {
                    imieGr.setText("");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void dodajListeIloscPrzeciwnikow(Border obramowanie, Font sansbold20) {


        iloscPrzeciwnikowCombo = new JComboBox<>();
        iloscPrzeciwnikowCombo.addItem("ilość graczy:");
        iloscPrzeciwnikowCombo.addItem(2);
        iloscPrzeciwnikowCombo.addItem(3);
        iloscPrzeciwnikowCombo.addItem(4);
        iloscPrzeciwnikowCombo.addItem(5);
        iloscPrzeciwnikowCombo.addItem(6);

        iloscPrzeciwnikowCombo.setBounds(1005, 285, 150, 30);
        iloscPrzeciwnikowCombo.setBackground(Color.GRAY);
        iloscPrzeciwnikowCombo.setFont(sansbold20);
        iloscPrzeciwnikowCombo.setBorder(obramowanie);
        add(iloscPrzeciwnikowCombo);

        iloscPrzeciwnikowCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int b = (Integer) e.getItemSelectable().getSelectedObjects()[0];
                    rozgrywka.setLiczbaGraczy(b);
                }
            }
        });
    }

    private void dodajListeIloscZetonowNaStart(Border obramowanie, Font sansbold20) {
        iloscZetonowStart = new JComboBox<>();
        iloscZetonowStart.addItem("ilość żetonów:");
        iloscZetonowStart.addItem(500);
        iloscZetonowStart.addItem(1000);
        iloscZetonowStart.addItem(1500);
        iloscZetonowStart.addItem(2000);
        iloscZetonowStart.addItem(3000);
        iloscZetonowStart.addItem(5000);
        iloscZetonowStart.addItem(10000);
        iloscZetonowStart.setBackground(Color.GRAY);
        iloscZetonowStart.setBounds(1005, 360, 165, 30);
        iloscZetonowStart.setFont(sansbold20);
        iloscZetonowStart.setBorder(obramowanie);
        add(iloscZetonowStart);

        iloscZetonowStart.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent f) {
                if (f.getStateChange() == ItemEvent.SELECTED) {
                    int d = (Integer) f.getItemSelectable().getSelectedObjects()[0];
                    rozgrywka.setIloscZetonow(d);
                }
            }
        });
    }

    private void dodajListeCzasuCiemnych(Border obramowanie, Font sansbold20) {
        czasCiemnych = new JComboBox<>();
        czasCiemnych.addItem("Wybierz czas:");
        czasCiemnych.addItem(5);
        czasCiemnych.addItem(8);
        czasCiemnych.addItem(10);
        czasCiemnych.addItem(15);
        czasCiemnych.setBackground(Color.GRAY);
        czasCiemnych.setBounds(1005, 448, 165, 30);
        czasCiemnych.setFont(sansbold20);
        czasCiemnych.setBorder(obramowanie);
        add(czasCiemnych);

        czasCiemnych.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int c = (Integer) e.getItemSelectable().getSelectedObjects()[0];
                    rozgrywka.setCzasCiemnych(c);
                }
            }
        });
    }

    private void dodajPrzyciski(MenuPoczatkowe menuPoczatkowe, Border obramowanie) {

        JRadioButton blue = new JRadioButton("", true);
        blue.setBounds(1028, 630, 20, 20);
        blue.setBackground(Color.GRAY);
        blue.setActionCommand("zdjecia\\\\Mblue.jpg");
        add(blue);

        JRadioButton gray = new JRadioButton("", false);
        gray.setBounds(1098, 630, 20, 20);
        gray.setBackground(Color.GRAY);
        gray.setActionCommand("zdjecia\\\\Mgray.jpg");
        add(gray);

        JRadioButton green = new JRadioButton("", false);
        green.setBounds(1163, 630, 20, 20);
        green.setBackground(Color.GRAY);
        green.setActionCommand("zdjecia\\\\Mgreen.jpg");
        add(green);

        JRadioButton purple = new JRadioButton("", false);
        purple.setBounds(1238, 630, 20, 20);
        purple.setBackground(Color.GRAY);
        purple.setActionCommand("zdjecia\\\\Mpurple.jpg");
        add(purple);


        JRadioButton red = new JRadioButton("", false);
        red.setBounds(1308, 630, 20, 20);
        red.setBackground(Color.GRAY);
        red.setActionCommand("zdjecia\\\\Mred.jpg");
        add(red);

        JRadioButton yellow = new JRadioButton("", false);
        yellow.setBounds(1378, 630, 20, 20);
        yellow.setBackground(Color.GRAY);
        yellow.setActionCommand("zdjecia\\\\Myellow.jpg");
        add(yellow);

        grupa.add(blue);
        grupa.add(gray);
        grupa.add(green);
        grupa.add(purple);
        grupa.add(red);
        grupa.add(yellow);

        graj = new JButton(new ImageIcon("zdjecia\\graj2.jpg"));
        graj.setBounds(877, 686, 243, 83);
        graj.setBorder(obramowanie);
        add(graj);

        graj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Objects.equals(iloscPrzeciwnikowCombo.getSelectedItem(), "ilość graczy:")) {

                    JOptionPane.showMessageDialog(null, "Podaj ilość przeciwników!", "Błąd przeciwników", JOptionPane.INFORMATION_MESSAGE);

                } else if (Objects.equals(iloscZetonowStart.getSelectedItem(), "ilość żetonów:")) {

                    JOptionPane.showMessageDialog(null, "Podaj ilość żetonów!", "Błąd ilości żetonów.", JOptionPane.INFORMATION_MESSAGE);

                } else if (Objects.equals(czasCiemnych.getSelectedItem(), "wybierz czas:")) {

                    JOptionPane.showMessageDialog(null, "Podaj po jakim czasię mają się zwiększać BigBlind i SmallBlind!", "Błąd czasu ciemnych", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    menuPoczatkowe.getWindow().dispose();
                    imieGracza = imieGr.getText();
                    new Stolik(me);
                }

            }
        });
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }

    public String getImieGracza() {
        return imieGracza;
    }

    @Override
    public void paintComponent(Graphics g) {

        Image blue = new ImageIcon("zdjecia\\Mblue.jpg").getImage();
        Image gray = new ImageIcon("zdjecia\\Mgray.jpg").getImage();
        Image green = new ImageIcon("zdjecia\\Mgreen.jpg").getImage();
        Image purple = new ImageIcon("zdjecia\\Mpurple.jpg").getImage();
        Image red = new ImageIcon("zdjecia\\Mred.jpg").getImage();
        Image yellow = new ImageIcon("zdjecia\\Myellow.jpg").getImage();

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
        g.drawImage(blue, 1005, 525, null);
        g.drawImage(gray, 1075, 525, null);
        g.drawImage(green, 1145, 525, null);
        g.drawImage(purple, 1215, 525, null);
        g.drawImage(red, 1285, 525, null);
        g.drawImage(yellow, 1355, 525, null);
    }


}

