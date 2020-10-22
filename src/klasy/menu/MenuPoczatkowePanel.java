package klasy.menu;

import klasy.Gracz;
import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPoczatkowePanel extends JPanel {


    private File imageFile;
    private BufferedImage image;
    private JButton graj;
    private MenuPoczatkowe menuPoczatkowe;
    private int liczbaGraczy;
    private int iloscZetonow;
    JComboBox<Integer> iloscPrzeciwnikowCombo;
    Rozgrywka rozgrywka = new Rozgrywka();
    JComboBox<Integer> iloscZetonowStart;



    public MenuPoczatkowePanel(MenuPoczatkowe menuPoczatkowe) {

        super();
        this.menuPoczatkowe = menuPoczatkowe;
        setLayout(null);
        Border obramowanie = BorderFactory.createEmptyBorder();

        imageFile = new File("zdjecia\\tlo.jpg");

        try {
            image = ImageIO.read(imageFile);
        } catch (
                IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        var sansbold20 = new Font("SansSerif", Font.BOLD, 20);

        JTextField imieGr = new JTextField(20);
        imieGr.setBackground(Color.GRAY);
        imieGr.setBounds(1005, 209, 300, 25);
        imieGr.setFont(sansbold20);
        imieGr.setBorder(obramowanie);
        add(imieGr);

        iloscPrzeciwnikowCombo = new JComboBox<>();
        iloscPrzeciwnikowCombo.addItem(1);
        iloscPrzeciwnikowCombo.addItem(2);
        iloscPrzeciwnikowCombo.addItem(3);
        iloscPrzeciwnikowCombo.addItem(4);
        iloscPrzeciwnikowCombo.addItem(5);
        iloscPrzeciwnikowCombo.addItem(6);
        iloscPrzeciwnikowCombo.addItem(7);;
        iloscPrzeciwnikowCombo.setBounds(1005, 285, 40, 30);
        iloscPrzeciwnikowCombo.setBackground(Color.GRAY);
        iloscPrzeciwnikowCombo.setFont(sansbold20);
        iloscPrzeciwnikowCombo.setBorder(obramowanie);
        add(iloscPrzeciwnikowCombo);

        int b  = (int) iloscPrzeciwnikowCombo.getSelectedItem();

        iloscPrzeciwnikowCombo.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    int b = (Integer)e.getItemSelectable().getSelectedObjects()[0];
                    liczbaGraczy = b;
                }
            }
        } );




        iloscZetonowStart = new JComboBox<>();
        iloscZetonowStart.addItem(500);
        iloscZetonowStart.addItem(1000);
        iloscZetonowStart.addItem(1500);
        iloscZetonowStart.addItem(2000);
        iloscZetonowStart.addItem(3000);
        iloscZetonowStart.addItem(5000);
        iloscZetonowStart.addItem(10000);
        iloscZetonowStart.setBackground(Color.GRAY);
        iloscZetonowStart.setBounds(1005, 360, 100, 30);
        iloscZetonowStart.setFont(sansbold20);
        iloscZetonowStart.setBorder(obramowanie);
        add(iloscZetonowStart);


        int d  = (int) iloscZetonowStart.getSelectedItem();

        iloscZetonowStart.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent f) {
                if(f.getStateChange() == ItemEvent.SELECTED){
                    int d = (Integer)f.getItemSelectable().getSelectedObjects()[0];
                    iloscZetonow = d;
                }
            }
        } );

        var czasCiemnych = new JComboBox<Integer>();
        czasCiemnych.addItem(5);
        czasCiemnych.addItem(8);
        czasCiemnych.addItem(10);
        czasCiemnych.addItem(15);
        czasCiemnych.setBackground(Color.GRAY);
        czasCiemnych.setBounds(1005, 448, 50, 30);
        czasCiemnych.setFont(sansbold20);
        czasCiemnych.setBorder(obramowanie);
        add(czasCiemnych);

        ButtonGroup grupa = new ButtonGroup();

        JRadioButton blue = new JRadioButton();
        blue.setBounds(1028, 630, 20, 20);
        blue.setBackground(Color.GRAY);
        add(blue);

        JRadioButton gray = new JRadioButton();
        gray.setBounds(1098, 630, 20, 20);
        gray.setBackground(Color.GRAY);
        add(gray);

        JRadioButton green = new JRadioButton();
        green.setBounds(1163, 630, 20, 20);
        green.setBackground(Color.GRAY);
        add(green);

        JRadioButton purple = new JRadioButton();
        purple.setBounds(1238, 630, 20, 20);
        purple.setBackground(Color.GRAY);
        add(purple);

        JRadioButton red = new JRadioButton();
        red.setBounds(1308, 630, 20, 20);
        red.setBackground(Color.GRAY);
        add(red);

        JRadioButton yellow = new JRadioButton();
        yellow.setBounds(1378, 630, 20, 20);
        yellow.setBackground(Color.GRAY);
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
                menuPoczatkowe.getWindow().dispose();
                new Stolik();
                Rozgrywka rozgrywka = new Rozgrywka();
                rozgrywka.setLiczbaGraczy(liczbaGraczy);
                System.out.println(liczbaGraczy);
                System.out.println(iloscZetonow);


            }
        });

    }

    Image blue = new ImageIcon("zdjecia\\Mblue.jpg").getImage();
    Image gray = new ImageIcon("zdjecia\\Mgray.jpg").getImage();
    Image green = new ImageIcon("zdjecia\\Mgreen.jpg").getImage();
    Image purple = new ImageIcon("zdjecia\\Mpurple.jpg").getImage();
    Image red = new ImageIcon("zdjecia\\Mred.jpg").getImage();
    Image yellow = new ImageIcon("zdjecia\\Myellow.jpg").getImage();


    @Override
    public void paintComponent(Graphics g) {
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

