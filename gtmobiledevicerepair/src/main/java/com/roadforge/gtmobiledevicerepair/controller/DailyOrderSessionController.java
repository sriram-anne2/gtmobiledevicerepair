package com.roadforge.gtmobiledevicerepair.controller;

import com.roadforge.gtmobiledevicerepair.models.Customer;
import com.roadforge.gtmobiledevicerepair.models.DailyOrderSession;
import com.roadforge.gtmobiledevicerepair.models.WaitlistCustomer;
import com.roadforge.gtmobiledevicerepair.services.FirebaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/session")
public class DailyOrderSessionController {

    @Autowired
    FirebaseOperations firebaseOperations;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/initialize")
    public ArrayList<String> initialize(@RequestParam String technicianId) throws ExecutionException, InterruptedException {

        DailyOrderSession dailyOrderSession = new DailyOrderSession();
        dailyOrderSession.setDailySessionOpenedBy(technicianId);
        dailyOrderSession.setActive(true);

        dailyOrderSession.setDailySessionOpenDateTime(dateFormat.format(new Date()));

        return firebaseOperations.createNewSession(dailyOrderSession);
    }


    @PostMapping("/addToWaitlist")
    public DailyOrderSession addCustomerToWaitlist(@RequestBody Customer customer) throws ExecutionException, InterruptedException {

        WaitlistCustomer waitlistCustomer = new WaitlistCustomer();

        waitlistCustomer.setCustomerArrivalDateTime(dateFormat.format(new Date()));

        return firebaseOperations.addCustomerToWaitlist(waitlistCustomer);
    }

    @GetMapping("")
    public ArrayList<String> getCustomerFromWaitlist(@RequestParam(required = false) String technicianId) throws ExecutionException, InterruptedException {

        return firebaseOperations.getCustomerFromWaitlist(technicianId);
    }



}
