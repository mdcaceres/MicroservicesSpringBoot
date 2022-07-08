package com.mdcaceres.Item.controllers;


import com.mdcaceres.Item.models.Item;
import com.mdcaceres.Item.models.Producto;
import com.mdcaceres.Item.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    //@Qualifier("serviceFeign")
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(){
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
        return itemService.findById(id,cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad){
        Item it = new Item();
        Producto prod = new Producto();
        it.setCantidad(cantidad);
        prod.setNombre("camara sony");
        prod.setPrecio(500.00);
        it.setProducto(prod);
        return it;
    }
}
