package com.hotel.controller;

import com.hotel.model.Hotel;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/")
    public String showHotels(Model model) {
        model.addAttribute("hoteis", hotelRepository.findAll());
        return "index";
    }

    @GetMapping("/hotel/add")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel("", "", 0.0, "", ""));
        return "add-hotel";
    }

    @PostMapping("/hotel/add")
    public String addHotel(@ModelAttribute Hotel hotel) {
        hotelRepository.save(hotel);
        return "redirect:/";
    }

    @GetMapping("/hotel/{id}")
    public String showHotelDetails(@PathVariable("id") Long id, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            return "hotel-page";
        }
        return "redirect:/";
    }

    @GetMapping("/hotel/{id}/reservar")
    public String reservarHotel(@PathVariable("id") Long id,
                                @RequestParam("checkin") String checkin,
                                @RequestParam("checkout") String checkout,
                                Model model) {

        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if (hotel == null) {
            return "redirect:/";
        }

        long dias = calculateDays(checkin, checkout);
        double totalPrice = hotel.getPreco() * dias;

        model.addAttribute("hotel", hotel);
        model.addAttribute("dias", dias);
        model.addAttribute("totalPrice", totalPrice);

        return "pagamento";
    }

    private long calculateDays(String checkin, String checkout) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date checkinDate = sdf.parse(checkin);
            Date checkoutDate = sdf.parse(checkout);
            long diffInMillies = checkoutDate.getTime() - checkinDate.getTime();
            return diffInMillies / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @GetMapping("/hotel/{id}/obrigado")
    public String mostrarPaginaObrigado(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hotelId", id);
        return "obrigado";
    }
}
