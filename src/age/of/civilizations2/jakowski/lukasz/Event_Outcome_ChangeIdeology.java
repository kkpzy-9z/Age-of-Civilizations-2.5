package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Event_Outcome_ChangeIdeology extends Event_Outcome
{
    protected int iCivID;
    protected int iValue;
    
    Event_Outcome_ChangeIdeology() {
        super();
        this.iCivID = -1;
        this.iValue = -1;
    }
    
    @Override
    protected int getCivID() {
        return this.iCivID;
    }
    
    @Override
    protected void setCivID(final int nCivID) {
        this.iCivID = nCivID;
    }
    
    @Override
    protected boolean updateCivIDAfterRemove(final int nRemovedCivID) {
        if (this.iCivID == nRemovedCivID) {
            this.iCivID = -1;
            return true;
        }
        if (nRemovedCivID < this.iCivID) {
            --this.iCivID;
        }
        return false;
    }
    
    @Override
    protected int getValue() {
        return this.iValue;
    }
    
    @Override
    protected void setValue(final int nValue) {
        this.iValue = nValue;
    }
    
    @Override
    protected void outcomeAction() {
        if (this.getValue() < 0) {
            this.iValue = CFG.oR.nextInt(CFG.ideologiesManager.getIdeologiesSize());
        }
        if (this.getCivID() >= 0 && this.getCivID() < CFG.game.getCivsSize()) {
            try {
                /*CFG.game.getCiv(this.getCivID()).setIdeologyID(this.getValue());
                CFG.game.getCiv(this.getCivID()).setCivTag(CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.getCivID()).getCivTag()) + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(this.getCivID()).getIdeologyID()).getExtraTag());
                CFG.game.getCiv(this.getCivID()).loadFlag();
                */

                //updated ideology update, scraped from diplomanager/game
                CFG.game.updateCivilizationIdeology(this.getCivID(), CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.getCivID()).getCivTag()) + CFG.ideologiesManager.getIdeology(this.getValue()).getExtraTag());
                for (int i = 0; i < CFG.game.getCiv(this.getCivID()).getCivRegionsSize(); ++i) {
                    CFG.game.getCiv(this.getCivID()).getCivRegion(i).buildScaleOfText();
                }

                //original
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    if (CFG.game.getPlayer(i).getCivID() == this.getCivID()) {
                        CFG.game.getPlayer(i).loadPlayersFlag();
                        break;
                    }
                }
                CFG.setActiveCivInfo(CFG.getActiveCivInfo());

                //added refresh
                CFG.updateActiveCivInfo_InGame();
                CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                CFG.viewsManager.disableAllViews();
            }
            catch (final IndexOutOfBoundsException ex) {}
        }
    }
    
    @Override
    protected String getConditionText() {
        try {
            return CFG.langManager.get("ChangeIdeology") + ": " + CFG.game.getCiv(this.getCivID()).getCivName() + " -> " + CFG.ideologiesManager.getIdeology(this.getValue()).getName();
        }
        catch (final IndexOutOfBoundsException ex) {
            return CFG.langManager.get("ChangeIdeology");
        }
    }
    
    protected boolean canMakeAction() {
        return false;
    }
    
    @Override
    protected List<MenuElement_Hover_v2_Element2> getHoverText() {
        try {
            final List<MenuElement_Hover_v2_Element2> tElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            tData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCivID()));
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeIdeology") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(this.getValue()).getName(), CFG.ideologiesManager.getIdeology(this.getValue()).getColor()));
            tData.add(new MenuElement_Hover_v2_Element_Type_Ideology(this.getValue(), CFG.PADDING, 0));
            tElements.add(new MenuElement_Hover_v2_Element2(tData));
            tData.clear();
            return tElements;
        }
        catch (final IndexOutOfBoundsException ex) {}
        catch (final NullPointerException ex2) {}
        return new ArrayList<MenuElement_Hover_v2_Element2>();
    }
    
    @Override
    protected final void editViewID() {
        CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY);
    }
}
