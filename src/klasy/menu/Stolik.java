package klasy.menu;

import javax.swing.*;
import java.awt.*;

class Stolik extends JPanel {

    JFrame window;
    private Stolik we;
    private MenuPoczatkowePanel menuPoczatkowePanel;
    private int liczbaGraczy;
    private int iloscZetonow;
    private String imieGracza;


    public Stolik(MenuPoczatkowePanel menuPoczatkowePanel) {

        this.menuPoczatkowePanel = menuPoczatkowePanel;
        we = this;
        dodajTlo(menuPoczatkowePanel);


    }

    private void dodajTlo(MenuPoczatkowePanel menuPoczatkowePanel) {
        liczbaGraczy = menuPoczatkowePanel.getLiczbaGraczy();
        iloscZetonow = menuPoczatkowePanel.getIloscZetonow();
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

    public int getLiczbaGraczy() {
        return liczbaGraczy;
    }

    public void setLiczbaGraczy(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
    }

    public int getIloscZetonow() {
        return iloscZetonow;
    }

    public void setIloscZetonow(int iloscZetonow) {
        this.iloscZetonow = iloscZetonow;
    }
}