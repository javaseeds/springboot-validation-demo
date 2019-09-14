package funk.shane.valid.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import funk.shane.valid.util.Utils;
import lombok.extern.slf4j.Slf4j;

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
        return Utils.getClassFromJsonResource(Phone.class, jsonFile);
    }
}