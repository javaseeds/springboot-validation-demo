/*
 *   Copyright (c) 2019 Shane Funk - Javaseeds Consulting
 *   All rights reserved.

 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

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