package org.example;

import org.immutables.value.Value;

import java.util.Optional;


public interface Address {

    String getHousenumber();

    String getZipCode();

    String getName();
    @Value.Redacted
    Optional<String> getPhoneNumber();

    String getStreet();


}
