package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ItemController {
    GlobalController globalController;

    public void initialize(GlobalController globalController){
        this.globalController = globalController;
    }

    @FXML
    public AnchorPane content;
}
