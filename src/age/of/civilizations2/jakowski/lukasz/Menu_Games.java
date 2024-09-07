package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Menu_Games extends SliderMenu {
   protected Menu_Games() {
      List menuElements = new ArrayList();
      int tempMenuWidth = Menu_Games_Title.getMenuWidth();
      int tY = 0;
      menuElements.add(new Text((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT * 3 / 4) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            CFG.drawRect_InfoBox_Right_Title(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawTextWithShadow(oSB, this.getText(), this.getPosX() + this.getWidth() / 2 - (int)((float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F) / 2 + iTranslateY, CFG.COLOR_TEXT_CIV_INFO_TITLE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_Descripted(CFG.map.getMapAuthor(CFG.map.getActiveMapID()), CFG.langManager.get("MapType") + ": " + CFG.map.getMapName(CFG.map.getActiveMapID()), (int)(50.0F * CFG.GUI_SCALE), 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true) {
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
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true) {
         protected boolean getClickable() {
            return SaveManager.gameCanBeContinued;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale_Important((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true) {
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
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true) {
         protected void buildElementHover() {
            try {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RandomGame"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.dice, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } catch (IndexOutOfBoundsException var3) {
               this.menuElementHover = null;
            }

         }

         protected int getSFX() {
            return SoundsManager.SOUND_RANDOM;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale_Important((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true) {
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, false));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, false));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_MainMenu_TextScale((String)null, -1, 0, tY, tempMenuWidth, CFG.BUTTON_HEIGHT, false));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu((SliderMenuTitle)null, CFG.GAME_WIDTH - tempMenuWidth, 0, tempMenuWidth, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, menuElements);
      this.updateLanguage();
      CFG.lCreateScenario_UndoAssignProvincesCivID = new ArrayList();
      CFG.lCreateScenario_UndoWastelandProvinces = new ArrayList();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Games"));
      this.getMenuElement(2).setText(CFG.langManager.get("LoadGame"));
      this.getMenuElement(3).setText(CFG.langManager.get("ContinueGame"));
      this.getMenuElement(4).setText(CFG.langManager.get("NewGame"));
      this.getMenuElement(5).setText(CFG.langManager.get("AgeofCivilizations"));
      this.getMenuElement(6).setText(CFG.langManager.get("Tutorial"));
      this.getMenuElement(7).setText(CFG.langManager.get("Achievements"));
      this.getMenuElement(8).setText(CFG.langManager.get("HallofFame"));
      this.getMenuElement(9).setText(CFG.langManager.get("Leaderboards"));
      this.getMenuElement(10).setText(CFG.langManager.get("Statistics"));
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      CFG.map.getIcon(CFG.map.getActiveMapID()).draw(oSB, this.getPosX() + this.getMenuElement(1).getTextPos() / 2 - CFG.map.getIcon(CFG.map.getActiveMapID()).getWidth() / 2 + iTranslateX, this.getMenuElement(1).getPosY() + this.getMenuElement(1).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + this.getMenuPosY() - CFG.map.getIcon(CFG.map.getActiveMapID()).getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      CFG.setRender_3(true);
      if ((Map.GAME_CRASHED_LOADED_MIN_SCALE || CFG.map.getMapBG().getMapScale() <= 1) && CFG.map.getMapBG().getMapScale() == 1 && !CFG.toast.getInView()) {
         List nMess = new ArrayList();
         List nCol = new ArrayList();
         nMess.add("Game crashed while loading");
         nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         nMess.add("-- Loaded minimum scale of map --");
         nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         nMess.add("Go to: Map: XX -> Earth: -> Scale X5");
         nCol.add(Color.WHITE);
         CFG.toast.setInView((List)nMess, (List)nCol);
         CFG.toast.setTimeInView(6000);
      }

   }

   protected static final void clickNewGame() {
      if (CFG.SPECTATOR_MODE) {
         CFG.SPECTATOR_MODE = false;
         CFG.FOG_OF_WAR = 2;
      }

      CFG.SANDBOX_MODE = false;
      CFG.RANDOM_PLACMENT = false;
      CFG.RANDOM_FILL = false;
      CFG.DYNAMIC_EVENTS = true; //auto set CFG DYNAMIC EVENTS on start change//
      CFG.PLAYER_PEACE = false;
      Game_Calendar.GAME_SPEED = 1.0F;
      Game_Calendar.AI_AGGRESSIVNESS = 1.25F;
      CFG.menuManager.setViewID(Menu.eCREATE_NEW_GAME);
      CFG.menuManager.setVisible_CreateNewGame_Options(false);
      CFG.menuManager.setVisible_CreateNewGame_CivInfo(true);
   }

   protected static final void clickRandomGame() {
      CFG.randomGameManager = new RandomGame_Manager();
      Game_Calendar.GAME_SPEED = 1.0F;
      CFG.RANDOM_PLACMENT = false;
      CFG.RANDOM_FILL = false;
      CFG.TOTAL_WAR_MODE = false;
      Game_Calendar.currentYear = -4000 - CFG.oR.nextInt(1000);
      Game_Calendar.currentDay = 15;
      Game_Calendar.currentMonth = 5;
      Game_Calendar.CURRENT_AGEID = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
      Game_Calendar.ENABLE_COLONIZATION = false;
      Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = false;
      CFG.FOG_OF_WAR = 2;

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!CFG.game.getProvince(i).getSeaProvince()) {
            CFG.game.getProvince(i).setWasteland(-1);
         }
      }

      Random oR = new Random();

      try {
         CFG.randomGameManager.setCivilizationsSize(Math.min((int)Math.max((float)Menu_RandomGame_Settings.getCivMax() * 0.1F, 2.0F) + oR.nextInt((int)((float)Menu_RandomGame_Settings.getCivMax() * 0.1F)), Menu_RandomGame_Settings.getCivMax()));
      } catch (IllegalArgumentException var2) {
         CFG.randomGameManager.setCivilizationsSize(3);
      }

      CFG.game.getGameScenarios().setScenario_StartingArmyInCapitals(75);
      CFG.randomGameManager.setNeutralArmy(30);
      CFG.game.getGameScenarios().setScenario_StartingPopulation(21000);
      CFG.game.getGameScenarios().setScenario_StartingEconomy(9750);
      CFG.game.getGameScenarios().setScenario_StartingMoney(50);
      CFG.menuManager.setViewID(Menu.eCREATE_RANDOM_GAME);
      CFG.menuManager.rebuildCreateRandomGame_Settings();
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 1:
            CFG.backToMenu = Menu.eGAMES;
            CFG.menuManager.setViewID(Menu.eSELECT_MAP_TYPE);
            break;
         case 2:
            CFG.menuManager.setViewID(Menu.eLOADGAME);
            CFG.menuManager.setOrderOfMenu_LoadGame();
            break;
         case 3:
            CFG.menuManager.setViewID(Menu.eINGAME);
            CFG.menuManager.setVisible_InGame_Options(false);

            try {
               if (!CFG.SPECTATOR_MODE && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() >= 0) {
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
                  CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
               }
            } catch (IndexOutOfBoundsException var3) {
            }
            break;
         case 4:
            if (SaveManager.gameCanBeContinued) {
               CFG.setDialogType(Dialog.ALL_NOT_SAVED_PROGRESS_WILL_BE_LOST);
            } else {
               clickNewGame();
            }
            break;
         case 5:
            if (CFG.isDesktop()) {
               if (SaveManager.gameCanBeContinued) {
                  CFG.setDialogType(Dialog.ALL_NOT_SAVED_PROGRESS_WILL_BE_LOST2);
               } else {
                  clickRandomGame();
               }
            }
            break;
         case 6:
            CFG.setDialogType(Dialog.START_TUTORIAL);
            break;
         case 7:
            CFG.menuManager.setViewID(Menu.eACHIEVEMENTS);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_Games();
   }
}
