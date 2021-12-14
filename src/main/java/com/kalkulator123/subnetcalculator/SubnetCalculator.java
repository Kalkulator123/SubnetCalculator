package com.kalkulator123.subnetcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SubnetCalculator extends Application {
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Subnet Calculator");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
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