package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.*;
import com.wms.api.repository.*;
import com.wms.api.service.SecurityService;
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
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private WarehouseRepository whRepository;

    @Autowired
    private DeliveryProductsRepository deliveryProductsRepository;

    @Autowired
    private WarehouseContentRepository warehouseContentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    //api methods
    //path, methodU
    @RequestMapping(value = "/api/deliveries/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Delivery> results;

        //filtering
        if(filter == null)
            results = deliveryRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<Delivery> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Delivery> spec = builder.build();

            results = deliveryRepository.findAll(spec);
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

    @RequestMapping(value = "/api/delivery/{id}", method = RequestMethod.GET)
    public Delivery findOne(@PathVariable Integer id) { //method name
        return deliveryRepository.findOne(id); //receiving data from JPA repo
    }

    @RequestMapping(value = "/api/delivery", method = RequestMethod.POST)
    public Delivery add(@RequestBody Delivery deliveryData) { //method name
        deliveryData.setStatus(0);
        Delivery delivery = deliveryRepository.save(deliveryData);

        for(int i = 0; i < deliveryData.getProductIds().length;i++){
            DeliveryProducts dp = deliveryProductsRepository.findOne(deliveryData.getProductIds()[i]);
            dp.setDelivery(delivery);
            deliveryProductsRepository.save(dp);
        }
        return deliveryRepository.findOne(delivery.getId());
    }

    @RequestMapping(value = "/api/delivery/{id}/products", method = RequestMethod.GET)
    public List<DeliveryProducts> getProducts(@PathVariable Integer id) { //method name
        return deliveryProductsRepository.findByDelivery(deliveryRepository.findById(id));
    }

    @RequestMapping(value = "/api/delivery/{id}/{status}", method = RequestMethod.POST)
    boolean setStatus(@PathVariable Integer id, @PathVariable Integer status){
        Delivery d = deliveryRepository.findOne(id);
        try {
            if (status == 2) {
                List<DeliveryProducts> listdp = deliveryProductsRepository.findByDelivery(d);
                for (DeliveryProducts dp : listdp) {
                    WarehouseContent w = warehouseContentRepository.findByProduct(dp.getProduct());
                    int k = w.getAvailable() + dp.getQuantity();
                    w.setAvailable(k);
                    warehouseContentRepository.save(w);
                    //deliveryProductsRepository.delete(dp.getId());
                }
            }
            d.setStatus(status);
        }catch(Exception e){
            return false;
        }
        d.setStatus(status);
        deliveryRepository.save(d);
        checkOrders(d.getWarehouseId());

        return true;

    }

    @RequestMapping(value = "/api/deliveryProducts/{warehouseId}/list", method = RequestMethod.GET)
    public Map<Integer,String> list(@PathVariable Integer warehouseId){
        Map<Integer,String> map = new TreeMap();
        List<DeliveryProducts> results;
        results = deliveryProductsRepository.findByWarehouseIdAndDelivery(warehouseId, null);
        for(DeliveryProducts d : results){
            map.put(d.getId(), "{\"name\":\""+d.getProduct().getName() + "\",\"amount\":"+d.getQuantity()+",\"unit\":\""+d.getProduct().getUnit()+"\",\"deliverer\":"+d.getProduct().getDelivererId()+",\"dName\":\""+d.getProduct().getDeliverer().getName()+"\"}");
        }
        return map;
    }

    void checkOrders(Integer warehouseId){
        List<Order> orders = orderRepository.findByWarehouseId(warehouseId);
        List<Integer> productIds = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        for(Order o : orders){
            if(o.getStatus() == 0) {
                productIds.clear();
                amounts.clear();
                for (OrderedProducts op : o.getOrderedProducts()) {
                    productIds.add(op.getProduct().getId());
                    amounts.add(op.getAmount());
                }
                setOrderStatus(o, productIds, amounts);
            }
        }

    }

    void setOrderStatus(Order order, List<Integer> productIds, List<Integer> amounts) {
        boolean canBeProcessed = true;

        for (int i = 0; i < productIds.size(); i++) {
            Product p = productRepository.findById(productIds.get(i));
            WarehouseContent w = warehouseContentRepository.findByProduct(p);
            if (w.getAvailable() < amounts.get(i))
                canBeProcessed = false;
        }

        if (canBeProcessed == true) {
            for (int i = 0; i < productIds.size(); i++) {
                Product p = productRepository.findById(productIds.get(i));
                WarehouseContent w = warehouseContentRepository.findByProduct(p);
                w.setAvailable(w.getAvailable() - amounts.get(i));
                w.setReserved(w.getReserved() + amounts.get(i));
                warehouseContentRepository.save(w);
            }
            order.setStatus(1);
            orderRepository.save(order);
        }
    }

}