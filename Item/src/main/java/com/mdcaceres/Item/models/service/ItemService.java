package com.mdcaceres.Item.models.service;

import com.mdcaceres.Item.models.Item;
import com.mdcaceres.Item.models.Producto;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer cantidad);

    public Producto save(Producto producto);
    public Producto update(Producto producto,Long Id );
    public void delete(Long id);
}
