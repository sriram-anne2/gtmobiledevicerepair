package com.roadforge.gtmobiledevicerepair.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.roadforge.gtmobiledevicerepair.models.Customer;
import com.roadforge.gtmobiledevicerepair.models.Device;
import com.roadforge.gtmobiledevicerepair.models.Repair;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseOperations {

    public Customer createNewCustomer(Customer customer) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        if (customer.getCustomerId() == null) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }

        ApiFuture<WriteResult> savedResult = firestore.collection("customer")
                .document(customer.getCustomerId())
                .set(customer);

        return customer;
    }

    public Repair createNewRepair(Repair repair) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        if (repair.getRepairId() == null) {
            repair.setRepairId(UUID.randomUUID().toString());
        }

        ApiFuture<WriteResult> savedResult = firestore.collection("repair")
                .document(repair.getRepairId())
                .set(repair);

        return repair;
    }

    public Device createNewDevice(Device device) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        if (device.getDeviceId() == null) {
            device.setDeviceId(UUID.randomUUID().toString());
        }

        ApiFuture<WriteResult> savedResult = firestore.collection("device")
                .document(device.getDeviceId())
                .set(device);

        return device;
    }


}
