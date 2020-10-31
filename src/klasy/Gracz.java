package klasy;

import enumy.Wartosc;
import klasy.karty.Karta;

import java.util.ArrayList;

public class Gracz {

    private String nick;
    private int iloscZetonow;
    protected ArrayList<Karta> kartyWRece = new ArrayList<>();
    private int wartoscKart;


    public Gracz() {
    }

    @Override
    public String toString() {
        return "Gracz{" +
                "nick='" + nick + '\'' +
                ", iloscZetonow=" + iloscZetonow +
                ", kartyWRece=" + kartyWRece +
                ", wartoscKart=" + wartoscKart +
                '}';
    }

    public Gracz(String nick, int iloscZetonow, int wartoscKart) {
        this.nick = nick;
        this.iloscZetonow = iloscZetonow;
        this.wartoscKart = wartoscKart;
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

    public int getWartoscKart(){
        return wartoscKart;
    }

    public void setWartoscKart(){
        this.wartoscKart = wartoscKart;
    }


}
