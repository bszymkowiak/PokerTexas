package klasy.karty;

import EnumKlasy.Kolor;
import EnumKlasy.Wartosc;

public class Karta {

    private Kolor kolor;
    private Wartosc wartosc;

    public Karta(Kolor kolor, Wartosc wartosc) {
        this.kolor = kolor;
        this.wartosc = wartosc;
    }

    public Kolor getKolor() {
        return kolor;
    }

    public Wartosc getWartosc() {
        return wartosc;
    }

    @Override
    public String toString() {
        return "Karta{" +
                "kolor=" + kolor +
                ", wartosc=" + wartosc +
                '}';
    }
}
