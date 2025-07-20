package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_NextPlayerTurn extends SliderMenu {
   protected static float SCALE_BEFORE_NEXT_PLAYER_MENU = 1.0F;
   private String sPlayer;
   private static String worldsProvinces = "";
   private static long lTime = 0L;
   private static final int ANIMATION_TIME = 3000;

   protected Menu_NextPlayerTurn() {
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      menuElements.add(new Button_Flag_JustFrame(CFG.CIV_FLAG_WIDTH / 2, CFG.GAME_HEIGHT / 2 - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2, true) {
         protected void buildElementHover() {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Hide"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Transparent(0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, true));
      this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.sPlayer = CFG.langManager.get("Player");
   }

   protected static void updateData() {
      if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() > 0 && !CFG.SPECTATOR_MODE) {
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() > 0) {
            worldsProvinces = CFG.langManager.get("XOfAllProvines", CFG.getPercentage(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces(), CFG.game.countLandProvinces_NotWasteland(), 4));
            lTime = System.currentTimeMillis();
         } else {
            worldsProvinces = "";
            lTime = 0L;
         }
      } else {
         worldsProvinces = "";
         lTime = 0L;
      }

   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2);
      ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT / 2, false, true);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.125F));
      ImageManager.getImage(Images.gameLogo).draw(oSB, this.getPosX() + this.getWidth() - CFG.PADDING - ImageManager.getImage(Images.gameLogo).getWidth() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING - ImageManager.getImage(Images.gameLogo).getHeight());
      if (this.getMenuElement(0).getVisible()) {
         CFG.glyphLayout.setText(CFG.fontMain, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
         int iCivNameWidth = (int)CFG.glyphLayout.width;
         oSB.setColor(CFG.COLOR_GRADIENT_DARK_BLUE);
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.BUTTON_HEIGHT - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, iCivNameWidth + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.CIV_FLAG_WIDTH + CFG.PADDING * 2, CFG.BUTTON_HEIGHT * 2);
         oSB.setColor(CFG.COLOR_FLAG_FRAME);
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + this.getHeight() / 2 - CFG.BUTTON_HEIGHT - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, iCivNameWidth + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.CIV_FLAG_WIDTH + CFG.PADDING * 2, 1);
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - 2 + this.getHeight() / 2 + CFG.BUTTON_HEIGHT - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, iCivNameWidth + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.CIV_FLAG_WIDTH + CFG.PADDING * 2, 1);
         oSB.setColor(Color.WHITE);
         CFG.game.getPlayer(CFG.PLAYER_TURNID).getFlag().draw(oSB, this.getPosX() + CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.game.getPlayer(CFG.PLAYER_TURNID).getFlag().getHeight() - ImageManager.getImage(Images.top_flag_frame).getHeight() / 2 + iTranslateY, ImageManager.getImage(Images.top_flag_frame).getWidth(), ImageManager.getImage(Images.top_flag_frame).getHeight());
         super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData != null && !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getName().isEmpty()) {
            CFG.drawTextWithShadow(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getName(), CFG.CIV_FLAG_WIDTH / 2 + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.PADDING + iTranslateX, CFG.GAME_HEIGHT / 2 + CFG.PADDING / 2, CFG.COLOR_TEXT_CIV_NAME);
         } else {
            CFG.drawTextWithShadow(oSB, this.sPlayer + " " + (CFG.PLAYER_TURNID + 1), CFG.CIV_FLAG_WIDTH / 2 + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.PADDING + iTranslateX, CFG.GAME_HEIGHT / 2 + CFG.PADDING / 2, CFG.COLOR_TEXT_CIV_NAME);
         }

         CFG.drawTextWithShadow(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName(), CFG.CIV_FLAG_WIDTH / 2 + ImageManager.getImage(Images.top_flag_frame).getWidth() + CFG.PADDING + iTranslateX, CFG.GAME_HEIGHT / 2 - CFG.TEXT_HEIGHT - CFG.PADDING / 2, CFG.COLOR_TEXT_RANK);
         if (lTime > System.currentTimeMillis() - 3000L) {
            CFG.fontMain.getData().setScale(0.8F);
            float tAlpha = 1.0F;
            if ((float)lTime <= (float)System.currentTimeMillis() - 2000.0F) {
               tAlpha = 1.0F - ((float)(System.currentTimeMillis() - lTime) - 1000.0F) / 3000.0F * 2.0F / 3.0F;
            }

            CFG.drawText(oSB, worldsProvinces, CFG.PADDING * 2 + iTranslateX, CFG.GAME_HEIGHT - (int)((float)CFG.TEXT_HEIGHT * 0.8F) - CFG.PADDING * 2, new Color(CFG.COLOR_TEXT_NUM_OF_PROVINCES.r, CFG.COLOR_TEXT_NUM_OF_PROVINCES.g, CFG.COLOR_TEXT_NUM_OF_PROVINCES.b, tAlpha));
            CFG.fontMain.getData().setScale(1.0F);
            CFG.setRender_3(true);
         }
      } else {
         oSB.setColor(Color.WHITE);
      }

   }

   protected static void clickBack() {
      RTS.resetTime();
      CFG.menuManager.setViewID(Menu.eINGAME);
      CFG.map.getMapScroll().stopScrollingTheMap();
      CFG.map.getMapBG().updateWorldMap_Shaders();
      CFG.game.disableDrawCivilizationRegions(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
      clickEnd();
      if (RTS.PAUSED_BY_NEXT_TURN) {
         RTS.PAUSE = false;
         RTS.resetTime();
         RTS.PAUSED_BY_NEXT_TURN = false;
      }

   }

   protected static void clickEnd_LoadPlayerData() {
      try {
         if (RTS.isEnabled() && !RTS.PAUSE) {
            if (!CFG.SPECTATOR_MODE || CFG.game.getActiveProvinceID() < 0) {
               CFG.game.setActiveProvinceID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince);
            }
            else {
               final int oldActiveProvinceID = CFG.game.getActiveProvinceID();
               CFG.game.setActiveProvinceID(-1);
               CFG.game.setActiveProvinceID(oldActiveProvinceID);
            }
         }
         else if (!CFG.SPECTATOR_MODE) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_PosY == -999999) {
               CFG.map.getMapScale().setCurrentScale(Menu_NextPlayerTurn.SCALE_BEFORE_NEXT_PLAYER_MENU);
               if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
                  CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
               }
               else {
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getProvinceID(0));
                  CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getProvinceID(0));
               }
            }
            else {
               if (CFG.gameAction.updatePosOfMap_NewTurn || CFG.gameAction.getNumOfPlayersInGame() > 1) {
                  CFG.map.getMapScale().setCurrentScale(CFG.game.getPlayer(CFG.PLAYER_TURNID).fBefore_Scale);
                  CFG.map.getMapCoordinates().setStartingPosX(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_PosX);
                  CFG.map.getMapCoordinates().setStartingPosY(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_PosY);
                  CFG.map.getMapCoordinates().updateSecondSideOfMap();
               }
               if (!CFG.SPECTATOR_MODE || CFG.game.getActiveProvinceID() < 0) {
                  CFG.game.setActiveProvinceID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince);
               }
               else {
                  final int oldActiveProvinceID = CFG.game.getActiveProvinceID();
                  CFG.game.setActiveProvinceID(-1);
                  CFG.game.setActiveProvinceID(oldActiveProvinceID);
               }
            }
         }
         else if (!CFG.SPECTATOR_MODE || CFG.game.getActiveProvinceID() < 0) {
            CFG.game.setActiveProvinceID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince);
         }
         else {
            final int oldActiveProvinceID = CFG.game.getActiveProvinceID();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.setActiveProvinceID(oldActiveProvinceID);
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_CivInfo >= 0) {
            CFG.setActiveCivInfo(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_CivInfo);
            CFG.menuManager.setVisible_InGame_CivInfo(true);
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_ManageInfo) {
            CFG.menuManager.setVisible_InGame_CivManage(true);
            CFG.updateActiveCivManagement_InGame();
         }
         CFG.menuManager.setVisible_Menu_InGame_Outliner(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Outliner);
         CFG.menuManager.setVisibleInGame_MilitaryAlliances(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Alliances);
         CFG.menuManager.setVisible_InGame_MapModes(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_MapModes);
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_History) {
            CFG.menuManager.rebuildInGame_History();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_HRE) {
            CFG.menuManager.rebuildInGame_HRE();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_WorldPop) {
            CFG.menuManager.rebuildInGame_WorldPopulation();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_CensusOfProvince >= 0) {
            CFG.menuManager.rebuildInGame_CensusOfProvince(Menu_InGame_CensusOfProvince.PROVINCE_ID);
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Alliance >= 0) {
            CFG.menuManager.rebuildInGame_Alliance(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Alliance);
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Rank) {
            CFG.menuManager.rebuildInGame_Rank();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_ConqueredProvinces) {
            CFG.menuManager.rebuildInGame_ConqueredProvinces();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_VictoryConditions) {
            CFG.menuManager.rebuildInGame_VictoryConditions();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_BuildingsConstructed) {
            CFG.menuManager.rebuildInGame_BuildingsConstrcuted();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_RecruitedArmy) {
            CFG.menuManager.rebuildInGame_RecruitedArmy();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Tribute) {
            CFG.menuManager.rebuildInGame_Tribute();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Army) {
            CFG.menuManager.rebuildInGame_Army();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Wars) {
            CFG.menuManager.rebuildInGame_Wars();
         }
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_WarStats >= 0) {
            Menu_InGame_WarDetails.WAR_ID = CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_WarStats;
            CFG.menuManager.rebuildInGame_WarDetails();
         }
         CFG.viewsManager.disableAllViews();
         CFG.viewsManager.setActiveViewID(CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE);
         CFG.game.checkProvinceActionMenu();
         BuildingsManager.iBuildInProvinceID = CFG.game.getActiveProvinceID();
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_BuildingsMore) {
            CFG.menuManager.setVisible_InGame_ProvinceBuild(CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_BuildingsMore, true);
         }
         else {
            CFG.menuManager.setVisible_InGame_ProvinceBuild(false, false);
         }
         if (SaveManager.gameSaved) {
            SaveManager.gameSaved = false;
            CFG.menuManager.rebuildMenu_InGame_SavedGame();
         }
      }
      catch (final IndexOutOfBoundsException | NullPointerException ex) {
         CFG.exceptionStack(ex);
         try {
            final int oldActiveProvinceID2 = CFG.game.getActiveProvinceID();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.setActiveProvinceID(oldActiveProvinceID2);
         }
         catch (final IndexOutOfBoundsException | NullPointerException | ArithmeticException | StackOverflowError ex2) {
            CFG.exceptionStack(ex);
         }
      }
      catch (final ArithmeticException ex7) {
         CFG.exceptionStack(ex7);
         try {
            final int oldActiveProvinceID2 = CFG.game.getActiveProvinceID();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.setActiveProvinceID(oldActiveProvinceID2);
         }
         catch (final IndexOutOfBoundsException ex2) {
            CFG.exceptionStack(ex7);
         }
         catch (final NullPointerException ex3) {
            CFG.exceptionStack(ex7);
         }
         catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex7);
         }
         catch (final StackOverflowError ex5) {
            CFG.exceptionStack(ex7);
         }
      }
      catch (final StackOverflowError ex8) {
         CFG.exceptionStack(ex8);
         try {
            final int oldActiveProvinceID2 = CFG.game.getActiveProvinceID();
            CFG.game.setActiveProvinceID(-1);
            CFG.game.setActiveProvinceID(oldActiveProvinceID2);
         }
         catch (final IndexOutOfBoundsException ex2) {
            CFG.exceptionStack(ex8);
         }
         catch (final NullPointerException ex3) {
            CFG.exceptionStack(ex8);
         }
         catch (final ArithmeticException ex4) {
            CFG.exceptionStack(ex8);
         }
         catch (final StackOverflowError ex5) {
            CFG.exceptionStack(ex8);
         }
      }
   }

   protected static final void clickEnd() {
      Gdx.app.log("AoC", "clickEnd: 000");
      clickEnd_LoadPlayerData();
      Gdx.app.log("AoC", "clickEnd: 1111");
      if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
         for (int j = 0; j < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++j) {
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnitsSize(); ++i) {
               CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getMoveUnitsLine().updateMoveTime();
            }
         }
      }
      else {
         for (int k = 0; k < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++k) {
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(k).getMoveUnitsLine().updateMoveTime();
         }
      }
      for (int k = 0; k < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrateSize(); ++k) {
         CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(k).getMoveUnitsLine().updateMoveTime();
      }
      if (!CFG.SPECTATOR_MODE && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize() > 0) {
         CFG.soundsManager.playSound(SoundsManager.SOUND_MOVE_ARMY);
      }
      for (int j = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() - 1; j > 0; --j) {
         if (CFG.game.getCiv(j).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
            for (int i = 0; i < CFG.game.getCiv(j).getMoveUnitsSize(); ++i) {
               CFG.game.getCiv(j).getMoveUnits(i).getMoveUnitsLine().updateMoveTime();
            }
         }
      }
      for (int j = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() + 1; j < CFG.game.getCivsSize(); ++j) {
         if (CFG.game.getCiv(j).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
            for (int i = 0; i < CFG.game.getCiv(j).getMoveUnitsSize(); ++i) {
               if (CFG.game.getCiv(j).getMoveUnits(i).getMoveUnitsLine() != null) {
                  CFG.game.getCiv(j).getMoveUnits(i).getMoveUnitsLine().updateMoveTime();
               }
            }
         }
      }
      Game_Render_Province.PROVINCE_COLOR_ANIMATION_TIMER = 0L;
      CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).runNextEvent();
      Menu_InGame_Messages.START_ANIMATION = true;
      Gdx.app.log("AoC", "clickEnd: END");
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.map.getMapScroll().stopScrollingTheMap();
            SCALE_BEFORE_NEXT_PLAYER_MENU = CFG.map.getMapScale().getCurrentScale();
            CFG.map.getMapScroll().stopScrollingTheMap();
            CFG.map.getMapCoordinates().centerToCivilizationBox(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), true);
            this.getMenuElement(iID).setVisible(false);
            break;
         case 1:
            this.onBackPressed();
      }

   }

   protected final void onBackPressed() {
      clickBack();
   }

   protected void onMenuPressed() {
      this.onBackPressed();
   }
}
