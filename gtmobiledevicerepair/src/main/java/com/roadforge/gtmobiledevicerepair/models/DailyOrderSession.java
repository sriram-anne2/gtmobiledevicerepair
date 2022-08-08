package com.roadforge.gtmobiledevicerepair.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class DailyOrderSession {

    String dailySessionId;
    String dailySessionOpenedBy;
    String dailySessionOpenDateTime;
    String dailySessionCloseDateTime;
    String dailySessionClosedBy;

    ArrayList<WaitlistCustomer> waitlistCustomers;
    ArrayList<Repair> repairs;

}
