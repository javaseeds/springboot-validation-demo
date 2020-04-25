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
        return Utils.getClassFromJsonFile(Name.class, jsonFile);
    }
}