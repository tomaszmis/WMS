package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;
import com.wms.api.model.Client;
import com.wms.api.repository.ClientRepository;
import com.wms.api.repository.WarehouseRepository;
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

@RestController
public class ClientsController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WarehouseRepository whRepository;


    @RequestMapping(value = "/api/clients/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<Client> results;

        //filtering
        if(filter == null)
            results = clientRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<Client> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<Client> spec = builder.build();

            results = clientRepository.findAll(spec);
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

    @RequestMapping(value ="/api/client/{id}/list", method = RequestMethod.GET)
    public Map<Integer,String> list(@PathVariable Integer id){
        Map<Integer,String> map = new TreeMap();
        List<Client> results;
        results = clientRepository.findByWarehouseId(id);
        for(Client c : results){
            map.put(c.getId(),c.getName());
        }
        return map;
    }

    @RequestMapping(value = "/api/client/{id}", method = RequestMethod.GET)
    public Client findOne(@PathVariable Integer id){
        return clientRepository.findOne(id);
    }


    @RequestMapping(value="/api/client", method = RequestMethod.POST)
    public Client add(@RequestBody Client clientData){ return  clientRepository.save(clientData); }

}