package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.example.records.Address;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;

class AddressTest {


    @Test
    void testInit(){
        Address address = new Address("test","test","test", Optional.of("test"),"test");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(address.zipCode()).isEqualTo("test");
            softAssertions.assertThat(address.name()).isEqualTo("test");
            softAssertions.assertThat(address.phoneNumber()).isNotEmpty();
            softAssertions.assertThat(address.houseNumber()).isEqualTo("test");
        });
    }

    @Nested
    public class Serialization{

        public static final String TEST_JSON = """
                {
                    "zipCode": "test",
                    "name": "test",
                    "street": "test",
                    "houseNumber": "test",
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
        void serialize() throws JsonProcessingException, JSONException {
            String json = objectMapper.writeValueAsString(new Address("test","test","test",Optional.empty(),"test"));
            JSONAssert.assertEquals(TEST_JSON,json,false);
        }
        @Test
        void deserialize() throws JsonProcessingException {
            Address expected = new Address("test","test","test",Optional.empty(),"test");
            Address address = objectMapper.readValue(TEST_JSON,Address.class);
            Assertions.assertThat(address).isEqualTo(expected);
        }



    }
    @Nested
    public class AddressFromHarburg{
        @Test
        public void valid(){
            assertThatNoException().isThrownBy(()-> new Address.AddresOnlyForHarburg(new Address("test","21073","test",Optional.empty(),"test")));
            Assertions.assertThatCode(()->  new Address.AddresOnlyForHarburg(new Address("test","21073","test",Optional.empty(),"test"))).doesNotThrowAnyException();

        }
        @Test
        public void invalid(){
            Assertions.assertThatCode(()-> new Address.AddresOnlyForHarburg(new Address("test","test","test",Optional.empty(),"test"))).isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("""
                            The zipCode %s is not from harburg.
                            ""","test");
        }

    }



}