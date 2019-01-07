package sample;

import XMLHandler.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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

    GlobalController globalController;

    public void initialize(GlobalController globalController){

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
            MonsterPopup monsterPopup = new MonsterPopup(xmlh,monster, globalController.getSpellController());
            globalController.checkForDuplicatePopup(monsterPopup);
            MonsterSearch.clear();
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


    public XMLHandler getXmlh(){
        return this.xmlh;
    }
}
