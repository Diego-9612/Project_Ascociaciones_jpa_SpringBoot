package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo;

import java.util.Optional;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories.ClientRepository;
import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories.InvoiceRepository;
import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Address;
import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Client;
import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Invoice;

@SpringBootApplication
public class ProjectSpringbootAsociacionesJpaDemoApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringbootAsociacionesJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		removeAddressFindById();
	}

	@Transactional
	public void removeAddressFindById() {

		Optional<Client> optionalCliente = clientRepository.findOne(2L);
		optionalCliente.ifPresent(client -> {
			Address address = new Address("Parana", 1234);
			Address address2 = new Address("Palermo", 1234);

			client.setAddresses(Arrays.asList(address, address2));

			clientRepository.save(client);
			System.out.println(client);

			// Buscar el cliente con ID 11
			Optional<Client> optionalCliente2 = clientRepository.findById(2L);
			optionalCliente2.ifPresent(c -> {
					c.getAddresses().remove(address2);
					clientRepository.save(c);
					System.out.println("Dirección eliminada: " + address2);
					System.out.println("Cliente actualizado: " + c);

			});
		});
	}

	@Transactional
	public void removeAddress() {
		Client client = new Client("Juan", "Perez");

		Address address = new Address("Parana", 1234);
		Address address2 = new Address("Palermo", 1234);

		client.getAddresses().add(address2);
		client.getAddresses().add(address);

		clientRepository.save(client);
		System.out.println(client);

		// Buscar el cliente con ID 11
		Optional<Client> optionalCliente = clientRepository.findById(11L);
		optionalCliente.ifPresent(c -> {
			// Verificar si la dirección está asociada al cliente
			if (c.getAddresses().contains(address2)) {
				// Eliminar la dirección del cliente
				c.getAddresses().remove(address2);
				clientRepository.save(c);
				System.out.println("Dirección eliminada: " + address2);
				System.out.println("Cliente actualizado: " + c);
			} else {
				System.out.println("La dirección no está asociada a este cliente.");
			}
		});

	}

	@Transactional
	public void oneToManyFindById() {

		Optional<Client> optionalCliente = clientRepository.findById(2L);
		optionalCliente.ifPresent(client -> {
			Address address = new Address("Parana", 1234);
			Address address2 = new Address("Palermo", 1234);

			client.setAddresses(Arrays.asList(address, address2));

			clientRepository.save(client);
			System.out.println(client);

		});
	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Juan", "Perez");

		Address address = new Address("Parana", 1234);
		Address address2 = new Address("Palermo", 1234);

		client.getAddresses().add(address2);
		client.getAddresses().add(address);

		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {

		Client client = new Client("Jhon", "Moncayo");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Compras de Accesorios para el Hogar ", 40.800);
		invoice.setClient(client);
		Invoice invoiceBD = invoiceRepository.save(invoice);
		System.out.println(invoiceBD);

	}

	@Transactional
	public void manyToOne2() {

		Optional<Client> optionalCliente = clientRepository.findById(1L);
		if (optionalCliente.isPresent()) {
			Client client = optionalCliente.orElseThrow();
			Invoice invoice = new Invoice("Compras de Accesorios para el Hogar ", 60.700);
			invoice.setClient(client);
			Invoice invoiceBD = invoiceRepository.save(invoice);
			System.out.println(invoiceBD);

		}

	}

}
