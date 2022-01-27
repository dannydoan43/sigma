package com.doan.sigma.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

//import com.doan.subscription.entity.Users;	//wrong import dumbass
import com.doan.sigma.exception.SubException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//45:00 at freecodecamp
@Service
public class JwtProvider {
	
	private KeyStore keyStore;	//do i need to autowire this? probably not cause he doesnt have final on his
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;
	
	@PostConstruct
	public void init() throws SubException {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/subkey.jks");
			keyStore.load(resourceAsStream,"mapled".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
			throw new SubException("error occured while loading keystore");
		}
	}

	public String generateToken(Authentication auth) throws SubException{
		User principal = (User) auth.getPrincipal();	//44:33 is imporatnt codecamp in case this fails
		return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
//		return Jwts.builder()		//what the fuck was the difference?
//				.setSubject(principal.getUsername())
//				.setIssuedAt(Date.from(Instant.now()))
//				.signWith(getPrivateKey())
//				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)));		haha difference was u had a semicolon here
//				.compact();
	}
	//somewhere in generateToken .claim("scope","ROLE_USER"
    public String generateTokenWithUserName(String username) throws SubException{
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

	public PrivateKey getPrivateKey() throws SubException{	//private to public
		try {
			return (PrivateKey) keyStore.getKey("subkey", "mapled".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SubException("error occured getting private key from keystore");
		}
	}
	
	public PublicKey getPublicKey() throws SubException {
		try {
			return keyStore.getCertificate("subkey").getPublicKey();	//alias not key name (no .jks)
		} catch (KeyStoreException e) {
			throw new SubException("error occured getting public key from keystore");
		}
	}
	
	public boolean validateToken(String jwt) throws SubException{
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}
	
	public String getUsernameFromJwt(String token) throws SubException {
		Claims claim = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claim.getSubject();
	}

	public long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;		//was returning 0
	}
}