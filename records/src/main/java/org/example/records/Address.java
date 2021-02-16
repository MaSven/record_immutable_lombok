package org.example.records;

import java.util.Optional;

public record Address(String houseNumber, String zipCode, String name, Optional<String> phoneNumber, String street) {
    public record AddresOnlyForHarburg(Address address) {
        public AddresOnlyForHarburg{
            if(!"21073".equals(address.zipCode)){
                throw new IllegalArgumentException("""
                        The zipCode %s is not from harburg.
                        """.formatted(address.zipCode));
            }
        }
    }

}
