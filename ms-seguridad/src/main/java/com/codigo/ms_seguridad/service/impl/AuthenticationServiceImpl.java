package com.codigo.ms_seguridad.service.impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Rol;
import com.codigo.ms_seguridad.entity.Role;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.repository.RolRepository;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.AuthenticationService;

import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {

        Usuario usuario = getUsuarioEntity(signUpRequest);
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario userAdmin = getUsuarioEntity(signUpRequest);
        Set<Rol> roles = new HashSet<>();
        roles.add(getRoles(Role.USER));
        roles.add(getRoles(Role.ADMIN));
        userAdmin.setRoles(roles);
        return usuarioRepository.save(userAdmin);
    }

    @Override
    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        //Autenticación con las credenciales (eamil, password)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail()
                , signInRequest.getPassword()));

        //Buscar al usuario en BD
        Usuario usuario = usuarioRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no econtrado en BD"));

        //Generamos los token
        String accessToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), usuario);

        //devuelvo la repsuesta
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public SignInResponse getTokenByRefreshToken(String token) throws IllegalAccessException {
        //validar que el token es un refreshToken
        if(!jwtService.validateIsRefreshToken(token)){
            throw new RuntimeException("Error: Token ingresado no es un RefreshToken");
        }
        //Extraer el username del token
        String userEmail = jwtService.extractUserName(token);

        //Buscar el usuario en la BD
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no econtrado en BD"));

        //Cargarmos los detalles del usuario
        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(usuario.getUsername());

        if(!jwtService.validateToken(token,userDetails)){
            throw new IllegalAccessException("El refresh token no es valido o esta vencido");
        }

        //Generar el nuevo accesToken
        String newAccessToken = jwtService.generateToken(usuario);

        return SignInResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(token)
                .build();

    }

    private Usuario getUsuarioEntity(SignUpRequest signUpRequest){
        return Usuario.builder()
                .nombres(signUpRequest.getNombres())
                .apellidos(signUpRequest.getApellidos())
                .email(signUpRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()))
                .tipoDoc(signUpRequest.getTipoDoc())
                .numDoc(signUpRequest.getNumDoc())
                .isAccountNonExpired(Constants.STATUS_ACTIVE)
                .isAccountNonLocked(Constants.STATUS_ACTIVE)
                .isCredentialsNonExpired(Constants.STATUS_ACTIVE)
                .isEnabled(Constants.STATUS_ACTIVE)
                .build();
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("Error el rol no exixte: " + rolBuscado.name()));
    }
}
