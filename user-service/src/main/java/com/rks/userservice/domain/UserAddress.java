package com.rks.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_addresses")
@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String addressType;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pin;
}
