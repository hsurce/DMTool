package sample;

import XMLHandler.Spell;
import XMLHandler.XMLHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;

public class SpellController {
    private ArrayList<String> classFilter;
    private ArrayList<String> levelFilter;
    private XMLHandler xmlh;
    private ArrayList<Spell> spells;
    private ArrayList<Spell> filteredSpellList;

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
        System.out.println("hej");
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

}
