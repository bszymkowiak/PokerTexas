package klasy.menu.panelKoncowy;

import klasy.Rozgrywka;
import klasy.menu.PanelStolik;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PanelKoncowy extends JPanel {

    private PanelKoncowy me;
    private PanelStolik panelStolik;
    private Rozgrywka rozgrywka;

    private JFrame window;

    public PanelKoncowy(PanelStolik panelStolik) {

        this.panelStolik = panelStolik;
        setRozgrywka(panelStolik.getRozgrywka());
        me = this;

        dodajTlo();


    }

    private void dodajTlo() {

        window = new JFrame("Poker Texas");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1920, 1080));
        window.add(new KlasyfikacjaKoncowa(me));

        window.pack();


    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }

    @Override
    protected void paintComponent(Graphics g) {



    }
}

