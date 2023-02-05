package lk.ijse.azupcycled.dao.custom.impl;

import lk.ijse.azupcycled.dao.SQLUtil;
import lk.ijse.azupcycled.dao.custom.CustomerDAO;
import lk.ijse.azupcycled.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("cusid"), rst.getString("name"), rst.getString("email"),rst.getString("phonenumber"),rst.getString("address"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer (cusid,name,email,phonenumber,address) VALUES (?,?,?,?,?)", entity.getCusId(), entity.getName(), entity.getEmail(),entity.getPn(),entity.getAddress());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name=?, email=?,phonenumber=?,address=? WHERE cusid=?", entity.getName(), entity.getEmail(), entity.getPn(),entity.getAddress());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT cusid FROM customer WHERE cusid=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM customer ORDER BY cusid DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("cusid");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cusid=?", id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE cusid=?", id + "");
        rst.next();
        return new Customer(id + "", rst.getString("name"), rst.getString("email"),rst.getString("phonenumber"),rst.getString("address"));
    }


}
