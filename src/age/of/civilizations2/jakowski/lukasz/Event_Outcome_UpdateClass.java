package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Event_Outcome_UpdateClass extends Event_Outcome
{
    protected int iCivID = -1;
    protected int iClassIndex = -1;
    protected float change = -1.0F;

    Event_Outcome_UpdateClass() {
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

    protected void setClassIndex(final int nValue) {
        this.iClassIndex = nValue;
    }

    protected int getClassIndex() {
        return this.iClassIndex;
    }

    protected void setChangePerc(final float fValue) {
        this.change = fValue;
    }

    protected float getChangePerc() {
        return this.change;
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
        if (this.getCivID() >= 0 && this.getCivID() < CFG.game.getCivsSize() && this.change > 0.0F && this.iClassIndex >= 0) {
            try {
                CFG.game.getCiv(this.getCivID()).civGameData.leaderData.setClassViews(iClassIndex, CFG.game.getCiv(this.getCivID()).civGameData.leaderData.getClassViews(iClassIndex) + change);

                //update manage through civinfo refresh
                CFG.updateActiveCivInfo_InGame();

                CFG.viewsManager.disableAllViews();
            }
            catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        }
    }
    
    @Override
    protected String getConditionText() {
        try {
            return CFG.langManager.get("ChangeClass") + ": " + (this.iClassIndex == 0 ? CFG.langManager.get("UpperClass") : (this.iClassIndex == 1 ? CFG.langManager.get("MiddleClass") : CFG.langManager.get("LowerClass")) + " " + ((int)(10000.0f * this.change / 100.0F)));
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {
            return CFG.langManager.get("ChangeClass");
        }
    }
    
    protected boolean canMakeAction() {
        return true;
    }
    
    @Override
    protected List<MenuElement_Hover_v2_Element2> getHoverText() {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iCivID).getCivName() + "  ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));

            final float fBoost = ((int)(10000.0f * this.change)) / 100.0F;
            if (this.iClassIndex == 0) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("UpperClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LeaderViews") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            } else if (this.iClassIndex == 1) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MiddleClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LeaderViews") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            } else if (this.iClassIndex == 2) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LowerClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LeaderViews") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }

            return nElements;
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        return new ArrayList<MenuElement_Hover_v2_Element2>();
    }
    
    @Override
    protected final void editViewID() {
        //todo
        //CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGELEADER);
    }
}
