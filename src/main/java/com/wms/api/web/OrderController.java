package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.*;

import com.wms.api.repository.*;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.regex.Matcher;

@RestController //this indicates that we're be returning rest json data
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WarehouseRepository whRepository;
    @Autowired
    private WarehouseContentRepository warehouseContentRepository;

    @Autowired
    private OrderedProductsRepository orderedProductsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  DeliveryProductsRepository deliveryProductsRepository;

    @RequestMapping(value = "/api/order/{id}/{status}", method = RequestMethod.POST)
    public boolean setStatus(@PathVariable Integer id, @PathVariable Integer status){
        try {
            Order p = orderRepository.findOne(id);
            p.setStatus(status);
            orderRepository.save(p);
            if(status == 2){
                List<OrderedProducts> ops = p.getOrderedProducts();
                for(OrderedProducts op: ops){
                    WarehouseContent wc = warehouseContentRepository.findByProduct(op.getProduct());
                    wc.setReserved(wc.getReserved() - op.getAmount());
                    warehouseContentRepository.save(wc);
                }

            }
        }catch (Exception e){
            return false;
        }
        return  true;
    }

    //api methods
    //path, methodU
    @RequestMapping(value = "/api/orders/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Order> results;

        //filtering
        if(filter == null)
            results = orderRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<Order> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Order> spec = builder.build();

            results = orderRepository.findAll(spec);
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

    @RequestMapping(value = "/api/order/{id}", method = RequestMethod.GET)
    public Order findOne(@PathVariable Integer id) { //method name
        return orderRepository.findOne(id); //receiving data from JPA repo
    }

    @RequestMapping(value = "/api/order/{id}/products", method = RequestMethod.GET)
    public List<OrderedProducts> getProducts(@PathVariable Integer id) { //method name
        return orderedProductsRepository.findByOrder(orderRepository.findById(id));
    }

    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public Order add(@RequestBody Order orderData){ //method name
        orderData.setStatus(0);
        Integer id = orderRepository.save(orderData).getId();
        boolean canBeProcessed = true;

        for(int i = 0; i < orderData.getProductIds().length; i++) {
            Product p = productRepository.findById(orderData.getProductIds()[i]);
            WarehouseContent w = warehouseContentRepository.findByProduct(p);
            if(w.getAvailable() < orderData.getProductAmounts()[i] || (w.getAvailable() - orderData.getProductAmounts()[i]) <=  (p.getOptimalQuant() * p.getMinimalQuant()) / 100) {
                if(canBeProcessed) canBeProcessed = w.getAvailable() >= orderData.getProductAmounts()[i];
                deliveryProductsRepository.save(new DeliveryProducts(null, p, (p.getOptimalQuant()) + orderData.getProductAmounts()[i], orderData.getWarehouseId() ));
            }

        }

        for(int i = 0; i < orderData.getProductIds().length; i++) {
            Product p = productRepository.findById(orderData.getProductIds()[i]);
            orderedProductsRepository.save(new OrderedProducts(orderRepository.findOne(id),p,orderData.getProductAmounts()[i]));
            if(canBeProcessed == true){
                WarehouseContent w = warehouseContentRepository.findByProduct(p);
                w.setAvailable(w.getAvailable() - orderData.getProductAmounts()[i]);
                w.setReserved(w.getReserved() + orderData.getProductAmounts()[i]);
                warehouseContentRepository.save(w);

            }
        }
        Order o = orderRepository.findOne(id);
        o.setStatus(canBeProcessed ? 1 : 0);
        orderRepository.save(o);
       // orderData.setOrderedProducts(orderedProducts);

        return orderRepository.findOne(id);
    }

}