package sample.ItemSkeletons;

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
            this.finalInitiative = 1000 + convertedDex;
        }
        else finalInitiative = 0 + convertedDex;
        }


    public Integer getFinalInitiative() {
        return finalInitiative;
    }

    public void setFinalInitiative(Integer finalInitiative) {
        this.finalInitiative = finalInitiative;
    }
}
