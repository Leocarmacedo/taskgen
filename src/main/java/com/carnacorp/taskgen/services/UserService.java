package com.carnacorp.taskgen.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.UserDTO;
import com.carnacorp.taskgen.dto.UserMinDTO;
import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.entities.Role;
import com.carnacorp.taskgen.entities.User;
import com.carnacorp.taskgen.projections.UserDetailsProjection;
import com.carnacorp.taskgen.repositories.DepartmentRepository;
import com.carnacorp.taskgen.repositories.RoleRepository;
import com.carnacorp.taskgen.repositories.UserRepository;
import com.carnacorp.taskgen.services.exceptions.DatabaseException;
import com.carnacorp.taskgen.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;

	}

	@Transactional(readOnly = true)
	public UserMinDTO findById(Long id) {

		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new UserMinDTO(user);
	}

	@Transactional(readOnly = true)
	public List<UserMinDTO> findAll() {
		List<User> result = repository.findAll();
		return result.stream().map(x -> new UserMinDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {
		User entity = new User();
		Role role = roleRepository.findById((long) 1)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		this.copyDtoToEntity(dto, entity);
		entity.addRole(role);
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public @Valid UserDTO update(Long id, @Valid UserDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			this.copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(UserDTO dto, User entity) {

		Department department = departmentRepository.findById(dto.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));

		entity.setDepartment(department);

	}

}
