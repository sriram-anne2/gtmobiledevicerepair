package com.roadforge.gtmobiledevicerepair.services;

import com.google.api.Documentation;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roadforge.gtmobiledevicerepair.models.Customer;
import com.roadforge.gtmobiledevicerepair.models.Device;
import com.roadforge.gtmobiledevicerepair.models.Repair;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseOperations {

    // CUSTOMER OPERATIONS

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

    public ArrayList<Customer> getCustomersById(String customerId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ArrayList<Customer> customers = new ArrayList<>();

        ApiFuture<QuerySnapshot> retrieveCustomers;
        if (customerId == null) {
            retrieveCustomers = firestore.collection("customer").get();

        } else {
            retrieveCustomers = firestore.collection("customer").
                    whereEqualTo("customerId", customerId).get();
        }

        List<QueryDocumentSnapshot> results = retrieveCustomers.get().getDocuments();
        for (DocumentSnapshot snapshot: results) {
            customers.add(snapshot.toObject(Customer.class));
        }

        return customers;
    }

    public Customer updateCustomer(Customer customer) {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> savedResult = firestore.collection("customer").
                document(customer.getCustomerId()).
                set(customer);

        return customer;
    }

    // REPAIR OPERATIONS

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

    public ArrayList<Repair> getRepairsByCustomerId(String customerId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ArrayList<Repair> repairs = new ArrayList<>();

        ApiFuture<QuerySnapshot> retrieveRepairs = firestore.collection("repair").
                whereEqualTo("customerId", customerId).get();

        List<QueryDocumentSnapshot> results = retrieveRepairs.get().getDocuments();
        for (DocumentSnapshot snapshot: results) {
            repairs.add(snapshot.toObject(Repair.class));
        }

        return repairs;
    }

    public ArrayList<Repair> getRepairsById(String repairId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ArrayList<Repair> repairs = new ArrayList<>();

        ApiFuture<QuerySnapshot> retrieveRepairs;
        if (repairId == null) {
            retrieveRepairs = firestore.collection("repair").get();

        } else {
            retrieveRepairs = firestore.collection("repair").
                    whereEqualTo("repairId", repairId).get();
        }

        List<QueryDocumentSnapshot> results = retrieveRepairs.get().getDocuments();
        for (DocumentSnapshot snapshot: results) {
            repairs.add(snapshot.toObject(Repair.class));
        }

        return repairs;
    }


    // DEVICE OPERATIONS

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

    public ArrayList<Device> getDevicesByCustomerId(String customerId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ArrayList<Device> devices = new ArrayList<>();

        ApiFuture<QuerySnapshot> retrieveRepairs = firestore.collection("device").
                whereEqualTo("customerId", customerId).get();

        List<QueryDocumentSnapshot> results = retrieveRepairs.get().getDocuments();
        for (DocumentSnapshot snapshot: results) {
            devices.add(snapshot.toObject(Device.class));
        }

        return devices;
    }



}
