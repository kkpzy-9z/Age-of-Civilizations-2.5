package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_Settings_Options extends SliderMenu {
   protected Menu_Settings_Options() {
      List menuElements = new ArrayList();
      int tY = CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, CFG.goToMenu2 == Menu.eMAINMENU) {
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
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true) {
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, Gdx.app.getType() == Application.ApplicationType.Android, ConfigINI.landscape) {
         protected boolean getCheckboxState() {
            return ConfigINI.landscape;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.randomLeaders) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.randomLeaders;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu("-", -1, 0, tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_Classic((String)null, -1, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, tY, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2) * 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu("-", -1, 0, tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_Classic((String)null, -1, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, tY, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2) * 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //width slider
      menuElements.add(new Button_Menu("-", -1, 0, tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_Classic((String)null, -1, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, tY, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2) * 2, CFG.BUTTON_HEIGHT, true) {
         @Override
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefWidth") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("240"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Menu_ReflectedBG("+", -1, Menu_InGame_FlagAction.getWindowWidth() - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), tY, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.showNextPlayerView) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.showNextPlayerView;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.showOrderOfMovesView) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.showOrderOfMovesView;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.CONTINUOUS_RENDERING) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.CONTINUOUS_RENDERING;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.CONFIRM_END_TURN) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.CONFIRM_END_TURN;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.CONFIRM_NO_ORDERS) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.CONFIRM_NO_ORDERS;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.reverseDirectionX) {
         protected boolean getCheckboxState() {
            return CFG.reverseDirectionX;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.reverseDirectionY) {
         protected boolean getCheckboxState() {
            return CFG.reverseDirectionY;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, LanguageManager.translationsKeysMode) {
         protected boolean getCheckboxState() {
            return LanguageManager.translationsKeysMode;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_Line((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.loadCursor) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.loadCursor;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_Line((String)null, (int)(50.0F * CFG.GUI_SCALE), 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true, CFG.settingsManager.autoskipEvents) {
         protected boolean getCheckboxState() {
            return CFG.settingsManager.autoskipEvents;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_Menu_LR_Line((String)null, -1, 0, tY, Menu_InGame_FlagAction.getWindowWidth(), CFG.BUTTON_HEIGHT, true));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu((SliderMenuTitle)null, 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 3 / 4, Menu_InGame_FlagAction.getWindowWidth(), CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4 - CFG.BUTTON_HEIGHT - CFG.PADDING, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("MapType") + ": " + CFG.map.getMapName(CFG.map.getActiveMapID()));
      this.getMenuElement(1).setText(CFG.langManager.get("Language") + ": " + CFG.langManager.get("LANGUAGENAME"));
      this.getMenuElement(2).setText(CFG.langManager.get("Graphics"));
      this.getMenuElement(3).setText(CFG.langManager.get("UIScale"));
      this.getMenuElement(4).setText(CFG.langManager.get("ProvinceSettings"));
      this.getMenuElement(5).setText(CFG.langManager.get("Audio"));
      this.getMenuElement(6).setText(CFG.langManager.get("Landscape"));
      this.getMenuElement(7).setText(CFG.langManager.get("Leaders") + ": " + CFG.langManager.get("Random"));
      this.getMenuElement(9).setText(CFG.langManager.get("FontSize") + ": " + CFG.settingsManager.FONT_MAIN_SIZE);
      this.getMenuElement(12).setText(CFG.langManager.get("TurnsBetweenAutosave") + ": " + CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE);

      //width text setting
      this.getMenuElement(15).setText(CFG.langManager.get("UIWidth") + ": " + CFG.settingsManager.CIV_INFO_MENU_WIDTH);

      this.getMenuElement(17).setText(CFG.langManager.get("ShowNextPlayerTurnView"));
      this.getMenuElement(18).setText(CFG.langManager.get("OrderOfMoves"));
      this.getMenuElement(19).setText(CFG.langManager.get("ContinuousRendering"));
      this.getMenuElement(20).setText(CFG.langManager.get("ConfirmEndTurn"));
      this.getMenuElement(21).setText(CFG.langManager.get("ConfirmNoOrders"));
      this.getMenuElement(22).setText(CFG.langManager.get("InvertXAxis"));
      this.getMenuElement(23).setText(CFG.langManager.get("InvertYAxis"));
      this.getMenuElement(24).setText(CFG.langManager.get("TranslationKeys"));
      this.getMenuElement(25).setText(CFG.langManager.get("CustomCursor"));
      //autoskip events setting
      this.getMenuElement(26).setText(CFG.langManager.get("AutoSkipEvents"));
      this.getMenuElement(27).setText(CFG.langManager.get("Defaults"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      CFG.map.getIcon(CFG.map.getActiveMapID()).draw(oSB, this.getMenuElement(0).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(0).getPosY() + this.getMenuElement(0).getHeight() / 2 - CFG.map.getIcon(CFG.map.getActiveMapID()).getHeight() - CFG.CIV_FLAG_HEIGHT / 2 + this.getMenuPosY() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.backToMenu = Menu.eSETTINGS;
            CFG.menuManager.setViewID(Menu.eSELECT_MAP_TYPE);
            return;
         case 1:
            CFG.backToMenu = Menu.eSETTINGS;
            CFG.menuManager.setViewID(Menu.eSELECT_LANGUAGE);
            CFG.map.getMapBG().updateWorldMap_Shaders();
            CFG.VIEW_SHOW_VALUES = true;
            break;
         case 2:
            CFG.menuManager.setViewID(Menu.eSETTINGS_GRAPHICS);
            break;
         case 3:
            CFG.menuManager.setViewID(Menu.eSELECT_UI_SCALE);
            return;
         case 4:
            if (!SaveManager.gameCanBeContinued) {
               for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                  CFG.game.getProvince(i).getArmy_Obj(0).updateArmyWidth(i);
               }
            }

            CFG.menuManager.setViewID(Menu.eSETTINGS_PROVINCE);
            return;
         case 5:
            CFG.menuManager.setVisible_Settings_Audio();
            break;
         case 6:
            ConfigINI.landscape = !ConfigINI.landscape;
            ConfigINI.saveConfig();
            Preferences prefs = Gdx.app.getPreferences("AND");
            prefs.putBoolean("landscape", ConfigINI.landscape);
            CFG.toast.setInView(CFG.langManager.get("GameNeedsToBeRestartedToApplyTheChanges"));
            CFG.toast.setTimeInView(6000);
            break;
         case 7:
            CFG.settingsManager.randomLeaders = !CFG.settingsManager.randomLeaders;
            break;
         case 8:
            --CFG.settingsManager.FONT_MAIN_SIZE;
            if (CFG.settingsManager.FONT_MAIN_SIZE < 8) {
               CFG.settingsManager.FONT_MAIN_SIZE = 8;
            }

            CFG.loadFontMain();
            this.updateLanguage();
            CFG.menuManager.updateLanguage();
            break;
         case 9:
            if (CFG.XXXXHDPI) {
               CFG.settingsManager.FONT_MAIN_SIZE = 42;
            } else if (CFG.XXXHDPI) {
               CFG.settingsManager.FONT_MAIN_SIZE = 36;
            } else if (CFG.XXHDPI) {
               CFG.settingsManager.FONT_MAIN_SIZE = 32;
            } else if (CFG.XHDPI) {
               CFG.settingsManager.FONT_MAIN_SIZE = 24;
            } else {
               CFG.settingsManager.FONT_MAIN_SIZE = 18;
            }

            CFG.loadFontMain();
            this.updateLanguage();
            CFG.menuManager.updateLanguage();
            break;
         case 10:
            ++CFG.settingsManager.FONT_MAIN_SIZE;
            if (CFG.settingsManager.FONT_MAIN_SIZE > 128) {
               CFG.settingsManager.FONT_MAIN_SIZE = 128;
            }

            CFG.loadFontMain();
            this.updateLanguage();
            CFG.menuManager.updateLanguage();
            break;
         case 11:
            --CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE;
            if (CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE < 0) {
               CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE = 0;
            }

            this.updateLanguage();
            break;
         case 12:
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            break;
         case 13:
            ++CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE;
            if (CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE > 100) {
               CFG.settingsManager.TURNS_BETWEEN_AUTOSAVE = 100;
            }

            this.updateLanguage();
            break;

         //width setting
         case 14:
            CFG.settingsManager.CIV_INFO_MENU_WIDTH -= 20;
            if (CFG.settingsManager.CIV_INFO_MENU_WIDTH < 160) {
               CFG.settingsManager.CIV_INFO_MENU_WIDTH = 160;
            }
            this.updateLanguage();

            CFG.toast.setInView(CFG.langManager.get("GameNeedsToBeRestartedToApplyTheChanges"));
            break;
         case 15:
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            break;
         case 16:
            CFG.settingsManager.CIV_INFO_MENU_WIDTH += 20;
            if (CFG.settingsManager.CIV_INFO_MENU_WIDTH > 400) {
               CFG.settingsManager.CIV_INFO_MENU_WIDTH = 400;
            }
            this.updateLanguage();

            CFG.toast.setInView(CFG.langManager.get("GameNeedsToBeRestartedToApplyTheChanges"));
            break;

         case 17:
            CFG.settingsManager.showNextPlayerView = !CFG.settingsManager.showNextPlayerView;
            break;
         case 18:
            CFG.settingsManager.showOrderOfMovesView = !CFG.settingsManager.showOrderOfMovesView;
            break;
         case 19:
            CFG.settingsManager.CONTINUOUS_RENDERING = !CFG.settingsManager.CONTINUOUS_RENDERING;
            break;
         case 20:
            CFG.settingsManager.CONFIRM_END_TURN = !CFG.settingsManager.CONFIRM_END_TURN;
            break;
         case 21:
            CFG.settingsManager.CONFIRM_NO_ORDERS = !CFG.settingsManager.CONFIRM_NO_ORDERS;
            break;
         case 22:
            CFG.reverseDirectionX = !CFG.reverseDirectionX;
            CFG.map.getMapTouchManager().buildReversePosX();
            CFG.map.getMapTouchManager().buildReversePosX2();
            break;
         case 23:
            CFG.reverseDirectionY = !CFG.reverseDirectionY;
            CFG.map.getMapTouchManager().buildReversePosY();
            CFG.map.getMapTouchManager().buildReversePosY2();
            break;
         case 24:
            LanguageManager.translationsKeysMode = !LanguageManager.translationsKeysMode;
            CFG.langManager.updateKeyOutput();
            CFG.menuManager.updateLanguage();
            break;
         case 25:
            CFG.settingsManager.loadCursor = !CFG.settingsManager.loadCursor;
            AoCGame.loadCursor(false);
            if (CFG.settingsManager.loadCursor) {
               CFG.toast.setInView(" --- The cursor may disappear during video recording --- ", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
            }
         case 26:
            CFG.settingsManager.autoskipEvents = !CFG.settingsManager.autoskipEvents;
            break;
      }

      CFG.saveSettings();
   }
}
