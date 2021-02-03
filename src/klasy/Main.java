package klasy;

import enumy.Kolor;
import enumy.Wartosc;
import klasy.karty.Karta;
import klasy.menu.MenuPoczatkowe;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        MenuPoczatkowe menuPoczatkowe = new MenuPoczatkowe();

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

        Gracz naszGracz = rozgrywka.getGracze().get(0);
        rozgrywka.getGracze().get(0).getKartyWRece().removeAll(rozgrywka.getGracze().get(0).getKartyWRece());

        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.CZTERY));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KIER, Wartosc.DWA));
        naszGracz.getKartyWRece().add(new Karta(Kolor.PIK, Wartosc.TRZY));
        naszGracz.getKartyWRece().add(new Karta(Kolor.KARO, Wartosc.AS));
        naszGracz.getKartyWRece().add(new Karta(Kolor.TREFL, Wartosc.PIEC));


        rozgrywka.sprawdzanieKart();

        System.out.println();
        System.out.println(rozgrywka.getKartyStol());





    }



}
