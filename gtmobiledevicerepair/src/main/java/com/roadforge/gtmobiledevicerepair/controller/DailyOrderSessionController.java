package com.roadforge.gtmobiledevicerepair.controller;

import com.roadforge.gtmobiledevicerepair.services.FirebaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/session")
public class DailyOrderSessionController {

    @Autowired
    FirebaseOperations firebaseOperations;



}
