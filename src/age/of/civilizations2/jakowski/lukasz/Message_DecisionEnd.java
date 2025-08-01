//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.List;

class Message_DecisionEnd extends Message {
    private boolean leaderDec;
    protected Message_DecisionEnd(int fromCivID, int iValue, boolean leaderDec) {
        super(fromCivID, iValue);
        //just copy assim bc same function
        this.messageType = Message_Type.ASSMILIATION_IS_OVER;
        this.requestsResponse = true;
        this.iNumOfTurnsLeft = 1;
        this.leaderDec = leaderDec;
    }

    protected void onAction(int iMessageID) {
        if (this.requestsResponse) {
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(iMessageID).onDecline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            this.requestsResponse = false;
        } else {
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(iMessageID).onDecline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
            CFG.menuManager.rebuildInGame_Messages();
        }
    }

    protected void onAccept(int iCivID) {
        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == iCivID) {
            List<String> lMess = new ArrayList();
            List<Color> lColors = new ArrayList();
            if (leaderDec) {
                lMess.add((CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.iValue).getName())));
            } else {
                lMess.add((CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(iCivID).civGameData.getDecision(this.iValue).getName())));
            }
            lColors.add(CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            CFG.toast.setInView(lMess, lColors);
            CFG.toast.setTimeInView(6000);
        }

    }

    protected void onDecline(int iCivID) {
        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == iCivID) {
            List<String> lMess = new ArrayList();
            List<Color> lColors = new ArrayList();
            if (leaderDec) {
                lMess.add((CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.iValue).getName())));
            } else {
                lMess.add((CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(iCivID).civGameData.getDecision(this.iValue).getName())));
            }
            lColors.add(CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            CFG.toast.setInView(lMess, lColors);
            CFG.toast.setTimeInView(6000);
        }

    }

    protected int getImageID() {
        return Images.diplo_message;
    }

    protected int getBGImageID() {
        return Images.messages_g;
    }

    protected MenuElement_Hover_v2 getHover() {
        List<MenuElement_Hover_v2_Element2> nElements = new ArrayList();
        List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Decision") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
        if (leaderDec) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(this.iFromCivID).civGameData.leaderData.getDecision(this.iValue).getName())));
        } else {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheDecisionHasEnded", CFG.game.getCiv(this.iFromCivID).civGameData.getDecision(this.iValue).getName())));
        }
        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iFromCivID, CFG.PADDING, 0));
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
