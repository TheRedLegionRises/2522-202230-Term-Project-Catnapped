module ca.bcit.comp2522.termproject.catnapped {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires net.synedra.validatorfx;

    opens ca.bcit.comp2522.termproject.catnapped to javafx.fxml;
    exports ca.bcit.comp2522.termproject.catnapped;
}