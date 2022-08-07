package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Repair {

    String repairId;
    String deviceId;
    String customerId;
    String repairStartDate;
    String repairEndDate;
    String technicianId;

    float repairCost;


}
