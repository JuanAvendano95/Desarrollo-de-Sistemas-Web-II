package com.tiendasara.Tienda.Sara.controllers;

import com.tiendasara.Tienda.Sara.models.*;
import com.tiendasara.Tienda.Sara.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    @Autowired
    private MarkRepository markRepo;

    // --- LISTAR ---
    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products/index";
    }

    // --- CREAR (GET) ---
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("marks", markRepo.findAll());
        return "products/CreateProduct";
    }

    // --- CREAR (POST) ---
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result, Model model) {
        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "La imagen es obligatoria"));
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("marks", markRepo.findAll());
            return "products/CreateProduct";
        }

        MultipartFile image = productDto.getImageFile();
        String storageFileName = new Date().getTime() + "_" + image.getOriginalFilename();
        
        try {
            String uploadDir = "src/main/resources/static/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }

        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());
        product.setCategory(categoryRepo.findById(productDto.getCategoryId()).orElse(null));
        product.setMark(markRepo.findById(productDto.getMarkId()).orElse(null));
        product.setCreatedAt(new Date());
        product.setImageFileName(storageFileName);

        repo.save(product);
        return "redirect:/products";
    }

    // --- EDITAR (GET) ---
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);
            
            ProductDto productDto = new ProductDto();
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setAmount(product.getAmount());
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setMarkId(product.getMark().getId());
            
            model.addAttribute("productDto", productDto);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("marks", markRepo.findAll());
        } catch (Exception ex) { return "redirect:/products"; }
        return "products/EditProduct";
    }

    // --- EDITAR (POST) ---
    @PostMapping("/edit")
    public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        try {
            Product product = repo.findById(id).get();
            if (result.hasErrors()) {
                model.addAttribute("product", product);
                return "products/EditProduct";
            }

            if (!productDto.getImageFile().isEmpty()) {
                // Borrar imagen vieja
                Path oldImagePath = Paths.get("src/main/resources/static/images/" + product.getImageFileName());
                try { Files.delete(oldImagePath); } catch (Exception e) { }
                // Guardar imagen nueva
                String storageFileName = new Date().getTime() + "_" + productDto.getImageFile().getOriginalFilename();
                try (InputStream inputStream = productDto.getImageFile().getInputStream()) {
                    Files.copy(inputStream, Paths.get("src/main/resources/static/images/" + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);
            }

            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setAmount(productDto.getAmount());
            product.setCategory(categoryRepo.findById(productDto.getCategoryId()).orElse(null));
            product.setMark(markRepo.findById(productDto.getMarkId()).orElse(null));
            
            repo.save(product);
        } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }
        return "redirect:/products";
    }

    // --- BORRAR ---
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Product product = repo.findById(id).get();
            Path imagePath = Paths.get("src/main/resources/static/images/" + product.getImageFileName());
            try { Files.delete(imagePath); } catch (Exception e) { }
            repo.delete(product);
        } catch (Exception ex) { }
        return "redirect:/products";
    }
}
