package sample;

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
import sample.ItemSkeletons.Initiative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


public class CombatController {
    ObservableList<Initiative> initiatives = FXCollections.observableArrayList();
    String sortOrder = "Order";
    HashMap<Integer, ArrayList<Initiative>> duplicationMap = new HashMap<>();
    CombatPopupInitiativeListController cpilc;
    ArrayList<TableRow<Initiative>> clearList;

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

    public void initialize(){

        TableViewOrder.setComparator(TableViewOrder.getComparator().reversed());
        TableViewInitiative.getSortOrder().add(TableViewOrder);

        //ADD
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
        //DELETE
        ButtonDeleteInitiative.setOnAction( e -> {
            ObservableList<Initiative> initiativeSelected, allInitiatives;
            allInitiatives = TableViewInitiative.getItems();
            initiativeSelected = TableViewInitiative.getSelectionModel().getSelectedItems();
            initiativeSelected.forEach(allInitiatives::remove);
            forceSortColumn(sortOrder);
        });
        //INITIATIVELISTPOPUP
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
        //CLEAR
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
        //NEW ROLL
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

        //ADD PLAYERS FROM WATERDEEP
        ArrayList<Initiative> arrListInit = new ArrayList<Initiative>();
        arrListInit.add(new Initiative("<p>Ivellios", "NEW ROLL!", "Nico", 16));
        arrListInit.add(new Initiative("<p>Will", "NEW ROLL!", "Jakob", 18));
        arrListInit.add(new Initiative("<p>Innil", "NEW ROLL!", "Daniel", 12));
        arrListInit.add(new Initiative("<p>Belfir", "NEW ROLL!", "Tobias", 15));
        arrListInit.add(new Initiative("<p>Kevin", "NEW ROLL!", "Christian", 10));
        arrListInit.add(new Initiative("<p>Selise", "NEW ROLL!", "Malte", 10));
        arrListInit.add(new Initiative("<p>Thorning", "NEW ROLL!", "Jon", 11));
        arrListInit.add(new Initiative("<p>Nisha", "NEW ROLL!", "Seb(Nattergalen)", 16));

        TableViewInitiative.getItems().setAll(arrListInit);
        for(Initiative initiative: arrListInit){
            initiative.calcFinalInitiative();
        }
        forceSortColumn(sortOrder);
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
}
