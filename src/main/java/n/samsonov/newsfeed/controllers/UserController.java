package n.samsonov.newsfeed.controllers;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.PutUserDto;
import n.samsonov.newsfeed.errors.ValidationConstants;
import n.samsonov.newsfeed.security.UserDetailsImpl;
import n.samsonov.newsfeed.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")

public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity getAllUsers() {
        return new ResponseEntity(service.viewAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(@NotNull Authentication authentication) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity(service.getUserInfoById(userDetailsImpl.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserInfoById(@Validated @PathVariable("id")
                                          @Size(min = 36, max = 36,
                                                  message = ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED)
                                          String id,
                                          Authentication authentication) {
        authentication.getAuthorities();
        return new ResponseEntity(service.getUserInfoById(UUID.fromString(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity replaceUserInfo(@Validated @RequestBody PutUserDto dto, Authentication authentication) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity(service.replaceUserInfo(dto, userDetailsImpl.getId(), userDetailsImpl.getEmail()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity(service.deleteUser(userDetails.getId()), HttpStatus.OK);

    }
}
