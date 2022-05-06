package com.doan.sigma.service;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doan.sigma.dto.AuthenticationResponse;
import com.doan.sigma.dto.LoginRequest;
import com.doan.sigma.dto.RefreshTokenRequest;
import com.doan.sigma.dto.RegisterRequest;
import com.doan.sigma.entity.NotificationEmail;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.UsersRepository;
import com.doan.sigma.security.JwtProvider;

@Service
@Transactional
public class AuthService {

	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private MailService mailService;

	public String signup(RegisterRequest registerRequest) throws SubException{
		boolean isAvail = usersRepo.findById(registerRequest.getEmail().toLowerCase()).isEmpty();
		if(isAvail) {		//if email is available, create the user and save
			Users user = new Users();
			user.setUsername(registerRequest.getUsername());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			user.setEmail(registerRequest.getEmail());
			user.setFirstName(registerRequest.getFirstName());
			user.setLastName(registerRequest.getLastName());
			user.setFollowersCount(0);
			user.setEnabled(false);
		
			usersRepo.save(user);
			mailService.sendMail(new NotificationEmail("You have signed up for Sigma Trade!",user.getEmail(),"Thanks for signing up for Sigma Trade! If this wasn't you-please ignore this email"));
			return "registered user to database";
		}
		return "unable to register user to database, email already exists";
	}
	
	public void fetchUserAndEnable(String username) throws SubException {
		Optional<Users> userOptional = usersRepo.findByUsername(username);
		Users user = userOptional.orElseThrow(()->new SubException("can't enable user"));
		user.setEnabled(true);
		usersRepo.save(user);
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) throws SubException {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(token,refreshTokenService.generateRefreshToken().getToken(),Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),loginRequest.getUsername());
	}
	
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws SubException{	
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
		return new AuthenticationResponse(token,
				refreshTokenRequest.getRefreshToken(),
				Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
				refreshTokenRequest.getUsername());
	}
	
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    
    @Transactional()
    public Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return usersRepo.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

}