package sample;

/**
 * Created by Jakob on 1/10/2019.
 */
import XMLHandler.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.Arrays;

public class MonsterCreatorController {

    @FXML
    private ScrollPane MonsterCreatorScrollPane;

    @FXML
    private AnchorPane MonsterCreatorAnchorPane;

    @FXML
    private TextField MonsterNameTextField;

    @FXML
    private TextField MonsterSizeTextField;

    @FXML
    private TextField MonsterTypeTextField;

    @FXML
    private TextField MonsterAlignmentTextField;

    @FXML
    private TextField MonsterACTextField;

    @FXML
    private TextField MonsterArmorTypeTextField;

    @FXML
    private TextField MonsterHPTextField;

    @FXML
    private TextField MonsterAmountTextField;

    @FXML
    private TextField MonsterDiceTextField;

    @FXML
    private TextField MonsterDiceTypeTextField;

    @FXML
    private ChoiceBox<String> MonsterSpeedChoiceBox;

    @FXML
    private TextField MonsterSpeedValueTextField;

    @FXML
    private Button MonsterSpeedAddButton;

    @FXML
    private TextField MonsterSTRTextField;

    @FXML
    private TextField MonsterDEXTextField;

    @FXML
    private TextField MonsterCONTextField;

    @FXML
    private TextField MonsterINTTextField;

    @FXML
    private TextField MonsterWISTextField;

    @FXML
    private TextField MonsterCHATextField;

    @FXML
    private TableView<StringTuple> MonsterAddedMovementsTableView;

    @FXML
    private TableColumn<?, ?> MonsterSpeedTypeColumn;

    @FXML
    private TableColumn<?, ?> MonsterSpeedValueColumn;

    @FXML
    private ChoiceBox<String> MonsterLanguagesChoiceBox;

    @FXML
    private Button MonsterLanguagesAddButton;

    @FXML
    private ChoiceBox<String> MonsterCRChoiceBox;

    @FXML
    private Button MonsterCRAddButton;

    @FXML
    private ChoiceBox<String> MonsterOtherChoiceBox;

    @FXML
    private TextField MonsterOtherTextField;

    @FXML
    private Button MonsterOtherAddButon;

    @FXML
    private TableView<StringTuple> MonsterDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterDetailsNameColumn;

    @FXML
    private TableColumn<?, ?> MonsterDetailsValueColumn;

    @FXML
    private TextField MosterAbilityDetailsNameTextField;

    @FXML
    private TextArea MonsterAbilityDescriptionTextArea;

    @FXML
    private Button MonsterAbilityAddButton;

    @FXML
    private TableView<Trait> MonsterAbilityDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterAbilityDetailsNameColumn;

    @FXML
    private ChoiceBox<String> MonsterInnateOrSpellcastingChoiceBox;

    @FXML
    private ChoiceBox<String> MonsterSpellcastingModifierChoiceBox;

    @FXML
    private TextField MonsterSpellSearchTextField;

    @FXML
    private Button MonsterSpellAddButton;

    @FXML
    private TableView<Spell> MonsterSpellDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterSpellDetailsSpellLevelColumn;

    @FXML
    private TableColumn<?, ?> MonsterSpellDetailsSpellNameColumn;

    @FXML
    private TextField MonsterActionNameTextField;

    @FXML
    private CheckBox IsAttackActionCheckBox;

    @FXML
    private TextField MonsterAttackActionDieAmountTextField;

    @FXML
    private TextField MonsterAttackActionDieSizeTextField;

    @FXML
    private TextField MonsterAttackActionDamageBonusTextField;

    @FXML
    private TextArea MonsterActionDescriptionTextArea;

    @FXML
    private Button MonsterActionAddButton;

    @FXML
    private TableView<Action> MonsterActionDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterActionDetailsActionNameColumn;

    @FXML
    private TextField MonsterLegendaryActionsNameTextField;

    @FXML
    private TextArea MonsterLegendaryActionsDescriptionTextArea;

    @FXML
    private Button MonsterLegendaryActionsAddButton;

    @FXML
    private TableView<LegendaryAction> MonsterLegendaryActionsDetailsTableView;

    @FXML
    private TableColumn<?, ?> MonsterLegendaryActionsDetailsLegendaryActionNameColumn;

    @FXML
    private Button MonsterAddImageButton;

    @FXML
    private Button MonsterTotalClearButton;

    @FXML
    private Button MonsterTotalSaveButton;
    private ArrayList<String> innateList;
    private ArrayList<String> spellcastingList;

    private Information.InformationBuilder informationBuilder;
    private Monster.MonsterBuilder monsterBuilder;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private GlobalController globalController;
    private XMLHandler xmlh;
    private Trait.TraitBuilder spellInnateTraitBuilder;
    private Trait.TraitBuilder spellcastingTraitBuilder;

    public void initialize(GlobalController globalController) {
        this.globalController = globalController;
        this.xmlh = globalController.getXmlh();
        ArrayList<String> spellNames = new ArrayList<>();
        innateList = new ArrayList<>(Arrays.asList("","","","","","","","","",""));
        spellcastingList = new ArrayList<>(Arrays.asList("","","","","","","","","",""));
        for (Spell spell: xmlh.getSpellHashMap().values()) {
            spellNames.add(spell.getName());
        }
        bindAutoComplete(spellNames);
        informationBuilder = new Information.InformationBuilder();
        monsterBuilder = new Monster.MonsterBuilder();
        initializeChoiceBoxes();
        initializeAddButtons();
        initializeSpecialButtons();
        initializeSaveMonsterButton();
        MonsterSTRTextField.setText("0");
        MonsterDEXTextField.setText("0");
        MonsterCONTextField.setText("0");
        MonsterINTTextField.setText("0");
        MonsterWISTextField.setText("0");
        MonsterCHATextField.setText("0");


    }

    public ScrollPane getContent() {
        return MonsterCreatorScrollPane;
    }

    private void initializeSpecialButtons() {
        MonsterTotalSaveButton.setOnAction(e ->{
            informationBuilder.name(MonsterNameTextField.getText());
            informationBuilder.size(MonsterSizeTextField.getText());
            informationBuilder.type(MonsterTypeTextField.getText());
            informationBuilder.alignment(MonsterAlignmentTextField.getText());
            informationBuilder.ac(new Stat(MonsterArmorTypeTextField.getText(),Integer.parseInt(MonsterACTextField.getText())));
            informationBuilder.hp(Integer.parseInt(MonsterHPTextField.getText()));
            informationBuilder.hp(new HitDie(Integer.parseInt(MonsterAmountTextField.getText()), Integer.parseInt(MonsterDiceTypeTextField.getText())));
            informationBuilder.str(Integer.parseInt(MonsterSTRTextField.getText()));
            informationBuilder.dex(Integer.parseInt(MonsterDEXTextField.getText()));
            informationBuilder.con(Integer.parseInt(MonsterCONTextField.getText()));
            informationBuilder.intel(Integer.parseInt(MonsterINTTextField.getText()));
            informationBuilder.wis(Integer.parseInt(MonsterWISTextField.getText()));
            informationBuilder.cha(Integer.parseInt(MonsterCHATextField.getText()));
            if(spellInnateTraitBuilder != null){
                for (String s:innateList) {
                    if(!s.isEmpty()) {
                        spellInnateTraitBuilder.text(s);
                    }
                }
                monsterBuilder.traits(spellInnateTraitBuilder.BuildTrait());
            }
            if(spellcastingTraitBuilder != null){
                for (String s:spellcastingList) {
                    if(!s.isEmpty()) {
                        spellcastingTraitBuilder.text(s);
                    }
                }
                monsterBuilder.traits(spellcastingTraitBuilder.BuildTrait());
            }
            monsterBuilder.info(informationBuilder.createInformation());
            Monster m = monsterBuilder.createMonster();
            //GØR DET PÆNERE
            xmlh.getMonsterHashMap().put(m.getInfo().getName(), m);
            globalController.getMonsterController().getMonsters().add(m);
            globalController.getCombatController().getMonsters().add(m);
            globalController.getMonsterController().initializeSearchBar();
            globalController.getCombatController().initializeMonsterSearchBar();
        });
    }

    private void initializeSaveMonsterButton() {
        //...
        saveToCustomMonsterBin();
    }

    private void initializeChoiceBoxes() {

        MonsterCRChoiceBox.getItems().addAll(Arrays.asList(new String[]{"0","1/8","1/4","1/2","1","2","3","4","5","6","7","8","9","10","11","12","12","13","14","15","16","17","18","19","20"}));
        MonsterLanguagesChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Abyssal","Aquan","Auran","Celestial","Common","Deep Speech","Draconic","Druidic","Dwarvish","Elvish","Giant"
                ,"Gnomish","Goblin","Gnoll","Halfling","Ignan","Infernal","Orc","Primordial","Sylvan","Terran","Undercommon"}));
        MonsterOtherChoiceBox.getItems().addAll(Arrays.asList(new String[]{}));
        MonsterSpeedChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Normal","Burrow","Fly","Swim","Climb"}));
        MonsterInnateOrSpellcastingChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Innate Spellcasting","Spellcasting"}));
        MonsterSpellcastingModifierChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Intelligence","Wisdom","Charisma"}));
        MonsterOtherChoiceBox.getItems().addAll(Arrays.asList(new String[]{"Senses","Saves","Skills","Damage Resistances","Damage Immunities","Condition Immunities"}));


    }

    private void initializeAddButtons() {
        //OTHERS
        //MANGLER VULNERABILITIES
        MonsterOtherAddButon.setOnAction(e ->{
            if(!MonsterOtherChoiceBox.getSelectionModel().getSelectedItem().isEmpty() && MonsterOtherTextField.getText() != null)
            switch(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem()){
                case "Senses":
                    if(monsterBuilder.getNestedSenses().isEmpty()) {
                        monsterBuilder.senses(monsterBuilder.getNestedSenses() + " " + MonsterOtherTextField.getText() + "ft.");
                    }
                    else {
                        monsterBuilder.senses(", " + monsterBuilder.getNestedSenses() + " "  + MonsterOtherTextField.getText() + "ft.");
                    }
                    MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    break;
                case "Saves":
                    Integer integer;
                    String[] SaveWords = MonsterOtherTextField.getText().split(" ");
                    if(Character.toString(SaveWords[1].charAt(0)).equals("+")){
                        SaveWords[1] = SaveWords[1].substring(1);
                    }
                    try {
                        integer =  Integer.parseInt(SaveWords[1]);
                    } catch (NumberFormatException ex) {
                        integer = null;
                    }
                    if(integer != null) {
                        Stat stat = new Stat(SaveWords[0], integer);
                        monsterBuilder.getNestedSaves().add(stat);

                        MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    }
                    break;
                case "Skills":
                    Integer i;
                    String[] words = MonsterOtherTextField.getText().split(" ");
                    if(Character.toString(words[1].charAt(0)).equals("+")){
                        words[1] = words[1].substring(1);
                    }
                    try {
                        i =  Integer.parseInt(words[1]);
                    } catch (NumberFormatException ex) {
                        i = null;
                    }
                    if(i != null) {
                        Stat stat = new Stat(words[0], i);
                        monsterBuilder.getNestedSkills().add(stat);

                        MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    }
                    break;
                case "Damage Resistances":

                        monsterBuilder.getNestedResists().add(MonsterOtherTextField.getText());

                    MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    break;
                case "Damage Immunities":

                        monsterBuilder.getNestedImmunities().add(MonsterOtherTextField.getText());

                    MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    break;
                case "Condition Immunities":
                        // VIRKER IKKE?
                        monsterBuilder.getNestedConditionImmunities().add(MonsterOtherTextField.getText());

                    MonsterDetailsTableView.getItems().add(new StringTuple(MonsterOtherChoiceBox.getSelectionModel().getSelectedItem(), MonsterOtherTextField.getText()));
                    break;

            }
        });
        //LEGENDARY ACTION
        MonsterLegendaryActionsAddButton.setOnAction(e ->{
            if(MonsterLegendaryActionsNameTextField.getText() != null){
                LegendaryAction.LegendaryActionBuilder legendaryActionBuilder = new LegendaryAction.LegendaryActionBuilder();
                legendaryActionBuilder.name(MonsterLegendaryActionsNameTextField.getText());
                legendaryActionBuilder.text(MonsterLegendaryActionsNameTextField.getText());
                LegendaryAction legendaryAction = legendaryActionBuilder.BuildLegendaryAction();
                monsterBuilder.legendaryActions(legendaryAction);
                MonsterLegendaryActionsDetailsTableView.getItems().add(legendaryAction);
            }
        });
        //MONSTER SPEED
        MonsterSpeedAddButton.setOnAction(e ->{
            if(MonsterSpeedChoiceBox.getSelectionModel().getSelectedItem() != null && MonsterSpeedValueTextField.getText() != null){
                String speed = "";
                if(!MonsterSpeedChoiceBox.getSelectionModel().getSelectedItem().equals("Normal")){
                    speed = MonsterSpeedChoiceBox.getSelectionModel().getSelectedItem();
                }
                if(monsterBuilder.getNestedSpeeds().isEmpty()) {
                    monsterBuilder.speeds(monsterBuilder.getNestedSpeeds() + " " + speed + " " + MonsterSpeedValueTextField.getText() + " ft.");
                }
                else {
                    monsterBuilder.speeds(", " + monsterBuilder.getNestedSpeeds() + " " + speed + " " + MonsterSpeedValueTextField.getText() + " ft.");
                }

                MonsterAddedMovementsTableView.getItems().add(new StringTuple(speed, MonsterSpeedValueTextField.getText()));
            }

        });
        //MONSTER LANGUAGES
        MonsterLanguagesAddButton.setOnAction(e ->{
            if(MonsterLanguagesChoiceBox.getSelectionModel().getSelectedItem() != null){
                monsterBuilder.getNestedLanguages().add(MonsterLanguagesChoiceBox.getSelectionModel().getSelectedItem());
                //LÆG TIL TABLEVIEWET
                System.out.println(MonsterLanguagesChoiceBox.getSelectionModel().getSelectedItem());
            }

        });
        //MONSTER CR
        MonsterCRAddButton.setOnAction(e ->{
            if(MonsterCRChoiceBox.getSelectionModel().getSelectedItem() != null){
                double cr = 0;
                if(MonsterCRChoiceBox.getSelectionModel().getSelectedItem().contains("/")){
                    String[] word = MonsterCRChoiceBox.getSelectionModel().getSelectedItem().split("/");
                    cr = Double.parseDouble(word[0])/Double.parseDouble(word[1]);
                }
                else{
                    cr = Double.parseDouble(MonsterCRChoiceBox.getSelectionModel().getSelectedItem());
                }
                informationBuilder.cr(cr);
                System.out.println(cr);
            }

        });
        //MONSTER ACTION
        MonsterActionAddButton.setOnAction(e ->{
            if(MonsterActionNameTextField.getText() != null){
                //...
                Action.ActionBuilder actionBuilder = new Action.ActionBuilder();
                actionBuilder.name(MonsterActionNameTextField.getText());
                actionBuilder.text(MonsterActionDescriptionTextArea.getText());
                Action action = actionBuilder.BuildAction();

                if(IsAttackActionCheckBox.isSelected()){
                    AttackAction.AttackActionBuilder attackActionBuilder = new AttackAction.AttackActionBuilder();
                    attackActionBuilder.action(action);
                    HitDie.HitDieBuilder hitDieBuilder = new HitDie.HitDieBuilder();
                    hitDieBuilder.dieAmount(Integer.parseInt(MonsterAttackActionDieAmountTextField.getText()));
                    hitDieBuilder.dieSize(Integer.parseInt(MonsterAttackActionDieAmountTextField.getText()));
                    attackActionBuilder.hitDie(hitDieBuilder.BuildHitDie());
                    attackActionBuilder.damageBonus(Integer.parseInt(MonsterAttackActionDamageBonusTextField.getText()));
                    monsterBuilder.attackActions(attackActionBuilder.BuildAttackAction());
                }
                else monsterBuilder.actions(action);
                MonsterActionDetailsTableView.getItems().add(action);
            }

        });
        //MONSTER TRAIT
        MonsterAbilityAddButton.setOnAction(e ->{
            if(MonsterAbilityDescriptionTextArea.getText() != null){
                Trait.TraitBuilder traitBuilder = new Trait.TraitBuilder();
                traitBuilder.name(MosterAbilityDetailsNameTextField.getText());
                traitBuilder.text(MonsterAbilityDescriptionTextArea.getText());
                Trait trait = traitBuilder.BuildTrait();
                monsterBuilder.traits(trait);

                MonsterAbilityDetailsTableView.getItems().add(trait);
            }

        });
        //MONSTER SPELLS

        MonsterSpellAddButton.setOnAction(e ->{
            if(MonsterInnateOrSpellcastingChoiceBox.getSelectionModel().getSelectedItem() != null
                    && MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem() != null
                    && MonsterNameTextField.getText() != null
                    && MonsterCRChoiceBox.getSelectionModel().getSelectedItem() != null){
                if(spellInnateTraitBuilder == null) {
                    spellInnateTraitBuilder = new Trait.TraitBuilder();
                }
                if(spellcastingTraitBuilder == null) {
                    spellcastingTraitBuilder = new Trait.TraitBuilder();
                }

                int proficiency;
                if(MonsterCRChoiceBox.getSelectionModel().getSelectedItem().contains("/")){
                    proficiency = 1;
                }
                else {
                    proficiency = (int) (7 + Double.parseDouble(MonsterCRChoiceBox.getSelectionModel().getSelectedItem()) / 4);
                }
                if(proficiency < 2){
                    proficiency = 2;
                }
                int spellModifier = 0;
                if (MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem().equals("Intelligence")) {
                   spellModifier =  (int)(Integer.parseInt(MonsterINTTextField.getText())-10)/2;
                }
                if (MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem().equals("Wisdom")){
                    spellModifier =  (int)(Integer.parseInt(MonsterWISTextField.getText())-10)/2;
                }
                if (MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem().equals("Charisma")){
                    spellModifier =  (int)(Integer.parseInt(MonsterCHATextField.getText())-10)/2;
                }
                int spellSaveDC = 8 + proficiency + spellModifier;
                int spellAttack = proficiency + spellModifier;
                if(MonsterInnateOrSpellcastingChoiceBox.getSelectionModel().getSelectedItem().equals("Innate Spellcasting")){

                    spellInnateTraitBuilder.name("Innate Spellcasting");
                    if(spellInnateTraitBuilder.getNestedTexts().size() == 0) {
                        spellInnateTraitBuilder.text("The " + MonsterNameTextField.getText() + "'s innate spellcasting ability is "
                                + MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem() + " (spell save DC " +
                                spellSaveDC + ", +" + spellAttack + " to hit with spell attacks). the " + MonsterNameTextField.getText() +
                                " can innately cast the following spells, requiring no material components:");
                    }
                    addSpellToText(innateList);
                }
                if(MonsterInnateOrSpellcastingChoiceBox.getSelectionModel().getSelectedItem().equals("Spellcasting")){
                    spellcastingTraitBuilder.name("Spellcasting");
                    if(spellcastingTraitBuilder.getNestedTexts().size() == 0) {
                        spellcastingTraitBuilder.text("The " + MonsterNameTextField.getText() + " spellcasting ability is "
                                + MonsterSpellcastingModifierChoiceBox.getSelectionModel().getSelectedItem() + " (spell save DC " +
                                spellSaveDC + ", +" + spellAttack + " to hit with spell attacks). the " + MonsterNameTextField.getText() +
                                " has the following spells prepared:");
                    }
                    addSpellToText(spellcastingList);
                }

            }
            //LAV EN TRAIT PÅ BAGGRUND AF DETTE DER MIRRORER DEM I MM.
            //HUSK AT LAV EN BYG FUNKTION I SAVE KNAPPEN
        });
    }

    private void bindAutoComplete(ArrayList<String> spellNames){
        if(autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(MonsterSpellSearchTextField, spellNames);

    }

    private void saveToCustomMonsterBin() {
    }

    private void addSpellToText(ArrayList<String> arrayList){
        switch(xmlh.getSpellHashMap().get(MonsterSpellSearchTextField.getText().toLowerCase()).getLevel()){
            case 0:
                if(arrayList.get(0).isEmpty()){
                    arrayList.set(0,"• Cantrips (at will): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(0,arrayList.get(0) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());
                break;
            case 1:
                if(arrayList.get(1).isEmpty()){
                    arrayList.set(1,"• 1st level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(1,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());
                break;
            case 2:
                if(arrayList.get(2).isEmpty()){
                    arrayList.set(2,"• 2nd level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(2,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());

                break;
            case 3:
                if(arrayList.get(3).isEmpty()){
                    arrayList.set(3,"• 3rd level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(3,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());

                break;
            case 4:
                if(arrayList.get(4).isEmpty()){
                    arrayList.set(4,"• 4th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(4,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());


                break;
            case 5:
                if(arrayList.get(5).isEmpty()){
                    arrayList.set(5,"• 5th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(5,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());


                break;
            case 6:
                if(arrayList.get(6).isEmpty()){
                    arrayList.set(6,"• 6th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(6,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());

                break;
            case 7:

                if(arrayList.get(7).isEmpty()){
                    arrayList.set(7,"• 7th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(7,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());
                break;
            case 8:
                if(arrayList.get(8).isEmpty()){
                    arrayList.set(8,"• 8th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(8,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());
                break;
            case 9:

                if(arrayList.get(9).isEmpty()){
                    arrayList.set(9,"• 9th level (? slots): " + MonsterSpellSearchTextField.getText().toLowerCase());
                }
                else arrayList.set(9,arrayList.get(1) + ", " + MonsterSpellSearchTextField.getText().toLowerCase());

                break;
        }
    }




    public class StringTuple {
        private String x;

        private String y;

        public StringTuple(String x, String y) {
            this.x = x;
            this.y = y;
        }

        public String getX() {
            return x;
        }
        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}
