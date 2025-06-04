package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_CreateNewGame_Options_v2 extends SliderMenu {
   protected static final int ANIMATION_TIME = 175;
   protected static long lTime = 0L;
   protected static boolean hideAnimation = true;

   protected Menu_CreateNewGame_Options_v2() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH;
      int tempMaxH = CFG.GAME_HEIGHT - (ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4) - (CFG.BUTTON_HEIGHT + CFG.PADDING * 2) - CFG.PADDING;
      int tempElemH = CFG.BUTTON_HEIGHT * 3 / 4;
      List menuElements = new ArrayList();
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2 + CFG.map.getIcon(CFG.map.getActiveMapID()).getWidth(), 0, 0, tempW, tempElemH, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            oSB.setColor(Color.WHITE);
            CFG.map.getIcon(CFG.map.getActiveMapID()).draw(oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + Menu_CreateNewGame_Options_v2.this.getMenuPosY() + this.getHeight() / 2 - CFG.map.getIcon(CFG.map.getActiveMapID()).getHeight() / 2);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.map.getMapName_Just(CFG.map.getActiveMapID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Provinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LandProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countLandProvinces(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SeaProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countSeaProvinces(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AverageGrowthRateOfProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countAvarageGrowthRate() + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Slider_BG_CNG("", CFG.PADDING * 2, tempElemH + CFG.PADDING, tempW - CFG.PADDING * 4, tempElemH - CFG.PADDING * 2, 0, 25, 0) {
         protected String getDrawText() {
            return this.getText() + ": " + (int)((1.0F + (float)this.getCurrent() * 0.1F) * 100.0F) + "%";
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefaultScaleOfMap") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 2, tempW, tempElemH, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);

            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
               if (CFG.game.getPlayer(i).getCivID() > 0) {
                  CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getFlag().draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getFlag().getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
                  ImageManager.getImage(Images.flag_rect).draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
                  oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getB() / 255.0F, 1.0F));
                  ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY - 2 - (int)((float)CFG.CIV_COLOR_WIDTH * CFG.GUI_SCALE), CFG.CIV_FLAG_WIDTH, (int)((float)CFG.CIV_COLOR_WIDTH * CFG.GUI_SCALE));
               } else {
                  ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
                  ImageManager.getImage(Images.flag_rect).draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
                  oSB.setColor(CFG.RANDOM_CIVILIZATION_COLOR);
                  ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getTextPos() + (int)((float)this.getTextWidth() * 0.8F) + CFG.PADDING + CFG.CIV_FLAG_WIDTH * i + CFG.PADDING * i + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY - 2 - (int)((float)CFG.CIV_COLOR_WIDTH * CFG.GUI_SCALE), CFG.CIV_FLAG_WIDTH, (int)((float)CFG.CIV_COLOR_WIDTH * CFG.GUI_SCALE));
               }

               oSB.setColor(Color.WHITE);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Players") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getPlayersSize()));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();

            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(i).getCivID()));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getPlayer(i).getCivID() > 0 ? CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCivName() : CFG.langManager.get("RandomCivilization")));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 3, tempW, tempElemH, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(CFG.game.getGameScenarios().getScenarioName(CFG.game.getScenarioID())), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getCurrentDate(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAges.getAge(CFG.game.getGameScenarios().getScenarioAge(CFG.game.getScenarioID())).getName()));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Civilizations") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getGameScenarios().getNumOfCivs(CFG.game.getScenarioID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Author") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getGameScenarios().getScenarioAuthor(CFG.game.getScenarioID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 4, tempW, tempElemH, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("VictoryConditions") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Domination")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ControlProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + VicotryManager.VICTORY_CONTROL_PROVINCES_PERC + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnsLimit") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (VicotryManager.VICTORY_LIMIT_OF_TURNS == 0 ? CFG.langManager.get("NoThanks") : CFG.langManager.get("TurnsX", VicotryManager.VICTORY_LIMIT_OF_TURNS)), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (VicotryManager.VICTORY_TECHNOLOGY == 0.0F ? CFG.langManager.get("Disabled") : (float)((int)(VicotryManager.VICTORY_TECHNOLOGY * 100.0F)) / 100.0F), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Slider_BG_CNG("", CFG.PADDING * 2, tempElemH * 5 + CFG.PADDING, tempW - CFG.PADDING * 4, tempElemH - CFG.PADDING * 2, 0, 9, CFG.DIFFICULTY * 2 + 1) {
         protected String getDrawText() {
            return this.getText();
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DifficultyLevel") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getDifficultyName(CFG.DIFFICULTY)));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Beginner")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Normal")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Hard")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Extreme")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Legendary")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Slider_BG_CNG("", CFG.PADDING * 2, tempElemH * 6 + CFG.PADDING, tempW - CFG.PADDING * 4, tempElemH - CFG.PADDING * 2, 0, 5, CFG.FOG_OF_WAR * 2 + 1) {
         protected String getDrawText() {
            return this.getText();
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Fogofwar") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getFogOfWarName(CFG.FOG_OF_WAR)));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Off") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheWholeMapAndSoldiersAreVisibleAtAllTimes") + "."));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Classic") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvinceOwnershipIsKnownButSoldiersCanOnlyBeSeenInAdjacentProvinces") + "."));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Discovery") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheWorldIsCoveredByFogCivilizationsMustBeDiscoveredBeforeTheyCanBeInteractedWith") + "."));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 9, tempW, tempElemH, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeDiplomaticRelationsBetweenCivilizations") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 10, tempW, tempElemH, true, CFG.FILL_THE_MAP) {
         protected boolean getCheckboxState() {
            return CFG.FILL_THE_MAP;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IfDisabledAllCivilizationsStartWithOnlyTheirCapitalProvince") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 12, tempW, tempElemH, true, CFG.RANDOM_PLACMENT) {
         protected boolean getCheckboxState() {
            return CFG.RANDOM_PLACMENT;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PlacesCapitalsInRandomProvinces") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 13, tempW, tempElemH, true, CFG.RANDOM_FILL) {
         protected boolean getCheckboxState() {
            return CFG.RANDOM_FILL;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RandomnlyFillsTheWorldWithDifferentCivilizations") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 14, tempW, tempElemH, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SwapCivilizationsToRandomPlaces") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 15, tempW, tempElemH, true, CFG.SANDBOX_MODE) {
         protected boolean getCheckboxState() {
            return CFG.SANDBOX_MODE;
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 19, tempW, tempElemH, true, CFG.TOTAL_WAR_MODE) {
         protected boolean getCheckboxState() {
            return CFG.TOTAL_WAR_MODE;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NoneoftheseCivilizationshasthewordforPeaceintheirlanguage") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2("", CFG.PADDING * 2, 0, tempElemH * 11, tempW, tempElemH, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (CFG.palletManager.getActivePalletID() == 0) {
               CFG.palletManager.drawSampleColors_Standard(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + CFG.PADDING * 2 + iTranslateY, this.getWidth() - CFG.PADDING * 4, this.getHeight() - CFG.PADDING * 4, 0, isActive);
            } else {
               CFG.palletManager.drawSampleColors(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + CFG.PADDING * 2 + iTranslateY, this.getWidth() - CFG.PADDING * 4, this.getHeight() - CFG.PADDING * 4, CFG.palletManager.getActivePalletID() - 1, isActive);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SetsOfTheColorsForCivilizations") + ".", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 16, tempW, tempElemH, true, CFG.SPECTATOR_MODE) {
         protected boolean getCheckboxState() {
            return CFG.SPECTATOR_MODE;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ESSMToolTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 17, tempW, tempElemH, true, Game_Calendar.ENABLE_COLONIZATION) {
         protected boolean getCheckboxState() {
            return Game_Calendar.ENABLE_COLONIZATION;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Enable") + "/" + CFG.langManager.get("Disable") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.langManager.get("ColonizationofWastelandProvinces") + "."));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_CNG_Options((String)null, CFG.PADDING * 2, 0, tempElemH * 18, tempW, tempElemH, true, Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES) {
         protected boolean getCheckboxState() {
            return Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Enable") + "/" + CFG.langManager.get("Disable") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ColonizationofNeutralProvinces") + "."));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Slider_BG_CNG("", CFG.PADDING * 2, tempElemH * 7 + CFG.PADDING, tempW - CFG.PADDING * 4, tempElemH - CFG.PADDING * 2, (int)(Game_Calendar.GAME_SPEED_MIN * 10.0F), (int)(Game_Calendar.GAME_SPEED_MAX * 10.0F), (int)(Game_Calendar.GAME_SPEED * 10.0F)) {
         protected String getDrawText() {
            return this.getText() + this.getCurrent() * 10 + "%";
         }
      });
      menuElements.add(new Slider_BG_CNG("", CFG.PADDING * 2, tempElemH * 8 + CFG.PADDING, tempW - CFG.PADDING * 4, tempElemH - CFG.PADDING * 2, 0, 400, (int)(Game_Calendar.AI_AGGRESSIVNESS * 100.0F)) {
         protected String getDrawText() {
            return this.getText() + this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, 0.65F);
         }
      });
      //player-led peace toggle change//
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 20, tempW, tempElemH, true, CFG.PLAYER_PEACE) {
         protected boolean getCheckboxState() {
            return CFG.PLAYER_PEACE;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PeaceToolTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      //dynamic events toggle change//
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 21, tempW, tempElemH, true, CFG.DYNAMIC_EVENTS) {
         protected boolean getCheckboxState() {
            return CFG.DYNAMIC_EVENTS;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DynamicEventsTooltip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      //vassalization toggle change//
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 22, tempW, tempElemH, true, CFG.AI_VASSALS) {
         protected boolean getCheckboxState() {
            return CFG.AI_VASSALS;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AIVassalToolTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      //ai diplomacy toggle change//
      menuElements.add(new Button_CNG_Options2((String)null, CFG.PADDING * 2, 0, tempElemH * 23, tempW, tempElemH, true, CFG.AI_DIPLOMACY) {
         protected boolean getCheckboxState() {
            return CFG.AI_DIPLOMACY;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AIDiplomacyToolTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_CreateNewGame_Options_v2.this.getPosX() + iTranslateX, Menu_CreateNewGame_Options_v2.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_CreateNewGame_Options_v2.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color(0.011F, 0.014F, 0.019F, 0.25F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_CreateNewGame_Options_v2.this.getPosX() + iTranslateX, Menu_CreateNewGame_Options_v2.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_CreateNewGame_Options_v2.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_CreateNewGame_Options_v2.this.getPosX() + iTranslateX, Menu_CreateNewGame_Options_v2.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_CreateNewGame_Options_v2.this.getWidth());
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_CreateNewGame_Options_v2.this.getPosX() + iTranslateX, Menu_CreateNewGame_Options_v2.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_CreateNewGame_Options_v2.this.getWidth(), 1);
            if (AoCGame.LEFT != 0) {
               oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
               ImageManager.getImage(Images.pix255_255_255).draw2(oSB, Menu_CreateNewGame_Options_v2.this.getPosX() + iTranslateX, Menu_CreateNewGame_Options_v2.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - this.getHeight(), 1, this.getHeight(), true, false);
               oSB.setColor(Color.WHITE);
            }

            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.8F / 2.0F), CFG.COLOR_TEXT_OPTIONS_LEFT_NS);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0 + AoCGame.LEFT, ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4, tempW, tempMaxH < tempElemH * menuElements.size() ? tempMaxH : tempElemH * menuElements.size(), menuElements);
      this.setVisible(false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getTitle().setText(CFG.langManager.get("Options"));
      this.getMenuElement(0).setText(CFG.langManager.get("MapType") + ": " + CFG.map.getMapName(CFG.map.getActiveMapID()));
      this.getMenuElement(1).setText(CFG.langManager.get("ScaleOfMap"));
      this.getMenuElement(2).setText(CFG.langManager.get("Players") + ":");
      this.getMenuElement(3).setText(CFG.langManager.get(CFG.game.getGameScenarios().getScenarioName(CFG.game.getScenarioID())) + " | " + CFG.game.getGameScenarios().getNumOfCivs(CFG.game.getScenarioID()) + " " + CFG.langManager.get("Civilizations"));
      this.getMenuElement(4).setText(CFG.langManager.get("VictoryConditions") + ": " + CFG.langManager.get("Domination"));
      this.getMenuElement(5).setText(CFG.langManager.get("Difficulty") + ": " + CFG.getDifficultyName(CFG.DIFFICULTY));
      this.getMenuElement(6).setText(CFG.langManager.get("Fogofwar") + ": " + CFG.getFogOfWarName(CFG.FOG_OF_WAR));
      this.getMenuElement(7).setText(CFG.langManager.get("ManageDiplomacy"));
      this.getMenuElement(8).setText(CFG.langManager.get("FillTheMap"));
      this.getMenuElement(9).setText(CFG.langManager.get("RandomPlacement"));
      this.getMenuElement(10).setText(CFG.langManager.get("RandomFill"));
      this.getMenuElement(11).setText(CFG.langManager.get("ShuffleCivilizations"));
      this.getMenuElement(12).setText(CFG.langManager.get("SandboxMode"));
      this.getMenuElement(13).setText(CFG.langManager.get("EternalWar"));
      this.getMenuElement(15).setText(CFG.langManager.get("ESSM"));
      this.getMenuElement(16).setText(CFG.langManager.get("ColonizationofWastelandProvinces"));
      this.getMenuElement(17).setText(CFG.langManager.get("NeutralProvinces") + ": " + (Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES ? CFG.langManager.get("Colonization") : CFG.langManager.get("Conquering")));
      this.getMenuElement(18).setText(CFG.langManager.get("GameSpeed") + ": ");
      this.getMenuElement(19).setText(CFG.langManager.get("AIAggressiveness") + ": ");
      this.getMenuElement(20).setText(CFG.langManager.get("PlayerPeace"));
      this.getMenuElement(21).setText(CFG.langManager.get("DynamicEvents"));
      this.getMenuElement(22).setText(CFG.langManager.get("AIVassalization"));
      this.getMenuElement(23).setText(CFG.langManager.get("AIDiplomacy"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (lTime + 175L >= System.currentTimeMillis()) {
         if (hideAnimation) {
            iTranslateX -= (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         } else {
            iTranslateX += -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), true, true);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
      if (AoCGame.LEFT != 0) {
         oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight(), true, true);
         oSB.setColor(Color.WHITE);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if ((sliderMenuIsActive || this.getScrollModeY()) && !CFG.menuManager.getSliderMode()) {
         super.drawScrollPos(oSB, iTranslateX - 2, iTranslateY, sliderMenuIsActive);
      }

   }

   protected static final void clickFillTheMap() {
      CFG.viewsManager.disableAllViews();
      CFG.FILL_THE_MAP = !CFG.FILL_THE_MAP;
      CFG.game.disableDrawCivlizationsRegions_Players();
      if (CFG.FILL_THE_MAP) {
         CFG.game.getGameScenarios().enableFillTheMap();
         CFG.game.setActiveProvinceID(CFG.game.getActiveProvinceID());
      } else {
         CFG.game.getGameScenarios().disableFillTheMap();

         try {
            if (CFG.getActiveCivInfo() > 0) {
               CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());
            } else {
               CFG.game.setActiveProvinceID(CFG.game.getActiveProvinceID());
            }
         } catch (IndexOutOfBoundsException var1) {
         }
      }

      CFG.game.enableDrawCivlizationsRegions_Players();
      CFG.setActiveCivInfo(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
      CFG.updateActiveCivInfo_CreateNewGame();
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.backToMenu = Menu.eCREATE_NEW_GAME;
            CFG.menuManager.setViewID(Menu.eSELECT_MAP_TYPE);
            break;
         case 1:
            Map_Scale.STANDARD_SCALE = 1.0F + (float)this.getMenuElement(iID).getCurrent() * 0.1F;
            CFG.map.getMapScale().setCurrentScale(Map_Scale.STANDARD_SCALE);
            CFG.map.getMapScale().setScaleBeforeReset(Map_Scale.STANDARD_SCALE >= 3.0F ? 2.0F : (Map_Scale.STANDARD_SCALE > 1.0F ? 1.0F : 0.5F));
            break;
         case 2:
            CFG.menuManager.setViewID(Menu.eNEWGAME_PLAYERS);
            break;
         case 3:
            CFG.menuManager.setVisible_CreateNewGame_Options_Scenarios(true);
            break;
         case 4:
            CFG.backToMenu = Menu.eCREATE_NEW_GAME;
            CFG.menuManager.setViewID(Menu.eVICTORY_CONDITIONS);
            break;
         case 5:
            if (CFG.DIFFICULTY != this.getMenuElement(iID).getCurrent() / 2) {
               CFG.DIFFICULTY = this.getMenuElement(iID).getCurrent() / 2;
               this.getMenuElement(iID).setText(CFG.langManager.get("Difficulty") + ": " + CFG.getDifficultyName(CFG.DIFFICULTY));
            }
            break;
         case 6:
            if (CFG.FOG_OF_WAR != this.getMenuElement(iID).getCurrent() / 2) {
               CFG.FOG_OF_WAR = this.getMenuElement(iID).getCurrent() / 2;
               this.getMenuElement(iID).setText(CFG.langManager.get("Fogofwar") + ": " + CFG.getFogOfWarName(CFG.FOG_OF_WAR));
            }
            break;
         case 7:
            CFG.game.setActiveProvinceID(-1);
            CFG.menuManager.rebuildManageDiplomacy_Alliances();
            CFG.game.disableDrawCivlizationsRegions_Players();
            CFG.chosen_AlphabetCharachter = null;
            CFG.resetManageDiplomacyIDs();
            CFG.backToMenu = Menu.eCREATE_NEW_GAME;
            CFG.menuManager.setViewID(Menu.eMANAGE_DIPLOMACY);
            Game_Render_Province.updateDrawProvinces();
            CFG.map.getMapTouchManager().update_ExtraAction();
            break;
         case 8:
            clickFillTheMap();
            break;
         case 9:
            CFG.RANDOM_PLACMENT = !CFG.RANDOM_PLACMENT;
            this.getMenuElement(iID).setCheckboxState(CFG.RANDOM_PLACMENT);
            break;
         case 10:
            CFG.RANDOM_FILL = !CFG.RANDOM_FILL;
            this.getMenuElement(iID).setCheckboxState(CFG.RANDOM_FILL);
            break;
         case 11:
            CFG.setDialogType(Dialog.SHUFFLE_CIVILIZATIONS);
            break;
         case 12:
            CFG.SANDBOX_MODE = !CFG.SANDBOX_MODE;
            this.getMenuElement(iID).setCheckboxState(CFG.SANDBOX_MODE);
            break;
         case 13:
            CFG.TOTAL_WAR_MODE = !CFG.TOTAL_WAR_MODE;
            this.getMenuElement(iID).setCheckboxState(CFG.TOTAL_WAR_MODE);
            if (CFG.TOTAL_WAR_MODE) {
               CFG.toast.setInView(CFG.langManager.get("TotalWar") + " - " + CFG.langManager.get("Enabled"));
            } else {
               CFG.toast.setInView(CFG.langManager.get("TotalWar") + " - " + CFG.langManager.get("Disabled"));
            }
            break;
         case 14:
            CFG.menuManager.setVisible_CreateNewGame_Options_Pallets(true);
            break;
         case 15:
            CFG.SPECTATOR_MODE = !CFG.SPECTATOR_MODE;
            this.getMenuElement(iID).setCheckboxState(CFG.SPECTATOR_MODE);
            break;
         case 16:
            Game_Calendar.ENABLE_COLONIZATION = !Game_Calendar.ENABLE_COLONIZATION;
            if (Game_Calendar.ENABLE_COLONIZATION) {
               CFG.toast.setInView(CFG.langManager.get("Colonization") + " - " + CFG.langManager.get("Enabled"));
            } else {
               CFG.toast.setInView(CFG.langManager.get("Colonization") + " - " + CFG.langManager.get("Disabled"));
            }
            break;
         case 17:
            Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = !Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES;
            this.updateLanguage();
            CFG.toast.setInView(this.getMenuElement(iID).getText());
            break;
         case 18:
            Game_Calendar.GAME_SPEED = (float)this.getMenuElement(iID).getCurrent() / 10.0F;
            break;
         case 19:
            Game_Calendar.AI_AGGRESSIVNESS = (float)this.getMenuElement(iID).getCurrent() / 100.0F;
            break;
         case 20:
            CFG.PLAYER_PEACE = !CFG.PLAYER_PEACE;
            this.getMenuElement(iID).setCheckboxState(CFG.PLAYER_PEACE);
            break;
         case 21:
            CFG.DYNAMIC_EVENTS = !CFG.DYNAMIC_EVENTS;
            this.getMenuElement(iID).setCheckboxState(CFG.DYNAMIC_EVENTS);
            break;
         case 22:
            CFG.AI_VASSALS = !CFG.AI_VASSALS;
            this.getMenuElement(iID).setCheckboxState(CFG.AI_VASSALS);
            break;
         case 23:
            CFG.AI_DIPLOMACY = !CFG.AI_DIPLOMACY;
            this.getMenuElement(iID).setCheckboxState(CFG.AI_DIPLOMACY);
            break;
      }

   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
         this.setHideAnimation(false);
      } else {
         this.setHideAnimation(true);
      }

   }

   protected final void setHideAnimation(boolean nHideAnimation) {
      if (nHideAnimation != hideAnimation) {
         if (lTime > System.currentTimeMillis() - 175L) {
            lTime = System.currentTimeMillis() - (175L - (System.currentTimeMillis() - lTime));
         } else {
            lTime = System.currentTimeMillis();
         }

         CFG.setRender_3(true);
      }

      hideAnimation = nHideAnimation;
   }
}
