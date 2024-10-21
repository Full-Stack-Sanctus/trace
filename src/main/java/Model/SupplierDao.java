
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao {
    private Connection con;
    Connect cn = new Connect();
    PreparedStatement ps;
    ResultSet rs;
    public boolean RegisterSupplier(Supplier pr){
        String sql = "INSERT INTO supplier(ruc, name, phone, direction) VALUES (?,?,?,?)";
        try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, pr.getRuc());
           ps.setString(2, pr.getName());
           ps.setString(3, pr.getPhone());
           ps.setString(4, pr.getDirection());
           ps.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List ListSupplier(){
        List<Supplier> Listpr = new ArrayList();
        String sql = "SELECT * FROM supplier";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Supplier pr = new Supplier();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getString("ruc"));
                pr.setName(rs.getString("name"));
                pr.setPhone(rs.getString("phone"));
                pr.setDirection(rs.getString("direction"));
                Listpr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listpr;
    }
    
    public boolean EliminateSupplier(int id){
        String sql = "DELETE FROM supplier WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean ModifySupplier(Supplier pr){
        String sql = "UPDATE supplier SET ruc=?, name=?, phone=?, direction=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getRuc());
            ps.setString(2, pr.getName());
            ps.setString(3, pr.getPhone());
            ps.setString(4, pr.getDirection());
            ps.setInt(5, pr.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
