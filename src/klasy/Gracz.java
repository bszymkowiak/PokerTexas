package klasy;

import klasy.karty.Karta;

import java.util.ArrayList;

public class Gracz {

    private String nick;
    private int iloscZetonow;
    protected ArrayList<Karta> kartyWRece = new ArrayList<>();
    protected int blind;
    int pulaZetonowGracza;

    private boolean czyRoyalFlush;
    private boolean czyStraightFlush;
    private boolean czyFourOfAKind;
    private boolean czyFullHouse;
    private boolean czyFlush;
    private boolean czyStraight;
    private boolean czyThreeOfAKind;
    private boolean czyTwoPair;
    private boolean czyOnePair;
    private boolean czyHighCard;

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

    public boolean isCzyRoyalFlush() {
        return czyRoyalFlush;
    }

    public void setCzyRoyalFlush(boolean czyRoyalFlush) {
        this.czyRoyalFlush = czyRoyalFlush;
    }

    public boolean isCzyStraightFlush() {
        return czyStraightFlush;
    }

    public void setCzyStraightFlush(boolean czyStraightFlush) {
        this.czyStraightFlush = czyStraightFlush;
    }

    public boolean isCzyFourOfAKind() {
        return czyFourOfAKind;
    }

    public void setCzyFourOfAKind(boolean czyFourOfAKind) {
        this.czyFourOfAKind = czyFourOfAKind;
    }

    public boolean isCzyFullHouse() {
        return czyFullHouse;
    }

    public void setCzyFullHouse(boolean czyFullHouse) {
        this.czyFullHouse = czyFullHouse;
    }

    public boolean isCzyFlush() {
        return czyFlush;
    }

    public void setCzyFlush(boolean czyFlush) {
        this.czyFlush = czyFlush;
    }

    public boolean isCzyStraight() {
        return czyStraight;
    }

    public void setCzyStraight(boolean czyStraight) {
        this.czyStraight = czyStraight;
    }

    public boolean isCzyThreeOfAKind() {
        return czyThreeOfAKind;
    }

    public void setCzyThreeOfAKind(boolean czyThreeOfAKind) {
        this.czyThreeOfAKind = czyThreeOfAKind;
    }

    public boolean isCzyTwoPair() {
        return czyTwoPair;
    }

    public void setCzyTwoPair(boolean czyTwoPair) {
        this.czyTwoPair = czyTwoPair;
    }

    public boolean isCzyOnePair() {
        return czyOnePair;
    }

    public void setCzyOnePair(boolean czyOnePair) {
        this.czyOnePair = czyOnePair;
    }

    public boolean isCzyHighCard() {
        return czyHighCard;
    }

    public void setCzyHighCard(boolean czyHighCard) {
        this.czyHighCard = czyHighCard;
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
