package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import jdk.swing.interop.SwingInterOpUtils;
import klasy.karty.Karta;
import klasy.karty.TaliaKart;
import klasy.menu.PanelStolik;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private int royalFlushCounter;
    private int straightFlushCounter;
    private int fourOfAKindCounter;
    private int fullHouseCounter;
    private int flushCounter;
    private int straightCounter;
    private int threeOfAKindCounter;
    private int twoPairCounter;
    private int onePairCounter;
    private int highCardCounter;

    public int pulaGlowna;
    int maxWartosc = 0;

    private String lineBaza;

    private Rozgrywka me;

    public Rozgrywka() {

        me = this;

    }

    public String getLineBaza() {
        return lineBaza;
    }

    public void setLineBaza(String lineBaza) {
        this.lineBaza = lineBaza;
    }

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
            ktoBlind = 5;//rand.nextInt(gracze.size());
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

    public void ruchGracza(int i) throws InterruptedException, SQLException, ClassNotFoundException {

        Random rand = new Random();
        int liczba = rand.nextInt(3);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        if (gracze.get(i).kartyWRece.size() != 0) {
            if (liczba == 0) {

                komputerFold(i);
                lineBaza = ("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + gracze.get(i).getNick() + " wykonał/a fold.");
            } else if (liczba == 1) {
                komputerCheck(i);
                lineBaza = ("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + gracze.get(i).getNick() + " wykonał/a check.");
            } else if (liczba == 2) {
                komputerBet(i);
                lineBaza = ("[" + LocalDateTime.now().format(dateTimeFormatter) + "] " + gracze.get(i).getNick() + " wykonał/a bet.");
            }

            new BazaDanych(me);

        }
    }

    private void wynikSprawdzeniaDlaFourOfAKind() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 1) {

            //przypadek gry mamy jednego gracza z kareta

            gdyFourOfAKindMaJedenGracz();
        } else if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter > 1) {

            //przypadek dla więcej gracz z Karetą.
            ArrayList<Gracz> playersWithFourOfAKind = new ArrayList<>();

            gdyFourOfAKindMaWiecejNizJedenGracz_PlusWszystkiePrzypadki(playersWithFourOfAKind);

        }
    }

    private void wynikSprawdzeniaDlaStraight() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter == 1) {
            for (Gracz g : gracze) {
                if (g.isCzyStraight()) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("BRAWO, wygrywasz bo masz Straighta: " + g.getNick());
                }
            }
        } else if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter > 1) {

            ArrayList<Gracz> playersWithStraight = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyStraight() && g.kartyWRece.size() != 0) {
                    playersWithStraight.add(g);
                }
            }
            maxWartosc = playersWithStraight.get(0).getWartoscKartGracza();

            for (int i = 0; i < playersWithStraight.size(); i++) {

                if (playersWithStraight.get(i).getWartoscKartGracza() >= maxWartosc) {
                    maxWartosc = playersWithStraight.get(i).getWartoscKartGracza();
                }
            }

            int temp = 0;

            for (Gracz g : gracze) {
                if (g.getWartoscKartGracza() == maxWartosc && g.isCzyStraight()) {
                    temp++;
                }
            }

            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc && g.isCzyStraight()) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("BRAWO, masz Straighta i wygrywasz : " + g.getNick());
                    }
                }
            }
            if (temp > 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc && g.isCzyStraight()) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                        System.out.println("BRAWO, masz Straighta i wygrywasz razem z innymi : " + g.getNick());
                    }
                }
            }


        }
    }

    private void wynikSprawdzeniaDlaFlush() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 1) {
            for (Gracz g : gracze) {
                if (g.isCzyFlush()) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("Brawo, masz jako jedyny flusha : " + g.getNick());
                }
            }
        } else if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter > 1) {

            ArrayList<Gracz> playersWithFlush = new ArrayList<>();

            int temp = sprawdzeniePierwszejKartyFlush(playersWithFlush);

            if (tylkoJedenGraczMaNajwyzszaKarte(temp)) {

                najwyzszaPierwszaKartaFlush();

            } else if (temp > 1) {

                temp = sprawdzenieDrugiejKartyFlush(playersWithFlush);

                if (tylkoJedenGraczMaNajwyzszaKarte(temp)) {

                    najwyzszaDrugaKartaFlush();

                } else if (temp > 1) {

                    temp = sprawdzenieNajwyzszejTrzeciejKartyFlush(playersWithFlush);

                    if (tylkoJedenGraczMaNajwyzszaKarte(temp)) {

                        najwyzszaTrzeciaKartaFlush();

                    } else if (temp > 1) {

                        temp = sprawdzenieCzwartejKartyFlush(playersWithFlush);

                        if (tylkoJedenGraczMaNajwyzszaKarte(temp)) {

                            najwyzszaCzwartaKartaFlush();

                        } else if (temp > 1) {

                            temp = sprawdzeniePiatejKartyFlush(playersWithFlush);

                            if (tylkoJedenGraczMaNajwyzszaKarte(temp)) {

                                najwyzszaPiataKartaFlush();

                            } else if (temp > 1) {

                                piataKartaFlushWiecejGraczy(temp);
                            }
                        }
                    }
                }
            }
        }
    }

    private void piataKartaFlushWiecejGraczy(int temp) {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                System.out.println("Brawo, masz flusha. \n Razem z : " + g.getNick()); // dla 5 karty
            }
        }
    }

    private void najwyzszaPiataKartaFlush() {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("Brawo, masz flusha i wygrywasz : " + g.getNick()); // dla 5 karty
            }
        }
    }

    private int sprawdzeniePiatejKartyFlush(ArrayList<Gracz> playersWithFlush) {
        int temp;
        maxWartosc = playersWithFlush.get(0).listaTmp.get(4).getWartosc().getWartosc();
        // sprawdzam 4 karte
        temp = 0;

        for (int i = 0; i < playersWithFlush.size(); i++) {

            if (playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc();
            }
        }
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                temp++;
            }

        }
        return temp;
    }

    private boolean tylkoJedenGraczMaNajwyzszaKarte(int temp) {
        return temp == 1;
    }

    private void najwyzszaCzwartaKartaFlush() {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("Brawo, masz flusha i wygrywasz : " + g.getNick()); // dla 4 karty
            }
        }
    }

    private int sprawdzenieCzwartejKartyFlush(ArrayList<Gracz> playersWithFlush) {
        int temp;
        maxWartosc = playersWithFlush.get(0).listaTmp.get(3).getWartosc().getWartosc();
        // sprawdzam 4 karte
        temp = 0;

        for (int i = 0; i < playersWithFlush.size(); i++) {

            //sprawdzam drugą kartę
            if (playersWithFlush.get(i).listaTmp.get(3).getWartosc().getWartosc() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).listaTmp.get(3).getWartosc().getWartosc();
            }
        }
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                temp++;
            }

        }
        return temp;
    }

    private void najwyzszaTrzeciaKartaFlush() {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(2).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("Brawo, masz flusha i wygrywasz : " + g.getNick()); // dla 3 karty
            }
        }
    }

    private int sprawdzenieNajwyzszejTrzeciejKartyFlush(ArrayList<Gracz> playersWithFlush) {

        int temp;
        maxWartosc = playersWithFlush.get(0).listaTmp.get(2).getWartosc().getWartosc();

        temp = 0;
        //sprawdzam 3 karte
        for (int i = 0; i < playersWithFlush.size(); i++) {

            //sprawdzam drugą kartę
            if (playersWithFlush.get(i).listaTmp.get(2).getWartosc().getWartosc() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).listaTmp.get(2).getWartosc().getWartosc();
            }
        }
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(2).getWartosc().getWartosc() == maxWartosc) {
                temp++;
            }

        }
        return temp;
    }

    private void najwyzszaDrugaKartaFlush() {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(1).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("Brawo, masz flusha i wygrywasz : " + g.getNick()); // dla 2 karty
            }
        }
    }

    private int sprawdzenieDrugiejKartyFlush(ArrayList<Gracz> playersWithFlush) {
        int temp;
        maxWartosc = playersWithFlush.get(0).listaTmp.get(1).getWartosc().getWartosc();
        temp = 0;
        for (int i = 0; i < playersWithFlush.size(); i++) {

            //sprawdzam drugą kartę
            if (playersWithFlush.get(i).listaTmp.get(1).getWartosc().getWartosc() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).listaTmp.get(1).getWartosc().getWartosc();
            }
        }
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(1).getWartosc().getWartosc() == maxWartosc) {
                temp++;
            }
        }
        return temp;
    }

    private void najwyzszaPierwszaKartaFlush() {
        for (Gracz g : gracze) {
            if (g.listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("Brawo, masz flusha i wygrywasz : " + g.getNick()); // dla 1 karty
            }
        }
    }

    private int sprawdzeniePierwszejKartyFlush(ArrayList<Gracz> playersWithFlush) {
        for (Gracz g : gracze) {

            if (g.isCzyFlush() && g.kartyWRece.size() != 0) {
                playersWithFlush.add(g);

            }
        }

        //sprawdzam pierwszą kartę

        int temp = 0;
        maxWartosc = playersWithFlush.get(0).listaTmp.get(0).getWartosc().getWartosc();

        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(0).getWartosc().getWartosc() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).listaTmp.get(0).getWartosc().getWartosc();
            }
        }

        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                temp++;
            }
        }
        return temp;
    }

    private void wynikSprawdzaniaDlaFullHouse() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 1) {
            for (Gracz g : gracze) {
                if (g.isCzyFullHouse()) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("Brawo, wygrałeś bo masz fulla : " + g.getNick());
                }
            }
        } else if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter > 1) {
            ArrayList<Gracz> playersWithFullHouse = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyFullHouse()) {
                    playersWithFullHouse.add(g);
                }
            }

            int temp = 0;
            maxWartosc = playersWithFullHouse.get(0).getWartoscKartGracza();

            for (int i = 0; i < playersWithFullHouse.size(); i++) {
                if (playersWithFullHouse.get(i).getWartoscKartGracza() >= maxWartosc) {
                    maxWartosc = playersWithFullHouse.get(i).getWartoscKartGracza();
                }
            }

            for (int i = 0; i < playersWithFullHouse.size(); i++) {
                if (playersWithFullHouse.get(i).getWartoscKartGracza() == maxWartosc) {
                    temp++;
                }
            }

            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Brawo, wygrałeś bo masz fulla : " + g.getNick());
                    }
                }

            } else if (temp > 1) {

                maxWartosc = playersWithFullHouse.get(0).listaTmp.get(4).getWartosc().getWartosc();
                temp = 0;
                for (int i = 0; i < playersWithFullHouse.size(); i++) {
                    if (playersWithFullHouse.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                        maxWartosc = playersWithFullHouse.get(i).listaTmp.get(4).getWartosc().getWartosc();
                        System.out.println("Max wartosc " + maxWartosc);
                    }
                }
                for (int i = 0; i < playersWithFullHouse.size(); i++) {
                    if (playersWithFullHouse.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                        temp++;
                    }
                }
                if (temp == 1) {
                    for (Gracz g : gracze) {
                        if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                            System.out.println("Brawo, wygrałeś bo masz Fulla najwyzszego + " + g.getNick());
                        }
                    }
                } else if (temp > 1) {
                    System.out.println("ile osob wygralo " + temp);
                    for (Gracz g : gracze) {

                        if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                            System.out.println("Brawo, wygrales razem z innym bo macie takiego samego Fulla : " + g.getNick());

                        }
                    }
                }

            }
        }
    }

    private void gdyFourOfAKindMaWiecejNizJedenGracz_PlusWszystkiePrzypadki(ArrayList<Gracz> playersWithFlush) {
        for (Gracz g : gracze) {

            if (g.isCzyFourOfAKind() && g.kartyWRece.size() != 0) {
                playersWithFlush.add(g);

            }
        }

        int temp = 0;
        maxWartosc = playersWithFlush.get(0).getWartoscKartGracza();

        szukajWartosciMaksymalnejKartFourOfAKind(playersWithFlush);

        temp = szukajIleOsobMaMaksymalnaWartoscKartFourOfAKind(playersWithFlush, temp);

        if (temp == 1) {
            for (Gracz g : gracze) {
                if (g.getWartoscKartGracza() == maxWartosc) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("Brawo, wygrałeś : " + g.getNick());
                }
            }

        } else if (temp > 1) {
            System.out.println("czy znajduje sie tutaj?");
            maxWartosc = playersWithFlush.get(0).listaTmp.get(4).getWartosc().getWartosc();
            temp = 0;
            for (int i = 0; i < playersWithFlush.size(); i++) {
                if (playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                    maxWartosc = playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc();
                    System.out.println("Max wartosc " + maxWartosc);

                }
            }
            for (int i = 0; i < playersWithFlush.size(); i++) {
                if (playersWithFlush.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                    temp++;
                }
            }
            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Brawo, wygrałeś + " + g.getNick());
                    }
                }
            } else if (temp > 1) {
                System.out.println("ile osob wygralo " + temp);
                for (Gracz g : gracze) {
                    if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                        System.out.println("Brawo, wygrales razem z innym : " + g.getNick());

                    }
                }
            }
        }
    }

    private void gdyFourOfAKindMaJedenGracz() {
        for (Gracz g : gracze) {
            System.out.println(g.isCzyFourOfAKind());
            if (g.isCzyFourOfAKind()) {
                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                System.out.println("BRAWO, Wygrałeś bo masz Karetę: " + g.getNick());
            }
        }
    }

    private int szukajIleOsobMaMaksymalnaWartoscKartFourOfAKind(ArrayList<Gracz> playersWithFlush, int temp) {
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).getWartoscKartGracza() == maxWartosc) {
                temp++;

            }
        }
        return temp;
    }

    private void szukajWartosciMaksymalnejKartFourOfAKind(ArrayList<Gracz> playersWithFlush) {
        for (int i = 0; i < playersWithFlush.size(); i++) {
            if (playersWithFlush.get(i).getWartoscKartGracza() >= maxWartosc) {
                maxWartosc = playersWithFlush.get(i).getWartoscKartGracza();

            }
        }
    }

    private void sprawdzamIleGraczyMaDanyUklad(Comparator<Karta> comparatorKartaWartosc) {

        for (Gracz g : gracze) {
            g.kartyWRece.sort(comparatorKartaWartosc);
            System.out.println();
            System.out.println();
            System.out.println(g.getNick());
            System.out.println();

            if (g.kartyWRece.size() != 0) {
                checkRoyalFlush(g);

                if (!g.isCzyRoyalFlush()) {
                    checkStraightFlush(g);
                    System.out.println(g.listaTmp);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);

                }
                if (!g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkFourOfAKind(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);
                }
                if (!g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkFullHouse(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);

                }
                if (!g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkFlush(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);
                }
                if (!g.isCzyFlush() && !g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkStraight(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);

                }

                if (!g.isCzyStraight() && !g.isCzyFlush() && !g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkThreeOfAKind(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);

                }

                if (!g.isCzyThreeOfAKind() && !g.isCzyStraight() && !g.isCzyFlush() && !g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkTwoPair(g);
                    System.out.println(g.getNick() + "\n " + g.listaTmp);

                }
                if (!g.isCzyTwoPair() && !g.isCzyThreeOfAKind() && !g.isCzyStraight() && !g.isCzyFlush() && !g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkOnePair(g);
                    System.out.println(g.listaTmp);

                }
                if (!g.isCzyOnePair() && !g.isCzyTwoPair() && !g.isCzyThreeOfAKind() && !g.isCzyStraight() && !g.isCzyFlush() && !g.isCzyFullHouse() && !g.isCzyFourOfAKind() && !g.isCzyStraightFlush() && !g.isCzyRoyalFlush()) {
                    checkHighCard(g);
                    System.out.println(g.listaTmp);
                }
            }
        }
    }

    private void wynikSprawdzeniaDlaStraightFlush() {

        if (royalFlushCounter == 0 && straightFlushCounter == 1) {
            for (Gracz g : gracze) {
                if (g.isCzyStraightFlush() && g.getKartyWRece().size() != 0) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("BRAWO WYGRALes bo masz Straight Flush: " + g.getNick());
                }
            }
        } else if (royalFlushCounter == 0 && straightFlushCounter > 1) {
            ArrayList<Gracz> playersWithStraightFlush = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyStraightFlush() && g.kartyWRece.size() != 0) {
                    playersWithStraightFlush.add(g);
                }
            }

            maxWartosc = playersWithStraightFlush.get(0).listaTmp.get(0).getWartosc().getWartosc();

            for (int i = 0; i < playersWithStraightFlush.size(); i++) {

                if (playersWithStraightFlush.get(i + 1).listaTmp.get(0).getWartosc().getWartosc() >= maxWartosc) {
                    maxWartosc = playersWithStraightFlush.get(i + 1).listaTmp.get(0).getWartosc().getWartosc();
                }
            }
            int temp = 0;

            for (Gracz g : gracze) {
                if (g.listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                    temp++;
                }
            }
            for (Gracz g : gracze) {
                if (g.listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                    System.out.println("BRAWO WYGRALes bo masz Straight Flush: " + g.getNick());
                }
            }

        }
    }

    private void wynikSprawdzeniaDlaRoyalFlush() {

        if (royalFlushCounter == 1) {
            for (Gracz g : gracze) {
                if (g.isCzyRoyalFlush() && g.getKartyWRece().size() != 0) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                    System.out.println("BRAWO, " + g.getNick() + " ma Royal Flusha! Wygrywa!!");
                }
            }
        } else if (royalFlushCounter > 1) {
            for (Gracz g : gracze) {
                if (g.isCzyRoyalFlush() && g.getKartyWRece().size() != 0) {
                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / royalFlushCounter));
                }
            }
        }
    }

    private void checkRoyalFlush(Gracz g) {

        System.out.println("Sprawdzam RoyalFlush");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        ArrayList<Karta> tmpPik = new ArrayList<>();
        ArrayList<Karta> tmpTrefl = new ArrayList<>();
        ArrayList<Karta> tmpKaro = new ArrayList<>();
        ArrayList<Karta> tmpKier = new ArrayList<>();

        ArrayList<ArrayList<Karta>> listaList = new ArrayList<>();

        for (Karta k : g.getKartyWRece()) {
            if (k.getKolor().equals(Kolor.PIK)) {
                tmpPik.add(k);
            } else if (k.getKolor().equals(Kolor.TREFL)) {
                tmpTrefl.add(k);
            } else if (k.getKolor().equals(Kolor.KARO)) {
                tmpKaro.add(k);
            } else if (k.getKolor().equals(Kolor.KIER)) {
                tmpKier.add(k);
            }
        }

        listaList.add(tmpPik);
        listaList.add(tmpTrefl);
        listaList.add(tmpKaro);
        listaList.add(tmpKier);

        for (ArrayList<Karta> lista : listaList) {
            lista.sort(comparatorKartaWartosc);
            if (lista.size() >= 5) {
                if (lista.get(0).getWartosc().equals(Wartosc.AS) && lista.get(1).getWartosc().equals(Wartosc.KROL) &&
                        lista.get(2).getWartosc().equals(Wartosc.DAMA) && lista.get(3).getWartosc().equals(Wartosc.JOPEK)
                        && lista.get(4).getWartosc().equals(Wartosc.DZIESIEC)) {

                    g.listaTmp.add(lista.get(0));
                    g.listaTmp.add(lista.get(1));
                    g.listaTmp.add(lista.get(2));
                    g.listaTmp.add(lista.get(3));
                    g.listaTmp.add(lista.get(4));
                    royalFlushCounter++;
                    g.setCzyRoyalFlush(true);

                }

            }
        }


        System.out.println(g.isCzyRoyalFlush());

    }

    private void checkStraightFlush(Gracz g) {

        System.out.println("Sprawdzam Straight Flush");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc);

        ArrayList<Karta> tmpPik = new ArrayList<>();
        ArrayList<Karta> tmpTrefl = new ArrayList<>();
        ArrayList<Karta> tmpKaro = new ArrayList<>();
        ArrayList<Karta> tmpKier = new ArrayList<>();

        ArrayList<ArrayList<Karta>> listaList = new ArrayList<>();

        for (Karta k : g.getKartyWRece()) {
            if (k.getKolor().equals(Kolor.PIK)) {
                tmpPik.add(k);
            } else if (k.getKolor().equals(Kolor.TREFL)) {
                tmpTrefl.add(k);
            } else if (k.getKolor().equals(Kolor.KARO)) {
                tmpKaro.add(k);
            } else if (k.getKolor().equals(Kolor.KIER)) {
                tmpKier.add(k);
            }
        }

        listaList.add(tmpPik);
        listaList.add(tmpTrefl);
        listaList.add(tmpKaro);
        listaList.add(tmpKier);

        for (ArrayList<Karta> lista : listaList) {
            lista.sort(comparatorKartaWartosc.reversed());
            if (lista.size() >= 5) {

                for (int j = 0; j < (lista.size() - 4); j++) {
                    for (int i = 14; i > 2; i--) {

                        if (czyStraight(lista, i, j)) {

                            g.listaTmp.add(lista.get(j));
                            g.listaTmp.add(lista.get(j + 1));
                            g.listaTmp.add(lista.get(j + 2));
                            g.listaTmp.add(lista.get(j + 3));
                            g.listaTmp.add(lista.get(j + 4));
                            straightFlushCounter++;
                            g.setCzyStraightFlush(true);

                        } else if (czyStrightOd5DoAsa(lista, 14, 5, 4, 3, 2)) {
                            //To nie działa tak jak powinno, nie rozpatruje jednego przypadku w ktorym mamy karty pik np. as, 2, 3 , 4 ,5 , 8
                            lista.sort(comparatorKartaWartosc);
//                            System.out.println(lista);
                            g.listaTmp.add(lista.get(0));
                            g.listaTmp.add(lista.get(1));
                            g.listaTmp.add(lista.get(2));
                            g.listaTmp.add(lista.get(3));
                            g.listaTmp.add(lista.get(4));
                            straightFlushCounter++;
                            g.setCzyStraightFlush(true);
                        }
                    }
                }
            }
        }


        System.out.println(g.isCzyStraightFlush());
//        System.out.println(g.getWartoscTmp());
    }

    private void checkFourOfAKind(Gracz g) {

        System.out.println("Sprawdzam four of a kind");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        g.kartyWRece.sort(comparatorKartaWartosc);
        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);

//        System.out.println("Po sortowaniu.");
//        System.out.println(kartyWReceCopy);

        for (int i = 0; i < 3; i++) {
            for (int j = 14; j > 2; j--) {
                if (czyFourOfAKind(g, j, i)) {
                    g.listaTmp.add(kartyWReceCopy.get(i));
                    g.listaTmp.add(kartyWReceCopy.get(i + 1));
                    g.listaTmp.add(kartyWReceCopy.get(i + 2));
                    g.listaTmp.add(kartyWReceCopy.get(i + 3));
                    kartyWReceCopy.remove(i + 3);
                    kartyWReceCopy.remove(i + 2);
                    kartyWReceCopy.remove(i + 1);
                    kartyWReceCopy.remove(i);
                    g.listaTmp.add(kartyWReceCopy.get(0));
                    g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() +
                            g.listaTmp.get(1).getWartosc().getWartosc() + g.listaTmp.get(2).getWartosc().getWartosc() +
                            g.listaTmp.get(3).getWartosc().getWartosc());
                    fourOfAKindCounter++;

                    g.setCzyFourOfAKind(true);
                }
            }
        }

//        System.out.println("Po usunieciu");
//        System.out.println(kartyWReceCopy);

        kartyWReceCopy.sort(comparatorKartaWartosc);

//        System.out.println("Lista tmp cała, razem z high card");
//        System.out.println(listaTmp);
//        System.out.println(g.isCzyFourOfAKind());
//        System.out.println(g.getWartoscTmp());
        System.out.println(g.isCzyFourOfAKind());
    }

    private void checkFullHouse(Gracz g) {

        System.out.println("Sprawdzam Fulla");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        boolean tmp = false;

        g.kartyWRece.sort(comparatorKartaWartosc);
        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);
//        System.out.println("Karty po sortowaniu.");
//        System.out.println(kartyWReceCopy);

        for (int i = 0; i < (kartyWReceCopy.size() - 2); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 2).getWartosc().getWartosc() && !tmp) {
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                g.listaTmp.add(kartyWReceCopy.get(i + 2));
                kartyWReceCopy.remove(i + 2);
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                tmp = true;
            }
        }

//        System.out.println("Karty po usunięciu");
//        System.out.println(kartyWReceCopy);
//        System.out.println();
//        System.out.println("Karty w liscieTMP");
//        System.out.println(listaTmp);

        for (int i = 0; i < (kartyWReceCopy.size() - 1); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && tmp) {
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                fullHouseCounter++;
                tmp = false;
                g.setCzyFullHouse(true);
                g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() + g.listaTmp.get(1).getWartosc().getWartosc() + g.listaTmp.get(2).getWartosc().getWartosc());

            }
        }
//
//        System.out.println();
//        System.out.println("KARTY");
//        System.out.println(listaTmp);

        if (!g.isCzyFullHouse()) {
            g.listaTmp.removeAll(g.listaTmp);
        }


        System.out.println(g.isCzyFullHouse());
    }

    private void checkFlush(Gracz g) {

        System.out.println("Sprawdzam Flush");

        Comparator<Karta> comparatorKartaWartoscIKolor = Comparator.comparing(Karta::getWartosc).reversed();

        ArrayList<Karta> tmpPik = new ArrayList<>();
        ArrayList<Karta> tmpTrefl = new ArrayList<>();
        ArrayList<Karta> tmpKaro = new ArrayList<>();
        ArrayList<Karta> tmpKier = new ArrayList<>();

        ArrayList<ArrayList<Karta>> listyTmp = new ArrayList<>();

        g.kartyWRece.sort(comparatorKartaWartoscIKolor);
//        System.out.println(g);

        for (Karta k : g.kartyWRece) {
            if (k.getKolor().equals(Kolor.PIK)) {
                tmpPik.add(k);
            } else if (k.getKolor().equals(Kolor.TREFL)) {
                tmpTrefl.add(k);
            } else if (k.getKolor().equals(Kolor.KARO)) {
                tmpKaro.add(k);
            } else if (k.getKolor().equals(Kolor.KIER)) {
                tmpKier.add(k);
            }
        }

        listyTmp.add(tmpPik);
        listyTmp.add(tmpTrefl);
        listyTmp.add(tmpKaro);
        listyTmp.add(tmpKier);

        for (ArrayList<Karta> aL : listyTmp) {
            if (aL.size() >= 5) {
                aL.sort(comparatorKartaWartoscIKolor);
                if (aL.size() == 6) {
                    aL.remove(5);
                } else if (aL.size() == 7) {
                    aL.remove(6);
                    aL.remove(5);
                }

                g.listaTmp.add(aL.get(0));
                g.listaTmp.add(aL.get(1));
                g.listaTmp.add(aL.get(2));
                g.listaTmp.add(aL.get(3));
                g.listaTmp.add(aL.get(4));

                flushCounter++;
                //zapisanie najwyzszej karty wartosci
                g.setCzyFlush(true);
//                System.out.println(g.isCzyFlush());
            }
        }

        System.out.println(g.isCzyFlush());

    }

    private void checkStraight(Gracz g) {

        System.out.println("Sprawdzam straight");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc);

        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);

        for (int i = 0; i < (kartyWReceCopy.size() - 1); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc()) {
                kartyWReceCopy.remove(j + 1);
                i--;
            }

        }

        for (int j = 0; j < (kartyWReceCopy.size() - 4); j++) {
            for (int i = 14; i > 2; i--) {

                kartyWReceCopy.sort(comparatorKartaWartosc.reversed());

                if (czyStraight(kartyWReceCopy, i, j) && !g.isCzyStraight()) {

//                    System.out.println(kartyWReceCopy);

                    g.listaTmp.add(kartyWReceCopy.get(j));
                    g.listaTmp.add(kartyWReceCopy.get(j + 1));
                    g.listaTmp.add(kartyWReceCopy.get(j + 2));
                    g.listaTmp.add(kartyWReceCopy.get(j + 3));
                    g.listaTmp.add(kartyWReceCopy.get(j + 4));
                    g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() +
                            g.listaTmp.get(1).getWartosc().getWartosc() + g.listaTmp.get(2).getWartosc().getWartosc() +
                            g.listaTmp.get(3).getWartosc().getWartosc() + g.listaTmp.get(4).getWartosc().getWartosc());
                    straightCounter++;

//                    System.out.println("SUPER");
                    //zapisuje najwyższą kartę
                    g.setCzyStraight(true);

                }

                kartyWReceCopy.sort(comparatorKartaWartosc);

                if (czyStrightOd5DoAsaPoprawka(kartyWReceCopy, 2, 3, 4, 5, 14) && !g.isCzyStraight()) {

                    g.listaTmp.add(kartyWReceCopy.get(j));
                    g.listaTmp.add(kartyWReceCopy.get(j + 1));
                    g.listaTmp.add(kartyWReceCopy.get(j + 2));
                    g.listaTmp.add(kartyWReceCopy.get(j + 3));
                    g.listaTmp.add(kartyWReceCopy.get(kartyWReceCopy.size() - 1));
                    g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() +
                            g.listaTmp.get(1).getWartosc().getWartosc() + g.listaTmp.get(2).getWartosc().getWartosc() +
                            g.listaTmp.get(3).getWartosc().getWartosc() + g.listaTmp.get(4).getWartosc().getWartosc() - 13);
                    straightCounter++;

                    //zapisuje wartosc najwyzszej karty

                    g.setCzyStraight(true);
                }
            }
        }

//        System.out.println(listaTmp);

        System.out.println(g.isCzyStraight());
    }

    private void checkThreeOfAKind(Gracz g) {

        System.out.println("Sprawdzam trójkę");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();
        boolean tmp = false;

        g.kartyWRece.sort(comparatorKartaWartosc);
        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);
//        System.out.println("Karty po sortowaniu.");
//        System.out.println(kartyWReceCopy);

        for (int i = 0; i < (kartyWReceCopy.size() - 2); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 2).getWartosc().getWartosc() && !tmp) {
//                System.out.println("RAS");
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                g.listaTmp.add(kartyWReceCopy.get(i + 2));
                g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() + g.listaTmp.get(1).getWartosc().getWartosc() + g.listaTmp.get(2).getWartosc().getWartosc());
                kartyWReceCopy.remove(i + 2);
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                g.listaTmp.add(kartyWReceCopy.get(0));
                g.listaTmp.add(kartyWReceCopy.get(1));
                threeOfAKindCounter++;
                tmp = true;
                g.setCzyThreeOfAKind(true);
            }
        }

//        System.out.println("Karty po usunięciu");
//        System.out.println(kartyWReceCopy);
//        System.out.println();
//        System.out.println("Karty w liscieTMP");
//        System.out.println(listaTmp);


        System.out.println(g.isCzyThreeOfAKind());


    }

    private void checkTwoPair(Gracz g) {

        System.out.println("Sprawdzam dwie pary");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        boolean tmp = false;

        g.kartyWRece.sort(comparatorKartaWartosc);
        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);
//        System.out.println("KARTY PO SORTOWANIU");
//        System.out.println(kartyWReceCopy + "\n");

        for (int i = 0; i < (kartyWReceCopy.size() - 1); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && !tmp) {
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() + g.listaTmp.get(1).getWartosc().getWartosc());
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                tmp = true;
            }
        }

//        System.out.println("PIERWSZA PARA");
//        System.out.println(listaTmp + "\n");

        boolean ostatniTest = true;

        for (int i = 0; i < (kartyWReceCopy.size() - 1); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && tmp && ostatniTest) {
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                g.listaTmp.add(kartyWReceCopy.get(0));
                twoPairCounter++;
                ostatniTest = false;
                tmp = true;
                g.setCzyTwoPair(true);
            }
        }

//        System.out.println("WSZYSTKIE KARTY");
//        System.out.println(listaTmp);

        if (!g.isCzyTwoPair()) {
            g.listaTmp.removeAll(g.listaTmp);
        }

        System.out.println(g.isCzyTwoPair());

    }

    private void checkOnePair(Gracz g) {

        System.out.println("Sprawdzam jedną parę");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        boolean tmp = false;

        g.kartyWRece.sort(comparatorKartaWartosc);
        ArrayList<Karta> kartyWReceCopy = new ArrayList<>(g.kartyWRece);
//        System.out.println("KARTY PO SORTOWANIU");
//        System.out.println(kartyWReceCopy + "\n");

        for (int i = 0; i < (kartyWReceCopy.size() - 1); i++) {
            if (kartyWReceCopy.get(i).getWartosc().getWartosc() == kartyWReceCopy.get(i + 1).getWartosc().getWartosc() && !tmp) {
                g.listaTmp.add(kartyWReceCopy.get(i));
                g.listaTmp.add(kartyWReceCopy.get(i + 1));
                kartyWReceCopy.remove(i + 1);
                kartyWReceCopy.remove(i);
                g.listaTmp.add(kartyWReceCopy.get(0));
                g.listaTmp.add(kartyWReceCopy.get(1));
                g.listaTmp.add(kartyWReceCopy.get(2));
                g.setWartoscKartGracza(g.listaTmp.get(0).getWartosc().getWartosc() + g.listaTmp.get(1).getWartosc().getWartosc());
                onePairCounter++;
                g.setCzyOnePair(true);
                tmp = true;
            }
        }

//        System.out.println("WSZYSTKIE KARRTY");
//        System.out.println(listaTmp);

        System.out.println(g.isCzyOnePair());

    }

    private void checkHighCard(Gracz g) {

        System.out.println("Sprawdzam wysoką kartę");

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();


        g.kartyWRece.sort(comparatorKartaWartosc);

        g.listaTmp.add(g.kartyWRece.get(0));
        g.listaTmp.add(g.kartyWRece.get(1));
        g.listaTmp.add(g.kartyWRece.get(2));
        g.listaTmp.add(g.kartyWRece.get(3));
        g.listaTmp.add(g.kartyWRece.get(4));
        highCardCounter++;


    }

    private boolean czyStraight(ArrayList<Karta> lista, int i, int j) {
        return lista.get(j).getWartosc().getWartosc() == i && lista.get(j + 1).getWartosc().getWartosc() == (i - 1) && lista.get(j + 2).getWartosc().getWartosc() == (i - 2) &&
                lista.get(j + 3).getWartosc().getWartosc() == (i - 3) && lista.get(j + 4).getWartosc().getWartosc() == (i - 4);
    }

    private boolean czyStrightOd5DoAsa(ArrayList<Karta> lista, int i, int i2, int i3, int i4, int i5) {
        return lista.get(0).getWartosc().getWartosc() == i && lista.get(1).getWartosc().getWartosc() == i2 && lista.get(2).getWartosc().getWartosc() == i3 &&
                lista.get(3).getWartosc().getWartosc() == i4 && lista.get(4).getWartosc().getWartosc() == i5;
    }

    private boolean czyStrightOd5DoAsaPoprawka(ArrayList<Karta> lista, int i, int i2, int i3, int i4, int i5) {
        return lista.get(j).getWartosc().getWartosc() == i && lista.get(j + 1).getWartosc().getWartosc() == i2 && lista.get(j + 2).getWartosc().getWartosc() == i3 &&
                lista.get(j + 3).getWartosc().getWartosc() == i4 && lista.get(lista.size() - 1).getWartosc().getWartosc() == i5;
    }

    private int sumaWartosciStraightFlush(Gracz g, int i) {
        return g.kartyWRece.get(j).getWartosc().getWartosc() + g.kartyWRece.get(j + 1).getWartosc().getWartosc() +
                g.kartyWRece.get(j + 2).getWartosc().getWartosc() +
                g.kartyWRece.get(j + 3).getWartosc().getWartosc() +
                g.kartyWRece.get(j + 4).getWartosc().getWartosc();
    }

    private int sumaWartosciStraightFlushOd5DoAsa(Gracz g, int i, ArrayList<Karta> lista) {
        return lista.get(j).getWartosc().getWartosc() + lista.get(j + 1).getWartosc().getWartosc() +
                lista.get(j + 2).getWartosc().getWartosc() +
                lista.get(j + 3).getWartosc().getWartosc() +
                lista.get(lista.size() - 1).getWartosc().getWartosc();
    }

    private int sumaWartosciFourOfAKind(Gracz g, int i) {
        return g.kartyWRece.get(i).getWartosc().getWartosc() + g.kartyWRece.get(i + 1).getWartosc().getWartosc() +
                g.kartyWRece.get(i + 2).getWartosc().getWartosc() +
                g.kartyWRece.get(i + 3).getWartosc().getWartosc();
    }

    private boolean czyFourOfAKind(Gracz g, int j, int i) {
        return g.kartyWRece.get(i).getWartosc().getWartosc() == j &&
                g.kartyWRece.get(i + 1).getWartosc().getWartosc() == j &&
                g.kartyWRece.get(i + 2).getWartosc().getWartosc() == j &&
                g.kartyWRece.get(i + 3).getWartosc().getWartosc() == j;
    }

    private void wynikSprawdzeniaDlaThreeOfAKind() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter == 0 && threeOfAKindCounter >= 1) {

            ArrayList<Gracz> playersWithThreeOfAKind = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyThreeOfAKind()) {
                    playersWithThreeOfAKind.add(g);
                }
            }

            int temp = 0;
            maxWartosc = playersWithThreeOfAKind.get(0).getWartoscKartGracza();

            for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                if (playersWithThreeOfAKind.get(i).getWartoscKartGracza() >= maxWartosc) {
                    maxWartosc = playersWithThreeOfAKind.get(i).getWartoscKartGracza();
                }
            }
            for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                if (playersWithThreeOfAKind.get(i).getWartoscKartGracza() == maxWartosc) {
                    temp++;
                }
            }

            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc && g.isCzyThreeOfAKind()) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Brawo, masz Trójkę i wygrywasz! " + g.getNick());
                    }
                }
            } else if (temp > 1) {

                // sprawdzenie czwartej karty w rece kiedy mamy trójkę
                maxWartosc = playersWithThreeOfAKind.get(0).listaTmp.get(3).getWartosc().getWartosc();
                temp = 0;

                for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                    if (playersWithThreeOfAKind.get(i).listaTmp.get(3).getWartosc().getWartosc() >= maxWartosc) {
                        maxWartosc = playersWithThreeOfAKind.get(i).listaTmp.get(3).getWartosc().getWartosc();
                    }
                }

                for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                    if (playersWithThreeOfAKind.get(i).listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                        temp++;
                    }
                }
                if (temp == 1) {
                    for (Gracz g : gracze) {
                        if (g.listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                            System.out.println("Brawo, wygrałes bo masz trójkę: " + g.getNick());
                        }
                    }
                } else if (temp > 1) {

                    maxWartosc = playersWithThreeOfAKind.get(0).listaTmp.get(4).getWartosc().getWartosc();
                    temp = 0;

                    for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                        if (playersWithThreeOfAKind.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                            maxWartosc = playersWithThreeOfAKind.get(i).listaTmp.get(4).getWartosc().getWartosc();
                        }
                    }

                    for (int i = 0; i < playersWithThreeOfAKind.size(); i++) {
                        if (playersWithThreeOfAKind.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                            temp++;
                        }
                    }
                    if (temp >= 1) {
                        for (Gracz g : gracze) {
                            if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc && g.isCzyThreeOfAKind()) {
                                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                                System.out.println("Brawo, wygrałeś bo masz fulla: " + g.getNick());
                            }
                        }
                    }


                }

            }


        }
    }

    private void wynikSprawdzenieDlaHighCard() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter == 0 && threeOfAKindCounter == 0 && twoPairCounter == 0 && onePairCounter == 0 && highCardCounter >= 1) {

            ArrayList<Gracz> playersWithHighCard = new ArrayList<>();

            for (Gracz g : gracze) {

                if (g.isCzyHighCard() && g.kartyWRece.size() != 0) {
                    playersWithHighCard.add(g);

                }
            }

            //sprawdzam pierwszą kartę

            int temp = 0;
            maxWartosc = playersWithHighCard.get(0).listaTmp.get(0).getWartosc().getWartosc();

            for (int i = 0; i < playersWithHighCard.size(); i++) {
                if (playersWithHighCard.get(i).listaTmp.get(0).getWartosc().getWartosc() >= maxWartosc) {
                    maxWartosc = playersWithHighCard.get(i).listaTmp.get(0).getWartosc().getWartosc();
                }
            }

            for (int i = 0; i < playersWithHighCard.size(); i++) {
                if (playersWithHighCard.get(i).listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                    temp++;
                }
            }
            ;

            if (temp == 1) {

                for (Gracz g : gracze) {
                    if (g.listaTmp.get(0).getWartosc().getWartosc() == maxWartosc) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Brawo, masz najwyzsza karte i wygrywasz : " + g.getNick()); // dla 1 karty
                    }
                }

            } else if (temp > 1) {

                maxWartosc = playersWithHighCard.get(0).listaTmp.get(1).getWartosc().getWartosc();
                temp = 0;
                for (int i = 0; i < playersWithHighCard.size(); i++) {

                    //sprawdzam drugą kartę

                    if (playersWithHighCard.get(i).listaTmp.get(1).getWartosc().getWartosc() >= maxWartosc) {
                        maxWartosc = playersWithHighCard.get(i).listaTmp.get(1).getWartosc().getWartosc();
                    }
                }
                for (int i = 0; i < playersWithHighCard.size(); i++) {
                    if (playersWithHighCard.get(i).listaTmp.get(1).getWartosc().getWartosc() == maxWartosc) {
                        temp++;
                    }
                }
                if (temp == 1) {
                    for (Gracz g : gracze) {
                        if (g.listaTmp.get(1).getWartosc().getWartosc() == maxWartosc) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                            System.out.println("Brawo, masz najwyzsza karte i wygrywasz : " + g.getNick());
                        }
                    }
                } else if (temp > 1) {


                    maxWartosc = playersWithHighCard.get(0).listaTmp.get(2).getWartosc().getWartosc();
                    temp = 0;
                    for (int i = 0; i < playersWithHighCard.size(); i++) {

                        //sprawdzam trzecia kartę
                        if (playersWithHighCard.get(i).listaTmp.get(2).getWartosc().getWartosc() >= maxWartosc) {
                            maxWartosc = playersWithHighCard.get(i).listaTmp.get(2).getWartosc().getWartosc();
                        }
                    }
                    for (int i = 0; i < playersWithHighCard.size(); i++) {
                        if (playersWithHighCard.get(i).listaTmp.get(2).getWartosc().getWartosc() == maxWartosc) {
                            temp++;
                        }
                    }
                    if (temp == 1) {
                        for (Gracz g : gracze) {
                            if (g.listaTmp.get(2).getWartosc().getWartosc() == maxWartosc) {
                                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                                System.out.println("Brawo, masz najwyzsza karte i wygrywasz : " + g.getNick());
                            }
                        }
                    } else if (temp > 1) {


                        maxWartosc = playersWithHighCard.get(0).listaTmp.get(3).getWartosc().getWartosc();
                        temp = 0;
                        for (int i = 0; i < playersWithHighCard.size(); i++) {

                            //sprawdzam czwarta kartę
                            if (playersWithHighCard.get(i).listaTmp.get(3).getWartosc().getWartosc() >= maxWartosc) {
                                maxWartosc = playersWithHighCard.get(i).listaTmp.get(3).getWartosc().getWartosc();
                            }
                        }
                        for (int i = 0; i < playersWithHighCard.size(); i++) {
                            if (playersWithHighCard.get(i).listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                                temp++;
                            }
                        }
                        if (temp == 1) {
                            for (Gracz g : gracze) {
                                if (g.listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                                    System.out.println("Brawo, masz najwyzsza karte i wygrywasz : " + g.getNick());
                                }
                            }
                        } else if (temp > 1) {

                            maxWartosc = playersWithHighCard.get(0).listaTmp.get(4).getWartosc().getWartosc();
                            temp = 0;

                            for (int i = 0; i < playersWithHighCard.size(); i++) {

                                //sprawdzam piata kartę
                                if (playersWithHighCard.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                                    maxWartosc = playersWithHighCard.get(i).listaTmp.get(4).getWartosc().getWartosc();
                                }
                            }
                            for (int i = 0; i < playersWithHighCard.size(); i++) {
                                if (playersWithHighCard.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                                    temp++;
                                }
                            }
                            if (temp >= 1) {
                                for (Gracz g : gracze) {
                                    if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                                        System.out.println("Brawo, masz najwyzsza karte i wygrywasz : " + g.getNick());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void wynikSprawdzenieDlaOnePair() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter == 0 && threeOfAKindCounter == 0 && twoPairCounter == 0 && onePairCounter >= 1) {

            ArrayList<Gracz> playersWithOnePair = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyOnePair()) {
                    playersWithOnePair.add(g);
                }
            }

            int temp = 0;
            maxWartosc = playersWithOnePair.get(0).getWartoscKartGracza();

            for (int i = 0; i < playersWithOnePair.size(); i++) {
                if (playersWithOnePair.get(i).getWartoscKartGracza() >= maxWartosc) {
                    maxWartosc = playersWithOnePair.get(i).getWartoscKartGracza();
                }
            }
            for (int i = 0; i < playersWithOnePair.size(); i++) {
                if (playersWithOnePair.get(i).getWartoscKartGracza() == maxWartosc) {
                    temp++;
                }
            }
            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc && g.isCzyOnePair()) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Masz najwyzsza pare " + g.getNick());
                    }
                }
            } else if (temp > 1) {

                temp = 0;
                maxWartosc = playersWithOnePair.get(0).listaTmp.get(2).getWartosc().getWartosc();

                for (int i = 0; i < playersWithOnePair.size(); i++) {
                    if (playersWithOnePair.get(i).listaTmp.get(2).getWartosc().getWartosc() >= maxWartosc) {
                        maxWartosc = playersWithOnePair.get(i).listaTmp.get(2).getWartosc().getWartosc();
                    }
                }
                for (int i = 0; i < playersWithOnePair.size(); i++) {
                    if (playersWithOnePair.get(i).listaTmp.get(2).getWartosc().getWartosc() == maxWartosc) {
                        temp++;
                    }
                }

                if (temp == 1) {
                    for (Gracz g : gracze) {
                        if (g.listaTmp.get(2).getWartosc().getWartosc() == maxWartosc && g.isCzyOnePair()) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                            System.out.println("Masz najwyzsza pare " + g.getNick());
                        }
                    }

                } else if (temp > 1) {

                    temp = 0;
                    maxWartosc = playersWithOnePair.get(0).listaTmp.get(3).getWartosc().getWartosc();

                    for (int i = 0; i < playersWithOnePair.size(); i++) {
                        if (playersWithOnePair.get(i).listaTmp.get(3).getWartosc().getWartosc() >= maxWartosc) {
                            maxWartosc = playersWithOnePair.get(i).listaTmp.get(3).getWartosc().getWartosc();
                        }
                    }
                    for (int i = 0; i < playersWithOnePair.size(); i++) {
                        if (playersWithOnePair.get(i).listaTmp.get(3).getWartosc().getWartosc() == maxWartosc) {
                            temp++;
                        }
                    }

                    if (temp == 1) {
                        for (Gracz g : gracze) {
                            if (g.listaTmp.get(3).getWartosc().getWartosc() == maxWartosc && g.isCzyOnePair()) {
                                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                                System.out.println("Masz najwyzsza pare " + g.getNick());
                            }
                        }

                    } else if (temp > 1) {

                        temp = 0;
                        maxWartosc = playersWithOnePair.get(0).listaTmp.get(4).getWartosc().getWartosc();

                        for (int i = 0; i < playersWithOnePair.size(); i++) {
                            if (playersWithOnePair.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                                maxWartosc = playersWithOnePair.get(i).listaTmp.get(4).getWartosc().getWartosc();
                            }
                        }
                        for (int i = 0; i < playersWithOnePair.size(); i++) {
                            if (playersWithOnePair.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                                temp++;
                            }
                        }

                        if (temp >= 1) {
                            for (Gracz g : gracze) {

                                if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc && g.isCzyOnePair()) {
                                    g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                                    System.out.println("Masz najwyzsza pare " + g.getNick());

                                }

                            }
                        }


                    }
                }


            }


        }
    }

    private void wynikSprawdzeniaDlaTwoPair() {
        if (royalFlushCounter == 0 && straightFlushCounter == 0 && fourOfAKindCounter == 0 && fullHouseCounter == 0 && flushCounter == 0 && straightCounter == 0 && threeOfAKindCounter == 0 && twoPairCounter >= 1) {

            ArrayList<Gracz> playersWithTwoPair = new ArrayList<>();

            for (Gracz g : gracze) {
                if (g.isCzyTwoPair()) {
                    playersWithTwoPair.add(g);
                }
            }

            int temp = 0;
            maxWartosc = playersWithTwoPair.get(0).getWartoscKartGracza();

            for (int i = 0; i < playersWithTwoPair.size(); i++) {
                if (playersWithTwoPair.get(i).getWartoscKartGracza() >= maxWartosc) {
                    maxWartosc = playersWithTwoPair.get(i).getWartoscKartGracza();
                }
            }
            for (int i = 0; i < playersWithTwoPair.size(); i++) {
                if (playersWithTwoPair.get(i).getWartoscKartGracza() == maxWartosc) {
                    temp++;
                }
            }
            if (temp == 1) {
                for (Gracz g : gracze) {
                    if (g.getWartoscKartGracza() == maxWartosc && g.isCzyTwoPair()) {
                        g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                        System.out.println("Brawo masz najwyzszą parę i wygrywasz : " + g.getNick());
                    }
                }
            } else if (temp > 1) {

                for (Gracz g : gracze) {
                    g.setWartoscKartGracza(g.listaTmp.get(2).getWartosc().getWartosc() + g.listaTmp.get(3).getWartosc().getWartosc());
                }
                temp = 0;

                maxWartosc = playersWithTwoPair.get(0).getWartoscKartGracza();

                for (int i = 0; i < playersWithTwoPair.size(); i++) {
                    if (playersWithTwoPair.get(i).getWartoscKartGracza() >= maxWartosc) {
                        maxWartosc = playersWithTwoPair.get(i).getWartoscKartGracza();
                    }
                }
                for (int i = 0; i < playersWithTwoPair.size(); i++) {
                    if (playersWithTwoPair.get(i).getWartoscKartGracza() == maxWartosc) {
                        temp++;
                    }
                }

                if (temp == 1) {
                    for (Gracz g : gracze) {
                        if (g.getWartoscKartGracza() == maxWartosc && g.isCzyTwoPair()) {
                            g.setPulaZetonowGracza(g.getPulaZetonowGracza() + pulaGlowna);
                            System.out.println("Brawo masz najwyższą drugą parę i wygrywasz ! " + g.getNick());
                        }
                    }
                } else if (temp > 1) {

                    temp = 0;
                    maxWartosc = playersWithTwoPair.get(0).listaTmp.get(4).getWartosc().getWartosc();

                    for (int i = 0; i < playersWithTwoPair.size(); i++) {
                        if (playersWithTwoPair.get(i).listaTmp.get(4).getWartosc().getWartosc() >= maxWartosc) {
                            maxWartosc = playersWithTwoPair.get(i).listaTmp.get(4).getWartosc().getWartosc();
                        }
                    }
                    for (int i = 0; i < playersWithTwoPair.size(); i++) {
                        if (playersWithTwoPair.get(i).listaTmp.get(4).getWartosc().getWartosc() == maxWartosc) {
                            temp++;
                        }
                    }

                    if (temp >= 1) {
                        for (Gracz g : gracze) {
                            if (g.listaTmp.get(4).getWartosc().getWartosc() == maxWartosc && g.isCzyTwoPair()) {
                                g.setPulaZetonowGracza(g.getPulaZetonowGracza() + (pulaGlowna / temp));
                                System.out.println("Brawo masz najwyższą kartę z dwóch par! " + g.getNick());
                            }
                        }
                    }


                }


            }


        }
    }

    public void sprawdzanieKart() {

        royalFlushCounter = 0;
        straightFlushCounter = 0;
        fourOfAKindCounter = 0;
        fullHouseCounter = 0;
        flushCounter = 0;
        straightCounter = 0;
        threeOfAKindCounter = 0;
        twoPairCounter = 0;
        onePairCounter = 0;
        highCardCounter = 0;

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        sprawdzamIleGraczyMaDanyUklad(comparatorKartaWartosc);

//        System.out.println("Royal :" + royalFlushCounter);
//        System.out.println("Straight Flush: " + straightFlushCounter);
//        System.out.println("Kareta: " + fourOfAKindCounter);
//        System.out.println("FULL: " + fullHouseCounter);
//        System.out.println("Flush: " + flushCounter);
//        System.out.println("Straight: " + straightCounter);
//        System.out.println("Trójka: " + threeOfAKindCounter);
//        System.out.println("Dwie pary: " + twoPairCounter);
//        System.out.println("Jedna para: " + onePairCounter);
//        System.out.println("Wysoka karta: " + highCardCounter);

        wynikSprawdzeniaDlaRoyalFlush();

        wynikSprawdzeniaDlaStraightFlush();

        wynikSprawdzeniaDlaFourOfAKind();

        wynikSprawdzaniaDlaFullHouse();

        wynikSprawdzeniaDlaFlush();

        wynikSprawdzeniaDlaStraight();

        wynikSprawdzeniaDlaThreeOfAKind();

        wynikSprawdzeniaDlaTwoPair();

        wynikSprawdzenieDlaOnePair();

        wynikSprawdzenieDlaHighCard();
    }

}






