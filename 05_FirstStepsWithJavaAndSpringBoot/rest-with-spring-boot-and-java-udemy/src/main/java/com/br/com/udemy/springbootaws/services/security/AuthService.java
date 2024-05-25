package com.br.com.udemy.springbootaws.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.com.udemy.springbootaws.repository.security.UserRepository;
import com.br.com.udemy.springbootaws.security.jwt.JwtTokenProvider;
import com.br.com.udemy.springbootaws.vo.v1.security.AccountCredentialsVO;
import com.br.com.udemy.springbootaws.vo.v1.security.TokenVO;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserRepository repository;

	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = repository.findByUsername(username);

			var tokenResponse = new TokenVO();

			if (user == null) {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());

			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = repository.findByUsername(username);
		var tokenResponse = new TokenVO();
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		tokenResponse = tokenProvider.refreshToken(refreshToken);

		return ResponseEntity.ok(tokenResponse);
	}

}
