package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import jdk.swing.interop.SwingInterOpUtils;
import klasy.karty.Karta;
import klasy.karty.TaliaKart;

import java.io.File;
import java.io.FileNotFoundException;
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

    public void rozpoznawanieKart() {

        for (Gracz g : gracze) {
            if (g.getKartyWRece().size() != 0) {

            }
        }
    }

    public void sprawdzanieKart() {


        for (Gracz g : gracze) {
            checkRoyalFlush(g);
            if (!g.isCzyStraightFlush()) {
                checkStraightFlush(g);
            }
        }

        for (Gracz g : gracze) {
            checkStraightFlush(g);
            if (g.isCzyStraightFlush()) {

            }
        }


    }


    public boolean checkRoyalFlush(Gracz g) {

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

                    g.setCzyRoyalFlush(true);
                    return true;

                }

            }
        }
        return false;
    }

    public void checkStraightFlush(Gracz g) {

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

                for(int j = 0; j < (7 - lista.size()); j++){
                    for (int i = 14; i > 2; i--) {

                        if (czyStraight(lista, i, j)) {
                            g.setWartoscTmp(sumaWartosciStraightFlush(g, i));
                            g.setCzyStraightFlush(true);

                        } else if (czyStrightOd5DoAsa(lista, 14, 5, 4, 3, 2)) {
                            //To nie działa tak jak powinno, nie rozpatruje jednego przypadku w ktorym mamy karty pik np. as, 2, 3 , 4 ,5 , 8
                            lista.sort(comparatorKartaWartosc);
                            System.out.println(lista);
                            g.setWartoscTmp(sumaWartosciStraightFlushOd5DoAsa(g, i, lista) - 13);
                            g.setCzyStraightFlush(true);
                        }
                    }
                }
            }
        }

        System.out.println(g.isCzyStraightFlush());
        System.out.println(g.getWartoscTmp());
    }

    private boolean czyStraight(ArrayList<Karta> lista, int i, int j) {
        return lista.get(j).getWartosc().getWartosc() == i && lista.get(j+1).getWartosc().getWartosc() == (i - 1) && lista.get(j+2).getWartosc().getWartosc() == (i - 2) &&
                lista.get(j+3).getWartosc().getWartosc() == (i - 3) && lista.get(j+4).getWartosc().getWartosc() == (i - 4);
    }

    private int sumaWartosciStraightFlush(Gracz g, int i) {
        return g.kartyWRece.get(j).getWartosc().getWartosc() + g.kartyWRece.get(j + 1).getWartosc().getWartosc() +
                g.kartyWRece.get(j + 2).getWartosc().getWartosc() +
                g.kartyWRece.get(j + 3).getWartosc().getWartosc() +
                g.kartyWRece.get(j+ 4).getWartosc().getWartosc();
    }

    private int sumaWartosciStraightFlushOd5DoAsa(Gracz g, int i, ArrayList<Karta> lista) {
        return lista.get(j).getWartosc().getWartosc() + lista.get(j + 1).getWartosc().getWartosc() +
                lista.get(j + 2).getWartosc().getWartosc() +
                lista.get(j + 3).getWartosc().getWartosc() +
                lista.get(lista.size() -1).getWartosc().getWartosc();
    }

    public void checkFourOfAKind(Gracz g) {

        ArrayList<Karta> listaTmp = new ArrayList<>();

        Comparator<Karta> comparatorKartaWartosc = Comparator.comparing(Karta::getWartosc).reversed();

        g.kartyWRece.sort(comparatorKartaWartosc);

        System.out.println("Po sortowaniu.");
        System.out.println(g.kartyWRece);

        for(int  i = 0; i < 3; i++){
            for (int j = 14; j > 2; j--) {
                if (czyFourOfAKind(g, j, i)) {
                    listaTmp.add(g.kartyWRece.get(i));
                    listaTmp.add(g.kartyWRece.get(i+1));
                    listaTmp.add(g.kartyWRece.get(i+2));
                    listaTmp.add(g.kartyWRece.get(i+3));
                    g.kartyWRece.remove(i + 3);
                    g.kartyWRece.remove(i + 2);
                    g.kartyWRece.remove(i + 1);
                    g.kartyWRece.remove(i);

                    g.setCzyFourOfAKind(true);
                }
            }
        }

        System.out.println("Po usunieciu");
        System.out.println(g.kartyWRece);

        g.kartyWRece.sort(comparatorKartaWartosc);
        listaTmp.add(g.kartyWRece.get(0));
        System.out.println("Lista tmp cała, razem z high card");
        System.out.println(listaTmp);
        System.out.println(g.isCzyFourOfAKind());
        System.out.println(g.getWartoscTmp());
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

    private boolean czyStrightOd5DoAsa(ArrayList<Karta> lista, int i, int i2, int i3, int i4, int i5) {
        return lista.get(0).getWartosc().getWartosc() == i && lista.get(1).getWartosc().getWartosc() == i2 && lista.get(2).getWartosc().getWartosc() == i3 &&
                lista.get(3).getWartosc().getWartosc() == i4 && lista.get(4).getWartosc().getWartosc() == i5;
    }

    public void checkFlush(Gracz g) {


        Comparator<Karta> comparatorKartaWartoscIKolor = Comparator.comparing(Karta::getWartosc).reversed();

        ArrayList<Karta> tmpPik = new ArrayList<>();
        ArrayList<Karta> tmpTrefl = new ArrayList<>();
        ArrayList<Karta> tmpKaro = new ArrayList<>();
        ArrayList<Karta> tmpKier = new ArrayList<>();

        ArrayList<ArrayList<Karta>> listyTmp = new ArrayList<>();

        g.kartyWRece.sort(comparatorKartaWartoscIKolor);
        System.out.println(g);

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

                g.setCzyFlush(true);
                System.out.println(g.isCzyFlush());
            }
        }

    }

    public void sprawdzeniePar() {


    }

}





