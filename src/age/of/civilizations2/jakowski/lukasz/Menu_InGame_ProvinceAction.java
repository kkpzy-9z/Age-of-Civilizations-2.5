package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_ProvinceAction extends SliderMenu {
   private String sMigration = "";
   private int iMigrationWidth = -1;

   protected Menu_InGame_ProvinceAction() {
      List menuElements = new ArrayList();
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING, CFG.PADDING, true) {
         protected int getSFX() {
            return SoundsManager.SOUND_ACTION_MOVE;
         }

         protected void buildElementHover() {
            if (CFG.isDesktop()) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("Q", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.275F));
            }

            ImageManager.getImage(Images.act_move).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_move).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_move).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH, CFG.PADDING, true) {
         protected boolean getVisible() {
            //try {
            //   return Menu_InGame_ProvinceAction.canRecruit();
            //} catch (IndexOutOfBoundsException var2) {
            //   return super.getVisible();
            //}
            return super.getVisible();
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (CFG.game.getActiveProvinceID() >= 0 && !Menu_InGame_ProvinceAction.canRecruit()) {
               //if puppet
               if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouCantRecruitArmyInOccupiedProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               }

               if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince()).getIsPupet()) {
                  //if player is owner of province
                  if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince()).getPuppetOfCivID(), CFG.PADDING, 0));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CannotRecruit"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  }
               }

               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_RECRUIT) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_RECRUIT / 10.0F, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            if (CFG.isDesktop()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("W", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else if (nElements.size() > 0) {
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
            }

            ImageManager.getImage(Images.act_recruit).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_recruit).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_recruit).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected int getSFX() {
            try {
               return this.getClickable() ? SoundsManager.SOUND_RECRUIT : super.getSFX();
            } catch (IndexOutOfBoundsException var2) {
               return super.getSFX();
            }
         }

         protected boolean getClickable() {
            try {
               if (CFG.game.getActiveProvinceID() >= 0) {
                  return Menu_InGame_ProvinceAction.canRecruit();
               } else {
                  return super.getClickable();
               }
            } catch (IndexOutOfBoundsException var2) {
               return super.getClickable();
            }
         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 3 + CFG.BUTTON_WIDTH * 2, CFG.PADDING, true) {
         protected boolean getVisible() {
            try {
               return Menu_InGame_ProvinceAction.canRecruit();
            } catch (IndexOutOfBoundsException var2) {
               return super.getVisible();
            }
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
            }

            ImageManager.getImage(Images.act_more).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_more).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_more).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            if (CFG.isDesktop()) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("E", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 4 + CFG.BUTTON_WIDTH * 3, CFG.PADDING, true) {
         protected int getPosX() {
            return Menu_InGame_ProvinceAction.this.getMenuElement(1).getVisible() ? super.getPosX() : Menu_InGame_ProvinceAction.this.getMenuElement(0).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(0).getWidth() + CFG.PADDING;
         }

         protected void buildElementHover() {
            if (CFG.isDesktop()) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("R", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
            }

            ImageManager.getImage(Images.act_recruit).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_recruit).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_recruit).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected boolean getVisible() {
            return CFG.game.getActiveProvinceID() >= 0 && (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > 0);
         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 5 + CFG.BUTTON_WIDTH * 4, CFG.PADDING, true) {
         protected int getSFX() {
            return SoundsManager.SOUND_ACTION_MOVE;
         }

         protected int getPosX() {
            return Menu_InGame_ProvinceAction.this.getMenuElement(3).getVisible() ? Menu_InGame_ProvinceAction.this.getMenuElement(3).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(3).getWidth() + CFG.PADDING : (Menu_InGame_ProvinceAction.this.getMenuElement(2).getVisible() ? Menu_InGame_ProvinceAction.this.getMenuElement(2).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(2).getWidth() + CFG.PADDING : Menu_InGame_ProvinceAction.this.getMenuElement(0).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(0).getWidth() + CFG.PADDING);
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.275F));
            }

            ImageManager.getImage(Images.act_moveto).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_moveto).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_moveto).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            if (CFG.isDesktop()) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("T", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 5 + CFG.BUTTON_WIDTH * 4, CFG.PADDING, true) {
         protected int getSFX() {
            return SoundsManager.SOUND_ACTION_MOVE;
         }

         protected int getPosX() {
            return Menu_InGame_ProvinceAction.this.getMenuElement(4).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(4).getWidth() + CFG.PADDING;
         }

         protected int getTextWidth() {
            return this.getClickable() ? super.getTextWidth() + CFG.PADDING + CFG.CIV_FLAG_WIDTH : Math.max(super.getTextWidth() + CFG.PADDING + CFG.CIV_FLAG_WIDTH, Menu_InGame_ProvinceAction.this.iMigrationWidth);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (this.getClickable()) {
               CFG.game.getCiv(CFG.game.getActiveCivID()).getFlag().draw(oSB, this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.game.getActiveCivID()).getFlag().getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
               if (isActive) {
                  CFG.drawText(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + CFG.CIV_FLAG_WIDTH + iTranslateX, this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY, this.getColor(isActive));
               } else {
                  CFG.drawTextWithShadow(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + CFG.CIV_FLAG_WIDTH + iTranslateX, this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY, this.getColor(isActive));
               }
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
               CFG.game.getCiv(CFG.game.getActiveCivID()).getFlag().draw(oSB, this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.PADDING / 2 - this.getTextHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.game.getActiveCivID()).getFlag().getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.PADDING / 2 - this.getTextHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
               oSB.setColor(Color.WHITE);
               if (isActive) {
                  CFG.drawText(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + CFG.CIV_FLAG_WIDTH + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.PADDING / 2 - this.getTextHeight() + iTranslateY, this.getColor(isActive));
               } else {
                  CFG.drawTextWithShadow(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + CFG.CIV_FLAG_WIDTH + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.PADDING / 2 - this.getTextHeight() + iTranslateY, this.getColor(isActive));
               }

               CFG.fontMain.getData().setScale(0.8F);
               CFG.drawTextWithShadow(oSB, Menu_InGame_ProvinceAction.this.sMigration, this.getPosX() + (this.getWidth() - Menu_InGame_ProvinceAction.this.iMigrationWidth) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 + CFG.PADDING + (CFG.TEXT_HEIGHT - CFG.TEXT_HEIGHT) / 2 + iTranslateY, new Color(0.46F, 0.46F, 0.46F, 0.65F));
               CFG.fontMain.getData().setScale(1.0F);
            }

         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.275F));
            }

            ImageManager.getImage(Images.act_migrate).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_migrate).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_migrate).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            try {
               ArrayList nElements;
               ArrayList nData;
               if (!this.getClickable() && CFG.game.getActiveProvinceID() >= 0) {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouCantMigrateUntilX", Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.uncivilizedCanMigrate_FromProvince_NumOfTurns(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()))), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.uncivilizedCanMigrate_FromProvince_NumOfTurns(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } else {
                  nElements = new ArrayList();
                  nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MigrateToAnotherProvince"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)((CFG.game.getProvince(CFG.game.getActiveProvinceID()).getIsCapital() ? 0.1F : 0.05F) * 100.0F) + "%", CFG.COLOR_TEXT_RESEARCH));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }
            } catch (IndexOutOfBoundsException var3) {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING * 5 + CFG.BUTTON_WIDTH * 4, CFG.PADDING, true) {
         protected int getPosX() {
            return Menu_InGame_ProvinceAction.this.getMenuElement(5).getVisible() ? Menu_InGame_ProvinceAction.this.getMenuElement(5).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(5).getWidth() + CFG.PADDING : Menu_InGame_ProvinceAction.this.getMenuElement(4).getPosX() + Menu_InGame_ProvinceAction.this.getMenuElement(4).getWidth() + CFG.PADDING;
         }

         protected boolean getVisible() {
            try {
               return CFG.game.getProvince(CFG.game.getActiveProvinceID()).isOccupied();
            } catch (IndexOutOfBoundsException var2) {
               return false;
            }
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
            }

            ImageManager.getImage(Images.act_plunder).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_plunder).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_plunder).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            try {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).isOccupied()) {
                  if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName().length() > 0) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Plunder") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName()));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince()));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Plunder"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OccupiedProvince")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getProvince(CFG.game.getActiveProvinceID()).isOccupied() ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_PLUNDER / 10.0F, CFG.COLOR_INGAME_MOVEMENT));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OnlyOccupiedProvinceCanBePlundered"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } catch (IndexOutOfBoundsException var3) {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING, CFG.PADDING, true) {
         protected boolean getVisible() {
            try {
               return CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
            } catch (IndexOutOfBoundsException var2) {
               return false;
            }
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            if (isActive) {
               oSB.setColor(Color.WHITE);
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.575F));
            }

            ImageManager.getImage(Images.top_diplomacy_points).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.act_plunder).getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.act_plunder).getHeight() - CFG.PADDING + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            if (CFG.isDesktop()) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Shortcut") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("TAB", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }
         }
      });

      this.initMenu((SliderMenuTitle)null, 0, CFG.GAME_HEIGHT - CFG.map.getMapBG().getMinimapHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, menuElements, true, false);
      this.updateLanguage();
      CFG.fMOVE_MENU_PERCENTAGE = 5.0F;
      CFG.lMOVE_MENU_TIME = System.currentTimeMillis();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Move"));
      this.getMenuElement(1).setText(CFG.langManager.get("Recruit"));
      this.getMenuElement(2).setText(CFG.langManager.get("More"));
      this.getMenuElement(3).setText(CFG.langManager.get("Disband"));
      this.getMenuElement(4).setText(CFG.langManager.get("MoveTo"));
      this.getMenuElement(5).setText(CFG.langManager.get("Migrate"));
      this.getMenuElement(6).setText(CFG.langManager.get("Plunder"));
      this.getMenuElement(7).setText(CFG.langManager.get("Manage"));
      this.updateButtonWidth(5, CFG.PADDING, CFG.BUTTON_WIDTH);
      //this.updatedButtonsWidth(CFG.PADDING, CFG.BUTTON_WIDTH);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      CFG.fMOVE_MENU_PERCENTAGE += (float)(System.currentTimeMillis() - CFG.lMOVE_MENU_TIME) / 300.0F * 95.0F;
      if (CFG.fMOVE_MENU_PERCENTAGE > 100.0F) {
         CFG.fMOVE_MENU_PERCENTAGE = 100.0F;
      } else {
         CFG.setRender_3(true);
      }

      CFG.lMOVE_MENU_TIME = System.currentTimeMillis();
      Rectangle clipBounds = new Rectangle((float)(this.getPosX() + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() + 1 - iTranslateY), (float)this.getWidth(), (float)(-this.getHeight() - 1));
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);

      int tWidth;
      if (this.getMenuElement(this.getMenuElementsSize() - 2).getVisible()) {
          tWidth = this.getMenuElement(this.getMenuElementsSize() - 2).getPosX() + this.getMenuElement(this.getMenuElementsSize() - 2).getWidth();
      } else if (this.getMenuElement(this.getMenuElementsSize() - 3).getVisible()) {
          tWidth = this.getMenuElement(this.getMenuElementsSize() - 3).getPosX() + this.getMenuElement(this.getMenuElementsSize() - 3).getWidth();
      } else {
          tWidth = this.getMenuElement(this.getMenuElementsSize() - 4).getPosX() + this.getMenuElement(this.getMenuElementsSize() - 4).getWidth();
      }
      tWidth += CFG.PADDING + 1;

      ImageManager.getImage(Images.bg_game_action).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.bg_game_action).getHeight() + (int)((float)this.getHeight() * (100.0F - CFG.fMOVE_MENU_PERCENTAGE) / 100.0F) - 1 + iTranslateY, tWidth, this.getHeight() + 1, true, false);
      ImageManager.getImage(Images.bg_game_action).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.bg_game_action).getHeight() + (int)((float)this.getHeight() * (100.0F - CFG.fMOVE_MENU_PERCENTAGE) / 100.0F) - 1 + iTranslateY, tWidth, this.getHeight() + 1, true, false);

      ImageManager.getImage(Images.bg_game_action).draw2(oSB, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH + CFG.PADDING * 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.bg_game_action).getHeight() + (int)((float)this.getHeight() * (100.0F - CFG.fMOVE_MENU_PERCENTAGE) / 100.0F) - 1 + iTranslateY, tWidth, this.getHeight() + 1, false, false);
      ImageManager.getImage(Images.bg_game_action).draw2(oSB, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING * 2 - iTranslateX, this.getPosY() - ImageManager.getImage(Images.bg_game_action).getHeight() + (int)((float)this.getHeight() * (100.0F - CFG.fMOVE_MENU_PERCENTAGE) / 100.0F) - 1 + iTranslateY, tWidth, this.getHeight() + 1, false, false);
      ImageManager.getImage(Images.line_32_off1).draw(oSB, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING * 2 - iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, tWidth, 1);

      oSB.setColor(CFG.COLOR_BG_GAME_MENU_SHADOW);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, tWidth, 1);
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, tWidth, 1);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, (int)((float)this.getHeight() * (100.0F - CFG.fMOVE_MENU_PERCENTAGE) / 100.0F) + iTranslateY, sliderMenuIsActive);
   }

   protected void beginClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
   }

   protected static final void clickMove() {
      if (CFG.gameAction.isMovingArmyFromProvince(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
         CFG.game.chooseProvinceMode();
         CFG.menuManager.setVisible_InGame_ProvinceAction(false);
      } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
         CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
      } else if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) <= 0) {
         CFG.menuManager.setVisible_InGame_ActionInfo_NoUnits();
      } else {
         CFG.game.chooseProvinceMode();
         CFG.menuManager.setVisible_InGame_ProvinceAction(false);
      }

      if (CFG.menuManager.getInGame_Plunder().getVisible()) {
         CFG.menuManager.getInGame_Plunder().setVisible(false);
      }

   }

   protected static final boolean canRecruit() {
      //if not valid, return false
      if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() > 0) return false;

      if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
         //if puppet and autonomy doesn't have eco control
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIsPupet() && !CFG.game.getCiv(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getVassal_AutonomyStatus(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isMilitaryControl()) {
            return false;
         }
         //if player province return true
         return true;
      } else if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
         //if puppet of player, return if military control
         return (!CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).isMilitaryControl());
      }

      return false;
   }

   protected static final void clickRecruit() {
      if (CFG.game.getActiveProvinceID() >= 0) {
         if (canRecruit()) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isRecruitingArmyInProvinceID(CFG.game.getActiveProvinceID()) < 0) {
               if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_RECRUIT) {
                  CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() < (long)CFG.getCostOfRecruitArmyMoney(CFG.game.getActiveProvinceID())) {
                  CFG.menuManager.setVisible_InGame_ActionInfo_TreasuryIsEmpty();
               } else {
                  CFG.game.resetChooseProvinceData();
                  CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                  CFG.gameAction.updateRecruitSlider();
                  CFG.menuManager.setVisible_InGame_ProvinceRecruit(true);
                  CFG.menuManager.setVisible_InGame_ActionInfo_Recruit();
               }
            } else {
               CFG.game.resetChooseProvinceData();
               CFG.menuManager.setVisible_InGame_ProvinceAction(false);
               CFG.gameAction.updateRecruitSlider();
               CFG.menuManager.setVisible_InGame_ProvinceRecruit(true);
               CFG.menuManager.setVisible_InGame_ActionInfo_Recruit();
            }
         } else {
            CFG.menuManager.setVisible_InGame_ActionInfo_RecruitOccupied();
         }
      }

      if (CFG.menuManager.getInGame_Plunder().getVisible()) {
         CFG.menuManager.getInGame_Plunder().setVisible(false);
      }

   }

   protected static final void clickBuild() {
      BuildingsManager.iBuildInProvinceID = CFG.game.getActiveProvinceID();
      if (BuildingsManager.iBuildInProvinceID >= 0) {
         CFG.game.resetChooseProvinceData();
         CFG.menuManager.setVisible_InGame_ProvinceBuild(!CFG.menuManager.getInGame_ProvinceBuild_Visible(), true);
      } else {
         BuildingsManager.iBuildInProvinceID = 0;
      }

      if (CFG.menuManager.getInGame_Plunder().getVisible()) {
         CFG.menuManager.getInGame_Plunder().setVisible(false);
      }

   }

   protected static final void clickDisband() {
      if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_DISBAND) {
         CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
      } else {
         CFG.game.resetChooseProvinceData();
         CFG.menuManager.setVisible_InGame_ProvinceAction(false);
         CFG.activeCivilizationArmyID = 0;
         CFG.gameAction.updateDisbandSlider();
         CFG.menuManager.setVisible_InGame_ProvinceDisband(true);
         CFG.menuManager.setVisible_InGame_ActionInfo_Disband();
      }

      if (CFG.menuManager.getInGame_Plunder().getVisible()) {
         CFG.menuManager.getInGame_Plunder().setVisible(false);
      }

   }

   protected static final void clickMoveTo() {
      if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
         CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
      } else if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) <= 0) {
         CFG.menuManager.setVisible_InGame_ActionInfo_NoUnits();
      } else {
         CFG.game.resetChooseProvinceData();
         CFG.game.regroupArmyMode();
         CFG.menuManager.setVisible_InGame_ProvinceAction(false);
      }

      if (CFG.menuManager.getInGame_Plunder().getVisible()) {
         CFG.menuManager.getInGame_Plunder().setVisible(false);
      }

   }

   protected static final void clickManage() {
      if (CFG.menuManager.getVisible_InGame_FlagAction()) {
         Menu_InGame.clickFlagAction();
      }

      CFG.menuManager.setVisible_InGame_CivManage(!CFG.menuManager.getVisible_InGame_CivManage());
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            clickMove();
            break;
         case 1:
            clickRecruit();
            break;
         case 2:
            clickBuild();
            break;
         case 3:
            clickDisband();
            break;
         case 4:
            clickMoveTo();
            break;
         case 5:
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE) {
               CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
            } else {
               CFG.migrateMode = true;
               CFG.game.chooseProvinceMode();
               CFG.menuManager.setVisible_InGame_ProvinceAction(false);
            }
            break;
         case 6:
            if (CFG.game.getActiveProvinceID() >= 0) {
               if (Menu_InGame_Plunder.iProvinceID == CFG.game.getActiveProvinceID() && CFG.menuManager.getInGame_Plunder().getVisible()) {
                  CFG.menuManager.getInGame_Plunder().actionElement(CFG.menuManager.getInGame_Plunder().getMenuElementsSize() - 1);
               } else {
                  CFG.menuManager.rebuildInGame_Plunder(CFG.game.getActiveProvinceID());
               }
            }
            break;
         case 7:
            clickManage();
            break;
      }

   }

   protected void setVisible(boolean visible) {
      if (visible && this.getVisible() != visible) {
         CFG.fMOVE_MENU_PERCENTAGE = 5.0F;
         CFG.lMOVE_MENU_TIME = System.currentTimeMillis();
      }

      super.setVisible(visible);
      if (this.getVisible()) {
         this.getMenuElement(5).setVisible(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0);
         if (this.getMenuElement(5).getVisible()) {
            MenuElement var10000 = this.getMenuElement(5);
            Game var10001 = CFG.game;
            var10000.setClickable(Game.uncivilizedCanMigrate_FromProvince(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
            if (!this.getMenuElement(5).getClickable()) {
               this.sMigration = CFG.langManager.get("TurnsX", CFG.game.uncivilizedCanMigrate_FromProvince_NumOfTurns(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
               CFG.glyphLayout.setText(CFG.fontMain, this.sMigration);
               this.iMigrationWidth = (int)(CFG.glyphLayout.width * 0.8F);
            }
         }
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame();
   }
}
