package example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

	//niente gestione della connessione 

    @Override
    public void addStudent(Connection conn, Student student) throws SQLException {
 
        String sql = "INSERT INTO studenti(nome, matricola) VALUES (?, ?)";
        try (PreparedStatement insertState = conn.prepareStatement(sql)) {
            insertState.setString(1, student.getName());
            insertState.setInt(2, student.getRollNo());
            insertState.executeUpdate();
        }
    }

    @Override
    public void addCourseRegistration(Connection conn, int studentId, String courseId) throws SQLException {
        String sql = "INSERT INTO iscrizioni(student_id, corso_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, courseId);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateStatistics(Connection conn) throws SQLException {
        String sql = "UPDATE statistiche SET numero_studenti = numero_studenti + 1";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    @Override
    public void updateStudent(Connection conn, Student student) throws SQLException {
        String sql = "UPDATE studenti Set nome=? where matricola = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, student.getName());
            st.setInt(2, student.getRollNo());
            st.executeUpdate();
        }
    }

    @Override
    public void deleteStudent(Connection conn, Student student) throws SQLException {
        String sql = "DELETE FROM studenti where matricola = ?";
        try (PreparedStatement del = conn.prepareStatement(sql)) {
            del.setInt(1, student.getRollNo());
            del.executeUpdate();
        }
    }

    @Override
    public Student getStudent(Connection conn, int rollNo) throws SQLException {
        String sql = "SELECT * FROM studenti where matricola=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, rollNo);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getString("nome"), rs.getInt("matricola"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents(Connection conn) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM studenti";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(rs.getString("nome"), rs.getInt("matricola")));
            }
        }
        return list;
    }
}