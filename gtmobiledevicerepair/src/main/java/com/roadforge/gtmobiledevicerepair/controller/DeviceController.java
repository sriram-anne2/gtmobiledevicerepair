package com.roadforge.gtmobiledevicerepair.controller;

import com.google.gson.Gson;
import com.roadforge.gtmobiledevicerepair.models.Device;
import com.roadforge.gtmobiledevicerepair.models.DeviceType;
import com.roadforge.gtmobiledevicerepair.models.OSType;
import com.roadforge.gtmobiledevicerepair.models.Repair;
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
    public String addDevice(@RequestBody Device device) throws ExecutionException, InterruptedException {

        Device device1 = firebaseOperations.createNewDevice(device);

        return new Gson().toJson(device1);

    }
}
