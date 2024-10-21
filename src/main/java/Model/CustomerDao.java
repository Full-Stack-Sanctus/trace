/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomerDao {
    private Connection con;
    
    Connect cn = new Connect();
    
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegisterCustomer(Customer cl){
        String sql = "INSERT INTO customers (name, phone, direction, email, hobbies, discomfort, birthdays) VALUES (?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            //ps.setString(1, cl.getDni());
            ps.setString(1, cl.getName());
            ps.setString(2, cl.getPhone());
            ps.setString(3, cl.getDirection());
            ps.setString(4, cl.getEmail());
            ps.setString(5, cl.getHobbies());
            ps.setString(6, cl.getDiscomfort());
            ps.setString(7, cl.getBirthdays());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List ListCustomer(){
       List<Customer> ListCl = new ArrayList();
       String sql = "SELECT * FROM customers";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Customer cl = new Customer();
               cl.setId(rs.getInt("id"));
               //cl.setDni(rs.getString("dni"));
               cl.setName(rs.getString("name"));
               cl.setPhone(rs.getString("phone"));
               cl.setDirection(rs.getString("direction"));
               cl.setDirection(rs.getString("direction"));
               cl.setEmail(rs.getString("email"));
               cl.setHobbies(rs.getString("hobbies"));
               cl.setDiscomfort(rs.getString("discomfort"));
               cl.setBirthdays(rs.getString("birthdays"));
               ListCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListCl;
   }
   
   public boolean EliminateCustomer(int id){
       String sql = "DELETE FROM customers WHERE id = ?";
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
   
   public boolean ModifyCustomer(Customer cl){
       String sql = "UPDATE customers SET name=?, phone=?, direction=?, email=?, hobbies=?, discomfort=?, birthdays=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);   
           //ps.setString(1, cl.getDni());
           ps.setString(1, cl.getName());
           ps.setString(2, cl.getPhone());
           ps.setString(3, cl.getDirection());
           ps.setString(4, cl.getEmail());
           ps.setString(5, cl.getHobbies());
           ps.setString(6, cl.getDiscomfort());
           ps.setString(7, cl.getBirthdays());
           ps.setInt(8, cl.getId());
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
   
   public Customer LookUpcustomer(int id){
       Customer cl = new Customer();
       String sql = "SELECT * FROM customers WHERE id = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           rs = ps.executeQuery();
           if (rs.next()) {
               //cl.setId(rs.getInt("id"));
               cl.setName(rs.getString("name"));
               cl.setPhone(rs.getString("phone"));
               cl.setDirection(rs.getString("direction"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return cl;
   } 
}
