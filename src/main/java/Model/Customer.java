/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Customer {
    private int id;
    //private String dni;
    private String name;
    private String phone;
    private String direction;
    private String email;
    private String hobbies;
    private String discomfort;
    private String birthdays;

    public Customer() {
    }
    // String dni
    public Customer(int id, String name, String phone, String direction, String email, String hobbies, String discomfort, String birthdays) {
        this.id = id;
        //this.dni = dni;
        this.name = name;
        this.phone = phone;
        this.direction = direction;
        this.email = email;
        this.hobbies = hobbies;
        this.discomfort = discomfort;
        this.birthdays = birthdays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getDiscomfort() {
        return discomfort;
    }

    public void setDiscomfort(String discomfort) {
        this.discomfort = discomfort;
    }

    public String getBirthdays() {
        return birthdays;
    }

    public void setBirthdays(String birthdays) {
        this.birthdays = birthdays;
    }   
}

