package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_CivInfo_Stats_Actions extends SliderMenu {
   protected Menu_InGame_CivInfo_Stats_Actions() {
      List menuElements = new ArrayList();
      int nPosY = 0;
      int tempElemH = CFG.isAndroid() ? Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.6F)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int i;
      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_war, CFG.langManager.get("DeclareWar"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (Game_Calendar.TURN_ID <= 4) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (!CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouMustBorderWithCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Government") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getColor()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DeclareWarOn") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), 0, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_DeclareWar(CFG.getActiveCivInfo());
            }

            protected boolean getClickable() {
               return super.getClickable() && Game_Calendar.TURN_ID > 4 && CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) == 0;
            }

            protected int getSFX() {
               return this.getClickable() ? SoundsManager.SOUND_WAR : super.getSFX();
            }
         });
         nPosY += tempElemH;
         boolean canPrepareForWar = false;
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
            for(i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
               if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.getActiveCivInfo() != CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) && !CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i))) {
                  canPrepareForWar = true;
                  break;
               }
            }
         }

         if (!canPrepareForWar) {
            for(i = 1; i < CFG.game.getCivsSize(); ++i) {
               if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != i && CFG.game.getCiv(i).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && !CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), i)) {
                  canPrepareForWar = true;
                  break;
               }
            }
         }

         if (canPrepareForWar) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_war_preparations, CFG.langManager.get("PrepareForWar"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
               protected void buildElementHover() {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  if (Game_Calendar.TURN_ID <= 4) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) > 0) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()))));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else if (!CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouMustBorderWithCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Government") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getColor()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID(), CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PrepareForTheWarAgainst") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), 0, CFG.PADDING));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war_preparations, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }

                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }

               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_WarPreparations(CFG.getActiveCivInfo());
               }

               protected boolean getClickable() {
                  return super.getClickable() && Game_Calendar.TURN_ID > 4 && CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) == 0;
               }

               protected int getSFX() {
                  return this.getClickable() ? SoundsManager.SOUND_WAR : super.getSFX();
               }
            });
            nPosY += tempElemH;
         }

         menuElements.add(new Button_Diplomacy_Action(Images.diplo_rivals, CFG.langManager.get("SendUltimatum"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            protected void buildElementHover() {
               ArrayList nElements;
               ArrayList nData;
               if (Game_Calendar.TURN_ID <= 4) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) > 0) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else if (!CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouMustBorderWithCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Government") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getColor()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else if (CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() != CFG.getActiveCivInfo() && CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouCanSendDemandsOnlyToALordOrYourVassal"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else if (CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > -10.0F) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OurRelationsNeedsToBeBelow") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("-10", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_relations, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else {
                  this.menuElementHover = null;
               }

            }

            protected void actionElement(int iID) {
               CFG.ultimatum = new Ultimatum_GameData();
               CFG.menuManager.rebuildInGame_SendUltimatum(CFG.getActiveCivInfo());
            }

            protected boolean getClickable() {
               return super.getClickable() && Game_Calendar.TURN_ID > 4 && CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) == 0 && (CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() == CFG.getActiveCivInfo() || CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) && CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) <= -10.0F;
            }
         });
         nPosY += tempElemH;
      }

      if (CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_rivals, CFG.langManager.get("CallToArms"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_CallToArms(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_truce, CFG.langManager.get("PeaceNegotiations"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               int nWarID = CFG.game.getWarID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo());
               if (nWarID >= 0) {
                  if (CFG.PLAYER_PEACE || CFG.SPECTATOR_MODE) {
                     CFG.sandbox_task = Menu.eINGAME_PEACE_TREATY;
                  }
                  CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                  CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                  CFG.viewsManager.disableAllViews();
                  Menu_PeaceTreaty.WAR_ID = nWarID;
                  CFG.peaceTreatyData = new PeaceTreaty_Data(Menu_PeaceTreaty.WAR_ID, CFG.game.getWar(nWarID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                  CFG.game.resetChooseProvinceData_Immediately();
                  CFG.game.resetRegroupArmyData();
                  CFG.menuManager.setViewID(Menu.eINGAME_PEACE_TREATY);
               }

            }
         });
         nPosY += tempElemH;
      }

      menuElements.add(new Button_Diplomacy_Action(Images.diplo_relations_inc, CFG.langManager.get("ImproveRelations"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, !CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmassyClosed(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
         protected void actionElement(int iID) {
            CFG.menuManager.rebuildInGame_ImproveRelations(CFG.getActiveCivInfo());
         }

         protected void buildElementHover() {
            ArrayList nElements;
            ArrayList nData;
            if (CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
               nElements = new ArrayList();
               nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeAreAtWar"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), CFG.PADDING, 0));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else if (CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmassyClosed(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
               nElements = new ArrayList();
               nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomaticRelationsAreSuspended"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), CFG.PADDING, 0));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_relations_dec, CFG.PADDING, 0));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName()));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.getActiveCivInfo()).getCivilization_Diplomacy_GameData().isEmbassyClosed_Turns(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

         }

         protected boolean getClickable() {
            return super.getClickable() && !CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
         }
      });
      nPosY += tempElemH;
      menuElements.add(new Button_Diplomacy_Action(Images.diplo_relations_dec, CFG.langManager.get("SendAnInsult"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
         protected void actionElement(int iID) {
            CFG.menuManager.rebuildInGame_SendInsult(CFG.getActiveCivInfo());
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DecreaseRelations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_relations, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      nPosY += tempElemH;
      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.getActiveCivInfo()).getAllianceID() != CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_alliance, CFG.langManager.get("OfferAlliance"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_OfferAlliance(CFG.getActiveCivInfo());
               }
            });
            nPosY += tempElemH;
         }

         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.getActiveCivInfo()).getAllianceID() == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_alliance, CFG.langManager.get("KickFromAlliance"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_KickFromAlliance(CFG.getActiveCivInfo());
               }
            });
            nPosY += tempElemH;
         }
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_trade, CFG.langManager.get("TradeRequest"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.hasSentTrade) {
            protected void actionElement(int iID) {
               CFG.tradeRequest = new TradeRequest_GameData();
               CFG.tradeRequest.iCivLEFT = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
               CFG.tradeRequest.iCivRIGHT = CFG.getActiveCivInfo();
               CFG.menuManager.rebuildInGame_TradeRequest(CFG.getActiveCivInfo());
            }

            //if traded already build tooltip
            protected void buildElementHover() {
               ArrayList nElements;
               ArrayList nData;
               if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.hasSentTrade) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouHaveSentTrade"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_trade, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }
            }

            //only clickable if not traded before
            protected boolean getClickable() {
               return super.getClickable() && !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.hasSentTrade;
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_non_aggression, CFG.langManager.get("NonAggressionPact"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_NonAggressionPact(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_defensive_pact, CFG.langManager.get("FormDefensivePact"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_DefensivePact(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_guarantee_gives, CFG.langManager.get("ProclaimIndependence"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_ProclaimIndependence(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_union, CFG.langManager.get("FormUnion"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_FormUnion(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_vassal, CFG.langManager.get("OfferVassalization"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_OfferVassalization(CFG.getActiveCivInfo());
            }

            protected boolean getClickable() {
               return super.getClickable() && CFG.game.getCiv(CFG.getActiveCivInfo()).getCivID() == CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID();
            }

            protected void buildElementHover() {
               if (CFG.game.getCiv(CFG.getActiveCivInfo()).getCivID() == CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()) {
                  this.menuElementHover = null;
               } else {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("VassalOfAnotherCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }

            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID()) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_lord, CFG.langManager.get("LiberateAVassal"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_LiberateAVassal(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      menuElements.add(new Button_Diplomacy_Action(Images.diplo_revolution, CFG.langManager.get("SupportRebels"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
         protected void actionElement(int iID) {
            CFG.menuManager.rebuildInGame_SupportRebels(CFG.getActiveCivInfo(), -1);
         }
      });
      nPosY += tempElemH;
      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_access_has, CFG.langManager.get("AskForMilitaryAccess"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_MilitartAccess_Ask(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_access_gives, CFG.langManager.get("OfferMilitaryAccess"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_MilitartAccess_Give(CFG.getActiveCivInfo());
            }
         });
         nPosY += tempElemH;
      }

      if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
         menuElements.add(new Button_Diplomacy_Action(Images.diplo_gift, CFG.langManager.get("SendGift"), 0, 0, nPosY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_SendGift(CFG.getActiveCivInfo());
            }
         });
         int var10000 = nPosY + tempElemH;
      }

      int tempMenuH = tempElemH * 8;
      this.initMenu((SliderMenuTitle)null, 0 + AoCGame.LEFT, ImageManager.getImage(Images.new_game_top).getHeight() + CFG.PADDING * 4 + (int)((float)CFG.TEXT_HEIGHT * 0.6F) + ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4, CFG.CIV_INFO_MENU_WIDTH, tempMenuH, menuElements, false, false);
      this.updateLanguage();

      for(i = 0; i < this.getMenuElementsSize(); ++i) {
         this.getMenuElement(i).setCurrent(i % 2);
      }

   }

   protected void updateLanguage() {
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_CivInfo.lTime + 175L >= System.currentTimeMillis()) {
         if (Menu_InGame_CivInfo.hideAnimation) {
            iTranslateX -= (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0F));
         } else {
            iTranslateX += -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (Menu_InGame_CivInfo.hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + 2, true, false);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);
      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight() + 1, this.getWidth() - 2, 1);
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + 1 + this.getHeight(), this.getWidth() - 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + 2 + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (AoCGame.LEFT != 0) {
         oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight() + 2, true, false);
         oSB.setColor(Color.WHITE);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_CivInfo();
   }

   protected void actionElement(int iID) {
      if (!CFG.SPECTATOR_MODE && CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
         this.getMenuElement(iID).actionElement(iID);
         if (RTS.isEnabled() && !RTS.PAUSE) {
            RTS.updateTimePast_AfterAction(0.3F);
         }

      }
   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
      }

   }

   protected void actionClose() {
      super.setVisible(false);
   }

   protected void setPosY(int iPosY) {
      super.setPosY(iPosY);
      this.setHeight(this.iMaxSliderPositionY);
      if (this.getPosY() + this.getHeight() > CFG.GAME_HEIGHT) {
         this.setHeight(Math.max(CFG.GAME_HEIGHT - this.getPosY(), CFG.BUTTON_HEIGHT / 2));
      }

      int tempElemH = CFG.isAndroid() ? Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.6F)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      this.setHeight(Math.min(this.getHeight(), tempElemH * (CFG.isDesktop() ? 8 : 6)));
      this.updateMenuElements_IsInView();
   }
}
