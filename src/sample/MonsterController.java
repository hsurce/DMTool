package sample;

import XMLHandler.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.Popups.MonsterPopup;

import java.util.ArrayList;
import java.util.Collections;

public class MonsterController {
    private ArrayList<String> classFilter;
    private ArrayList<String> levelFilter;
    private XMLHandler xmlh;

    private ArrayList<Monster> monsters;

    private ArrayList<Monster> filteredMonsterList;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private Main root2;
    private boolean isAlwaysPopup = false;
    GlobalController globalController;

    public void initialize(GlobalController globalController) {

        this.globalController = globalController;
        this.xmlh = globalController.getXmlh();

        monsters = new ArrayList(xmlh.getMonsterHashMap().values());
        Collections.sort(monsters, (item, t1) -> {
            String s1 = item.getInfo().getName();
            String s2 = t1.getInfo().getName();
            return s1.compareToIgnoreCase(s2);
        });

        classFilter = new ArrayList<>();
        levelFilter = new ArrayList<>();
        filteredMonsterList = new ArrayList<>();

        initializeSearchBar();
        initializeIsAlwaysPopupButton();
        initializeMonsterChoiceBox();
        initializeGenerateStatBlockButton();
        initializeDeleteFromMonsterChoiceBoxButton();
        initializeClearMonsterChoiceBoxButton();
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    private void initializeClearMonsterChoiceBoxButton() {
        ClearMonsterChoiceBoxButton.setOnAction(e -> {
            MonsterScrollPane.setContent(null);
            MonsterChoiceBox.getItems().clear();
        });
    }

    private void initializeDeleteFromMonsterChoiceBoxButton() {
        DeleteFromMonsterChoiceBoxButton.setOnAction(e ->{
            if(MonsterChoiceBox.getItems().get(MonsterChoiceBox.getSelectionModel().getSelectedIndex()-1) != null){
                int currentIndex = MonsterChoiceBox.getSelectionModel().getSelectedIndex();
                MonsterChoiceBox.getItems().remove(MonsterChoiceBox.getSelectionModel().getSelectedItem());
                MonsterChoiceBox.getSelectionModel().clearAndSelect(currentIndex-1);
            }
            else MonsterChoiceBox.getItems().remove(MonsterChoiceBox.getSelectionModel().getSelectedItem());
        });
    }

    private void initializeGenerateStatBlockButton() {
        GenerateStatBlockButton.setOnAction(e-> {
            if(xmlh.getMonsterHashMap().containsKey(MonsterChoiceBox.getSelectionModel().getSelectedItem())) {
                Monster monster = xmlh.getMonsterHashMap().get(MonsterChoiceBox.getSelectionModel().getSelectedItem());
                MonsterPopup monsterPopup = new MonsterPopup(globalController.getXmlh(), monster, globalController.getSpellController());
                globalController.checkForDuplicatePopup(monsterPopup);
                monsterPopup.show();
            }
        });
    }

    private void initializeMonsterChoiceBox() {
        MonsterChoiceBox.setOnAction(e ->{
            if(xmlh.getMonsterHashMap().containsKey(MonsterChoiceBox.getSelectionModel().getSelectedItem())) {
                Monster monster = xmlh.getMonsterHashMap().get(MonsterChoiceBox.getSelectionModel().getSelectedItem());
                MonsterPopup monsterPopup = new MonsterPopup(globalController.getXmlh(), monster, globalController.getSpellController());
                MonsterScrollPane.setContent(monsterPopup.getGridPane());
                //MULIGVIS IKKE RELEVANT AT KALDE:
                monsterPopup.closeStage();
            }
        });
    }

    private void initializeIsAlwaysPopupButton() {
        IsAlwaysPopupToggle.setOnAction(e ->{
            if(IsAlwaysPopupToggle.isSelected()){
                isAlwaysPopup = true;
            }
            else isAlwaysPopup = false;
        });
    }

    public void initializeSearchBar() {
        ArrayList<String> monsterNames = new ArrayList<>();
        TextFields.bindAutoCompletion(MonsterSearch, monsterNames);
        if (filteredMonsterList.isEmpty()) {
            for (Monster monster : monsters) {
                monsterNames.add(monster.getInfo().getName());
            }
            bindAutoComplete(monsterNames);
        }
    }

    private void bindAutoComplete(ArrayList<String> monsterNames) {
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(MonsterSearch, monsterNames);
        autoCompletionBinding.setOnAutoCompleted(event ->
                handleCompletion(event.getCompletion()));

    }

    public void handleCompletion(String s) {

        if (xmlh.getMonsterHashMap().containsKey(s) && isAlwaysPopup) {
            MonsterSearch.clear();
            Monster monster = xmlh.getMonsterHashMap().get(s);
            MonsterPopup monsterPopup = new MonsterPopup(xmlh, monster, globalController.getSpellController());
            globalController.checkForDuplicatePopup(monsterPopup);
            monsterPopup.show();
        }
        else if(xmlh.getMonsterHashMap().containsKey(s)){
            MonsterSearch.clear();
            MonsterChoiceBox.getItems().add(s);
            MonsterChoiceBox.getSelectionModel().select(s);
        }
    }

    @FXML
    private AnchorPane content;

    @FXML
    private ChoiceBox<String> MonsterChoiceBox;

    @FXML
    private TextField MonsterSearch;

    @FXML
    private Button AddInitiativeButton;

    @FXML
    private Button GenerateStatBlockButton;

    @FXML
    private GridPane MonsterGridPane;

    @FXML
    private Button DeleteFromMonsterChoiceBoxButton;

    @FXML
    private Button ClearMonsterChoiceBoxButton;

    @FXML
    private ToggleButton IsAlwaysPopupToggle;

    @FXML
    private ScrollPane MonsterScrollPane;

    public AnchorPane getContent() {
        return content;
    }
}

