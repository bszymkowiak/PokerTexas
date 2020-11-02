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

            for(int i = 0; i < 5; i++) {

                g.kartyWRece.remove(i);
            }

        }

        return gracze;

    }

    public void symulacja(){

        Scanner scnr = new Scanner(System.in);
        String decyzja;


//        System.out.println(taliaKart.getTaliaKart().size());
//        dodajGraczy();
//        rozdajKartyDoReki();
//        wyswietlGraczy();
//        System.out.println(taliaKart.getTaliaKart().size());
//        rozdajFlop();
//        rozdajTurn();
//        rozdajRiver();
////        wyswietlGraczy();
//        System.out.println(taliaKart.getTaliaKart().size());





    }




}