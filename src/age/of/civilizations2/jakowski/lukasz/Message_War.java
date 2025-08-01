package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Message_War extends Message
{
    protected Message_War(final int warDeclareByCivID, final int onCivID) {
        super(warDeclareByCivID, onCivID);
        this.messageType = Message_Type.WAR;
        this.willPauseTheGame = true;
        //stop acknowledge spam
        this.requestsResponse = false;
        this.iNumOfTurnsLeft = 3;
    }
    
    @Override
    protected void onAction(final int iMessageID) {
        CFG.game.getPlayer(CFG.PLAYER_TURNID).setMetCivilization(this.iFromCivID, true);
        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(iMessageID).onDecline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
        CFG.menuManager.rebuildInGame_Messages();
    }
    
    @Override
    protected void onAccept(final int iCivID) {
        CFG.menuManager.rebuildMenu_InGame_War(this.iFromCivID, this.iValue);
    }
    
    @Override
    protected void onDecline(final int iCivID) {
        CFG.menuManager.rebuildMenu_InGame_War(this.iFromCivID, this.iValue);
    }
    
    @Override
    protected int getImageID() {
        return Images.diplo_war;
    }
    
    @Override
    protected int getBGImageID() {
        return Images.messages_r;
    }
    
    @Override
    protected MenuElement_Hover_v2 getHover() {
        final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iValue));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeAreAtWarWith") + ": ", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iFromCivID, 0, CFG.PADDING));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iFromCivID).getCivName()));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
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
