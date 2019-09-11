package funk.shane.valid.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @EqualsAndHashCode
@Slf4j
public class Phone {
    private static final String US_FORMAT = "+%d (%03d) %03d-%04d %s";

    @Min(value = 1, message = "Country Code must be non-negative value")
    private int countryCode;

    @Min(value = 201, message = "Area Code must be greater than 200")
    @Max(value = 999, message = "Area Code must be less than 3 digits")
    private int areaCode;

    @Min(value = 101, message = "Prefix must be greater than 100")
    @Max(value = 999, message = "Prefix must be less than 4 digits")
    private int prefix;

    @Min(value = 0, message = "Suffix must be a positive number")
    @Max(value = 9999, message = "Suffix must be less than 5 digits")
    private int suffix;

    private int extension;

    /* Note: in practice, this format would be in a Locale supported way, but for this demo app, 
       just going to use USA for now */
    private String usformatted;

    public Phone() { /* empty constructor */ }

    public String generateFormatted(@NotBlank(message = "Phone cannot be blank") 
                                    final Phone phone) {
        log.debug("Phone object: {}", phone);                                
        return String.format(US_FORMAT, phone.getCountryCode(), phone.getAreaCode(), phone.getPrefix(), 
          phone.getSuffix(), buildExtension(phone.getExtension()));
    }

    private static String buildExtension(final Integer extension) {
        return extension == null ? "" : "x" + extension;
    }    

    /* So I know that Lombok has support for toString, I just prefer this style */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}