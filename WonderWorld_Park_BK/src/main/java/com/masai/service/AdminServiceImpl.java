package com.masai.service;

import java.time.LocalDateTime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.masai.Exception.AdminException;

import com.masai.model.Admin;
import com.masai.model.Role;

import com.masai.repository.AdminRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
	
	private AdminRepository adminRepo;
	
	
	
	
	@Autowired
	public AdminServiceImpl(AdminRepository adminRepo) {
		super();
		this.adminRepo = adminRepo;
		
	}

	@Override
	public Admin insertAdmin(Admin admin) throws AdminException{
		log.debug("Calling findByAdminEmail method from AdminJpa Repository");
		Optional<Admin> a=adminRepo.findByAdminEmail(admin.getEmail());
		if(a.isPresent()) {
			throw new AdminException("Email id is already present");
		}
		
//		log.debug("Calling findByAdminUsername method from AdminJpa Repository");
//		Optional<Admin> au=adminRepo.findByAdminUsername(admin.getUsername());
//		if(au.isPresent()) {
//			throw new AdminException("Username is already present");
//		}
		
		log.debug("Calling save method from AdminJpa Repository");
		
		admin.setRole(Role.ADMIN);
		
		admin.setCreatedOn(LocalDateTime.now());
		Admin main=adminRepo.save(admin);
		
		log.info("Admin saved sucessfully");
		return main;
	}

	

	@Override
	public Admin deleteAdmin(Integer adminId) throws AdminException{
		log.debug("Calling findById method from AdminJpa Repository");
		Optional<Admin> a= adminRepo.findById(adminId);
		if(a.isEmpty()) {
			throw new AdminException("No admin found");
		}
		Admin admin=a.get();
		admin.setIsDeleted(true);
		admin.setDeletionTime(LocalDateTime.now());
		log.info("Admin is deleted sucessfully");
		return admin;
	}

	

}