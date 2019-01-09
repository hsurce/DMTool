package sample.Popups;

import XMLHandler.Spell;
import XMLHandler.SpellHitDie;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Jakob on 1/6/2019.
 */
public class SpellPopup extends Popup {
    ArrayList<String> schoolPool;
    int counter;
    GridPane gridPane;

    Spell spell;

    public SpellPopup(Spell spell){
        schoolPool = new ArrayList<>();
        this.spell = spell;
        gridPane = new GridPane();
        gridPane.setMaxWidth(600);
        counter = 0;
        populateSchoolList();
        BuildSpellPopUp(spell);

    }
    public void BuildSpellPopUp(Spell spell) {

/**
 * HERFRA BLIVER ET HELT POPUP VINDUE KONSTRUERET PÅ BAGGRUND AF VÆRDIER I SPELL.
 */
        if(!spell.getName().isEmpty()) {
            buildName();
        }
        if(!spell.getSchool().isEmpty()) {
            buildSchool();
        }
        if(!spell.getTime().isEmpty()) {
            buildTime();
        }
        if(!spell.getRange().isEmpty()) {
            buildRange();
        }
        if(!spell.getComponents().isEmpty()) {
            buildComponents();
        }
        if(!spell.getDuration().isEmpty()) {
            buildDuration();
        }
        if(!spell.getClasses().isEmpty()) {
            buildClasses();
        }
        if(!spell.getTexts().isEmpty()) {
            buildTexts();
        }
        if(!spell.getRolls().isEmpty()){
            buildRolls();
        }



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        scrollPane.setMaxHeight(1000);
        scrollPane.setHvalue(0);
        scrollPane.setVvalue(0);
        scrollPane.setHmax(1);
        scrollPane.setVmax(1);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        gridPane.setPrefWidth(600);
        gridPane.setScaleX(scrollPane.getScaleX());
        scrollPane.setFitToWidth(true);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        gridPane.getColumnConstraints().add(columnConstraints);


        super.stage = new Stage();
        super.stage.setTitle(spell.getName());
        Scene scene = new Scene(scrollPane, 800, 600);


        super.stage.setScene(scene);
    }

    public void buildName(){
        Label name = new Label("\n"+spell.getName());
        name.setFont(Font.font("Apple Braille", 20));
        name.setStyle("-fx-text-fill: red");
        gridPane.add(name, 0,counter,1,1);
        counter++;
    }
    public void buildSchool(){

        String levelName = "";
        String school = "";
        boolean match = false;
        switch(spell.getLevel()) {
            case 0: levelName = "Cantrip";
                match = true;
                break;
            case 1: levelName = "1st-level";
                match = true;
                break;
            case 2: levelName = "2nd-level";
                match = true;
                break;
            case 3: levelName = "3rd-level";
                match = true;
                break;
        }
        if(match == false){
            levelName = spell.getLevel()+"th-level";
        }
        for (String s: schoolPool) {

            if(Character.toString(s.charAt(0)).equals(Character.toString(spell.getSchool().charAt(0)))){
                if(spell.getSchool().length() > 1) {
                    if (Character.toString(s.charAt(1)).equalsIgnoreCase(Character.toString(spell.getSchool().charAt(1)))) {
                        school = s;
                    }
                }
                else school = s;
            }
        }
        Label levelAndSchool = new Label(levelName + " " + school);
        levelAndSchool.setFont(Font.font("Apple Braille", FontPosture.ITALIC, 16));
        gridPane.add(levelAndSchool,0,counter,1,1);
        counter++;
    }
    public void buildTime(){
        TextFlow timeFlow = new TextFlow();
        Text text1 = new Text("Casting Time: ");
        text1.setStyle("-fx-font-weight: bold");

        Text text2 = new Text(spell.getTime());
        text2.setStyle("-fx-font-weight: regular");
        timeFlow.getChildren().addAll(text1,text2);

        gridPane.add(timeFlow,0,counter,1,1);
        counter++;
    }
    public void buildRange(){
        TextFlow rangeFlow = new TextFlow();
        Text text1 = new Text("Range: ");
        text1.setStyle("-fx-font-weight: bold");

        Text text2 = new Text(spell.getRange());
        text2.setStyle("-fx-font-weight: regular");
        rangeFlow.getChildren().addAll(text1,text2);

        gridPane.add(rangeFlow,0,counter,1,1);
        counter++;
    }
    public void buildComponents(){
        TextFlow componentFlow = new TextFlow();
        Text text1 = new Text("Components: ");
        text1.setStyle("-fx-font-weight: bold");

        String concatComponents = "";
        for (String s: spell.getComponents()) {
            concatComponents = concatComponents + s;
        }
        Text text2 = new Text(concatComponents);
        text2.setStyle("-fx-font-weight: regular");
        componentFlow.getChildren().addAll(text1,text2);

        gridPane.add(componentFlow,0,counter,1,1);
        counter++;
    }
    public void buildDuration(){
        TextFlow durationFlow = new TextFlow();
        Text text1 = new Text("Duration: ");
        text1.setStyle("-fx-font-weight: bold");

        Text text2 = new Text(spell.getDuration());
        text2.setStyle("-fx-font-weight: regular");
        durationFlow.getChildren().addAll(text1,text2);

        gridPane.add(durationFlow,0,counter,1,1);
        counter++;
    }
    public void buildClasses(){
        TextFlow classesFlow = new TextFlow();
        Text text1 = new Text("Classes: ");
        text1.setStyle("-fx-font-weight: bold");

        String concatClasses = "";
        for (String s: spell.getClasses()) {
            concatClasses = concatClasses + s;
        }
        Text text2 = new Text(concatClasses);
        text2.setStyle("-fx-font-weight: regular");
        classesFlow.getChildren().addAll(text1,text2);

        gridPane.add(classesFlow,0,counter,1,1);
        counter++;
    }
    public void buildTexts(){
        for (String s: spell.getTexts()) {
            Label textLabel = new Label("\n"+s);
            textLabel.setFont(new Font(14));
            textLabel.setWrapText(true);
            gridPane.add(textLabel,0,counter,1,1);
            counter++;
        }
    }
    public void buildRolls(){
        counter++;
        ButtonBar buttonBar = new ButtonBar();
        for (SpellHitDie shd: spell.getRolls()) {
            String roll;
            String bonus = "";
            if(shd.getBonus()<0) bonus = "+"+shd.getBonus();

            roll = "\n"+shd.getDieAmount()+"d"+shd.getDieSize()+bonus;

            Button rollButton = new Button("roll: " + roll);
            rollButton.setFocusTraversable(false);
            rollButton.setOnAction(e -> {
                System.out.println("hej" + roll);
            });
            buttonBar.getButtons().add(rollButton);
        }

        gridPane.add(buttonBar, 0, counter, 1 ,1);

    }

    public void populateSchoolList(){
        schoolPool.add("Abjuration");
        schoolPool.add("Conjuration");
        schoolPool.add("Enchantment");
        schoolPool.add("Evocation");
        schoolPool.add("Divination");
        schoolPool.add("Illusion");
        schoolPool.add("Necromancy");
        schoolPool.add("Transmutation");
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
