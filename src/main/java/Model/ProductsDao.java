
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductsDao {
    private Connection con;
    Connect cn = new Connect();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegisterProducts(Products pro){
        String sql = "INSERT INTO products (code, name, stock, price, duration, photo, benefits, enpromo, pricepromo, startend) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCode());
            ps.setString(2, pro.getName());
            //ps.setInt(3, pro.getSupplier());
            ps.setInt(3, pro.getStock());
            ps.setDouble(4, pro.getPrice());
            ps.setInt(5, pro.getDuration());
            ps.setBytes(6, pro.getPhoto());
            ps.setString(7, pro.getBenefits());
            ps.setString(8, pro.getEnpromo());
            ps.setDouble(9, pro.getPricepromo());
            ps.setString(10, pro.getStartend());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            //System.out.println(e.toString());
            return false;
        } finally{
            try{
                con.close();
            } catch(SQLException e){
                System.out.println(e.toString());
            }  
        }
    }
    
    public List ListProducts(){
       List<Products> Listpro = new ArrayList();
       String sql = "SELECT * FROM products";
       //String sql = "SELECT pr.id AS id_supplier, pr.name AS name_supplier, p.* FROM supplier pr INNER JOIN products p ON pr.id = p.supplier ORDER BY p.id DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Products pro = new Products();
               pro.setId(rs.getInt("id"));
               pro.setCode(rs.getString("code"));
               pro.setName(rs.getString("name"));
               //pro.setSupplier(rs.getInt("id_supplier"));
               //pro.setSupplierPro(rs.getString("name_supplier"));
               pro.setStock(rs.getInt("stock"));
               pro.setPrice(rs.getDouble("price"));
               pro.setDuration(rs.getInt("duration"));
               pro.setPhoto(rs.getBytes("photo"));
               pro.setBenefits(rs.getString("benefits"));
               pro.setEnpromo(rs.getString("enpromo"));
               pro.setPricepromo(rs.getDouble("pricepromo"));
               pro.setStartend(rs.getString("startend"));
               Listpro.add(pro);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Listpro;
   }
    
    public boolean EliminateProducts(int id){
       String sql = "DELETE FROM products WHERE id = ?";
       try {
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
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
    
    public boolean ModifyProductsImagen(Products pro){
       String sql = "UPDATE products SET code=?, name=?, stock=?, price=?, duration=?, photo=?, benefits=?, enpromo=?, pricepromo=?, startend=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, pro.getCode());
           ps.setString(2, pro.getName());
           //ps.setInt(3, pro.getSupplier());
           ps.setInt(3, pro.getStock());
           ps.setDouble(4, pro.getPrice());
           ps.setInt(5, pro.getDuration());
           ps.setBytes(6, pro.getPhoto());
           ps.setString(7, pro.getBenefits());
           ps.setString(8, pro.getEnpromo());
           ps.setDouble(9, pro.getPricepromo());
           ps.setString(10, pro.getStartend());
           ps.setInt(11, pro.getId());
           ps.executeUpdate();
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
    
    public boolean ModifyProducts(Products pro){
       String sql = "UPDATE products SET code=?, name=?, stock=?, price=?, duration=?, benefits=?, enpromo=?, pricepromo=?, startend=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, pro.getCode());
           ps.setString(2, pro.getName());
           //ps.setInt(3, pro.getSupplier());
           ps.setInt(3, pro.getStock());
           ps.setDouble(4, pro.getPrice());
           ps.setInt(5, pro.getDuration());
           ps.setString(6, pro.getBenefits());
           ps.setString(7, pro.getEnpromo());
           ps.setDouble(8, pro.getPricepromo());
           ps.setString(9, pro.getStartend());
           ps.setInt(10, pro.getId());
           ps.executeUpdate();
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
    
    public Products LookUpPro(String cod){
        Products producto = new Products();
        String sql = "SELECT * FROM products WHERE code = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getInt("id"));
                producto.setName(rs.getString("name"));
                producto.setPrice(rs.getDouble("price"));
                producto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    public Products LookUpId(int id){
        Products pro = new Products();
        String sql = "SELECT * FROM products WHERE id = ?";
        //String sql = "SELECT pr.id AS id_supplier, pr.name AS name_supplier, p.* FROM supplier pr INNER JOIN products p ON p.supplier = pr.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                //pro.setId(rs.getInt("id"));
                pro.setCode(rs.getString("code"));
                pro.setName(rs.getString("name"));
                //pro.setSupplier(rs.getInt("supplier"));
                //pro.setSupplierPro(rs.getString("name_supplier"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pro;
    }
    
    //This is the jCombobox, this motherfucker must be eliminated later haha
    public Supplier LookUpSupplier(String name){
        Supplier pr = new Supplier();
        String sql = "SELECT * FROM supplier WHERE name = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }
    
    
    public Config LookUpData(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setName(rs.getString("name"));
                conf.setPhone(rs.getString("phone"));
                conf.setDirection(rs.getString("direction"));
                conf.setMessage(rs.getString("message"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModifyData(Config conf){
       String sql = "UPDATE config SET ruc=?, name=?, phone=?, direction=?, message=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, conf.getRuc());
           ps.setString(2, conf.getName());
           ps.setString(3, conf.getPhone());
           ps.setString(4, conf.getDirection());
           ps.setString(5, conf.getMessage());
           ps.setInt(6, conf.getId());
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
