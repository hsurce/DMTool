package sample;

import XMLHandler.XMLHandler;
import sample.Popups.Popup;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Jakob on 1/7/2019.
 */
public class GlobalController {



    private XMLHandler xmlh;
    private CombatController combatController;
    private MonsterController monsterController;
    private SpellController spellController;
    private ItemController itemController;
    private MagicItemController magicItemController;
    private NameGeneratorController nameGeneratorController;
    private HashMap<String, Popup> checkPopupMap;

    public GlobalController(XMLHandler xmlh, CombatController combatController, MonsterController monsterController,
                            SpellController spellController, ItemController itemController, MagicItemController magicItemController,
                            NameGeneratorController nameGeneratorController)
    {
        this.checkPopupMap = new HashMap<>();
        this.xmlh = xmlh;
        this.combatController = combatController;
        this.monsterController = monsterController;
        this.spellController = spellController;
        this.itemController = itemController;
        this.magicItemController = magicItemController;
        this.nameGeneratorController = nameGeneratorController;

    }

    public XMLHandler getXmlh() {
        return xmlh;
    }

    public CombatController getCombatController() {
        return combatController;
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public SpellController getSpellController() {
        return spellController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public MagicItemController getMagicItemController() {
        return magicItemController;
    }

    public NameGeneratorController getNameGeneratorController() {
        return nameGeneratorController;
    }

    public void checkForDuplicatePopup(Popup popup) {
        if(checkPopupMap.containsKey(popup.getStageTitle())){
            Popup p = checkPopupMap.get(popup.getStageTitle());
            p.closeStage();
            checkPopupMap.remove(popup.getStageTitle());
        }
        checkPopupMap.put(popup.getStageTitle(), popup);
    }
}
