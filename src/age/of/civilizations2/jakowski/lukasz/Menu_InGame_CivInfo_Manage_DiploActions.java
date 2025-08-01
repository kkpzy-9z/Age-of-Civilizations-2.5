package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_CivInfo_Manage_DiploActions extends SliderMenu {
   protected static boolean inLeaderActions = true;

   protected Menu_InGame_CivInfo_Manage_DiploActions() {
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      int nPosY = 0;

      menuElements.add(new Button_Diplo_Actions((String)null, -1, 0, 1, CFG.CIV_INFO_MENU_WIDTH / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3 - 2, true) {
         protected void buildElementHover() {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LeaderActions"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Diplo_Decisions((String)null, -1, CFG.CIV_INFO_MENU_WIDTH / 2, 1, CFG.CIV_INFO_MENU_WIDTH / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3 - 2, true) {
         protected void buildElementHover() {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CivilizationActions"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      nPosY += CFG.TEXT_HEIGHT + CFG.PADDING * 3 - 2;

      int tempElemH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      int tempElemW = CFG.CIV_INFO_MENU_WIDTH - 2 - CFG.PADDING * 4;
      nPosY += CFG.PADDING;

      int iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
      if (inLeaderActions) {
         try {
            CFG.game.getCiv(iCivID).civGameData.leaderData.getDecisionsCount();
            CFG.game.getCiv(iCivID).civGameData.leaderData.getClassViews(0);
         } catch (NullPointerException e) {
            DynamicEventManager_Leader.safeReplaceLeader(iCivID);
         }

         for (int decisionIndex = 0; decisionIndex < CFG.game.getCiv(iCivID).civGameData.leaderData.getDecisionsCount(); decisionIndex++) {
            //skip decisions not yet available at current date
            if (CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(decisionIndex).getYear() > Game_Calendar.currentYear
                && CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(decisionIndex).getMonth() > Game_Calendar.currentMonth
                && CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(decisionIndex).getDay() > Game_Calendar.currentDay) continue;

            final int fIndex = decisionIndex;
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_message, CFG.langManager.get(CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(decisionIndex).getName()), 0, 1 + CFG.PADDING * 2, nPosY + CFG.PADDING, tempElemW, tempElemH, true) {
               protected int getCurrent() {
                  return fIndex;
               }

               protected boolean getClickable() {
                  return super.getClickable() && CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.getCurrent()).canEnactDecision(iCivID);
               }

               protected void buildElementHover() {
                  this.menuElementHover = CFG.game.getHover_Decision(CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.getCurrent()));
               }

               protected int getSFX() {
                  return SoundsManager.SOUND_COINS;
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  ImageManager.getImage(Images.diplo_message).draw(oSB, this.getPosX() + (Button_Diplomacy.iDiploWidth - ImageManager.getImage(Images.diplo_message).getWidth()) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_message).getHeight() / 2 + iTranslateY);
                  CFG.fontMain.getData().setScale(0.6F);
                  CFG.drawText(oSB, this.getText(), this.getPosX() + Button_Diplomacy.iDiploWidth + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.6F / 2.0F) + iTranslateY, this.getColor(isActive));
                  CFG.drawText(oSB, String.valueOf(CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.getCurrent()).getTurnLength() - CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.getCurrent()).getTurnsProgress()), ((this.getPosX() + iTranslateX + this.getWidth()) - ((Button_Diplomacy.iDiploWidth - ImageManager.getImage(Images.diplo_message).getWidth()) / 2)) - CFG.PADDING, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.6F / 2.0F) + iTranslateY, CFG.game.getCiv(iCivID).civGameData.leaderData.getDecision(this.getCurrent()).getInProgress() ? this.getColor(true) : this.getColor(false));
                  CFG.fontMain.getData().setScale(1.0F);
               }
            });

            menuElements.get(menuElements.size() - 1).setCurrent(fIndex);
            nPosY += tempElemH;
         }
      } else {
         try {
            CFG.game.getCiv(iCivID).civGameData.getDecisionsCount();
         } catch (NullPointerException e) {
            CFG.gameAction.updateCivDecisions(iCivID);
         }

         for (int decisionIndex = 0; decisionIndex < CFG.game.getCiv(iCivID).civGameData.getDecisionsCount(); decisionIndex++) {
            //skip decisions not yet available at current date
            if (CFG.game.getCiv(iCivID).civGameData.getDecision(decisionIndex).getYear() > Game_Calendar.currentYear
                    && CFG.game.getCiv(iCivID).civGameData.getDecision(decisionIndex).getMonth() > Game_Calendar.currentMonth
                    && CFG.game.getCiv(iCivID).civGameData.getDecision(decisionIndex).getDay() > Game_Calendar.currentDay) continue;

            final int fIndex = decisionIndex;
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_message, CFG.langManager.get(CFG.game.getCiv(iCivID).civGameData.getDecision(decisionIndex).getName()), 0, 1 + CFG.PADDING * 2, nPosY + CFG.PADDING, tempElemW, tempElemH, true) {
               protected int getCurrent() {
                  return fIndex;
               }

               protected boolean getClickable() {
                  return super.getClickable() && CFG.game.getCiv(iCivID).civGameData.getDecision(this.getCurrent()).canEnactDecision(iCivID);
               }

               protected void buildElementHover() {
                  this.menuElementHover = CFG.game.getHover_Decision(CFG.game.getCiv(iCivID).civGameData.getDecision(this.getCurrent()));
               }

               protected int getSFX() {
                  return SoundsManager.SOUND_COINS;
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  ImageManager.getImage(Images.diplo_message).draw(oSB, this.getPosX() + (Button_Diplomacy.iDiploWidth - ImageManager.getImage(Images.diplo_message).getWidth()) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_message).getHeight() / 2 + iTranslateY);
                  CFG.fontMain.getData().setScale(0.6F);
                  CFG.drawText(oSB, this.getText(), this.getPosX() + Button_Diplomacy.iDiploWidth + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.6F / 2.0F) + iTranslateY, this.getColor(isActive));
                  CFG.drawText(oSB, String.valueOf(CFG.game.getCiv(iCivID).civGameData.getDecision(this.getCurrent()).getTurnLength() - CFG.game.getCiv(iCivID).civGameData.getDecision(this.getCurrent()).getTurnsProgress()), ((this.getPosX() + iTranslateX + this.getWidth()) - ((Button_Diplomacy.iDiploWidth - ImageManager.getImage(Images.diplo_message).getWidth()) / 2)) - CFG.PADDING, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.6F / 2.0F) + iTranslateY, CFG.game.getCiv(iCivID).civGameData.getDecision(this.getCurrent()).getInProgress() ? this.getColor(true) : this.getColor(false));
                  CFG.fontMain.getData().setScale(1.0F);
               }
            });

            menuElements.get(menuElements.size() - 1).setCurrent(fIndex);
            nPosY += tempElemH;
         }
      }
      nPosY += CFG.PADDING * 2;

      int tempPosY = (ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3) + (ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4);
      tempPosY += (CFG.TEXT_HEIGHT + CFG.PADDING) + CFG.PADDING * 2 + (((CFG.TEXT_HEIGHT + CFG.PADDING) + (CFG.graphCircleDraw.getWidth() + CFG.PADDING * 2)) * 3);
      this.initMenu((SliderMenuTitle)null, CFG.GAME_WIDTH - CFG.CIV_INFO_MENU_WIDTH + ImageManager.getImage(Images.new_game_top_edge_line_horizontal).getWidth(), tempPosY, CFG.CIV_INFO_MENU_WIDTH, Math.min(nPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 3 - 2) + CFG.PADDING + ((CFG.TEXT_HEIGHT + CFG.PADDING * 4) * 7)), menuElements, false, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Leader"));
      this.getMenuElement(1).setText(CFG.langManager.get("Civilization"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_Manage_Info.lTime + 175L >= System.currentTimeMillis()) {
         if (Menu_InGame_Manage_Info.hideAnimation) {
            iTranslateX += (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_Manage_Info.lTime) / 175.0F));
         } else {
            iTranslateX -= -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_Manage_Info.lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (Menu_InGame_Manage_Info.hideAnimation) {
         super.setVisible(false);
         return;
      }


      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX - ImageManager.getImage(Images.new_game_top_edge_line_horizontal).getWidth(), this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + 2, false, false);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);
      CFG.drawRect_InfoBox_Left(oSB, this.getPosX() + iTranslateX + CFG.PADDING, this.getPosY() + (CFG.TEXT_HEIGHT + CFG.PADDING * 3 - 2) + CFG.PADDING + iTranslateY, this.getWidth() - CFG.PADDING * 2, ((this.getMenuElementsSize() - 2) * (CFG.TEXT_HEIGHT + CFG.PADDING * 4)) + CFG.PADDING * 2);

      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight() + 1, this.getWidth() - 2, 1);
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + 1 + this.getHeight(), this.getWidth() - 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + 2 + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(Color.WHITE);

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_ManageCiv_Info();
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
            inLeaderActions = true;
            CFG.updateActiveCivManagement_InGame();
            break;
         case 1:
            inLeaderActions = false;
            CFG.updateActiveCivManagement_InGame();
            break;
         default:
            if (inLeaderActions) {
               if (DiplomacyManager.acceptDecision(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getMenuElement(iID).getCurrent())) {
                  CFG.toast.setInView(CFG.langManager.get(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getDecision(this.getMenuElement(iID).getCurrent()).getName()));
                  CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                  CFG.updateActiveCivManagement_InGame();
                  break;
               } else {
                  CFG.toast.setInView(CFG.langManager.get("DecisionNotAvailable"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
               }
            } else {
               if (DiplomacyManager.acceptCivDecision(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getMenuElement(iID).getCurrent())) {
                  CFG.toast.setInView(CFG.langManager.get(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.getDecision(this.getMenuElement(iID).getCurrent()).getName()));
                  CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                  CFG.updateActiveCivManagement_InGame();
                  break;
               } else {
                  CFG.toast.setInView(CFG.langManager.get("DecisionNotAvailable"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
               }
            }

      }

   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
      }

   }

}
