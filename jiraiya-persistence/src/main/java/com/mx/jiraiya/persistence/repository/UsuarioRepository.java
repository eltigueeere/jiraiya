package com.mx.jiraiya.persistence.repository;

import com.mx.jiraiya.persistence.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);



}
