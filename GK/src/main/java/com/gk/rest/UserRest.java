/**
 * 
 */
package com.gk.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gk.entity.Authority;
import com.gk.entity.Users;
import com.gk.repository.UserRepository;
import com.gk.security.JwtUser;
import com.gk.service.PasswordService;
import com.gk.tenant.TenantContext;
import com.gk.tenant.TenantResolve;

/**
 * @author clovis
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class UserRest extends TenantResolve{
	
	@Autowired
	private UserRepository usuarioRepository;
		
	@PostMapping(path="/usuario/novo")
	public ResponseEntity<Object> salvar(@RequestBody Users usuario) {
		usuario.setPassword(PasswordService.hashPassword(usuario.getPassword()));
		try {
			TenantContext.setCurrentTenant(usuario.getTenantId().toLowerCase());
			usuarioRepository.save(usuario);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
		}
		return ResponseEntity.ok(new JwtUser(usuario.getId(), 
											 usuario.getUsername(), 
											 usuario.getFirstname(), 
											 usuario.getLastname(), 
											 usuario.getPassword(), 
											 mapToGrantedAuthorities(usuario.getAuthorities()), 
											 usuario.getEnabled(), 
											 usuario.getLastPasswordResetDate(),
											 usuario.getTenantId())); 
	}
	
	@PutMapping(path="/usuario/atualizar")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> editar(@RequestBody Users usuario) {
		try {
			usuario.setPassword(PasswordService.hashPassword(usuario.getPassword()));
			usuarioRepository.save(usuario);
		}catch(Exception e){
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok(new JwtUser(usuario.getId(), 
				 usuario.getUsername(), 
				 usuario.getFirstname(), 
				 usuario.getLastname(), 
				 usuario.getPassword(), 
				 mapToGrantedAuthorities(usuario.getAuthorities()), 
				 usuario.getEnabled(), 
				 usuario.getLastPasswordResetDate(),
				 usuario.getTenantId()));
	}
	
	@DeleteMapping(path="/usuario/remover/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<String> salvar(@PathVariable("id")	Long	id) {
		try {
			usuarioRepository.delete(id);
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(path="/usuario/lista")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<JwtUser>> lista(){
		List<Users> listaDeUsuarios = null;
		try {
			applyTenant();
			listaDeUsuarios = usuarioRepository.findAll();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		List<JwtUser> listaUsuariosJwt = montaListaDeUsuariosJwt(listaDeUsuarios);
		if(listaUsuariosJwt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(listaUsuariosJwt);
	}
	
	@GetMapping(path="/usuario/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> usuario(@PathVariable("id") Long id){
		Users usuario = null;
		try {
			usuario = usuarioRepository.findOne(id);
		}catch(Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(new JwtUser(usuario.getId(), 
				 usuario.getUsername(), 
				 usuario.getFirstname(), 
				 usuario.getLastname(), 
				 usuario.getPassword(), 
				 mapToGrantedAuthorities(usuario.getAuthorities()), 
				 usuario.getEnabled(), 
				 usuario.getLastPasswordResetDate(),
				 usuario.getTenantId()));
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
	
	private List<JwtUser> montaListaDeUsuariosJwt(List<Users> listaDeUsuarios){
		List<JwtUser> lista = new ArrayList<>();
		for (Users users : listaDeUsuarios) {
			lista.add(new JwtUser(users.getId(), 
					users.getUsername(), 
					users.getFirstname(), 
					users.getLastname(), 
					users.getPassword(), 
					mapToGrantedAuthorities(users.getAuthorities()), 
					users.getEnabled(), 
					users.getLastPasswordResetDate(),
					users.getTenantId()));
			
		}
		return lista;
	}

}
