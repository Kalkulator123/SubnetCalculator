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
    int index;
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
    Calculator calculator;
    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        ClassA.setToggleGroup(group);
        ClassB.setToggleGroup(group);
        ClassC.setToggleGroup(group);
        ClassC.setSelected(true);
        calculator = new Calculator(scene, "C", "192.168.0.1", 0);
        SubnetMask.setValue("255.255.255.0");
        setValues(calculator);
        calculator.ended = true;
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            changeSubnet(0);
            calculator.ended = false;
            calculator = new Calculator(scene,
                    ClassA.isSelected() ? "A" : (ClassB.isSelected() ? "B" : "C"),
                    IPAddress.getText(), SubnetMask.getSelectionModel().getSelectedIndex());
            setValues(calculator);
            calculator.ended = true;
        });
        SubnetMask.valueProperty().addListener((observableValue, o, t1) -> {
            changeSubnet(SubnetMask.getSelectionModel().getSelectedIndex());

        });
        SubnetBits.valueProperty().addListener((observableValue, o, t1) -> {
            changeSubnet(SubnetBits.getSelectionModel().getSelectedIndex());
        });
        MaskBits.valueProperty().addListener((observableValue, o, t1) -> {
            changeSubnet(MaskBits.getSelectionModel().getSelectedIndex());
        });
        MaximumSubnets.valueProperty().addListener((observableValue, o, t1) -> {
            changeSubnet(MaximumSubnets.getSelectionModel().getSelectedIndex());
        });
        HostsPerSubnet.valueProperty().addListener((observableValue, o, t1) -> {
            changeSubnet(HostsPerSubnet.getSelectionModel().getSelectedIndex());
        });
        SubmitButton.setOnAction(event -> {
            calculator.ended = false;
            calculator = new Calculator(scene,
                    ClassA.isSelected() ? "A" : (ClassB.isSelected() ? "B" : "C"),
                    IPAddress.getText(), calculator.id);
            setValues(calculator);
            calculator.ended = true;
        });
    }

    private void changeSubnet(int i) {
        if(calculator.ended)
            index = i;
        calculator.setSubnetByIndex(index);
        SubnetBits.getSelectionModel().select(index);
        SubnetMask.getSelectionModel().select(index);
        MaskBits.getSelectionModel().select(index);
        MaximumSubnets.getSelectionModel().select(index);
        HostsPerSubnet.getSelectionModel().select(index);

    }

    private void setValues(Calculator calculator){
        SubnetMask.setItems(FXCollections.observableList(calculator.getSubnetMaskList()));
        SubnetMask.setValue(calculator.getValue(CalculatorValues.SubnetMask));
        SubnetBits.setItems(FXCollections.observableList(calculator.getSubnetBitsList()));
        SubnetBits.setValue(calculator.getValue(CalculatorValues.SubnetBits));
        MaskBits.setItems(FXCollections.observableList(calculator.getMaskBitsList()));
        MaskBits.setValue(calculator.getValue(CalculatorValues.MaskBits));
        SubnetID.setText(calculator.getValue(CalculatorValues.SubnetID));
        MaximumSubnets.setItems(FXCollections.observableList(calculator.getMaximumSubnetsList()));
        MaximumSubnets.setValue(calculator.getValue(CalculatorValues.MaximumSubnets));
        HostsPerSubnet.setItems(FXCollections.observableList(calculator.getHostsPerSubnetList()));
        HostsPerSubnet.setValue(calculator.getValue(CalculatorValues.HostsPerSubnet));
        IPAddress.setText(calculator.getValue(CalculatorValues.IPAddress));
        FirstOctetRange.setText(calculator.getValue(CalculatorValues.FirstOctetRange));
        HexIPAdress.setText(calculator.getValue(CalculatorValues.HexIPAddress));
        WildcardMask.setText(calculator.getValue(CalculatorValues.WildCardMask));
        HostAdressRange.setText(calculator.getValue(CalculatorValues.HostAddressRange));
        BroadcastAdress.setText(calculator.getValue(CalculatorValues.BroadcastAddress));
        SubnetBitmap.setText(calculator.getValue(CalculatorValues.SubnetBitmap));
    }

}