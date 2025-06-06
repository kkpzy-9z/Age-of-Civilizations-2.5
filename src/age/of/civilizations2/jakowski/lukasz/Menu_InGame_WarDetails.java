package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_WarDetails extends SliderMenu {
   protected static int WAR_ID = 0;
   protected static int iSort = 0;
   protected String sDefender;
   protected String sWarDate;
   protected int iWarDateWidth;
   protected static final float FONT_SCALE = 0.55F;

   protected Menu_InGame_WarDetails(int tInit) {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 3;
      int tempMenuPosY = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 + CFG.BUTTON_HEIGHT * 3 / 5 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 2;
      this.initMenu((SliderMenuTitle)null, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, 5, menuElements, false, false);
   }

   protected Menu_InGame_WarDetails() {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 3;
      if (WAR_ID >= CFG.game.getWarsSize()) {
         WAR_ID = 0;
      }

      this.sWarDate = Game_Calendar.getNumOfDates_ByTurnID(CFG.game.getWar(WAR_ID).getWarTurnID());
      CFG.glyphLayout.setText(CFG.fontMain, this.sWarDate);
      this.iWarDateWidth = (int)(CFG.glyphLayout.width * 0.55F);
      menuElements.add(new Button_Statistics_WarDetails_WarResult(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID(), CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID(), WAR_ID, 2, 0, tempWidth - 4) {
         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getW();
         }
      });
      int tY = ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Statistics_Title(CFG.langManager.get("Aggressors"), CFG.PADDING * 2, 2, tY, CFG.BUTTON_WIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getElementW() * 4;
         }

         protected Color getColor(boolean isActive) {
            return Menu_InGame_WarDetails.iSort == 0 ? CFG.COLOR_TEXT_NUM_OF_PROVINCES : super.getColor(isActive);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SortBy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            if (Menu_InGame_WarDetails.iSort != 0) {
               Menu_InGame_WarDetails.iSort = 0;
               CFG.menuManager.rebuildInGame_WarDetails();
            }

         }
      });
      menuElements.add(new Button_Statistics_Title(CFG.langManager.get("Casualties"), -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, tY, CFG.BUTTON_WIDTH, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected int getPosX() {
            return Menu_InGame_WarDetails.this.getElementW() * 4 + 2;
         }

         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getElementW() + 2;
         }

         protected Color getColor(boolean isActive) {
            return Menu_InGame_WarDetails.iSort == 1 ? CFG.COLOR_TEXT_NUM_OF_PROVINCES : super.getColor(isActive);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SortBy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            if (Menu_InGame_WarDetails.iSort != 1) {
               Menu_InGame_WarDetails.iSort = 1;
               CFG.menuManager.rebuildInGame_WarDetails();
            }

         }
      });
      menuElements.add(new Button_Statistics_Title(CFG.langManager.get("Casualties"), -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, tY, CFG.BUTTON_WIDTH, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected int getPosX() {
            return Menu_InGame_WarDetails.this.getElementW() * 5 + 4;
         }

         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getElementW() + 2;
         }

         protected Color getColor(boolean isActive) {
            return Menu_InGame_WarDetails.iSort == 1 ? CFG.COLOR_TEXT_NUM_OF_PROVINCES : super.getColor(isActive);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SortBy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            if (Menu_InGame_WarDetails.iSort != 1) {
               Menu_InGame_WarDetails.iSort = 1;
               CFG.menuManager.rebuildInGame_WarDetails();
            }

         }
      });
      menuElements.add(new Button_Statistics_Title_Right(CFG.langManager.get("Defenders"), CFG.PADDING, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 5, tY, CFG.BUTTON_WIDTH, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected int getPosX() {
            return Menu_InGame_WarDetails.this.getElementW() * 6 + 6;
         }

         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getW() - Menu_InGame_WarDetails.this.getElementW() * 6 - 4;
         }

         protected Color getColor(boolean isActive) {
            return Menu_InGame_WarDetails.iSort == 0 ? CFG.COLOR_TEXT_NUM_OF_PROVINCES : super.getColor(isActive);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SortBy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void actionElement(int iID) {
            if (Menu_InGame_WarDetails.iSort != 0) {
               Menu_InGame_WarDetails.iSort = 0;
               CFG.menuManager.rebuildInGame_WarDetails();
            }

         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      int tempMaxY;
      for(tempMaxY = 0; tempMaxY < CFG.game.getWar(WAR_ID).getAggressorsSize(); ++tempMaxY) {
         menuElements.add(new Button_Statistics_War_Casualties(CFG.game.getWar(WAR_ID).getAggressorID(tempMaxY).getCasualties() + CFG.game.getWar(WAR_ID).getAggressorID(tempMaxY).getCivilianDeaths(), -1, tY, CFG.BUTTON_WIDTH * 2) {
            protected int getPosX() {
               return Menu_InGame_WarDetails.this.getElementW() * 4 + 2;
            }

            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getElementW() + 2;
            }
         });
         menuElements.add(new Button_Statistics_WarDetails(CFG.game.getWar(WAR_ID).getAggressorID(tempMaxY).getCivID(), CFG.game.getWar(WAR_ID).getAggressorID(tempMaxY).getCivilianDeaths(), CFG.game.getWar(WAR_ID).getAggressorID(tempMaxY).getEconomicLosses(), CFG.game.getWar(WAR_ID).getParticipation_AggressorID(tempMaxY), CFG.game.getWar(WAR_ID).getProvinces_Aggressor_OwnTotal(tempMaxY), CFG.game.getWar(WAR_ID).getProvinces_Aggressor_Own(tempMaxY), 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getElementW() * 4;
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      }

      tY = ((MenuElement)menuElements.get(1)).getPosY() + ((MenuElement)menuElements.get(1)).getHeight();

      for(tempMaxY = 0; tempMaxY < CFG.game.getWar(WAR_ID).getDefendersSize(); ++tempMaxY) {
         menuElements.add(new Button_Statistics_War_Casualties_Right(CFG.game.getWar(WAR_ID).getDefenderID(tempMaxY).getCasualties() + CFG.game.getWar(WAR_ID).getDefenderID(tempMaxY).getCivilianDeaths(), -1, tY, CFG.BUTTON_WIDTH * 2) {
            protected int getPosX() {
               return Menu_InGame_WarDetails.this.getElementW() * 5 + 4;
            }

            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getElementW() + 2;
            }
         });
         menuElements.add(new Button_Statistics_WarDetails_Right(CFG.game.getWar(WAR_ID).getDefenderID(tempMaxY).getCivID(), CFG.game.getWar(WAR_ID).getDefenderID(tempMaxY).getCivilianDeaths(), CFG.game.getWar(WAR_ID).getDefenderID(tempMaxY).getEconomicLosses(), CFG.game.getWar(WAR_ID).getParticipation_DefenderID(tempMaxY), CFG.game.getWar(WAR_ID).getProvinces_Defender_OwnTotal(tempMaxY), CFG.game.getWar(WAR_ID).getProvinces_Defender_Own(tempMaxY), CFG.PADDING * 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
            protected int getPosX() {
               return Menu_InGame_WarDetails.this.getElementW() * 6 + 6;
            }

            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getW() - Menu_InGame_WarDetails.this.getElementW() * 6 - 4;
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      }

      if (CFG.FOG_OF_WAR == 2) {
         if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID() > 0) {
            this.sDefender = CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()) ? CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getAllianceName() : CFG.langManager.get("Undiscovered");
         } else {
            this.sDefender = CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()) ? CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getCivName() : CFG.langManager.get("Undiscovered");
         }
      } else {
         this.sDefender = CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID() > 0 ? CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getAllianceName() : CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getCivName();
      }

      tempMaxY = 0;
      int i = 0;

      int tempMenuPosY;
      for(tempMenuPosY = menuElements.size(); i < tempMenuPosY; ++i) {
         if (((MenuElement)menuElements.get(i)).getPosY() + ((MenuElement)menuElements.get(i)).getHeight() > tempMaxY) {
            tempMaxY = ((MenuElement)menuElements.get(i)).getPosY() + ((MenuElement)menuElements.get(i)).getHeight();
         }
      }

      boolean addAlliesNotInWar = false;
      if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID() > 0) {
         for(tempMenuPosY = 0; tempMenuPosY < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilizationsSize(); ++tempMenuPosY) {
            if (!CFG.game.getWar(WAR_ID).getIsInAggressors(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilization(tempMenuPosY))) {
               addAlliesNotInWar = true;
               break;
            }
         }
      }

      if (!addAlliesNotInWar && CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID() > 0) {
         for(tempMenuPosY = 0; tempMenuPosY < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilizationsSize(); ++tempMenuPosY) {
            if (!CFG.game.getWar(WAR_ID).getIsInDefenders(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilization(tempMenuPosY))) {
               addAlliesNotInWar = true;
               break;
            }
         }
      }

      if (!addAlliesNotInWar) {
         for(tempMenuPosY = 0; tempMenuPosY < CFG.game.getWar(WAR_ID).getAggressorsSize(); ++tempMenuPosY) {
            if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(tempMenuPosY).getCivID()).getNumOfProvinces() > 0) {
               for(i = 1; i < CFG.game.getCivsSize(); ++i) {
                  if (i != CFG.game.getWar(WAR_ID).getAggressorID(tempMenuPosY).getCivID() && (CFG.game.getCiv(i).getPuppetOfCivID() == CFG.game.getWar(WAR_ID).getAggressorID(tempMenuPosY).getCivID() || CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(tempMenuPosY).getCivID()).getPuppetOfCivID() == i) && !CFG.game.getWar(WAR_ID).getIsAggressor(i) && !CFG.game.getCivsAreAllied(i, CFG.game.getWar(WAR_ID).getAggressorID(tempMenuPosY).getCivID())) {
                     addAlliesNotInWar = true;
                     break;
                  }
               }
            }
         }
      }

      if (!addAlliesNotInWar) {
         for(tempMenuPosY = 0; tempMenuPosY < CFG.game.getWar(WAR_ID).getDefendersSize(); ++tempMenuPosY) {
            if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(tempMenuPosY).getCivID()).getNumOfProvinces() > 0) {
               for(i = 1; i < CFG.game.getCivsSize(); ++i) {
                  if (i != CFG.game.getWar(WAR_ID).getDefenderID(tempMenuPosY).getCivID() && (CFG.game.getCiv(i).getPuppetOfCivID() == CFG.game.getWar(WAR_ID).getDefenderID(tempMenuPosY).getCivID() || CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(tempMenuPosY).getCivID()).getPuppetOfCivID() == i) && !CFG.game.getWar(WAR_ID).getIsDefender(i) && !CFG.game.getCivsAreAllied(i, CFG.game.getWar(WAR_ID).getDefenderID(tempMenuPosY).getCivID())) {
                     addAlliesNotInWar = true;
                     break;
                  }
               }
            }
         }
      }

      if (addAlliesNotInWar) {
         tY = tempMaxY + CFG.PADDING * 2;
         menuElements.add(new Text_AlliesNotInWar(CFG.langManager.get("AlliesNotInWar"), -1, CFG.PADDING, tY, tempWidth - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3) {
            protected int getPosX() {
               return 2;
            }

            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getW();
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         tempMenuPosY = 0;
         if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID() > 0) {
            for(i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
               if (!CFG.game.getWar(WAR_ID).getIsInAggressors(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilization(i))) {
                  menuElements.add(new Button_Statistics_CallAlly(CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilization(i)) ? -1 : CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilization(i), 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                     protected int getWidth() {
                        return Menu_InGame_WarDetails.this.getElementW() * 5 + 2;
                     }

                     protected void actionElement(int iID) {
                        if (Menu_InGame_WarDetails.WAR_ID >= 0 && Menu_InGame_WarDetails.WAR_ID < CFG.game.getWarsSize()) {
                           if (this.getCurrent() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                              CFG.menuManager.rebuildInGame_JoinAWar(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID(), CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID());
                           } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                              CFG.menuManager.rebuildInGame_CallToArms(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID());
                           } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                              CFG.menuManager.rebuildInGame_DeclareWar(this.getCurrent());
                           }
                        }

                     }
                  });
                  ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tempMenuPosY++ % 2);
                  ((MenuElement)menuElements.get(menuElements.size() - 1)).setClickable(CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getCivilization(i) == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                  tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
               }
            }
         }

         int j;
         for(i = 0; i < CFG.game.getWar(WAR_ID).getAggressorsSize(); ++i) {
            if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(i).getCivID()).getNumOfProvinces() > 0) {
               for(j = 1; j < CFG.game.getCivsSize(); ++j) {
                  if (j != CFG.game.getWar(WAR_ID).getAggressorID(i).getCivID() && CFG.game.getCiv(j).getPuppetOfCivID() == CFG.game.getWar(WAR_ID).getAggressorID(i).getCivID() && !CFG.game.getCivsAreAllied(j, CFG.game.getWar(WAR_ID).getAggressorID(i).getCivID()) && !CFG.game.getWar(WAR_ID).getIsInAggressors(j)) {
                     menuElements.add(new Button_Statistics_CallAlly(CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(j) ? -1 : j, 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                        protected int getWidth() {
                           return Menu_InGame_WarDetails.this.getElementW() * 5 + 2;
                        }

                        protected void actionElement(int iID) {
                           if (Menu_InGame_WarDetails.WAR_ID >= 0 && Menu_InGame_WarDetails.WAR_ID < CFG.game.getWarsSize()) {
                              if (this.getCurrent() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                 CFG.menuManager.rebuildInGame_JoinAWar(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID(), CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID());
                              } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                 CFG.menuManager.rebuildInGame_CallToArms(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID());
                              } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                 CFG.menuManager.rebuildInGame_DeclareWar(this.getCurrent());
                              }
                           }

                        }
                     });
                     ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tempMenuPosY++ % 2);
                     ((MenuElement)menuElements.get(menuElements.size() - 1)).setClickable(CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || j == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                     tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
                  }
               }
            }
         }

         tempMenuPosY = 0;
         tY = tempMaxY + CFG.PADDING * 2;
         if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID() > 0) {
            for(i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
               if (!CFG.game.getWar(WAR_ID).getIsInDefenders(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilization(i))) {
                  menuElements.add(new Button_Statistics_CallAlly_Right(CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilization(i)) ? -1 : CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilization(i), 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                     protected int getPosX() {
                        return Menu_InGame_WarDetails.this.getElementW() * 5 + 4;
                     }

                     protected int getWidth() {
                        return Menu_InGame_WarDetails.this.getW() - Menu_InGame_WarDetails.this.getElementW() * 6 - 4 + Menu_InGame_WarDetails.this.getElementW() + 2;
                     }

                     protected void actionElement(int iID) {
                        if (Menu_InGame_WarDetails.WAR_ID >= 0 && Menu_InGame_WarDetails.WAR_ID < CFG.game.getWarsSize()) {
                           if (this.getCurrent() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                              CFG.menuManager.rebuildInGame_JoinAWar(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID(), CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID());
                           } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                              CFG.menuManager.rebuildInGame_DeclareWar(this.getCurrent());
                           } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                              CFG.menuManager.rebuildInGame_CallToArms(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID());
                           }
                        }

                     }
                  });
                  ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tempMenuPosY++ % 2);
                  ((MenuElement)menuElements.get(menuElements.size() - 1)).setClickable(CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(0).getCivID()).getAllianceID()).getCivilization(i) == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                  tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
               }
            }
         }

         for(i = 0; i < CFG.game.getWar(WAR_ID).getDefendersSize(); ++i) {
            if (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getDefenderID(i).getCivID()).getNumOfProvinces() > 0) {
               for(j = 1; j < CFG.game.getCivsSize(); ++j) {
                  if (j != CFG.game.getWar(WAR_ID).getDefenderID(i).getCivID() && CFG.game.getCiv(j).getPuppetOfCivID() == CFG.game.getWar(WAR_ID).getDefenderID(i).getCivID() && !CFG.game.getCivsAreAllied(j, CFG.game.getWar(WAR_ID).getDefenderID(i).getCivID()) && !CFG.game.getWar(WAR_ID).getIsInDefenders(j)) {
                     menuElements.add(new Button_Statistics_CallAlly_Right(CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(j) ? -1 : j, 2, tY, CFG.BUTTON_WIDTH * 2, CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                        protected int getPosX() {
                           return Menu_InGame_WarDetails.this.getElementW() * 5 + 4;
                        }

                        protected int getWidth() {
                           return Menu_InGame_WarDetails.this.getW() - Menu_InGame_WarDetails.this.getElementW() * 6 - 4 + Menu_InGame_WarDetails.this.getElementW() + 2;
                        }

                        protected void actionElement(int iID) {
                           if (Menu_InGame_WarDetails.WAR_ID >= 0 && Menu_InGame_WarDetails.WAR_ID < CFG.game.getWarsSize()) {
                              if (this.getCurrent() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                 CFG.menuManager.rebuildInGame_JoinAWar(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(0).getCivID(), CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID());
                              } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                 CFG.menuManager.rebuildInGame_DeclareWar(this.getCurrent());
                              } else if (CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                 CFG.menuManager.rebuildInGame_CallToArms(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(0).getCivID());
                              }
                           }

                        }
                     });
                     ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tempMenuPosY++ % 2);
                     ((MenuElement)menuElements.get(menuElements.size() - 1)).setClickable(CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || j == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                     tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
                  }
               }
            }
         }
      }

      tempMaxY = 0;
      tempMenuPosY = 0;

      for(i = menuElements.size(); tempMenuPosY < i; ++tempMenuPosY) {
         if (((MenuElement)menuElements.get(tempMenuPosY)).getPosY() + ((MenuElement)menuElements.get(tempMenuPosY)).getHeight() > tempMaxY) {
            tempMaxY = ((MenuElement)menuElements.get(tempMenuPosY)).getPosY() + ((MenuElement)menuElements.get(tempMenuPosY)).getHeight();
         }
      }

      if (CFG.game.getWar(WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.game.getWar(WAR_ID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
         tempMaxY += CFG.PADDING;
         menuElements.add(new Button_FlagActionSliderStyle_Animated(CFG.langManager.get("PeaceNegotiations"), -1, 2, tempMaxY, CFG.BUTTON_WIDTH, true) {
            protected int getPosX() {
               return 2 + CFG.PADDING;
            }

            protected int getWidth() {
               return Menu_InGame_WarDetails.this.getW() - CFG.PADDING * 2;
            }

            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               ImageManager.getImage(Images.diplo_truce).draw(oSB, this.getPosX() + this.getWidth() / 2 - CFG.PADDING - (int)((float)ImageManager.getImage(Images.diplo_truce).getWidth() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce)) / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)ImageManager.getImage(Images.diplo_truce).getHeight() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce)) / 2 - ImageManager.getImage(Images.diplo_truce).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.diplo_truce).getWidth() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce)), (int)((float)ImageManager.getImage(Images.diplo_truce).getHeight() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce)));
               CFG.fontMain.getData().setScale(0.8F);
               CFG.drawText(oSB, this.getText(), this.getPosX() + (this.getTextPos() < 0 ? this.getWidth() / 2 - (int)(((float)this.getTextWidth() * 0.8F + (float)((int)((float)ImageManager.getImage(Images.diplo_truce).getWidth() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce))) + (float)CFG.PADDING) / 2.0F) + (int)((float)ImageManager.getImage(Images.diplo_truce).getWidth() * Menu_InGame_WarDetails.this.getImageScale3(Images.diplo_truce)) + CFG.PADDING : this.getTextPos()) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, this.getColor(isActive));
               CFG.fontMain.getData().setScale(1.0F);
            }

            protected void actionElement(int iID) {
               if (CFG.PLAYER_PEACE || CFG.SPECTATOR_MODE) {
                  CFG.sandbox_task = Menu.eINGAME_PEACE_TREATY;
               }

               CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
               CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
               CFG.viewsManager.disableAllViews();
               Menu_PeaceTreaty.WAR_ID = Menu_InGame_WarDetails.WAR_ID;
               CFG.peaceTreatyData = new PeaceTreaty_Data(Menu_PeaceTreaty.WAR_ID, CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getIsAggressor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
               CFG.game.resetChooseProvinceData_Immediately();
               CFG.game.resetRegroupArmyData();
               CFG.menuManager.setViewID(Menu.eINGAME_PEACE_TREATY);
            }

            protected int getSFX() {
               return SoundsManager.SOUND_CLICK2;
            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PeaceNegotiations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));

               int i;
               for(i = 0; i < CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorsSize() && i < 5; ++i) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getAggressorID(i).getCivID(), i == 0 ? CFG.PADDING : 0, 0));
               }

               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));

               for(i = 0; i < CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefendersSize() && i < 5; ++i) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getWar(Menu_InGame_WarDetails.WAR_ID).getDefenderID(i).getCivID(), i == 0 ? CFG.PADDING : 0, 0));
               }

               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         tempMaxY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      }

      menuElements.add(new Button_Transparent(0, 0, tempWidth, tempMaxY, true) {
         protected int getPosX() {
            return 2;
         }

         protected int getWidth() {
            return Menu_InGame_WarDetails.this.getW();
         }
      });
      tempMenuPosY = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 + CFG.BUTTON_HEIGHT * 3 / 5 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 2;
      this.initMenu(new SliderMenuTitle(CFG.FOG_OF_WAR == 2 ? (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID() > 0 ? (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()) ? CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getAllianceName() : CFG.langManager.get("Undiscovered")) : (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()) ? CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getCivName() : CFG.langManager.get("Undiscovered"))) : (CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID() > 0 ? CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getAllianceID()).getAllianceName() : CFG.game.getCiv(CFG.game.getWar(WAR_ID).getAggressorID(0).getCivID()).getCivName()), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color(0.5411765F, 0.050980393F, 0.050980393F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + 2 + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth - 4, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(0.5411765F, 0.050980393F, 0.050980393F, 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + 2 + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth - 4, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + 2 + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth - 4, CFG.PADDING, false, true);
            oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth - 4, 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), (nWidth - 4) / 2, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + 2 + (nWidth - 4) - (nWidth - 4) / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), (nWidth - 4) / 2, 1, true, false);
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.diplo_rivals).draw(oSB, nPosX + nWidth / 2 - ImageManager.getImage(Images.diplo_rivals).getWidth() / 2 + iTranslateX, 2 + nPosY - this.getHeight() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_rivals).getHeight() / 2);
            CFG.fontMain.getData().setScale(0.7F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.7F) - ImageManager.getImage(Images.diplo_rivals).getWidth() / 2 - CFG.PADDING + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.7F) / 2, Color.WHITE);
            CFG.drawText(oSB, Menu_InGame_WarDetails.this.sDefender, nPosX + nWidth / 2 + ImageManager.getImage(Images.diplo_rivals).getWidth() / 2 + CFG.PADDING + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.7F) / 2, Color.WHITE);
            ImageManager.getImage(Images.time).draw(oSB, nPosX + nWidth - CFG.PADDING - 2 - (int)((float)ImageManager.getImage(Images.time).getWidth() * Menu_InGame_WarDetails.this.getImageScale2(Images.time)) + iTranslateX, nPosY - CFG.PADDING - (int)((float)ImageManager.getImage(Images.time).getHeight() * Menu_InGame_WarDetails.this.getImageScale2(Images.time)) - ImageManager.getImage(Images.time).getHeight(), (int)((float)ImageManager.getImage(Images.time).getWidth() * Menu_InGame_WarDetails.this.getImageScale2(Images.time)), (int)((float)ImageManager.getImage(Images.time).getHeight() * Menu_InGame_WarDetails.this.getImageScale2(Images.time)));
            CFG.fontMain.getData().setScale(0.55F);
            CFG.drawText(oSB, Menu_InGame_WarDetails.this.sWarDate, nPosX + nWidth - Menu_InGame_WarDetails.this.iWarDateWidth - CFG.PADDING * 2 - (int)((float)ImageManager.getImage(Images.time).getWidth() * Menu_InGame_WarDetails.this.getImageScale2(Images.time)) - 2 + iTranslateX, nPosY - CFG.PADDING - (int)((float)CFG.TEXT_HEIGHT * 0.55F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH / 2 - tempWidth * 3 / 4, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, true);
      this.updateLanguage();

      for(i = 0; i < this.getMenuElementsSize() && i < CFG.game.getWar(WAR_ID).getAggressorsSize() * 2; ++i) {
         this.getMenuElement(i).setCurrent(i / 2 % 2);
      }

      for(i = 4 + CFG.game.getWar(WAR_ID).getAggressorsSize() * 2; i < this.getMenuElementsSize() && i < 4 + CFG.game.getWar(WAR_ID).getAggressorsSize() * 2 + CFG.game.getWar(WAR_ID).getDefendersSize(); ++i) {
         this.getMenuElement(i).setCurrent((i / 2 + (CFG.game.getWar(WAR_ID).getAggressorsSize() + 1) % 2) % 2);
      }

   }

   protected void updateLanguage() {
   }

   private final float getImageScale3(int nImageID) {
      return (float)CFG.TEXT_HEIGHT * 1.0F / (float)ImageManager.getImage(nImageID).getHeight() < 1.0F ? (float)CFG.TEXT_HEIGHT * 1.0F / (float)ImageManager.getImage(nImageID).getHeight() : 1.0F;
   }

   private final float getImageScale2(int nImageID) {
      return (float)CFG.TEXT_HEIGHT * 0.55F / (float)ImageManager.getImage(nImageID).getHeight() < 1.0F ? (float)CFG.TEXT_HEIGHT * 0.55F / (float)ImageManager.getImage(nImageID).getHeight() : 1.0F;
   }

   private final void clickFlag(int iID) {
      try {
         CFG.toast.setInView(CFG.game.getCiv(this.getMenuElement(iID).getCurrent()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
         if (CFG.FOG_OF_WAR == 2) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(this.getMenuElement(iID).getCurrent()) && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(this.getMenuElement(iID).getCurrent()).getCapitalProvinceID())) {
               CFG.game.setActiveProvinceID(CFG.game.getCiv(this.getMenuElement(iID).getCurrent()).getCapitalProvinceID());
               CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
            }
         } else {
            CFG.game.setActiveProvinceID(CFG.game.getCiv(this.getMenuElement(iID).getCurrent()).getCapitalProvinceID());
            CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
         }

         if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
            CFG.game.disableDrawCivilizationRegions_Active();
            CFG.game.enableDrawCivilizationRegions_ActiveProvince();
         }
      } catch (IndexOutOfBoundsException var3) {
      }

   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, false, true);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
      oSB.setColor(Color.WHITE);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.65F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getMenuPosY() - 1 + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getMenuElement(0).getHeight() + iTranslateY, this.getWidth() - 4, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getMenuElement(0).getHeight() + iTranslateY, this.getWidth() - 4, 1);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void drawCloseButton(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      this.getCloseButtonImage(sliderMenuIsActive).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() * 3 / 5 + iTranslateX, this.getPosY() - this.getTitle().getHeight() - ImageManager.getImage(Images.btn_close).getHeight() + iTranslateY, ImageManager.getImage(Images.btn_close).getWidth() * 3 / 5, ImageManager.getImage(Images.btn_close).getHeight() * 3 / 5);
   }

   protected final void actionElement(int iID) {
      if (iID != this.getMenuElementsSize() - 1) {
         this.getMenuElement(iID).actionElement(iID);
      }
   }

   protected final int getW() {
      return this.getWidth() - 4;
   }

   protected final int getElementW() {
      return this.getW() / 10;
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
