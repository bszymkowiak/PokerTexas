package klasy.menu.panelKoncowy;

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


    public KlasyfikacjaKoncowa(PanelKoncowy panelKoncowy) {
        super();
        me = this;
        this.panelKoncowy = panelKoncowy;
        setLayout(null);

        dodajTlo();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        g.setFont(f);
        g.setColor(Color.WHITE);

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
