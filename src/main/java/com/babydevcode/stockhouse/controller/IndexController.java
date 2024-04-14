package com.babydevcode.stockhouse.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.services.CategoryService;
import com.babydevcode.stockhouse.services.ProductService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
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

    @FXML
    private Pagination paginationProduct;

    private final ObservableList<ProductDTO> stockList = FXCollections.observableArrayList();

    private final ObservableList<String> categoriesList = FXCollections.observableArrayList();

    private Page<ProductDTO> listProductsPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stockTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        paginationProduct.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int currentPageIndex = newValue.intValue();
                listProducts("",currentPageIndex,"") ;
            }
        });
        configurationColumns();
        listProducts("",0,"") ;
        listCategories();
    }
    
    private void configurationColumns() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amountProduct"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void listProducts(String category, Integer page, String searching) {
        stockList.clear();
        listProductsPage = productService.getProductsByCategoryOrSearch(category, searching, page);
        paginationProduct.setPageCount(listProductsPage.getTotalPages());
        paginationProduct.setCurrentPageIndex(listProductsPage.getNumber());
        stockList.addAll(listProductsPage.getContent());
        stockTable.setItems(stockList); 
    }

    private void listCategories() {
        categoriesList.addAll(categoryService.allCategories());
        selectCategory.setItems(categoriesList);
        selectCategoryProduct.setItems(categoriesList);
    }
    
    @FXML
    public void getCategory(){
        String categoryExist = selectCategory.getSelectionModel().getSelectedItem() ;
        String categoryName = categoryExist != null ? categoryExist : "";
        searchText.setText("");
        listProducts(categoryName, 0, "");
    }

    @FXML
    public void searchProduct(){
        String searchName = searchText.getText();
        selectCategory.getSelectionModel().clearSelection();
        listProducts("", 0, searchName);
    }

    @FXML
    public void clearCategory(){
        selectCategory.getSelectionModel().clearSelection();
        searchText.setText("");
        listProducts("",0,"");
    }

    @FXML
    public void addItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        if (Boolean.TRUE.equals(productService.getProduct(nameText.getText()))) {
            mostrarMensaje("Producto existe", "El producto ya existe");
            return;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(nameText.getText());
        productDTO.setAmountProduct(Integer.valueOf(amountText.getText()));
        productDTO.setCategory(selectCategoryProduct.getSelectionModel().getSelectedItem());
        productService.addProduct(productDTO);
        mostrarMensaje("Agregado", "El producto fue agregado a la lista");
        refreshProducto();
    }

    @FXML
    public void updateItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        if (Boolean.FALSE.equals(productService.getProduct(nameText.getText()))) {
            mostrarMensaje("Producto no existe", "El producto no existe");
            return;
        }
        productService.updateProductAmount(nameText.getText(),Integer.valueOf(amountText.getText()));
        mostrarMensaje("Actualización", "El producto se actualizo correctamente");
        refreshProducto();
    }

    @FXML
    public void deleteItemStock(){
        if ( nameText.getText().isBlank() || amountText.getText().isBlank() || selectCategoryProduct.getSelectionModel().getSelectedItem().isBlank()) {
            mostrarMensaje("Campos incompletos", "Todos los campos son necesarios");
            return;
        }
        if (Boolean.FALSE.equals(productService.getProduct(nameText.getText()))) {
            mostrarMensaje("Producto no existe", "El producto no existe");
            return;
        }
        productService.deleteProduct(nameText.getText());
        mostrarMensaje("Eliminación", "Producto eliminado");
        refreshProducto();
    }
    
    public void selectedItemStock(){
        ProductDTO productStock = stockTable.getSelectionModel().getSelectedItem();
        nameText.setText(productStock.getNameProduct());
        amountText.setText(Integer.toString(productStock.getAmountProduct()));
        selectCategoryProduct.getSelectionModel().select(productStock.getCategory());
    }

    private void refreshProducto(){
        nameText.setText("");
        amountText.setText("");
        selectCategoryProduct.getSelectionModel().clearSelection();
        clearCategory();
    }

    @FXML
    public void addOneProduct(){
        int amount = 0 ;
        if (!amountText.getText().isEmpty()) {
            amount = Integer.parseInt(amountText.getText());
        }
        int amountSend = amount + 1;
        amountText.setText(String.valueOf(amountSend));
    }

    @FXML
    public void restOneProduct(){
        if (!amountText.getText().isEmpty() && Integer.parseInt(amountText.getText()) > 0 ) {
           int amount = Integer.parseInt(amountText.getText());
           int amountSend = amount - 1;
           amountText.setText(String.valueOf(amountSend));
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
