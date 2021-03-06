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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import funk.shane.valid.util.Utils;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PhoneTest {

    @Autowired
    private LocalValidatorFactoryBean factoryBean;

    /**
     * The first test of the validation is that all validations pass.  Here, all the Phone class 
     * required validation pass.  Subsequent tests will go outside the boundaries of the Validation
     */
    @Test
    void testHappyPath() {
        final Phone testPhone = getTestPhone("phone-1.json");
        assertNotNull(testPhone);

        log.info("testPhone: {}", testPhone);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(testPhone);
        assertEquals(0, violations.size());
    }

    @Test
    void testCountryCodeInvalid() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setCountryCode(-1);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Country Code must be non-negative value", phoneViolation.getMessage());
    }

    @Test
    void testAreaCodeTooSmall() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setAreaCode(200);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Area Code must be greater than 200", phoneViolation.getMessage());
    }

    @Test
    void testAreaCodeTooLarge() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setAreaCode(1000);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Area Code must be less than 3 digits", phoneViolation.getMessage());
    }

    @Test
    void testPrefixTooSmall() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setPrefix(100);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Prefix must be greater than 100", phoneViolation.getMessage());
    }

    @Test
    void testPrefixTooLarge() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setPrefix(1000);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Prefix must be less than 4 digits", phoneViolation.getMessage());
    }

    @Test
    void testSuffixTooSmall() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setSuffix(-1);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Suffix must be a positive number", phoneViolation.getMessage());
    }

    @Test
    void testSuffixTooLarge() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setSuffix(10000);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(1, violations.size());

        ConstraintViolation<Phone> phoneViolation = violations.iterator().next();
        assertEquals("Suffix must be less than 5 digits", phoneViolation.getMessage());
    }

    @Test
    void testBunchesOfValidations() {
        final Phone badPhone = getTestPhone("phone-1.json");
        badPhone.setCountryCode(-1);
        badPhone.setAreaCode(1);
        badPhone.setPrefix(99999);
        badPhone.setSuffix(99999);

        final Set<ConstraintViolation<Phone>> violations = factoryBean.validate(badPhone);
        assertEquals(4, violations.size());
    }

    /* Generate a test object - change as needed */
    private static Phone getTestPhone(final String jsonFile) {
        return Utils.getClassFromJsonFile(Phone.class, jsonFile);
    }
}