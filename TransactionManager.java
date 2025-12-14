package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionManager {
    private String url;
    private String user;
    private String password;

    public TransactionManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    @FunctionalInterface
    public interface SQLFunction<R> {
        R apply(Connection c) throws SQLException;
    }

    //contratto che accetta la funzione e qua gestisco solo connessione
    public <T> T inTransaction(SQLFunction<T> work) {
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            
            boolean old = c.getAutoCommit();
            c.setAutoCommit(false); 

            try {
                // Esegue il codice che passo (i 3 passaggi: studente, corso, stats)
                T result = work.apply(c); 
                
                c.commit(); 
                return result;
                
            } catch (Exception e) {
                // In caso di errore nel tuo codice, fa rollback
                try { c.rollback(); } catch (SQLException ignore) {}
                
                throw new RuntimeException("Errore durante la transazione", e);
            } finally {

                try { c.setAutoCommit(old); } catch (SQLException ignore) {}
            }//non chiudo la connessione tanto lo fa da solo per il try
            
        } catch (SQLException e) {
            // Questo catch intercetta errori di apertura connessione (URL errato, DB giù)
            throw new RuntimeException("Errore connessione DB: Impossibile aprire la Connection", e);
        }

    }
}