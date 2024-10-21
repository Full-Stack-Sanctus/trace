
package Model;

public class Sale {
    private int id;
    private int customer;
    private String name_cli;
    private String seller;
    private double total;
    private String date;
    
    public Sale(){
        
    }

    public Sale(int id, int customer, String name_cli, String seller, double total, String date) {
        this.id = id;
        this.customer = customer;
        this.name_cli = name_cli;
        this.seller = seller;
        this.total = total;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public String getName_cli() {
        return name_cli;
    }

    public void setName_cli(String name_cli) {
        this.name_cli = name_cli;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    
}
