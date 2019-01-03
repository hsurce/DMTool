package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
     * Grunden til denne klasses tilstedeværelse er blot at mindske kode-bloat i monster klassen.
     * Et monster kan have op til 30 forskellige felter af information, og ved at modularisere på denne måde
     * kan man lettere navigere i koden.
     */
    public class Spell implements Serializable {
    static final long serialVersionUID = 200;
    private String name;
    private String school;
    private String time;
    private String range;
    private String duration;
    private ArrayList<String> classes;
    private ArrayList<String> components;
    private ArrayList<String> texts;
    private ArrayList<SpellHitDie> rolls;
    private int level;

    public Spell(String name, String school, String time, String range, String duration, ArrayList<String> classes, ArrayList<String> components,
                 ArrayList<String> texts, ArrayList<SpellHitDie> rolls, int level) {
        this.name = name;
        this.school = school;
        this.time = time;
        this.range = range;
        this.duration = duration;
        this.classes = classes;
        this.components = components;
        this.texts = texts;
        this.rolls = rolls;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public ArrayList<String> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<String> components) {
        this.components = components;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<String> texts) {
        this.texts = texts;
    }

    public ArrayList<SpellHitDie> getRolls() {
        return rolls;
    }

    public void setRolls(ArrayList<SpellHitDie> rolls) {
        this.rolls = rolls;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
