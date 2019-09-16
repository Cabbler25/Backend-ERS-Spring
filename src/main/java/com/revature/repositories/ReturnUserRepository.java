package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.ReturnableUser;

public interface ReturnUserRepository<P> extends JpaRepository<ReturnableUser, Integer> {
}