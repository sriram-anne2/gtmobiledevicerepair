package com.roadforge.gtmobiledevicerepair.controller;

import com.google.gson.Gson;
import com.roadforge.gtmobiledevicerepair.models.*;
import com.roadforge.gtmobiledevicerepair.services.FirebaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/repair")
public class RepairController {


    @Autowired
    FirebaseOperations firebaseOperations;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Repair createTestRepair() {

        Repair repair = new Repair();
        repair.setRepairId("randomRepairId1");
        repair.setCustomerId("randomCustId1");
        repair.setRepairStartDate("2022-08-01");
        repair.setRepairEndDate("2022-08-02");
        repair.setRepairCost((float) 39.99);

        Device device = new DeviceController().createTestDevice();

        repair.setDevice(device);

        return repair;
    }


    @GetMapping("")
    public ArrayList<Repair> getRepairs(@RequestParam(required = false) String repairId) throws ExecutionException, InterruptedException {

        if (repairId != null) {
            return firebaseOperations.getRepairsById(repairId);
        } else {
            return firebaseOperations.getRepairsById(null);
        }

    }

    @GetMapping("/customer")
    public ArrayList<Repair> getRepairsForCustomer(@RequestParam String customerId) throws ExecutionException, InterruptedException {

        return firebaseOperations.getRepairsByCustomerId(customerId);

    }

    @PostMapping("test")
    public Repair testCrud() throws ExecutionException, InterruptedException {
        return firebaseOperations.createNewRepair(createTestRepair());
    }


    @PostMapping("")
    public String addRepair(@RequestBody Repair repair) throws ExecutionException, InterruptedException {

        Repair repair1 = firebaseOperations.createNewRepair(repair);

        return new Gson().toJson(repair1);

    }

    @PostMapping("end")
    public Repair finishRepair(@RequestParam String repairId, @RequestParam (required = false) String technicianId) throws ExecutionException, InterruptedException {

        return firebaseOperations.finishRepair(repairId, dateFormat.format(new Date()), technicianId);
    }
}
