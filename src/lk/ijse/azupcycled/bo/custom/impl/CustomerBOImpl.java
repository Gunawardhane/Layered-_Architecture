package lk.ijse.azupcycled.bo.custom.impl;

import lk.ijse.azupcycled.bo.custom.CustomerBO;
import lk.ijse.azupcycled.dao.DAOFactory;
import lk.ijse.azupcycled.dao.custom.CustomerDAO;
import lk.ijse.azupcycled.dto.CustomerDTO;
import lk.ijse.azupcycled.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();

        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCusId(),c.getName(),c.getEmail(),c.getPn(),c.getAddress()));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getCusId(), dto.getName(),dto.getEmail(),dto.getPn(),dto.getAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer((dto.getCusId()),dto.getName(),dto.getEmail(),dto.getPn(),dto.getAddress()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
