package enumy;

public enum Wartosc{

    DWA(2),
    TRZY(3),
    CZTERY(4),
    PIEC(5),
    SZESC(6),
    SIEDEM(7),
    OSIEM(8),
    DZIEWIEC(9),
    DZIESIEC(10),
    JOPEK(11),
    DAMA(12),
    KROL(13),
    AS(14);

    private int wartosc;

    Wartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public int getWartosc() {
        return wartosc;
    }


}
