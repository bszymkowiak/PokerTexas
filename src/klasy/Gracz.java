package klasy;

import klasy.karty.Karta;
import klasy.karty.TaliaKart;

import java.util.ArrayList;

public class Gracz {

    private String nick;
    private int iloscZetonow;

    protected ArrayList<Karta> kartyWRece = new ArrayList<>();

    public Gracz() {
    }

    public Gracz(String nick, int iloscZetonow) {
        this.nick = nick;
        this.iloscZetonow = iloscZetonow;
    }

    public ArrayList<Karta> getKartyWRece() {
        return kartyWRece;
    }

    public void setKartyWRece(ArrayList<Karta> kartyWRece) {
        this.kartyWRece = kartyWRece;
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
                ", kartyWRece=" + kartyWRece +
                '}';
    }
}
