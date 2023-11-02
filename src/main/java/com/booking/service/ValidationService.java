package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;


public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static String validateInputCustomerId(Customer customer) {
        if (customer == null) {
            return "Customer yang dicari tidak tersedia";
        }
        return null;
    }
    public static String validateInputEmployeeId(Employee employee) {
        if (employee == null) {
            return "Employee yang dicari tidak tersedia";
        }
        return null;
    }

}
