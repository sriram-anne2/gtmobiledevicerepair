package com.roadforge.gtmobiledevicerepair.controller;

import com.google.gson.Gson;
import com.roadforge.gtmobiledevicerepair.models.*;
import com.roadforge.gtmobiledevicerepair.services.FirebaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/device")
public class DeviceController {


    @Autowired
    FirebaseOperations firebaseOperations;

    public Device createTestDevice() {

        Device device = new Device();
        device.setDeviceId(UUID.randomUUID().toString());
        device.setDeviceType(DeviceType.SMARTPHONE);
        device.setOperatingSystem(OSType.iOS);
        device.setCustomerId("randomCustId1");

        ArrayList<String> repairList = new ArrayList<>();
        repairList.add("randomRepairId1");

        device.setRepairIds(repairList);

        return device;
    }

    @GetMapping("")
    public ArrayList<Device> getDevices(@RequestParam(required = false) String deviceId) {

        if (deviceId != null) {

        } else {

        }

        return new ArrayList<Device>();
    }

    @PostMapping("test")
    public Device testCrud() throws ExecutionException, InterruptedException {
        return firebaseOperations.createNewDevice(createTestDevice());
    }


    @PostMapping("")
    public String addNewDeviceToCustomer(@RequestBody Device device) throws ExecutionException, InterruptedException {

        ArrayList<Device> existingDevices = firebaseOperations.getDevicesByCustomerId(device.getCustomerId());
        existingDevices.add(device);

        // always going to be a singleton result
        ArrayList<Customer> customers = firebaseOperations.getCustomersById(device.getCustomerId());

        Customer customer = customers.get(0);
        customer.setDevices(existingDevices);
        firebaseOperations.updateCustomer(customer);

        Device device1 = firebaseOperations.createNewDevice(device);

        return new Gson().toJson(device1);

    }
}
