package com.mdcaceres.Item.controllers;


import com.mdcaceres.Item.models.Item;
import com.mdcaceres.Item.models.Producto;
import com.mdcaceres.Item.models.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//refresscope para actualizar (componentes spring, controllers) de forma automatica sin reiniciar el servidor
@RefreshScope
@RestController
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

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

    @GetMapping("/obtener-config")
    public ResponseEntity<Map<String,String>> obtenerConfig(@Value("${server.port}") String puerto){
        //log.info("puerto de items es " + texto);
        Map<String,String> json = new HashMap<>();
        //json.put("texto", texto);
        json.put("puerto", puerto);
        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor", env.getProperty("configaracion.autor.nombre"));
            json.put("email", env.getProperty("configaracion.autor.email"));
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        return itemService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
        return itemService.update(producto,id);
    }

    @DeleteMapping("eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        itemService.delete(id);
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e){
        log.info(e.getMessage());
        Item it = new Item();
        Producto prod = new Producto();
        it.setCantidad(cantidad);
        prod.setNombre("circuit breaker!!!");
        prod.setPrecio(000.00);
        it.setProducto(prod);
        return it;
    }
}
