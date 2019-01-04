package sample;

import XMLHandler.Information;
import XMLHandler.Monster;
import XMLHandler.XMLHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import XMLHandler.Stat;

import java.util.ArrayList;
import java.util.Collections;

public class MonsterController {
    private ArrayList<String> classFilter;
    private ArrayList<String> levelFilter;
    private XMLHandler xmlh;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> filteredMonsterList;
    private AutoCompletionBinding<String> autoCompletionBinding;

    public void Initialize(XMLHandler xmlh){
        this.xmlh = xmlh;
        monsters = new ArrayList(xmlh.getMonsterHashMap().values());
        Collections.sort(monsters, (item, t1) -> {
            String s1 = item.getInfo().getName();
            String s2 = t1.getInfo().getName();
            return s1.compareToIgnoreCase(s2);
        });

        classFilter = new ArrayList<>();
        levelFilter = new ArrayList<>();
        filteredMonsterList = new ArrayList<>();

        InitializeSearchBar();

    }

    private void InitializeSearchBar() {
        ArrayList<String> monsterNames = new ArrayList<>();
        TextFields.bindAutoCompletion(MonsterSearch, monsterNames);
        if(filteredMonsterList.isEmpty()) {
            for (Monster monster: monsters) {
                monsterNames.add(monster.getInfo().getName());
            }
            bindAutoComplete(monsterNames);
        }
    }
    private void bindAutoComplete(ArrayList<String> monsterNames){
        if(autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(MonsterSearch, monsterNames);
        autoCompletionBinding.setOnAutoCompleted(event ->
                handleCompletion(event.getCompletion()));

    }


    public void handleCompletion(String s) {

        if(xmlh.getMonsterHashMap().containsKey(s)){
            Monster monster = xmlh.getMonsterHashMap().get(s);
            System.out.println("YO!");
            BuildMonsterPopUp(monster);
        }
    }

    @FXML
    public AnchorPane content;

    @FXML
    private ChoiceBox<?> MonsterChoiceBox;

    @FXML
    private TextField MonsterSearch;

    @FXML
    private Button ButtonAddInitiative;

    @FXML
    private Button ButtonGenerateStatBlock;

    @FXML
    private Button ButtonShowImage;

    @FXML
    private Label LabelMonsterName;

    @FXML
    private Text TextSizeTypeAlignment;

    @FXML
    private Text STRScore;

    @FXML
    private Text DEXScore;

    @FXML
    private Text CONScore;

    @FXML
    private Text INTScore;

    @FXML
    private Text WISScore;

    @FXML
    private Text CHAScore;

    private void BuildMonsterPopUp(Monster monster) {
        Information info = monster.getInfo();
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(600);
        int counter = 0;
/**
 * HERFRA BLIVER ET HELT POPUP VINDUE KONSTRUERET PÅ BAGGRUND AF VÆRDIER I SPELL.
 */
        if(!info.getName().isEmpty()) {

            Label name = new Label("\n"+info.getName());

            name.setFont(Font.font("Apple Braille", 20));
            name.setStyle("-fx-text-fill: red");
            gridPane.add(name, 0,counter,1,1);
            counter++;
        }
        if(!info.getSize().isEmpty()) {

            String concat = "\n"+info.getSize();
            if(!info.getType().isEmpty()){
                concat = concat + " " + info.getType();
            }

            if(!info.getAlignment().isEmpty()){
                concat = concat + ", " + info.getAlignment();
            }
            Label sizeTypeAlignment = new Label(concat);
            sizeTypeAlignment.setFont(Font.font("Apple Braille", 12));
            sizeTypeAlignment.setStyle("-fx-text-fill: red");
            gridPane.add(sizeTypeAlignment, 0,counter,1,1);
            counter++;
        }
        if(info.getAc() != null) {

            TextFlow acFlow = new TextFlow();
            Text text1 = new Text("Armor Class: ");
            text1.setStyle("-fx-font-weight: bold");
            String statName = "";
            if(info.getAc().getName() != null){
                statName = " "+info.getAc().getName();
            }

            Text text2 = new Text(info.getAc().getValue()+statName);
            text2.setStyle("-fx-font-weight: regular");
            acFlow.getChildren().addAll(text1,text2);

            gridPane.add(acFlow,0,counter,1,1);
            counter++;

        }
        if(info.getHp() != 0) {
            TextFlow hpFlow = new TextFlow();
            Text text1 = new Text("Hit Points: ");
            text1.setStyle("-fx-font-weight: bold");
            String hitDieString = "";
            if (info.getHitDie() != null) {
                hitDieString = info.getHitDie().getDieAmount() + "d" + info.getHitDie().getDieSize();
            }
            int bonusHealth = ((info.getCon()-10)/2)*info.getHitDie().getDieAmount();
            Text text2 = new Text(info.getHp() + " (" + hitDieString + "+" + bonusHealth + ")");
            text2.setStyle("-fx-font-weight: regular");
            hpFlow.getChildren().addAll(text1,text2);

            gridPane.add(hpFlow,0,counter,1,1);
            counter++;
        }

        if(!monster.getSpeeds().isEmpty()) {

            TextFlow speedFlow = new TextFlow();
            Text text1 = new Text("Speed: ");
            text1.setStyle("-fx-font-weight: bold");

            String concat = "";
            int statCounter = 0;
            for (Stat stat :monster.getSpeeds()            ) {
                if(statCounter > 0){
                    concat += ", ";
                }

                String statName = "";
                if(!stat.getName().isEmpty()){
                    statName = " " + stat.getName();
                }
                concat += stat.getValue() + " ft." + statName;
                statCounter++;
            }

            Text text2 = new Text(concat);
            text2.setStyle("-fx-font-weight: regular");
            speedFlow.getChildren().addAll(text1,text2);

            gridPane.add(speedFlow,0,counter,1,1);
            counter++;

        }
        /**
         * LÆG ABILITY SCORES OG MODIFIERS IND
         */



        AnchorPane anchorPane = new AnchorPane(gridPane);
        anchorPane.setScaleX(gridPane.getScaleX());
        anchorPane.setScaleY(gridPane.getScaleY());
        anchorPane.setMaxWidth(800);
        anchorPane.setMaxHeight(2000);

        ScrollPane scrollPane = new ScrollPane(anchorPane);
        scrollPane.setScaleX(anchorPane.getScaleX());
        scrollPane.setScaleY(anchorPane.getScaleY());
        scrollPane.setMaxWidth(800);
        scrollPane.setMaxHeight(1000);
        scrollPane.setHvalue(0);
        scrollPane.setVvalue(0);
        scrollPane.setHmax(1);
        scrollPane.setVmax(1);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        BorderPane borderPane = new BorderPane(scrollPane);
        borderPane.setScaleX(scrollPane.getScaleX());
        borderPane.setScaleY(scrollPane.getScaleY());
        borderPane.setMaxWidth(800);
        borderPane.setMaxHeight(1000);

        Stage stage = new Stage();
        stage.setScene(new Scene(borderPane));
        stage.show();
    }

}
