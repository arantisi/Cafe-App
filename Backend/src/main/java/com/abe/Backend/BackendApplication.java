package com.abe.Backend;

import com.abe.Backend.constant.RoleEnum;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.entity.RoleEntity;
import com.abe.Backend.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData( RoleRepository roleRepository) {


		return args ->

		{
			RoleEntity roleEntity1 = new RoleEntity(1, RoleEnum.ROLE_USER);
			RoleEntity roleEntity2 = new RoleEntity(2, RoleEnum.ROLE_MODERATOR);
			RoleEntity roleEntity3 = new RoleEntity(3,RoleEnum.ROLE_ADMIN);

			roleRepository.save(roleEntity1);
			roleRepository.save(roleEntity2);
			roleRepository.save(roleEntity3);
			SignUpRequestDTO object = new SignUpRequestDTO();
			Set<String> s = new HashSet<>(Arrays.asList("ROLE_USER","ROLE_ADMIN"));
			object.setRoleEntitySet(s);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(object);
			System.out.println(json);

		};

	}

}
