package klasy.karty;

import EnumKlasy.Kolor;
import EnumKlasy.Wartosc;

import java.util.ArrayList;
import java.util.Random;

public class TaliaKart {

    private ArrayList<Karta> taliaKart = new ArrayList<>();

    public TaliaKart() {

        for (Kolor k : Kolor.values()) {
            for (Wartosc w : Wartosc.values()) {

                taliaKart.add(new Karta(k, w));
            }
        }
    }

    public void wyswietlTalie() {

        for (int i = 0; i < taliaKart.size(); i++) {

            System.out.println(taliaKart.get(i));

        }
    }

    public ArrayList<Karta> tasujKarty(){

        Random rand = new Random();
        Karta[] taliaTmp = new Karta[1];

        for (int i = 0; i < 1000; i++) {
            int a = rand.nextInt(52);
            int b = rand.nextInt(52);

            taliaTmp[0] = taliaKart.get(a);
            taliaKart.set(a, taliaKart.get(b));
            taliaKart.set(b , taliaTmp[0]);
        }

        return taliaKart;

    }

    public ArrayList<Karta> getTaliaKart() {
        return taliaKart;
    }

    public void setTaliaKart(ArrayList<Karta> taliaKart) {
        this.taliaKart = taliaKart;
    }

    @Override
    public String toString() {
        return "TaliaKart{" +
                "taliaKart=" + taliaKart +
                '}';
    }
}
