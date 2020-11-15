package klasy;

import klasy.karty.Karta;

import java.util.ArrayList;

public class Gracz {

    private String nick;
    private int iloscZetonow;
    protected ArrayList<Karta> kartyWRece = new ArrayList<>();
    protected int blind;
    int pulaZetonowGracza;

    public Gracz() {
    }

    public Gracz(String nick, int iloscZetonow) {
        this.nick = nick;
        this.iloscZetonow = iloscZetonow;
    }

    public int getPulaZetonowGracza() {
        return pulaZetonowGracza;
    }

    public void setPulaZetonowGracza(int pulaZetonowGracza) {
        this.pulaZetonowGracza = pulaZetonowGracza;
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

    public int getBlind() {
        return blind;
    }

    public void setBlind(int blind) {
        this.blind = blind;
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
