import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

    public List<Escuela> listarEscuelas() throws SQLException {
        String sql = "SELECT id, nombre FROM escuelas ORDER BY nombre";
        List<Escuela> lista = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Escuela(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }

    public List<Carrera> listarPorEscuela(int idEscuela) throws SQLException {
        String sql = "SELECT id, nombre, grado FROM carreras WHERE id_escuela = ? ORDER BY nombre";
        List<Carrera> lista = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idEscuela);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Carrera(rs.getInt("id"), rs.getString("nombre"), rs.getString("grado")));
                }
            }
        }
        return lista;
    }
}