package com.wms.api.web;

import com.wms.api.helpers.SpecificationsBuilder;

import com.wms.api.model.User;
import com.wms.api.model.Warehouse;
import com.wms.api.repository.UserRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.service.SecurityService;
import com.wms.api.service.UserService;
import com.wms.api.validator.UserValidator;
import com.wms.api.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController //this indicates that we're be returning rest json data
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseRepository whRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    //api methods
    //path, methodU
    @RequestMapping(value = "/api/users/{warehouseId}",method = RequestMethod.GET)
    public MappingJackson2JsonView find(
            Model model,
            @PathVariable Integer warehouseId,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, value = "filter") String filter
    ){

        List<User> results;

        //filtering
        if(filter == null)
            results = userRepository.findByWarehouseId(warehouseId);
        else {
            SpecificationsBuilder<User> builder = new SpecificationsBuilder<>();
            Matcher matcher = SearchOperation.filterPattern.matcher(filter + "warehouseId:" + warehouseId + ".");

            while (matcher.find())
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));

            Specification<User> spec = builder.build();

            results = userRepository.findAll(spec);
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

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable Integer id) { //method name
        return userRepository.findOne(id);
    }

    @RequestMapping(value="/api/user", method = RequestMethod.POST)
    public User add(@RequestBody User userData) {
        userData.setRoleId(3);//method name
        return userRepository.save(userData);
    }

    @RequestMapping(value="/api/user/{id}", method = RequestMethod.PUT)
    public User edit(@PathVariable Integer id, @RequestBody User userData) { //method name
        User user = userRepository.findOne(id);
        if(userData.getEmail() != null) user.setEmail(userData.getEmail());
        if(userData.getLogin() != null) user.setLogin(userData.getLogin());
        if(userData.getOwnedWarehouse() != null) user.setOwnedWarehouse(userData.getOwnedWarehouse());
        if(userData.getPassword() != null) user.setPassword(userData.getPassword());
        if(userData.getPasswordConfirm() != null) user.setPasswordConfirm(userData.getPasswordConfirm());
        if(userData.getName() != null) user.setName(userData.getName());
        if(userData.getPosition() >= 0) user.setPosition(userData.getPosition());
        if(userData.getSalary() != null) user.setSalary(userData.getSalary());
        if(userData.getSurname() != null) user.setSurname(userData.getSurname());
        if(userData.getRole() != null) user.setRole(userData.getRole());
        return userRepository.save(user);
    }

    @RequestMapping(value = "api/user/delete/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable Integer id){
        try{
            userRepository.delete(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
