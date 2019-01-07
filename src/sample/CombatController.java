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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.ItemSkeletons.Initiative;
import XMLHandler.Monster;
import sample.Popups.MonsterPopup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


public class CombatController {
    private ObservableList<Initiative> initiatives = FXCollections.observableArrayList();
    private String sortOrder = "Order";
    private HashMap<Integer, ArrayList<Initiative>> duplicationMap = new HashMap<>();
    private CombatPopupInitiativeListController cpilc;
    private ArrayList<TableRow<Initiative>> clearList;
    private XMLHandler xmlh;
    private ArrayList<Monster> monsters;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private MonsterController monsterController;
    private GlobalController globalController;

    @FXML
    public AnchorPane content;

    @FXML
    public Button ButtonNewRoll;

    @FXML Button ButtonClear;

    @FXML
    public TextField TextFieldRoll;

    @FXML
    public Button ButtonAddInitiative;

    @FXML
    public ChoiceBox<?> ChoiceBoxInitiative;

    @FXML
    public TableView<Initiative> TableViewInitiative;

    @FXML
    public TableColumn<?, ?> TableViewName;

    @FXML
    public TextField TextFieldAdditionalNotes;

    @FXML
    public GridPane GridPaneAutomaticManeuvers;

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

    public void initialize(GlobalController globalController){

        TableViewOrder.setComparator(TableViewOrder.getComparator().reversed());
        TableViewInitiative.getSortOrder().add(TableViewOrder);
        this.xmlh = globalController.getXmlh();
        this.monsterController = globalController.getMonsterController();
        monsters = new ArrayList(xmlh.getMonsterHashMap().values());


        InitializeSearchBar();
        initiateGetMonsterOnDoubleClick();
        initiateAddButton();
        initiateDeleteButton();
        initiateInitiativeListPopupButton();
        initiateClearButton();
        initiateNewRollButton();
        initiatePreBuiltPlayerList();

        forceSortColumn(sortOrder);
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
        });
    }

    private void initiateAddButton() {
        ButtonAddInitiative.setOnAction(e -> {
            if(!TextFieldDex.getText().isEmpty() && !TextFieldName.getText().isEmpty() && !TextFieldRoll.getText().isEmpty()){
                Initiative initiative = new Initiative();
                initiative.setCharacterName(TextFieldName.getText());
                initiative.setDexScore(Integer.parseInt(TextFieldDex.getText()));
                initiative.setExtraNotes(TextFieldAdditionalNotes.getText());
                initiative.setInitiativeRoll(TextFieldRoll.getText());
                initiative.calcFinalInitiative();
                TableViewInitiative.getItems().add(initiative);
                forceSortColumn(sortOrder);

                //Clear fields
                TextFieldAdditionalNotes.clear();
                TextFieldRoll.clear();
                TextFieldDex.clear();
                TextFieldName.clear();

                TableViewInitiative.refresh();
                checkForDuplication();
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
    private void InitializeSearchBar() {
        ArrayList<String> monsterNames = new ArrayList<>();
        TextFields.bindAutoCompletion(monsterSearchBar, monsterNames);

            for (Monster monster: monsters) {
                monsterNames.add(monster.getInfo().getName());
            }
            bindAutoComplete(monsterNames);
    }

    private void bindAutoComplete(ArrayList<String> monsterNames){
        if(autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(monsterSearchBar, monsterNames);
        autoCompletionBinding.setOnAutoCompleted(event ->
                handleCompletion(event.getCompletion()));

    }

    private void handleCompletion(String s) {
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

}
