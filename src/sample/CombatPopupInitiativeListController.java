package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import sample.ItemSkeletons.Initiative;

public class CombatPopupInitiativeListController {

    @FXML
    public ScrollPane PopupScrollPane;

    @FXML
    public AnchorPane PopupAnchorPane;

    @FXML
    public TableView<Initiative> PopupTableView;

    @FXML
    public TableColumn<?, ?> CharName;

    @FXML
    public TableColumn<?, ?> AddNotes;

    @FXML
    public TableColumn<?, ?> Roll;

    @FXML
    public TableColumn<Initiative, String> Order;

}
