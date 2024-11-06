package br.univille.neighborhood.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpServletRequest request, Authentication authentication, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            System.out.println("Sessão ID: " + session.getId());
        } else {
            System.out.println("Sessão não encontrada");
        }

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        return "index";
    }

}
