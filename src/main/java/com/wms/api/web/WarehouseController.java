package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.Warehouse;
import com.wms.api.model.WarehouseContent;
import com.wms.api.repository.WarehouseContentRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.service.SecurityService;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController //this indicates that we're be returning rest json data
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseContentRepository warehouseContentRepository;

    @Autowired
    private WarehouseRepository whRepository;

    @Autowired
    private SecurityService securityService;

    //api methods
    //path, methodU
    @RequestMapping(value = "/api/warehouses", method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "filter") String filter
    ) {

        List<Warehouse> results;

        //filtering
        if (filter == null)
            results = warehouseRepository.findAll();
        else {
            SpecificationsBuilder<Warehouse> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter);

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Warehouse> spec = builder.build();

            results = warehouseRepository.findAll(spec);
        }

        if (pageSize == null)
            pageSize = results.size() > 0 ? results.size() : 1;

        if (pageSize <= 0)
            throw new IllegalArgumentException("Invalid page size: " + pageSize);
        if (page <= 0)
            throw new IllegalArgumentException("Invalid page number: " + page);

        int fromIndex = (page - 1) * pageSize;
        if (results == null || results.size() < fromIndex) {
            model.addAttribute("data", results);
            model.addAttribute("total", 0);
        } else {
            model.addAttribute("data", results.subList(fromIndex, Math.min(fromIndex + pageSize, results.size())));
            model.addAttribute("total", results.size());
        }

        //return results.subList(fromIndex,Math.min(fromIndex + pageSize, results.size()));

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @RequestMapping(value = "/api/warehouse/{warehouseId}/contents", method = RequestMethod.GET)
    public MappingJackson2JsonView findContents(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "filter") String filter
    ) {

        List<WarehouseContent> results;

        //filtering
        if (filter == null)
            results = warehouseContentRepository.findAll();
        else {
            SpecificationsBuilder<WarehouseContent> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<WarehouseContent> spec = builder.build();

            results = warehouseContentRepository.findAll(spec);
        }

        if (pageSize == null)
            pageSize = results.size() > 0 ? results.size() : 1;

        if (pageSize <= 0)
            throw new IllegalArgumentException("Invalid page size: " + pageSize);
        if (page <= 0)
            throw new IllegalArgumentException("Invalid page number: " + page);

        int fromIndex = (page - 1) * pageSize;
        if (results == null || results.size() < fromIndex) {
            model.addAttribute("data", results);
            model.addAttribute("total", 0);
        } else {
            model.addAttribute("data", results.subList(fromIndex, Math.min(fromIndex + pageSize, results.size())));
            model.addAttribute("total", results.size());
        }

        //return results.subList(fromIndex,Math.min(fromIndex + pageSize, results.size()));

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @RequestMapping(value = "/api/warehouse/{id}", method = RequestMethod.GET)
    public Warehouse findOne(@PathVariable Integer id) { //method name
        return warehouseRepository.findOne(id); //receiving data from JPA repo
    }

    @RequestMapping(value = "/api/warehouse", method = RequestMethod.POST)
    public Warehouse add(@RequestBody Warehouse warehouseData) { //method name
        return warehouseRepository.save(warehouseData);
    }

    @RequestMapping(value = "/api/warehouse/content", method = RequestMethod.POST)
    public WarehouseContent addContent(@RequestBody WarehouseContent warehouseData) { //method name
        return warehouseContentRepository.save(warehouseData);
    }
}