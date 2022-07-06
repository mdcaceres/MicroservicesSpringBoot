package com.mdcaceres.Productos.models.service;

import com.mdcaceres.Productos.models.entity.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);

}
