package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo;

import java.util.Optional;

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
		oneToMany();
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
