package com.tshewang.todo.service;

import com.tshewang.todo.entity.Authority;
import com.tshewang.todo.entity.User;
import com.tshewang.todo.repository.UserRepository;
import com.tshewang.todo.request.PasswordUpdateRequest;
import com.tshewang.todo.response.UserResponse;
import com.tshewang.todo.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                // Fixed: Removed unnecessary casting
                user.getAuthorities().stream().map(auth->(Authority)auth).toList()
        );
    }

    @Override
    @Transactional // Added @Transactional for database operation
    public void deleteUser() {
        // Find the current authenticated user
        User user = findAuthenticatedUser.getAuthenticatedUser();

        // Check if user is the last admin
        if (isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Last admin cannot delete their account");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        // Validate the user
        User user = findAuthenticatedUser.getAuthenticatedUser();

        // Validate the old password
        if (!isOldPasswordCorrect(user.getPassword(), passwordUpdateRequest.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        // Validate the password confirmation
        if (!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getNewPassword2())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password confirmation does not match");
        }

        // New password must be different from old password
        if (!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the current password");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    // Method to check if old password is correct
    private boolean isOldPasswordCorrect(String currentEncodedPassword, String oldPasswordPlainText) {
        return passwordEncoder.matches(oldPasswordPlainText, currentEncodedPassword);
    }

    // Confirm new password matches confirmation
    private boolean isNewPasswordConfirmed(String newPassword, String newPasswordConfirmed) {
        return newPassword != null && newPassword.equals(newPasswordConfirmed);
    }

    // Check if new password is different from old password
    private boolean isNewPasswordDifferent(String oldPasswordPlainText, String newPasswordPlainText) {
        return !oldPasswordPlainText.equals(newPasswordPlainText);
    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getAuthorities()
                .stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            return adminCount <= 1;
        }
        return false;
    }
}