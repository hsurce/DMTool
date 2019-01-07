package sample;

import XMLHandler.Spell;
import XMLHandler.SpellHitDie;
import XMLHandler.XMLHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sample.Popups.SpellPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SpellController {
    private ArrayList<String> classFilter;
    private ArrayList<String> levelFilter;
    private XMLHandler xmlh;
    private ArrayList<Spell> spells;
    private ArrayList<Spell> filteredSpellList;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private GlobalController globalController;

    public void initialize(GlobalController globalController){
        this.globalController = globalController;
        this.xmlh = globalController.getXmlh();

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
        ArrayList tempLevelFilter = new ArrayList();
        for(Spell spell: spells){
            if(levelFilter.isEmpty()){
                tempLevelFilter.addAll(Arrays.asList(new String[]{"0","1","2","3","4","5","6","7","8","9"}));
            }
            else tempLevelFilter = levelFilter;

            if(tempLevelFilter.contains(spell.getLevel()+"")) {
                if (classFilter.isEmpty()) {
                    isFilteredSpell = true;
                } else {
                    for (String string : spell.getClasses()) {
                        String[] word = string.split(" ");

                        if (classFilter.contains(word[0]) && !string.startsWith(" ")) {
                            isFilteredSpell = true;

                        } else if (word.length > 1) {
                            if (classFilter.contains(word[1])) {
                                isFilteredSpell = true;
                            }
                        }
                    }
                }
                if (isFilteredSpell) {
                    filteredSpellList.add(spell);
                    isFilteredSpell = false;
                }
            }

        }
        InitializeSearchBar();
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

        /**
         * HUSK AT VI EVALUERER PÅ BAGGRUND AF LOWERCASE ALTID PÅ SPELLS!
         */
        if(xmlh.getSpellHashMap().containsKey(s.toLowerCase())){
            Spell spell = xmlh.getSpellHashMap().get(s.toLowerCase());
            System.out.println("YO!");
            SpellPopup spellPopup = new SpellPopup(spell);
            globalController.checkForDuplicatePopup(spellPopup);

            SpellSearchBar.clear();
        }
    }
}
