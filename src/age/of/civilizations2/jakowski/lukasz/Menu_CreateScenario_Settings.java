package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_CreateScenario_Settings extends SliderMenu {
   private String sName;
   private String sAuthor;
   private String sWiki;
   private int iWikiWidth = 0;
   private static Image previewImage = null;

   protected Menu_CreateScenario_Settings() {
      List menuElements = new ArrayList();
      menuElements.add(new Button_Menu("", CFG.PADDING * 2, 0, CFG.PADDING * 3, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected String getTextToDraw() {
            return Menu_CreateScenario_Settings.this.sName + ": " + super.getText();
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(0.981F, 0.981F, 0.981F, 0.65F);
            } else {
               oSB.setColor(0.019F, 0.024F, 0.03F, 0.65F);
            }

            ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getHeight() / 2, this.getHeight(), false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 4, false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + this.getHeight() - this.getHeight() / 4 + iTranslateY, this.getWidth(), this.getHeight() / 4, false, true);
            if (isActive) {
               oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
            } else {
               oSB.setColor(CFG.COLOR_MINIMAP_BORDER);
            }

            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.3F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ScenarioName") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu("", CFG.PADDING * 2, 0, CFG.BUTTON_HEIGHT + CFG.PADDING * 4, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT - CFG.PADDING * 2, true) {
         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(0.981F, 0.981F, 0.981F, 0.65F);
            } else {
               oSB.setColor(0.019F, 0.024F, 0.03F, 0.65F);
            }

            ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getHeight() / 2, this.getHeight(), false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 4, false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + this.getHeight() - this.getHeight() / 4 + iTranslateY, this.getWidth(), this.getHeight() / 4, false, true);
            if (isActive) {
               oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
            } else {
               oSB.setColor(CFG.COLOR_MINIMAP_BORDER);
            }

            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.3F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DateAndAgeOfScenario") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAges.getAge(CFG.CREATE_SCENARIO_AGE).getName()));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Date") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getCurrentDate(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu("", CFG.PADDING * 2, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 3, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT - CFG.PADDING * 2, true) {
         protected String getTextToDraw() {
            return Menu_CreateScenario_Settings.this.sAuthor + ": " + super.getText();
         }

         protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               oSB.setColor(0.981F, 0.981F, 0.981F, 0.65F);
            } else {
               oSB.setColor(0.019F, 0.024F, 0.03F, 0.65F);
            }

            ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getHeight() / 2, this.getHeight(), false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() / 4, false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + this.getHeight() - this.getHeight() / 4 + iTranslateY, this.getWidth(), this.getHeight() / 4, false, true);
            if (isActive) {
               oSB.setColor(0.0F, 0.0F, 0.0F, 1.0F);
            } else {
               oSB.setColor(CFG.COLOR_MINIMAP_BORDER);
            }

            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.3F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AuthorOfScenario") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 5 + CFG.PADDING * 6, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 5 + CFG.PADDING * 7, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 0, 40000, CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals() / 25) {
         protected String getDrawText() {
            return super.getText() + this.getCurrent() * 25;
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_StartingArmyInCapitals(nCurrent * 25);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 5 + CFG.PADDING * 6, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 6 + CFG.PADDING * 7, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 6 + CFG.PADDING * 8, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 1, 10000, CFG.game.getGameScenarios().getScenario_StartingPopulation() / 100) {
         protected String getDrawText() {
            return super.getText() + this.getCurrent() * 100;
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected Color getColorLEFT() {
            return CFG.COLOR_POPULATION[CFG.COLOR_POPULATION.length - 1];
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_StartingPopulation(nCurrent * 100);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 6 + CFG.PADDING * 7, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 7 + CFG.PADDING * 8, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 7 + CFG.PADDING * 9, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 1, 10000, CFG.game.getGameScenarios().getScenario_StartingEconomy() / 100) {
         protected String getDrawText() {
            return super.getText() + this.getCurrent() * 100;
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected Color getColorLEFT() {
            return CFG.COLOR_ECONOMY[CFG.COLOR_ECONOMY.length - 1];
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_StartingEconomy(nCurrent * 100);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 7 + CFG.PADDING * 8, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_Descripted(CFG.langManager.get("ChangeDiplomaticRelationsBetweenCivilizations"), (String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 4 + CFG.PADDING * 5, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeDiplomaticRelationsBetweenCivilizations") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.top_diplomacy_points).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.top_diplomacy_points).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_diplomacy_points).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 4, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.technology).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.technology).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.technology).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 9 + CFG.PADDING * 10, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Transparent(CFG.PADDING, CFG.PADDING * 3, CFG.PADDING, CFG.BUTTON_HEIGHT * 3, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Civilizations") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (CFG.game.getCivsSize() - 1), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu((String)null, -1, 0, CFG.BUTTON_HEIGHT * 17 + CFG.PADDING * 18, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (CFG.palletManager.getActivePalletID() == 0) {
               CFG.palletManager.drawSampleColors_Standard(oSB, this.getPosX() + this.getWidth() / 2 - CFG.BUTTON_WIDTH - CFG.BUTTON_WIDTH / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - this.getHeight() / 4 + Menu_CreateScenario_Settings.this.getMenuPosY(), CFG.BUTTON_WIDTH * 3, this.getHeight() / 2, CFG.palletManager.getActivePalletID(), isActive);
            } else {
               CFG.palletManager.drawSampleColors(oSB, this.getPosX() + this.getWidth() / 2 - CFG.BUTTON_WIDTH - CFG.BUTTON_WIDTH / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - this.getHeight() / 4 + Menu_CreateScenario_Settings.this.getMenuPosY(), CFG.BUTTON_WIDTH * 3, this.getHeight() / 2, CFG.palletManager.getActivePalletID() - 1, isActive);
            }

            CFG.fontMain.getData().setScale(0.8F);
            if (isActive) {
               CFG.drawText(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F) / 2 + iTranslateY, this.getColor(isActive));
            } else {
               CFG.drawTextWithShadow(oSB, this.getTextToDraw(), this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F) / 2 + iTranslateY, this.getColor(isActive));
            }

            CFG.fontMain.getData().setScale(1.0F);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefaultSetOfCivilizationsColorsInAScenario") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 8 + CFG.PADDING * 9, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 8 + CFG.PADDING * 10, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, -20000, 20000, CFG.game.getGameScenarios().getScenario_StartingMoney() / 50) {
         protected String getDrawText() {
            return super.getText() + this.getCurrent() * 50;
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected Color getColorLEFT() {
            return new Color(0.15686275F, 0.50980395F, 0.26666668F, 1.0F);
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_StartingMoney(nCurrent * 50);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 8 + CFG.PADDING * 9, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 14 + CFG.PADDING * 15, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.top_gold).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.top_gold).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.top_gold).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 12 + CFG.PADDING * 13, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 15 + CFG.PADDING * 16, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.happiness).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.happiness).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.happiness).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 13 + CFG.PADDING * 14, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.hre_flag).draw(oSB, this.getPosX() + this.getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.hre_flag).getHeight() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.flag_rect).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 10 + CFG.PADDING * 11, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.time).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.time).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.time).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 11 + CFG.PADDING * 12, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.time).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.time).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.time).getHeight() / 2 + iTranslateY);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * 16 + CFG.PADDING * 17, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            ImageManager.getImage(Images.wikipedia).draw(oSB, this.getPosX() + this.getTextPos() / 2 - ImageManager.getImage(Images.wikipedia).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.wikipedia).getHeight() / 2 + iTranslateY);
            CFG.drawText(oSB, Menu_CreateScenario_Settings.this.sWiki, this.getPosX() + this.getTextPos() + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.TEXT_HEIGHT / 2 + iTranslateY, this.getColor(isActive));
            super.drawText(oSB, Menu_CreateScenario_Settings.this.iWikiWidth + iTranslateX, iTranslateY, isActive);
         }
      });
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 18 + CFG.PADDING * 19, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 18 + CFG.PADDING * 20, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 0, 10000, 100 + (int)(CFG.game.getGameScenarios().getScenario_PopulationGrowthRate_Modifier() * 100.0F)) {
         protected String getDrawText() {
            return super.getText() + (this.getCurrent() - 100 >= 0 ? "+" : "") + (this.getCurrent() - 100) + "%";
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_PopulationGrowthRate_Modifier((float)(nCurrent - 100) / 100.0F);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 18 + CFG.PADDING * 19, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 19 + CFG.PADDING * 20, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 19 + CFG.PADDING * 21, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 0, 10000, 100 + (int)(CFG.game.getGameScenarios().getScenario_EconomyGrowthRate_Modifier() * 100.0F)) {
         protected String getDrawText() {
            return super.getText() + (this.getCurrent() - 100 >= 0 ? "+" : "") + (this.getCurrent() - 100) + "%";
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_EconomyGrowthRate_Modifier((float)(nCurrent - 100) / 100.0F);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 19 + CFG.PADDING * 20, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu("-", -1, 0, CFG.BUTTON_HEIGHT * 20 + CFG.PADDING * 21, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Slider((String)null, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 20 + CFG.PADDING * 22, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT - CFG.PADDING * 2, 15, 200, 100 + (int)(CFG.game.getGameScenarios().getScenario_DiseasesDeathRate_Modifier() * 100.0F)) {
         protected String getDrawText() {
            return super.getText() + (this.getCurrent() - 100 >= 0 ? "+" : "") + (this.getCurrent() - 100) + "%";
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            ImageManager.getImage(Images.btn_menu_1_h).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth());
            super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         }

         protected void setCurrent(int nCurrent) {
            CFG.game.getGameScenarios().setScenario_DiseasesDeathRate_Modifier((float)(nCurrent - 100) / 100.0F);
            super.setCurrent(nCurrent);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT * 20 + CFG.PADDING * 21, CFG.BUTTON_WIDTH, CFG.BUTTON_HEIGHT, true));
      this.initMenu((SliderMenuTitle)null, 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, Menu_InGame_FlagAction.getWindowWidth(), CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT + CFG.PADDING * 2), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.sName = CFG.langManager.get("ScenarioName");
      this.sAuthor = CFG.langManager.get("Author");
      this.sWiki = CFG.langManager.get("Wiki") + ": ";

      try {
         CFG.glyphLayout.setText(CFG.fontMain, this.sWiki);
         this.iWikiWidth = (int)CFG.glyphLayout.width;
      } catch (NullPointerException var2) {
         this.iWikiWidth = 0;
      } catch (IndexOutOfBoundsException var3) {
         this.iWikiWidth = 0;
      }

      this.getMenuElement(0).setText(CFG.CREATE_SCENARIO_NAME);
      this.getMenuElement(1).setText(CFG.gameAges.getAge(CFG.CREATE_SCENARIO_AGE).getName() + " [" + Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth) + " " + CFG.gameAges.getYear(Game_Calendar.currentYear) + "]");
      this.getMenuElement(2).setText(CFG.CREATE_SCENARIO_AUTHOR);
      this.getMenuElement(4).setText(CFG.langManager.get("StartingArmyInCapitals") + ": ");
      this.getMenuElement(7).setText(CFG.langManager.get("StartingPopulation") + ": ");
      this.getMenuElement(10).setText(CFG.langManager.get("StartingEconomy") + ": ");
      this.getMenuElement(12).setText(CFG.langManager.get("ManageDiplomacy"));
      this.getMenuElement(13).setText(CFG.langManager.get("TechnologyLevels"));
      this.getMenuElement(14).setText(CFG.langManager.get("SetUpArmy"));
      this.getMenuElement(16).setText(CFG.langManager.get("PalletCivColors"));
      this.getMenuElement(18).setText(CFG.langManager.get("StartingMoney") + ": ");
      this.getMenuElement(20).setText(CFG.langManager.get("StartingMoney"));
      this.getMenuElement(21).setText(CFG.langManager.get("Cores") + ", " + CFG.langManager.get("Population"));
      this.getMenuElement(22).setText(CFG.langManager.get("Happiness"));
      this.getMenuElement(23).setText(CFG.langManager.get("HolyRomanEmpire"));
      this.getMenuElement(24).setText(CFG.langManager.get("SetEvents"));
      this.getMenuElement(25).setText(CFG.langManager.get("Colonization"));
      this.getMenuElement(26).setText(CFG.CREATE_SCENARIO_WIKI);
      this.getMenuElement(28).setText(CFG.langManager.get("PopulationGrowthModifier") + ": ");
      this.getMenuElement(31).setText(CFG.langManager.get("EconomyGrowthModifier") + ": ");
      this.getMenuElement(34).setText(CFG.langManager.get("DiseasesDeathRate") + ": ");
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(new Color(0.0F, 0.01F, 0.012F, 0.45F));
      ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.01F, 0.012F, 0.32F));
      ImageManager.getImage(Images.patt2).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) - ImageManager.getImage(Images.patt2).getHeight() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.01F, 0.012F, 0.75F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT / 2);
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) + CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2 - CFG.BUTTON_HEIGHT / 2 - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT / 2, false, true);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
      ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) + CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2 - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
      ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) - ImageManager.getImage(Images.pix255_255_255).getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, -CFG.PADDING + this.getMenuPosY() + (this.getMenuElement(0).getPosY() - CFG.PADDING) + CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 2 - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);

      try {
         oSB.setColor(Color.BLACK);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, previewImage.getWidth(), previewImage.getHeight());
         oSB.setColor(Color.WHITE);
         previewImage.draw(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - previewImage.getHeight() + iTranslateY, (int)((float)previewImage.getWidth() * CFG.GUI_SCALE), (int)((float)previewImage.getHeight() * CFG.GUI_SCALE), false, true);
         CFG.map.getMapBG().getMinimap().draw2(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.map.getMapBG().getMinimap().getHeight() + iTranslateY, (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getWidth(), (int)((float)previewImage.getHeight() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getHeight());
         CFG.map.getMapBG().getMinimap().draw2(oSB, this.getPosX() + CFG.PADDING * 2 + (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getWidth() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.map.getMapBG().getMinimap().getHeight() + iTranslateY, CFG.map.getMapBG().getMinimap().getWidth(), (int)((float)previewImage.getHeight() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getHeight(), true);
         CFG.map.getMapBG().getMinimap().draw2(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.map.getMapBG().getMinimap().getHeight() + (int)((float)previewImage.getHeight() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getHeight() + iTranslateY, (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getWidth(), CFG.map.getMapBG().getMinimap().getHeight(), false, true);
         CFG.map.getMapBG().getMinimap().draw2(oSB, this.getPosX() + CFG.PADDING * 2 + (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getWidth() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.map.getMapBG().getMinimap().getHeight() + (int)((float)previewImage.getHeight() * CFG.GUI_SCALE) - CFG.map.getMapBG().getMinimap().getHeight() + iTranslateY, CFG.map.getMapBG().getMinimap().getWidth(), CFG.map.getMapBG().getMinimap().getHeight(), true, true);
         oSB.setColor(CFG.COLOR_MINIMAP_BORDER);
         CFG.drawRect(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - 1 + iTranslateY, (int)((float)previewImage.getWidth() * CFG.GUI_SCALE), (int)((float)previewImage.getHeight() * CFG.GUI_SCALE));
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
         CFG.drawRect(oSB, this.getPosX() + 1 + CFG.PADDING * 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() + iTranslateY, (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) - 2, (int)((float)previewImage.getHeight() * CFG.GUI_SCALE) - 2);
         oSB.setColor(Color.WHITE);
      } catch (NullPointerException var6) {
         previewImage = CFG.map.getMapBG().getScenarioMinimapPreviewTexture(oSB);
         oSB.setColor(Color.BLACK);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, 0, -ImageManager.getImage(Images.pix255_255_255).getHeight(), previewImage.getWidth(), previewImage.getHeight());
         oSB.setColor(Color.WHITE);
         this.getMenuElement(0).setPosX(CFG.PADDING * 2 + (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) + 1);
         this.getMenuElement(0).setWidth(Menu_InGame_FlagAction.getWindowWidth() - this.getMenuElement(0).getPosX());
         this.getMenuElement(1).setPosX(CFG.PADDING * 2 + (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) + 1);
         this.getMenuElement(1).setWidth(Menu_InGame_FlagAction.getWindowWidth() - this.getMenuElement(1).getPosX());
         this.getMenuElement(2).setPosX(CFG.PADDING * 2 + (int)((float)previewImage.getWidth() * CFG.GUI_SCALE) + 1);
         this.getMenuElement(2).setWidth(Menu_InGame_FlagAction.getWindowWidth() - this.getMenuElement(2).getPosX());
         this.getMenuElement(15).setPosX(CFG.PADDING * 2);
         this.getMenuElement(15).setHeight(previewImage.getHeight());
         this.getMenuElement(15).setWidth(previewImage.getWidth());
      }

      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected static final void disposePreview() {
      if (previewImage != null) {
         previewImage.getTexture().dispose();
         previewImage = null;
      }

   }

   private final void updateStartingArmyInCapitals(int nArmyBefore) {
      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
         if (CFG.game.getCiv(i).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getCivID() == i && CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getArmy(0) == nArmyBefore) {
            CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).updateArmy(CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals());
         }
      }

   }

   protected final void actionElement(int iID) {
      int i;
      switch (iID) {
         case 0:
            CFG.showKeyboard();
            return;
         case 1:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.backToMenu = Menu.eCREATE_SCENARIO_SETTINGS;
            CFG.menuManager.setViewID(Menu.eSCENARIO_AGE);
            CFG.menuManager.updateSelecetScenarioAge_Slider();
            return;
         case 2:
            CFG.showKeyboard();
            return;
         case 3:
            int nBefore = CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals();
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            this.updateStartingArmyInCapitals(nBefore);
            return;
         case 4:
            int nBefore2 = CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals();
            CFG.game.getGameScenarios().setScenario_StartingArmyInCapitals(this.getMenuElement(iID).getCurrent() * 25);
            this.updateStartingArmyInCapitals(nBefore2);
            return;
         case 5:
            int nBefore3 = CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals();
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            this.updateStartingArmyInCapitals(nBefore3);
            return;
         case 6:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 7:
            CFG.game.getGameScenarios().setScenario_StartingPopulation(this.getMenuElement(iID).getCurrent() * 100);
            return;
         case 8:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         case 9:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 10:
            CFG.game.getGameScenarios().setScenario_StartingEconomy(this.getMenuElement(iID).getCurrent() * 100);
            return;
         case 11:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         case 12:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.game.setActiveProvinceID(-1);
            CFG.menuManager.rebuildManageDiplomacy_Alliances();
            CFG.chosen_AlphabetCharachter = null;
            CFG.resetManageDiplomacyIDs();
            CFG.backToMenu = Menu.eCREATE_SCENARIO_SETTINGS;
            CFG.menuManager.setViewID(Menu.eMANAGE_DIPLOMACY);
            Game_Render_Province.updateDrawProvinces();
            CFG.map.getMapTouchManager().update_ExtraAction();
            return;
         case 13:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.iCreateScenario_AssignProvinces_Civ = 0;
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_TECHNOLOGY_LEVELS);
            CFG.VIEW_SHOW_VALUES = true;

            for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
               if (CFG.game.getProvince(i).getIsCapital()) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTechnologyLevel());
               }
            }

            Game_Render_Province.updateDrawProvinces();
            return;
         case 14:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.game.checkArmies();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.getSelectedProvinces().clearSelectedProvinces();

            try {
               CFG.menuManager.setVisible_CreateScenario_SetUpArmies_Neutral(false);
               CFG.menuManager.setVisible_CreateScenario_SetUpArmies_Civs(false);
               CFG.menuManager.setVisible_CreateScenario_SetUpArmies_Sliders(false);
            } catch (IndexOutOfBoundsException var6) {
            }

            CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = 100000;
            CFG.game.sortCivilizationsAZ();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_SET_UP_ARMY);
            return;
         case 15:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_PREVIEW);
            return;
         case 16:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_PALLET_OF_COLORS);
            return;
         case 17:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 18:
            CFG.game.getGameScenarios().setScenario_StartingMoney(this.getMenuElement(iID).getCurrent() * 50);
            return;
         case 19:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         case 20:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.iCreateScenario_AssignProvinces_Civ = 0;
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_STARTING_MONEY);
            CFG.VIEW_SHOW_VALUES = true;

            for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
               if (CFG.game.getProvince(i).getIsCapital()) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth((int)(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getMoney() == -999999L ? (long)CFG.game.getGameScenarios().getScenario_StartingMoney() : CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getMoney()));
               }
            }

            Game_Render_Province.updateDrawProvinces();
            return;
         case 21:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.getSelectedProvinces().clearSelectedProvinces();
            CFG.selectMode = true;
            CFG.brushTool = false;
            CFG.VIEW_SHOW_VALUES = true;
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_CORES);
            return;
         case 22:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_HAPPINESS);

            for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
               if (CFG.game.getProvince(i).getIsCapital()) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getHappiness() + "%");
               }
            }

            Game_Render_Province.updateDrawProvinces();
            return;
         case 23:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.getSelectedProvinces().clearSelectedProvinces();
            CFG.selectMode = true;
            CFG.brushTool = false;
            CFG.VIEW_SHOW_VALUES = true;

            for(i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getProvincesSize(); ++i) {
               CFG.game.getSelectedProvinces().addProvince(CFG.holyRomanEmpire_Manager.getHRE().getProvinces(i));
            }

            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_HOLY_ROMAN_EMPIRE);
            CFG.toast.setInView(this.getMenuElement(iID).getText(), HolyRomanEmpire_Manager.oColorHRE);
            return;
         case 24:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.eventsManager.lCreateScenario_Event = new Event_GameData();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS);
            return;
         case 25:
            CFG.menuManager.saveCreateScenarioSettings_Data();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_COLONIZATION);
            return;
         case 26:
            CFG.showKeyboard();
            return;
         case 27:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 28:
            CFG.game.getGameScenarios().setScenario_PopulationGrowthRate_Modifier((float)(this.getMenuElement(iID).getCurrent() - 100) / 100.0F);
            return;
         case 29:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         case 30:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 31:
            CFG.game.getGameScenarios().setScenario_EconomyGrowthRate_Modifier((float)(this.getMenuElement(iID).getCurrent() - 100) / 100.0F);
            return;
         case 32:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         case 33:
            this.getMenuElement(iID + 1).setCurrent(this.getMenuElement(iID + 1).getCurrent() - 1);
            return;
         case 34:
            CFG.game.getGameScenarios().setScenario_DiseasesDeathRate_Modifier((float)(this.getMenuElement(iID).getCurrent() - 100) / 100.0F);
            return;
         case 35:
            this.getMenuElement(iID - 1).setCurrent(this.getMenuElement(iID - 1).getCurrent() + 1);
            return;
         default:
      }
   }
}
