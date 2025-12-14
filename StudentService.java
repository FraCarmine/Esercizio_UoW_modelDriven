package example;

//definisco i casi d'uso in questo caso quello che di

public class StudentService {
	private TransactionManager tm;
    private StudentDao studentDao;
    
    public StudentService(TransactionManager tm, StudentDao studentDao) { //costruttore
        this.tm = tm;
        this.studentDao = studentDao;
    }
    
    //caso d'uso 
    public void enrollNewStudent(Student s) {
    	try {
    		//lambda e gestisco solo la funzione inel logica
            tm.inTransaction(conn -> {
                // 1. Creare lo studente
                System.out.println("Step 1: Creazione studente " + s.getName());
                studentDao.addStudent(conn, s);

                // 2. Creare la registrazione al corso obbligatorio
                System.out.println("Step 2: Iscrizione corso obbligatorio");
                studentDao.addCourseRegistration(conn, s.getRollNo(), "CORSO-BASE-01");

                // 3. Aggiornare statistiche
                System.out.println("Step 3: Aggiornamento statistiche");
                studentDao.updateStatistics(conn);
                System.out.println("Transazione completata con successo!");
                return null; // Tutto OK
        
            });
    	} catch (RuntimeException e) {
            System.err.println("ERRORE: Iscrizione fallita. Rollback eseguito.");
            System.err.println("Dettaglio: " + e.getMessage());
        }
    }
		
}
