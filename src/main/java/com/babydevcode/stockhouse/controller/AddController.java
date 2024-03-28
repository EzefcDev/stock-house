package com.babydevcode.stockhouse.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babydevcode.stockhouse.services.CategoryService;
import com.babydevcode.stockhouse.services.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Component
public class AddController implements Initializable {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    
    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private TextField nameProduct;

    @FXML
    private TextField amountProduct;

    private final ObservableList<String> categoriesStock = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCategory();
    }

    public void saveProduct(){
        System.out.println(nameProduct.getText() + amountProduct.getText());
        
    }

    private void listCategory() {
        System.out.println("Lista");
        categoriesStock.setAll("hol","mate");
        comboCategory.setItems(categoriesStock);
        nameProduct.setText("Hola");
    }

    
}
