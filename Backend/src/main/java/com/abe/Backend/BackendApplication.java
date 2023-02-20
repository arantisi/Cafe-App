package com.abe.Backend;

import com.abe.Backend.constant.RoleEnum;
import com.abe.Backend.entity.RoleEntity;
import com.abe.Backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData( RoleRepository roleRepository) {


		return args ->

		{
			RoleEntity roleEntity1 = new RoleEntity(1, RoleEnum.USER);
			RoleEntity roleEntity2 = new RoleEntity(2, RoleEnum.MODERATOR);
			RoleEntity roleEntity3 = new RoleEntity(3,RoleEnum.ADMIN);

			roleRepository.save(roleEntity1);
			roleRepository.save(roleEntity2);
			roleRepository.save(roleEntity3);



		};

	}

}
