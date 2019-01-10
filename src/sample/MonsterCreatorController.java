package sample;

/**
 * Created by Jakob on 1/10/2019.
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

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
    private ChoiceBox<String> MonsterSpeedChoiceBox;

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
    private ChoiceBox<String> MonsterLanguagesChoiceBox;

    @FXML
    private Button MonsterLanguagesAddButton;

    @FXML
    private ChoiceBox<String> MonsterCRChoiceBox;

    @FXML
    private Button MonsterCRAddButton;

    @FXML
    private ChoiceBox<String> MonsterOtherChoiceBox;

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
    private ChoiceBox<String> MonsterSpellsLevelChoiceBox;

    @FXML
    private Button MonsterSpellAddButton;

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

    @FXML
    private TextField MonsterSpellSearchTextField;

    @FXML
    private CheckBox IsAttackActionCheckBox;

    @FXML
    private TextField MonsterAttackActionDieAmountTextField;

    @FXML
    private TextField MonsterAttackActionDieSizeTextField;

    @FXML
    private TextField MonsterAttackActionDamageBonusTextField;


    public ScrollPane getContent() {
        return MonsterCreatorScrollPane;
    }

    public void initialize(GlobalController globalController) {
        initializeChoiceBoxes();
        initializeAddButtons();
        initializeSpecialButtons();
        initializeSaveMonsterButton();

    }

    private void initializeSpecialButtons() {
    }

    private void initializeSaveMonsterButton() {
        //...
        saveToCustomMonsterBin();
    }

    private void initializeChoiceBoxes() {

        MonsterCRChoiceBox.getItems().addAll(Arrays.asList(new String[]{"0","1/8","1/4","1/2","1","2","3","4","5","6","7","8","9","10","11","12","12","13","14","15","16","17","18","19","20"}));
        MonsterLanguagesChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Abyssal","Aquan","Auran","Celestial","Common","Deep Speech","Draconic","Druidic","Dwarvish","Elvish","Giant"
                ,"Gnomish","Goblin","Gnoll","Halfling","Ignan","Infernal","Orc","Primordial","Sylvan","Terran","Undercommon"}));
        MonsterOtherChoiceBox.getItems().addAll(Arrays.asList(new String[]{}));
        MonsterSpeedChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Normal","Burrow","Fly","Swim","Climb"}));
        MonsterSpellsLevelChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Cantrip","1","2","3","4","5","6","7","8","9"}));

    }

    private void initializeAddButtons() {
        MonsterLegendaryActionsAddButton.setOnAction(e ->{
            if(MonsterLegendaryActionsNameTextField.getText() != null){

            }
        });
        MonsterSpeedAddButton.setOnAction(e ->{
            if(MonsterSpeedChoiceBox.getSelectionModel().getSelectedItem() != null && MonsterSpeedValueTextField.getText() != null){

            }

        });
        MonsterLanguagesAddButton.setOnAction(e ->{
            if(MonsterLanguagesChoiceBox.getSelectionModel().getSelectedItem() != null){

            }

        });
        MonsterCRAddButton.setOnAction(e ->{
            if(MonsterCRChoiceBox.getSelectionModel().getSelectedItem() != null){

            }

        });
        MonsterActionAddButton.setOnAction(e ->{
            if(MonsterActionNameTextField.getText() != null){
                //...

                if(IsAttackActionCheckBox.isSelected()){
                    //Lav attack action...
                }
            }

        });
        MonsterAbilityAddButton.setOnAction(e ->{
            if(MonsterAbilityDescriptionTextArea.getText() != null){
                //Lav en trait
            }

        });
        MonsterSpellAddButton.setOnAction(e ->{
            if(MonsterSpellSearchTextField.getText() != null){

            }

        });
    }

    private void saveToCustomMonsterBin() {
    }

}
