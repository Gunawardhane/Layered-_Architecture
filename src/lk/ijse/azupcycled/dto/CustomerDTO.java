package lk.ijse.azupcycled.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private String cusId;
    private String name;
    private String email;
    private String pn;
    private String address;

    public CustomerDTO() {
    }

    public CustomerDTO(String cusId, String name, String email, String pn, String address) {
        this.cusId = cusId;
        this.name = name;
        this.email = email;
        this.pn = pn;
        this.address = address;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cusId='" + cusId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pn='" + pn + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
