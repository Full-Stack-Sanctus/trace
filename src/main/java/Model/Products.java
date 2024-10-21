
package Model;

public class Products {
    private int id;
    private String code;
    private String name;
    private int stock;
    private double price;
    private int duration;
    private byte[] photo;
    private String benefits;
    private String enpromo;
    private double pricepromo;
    private String startend;
    
    public Products(){ 
    }
    public Products(String benefits, String enpromo, double pricepromo, String startend) {
        this.benefits = benefits;
        this.enpromo = enpromo;
        this.pricepromo = pricepromo;
        this.startend = startend;
    }
    
    public Products(int id, String code, String name, int supplier, String supplierPro, int stock, double price, int duration, byte[] photo,String benefits, String enpromo, double pricepromo, String startend) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.duration = duration;
        this.photo = photo; 
        this.benefits = benefits;
        this.enpromo = enpromo;
        this.pricepromo = pricepromo;
        this.startend = startend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEnpromo() {
        return enpromo;
    }

    public void setEnpromo(String enpromo) {
        this.enpromo = enpromo;
    }

    public double getPricepromo() {
        return pricepromo;
    }

    public void setPricepromo(double pricepromo) {
        this.pricepromo = pricepromo;
    }

    public String getStartend() {
        return startend;
    }

    public void setStartend(String startend) {
        this.startend = startend;
    }
}