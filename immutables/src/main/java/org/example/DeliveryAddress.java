package org.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDeliveryAddress.class)
@JsonDeserialize(as= ImmutableDeliveryAddress.class)
public interface DeliveryAddress extends Address{

    String getDeliveryNumber();
}
