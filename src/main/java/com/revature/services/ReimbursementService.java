package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.JoinUser;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.repositories.ReimbursementRepository;

@Service
public class ReimbursementService {
	ReimbursementRepository<Reimbursement> reimbursementRepository;

	@Autowired
	public ReimbursementService(ReimbursementRepository<Reimbursement> reimbursementRepository) {
		super();
		this.reimbursementRepository = reimbursementRepository;
	}

	public List<Reimbursement> findByAuthor(JoinUser author) {
		return reimbursementRepository.findByAuthor(author);
	}

	public List<Reimbursement> findByStatus(ReimbursementStatus status) {
		return reimbursementRepository.findByStatus(status);
	}

	public List<Reimbursement> getAll() {
		return reimbursementRepository.findAll();
	}

	public boolean updateReimbursement(Reimbursement reimbursement) {
		return reimbursementRepository.save(reimbursement) != null;
	}
}
