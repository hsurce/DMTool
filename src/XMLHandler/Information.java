package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Grunden til denne klasses tilstedeværelse er blot at mindske kode-bloat i monster klassen.
 * Et monster kan have op til 30 forskellige felter af information, og ved at modularisere på denne måde
 * kan man lettere navigere i koden.
 */
public class Information implements Serializable {

    static final long serialVersionUID = 103;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }



    private String size;
    private String name;
    private ArrayList<String> type;
    private String alignment;
    private int hp;
    private int str;
    private int dex;
    private int con;
    private int intel;
    private int wis;
    private int cha;
    private double cr;
    private int passivePerception;
    private HitDie hitDie;
    private Stat ac;
    private Action reaction;
    private String innateAbility;
    private String spellCastingAbility;
    public Information(String name, String size, ArrayList<String> type, String alignment, Stat ac, int hp, HitDie hitDie,
                       int str, int dex, int con, int intel, int wis, int cha, double cr, int passivePerception, Action reaction, String innateAbility, String spellCastingAbility){
        this.name = name;
        this.size = size;
        this.type = type;
        this.alignment = alignment;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.intel = intel;
        this.wis = wis;
        this.cha = cha;
        this.cr = cr;
        this.passivePerception = passivePerception;
        this.hitDie = hitDie;
        this.ac = ac;
        this.reaction = reaction;
        this.innateAbility = innateAbility;
        this.spellCastingAbility = spellCastingAbility;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public int getWis() {
        return wis;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public int getCha() {
        return cha;
    }

    public void setCha(int cha) {
        this.cha = cha;
    }

    public double getCr() {
        return cr;
    }

    public void setCr(double cr) {
        this.cr = cr;
    }

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
    }

    public HitDie getHitDie() {
        return hitDie;
    }

    public void setHitDie(HitDie hitDie) {
        this.hitDie = hitDie;
    }

    public Stat getAc() {
        return ac;
    }

    public void setAc(Stat ac) {
        this.ac = ac;
    }

    public Action getReaction() {
        return reaction;
    }

    public void setReaction(Action reaction) {
        this.reaction = reaction;
    }

    public String getInnateAbility() {
        return innateAbility;
    }

    public void setInnateAbility(String innateAbility) {
        this.innateAbility = innateAbility;
    }

    public String getSpellCastingAbility() {
        return spellCastingAbility;
    }

    public void setSpellCastingAbility(String spellCastingAbility) {
        this.spellCastingAbility = spellCastingAbility;
    }
}
