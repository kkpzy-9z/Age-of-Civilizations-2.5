package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Game_Render_Province {
   protected static DrawProvinces drawProvinces;
   protected static long PROVINCE_COLOR_ANIMATION_TIMER = 0L;
   protected static final float ALPHA_PEACE_TREATY_PROVINCES = 0.25F;

   protected static final Color getProvince_PortColor(int nProvinceID) {
      switch (CFG.game.getProvince(nProvinceID).getLevelOfPort()) {
         case -1:
            return CFG.COLOR_PORT_m1;
         case 0:
            return CFG.COLOR_PORT_0;
         default:
            return CFG.COLOR_PORT_1;
      }
   }

   protected static final Color getProvince_FortColor(int nProvinceID) {
      switch (CFG.game.getProvince(nProvinceID).getLevelOfFort()) {
         case -1:
         case 0:
            return CFG.COLOR_PORT_m1;
         case 1:
            return CFG.COLOR_FORT_1;
         default:
            return CFG.COLOR_FORT_2;
      }
   }

   protected static final Color getProvince_WatchTowerColor(int nProvinceID) {
      switch (CFG.game.getProvince(nProvinceID).getLevelOfFort()) {
         case -1:
         case 0:
            return CFG.COLOR_PORT_m1;
         default:
            return CFG.COLOR_WATCH_TOWER;
      }
   }

   protected static final void updateDrawProvinces() {
      if (CFG.menuManager.getInGameView()) {
         if (CFG.viewsManager.getActiveViewID() >= 0) {
            drawProvinces = CFG.viewsManager.getActiveView().drawProvinces;
         } else {
            updateDrawProvinces_Standard();
         }
      } else if (CFG.menuManager.getInCreateNewGame()) {
         if (CFG.viewsManager.getActiveViewID() >= 0) {
            drawProvinces = CFG.viewsManager.getActiveView().drawProvinces;
         } else {
            updateDrawProvinces_Standard();
         }
      } else if (CFG.menuManager.getInGame_TradeSelectCiv()) {
         updateDrawProvinces_Standard();
      } else if (CFG.menuManager.getInGame_CreateAVassal()) {
         if (CFG.viewsManager.getActiveViewID() >= 0) {
            drawProvinces = CFG.viewsManager.getActiveView().drawProvinces;
         } else if (CFG.FOG_OF_WAR == 2) {
            if (!CFG.VIEW_SHOW_VALUES) {
               drawProvinces = new DrawProvinces() {
                  public void draw(SpriteBatch oSB) {
                     for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i))) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                              if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince()) {
                                 CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
                              } else {
                                 oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                              }

                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                              oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        } else {
                           oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }

                  }
               };
            } else {
               updateDrawProvinces_Standard();
            }
         } else if (!CFG.VIEW_SHOW_VALUES) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                     if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince()) {
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
                        } else {
                           oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                        }

                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                        oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

               }
            };
         } else {
            updateDrawProvinces_Standard();
         }
      } else if (CFG.menuManager.getInGame_SelectProvinces()) {
         if (CFG.FOG_OF_WAR == 2) {
            if (!CFG.VIEW_SHOW_VALUES) {
               drawProvinces = new DrawProvinces() {
                  public void draw(SpriteBatch oSB) {
                     for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i))) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID) {
                              if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince()) {
                                 CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
                              } else {
                                 oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                              }

                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                              oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        } else {
                           oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }

                  }
               };
            } else {
               updateDrawProvinces_Standard();
            }
         } else if (!CFG.VIEW_SHOW_VALUES) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                     if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince()) {
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
                        } else {
                           oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                        }

                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                        oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.5F));
                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

               }
            };
         } else {
            updateDrawProvinces_Standard();
         }
      } else if (CFG.menuManager.getInGame_ShowProvinces()) {
         if (CFG.FOG_OF_WAR == 2) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                     if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i))) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID) {
                           oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.7F));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                           oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.7F));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     } else {
                        oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

               }
            };
         } else {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                     if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID) {
                        oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.7F));
                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                        oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.7F));
                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

               }
            };
         }
      } else if (CFG.menuManager.getInManageDiplomacy()) {
         if (CFG.menuManager.getInManageDiplomacy_Pacts3()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  int i;
                  int tempRelation;
                  if (CFG.game.getActiveProvinceID() >= 0 && (CFG.game.getActiveProvinceID() < 0 || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0)) {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getCivNonAggressionPact(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) > 0) {
                              oSB.setColor(CFG.getPactColor(CFG.game.getCivNonAggressionPact(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()), CFG.ALPHA_DIPLOMACY));
                           } else {
                              tempRelation = (int)CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                              if (tempRelation == 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              } else {
                                 oSB.setColor(CFG.getRelationColor(tempRelation, CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F + CFG.ALPHA_DIPLOMACY * 2.0F / 5.0F * ((float)Math.abs(tempRelation) / 100.0F)));
                              }
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  } else if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 < 0) {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  } else {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getCivNonAggressionPact(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) > 0) {
                              oSB.setColor(CFG.getPactColor(CFG.game.getCivNonAggressionPact(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1), CFG.ALPHA_DIPLOMACY));
                           } else {
                              tempRelation = (int)CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1);
                              if (tempRelation == 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              } else {
                                 oSB.setColor(CFG.getRelationColor(tempRelation, CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F + CFG.ALPHA_DIPLOMACY * 2.0F / 5.0F * ((float)Math.abs(tempRelation) / 100.0F)));
                              }
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  }

               }
            };
         } else if (CFG.menuManager.getInManageDiplomacy_Truces()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  int i;
                  int tempRelation;
                  if (CFG.game.getActiveProvinceID() >= 0 && (CFG.game.getActiveProvinceID() < 0 || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0)) {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getCivTruce(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) > 0) {
                              oSB.setColor(CFG.getTruceColor(CFG.ALPHA_DIPLOMACY));
                           } else {
                              tempRelation = (int)CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                              if (tempRelation == 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              } else {
                                 oSB.setColor(CFG.getRelationColor(tempRelation, CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F + CFG.ALPHA_DIPLOMACY * 2.0F / 5.0F * ((float)Math.abs(tempRelation) / 100.0F)));
                              }
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  } else if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 < 0) {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  } else {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getCivTruce(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) > 0) {
                              oSB.setColor(CFG.getTruceColor(CFG.ALPHA_DIPLOMACY));
                           } else {
                              tempRelation = (int)CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1);
                              if (tempRelation == 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              } else {
                                 oSB.setColor(CFG.getRelationColor(tempRelation, CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F + CFG.ALPHA_DIPLOMACY * 2.0F / 5.0F * ((float)Math.abs(tempRelation) / 100.0F)));
                              }
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  }

               }
            };
         } else if (CFG.menuManager.getInManageDiplomacy_Guarantee()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  int i;
                  if (CFG.game.getActiveProvinceID() < 0 || CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == 0) {
                     if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 < 0) {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     } else {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                              } else if (CFG.game.getGuarantee(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getB(), CFG.ALPHA_DIPLOMACY));
                              } else {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              }

                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     }
                  } else {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getGuarantee(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.getB(), CFG.ALPHA_DIPLOMACY));
                           } else {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  }

               }
            };
         } else if (CFG.menuManager.getInManageDiplomacy_DefensivePact()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  int i;
                  if (CFG.game.getActiveProvinceID() < 0 || CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == 0) {
                     if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 < 0) {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     } else {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                              } else if (CFG.game.getDefensivePact(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getB(), CFG.ALPHA_DIPLOMACY));
                              } else {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              }

                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     }
                  } else {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getDefensivePact(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.getB(), CFG.ALPHA_DIPLOMACY));
                           } else {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  }

               }
            };
         } else if (CFG.menuManager.getInManageDiplomacy_MilitaryAccess()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  int i;
                  if (CFG.game.getActiveProvinceID() < 0 || CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == 0) {
                     if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 < 0) {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     } else {
                        for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                              if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                              } else if (CFG.game.getMilitaryAccess(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getB(), CFG.ALPHA_DIPLOMACY));
                              } else {
                                 oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                              }

                              CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                           }
                        }
                     }
                  } else {
                     for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (CFG.game.getMilitaryAccess(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) > 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.getB(), CFG.ALPHA_DIPLOMACY));
                           } else {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }
                  }

               }
            };
         } else if (CFG.menuManager.getInManageDiplomacy_Relations_Interactive()) {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
                  for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                     if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID) {
                           oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                        } else {
                           int tempRelation = (int)CFG.game.getCivRelation_OfCivB(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID());
                           if (tempRelation == 0) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           } else {
                              oSB.setColor(CFG.getRelationColor(tempRelation, CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F + CFG.ALPHA_DIPLOMACY * 2.0F / 5.0F * ((float)Math.abs(tempRelation) / 100.0F)));
                           }
                        }

                        CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                     }
                  }

               }
            };
         } else if (!CFG.menuManager.getInGame_Timeline() && !CFG.menuManager.getInVictory()) {
            if (CFG.menuManager.getInManageDiplomacy_Vassals()) {
               drawProvinces = new DrawProvinces() {
                  public void draw(SpriteBatch oSB) {
                     int nActiveCivID = 0;
                     if (CFG.game.getActiveProvinceID() >= 0) {
                        nActiveCivID = CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getPuppetOfCivID();
                     }

                     for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                           if (nActiveCivID == CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.getB(), CFG.ALPHA_DIPLOMACY));
                           } else if (nActiveCivID == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getPuppetOfCivID()) {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.getB(), CFG.ALPHA_DIPLOMACY));
                           } else {
                              oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                           }

                           CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                        }
                     }

                  }
               };
            } else if (CFG.menuManager.getInManageDiplomacy_Alliances()) {
               updateDrawProvinces_ManageDiplomacyAlliances();
            } else {
               updateDrawProvinces_Standard();
            }
         } else {
            drawProvinces = new DrawProvinces() {
               public void draw(SpriteBatch oSB) {
               }
            };
         }
      } else if (CFG.menuManager.getInCustomizeAlliance()) {
         updateDrawProvinces_ManageDiplomacyAlliances();
      } else if (CFG.menuManager.getInMapEditor_Create_NewContinent()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color(CFG.editor_Continent_GameData.getR(), CFG.editor_Continent_GameData.getG(), CFG.editor_Continent_GameData.getB(), 0.7F));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInMapEditor_Create_NewRegion()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color(CFG.editor_Region_GameData.getR(), CFG.editor_Region_GameData.getG(), CFG.editor_Region_GameData.getB(), 0.45F));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInGameEditor_Create_DiplomacyPackage()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color(CFG.menuManager.getColorPicker().getActiveColor().r, CFG.menuManager.getColorPicker().getActiveColor().g, CFG.menuManager.getColorPicker().getActiveColor().b, CFG.ALPHA_DIPLOMACY));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInCreateScenario_TechnologyLevels()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                     oSB.setColor(CFG.getTechnologyLevelColor((int)(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getTechnologyLevel() * (float)CFG.getCreateScenario_TechnologyLevelsByContinents_Continent(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() - 1, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getRegion())), CFG.PROVINCE_ALPHA_TECHNOLOGY_LEVEL));
                     CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

            }
         };
      } else if (CFG.menuManager.getInCreateScenario_Happiness()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() > 0) {
                     oSB.setColor(CFG.getColorStep(CFG.COLOR_TEXT_HAPPINESS_MIN, CFG.COLOR_TEXT_HAPPINESS_MAX, CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getHappiness(), 100, 0.5F));
                     CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

            }
         };
      } else if (CFG.menuManager.getInCreateScenario_StartingMoney()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  int tempMoney = (int)(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getMoney() == -999999L ? (long)CFG.game.getGameScenarios().getScenario_StartingMoney() : CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getMoney());
                  if (tempMoney < 0) {
                     oSB.setColor(CFG.getColorStep(CFG.COLOR_STARTINGMONEY_0, CFG.COLOR_STARTINGMONEY_MIN, -tempMoney, 100000, CFG.PROVINCE_ALPHA_TECHNOLOGY_LEVEL));
                  } else {
                     oSB.setColor(CFG.getColorStep(CFG.COLOR_STARTINGMONEY_0, CFG.COLOR_STARTINGMONEY_MAX, tempMoney, 100000, CFG.PROVINCE_ALPHA_TECHNOLOGY_LEVEL));
                  }

                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInEditor_GameCivs()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color((float)CFG.editorCivilization_GameData.getR() / 255.0F, (float)CFG.editorCivilization_GameData.getG() / 255.0F, (float)CFG.editorCivilization_GameData.getB() / 255.0F, CFG.ALPHA_DIPLOMACY));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInCreateCivilization()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color((float)CFG.editorCivilization_GameData.getR() / 255.0F, (float)CFG.editorCivilization_GameData.getG() / 255.0F, (float)CFG.editorCivilization_GameData.getB() / 255.0F, CFG.ALPHA_DIPLOMACY));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInRandomGame()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
            }
         };
      } else if (CFG.menuManager.getInGameEditor_TerrainAdd()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color(CFG.editorTerrain_Data2.getColor().getR(), CFG.editorTerrain_Data2.getColor().getG(), CFG.editorTerrain_Data2.getColor().getB(), 0.55F));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInMapEditor_Terrain()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  oSB.setColor(CFG.terrainTypesManager.getColor(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTerrainTypeID()));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInMapEditor_Continents()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  oSB.setColor(CFG.map.getMapContinents().getColor(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getContinent()));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInMapEditor_Regions()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  oSB.setColor(CFG.map.getMapRegions().getColor(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getRegion()));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInMapEditor_GrowthRate()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  oSB.setColor(CFG.getGrowthRateColor((int)(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getGrowthRate_Population() * 100.0F), 0.75F));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else if (CFG.menuManager.getInPrintAMap()) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));

               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

            }
         };
      } else {
         updateDrawProvinces_Standard();
      }

   }

   private static final void updateDrawProvinces_Standard() {
      if (CFG.FOG_OF_WAR == 2) {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor_FoG_Discovery(oSB);
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }

               Game_Render_Province.drawOccupiedProvinces_FogOfWar(oSB);
            }
         };
      } else {
         drawProvinces = new DrawProvinces() {
            public void draw(SpriteBatch oSB) {
               for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                  if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                     CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
                     CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
                  }
               }

               Game_Render_Province.drawOccupiedProvinces(oSB);
            }
         };
      }

   }

   private static final void updateDrawProvinces_ManageDiplomacyAlliances() {
      drawProvinces = new DrawProvinces() {
         public void draw(SpriteBatch oSB) {
            for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
               if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
                  if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getAllianceID() == 0) {
                     oSB.setColor(new Color(CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getR(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getG(), CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.getB(), CFG.ALPHA_DIPLOMACY * 3.0F / 5.0F));
                  } else {
                     oSB.setColor(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getAllianceID()).getColorOfAlliance().getR(), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getAllianceID()).getColorOfAlliance().getG(), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getAllianceID()).getColorOfAlliance().getB(), CFG.ALPHA_DIPLOMACY * 1.25F);
                  }

                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }
            }

         }
      };
   }

   protected static final void drawProvinces(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawOccupiedProvinces(SpriteBatch oSB) {
      oSB.setShader(AoCGame.shaderAlpha2);

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince()) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawOccupiedProvinces_FogOfWar(SpriteBatch oSB) {
      oSB.setShader(AoCGame.shaderAlpha2);

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getTrueOwnerOfProvince() && CFG.getMetProvince(CFG.game.getProvinceInViewID(i))) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawProvinces_NextPlayer_Turn(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvinces_CivilizationView(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == Menu_InGame_CivilizationView.iCivID) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvinces_CivilizationView_FogOfWar(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i)) && CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == Menu_InGame_CivilizationView.iCivID) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvinces_FormableCiv(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.formableCivs_GameData.getProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getDrawProvince() && CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getWasteland() < 0) {
            if (CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getCivID() == CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getCivID()) {
               CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).setProvinceColor(oSB);
            } else {
               oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.85F));
            }

            CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).drawLandProvince(oSB);
            CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).setDrawProvince(false);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getDrawProvince() && CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getCivID()) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.35F));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }

         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setDrawProvince(true);
      }

   }

   protected static final void drawProvinces_FormableCiv_FogOfWarDiscovery(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.formableCivs_GameData.getProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getDrawProvince() && CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getWasteland() < 0) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.formableCivs_GameData.getProvinceID(i)) && CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).getCivID() == CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getCivID()) {
               CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).setProvinceColor(oSB);
            } else if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.formableCivs_GameData.getProvinceID(i))) {
               oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.85F));
            } else {
               oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.75F));
            }

            CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).drawLandProvince(oSB);
            CFG.game.getProvince(CFG.formableCivs_GameData.getProvinceID(i)).setDrawProvince(false);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i)) && CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getDrawProvince() && CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getCivID()) {
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.35F));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }

         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setDrawProvince(true);
      }

   }

   protected static final void drawProvinces_LoadAI_RTO(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      int i;
      if (CFG.FOG_OF_WAR == 2) {
         for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         }
      } else {
         for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getControlledByPlayer()) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         }
      }

   }

   protected static final void drawProvinces_Timeline(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         if ((Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getWastelandProvinceInViewID(i)) > 0) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).setCivilizationProvinceColor(oSB, (Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getWastelandProvinceInViewID(i)));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if ((Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getProvinceInViewID(i)) > 0) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, (Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getProvinceInViewID(i)));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.shaderAlpha2);

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if ((Boolean)CFG.timelapseManager.timelineOwners_IsOccupied.get(CFG.game.getProvinceInViewID(i))) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawProvinces_Timeline_FogOfWar(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         if ((Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getWastelandProvinceInViewID(i)) > 0) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).setCivilizationProvinceColor(oSB, (Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getWastelandProvinceInViewID(i)));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getProvinceInViewID(i))) {
            if ((Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getProvinceInViewID(i)) > 0) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, (Integer)CFG.timelapseManager.timelineOwners.get(CFG.game.getProvinceInViewID(i)));
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         } else {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.shaderAlpha2);

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if ((Boolean)CFG.timelapseManager.timelineOwners_IsOccupied.get(CFG.game.getProvinceInViewID(i))) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawProvincesBorder_Timeline(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_Timeline(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_Timeline(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Timeline_OnlyCivilizationBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_Timeline_Only_CivilizationBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_Timeline_Only_CivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_PeaceTreaty(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_Wasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_PeaceTreaty_Only_CivilizationBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_Wasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_Only_CivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_PeaceTreaty_FogOfWarDiscovery(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_Wasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_FogOfWarDiscovery(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_PeaceTreaty_FogOfWarDiscovery_Only_CivilizationBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_Wasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_PeaceTreaty_FogOfWarDiscovery_Only_CivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvinces_PeaceTreaty(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID != 0) {
            if (Math.abs(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID) == CFG.peaceTreatyData.iBrushCivID) {
               //if brush/selected civ is being drawn, draw as slightly less opaque
               oSB.setColor(new Color((float)CFG.game.getCiv(Math.abs(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID)).getR() / 255.0F, (float)CFG.game.getCiv(Math.abs(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID)).getG() / 255.0F, (float)CFG.game.getCiv(Math.abs(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID)).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 1.00F));
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            } else if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID < 0) {
               oSB.setColor(new Color((float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getR() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getG() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.90F));
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            } else if (!((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isToTake || ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isTaken > 0) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID);
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            } else {
               //else add slightly opaque country color (occupied province)
               oSB.setColor(new Color((float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getR() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getG() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.75F));
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
            }
         }
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince_PeaceTreaty(oSB);
      }

      oSB.setShader(AoCGame.shaderAlpha2);

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isToTake && ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isTaken < 0) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawProvinces_PeaceTreaty_FogOfWarDiscovery(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.getMetProvince(CFG.game.getProvinceInViewID(i))) {
            if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID != 0) {
               if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID < 0) {
                  oSB.setColor(new Color((float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getR() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getG() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID * -1).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.25F));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               } else if (!((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isToTake || ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isTaken > 0) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID);
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               } else {
                  //else add slightly opaque country color (occupied province)
                  oSB.setColor(new Color((float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getR() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getG() / 255.0F, (float)CFG.game.getCiv(((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).iCivID).getB() / 255.0F, (float)CFG.settingsManager.PROVINCE_ALPHA / 255.0F * 0.75F));
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
               }
            }
         } else {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * ((float)CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince_PeaceTreaty(oSB);
      }

      oSB.setShader(AoCGame.shaderAlpha2);

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isToTake && ((PeaceTreaty_DrawData)CFG.peaceTreatyData.drawProvinceOwners.get(CFG.game.getProvinceInViewID(i))).isTaken < 0) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawOccupiedProvince(oSB);
         }
      }

      oSB.setShader(AoCGame.defaultShader);
   }

   protected static final void drawProvincesInCreateNewGameSelectAvailableCivs(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
            if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID()).getIsAvailable()) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            } else {
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, (float)CFG.settingsManager.PROVINCE_ALPHA * 0.6F / 255.0F));
            }

            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvincesInCreateRandomGame(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
      }

   }

   protected static final void drawProvincesInGame(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      drawProvincesInGame_StandardWasteland_FogOFWar(oSB);
      drawProvinces.draw(oSB);
   }

   protected static final void drawProvincesInGame_StandardWasteland(SpriteBatch oSB) {
      for(int i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
      }

   }

   protected static final void drawProvincesInGame_StandardWasteland_FogOFWar(SpriteBatch oSB) {
      for(int i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         if (CFG.getMetProvince(CFG.game.getWastelandProvinceInViewID(i))) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
         } else {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvinces_InLoad_PreDefinedBorders(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() == CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setProvinceColor(oSB);
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvincesInMapEditor_Connections(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));

      int i;
      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
      if (CFG.VIEW_SHOW_VALUES) {
         int j;
         for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            for(j = 0; j < CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getProvinceBordersLandByLandSize(); ++j) {
               drawProvincesInMapEditor_Connections_Line(oSB, Images.pix255_255_255, CFG.game.getProvinceInViewID(i), ((Province_Border)CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getProvinceBordersLandByLand().get(j)).getWithProvinceID());
            }

            for(j = 0; j < CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getProvinceBordersLandBySeaSize(); ++j) {
               drawProvincesInMapEditor_Connections_Line(oSB, Images.line_33, CFG.game.getProvinceInViewID(i), ((Province_Border)CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getProvinceBordersLandBySea().get(j)).getWithProvinceID());
            }
         }

         for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            for(j = 0; j < CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceBordersLandBySeaSize(); ++j) {
               drawProvincesInMapEditor_Connections_Line(oSB, Images.line_33, CFG.game.getSeaProvinceInViewID(i), ((Province_Border)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceBordersLandBySea().get(j)).getWithProvinceID());
            }

            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));

            for(j = 0; j < CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceBordersSeaBySeaSize(); ++j) {
               drawProvincesInMapEditor_Connections_Line(oSB, Images.line_33, CFG.game.getSeaProvinceInViewID(i), ((Province_Border)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceBordersSeaBySea().get(j)).getWithProvinceID());
            }
         }
      }

   }

   private static final void drawProvincesInMapEditor_Connections_Line(SpriteBatch oSB, int nImageID, int fromProvinceID, int toProvinceID) {
      if (CFG.game.getProvince(toProvinceID).getDrawProvince()) {
         int iWidth = (int)Math.ceil(Math.sqrt((double)((CFG.game.getProvince(toProvinceID).getCenterX() + CFG.game.getProvince(toProvinceID).getShiftX() + CFG.game.getProvince(toProvinceID).getTranslateProvincePosX() - (CFG.game.getProvince(fromProvinceID).getCenterX() + CFG.game.getProvince(fromProvinceID).getShiftX() + CFG.game.getProvince(fromProvinceID).getTranslateProvincePosX())) * (CFG.game.getProvince(toProvinceID).getCenterX() + CFG.game.getProvince(toProvinceID).getShiftX() + CFG.game.getProvince(toProvinceID).getTranslateProvincePosX() - (CFG.game.getProvince(fromProvinceID).getCenterX() + CFG.game.getProvince(fromProvinceID).getShiftX() + CFG.game.getProvince(fromProvinceID).getTranslateProvincePosX())) + (CFG.game.getProvince(fromProvinceID).getCenterY() + CFG.game.getProvince(fromProvinceID).getShiftY() - (CFG.game.getProvince(toProvinceID).getCenterY() + CFG.game.getProvince(toProvinceID).getShiftY())) * (CFG.game.getProvince(fromProvinceID).getCenterY() + CFG.game.getProvince(fromProvinceID).getShiftY() - (CFG.game.getProvince(toProvinceID).getCenterY() + CFG.game.getProvince(toProvinceID).getShiftY())))));
         float fAngle = (float)(Math.atan2((double)(CFG.game.getProvince(fromProvinceID).getCenterY() + CFG.game.getProvince(fromProvinceID).getShiftY() - (CFG.game.getProvince(toProvinceID).getCenterY() + CFG.game.getProvince(toProvinceID).getShiftY())), (double)(-(CFG.game.getProvince(fromProvinceID).getCenterX() + CFG.game.getProvince(fromProvinceID).getShiftX() + CFG.game.getProvince(fromProvinceID).getTranslateProvincePosX()) + CFG.game.getProvince(toProvinceID).getCenterX() + CFG.game.getProvince(toProvinceID).getShiftX() + CFG.game.getProvince(toProvinceID).getTranslateProvincePosX())) * 180.0 / Math.PI);
         ImageManager.getImage(nImageID).draw(oSB, CFG.game.getProvince(fromProvinceID).getCenterX() + CFG.game.getProvince(fromProvinceID).getShiftX() + CFG.game.getProvince(fromProvinceID).getTranslateProvincePosX(), CFG.game.getProvince(fromProvinceID).getCenterY() + CFG.game.getProvince(fromProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY(), iWidth, ImageManager.getImage(nImageID).getHeight(), fAngle, 0);
      }
   }

   protected static final void drawProvincesInMapEditor_SeaProvinces(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).getLevelOfPort() >= -1) {
            oSB.setColor(new Color(0.1254902F, 0.2901961F, 0.043137256F, 0.6F));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else if (CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).getLevelOfPort() == -1) {
            oSB.setColor(new Color(0.02745098F, 0.12941177F, 0.18431373F, 0.6F));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else {
            oSB.setColor(new Color(0.007843138F, 0.09411765F, 0.13725491F, 0.6F));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getLevelOfPort() >= -1) {
            oSB.setColor(new Color(0.1254902F, 0.2901961F, 0.043137256F, 0.6F));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getLevelOfPort() == -1) {
            oSB.setColor(new Color(0.02745098F, 0.12941177F, 0.18431373F, 0.6F));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else {
            oSB.setColor(new Color(0.007843138F, 0.09411765F, 0.13725491F, 0.6F));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getLevelOfPort() >= -1) {
            oSB.setColor(new Color(0.1254902F, 0.2901961F, 0.043137256F, 0.6F));
            CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else if (CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getLevelOfPort() == -1) {
            oSB.setColor(new Color(0.02745098F, 0.12941177F, 0.18431373F, 0.6F));
            CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         } else {
            oSB.setColor(new Color(0.007843138F, 0.09411765F, 0.13725491F, 0.6F));
            CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
         }
      }

   }

   protected static final void drawProvincesInGameEditorRegions(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         oSB.setColor((Color)Menu_GameEditor_Regions.lColors.get(CFG.game.getRegionID(CFG.game.getWastelandProvinceInViewID(i))));
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         oSB.setColor((Color)Menu_GameEditor_Regions.lColors.get(CFG.game.getRegionID(CFG.game.getProvinceInViewID(i))));
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         oSB.setColor((Color)Menu_GameEditor_Regions.lColors.get(CFG.game.getRegionID(CFG.game.getSeaProvinceInViewID(i))));
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvince_ActiveProvince(oSB);
      }

      if (CFG.game.getActiveProvinceID() >= 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX() + CFG.map.getMapCoordinates().getPosX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY() + CFG.map.getMapCoordinates().getPosY(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxX() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxY() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY());
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
         CFG.drawRect(oSB, ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX() + CFG.map.getMapCoordinates().getPosX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY() + CFG.map.getMapCoordinates().getPosY(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxX() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxY() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY() + CFG.map.getMapCoordinates().getPosY(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxX() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxY() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY());
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
            CFG.drawRect(oSB, ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY() + CFG.map.getMapCoordinates().getPosY(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxX() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinX(), ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMaxY() - ((Region)CFG.game.getRegions().get(CFG.game.getRegionID(CFG.game.getActiveProvinceID()))).getMinY());
         }
      }

   }

   protected static final void drawProvincesInMapEditor_ArmySeaBoxes(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX() + CFG.map.getMapCoordinates().getPosX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxX() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxY() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY());
         oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.8F));
         CFG.drawRect(oSB, CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX() + CFG.map.getMapCoordinates().getPosX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxX() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxY() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxX() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxY() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY());
            oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.8F));
            CFG.drawRect(oSB, CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxX() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinX(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMaxY() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getMinY());
         }
      }

      for(int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes() != null) {
            for(int j = CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
               ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY());
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
               CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY());
               if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
                  ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY());
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
                  CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).getProvinceArmyBoxes().get(j)).getStartPosY());
               }
            }
         }
      }

   }

   protected static final void drawProvincesInMapEditor_ArmySeaBoxes_Edit(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 >= 0 && CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getSeaProvince()) {
         oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.3F));
         CFG.drawRect(oSB, CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX() + CFG.map.getMapCoordinates().getPosX(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxX() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxY() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.3F));
            CFG.drawRect(oSB, CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxX() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxY() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY());
         }
      }

      int j;
      if (CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes() != null) {
         for(j = CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
            CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
            if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
               ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
               CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
            }
         }
      }

      if (CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes() != null) {
         for(j = CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
            CFG.glyphLayout.setText(CFG.fontMain, "" + (j + 1));
            CFG.drawText(oSB, "" + (j + 1), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX()) / 2 - (int)CFG.glyphLayout.width / 2, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY()) / 2 - CFG.TEXT_HEIGHT / 2, new Color(1.0F, 1.0F, 1.0F, 0.4F));
            if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            }
         }
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesInMapEditor_ArmySeaBoxes_Add(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 >= 0 && CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getSeaProvince()) {
         oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.3F));
         CFG.drawRect(oSB, CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxX() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxY() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            oSB.setColor(new Color(CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.r, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.g, CFG.COLOR_PROVINCE_ACTIVE_PROVINCE_BORDER.b, 0.3F));
            CFG.drawRect(oSB, CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY() + CFG.map.getMapCoordinates().getPosY(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxX() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinX(), CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMaxY() - CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getMinY());
         }
      }

      int j;
      if (CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes() != null) {
         for(j = CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
            if (j != CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
               ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
               CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
               if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.05F));
                  ImageManager.getImage(Images.pix255_255_255).draw(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
                  oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
                  CFG.drawRect(oSB, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + CFG.map.getMapBG().getWidth(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY());
               }
            }
         }
      }

      if (CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes() != null) {
         for(j = CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
            if (j != CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2) {
               CFG.glyphLayout.setText(CFG.fontMain, "" + (j + 1));
               CFG.drawText(oSB, "" + (j + 1), ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX()) / 2 - (int)CFG.glyphLayout.width / 2, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY()) / 2 - CFG.TEXT_HEIGHT / 2, new Color(1.0F, 1.0F, 1.0F, 0.4F));
               if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
                  CFG.drawText(oSB, "" + (j + 1), CFG.map.getMapBG().getWidth() + ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapCoordinates().getPosX() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosX() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosX()) / 2 - (int)CFG.glyphLayout.width / 2, ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY() + CFG.map.getMapCoordinates().getPosY() + (((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getEndPosY() - ((Province_ArmyBox)CFG.game.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getProvinceArmyBoxes().get(j)).getStartPosY()) / 2 - CFG.TEXT_HEIGHT / 2, new Color(1.0F, 1.0F, 1.0F, 0.4F));
               }
            }
         }
      }

      if (Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() >= 0 && Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() >= 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY());
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.45F));
         CFG.drawRect(oSB, Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, CFG.map.getMapBG().getWidth() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY());
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.45F));
            CFG.drawRect(oSB, CFG.map.getMapBG().getWidth() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), -ImageManager.getImage(Images.pix255_255_255).getHeight() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY());
         }
      }

      oSB.setColor(Color.RED);
      if (Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() >= 0) {
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, CFG.map.getMapBG().getWidth() + Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() + CFG.map.getMapCoordinates().getPosY());
         }
      }

      if (Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() >= 0) {
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() + CFG.map.getMapCoordinates().getPosY());
         if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, CFG.map.getMapBG().getWidth() + Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() + CFG.map.getMapCoordinates().getPosX(), Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() + CFG.map.getMapCoordinates().getPosY());
         }
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesInStartGame(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      CFG.startTheGameData.updateData();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB, (float)CFG.startTheGameData.getWastelandAlpha() / 255.0F);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
            if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getIsCapital()) {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), (float)CFG.startTheGameData.getCapitalsAlpha() / 255.0F);
            } else {
               CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), (float)CFG.startTheGameData.getProvincesAlpha() / 255.0F);
            }

            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvincesInStartGame_FogOfWarDiscovery(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      CFG.startTheGameData.updateData();

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         if (CFG.getMetProvince(CFG.game.getWastelandProvinceInViewID(i))) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB, (float)CFG.startTheGameData.getWastelandAlpha() / 255.0F);
         } else {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * ((float)CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID() != 0) {
            if (CFG.getMetProvince(CFG.game.getProvinceInViewID(i))) {
               if (CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getIsCapital()) {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), (float)CFG.startTheGameData.getCapitalsAlpha() / 255.0F);
               } else {
                  CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).setCivilizationProvinceColor(oSB, CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).getCivID(), (float)CFG.startTheGameData.getProvincesAlpha() / 255.0F);
               }
            } else {
               oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * ((float)CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            }

            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         } else if (!CFG.getMetProvince(CFG.game.getProvinceInViewID(i))) {
            oSB.setColor(new Color(CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getR(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getG(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.getB(), CFG.settingsManager.COLOR_PROVINCE_DISCOVERY_ALPHA * ((float)CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
         }
      }

   }

   protected static final void drawProvinces_PrintAMap(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();
      oSB.setColor(Color.WHITE);

      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawLandProvince(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawLandProvince(oSB);
      }

   }

   protected static final void drawProvincesIn_MapEditor_WastelandMaps(SpriteBatch oSB) {
      CFG.game.updateProvincesInView();

      for(int i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawWastelandProvince(oSB);
      }

   }

   protected static final void drawProvincesBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Only_CivilizationBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Only_CivilizationBorder_InGame(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Only_CivilizationBorder_InGame_AndSea(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Only_CivilizationBorder_Capitals(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder_Capitals(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder_Capitals(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_Only_CivilizationBorder_Capitals_FogOfWarDiscovery(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder_Capitals_FogOfWarDiscovery(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_OnlyCivilizationBorder_Capitals_FogOfWarDiscoveryWasteland(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_NextPlayer(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_NextPlayerTurn(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_NextPlayerTurn(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_CivilizationView(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_CivilizationView(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_CivilizationView(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_LoadAI_RTO(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      if (CFG.FOG_OF_WAR == 2) {
         for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_LoadAI_RTO_FogOfWarDiscovery(oSB);
         }

         for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_LoadAI_RTO_FogOfWarDiscovery(oSB);
         }
      } else {
         for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_LoadAI_RTO(oSB);
         }

         for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_LoadAI_RTO(oSB);
         }
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_TerrainMode(SpriteBatch oSB) {
      CFG.fTerrainMode_LinePercentage += (float)(System.currentTimeMillis() - CFG.lTerrainMode_LineTime) / 700.0F * 100.0F;
      if (CFG.fTerrainMode_LinePercentage > 100.0F) {
         CFG.fTerrainMode_LinePercentage = 100.0F;
      }

      CFG.lTerrainMode_LineTime = System.currentTimeMillis();

      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_TerrainMode(oSB);
      }

   }

   protected static final void drawProvincesBorder_ContinentMode(SpriteBatch oSB) {
      CFG.fTerrainMode_LinePercentage += (float)(System.currentTimeMillis() - CFG.lTerrainMode_LineTime) / 700.0F * 100.0F;
      if (CFG.fTerrainMode_LinePercentage > 100.0F) {
         CFG.fTerrainMode_LinePercentage = 100.0F;
      } else {
         CFG.setRender_3(true);
      }

      CFG.lTerrainMode_LineTime = System.currentTimeMillis();

      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_ContinentMode(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_ContinentModeWasteland(oSB);
      }

   }

   protected static final void drawProvincesBorder_ContinentMode_FogOfWarDiscovey(SpriteBatch oSB) {
      CFG.fTerrainMode_LinePercentage += (float)(System.currentTimeMillis() - CFG.lTerrainMode_LineTime) / 700.0F * 100.0F;
      if (CFG.fTerrainMode_LinePercentage > 100.0F) {
         CFG.fTerrainMode_LinePercentage = 100.0F;
      } else {
         CFG.setRender_3(true);
      }

      CFG.lTerrainMode_LineTime = System.currentTimeMillis();

      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_ContinentMode_FogOfWarDiscovery(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_ContinentModeWasteland(oSB);
      }

   }

   protected static final void drawProvincesBorder_RegionsMode(SpriteBatch oSB) {
      CFG.fTerrainMode_LinePercentage += (float)(System.currentTimeMillis() - CFG.lTerrainMode_LineTime) / 700.0F * 100.0F;
      if (CFG.fTerrainMode_LinePercentage > 100.0F) {
         CFG.fTerrainMode_LinePercentage = 100.0F;
      } else {
         CFG.setRender_3(true);
      }

      CFG.lTerrainMode_LineTime = System.currentTimeMillis();

      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_RegionMode(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_RegionModeWasteland(oSB);
      }

   }

   protected static final void drawProvincesBorder_RegionsMode_FogOfWarDiscovery(SpriteBatch oSB) {
      CFG.fTerrainMode_LinePercentage += (float)(System.currentTimeMillis() - CFG.lTerrainMode_LineTime) / 700.0F * 100.0F;
      if (CFG.fTerrainMode_LinePercentage > 100.0F) {
         CFG.fTerrainMode_LinePercentage = 100.0F;
      } else {
         CFG.setRender_3(true);
      }

      CFG.lTerrainMode_LineTime = System.currentTimeMillis();

      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_RegionMode_FogOfWarDiscovery(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_RegionModeWasteland(oSB);
      }

   }

   protected static final void drawProvincesBorderInStartGame(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorderInStartGame(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorderInStartGame_Wasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorderInStartGame(oSB);
      }

   }

   protected static final void drawProvincesBorderInStartGame_FogOfWar(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorderInStartGame(oSB);
      }

      if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
         for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorderInStartGame(oSB);
         }
      } else {
         for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
            CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorderInStartGame_Wasteland(oSB);
         }
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorderInStartGame(oSB);
      }

   }

   protected static final void drawLandProvincesBorder(SpriteBatch oSB) {
      for(int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

   }

   protected static final void drawProvincesBorder_PrintAMap(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder_PrintAMap(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_PrintAMap(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_PrintAMap(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_CreateRandomGame(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_CreateRandomGameWasteland(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_CreateRandomGame(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   protected static final void drawProvincesBorder_DrawJustInnerBorder(SpriteBatch oSB) {
      int i;
      for(i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getSeaProvinceInViewID(i)).drawProvinceBorder(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getWastelandProvinceInViewID(i)).drawProvinceBorder_CreateRandomGame(oSB);
      }

      for(i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
         CFG.game.getProvince(CFG.game.getProvinceInViewID(i)).drawProvinceBorder_CreateRandomGame(oSB);
      }

      oSB.setColor(Color.WHITE);
   }

   interface DrawProvinces {
      void draw(SpriteBatch var1);
   }
}
