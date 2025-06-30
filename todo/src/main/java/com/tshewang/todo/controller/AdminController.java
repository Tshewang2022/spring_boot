package com.tshewang.todo.controller;

import com.tshewang.todo.response.UserResponse;
import com.tshewang.todo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Admin REST API endpoints", description = "Operations related to admin")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return adminService.getAllUsers();
    }

    @Operation(summary = "Promote user to admin",description = "Promote user to admin role")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{userId}/role")
    public UserResponse promoteToAdmin(@PathVariable @Min(1) long userId){
        return adminService.promoteToAdmin(userId);
    }


    @Operation(summary = "Delete user",description = "Delete a non-admin user from the system")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Min(1) long userId){
        adminService.deleteNonAdminUser(userId);
    }
}
