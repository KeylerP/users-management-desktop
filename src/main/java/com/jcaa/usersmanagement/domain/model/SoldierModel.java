package com.jcaa.usersmanagement.domain.model;

import lombok.Value;
import java.time.LocalDate;

@Value
public class SoldierModel {

    String id;
    String code;
    String identityDocument;
    String firstName;
    String secondName;
    String firstSurname;
    String secondSurname;
    LocalDate birthDate;
    String gender;
    double initialHeight;
    double initialWeight;
    String address;
    String phone;
    String email;
    String rank;

}