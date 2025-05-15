package com.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A simple controller that returns a greeting. Use this to test that the server is running and that you are providing
 * the correct port and authentication credentials.
 */
@Slf4j
@Controller
public class HelloController {

    /**
     * Returns a greeting.
     * If you are able to access this endpoint, then you have successfully started the server and provided the correct
     * port and authentication credentials.
     *
     * @return A greeting.
     */
    @GetMapping
    public String index() {
        return "index";
    }
}
