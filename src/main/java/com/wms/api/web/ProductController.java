package com.wms.api.web;


import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.Product;
import com.wms.api.model.WarehouseContent;
import com.wms.api.repository.ProductRepository;
import com.wms.api.repository.WarehouseContentRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController //this indicates that we're be returning rest json data
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    WarehouseContentRepository warehouseContentRepository;


    //api methods
    //path, methodU
    @RequestMapping(value = "/api/products/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Product> results;

        //filtering
        if(filter == null)
            results = productRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<Product> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Product> spec = builder.build();

            results = productRepository.findAll(spec);
        }

        if(pageSize == null)
            pageSize = results.size() > 0 ? results.size() : 1;

        if(pageSize <= 0)
            throw new IllegalArgumentException("Invalid page size: " + pageSize);
        if(page <= 0)
            throw new IllegalArgumentException("Invalid page number: " + page);

        int fromIndex = (page - 1) * pageSize;
        if(results == null || results.size() < fromIndex) {
            model.addAttribute("data", results);
            model.addAttribute("total", 0);
        } else {
            model.addAttribute("data", results.subList(fromIndex,Math.min(fromIndex + pageSize, results.size())));
            model.addAttribute("total", results.size());
        }

        //return results.subList(fromIndex,Math.min(fromIndex + pageSize, results.size()));

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @RequestMapping(value = "/api/product/{id}", method = RequestMethod.GET)
    public Product findOne(@PathVariable Integer id) { //method name
        return productRepository.findOne(id); //receiving data from JPA repo
    }

    @RequestMapping(value= "/api/product/checkState/{productId}/{amount}", method = RequestMethod.GET)
    public boolean checkState(@PathVariable Integer productId, @PathVariable Integer amount){
        WarehouseContent w = warehouseContentRepository.findByProduct(productRepository.findById(productId));
        return w.getAvailable() >= amount ? true : false;
    }


    @RequestMapping(value = "/api/product/setStatus/{id}/{status}", method = RequestMethod.POST)
    public boolean setStatus(@PathVariable Integer id, @PathVariable Integer status){
        try {
            Product p = productRepository.findById(id);
            p.setStatus(status);
            productRepository.save(p);
        }catch (Exception e){
            return false;
        }
        return  true;
    }

    @RequestMapping(value ="/api/product/{id}/list", method = RequestMethod.GET)
    public Map<Integer,String> list(@PathVariable Integer id){
        Map<Integer,String> map = new TreeMap();
        List<Product> results;
        results = productRepository.findByWarehouseIdAndStatus(id, 1);
        for(Product p : results){
            map.put(p.getId(),p.getName());
        }
        return map;
    }

    @RequestMapping(value = "/api/product", method = RequestMethod.POST)
    public Product add(@RequestBody Product productData) { //method name
        boolean isSave = productData.getId() == null;
        Product p = productRepository.save(productData);
        if(isSave) {
            WarehouseContent w = new WarehouseContent(productData.getWarehouseId(),productRepository.findOne(p.getId()),0,0);
            warehouseContentRepository.save(w);
        }
        return p;
    }
}