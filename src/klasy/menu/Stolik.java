package klasy.menu;

import klasy.Rozgrywka;

import javax.swing.*;
import java.awt.*;

class Stolik extends JPanel {

    JFrame window;
    private Stolik we;
    private MenuPoczatkowePanel menuPoczatkowePanel;

    private Rozgrywka rozgrywka;
    private String imieGracza;


    public Stolik(MenuPoczatkowePanel menuPoczatkowePanel) {

        this.menuPoczatkowePanel = menuPoczatkowePanel;
        we = this;
        dodajTlo(menuPoczatkowePanel);


    }

    private void dodajTlo(MenuPoczatkowePanel menuPoczatkowePanel) {
        setRozgrywka(menuPoczatkowePanel.getRozgrywka());
        imieGracza = menuPoczatkowePanel.getImieGracza();


        window = new JFrame("Poker Texas");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1920, 1080));
        window.add(new PanelStolik(we));

        window.pack();
    }

    public String getImieGracza() {
        return imieGracza;
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }
}