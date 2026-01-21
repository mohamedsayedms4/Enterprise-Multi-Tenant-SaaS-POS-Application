package org.example.enterprisemultitenantsaasposapplication.controller;

import lombok.RequiredArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.configuration.JwtConstant;
import org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse;
import org.example.enterprisemultitenantsaasposapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /api/v1/users?page=0&size=10&sort=id,desc
     */
    @GetMapping
    public ResponseEntity<Page<UserDtoResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDtoResponse> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @GetMapping("/me")
    public ResponseEntity<UserDtoResponse> getMe(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @GetMapping("/from-token")
    public ResponseEntity<UserDtoResponse> getFromToken(
            @RequestHeader(name = JwtConstant.JWT_HEADER, required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(JwtConstant.BEARER_PREFIX)) {
            // خليه يترمي BadException عندك بدل ما نعمل 400 هنا لو تحب
            return ResponseEntity.badRequest().build();
        }

        String token = authorizationHeader.substring(JwtConstant.BEARER_PREFIX.length());
        return ResponseEntity.ok(userService.getUserFromJwtToken(token));
    }
}
