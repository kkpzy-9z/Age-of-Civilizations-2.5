package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_Settings_Province extends SliderMenu {
   private String sScale;

   protected Menu_Settings_Province() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4;
      List menuElements = new ArrayList();
      int tPosY = CFG.PADDING;
      menuElements.add(new Button_NewGameStyle((String)null, -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + CFG.BUTTON_HEIGHT + (tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2), tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 25, 255, CFG.settingsManager.PROVINCE_ALPHA));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 100, 400, (int)(CFG.settingsManager.STOP_SCALING_ARMY * 100.0F)) {
         protected String getDrawText() {
            return "" + (float)this.getCurrent() / 100.0F;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;

      //new border settings header @ 6
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //new border size slider @ 7
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 300, (int)(CFG.settingsManager.PROVINCE_BORDER_SIZE * 100.0F)) {
         protected String getDrawText() {
            return "" + (float)this.getCurrent() / 100.0F;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;

      //new border color picker @ 8
      menuElements.add(new Button_NewGameStyle_Clear("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         int iCurrent;

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor(CFG.settingsManager.PROVINCE_BORDER_COLOR.getR(), CFG.settingsManager.PROVINCE_BORDER_COLOR.getG(), CFG.settingsManager.PROVINCE_BORDER_COLOR.getB(), 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //inner borders 6 -> 7 -> 8 -> 9
      menuElements.add(new Button_NewGameStyle((String)null, -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true, CFG.settingsManager.ENABLE_INNER_BORDERS) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.ENABLE_INNER_BORDERS;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //cities header label 7 -> 8 -> 9 -> 10
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 100, CFG.settingsManager.PERCETANGE_OF_CITIES_ON_MAP) {
         protected String getDrawText() {
            return super.getDrawText() + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 10, 200, (int)(CFG.settingsManager.CITIES_FONT_SCALE * 100.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle((String)null, -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true, CFG.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + CFG.BUTTON_HEIGHT + (tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2), tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 100, (int)(CFG.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT * 100.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Button_NewGameStyle_Clear("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         int iCurrent;

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor(CFG.settingsManager.civNamesFontColor.getR(), CFG.settingsManager.civNamesFontColor.getG(), CFG.settingsManager.civNamesFontColor.getB(), 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 100, (int)(CFG.settingsManager.civNamesFontColor_ALPHA * 100.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Button_NewGameStyle_Clear("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         int iCurrent;

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor(CFG.settingsManager.civNamesFontColorBorder.getR(), CFG.settingsManager.civNamesFontColorBorder.getG(), CFG.settingsManager.civNamesFontColorBorder.getB(), 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 100, (int)(CFG.settingsManager.civNamesFontColorBorder_ALPHA * 100.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, (int)((float)CFG.settingsManager.FONT_BORDER_SIZE * 0.4F), CFG.settingsManager.FONT_BORDER_WIDTH_OF_BORDER) {
         protected String getDrawText() {
            return super.getDrawText() + "px";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 5000, CFG.settingsManager.CIVILIZATIONS_NAMES_INTERVAL) {
         protected String getDrawText() {
            return super.getDrawText() + "ms";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Clear("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         int iCurrent;

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor(CFG.settingsManager.civNamesFontColor.getR(), CFG.settingsManager.civNamesFontColor.getG(), CFG.settingsManager.civNamesFontColor.getB(), 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 255, (int)(CFG.settingsManager.PROVINCE_ALPHA_WASTELAND * 255.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Clear("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         int iCurrent;

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor(CFG.settingsManager.civNamesFontColorBorder.getR(), CFG.settingsManager.civNamesFontColorBorder.getG(), CFG.settingsManager.civNamesFontColorBorder.getB(), 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + (int)((float)this.getTextWidth() * 0.8F) - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Settings_Province.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 0, 255, (int)(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * 255.0F)) {
         protected String getDrawText() {
            return super.getDrawText() + "";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Button_NewGameStyle_Left("<<", -1, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle("", -1, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            oSB.setColor(Color.WHITE);
            CFG.linesManager.moveLandImage.draw2(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.linesManager.moveLandImage.getHeight() / 2 - CFG.linesManager.moveLandImage.getHeight() + iTranslateY, this.getWidth() - CFG.PADDING * 4, CFG.linesManager.moveLandImage.getHeight());
         }
      });
      menuElements.add(new Button_NewGameStyle_Right(">>", -1, CFG.PADDING + CFG.BUTTON_HEIGHT + (tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2), tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Button_NewGameStyle_Left("<<", -1, CFG.PADDING, tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle("", -1, CFG.PADDING + CFG.BUTTON_HEIGHT, tPosY, tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            oSB.setColor(Color.WHITE);
            CFG.linesManager.highlightImage.draw2(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.linesManager.highlightImage.getHeight() / 2 - CFG.linesManager.highlightImage.getHeight() + iTranslateY, this.getWidth() - CFG.PADDING * 4, CFG.linesManager.highlightImage.getHeight());
         }
      });
      menuElements.add(new Button_NewGameStyle_Right(">>", -1, CFG.PADDING + CFG.BUTTON_HEIGHT + (tempW - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT * 2), tPosY, CFG.BUTTON_HEIGHT, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_BudgetTitle("", -1, 2, tPosY, tempW - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 25, 255, CFG.settingsManager.OCCUPIED_PROVINCE_ALPHA) {
         protected String getDrawText() {
            return "" + (int)((float)this.getCurrent() / 255.0F * 100.0F) + "%";
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Clear("", CFG.PADDING * 2, tPosY, tempW - CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), 1, 100, (int)(CFG.settingsManager.OCCUPIED_STRIPES_SIZE * 10.0F)) {
         protected String getDrawText() {
            return "" + (float)this.getCurrent() / 10.0F;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Button_NewGameStyle((String)null, -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 5, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4, this.getHeight());
            oSB.setColor(new Color(0.003921569F, 0.32941177F, 0.50980395F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(0.003921569F, 0.32941177F, 0.50980395F, 0.375F));
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
      }, CFG.GAME_WIDTH - tempW, CFG.PADDING + CFG.BUTTON_HEIGHT * 3 / 4, tempW, Math.min(tPosY, CFG.GAME_HEIGHT - (CFG.PADDING + CFG.BUTTON_HEIGHT * 3 / 4)), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getMenuElement(2).setText(CFG.langManager.get("FontSizeofArmy") + ": " + CFG.settingsManager.FONT_ARMY_SIZE);
      this.getMenuElement(4).setText(CFG.langManager.get("ProvinceAlpha"));
      this.getMenuElement(5).setText(CFG.langManager.get("Scale"));

      //new lang init
      this.getMenuElement(6).setText(CFG.langManager.get("Borders"));
      this.getMenuElement(7).setText(CFG.langManager.get("BorderSize"));
      this.getMenuElement(8).setText(CFG.langManager.get("Color"));

      this.getMenuElement(3 + 6).setText(CFG.langManager.get("InnerBorders"));
      this.getMenuElement(3 + 7).setText(CFG.langManager.get("Cities"));
      this.getMenuElement(3 + 8).setText(CFG.langManager.get("NumberOfCities"));
      this.getMenuElement(3 + 9).setText(CFG.langManager.get("ScaleOfCitiesNames"));
      this.getMenuElement(3 + 10).setText(CFG.langManager.get("CivilizationsNames"));
      this.getMenuElement(3 + 11).setText(CFG.langManager.get("NamesOfCivilizationsOverProvinces"));
      this.getMenuElement(3 + 13).setText(CFG.langManager.get("FontSize") + ": " + CFG.settingsManager.FONT_BORDER_SIZE);
      this.getMenuElement(3 + 15).setText(CFG.langManager.get("MinScaleofCivilizationsNames"));
      this.getMenuElement(3 + 16).setText(CFG.langManager.get("Color"));
      this.getMenuElement(3 + 17).setText(CFG.langManager.get("Alpha"));
      this.getMenuElement(3 + 18).setText(CFG.langManager.get("BorderColor"));
      this.getMenuElement(3 + 19).setText(CFG.langManager.get("Alpha"));
      this.getMenuElement(3 + 20).setText(CFG.langManager.get("Width"));
      this.getMenuElement(3 + 21).setText(CFG.langManager.get("AnimationTime"));
      this.getMenuElement(3 + 22).setText(CFG.langManager.get("Wasteland"));
      this.getMenuElement(3 + 23).setText(CFG.langManager.get("Color"));
      this.getMenuElement(3 + 24).setText(CFG.langManager.get("Alpha"));
      this.getMenuElement(3 + 25).setText(CFG.langManager.get("Fogofwar"));
      this.getMenuElement(3 + 26).setText(CFG.langManager.get("Color"));
      this.getMenuElement(3 + 27).setText(CFG.langManager.get("Alpha"));
      this.getMenuElement(3 + 34).setText(CFG.langManager.get("OccupiedProvinces"));
      this.getMenuElement(3 + 35).setText(CFG.langManager.get("Alpha"));
      this.getMenuElement(3 + 36).setText(CFG.langManager.get("Scale"));
      this.getMenuElement(3 + 37).setText(CFG.langManager.get("Defaults"));
      this.getMenuElement(4).setCurrent(CFG.settingsManager.PROVINCE_ALPHA);
      this.getMenuElement(5).setCurrent((int)(CFG.settingsManager.STOP_SCALING_ARMY * 100.0F));

      //defaults
      this.getMenuElement(7).setCurrent((int)(CFG.settingsManager.PROVINCE_BORDER_SIZE * 100.0F));

      this.getMenuElement(3 + 7).setCurrent(CFG.settingsManager.PERCETANGE_OF_CITIES_ON_MAP);
      this.getMenuElement(3 + 9).setCurrent((int)(CFG.settingsManager.CITIES_FONT_SCALE * 100.0F));
      this.getMenuElement(3 + 15).setCurrent((int)(CFG.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT * 100.0F));
      this.getMenuElement(3 + 17).setCurrent((int)(CFG.settingsManager.civNamesFontColor_ALPHA * 100.0F));
      this.getMenuElement(3 + 19).setCurrent((int)(CFG.settingsManager.civNamesFontColorBorder_ALPHA * 100.0F));
      this.getMenuElement(3 + 20).setCurrent(CFG.settingsManager.FONT_BORDER_WIDTH_OF_BORDER);
      this.getMenuElement(3 + 21).setCurrent(CFG.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
      this.getMenuElement(3 + 24).setCurrent((int)(CFG.settingsManager.PROVINCE_ALPHA_WASTELAND * 255.0F));
      this.getMenuElement(3 + 27).setCurrent((int)(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * 255.0F));
      this.getMenuElement(3 + 35).setCurrent(CFG.settingsManager.PROVINCE_ALPHA);
      this.getMenuElement(3 + 36).setCurrent((int)(CFG.settingsManager.OCCUPIED_STRIPES_SIZE * 10.0F));
      this.getTitle().setText(CFG.langManager.get("ProvinceSettings"));
      this.sScale = CFG.langManager.get("Scale") + ": ";
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT * 3 / 4);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), false, true);
      CFG.fontBorder.getData().setScale(1.0F);
      CFG.drawTextBorder(oSB, "Age of History II", CFG.PADDING * 2 + iTranslateX, CFG.PADDING * 2, Color.WHITE);
      CFG.drawTextWithShadow(oSB, this.sScale + CFG.map.getMapScale().getCurrentScale(), CFG.PADDING + iTranslateX, CFG.GAME_HEIGHT - CFG.PADDING - CFG.TEXT_HEIGHT, CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
   }

   protected final void updateArmyWidth() {
      for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         for (int j = 0; j < CFG.game.getProvince(i).getCivsSize(); ++j) {
            CFG.game.getProvince(i).getArmy_Obj(j).updateArmyWidth_Just(i);
         }
      }
      for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
         for (int j = 0; j < CFG.game.getCiv(i).getRecruitArmySize(); ++j) {
            CFG.game.getCiv(i).getRecruitArmy(j).setArmy(CFG.game.getCiv(i).getRecruitArmy(j).getArmy());
         }
         for (int j = 0; j < CFG.game.getCiv(i).getMoveUnitsPlunderSize(); ++j) {
            CFG.game.getCiv(i).getMoveUnits_Plunder(j).setNumOfUnits(CFG.game.getCiv(i).getMoveUnits_Plunder(j).getNumOfUnits());
         }
      }
   }

   @Override
   protected void actionElement(final int iID) {
      switch (iID) {
         case 0: {
            this.onBackPressed();
            break;
         }
         case 1: {
            final SettingsManager settingsManager = CFG.settingsManager;
            --settingsManager.FONT_ARMY_SIZE;
            if (CFG.settingsManager.FONT_ARMY_SIZE < 12) {
               CFG.settingsManager.FONT_ARMY_SIZE = 12;
            }
            CFG.loadFontArmy();
            if (SaveManager.gameCanBeContinued) {
               this.updateArmyWidth();
            }
            else {
               for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(i);
               }
            }
            Menu_InitGame.loadArmyBGImages();
            this.updateLanguage();
            break;
         }
         case 2: {
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            break;
         }
         case 3: {
            final SettingsManager settingsManager2 = CFG.settingsManager;
            ++settingsManager2.FONT_ARMY_SIZE;
            if (CFG.settingsManager.FONT_ARMY_SIZE > 128) {
               CFG.settingsManager.FONT_ARMY_SIZE = 128;
            }
            CFG.loadFontArmy();
            if (SaveManager.gameCanBeContinued) {
               this.updateArmyWidth();
            }
            else {
               for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(i);
               }
            }
            Menu_InitGame.loadArmyBGImages();
            this.updateLanguage();
            break;
         }
         case 4: {
            CFG.settingsManager.PROVINCE_ALPHA = this.getMenuElement(iID).getCurrent();
            break;
         }
         case 5: {
            CFG.settingsManager.STOP_SCALING_ARMY = this.getMenuElement(iID).getCurrent() / 100.0f;
            break;
         }

         //new border settings functionoloy
         case 7: {
            CFG.settingsManager.PROVINCE_BORDER_SIZE = (float)this.getMenuElement(iID).getCurrent() / 100.0F;

            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
               CFG.game.getProvince(i).updateProvinceBorder();
            }
            CFG.game.updateActiveProvinceBorderStyle();
            break;
         }
         case 8: {
            CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.settingsManager.PROVINCE_BORDER_COLOR.getR(), CFG.settingsManager.PROVINCE_BORDER_COLOR.getG(), CFG.settingsManager.PROVINCE_BORDER_COLOR.getB());
            CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.PROVINCE_BORDER_COLOR);
            break;
         }

         case 9: {
            CFG.settingsManager.ENABLE_INNER_BORDERS = !CFG.settingsManager.ENABLE_INNER_BORDERS;
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
               CFG.game.getProvince(i).updateProvinceBorder();
            }
            break;
         }
         case 11: {
            CFG.settingsManager.PERCETANGE_OF_CITIES_ON_MAP = this.getMenuElement(iID).getCurrent();
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
               CFG.game_NextTurnUpdate.updateCities(i);
            }
            break;
         }
         case 12: {
            CFG.settingsManager.CITIES_FONT_SCALE = this.getMenuElement(iID).getCurrent() / 100.0f;
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
               for (int j = 0; j < CFG.game.getProvince(i).getCitiesSize(); ++j) {
                  CFG.game.getProvince(i).getCity(j).updateCityNameWidth();
               }
            }
            break;
         }
         case 14: {
            CFG.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME = !CFG.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME;
            Game_Render.updateRenderer_CivNames();
            break;
         }
         case 15: {
            final SettingsManager settingsManager3 = CFG.settingsManager;
            --settingsManager3.FONT_BORDER_SIZE;
            if (CFG.settingsManager.FONT_BORDER_SIZE < 8) {
               CFG.settingsManager.FONT_BORDER_SIZE = 8;
            }
            CFG.loadFontBorder();
            for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
               for (int j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
                  CFG.game.getCiv(i).getCivRegion(j).buildScaleOfText();
               }
            }
            this.updateLanguage();
            break;
         }
         case 17: {
            final SettingsManager settingsManager4 = CFG.settingsManager;
            ++settingsManager4.FONT_BORDER_SIZE;
            if (CFG.settingsManager.FONT_BORDER_SIZE > 256) {
               CFG.settingsManager.FONT_BORDER_SIZE = 256;
            }
            CFG.loadFontBorder();
            for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
               for (int j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
                  CFG.game.getCiv(i).getCivRegion(j).buildScaleOfText();
               }
            }
            this.updateLanguage();
            break;
         }
         case 18: {
            CFG.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT = this.getMenuElement(iID).getCurrent() / 100.0f;
            break;
         }
         case 19: {
            CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.settingsManager.civNamesFontColor.getR(), CFG.settingsManager.civNamesFontColor.getG(), CFG.settingsManager.civNamesFontColor.getB());
            CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.CIV_NAMES_OVER_PROVINCES);
            break;
         }
         case 20: {
            CFG.settingsManager.civNamesFontColor_ALPHA = this.getMenuElement(iID).getCurrent() / 100.0f;
            CFG.loadFontBorder();
            break;
         }
         case 21: {
            CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.settingsManager.civNamesFontColorBorder.getR(), CFG.settingsManager.civNamesFontColorBorder.getG(), CFG.settingsManager.civNamesFontColorBorder.getB());
            CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.CIV_NAMES_OVER_PROVINCES_BORDER);
            break;
         }
         case 22: {
            CFG.settingsManager.civNamesFontColorBorder_ALPHA = this.getMenuElement(iID).getCurrent() / 100.0f;
            CFG.loadFontBorder();
            break;
         }
         case 23: {
            CFG.settingsManager.FONT_BORDER_WIDTH_OF_BORDER = this.getMenuElement(iID).getCurrent();
            CFG.loadFontBorder();
            break;
         }
         case 24: {
            CFG.settingsManager.CIVILIZATIONS_NAMES_INTERVAL = this.getMenuElement(iID).getCurrent();
            break;
         }
         case 26: {
            CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getR(), CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getG(), CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.getB());
            CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.PROVINCE_SETTINGS_WASTELAND_COLOR);
            break;
         }
         case 27: {
            CFG.settingsManager.PROVINCE_ALPHA_WASTELAND = this.getMenuElement(iID).getCurrent() / 255.0f;
            break;
         }
         case 29: {
            CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB());
            CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.PROVINCE_SETTINGS_DISCOVERY_COLOR);
            break;
         }
         case 30: {
            CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA = this.getMenuElement(iID).getCurrent() / 255.0f;
            break;
         }
         case 31: {
            CFG.settingsManager.sMoveLine = CFG.linesManager.loadNext(CFG.linesManager.moveLandTAG, false);
            CFG.linesManager.moveLandTAG = CFG.settingsManager.sMoveLine;
            CFG.linesManager.loadMoveLand();
         }
         case 33: {
            CFG.settingsManager.sMoveLine = CFG.linesManager.loadNext(CFG.linesManager.moveLandTAG, true);
            CFG.linesManager.moveLandTAG = CFG.settingsManager.sMoveLine;
            CFG.linesManager.loadMoveLand();
            break;
         }
         case 34: {
            CFG.settingsManager.sHighlightLine = CFG.linesManager.loadNext(CFG.linesManager.highlightTAG, false);
            CFG.linesManager.highlightTAG = CFG.settingsManager.sHighlightLine;
            CFG.linesManager.loadHighlight();
         }
         case 36: {
            CFG.settingsManager.sHighlightLine = CFG.linesManager.loadNext(CFG.linesManager.highlightTAG, true);
            CFG.linesManager.highlightTAG = CFG.settingsManager.sHighlightLine;
            CFG.linesManager.loadHighlight();
            break;
         }
         case 38: {
            CFG.settingsManager.OCCUPIED_PROVINCE_ALPHA = this.getMenuElement(iID).getCurrent();
            break;
         }
         case 39: {
            CFG.settingsManager.OCCUPIED_STRIPES_SIZE = this.getMenuElement(iID).getCurrent() / 10.0f;
            break;
         }
         case 40: {
            final SettingsManager tempS = new SettingsManager();
            CFG.settingsManager.PROVINCE_ALPHA = tempS.PROVINCE_ALPHA;
            CFG.settingsManager.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME = tempS.DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME;
            CFG.settingsManager.OCCUPIED_PROVINCE_ALPHA = tempS.OCCUPIED_PROVINCE_ALPHA;
            CFG.settingsManager.OCCUPIED_STRIPES_SIZE = tempS.OCCUPIED_STRIPES_SIZE;
            CFG.settingsManager.FONT_ARMY_SIZE = tempS.FONT_ARMY_SIZE;
            AoCGame.updateArmyFontSize();
            CFG.loadFontArmy();
            for (int k = 0; k < CFG.game.getProvincesSize(); ++k) {
               CFG.game.getProvince(k).getArmy_Obj(0).updateArmyWidth(k);
            }
            CFG.settingsManager.PERCETANGE_OF_CITIES_ON_MAP = tempS.PERCETANGE_OF_CITIES_ON_MAP;
            CFG.settingsManager.STOP_SCALING_ARMY = tempS.STOP_SCALING_ARMY;
            for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
               CFG.game_NextTurnUpdate.updateCities(k);
            }
            CFG.settingsManager.updateCitiesFontScale();
            for (int k = 0; k < CFG.game.getProvincesSize(); ++k) {
               for (int l = 0; l < CFG.game.getProvince(k).getCitiesSize(); ++l) {
                  CFG.game.getProvince(k).getCity(l).updateCityNameWidth();
               }
            }
            CFG.settingsManager.FONT_BORDER_WIDTH_OF_BORDER = tempS.FONT_BORDER_WIDTH_OF_BORDER;

            //new border settings
            CFG.settingsManager.PROVINCE_BORDER_SIZE = tempS.PROVINCE_BORDER_SIZE;
            CFG.settingsManager.PROVINCE_BORDER_COLOR.setR(tempS.PROVINCE_BORDER_COLOR.getR());
            CFG.settingsManager.PROVINCE_BORDER_COLOR.setG(tempS.PROVINCE_BORDER_COLOR.getG());
            CFG.settingsManager.PROVINCE_BORDER_COLOR.setB(tempS.PROVINCE_BORDER_COLOR.getB());
            CFG.COLOR_PROVINCE_STRAIGHT.r = CFG.settingsManager.PROVINCE_BORDER_COLOR.getR();
            CFG.COLOR_PROVINCE_STRAIGHT.g = CFG.settingsManager.PROVINCE_BORDER_COLOR.getG();
            CFG.COLOR_PROVINCE_STRAIGHT.b = CFG.settingsManager.PROVINCE_BORDER_COLOR.getB();

            CFG.settingsManager.ENABLE_INNER_BORDERS = tempS.ENABLE_INNER_BORDERS;

            for (int k = 0; k < CFG.game.getProvincesSize(); ++k) {
               CFG.game.getProvince(k).updateProvinceBorder();
            }
            CFG.game.updateActiveProvinceBorderStyle();

            CFG.settingsManager.civNamesFontColor.setR(tempS.civNamesFontColor.getR());
            CFG.settingsManager.civNamesFontColor.setG(tempS.civNamesFontColor.getG());
            CFG.settingsManager.civNamesFontColor.setB(tempS.civNamesFontColor.getB());
            CFG.settingsManager.civNamesFontColor_ALPHA = tempS.civNamesFontColor_ALPHA;
            CFG.settingsManager.civNamesFontColorBorder.setR(tempS.civNamesFontColorBorder.getR());
            CFG.settingsManager.civNamesFontColorBorder.setG(tempS.civNamesFontColorBorder.getG());
            CFG.settingsManager.civNamesFontColorBorder.setB(tempS.civNamesFontColorBorder.getB());
            CFG.settingsManager.civNamesFontColorBorder_ALPHA = tempS.civNamesFontColorBorder_ALPHA;
            CFG.settingsManager.CIV_NAMES_MIN_SCALE_OF_FONT = tempS.CIV_NAMES_MIN_SCALE_OF_FONT;
            CFG.settingsManager.CIVILIZATIONS_NAMES_INTERVAL = tempS.CIVILIZATIONS_NAMES_INTERVAL;
            CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setR(tempS.COLOR_PROVINCE_BG_WASTELAND.getR());
            CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setG(tempS.COLOR_PROVINCE_BG_WASTELAND.getG());
            CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setB(tempS.COLOR_PROVINCE_BG_WASTELAND.getB());
            CFG.settingsManager.PROVINCE_ALPHA_WASTELAND = tempS.PROVINCE_ALPHA_WASTELAND;
            CFG.settingsManager.COLOR_PROVINCE_DISCOVERY = tempS.COLOR_PROVINCE_DISCOVERY;
            CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA = tempS.COLOR_PROVINCE_DISCOVERY_ALPHA;

            CFG.settingsManager.sMoveLine = tempS.sMoveLine;
            CFG.linesManager.loadMoveLand();
            CFG.settingsManager.sHighlightLine = tempS.sHighlightLine;
            CFG.linesManager.loadHighlight();
            CFG.loadFontBorder();
            Game_Render.updateRenderer_CivNames();
            this.updateLanguage();
            break;
         }
      }
      CFG.saveSettings();
   }

   @Override
   protected void onBackPressed() {
      CFG.menuManager.getColorPicker().setVisible(false, null);
      CFG.menuManager.setViewID(Menu.eSETTINGS);
      CFG.menuManager.setBackAnimation(true);
      this.updateArmyWidth();
   }
}
