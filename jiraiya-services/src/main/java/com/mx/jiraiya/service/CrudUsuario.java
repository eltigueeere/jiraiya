package com.mx.jiraiya.service;

import java.util.HashMap;
import java.util.Map;

import com.mx.jiraiya.persistence.entities.Usuario;
import com.mx.jiraiya.persistence.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service("CrudUsuario")
public class CrudUsuario {

    Logger logger = LogManager.getLogger(CrudUsuario.class);

    @Autowired
    private UsuarioRepository usuarioRepository = null;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = null;;

    public ResponseEntity<?> updatePasswordService(Usuario usuario) throws IOException {
        logger.info("Cabio de contraseña del usuario " + usuario.getUsername());
        Usuario usuarioActual = new Usuario();
        Usuario usuarioUpdate = new Usuario();
        Map<String, Object> response = new HashMap<>();
        //usuarioActual = usuarioRepository.findByUsername(usuario.getUsername());
        //logger.info("Usuario Actual --> " + usuarioActual.getUsername() + usuarioActual.getPassword() + usuarioActual.getId());
        try {
            String username = usuario.getUsername();
            usuarioActual = usuarioRepository.findByUsername(username);
            logger.info("Usuario Actual --> " + username);
            if (usuarioActual == null) {
                response.put("responseCode", "00 ==> Succes");
                response.put("mensaje", "No se edito el usuario: "
                        .concat(usuario.getUsername().toString().concat(" no existe en la DB")));
                logger.info(response);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }

            if (usuarioActual.getUsername().equals(usuario.getUsername())) {
                try {
                    usuarioActual.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    logger.info("Actualizando a ->" + usuarioActual.getPassword());
                    usuarioUpdate = usuarioRepository.save(usuarioActual);
                } catch (Exception e) {
                    response.put("responseCode", "03");
                    response.put("mensaje", "Error al actualizar : ".concat(usuario.getUsername().toString()));
                    response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                    logger.info(response);
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                response.put("responseCode", "00 ==> Succes");
                response.put("mensaje", "No se edito el usuario: "
                        .concat(usuario.getUsername().toString().concat(" no existe en la DB")));
                logger.info(response);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("responseCode", "00 ==> Succes");
            response.put("mensaje", "Se actualizo bien");
            response.put("usuario", usuarioUpdate);
            logger.info(response);
        } catch (Exception e) {
            logger.error("Error linea 61" + e);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

}