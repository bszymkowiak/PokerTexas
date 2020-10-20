package EnumKlasy;

public enum Kolor {

    PIK(1),
    TREFL(2),
    KARO(3),
    KIER(4);

    private int hierarchia;

    Kolor(int hierarchia) {
        this.hierarchia = hierarchia;
    }

    public int getHierarchia() {
        return hierarchia;
    }
}
