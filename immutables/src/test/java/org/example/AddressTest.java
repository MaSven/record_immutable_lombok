package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Optional;

class AddressTest {



    @Test
    void testInitiate(){
        DeliveryAddress deliveryAddress = ImmutableDeliveryAddress.builder().deliveryNumber("test").phoneNumber("test").housenumber("test").name("test").street("test").zipCode("test").build();

        DeliveryAddressAssert
                .assertThat(deliveryAddress)
                .hasHousenumber("test")
                .hasDeliveryNumber("test")
                .hasName("test")
                .hasStreet("test")
                .hasZipCode("test")
                .hasPhoneNumber(Optional.of("test"));
    }

    @Test()
    void strictBuilder(){
        Assertions.assertThrows(IllegalStateException.class,() -> ImmutableDeliveryAddress.builder().deliveryNumber("test").phoneNumber("test").housenumber("test").build());
    }
    @Test
    void optionalValues(){
        Assertions.assertDoesNotThrow(() -> ImmutableDeliveryAddress.builder().deliveryNumber("test").zipCode("test").housenumber("test").name("test").street("test").build());
    }
    @Nested
    public class Serialization{
        public static final String TEST_JSON = """
                {
                    "deliveryNumber": "test",
                    "zipCode": "test",
                    "housenumber": "test",
                    "name": "test",
                    "street": "test",
                    "phoneNumber": null
                }
                """;
        private ObjectMapper objectMapper;

        @BeforeEach
        public void setup(){
            this.objectMapper = new ObjectMapper();
            this.objectMapper.registerModule(new Jdk8Module());
        }

        @Test
        void serialization() throws JsonProcessingException, JSONException {
            String json = objectMapper.writeValueAsString(ImmutableDeliveryAddress.builder().deliveryNumber("test").zipCode("test").housenumber("test").name("test").street("test").build());
            JSONAssert.assertEquals(TEST_JSON,json,true);
        }
        @Test
        void deserialization() throws JsonProcessingException {
            String json =TEST_JSON;
            DeliveryAddress deliveryAddress = this.objectMapper.readValue(TEST_JSON,DeliveryAddress.class);
            DeliveryAddressAssert.assertThat(deliveryAddress)
                    .hasHousenumber("test")
                    .hasPhoneNumber(Optional.empty())
                    .hasStreet("test")
                    .hasZipCode("test")
                    .hasName("test");
        }

    }
    @Nested
    class Preconditions {
        @Test
        void validAddress(){
            Assertions.assertDoesNotThrow(()->
             ImmutableAddressOnlyForHarburg.builder().housenumber("test")
                    .zipCode("21073")
                    .namge("test")
                    .street("test")
                    .build(),"Does not throw because zipCode is correct");
        }
        @Test
        void invalidAddresss(){
            Assertions.assertThrows(IllegalStateException.class,() ->              ImmutableAddressOnlyForHarburg.builder().housenumber("test")
                    .zipCode("22568")
                    .name("test")
                    .street("test")
                    .build());
        }
    }

}