package com.doan.sigma.api;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.sigma.dto.AuthenticationResponse;
import com.doan.sigma.dto.LoginRequest;
import com.doan.sigma.dto.RefreshTokenRequest;
import com.doan.sigma.dto.RegisterRequest;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.service.AuthService;
import com.doan.sigma.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin	//if your program breaks its because you addewd validated and co
public class AuthAPI {

	@Autowired
	private AuthService authService;
	@Autowired
	private RefreshTokenService refreshTokenService;
		//probably very users on signup for testing purposes
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SubException{
		return new ResponseEntity<String>(authService.signup(registerRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("verify/{username}")
	public ResponseEntity<String> verifyAccount(@PathVariable String username) throws SubException{
		authService.fetchUserAndEnable(username);
		return new ResponseEntity<>("user verified and enabled", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws SubException {
		return authService.login(loginRequest);
	}
	
	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws SubException {
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")	//why does logout need a token?
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws SubException {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());	//look at this
		return new ResponseEntity<String>("successfully logged out",HttpStatus.OK);
	}
}
