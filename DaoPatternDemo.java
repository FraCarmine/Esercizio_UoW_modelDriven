package example;

import java.util.ArrayList;
import java.util.List;

public class DaoPatternDemo {
	public static void main(String[] args) {
	      
	      String url = "jdbc:mariadb://localhost/student"; 
	      String user = "root";
	      String password = "";

	      
	      TransactionManager tm = new TransactionManager(url, user, password);
	      StudentDao dao = new StudentDaoImpl();
	      

	      StudentService service = new StudentService(tm, dao);

	      // 3. Esecuzione del Caso d'Uso
	      Student nuovoStudente = new Student("Marco", 105);
	      
	      System.out.println("--- Avvio Processo Iscrizione ---");
	      service.enrollNewStudent(nuovoStudente);
	   }
}
