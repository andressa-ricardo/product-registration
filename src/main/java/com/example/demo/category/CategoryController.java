package com.example.demo.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    Iterable<Category> getCategories(){
        Iterable<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @PostMapping("/category")
    Category posCategory(@RequestBody() CategoryBodyDto body){
        Category category = new Category(body.name());
        Category categorySave = categoryRepository.save(category);
        return categorySave;
    }

    record CategoryBodyDto(String name){

    }

}
