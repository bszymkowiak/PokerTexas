package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.karty.Karta;
import klasy.menu.MenuPoczatkowe;

public class Main {

    public static void main(String[] args) {

        Rozgrywka rozgrywka = new Rozgrywka();

        rozgrywka.setLiczbaGraczy(2);
        rozgrywka.dodajGraczy(2000);


        rozgrywka.rozdajKartyDoReki(rozgrywka.getTaliaKart());

        rozgrywka.rozdajFlop();
        rozgrywka.rozdajTurn();
        rozgrywka.rozdajRiver();

        for (Gracz g : rozgrywka.getGracze()) {
            g.kartyWRece.addAll(rozgrywka.getKartyStol());
        }

        Gracz naszGracz = rozgrywka.getGracze().get(0);
        rozgrywka.getGracze().get(0).getKartyWRece().removeAll(rozgrywka.getGracze().get(0).getKartyWRece());

        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.OSIEM));
        naszGracz.getKartyWRece().add(new Karta(Kolor.TREFL, Wartosc.SZESC));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.DWA));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.OSIEM));
        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.AS));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.KROL));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.DWA));

        rozgrywka.getGracze().get(1).getKartyWRece().removeAll(rozgrywka.getGracze().get(1).getKartyWRece());
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.JOPEK));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.TREFL, Wartosc.JOPEK));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.JOPEK));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.DAMA));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.DAMA));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.KROL));
        rozgrywka.getGracze().get(1).getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.AS));

        rozgrywka.sprawdzanieKart();

    }



}
