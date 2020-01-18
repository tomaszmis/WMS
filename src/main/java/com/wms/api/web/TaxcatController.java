package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;

import com.wms.api.model.Taxcat;
import com.wms.api.repository.TaxcatRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.service.SecurityService;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController //this indicates that we're be returning rest json data
public class TaxcatController {

    @Autowired
    private TaxcatRepository taxcatRepository;

    @Autowired
    private WarehouseRepository whRepository;


    //api methods
    //path, methodU
    @RequestMapping(value = "/api/taxes",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Taxcat> results;

        //filtering
        if(filter == null)
            results = taxcatRepository.findAll();
        else {
            SpecificationsBuilder<Taxcat> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter +  ",");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Taxcat> spec = builder.build();

            results = taxcatRepository.findAll(spec);
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

    @RequestMapping(value = "/api/taxcat/{id}", method = RequestMethod.GET)
    public Taxcat findOne(@PathVariable Integer id) { //method name
        return taxcatRepository.findOne(id); //receiving data from JPA repo
    }

    @RequestMapping(value ="/api/taxcat/{id}/list", method = RequestMethod.GET)
    public Map<Integer,String> list(@PathVariable Integer id){
        Map<Integer,String> map = new TreeMap();
        List<Taxcat> results;
        results = taxcatRepository.findAll();
        for(Taxcat d : results){
            map.put(d.getId(),d.getName());
        }
        return map;
    }

    @RequestMapping(value = "/api/taxcat", method = RequestMethod.POST)
    public Taxcat add(@RequestBody Taxcat taxcatData) { //method name
        return taxcatRepository.save(taxcatData);
    }

    @RequestMapping(value = "/api/taxcat/edit/{id}", method = RequestMethod.POST)
    public void edit(@PathVariable Integer id, @RequestBody Taxcat taxcatData) {
        Taxcat taxcat = taxcatRepository.findById(id);
        if(taxcatData.getName() != null) taxcat.setName(taxcatData.getName());
        if(taxcatData.getTax() > 0)  taxcat.setTax(taxcatData.getTax());
        taxcatRepository.save(taxcat);
    }


}