package klasy;

import java.sql.*;

public class BazaDanych {

    final String JDBC_DRIVER = "org.postgresql.Driver";
    final String URL = "jdbc:postgresql://localhost/PokerTexas";

    final String USER = "postgres";
    final String PASS = "postgres";

    Statement stmt;
    Connection conn;

    private Rozgrywka rozgrywka;


    public BazaDanych(Rozgrywka rozgrywka) throws SQLException, ClassNotFoundException {

        this.rozgrywka = rozgrywka;

        zapiszDane();

    }

    public void zapiszDane() throws SQLException, ClassNotFoundException {

        connectDb();

        String query1 = "SELECT COUNT(*) FROM rozgrywka";
        ResultSet rs1 = stmt.executeQuery(query1);
        rs1.next();
        int i = rs1.getInt("count");

        String sql = "INSERT INTO rozgrywka (id_rozgrywka, lineBaza)" +
                "VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, ++i);
        preparedStatement.setString(2, rozgrywka.getLineBaza());
        preparedStatement.executeUpdate();

        disconnectDB();

    }

    public void connectDb() throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(URL, USER, PASS);
        stmt = conn.createStatement();
    }

    public void disconnectDB() throws SQLException {
        stmt.close();
        conn.close();
    }
}
