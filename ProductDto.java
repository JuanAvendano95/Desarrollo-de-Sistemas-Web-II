package com.tiendasara.Tienda.Sara.models;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.*;

public class ProductDto {

    @NotEmpty(message = "La descripción es obligatoria")
    private String description;

    @Min(0)
    private double price;

    @Min(0)
    private int amount;

    // Estos IDs son los que vienen de tus menús desplegables (<select>) en el HTML
    private int categoryId;
    private int markId;

    private MultipartFile imageFile;

    // Getters y Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getMarkId() { return markId; }
    public void setMarkId(int markId) { this.markId = markId; }

    public MultipartFile getImageFile() { return imageFile; }
    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }
}
