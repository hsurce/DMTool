package sample;

/**
 * Created by Jakob on 1/10/2019.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MonsterCreatorController {

    @FXML
    private ScrollPane MonsterCreatorScrollPane;

    @FXML
    private AnchorPane MonsterCreatorAnchorPane;

    @FXML
    private TextField MonsterNameTextField;

    @FXML
    private TextField MonsterSizeTextField;

    @FXML
    private TextField MonsterTypeTextField;

    @FXML
    private TextField MonsterAlignmentTextField;

    @FXML
    private TextField MonsterACTextField;

    @FXML
    private TextField MonsterArmorTypeTextField;

    @FXML
    private TextField MonsterHPTextField;

    @FXML
    private TextField MonsterAmountTextField;

    @FXML
    private TextField MonsterDiceTextField;

    @FXML
    private TextField MonsterDiceTypeTextField;

    @FXML
    private ChoiceBox<?> MonsterSpeedChoiceBox;

    @FXML
    private TextField MonsterSpeedValueTextField;

    @FXML
    private Button MonsterSpeedAddButton;

    @FXML
    private TextField MonsterSTRTextField;

    @FXML
    private TextField MonsterDEXTextField;

    @FXML
    private TextField MonsterCONTextField;

    @FXML
    private TextField MonsterINTTextField;

    @FXML
    private TextField MonsterWISTextField;

    @FXML
    private TextField MonsterCHATextField;

    @FXML
    private TableView<?> MonsterAddedMovementsTableView;

    @FXML
    private TableColumn<?, ?> MonsterSpeedTypeColumn;

    @FXML
    private TableColumn<?, ?> MonsterSpeedValueColumn;

    @FXML
    private ChoiceBox<?> MonsterLanguagesChoiceBox;

    @FXML
    private Button MonsterLanguagesAddButton;

    @FXML
    private ChoiceBox<?> MonsterCRChoiceBox;

    @FXML
    private Button MonsterCRAddButton;

    @FXML
    private ChoiceBox<?> MonsterOtherChoiceBox;

    @FXML
    private Button MonsterOtherAddButon;

    @FXML
    private TableView<?> MonsterDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterDetailsNameColumn;

    @FXML
    private TableColumn<?, ?> MonsterDetailsValueColumn;

    @FXML
    private TextField MosterAbilityDetailsNameTextField;

    @FXML
    private TextArea MonsterAbilityDescriptionTextArea;

    @FXML
    private Button MonsterAbilityAddButton;

    @FXML
    private TableView<?> MonsterAbilityDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterAbilityDetailsNameColumn;

    @FXML
    private ChoiceBox<?> MonsterSpellsCantripsChoiceBox;

    @FXML
    private Button MonsterSpellsCantripsAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsFirstLevelChoiceBox;

    @FXML
    private Button MonsterSpellsFirstLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsSecondLevelChoiceBox;

    @FXML
    private Button MonsterSpellsSecondLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsThirdLevelChoiceBox;

    @FXML
    private Button MonsterSpellsThirdLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsFourthLevelChoiceBox;

    @FXML
    private Button MonsterSpellsFourthLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsFifthLevelChoiceBox;

    @FXML
    private Button MonsterSpellsFifthLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsSixthLevelChoiceBox;

    @FXML
    private Button MonsterSpellsSixthLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsSeventhLevelChoiceBox;

    @FXML
    private Button MonsterSpellsSeventhLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsEighthLevelChoiceBox;

    @FXML
    private Button MonsterSpellsEighthLevelAddButton;

    @FXML
    private ChoiceBox<?> MonsterSpellsNinthLevelChoiceBox;

    @FXML
    private Button MonsterSpellsNinthLevelAddButton;

    @FXML
    private TableView<?> MonsterSpellDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterSpellDetailsSpellLevelColumn;

    @FXML
    private TableColumn<?, ?> MonsterSpellDetailsSpellNameColumn;

    @FXML
    private TextField MonsterActionNameTextField;

    @FXML
    private TextArea MonsterActionDescriptionTextArea;

    @FXML
    private Button MonsterActionAddButton;

    @FXML
    private TableView<?> MonsterActionDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterActionDetailsActionNameColumn;

    @FXML
    private TextField MonsterLegendaryActionsNameTextField;

    @FXML
    private TextArea MonsterLegendaryActionsDescriptionTextArea;

    @FXML
    private Button MonsterLegendaryActionsAddButton;

    @FXML
    private TableView<?> MonsterLegendaryActionsDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterLegendaryActionsDetailsLegendaryActionNameColumn;

    @FXML
    private Button MonsterAddImageButton;

    @FXML
    private Button MonsterTotalClearButton;

    @FXML
    private Button MonsterTotalSaveButton;

    public ScrollPane getContent() {
        return MonsterCreatorScrollPane;
    }

    public void initialize(GlobalController globalController) {
        //removeButtonTraversability();
    }

    private void removeButtonTraversability() {
        MonsterAbilityAddButton.setFocusTraversable(false);
        MonsterActionAddButton.setFocusTraversable(false);
        MonsterAddImageButton.setFocusTraversable(false);
        MonsterCRAddButton.setFocusTraversable(false);
        MonsterLanguagesAddButton.setFocusTraversable(false);
        MonsterSpeedAddButton.setFocusTraversable(false);
        MonsterTotalClearButton.setFocusTraversable(false);
        MonsterTotalSaveButton.setFocusTraversable(false);
        MonsterLegendaryActionsAddButton.setFocusTraversable(false);
        MonsterSpellsCantripsAddButton.setFocusTraversable(false);
        MonsterSpellsFirstLevelAddButton.setFocusTraversable(false);
        MonsterSpellsSecondLevelAddButton.setFocusTraversable(false);
        MonsterSpellsThirdLevelAddButton.setFocusTraversable(false);
        MonsterSpellsFourthLevelAddButton.setFocusTraversable(false);
        MonsterSpellsFifthLevelAddButton.setFocusTraversable(false);
        MonsterSpellsSixthLevelAddButton.setFocusTraversable(false);
        MonsterSpellsSeventhLevelAddButton.setFocusTraversable(false);
        MonsterSpellsEighthLevelAddButton.setFocusTraversable(false);
        MonsterSpellsNinthLevelAddButton.setFocusTraversable(false);
    }
}
