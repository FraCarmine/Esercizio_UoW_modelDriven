package example;
import java.sql.Connection;
import java.sql.SQLException; 
import java.util.List;

public interface StudentDao { 
	//aggiunte il lancio delle eccezioni cosi da segnalare al tm
    public List<Student> getAllStudents(Connection conn) throws SQLException;
    public Student getStudent(Connection conn, int rollNo) throws SQLException;
    public void updateStudent(Connection conn, Student student) throws SQLException;
    public void deleteStudent(Connection conn, Student student) throws SQLException;
    public void addStudent(Connection conn, Student student) throws SQLException;

    // NUOVI METODI 
    public void addCourseRegistration(Connection conn, int studentId, String courseId) throws SQLException;
    public void updateStatistics(Connection conn) throws SQLException;
}