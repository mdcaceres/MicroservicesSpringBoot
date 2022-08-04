package com.mdcaceres.productos.controllers;

import com.mdcaceres.commons.models.entity.Producto;
import com.mdcaceres.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
            producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            //producto.setPort(port);
            return producto;
        }).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {
        if(id.equals(10L)){
            throw new IllegalStateException("producto no encontrado");
        }
        //probando time out
        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = productoService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        //producto.setPort(port);
        //retardo para pobrar un circuitBraker
//        try {
//            Thread.sleep(2000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return producto;
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto){
        return ResponseEntity.ok(productoService.save(producto));
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
        Producto p = productoService.findById(id);
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        p.setCreateAt(producto.getCreateAt());
        return productoService.save(p);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        productoService.deleteById(id);
    }

}
