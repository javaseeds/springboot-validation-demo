package funk.shane.valid.pojo;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class Address {
    @NotBlank(message = "Street Line 1 cannot be blank")
    private String streetLine1;

    private String streetLine2;
    
    private String streetLine3;
    
    @NotBlank(message = "City cannot be blank")
    private String city;
    
    @NotBlank(message = "State Code cannot be blank")
    @Size(min = 2, max = 2, message = "State Code can only be two (2) character long")
    private String stateCode;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal Code in US is 5 digit or 5+4 format")
    @NotBlank(message = "Postal Code cannot be blank")
    private String postalCode;
    
    private String country;

    enum AddressType {
        LEASED,
        OWNED
    }

    @FutureOrPresent(message = "End of lease cannot be in the past")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endOfLease;
    
    public Address() { /* empty constructor */ }

    /* So I know that Lombok has support for toString, I just prefer this style */
    @Override
    public String toString() {
     return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}