package de.ait.javalessons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String homepage() {
        return "Main page";
    }

    @GetMapping("/public/info")
    public String publishInfoPage() {
        return "This page is available without authorization";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "Private users cabinet";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Admins section";
    }
}
