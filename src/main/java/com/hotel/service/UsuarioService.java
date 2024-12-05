package com.hotel.service;

import com.hotel.dto.UsuarioRegistroDTO;
import com.hotel.model.Usuario;
import com.hotel.model.Usuario.Role;
import com.hotel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(UsuarioRegistroDTO dto) {
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Nome de usuário já existe.");
        }

        Usuario usuario = new Usuario(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                Role.USER
        );

        return usuarioRepository.save(usuario);
    }
}
