package sample.ItemSkeletons;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Initiative {

    private String characterName;
    private String initiativeRoll;
    private String extraNotes;
    private Integer dexScore;
    private Integer finalInitiative;
    private Pattern p;
    private Matcher m;
    private TableView conditionTableView;
    private TableView spellConditionTableView;
    private int hp;
    private int ac;
    private int currentHP;
    private boolean hasDamImmunity;
    private boolean hasDamResistance;
    private boolean hasCondImmunity;

    public Initiative(){
        this.characterName = "";
        this.initiativeRoll = "";
        this.extraNotes = "";
        this.dexScore = 0;
    }

    public Initiative(String charactername, String initiativeroll, String extranotes, Integer dexscore) {
        this.characterName = charactername;
        this.initiativeRoll = initiativeroll;
        this.extraNotes = extranotes;
        this.dexScore = dexscore;
        calcFinalInitiative();
        this.conditionTableView = new TableView<Condition>();
        TableColumn conditionCol = new TableColumn<>();
        conditionCol.setCellValueFactory(new PropertyValueFactory<Condition, String>("conditionName"));
        conditionTableView.getColumns().add(conditionCol);
    }


    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String charactername) {
        this.characterName = charactername;
    }

    public String getInitiativeRoll() {
        return initiativeRoll;
    }

    public void setInitiativeRoll(String initiativeroll) {
        this.initiativeRoll = initiativeroll;
    }

    public String getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(String extranotes) {
        this.extraNotes = extranotes;
    }

    public int getDexScore() {
        return dexScore;
    }

    public void setDexScore(int dexscore) {
        this.dexScore = dexscore;
    }

    public void calcFinalInitiative(){
        int convertedDex = (dexScore -10)/2;
        p = Pattern.compile("^[0-9]+$");
        m = p.matcher(initiativeRoll);
        if(m.find()){
            this.finalInitiative = Integer.parseInt(initiativeRoll)+convertedDex;

        }
        else if(initiativeRoll.contains("*")) {
            if(initiativeRoll.startsWith("20")) {
                this.finalInitiative = 1000 + convertedDex;
            }
            if(initiativeRoll.startsWith("1")){
                this.finalInitiative = -1000 + convertedDex;
            }
            else{
                this.finalInitiative = 1000 + convertedDex;
            }
        }
        else finalInitiative = 0 + convertedDex;
        }


    public Integer getFinalInitiative() {
        return finalInitiative;
    }

    public void setFinalInitiative(Integer finalInitiative) {
        this.finalInitiative = finalInitiative;
    }

    public TableView getConditionTableView() {
        return conditionTableView;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public boolean isHasDamImmunity() {
        return hasDamImmunity;
    }

    public void setHasDamImmunity(boolean hasDamImmunity) {
        this.hasDamImmunity = hasDamImmunity;
    }

    public boolean isHasDamResistance() {
        return hasDamResistance;
    }

    public void setHasDamResistance(boolean hasDamResistance) {
        this.hasDamResistance = hasDamResistance;
    }

    public boolean isHasCondImmunity() {
        return hasCondImmunity;
    }

    public void setHasCondImmunity(boolean hasCondImmunity) {
        this.hasCondImmunity = hasCondImmunity;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
}
