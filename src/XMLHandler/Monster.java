package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Dette er den klasse, der skal samle alle informationerne på det enkelte monster.
 */
public class Monster implements Serializable {

    static final long serialVersionUID = 105;
    private Information info;
    private ArrayList<Stat> skills;
    private String speeds;
    private ArrayList<Stat> saves;
    private String senses;
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
            Information info, ArrayList<Stat> skills, String speeds, ArrayList<Stat> saves, String senses, ArrayList<Trait> traits,
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

    public String getSpeeds() {
        return speeds;
    }

    public void setSpeeds(String speeds) {
        this.speeds = speeds;
    }

    public ArrayList<Stat> getSaves() {
        return saves;
    }

    public void setSaves(ArrayList<Stat> saves) {
        this.saves = saves;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
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
}
