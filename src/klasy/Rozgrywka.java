package klasy;

import klasy.karty.Karta;
import klasy.karty.TaliaKart;

import javax.swing.*;
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
    private boolean czyRozdalemBlind;


    public int getKtoraRunda() {
        return ktoraRunda;
    }

    public int getMalyBlind() {
        return malyBlind;
    }

    public void setMalyBlind(int malyBlind) {
        this.malyBlind = malyBlind;
    }

    public int getDuzyBlind() {
        return duzyBlind;
    }

    public void setDuzyBlind(int duzyBlind) {
        this.duzyBlind = duzyBlind;
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

    public void wyswietlGraczy() {

        for (Gracz g : gracze) {
            System.out.println(g);
        }
    }

    public ArrayList<Gracz> dodajGraczy(int iloscZetonow) {

        for (int i = 0; i < liczbaGraczy; i++) {
            gracze.add(new Gracz());
            gracze.get(i).setNick(losoweImie());
            gracze.get(i).setIloscZetonow(iloscZetonow);
        }
        return gracze;
    }

    public ArrayList<Gracz> rozdajKartyDoReki(TaliaKart taliaKart) {

        taliaKart.tasujKarty();

        for (Gracz g : gracze) {

            for (int i = 0; i < 2; i++) {

                g.kartyWRece.add(getTaliaKart().getTaliaKart().get(i));
                taliaKart.getTaliaKart().remove(i);
            }


        }
        return gracze;

    }

    private String losoweImie() {

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


    public ArrayList<Karta> rozdajFlop() {

        taliaKart.getTaliaKart().remove(0);

        for (int i = 0; i < 3; i++) {
            kartyStol.add(taliaKart.getTaliaKart().get(i));
//
//            for (Gracz g : gracze) {
//                g.getKartyWRece().add(taliaKart.getTaliaKart().get(i));
//            }

            taliaKart.getTaliaKart().remove(i);
        }

        return kartyStol;


    }

    public ArrayList<Karta> rozdajTurn() {

        taliaKart.getTaliaKart().remove(0);
        kartyStol.add(taliaKart.getTaliaKart().get(0));

//        for (Gracz g : gracze) {
//            g.getKartyWRece().add(taliaKart.getTaliaKart().get(0));
//        }

        taliaKart.getTaliaKart().remove(0);

        return kartyStol;

    }

    public ArrayList<Karta> rozdajRiver() {

        taliaKart.getTaliaKart().remove(0);
        kartyStol.add(taliaKart.getTaliaKart().get(0));

//        for (Gracz g : gracze) {
//            g.getKartyWRece().add(taliaKart.getTaliaKart().get(0));
//        }

        taliaKart.getTaliaKart().remove(0);

        return kartyStol;
    }

    public ArrayList<Gracz> usunKartyZReki() {

        for (Gracz g : gracze) {
            g.getKartyWRece().removeAll(g.getKartyWRece());
        }

        return gracze;

    }

    public ArrayList<Karta> usunKartyStol() {

        kartyStol.removeAll(kartyStol);

        return kartyStol;
    }

    public void setTaliaKart(TaliaKart taliaKart) {
        this.taliaKart = taliaKart;
    }

    public int getKtoBlind() {
        return ktoBlind;
    }

    public int getPobierzBlind() {
        return ktoBlind + counter;
    }

    public void setKtoBlind(int ktoBlind) {
        this.ktoBlind = ktoBlind;
    }

    public void rozdajBlind() {

        for (Gracz g : gracze) {

            g.setBlind(0);
            g.setPulaZetonowGracza(0);
        }

        if (!czyRozdalemBlind) {
            ktoBlind = rand.nextInt(gracze.size());
            malyBlind = 10;
            duzyBlind = 20;
            czyRozdalemBlind = true;
        }

        ktoraRunda++;

        if (ktoraRunda % 10 == 0) {
            malyBlind *= 2;
            duzyBlind *= 2;
        }

        gracze.get(ktoBlind + counter).setBlind(malyBlind);
        gracze.get(ktoBlind + counter).setIloscZetonow(gracze.get(ktoBlind + counter).getIloscZetonow() - malyBlind);
        gracze.get(ktoBlind + counter).setPulaZetonowGracza(malyBlind);
        counter++;

        if ((ktoBlind + counter) > (gracze.size() - 1)) {
            counter -= gracze.size();
        }
        gracze.get(ktoBlind + counter).setBlind(duzyBlind);
        gracze.get(ktoBlind + counter).setIloscZetonow(gracze.get(ktoBlind + counter).getIloscZetonow() - duzyBlind);
        gracze.get(ktoBlind + counter).setPulaZetonowGracza(duzyBlind);
    }

    public ArrayList<Karta> komputerFold(int i) {

        gracze.get(i).kartyWRece.removeAll(gracze.get(i).kartyWRece);
        System.out.println(gracze.get(i).getNick() + " wykonuje fold.");

        return gracze.get(i).kartyWRece;
    }

    public void komputerCheck(int i) {

        int wartoscTmp = gracze.get(i).getPulaZetonowGracza();


        System.out.println(gracze.get(i).getNick() + " wykonuje check.");

        for (Gracz g : gracze) {
            if (g.getPulaZetonowGracza() >= wartoscTmp && g.getKartyWRece().size() != 0) {
                wartoscTmp = g.getPulaZetonowGracza();
            }
        }

        if (gracze.get(i).getPulaZetonowGracza() < wartoscTmp && gracze.get(i).getKartyWRece().size() != 0) {
            gracze.get(i).setIloscZetonow(gracze.get(i).getIloscZetonow() - wartoscTmp + gracze.get(i).getPulaZetonowGracza());
            gracze.get(i).setPulaZetonowGracza(wartoscTmp);
        }

    }

    public void komputerBet(int i) {

        System.out.println(gracze.get(i).getNick() + " wykonuje bet.");

        int wartoscTmp = gracze.get(i).getPulaZetonowGracza();

        for (Gracz g : gracze) {
            if (g.getPulaZetonowGracza() >= wartoscTmp) {
                wartoscTmp = g.getPulaZetonowGracza();
            }
        }

        wartoscTmp += 20;

        gracze.get(i).setIloscZetonow(gracze.get(i).getIloscZetonow() - wartoscTmp + gracze.get(i).getPulaZetonowGracza());
        gracze.get(i).setPulaZetonowGracza(wartoscTmp);


    }

    public void ruchGracza(int i) {

        Random rand = new Random();
        int liczba = rand.nextInt(3);


        if (gracze.get(i).kartyWRece.size() != 0) {
            if (liczba == 0) {
                komputerFold(i);
            } else if (liczba == 1) {
                komputerCheck(i);
            } else if (liczba == 2) {
                komputerBet(i);
            }
        }
    }

}





