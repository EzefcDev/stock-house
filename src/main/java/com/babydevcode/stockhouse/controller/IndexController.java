package com.babydevcode.stockhouse.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Product;
import com.babydevcode.stockhouse.services.CategoryService;
import com.babydevcode.stockhouse.services.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class IndexController implements Initializable {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @FXML
    private ComboBox<String> selectCategory;

    @FXML
    private ComboBox<String> selectCategoryProduct;

    @FXML
    private TableView<ProductDTO> stockTable;

    @FXML
    private TableColumn<ProductDTO, String> columnName;

    @FXML
    private TableColumn<ProductDTO, Integer> columnAmount;

    @FXML
    private TableColumn<ProductDTO,String> columnCategory;

    @FXML
    private TextField nameText;

    @FXML
    private TextField searchText;

    @FXML
    private TextField amountText;

    @FXML
    private Button addButton;

    private final ObservableList<ProductDTO> stockList = FXCollections.observableArrayList();

    private final ObservableList<String> categoriesList = FXCollections.observableArrayList();

    private List<ProductDTO> listProducts = new ArrayList<>();

    private ProductDTO productStock;

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
        selectCategoryProduct.setItems(categoriesList);
    }
    
    @FXML
    public void getCategory(){
        String category = selectCategory.getSelectionModel().getSelectedItem();
        List<ProductDTO> listProducts = productService.getProductsByCategory(category);
        listProducts(listProducts);
    }

    @FXML
    public void clearCategory(){
        selectCategory.getSelectionModel().clearSelection();
        listProducts(productService.getProducts());
    }

    @FXML
    public void addItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(nameText.getText());
        productDTO.setAmountProduct(Integer.valueOf(amountText.getText()));
        productDTO.setCategory(selectCategoryProduct.getSelectionModel().getSelectedItem());
        productService.addProduct(productDTO);
        refreshProducto();
    }

    @FXML
    public void updateItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        productService.updateProductAmount(nameText.getText(),Integer.valueOf(amountText.getText()));
        refreshProducto();
    }

    @FXML
    public void deleteItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        productService.deleteProduct(nameText.getText());
        refreshProducto();
    }
    
    @FXML
    public void searchProduct(){
        List<ProductDTO> listProducts = productService.getProductsByName(searchText.getText());
        listProducts(listProducts);
    }

    public void selectedItemStock(){
        productStock = stockTable.getSelectionModel().getSelectedItem();
        nameText.setText(productStock.getNameProduct());
        amountText.setText(Integer.toString(productStock.getAmountProduct()));
        selectCategoryProduct.getSelectionModel().select(productStock.getCategory());
    }

    private void refreshProducto(){
        clearCategory();
        nameText.setText(null);
        amountText.setText(null);
        selectCategoryProduct.getSelectionModel().clearSelection();
    }

    @FXML
    public void nextPage(){
        
    }

    @FXML
    public void afterPage(){

    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
