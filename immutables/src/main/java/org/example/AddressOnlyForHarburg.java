package org.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(stagedBuilder = true)
@JsonSerialize(as = ImmutableAddressOnlyForHarburg.class)
@JsonDeserialize(as = ImmutableAddressOnlyForHarburg.class)
public abstract class AddressOnlyForHarburg implements Address{

    @Value.Check
    protected void check(){
        if(!"21073".equals(this.getZipCode())){
            throw new IllegalStateException("Only zipcodes from harburg are valid");
        }
    }
}
