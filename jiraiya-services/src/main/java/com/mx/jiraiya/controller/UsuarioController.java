package com.mx.jiraiya.controller;

import com.mx.jiraiya.persistence.entities.Usuario;
import com.mx.jiraiya.service.CrudUsuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = ("http://127.0.0.1:8080"))
@RestController
public class UsuarioController {

    Logger logger = LogManager.getLogger(UsuarioController.class);

    
    @Autowired
    private CrudUsuario _CrudUsuario = new CrudUsuario();

    @PostMapping("/resetPassword")
    public ResponseEntity<?> updatePassword(@RequestBody Usuario usuario)  {
        logger.info("Cabio de contraseña.");
        return _CrudUsuario.updatePasswordService(usuario);
    }

}



