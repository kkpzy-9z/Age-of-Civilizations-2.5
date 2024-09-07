package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_CivInfo_Stats extends SliderMenu {
   protected Menu_InGame_CivInfo_Stats() {
      List menuElements = new ArrayList();
      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 3, CFG.PADDING * 3) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_PopulationOfCiv(CFG.getActiveCivInfo());
         }
      });
      menuElements.add(new Text_LeftSide_Icon("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, CFG.PADDING * 3, Images.population) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_PopulationOfCiv(CFG.getActiveCivInfo());
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_POPULATION_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_POPULATION_HOVER : CFG.COLOR_TEXT_POPULATION) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 3, CFG.PADDING * 3 + CFG.TEXT_HEIGHT + CFG.PADDING) {
         protected void buildElementHover() {
            try {
               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID())) {
                     this.menuElementHover = CFG.game.getHover_CapitalCity(CFG.getActiveCivInfo());
                  } else {
                     this.menuElementHover = null;
                  }
               } else {
                  this.menuElementHover = CFG.game.getHover_CapitalCity(CFG.getActiveCivInfo());
               }
            } catch (IndexOutOfBoundsException var2) {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Text_LeftSide_Icon("3", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, CFG.PADDING * 3 + CFG.TEXT_HEIGHT + CFG.PADDING, Images.city) {
         protected void buildElementHover() {
            try {
               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID())) {
                     this.menuElementHover = CFG.game.getHover_CapitalCity(CFG.getActiveCivInfo());
                  } else {
                     this.menuElementHover = null;
                  }
               } else {
                  this.menuElementHover = CFG.game.getHover_CapitalCity(CFG.getActiveCivInfo());
               }
            } catch (IndexOutOfBoundsException var2) {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 3, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 2) {
         protected void buildElementHover() {
            try {
               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent())) {
                     this.menuElementHover = CFG.game.getHover_LargestCity(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent());
                  } else {
                     this.menuElementHover = null;
                  }
               } else {
                  this.menuElementHover = CFG.game.getHover_LargestCity(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent());
               }
            } catch (IndexOutOfBoundsException var2) {
               this.menuElementHover = null;
            }

         }
      });
      menuElements.add(new Text_LeftSide("3", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 2) {
         int iCurrent;

         protected int getCurrent() {
            return this.iCurrent;
         }

         protected void setCurrent(int nCurrent) {
            this.iCurrent = nCurrent;
         }

         protected void buildElementHover() {
            try {
               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent())) {
                     this.menuElementHover = CFG.game.getHover_LargestCity(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent());
                  } else {
                     this.menuElementHover = null;
                  }
               } else {
                  this.menuElementHover = CFG.game.getHover_LargestCity(Menu_InGame_CivInfo_Stats.this.getMenuElement(5).getCurrent());
               }
            } catch (IndexOutOfBoundsException var2) {
               this.menuElementHover = null;
            }

         }
      });
      final List<Integer> lData = new ArrayList<Integer>();
      final List<Integer> lCivs = new ArrayList<Integer>();
      lData.add(18);
      lCivs.add(1);
      lData.add(7);
      lCivs.add(2);
      menuElements.add(new Graph_Circle(CFG.PADDING * 3, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 3 + CFG.PADDING, lData, lCivs, (MenuElement_Hover)null) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_PopulationOfCiv(CFG.getActiveCivInfo());
         }
      });
      ((Graph_Circle)menuElements.get(menuElements.size() - 1)).isDescriptionActive = false;
      ((Graph_Circle)menuElements.get(menuElements.size() - 1)).hideAnimation = false;

      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 3, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 3 + CFG.PADDING + CFG.graphCircleDraw.getWidth() + CFG.PADDING * 3) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_TechnologyLevel(CFG.getActiveCivInfo());
         }
      });
      menuElements.add(new Text_LeftSide_Icon("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 3 + CFG.PADDING + CFG.graphCircleDraw.getWidth() + CFG.PADDING * 3, Images.technology) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.game.getHover_TechnologyLevel(CFG.getActiveCivInfo());
         }
      });
      menuElements.add(new Text_CivInfo((String)null, CFG.PADDING * 3, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 4 + CFG.PADDING + CFG.graphCircleDraw.getWidth() + CFG.PADDING * 3));
      menuElements.add(new Text_LeftSide_Icon("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, CFG.PADDING * 3 + (CFG.TEXT_HEIGHT + CFG.PADDING) * 4 + CFG.PADDING + CFG.graphCircleDraw.getWidth() + CFG.PADDING * 3, Images.economy) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_ECONOMY_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_ECONOMY_HOVER : CFG.COLOR_TEXT_ECONOMY) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      menuElements.add(new Text_LeftSide_Happiness("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, ((MenuElement)menuElements.get(6)).getPosY() + ((MenuElement)menuElements.get(6)).getHeight() - CFG.TEXT_HEIGHT * 2 - CFG.PADDING) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Happiness") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Text_LeftSide_Ideology("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, ((MenuElement)menuElements.get(6)).getPosY() + ((MenuElement)menuElements.get(6)).getHeight() - CFG.TEXT_HEIGHT) {
         protected void buildElementHover() {
            this.menuElementHover = CFG.ideologiesManager.getIdeologyHover(CFG.getActiveCivInfo());
         }
      });
      menuElements.add(new Text_LeftSide_Stability("", CFG.CIV_INFO_MENU_WIDTH - CFG.PADDING * 3 - 2, ((MenuElement)menuElements.get(6)).getPosY() + ((MenuElement)menuElements.get(6)).getHeight() - CFG.TEXT_HEIGHT * 3 - CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Stability") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_popstability, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Transparent(0, 0, CFG.CIV_INFO_MENU_WIDTH, ((MenuElement)menuElements.get(menuElements.size() - 4)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 4)).getHeight() + CFG.PADDING, true));
      int tempPosY = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 + ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3;
      this.initMenu((SliderMenuTitle)null, 0 + AoCGame.LEFT, tempPosY, CFG.CIV_INFO_MENU_WIDTH, Math.min(CFG.GAME_HEIGHT - tempPosY - CFG.map.getMapBG().getMinimapHeight() - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT - CFG.PADDING, ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2), menuElements, false, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Population"));
      this.getMenuElement(2).setText(CFG.langManager.get("Capital"));
      this.getMenuElement(4).setText(CFG.langManager.get("LargestCity"));
      this.getMenuElement(7).setText(CFG.langManager.get("TechnologyLevel"));
      this.getMenuElement(9).setText(CFG.langManager.get("Economy"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_CivInfo.lTime + 175L >= System.currentTimeMillis()) {
         if (Menu_InGame_CivInfo.hideAnimation) {
            iTranslateX -= (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0F));
         } else {
            iTranslateX += -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (Menu_InGame_CivInfo.hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + 2, true, true);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);
      CFG.drawRect_InfoBox_Left(oSB, this.getMenuElement(0).getPosX() - CFG.PADDING + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING + iTranslateY, this.getMenuElement(1).getPosX() - this.getMenuElement(0).getPosX() + this.getMenuElement(1).getWidth() + CFG.PADDING * 2, this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2);

      try {
         if (CFG.activeCivLeader != null) {
            float fScale = ((float)(this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2) - 2.0F) / (float)CFG.activeCivLeader.getHeight();
            CFG.activeCivLeader.draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING - CFG.activeCivLeader.getHeight() + 1 + iTranslateY, (int)((float)CFG.activeCivLeader.getWidth() * fScale), this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2 - 2);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.575F));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + 1 + iTranslateY, (int)((float)CFG.activeCivLeader.getWidth() * fScale), CFG.PADDING);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + 1 + (this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2 - 2) - CFG.PADDING + iTranslateY, (int)((float)CFG.activeCivLeader.getWidth() * fScale), CFG.PADDING, false, true);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING - ImageManager.getImage(Images.slider_gradient).getHeight() + 1 + iTranslateY, CFG.PADDING, this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2 - 2);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + (int)((float)CFG.activeCivLeader.getWidth() * fScale) - CFG.PADDING + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING - ImageManager.getImage(Images.slider_gradient).getHeight() + 1 + iTranslateY, CFG.PADDING, this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2 - 2, true, false);
            oSB.setColor(new Color(CFG.COLOR_TEXT_NUM_OF_PROVINCES.r, CFG.COLOR_TEXT_NUM_OF_PROVINCES.g, CFG.COLOR_TEXT_NUM_OF_PROVINCES.b, 0.175F));
            CFG.drawRect(oSB, this.getPosX() + this.getMenuElement(0).getPosX() - CFG.PADDING + 1 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - CFG.PADDING + iTranslateY, (int)((float)CFG.activeCivLeader.getWidth() * fScale) - 1, this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() - this.getMenuElement(0).getPosY() + CFG.PADDING * 2 - 2);
            oSB.setColor(Color.WHITE);
         }
      } catch (NullPointerException var6) {
      }

      CFG.drawRect_InfoBox_Left(oSB, this.getMenuElement(7).getPosX() - CFG.PADDING + iTranslateX, this.getMenuPosY() + this.getMenuElement(7).getPosY() - CFG.PADDING + iTranslateY, this.getMenuElement(8).getPosX() - this.getMenuElement(7).getPosX() + this.getMenuElement(8).getWidth() + CFG.PADDING * 2, this.getMenuElement(9).getPosY() + this.getMenuElement(9).getHeight() - this.getMenuElement(7).getPosY() + CFG.PADDING * 2);
      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      if (AoCGame.LEFT != 0) {
         oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight() + 2, true, true);
         oSB.setColor(Color.WHITE);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_CivInfo();
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
         case 1:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_POPULATION_OF_CIV_MODE);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_POPULATION_OF_CIV_MODE) {
               CFG.toast.setInView(CFG.langManager.get("Population"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
            break;
         case 2:
         case 3:
            try {
               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID())) {
                     CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());
                     CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());
                  }
               } else {
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());
                  CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());
               }
            } catch (IndexOutOfBoundsException var4) {
            }
            break;
         case 4:
         case 5:
            try {
               int nLargestCity = CFG.game.getCiv(CFG.getActiveCivInfo()).getProvinceID(0);

               for(int i = 1; i < CFG.game.getCiv(CFG.getActiveCivInfo()).getNumOfProvinces(); ++i) {
                  if (CFG.game.getProvince(nLargestCity).getPopulationData().getPopulation() < CFG.game.getProvince(CFG.game.getCiv(CFG.getActiveCivInfo()).getProvinceID(i)).getPopulationData().getPopulation()) {
                     nLargestCity = CFG.game.getCiv(CFG.getActiveCivInfo()).getProvinceID(i);
                  }
               }

               if (CFG.FOG_OF_WAR == 2) {
                  if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(nLargestCity)) {
                     CFG.map.getMapCoordinates().centerToProvinceID(nLargestCity);
                     CFG.game.setActiveProvinceID(nLargestCity);
                  }
               } else {
                  CFG.map.getMapCoordinates().centerToProvinceID(nLargestCity);
                  CFG.game.setActiveProvinceID(nLargestCity);
               }
            } catch (IndexOutOfBoundsException var5) {
            }
            break;
         case 6:
            //if no leader, auto-set to invis
            if (CFG.activeCivLeader != null) {
               this.getMenuElement(this.getMenuElementsSize() - 1).setVisible(false);
               this.getMenuElement(this.getMenuElementsSize() - 1).menuElementHover = null;
            }
            break;
         case 7:
         case 8:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_TECHNOLOGY_MODE, false);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_TECHNOLOGY_MODE) {
               CFG.toast.setInView(CFG.langManager.get("TechnologyLevel"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
            break;
         case 9:
         case 10:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_ECONOMY_MODE);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_ECONOMY_MODE) {
               CFG.toast.setInView(CFG.langManager.get("Economy"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
            break;
         case 11:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_HAPPINESS_MODE);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_HAPPINESS_MODE) {
               CFG.toast.setInView(CFG.langManager.get("Happiness"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
            break;
         case 12:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_IDEOLOGIES_MODE);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_IDEOLOGIES_MODE) {
               CFG.toast.setInView(CFG.langManager.get("Governments"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
            break;
         case 13:
            CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_PROVINCE_STABILITY_MODE);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_PROVINCE_STABILITY_MODE) {
               CFG.toast.setInView(CFG.langManager.get("ProvinceStability"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            }
      }

   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
      }

   }
}
