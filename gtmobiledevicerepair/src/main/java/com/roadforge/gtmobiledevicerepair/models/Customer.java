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
    ArrayList<String> deviceIds;
    ArrayList<String> repairIds;
}
