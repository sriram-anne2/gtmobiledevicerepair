package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Device {

    String deviceId;
    String deviceType;
    String operatingSystem;
    String customerId;
    ArrayList<String> repairIds;



}
