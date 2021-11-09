module com.kalkulator123.subnetcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kalkulator123.subnetcalculator to javafx.fxml;
    exports com.kalkulator123.subnetcalculator;
}