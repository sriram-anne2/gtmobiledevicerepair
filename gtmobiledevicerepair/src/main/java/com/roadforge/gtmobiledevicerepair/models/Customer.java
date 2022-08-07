package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    String customerId;
    String customerName;
    String customerEmail;
    String customerPhone;
    ArrayList<Device> devices;
    ArrayList<Repair> repairs;
}
