package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MagicItemController {
    GlobalController globalController;

    public void initialize(GlobalController globalController){
        this.globalController = globalController;
    }

    @FXML
    public AnchorPane content;
}
