
package Model;

public class Details {
    private int id;
    private int id_pro;
    private int amount;
    private double price;
    private int id_sale;
    
    public Details(){
        
    }

    public Details(int id, int id_pro, int amount, double price, int id_sale) {
        this.id = id;
        this.id_pro = id_pro;
        this.amount = amount;
        this.price = price;
        this.id_sale = id_sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId_sale() {
        return id_sale;
    }

    public void setId_sale(int id_sale) {
        this.id_sale = id_sale;
    }
}
