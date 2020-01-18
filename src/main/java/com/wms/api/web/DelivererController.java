package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.Client;
import com.wms.api.model.Deliverer;
import com.wms.api.repository.DelivererRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.service.SecurityService;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DelivererController{

    @Autowired
    private DelivererRepository delivererRepository;

    @Autowired
    private WarehouseRepository whRepository;


    @RequestMapping(value = "/api/deliverers/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Deliverer> results;

        //filtering
        if(filter == null)
            results = delivererRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<Deliverer> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Deliverer> spec = builder.build();

            results = delivererRepository.findAll(spec);
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
    //set status product zamowienie i dostawa
    //int int
    //usuwanie usera delete id usera w sciezce


    @RequestMapping(value = "/api/deliverer/{id}", method = RequestMethod.GET)
    public Deliverer findOne(@PathVariable Integer id){
        return delivererRepository.findOne(id);
    }

    @RequestMapping(value ="/api/deliverer/{id}/list", method = RequestMethod.GET)
    public Map<Integer,String> list(@PathVariable Integer id){
        Map<Integer,String> map = new TreeMap();
        List<Deliverer> results;
        results = delivererRepository.findByWarehouseId(id);
        for(Deliverer d : results){
            map.put(d.getId(),d.getName());
        }
        return map;
    }

    @RequestMapping(value="/api/deliverer", method = RequestMethod.POST)
    public Deliverer add(@RequestBody Deliverer delivererData){
        return  delivererRepository.save(delivererData);
    }

}