package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.karty.Karta;

public class Main {

    public static void main(String[] args) {

        Rozgrywka rozgrywka = new Rozgrywka();

        rozgrywka.setLiczbaGraczy(4);

        rozgrywka.dodajGraczy(2000);


        rozgrywka.rozdajKartyDoReki(rozgrywka.getTaliaKart());

        rozgrywka.rozdajFlop();
        rozgrywka.rozdajTurn();
        rozgrywka.rozdajRiver();

        for (Gracz g : rozgrywka.getGracze()) {
            g.kartyWRece.addAll(rozgrywka.getKartyStol());
        }

        Gracz naszGracz = rozgrywka.getGracze().get(1);
        rozgrywka.getGracze().get(1).getKartyWRece().removeAll(rozgrywka.getGracze().get(1).getKartyWRece());

        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.AS));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.KROL));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.DZIESIEC));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.DAMA));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.JOPEK));


        rozgrywka.sprawdzanieKart();













    }



}
