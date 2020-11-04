package klasy.wyjatki;

import javax.swing.*;
import java.util.ConcurrentModificationException;

public class KoniecGryException extends ConcurrentModificationException {

    public KoniecGryException() {

        JOptionPane.showMessageDialog(null, "Nie masz już żetonów!\n KONIEC GRY");

    }
}
