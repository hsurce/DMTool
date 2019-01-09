package sample;

import XMLHandler.XMLHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.ItemSkeletons.Condition;
import sample.ItemSkeletons.Initiative;
import XMLHandler.Monster;
import sample.Popups.MonsterPopup;
import XMLHandler.Spell;
import sample.Popups.SpellPopup;

import java.io.IOException;
import java.util.*;


public class CombatController {
    private ObservableList<Initiative> initiatives = FXCollections.observableArrayList();
    private String sortOrder = "Order";
    private HashMap<Integer, ArrayList<Initiative>> duplicationMap = new HashMap<>();
    private CombatPopupInitiativeListController cpilc;
    private ArrayList<TableRow<Initiative>> clearList;
    private XMLHandler xmlh;
    private ArrayList<Monster> monsters;
    private AutoCompletionBinding<String> autoCompletionBindingMonster;
    private AutoCompletionBinding<String> autoCompletionBindingSpellsAndConditions;
    private MonsterController monsterController;
    private GlobalController globalController;
    private ArrayList<Spell> spells;
    private ArrayList<String> conditions;

    @FXML
    private AnchorPane content;

    @FXML
    public Button ButtonNewRoll;

    @FXML
    public Button ButtonClear;

    @FXML
    public TextField TextFieldRoll;

    @FXML
    public Button ButtonAddInitiative;

    @FXML
    public ChoiceBox<String> InitiativeConditionChoiceBox;

    @FXML
    public TableView<Initiative> TableViewInitiative;

    @FXML
    public TableColumn<?, ?> TableViewName;

    @FXML
    public TextField TextFieldAdditionalNotes;

    @FXML
    public TableView<Condition> ConditionTableView;

    @FXML
    public TextField TextFieldName;

    @FXML
    public TextField monsterSearchBar;

    @FXML
    public Button ButtonShowInitiativeListPopup;

    @FXML
    public TableColumn<Initiative, String> TableViewOrder;

    @FXML
    public TableColumn<?, ?> TableViewExtra;

    @FXML
    public TableColumn<?, ?> TableViewAdditionalNotes;

    @FXML
    public Button ButtonDeleteInitiative;

    @FXML
    public TextField TextFieldDex;

    @FXML
    public TextField InitiativeConditionOrSpellSearchBar;

    @FXML
    public Button DeleteConditionButton;

    @FXML
    public Button ClearConditionsButton;

    public void initialize(GlobalController globalController){

        TableViewOrder.setComparator(TableViewOrder.getComparator().reversed());
        TableViewInitiative.getSortOrder().add(TableViewOrder);

        this.xmlh = globalController.getXmlh();
        this.monsterController = globalController.getMonsterController();
        monsters = new ArrayList(xmlh.getMonsterHashMap().values());
        this.globalController = globalController;


        conditions = new ArrayList(Arrays.asList(new String[]{"Blinded","Charmed","Deafened","Exhaustion","Frightened", "Grappled", "Incapacitated", "Paralyzed", "Petrified",
        "Poisoned", "Prone", "Restrained", "Stunned", "Unconscious"}));

        spells = new ArrayList(xmlh.getSpellHashMap().values());
        Collections.sort(spells, (item, t1) -> {
            String s1 = item.getName();
            String s2 = t1.getName();
            return s1.compareToIgnoreCase(s2);
        });

        initializeTableColumnSortability();
        initializeSpellOrConditionSearchBar();
        initializeMonsterSearchBar();
        initiateGetMonsterOnDoubleClick();
        initiateGetSpellOnDoubleClick();
        initiateAddButton();
        initiateDeleteButton();
        initiateInitiativeListPopupButton();
        initiateClearButton();
        initiateNewRollButton();
        initiatePreBuiltPlayerList();
        initializeInitiativeChoiceBox();
        initiateClearConditionsButton();
        initiateConditionDeleteButton();
        initiateInitiativeTableRowListener();

        forceSortColumn(sortOrder);
    }

    private void initializeTableColumnSortability() {
        for (TableColumn column : TableViewInitiative.getColumns()) {
            if (column.getId() != "TableViewOrder") {
                //column.setSortable(false);
            }
        }
    }

    private void initiateInitiativeTableRowListener() {
        TableViewInitiative.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                InitiativeConditionChoiceBox.getSelectionModel().select(newSelection.getCharacterName());
            }
        });
    }

    private void initiateConditionDeleteButton() {

        DeleteConditionButton.setOnAction( e -> {
            for (Initiative initiative: TableViewInitiative.getItems()) {
                if(initiative.getCharacterName().equals(InitiativeConditionChoiceBox.getSelectionModel().getSelectedItem())){
                    ObservableList<Condition> conditionSelected, allConditions;
                    allConditions = initiative.getConditionTableView().getItems();
                    conditionSelected = ConditionTableView.getSelectionModel().getSelectedItems();
                    conditionSelected.forEach(allConditions::remove);
                    populateConditionTable(initiative);
                    break;
                }
            }
        });
    }

    private void initiateClearConditionsButton(){
        ClearConditionsButton.setOnAction(e -> {
            for (Initiative initiative: TableViewInitiative.getItems()) {
                if(initiative.getCharacterName().equals(InitiativeConditionChoiceBox.getSelectionModel().getSelectedItem())){
                    initiative.getConditionTableView().getItems().clear();
                    ConditionTableView.getItems().clear();
                    break;
                }

            }
        });
    }

    private void initializeInitiativeChoiceBox() {
        if(!InitiativeConditionChoiceBox.getItems().isEmpty()){
            InitiativeConditionChoiceBox.getItems().clear();
        }
        //SET ONACTION PÅ CHOICEBOX
            InitiativeConditionChoiceBox.setOnAction(e ->{
                for (Initiative initiative: TableViewInitiative.getItems()) {
                    ConditionTableView.getItems().clear();
                    if(InitiativeConditionChoiceBox.getSelectionModel().getSelectedItem() != null) {
                        if (InitiativeConditionChoiceBox.getSelectionModel().getSelectedItem().equals(initiative.getCharacterName())) {
                            if (initiative.getConditionTableView().getItems() != null) {
                                populateConditionTable(initiative);
                                break;
                            }
                        }
                    }
                }
            });

        //KONSTRUÉR CHOICE BOX
        for (Initiative initiative :TableViewInitiative.getItems()) {
            InitiativeConditionChoiceBox.getItems().add(initiative.getCharacterName());
        }

    }

    private void initiatePreBuiltPlayerList() {
        //ADD PLAYERS FROM WATERDEEP
        ArrayList<Initiative> arrListInit = new ArrayList<Initiative>();
        arrListInit.add(new Initiative("<p>Ivellios", "NEW ROLL!", "Nico", 16));
        arrListInit.add(new Initiative("<p>Will", "NEW ROLL!", "Jakob", 18));
        arrListInit.add(new Initiative("<p>Innil", "NEW ROLL!", "Daniel", 12));
        arrListInit.add(new Initiative("<p>Belfir", "NEW ROLL!", "Tobias", 15));
        arrListInit.add(new Initiative("<p>Kevin", "NEW ROLL!", "Christian", 19));
        arrListInit.add(new Initiative("<p>Selise", "NEW ROLL!", "Malte", 11));
        arrListInit.add(new Initiative("<p>Thorning", "NEW ROLL!", "Jon", 11));
        arrListInit.add(new Initiative("<p>Nisha", "NEW ROLL!", "Seb(Nattergalen)", 16));

        TableViewInitiative.getItems().setAll(arrListInit);
        for(Initiative initiative: arrListInit){
            initiative.calcFinalInitiative();
        }
    }

    private void initiateGetMonsterOnDoubleClick() {
        TableViewInitiative.setRowFactory(tv -> {
            TableRow<Initiative> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Initiative rowData = row.getItem();
                    if(xmlh.getMonsterHashMap().containsKey(rowData.getCharacterName())) {
                        MonsterPopup monsterPopup = new MonsterPopup(xmlh, xmlh.getMonsterHashMap().get(rowData.getCharacterName()), globalController.getSpellController());
                        globalController.checkForDuplicatePopup(monsterPopup);
                        monsterPopup.show();
                    }
                }
            });
            return row ;
        });
    }

    private void initiateGetSpellOnDoubleClick() {
        ConditionTableView.setRowFactory(tv -> {
            TableRow<Condition> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Condition rowData = row.getItem();
                    if(xmlh.getSpellHashMap().containsKey(rowData.getConditionName().toLowerCase())) {
                        SpellPopup spellPopup = new SpellPopup(xmlh.getSpellHashMap().get(rowData.getConditionName().toLowerCase()));
                        globalController.checkForDuplicatePopup(spellPopup);
                        spellPopup.show();
                    }
                }
            });
            return row ;
        });
    }

    private void initiateNewRollButton() {
        ButtonNewRoll.setOnAction(e -> {
            ObservableList<Initiative> initiativeSelected;
            initiativeSelected = TableViewInitiative.getSelectionModel().getSelectedItems();
            for(Initiative initiative: initiativeSelected){
                initiative.setInitiativeRoll("0");
                initiative.setFinalInitiative(0);

                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("New Roll");
                dialog.setHeaderText("Add a new roll to this character!");
                dialog.setContentText("Please enter your new roll here:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(newRoll -> initiative.setInitiativeRoll(newRoll));
                initiative.calcFinalInitiative();
                /**
                 * HER ER finalInitiative LAGT TIL FOR LETHEDENS SKYLD. SKAL HAVE BEDRE FIX!
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 */
                initiative.setInitiativeRoll(initiative.getInitiativeRoll() + " | " + initiative.getFinalInitiative());
                forceSortColumn(sortOrder);
            }
            checkForDuplication();
            TableViewInitiative.refresh();
        });
    }

    private void initiateClearButton() {
        ButtonClear.setOnAction(e -> {

            for(int i = 0; i < TableViewInitiative.getItems().size(); i++){
                Initiative initiative = TableViewInitiative.getItems().get(i);
                if (!initiative.getCharacterName().contains("<p>")){
                    TableViewInitiative.getItems().remove(initiative);
                }
                else{
                    initiative.setInitiativeRoll("SET NEW!");
                }
                /**
                 for (Node n: TableViewInitiative.lookupAll("TableRow")) {
                 if (n instanceof TableRow) {
                 TableRow<Initiative> row = (TableRow) n;
                 row.setStyle("-fx-background-color: white");
                 }
                 }
                 */
                initializeInitiativeChoiceBox();
            }
            TableViewInitiative.refresh();
        });
    }

    private void initiateInitiativeListPopupButton() {
        ButtonShowInitiativeListPopup.setOnAction( e -> {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("XMLFiles/CombatPopupInitiativeList.fxml"));

            try {
                Parent p = fxmlLoader.load();
                cpilc =(CombatPopupInitiativeListController)fxmlLoader.getController();
                for(int i = 0; i < TableViewInitiative.getColumns().size(); i++){
                    cpilc.PopupTableView.getColumns().get(i).setText(TableViewInitiative.getColumns().get(i).getText());
                }
                cpilc.PopupTableView.getItems().setAll(TableViewInitiative.getItems());
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void initiateDeleteButton() {
        ButtonDeleteInitiative.setOnAction( e -> {
            ObservableList<Initiative> initiativeSelected, allInitiatives;
            allInitiatives = TableViewInitiative.getItems();
            initiativeSelected = TableViewInitiative.getSelectionModel().getSelectedItems();
            initiativeSelected.forEach(allInitiatives::remove);
            forceSortColumn(sortOrder);
            TableViewInitiative.refresh();
            initializeInitiativeChoiceBox();
        });
    }

    private void initiateAddButton() {
        ButtonAddInitiative.setOnAction(e -> {
            if(!TextFieldDex.getText().isEmpty() && !TextFieldName.getText().isEmpty() && !TextFieldRoll.getText().isEmpty()){
                Initiative initiative = new Initiative(TextFieldName.getText(),TextFieldRoll.getText(),TextFieldAdditionalNotes.getText(),Integer.parseInt(TextFieldDex.getText()));

                /**
                 * HER ER finalInitiative LAGT TIL FOR LETHEDENS SKYLD. SKAL HAVE BEDRE FIX!
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 */
                initiative.setInitiativeRoll(initiative.getInitiativeRoll() + " | " + initiative.getFinalInitiative());
                TableViewInitiative.getItems().add(initiative);
                forceSortColumn(sortOrder);

                //Clear fields
                TextFieldAdditionalNotes.clear();
                TextFieldRoll.clear();
                TextFieldDex.clear();
                TextFieldName.clear();

                TableViewInitiative.refresh();
                checkForDuplication();
                initializeInitiativeChoiceBox();
            }
            else{
                //ERSTART MED POPUP ERROR
                System.out.println("Du mangler et felt!");
            }
        });
    }

    private void checkForDuplication() {
/**
        int i = 0;
        TableRow<Initiative> prevRow = null;
        for (Node n: TableViewInitiative.lookupAll("TableRow")) {
            if (n instanceof TableRow) {
                TableRow<Initiative> row = (TableRow) n;
                if(i>0 && i+1<TableViewInitiative.getItems().size()) {
                    if(prevRow != null) {
                        int r = TableViewInitiative.getItems().get(i-1).getFinalInitiative();
                        int r2 = TableViewInitiative.getItems().get(i).getFinalInitiative();
                        if (r == r2) {
                            row.setStyle("-fx-background-color: blue");
                            prevRow.setStyle("-fx-background-color: blue");

                            //row.setDisable(false);
                        }
                    }
                    prevRow = row;
                }
                i++;
                int j = TableViewInitiative.getItems().size();
                if (i == j)
                    break;

            }

        }
*/
    }



    private void forceSortColumn(String columnName){
        TableViewOrder.setComparator(TableViewOrder.getComparator());

        TableViewInitiative.getSortOrder().add(TableViewOrder);

    }

    public void onEnter(ActionEvent actionEvent) {
        TextField f = new TextField();
        if(actionEvent.getSource().getClass() == f.getClass()) {
            ButtonAddInitiative.fire();
        }

    }

    public void HandleOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if (!TableViewInitiative.getSelectionModel().isEmpty()) {
                int j = TableViewInitiative.getSelectionModel().getFocusedIndex();
                ButtonNewRoll.fire();
                TableViewInitiative.getSelectionModel().clearAndSelect(j+1);
            }
        }
    }

    private void initializeMonsterSearchBar() {
        ArrayList<String> monsterNames = new ArrayList<>();
        TextFields.bindAutoCompletion(monsterSearchBar, monsterNames);

            for (Monster monster: monsters) {
                monsterNames.add(monster.getInfo().getName());
            }
            bindAutoCompleteMonster(monsterNames);
    }

    private void bindAutoCompleteMonster(ArrayList<String> monsterNames){
        if(autoCompletionBindingMonster != null) {
            autoCompletionBindingMonster.dispose();
        }
        autoCompletionBindingMonster = TextFields.bindAutoCompletion(monsterSearchBar, monsterNames);
        autoCompletionBindingMonster.setOnAutoCompleted(event ->
                handleMonsterCompletion(event.getCompletion()));

    }

    private void handleMonsterCompletion(String s) {
        if(xmlh.getMonsterHashMap().containsKey(s)){
            Monster monster = xmlh.getMonsterHashMap().get(s);
            TextFieldName.clear();
            TextFieldDex.clear();
            TextFieldRoll.clear();
            TextFieldName.setText(monster.getInfo().getName());
            TextFieldDex.setText(monster.getInfo().getDex()+"");
            TextFieldRoll.setText("NEW ROLL!");
            ButtonAddInitiative.fire();
            monsterSearchBar.clear();
        }
    }

    private void bindAutoCompleteSpellsAndConditions(ArrayList<String> spellNames){
        if(autoCompletionBindingSpellsAndConditions != null) {
            autoCompletionBindingSpellsAndConditions.dispose();
        }
        autoCompletionBindingSpellsAndConditions = TextFields.bindAutoCompletion(InitiativeConditionOrSpellSearchBar, spellNames);
        autoCompletionBindingSpellsAndConditions.setOnAutoCompleted(event ->
                handleSpellAndConditionCompletion(event.getCompletion()));

    }

    /**
     * Lægger en condition i det specifikke initiativs ConditionTableView.
     * For at finde det initiativ vi gerne vil have er vi nødt til at løbe dem igennem et for-loop indtil vi rammer
     * initiativet ved navn s.
     * Så checkes der for om conditionen ligger i tableViewet i forvejen.
     * Hvis ikke, lægges conditionen til initiativ objektet.
     * @param s Laver en ny condition på baggrund af s, hvis den den condition ikke ligger i listen i forvejen.
     */
    public void handleSpellAndConditionCompletion(String s) {
            TableViewInitiative.getItems().forEach(initiative -> {
                if(!InitiativeConditionChoiceBox.getSelectionModel().isEmpty()){
                    if(InitiativeConditionChoiceBox.getSelectionModel().getSelectedItem().equals(initiative.getCharacterName())) {
                            Condition condition = new Condition();
                            condition.setConditionName(s);
                            boolean notHasCondition = true;
                        for (Object obj: initiative.getConditionTableView().getItems()) {
                            Condition initiativeCondition = (Condition) obj;
                            if(initiativeCondition.getConditionName().equals(s)){
                                notHasCondition = false;
                                break;
                            }
                        }
                        if(notHasCondition) {
                            initiative.getConditionTableView().getItems().add(condition);
                            populateConditionTable(initiative);
                        }
                    }
                }
                else System.out.println("LOL");
            });
            InitiativeConditionOrSpellSearchBar.clear();
    }

    private void populateConditionTable(Initiative initiative) {
        ConditionTableView.getItems().clear();
        for (Object obj:initiative.getConditionTableView().getItems()) {
            Condition c = (Condition) obj;
            ConditionTableView.getItems().add(c);
        }
    }


    private void initializeSpellOrConditionSearchBar() {
        ArrayList<String> spellAndConditionNames = new ArrayList<>();
        TextFields.bindAutoCompletion(InitiativeConditionOrSpellSearchBar, spellAndConditionNames);

        for (String s: conditions) {
            spellAndConditionNames.add(s);
        }
        for (Spell spell : spells) {
            spellAndConditionNames.add(spell.getName());
        }
        bindAutoCompleteSpellsAndConditions(spellAndConditionNames);


    }

    public AnchorPane getContent() {
        return content;
    }
}
