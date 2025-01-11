package com.techphile.userManagement.apis;

import com.techphile.userManagement.domain.UserRepository;
import com.techphile.userManagement.dtos.UserRequest;
import com.techphile.userManagement.dtos.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public void add(@RequestBody UserRequest userRequest) {
        var user = userRequest.toUser();
        userRepository.save(user);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(UserResponse::fromUser ).toList();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        var user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEmail(userRequest.email());
        user.setName(userRequest.name());
        user.setPhone(userRequest.phone());
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
