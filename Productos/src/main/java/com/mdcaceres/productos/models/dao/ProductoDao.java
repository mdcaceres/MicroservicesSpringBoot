package com.mdcaceres.productos.models.dao;

import com.mdcaceres.commons.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto, Long> {
}
