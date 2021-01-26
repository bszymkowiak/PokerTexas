package klasy.menu.panelKoncowy;

import klasy.menu.PanelStolik;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PanelKoncowy extends JPanel {

    private PanelKoncowy me;
    private PanelStolik panelStolik;

    private JFrame window;

    public PanelKoncowy(PanelStolik panelStolik) {

        this.panelStolik = panelStolik;
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


}

