package sample;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassroomController implements Initializable {
    @FXML
    ScrollPane scroll;
    private ScrollBar getVerticalScrollbar(ScrollPane scroll) {
        ScrollBar result = null;
        for (Node n : scroll.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      /*  scroll.setPannable(true);
        ScrollBar bar = getVerticalScrollbar(scroll);
        bar.valueProperty().addListener(this::scrolled);*/
    }
    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        System.out.println("Scrolled to " + value);
        ScrollBar bar = getVerticalScrollbar(scroll);
        if (value == bar.getMax()) {
            System.out.println("Adding new POSTS.");
            /*double targetValue = value * items.size();
            addPersons();
            bar.setValue(targetValue / items.size());*/
        }
    }
}
