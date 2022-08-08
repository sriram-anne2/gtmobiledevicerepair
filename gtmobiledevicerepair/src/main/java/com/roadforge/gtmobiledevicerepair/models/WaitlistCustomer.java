package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WaitlistCustomer extends Customer{

    String customerArrivalDateTime;
    String customerServicedAtDateTime;
    String technicianId;
}
