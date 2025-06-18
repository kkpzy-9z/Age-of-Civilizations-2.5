package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Event_Outcome_ChangeLeader extends Event_Outcome
{
    protected int iCivID = -1;
    protected LeaderOfCiv_GameData iValue = new LeaderOfCiv_GameData();

    Event_Outcome_ChangeLeader() {
        super();
    }
    
    @Override
    protected int getCivID() {
        return this.iCivID;
    }
    
    @Override
    protected void setCivID(final int nCivID) {
        this.iCivID = nCivID;
    }

    protected void setLeader(final LeaderOfCiv_GameData nValue) {
        this.iValue = nValue;
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
    protected void outcomeAction() {
        if (this.getCivID() >= 0 && this.getCivID() < CFG.game.getCivsSize()) {
            try {
                CFG.game.getCiv(this.getCivID()).civGameData.setLeader(this.iValue);

                //added refresh
                if (CFG.menuManager.getVisible_InGame_CivInfo() && CFG.getActiveCivInfo() == this.getCivID()) {
                    CFG.setActiveCivInfo(this.getCivID());
                }
                CFG.updateActiveCivInfo_InGame();

                CFG.viewsManager.disableAllViews();
            }
            catch (final IndexOutOfBoundsException ex) {}
        }
    }
    
    @Override
    protected String getConditionText() {
        try {
            return CFG.langManager.get("ChangeLeader") + ": " + CFG.game.getCiv(this.getCivID()).civGameData.leaderData.getName() + " -> " + this.iValue.getName();
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {
            return CFG.langManager.get("ChangeLeader");
        }
    }
    
    protected boolean canMakeAction() {
        return true;
    }
    
    @Override
    protected List<MenuElement_Hover_v2_Element2> getHoverText() {
        try {
            final List<MenuElement_Hover_v2_Element2> tElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            tData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCivID()));
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeLeader") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(this.iValue.getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            tElements.add(new MenuElement_Hover_v2_Element2(tData));
            tData.clear();
            return tElements;
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        return new ArrayList<MenuElement_Hover_v2_Element2>();
    }
    
    @Override
    protected final void editViewID() {
        //CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGELEADER);
    }
}
