package klasy.menu;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.Gracz;
import klasy.Rozgrywka;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuPoczatkowe extends JPanel{

    JFrame window;

    private MenuPoczatkowe me;

    private Rozgrywka rozgrywka = new Rozgrywka();

    public MenuPoczatkowe() throws SQLException, ClassNotFoundException {

        me = this;
        dodajRamke();

//        var rewersKarty = new Hashtable<Integer, Component>();
//        rewersKarty.put(0,new JLabel(new ImageIcon("src\\poker\\zdjecia\\blue_black.jpg")));
//        slider.setLebelTable(rewersKarty);
//        add(rewersKarty);


    }


    private void dodajRamke() {
        window = new JFrame("Poker Texas");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1920, 1080));

        window.add(new MenuPoczatkowePanel(me));

        window.pack();
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }


}
