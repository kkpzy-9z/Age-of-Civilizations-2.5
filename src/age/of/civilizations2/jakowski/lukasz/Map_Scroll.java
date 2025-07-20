package age.of.civilizations2.jakowski.lukasz;

class Map_Scroll {
   protected static final float SCROLL_SLOW = 0.97F;
   private boolean scrollingTheMap = false;
   private int iScrollPosX;
   private int iScrollPosY;
   private int iScrollPosX2 = -1;
   private int iScrollPosY2 = -1;
   private float fScrollNewPosX;
   private float fScrollNewPosY;
   private long moveMapTime = 0L;
   private boolean moveMapDirection = false;
   private int iStepID = 0;
   private int iScrollEvent_PosX;
   private int iScrollEvent_PosY;
   private boolean scrollEvent = false;
   private int iPlayerID = 0;
   private boolean enableBackgroundAnimation = false;
   private BackgroundAnimation backgroundAnimation = new BackgroundAnimation() {
      public void updateBackgroundAnimation() {
      }
   };
   private ReverseDirection reverseDirectionX = null;
   private ReverseDirection reverseDirectionY = null;

   protected final void updateEnableBackroundAnimation() {
      this.enableBackgroundAnimation = !CFG.menuManager.getInGameView() && !CFG.menuManager.getInSelectCiv() && !CFG.menuManager.getInCreateScenario_Civilizations() && !CFG.menuManager.getInCreateScenario_Assign() && !CFG.menuManager.getInCreateScenario_Assign_Select() && !CFG.menuManager.getInCreateScenario_Civilizations_Select() && !CFG.menuManager.getInCreateScenario_WastelandMap() && !CFG.menuManager.getInCreateScenario_Available_Provinces() && !CFG.menuManager.getInCreateScenario_SetUpArmy() && !CFG.menuManager.getInCreateScenario_TechnologyLevels() && !CFG.menuManager.getInCreateScenario_Preview() && !CFG.menuManager.getInCreateScenario_PalletOfColors() && !CFG.menuManager.getInCreateScenario_StartingMoney() && !CFG.menuManager.getInCreateScenario_Happiness() && !CFG.menuManager.getInMainMenu() && !CFG.menuManager.getInAboutMenu() && !CFG.menuManager.getInStartGameMenu() && !CFG.menuManager.getInEndGameMenu() && !CFG.menuManager.getInCreateNewGame() && !CFG.menuManager.getInManageDiplomacy() && !CFG.menuManager.getInLoadMap() && !CFG.menuManager.getInLoadSave() && !CFG.menuManager.getInSelectMapType() && !CFG.menuManager.getInCustomizeAlliance() && !CFG.menuManager.getInSelectAvailableCivilizations() && !CFG.menuManager.getInCreateCivilization() && !CFG.menuManager.getInCreateCity() && !CFG.menuManager.getInGame_PeaceTreaty() && !CFG.menuManager.getInGame_PeaceTreaty_Response() && !CFG.menuManager.getInMapEditor_Create_NewContinent() && !CFG.menuManager.getInGameEditor_Create_DiplomacyPackage() && !CFG.menuManager.getInGameEditor_TerrainAdd() && !CFG.menuManager.getInGameEditor_ReligionAdd() && !CFG.menuManager.getInChooseScenario() && !CFG.menuManager.getInSettingsProvince() && !CFG.menuManager.getInMapEditor_Terrain() && !CFG.menuManager.getInMapEditor_Continents() && !CFG.menuManager.getInMapEditor_GrowthRate() && !CFG.menuManager.getInMapEditor_ArmyPosition() && !CFG.menuManager.getInMapEditor_TradeZones() && !CFG.menuManager.getInMapEditor_TradeZones_Edit() && !CFG.menuManager.getInMapEditor_WastelandMaps_Edit() && !CFG.menuManager.getInMapEditor_ArmySeaBoxes() && !CFG.menuManager.getInMapEditor_ArmySeaBoxes_Edit() && !CFG.menuManager.getInMapEditor_ArmySeaBoxes_Add() && !CFG.menuManager.getInMapEditor_Connections() && !CFG.menuManager.getInMapEditor_ProvinceBackground() && !CFG.menuManager.getInMapEditor_SeaProvinces() && !CFG.menuManager.getInMapEditor_PortPosition() && !CFG.menuManager.getInGame_Timeline() && !CFG.menuManager.getInMapEditor_Create_NewRegion() && !CFG.menuManager.getInMapEditor_Regions() && !CFG.menuManager.getInDownloadPallets() && !CFG.menuManager.getInSelectLanguage() && !CFG.menuManager.getInMapEditor_LoadSuggestedOwners() && !CFG.menuManager.getInMapEditor_LoadPreDefinedBorders() && !CFG.menuManager.getInCreateScenario_Cores() && !CFG.menuManager.getInPalletOfCivsColorsEdit() && !CFG.menuManager.getInCreateScenario_Events_SelectProvinces() && !CFG.menuManager.getInGameEditor_Regions() && !CFG.menuManager.getInNextPlayerTurn() && !CFG.menuManager.getInVictory() && !CFG.menuManager.getInGame_CivlizationView() && !CFG.menuManager.getInPrintAMap() && !CFG.menuManager.getInRandomGame() && !CFG.menuManager.getInRandomGame_Civilizations_Select() && !CFG.menuManager.getCreateScenario_ScenarioAge() && !CFG.menuManager.getInCreateScenario_HolyRomanEmpire() && !CFG.menuManager.getInMapEditor_FormableCivs_Edit() && !CFG.menuManager.getInGame_CreateAVassal() && !CFG.menuManager.getInGame_SelectProvinces() && !CFG.menuManager.getInGame_ShowProvinces() && !CFG.menuManager.getInGame_TradeSelectCiv() && !CFG.menuManager.getInMapEditor_FormableCivs_SelectFormable() && !CFG.menuManager.getInMapEditor_FormableCivs_SelectClaimant() && !CFG.menuManager.getInGame_Formable_Civ_Provinces() && !CFG.menuManager.getInGame_FormAnimation();
      if (this.enableBackgroundAnimation) {
         if (CFG.menuManager.getInNewGamePlayers()) {
            this.backgroundAnimation = new BackgroundAnimation() {
               public void updateBackgroundAnimation() {
                  if (!CFG.map.getMapTouchManager().getActionMap() && CFG.game.getPlayersSize() > 1 && CFG.menuManager.getInNewGamePlayers()) {
                     try {
                        if (CFG.game.getPlayer(Map_Scroll.this.iPlayerID).getCivID() < 0) {
                           ++Map_Scroll.this.iPlayerID;
                           if (Map_Scroll.this.iPlayerID >= CFG.game.getPlayersSize()) {
                              Map_Scroll.this.iPlayerID = 0;
                           }

                           return;
                        }
                     } catch (IndexOutOfBoundsException var3) {
                        Map_Scroll.this.iPlayerID = 0;
                        return;
                     }

                     if (Map_Scroll.this.moveMapTime <= System.currentTimeMillis() - 2500L) {
                        try {
                           Map_Scroll.this.setScrollEvent(CFG.game.getCiv(CFG.game.getPlayer(Map_Scroll.this.iPlayerID).getCivID()).getCapitalProvinceID());
                           Map_Scroll.this.iPlayerID++;
                        } catch (IndexOutOfBoundsException var2) {
                           Map_Scroll.this.iPlayerID = 0;
                           Map_Scroll.this.setScrollEvent(CFG.game.getCiv(CFG.game.getPlayer(Map_Scroll.this.iPlayerID).getCivID()).getCapitalProvinceID());
                        }

                        if (Map_Scroll.this.iPlayerID >= CFG.game.getPlayersSize()) {
                           Map_Scroll.this.iPlayerID = 0;
                        }
                     }
                  }

               }
            };
         } else {
            this.backgroundAnimation = new BackgroundAnimation() {
               public void updateBackgroundAnimation() {
               }
            };
         }
      } else {
         this.backgroundAnimation = new BackgroundAnimation() {
            public void updateBackgroundAnimation() {
            }
         };
      }

   }

   protected Map_Scroll() {
      this.buildReverseDirectionX();
      this.buildReverseDirectionY();
   }

   protected final void update() {
      if (this.scrollEvent) {
         if (this.iStepID < 14) {
            CFG.map.getMapCoordinates().setNewPosX(CFG.map.getMapCoordinates().getPosX() - (int)changeAnimationPos(this.iStepID, this.iScrollEvent_PosX));
            CFG.map.getMapCoordinates().setNewPosY(CFG.map.getMapCoordinates().getPosY() - (int)changeAnimationPos(this.iStepID++, this.iScrollEvent_PosY));
            if (this.iStepID == 14) {
               this.moveMapTime = System.currentTimeMillis();
               this.scrollEvent = false;
            }
         }
      } else if (this.scrollingTheMap && !CFG.map.getMapCoordinates().getDisableMovingMap()) {
         if (!(Math.abs(this.fScrollNewPosX) > 1.0F) && !(Math.abs(this.fScrollNewPosY) > 1.0F)) {
            this.stopScrollingTheMap();
         } else {
            if (Math.abs(this.fScrollNewPosX) > 1.0F) {
               CFG.map.getMapCoordinates().setNewPosX(this.reverseDirectionX.getNewPos((int)this.fScrollNewPosX));
               this.fScrollNewPosX *= 0.97F;
            }

            if (Math.abs(this.fScrollNewPosY) > 1.0F) {
               CFG.map.getMapCoordinates().setNewPosY(this.reverseDirectionY.getNewPos((int)this.fScrollNewPosY));
               this.fScrollNewPosY *= 0.97F;
            }
         }
      } else {
         this.backgroundAnimation.updateBackgroundAnimation();
      }

   }

   protected static float changeAnimationPos(final int animationStepID, final int nWidth) {
      switch (animationStepID) {
         case 0:
         case 1:
         case 12:
         case 13: {
            return nWidth * 2.5f / 100.0f;
         }
         case 2:
         case 3:
         case 10:
         case 11: {
            return nWidth * 5.0f / 100.0f;
         }
         case 4:
         case 5:
         case 8:
         case 9: {
            return nWidth * 10.0f / 100.0f;
         }
         case 6:
         case 7: {
            return nWidth * 15.0f / 100.0f;
         }
         default: {
            return 0.0f;
         }
      }
   }

   protected final void startScrollingTheMap() {
      if (this.iScrollPosX2 >= 0 || this.iScrollPosY2 >= 0) {
         if ((float)Math.abs(this.iScrollPosX - this.iScrollPosX2) > (CFG.isDesktop() ? (float)CFG.PADDING * 1.5F : 4.0F) * CFG.DENSITY) {
            this.fScrollNewPosX = (float)(this.iScrollPosX - this.iScrollPosX2) * 1.25F * (float)(CFG.reverseDirectionX ? 1 : -1);
            this.scrollingTheMap = true;
         }

         if ((float)Math.abs(this.iScrollPosY - this.iScrollPosY2) > (CFG.isDesktop() ? (float)CFG.PADDING * 1.5F : 4.0F) * CFG.DENSITY) {
            this.fScrollNewPosY = (float)(this.iScrollPosY - this.iScrollPosY2) * 1.25F * (float)(CFG.reverseDirectionY ? 1 : -1);
            this.scrollingTheMap = true;
         }
      }

      if (this.iScrollPosX != this.iScrollPosX2) {
         this.updateMoveMapDirection(this.iScrollPosX > this.iScrollPosX2);
      }

      this.resetScrollInfo();
   }

   protected final void stopScrollingTheMap() {
      this.scrollingTheMap = false;
      this.resetScrollInfo();
      this.scrollEvent = false;
   }

   protected final void updateMoveMapDirection(boolean moveMapDirection) {
      this.moveMapDirection = moveMapDirection;
      this.moveMapTime = 0L;
   }

   protected final void resetScrollInfo() {
      this.iScrollPosX = this.iScrollPosY = this.iScrollPosX2 = this.iScrollPosY2 = -1;
   }

   protected final void setScrollEvent(int nProvinceID) {
      this.setScrollEvent_Pos((int)((float)(CFG.map.getMapCoordinates().getPosX() + CFG.game.getProvince(nProvinceID).getCenterX()) - (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() / 2.0F), (int)((float)(CFG.map.getMapCoordinates().getPosY() + CFG.game.getProvince(nProvinceID).getCenterY()) - (float)CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale() / 2.0F));
   }

   protected final void setScrollEvent_ToPosition(int nPosX, int nPosY) {
      this.setScrollEvent_Pos((int)((float)(CFG.map.getMapCoordinates().getPosX() + nPosX) - (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() / 2.0F), (int)((float)(CFG.map.getMapCoordinates().getPosY() + nPosY) - (float)CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale() / 2.0F));
   }

   private final void setScrollEvent_Pos(int nPosX, int nPosY) {
      if (!this.scrollEvent) {
         this.scrollEvent = true;
         this.iStepID = 0;
         this.iScrollEvent_PosX = nPosX;
         this.iScrollEvent_PosY = nPosY;
         this.moveMapTime = System.currentTimeMillis() + 208L;
      }
   }

   protected final void buildReverseDirectionX() {
      if (CFG.reverseDirectionX) {
         this.reverseDirectionX = new ReverseDirection() {
            public int getNewPos(int nPosX) {
               return CFG.map.getMapCoordinates().getNewPosX() + nPosX;
            }
         };
      } else {
         this.reverseDirectionX = new ReverseDirection() {
            public int getNewPos(int nPosX) {
               return CFG.map.getMapCoordinates().getNewPosX() - nPosX;
            }
         };
      }

   }

   protected final void buildReverseDirectionY() {
      if (CFG.reverseDirectionY) {
         this.reverseDirectionY = new ReverseDirection() {
            public int getNewPos(int nPosY) {
               return CFG.map.getMapCoordinates().getNewPosY() + nPosY;
            }
         };
      } else {
         this.reverseDirectionY = new ReverseDirection() {
            public int getNewPos(int nPosY) {
               return CFG.map.getMapCoordinates().getNewPosY() - nPosY;
            }
         };
      }

   }

   protected final void setScrollPos(int nPosX, int nPosY) {
      this.iScrollPosX2 = this.iScrollPosX;
      this.iScrollPosY2 = this.iScrollPosY;
      this.iScrollPosX = nPosX;
      this.iScrollPosY = nPosY;
   }

   protected boolean getScrollingTheMap() {
      return this.scrollingTheMap;
   }

   interface BackgroundAnimation {
      void updateBackgroundAnimation();
   }

   private interface ReverseDirection {
      int getNewPos(int var1);
   }
}
