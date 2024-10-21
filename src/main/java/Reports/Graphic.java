
package Reports;

import Model.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Graphic {
    public static void Graph(String date){
        Connection con;
        Connect cn = new Connect();
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "SELECT total FROM sales WHERE date = ?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, date);
            rs = ps.executeQuery();
            DefaultPieDataset dateset = new DefaultPieDataset();
            while(rs.next()){
                dateset.setValue(rs.getString("total"), rs.getDouble("total"));
            }
            JFreeChart jf = ChartFactory.createPieChart("Reporte de Sale", dateset);
            ChartFrame f = new ChartFrame("Total de Sales por dia", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
