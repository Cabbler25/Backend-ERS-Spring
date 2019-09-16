package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.JoinUser;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.services.JWTService;
import com.revature.services.ReimbursementService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.PATCH })
@RequestMapping("reimbursements")
public class ReimbursementController {
	ReimbursementService reimbursementService;

	@Autowired
	public ReimbursementController(ReimbursementService reimbursementService) {
		super();
		this.reimbursementService = reimbursementService;
	}

	@GetMapping(value = "/get/all")
	public ResponseEntity<Object> getAll(@RequestHeader("Authorization") String token) {
		if (!JWTService.checkAuthorization(token, "finance-manager")) {
			return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
		}

		List<Reimbursement> resultsList = reimbursementService.getAll();
		return new ResponseEntity<>(resultsList, resultsList == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
	}

	@GetMapping(value = "/author/{author}")
	public ResponseEntity<Object> findByAuthor(@RequestHeader("Authorization") String token,
			@PathVariable("author") int author) {
		if (!JWTService.checkAuthorization(token, "finance-manager")) {
			if (!JWTService.checkAuthByID(token, author)) {
				return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
			}
		}

		JoinUser user = new JoinUser();
		user.setId(author);
		List<Reimbursement> resultsList = reimbursementService.findByAuthor(user);
		return new ResponseEntity<>(resultsList, resultsList == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
	}

	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> findByStatus(@RequestHeader("Authorization") String token,
			@PathVariable("status") int status) {
		if (!JWTService.checkAuthorization(token, "finance-manager")) {
			return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
		}

		ReimbursementStatus rmbmntStatus = new ReimbursementStatus();
		rmbmntStatus.setId(status);
		List<Reimbursement> resultsList = reimbursementService.findByStatus(rmbmntStatus);
		return new ResponseEntity<>(resultsList, resultsList == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
	}

	@PatchMapping(value = "/update")
	public ResponseEntity<Object> updateUser(@RequestHeader("Authorization") String token,
			@RequestBody Reimbursement reimbursement) {
		if (!JWTService.checkAuthorization(token, "finance-manager")) {
			return new ResponseEntity<>("You are not authorized for this operation!", HttpStatus.UNAUTHORIZED);
		}
		return reimbursementService.updateReimbursement(reimbursement) ? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
