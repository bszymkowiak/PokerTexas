package klasy.stoper;

public class Stoper{


    private long start;

    private long stop;

    public Stoper() {
    }

    public void start() {

        start = System.currentTimeMillis();
    }

    public void stop() {

        stop = System.currentTimeMillis();
    }


    public double pobierzWynik() {

        return (stop - start) / 1000.0;
    }

}
