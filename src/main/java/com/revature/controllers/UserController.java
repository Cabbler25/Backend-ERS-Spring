package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.ClientUser;
import com.revature.models.ReturnableUser;
import com.revature.models.User;
import com.revature.services.JWTService;
import com.revature.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.PATCH,
		RequestMethod.GET })
@RequestMapping("user")
public class UserController {
	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping(value = "/get/all")
	public ResponseEntity<Object> getAllUsers(@RequestHeader("Authorization") String token) {
		if (!JWTService.checkAuthorization(token, "admin")) {
			return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
		}
		List<ReturnableUser> list = userService.getAllUsers();
		return list == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody User user) {
		ClientUser userClient = userService.login(user);
		return userClient == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<>(userClient, HttpStatus.OK);
	}

	@PatchMapping(value = "/update")
	public ResponseEntity<Object> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
		if (!JWTService.checkAuthorization(token, "admin")) {
			if (!JWTService.checkAuthByID(token, user.getId()))
				return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
		}
		return userService.updateUser(user) ? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
