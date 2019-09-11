package funk.shane.valid.pojo;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

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
public class Person {
    @NotNull(message = "Person requires a name")
    private Name name;

    @NotNull(message = "Person requires an address")
    private Address address;

    @NotNull(message = "Person requires a phone number")
    private Phone phone;

    @NotNull(message = "Person requires a birth date")
    @Past(message = "Person's birthday cannot be in present or the future")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    public Person() { /* empty constructor */ }

    /* So I know that Lombok has support for toString, I just prefer this style */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}