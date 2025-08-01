package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Message_CallToArms extends Message
{
   protected Message_CallToArms(final int fromCivID, final int iValue) {
      super(fromCivID, iValue);
      this.messageType = Message_Type.WAR_DECLARED_ON_ALLY;
      this.iNumOfTurnsLeft = 3;
      //stop spamming call to arms
      this.requestsResponse = false;
   }

   @Override
   protected void onAction(final int iMessageID) {
      CFG.game.getPlayer(CFG.PLAYER_TURNID).setMetCivilization(this.iFromCivID, true);
      CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(iMessageID).onDecline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
      CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.removeMessage(iMessageID);
      CFG.menuManager.rebuildInGame_Messages();

      //CFG.menuManager.rebuildInGame_Message_CallToArms(this.iFromCivID, iMessageID, this.iValue);
   }

   @Override
   protected void onAccept(final int iCivID) {
      DiplomacyManager.acceptCallToArms(iCivID, this.iFromCivID, this.iValue);
   }

   @Override
   protected void onDecline(final int iCivID) {
      DiplomacyManager.declineCallToArms(iCivID, this.iFromCivID, this.iValue);
   }

   @Override
   protected int getImageID() {
      return Images.diplo_rivals;
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
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CallToArms"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("JoinAWarAgainst") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.iValue, 0, CFG.PADDING));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.iValue).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
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
