package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories.*;
import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.*;

@SpringBootApplication
public class ProjectSpringbootAsociacionesJpaDemoApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringbootAsociacionesJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyFindById();
		;
	}

	public void manyToManyFindById() {

		Optional<Students> optionalStudent1 = studentRepository.findById(3L);
		Optional<Students> optionalStudent2 = studentRepository.findById(5L);

		Students student1 = optionalStudent1.get();
		Students student2 = optionalStudent2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

	}

	public void manyToMany() {

		Students student1 = new Students("Felipe", "Garcia");
		Students student2 = new Students("Diego", "Guerra");
		Students student3 = new Students("Roman", "Perez");
		Students student4 = new Students("Jesus", "Cacho");

		Course course1 = new Course("Java master", "Diego Guerrero");
		Course course2 = new Course("Bases de datos con mySql", "Benito Camelas");
		Course course3 = new Course("SpringJava", "Lucho Portuano");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course3));
		student3.setCourses(Set.of(course3, course1));
		student4.setCourses(Set.of(course1, course2, course3));

		studentRepository.saveAll(Set.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

	}

	public void oneToOneBidireccionalFindById() {

		Optional<Client> optionalClient = clientRepository.findOne(2L);
		optionalClient.ifPresent(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 4567);

			client.setClientDetails(clientDetails);

			clientRepository.save(client);

			System.out.println(client);
		});
	}

	public void oneToOneBidireccional() {

		Client client = new Client("Pepito", "Perez");

		ClientDetails clientDetails = new ClientDetails(true, 4567);

		client.setClientDetails(clientDetails);

		clientRepository.save(client);

		System.out.println(client);

	}

	public void oneToOneFindById() {
		ClientDetails clientDetails = new ClientDetails(true, 6000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> optionalClient = clientRepository.findOne(2L);
		optionalClient.ifPresent(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
			System.out.println(client);
		});

	}

	public void oneToOne() {
		ClientDetails clientDetails = new ClientDetails(true, 6000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Manuel", "Rosero");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);

	}

	/**
	 * @Transactional
	 *                public void oneToOne() {
	 *                Client client = new Client("Manuel", "Rosero");
	 *                clientRepository.save(client);
	 * 
	 *                ClientDetails clientDetails = new ClientDetails(true, 6000);
	 *                clientDetails.setClient(client);
	 * 
	 *                clientDetailsRepository.save(clientDetails);
	 *                }
	 */
	@Transactional
	public void oneToManyInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {

			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			clientRepository.save(client);

			System.out.println(client);
		});
	}

	@Transactional
	public void oneToManyInvoiceBidireccional() {
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client);

			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(address2);
				clientRepository.save(c);
				System.out.println(c);
			});
		});

	}

	@Transactional
	public void removeAddress() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void oneToManyFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client);

			System.out.println(client);
		});

	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {

		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient() {

		Optional<Client> optionalClient = clientRepository.findById(1L);

		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}

}
