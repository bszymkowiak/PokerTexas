package klasy;

import klasy.karty.TaliaKart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Rozgrywka extends Gracz {

    private TaliaKart taliaKart = new TaliaKart();
    private ArrayList<Gracz> gracze = new ArrayList<>();
    private int liczbaGraczy;

    private ArrayList<String> imionaGraczy = new ArrayList<>();

    public TaliaKart getTaliaKart() {
        return taliaKart;
    }

    public void setTaliaKart(TaliaKart taliaKart) {
        this.taliaKart = taliaKart;
    }

    public ArrayList<String> getImionaGraczy() {
        return imionaGraczy;
    }

    public void setImionaGraczy(ArrayList<String> imionaGraczy) {
        this.imionaGraczy = imionaGraczy;
    }

    public ArrayList<Gracz> getGracze() {
        return gracze;
    }

    public void setGracze(ArrayList<Gracz> gracze) {
        this.gracze = gracze;
    }

    public int getLiczbaGraczy() {
        return liczbaGraczy;
    }

    public void setLiczbaGraczy(int liczbaGraczy) {
        this.liczbaGraczy = liczbaGraczy;
    }

    public void wyswietlGraczy(){

        for (Gracz g : gracze) {
            System.out.println(g);
        }
    }

    public ArrayList<Gracz> dodajGraczy(){

        for (int i = 0; i < liczbaGraczy; i++) {
            gracze.add(new Gracz());
            gracze.get(i).setNick(losoweImie());
            gracze.get(i).setIloscZetonow(1500);
        }
        return gracze;
    }

    public ArrayList<Gracz> rozdajKartyDoReki(){

        taliaKart.tasujKarty();

        for(Gracz g : gracze){
            g.setKarta1(taliaKart.getTaliaKart().get(0));
            taliaKart.getTaliaKart().remove(0);
            g.setKarta2(taliaKart.getTaliaKart().get(0));
            taliaKart.getTaliaKart().remove(0);
        }
        return gracze;
    }

    public String losoweImie(){

        String losoweImie = "";


        try {
            Scanner scnr = new Scanner(new File("imiona.txt"));

            String line;


            while (scnr.hasNextLine()) {
                line = scnr.nextLine();
                imionaGraczy.add(line);
            }


            Random rand = new Random();
            int imieGraczaIndex = rand.nextInt(imionaGraczy.size());

            losoweImie = imionaGraczy.get(imieGraczaIndex);

            scnr.close();


        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Brak pliku.");
        }

        return losoweImie;

    }
}
