package com.revature.services;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.revature.models.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTService {

	public static String createJWT(String id, String username, String email, Role role, long ttlMillis) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(System.getenv("A_SECRET_KEY"));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		Claims claims = Jwts.claims();
		claims.setId(id).setIssuedAt(now).setSubject(username).setAudience(email).put("role", role);
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setClaims(claims).signWith(signingKey, signatureAlgorithm);

		// if it has been specified, let's add the expiration
		if (ttlMillis > 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public static boolean checkAuthByID(String token, int id) {
		try {
			Jws<Claims> parsedToken = Jwts.parser().setSigningKey(System.getenv("A_SECRET_KEY")).parseClaimsJws(token);
			if (Integer.parseInt(parsedToken.getBody().getId()) == id)
				return true;
			else
				return false;
		} catch (JwtException e) {
			return false;
		}
	}

	public static Role getTokenRole(String token) {
		try {
			Jws<Claims> parsedToken = Jwts.parser().setSigningKey(System.getenv("A_SECRET_KEY")).parseClaimsJws(token);
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) parsedToken.getBody().get("role");
			return new Role(Integer.parseInt(map.get("id").toString()), map.get("role").toString());
		} catch (JwtException e) {
			return null;
		}
	}

	public static boolean checkAuthorization(String token, String requiredRole) {
		try {
			Role userRole = getTokenRole(token);
			if (userRole.getRole().equals(requiredRole) || userRole.getId() == 1) {
				return true;
			}
			return false;
		} catch (JwtException e) {
			System.out.println(e);
			return false;
		}

	}

	public static boolean checkAuthByUsername(String token, String un) {
		try {
			Jws<Claims> parsedToken = Jwts.parser().setSigningKey(System.getenv("A_SECRET_KEY")).parseClaimsJws(token);
			if (parsedToken.getBody().getSubject().equals(un))
				return true;
			else
				return false;

		} catch (JwtException e) {
			System.out.println(e);
			return false;
		}

	}

}
