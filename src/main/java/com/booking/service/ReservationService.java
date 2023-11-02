package com.booking.service;


import com.booking.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ReservationService {
    private static PrintService printService = new PrintService();
    private static Scanner scanner = new Scanner(System.in);

    public void createReservation(List<Person> allPersons, List<Service> services, List<Reservation> reservations) {
        Customer customer = null;
        Employee employee = null;
        String validationMessage = null;

        while (customer == null) {
            printService.showAllCustomer(allPersons);
            System.out.print("Silahkan Masukkan Customer Id: ");
            String customerId = scanner.nextLine();
            customer = getCustomerById(allPersons, customerId);

            validationMessage = ValidationService.validateInputCustomerId(customer);
            if (validationMessage != null) {
                System.out.println(validationMessage);
            }
        }

        while (employee == null) {
            printService.showAllEmployee(allPersons);
            System.out.print("Silahkan Masukkan Employee Id: ");
            String employeeId = scanner.nextLine();
            employee = getEmployeeById(allPersons, employeeId);

            validationMessage = ValidationService.validateInputEmployeeId(employee);
            if (validationMessage != null) {
                System.out.println(validationMessage);
            }
        }

        printService.showAllService(services);
        boolean addMoreServices = true;
        List<Service> selectedServices = new ArrayList<>();

        while (addMoreServices) {
            System.out.print("Silahkan Masukkan Service Id: ");
            String serviceId = scanner.nextLine();


            if (selectedServices.stream().anyMatch(service -> service.getServiceId().equals(serviceId))) {
                System.out.println("Service dengan ID yang sama sudah dipilih sebelumnya.");
            } else {
                Service selectedService = services.stream()
                        .filter(service -> service.getServiceId().equals(serviceId))
                        .findFirst()
                        .orElse(null);

                if (selectedService != null) {
                    selectedServices.add(selectedService);
                    System.out.print("Ingin pilih service yang lain (Y/T)? ");
                    String choice = scanner.nextLine();
                    addMoreServices = choice.equalsIgnoreCase("Y");
                } else {
                    System.out.println("Service dengan ID yang dimasukkan tidak ditemukan.");
                }
            }

        }
        double totalBookingPrice = services.stream().mapToDouble(Service::getPrice).sum();
        String reservationId = generateUniqueReservationId();


        String workstage = "In Process";
        Reservation reservation = new Reservation(reservationId, customer, employee, selectedServices, workstage);
        reservations.add(reservation);

        System.out.println("Booking Berhasil!");
        System.out.println("Total Biaya Booking: Rp." + totalBookingPrice);

    }

    private static Customer getCustomerById(List<Person> allPersons, String customerId) {
        return allPersons.stream()
                .filter(person -> person instanceof Customer && person.getId().equals(customerId))
                .map(Customer.class::cast)
                .findFirst()
                .orElse(null);
    }

    private static Employee getEmployeeById(List<Person> allPersons, String employeeId) {
        return allPersons.stream()
                .filter(person -> person instanceof Employee && person.getId().equals(employeeId))
                .map(Employee.class::cast)
                .findFirst()
                .orElse(null);
    }

    private static String generateUniqueReservationId() {
        UUID uuid = UUID.randomUUID();
        return "Res-" + uuid.toString().substring(0, 3);
    }

    public void editReservationWorkstage(List<Reservation> reservations) {
        printService.showRecentReservation(reservations);
        System.out.println("Silahkan Masukkan Reservation Id: ");
        String reservationId = scanner.nextLine();

        System.out.println("Selesaikan Reservasi ");
        String workstage = scanner.nextLine();


        Reservation reservation = reservations.stream()
                .filter(reservation1 -> reservation1.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
        if (reservation != null) {
            reservation.setWorkstage(workstage);
            System.out.println("Reservasi dengan id " + reservationId + " sudah " + workstage);
        } else {
            System.out.println("Reservation dengan ID " + reservationId + " tidak ditemukan.");
        }
    }


}
