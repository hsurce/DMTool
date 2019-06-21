package sample.Popups;

import XMLHandler.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.SpellController;

/**
 * Created by Jakob on 1/6/2019.
 */
public class MonsterPopup extends Popup {
    private XMLHandler xmlh;
    private Monster monster;

    private GridPane gridPane;
    private Information info;
    private int counter;
    private SpellController spellController;

    public MonsterPopup(XMLHandler xmlh, Monster monster, SpellController spellController){
        this.spellController = spellController;
        this.monster = monster;
        this.xmlh = xmlh;
        this.info = monster.getInfo();
        gridPane = new GridPane();
        BuildMonsterPopUp(monster);
    }

    public void BuildMonsterPopUp(Monster monster) {

        counter = 0;
/**
 * HERFRA BLIVER ET HELT POPUP VINDUE KONSTRUERET PÅ BAGGRUND AF VÆRDIER I SPELL.
 */
        //Name
        if(!info.getName().isEmpty()) {
            buildName();
        }
        //Size, type and Alignment
        if(!info.getSize().isEmpty()) {
            buildSizeTypeAlignment();
        }
        //Armor Class + text
        if(info.getAc() != null) {
            buildAc();
        }
        //Hp & HitDie + Bonus
        if(info.getHp() != 0) {
            buildHpHitDieBonus();
        }
        //Speeds
        if(monster.getSpeeds() != null) {
            buildSpeeds();
        }
        //Ability Scores & Modifiers
        buildAbilityScoresAndModifiers();

        //Saving throws
        if(monster.getSaves() != null) {
            buildSavingThrows();
        }
        //Skills
        if(monster.getSkills() != null) {
            buildSkills();
        }
        //DamageResistances
        if(monster.getResists() != null){
            buildDamageResistances();
        }
        //DamageImmunities
        if(monster.getImmunities() != null){
            buildDamageImmunities();
        }
        //ConditionImmunities
        if(monster.getConditionImmunities() != null){
            buildConditionImmunities();
        }
        //Senses
        if(monster.getSenses() != null){
           buildSenses();
        }
        //PassivePerception
        if(info.getPassivePerception() != 0){
            buildPassivePerception();
        }
        //Languages
        if(monster.getLanguages() != null){
            buildLanguages();
        }
        //Challenge
        if(info.getCr() != -1) {
            buildChallenge();
        }

        //Traits
        if(monster.getTraits() != null) {
            buildTraits();
        }

        //Actions
        if(monster.getActions() != null){
            buildActions();
        }
        //AttackActions

        if(monster.getAttackActions() != null) {
           buildAttackActions();
        }
        //Reaction
        if(monster.getInfo().getReaction() != null){
            buildReaction();
        }
        //LegendaryActions
        if(monster.getLegendaryActions() != null){
            buildLegendaryActions();
        }



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        //scrollPane.setMaxWidth(800);
        scrollPane.setMaxHeight(1000);
        scrollPane.setHvalue(0);
        scrollPane.setVvalue(0);
        scrollPane.setHmax(1);
        scrollPane.setVmax(1);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        gridPane.setPrefWidth(600);
        gridPane.setScaleX(scrollPane.getScaleX());
        scrollPane.setFitToWidth(true);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        gridPane.getColumnConstraints().add(columnConstraints);

        super.stage = new Stage();
        super.stage.setTitle(monster.getInfo().getName());
        Scene scene = new Scene(scrollPane, 800, 600);

        super.stage.setScene(scene);
    }

    private void buildReaction() {
        TextFlow reactionFlow = new TextFlow();
        Text text1 = new Text(monster.getInfo().getReaction().getName());
        text1.setStyle("-fx-font-weight: bold");
        String concat = "";
        for (String string: monster.getInfo().getReaction().getTexts()) {
            concat += "\n"+string;

        }
        Text text2 = new Text(concat);
        reactionFlow.getChildren().addAll(text1,text2);
        gridPane.add(reactionFlow,0,counter,1,1);
        counter++;
    }

    private void buildSkills(){

        TextFlow skillFlow = new TextFlow();
        Text text1 = new Text("Skills: ");
        text1.setStyle("-fx-font-weight: bold");

        String concat = "";
        int statCounter = 0;
        for (Stat stat :monster.getSkills()) {
            if(statCounter > 0){
                concat += ", ";
            }

            String statName = "";
            if(!stat.getName().isEmpty()){
                statName = " " + stat.getName();
            }

            if(stat.getValue() < 0) {
                concat += stat.getValue() + statName;
            }
            else concat += "+"+stat.getValue()+statName;

            statCounter++;
        }

        Text text2 = new Text(concat);

        skillFlow.getChildren().addAll(text1,text2);

        gridPane.add(skillFlow,0,counter,1,1);
        counter++;

    }
    private void buildDamageResistances(){
        TextFlow resistFlow = new TextFlow();
        Text text1 = new Text("Damage Resistances: ");
        text1.setStyle("-fx-font-weight: bold");
        int statCounter = 0;
        String concat = "";
        for (String s: monster.getResists()) {
            if(statCounter > 0){
                concat += ", ";
            }
            concat += s;
            statCounter++;
        }
        Text text2 = new Text(concat);

        resistFlow.getChildren().addAll(text1,text2);
        gridPane.add(resistFlow,0,counter,1,1);
        counter++;

    }
    private void buildDamageImmunities(){

        TextFlow immunityFlow = new TextFlow();
        Text text1 = new Text("Damage Immunities: ");
        text1.setStyle("-fx-font-weight: bold");
        int statCounter = 0;
        String concat = "";
        for (String s: monster.getImmunities()) {
            if(statCounter > 0){
                concat += ", ";
            }
            concat += s;
            statCounter++;
        }
        Text text2 = new Text(concat);

        immunityFlow.getChildren().addAll(text1,text2);
        gridPane.add(immunityFlow,0,counter,1,1);
        counter++;

    }
    private void buildConditionImmunities(){

        TextFlow conditionImmunityFlow = new TextFlow();
        Text text1 = new Text("Condition Immunities: ");
        text1.setStyle("-fx-font-weight: bold");
        int statCounter = 0;
        String concat = "";
        for (String s: monster.getConditionImmunities()) {
            if(statCounter > 0){
                concat += ", ";
            }
            concat += s;
            statCounter++;
        }
        Text text2 = new Text(concat);

        conditionImmunityFlow.getChildren().addAll(text1,text2);
        gridPane.add(conditionImmunityFlow,0,counter,1,1);
        counter++;

    }
    private void buildSenses(){

        TextFlow sensesFlow = new TextFlow();
        Text text1 = new Text("Senses: ");
        text1.setStyle("-fx-font-weight: bold");
        String concatString = "";
        for(String string: monster.getSenses()){
            concatString += string;
        }
        Text text2 = new Text(concatString);

        sensesFlow.getChildren().addAll(text1,text2);
        gridPane.add(sensesFlow,0,counter,1,1);
        counter++;

    }
    private void buildPassivePerception(){

        TextFlow ppFlow = new TextFlow();
        Text text1 = new Text("Passive Perception: ");
        text1.setStyle("-fx-font-weight: bold");
        Text text2;
        if(info.getPassivePerception() < 0){
            text2 = new Text(info.getPassivePerception()+"");
        }
        else text2 = new Text("+"+info.getPassivePerception()+"");

        ppFlow.getChildren().addAll(text1,text2);

        gridPane.add(ppFlow,0,counter,1,1);
        counter++;

    }
    private void buildLanguages(){
        TextFlow languagesFlow = new TextFlow();
        Text text1 = new Text("Languages: ");
        text1.setStyle("-fx-font-weight: bold");
        int statCounter = 0;
        String concat = "";
        for (String s: monster.getLanguages()) {
            if(statCounter > 0){
                concat += ", ";
            }
            concat += s;
            statCounter++;
        }
        Text text2 = new Text(concat);

        languagesFlow.getChildren().addAll(text1,text2);
        gridPane.add(languagesFlow,0,counter,1,1);
        counter++;
    }
    private void buildChallenge(){
        TextFlow challengeFlow = new TextFlow();
        Text text1 = new Text("Challenge Rating: ");
        text1.setStyle("-fx-font-weight: bold");
        String cr = "";
        if (info.getCr() < 1) {
            cr = convertDecimalToFraction(info.getCr());
        } else cr = Math.round(info.getCr()) + "";
        Text text2 = new Text(cr);

        challengeFlow.getChildren().addAll(text1, text2);

        gridPane.add(challengeFlow, 0, counter, 1, 1);
        counter++;
    }
    private void buildTraits() {
        //For hver trait;
        for (Trait trait : monster.getTraits()) {
            //Initialisér værdier
            boolean isSpellcasting = false;
            int textCount = 0;

            //Sæt spellcasting hvis nødvendigt.
            if (trait.getName().contains("Spellcasting") || trait.getName().contains("Innate Spellcasting")) {
                isSpellcasting = true;
            }
            //Navnet på traiten
            Label nameLabel = new Label(trait.getName());
            nameLabel.setStyle("-fx-font-weight: bold");
            gridPane.add(nameLabel, 0, counter, 1, 1);
            counter++;

            //Alt hvad traiten indeholder.
            for (String text : trait.getTexts()) {
                TextFlow textFlow = new TextFlow();
                //Hvis det er en spell:
                if (isSpellcasting && textCount > 0) {
                    String[] words = text.split(":");
                    Text text1 = new Text(words[0] + ":");
                    textFlow.getChildren().add(text1);
                    Node n;
                    if(words.length > 1) {
                        for (String s : words[1].split(",")) {
                            if (s.startsWith(" ")) {
                                s = s.substring(1);
                            }
                            boolean containedStar = false;
                            if(s.contains("*")){
                                s = s.replace("*", "");
                                containedStar = true;
                            }
                            if (xmlh.getSpellHashMap().containsKey(s.toLowerCase())) {
                                String finalS = s;
                                if(containedStar){
                                    s = s + "*";
                                }
                                n = new Hyperlink(s);
                                ((Hyperlink) n).setOnAction(e -> {
                                    SpellPopup spellPopup = new SpellPopup(xmlh.getSpellHashMap().get(finalS.toLowerCase()));
                                    spellPopup.show();
                                });
                            } else {
                                n = new Text(s + "\n");

                            }

                            textFlow.getChildren().add(n);
                        }
                    }
                    gridPane.add(textFlow, 0, counter, 1, 1);
                    counter++;
                } else {
                    textFlow.getChildren().add(new Text(text));
                    gridPane.add(textFlow, 0, counter, 1, 1);
                    counter++;
                }
                textCount++;
            }
        }


        if (monster.getActions() != null && monster.getAttackActions() != null) {
            Label actionLabel = new Label("\n Actions \n");
            actionLabel.setFont(Font.font("Apple Braille", 20));
            actionLabel.setStyle("-fx-text-fill: #B22222");
            gridPane.add(actionLabel, 0, counter, 1, 1);
            counter++;
        }
    }

    private void buildActions(){

        for (Action action: monster.getActions()) {

            TextFlow actionFlow = new TextFlow();
            Text text1 = new Text(action.getName());
            text1.setStyle("-fx-font-weight: bold");
            String concat = "";
            for (String string: action.getTexts()) {
                concat += "\n"+string;

            }
            Text text2 = new Text(concat);
            actionFlow.getChildren().addAll(text1,text2);
            gridPane.add(actionFlow,0,counter,1,1);
            counter++;

        }

    }
    private void buildAttackActions(){
        counter++;
        for (AttackAction attackAction: monster.getAttackActions()){
            Action action = attackAction.getAction();

            TextFlow actionFlow = new TextFlow();
            Text text1 = new Text(action.getName());
            text1.setStyle("-fx-font-weight: bold");
            String concat = "";
            for (String string: action.getTexts()) {
                concat += "\n"+string;

            }
            Text text2 = new Text(concat);
            actionFlow.getChildren().addAll(text1,text2);
            gridPane.add(actionFlow,0,counter,1,1);
            counter++;
            Button b;
            if(attackAction.getHitDie() != null) {
                b = new Button(attackAction.getHitDie().getDieAmount() + "d" +
                        attackAction.getHitDie().getDieSize() + " + " + attackAction.getDamageBonus());
                b.setFocusTraversable(false);
                gridPane.add(b, 0, counter, 1, 1);
                counter++;
            }

        }
    }
    private void buildLegendaryActions(){
        Label actionLabel = new Label("\n Legendary Actions \n");
        actionLabel.setFont(Font.font("Apple Braille", 20));
        actionLabel.setStyle("-fx-text-fill: #B22222");
        gridPane.add(actionLabel, 0, counter, 1, 1);
        counter++;
        for (Action action: monster.getLegendaryActions()) {
            TextFlow actionFlow = new TextFlow();
            Text text1 = new Text(action.getName());
            text1.setStyle("-fx-font-weight: bold");
            String concat = "";
            for (String string: action.getTexts()) {
                concat += "\n"+string;

            }
            Text text2 = new Text(concat);
            actionFlow.getChildren().addAll(text1,text2);
            gridPane.add(actionFlow,0,counter,1,1);
            counter++;
        }
    }


    private void buildSavingThrows() {
        TextFlow saveFlow = new TextFlow();
        Text text1 = new Text("\nSaves: ");
        text1.setStyle("-fx-font-weight: bold");

        String concat = "";
        int statCounter = 0;
        for (Stat stat :monster.getSaves()            ) {
            if(statCounter > 0){
                concat += ", ";
            }

            String statName = "";
            if(!stat.getName().isEmpty()){
                statName = " " + stat.getName();
            }
            if(stat.getValue() < 0) {
                concat += stat.getValue() + statName;
            }
            else concat += "+"+stat.getValue()+statName;
            statCounter++;
        }

        Text text2 = new Text(concat);

        saveFlow.getChildren().addAll(text1,text2);

        gridPane.add(saveFlow,0,counter,1,1);
        counter++;

    }

    private void buildAbilityScoresAndModifiers() {
        TextFlow abilityStaticFlow = new TextFlow();
        String[] staticAbilities = new String[]{"Str","Dex","Con","Int","Wis","Cha"};

        for (String s: staticAbilities) {
            Text t = new Text(s+"\t");
            t.setFont(Font.font("Apple Braille", 20));
            t.setStyle("-fx-text-fill: #B22222");
            abilityStaticFlow.getChildren().add(t);
        }
        gridPane.add(abilityStaticFlow,0,counter,1,1);
        counter++;
        int[] dynamicAbilities = new int[]{monster.getInfo().getStr(),monster.getInfo().getDex(),monster.getInfo().getCon(),
                monster.getInfo().getIntel(), monster.getInfo().getWis(),monster.getInfo().getCha()};

        TextFlow abilityDynamicFlow = new TextFlow();
        for (int i: dynamicAbilities) {
            Text t;
            int modifier = 0;
            modifier =  Math.toIntExact(Math.round(Math.floor((i-10)/2)));
            if(modifier < 0){
                t = new Text(i + "("+modifier+")  ");
                t.setFont(Font.font("Apple Braille", 14));
            }
            else {
                t = new Text(i + "(+" + modifier + ") ");
                t.setFont(Font.font("Apple Braille", 14));
            }
            abilityDynamicFlow.getChildren().add(t);
        }
        gridPane.add(abilityDynamicFlow,0,counter,1,1);
        counter++;
    }

    private void buildSpeeds() {
        TextFlow speedFlow = new TextFlow();
        Text text1 = new Text("Speed: ");
        text1.setStyle("-fx-font-weight: bold");

        Text text2 = new Text(monster.getSpeeds() +"\n");

        speedFlow.getChildren().addAll(text1,text2);

        gridPane.add(speedFlow,0,counter,1,1);
        //HOPPER 2 GANGE AF FORMATERINGSÅRSAGER
        counter++;
        counter++;
    }

    private void buildHpHitDieBonus() {
        TextFlow hpFlow = new TextFlow();
        Text text1 = new Text("Hit Points: ");
        text1.setStyle("-fx-font-weight: bold");
        String hitDieString = "";
        if (info.getHitDie() != null) {
            hitDieString = info.getHitDie().getDieAmount() + "d" + info.getHitDie().getDieSize();
        }
        int bonusHealth = ((info.getCon()-10)/2)*info.getHitDie().getDieAmount();
        Text text2 = new Text(info.getHp() + " (" + hitDieString + "+" + bonusHealth + ")");

        hpFlow.getChildren().addAll(text1,text2);

        gridPane.add(hpFlow,0,counter,1,1);
        counter++;
    }

    private void buildAc() {
        TextFlow acFlow = new TextFlow();
        Text text1 = new Text("Armor Class: ");
        text1.setStyle("-fx-font-weight: bold");
        String statName = "";
        if(info.getAc().getName() != null){
            statName = " "+info.getAc().getName();
        }

        Text text2 = new Text(info.getAc().getValue()+statName);

        acFlow.getChildren().addAll(text1,text2);

        gridPane.add(acFlow,0,counter,1,1);
        counter++;

    }


    private void buildSizeTypeAlignment() {

        String concat = "\n"+info.getSize();
        if(!info.getType().isEmpty()){
            concat = concat + " " + info.getType();
        }

        if(!info.getAlignment().isEmpty()){
            concat = concat + ", " + info.getAlignment();
        }
        Label sizeTypeAlignment = new Label(concat);
        sizeTypeAlignment.setFont(Font.font("Apple Braille", 12));
        sizeTypeAlignment.setStyle("-fx-text-fill: #B22222");
        gridPane.add(sizeTypeAlignment, 0,counter,1,1);
        counter++;
    }

    private void buildName() {

        Label name = new Label("\n"+info.getName());

        name.setFont(Font.font("Apple Braille", 20));
        name.setStyle("-fx-text-fill: red");
        gridPane.add(name, 0,counter,1,1);
        counter++;
    }


    /**
     * En metode der ændrer feks. 0.25 til 1/4
     * @param x
     * @return
     */
    static private String convertDecimalToFraction(double x){
        if (x < 0){
            return "-" + convertDecimalToFraction(-x);
        }
        double tolerance = 1.0E-6;
        double h1=1; double h2=0;
        double k1=0; double k2=1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1; h1 = a*h1+h2; h2 = aux;
            aux = k1; k1 = a*k1+k2; k2 = aux;
            b = 1/(b-a);
        } while (Math.abs(x-h1/k1) > x*tolerance);
        return Math.round(h1)+"/"+Math.round(k1);
    }
    public GridPane getGridPane() {
        return gridPane;
    }
}
