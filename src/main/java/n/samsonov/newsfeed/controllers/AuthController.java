package n.samsonov.newsfeed.controllers;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.services.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<CustomSuccessResponse> register(@Validated @RequestBody RegisterUserDto dto) {
        return new ResponseEntity(CustomSuccessResponse
                .okWithData(service.registerUser(dto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody AuthUserDto dto) {
        return new ResponseEntity(service.authUser(dto), HttpStatus.OK);
    }
}
