package com.matsindect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matsindect.exception.ResourceNotFoundException;
import com.matsindect.model.User;
import com.matsindect.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	// Get Users
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	// Get User by Id
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException ("User with id:"+ userId +"not found"));
		return ResponseEntity.ok().body(user);	
	}
	// Post User
	
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	// Update User
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails ) throws ResourceNotFoundException{
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException ("User with id:"+ userId +"not found"));
		
		user.setEmail(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		
		return ResponseEntity.ok(this.userRepository.save(user));
		
	} 
	// Delete User
	
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)  throws ResourceNotFoundException{
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException ("User with id:"+ userId +"not found"));
		
		this.userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}

}
