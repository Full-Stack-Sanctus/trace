package Model;

public class LoginModel {
    private int id;
    private String name;
    private String email;
    private String pass;
    private String rol;

    // Default constructor
    public LoginModel() {
    }

    // Parameterized constructor
    public LoginModel(int id, String name, String email, String pass, String rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for pass
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    // Getter and setter for rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
