package com.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import com.booking.models.*;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public static String printServices(List<Service> serviceList) {
        StringBuilder result = new StringBuilder();
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result.append(service.getServiceName()).append(", ");
        }
        return result.toString();
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-10s | %-10s | %-15s | %-25s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-10s | %-10s | %-15s | %-25s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(),
                        printServices(reservation.getServices()), reservation.getReservationPrice(),
                        reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public void showAllCustomer(List<Person> allPersons) {
        int num = 1;
        System.out.printf("| %-10s | %-10s | %-15s | %-15s | %-15s | %-15s |\n",
                "No.", "ID", "Nama Customer", "Alamat", "Membership", "Uang");
        System.out.println("+========================================================================================+");
        List<Customer> customers = allPersons.stream()
                .filter(Customer.class::isInstance)
                .map(Customer.class::cast)
                .collect(Collectors.toList());
        for (Customer customer : customers) {
            System.out.printf("| %-10s | %-10s | %-15s | %-15s | %-15s | %-15s |\n",
                    num, customer.getId(), customer.getName(),
                    customer.getAddress(),customer.getMember().getMembershipName(),customer.getWallet());
            num++;
        }
    }

    public void showAllEmployee(List<Person> allPersons) {
        int num = 1;
        System.out.printf("| %-10s | %-10s | %-11s | %-15s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+========================================================================================+");
        List<Employee> employees = allPersons.stream()
                .filter(Employee.class::isInstance)
                .map(Employee.class::cast)
                .collect(Collectors.toList());
        for (Employee employee : employees) {
            System.out.printf("| %-10s | %-10s | %-11s | %-15s | %-15s |\n",
                    num, employee.getId(),employee.getName(),employee.getAddress(),employee.getExperience());
            num++;
        }
    }
    public void showAllService(List<Service> allServices) {
        int num = 1;
        System.out.printf("| %-10s | %-10s | %-25s | %-15s |\n",
                "No.", "ID", "Nama ", "Harga");
        System.out.println("+========================================================================================+");

        for (Service service : allServices) {
            System.out.printf("| %-10s | %-10s | %-25s | %-15s |\n",
                    num, service.getServiceId(), service.getServiceName(), service.getPrice());
            num++;
        }
    }

    public void showHistoryReservation(List<Reservation> allReservation) {
        int num = 1;
        System.out.printf("| %-10s | %-10s | %-11s | %-15s | %-15s | %-15s |\n",
                "No.", "ID", "Nama ", "Service", "Total Biaya","Workstage");
        System.out.println("+========================================================================================+");
        List<Reservation> reservations = allReservation.stream()
                .filter(reservation -> reservation.getWorkstage().equals("Finish")||reservation.getWorkstage().equals("Canceled"))
                .map(Reservation.class::cast)
                .collect(Collectors.toList());
        for (Reservation reservation : reservations) {
            System.out.printf("| %-10s | %-10s | %-11s | %-15s | %-15s | %-15s |\n",
                    num, reservation.getReservationId(),reservation.getCustomer().getName(),printServices(reservation.getServices()),
                    reservation.getReservationPrice(),reservation.getWorkstage());
            num++;
        }
        double totalKeuntungan = reservations.stream().filter(reservation -> reservation.getWorkstage().equals("Finish"))
                .mapToDouble(Reservation::getReservationPrice).sum();
        System.out.println("+========================================================================================+");
        System.out.println("Total Keuntungan : Rp. "+totalKeuntungan);
    }

}
