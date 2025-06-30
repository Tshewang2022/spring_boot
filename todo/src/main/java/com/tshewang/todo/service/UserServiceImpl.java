package com.tshewang.todo.service;


import com.tshewang.todo.entity.Authority;
import com.tshewang.todo.entity.User;
import com.tshewang.todo.repository.UserRepository;
import com.tshewang.todo.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authentication required");
        }

        User user = (User) authentication.getPrincipal();
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth->(Authority)auth).toList()
        );
    }

    @Override
    public void deleteUser() {
        // find the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authentication required");
        }

        User user = (User) authentication.getPrincipal();

        // isAdmin
        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }

        userRepository.delete(user);
    }

    private boolean isLastAdmin(User user){
        boolean isAdmin = user.getAuthorities()
                .stream()
                .anyMatch(authority->"ROLE_ADMIN".equals(authority.getAuthority()));
        if (isAdmin){
           long adminCount = userRepository.countAdminUsers();
           return adminCount<=1;
        }
        return false;
    }
}
