
package Model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

public class SaleDao {
    private Connection con;
    Connect cn = new Connect();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public int IdSale(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM sales";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    
    public int RegisterSale(Sale v){
        String sql = "INSERT INTO sales (customer, seller, total, date) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCustomer());
            ps.setString(2, v.getSeller());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getDate());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    public int RegisterDetails(Details Dv){
       String sql = "INSERT INTO details (id_pro, amount, price, id_sale) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getAmount());
            ps.setDouble(3, Dv.getPrice());
            ps.setInt(4, Dv.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    public boolean ActualizarStock(int cant, int id){
        String sql = "UPDATE products SET stock = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List Listsales(){
       List<Sale> ListSale = new ArrayList();
       String sql = "SELECT c.id AS id_cli, c.name, v.* FROM customers c INNER JOIN sales v ON c.id = v.customer";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Sale vent = new Sale();
               vent.setId(rs.getInt("id"));
               vent.setName_cli(rs.getString("name"));
               vent.setSeller(rs.getString("seller"));
               vent.setTotal(rs.getDouble("total"));
               ListSale.add(vent);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListSale;
   }
    public Sale LookUpSale(int id){
        Sale cl = new Sale();
        String sql = "SELECT * FROM sales WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCustomer(rs.getInt("customer"));
                cl.setTotal(rs.getDouble("total"));
                cl.setSeller(rs.getString("seller"));
                cl.setDate(rs.getString("date"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
    public void pdfV(int idsale, int Customer, double total, String user) {
        try {
            Date date = new Date();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + "sale.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance(getClass().getResource("/Img/logo_pdf.png"));
            //Date
            Paragraph par = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED);
            par.add(Chunk.NEWLINE);
            par.add("Seller: " + user + "\nFolio: " + idsale + "\nDate: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");
            PdfPTable Heading = new PdfPTable(4);
            Heading.setWidthPercentage(100);
            Heading.getDefaultCell().setBorder(0);
            float[] columnWidthsHeading = new float[]{20f, 30f, 70f, 40f};
            Heading.setWidths(columnWidthsHeading);
            Heading.setHorizontalAlignment(Element.ALIGN_LEFT);
            Heading.addCell(img);
            Heading.addCell("");
            //info empresa
            String config = "SELECT * FROM config";
            String message = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    message = rs.getString("message");
                    Heading.addCell("No.Suc. " + rs.getString("ruc") + "\nName: " + rs.getString("name") + "\nTeléfono: " + rs.getString("phone") + "\nDirección: " + rs.getString("direction") + "\n\n");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Heading.addCell(par);
            doc.add(Heading);
            //customer
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATA OF THE CUSTOMER" + "\n\n");
            doc.add(cli);

            PdfPTable supplier = new PdfPTable(3);
            supplier.setWidthPercentage(100);
            supplier.getDefaultCell().setBorder(0);
            float[] columnWidthsCustomer = new float[]{50f, 25f, 25f};
            supplier.setWidths(columnWidthsCustomer);
            supplier.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Name", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);
            supplier.addCell(cliNom);
            supplier.addCell(cliTel);
            supplier.addCell(cliDir);
            String prove = "SELECT * FROM customers WHERE id = ?";
            try {
                ps = con.prepareStatement(prove);
                ps.setInt(1, Customer);
                rs = ps.executeQuery();
                if (rs.next()) {
                    supplier.addCell(rs.getString("name"));
                    supplier.addCell(rs.getString("phone"));
                    supplier.addCell(rs.getString("direction") + "\n\n");
                } else {
                    supplier.addCell("Publico en General");
                    supplier.addCell("S/N");
                    supplier.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(supplier);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            String product = "SELECT d.id, d.id_pro,d.id_sale, d.price, d.amount, p.id, p.name FROM details d INNER JOIN products p ON d.id_pro = p.id WHERE d.id_sale = ?";
            try {
                ps = con.prepareStatement(product);
                ps.setInt(1, idsale);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("amount") * rs.getDouble("price");
                    tabla.addCell(rs.getString("amount"));
                    tabla.addCell(rs.getString("name"));
                    tabla.addCell(rs.getString("price"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total S/: " + total);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            Paragraph signature = new Paragraph();
            signature.add(Chunk.NEWLINE);
            signature.add("Cancelacion \n\n");
            signature.add("------------------------------------\n");
            signature.add("Signature \n");
            signature.setAlignment(Element.ALIGN_CENTER);
            doc.add(signature);
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(message);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }
}
