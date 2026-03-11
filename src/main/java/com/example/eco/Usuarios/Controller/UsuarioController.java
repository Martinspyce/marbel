package com.example.eco.Usuarios.Controller;

import com.example.eco.Model.UsuarioModel;
import com.example.eco.Usuarios.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista completa de usuarios con enlaces HATEOAS.")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioModel>>> getAllUsuarios() {
        List<EntityModel<UsuarioModel>> usuarios = usuarioService.getAllUsuarios()
                .stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).deleteUsuario(usuario.getId())).withRel("delete"),
                        linkTo(methodOn(UsuarioController.class).updateUsuario(usuario.getId(), usuario)).withRel("update")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(usuarios, linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withSelfRel())
        );
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los detalles de un usuario específico con enlaces HATEOAS.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioModel>> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("all-usuarios"),
                        linkTo(methodOn(UsuarioController.class).deleteUsuario(id)).withRel("delete"),
                        linkTo(methodOn(UsuarioController.class).updateUsuario(id, usuario)).withRel("update")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @PostMapping
    public ResponseEntity<EntityModel<UsuarioModel>> createUsuario(@RequestBody UsuarioModel usuario) {
        UsuarioModel saved = usuarioService.saveUsuario(usuario);
        EntityModel<UsuarioModel> resource = EntityModel.of(saved,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(saved.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("all-usuarios")
        );
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioModel>> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioModel usuarioDetails) {
        try {
            UsuarioModel updated = usuarioService.updateUsuario(id, usuarioDetails);
            EntityModel<UsuarioModel> resource = EntityModel.of(updated,
                    linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("all-usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar usuario por email", description = "Busca un usuario utilizando su correo electrónico.")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioModel> getUsuarioByEmail(@PathVariable String email) {
        return usuarioService.getUsuarioByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuario por nombre", description = "Busca un usuario por su nombre.")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<UsuarioModel> getUsuarioByNombre(@PathVariable String nombre) {
        return usuarioService.getUsuarioByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuarios por apellido", description = "Devuelve una lista de usuarios que tienen el apellido dado.")
    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<UsuarioModel>> getUsuariosByApellido(@PathVariable String apellido) {
        return ResponseEntity.ok(usuarioService.getUsuariosByApellido(apellido));
    }
}

