package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
        this.getCustomer().setWallet(getCustomer().getWallet()-calculateReservationPrice());
    }

    private double calculateReservationPrice(){
        double totalPrice = 0;

        for (Service service : services) {
            totalPrice += service.getPrice();
        }
        if (customer != null && customer.getMember() != null) {
            String membership = customer.getMember().getMembershipName();

            if ("Silver".equalsIgnoreCase(membership)) {
                totalPrice *= 0.95;
            } else if ("Gold".equalsIgnoreCase(membership)) {
                totalPrice *= 0.90;
            }
            return totalPrice;
        }
        return totalPrice;
    }
}
