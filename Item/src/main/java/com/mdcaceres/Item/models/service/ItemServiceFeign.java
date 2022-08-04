package com.mdcaceres.Item.models.service;

import com.mdcaceres.Item.clientes.ProductoClienteRest;
import com.mdcaceres.Item.models.Item;
import com.mdcaceres.Item.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {
    @Autowired
    private ProductoClienteRest clienteFeign;
    @Override
    public List<Item> findAll() {
        return clienteFeign.listar().stream()
                .map(x -> new Item(x,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clienteFeign.detalle(id),cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        return null;
    }

    @Override
    public Producto update(Producto producto, Long Id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
