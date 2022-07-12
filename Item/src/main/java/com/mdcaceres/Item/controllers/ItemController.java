package com.mdcaceres.Item.controllers;


import com.mdcaceres.Item.models.Item;
import com.mdcaceres.Item.models.Producto;
import com.mdcaceres.Item.models.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory circuitFactory;
    @Autowired
    //@Qualifier("serviceFeign")
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre,
                             @RequestHeader(name="token-request", required = false) String token){
        System.out.printf("nombre: %s, token: %s%n",nombre,token);
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
        return circuitFactory.create("items")
                .run(() -> itemService.findById(id,cantidad), e -> metodoAlternativo(id,cantidad, e));
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e){
        log.info(e.getMessage());
        Item it = new Item();
        Producto prod = new Producto();
        it.setCantidad(cantidad);
        prod.setNombre("circuit breaker!!!");
        prod.setPrecio(500.00);
        it.setProducto(prod);
        return it;
    }
}
