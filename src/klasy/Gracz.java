package klasy;

import klasy.karty.Karta;

public class Gracz {

    public  int numerGracza;
    private String nick;
    private int iloscZetonow;
    private Karta karta1;
    private Karta karta2;

    public Gracz(int numerGracza, String nick, int iloscZetonow, Karta karta1, Karta karta2) {
        this.numerGracza = numerGracza;
        this.nick = nick;
        this.iloscZetonow = iloscZetonow;
        this.karta1 = karta1;
        this.karta2 = karta2;
    }

    public Gracz() {
    }

    public int getNumerGracza (){return numerGracza;}

    public Karta getKarta1() {
        return karta1;
    }

    public void setKarta1(Karta karta1) {
        this.karta1 = karta1;
    }

    public Karta getKarta2() {
        return karta2;
    }

    public void setKarta2(Karta karta2) {
        this.karta2 = karta2;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getIloscZetonow() {
        return iloscZetonow;
    }

    public void setIloscZetonow(int iloscZetonow) {
        this.iloscZetonow = iloscZetonow;
    }


    @Override
    public String toString() {
        return "Gracz{" +
                "nick='" + nick + '\'' +
                ", iloscZetonow=" + iloscZetonow +
                ", karta1=" + karta1 +
                ", karta2=" + karta2 +
                '}';
    }
}
