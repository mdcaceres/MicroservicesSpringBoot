package com.mdcaceres.Productos.controllers;
import com.mdcaceres.Productos.models.entity.Producto;
import com.mdcaceres.Productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    private Environment env;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.findAll().stream().map(producto -> {
            //usando enviroment y no @VALUE
            //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            producto.setPort(port);
            return producto;
        }).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        producto.setPort(port);
        return producto;
    }
}
