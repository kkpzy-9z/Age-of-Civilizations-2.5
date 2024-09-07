package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_DeclareWar extends SliderMenu {
   private int iOnCivID = -1;

   protected Menu_InGame_DeclareWar() {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tY = CFG.PADDING;
      menuElements.add(new Button_Flag_JustFrame(CFG.PADDING, tY, true));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
      this.initMenu(new SliderMenuTitle(CFG.langManager.get("DeclareWar"), CFG.BUTTON_HEIGHT * 3 / 5, true, true), CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, false, true);
      this.updateLanguage();
   }

   protected Menu_InGame_DeclareWar(int onCivID) {
      List menuElements = new ArrayList();
      this.iOnCivID = onCivID;
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tY = 0;
      menuElements.add(new Button_Diplomacy_War(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), onCivID, 2, tY, CFG.BUTTON_WIDTH * 2) {
         protected int getWidth() {
            return Menu_InGame_DeclareWar.this.getElementW() * 2;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      List lAlliesAggressor = new ArrayList();
      List lAlliesDefender = new ArrayList();

      int i;
      for(i = 1; i < CFG.game.getCivsSize(); ++i) {
         if (i != this.iOnCivID && i != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(i).getNumOfProvinces() > 0) {
            if (CFG.game.getCiv(i).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() != i) {
               if (CFG.game.getCiv(i).getPuppetOfCivID() == this.iOnCivID) {
                  lAlliesDefender.add(i);
               } else if (i == CFG.game.getCiv(this.iOnCivID).getPuppetOfCivID()) {
                  lAlliesDefender.add(i);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() == CFG.game.getCiv(i).getAllianceID()) {
                  lAlliesAggressor.add(i);
               } else if (CFG.game.getCiv(this.iOnCivID).getAllianceID() > 0 && CFG.game.getCiv(this.iOnCivID).getAllianceID() == CFG.game.getCiv(i).getAllianceID()) {
                  lAlliesDefender.add(i);
               } else if (CFG.game.getDefensivePact(this.iOnCivID, i) > 0) {
                  lAlliesDefender.add(i);
               } else if (CFG.game.getGuarantee(i, this.iOnCivID) > 0) {
                  lAlliesDefender.add(i);
               }
            } else {
               lAlliesAggressor.add(i);
            }
         }
      }

      if (lAlliesDefender.size() > 0 || lAlliesAggressor.size() > 0) {
         tY += CFG.PADDING;
         menuElements.add(new Text_AlliesNotInWar(CFG.langManager.get("Allies"), -1, CFG.PADDING, tY, tempWidth - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3) {
            protected int getPosX() {
               return 0;
            }

            protected int getWidth() {
               return Menu_InGame_DeclareWar.this.getW() + 4;
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
         int tYClone = tY;
         i = 0;

         for(i = 0; i < lAlliesAggressor.size(); ++i) {
            menuElements.add(new Button_Statistics_CallAlly((Integer)lAlliesAggressor.get(i), 0, tY, CFG.BUTTON_WIDTH * 2, false, false) {
               protected int getWidth() {
                  return Menu_InGame_DeclareWar.this.getElementW();
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i++ % 2);
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         }

         i = 0;
         tY = tYClone;

         for(i = 0; i < lAlliesDefender.size(); ++i) {
            menuElements.add(new Button_Statistics_CallAlly_Right((Integer)lAlliesDefender.get(i), 0, tY, CFG.BUTTON_WIDTH * 2, false, true) {
               protected int getPosX() {
                  return Menu_InGame_DeclareWar.this.getElementW();
               }

               protected int getWidth() {
                  return Menu_InGame_DeclareWar.this.getElementW();
               }

               protected void buildElementHover() {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DeclareWarOn") + ":", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCurrent(), CFG.PADDING, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.getCurrent()).getCivName()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i++ % 2);
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         }

         for(i = 0; i < menuElements.size(); ++i) {
            if (((MenuElement)menuElements.get(i)).getPosY() + ((MenuElement)menuElements.get(i)).getHeight() > tY) {
               tY = ((MenuElement)menuElements.get(i)).getPosY() + ((MenuElement)menuElements.get(i)).getHeight();
            }
         }
      }

      tY += CFG.PADDING;
      menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Cancel"), -1, 2 + CFG.PADDING, tY, CFG.BUTTON_WIDTH, true) {
         protected int getWidth() {
            return Menu_InGame_DeclareWar.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
         }
      });
      menuElements.add(new Button_FlagActionSliderStyle_War(CFG.langManager.get("DeclareWar"), -1, 2, tY, CFG.BUTTON_WIDTH, true) {
         protected int getPosX() {
            return Menu_InGame_DeclareWar.this.getElementW() + CFG.PADDING / 2;
         }

         protected int getWidth() {
            return Menu_InGame_DeclareWar.this.getElementW() - CFG.PADDING - CFG.PADDING / 2;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (Game_Calendar.TURN_ID > 4) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DeclareWar") + ":", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(Menu_InGame_DeclareWar.this.iOnCivID, CFG.PADDING, CFG.PADDING));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(Menu_InGame_DeclareWar.this.iOnCivID).getCivName()));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
                  if (i != Menu_InGame_DeclareWar.this.iOnCivID && i != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.game.getDefensivePact(i, Menu_InGame_DeclareWar.this.iOnCivID) > 0) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefensivePact") + ":"));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(i, CFG.PADDING, CFG.PADDING));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(i).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_defensive_pact, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }
               }
            } else {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected boolean getClickable() {
            return Game_Calendar.TURN_ID > 4;
         }

         protected int getSFX() {
            return this.getClickable() ? SoundsManager.SOUND_WAR2 : super.getSFX();
         }
      });
      i = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
      this.initMenu(new SliderMenuTitle(CFG.langManager.get("DeclareWar"), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getB(), 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.getB(), 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth, CFG.PADDING, false, true);
            oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth - nWidth / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1, true, false);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH / 2 - tempWidth / 2, i, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + i > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - i, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, true);
      this.updateLanguage();
      Menu_InGame_OfferAlliance.lTime = System.currentTimeMillis();
   }

   protected void updateLanguage() {
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_OfferAlliance.lTime + 200L >= System.currentTimeMillis()) {
         Rectangle clipBounds = new Rectangle((float)(this.getPosX() - 2), (float)(CFG.GAME_HEIGHT - this.getPosY()), (float)(this.getWidth() + 4), (float)(-((int)((float)(this.getHeight() + CFG.PADDING) * ((float)(System.currentTimeMillis() - Menu_InGame_OfferAlliance.lTime) / 200.0F)))));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
         oSB.setColor(Color.WHITE);
         this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         CFG.setRender_3(true);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      } else {
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
         oSB.setColor(Color.WHITE);
         this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected final void actionElement(int iID) {
      if (iID != this.getMenuElementsSize() - 1) {
         if (iID == this.getMenuElementsSize() - 2) {
            this.setVisible(false);
         } else {
            this.getMenuElement(iID).setCheckboxState(!this.getMenuElement(iID).getCheckboxState());
         }
      } else {
         CFG.game.declareWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.iOnCivID, false);

         int i;
         for(i = 2; i < this.getMenuElementsSize() - 2; ++i) {
            if (this.getMenuElement(i).getCheckboxState() && this.getMenuElement(i).getClickable()) {
               if (CFG.game.getCiv(this.getMenuElement(i).getCurrent()).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() != CFG.game.getCiv(this.getMenuElement(i).getCurrent()).getAllianceID())) {
                  CFG.game.declareWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getMenuElement(i).getCurrent(), false);
               } else {
                  DiplomacyManager.sendCallToArms(this.getMenuElement(i).getCurrent(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.iOnCivID);
               }
            }
         }

         CFG.updateActiveCivInfo_InGame();

         for(i = 0; i < CFG.game.getCiv(this.iOnCivID).getNumOfProvinces(); ++i) {
            CFG.game.getProvince(CFG.game.getCiv(this.iOnCivID).getProvinceID(i)).updateDrawArmy();
         }

         CFG.menuManager.rebuildMenu_InGame_War(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.iOnCivID);
         CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);
         if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
            CFG.viewsManager.disableAllViews();
         }

         if (CFG.menuManager.getVisibleInGame_WarDetails()) {
            CFG.menuManager.rebuildInGame_WarDetails();
         }

         if (CFG.menuManager.getVisibleInGame_WarPreparations()) {
            CFG.menuManager.setVisibleInGame_WarPreparations(false);
         }

         this.setVisible(false);
      }
   }

   protected final int getW() {
      return this.getWidth() - 4;
   }

   protected final int getElementW() {
      return this.getW() / 2;
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         for(int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setVisible(false);
         }
      }

   }
}
