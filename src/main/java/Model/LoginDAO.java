package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    private Connection con;
    private Connect cn = new Connect(); // Use the Connect class to get connections

    public LoginModel log(String email, String pass) {
        LoginModel l = new LoginModel();
        String sql = "SELECT * FROM users WHERE email = ? AND pass = ?";
        try {
            con = cn.getConnection(); // Use getConnection()
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, pass);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        l.setId(rs.getInt("id"));
                        l.setName(rs.getString("name"));
                        l.setEmail(rs.getString("email"));
                        l.setPass(rs.getString("pass"));
                        l.setRol(rs.getString("rol"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    public boolean Register(LoginModel reg) {
        String sql = "INSERT INTO users (name, email, pass, rol) VALUES (?, ?, ?, ?)";
        try {
            con = cn.getConnection(); // Use getConnection()
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, reg.getName());
                ps.setString(2, reg.getEmail());
                ps.setString(3, reg.getPass());
                ps.setString(4, reg.getRol());
                ps.executeUpdate(); // Use executeUpdate() for INSERT statements
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public List<LoginModel> ListUsers() {
        List<LoginModel> List = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            con = cn.getConnection(); // Use getConnection()
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LoginModel lg = new LoginModel();
                    lg.setId(rs.getInt("id"));
                    lg.setName(rs.getString("name"));
                    lg.setEmail(rs.getString("email"));
                    lg.setRol(rs.getString("rol"));
                    List.add(lg);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return List;
    }
}
