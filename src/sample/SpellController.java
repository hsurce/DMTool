package sample;

import XMLHandler.Spell;
import XMLHandler.SpellHitDie;
import XMLHandler.XMLHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.Collections;

public class SpellController {
    private ArrayList<String> classFilter;
    private ArrayList<String> levelFilter;
    private XMLHandler xmlh;
    private ArrayList<Spell> spells;
    private ArrayList<Spell> filteredSpellList;
    private AutoCompletionBinding<String> autoCompletionBinding;


    public void Initialize(XMLHandler xmlh){
        this.xmlh = xmlh;
        spells = new ArrayList(xmlh.getSpellHashMap().values());
        Collections.sort(spells, (item, t1) -> {
            String s1 = item.getName();
            String s2 = t1.getName();
            return s1.compareToIgnoreCase(s2);
        });
        classFilter = new ArrayList<>();
        levelFilter = new ArrayList<>();
        filteredSpellList = new ArrayList<>();
        setButtons();
        InitializeSearchBar();


    }

    private void InitializeSearchBar() {
        ArrayList<String> spellNames = new ArrayList<>();
        TextFields.bindAutoCompletion(SpellSearchBar, spellNames);
        if(filteredSpellList.isEmpty()) {
            for (Spell spell : spells) {
                spellNames.add(spell.getName());
            }
            bindAutoComplete(spellNames);
        }
        else{
            for (Spell spell: filteredSpellList) {
                spellNames.add(spell.getName());
            }
            /**
             * KAN GØRES PÆNERE
             *
             */
           bindAutoComplete(spellNames);
        }
    }

    private void Filter(){
        filteredSpellList.clear();
        boolean isFilteredSpell = false;
        for(Spell spell: spells){
            if(levelFilter.contains(spell.getLevel()+"") || levelFilter.isEmpty()) {
                for(String string: spell.getClasses()){
                    String[] word = string.split(" ");

                    if(classFilter.contains(word[0]) && !string.startsWith(" ")){
                        isFilteredSpell = true;

                    }
                    else if(word.length > 1){
                        if(classFilter.contains(word[1])){
                            isFilteredSpell = true;
                        }
                    }
                    }
                }
            if(isFilteredSpell){
                filteredSpellList.add(spell);
                isFilteredSpell = false;
            }

        }
        InitializeSearchBar();
        System.out.println(filteredSpellList);
    }

    /**
     *
     * @param toggleButton
     * @param filterType takes "Levels" to filterlist on levels. Takes "Classes" to add to filterlist on classes.
     */
    private void addButtonToFilter(ToggleButton toggleButton, String filterType){
        if(filterType.equals("Classes")) {
            classFilter.add(toggleButton.getText());
        }
        else if(filterType.equals("Levels")){
            if(toggleButton.getText().equals("Cantrips")){
                levelFilter.add("0");
            }
            else{
                Character c = toggleButton.getText().charAt(0);
                levelFilter.add(c.toString());
            }
        }
        Filter();
    }
    private void removeButtonFromFilter(ToggleButton toggleButton, String filterType){
        if(filterType.equals("Classes")) {
            classFilter.remove(toggleButton.getText());
        }
        else if(filterType.equals("Levels")){
            if(toggleButton.getText().equals("Cantrips")){

                levelFilter.remove("0");
            }
            else{
                Character c = toggleButton.getText().charAt(0);
                levelFilter.remove(c.toString());
            }
        }
        Filter();
    }
    /**
     * FOR FILTER FUNKTION:
     * 1. Lav arrayList(A1) på baggrund af navnene på knapperne når de bliver trykket på(både ved aktivering og deaktivering).
     * 2. Konstruér en arrayList(A2) på baggrund af hashmappet ved at kalde hashmap.values() og læg det i en ArrayList(collection?)
     * 3. For-loop på hver spell i values for at lave Venn-diagrammet. For hver entry i A2, skal der kaldes entry.getClasses().ContainsAll(A1).
     * 4. Hvis denne returnerer true, skal de lægges i result-arrayListen og blive vist i spell listen.
     * PROBLEM: Der er ikke taget højde for domains( f.eks. er Fireball også en Cleric (Light) domain spell el. Warlock (Fiend) )
     *
     * LØSNING: Udvid Spells til at indeholde et Domain field som bliver konstrueret ved lede efter parenteser efter String.split er blevet kaldt,
     * og læg derefter både class navn og domain navn (feks. "Cleric (Light)") i en domain ArrayList, skær hele domainet fra i
     * class name og læg så class name i class arrayListen.
     * Så kan vi skrive "Archetype specific: CL: Light, WA: Fiend" el. noget lign. ud for spellens navn i spell listen.
     *
     * Der er heller ikke taget højde for hvilken bog de kommer fra. Vi kunne dog loope på om navnet indeholder ( .. ) da dette vil betyde det ikke er players.
     *
     */
    @FXML
    public AnchorPane content;

    @FXML
    public TextField SpellSearchBar;

    @FXML
    public ToggleButton CantripsToggleButton;

    @FXML
    public ToggleButton firstToggleButton;

    @FXML
    public ToggleButton secondToggleButton;

    @FXML
    public ToggleButton thirdToggleButton;

    @FXML
    public ToggleButton fourthToggleButton;

    @FXML
    public ToggleButton fifthToggleButton;

    @FXML
    public ToggleButton sixthToggleButton;

    @FXML
    public ToggleButton seventhToggleButton;

    @FXML
    public ToggleButton eighthToggleButton;

    @FXML
    public ToggleButton ninethToggleButton;

    @FXML
    public ChoiceBox<?> SourceChoiceBox;

    @FXML
    public ToggleButton BardToggleButton;

    @FXML
    public ToggleButton ClericToggleButton;

    @FXML
    public ToggleButton DruidToggleButton;

    @FXML
    public ToggleButton PaladinToggleButton;

    @FXML
    public ToggleButton RangerToggleButton;

    @FXML
    public ToggleButton SorcererToggleButton;

    @FXML
    public ToggleButton WarlockToggleButton;

    @FXML
    public ToggleButton WizardToggleButton;

    private void setClassButtonAction(ToggleButton button){
        button.setOnAction(e -> {
            if(button.isSelected()){
                addButtonToFilter(button, "Classes");
            }
            else{
                removeButtonFromFilter(button,"Classes");
            }
        });
    }

    private void setLevelButtonAction(ToggleButton button){
        button.setOnAction(e -> {
            if(button.isSelected()){
                addButtonToFilter(button, "Levels");
            }
            else{
                removeButtonFromFilter(button,"Levels");
            }
        });
    }

    private void setButtons(){
        setClassButtonAction(BardToggleButton);
        setClassButtonAction(ClericToggleButton);
        setClassButtonAction(DruidToggleButton);
        setClassButtonAction(PaladinToggleButton);
        setClassButtonAction(RangerToggleButton);
        setClassButtonAction(SorcererToggleButton);
        setClassButtonAction(WarlockToggleButton);
        setClassButtonAction(WizardToggleButton);

        setLevelButtonAction(CantripsToggleButton);
        setLevelButtonAction(firstToggleButton);
        setLevelButtonAction(secondToggleButton);
        setLevelButtonAction(thirdToggleButton);
        setLevelButtonAction(fourthToggleButton);
        setLevelButtonAction(fifthToggleButton);
        setLevelButtonAction(sixthToggleButton);
        setLevelButtonAction(seventhToggleButton);
        setLevelButtonAction(eighthToggleButton);
        setLevelButtonAction(ninethToggleButton);

    }
    private void bindAutoComplete(ArrayList<String> spellNames){
        if(autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(SpellSearchBar, spellNames);
        autoCompletionBinding.setOnAutoCompleted(event ->
                handleCompletion(event.getCompletion()));

    }

    public void handleCompletion(String s) {

        if(xmlh.getSpellHashMap().containsKey(s)){
            Spell spell = xmlh.getSpellHashMap().get(s);
            System.out.println("YO!");
            BuildSpellPopUp(spell);
        }
    }

    private void BuildSpellPopUp(Spell spell) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(600);
        int counter = 0;

        if(!spell.getName().isEmpty()) {
            Label name = new Label(spell.getName());
            name.setFont(Font.font("Apple Braille", 20));
            name.setStyle("-fx-text-fill: red");
            gridPane.add(name, 0,counter,1,1);
            counter++;
        }
        if(!spell.getSchool().isEmpty()) {

            String levelName = "";

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

            Label levelAndSchool = new Label(levelName + " " + spell.getSchool());
            levelAndSchool.setFont(Font.font("Apple Braille", FontPosture.ITALIC, 16));
            gridPane.add(levelAndSchool,0,counter,1,1);
            counter++;
        }
        if(!spell.getTime().isEmpty()) {
            TextFlow timeFlow = new TextFlow();
            Text text1 = new Text("Casting Time: ");
            text1.setStyle("-fx-font-weight: bold");

            Text text2 = new Text(spell.getTime());
            text2.setStyle("-fx-font-weight: regular");
            timeFlow.getChildren().addAll(text1,text2);

            gridPane.add(timeFlow,0,counter,1,1);
            counter++;
        }
        if(!spell.getRange().isEmpty()) {
            TextFlow rangeFlow = new TextFlow();
            Text text1 = new Text("Range: ");
            text1.setStyle("-fx-font-weight: bold");

            Text text2 = new Text(spell.getRange());
            text2.setStyle("-fx-font-weight: regular");
            rangeFlow.getChildren().addAll(text1,text2);

            gridPane.add(rangeFlow,0,counter,1,1);
            counter++;
        }
        if(!spell.getComponents().isEmpty()) {
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
        if(!spell.getDuration().isEmpty()) {
            TextFlow durationFlow = new TextFlow();
            Text text1 = new Text("Duration: ");
            text1.setStyle("-fx-font-weight: bold");

            Text text2 = new Text(spell.getDuration());
            text2.setStyle("-fx-font-weight: regular");
            durationFlow.getChildren().addAll(text1,text2);

            gridPane.add(durationFlow,0,counter,1,1);
            counter++;
        }
        if(!spell.getClasses().isEmpty()) {
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
        if(!spell.getTexts().isEmpty()) {
            for (String s: spell.getTexts()) {
                Label textLabel = new Label("\n"+s);
                textLabel.setWrapText(true);
                gridPane.add(textLabel,0,counter,1,1);
                counter++;
            }
        }
        if(!spell.getRolls().isEmpty()){

            for (SpellHitDie shd: spell.getRolls()) {
                String roll;
                roll = shd.getDieAmount()+"d"+shd.getDieSize()+"+"+shd.getBonus();
                gridPane.add(new Label(roll),0,counter,1,1);
                counter++;
                Button rollButton = new Button("roll: " + roll);
                rollButton.setOnAction(e -> {
                    System.out.println("hej" + roll);
                });
                gridPane.add(rollButton, 1, counter, 1 ,1);
            }
        }




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
