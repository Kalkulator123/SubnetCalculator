package com.kalkulator123.subnetcalculator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainController {
    int index;
    @FXML
    private TextField IPAddress, FirstOctetRange, HostAddressRange, SubnetID, BroadcastAddress, SubnetBitmap, HexIPAddress, WildcardMask;
    @FXML
    private ComboBox SubnetMask, SubnetBits, MaximumSubnets, MaskBits, HostsPerSubnet;
    @FXML
    private RadioButton ClassA, ClassB, ClassC;

    Calculator calculator;

    private boolean ended = false;
    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        ClassA.setToggleGroup(group);
        ClassB.setToggleGroup(group);
        ClassC.setToggleGroup(group);
        ClassC.setSelected(true);
        calculator = new Calculator("C", "", 0);
        setValues();
        ended = true;
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> calculate());
        SubnetMask.valueProperty().addListener((observableValue, o, t1) -> changeSubnet(SubnetMask.getSelectionModel().getSelectedIndex()));
        SubnetBits.valueProperty().addListener((observableValue, o, t1) -> changeSubnet(SubnetBits.getSelectionModel().getSelectedIndex()));
        MaskBits.valueProperty().addListener((observableValue, o, t1) -> changeSubnet(MaskBits.getSelectionModel().getSelectedIndex()));
        MaximumSubnets.valueProperty().addListener((observableValue, o, t1) -> changeSubnet(MaximumSubnets.getSelectionModel().getSelectedIndex()));
        HostsPerSubnet.valueProperty().addListener((observableValue, o, t1) -> changeSubnet(HostsPerSubnet.getSelectionModel().getSelectedIndex()));
        IPAddress.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) calculate();
        });

    }
    private void calculate(){
        ended = false;
        calculator.setIPAddress(ClassA.isSelected() ? "A" : (ClassB.isSelected() ? "B" : "C"),
                IPAddress.getText(), index);
        setValues();
        ended = true;
    }
    private void changeSubnet(int i) {
        if(ended) index = i;
        calculator.setSubnetByIndex(index);
        SubnetBits.getSelectionModel().select(index);
        SubnetMask.getSelectionModel().select(index);
        MaskBits.getSelectionModel().select(index);
        MaximumSubnets.getSelectionModel().select(index);
        HostsPerSubnet.getSelectionModel().select(index);
        WildcardMask.setText(calculator.getValue(CalculatorValues.WildCardMask));
        HostAddressRange.setText(calculator.getValue(CalculatorValues.HostAddressRange));
        BroadcastAddress.setText(calculator.getValue(CalculatorValues.BroadcastAddress));
        SubnetBitmap.setText(calculator.getValue(CalculatorValues.SubnetBitmap));
        SubnetID.setText(calculator.getValue(CalculatorValues.SubnetID));
        setValues();
    }

    private void setValues(){
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
        HexIPAddress.setText(calculator.getValue(CalculatorValues.HexIPAddress));
        WildcardMask.setText(calculator.getValue(CalculatorValues.WildCardMask));
        HostAddressRange.setText(calculator.getValue(CalculatorValues.HostAddressRange));
        BroadcastAddress.setText(calculator.getValue(CalculatorValues.BroadcastAddress));
        SubnetBitmap.setText(calculator.getValue(CalculatorValues.SubnetBitmap));
    }

}
/*                                    .-+*#%@@@@@@%#*+-:
                                .=#@@@@@@@@@@@@@@@@@@@@#=.
                              =%@@@@@@@@@%#****#%@@@@@@@@@%=.
                           .+@@@@@@@#=:            :=#@@@@@@@*.
                          =@@@@@@#-                    -*@@@@@@=
                         #@@@@@*.     .-+#%@@@@@%#+-     .+@@@@@%.
                       .%@@@@%:     -#@@@@@@@@@@@@@@@#-    :%@@@@@.
                       %@@@@#     :%@@@@@@%#***#%@@@@@@%:    #@@@@%
                      *@@@@%     +@@@@@%=.       .=%@@@@@=    %@@@@#
                     .@@@@@:    =@@@@@=             =%%%%%-   :@@@@@:
                     =@@@@%     @@@@@=  DJMixu                 %@@@@+
                     +@@@@*    :@@@@@   Pabelito04             *@@@@*
                     +@@@@#    .@@@@@.  NBright                *@@@@*
                     -@@@@@     %@@@@*               :::::.    @@@@@=
                      @@@@@=    .@@@@@#.           .#@@@@@.   =@@@@@
                      -@@@@@:    :%@@@@@#=:     :=#@@@@@%:   .@@@@@=
                       *@@@@@:     +@@@@@@@@@@@@@@@@@@@+    :@@@@@#
                        *@@@@@+      =#@@@@@@@@@@@@@#=     +@@@@@*
                         =@@@@@@+       :-+*****+-:      =@@@@@@=
                          .#@@@@@@*-                  -*@@@@@@#.
                            :#@@@@@@@#+=:.      .:-+#@@@@@@@#-
                              .+%@@@@@@@@@@@@@@@@@@@@@@@@%+.
                                 .-*%@@@@@@@@@@@@@@@@%*=.

 */
