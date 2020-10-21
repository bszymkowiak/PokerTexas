package klasy.menu;

import javax.swing.*;
import java.awt.*;

public class MenuPoczatkowe extends JPanel{

    JFrame window;

    private MenuPoczatkowe me;

    public MenuPoczatkowe() {

        me = this;

        window = new JFrame("Poker Texas");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1920, 1080));

        window.add(new BackgroundPanel(me));

        window.pack();


//        var rewersKarty = new Hashtable<Integer, Component>();
//        rewersKarty.put(0,new JLabel(new ImageIcon("src\\poker\\zdjecia\\blue_black.jpg")));
//        slider.setLebelTable(rewersKarty);
//        add(rewersKarty);


    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }







}
