package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_PeaceTreaty_Response extends SliderMenu {
   protected static int WAR_ID = 0;
   private static final float SCORE_SCALE = 0.6F;
   private String sScore;
   private int iScoreWidth = 0;
   protected static boolean DRAW_TREATY_PROVINCES = true;

   protected Menu_PeaceTreaty_Response() {
      List menuElements = new ArrayList();

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(i)).iProvinceValue);
      }

      menuElements.add(new Button_PeaceTreaty(CFG.langManager.get("PeaceOffer"), WAR_ID, 0, 0, CFG.GAME_WIDTH, Math.max(CFG.BUTTON_HEIGHT * 4 / 5, Math.max(CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 4, (CFG.TEXT_HEIGHT + CFG.PADDING) * 2 + CFG.PADDING)), true, true) {
         protected void buildElementHover() {
         }
      });
      menuElements.add(new Text((String)null, 0, 0, CFG.PADDING, ImageManager.getImage(Images.top_left2).getHeight()) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, 0.275F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 2 - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() * 4 / 5, this.getHeight() - 2);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawTextWithShadow(oSB, this.sText, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + 1 + iTranslateY, this.getColor(isActive));
            CFG.fontMain.getData().setScale(1.0F);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT) : CFG.COLOR_BUTTON_GAME_TEXT_NOT_CLICKABLE);
         }

         protected int getPosY() {
            return CFG.GAME_HEIGHT - this.getHeight();
         }

         protected int getWidth() {
            return Math.max(CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING * 4);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      menuElements.add(new Button_Casualties(WAR_ID, 0, CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left2).getHeight() - CFG.PADDING - Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), CFG.CIV_INFO_MENU_WIDTH * 3 / 4, Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), true));
      menuElements.add(new Text((String)null, 0, 0, CFG.PADDING, ImageManager.getImage(Images.top_left2).getHeight()) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_POSITIVE.r, CFG.COLOR_TEXT_MODIFIER_POSITIVE.g, CFG.COLOR_TEXT_MODIFIER_POSITIVE.b, 0.275F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() * 4 / 5 + iTranslateX, this.getPosY() + 2 - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() * 4 / 5, this.getHeight() - 2, true, false);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawTextWithShadow(oSB, this.sText, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + 1 + iTranslateY, this.getColor(isActive));
            CFG.fontMain.getData().setScale(1.0F);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT) : CFG.COLOR_BUTTON_GAME_TEXT_NOT_CLICKABLE);
         }

         protected int getPosY() {
            return CFG.GAME_HEIGHT - this.getHeight();
         }

         protected int getPosX() {
            return CFG.GAME_WIDTH - this.getWidth();
         }

         protected int getWidth() {
            return Math.max(CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING * 4);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_CLICK2;
         }
      });
      this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
      String tempCityName = "";
      int tempScore = CFG.game.getWar(WAR_ID).getWarScore();
      int tempProvinceID = -1;

      try {
         if (tempScore != 0) {
            int iBest;
            int i;
            if (tempScore < 0) {
               iBest = 0;

               for(i = 1; i < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.size(); ++i) {
                  if (CFG.game.getWar(WAR_ID).getDefenderID(CFG.game.getWar(WAR_ID).getDefenderID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(iBest)).iCivID)).getCasualties() < CFG.game.getWar(WAR_ID).getDefenderID(CFG.game.getWar(WAR_ID).getDefenderID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i)).iCivID)).getCasualties()) {
                     iBest = i;
                  }
               }

               tempProvinceID = CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(CFG.game.getWar(WAR_ID).getDefenderID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(iBest)).iCivID)).getCivID()).getCapitalProvinceID();
            } else {
               iBest = 0;

               for(i = 1; i < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.size(); ++i) {
                  if (CFG.game.getWar(WAR_ID).getAggressorID(CFG.game.getWar(WAR_ID).getAggressorID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(iBest)).iCivID)).getCasualties() < CFG.game.getWar(WAR_ID).getAggressorID(CFG.game.getWar(WAR_ID).getAggressorID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i)).iCivID)).getCasualties()) {
                     iBest = i;
                  }
               }

               tempProvinceID = CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(CFG.game.getWar(WAR_ID).getAggressorID_ByCivID(((PeaceTreaty_Civs)CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(iBest)).iCivID)).getCivID()).getCapitalProvinceID();
            }
         }
      } catch (IndexOutOfBoundsException var7) {
      }

      if (tempProvinceID >= 0 && !CFG.game.getProvince(tempProvinceID).getName().isEmpty()) {
         tempCityName = " - " + CFG.game.getProvince(tempProvinceID).getName();
      }

      this.sScore = Game_Calendar.getCurrentDate() + tempCityName;
      CFG.glyphLayout.setText(CFG.fontMain, this.sScore);
      this.iScoreWidth = (int)(CFG.glyphLayout.width * 0.6F);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(1).setText(CFG.langManager.get("Refuse"));
      this.getMenuElement(3).setText(CFG.langManager.get("Accept"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2);
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2, false, true);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.525F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, this.getHeight() - CFG.PADDING * 2 - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 2, false, true);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.top_left2_sha).draw2(oSB, this.getMenuElement(1).getPosX() + iTranslateX, this.getMenuElement(1).getPosY() - ImageManager.getImage(Images.top_left2_sha).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left2_sha).getWidth() / 2 + CFG.PADDING + this.getMenuElement(1).getWidth(), ImageManager.getImage(Images.top_left2_sha).getHeight(), true, true);
      if (this.getMenuElement(1).getIsHovered()) {
         ImageManager.getImage(Images.top_left3).draw2(oSB, this.getMenuElement(1).getPosX() + iTranslateX, this.getMenuElement(1).getPosY() - ImageManager.getImage(Images.top_left3).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left3).getWidth() / 2 + CFG.PADDING + this.getMenuElement(1).getWidth(), ImageManager.getImage(Images.top_left3).getHeight(), true, true);
      } else {
         ImageManager.getImage(Images.top_left2).draw2(oSB, this.getMenuElement(1).getPosX() + iTranslateX, this.getMenuElement(1).getPosY() - ImageManager.getImage(Images.top_left2).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left2).getWidth() / 2 + CFG.PADDING + this.getMenuElement(1).getWidth(), ImageManager.getImage(Images.top_left2).getHeight(), true, true);
      }

      ImageManager.getImage(Images.top_left2_sha).draw2(oSB, this.getMenuElement(3).getPosX() - (ImageManager.getImage(Images.top_left2_sha).getWidth() / 2 + CFG.PADDING) + iTranslateX, this.getMenuElement(3).getPosY() - ImageManager.getImage(Images.top_left2_sha).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left2_sha).getWidth() / 2 + CFG.PADDING + this.getMenuElement(3).getWidth(), ImageManager.getImage(Images.top_left2_sha).getHeight(), false, true);
      if (this.getMenuElement(1).getIsHovered()) {
         ImageManager.getImage(Images.top_left3).draw2(oSB, this.getMenuElement(3).getPosX() - (ImageManager.getImage(Images.top_left3).getWidth() / 2 + CFG.PADDING) + iTranslateX, this.getMenuElement(3).getPosY() - ImageManager.getImage(Images.top_left3).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left3).getWidth() / 2 + CFG.PADDING + this.getMenuElement(3).getWidth(), ImageManager.getImage(Images.top_left3).getHeight(), false, true);
      } else {
         ImageManager.getImage(Images.top_left2).draw2(oSB, this.getMenuElement(3).getPosX() - (ImageManager.getImage(Images.top_left2).getWidth() / 2 + CFG.PADDING) + iTranslateX, this.getMenuElement(3).getPosY() - ImageManager.getImage(Images.top_left2).getHeight() + iTranslateY, ImageManager.getImage(Images.top_left2).getWidth() / 2 + CFG.PADDING + this.getMenuElement(3).getWidth(), ImageManager.getImage(Images.top_left2).getHeight(), false, true);
      }

      oSB.setColor(new Color(CFG.COLOR_GRADIENT_DARK_BLUE.r, CFG.COLOR_GRADIENT_DARK_BLUE.g, CFG.COLOR_GRADIENT_DARK_BLUE.b, 0.75F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, this.getMenuElement(0).getHeight() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      ImageManager.getImage(Images.line_32_off1).draw(oSB, CFG.GAME_WIDTH / 2 - this.iScoreWidth - CFG.PADDING * 2 + iTranslateX, this.getMenuElement(0).getHeight() + iTranslateY, this.iScoreWidth * 2 + CFG.PADDING * 4, (int)((float)CFG.TEXT_HEIGHT * 0.6F) + CFG.PADDING * 4);
      oSB.setColor(CFG.COLOR_FLAG_FRAME);
      ImageManager.getImage(Images.line_32_off1).draw(oSB, CFG.GAME_WIDTH / 2 - this.iScoreWidth - CFG.PADDING * 2 + iTranslateX, this.getMenuElement(0).getHeight() + (int)((float)CFG.TEXT_HEIGHT * 0.6F) + CFG.PADDING * 4 - 2 + iTranslateY, this.iScoreWidth * 2 + CFG.PADDING * 4, 1);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      CFG.fontMain.getData().setScale(0.6F);
      CFG.drawText(oSB, this.sScore, CFG.GAME_WIDTH / 2 - this.iScoreWidth / 2 + iTranslateX, CFG.PADDING * 2 + this.getMenuElement(0).getHeight() + iTranslateY, new Color(1.0F, 1.0F, 1.0F, 0.55F));
      CFG.fontMain.getData().setScale(1.0F);
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            DRAW_TREATY_PROVINCES = !DRAW_TREATY_PROVINCES;
            Game_Render.updateRenderer();
            int i;
            if (DRAW_TREATY_PROVINCES) {
               for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(i)).iProvinceValue);
               }

               return;
            } else {
               for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth_Just(i);
               }

               return;
            }
         case 1:
            CFG.setDialogType(Dialog.PEACE_TREARY_REFUSE);
         case 2:
         default:
            break;
         case 3:
            CFG.setDialogType(Dialog.PEACE_TREARY_ACCEPT);
      }

   }

   protected final void onBackPressed() {
      backToInGame();
   }

   protected static final void backToInGame() {
      if ((CFG.PLAYER_PEACE || CFG.SPECTATOR_MODE) && CFG.sandbox_task == Menu.eINGAME_PEACE_TREATY) {
         CFG.sandbox_task = null;
      }
      CFG.menuManager.setViewID(Menu.eINGAME);

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth_Just(i);
      }

      CFG.game.checkProvinceActionMenu();
      CFG.map.getMapBG().updateWorldMap_Shaders();
      CFG.viewsManager.setActiveViewID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE);
      CFG.game.setActiveProvinceID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince);
   }
}
