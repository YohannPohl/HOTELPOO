package com.hotel.controller;

import com.hotel.dto.UsuarioRegistroDTO;
import com.hotel.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("usuario") @Valid UsuarioRegistroDTO usuarioDTO,
                               Model model) {
        try {
            usuarioService.registrarUsuario(usuarioDTO);
            return "redirect:/login?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
