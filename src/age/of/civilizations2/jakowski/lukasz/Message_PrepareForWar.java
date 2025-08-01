package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Message_PrepareForWar extends Message
{
    protected int iLeaderCivID;
    
    protected Message_PrepareForWar(final int fromCivID, final int iValue, final int iValue2, final int iLeaderCivID) {
        super(fromCivID, iValue);
        this.messageType = Message_Type.PREPARE_FOR_WAR;
        this.iNumOfTurnsLeft = 2;
        this.iValue2 = iValue2;
        this.iLeaderCivID = iLeaderCivID;
        //stop spamming acknowledgement
        this.requestsResponse = false;
        this.willPauseTheGame = true;
    }
    
    @Override
    protected void onAction(final int iMessageID) {
        CFG.game.getPlayer(CFG.PLAYER_TURNID).setMetCivilization(this.iFromCivID, true);
        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(iMessageID).onDecline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
        CFG.menuManager.rebuildInGame_Messages();

        //CFG.menuManager.rebuildInGame_Message_PrepareForWar(this.iFromCivID, iMessageID, this.iValue, this.iValue2 - Game_Calendar.TURN_ID);
    }
    
    @Override
    protected void onAccept(final int iCivID) {
        DiplomacyManager.acceptPrepareForWar(this.iLeaderCivID, iCivID, this.iFromCivID, this.iValue, this.iValue2 - Game_Calendar.TURN_ID);
    }
    
    @Override
    protected void onDecline(final int iCivID) {
        DiplomacyManager.declinePrepareForWar(this.iLeaderCivID, iCivID, this.iFromCivID, this.iValue, this.iValue2 - Game_Calendar.TURN_ID);
    }
    
    @Override
    protected int getImageID() {
        return Images.diplo_war_preparations;
    }
    
    @Override
    protected int getBGImageID() {
        return Images.messages;
    }
    
    @Override
    protected MenuElement_Hover_v2 getHover() {
        final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iFromCivID));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WarPreparations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PrepareForTheWarAgainst") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iValue, 0, CFG.PADDING));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iValue).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PreparationTime") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnsX", this.iValue2 - Game_Calendar.TURN_ID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        nData.add(new MenuElement_Hover_v2_Element_Type_Space());
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_message));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MessageWillExpireIn") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnsX", this.iNumOfTurnsLeft) + " ", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + this.iNumOfTurnsLeft) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        if (CFG.game.getCiv(this.iFromCivID).civGameData.leaderData != null) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iFromCivID).civGameData.leaderData.getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iFromCivID, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        return new MenuElement_Hover_v2(nElements);
    }
}
