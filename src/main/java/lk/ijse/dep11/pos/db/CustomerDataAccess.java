package lk.ijse.dep11.pos.db;

import lk.ijse.dep11.pos.tm.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataAccess {
    private static final PreparedStatement STM_INSERT;
    private static final PreparedStatement STM_GET_ALL;

    static {
        try {
            Connection connection = SingleConnectionDataSource.getInstance().getConnection();

            STM_GET_ALL = connection
                    .prepareStatement("SELECT * FROM customer ORDER BY id");
            STM_INSERT = connection
                    .prepareStatement("INSERT INTO customer (id, name, address) VALUES (?, ?, ?)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Customer> getAllCustomers() throws SQLException {
        ResultSet rst = STM_GET_ALL.executeQuery();
        List<Customer> customerList = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString("id");
            String name = rst.getString("name");
            String address = rst.getString("address");
            customerList.add(new Customer(id, name, address));
        }
        return customerList;
    }
    public static void saveCustomer(Customer customer) throws SQLException {
        STM_INSERT.setString(1, customer.getId());
        STM_INSERT.setString(2, customer.getName());
        STM_INSERT.setString(3, customer.getAddress());
        STM_INSERT.executeUpdate();
    }

}
