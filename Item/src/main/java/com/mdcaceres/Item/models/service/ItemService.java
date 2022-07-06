package com.mdcaceres.Item.models.service;

import com.mdcaceres.Item.models.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer cantidad);
}
