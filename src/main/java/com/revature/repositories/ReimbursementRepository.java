package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.revature.models.JoinUser;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;

@Service
public interface ReimbursementRepository<P> extends JpaRepository<Reimbursement, Integer> {

	List<Reimbursement> findByAuthor(JoinUser author);

	List<Reimbursement> findByStatus(ReimbursementStatus status);

}
