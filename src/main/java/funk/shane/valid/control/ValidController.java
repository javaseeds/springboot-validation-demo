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

package funk.shane.valid.control;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import funk.shane.valid.pojo.Person;
import funk.shane.valid.util.Utils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api")
@Slf4j
public class ValidController {

    /**
     * validDemo - REST POST call to "write" a person to a data source (not provided in this demo app)
     * Only returns a String with either an affirmative valid message or explains why the input was incorrect
     * 
     * @param person
     * @return
     */
    @PostMapping(path = "/v1/valid", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> validDemo(@Valid @RequestBody Person person) {
        log.info("inbound person: {}", person);

        return ResponseEntity.ok(String.format("Spring Boot person [%s] was valid", person));
    }

    /**
     * Retrieve a person - even though there's an id sent in, this trivial example doesn't pull from a data source 
     * in this version
     * @return
     */
    @GetMapping(path = "/v1/get-a-person/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Person> getPerson(@PathVariable String id) {
        log.info("Get Person with id: [{}]", id);
        final Person person = Utils.getClassFromJsonFile(Person.class, "person-1.json");
        log.info("Returning Person: {}", person);

        return ResponseEntity.ok().body(person);
    }

    /**
     * https://www.baeldung.com/exception-handling-for-rest-with-spring
     * Spring recommends having exceptions in their own @ControllerAdvice, but is ok for this simple example
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> validationErrorHandler(ConstraintViolationException ex) {
        final List<String> validationStrings = ex.getConstraintViolations()
            .stream()
            .map(v -> v.getMessage())
            .sorted()
            .collect(Collectors.toList());
        log.error("Inbound REST call tripped validation error(s) [{}]", validationStrings.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationStrings.toString());
    }
}