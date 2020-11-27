package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.karty.Karta;
import klasy.menu.MenuPoczatkowe;

public class Main {

    public static void main(String[] args) {

        Rozgrywka rozgrywka = new Rozgrywka();

        rozgrywka.setLiczbaGraczy(6);
        rozgrywka.dodajGraczy(2000);


        rozgrywka.rozdajKartyDoReki(rozgrywka.getTaliaKart());

        rozgrywka.rozdajFlop();
        rozgrywka.rozdajTurn();
        rozgrywka.rozdajRiver();

        for (Gracz g : rozgrywka.getGracze()) {
            g.kartyWRece.addAll(rozgrywka.getKartyStol());
        }




        rozgrywka.sprawdzanieKart();

    }



}
