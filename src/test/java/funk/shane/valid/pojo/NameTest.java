package funk.shane.valid.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import funk.shane.valid.util.Utils;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
public class NameTest {
    @Autowired
    private LocalValidatorFactoryBean factoryBean;

    @Test
    void testHappyPath() {
        final Name testName = getTestName("name-1.json");
        assertNotNull(testName);
        log.info("testName: {}", testName);

        final Set<ConstraintViolation<Name>> violations = factoryBean.validate(testName);
        assertEquals(0, violations.size());
    }

    @Test
    void testBlankFirstName() {
        final Name badName = getTestName("name-1.json");
        badName.setFirstName("");

        final Set<ConstraintViolation<Name>> violations = factoryBean.validate(badName);
        assertEquals(1, violations.size());

        ConstraintViolation<Name> nameViolation = violations.iterator().next();
        assertEquals("First Name is required", nameViolation.getMessage());    
    }

    @Test
    void testNullFirstName() {
        final Name badName = getTestName("name-1.json");
        badName.setFirstName(null);

        final Set<ConstraintViolation<Name>> violations = factoryBean.validate(badName);
        assertEquals(1, violations.size());

        ConstraintViolation<Name> nameViolation = violations.iterator().next();
        assertEquals("First Name is required", nameViolation.getMessage());    
    }


    @Test
    void testBlankLastName() {
        final Name badName = getTestName("name-1.json");
        badName.setLastName("");

        final Set<ConstraintViolation<Name>> violations = factoryBean.validate(badName);
        assertEquals(1, violations.size());

        ConstraintViolation<Name> nameViolation = violations.iterator().next();
        assertEquals("Last Name is required", nameViolation.getMessage());    
    }

    @Test
    void testNullLastName() {
        final Name badName = getTestName("name-1.json");
        badName.setLastName(null);

        final Set<ConstraintViolation<Name>> violations = factoryBean.validate(badName);
        assertEquals(1, violations.size());

        ConstraintViolation<Name> nameViolation = violations.iterator().next();
        assertEquals("Last Name is required", nameViolation.getMessage());    
    }

    /* Generate a test object - change as needed */
    private static Name getTestName(final String jsonFile) {
        return Utils.getClassFromJsonResource(Name.class, jsonFile);
    }
}