package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.karty.Karta;
import klasy.menu.MenuPoczatkowe;

public class Main {

    public static void main(String[] args) {

        Rozgrywka rozgrywka = new Rozgrywka();

        rozgrywka.setLiczbaGraczy(1);
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

        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.JOPEK));
        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.SZESC));
        naszGracz.getKartyWRece().add(new Karta(Kolor.TREFL, Wartosc.DAMA));
        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.CZTERY));
        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.PIEC));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.KROL));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.AS));

//        rozgrywka.checkHighCard(naszGracz);

    }



}
