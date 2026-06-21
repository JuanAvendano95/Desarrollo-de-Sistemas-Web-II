package com.tiendasara.Tienda.Sara.models;



import java.util.Date;

import jakarta.persistence.*;



@Entity

@Table(name = "productos")

public class Product {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;



    @Column(name = "description")

    private String description;



    @Column(name = "precio")

    private double price;



    @Column(name = "cantidad")

    private int amount;



    @Column(name = "created_at")

    private Date createdAt;



    @Column(name = "image_file_name")

    private String imageFileName;



    @ManyToOne

    @JoinColumn(name = "id_categoria")

    private Category category;



    @ManyToOne

    @JoinColumn(name = "id_marca")

    private Mark mark;



    // Getters y Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getImageFileName() { return imageFileName; }

    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public Mark getMark() { return mark; }

    public void setMark(Mark mark) { this.mark = mark; }

}
