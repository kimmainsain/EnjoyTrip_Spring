package com.ssafy.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@ApiIgnore
@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String adminPage(HttpSession session, RedirectAttributes redirectAttributes){
        if(session.getAttribute("admin") == null){
            redirectAttributes.addFlashAttribute("msg", "유효하지 않은 접근입니다.");
            return "redirect:/";
        }

        return "admin";
    }
}
