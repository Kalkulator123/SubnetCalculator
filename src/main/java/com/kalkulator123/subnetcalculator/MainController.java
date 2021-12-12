package com.kalkulator123.subnetcalculator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;


public class MainController {
    Scene scene = SubnetCalculator.scene;
    @FXML
    private TextField IPAddress;
    @FXML
    private ComboBox SubnetMask;
    @FXML
    private ComboBox SubnetBits;
    @FXML
    private ComboBox MaximumSubnets;
    @FXML
    private TextField FirstOctetRange;
    @FXML
    private TextField WildcardMask;
    @FXML
    private ComboBox MaskBits;
    @FXML
    private ComboBox HostsPerSubnet;
    @FXML
    private TextField HostAdressRange;
    @FXML
    private TextField SubnetID;
    @FXML
    private TextField BroadcastAdress;
    @FXML
    private TextField SubnetBitmap;
    @FXML
    private TextField HexIPAdress;
    @FXML
    private RadioButton ClassA;
    @FXML
    private RadioButton ClassB;
    @FXML
    private RadioButton ClassC;
    @FXML
    private Button SubmitButton;
    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        ClassA.setToggleGroup(group);
        ClassB.setToggleGroup(group);
        ClassC.setToggleGroup(group);
        ClassC.setSelected(true);
        Calculator calculator = new Calculator(scene, "C", "192.168.0.1", 0);
        SubnetMask.setValue("255.255.255.0");
        setValues(calculator);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                Calculator calculator = new Calculator(scene,
                        ClassA.isSelected() ? "A" : (ClassB.isSelected() ? "B" : "C"),
                        IPAddress.getText(), SubnetMask.getSelectionModel().getSelectedIndex());
                setValues(calculator);
            }
        });
        SubmitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(SubnetMask.getSelectionModel().getSelectedIndex());
                Calculator calculator = new Calculator(scene,
                        ClassA.isSelected() ? "A" : (ClassB.isSelected() ? "B" : "C"),
                        IPAddress.getText(), SubnetMask.getSelectionModel().getSelectedIndex());
                setValues(calculator);
            }
        });
    }

    private void setValues(Calculator calculator){
        SubnetMask.setItems(FXCollections.observableList(calculator.getSubnetMaskList()));
        SubnetBits.setItems(FXCollections.observableList(calculator.getSubnetBitsList()));
        SubnetBits.setValue(calculator.getValue(CalculatorValues.SubnetBits));
        MaskBits.setItems(FXCollections.observableList(calculator.getMaskBitsList()));
        MaskBits.setValue(calculator.getValue(CalculatorValues.MaskBits));
        MaximumSubnets.setItems(FXCollections.observableList(calculator.getMaximumSubnetsList()));
        MaximumSubnets.setValue(calculator.getValue(CalculatorValues.MaximumSubnets));
        HostsPerSubnet.setItems(FXCollections.observableList(calculator.getHostsPerSubnetList()));
        HostsPerSubnet.setValue(calculator.getValue(CalculatorValues.HostsPerSubnet));

        FirstOctetRange.setText(calculator.getValue(CalculatorValues.FirstOctetRange));
        HexIPAdress.setText(calculator.getValue(CalculatorValues.HexIPAddress));
        WildcardMask.setText(calculator.getValue(CalculatorValues.WildCardMask));
        HostAdressRange.setText(calculator.getValue(CalculatorValues.HostAddressRange));
        SubnetID.setText(calculator.getValue(CalculatorValues.SubnetID));
        BroadcastAdress.setText(calculator.getValue(CalculatorValues.BroadcastAddress));
        SubnetBitmap.setText(calculator.getValue(CalculatorValues.SubnetBitmap));
    }

}