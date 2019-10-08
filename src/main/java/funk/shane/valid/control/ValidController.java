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

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import funk.shane.valid.pojo.Person;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ValidController {

    /**
     * 
     * @param person
     * @return
     */
    @PostMapping(path = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> validDemo(@Valid @RequestBody Person person) {
        log.info("inbound person: {}", person);

        return ResponseEntity.ok(String.format("Spring Boot person [%s] was valid"));
    }


    /**
     * 
     * @return
     */
    @GetMapping(path = "")
    public ResponseEntity<Person> getPerson() {
        return null;
    }

    /**
     * https://www.baeldung.com/exception-handling-for-rest-with-spring
     * Spring recommends having exceptions in their own @ControllerAdvice, but is ok for this simple example
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public void validationErrorHandler() {

    }
}