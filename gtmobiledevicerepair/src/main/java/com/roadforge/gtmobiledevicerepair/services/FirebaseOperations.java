package com.roadforge.gtmobiledevicerepair.services;

import com.google.api.Documentation;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.roadforge.gtmobiledevicerepair.models.*;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
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

        savedResult.get().getUpdateTime().toString();

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

    public Customer updateCustomer(Customer customer) throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> savedResult = firestore.collection("customer").
                document(customer.getCustomerId()).
                set(customer);

        savedResult.get().getUpdateTime().toString();

        return customer;
    }

    // REPAIR OPERATIONS

    public Repair createNewRepair(Repair repair) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        DailyOrderSession dailyOrderSession = getActiveSession();
        repair.setSessionId(dailyOrderSession.getDailySessionId());

        if (repair.getRepairId() == null) {
            repair.setRepairId(UUID.randomUUID().toString());
        }

        Device device = repair.getDevice();
        ArrayList<String> repairIds = ((device.getRepairIds() == null) ? new ArrayList<String >() : device.getRepairIds());
        repairIds.add(repair.getRepairId());
        device.setRepairIds(repairIds);

        updateDevice(device);


        ApiFuture<WriteResult> savedResult = firestore.collection("repair")
                .document(repair.getRepairId())
                .set(repair);

        savedResult.get().getUpdateTime().toString();

        ArrayList<Repair> existingRepairs = ((dailyOrderSession.getRepairs() == null) ? new ArrayList<Repair>() : dailyOrderSession.getRepairs());
        existingRepairs.add(repair);
        updateActiveSession(dailyOrderSession);


        Customer customer = getCustomersById(repair.getCustomerId()).get(0);
        ArrayList<Device> devices = customer.getDevices();
        for (int i = 0; i < devices.size(); i++) {
            if (Objects.equals(devices.get(i).getDeviceId(), device.getDeviceId())) {
                devices.remove(i);
                devices.add(device);
            }
        }
        customer.setDevices(devices);
        ArrayList<Repair> repairs = ((customer.getRepairs() == null) ? new ArrayList<Repair>() : customer.getRepairs());
        repairs.add(repair);
        customer.setRepairs(repairs);

        updateCustomer(customer);

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

        updateDevice(device);

        return device;
    }

    public Device updateDevice(Device device) throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> savedResult = firestore.collection("device")
                .document(device.getDeviceId())
                .set(device);

        savedResult.get().getUpdateTime().toString();

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


    // SESSION OPERATIONS

    public DailyOrderSession getActiveSession() throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> retrieveSession = firestore.collection("dailyOrderSession").
                whereEqualTo("active", true).get();

        List<QueryDocumentSnapshot> results = retrieveSession.get().getDocuments();

        return results.get(0).toObject(DailyOrderSession.class);
    }

    public String updateActiveSession(DailyOrderSession dailyOrderSession) throws ExecutionException, InterruptedException {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> savedResult = firestore.collection("dailyOrderSession")
                .document(dailyOrderSession.getDailySessionId())
                .set(dailyOrderSession);

        return savedResult.get().getUpdateTime().toString();
    }

    public ArrayList<String> createNewSession(DailyOrderSession dailyOrderSession) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        dailyOrderSession.setDailySessionId(UUID.randomUUID().toString());
        ApiFuture<WriteResult> savedResult = firestore.collection("dailyOrderSession")
                .document(dailyOrderSession.getDailySessionId())
                .set(dailyOrderSession);

        ArrayList<String> output = new ArrayList<>();
        output.add(dailyOrderSession.getDailySessionId());
        output.add(savedResult.get().getUpdateTime().toString());

        return output;
    }

    public DailyOrderSession closeOutSession(String technicianId, String closeOutDateTime) throws ExecutionException, InterruptedException {
        DailyOrderSession session = getActiveSession();
        session.setDailySessionClosedBy(technicianId);
        session.setDailySessionCloseDateTime(closeOutDateTime);
        session.setActive(false);

        updateActiveSession(session);

        return session;
    }

    public ArrayList<String> getCustomerFromWaitlist(String techId) throws ExecutionException, InterruptedException {

        DailyOrderSession dailyOrderSession = getActiveSession();
        ArrayList<WaitlistCustomer> existingWaitlist = dailyOrderSession.getWaitlistCustomers();

        WaitlistCustomer currentCustomer = existingWaitlist.remove(0);
        dailyOrderSession.setWaitlistCustomers(existingWaitlist);


        ArrayList<String> output = new ArrayList<>();
        output.add(currentCustomer.getCustomerId());
        output.add(updateActiveSession(dailyOrderSession));

        return output;

    }

    public DailyOrderSession addCustomerToWaitlist(WaitlistCustomer waitlistCustomer) throws ExecutionException, InterruptedException {

        DailyOrderSession dailyOrderSession = getActiveSession();
        ArrayList<WaitlistCustomer> existingWaitlist = dailyOrderSession.getWaitlistCustomers();
        existingWaitlist.add(waitlistCustomer);

        dailyOrderSession.setWaitlistCustomers(existingWaitlist);

        updateActiveSession(dailyOrderSession);
        return dailyOrderSession;
    }


}
