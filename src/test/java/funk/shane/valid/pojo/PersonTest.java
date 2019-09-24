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

import java.time.LocalDate;
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
public class PersonTest {

    @Autowired
    private LocalValidatorFactoryBean factoryBean;
    
    @Test
    void testHappyPath() {
        final Person testPerson = getTestPerson("person-1.json");
        assertNotNull(testPerson);
        log.info("testPerson: {}", testPerson);

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(testPerson);
        assertEquals(0, violations.size());
    }

    @Test
    void testMissingName() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setName(null);

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> personViolation = violations.iterator().next();
        assertEquals("Person requires a name", personViolation.getMessage());    
    }

    @Test
    void testMissingAddress() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setAddress(null);

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> personViolation = violations.iterator().next();
        assertEquals("Person requires an address", personViolation.getMessage());    
    }

    @Test
    void testMissingPhone() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setPhone(null);

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person requires a phone number", nameViolation.getMessage());    
    }

    @Test
    void testMissingBirthDate() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setBirthDate(null);

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person requires a birth date", nameViolation.getMessage());    
    }

    @Test
    void testBirthDateIsInFuture() {
        final Person badPerson = getTestPerson("person-1.json");
        badPerson.setBirthDate(LocalDate.now().plusYears(10));

        final Set<ConstraintViolation<Person>> violations = factoryBean.validate(badPerson);
        assertEquals(1, violations.size());

        ConstraintViolation<Person> nameViolation = violations.iterator().next();
        assertEquals("Person's birthday cannot be in present or the future", nameViolation.getMessage());    
    }

    /* Generate a test object - change as needed */
    private static Person getTestPerson(final String jsonFile) {
        return Utils.getClassFromJsonResource(Person.class, jsonFile);
    }
}