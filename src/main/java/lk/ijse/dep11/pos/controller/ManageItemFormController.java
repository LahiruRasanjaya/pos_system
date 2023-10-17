package lk.ijse.dep11.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep11.pos.db.ItemDataAccess;
import lk.ijse.dep11.pos.tm.Item;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ManageItemFormController {
    public AnchorPane root;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<Item> tblItems;
    public JFXTextField txtUnitPrice;

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(primaryStage::sizeToScene);
    }
    public void initialize(){
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        btnDelete.setDisable(true);
        btnSave.setDefaultButton(true);
        try {
            tblItems.getItems().addAll(ItemDataAccess.getAllItems());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load items, try later").show();
            e.printStackTrace();
        }
        Platform.runLater(txtCode::requestFocus);
        tblItems.getSelectionModel().selectedItemProperty().addListener((ov, prev, cur)->{
            if (cur == null){
                btnSave.setText("SAVE");
                btnDelete.setDisable(true);
                txtCode.setDisable(false);
            }else{
                btnSave.setText("UPDATE");
                btnDelete.setDisable(false);
                txtCode.setText(cur.getCode());
                txtCode.setDisable(true);
                txtDescription.setText(cur.getDescription());
                txtQtyOnHand.setText(cur.getQty()  + "");
                txtUnitPrice.setText(cur.getUnitPrice() + "");
            }
        });
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }
}
