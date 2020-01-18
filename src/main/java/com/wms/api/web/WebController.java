package com.wms.api.web;

import com.wms.api.model.*;
import com.wms.api.repository.UserRepository;
import com.wms.api.repository.WarehouseRepository;
import com.wms.api.service.SecurityService;
import com.wms.api.service.UserService;
import com.wms.api.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationHelper());

        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registrationForm") RegistrationHelper registrationForm, BindingResult bindingResult, Model model) {

        userValidator.validate(registrationForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User userForm = registrationForm.getUser();
        Warehouse whForm = registrationForm.getWh();

        User savedUser = userService.save(userForm);
        whForm.setOwner(userRepository.findOne(savedUser.getId()));
        whForm.setPaymentStatus(1);
        whForm.setStatus(1);
        Warehouse savedWH = whRepository.save(whForm);

        savedUser.setWarehouseId(savedWH.getId());
        userRepository.save(savedUser);

        //securityService.autologin(userForm.getLogin(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Podany login lub hasło jest nieprawidłowe.");

        if (logout != null)
            model.addAttribute("message", "Wylogowano");

        return "login";
    }

    @RequestMapping(value = "/app", method = RequestMethod.GET) //value = {"/path1", "path2"}
    public String welcome() {
        return "landing";
    }

}
