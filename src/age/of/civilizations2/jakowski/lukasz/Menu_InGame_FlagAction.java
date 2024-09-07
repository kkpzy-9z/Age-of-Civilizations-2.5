package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_FlagAction extends SliderMenu {
   //added so getwindowwith function on old versions
   protected static int getWindowWidth() {
      return CFG.GAME_WIDTH - AoCGame.LEFT;
   }

   protected Menu_InGame_FlagAction() {
      List menuElements = new ArrayList();
      menuElements.add(new Button_Flag_JustFrame(CFG.PADDING * 2, CFG.PADDING * 2, true) {
         protected void buildElementHover() {
            if (!CFG.FLIP_Y_CIV_FLAG && CFG.FLIP_Y_CIV_FLAG_COUNTER != 3) {
               this.menuElementHover = null;
            } else {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.FLIP_Y_CIV_FLAG_COUNTER == 3) {
                  if (CFG.FLIP_Y_CIV_FLAG) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("Psst! ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("Hey kid!"));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("Wanna buy some juicy DLC to flip flag back?", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("Just kidding!"));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("That would be a Paradox to pay for that!", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(":(", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      menuElements.add(new Button_Chatbox(CFG.langManager.get("TypeMessage") + "..", 1, "", CFG.PADDING, ((MenuElement)menuElements.get(0)).getPosX() + ((MenuElement)menuElements.get(0)).getWidth() + CFG.PADDING, CFG.PADDING * 2, getWindowWidth() - CFG.PADDING * 4 - ((MenuElement)menuElements.get(0)).getWidth() - ((MenuElement)menuElements.get(0)).getPosX() * 2 - CFG.PADDING * 3 - CFG.BUTTON_WIDTH * 3 - CFG.PADDING, ((MenuElement)menuElements.get(0)).getHeight(), true));
      menuElements.add(new Button_New_Game_Players("", -1, getWindowWidth() - CFG.PADDING * 4 - CFG.PADDING * 2 - CFG.BUTTON_WIDTH, ((MenuElement)menuElements.get(0)).getPosY(), CFG.BUTTON_WIDTH, ((MenuElement)menuElements.get(0)).getHeight(), true) {
         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
            } else {
               oSB.setColor(Color.WHITE);
            }

            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.85F));
            }

            ImageManager.getImage(Images.editor_game).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.editor_game).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.editor_game).getHeight() / 2 + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Options"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      menuElements.add(new Button_New_Game_Players("", -1, getWindowWidth() - CFG.PADDING * 4 - CFG.PADDING * 2 - CFG.BUTTON_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING, ((MenuElement)menuElements.get(0)).getPosY(), CFG.BUTTON_WIDTH, ((MenuElement)menuElements.get(0)).getHeight(), true) {
         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
            } else {
               oSB.setColor(Color.WHITE);
            }

            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.85F));
            }

            ImageManager.getImage(Images.icon_save).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.icon_save).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.icon_save).getHeight() / 2 + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SaveYourProgress"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      menuElements.add(new Button_New_Game_Players("", -1, getWindowWidth() - CFG.PADDING * 4 - CFG.PADDING * 2 - CFG.BUTTON_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 2, ((MenuElement)menuElements.get(0)).getPosY(), CFG.BUTTON_WIDTH, ((MenuElement)menuElements.get(0)).getHeight(), true) {
         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
            } else {
               oSB.setColor(Color.WHITE);
            }

            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.85F));
            }

            ImageManager.getImage(Images.stats).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.stats).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.stats).getHeight() / 2 + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Statistics"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            if (CFG.isDesktop()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("F5", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      this.initMenu((SliderMenuTitle)null, CFG.PADDING * 2 + AoCGame.LEFT, ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 2, getWindowWidth() - CFG.PADDING * 4, CFG.PADDING * 4 + ImageManager.getImage(Images.top_flag_frame).getHeight(), menuElements, false, false);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge_title).getWidth(), ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4);
      ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge_title).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge_title).getWidth(), ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4, true);
      CFG.game.getPlayer(CFG.PLAYER_TURNID).getFlag().draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() - CFG.game.getPlayer(CFG.PLAYER_TURNID).getFlag().getHeight() + this.getMenuElement(0).getPosY() + iTranslateY, ImageManager.getImage(Images.top_flag_frame).getWidth(), ImageManager.getImage(Images.top_flag_frame).getHeight(), false, CFG.FLIP_Y_CIV_FLAG);
      oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 - 1 + iTranslateY, this.getWidth() - 4);
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.375F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth() - 4, 1);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      if (CFG.FLIP_Y_CIV_FLAG_COUNTER == 3) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.7F));
         ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getMenuElement(0).getPosY() + iTranslateY, this.getMenuElement(0).getWidth(), 1);
         ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() + this.getMenuElement(0).getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getMenuElement(0).getPosY() + iTranslateY, this.getMenuElement(0).getWidth(), 1);
         oSB.setColor(Color.WHITE);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_FlagAction();
   }

   protected static final void clickOptions() {
      CFG.game.resetChooseProvinceData();
      CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
      CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
      CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
      int tX = Touch.getMousePosX();
      int tY = Touch.getMousePosY();
      Touch.setMousePosXY(CFG.GAME_WIDTH / 2, CFG.GAME_HEIGHT / 2);
      Map_Scale.SCALE_ANIMATION_TIME = 225;
      CFG.map.getMapScale().setNewCurrentScaleByButton2(-0.175F);
      Touch.setMousePosXY(tX, tY);
      CFG.menuManager.setVisible_InGame_FlagAction(false);
      CFG.menuManager.setVisible_InGame_Options(true);
   }

   protected static final void clickStats() {
      if (!CFG.menuManager.getVisible_Menu_InGame_Outliner()) {
         CFG.menuManager.setVisible_InGame_FlagAction(false);
         CFG.menuManager.setVisible_Menu_InGame_Outliner(true);
      } else {
         CFG.menuManager.setVisible_InGame_FlagAction(false);
         CFG.menuManager.setOrderOfMenu_Menu_InGame_Outliner();
      }

   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.FLIP_Y_CIV_FLAG = !CFG.FLIP_Y_CIV_FLAG;
            if (CFG.FLIP_Y_CIV_FLAG) {
               ++CFG.FLIP_Y_CIV_FLAG_COUNTER;
               if (CFG.FLIP_Y_CIV_FLAG_COUNTER < 0) {
                  CFG.FLIP_Y_CIV_FLAG_COUNTER = 0;
               } else if (CFG.FLIP_Y_CIV_FLAG_COUNTER > 10) {
                  CFG.FLIP_Y_CIV_FLAG_COUNTER = 0;
               }
            }

            this.getMenuElement(iID).buildElementHover();
            break;
         case 1:
            CFG.showKeyboard_Commands();
            break;
         case 2:
            clickOptions();
            break;
         case 3:
            CFG.menuManager.setVisible_InGame_FlagAction(false);
            CFG.setDialogType(Dialog.SAVE_THE_GAME);
            break;
         case 4:
            clickStats();
      }

   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         CFG.menuManager.getKeyboard().setVisible(false);
      }

   }
}
