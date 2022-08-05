package com.mdcaceres.usuario.models.dao;


import com.mdcaceres.usuariocommons.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
    public Usuario findByUsername(String username);

    @Query("select u from Usuario u where u.username =?1")
    public Usuario obtenerPorNombreDeUsuario(String username);
}
