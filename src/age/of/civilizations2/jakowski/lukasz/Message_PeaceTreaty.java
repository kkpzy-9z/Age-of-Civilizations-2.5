package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Message_PeaceTreaty extends Message
{
    protected Message_PeaceTreaty(final int fromCivID, final String peaceTreatyTag) {
        super(fromCivID, 0);
        this.TAG = peaceTreatyTag;
        this.messageType = Message_Type.PEACE_TREATY_LIST_OF_DEMANDS;
        this.requestsResponse = true;
        this.willPauseTheGame = true;
        this.iNumOfTurnsLeft = 1;
    }
    
    @Override
    protected void onAction(final int iMessageID) {
        final int peaceID = CFG.game.getPeaceTreaty_GameDataID(this.TAG);
        if (peaceID >= 0) {
            boolean warFound = false;
            for (int i = 0; i < CFG.game.getWarsSize(); ++i) {
                if (CFG.game.getWar(i).WAR_TAG.equals(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.WAR_TAG)) {
                    CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.iWarID = i;
                    warFound = true;
                    break;
                }
            }
            if (warFound) {
                CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.peaceTreatyData = new PeaceTreaty_Data(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData);
                CFG.game.sRespondToPeaceTreatyID = this.TAG;
                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
                CFG.menuManager.rebuildInGame_Messages();
                CFG.menuManager.setViewID(Menu.eINGAME_PEACE_TREATY_RESPONSE);
                CFG.toast.setInView(CFG.langManager.get("PeaceOffer"));
                CFG.toast.setTimeInView(2000);
            }
            else {
                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
                CFG.menuManager.rebuildInGame_Messages();
                CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                CFG.toast.setTimeInView(1500);
            }
        }
        else {
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
            CFG.menuManager.rebuildInGame_Messages();
            CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
            CFG.toast.setTimeInView(1500);
        }
    }
    
    @Override
    protected void onAccept(final int iCivID) {
        try {
            int iSenderID = this.iFromCivID;

            ArrayList<Integer> topPrestige = null;
            if (CFG.PLAYER_PEACE && (CFG.getMetCiv(iCivID) || CFG.getMetCiv(iSenderID))) {
                //only calculate topPrestige if the first two conditions are met
                topPrestige = AI_Style.getTopPrestige();
            }
            if (CFG.PLAYER_PEACE
                    && (CFG.getMetCiv(iCivID) || CFG.getMetCiv(iSenderID))
                    && (topPrestige != null && (topPrestige.indexOf(iCivID) < 12 || topPrestige.indexOf(iSenderID) < 12))
                    && (!CFG.menuManager.getInGame_PeaceTreaty() && !CFG.menuManager.getInGame_PeaceTreaty_Response() && !(CFG.sandbox_task == Menu.eINGAME_PEACE_TREATY))) {
                topPrestige.clear();
                CFG.sandbox_task = Menu.eINGAME_PEACE_TREATY;

                CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                Menu_PeaceTreaty.WAR_ID = Menu_InGame_WarDetails.WAR_ID;
                CFG.peaceTreatyData = new PeaceTreaty_Data(Menu_PeaceTreaty.WAR_ID, CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(iCivID));
                CFG.game.resetChooseProvinceData_Immediately();
                CFG.game.resetRegroupArmyData();
                CFG.menuManager.setViewID(Menu.eINGAME_PEACE_TREATY);
            } else {
                final int peaceID = CFG.game.getPeaceTreaty_GameDataID(this.TAG);
                DiplomacyManager.acceptPeaceTreaty(iCivID, this.TAG);
            }

        }
        catch (final IndexOutOfBoundsException ex) {}
    }
    
    @Override
    protected void onDecline(final int iCivID) {
        try {
            final int peaceID = CFG.game.getPeaceTreaty_GameDataID(this.TAG);
            DiplomacyManager.declinePeaceTreaty(iCivID, this.TAG);
        }
        catch (final IndexOutOfBoundsException ex) {}
    }
    
    @Override
    protected int getImageID() {
        return Images.diplo_truce;
    }
    
    @Override
    protected MenuElement_Hover_v2 getHover() {
        try {
            final int peaceID = CFG.game.getPeaceTreaty_GameDataID(this.TAG);
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PeaceNegotiations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.size(); ++i) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID, (i == 0) ? CFG.PADDING : 0, 0));
            }
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, CFG.PADDING));
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.size(); ++i) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID, 0, 0));
            }
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.size() > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + CFG.langManager.get("Provinces") + ": " + CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.size()));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.size() && j < 5; ++j) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getTrueOwnerOfProvince(), 0, 0));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, 0, CFG.PADDING));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getName(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.game.getProvinceValue(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                }
            }
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.size() > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + CFG.langManager.get("Provinces") + ": " + CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.size()));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.size() && j < 5; ++j) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getTrueOwnerOfProvince(), 0, 0));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, 0, CFG.PADDING));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getName(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.game.getProvinceValue(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.victoryPoints, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                }
            }
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_message));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MessageWillExpireIn") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnsX", this.iNumOfTurnsLeft) + " ", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + this.iNumOfTurnsLeft) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {}
        catch (final NullPointerException ex2) {}
        final List<MenuElement_Hover_v2_Element2> nElements2 = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData2 = new ArrayList<MenuElement_Hover_v2_Element_Type>();
        nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PeaceNegotiations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
        nData2.clear();
        nData2.add(new MenuElement_Hover_v2_Element_Type_Space());
        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
        nData2.clear();
        nData2.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_message));
        nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MessageWillExpireIn") + ": "));
        nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnsX", this.iNumOfTurnsLeft) + " ", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
        nData2.add(new MenuElement_Hover_v2_Element_Type_Text("[" + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + this.iNumOfTurnsLeft) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
        nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
        nData2.clear();
        if (CFG.game.getCiv(this.iFromCivID).civGameData.leaderData != null) {
            nData2.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iFromCivID).civGameData.leaderData.getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData2.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iFromCivID, CFG.PADDING, 0));
            nElements2.add(new MenuElement_Hover_v2_Element2(nData2));
            nData2.clear();
        }
        return new MenuElement_Hover_v2(nElements2);
    }
}
