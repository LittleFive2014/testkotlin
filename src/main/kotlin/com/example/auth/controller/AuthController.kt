package com.example.auth.controller

import com.example.auth.dto.LoginRequest
import com.example.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/login")
    fun showLoginPage(model: Model): String {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", LoginRequest("", "", false))
        }
        return "login"
    }

    @PostMapping("/login")
    fun processLogin(
        @Valid @ModelAttribute loginRequest: LoginRequest,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please check your input")
            return "redirect:/login"
        }

        return try {
            authService.authenticate(loginRequest)
            "redirect:/dashboard"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password")
            "redirect:/login"
        }
    }

    @GetMapping("/logout")
    fun logout(): String {
        return "redirect:/login?logout"
    }

    @GetMapping("/dashboard")
    fun showDashboard(): String {
        return "dashboard"
    }
}
