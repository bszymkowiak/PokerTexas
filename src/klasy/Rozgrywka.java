package klasy;

import klasy.karty.Karta;
import klasy.karty.TaliaKart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Rozgrywka extends Gracz {

    private TaliaKart taliaKart = new TaliaKart();
    private ArrayList<Gracz> gracze = new ArrayList<>();
    private ArrayList<Karta> kartyStol = new ArrayList<>();
    private int liczbaGraczy;
    private int ktoBlind;
    private Random rand = new Random();
    private int ktoraRunda = 0;
    int j = 0;
    private int malyBlind;
    private int duzyBlind;
    private int counter = 0;

    public int getKtoraRunda() {
        return ktoraRunda;
    }

    public void setKtoraRunda(int ktoraRunda) {
        this.ktoraRunda = ktoraRunda;
    }

    private ArrayList<String> imionaGraczy = new ArrayList<>();

    public TaliaKart getTaliaKart() {
        return taliaKart;
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

    public ArrayList<Karta> getKartyStol() {
        return kartyStol;
    }

    public void setKartyStol(ArrayList<Karta> kartyStol) {
        this.kartyStol = kartyStol;
    }

    public void wyswietlGraczy(){

        for (Gracz g : gracze) {
            System.out.println(g);
        }
    }

    public ArrayList<Gracz> dodajGraczy(int iloscZetonow){

        for (int i = 0; i < liczbaGraczy; i++) {
            gracze.add(new Gracz());
            gracze.get(i).setNick(losoweImie());
            gracze.get(i).setIloscZetonow(iloscZetonow);
        }
        return gracze;
    }

    public ArrayList<Gracz> rozdajKartyDoReki(TaliaKart taliaKart){

        taliaKart.tasujKarty();

        for(Gracz g : gracze){

            for(int i = 0; i < 2; i++) {

                g.kartyWRece.add(getTaliaKart().getTaliaKart().get(i));
                taliaKart.getTaliaKart().remove(i);
            }


        }
        return gracze;

    }

    private String losoweImie(){

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

    public ArrayList<Karta> rozdajFlop(TaliaKart taliaKart){

        System.out.println("FLOP");

        taliaKart.getTaliaKart().remove(0);

        for (int i = 0; i < 3; i++) {
            kartyStol.add(taliaKart.getTaliaKart().get(i));

            for (Gracz g : gracze) {
                g.getKartyWRece().add(taliaKart.getTaliaKart().get(i));
            }

            taliaKart.getTaliaKart().remove(i);
        }

        return kartyStol;


    }

    public ArrayList<Karta> rozdajTurn(){

        System.out.println("TURN");

        taliaKart.getTaliaKart().remove(0);
        kartyStol.add(taliaKart.getTaliaKart().get(0));

        for (Gracz g : gracze) {
            g.getKartyWRece().add(taliaKart.getTaliaKart().get(0));
        }

        taliaKart.getTaliaKart().remove(0);

        return kartyStol;

    }

    public ArrayList<Karta> rozdajRiver(){

        System.out.println("RIVER");

        taliaKart.getTaliaKart().remove(0);
        kartyStol.add(taliaKart.getTaliaKart().get(0));

        for (Gracz g : gracze) {
            g.getKartyWRece().add(taliaKart.getTaliaKart().get(0));
        }

        taliaKart.getTaliaKart().remove(0);

        return kartyStol;
    }

    public ArrayList<Gracz> usunKartyZReki(){

        for(Gracz g : gracze){
            g.getKartyWRece().removeAll(g.getKartyWRece());
            }

        return gracze;

    }

    public ArrayList<Karta> usunKartyStol(){

        kartyStol.removeAll(kartyStol);

        return kartyStol;
    }

    public void setTaliaKart(TaliaKart taliaKart) {
        this.taliaKart = taliaKart;
    }

    public void rozdajBlind(){

        for (Gracz g : gracze) {

            g.blind = 0;
        }

        if (ktoraRunda < 15) {
            malyBlind = 10;
            duzyBlind = 20;
        } else if (ktoraRunda > 15 && ktoraRunda < 30) {
            malyBlind = 20;
            duzyBlind = 40;
        } else if (ktoraRunda > 30 && ktoraRunda < 45) {
            malyBlind = 40;
            duzyBlind = 80;
        } else if (ktoraRunda > 45 && ktoraRunda < 60) {
            malyBlind = 80;
            duzyBlind = 160;
        }

        if (j == 0) {
            ktoBlind = rand.nextInt(6);
            j++;
        }

        gracze.get(ktoBlind+counter).blind = malyBlind;
        counter++;

        if ((ktoBlind +counter) > 5) {
            counter -= 6;
        }
        gracze.get(ktoBlind+counter).blind = duzyBlind;

    }
}