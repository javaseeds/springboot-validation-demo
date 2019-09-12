package funk.shane.valid.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;

import funk.shane.valid.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonTest {


    @Test
    void testHappyPath() {
        final Person testPerson = getTestPerson("person-1.json");
        assertNotNull(testPerson);
        log.info("testPerson: {}", testPerson);

        final Set<ConstraintViolation<Person>> violations = validator.validate(testPerson);
        assertEquals(0, violations.size());
    }

    @Test
    void testMissingName() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setName(null);

        final Set<ConstraintViolation<Person>> violations = validator.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> personViolation = violations.iterator().next();
        assertEquals("Person requires a name", personViolation.getMessage());    
    }

    @Test
    void testMissingAddress() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setAddress(null);

        final Set<ConstraintViolation<Person>> violations = validator.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> personViolation = violations.iterator().next();
        assertEquals("Person requires an address", personViolation.getMessage());    
    }

    @Test
    void testMissingPhone() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setPhone(null);

        final Set<ConstraintViolation<Person>> violations = validator.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person requires a phone number", nameViolation.getMessage());    
    }

    @Test
    void testMissingBirthDate() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setBirthDate(null);

        final Set<ConstraintViolation<Person>> violations = validator.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person requires a birth date", nameViolation.getMessage());    
    }

    @Test
    void testBirthDateIsInFuture() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setBirthDate(LocalDate.now().plusYears(10));

        final Set<ConstraintViolation<Person>> violations = validator.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person's birthday cannot be in present or the future", nameViolation.getMessage());    
    }

    /* Generate a test object - change as needed */
    private static Person getTestPerson(final String jsonFile) {
        return Utils.getClassFromJsonResource(Person.class, jsonFile);
    }
}