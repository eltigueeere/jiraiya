package com.mx.jiraiya.controller;

import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController

public class MainController {

    private static Logger log = LogManager.getLogger(MainController.class);

    @GetMapping(value= "/")
    @ResponseBody
    public String hello() {
        log.debug("### Pantalla Inicial  / ###\n");
        return "Bienvenido a jiraiya Services!";
    }

}
