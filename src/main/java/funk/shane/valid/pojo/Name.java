package funk.shane.valid.pojo;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class Name {
    @NotBlank(message = "First Name is required")    
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    private String prefix;
    private String suffix;
    
    public Name() { /* Empty Constructor */ }

    /* So I know that Lombok has support for toString, I just prefer this style */
    @Override
    public String toString() {
     return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}