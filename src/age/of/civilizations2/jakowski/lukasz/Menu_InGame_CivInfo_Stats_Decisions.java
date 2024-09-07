/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_CivInfo_Stats_Decisions
extends SliderMenu {
    int tempElemH;
    int tY;
    int tempW;
    protected Menu_InGame_CivInfo_Stats_Decisions() {
        int i;
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        tempW = CFG.CIV_INFO_MENU_WIDTH;
        tY = CFG.PADDING;
        tempElemH = CFG.isAndroid() ? Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4;

        if (CFG.SPECTATOR_MODE) {
            menuElements = Sandbox_Stat_Action_Init(menuElements);
        } else {
            for (i = 0; i < CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanFormSize(); ++i) {
                menuElements.add(new Button_Diplomacy_FormCivilization(CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(i), 0, tY, tempW - 2, true, CFG.canFormACiv(CFG.getActiveCivInfo(), CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(i), true)){

                    @Override
                    protected void actionElement(int iID) {
                        CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                        CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.viewsManager.disableAllViews();
                        CFG.game.resetChooseProvinceData();
                        CFG.game.resetRegroupArmyData();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.resetChooseProvinceData_Immediately();
                        CFG.gameAction.hideAllProvinceActionViews();
                        CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                        CFG.loadFormableCiv_GameData(CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(iID));
                        CFG.menuManager.setViewID(Menu.eINGAME_FORMABLE_CIV_PROVINCES);
                        CFG.map.getMapBG().updateWorldMap_Shaders();
                    }
                });
                tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            }
            if (CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
                menuElements.add(new Button_Diplomacy_Civilize(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 0, tY, tempW - 2, true, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).CIVILIZE_TECH_LEVEL){

                    @Override
                    protected void actionElement(int iID) {
                        CFG.menuManager.rebuildInGame_Civilize(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                        CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_IDEOLOGIES_MODE);
                    }
                });
                tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            }
            menuElements.add(new Button_Diplomacy_Action_Tech(Images.technology, CFG.langManager.get("Technology"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    if (CFG.menuManager.getVisibleInGame_Technology()) {
                        CFG.menuManager.setVisibleInGame_Technology(false);
                    } else {
                        CFG.menuManager.rebuildInGame_Technology(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    }
                }

                @Override
                protected void buildElementHover() {
                    try {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        int pointsLeft = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).civGameData.skills.getPointsLeft(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TechnologyPoints") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + pointsLeft, pointsLeft > 0 ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        this.menuElementHover = null;
                    }
                }
            });
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                menuElements.add(new Button_Diplomacy_Action(Images.diplo_alliance, CFG.langManager.get("LeaveAlliance"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                    @Override
                    protected void actionElement(int iID) {
                        CFG.menuManager.rebuildInGame_LeaveAllinace(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    }
                });
                tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            }
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_vassal, CFG.langManager.get("Vassals"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    if (CFG.menuManager.getVisibleInGame_Tribute()) {
                        CFG.menuManager.setVisibleInGame_Tribute(false);
                    } else {
                        CFG.menuManager.rebuildInGame_Tribute();
                    }
                }

                @Override
                protected void buildElementHover() {
                    int i;
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Vassals"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Ideology_Vassal(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID(), CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    for (i = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() - 1; i > 0; --i) {
                        if (CFG.game.getCiv(i).getNumOfProvinces() <= 0 || CFG.game.getCiv(i).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) continue;
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(i));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(i).getCivName() + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getIncome_Vassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    for (i = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() + 1; i < CFG.game.getCivsSize(); ++i) {
                        if (CFG.game.getCiv(i).getNumOfProvinces() <= 0 || CFG.game.getCiv(i).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) continue;
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(i));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(i).getCivName() + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getIncome_Vassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    if (nElements.size() <= 1) {
                        nElements.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NoVassals"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Ideology_Vassal(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
            });
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                menuElements.add(new Button_Diplomacy_Action(Images.diplo_vassal, CFG.langManager.get("DeclarationOfIndependence"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()) == 0){

                    @Override
                    protected void actionElement(int iID) {
                        CFG.menuManager.rebuildInGame_DeclarationOfIndependence(CFG.getActiveCivInfo());
                    }

                    @Override
                    protected void buildElementHover() {
                        try {
                            ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                            ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                            if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()) > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()))));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                                this.menuElementHover = new MenuElement_Hover_v2(nElements);
                            } else {
                                this.menuElementHover = null;
                            }
                        }
                        catch (IndexOutOfBoundsException ex) {
                            this.menuElementHover = null;
                        }
                    }
                });
                tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
            }
            menuElements.add(new Button_Diplomacy_Action(Images.top_diplomacy_points, CFG.langManager.get("ReleaseAVassal"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    CFG.viewsManager.disableAllViews();
                    CFG.game.resetChooseProvinceData();
                    CFG.game.resetRegroupArmyData();
                    CFG.game.setActiveProvinceID(-1);
                    CFG.game.resetChooseProvinceData_Immediately();
                    CFG.gameAction.hideAllProvinceActionViews();
                    CFG.game.getSelectedProvinces().clearSelectedProvinces();
                    CFG.createVassal_Data = new CreateVassal_Data();
                    CFG.selectMode = true;
                    CFG.brushTool = false;
                    CFG.VIEW_SHOW_VALUES = false;
                    CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL);
                    Game_Render_Province.updateDrawProvinces();
                    CFG.map.getMapBG().updateWorldMap_Shaders();
                }
            });
            menuElements.add(new Button_Diplomacy_Action(Images.editor_city, CFG.langManager.get("MoveCapital"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.gameAction.moveCapital_CanMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())){

                @Override
                protected void buildElementHover() {
                    if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        if (!CFG.gameAction.moveCapital_CanMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheCapitalCityHasRecentlyBeenMoved"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.city, CFG.PADDING, 0));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + Math.abs(Game_Calendar.TURN_ID - (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalMoved_LastTurnID() + 50)))));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", Math.abs(Game_Calendar.TURN_ID - (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalMoved_LastTurnID() + 50))) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                            if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName().length() > 0) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MoveCapitalTo") + ": "));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize() > 0 ? CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCity(0).getCityName() : CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                    nData.clear();
                                }
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectProvince"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAtWar()) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheCapitalCityIsLost"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.gameAction.moveCapital_Cost(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()), CFG.COLOR_INGAME_GOLD));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                        } else {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OccupiedProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince(), CFG.PADDING, 0));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName().length() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getName()));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                        }
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectProvince"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAtWar()) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TheCapitalCityIsLost"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    }
                }

                @Override
                protected boolean getClickable() {
                    return super.getClickable() && CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getActiveProvinceID() != CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() < 0 || CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAtWar());
                }

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_MoveCapital(CFG.game.getActiveProvinceID());
                }
            });

            //automove button change//
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_army, CFG.langManager.get("AutoMobilize"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();

                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoMobilizeToolTip")));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();

                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }

                @Override
                protected boolean getClickable() {
                    //only active not during turns
                    return super.getClickable();// && (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS);
                }

                @Override
                protected void actionElement(int iID) {
                    //toggle ai movement var
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).armyAImovement = !CFG.game.getPlayer(CFG.PLAYER_TURNID).armyAImovement;
                    CFG.toast.setInView(CFG.langManager.get("AutoMobilize") + ": " + (CFG.game.getPlayer(CFG.PLAYER_TURNID).armyAImovement ? "On" : "Off"));
                }
            });

            //stability button change//
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_popstability, CFG.langManager.get("Stability"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true) {

                @Override
                protected void buildElementHover() {
                    if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();

                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AssimilateTheProvincesToIncreaseStability")));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();

                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();

                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectProvince"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();

                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    }
                }

                @Override
                protected boolean getClickable() {
                    return super.getClickable() && CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                }

                @Override
                protected void actionElement(int iID) {
                    if (CFG.viewsManager.getActiveViewID() != ViewsManager.VIEW_PROVINCE_STABILITY_MODE) {
                        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).lProvincesWithLowStability.size() > 0) {
                            CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).lProvincesWithLowStability.get(0));
                        }
                        CFG.viewsManager.setActiveViewID(ViewsManager.VIEW_PROVINCE_STABILITY_MODE);
                    } else {
                        CFG.viewsManager.setActiveViewID(-1, false);
                    }
                }
            });

            menuElements.add(new Button_Diplomacy_Action_Goverment(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID(), CFG.langManager.get("ChangeTypeOfGovernment"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_ChangeGovernment();
                }

                @Override
                protected void buildElementHover() {
                    try {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        int pointsLeft = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).civGameData.skills.getPointsLeft(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeTypeOfGovernment"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getNumberWithSpaces("" + Ideologies_Manager.getChangeGovernmentCost(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())), CFG.COLOR_INGAME_GOLD));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("2.2", CFG.COLOR_INGAME_MOVEMENT));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        this.menuElementHover = null;
                    }
                }
            });
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_loan, CFG.langManager.get("TakeLoan"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_TakeLoan(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                }

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 0, CFG.PADDING));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TakeLoan"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_loan, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("-0.6", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }
            });
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_loan2, CFG.langManager.get("RepayLoans"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_Loans(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                }

                @Override
                protected void buildElementHover() {
                    try {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        int pointsLeft = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).civGameData.skills.getPointsLeft(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Loans") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getLoansSize(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_loan2, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        this.menuElementHover = null;
                    }
                }
            });
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
        }
        this.initMenu(new SliderMenuTitle(null, CFG.TEXT_HEIGHT + CFG.PADDING * 2, false, false){

            @Override
            protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
                ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, Menu_InGame_CivInfo_Stats_Decisions.this.getPosX() + iTranslateX, Menu_InGame_CivInfo_Stats_Decisions.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() - this.getHeight(), Menu_InGame_CivInfo_Stats_Decisions.this.getWidth(), this.getHeight(), true, false);
                CFG.drawRect_InfoBox_Left_Title(oSB, Menu_InGame_CivInfo_Stats_Decisions.this.getPosX() + iTranslateX, Menu_InGame_CivInfo_Stats_Decisions.this.getPosY() - this.getHeight(), Menu_InGame_CivInfo_Stats_Decisions.this.getWidth() - 2, this.getHeight());
                CFG.fontMain.getData().setScale(0.7f);
                CFG.drawTextWithShadow(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.7f) / 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.7f) / 2, CFG.COLOR_TEXT_CIV_INFO_TITLE);
                CFG.fontMain.getData().setScale(1.0f);
            }
        }, 0 /*+ AoCGame.LEFT*/, ImageManager.getImage(Images.new_game_top).getHeight() + CFG.PADDING * 4 + (int)((float)CFG.TEXT_HEIGHT * 0.6f) + ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4, tempW, (CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2) * 6, menuElements, false, false);
        this.updateLanguage();
        for (i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setCurrent(i % 2);
        }
    }

    protected ArrayList<MenuElement> Sandbox_Stat_Action_Init(ArrayList<MenuElement> menuElements) {
        int i;

        menuElements.add(new Button_Diplomacy_FormCivilization_Sandbox("", 0, tY, tempW - 2, true, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eINGAME_FORMABLE_CIV_PROVINCES;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                CFG.viewsManager.disableAllViews();
                CFG.game.resetChooseProvinceData();
                CFG.game.resetRegroupArmyData();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.resetChooseProvinceData_Immediately();
                CFG.gameAction.hideAllProvinceActionViews();
                CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

                CFG.formableCivs_GameData = new FormableCivs_GameData();
                CFG.formableCivs_GameData.setFormableCivTag(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivTag());
                CFG.formableCivs_GameData.setCapitalProvinceID(CFG.game.getCiv(CFG.getActiveCivInfo()).getCapitalProvinceID());

                //CFG.loadFormableCiv_GameData(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivTag());
                CFG.menuManager.setViewID(Menu.eINGAME_FORMABLE_CIV_PROVINCES);
                CFG.map.getMapBG().updateWorldMap_Shaders();
            }
        });
        tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
        for (i = 0; i < CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanFormSize(); ++i) {
            menuElements.add(new Button_Diplomacy_FormCivilization_Sandbox(CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(i), 0, tY, tempW - 2, true, true){

                @Override
                protected void actionElement(int iID) {
                    iID -= 1;
                    CFG.sandbox_task = Menu.eINGAME_FORMABLE_CIV_PROVINCES;
                    try {
                        try {
                            FileHandle file = Gdx.files.local(CFG.FILE_MAP_PATH + CFG.map.getFile_ActiveMap_Path() + CFG.FILE_MAP_FORMABLE_CIVS_PATH + CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(iID));
                            CFG.formableCivs_GameData = (FormableCivs_GameData) CFG.deserialize(file.readBytes());
                        } catch (GdxRuntimeException ex) {
                            FileHandle file = Gdx.files.internal(CFG.FILE_MAP_PATH + CFG.map.getFile_ActiveMap_Path() + CFG.FILE_MAP_FORMABLE_CIVS_PATH + CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(iID));
                            CFG.formableCivs_GameData = (FormableCivs_GameData) CFG.deserialize(file.readBytes());
                        }
                    }
                    catch (ClassNotFoundException | IOException | IndexOutOfBoundsException e) {
                        CFG.toast.setInView(CFG.langManager.get("Error") + " " + CFG.langManager.get("XDoesNotExist", CFG.langManager.get("FormableCivilization")), CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                        CFG.toast.setTimeInView(4500);
                        CFG.exceptionStack(e);
                        return;
                    }

                    CFG.formCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    CFG.menuManager.setViewIDWithoutAnimation(Menu.eINGAME);
                    CFG.menuManager.setVisible_InGame_CivInfo(false);
                    CFG.map.getMapBG().updateWorldMap_Shaders();
                    CFG.game.buildCivilizationsRegions_TextOver(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    CFG.updateActiveCivInfo_InGame();
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    CFG.menuManager.rebuildInGame_Civ_Info_Decisions();

                    CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).loadPlayersFlag();

                    /*CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                    CFG.viewsManager.disableAllViews();
                    CFG.game.resetChooseProvinceData();
                    CFG.game.resetRegroupArmyData();
                    CFG.game.setActiveProvinceID(-1);
                    CFG.game.resetChooseProvinceData_Immediately();
                    CFG.gameAction.hideAllProvinceActionViews();
                    CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                    CFG.loadFormableCiv_GameData(CFG.game.getCiv(CFG.getActiveCivInfo()).getTagsCanForm(iID));
                    CFG.menuManager.setViewID(Menu.eINGAME_FORMABLE_CIV_PROVINCES);
                    CFG.map.getMapBG().updateWorldMap_Shaders();*/
                }
            });
            tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
        }
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_war, CFG.langManager.get("DeclareWar"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_DECLAREWAR;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }

            @Override
            protected int getSFX() {
                return this.getClickable() ? SoundsManager.SOUND_WAR : super.getSFX();
            }
        });
        tY += tempElemH;
        /*boolean canPrepareForWar = false;
        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
            for (i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
                if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.getActiveCivInfo() == CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) || CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i))) continue;
                canPrepareForWar = true;
                break;
            }
        }
        if (!canPrepareForWar) {
            for (i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == i || CFG.game.getCiv(i).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCivsAtWar(CFG.getActiveCivInfo(), i)) continue;
                canPrepareForWar = true;
                break;
            }
        }
        if (canPrepareForWar) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_war_preparations, CFG.langManager.get("PrepareForWar"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())){

                @Override
                protected void buildElementHover() {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (Game_Calendar.TURN_ID <= 4) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) > 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()))));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (!CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouMustBorderWithCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Government") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getColor()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PrepareForTheWarAgainst") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo(), 0, CFG.PADDING));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war_preparations, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                }

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_WarPreparations(CFG.getActiveCivInfo());
                }

                @Override
                protected boolean getClickable() {
                    return super.getClickable() && Game_Calendar.TURN_ID > 4 && CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) == 0;
                }

                @Override
                protected int getSFX() {
                    return this.getClickable() ? SoundsManager.SOUND_WAR : super.getSFX();
                }
            });
            tY += tempElemH;
        }*/
        /*menuElements.add(new Button_Diplomacy_Action(Images.diplo_rivals, CFG.langManager.get("SendUltimatum"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())){

                @Override
                protected void buildElementHover() {
                    if (Game_Calendar.TURN_ID <= 4) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AWarCantBeDeclaredInFirstXTurns", 4) + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else if (CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) > 0) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeHaveATruceUntil") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()))));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) + "]", CFG.COLOR_TEXT_OPTIONS_NS_HOVER));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else if (!CFG.game.canDeclareWar_TribalColonize_NeedsToBorder(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouMustBorderWithCivilization"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_war, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getActiveCivInfo()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.getActiveCivInfo()).getCivName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Government") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID()).getColor()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Ideology(CFG.game.getCiv(CFG.getActiveCivInfo()).getIdeologyID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else if (CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() != CFG.getActiveCivInfo() && CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("YouCanSendDemandsOnlyToALordOrYourVassal"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else if (CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > -10.0f) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OurRelationsNeedsToBeBelow") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("-10", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_relations, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover_v2(nElements);
                    } else {
                        this.menuElementHover = null;
                    }
                }

                @Override
                protected void actionElement(int iID) {
                    CFG.ultimatum = new Ultimatum_GameData();
                    CFG.menuManager.rebuildInGame_SendUltimatum(CFG.getActiveCivInfo());
                }

                @Override
                protected boolean getClickable() {
                    return super.getClickable() && Game_Calendar.TURN_ID > 4 && CFG.game.getCivTruce(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo()) == 0 && (CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() == CFG.getActiveCivInfo() || CFG.game.getCiv(CFG.getActiveCivInfo()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) && CFG.game.getCivRelation_OfCivB(CFG.getActiveCivInfo(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) <= -10.0f;
                }
            });
            tY += tempElemH;*/
        /*if (CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_rivals, CFG.langManager.get("CallToArms"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_CallToArms(CFG.getActiveCivInfo());
                }
            });
            tY += tempElemH;
        }*/
        if (!CFG.game.isAtPeace(CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_truce, CFG.langManager.get("PeaceTreaty"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
                @Override
                protected void actionElement(int iID) {
                    CFG.sandbox_task = Menu.eINGAME_PEACE_TREATY;
                    CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    CFG.viewsManager.disableAllViews();
                    CFG.game.setActiveProvinceID(-1);
                    CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                    CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    Game_Render_Province.updateDrawProvinces();
                }
            });
            tY += tempElemH;
        }
        /*menuElements.add(new Button_Diplomacy_Action(Images.transfer_control, CFG.langManager.get("Annexation"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eVICTORY_CONDITIONS;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;*/
        menuElements.add(new Button_Diplomacy_Action(Images.transfer_control, CFG.langManager.get("TransferControl"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.menuManager.rebuildInGame_AnnexTerritory(CFG.getActiveCivInfo());

                /*CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                CFG.game.setActiveProvinceID(-1);
                CFG.VIEW_SHOW_VALUES = false;
                CFG.selectMode = true;
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.menuManager.setViewID(Menu.eINGAME_SELECT_PROVINCES);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();*/
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.transfer_control, CFG.langManager.get("Occupy"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_OCCUPY;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_army, CFG.langManager.get("AddArmy"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                //CFG.menuManager.rebuildInGame_AnnexTerritory(CFG.getActiveCivInfo());
                CFG.game.resetChooseProvinceData();
                CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setMax(Math.max(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfUnits() * 2, 1000));
                CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setCurrent(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfUnits());
                CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(true);
                CFG.menuManager.setVisible_InGame_ActionInfo_RecruitInstantly();
                CFG.menuManager.setVisible_InGame_CivInfo(false);
                //Menu_InGame_CivInfo_Stats_Decisions.this.setVisible(false);

                /*
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_SET_UP_ARMY;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
                CFG.game.setActiveProvinceID(-1);
                CFG.VIEW_SHOW_VALUES = false;
                CFG.selectMode = true;
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.menuManager.setViewID(Menu.eINGAME_SELECT_PROVINCES);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();*/
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_popstability, CFG.langManager.get("Stability"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                int plyCivID = CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID();
                CFG.game.getCiv(plyCivID).setWarWeariness(0.0F);
                CFG.game.getCiv(plyCivID).setStability(1.0F);

                for (int j = 0; j < CFG.game.getCiv(plyCivID).getNumOfProvinces(); j++) {
                    int provinceID = CFG.game.getCiv(plyCivID).getProvinceID(j);

                    CFG.game.getProvince(provinceID).setHappiness(1.0F);
                    CFG.game.getProvince(provinceID).setRevolutionaryRisk(0.0F);

                    CFG.game.getProvince(provinceID).getCore().addNewCore(plyCivID, CFG.PLAYER_TURNID);
                    CFG.game.getProvince(provinceID).getCore().increaseOwnership(plyCivID, provinceID);

                    for (int j2 = CFG.game.getProvince(provinceID).getPopulationData().getNationalitiesSize() - 1; j2 >= 0; j2--) {
                        if (CFG.game.getProvince(provinceID).getPopulationData().getCivID(j2) != plyCivID) {
                            CFG.game.getProvince(provinceID).getPopulationData().setPopulationOfCivID(plyCivID, CFG.game.getProvince(provinceID).getPopulationData().getPopulationOfCivID(plyCivID) + CFG.game.getProvince(provinceID).getPopulationData().getPopulationID(j2));
                            CFG.game.getProvince(provinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(provinceID).getPopulationData().getCivID(j2), 0);
                            CFG.game.getProvince(provinceID).getPopulationData().updatePopulationOfProvince();

                            CFG.menuManager.updateInGame_ProvinceInfoGraph(provinceID);
                        }
                    }
                }

                CFG.toast.setInView(CFG.langManager.get("CoreConstruction") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                CFG.toast.setTimeInView(4500);

                CFG.updateActiveCivInfo_InGame();
                CFG.menuManager.updateInGame_TOP_All(plyCivID);
            }
        });
        menuElements.add(new Button_Diplomacy_Action_Goverment(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID(), CFG.langManager.get("Government"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.menuManager.rebuildInGame_ChangeGovernment();
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_alliance, CFG.langManager.get("CreateAlliance"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_COND_ALLIES;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_alliance, CFG.langManager.get("LeaveAlliance"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    int allianceID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID();
                    CFG.game.getAlliance(allianceID).removeCivilization(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setAllianceID(0);

                    CFG.updateActiveCivInfo_InGame();
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

                    CFG.toast.setInView(CFG.langManager.get("LeaveAlliance") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                    CFG.toast.setTimeInView(4500);
                }
            });
            tY += tempElemH;
        }
        /*if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_trade, CFG.langManager.get("TradeRequest"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.tradeRequest = new TradeRequest_GameData();
                    CFG.tradeRequest.iCivLEFT = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                    CFG.tradeRequest.iCivRIGHT = CFG.getActiveCivInfo();
                    CFG.menuManager.rebuildInGame_TradeRequest(CFG.getActiveCivInfo());
                }
            });
            tY += tempElemH;
        }*/
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_union, CFG.langManager.get("Union"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eEDITOR_UNIONS;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_non_aggression, CFG.langManager.get("NonAggressionPact"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_defensive_pact, CFG.langManager.get("DefensivePact"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_COND_DEFENSIVE;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        /*menuElements.add(new Button_Diplomacy_Action(Images.diplo_guarantee_gives, CFG.langManager.get("GuaranteeIndependence"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_COND_INDEPENDENCE;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;*/
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_vassal, CFG.langManager.get("Vassalizator"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        tY += tempElemH;
        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIsPupet()) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_lord, "Liberate Self", 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    int iLord = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID();
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setPuppetOfCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setVassalLiberityDesire(0.0F);
                    if (CFG.game.getMilitaryAccess(iLord, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) <= 0) {
                        CFG.gameAction.accessLost_UpdateArmies(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iLord);
                    }
                    if (CFG.game.getMilitaryAccess(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iLord) <= 0) {
                        CFG.gameAction.accessLost_UpdateArmies(iLord, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    }

                    CFG.palletManager.loadCivilizationStandardColor(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

                    CFG.historyManager.addHistoryLog(new HistoryLog_IsNotVassal(iLord, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                    CFG.toast.setInView(CFG.langManager.get("Liberation"), CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                    CFG.toast.setTimeInView(4500);

                    CFG.updateActiveCivInfo_InGame();
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

                    //CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL;
                    //CFG.game.getPlayer((int) CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                    //CFG.viewsManager.disableAllViews();
                    //CFG.game.setActiveProvinceID(-1);
                    //CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                    //CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    //Game_Render_Province.updateDrawProvinces();
                }
            });
            tY += tempElemH;

            //change autonomy
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_lord, CFG.langManager.get("Autonomy"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildManageDiplomacy_Vassals_Autonomy(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID());
                }
            });
            tY += tempElemH;
        }
        menuElements.add(new Button_Diplomacy_Action(Images.top_diplomacy_points, CFG.langManager.get("CreateAVassal"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eINGAME_CREATE_VASSAL;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.resetChooseProvinceData();
                CFG.game.resetRegroupArmyData();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.resetChooseProvinceData_Immediately();
                CFG.gameAction.hideAllProvinceActionViews();
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.createVassal_Data = new CreateVassal_Data();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL);
                Game_Render_Province.updateDrawProvinces();
                CFG.map.getMapBG().updateWorldMap_Shaders();
                CFG.updateActiveCivInfo_InGame();
                CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID());
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.top_diplomacy_points, "Create a State", 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eINGAME_CREATE_VASSAL_SELECT_CIV;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.resetChooseProvinceData();
                CFG.game.resetRegroupArmyData();
                CFG.game.setActiveProvinceID(-1);
                CFG.game.resetChooseProvinceData_Immediately();
                CFG.gameAction.hideAllProvinceActionViews();
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.createVassal_Data = new CreateVassal_Data();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL);
                Game_Render_Province.updateDrawProvinces();
                CFG.map.getMapBG().updateWorldMap_Shaders();
                CFG.updateActiveCivInfo_InGame();
                CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID());
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_revolution, CFG.langManager.get("SupportRebels"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.menuManager.rebuildInGame_SupportRebels(CFG.getActiveCivInfo(), -1);
            }
        });
        tY += tempElemH;
        /*if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_access_has, CFG.langManager.get("AskForMilitaryAccess"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_MilitartAccess_Ask(CFG.getActiveCivInfo());
                }
            });
            tY += tempElemH;
        }
        if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_access_gives, CFG.langManager.get("OfferMilitaryAccess"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_MilitartAccess_Give(CFG.getActiveCivInfo());
                }
            });
            tY += tempElemH;
        }*/
        if (!CFG.game.getCivsAtWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.getActiveCivInfo())) {
            menuElements.add(new Button_Diplomacy_Action(Images.diplo_gift, CFG.langManager.get("UpdateMoney"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

                @Override
                protected void actionElement(int iID) {
                    CFG.menuManager.rebuildInGame_SendGift(CFG.getActiveCivInfo());
                }
            });
        }
        menuElements.add(new Button_Diplomacy_Action(Images.editor_city, CFG.langManager.get("SetCapital"), 0, 0, tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight(), CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){
            @Override
            protected void actionElement(int iID) {
                if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getActiveProvinceID() != CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()) {
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setCapitalMoved_LastTurnID(Game_Calendar.TURN_ID);
                    int tempOld = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID();
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setCapitalProvinceID(CFG.game.getActiveProvinceID());
                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).setIsCapital(false);
                    if (tempOld >= 0) {
                        CFG.game.getProvince(tempOld).setIsCapital(false);
                        CFG.game.getProvince(tempOld).updateDrawArmy();
                        try {
                            CFG.game.getProvince(tempOld).getCity(0).setCityLevel(CFG.getEditorCityLevel(1));
                        } catch (IndexOutOfBoundsException var6) {
                        }
                    }

                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).setIsCapital(true);
                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).updateDrawArmy();
                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).setHappiness(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getHappiness() + 0.025F);

                    try {
                        CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
                    } catch (IndexOutOfBoundsException var5) {
                    }

                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID()).setDrawCities(true);

                    CFG.toast.setInView(CFG.langManager.get("CapitalMoved") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                    CFG.toast.setTimeInView(4500);

                    CFG.updateActiveCivInfo_InGame();
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                } else {
                    CFG.toast.setInView(CFG.langManager.get("Error") + CFG.langManager.get("Capital") + " Invalid Province!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                    CFG.toast.setTimeInView(4500);
                }
            }
        });
        tY += tempElemH;
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_relations_inc, CFG.langManager.get("ImproveRelations"), 0, 0, tY, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_INCRELATION;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        menuElements.add(new Button_Diplomacy_Action(Images.diplo_relations_dec, CFG.langManager.get("SendAnInsult"), 0, 0, tY += tempElemH, CFG.CIV_INFO_MENU_WIDTH - 2, tempElemH, true){

            @Override
            protected void actionElement(int iID) {
                CFG.sandbox_task = Menu.eCREATE_SCENARIO_EVENTS_OUT_DECRELATION;
                CFG.game.getPlayer((int)CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
                CFG.viewsManager.disableAllViews();
                CFG.game.setActiveProvinceID(-1);
                CFG.menuManager.setViewID(Menu.eINGAME_TRADE_SELECT_CIV);
                CFG.toast.setInView(CFG.langManager.get("SelectProvince"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                Game_Render_Province.updateDrawProvinces();
            }
        });
        return menuElements;
    }

    @Override
    protected void updateLanguage() {
        this.getTitle().setText(CFG.langManager.get("Decisions"));
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        if (Menu_InGame_CivInfo.lTime + 175L >= System.currentTimeMillis()) {
            iTranslateX = Menu_InGame_CivInfo.hideAnimation ? (iTranslateX -= (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0f))) : (iTranslateX += -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - Menu_InGame_CivInfo.lTime) / 175.0f)));
            CFG.setRender_3(true);
        } else if (Menu_InGame_CivInfo.hideAnimation) {
            super.setVisible(false);
            return;
        }
        ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + 2, true, false);
        this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        oSB.setColor(Color.WHITE);
        this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight() + 1, this.getWidth() - 2, 1);
        oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4f));
        ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + 1 + this.getHeight(), this.getWidth() - 2, 1);
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + 2 + this.getHeight(), this.getWidth(), 1);
        oSB.setColor(Color.WHITE);
        /*if (AoCGame.LEFT != 0) {
            oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
            ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight() + 2, true, false);
            oSB.setColor(Color.WHITE);
        }*/
    }

    @Override
    protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        if (sliderMenuIsActive) {
            super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        }
    }

    @Override
    protected void onHovered() {
        CFG.menuManager.setOrderOfMenu_InGame_CivInfo();
        CFG.toast.setInView(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
    }

    @Override
    protected void actionElement(int iID) {
        if (CFG.gameAction.getActiveTurnState() != Game_Action.TurnStates.INPUT_ORDERS) {
            return;
        }
        this.getMenuElement(iID).actionElement(iID);
    }

    @Override
    protected void setVisible(boolean visible) {
        if (visible) {
            super.setVisible(visible);
        }
    }

    @Override
    protected void actionClose() {
        super.setVisible(false);
        for (int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setVisible(false);
        }
    }

    @Override
    protected void setPosY(int iPosY) {
        super.setPosY(iPosY);
        this.setHeight(this.iMaxSliderPositionY);
        if (this.getPosY() + this.getHeight() > CFG.GAME_HEIGHT) {
            this.setHeight(Math.max(CFG.GAME_HEIGHT - this.getPosY(), CFG.BUTTON_HEIGHT / 2));
        }
        int tempElemH = CFG.isAndroid() ? Math.max(CFG.TEXT_HEIGHT + CFG.PADDING * 4, (int)((float)CFG.BUTTON_HEIGHT * 0.6f)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4;
        this.setHeight(Math.min(this.getHeight(), tempElemH * 6));
        this.updateMenuElements_IsInView();
    }
}

