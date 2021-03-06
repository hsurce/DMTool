package sample;

import XMLHandler.Monster;
import XMLHandler.Spell;
import XMLHandler.XMLHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.ItemSkeletons.Condition;
import sample.ItemSkeletons.Initiative;
import sample.Popups.MonsterPopup;
import sample.Popups.SpellPopup;

import java.io.*;
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
    public TableColumn<?, ?> TableViewRoll;

    @FXML
    public Button ClearConditionsButton;

    @FXML
    private TextField HPTextField;

    @FXML
    private TextField CurrentHPTextField;


    @FXML
    private TextField ACTextField;

    @FXML
    private Label DamImmunityLabel;

    @FXML
    private Label DamResistanceLabel;

    @FXML
    private Label CondImmunityLabel;

    public void initialize(GlobalController globalController){

        initializeTableColumnSortability();
        TableViewOrder.setComparator(TableViewOrder.getComparator().reversed());
        TableViewInitiative.getSortOrder().add(TableViewOrder);

        this.xmlh = globalController.getXmlh();
        this.monsterController = globalController.getMonsterController();
        monsters = new ArrayList(xmlh.getMonsterHashMap().values());
        this.globalController = globalController;


        conditions = new ArrayList(Arrays.asList(new String[]{"Blinded","Charmed","Deafened","Exhaustion","Frightened", "Grappled", "Incapacitated", "Paralyzed", "Petrified",
        "Poisoned", "Prone", "Restrained", "Stunned", "Unconscious", "Duration", "Concentration"}));

        spells = new ArrayList(xmlh.getSpellHashMap().values());
        Collections.sort(spells, (item, t1) -> {
            String s1 = item.getName();
            String s2 = t1.getName();
            return s1.compareToIgnoreCase(s2);
        });
        initializeInitiativeCurrentHPBar();
        initializeSpellOrConditionSearchBar();
        initializeMonsterSearchBar();
        initiateGetMonsterOnDoubleClick();
        initiateGetSpellOnDoubleClick();
        initiateAddButton();
        initiateDeleteButton();
        initiateInitiativeListPopupButton();
        initiateClearButton();
        initiateNewRollButton();
        try {
            initiatePreBuiltPlayerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeInitiativeChoiceBox();
        initiateClearConditionsButton();
        initiateConditionDeleteButton();
        initiateInitiativeTableRowListener();
        initiateTableViewKeyListener();

        forceSortColumn(sortOrder);
    }

    private void initializeInitiativeCurrentHPBar() {
        CurrentHPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty()) {
                System.out.println("textfield changed from " + oldValue + " to " + newValue);
                TableViewInitiative.getSelectionModel().getSelectedItem().setCurrentHP(Integer.parseInt(newValue));
            }
        });
    }

    private void initiateTableViewKeyListener() {

        TableViewInitiative.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case DOWN:
                    event.consume();
                    if(TableViewInitiative.getSelectionModel().getSelectedItem() == TableViewInitiative.getItems().get(TableViewInitiative.getItems().size()-1)) {
                        System.out.println("HE");
                        TableViewInitiative.getSelectionModel().clearSelection();
                        TableViewInitiative.getSelectionModel().selectFirst();
                    }
                    else TableViewInitiative.getSelectionModel().selectNext();
                    break;
                case ENTER:
                    if (!TableViewInitiative.getSelectionModel().isEmpty() && !event.isControlDown()) {
                        int j = TableViewInitiative.getSelectionModel().getFocusedIndex();
                        ButtonNewRoll.fire();
                        TableViewInitiative.getSelectionModel().clearAndSelect(j+1);
                    }
                    else if(event.isControlDown()){
                        if(!TableViewInitiative.getSelectionModel().isEmpty()){
                            if(TableViewInitiative.getSelectionModel().getSelectedItem().getCharacterName().contains("[")) {
                                Initiative currentInitiative = TableViewInitiative.getSelectionModel().getSelectedItem();
                                String[] splitName = currentInitiative.getCharacterName().split("\\[");
                                splitName = splitName[1].split("]");
                                String number = splitName[0];

                                TextFieldName.setText(currentInitiative.getExtraNotes() + "#" + number);
                                TextFieldAdditionalNotes.setText(currentInitiative.getExtraNotes()+"");
                                String rollString;
                                if(currentInitiative.getInitiativeRoll().substring(0,3).contains("*")){
                                    rollString = currentInitiative.getInitiativeRoll().substring(0,3);
                                }
                                else{
                                    rollString = currentInitiative.getInitiativeRoll().substring(0,2);
                                }
                                TextFieldRoll.setText(rollString);

                                TextFieldDex.setText(currentInitiative.getDexScore()+"");
                                ButtonAddInitiative.fire();
                                int numberToInt = Integer.parseInt(number);
                                numberToInt = numberToInt-1;
                                if(numberToInt != 0) {
                                    currentInitiative.setCharacterName(currentInitiative.getExtraNotes() + "[" + numberToInt + "]");
                                }
                                else{
                                    TableViewInitiative.getItems().remove(currentInitiative);
                                }
                                forceSortColumn(sortOrder);
                                TableViewInitiative.refresh();
                            }

                        }
                    }
                    break;
            }
        });
    }

    /**
     * Det virkede ikke at lave case-specific sortableFalse.
     * Det her virkede, men er relativt obskurt.
     * Der er 4 columns. 3 som er viste, men som ikke skal kunne sorteres på
     * og 1 usynlig som bliver brugt som sortering.
     */
    private void initializeTableColumnSortability() {
        int count = 0;

        for (TableColumn column : TableViewInitiative.getColumns()) {
            if(count < 3) {
                column.setSortable(false);
            }
            count++;
        }
    }

    private void initiateInitiativeTableRowListener() {

        TableViewInitiative.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(TableViewInitiative.getSelectionModel().getSelectedItem().getCharacterName());
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
                    if(conditionSelected.get(0).getConditionName().equals("Duration")){
                        initiative.setCharacterName(initiative.getCharacterName().replace("¹",""));
                    }
                    else if(conditionSelected.get(0).getConditionName().equals("Concentration")){
                        initiative.setCharacterName(initiative.getCharacterName().replace("²",""));
                    }
                    else{
                        initiative.setCharacterName(initiative.getCharacterName().replace("³",""));
                    }
                    TableViewInitiative.refresh();
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
                                //MONSTER INFO
                                HPTextField.setText(initiative.getHp()+"");
                                ACTextField.setText(initiative.getAc()+"");
                                CurrentHPTextField.setText(initiative.getCurrentHP()+"");
                                if(initiative.isHasDamImmunity()) {
                                    DamImmunityLabel.setText("Yes");
                                }
                                else DamImmunityLabel.setText("No");
                                if(initiative.isHasCondImmunity()) {
                                    CondImmunityLabel.setText("Yes");
                                }
                                else CondImmunityLabel.setText("No");
                                if(initiative.isHasDamResistance()) {
                                    DamResistanceLabel.setText("Yes");
                                }
                                else DamResistanceLabel.setText("No");
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

    private void initiatePreBuiltPlayerList() throws IOException {
        String tmpPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File tmpFile = new File(tmpPath);
        tmpFile = tmpFile.getParentFile();

        String file = tmpFile.getAbsolutePath() + "/players/players.txt";
        File absFile = new File(file);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(absFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Initiative> arrListInit = new ArrayList<Initiative>();
        String st;
        while ((st = br.readLine()) != null){
            String[] stArr = st.split(",");
            arrListInit.add(new Initiative(stArr[0],stArr[1], stArr[2], Integer.parseInt(stArr[3])));
        }
        br.close();

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
                    else if(xmlh.getMonsterHashMap().containsKey(rowData.getExtraNotes())){
                        MonsterPopup monsterPopup = new MonsterPopup(xmlh, xmlh.getMonsterHashMap().get(rowData.getExtraNotes()), globalController.getSpellController());
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
                /**
                initiative.setInitiativeRoll("0");
                initiative.setFinalInitiative(0);
*/
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("New Roll");
                dialog.setHeaderText("Add a new roll to this character!");
                dialog.setContentText("Please enter your new roll here:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(newRoll -> initiative.setInitiativeRoll(newRoll));
                globalController.getPrimaryStage().requestFocus();
                TableViewInitiative.requestFocus();
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
                if(!initiative.getInitiativeRoll().endsWith("|")) {
                    initiative.setInitiativeRoll(initiative.getInitiativeRoll() + " | " + initiative.getFinalInitiative() + " |");
                }
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
                if(xmlh.getMonsterHashMap().containsKey(TextFieldName.getText())){
                    Monster monster = xmlh.getMonsterHashMap().get(TextFieldName.getText());
                    initiative.setAc(monster.getInfo().getAc().getValue());
                    initiative.setHp(monster.getInfo().getHp());
                    initiative.setCurrentHP(monster.getInfo().getHp());
                    //CONDITIONS
                    if(monster.getConditionImmunities() != null){
                        initiative.setHasCondImmunity(true);
                    }
                    else initiative.setHasCondImmunity(false);
                    //DAMAGE IMMUNITIES
                    if(monster.getImmunities() != null){
                        initiative.setHasDamImmunity(true);
                    }
                    else initiative.setHasDamImmunity(false);
                    //DAMAGE RESISTANCES
                    if(monster.getResists() != null){
                        initiative.setHasDamResistance(true);
                    }
                    else initiative.setHasDamResistance(false);
                }
                else if(xmlh.getMonsterHashMap().containsKey(TextFieldAdditionalNotes.getText())){
                    Monster monster = xmlh.getMonsterHashMap().get(TextFieldName.getText());
                    initiative.setAc(monster.getInfo().getAc().getValue());
                    initiative.setHp(monster.getInfo().getHp());
                    //CONDITIONS
                    if(!monster.getConditionImmunities().isEmpty()){
                        initiative.setHasCondImmunity(true);
                    }
                    else initiative.setHasCondImmunity(false);
                    //DAMAGE IMMUNITIES
                    if(!monster.getImmunities().isEmpty()){
                        initiative.setHasDamImmunity(true);
                    }
                    else initiative.setHasDamImmunity(false);
                    //DAMAGE RESISTANCES
                    if(!monster.getResists().isEmpty()){
                        initiative.setHasDamResistance(true);
                    }
                    else initiative.setHasDamResistance(false);
                }

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


    public void initializeMonsterSearchBar() {
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

                handleAddAdditionalMonsterInfo(event.getCompletion()));

    }

    private void handleAddAdditionalMonsterInfo(String s){
        // Clear add info for later.
        TextFieldName.clear();
        TextFieldDex.clear();
        TextFieldRoll.clear();
        TextFieldAdditionalNotes.clear();

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add additional monster info");
        dialog.setHeaderText("Add alternative name and number of monsters!");


// Set the button types.
        ButtonType loginButtonType = new ButtonType("Complete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField newName = new TextField();
        newName.setPromptText("example: Mister newName");
        TextField numberOfMonsters = new TextField();
        numberOfMonsters.setPromptText("example: 3");

        grid.add(new Label("New monster name:"), 0, 0);
        grid.add(newName, 1, 0);
        grid.add(new Label("Number of monsters:"), 0, 1);
        grid.add(numberOfMonsters, 1, 1);



        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> newName.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(newName.getText(), numberOfMonsters.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(newNameNumberOfMonsters -> {

            if(!newNameNumberOfMonsters.getKey().isEmpty()){
                if(!newNameNumberOfMonsters.getValue().isEmpty()) {
                    TextFieldName.setText(newNameNumberOfMonsters.getKey() + "[" + newNameNumberOfMonsters.getValue() + "]");
                }
                else TextFieldName.setText(newNameNumberOfMonsters.getKey());
            }
            else if(!newNameNumberOfMonsters.getValue().isEmpty()){
                TextFieldName.setText(s + "[" + newNameNumberOfMonsters.getValue() + "]");
            }

            System.out.println("Username=" + newNameNumberOfMonsters.getKey() + ", Password=" + newNameNumberOfMonsters.getValue());
            handleMonsterCompletion(s);
        });

    }
    private void handleMonsterCompletion(String s) {
        if(xmlh.getMonsterHashMap().containsKey(s)){
            Monster monster = xmlh.getMonsterHashMap().get(s);
            if(TextFieldName.getText().isEmpty()){
                TextFieldName.setText(monster.getInfo().getName());
            }
            TextFieldAdditionalNotes.setText(monster.getInfo().getName());
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
        //HER?
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
                            if(condition.getConditionName().equals("Duration")){
                                initiative.setCharacterName(initiative.getCharacterName() + "¹");
                            }
                            else if(condition.getConditionName().equals("Concentration")){
                                initiative.setCharacterName(initiative.getCharacterName() + "²");
                            }
                            else{
                                initiative.setCharacterName(initiative.getCharacterName() + "³");
                            }
                            TableViewInitiative.refresh();
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

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
