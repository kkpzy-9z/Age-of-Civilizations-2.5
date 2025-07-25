package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import java.io.*;

class Event_Outcome_CreateVassal extends Event_Outcome
{
    protected int iCivID;
    protected int iCivID2;
    protected List<Integer> lProvinces;
    protected int autonomyStatus = -1;
    
    Event_Outcome_CreateVassal() {
        super();
        this.iCivID = -1;
        this.iCivID2 = -1;
        this.autonomyStatus = -1;
        this.lProvinces = new ArrayList<Integer>();
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
    protected int getCivID2() {
        return this.iCivID2;
    }

    protected int getAutonomyStatus() {
        return this.autonomyStatus;
    }

    protected void setAutonomyStatus(final int nAutonomyStatus) {
        this.autonomyStatus = nAutonomyStatus;
    }
    
    @Override
    protected void setCivID2(final int nCivID) {
        this.iCivID2 = nCivID;
    }
    
    @Override
    protected List<Integer> getProvinces() {
        return this.lProvinces;
    }

    protected void setProvinces(List nProvinces) {
        this.lProvinces.clear();

        for (int i = 0; i < nProvinces.size(); ++i) {
            this.lProvinces.add((Integer)nProvinces.get(i));
        }
    }
    
    @Override
    protected boolean updateCivIDAfterRemove(final int nRemovedCivID) {
        boolean out = false;
        if (this.iCivID == nRemovedCivID) {
            this.iCivID = -1;
            out = true;
        }
        else if (nRemovedCivID < this.iCivID) {
            --this.iCivID;
        }
        if (this.iCivID2 == nRemovedCivID) {
            this.iCivID2 = -1;
            out = true;
        }
        else if (nRemovedCivID < this.iCivID2) {
            --this.iCivID2;
        }
        return out;
    }
    
    @Override
    protected void outcomeAction() {
        if (this.getCivID() >= 0 && this.getCivID() < CFG.game.getCivsSize() && this.getCivID2() >= 0 && this.getCivID2() < CFG.game.getCivsSize()) {
            CFG.game.setVassal_OfCiv(this.getCivID(), this.getCivID2(), this.getAutonomyStatus());

            for (int i = 0; i < this.lProvinces.size(); ++i) {
                try {
                    if (this.canMakeAction(i)) {
                        CFG.game.getProvince(this.getProvinces().get(i)).setCivID(this.getCivID2(), false);
                        CFG.game.getProvince(this.getProvinces().get(i)).setTrueOwnerOfProvince(this.getCivID2());
                    }
                }
                catch (final IndexOutOfBoundsException ex) {}
            }

            CFG.gameAction.updateCivsHappiness(this.getCivID());
            CFG.gameAction.updateCivsHappiness(this.getCivID2());
            if (CFG.game.getCiv(this.getCivID()).getCapitalProvinceID() < 0 || CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getCapitalProvinceID()).getCivID() != this.getCivID()) {
                CFG.game.moveCapitalToTheLargestCity(this.getCivID());
            }
            CFG.game.buildCivilizationRegions(this.getCivID());
            CFG.game.buildCivilizationRegions(this.getCivID2());
        }
    }
    
    protected boolean canMakeAction(final int i) {
        try {
            return !CFG.game.getProvince(this.getProvinces().get(i)).getSeaProvince() && CFG.game.getProvince(this.getProvinces().get(i)).getWasteland() < 0 && (CFG.game.getProvince(this.getProvinces().get(i)).getCivID() == this.getCivID() || CFG.game.getCiv(CFG.game.getProvince(this.getProvinces().get(i)).getCivID()).getPuppetOfCivID() == this.getCivID()) && this.getCivID() != this.getCivID2();
        }
        catch (final IndexOutOfBoundsException ex) {
            return false;
        }
    }
    
    @Override
    protected List<MenuElement_Hover_v2_Element2> getHoverText() {
        try {
            final List<MenuElement_Hover_v2_Element2> tElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CreateAVassal") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.getCivID2()).getCivName()));
            tData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCivID2(), CFG.PADDING, 0));
            tElements.add(new MenuElement_Hover_v2_Element2(tData));
            tData.clear();
            try {
                tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Autonomy") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.getAutonomy(this.getAutonomyStatus()).getName()));
                tData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCivID2(), CFG.PADDING, 0));
            } catch (NullPointerException | IndexOutOfBoundsException ex) {
            }
            tElements.add(new MenuElement_Hover_v2_Element2(tData));
            tData.clear();

            for (int i = 0; i < this.getProvinces().size(); ++i) {
                if (this.canMakeAction(i)) {
                    tData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCivID2()));
                    tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Controls") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    tData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.game.getProvince(this.getProvinces().get(i)).getName().isEmpty()) ? ((Serializable)this.getProvinces().get(i)) : CFG.game.getProvince(this.getProvinces().get(i)).getName())));
                    tElements.add(new MenuElement_Hover_v2_Element2(tData));
                    tData.clear();
                }
            }
            return tElements;
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        return new ArrayList<MenuElement_Hover_v2_Element2>();
    }
    
    @Override
    protected String getConditionText() {
        try {
            return CFG.langManager.get("CreateAVassal") + ": " + CFG.game.getCiv(this.getCivID2()).getCivName();
        }
        catch (final IndexOutOfBoundsException ex) {
            return CFG.langManager.get("CreateAVassal");
        }
    }
    
    @Override
    protected final void editViewID() {
        CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL);
    }
}
