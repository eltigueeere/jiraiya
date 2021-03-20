package com.mx.jiraiya.controller;

import com.mx.jiraiya.persistence.entities.Usuario;
import com.mx.jiraiya.service.CrudUsuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    Logger logger = LogManager.getLogger(UsuarioController.class);

    
    @Autowired
    private CrudUsuario _CrudUsuario = new CrudUsuario();

    @Secured("ROLE_USER")
    @PostMapping("/resetPassword")
    public ResponseEntity<?> updatePassword(@RequestBody Usuario usuario)  {
        logger.info("Cabio de contraseña.");
        return _CrudUsuario.updatePasswordService(usuario);
    }

    @GetMapping("/hakuLogin")
    public String hakuLogin() {
        return "view/login";
    }
    

}



