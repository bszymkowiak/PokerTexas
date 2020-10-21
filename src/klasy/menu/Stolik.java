package klasy.menu;

import javax.swing.*;
import java.awt.*;

class Stolik extends JPanel {

    JFrame window;
    private Stolik we;

    public Stolik() {

        we = this;

        window = new JFrame("Poker Texas");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1920, 1080));
        window.add(new PanelStolik(we));

        window.pack();


    }
}