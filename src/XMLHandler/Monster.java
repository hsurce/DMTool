package XMLHandler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Dette er den klasse, der skal samle alle informationerne på det enkelte monster.
 */
public class Monster implements Serializable {

    static final long serialVersionUID = 105;
    private Information info;
    private ArrayList<Stat> skills;
    private ArrayList<String> speeds;
    private ArrayList<Stat> saves;
    private ArrayList<String> senses;
    private ArrayList<Trait> traits;
    private ArrayList<Action> actions;
    private ArrayList<Action> legendaryActions;
    private ArrayList<AttackAction> attackActions;
    private ArrayList<String> languages;
    private ArrayList<String> resists;
    private ArrayList<String> immunities;
    private ArrayList<String> conditionImmunities;
    private ArrayList<String> spells;
    private ArrayList<Integer> spellSlots;
    private ArrayList<String> vulnerable;
    public Monster(
            Information info, ArrayList<Stat> skills, ArrayList<String> speeds, ArrayList<Stat> saves, ArrayList<String> senses, ArrayList<Trait> traits,
            ArrayList<Action> actions, ArrayList<Action> legendaryActions, ArrayList<AttackAction> attackActions, ArrayList<String> languages,
            ArrayList<String> resists, ArrayList<String> immunities, ArrayList<String> conditionImmunities, ArrayList<String> spells, ArrayList<Integer> spellSlots, ArrayList<String> vulnerable){

        this.info = info;
        this.skills = skills;
        this.speeds = speeds;
        this.saves = saves;
        this.senses = senses;
        this.traits = traits;
        this.actions = actions;
        this.legendaryActions = legendaryActions;
        this.attackActions = attackActions;
        this.languages = languages;
        this.resists = resists;
        this.immunities = immunities;
        this.conditionImmunities = conditionImmunities;
        this.spells = spells;
        this.spellSlots = spellSlots;
        this.vulnerable = vulnerable;
    }

    public Information getInfo() {
        return info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public ArrayList<Stat> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Stat> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getSpeeds() {
                if(speeds != null){
                    return speeds;
                }
                else{
                    speeds = new ArrayList<>();
                    return speeds;
                }
    }

    public void setSpeeds(ArrayList<String> speeds) {
        this.speeds = speeds;
    }

    public ArrayList<Stat> getSaves() {
        return saves;
    }

    public void setSaves(ArrayList<Stat> saves) {
        this.saves = saves;
    }

    public ArrayList<String> getSenses() {
        if(senses != null) {
            return senses;
        }
        else{
            senses = new ArrayList<>();
            return senses;
        }
    }

    public void setSenses(ArrayList<String> senses) {
        this.senses = senses;
    }

    public ArrayList<Trait> getTraits() {
        return traits;
    }

    public void setTraits(ArrayList<Trait> traits) {
        this.traits = traits;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public ArrayList<Action> getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(ArrayList<Action> legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public ArrayList<AttackAction> getAttackActions() {
        return attackActions;
    }

    public void setAttackActions(ArrayList<AttackAction> attackActions) {
        this.attackActions = attackActions;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getResists() {
        return resists;
    }

    public void setResists(ArrayList<String> resists) {
        this.resists = resists;
    }

    public ArrayList<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(ArrayList<String> immunities) {
        this.immunities = immunities;
    }

    public ArrayList<String> getConditionImmunities() {
        return conditionImmunities;
    }

    public void setConditionImmunities(ArrayList<String> conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    public ArrayList<String> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<String> spells) {
        this.spells = spells;
    }

    public ArrayList<Integer> getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(ArrayList<Integer> spellSlots) {
        this.spellSlots = spellSlots;
    }

    public ArrayList<String> getVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(ArrayList<String> vulnerable) {
        this.vulnerable = vulnerable;
    }

    public static class MonsterBuilder{

        Information nestedInfo;

        public ArrayList<Stat> getNestedSkills() {
            if(nestedSkills == null) nestedSkills = new ArrayList<Stat>();
            return nestedSkills;
        }

        public ArrayList<Stat> getNestedSaves() {
            if(nestedSaves == null) nestedSaves = new ArrayList<Stat>();
            return nestedSaves;
        }

        public ArrayList<String> getNestedResists() {

            if(nestedResists == null) nestedResists = new ArrayList<String>();
            return nestedResists;
        }

        public ArrayList<String> getNestedImmunities() {

            if(nestedImmunities == null) nestedImmunities = new ArrayList<String>();
            return nestedImmunities;
        }

        public ArrayList<String> getNestedConditionImmunities() {

            if(nestedConditionImmunities == null) nestedConditionImmunities = new ArrayList<String>();
            return nestedConditionImmunities;
        }

        public ArrayList<Action> getNestedActions() {

            if(nestedActions == null) nestedActions = new ArrayList<Action>();
            return nestedActions;
        }

        public ArrayList<AttackAction> getNestedAttackActions() {

            if(nestedAttackActions == null) nestedAttackActions = new ArrayList<AttackAction>();
            return nestedAttackActions;
        }

        public ArrayList<Action> getNestedLegendaryActions() {

            if(nestedLegendaryActions == null) nestedLegendaryActions = new ArrayList<Action>();
            return nestedLegendaryActions;
        }
        public ArrayList<Trait> getNestedTraits() {

            if(nestedTraits == null) nestedTraits = new ArrayList<Trait>();
            return nestedTraits;
        }

        public void setNestedSenses(ArrayList<String> s){
            nestedSenses = s;
        }

        public void setNestedSpeeds(ArrayList<String> s){
            nestedSpeeds = s;
        }


        public ArrayList<String> getNestedSpeeds() {
            if(nestedSpeeds != null){
                return nestedSpeeds;
            }
            else{
                nestedSpeeds = new ArrayList<>();
                return nestedSpeeds;
            }

        }

        public ArrayList<String> getNestedSenses() {

            if(nestedSenses != null){
                return nestedSenses;
            }
            else{
                nestedSenses = new ArrayList<>();
                return nestedSenses;
            }
        }

        ArrayList<Stat> nestedSkills;

        private ArrayList<String> nestedSpeeds;

        private ArrayList<String> nestedSenses;
        ArrayList<Stat> nestedSaves;
        ArrayList<Trait> nestedTraits;
        ArrayList<Action> nestedActions;
        ArrayList<Action> nestedLegendaryActions;
        ArrayList<AttackAction> nestedAttackActions;

        ArrayList<String> nestedLanguages;

        ArrayList<String> nestedResists;
        ArrayList<String> nestedImmunities;
        ArrayList<String> nestedConditionImmunities;
        ArrayList<String> nestedSpells;
        ArrayList<String> nestedVulnerable;
        ArrayList<Integer> nestedSpellSlots;
        public ArrayList<String> getNestedLanguages() {
            if(nestedLanguages == null){
                nestedLanguages = new ArrayList<>();
            }
            return nestedLanguages;
        }

        /**
         * Vi gør brug af et builder-pattern for at gøre arbejdet nemmest muligt.
         * Ved at bruge denne struktur kan vi sørge for at XMLParseren ikke skal
         * junglere med mange forskellige værdier indtil de yderste tags den behandler
         * bliver sluttet. I stærk kontrast til det, kan man ved brug af denne struktur
         * blot passere værdierne til builderen, hvorefter builderen tager sig af at oplagre
         * værdierne, indtil det egentlige objekt skal dannes.
         */
        public MonsterBuilder(){
            /**
             nestedSkills = new ArrayList<>();
             nestedSpeeds = "";
             nestedSenses = "";
             nestedSaves = new ArrayList<>();
             nestedTraits = new ArrayList<>();
             nestedActions = new ArrayList<>();
             nestedLegendaryActions = new ArrayList<>();
             nestedAttackActions = new ArrayList<>();
             nestedLanguages = new ArrayList<>();
             nestedResists = new ArrayList<>();
             nestedImmunities = new ArrayList<>();
             nestedConditionImmunities = new ArrayList<>();
             nestedSpells = new ArrayList<>();
             nestedSpellSlots = new ArrayList<>();
             nestedVulnerable = new ArrayList<>();
            */
        }

        public void info(final Information newInfo){
            this.nestedInfo = newInfo;
        }


        public void speeds(String newSpeed){
            if(nestedSpeeds != null) {
                nestedSpeeds.add(newSpeed);
            }
            else{
                nestedSpeeds = new ArrayList<>();
                nestedSpeeds.add(newSpeed);
            }
        }

        public void skills(ArrayList<Stat> newSkills){
            this.nestedSkills = newSkills;
        }

        public void saves(ArrayList<Stat> newSaves){
            this.nestedSaves = newSaves;
        }

        public void senses(String newSense){
            if(nestedSenses != null) {
                this.nestedSenses.add(newSense);
            }
            else{
                nestedSenses = new ArrayList<>();
                this.nestedSenses.add(newSense);
            }
        }

        public void traits(Trait newTraits){
            if(nestedTraits != null) {
                this.nestedTraits.add(newTraits);
            }
            else {
                nestedTraits = new ArrayList<>();
                nestedTraits.add(newTraits);
            }
        }

        public void actions(Action newActions){
            if(nestedActions != null) {
                this.nestedActions.add(newActions);
            }
            else {
                nestedActions = new ArrayList<>();
                nestedActions.add(newActions);
            }

        }

        public void legendaryActions(Action newLegendaryActions){
            if(nestedLegendaryActions != null) {
                this.nestedLegendaryActions.add(newLegendaryActions);
            }
            else{
                nestedLegendaryActions = new ArrayList<>();
                nestedLegendaryActions.add(newLegendaryActions);
            }
        }

        public void attackActions(AttackAction newAttackActions){
            if(nestedAttackActions != null) {
                this.nestedAttackActions.add(newAttackActions);
            }
            else{
                nestedAttackActions = new ArrayList<>();
                nestedAttackActions.add(newAttackActions);
            }
        }

        /**
         * En metode som splitter en string på kommaer, og derefter indsætter rest-værdierne i en arraylist
         * som senere kan videresendes til det relevante monsterobjekt. Dette er en meget generel metode
         * der bliver brugt til at lave arraylists til forskellige formål.
         * @param s

         * @return
         */
        public ArrayList<String> ProcessMonsterString(String s){
            ArrayList<String> al = new ArrayList<>();
            String[] word = s.split(",");
            for(int i = 0; i<word.length; i++){
                al.add(word[i]);
            }
            return al;
        }

        public void languages(ArrayList<String> newLanguages){
            this.nestedLanguages = newLanguages;
        }

        public void vulnerable(ArrayList<String> newVulnerable){this.nestedVulnerable = newVulnerable;}

        public void resists(ArrayList<String> newResists){
            this.nestedResists = newResists;
        }

        public void immunities(ArrayList<String> newImmunities){
            this.nestedImmunities = newImmunities;
        }

        public void conditionImmunities(ArrayList<String> newConditionImmunities){
            this.nestedConditionImmunities = newConditionImmunities;
        }
        public void spells(ArrayList<String> newSpells){
            this.nestedSpells = newSpells;
        }

        public void spellSlots(Integer newSlots){
            if(nestedSpellSlots != null) {
                this.nestedSpellSlots.add(newSlots);
            }
            else{
                nestedSpellSlots = new ArrayList<>();
                nestedSpellSlots.add(newSlots);
            }
        }

        public Monster createMonster(){
            Monster monster = new Monster(nestedInfo,nestedSkills,nestedSpeeds,nestedSaves,nestedSenses,nestedTraits,nestedActions,
                    nestedLegendaryActions,nestedAttackActions,nestedLanguages,nestedResists,nestedImmunities,nestedConditionImmunities, nestedSpells,nestedSpellSlots,nestedVulnerable);
            try {
                checkNull();
                System.out.println(this.nestedInfo.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return monster;
        }
        public void checkNull() throws IllegalAccessException {
            for (Field f : getClass().getDeclaredFields()){
                if (f.get(this) == null)
                    System.out.println(f.getName());
            }
        }


    }
}

