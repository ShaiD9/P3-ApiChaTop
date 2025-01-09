package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.UserDTO;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName());
        return ResponseEntity.ok(userDTO);
    }
}
