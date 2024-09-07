// Decompiled with: CFR 0.152
// Class Version: 6
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Dialog;
import age.of.civilizations2.jakowski.lukasz.Editor_Continents;
import age.of.civilizations2.jakowski.lukasz.Editor_GrowthRate;
import age.of.civilizations2.jakowski.lukasz.Editor_MapRegions;
import age.of.civilizations2.jakowski.lukasz.Editor_Regions;
import age.of.civilizations2.jakowski.lukasz.Editor_TerrainType;
import age.of.civilizations2.jakowski.lukasz.Game_Action;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.Menu_MapEditor_ArmySeaBoxes_Add;
import age.of.civilizations2.jakowski.lukasz.Menu_PeaceTreaty_Response;
import age.of.civilizations2.jakowski.lukasz.RTS;
import age.of.civilizations2.jakowski.lukasz.SoundsManager;
import age.of.civilizations2.jakowski.lukasz.ViewsManager;

class Map_TouchManager {
    private boolean actionMap = false;
    private int iStartMovePosX;
    private int iStartMovePosY;
    private int actionDownPosX;
    private int actionDownPosY;
    private boolean updateStartMovePosX;
    private boolean updateStartMovePosY;
    private long lActionDownTime = 0L;
    private boolean enableScaling = false;
    private boolean actionBrushMove = false;
    private boolean actionBrush = false;
    private ExtraAction map_ActionDown_ExtraAction;
    private ExtraAction map_ActionMove_ExtraAction;
    private ExtraAction map_ActionUp_SetActiveProvinceID_ExtraAction;
    private ExtraAction map_ActionUp_ExtraAction;
    private ReverseDirection reverseDirectionX;
    private ReverseDirection reverseDirectionY;
    private ReverseDirection2 reverseDirectionX2;
    private ReverseDirection2 reverseDirectionY2;

    protected Map_TouchManager() {
        this.buildReversePosX();
        this.buildReversePosY();
        this.buildReversePosX2();
        this.buildReversePosY2();
        this.update_ExtraAction();
    }

    protected final void updateEnableScaling() {
        this.enableScaling = !CFG.menuManager.getInMainMenu() && !CFG.menuManager.getInAboutMenu() && !CFG.menuManager.getInInitMenu() && !CFG.menuManager.getInLoadMap();
    }

    protected final void actionDown(int nPosX, int nPosY) {
        this.actionMap = true;
        this.actionBrush = false;
        this.actionBrushMove = false;
        if (CFG.map.getMapScroll().getScrollingTheMap()) {
            CFG.map.getMapScroll().stopScrollingTheMap();
        }
        this.iStartMovePosX = this.reverseDirectionX.getStartMovePos((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
        this.iStartMovePosY = this.reverseDirectionY.getStartMovePos((int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
        this.actionDownPosX = nPosX;
        this.actionDownPosY = nPosY;
        this.map_ActionDown_ExtraAction.extraAction(nPosX, nPosY);
    }

    protected final void actionMove(int nPosX, int nPosY) {
        if (CFG.brushTool) {
            this.actionDownPosX = nPosX;
            this.actionDownPosY = nPosY;
            this.actionUp_setActiveProvinceID(nPosX, nPosY);
            return;
        }
        this.actionMoveMap(nPosX, nPosY);
        CFG.map.getMapScroll().setScrollPos((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
    }

    protected final void actionMoveMap(int nPosX, int nPosY) {
        if (!CFG.map.getMapCoordinates().getDisableMovingMap()) {
            if (this.updateStartMovePosX) {
                this.iStartMovePosX = this.reverseDirectionX.getStartMovePos((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                this.iStartMovePosY = this.reverseDirectionY.getStartMovePos((int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                this.updateStartMovePosX = false;
            }
            if (this.updateStartMovePosY) {
                this.iStartMovePosX = this.reverseDirectionX.getStartMovePos((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                this.iStartMovePosY = this.reverseDirectionY.getStartMovePos((int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                this.updateStartMovePosY = false;
            }
            CFG.map.getMapCoordinates().setNewPosX(this.reverseDirectionX2.getNewPos(this.iStartMovePosX, (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale())));
            CFG.map.getMapCoordinates().setNewPosY(this.reverseDirectionY2.getNewPos(this.iStartMovePosY, (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale())));
        } else {
            this.map_ActionMove_ExtraAction.extraAction(nPosX, nPosY);
        }
    }

    protected final void actionMove(int nPosX, int nPosY, int nPosX2, int nPosY2) {
        if (!CFG.map.getMapCoordinates().getDisableMovingMap() && this.enableScaling) {
            if (CFG.map.getMapScale().getStartScalePosY() <= 0) {
                CFG.map.getMapScale().startScaleTheMap(nPosX, nPosX2, nPosY, nPosY2);
            } else {
                CFG.map.getMapScale().scaleTheMap(nPosX, nPosX2, nPosY, nPosY2);
            }
        }
    }

    protected final void actionUp(int nPosX, int nPosY) {
        this.actionUp_setActiveProvinceID(nPosX, nPosY);
        if (CFG.menuManager.getActiveMenuElementID() < 0 && this.enableScaling) {
            CFG.map.getMapScale().resetScaleOfMap(System.currentTimeMillis());
        }
        if (!CFG.map.getMapScale().getScaleMode() && !CFG.map.getMapCoordinates().getDisableMovingMap()) {
            CFG.map.getMapScroll().startScrollingTheMap();
        }
        this.map_ActionUp_ExtraAction.extraAction(nPosX, nPosY);
    }

    private final void actionUp_setActiveProvinceID(int nPosX, int nPosY) {
        if (!CFG.map.getMapScale().getScaleMode() && (float)this.actionDownPosX + (float)CFG.PADDING * CFG.DENSITY > (float)nPosX && (float)this.actionDownPosX - (float)CFG.PADDING * CFG.DENSITY < (float)nPosX && (float)this.actionDownPosY + (float)CFG.PADDING * CFG.DENSITY > (float)nPosY && (float)this.actionDownPosY - (float)CFG.PADDING * CFG.DENSITY < (float)nPosY) {
            CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
            if (!CFG.brushTool) {
                CFG.soundsManager.playSound(SoundsManager.SOUND_PROVINCE, SoundsManager.PERC_VOLUME_SELECT_PROVINCE);
            }
            this.map_ActionUp_SetActiveProvinceID_ExtraAction.extraAction(nPosX, nPosY);
        }
    }

    protected final void buildReversePosX() {
        this.reverseDirectionX = CFG.reverseDirectionX ? new ReverseDirection(){

            @Override
            public int getStartMovePos(int nPos) {
                return CFG.map.getMapCoordinates().getPosX() - nPos;
            }
        } : new ReverseDirection(){

            @Override
            public int getStartMovePos(int nPos) {
                return CFG.map.getMapCoordinates().getPosX() + nPos;
            }
        };
    }

    protected final void buildReversePosY() {
        this.reverseDirectionY = CFG.reverseDirectionY ? new ReverseDirection(){

            @Override
            public int getStartMovePos(int nPos) {
                return CFG.map.getMapCoordinates().getPosY() - nPos;
            }
        } : new ReverseDirection(){

            @Override
            public int getStartMovePos(int nPos) {
                return CFG.map.getMapCoordinates().getPosY() + nPos;
            }
        };
    }

    protected final void buildReversePosX2() {
        this.reverseDirectionX2 = CFG.reverseDirectionX ? new ReverseDirection2(){

            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
                return iStartMovePos + nPos;
            }
        } : new ReverseDirection2(){

            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
                return iStartMovePos - nPos;
            }
        };
    }

    protected final void buildReversePosY2() {
        this.reverseDirectionY2 = CFG.reverseDirectionY ? new ReverseDirection2(){

            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
                return iStartMovePos + nPos;
            }
        } : new ReverseDirection2(){

            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
                return iStartMovePos - nPos;
            }
        };
    }

    protected final void update_ExtraAction() {
        this.map_ActionUp_SetActiveProvinceID_ExtraAction = null;
        this.map_ActionUp_SetActiveProvinceID_ExtraAction = CFG.menuManager.getInSelectCiv() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                    for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                        if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.game.getPlayer(i).getCivID()) continue;
                        return;
                    }
                    CFG.setDialogType(Dialog.SELECT_CIVILIZATION);
                }
            }
        } : (CFG.menuManager.getInGameView() ? (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOAD_AI_RTO || CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOADING_NEXT_TURN ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.game.setActiveProvinceID(-1);
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.SPECTATOR_MODE) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).setCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).loadPlayersFlag();
                        CFG.setActiveCivInfo(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                        CFG.updateActiveCivInfo_InGame();
                        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                        CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);
                        CFG.menuManager.rebuildInGame_Messages();
                        if (CFG.menuManager.getVisible_InGame_Budget()) {
                            CFG.menuManager.setVisible_InGame_Budget(true);
                        }
                        if (CFG.menuManager.getVisible_InGame_FlagAction() && !CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                            CFG.menuManager.setVisible_InGame_FlagAction(true);
                        }
                        if (CFG.menuManager.getVisibleInGame_VictoryConditions()) {
                            CFG.menuManager.rebuildInGame_VictoryConditions();
                        }
                        if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
                            CFG.game.disableDrawCivilizationRegions_Active();
                            CFG.game.enableDrawCivilizationRegions_ActiveProvince();
                        } else if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_INCOME_MODE && CFG.menuManager.getVisible_InGame_View_Stats()) {
                            CFG.menuManager.setVisible_InGame_ViewIncome(true);
                        }
                    }
                } else if (CFG.game.getActiveProvinceID() >= 0) {
                    CFG.game.autoBuildChooseProvinceMode(false);
                    int nCivID = CFG.getActiveCivInfo_BasedOnActiveProvinceID(CFG.game.getActiveProvinceID());
                    if (nCivID > 0 && CFG.getActiveCivInfo() != nCivID) {
                        if (CFG.viewsManager.getActiveViewID() >= 0) {
                            CFG.viewsManager.getActiveView().updateActiveCivInfo_ExtraAction(nCivID);
                        }
                        if (CFG.menuManager.getInGame_CivInfo().getVisible()) {
                            CFG.setActiveCivInfo(nCivID);
                            CFG.updateActiveCivInfo_InGame();
                            if (CFG.viewsManager.getActiveViewID() >= 0) {
                                CFG.viewsManager.getActiveView().setActiveProvinceAction();
                            }
                        }
                    }
                    if (RTS.isEnabled() && !RTS.PAUSE) {
                        RTS.updateTimePast_AfterAction(0.5f);
                    }
                }
            }
        }) : (CFG.menuManager.getInGame_Timeline() || CFG.menuManager.getInVictory() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        CFG.game.setActiveProvinceID(-1);
                    } else if (CFG.timelapseManager.timelineOwners.get(CFG.game.getActiveProvinceID()) > 0) {
                        CFG.toast.setInView(CFG.game.getCiv(CFG.timelapseManager.timelineOwners.get(CFG.game.getActiveProvinceID())).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                        CFG.toast.setTimeInView(1500);
                    }
                }
            }
        } : (CFG.menuManager.getInGame_Formable_Civ_Provinces() || CFG.menuManager.getInGame_FormAnimation() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.game.setActiveProvinceID(-1);
            }
        } : (CFG.menuManager.getInCreateNewGame() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                block6: {
                    try {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.getActiveCivInfo() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (!CFG.game.getCiv(CFG.getActiveCivInfo()).getControlledByPlayer()) {
                                CFG.game.disableDrawCivilizationRegions(CFG.getActiveCivInfo());
                            }
                            CFG.setActiveCivInfo(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            CFG.updateActiveCivInfo_CreateNewGame();
                            CFG.game.enableDrawCivilizationRegions(CFG.getActiveCivInfo(), 1);
                        }
                    }
                    catch (IndexOutOfBoundsException ex) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex);
                        }
                    }
                    catch (NullPointerException ex) {
                        if (!CFG.LOGS) break block6;
                        CFG.exceptionStack(ex);
                    }
                }
            }
        } : (CFG.menuManager.getInSelectAvailableCivilizations() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                    CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setVisible(true);
                    CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setClickable(true);
                    CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setCheckboxState(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getIsAvailable());
                    if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getIsAvailable()) {
                        CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setText(CFG.langManager.get("Disable") + " - " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                        if (CFG.game.getAvailableCivilizations() < 3) {
                            CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setClickable(false);
                        }
                    } else {
                        CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setText(CFG.langManager.get("Enable") + " - " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                    }
                    CFG.menuManager.getSelectAvailableCivilizations().updateButtonWidth(3, CFG.PADDING, CFG.BUTTON_WIDTH * 2);
                } else {
                    CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setVisible(false);
                    CFG.menuManager.getSelectAvailableCivilizations().getMenuElement(3).setClickable(false);
                }
            }
        } : (CFG.menuManager.getInGame_PeaceTreaty() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (Menu_PeaceTreaty_Response.DRAW_TREATY_PROVINCES) {
                    //if player peace, only take province not of civ
                    //if (CFG.PLAYER_PEACE || CFG.SPECTATOR_MODE) {
                    //    if (!(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.peaceTreatyData.iBrushCivID)) {
                    //    CFG.peaceTreatyData.takeProvince(CFG.game.getActiveProvinceID(), CFG.peaceTreatyData.iBrushCivID, CFG.game.getCiv(CFG.peaceTreatyData.iBrushCivID).getControlledByPlayer() ? CFG.peaceTreatyData.iBrushCivID : CFG.game.getPlayer(CFG.peaceTreatyData.iPlayerTurnID).getCivID());
                    //    }
                    //} else {
                    CFG.peaceTreatyData.takeProvince(CFG.game.getActiveProvinceID(), CFG.peaceTreatyData.iBrushCivID, CFG.game.getCiv(CFG.peaceTreatyData.iBrushCivID).getControlledByPlayer() ? CFG.peaceTreatyData.iBrushCivID : CFG.game.getPlayer(CFG.peaceTreatyData.iPlayerTurnID).getCivID());
                    //}
                }
            }
        } : (CFG.menuManager.getInGame_PeaceTreaty_Response() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.game.setActiveProvinceID(-1);
            }
        } : (CFG.menuManager.getInCreateScenario_Civilizations() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.updateCreateScenario_Civilizations();
            }
        } : (CFG.menuManager.getInCreateScenario_Civilizations_Select() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.game.setActiveProvinceID(CFG.iCreateScenario_ActiveProvinceID);
            }
        } : (CFG.menuManager.getInCreateScenario_TechnologyLevels() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0) {
                        if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                            CFG.game.disableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ);
                        }
                        CFG.iCreateScenario_AssignProvinces_Civ = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                        CFG.menuManager.set_CreateScenario_TechnologyLevels_Slider((int)(CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ).getTechnologyLevel() * 100.0f));
                        if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                            CFG.game.enableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ, 0);
                        }
                    } else {
                        CFG.menuManager.set_CreateScenario_TechnologyLevels_SliderCivs();
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_Happiness() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0) {
                    if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                        CFG.game.disableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ);
                    }
                    CFG.iCreateScenario_AssignProvinces_Civ = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.set_CreateScenario_Happiness_Slider(CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ).getHappiness());
                    if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                        CFG.game.enableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ, 0);
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_StartingMoney() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0) {
                    if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                        CFG.game.disableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ);
                    }
                    CFG.menuManager.set_CreateScenario_StartingMoney_Slider((int)(CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getMoney() == -999999L ? (long)CFG.game.getGameScenarios().getScenario_StartingMoney() : CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ).getMoney()));
                    if (CFG.iCreateScenario_AssignProvinces_Civ > 0) {
                        CFG.game.enableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ, 0);
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_Available_Provinces() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (!Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                    } else if (!Map_TouchManager.this.actionBrushMove) {
                        if (!CFG.bSetWasteland_AvailableProvinces || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                            CFG.game.setWasteland(CFG.game.getActiveProvinceID(), CFG.bSetWasteland_AvailableProvinces);
                        }
                        CFG.updateNumOfAvailableProvinces();
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInMapEditor_WastelandMaps_Edit() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (!Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                    } else if (!Map_TouchManager.this.actionBrushMove) {
                        if (CFG.bSetWasteland_AvailableProvinces) {
                            if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                                CFG.game.setWasteland(CFG.game.getActiveProvinceID(), CFG.bSetWasteland_AvailableProvinces);
                            }
                        } else {
                            CFG.game.setWasteland(CFG.game.getActiveProvinceID(), CFG.bSetWasteland_AvailableProvinces);
                        }
                        CFG.updateNumOfAvailableProvinces();
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInMapEditor_ArmySeaBoxes_Add() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY() < 0) {
                        Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.setPosX(-CFG.map.getMapCoordinates().getPosX() + (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                        Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.setPosY(-CFG.map.getMapCoordinates().getPosY() + (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    } else if (Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY() < 0) {
                        Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.setPosX(-CFG.map.getMapCoordinates().getPosX() + (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                        Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.setPosY(-CFG.map.getMapCoordinates().getPosY() + (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    } else {
                        int tempWidthSecond;
                        int tempPosX = -CFG.map.getMapCoordinates().getPosX() + (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale());
                        int tempPosY = -CFG.map.getMapCoordinates().getPosY() + (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale());
                        int tempWidthFirst = (int)Math.ceil(Math.sqrt((Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() - tempPosX) * (Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosX() - tempPosX) + (tempPosY - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY()) * (tempPosY - Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.getPosY())));
                        if (tempWidthFirst < (tempWidthSecond = (int)Math.ceil(Math.sqrt((Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - tempPosX) * (Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosX() - tempPosX) + (tempPosY - Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY()) * (tempPosY - Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.getPosY()))))) {
                            Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.setPosX(-CFG.map.getMapCoordinates().getPosX() + (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                            Menu_MapEditor_ArmySeaBoxes_Add.oFirstPoint.setPosY(-CFG.map.getMapCoordinates().getPosY() + (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                        } else {
                            Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.setPosX(-CFG.map.getMapCoordinates().getPosX() + (int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()));
                            Menu_MapEditor_ArmySeaBoxes_Add.oSecondPoint.setPosY(-CFG.map.getMapCoordinates().getPosY() + (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                        }
                    }
                    if (CFG.game.getActiveProvinceID() != CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1) {
                        CFG.game.setActiveProvinceID(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1);
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_Assign() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.brushTool && !Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.iCreateScenario_AssignProvinces_Civ >= 0) {
                        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                            if (CFG.game.getCiv(i).getCapitalProvinceID() != CFG.game.getActiveProvinceID()) continue;
                            if (!CFG.brushTool && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ) {
                                CFG.setDialogType(Dialog.CREATE_SCENARIO_ASSIGN_CIVILIZATION);
                            }
                            return;
                        }
                        if ((CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ ||
                             (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() != CFG.iCreateScenario_AssignProvinces_Civ && !CFG.occupyTool)) &&
                             CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0)
                        {
                            CFG.addUndoAssignProvinces(CFG.game.getActiveProvinceID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            //if occupytool and on non-neutral province, occupy, else actually giv the civ the province
                            if (CFG.occupyTool && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).setTrueOwnerOfProvince(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).setCivID(CFG.iCreateScenario_AssignProvinces_Civ, true, false);
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).resetArmies(-1);
                            } else {
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).setTrueOwnerOfProvince(CFG.iCreateScenario_AssignProvinces_Civ);
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).setCivID(CFG.iCreateScenario_AssignProvinces_Civ, false, false);
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).resetArmies(-1);
                                CFG.game.getProvince(CFG.game.getActiveProvinceID()).buildProvinceCore();
                            }

                            CFG.game.setActiveProvinceID(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                            if (CFG.game.getCiv(i).getCapitalProvinceID() != CFG.game.getActiveProvinceID()) continue;
                            if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ) {
                                CFG.setDialogType(Dialog.CREATE_SCENARIO_ASSIGN_CIVILIZATION);
                            }
                            return;
                        }
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_SetUpArmy() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() >= 0) {
                        if (CFG.brushTool) {
                            if (!Map_TouchManager.this.actionBrush) {
                                Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                                Map_TouchManager.this.actionBrushMove = true;
                            }
                        } else if (CFG.selectMode && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                            CFG.menuManager.rebuildCreateScenario_SetUpArmies_Sliders();
                            if (CFG.menuManager.getVisible_CreateScenario_SetUpArmies_Civs()) {
                                CFG.menuManager.rebuildCreateScenario_SetUpArmies_Civs();
                            }
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.brushTool) {
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        } else {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                    }
                    CFG.menuManager.rebuildCreateScenario_SetUpArmies_Sliders();
                    if (CFG.menuManager.getVisible_CreateScenario_SetUpArmies_Civs()) {
                        CFG.menuManager.rebuildCreateScenario_SetUpArmies_Civs();
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_Events_SelectProvinces() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.brushTool) {
                            if (!Map_TouchManager.this.actionBrush) {
                                Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                                Map_TouchManager.this.actionBrushMove = true;
                            }
                        } else if (CFG.selectMode && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.brushTool) {
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        } else {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_Cores() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() >= 0) {
                        if (CFG.brushTool) {
                            if (!Map_TouchManager.this.actionBrush) {
                                Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                                Map_TouchManager.this.actionBrushMove = true;
                            }
                        } else if (CFG.selectMode && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                        }
                        CFG.menuManager.rebuildCreateScenario_Cores_SetUp();
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.brushTool) {
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        } else {
                            CFG.game.getSelectedProvinces().clearSelectedProvinces();
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                    }
                    CFG.menuManager.rebuildCreateScenario_Cores_SetUp();
                }
            }
        } : (CFG.menuManager.getInMapEditor_FormableCivs_Edit() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.brushTool && !Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.brushTool) {
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        } else {
                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                    }
                }
            }
        } : (CFG.menuManager.getInCreateScenario_HolyRomanEmpire() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.brushTool && !Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID()) && CFG.holyRomanEmpire_Manager.addProvince(CFG.game.getActiveProvinceID())) {
                            CFG.menuManager.rebuildCreateScenario_HolyRomanEmpire_Princes();
                        }
                    } else if (CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID()) && CFG.holyRomanEmpire_Manager.removeProvince(CFG.game.getActiveProvinceID())) {
                        CFG.menuManager.rebuildCreateScenario_HolyRomanEmpire_Princes();
                    }
                }
            }
        } : (CFG.menuManager.getInGame_CreateAVassal() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || !CFG.game.getSelectedProvinces().canBeReleasedAsVassal(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getActiveProvinceID())) {
                        if (CFG.brushTool && !Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.game.getSelectedProvinces().canBeReleasedAsVassal(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getActiveProvinceID())) {
                            if (CFG.brushTool) {
                                CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                CFG.updateCreateAVassal_CivInfo();
                            } else {
                                CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                CFG.updateCreateAVassal_CivInfo();
                            }
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                        boolean resetCapital = true;
                        for (int i = 0; i < CFG.game.getSelectedProvinces().getProvincesSize(); ++i) {
                            if (CFG.createVassal_Data.iCapitalProvinceID != CFG.game.getSelectedProvinces().getProvince(i)) continue;
                            resetCapital = false;
                            break;
                        }
                        if (resetCapital) {
                            CFG.createVassal_Data.iCapitalProvinceID = -1;
                        }
                        CFG.updateCreateAVassal_CivInfo();
                    }
                }
            }
        } : (CFG.menuManager.getInGame_SelectProvinces() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (!CFG.SPECTATOR_MODE && (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.FOG_OF_WAR == 2 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()) || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID)) {
                        if (CFG.brushTool && !Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                        return;
                    }
                    if (CFG.brushTool) {
                        if (Map_TouchManager.this.actionBrushMove) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            return;
                        }
                        Map_TouchManager.this.actionBrush = true;
                    }
                    if (CFG.selectMode) {
                        if (CFG.SPECTATOR_MODE) {
                            switch (CFG.sandbox_task) {
                                case eSETTINGS_PROVINCE:
                                    if (!(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() >= 0 || (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()))) {
                                        if (CFG.brushTool) {
                                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                        } else {
                                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                        }
                                    }
                                    break;
                                case eCREATE_SCENARIO_SET_UP_ARMY:
                                    if (!(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() >= 0)) {
                                        if (CFG.brushTool) {
                                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                        } else {
                                            CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                        }
                                    }
                                    break;
                            }


                        } else {
                            if ((CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince())) {
                                if (CFG.brushTool) {
                                    CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                } else {
                                    CFG.game.getSelectedProvinces().addProvince(CFG.game.getActiveProvinceID());
                                }
                            }
                        }
                    } else {
                        CFG.game.getSelectedProvinces().removeProvince(CFG.game.getActiveProvinceID());
                    }
                }
            }
        } : (CFG.menuManager.getInGame_TradeSelectCiv() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.tradeRequest.iCivLEFT && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.tradeRequest.iCivRIGHT && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && (CFG.FOG_OF_WAR != 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()))) {
                    CFG.setDialogType(Dialog.TRADE_REQUEST_SELECT_CIV);
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy() ? (CFG.menuManager.getInManageDiplomacy_Relations_Interactive() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID) {
                        CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(1).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(3).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setCurrent((int)CFG.game.getCivRelation_OfCivB(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID, CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2));
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(4).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(6).setClickable(true);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setCurrent((int)CFG.game.getCivRelation_OfCivB(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2, CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID));
                    } else {
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(1).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(3).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setCurrent(0);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(4).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(6).setClickable(false);
                        CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setCurrent(0);
                        CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2 = 0;
                    }
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_Pacts3() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_Pacts_List();
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_Truces() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_Trcues_List();
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_Guarantee() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_Guarantee_List();
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_DefensivePact() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_DefensivePacts_List();
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_MilitaryAccess() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_MilitaryAccess_List();
                }
            }
        } : (CFG.menuManager.getInManageDiplomacy_Vassals() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                    CFG.menuManager.rebuildManageDiplomacy_Vassals_List();
                }
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
            }
        }))))))) : (CFG.menuManager.getInCreateCity() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    CFG.editorCity.setPosX(((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()) - CFG.map.getMapCoordinates().getPosX()) / CFG.map.getMapBG().getMapScale());
                    CFG.editorCity.setPosY(((int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()) - CFG.map.getMapCoordinates().getPosY()) / CFG.map.getMapBG().getMapScale());
                    if (CFG.editorCity.getPosX() > CFG.map.getMapBG().getWidth() / CFG.map.getMapBG().getMapScale()) {
                        CFG.editorCity.setPosX(CFG.editorCity.getPosX() % (CFG.map.getMapBG().getWidth() / CFG.map.getMapBG().getMapScale()));
                    }
                    CFG.menuManager.getCreateCity_UpdateSaveButton();
                }
            }
        } : (CFG.menuManager.getInMapEditor_Terrain() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (!Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                    } else if (!Map_TouchManager.this.actionBrushMove) {
                        Editor_TerrainType.actionSave(true);
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInMapEditor_GrowthRate() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (!Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                    } else if (!Map_TouchManager.this.actionBrushMove) {
                        Editor_GrowthRate.actionSave(true);
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInMapEditor_Continents() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (!Map_TouchManager.this.actionBrushMove) {
                        Editor_Continents.actionSave(true);
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInMapEditor_Regions() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (!Map_TouchManager.this.actionBrush) {
                            Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                            Map_TouchManager.this.actionBrushMove = true;
                        }
                    } else if (!Map_TouchManager.this.actionBrushMove) {
                        Editor_MapRegions.actionSave(true);
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : (CFG.menuManager.getInGameEditor_Regions() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.game.getActiveProvinceID() >= 0) {
                    if (!Map_TouchManager.this.actionBrushMove) {
                        Editor_Regions.actionUpdateRegionID(true);
                        Map_TouchManager.this.actionBrush = true;
                    } else {
                        Map_TouchManager.this.actionMoveMap(nPosX, nPosY);
                    }
                }
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
            }
        })))))))))))))))))))))))))))))));
        this.map_ActionDown_ExtraAction = null;
        this.map_ActionDown_ExtraAction = CFG.menuManager.getInManageDiplomacy() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if ((CFG.menuManager.getManageDiplomacy_Alliances().getVisible() || CFG.menuManager.getInManageDiplomacy_Relations_Interactive() || CFG.menuManager.getInManageDiplomacy_Pacts3() || CFG.menuManager.getInManageDiplomacy_Truces() || CFG.menuManager.getInManageDiplomacy_MilitaryAccess() || CFG.menuManager.getInManageDiplomacy_DefensivePact() || CFG.menuManager.getInManageDiplomacy_Guarantee() || CFG.menuManager.getInManageDiplomacy_Vassals()) && CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != 0) {
                    int tempOldActiveProvinceID = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() == tempOldActiveProvinceID) {
                        CFG.map.getMapCoordinates().setDisableMovingMap(true);
                        CFG.menuManager.getDrawCivilization().setVisible(true);
                        CFG.menuManager.getDrawCivilization().setCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                        CFG.menuManager.getDrawCivilization().setPosX(nPosX);
                        CFG.menuManager.getDrawCivilization().setPosY(nPosY);
                    } else {
                        CFG.game.setActiveProvinceID(tempOldActiveProvinceID);
                    }
                }
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
            }
        };
        this.map_ActionMove_ExtraAction = null;
        this.map_ActionMove_ExtraAction = CFG.menuManager.getInManageDiplomacy() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                CFG.menuManager.getDrawCivilization().setPosX(nPosX);
                CFG.menuManager.getDrawCivilization().setPosY(nPosY);
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
            }
        };
        this.map_ActionUp_ExtraAction = null;
        this.map_ActionUp_ExtraAction = CFG.menuManager.getInManageDiplomacy_Vassals() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Vassals().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Vassals().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                                    CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                } else {
                                    int tempID = CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1;
                                    CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2;
                                    CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = tempID;
                                }
                            } else if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                int tempID = CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = tempID;
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(CFG.menuManager.getManageDiplomacy_Vassals().getMenuElementsSize() - 1).setClickable(true);
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0) {
                                CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(0).setText(CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).getCivName());
                            } else {
                                CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(0).setText("");
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(1).setText(CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2).getCivName());
                                break;
                            }
                            CFG.menuManager.getManageDiplomacy_Vassals().getMenuElement(1).setText("");
                            break;
                        }
                    }
                    CFG.game.setActiveProvinceID(-1);
                    CFG.menuManager.getDrawCivilization().setVisible(false);
                    CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                }
                CFG.map.getMapCoordinates().setDisableMovingMap(false);
            }
        } : (CFG.menuManager.getInManageDiplomacy() ? new ExtraAction(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.menuManager.getManageDiplomacy_Alliances().getVisible() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Alliances().getMenuElementsSize(); ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Alliances().getMenuPosY()) continue;
                            if (i == 0) {
                                CFG.game.addAlliance("");
                                CFG.game.getAlliance(CFG.game.getAlliancesSize() - 1).addCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                                if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() != 0) {
                                    CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID()).removeCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                                }
                                CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setAllianceID(CFG.game.getAlliancesSize() - 1);
                                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getAlliancesSize() - 1;
                                CFG.menuManager.setViewID(Menu.eCUSTOMIZE_ALLIANCE);
                                CFG.game.disableDrawCivilizationRegions_ActiveProvince();
                                CFG.menuManager.getDrawCivilization().setVisible(false);
                                CFG.game.setActiveProvinceID(-1);
                                CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                                return;
                            }
                            if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() != 0) {
                                if (i == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID()) break;
                                CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID()).removeCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            }
                            CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setAllianceID(i);
                            CFG.game.getAlliance(i).addCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            CFG.game.checkAlliances();
                            CFG.menuManager.rebuildManageDiplomacy_Alliances();
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.game.setActiveProvinceID(-1);
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            return;
                        }
                        int nProvinceBefore = CFG.game.getActiveProvinceID();
                        CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() > 0) {
                                if (CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).getAllianceID() != 0) {
                                    CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).getAllianceID()).removeCivilization(CFG.game.getProvince(nProvinceBefore).getCivID());
                                }
                                CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).setAllianceID(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID());
                                CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID()).addCivilization(CFG.game.getProvince(nProvinceBefore).getCivID());
                                CFG.game.checkAlliances();
                                CFG.menuManager.rebuildManageDiplomacy_Alliances();
                            } else {
                                CFG.game.addAlliance("");
                                CFG.game.getAlliance(CFG.game.getAlliancesSize() - 1).addCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                                CFG.game.getAlliance(CFG.game.getAlliancesSize() - 1).addCivilization(CFG.game.getProvince(nProvinceBefore).getCivID());
                                if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() != 0) {
                                    CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID()).removeCivilization(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                                }
                                if (CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).getAllianceID() != 0) {
                                    CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).getAllianceID()).removeCivilization(CFG.game.getProvince(nProvinceBefore).getCivID());
                                }
                                CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setAllianceID(CFG.game.getAlliancesSize() - 1);
                                CFG.game.getCiv(CFG.game.getProvince(nProvinceBefore).getCivID()).setAllianceID(CFG.game.getAlliancesSize() - 1);
                                CFG.game.checkAlliances();
                                CFG.menuManager.rebuildManageDiplomacy_Alliances();
                                CFG.game.disableDrawCivilizationRegions_ActiveProvince();
                            }
                        }
                        CFG.menuManager.getDrawCivilization().setVisible(false);
                        CFG.game.setActiveProvinceID(-1);
                        CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_Pacts3() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Pacts3().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElementsSize() - 1).setClickable(true);
                            }
                            CFG.menuManager.getManageDiplomacy_Pacts3().getMenuElement(i).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                            CFG.game.setActiveProvinceID(-1);
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            return;
                        }
                    }
                    int nProvinceBefore = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        if (CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getCivNonAggressionPact(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) == 0) {
                                CFG.game.setCivNonAggressionPact(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 5);
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                }
                                CFG.menuManager.rebuildManageDiplomacy_Pacts3();
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = -1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = -1;
                            } else {
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                            }
                        } else {
                            CFG.game.setActiveProvinceID(nProvinceBefore);
                        }
                    } else {
                        CFG.game.setActiveProvinceID(nProvinceBefore);
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_Truces() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Truces().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Truces().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Truces().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Truces().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Truces().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(CFG.menuManager.getManageDiplomacy_Truces().getMenuElementsSize() - 1).setClickable(true);
                            }
                            CFG.menuManager.getManageDiplomacy_Truces().getMenuElement(i).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                            CFG.game.setActiveProvinceID(-1);
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            return;
                        }
                    }
                    int nProvinceBefore = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        if (CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getCivTruce(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) == 0) {
                                CFG.game.setCivTruce(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 5);
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                }
                                CFG.menuManager.rebuildManageDiplomacy_Truces();
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = -1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = -1;
                            } else {
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                            }
                        } else {
                            CFG.game.setActiveProvinceID(nProvinceBefore);
                        }
                    } else {
                        CFG.game.setActiveProvinceID(nProvinceBefore);
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_MilitaryAccess() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuPosX() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElementsSize() - 1).setClickable(true);
                            }
                            CFG.menuManager.getManageDiplomacy_MilitaryAccess().getMenuElement(i).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                            CFG.game.setActiveProvinceID(-1);
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            return;
                        }
                    }
                    int nProvinceBefore = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        if (CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getMilitaryAccess(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) == 0) {
                                CFG.game.setMilitaryAccess(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 5);
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                }
                                CFG.menuManager.rebuildManageDiplomacy_MilitaryAccess();
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = -1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = -1;
                            } else {
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                            }
                        } else {
                            CFG.game.setActiveProvinceID(nProvinceBefore);
                        }
                    } else {
                        CFG.game.setActiveProvinceID(nProvinceBefore);
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_Guarantee() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Guarantee().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElementsSize() - 1).setClickable(true);
                            }
                            CFG.menuManager.getManageDiplomacy_Guarantee().getMenuElement(i).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                            CFG.game.setActiveProvinceID(-1);
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            return;
                        }
                    }
                    int nProvinceBefore = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        if (CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getGuarantee(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) == 0) {
                                CFG.game.setGuarantee(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 5);
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                }
                                CFG.menuManager.rebuildManageDiplomacy_Guarantee();
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = -1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = -1;
                            } else {
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                            }
                        } else {
                            CFG.game.setActiveProvinceID(nProvinceBefore);
                        }
                    } else {
                        CFG.game.setActiveProvinceID(nProvinceBefore);
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_DefensivePact() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < CFG.menuManager.getManageDiplomacy_Defensive().getMenuElementsSize() - 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Defensive().getMenuPosY()) continue;
                            if (i == 0) {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            } else {
                                if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 == CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) return;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            }
                            if (CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 > 0 && CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 > 0) {
                                CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(CFG.menuManager.getManageDiplomacy_Defensive().getMenuElementsSize() - 1).setClickable(true);
                            }
                            CFG.menuManager.getManageDiplomacy_Defensive().getMenuElement(i).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                            CFG.game.setActiveProvinceID(-1);
                            CFG.menuManager.getDrawCivilization().setVisible(false);
                            CFG.MANAGE_DIPLOMACY_DRAW_HELP_LINE = false;
                            CFG.map.getMapCoordinates().setDisableMovingMap(false);
                            return;
                        }
                    }
                    int nProvinceBefore = CFG.game.getActiveProvinceID();
                    CFG.game.setProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        if (CFG.game.getProvince(nProvinceBefore).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            if (CFG.game.getDefensivePact(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) == 0) {
                                CFG.game.setDefensivePact(CFG.game.getProvince(nProvinceBefore).getCivID(), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 5);
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                }
                                CFG.menuManager.rebuildManageDiplomacy_Defensive();
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1 = -1;
                                CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV2 = -1;
                            } else {
                                CFG.game.setActiveProvinceID(nProvinceBefore);
                            }
                        } else {
                            CFG.game.setActiveProvinceID(nProvinceBefore);
                        }
                    } else {
                        CFG.game.setActiveProvinceID(nProvinceBefore);
                    }
                } else if (CFG.menuManager.getInManageDiplomacy_Relations_Interactive() && CFG.map.getMapCoordinates().getDisableMovingMap()) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                        for (int i = 0; i < 1; ++i) {
                            if (nPosX < CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuPosX() || nPosX > CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getPosX() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuPosX() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getWidth() || nPosY < CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuPosY() || nPosY > CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getPosY() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).getHeight() + CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuPosY()) continue;
                            if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                                CFG.toast.setInView(CFG.langManager.get("CustomizeRelations") + ": " + CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getCivName());
                                CFG.toast.setTimeInView(3000);
                                if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID == CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2) {
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(1).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(3).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(2).setCurrent(0);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(4).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(6).setClickable(false);
                                    CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(5).setCurrent(0);
                                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2 = 0;
                                }
                            }
                            CFG.menuManager.getManageDiplomacy_Relations_Interactive().getMenuElement(i).setText(CFG.langManager.get("CustomizeRelations") + " [" + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName() + "]");
                            break;
                        }
                    }
                    CFG.menuManager.getDrawCivilization().setVisible(false);
                    CFG.game.setActiveProvinceID(-1);
                }
                CFG.menuManager.getDrawCivilization().setVisible(false);
                CFG.map.getMapCoordinates().setDisableMovingMap(false);
            }
        } : (CFG.menuManager.getInCreateScenario_Assign() ? new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
                if (CFG.brushTool) {
                    for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                        if (CFG.game.getCiv(i).getCapitalProvinceID() != CFG.game.getActiveProvinceID()) continue;
                        if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() != CFG.iCreateScenario_AssignProvinces_Civ) {
                            CFG.setDialogType(Dialog.CREATE_SCENARIO_ASSIGN_CIVILIZATION);
                        }
                        return;
                    }
                }
            }
        } : new ExtraAction(){

            @Override
            public void extraAction(int nPosX, int nPosY) {
            }
        }));
    }

    protected final void setUpdateStartMovePosX(boolean updateStartMovePosX) {
        this.updateStartMovePosX = updateStartMovePosX;
    }

    protected final void setUpdateStartMovePosY(boolean updateStartMovePosY) {
        this.updateStartMovePosY = updateStartMovePosY;
    }

    protected final boolean getActionMap() {
        return this.actionMap;
    }

    protected final void setActionMap(boolean actionMap) {
        this.actionMap = actionMap;
    }

    protected final long getActionDownTime() {
        return this.lActionDownTime;
    }

    protected final void setActionDownTime(long lActionDownTime) {
        this.lActionDownTime = lActionDownTime;
    }

    private static interface ReverseDirection2 {
        public int getNewPos(int var1, int var2);
    }

    private static interface ReverseDirection {
        public int getStartMovePos(int var1);
    }

    protected static interface ExtraAction {
        public void extraAction(int var1, int var2);
    }
}
