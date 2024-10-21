
package Model;

public class Supplier {
    private int id;
    private String ruc;
    private String name;
    private String phone;
    private String direction;
    
    public Supplier(){
        
    }

    public Supplier(int id, String ruc, String name, String phone, String direction) {
        this.id = id;
        this.ruc = ruc;
        this.name = name;
        this.phone = phone;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
}
