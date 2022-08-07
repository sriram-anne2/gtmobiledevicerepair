package com.roadforge.gtmobiledevicerepair.controller;

import com.google.gson.Gson;
import com.roadforge.gtmobiledevicerepair.models.Customer;
import com.roadforge.gtmobiledevicerepair.models.Device;
import com.roadforge.gtmobiledevicerepair.models.Repair;
import com.roadforge.gtmobiledevicerepair.services.FirebaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    FirebaseOperations firebaseOperations;

    public Customer createTestCustomer() {

        Customer customer = new Customer();
        customer.setCustomerId("randomCustId1");
        customer.setCustomerEmail("customer@gmail.com");
        customer.setCustomerName("John Doe");
        customer.setCustomerPhone("8974563210");

        Repair repair = new RepairController().createTestRepair();
        ArrayList<Repair> repairList = new ArrayList<>();
        repairList.add(repair);
        customer.setRepairs(repairList);

        Device device = new DeviceController().createTestDevice();
        ArrayList<Device> deviceList = new ArrayList<>();
        deviceList.add(device);
        customer.setDevices(deviceList);

        return customer;

    }

    @GetMapping("")
    public ArrayList<Customer> getCustomers(@RequestParam(required = false) String customerId) {

        if (customerId != null) {

        } else {

        }

        return new ArrayList<Customer>();
    }

    @PostMapping("test")
    public Customer testCrud() throws ExecutionException, InterruptedException {

        return firebaseOperations.createNewCustomer(createTestCustomer());
    }


    @PostMapping("")
    public String addCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException {

        Customer customer1 = firebaseOperations.createNewCustomer(customer);

        return new Gson().toJson(customer1);

    }
}
