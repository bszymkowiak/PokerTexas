package klasy.menu.panelKoncowy;

import klasy.Gracz;
import klasy.Rozgrywka;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KlasyfikacjaKoncowa extends JPanel {

    private KlasyfikacjaKoncowa me;
    private PanelKoncowy panelKoncowy;

    private File imageFile;
    private BufferedImage image;
    private Rozgrywka rozgrywka;


    public KlasyfikacjaKoncowa(PanelKoncowy panelKoncowy) {
        super();
        me = this;
        setRozgrywka(panelKoncowy.getRozgrywka());
        this.panelKoncowy = panelKoncowy;
        setLayout(null);

        dodajTlo();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 45);
        g.setFont(f);
        g.setColor(Color.WHITE);
        var mess1 = rozgrywka.getGracze().get(0).getNick();

        if (rozgrywka.getGracze().size() == 1) {
            g.drawString(mess1, 860, 547);
        }



    }

    public Rozgrywka getRozgrywka() {
        return rozgrywka;
    }

    public void setRozgrywka(Rozgrywka rozgrywka) {
        this.rozgrywka = rozgrywka;
    }

    private void dodajTlo() {

        imageFile = new File("zdjecia\\koniecGry.jpg");

        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }



}
