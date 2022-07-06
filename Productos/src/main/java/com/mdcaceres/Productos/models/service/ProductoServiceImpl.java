package com.mdcaceres.Productos.models.service;

import com.mdcaceres.Productos.models.dao.ProductoDao;
import com.mdcaceres.Productos.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {
    @Autowired
    private ProductoDao productoDao;
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }
}
