package com.babydevcode.stockhouse.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Category;
import com.babydevcode.stockhouse.entities.Product;
import com.babydevcode.stockhouse.services.CategoryService;
import com.babydevcode.stockhouse.services.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class IndexController implements Initializable {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @FXML
    private ComboBox<String> selectCategory;

    @FXML
    private TableView<ProductDTO> stockTable;

    @FXML
    private TableColumn<ProductDTO, String> columnName;

    @FXML
    private TableColumn<ProductDTO, Integer> columnAmount;

    @FXML
    private TableColumn<ProductDTO,String> columnCategory;

    private final ObservableList<ProductDTO> stockList = FXCollections.observableArrayList();

    private final ObservableList<String> categoriesList = FXCollections.observableArrayList();

    private List<ProductDTO> listProducts = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stockTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurationColumns();
        listProducts(listProducts);
        listCategories();
    }
    
    private void configurationColumns() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amountProduct"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void listProducts(List<ProductDTO> listProducts) {
        stockList.clear();
        if (listProducts.size() > 0) {
            stockList.addAll(listProducts);
            stockTable.setItems(stockList); 
        } else {
            stockList.addAll(productService.getProducts());
            stockTable.setItems(stockList);
        }
    }

    private void listCategories() {
        categoriesList.addAll(categoryService.allCategories());
        selectCategory.setItems(categoriesList);
    }
    
    public void getCategory(){
        String category = selectCategory.getSelectionModel().getSelectedItem();
        List<ProductDTO> listProducts = productService.getProductsByCategory(category);
        listProducts(listProducts);
    }

    public void clearCategory(){
        selectCategory.getSelectionModel().clearSelection();
        listProducts(productService.getProducts());
    }
}
