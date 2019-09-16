package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.revature.models.ClientUser;
import com.revature.models.JoinUser;
import com.revature.models.ReturnableUser;
import com.revature.models.User;
import com.revature.repositories.ReturnUserRepository;
import com.revature.repositories.UserRepository;

@Service
public class UserService {
	UserRepository<User> userRepository;
	ReturnUserRepository<JoinUser> returnUserRepository;

	@Autowired
	public UserService(UserRepository<User> userRepository, ReturnUserRepository<JoinUser> returnUserRepository) {
		super();
		this.userRepository = userRepository;
		this.returnUserRepository = returnUserRepository;
	}

	public ClientUser login(User user) {
		Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
		if (optionalUser.isPresent()) {
			User foundUser = optionalUser.get();
			if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
				String token = JWTService.createJWT(String.valueOf(foundUser.getId()), foundUser.getUsername(),
						foundUser.getEmail(), foundUser.getRole(), 0);
				JWTService.getTokenRole(token);
				return new ClientUser(foundUser.getId(), foundUser.getUsername(), foundUser.getFirstName(),
						foundUser.getLastName(), foundUser.getEmail(), token, foundUser.getRole());
			}
		}
		return null;
	}

	public List<ReturnableUser> getAllUsers() {
		return returnUserRepository.findAll();
	}

	public boolean updateUser(User user) {
		if (user.getPassword() != null) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		} else {
			String hashedPassword = getHashedPassword(user.getId());
			if (hashedPassword == null)
				return false;
			user.setPassword(hashedPassword);
		}

		return userRepository.save(user) != null;
	}

	private String getHashedPassword(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get().getPassword();
		} else {
			return null;
		}
	}
}
