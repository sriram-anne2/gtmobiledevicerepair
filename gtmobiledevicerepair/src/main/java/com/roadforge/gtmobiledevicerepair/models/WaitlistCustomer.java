package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WaitlistCustomer {

    String customerId;
    String customerArrivalDateTime;
    String customerServicedAtDateTime;
}
