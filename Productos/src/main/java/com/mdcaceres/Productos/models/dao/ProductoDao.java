package com.mdcaceres.Productos.models.dao;

import com.mdcaceres.Productos.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto,Long> {

}
