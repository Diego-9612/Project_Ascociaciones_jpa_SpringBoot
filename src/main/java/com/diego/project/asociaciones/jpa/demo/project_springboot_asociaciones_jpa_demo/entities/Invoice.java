package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String description;
    private double total;

    public Invoice() {
    }

    public Invoice(String description, double total) {
        this.description = description;
        this.total = total;
    }

    public Invoice(String description, double total, Client client) {
        this.description = description;
        this.total = total;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", description=" + description +
                ", total=" + total + "}";
    }

}
