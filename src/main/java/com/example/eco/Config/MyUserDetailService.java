package com.example.eco.Config;

import com.example.eco.Model.UsuarioModel;
import com.example.eco.Usuarios.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRol().toUpperCase()) // "ADMIN", "USER", etc.
                .build();
    }
}
