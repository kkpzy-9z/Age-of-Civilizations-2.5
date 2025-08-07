package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import java.io.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

import java.lang.StringBuilder;
import java.util.*;

class Game {
    private int scenarioID;
    //lProvinces arrayList => fixed size array set whenever map is loaded
    //fucking huge performance improvement
    private Province[] lProvinces;
    private List<Region> lRegions;
    private int iRegionsSize;
    private boolean updateProvincesInView;
    private List<Integer> lProvincesInView;
    private List<Integer> lSeaProvincesInView;
    private List<Integer> lWastelandProvincesInView;
    private List<Civilization> lCivs;
    private int iCivsSize;
    private List<Integer> lCivsSortedAZ;
    private int iAvailableCivilizations;
    private List<Player> lPlayers;
    private int iPlayersSize;
    private List<City> lCities;
    private List<Mountain> lMountains;
    private List<Alliance> lAlliances;
    private int iAlliancesSize;
    private List<War_GameData> lWars;
    private int iWarsSize;
    protected List<PeaceTreaty_GameData_MessageData> lPeaceTreaties;
    protected String sRespondToPeaceTreatyID;
    private RandomTurnOrder oRTO;
    protected Wonders_Manager wondersManager;
    private int iActiveProvince;
    private Province_Animation2 activeProvince_Animation_Data;
    protected static int MAX_BELOW_ZERO_POINT_X;
    private List<Civilization_Region_Active> lActive_CivRegion;
    private List<Integer> lHighlightedProvinces;
    private int iHighlightedProvincesSize;
    private Province_Animation_MoveUnits2 highlightedProvince_AnimationData;
    private List<MoveUnits_Line> lHighlightedProvinces_MoveUnits_Lines;
    private MoveUnits_Line currentMoveUnitsLine;
    private Selected_Provinces selectedProvinces;
    private DrawMoveUnitsArmy drawMoveUnitsArmy;
    private static final float HIGHLIGHTED_PROVINCES_ANIMATION_TIME = 750.0f;
    private static final float HIGHLIGHTED_PROVINCES_ANIMATION_TIME_BACK = 350.0f;
    private static final int ACTIVE_CITIES_ANIMATION_TIME = 525;
    private long lTIME_ACTIVE_CITIES;
    private long lTIME_HIGHLIGHTED_CITIES;
    private boolean HIGHLIGHTED_CITIES_DISABLE_ANIMATION;
    protected RegroupArmy_Data currentRegroupArmy;
    private List<MoveUnits_Line> lCurrentRegroupArmyLine;
    private List<Civilization_Region_Style> lCivRegion_Styles;
    protected static final int REGION_STYLE_CLASSIC = 0;
    private boolean activeProvinceBorder_LastUpdateInCreateScenario;
    private ActiveProvinceBorderStyle activeProvinceBorder_LandByLand_Style;
    private ActiveProvinceBorderStyle activeProvinceBorder_LandBySea_Style;
    private ActiveProvinceBorderStyle activeProvinceBorder_SeaBySea_Style;
    private HighlightedProvince_Animation_UpdateOffset highlightedProvince_Animation_UpdateOffset;
    private List<DrawProvinceBorder_LandBySeaIDs> drawProvinceBorder_LandBySeaIDs;
    private Game_Scenarios gameScenarios;
    private Game_Cities gameCities;
    private Game_Mountains gameMountains;
    private Game_Wonders gameWonders;
    protected static final int CAN_MIGRATE_EVERY_X_TURNS = 10;
    private SetActiveProvince_ExtraAction setActiveProvince_ExtraAction;
    protected static final float LEADER_MAX_VALUE = 0.25f;
    protected static final float LEADER_MIN_VALUE = -0.25f;
    protected static final float RELEASE_VASSAL_PERC_OF_TECH = 0.72f;
    protected static final int RELEASE_VASSAL_PERC_OF_TECH_RAND = 22;
    protected static final int CREATE_VASSAL_STARTING_RELATION = 65;
    protected float fDashedLine_Percentage_HighlitedProvinceBorder;
    private long lDashedLineTime_Percentage_HighlitedProvinceBorder;
    private boolean highlightedProvinceBorder_BackAnimation;
    private boolean highlightedProvinceBorder_Update;
    protected static List<Integer> lBuildingsImages;
    protected static int iBuildingsWidth;
    private long lMoveUnitsLineTime;
    private boolean drawMoveUnitsArmy_UpdateAnimation;
    private List<Move_Units_JustDraw> lMoveUnits_JustDraw_AnotherArmies;
    private int iMoveUnits_JustDraw_AnotherArmiesSize;
    private boolean breakWasteland;
    protected static final int BASE_PROVINCE_VALUE = 1;
    protected static final int BASE_PROVINCE_VALUE_CAPITAL = 2;
    protected static final int WAR_CANT_BE_DECLARED_IN_FIRST_XTURNS = 4;
    protected static final int DECLARE_WAR_VASSALS_REACTIONS = 45;
    protected static final int DECLARE_WAR_REACTIONS = 35;
    protected List<Integer> loadArmiesWidth_ErrorIDs;

    protected final void updateActiveProvinceBorderStyle() {
        if ((!CFG.menuManager.getInGameView() || CFG.viewsManager.getActiveViewID() != ViewsManager.VIEW_DIPLOMACY_MODE) && !CFG.menuManager.getInCreateScenario_Assign() && !CFG.menuManager.getInCreateScenario_Assign_Select() && !CFG.menuManager.getInCreateScenario_Available_Provinces() && !CFG.menuManager.getInCreateScenario_Civilizations() && !CFG.menuManager.getInCreateScenario_Civilizations_Select() && !CFG.menuManager.getInCreateScenario_SetUpArmy() && !CFG.menuManager.getInCreateScenario_WastelandMap() && !CFG.menuManager.getInCreateScenario_TechnologyLevels() && !CFG.menuManager.getInCreateScenario_StartingMoney() && !CFG.menuManager.getInMapEditor_FormableCivs_Edit() && !CFG.menuManager.getInCreateScenario_HolyRomanEmpire() && !CFG.menuManager.getInGame_CreateAVassal() && !CFG.menuManager.getInGame_SelectProvinces() && !CFG.menuManager.getInGame_ShowProvinces() && !CFG.menuManager.getInGame_TradeSelectCiv() && !CFG.menuManager.getInMapEditor_FormableCivs_SelectFormable() && !CFG.menuManager.getInManageDiplomacy() && !CFG.menuManager.getInSelectAvailableCivilizations() && !CFG.menuManager.getInCreateCivilization() && !CFG.menuManager.getInCreateCity() && !CFG.menuManager.getInMapEditor_Terrain() && !CFG.menuManager.getInPalletOfCivsColorsEdit() && !CFG.menuManager.getInMapEditor_TradeZones_Edit() && !CFG.menuManager.getInMapEditor_Continents() && !CFG.menuManager.getInMapEditor_WastelandMaps_Edit() && !CFG.menuManager.getInCreateNewGame()) {
            if (!this.activeProvinceBorder_LastUpdateInCreateScenario) {
                return;
            }

            this.activeProvinceBorder_LandByLand_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersLandByLand(withProvinceID).updateDrawProvinceBorder_Active();
                }
            };
            this.activeProvinceBorder_LandBySea_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersLandBySea(withProvinceID).updateDrawProvinceBorder_ActiveSea();
                }
            };
            this.activeProvinceBorder_SeaBySea_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersSeaBySea(withProvinceID).updateDrawProvinceBorder_ActiveSea();
                }
            };
            this.highlightedProvince_Animation_UpdateOffset = new HighlightedProvince_Animation_UpdateOffset() {
                public void updateOffset() {
                    if (Game.this.iHighlightedProvincesSize > 0 || Game.this.getActiveProvinceID() >= 0 && Game.this.getProvince(Game.this.getActiveProvinceID()).getSeaProvince() || Game.this.lCurrentRegroupArmyLine.size() > 0) {
                        Game.this.highlightedProvince_AnimationData.update();
                    }

                }
            };
            this.highlightedProvince_AnimationData.setLineOffsetInterval(75);
            this.activeProvinceBorder_LastUpdateInCreateScenario = false;
        } else {
            if (this.activeProvinceBorder_LastUpdateInCreateScenario) {
                return;
            }

            this.activeProvinceBorder_LandByLand_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersLandByLand(withProvinceID).updateDrawProvinceBorder_ActiveDashed();
                }
            };
            this.activeProvinceBorder_LandBySea_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersLandBySea(withProvinceID).updateDrawProvinceBorder_ActiveSea_Dashed();
                }
            };
            this.activeProvinceBorder_SeaBySea_Style = new ActiveProvinceBorderStyle() {
                public void update(int nProvinceID, int withProvinceID) {
                    Game.this.getProvince(nProvinceID).getProvinceBordersSeaBySea(withProvinceID).updateDrawProvinceBorder_ActiveSea_Dashed();
                }
            };
            this.highlightedProvince_Animation_UpdateOffset = new HighlightedProvince_Animation_UpdateOffset() {
                public void updateOffset() {
                    Game.this.highlightedProvince_AnimationData.update();
                }
            };
            this.highlightedProvince_AnimationData.setLineOffsetInterval(90);
            this.activeProvinceBorder_LastUpdateInCreateScenario = true;
        }

        if (this.getActiveProvinceID() >= 0) {
            this.setActiveProvinceID(this.getActiveProvinceID());
        }

    }

    private final void addDrawProvinceBorder_LandBySea(int nProvinceID, int nWithProvinceID) {
        for (int i = this.drawProvinceBorder_LandBySeaIDs.size() - 1; i >= 0; --i) {
            if (this.drawProvinceBorder_LandBySeaIDs.get(i).iProvinceID == nProvinceID && this.drawProvinceBorder_LandBySeaIDs.get(i).withProvinceID == nWithProvinceID) {
                return;
            }
        }

        this.drawProvinceBorder_LandBySeaIDs.add(new DrawProvinceBorder_LandBySeaIDs(nProvinceID, nWithProvinceID));
    }

    private final void removeDrawProvinceBorder_LandBySea(final int nProvinceID, final int nWithProvinceID) {
        for (int i = this.drawProvinceBorder_LandBySeaIDs.size() - 1; i >= 0; --i) {
            if (this.drawProvinceBorder_LandBySeaIDs.get(i).iProvinceID == nProvinceID && this.drawProvinceBorder_LandBySeaIDs.get(i).withProvinceID == nWithProvinceID) {
                this.drawProvinceBorder_LandBySeaIDs.remove(i);
                return;
            }
        }
    }

    protected Game() {
        super();
        this.scenarioID = -1;
        this.lProvinces = null;
        this.lRegions = new ArrayList<Region>();
        this.iRegionsSize = 0;
        this.updateProvincesInView = true;
        this.lProvincesInView = new ArrayList<Integer>();
        this.lSeaProvincesInView = new ArrayList<Integer>();
        this.lWastelandProvincesInView = new ArrayList<Integer>();
        this.lCivs = null;
        this.iCivsSize = 0;
        this.lCivsSortedAZ = new ArrayList<Integer>();
        this.iPlayersSize = 0;
        this.lCities = null;
        this.lMountains = null;
        this.lAlliances = null;
        this.iAlliancesSize = 0;
        this.lWars = null;
        this.iWarsSize = 0;
        this.lPeaceTreaties = null;
        this.sRespondToPeaceTreatyID = "";
        this.wondersManager = new Wonders_Manager();
        this.lActive_CivRegion = new ArrayList<Civilization_Region_Active>();
        this.lHighlightedProvinces = new ArrayList<Integer>();
        this.iHighlightedProvincesSize = 0;
        this.lHighlightedProvinces_MoveUnits_Lines = new ArrayList<MoveUnits_Line>();
        this.currentMoveUnitsLine = null;
        this.selectedProvinces = new Selected_Provinces();
        this.drawMoveUnitsArmy = null;
        this.lTIME_ACTIVE_CITIES = 0L;
        this.lTIME_HIGHLIGHTED_CITIES = 0L;
        this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION = false;
        this.currentRegroupArmy = null;
        this.lCurrentRegroupArmyLine = new ArrayList<MoveUnits_Line>();
        this.activeProvinceBorder_LastUpdateInCreateScenario = true;
        this.drawProvinceBorder_LandBySeaIDs = new ArrayList<DrawProvinceBorder_LandBySeaIDs>();
        this.fDashedLine_Percentage_HighlitedProvinceBorder = 0.0f;
        this.highlightedProvinceBorder_BackAnimation = false;
        this.highlightedProvinceBorder_Update = false;
        this.lMoveUnitsLineTime = 0L;
        this.drawMoveUnitsArmy_UpdateAnimation = false;
        this.lMoveUnits_JustDraw_AnotherArmies = new ArrayList<Move_Units_JustDraw>();
        this.breakWasteland = false;
        this.loadArmiesWidth_ErrorIDs = new ArrayList<Integer>();
        this.iActiveProvince = -1;
        this.activeProvince_Animation_Data = new Province_Animation2();
        this.highlightedProvince_AnimationData = new Province_Animation_MoveUnits2();
        //this.lProvinces = new ArrayList<>();
        this.lCivRegion_Styles = new ArrayList<>();
        this.lCivRegion_Styles.add(new Civilization_Region_Style());
        this.lCivRegion_Styles.add(new Civilization_Region_Style() {
            protected void updatePB(int nProvinceID, int withProvinceID) {
                CFG.game.getProvince(nProvinceID).getProvinceBordersLandByLand(withProvinceID).updateDrawProvinceBorder_CivilizationRegion2();
            }
        });
        this.oRTO = new RandomTurnOrder();
    }

    protected static boolean checkUncivilizedMenu(final int nProvinceID) {
        return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0 && CFG.game.getProvince(nProvinceID).getCivID() == 0 && uncivilizedProvinceBordersWithCiv(nProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
    }

    protected static boolean uncivilizedProvinceBordersWithCiv(final int nProvinceID, final int nCivID) {
        if (nProvinceID >= 0) {
            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int uncivilizedCanMigrate_FromProvince_NumOfTurns(final int nProvinceID, final int nCivID) {
        try {
            return Math.max(1, 10 - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCore().getNumOfOwnership(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        } catch (final IndexOutOfBoundsException ex) {
            return 1;
        }
    }

    protected static boolean uncivilizedCanMigrate_FromProvince(final int nProvinceID, final int nCivID) {
        if (nProvinceID >= 0 && !CFG.game.getProvince(nProvinceID).isOccupied() && CFG.game.getProvince(nProvinceID).getCivID() == nCivID) {
            boolean bordersWithNeutralProvince = false;
            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == 0 && CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                    bordersWithNeutralProvince = true;
                    break;
                }
            }
            return bordersWithNeutralProvince && CFG.game.getProvince(nProvinceID).getCore().getNumOfOwnership(nCivID) >= 10;
        }
        return false;
    }

    protected static boolean uncivilizedCanMigrate(final int nProvinceID, final int nCivID) {
        if (nProvinceID >= 0) {
            if (CFG.game.getProvince(nProvinceID).getSeaProvince() || CFG.game.getProvince(nProvinceID).getWasteland() >= 0 || CFG.game.getProvince(nProvinceID).getCivID() > 0) {
                return false;
            }
            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean uncivilizedCanFishing(final int nProvinceID) {
        return nProvinceID >= 0 && CFG.game.getProvince(nProvinceID).getSeaProvince();
    }

    protected static boolean provinceBordersWithProvince_LandByLand(final int iA, final int iB_bordersWithA) {
        for (int i = 0; i < CFG.game.getProvince(iA).getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(iA).getNeighboringProvinces(i) == iB_bordersWithA) {
                return true;
            }
        }
        return false;
    }

    protected final void updateSetActiveProvinceID_ExtraAction() {
        try {
            if (CFG.menuManager.getInGameView()) {
                this.setActiveProvince_ExtraAction = new SetActiveProvince_ExtraAction() {
                    public void extraAction(int newActiveProvinceID) {
                        CFG.chooseProvinceMode_BEFORE = CFG.chooseProvinceMode;
                        CFG.activeProvince_BEFORE = Game.this.getActiveProvinceID();
                        if (newActiveProvinceID >= 0) {
                            if (!CFG.chooseProvinceMode) {
                                if (CFG.game.getProvince(newActiveProvinceID).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                    if (CFG.menuManager.getInGame_ProvinceRecruit_Visible()) {
                                        if (newActiveProvinceID != Game.this.iActiveProvince) {
                                            CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
                                            Game.this.checkProvinceActionMenu();
                                        }
                                    } else if (CFG.menuManager.getInGame_ProvinceRecruitInstantly_Visible()) {
                                        if (newActiveProvinceID != Game.this.iActiveProvince) {
                                            CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
                                            Game.this.checkProvinceActionMenu();
                                        }
                                    } else if (CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                                        if (newActiveProvinceID != Game.this.iActiveProvince) {
                                            CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
                                            Game.this.checkProvinceActionMenu();
                                        }
                                    } else if (CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible()) {
                                        if (newActiveProvinceID != Game.this.iActiveProvince) {
                                            CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(false);
                                            Game.this.checkProvinceActionMenu();
                                        }
                                    } else {
                                        Game.this.checkProvinceActionMenu();
                                    }
                                } else if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
                                    if (CFG.gameAction.canColonizieWasteland_Tech(newActiveProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                        CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                                        if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(newActiveProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(newActiveProvinceID));
                                        } else {
                                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(newActiveProvinceID));
                                        }
                                    } else {
                                        CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                                        if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(newActiveProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(newActiveProvinceID));
                                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(newActiveProvinceID));
                                        } else {
                                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(newActiveProvinceID));
                                        }
                                    }

                                    CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
                                } else {
                                    CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(false);
                                    CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
                                }
                            } else {
                                Game.this.resetChooseProvinceData();
                            }
                        }

                        Game.this.updateProvinceNameWidth(newActiveProvinceID);
                    }
                };
                this.updateProvinceNameWidth(this.getActiveProvinceID());
            } else if (CFG.menuManager.getInCreateScenario_Civilizations()) {
                this.setActiveProvince_ExtraAction = new SetActiveProvince_ExtraAction() {
                    public void extraAction(int newActiveProvinceID) {
                        if (Game.this.getActiveProvinceID() != newActiveProvinceID) {
                            if (Game.this.getActiveProvinceID() >= 0 && Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID() > 0) {
                                Game.this.disableDrawCivilizationRegions(Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID());
                            }

                            if (newActiveProvinceID >= 0 && Game.this.getProvince(newActiveProvinceID).getCivID() > 0) {
                                Game.this.enableDrawCivilizationRegions(Game.this.getProvince(newActiveProvinceID).getCivID(), 0);
                            }
                        }

                    }
                };
            } else if (CFG.menuManager.getInSelectAvailableCivilizations()) {
                this.setActiveProvince_ExtraAction = new SetActiveProvince_ExtraAction() {
                    public void extraAction(int newActiveProvinceID) {
                        if (Game.this.getActiveProvinceID() != newActiveProvinceID) {
                            if (Game.this.getActiveProvinceID() >= 0 && Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID() > 0) {
                                Game.this.disableDrawCivilizationRegions(Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID());
                            }

                            if (newActiveProvinceID >= 0 && Game.this.getProvince(newActiveProvinceID).getCivID() > 0 && Game.this.getCiv(Game.this.getProvince(newActiveProvinceID).getCivID()).getIsAvailable()) {
                                Game.this.enableDrawCivilizationRegions(Game.this.getProvince(newActiveProvinceID).getCivID(), 0);
                            }
                        }

                    }
                };
            } else if (CFG.menuManager.getInManageDiplomacy()) {
                this.setActiveProvince_ExtraAction = new SetActiveProvince_ExtraAction() {
                    public void extraAction(int newActiveProvinceID) {
                        if (Game.this.getActiveProvinceID() != newActiveProvinceID) {
                            if (Game.this.getActiveProvinceID() >= 0 && Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID() > 0) {
                                Game.this.disableDrawCivilizationRegions(Game.this.getProvince(Game.this.getActiveProvinceID()).getCivID());
                            }

                            if (newActiveProvinceID >= 0 && Game.this.getProvince(newActiveProvinceID).getCivID() > 0 && Game.this.getCiv(Game.this.getProvince(newActiveProvinceID).getCivID()).getIsAvailable()) {
                                Game.this.enableDrawCivilizationRegions(Game.this.getProvince(newActiveProvinceID).getCivID(), 0);
                            }
                        }

                    }
                };
            } else {
                this.setActiveProvince_ExtraAction = new SetActiveProvince_ExtraAction() {
                    public void extraAction(int newActiveProvinceID) {
                    }
                };
            }
        } catch (IndexOutOfBoundsException | NullPointerException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }
        }

    }

    protected final void randomPlayerCivilizations(final int nPlayerID) {
        final Random oR = new Random();
        int nPlayerCivID;
        while (true) {
            nPlayerCivID = oR.nextInt(this.gameScenarios.getNumOfCivs(this.scenarioID)) + 1;
            if (this.getCiv(nPlayerCivID).getNumOfProvinces() == 0) {
                continue;
            }
            if (!this.getCiv(nPlayerCivID).getControlledByPlayer()) {
                break;
            }
        }
        this.getPlayer(nPlayerID).setCivID(nPlayerCivID);
        this.getCiv(this.getPlayer(nPlayerID).getCivID()).setControlledByPlayer(true);
    }

    protected final void loadProvinces() {
        this.lProvinces = new Province[CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID())];

        for (int i = 0; i < CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()); ++i) {
            this.loadProvince(i);
        }
        this.updateProvincesSize();
    }

    protected final void loadProvince(final int i) {
        if (this.lProvinces == null) {
            this.lProvinces = new Province[CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID())];
        }

        final FileHandle fileProvinceData = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);
        try {
            this.lProvinces[i] = (new Province(i, (Province_GameData2) CFG.deserialize(fileProvinceData.readBytes())));
        } catch (final ClassNotFoundException e) {
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        } catch (final IOException | GdxRuntimeException e2) {
            try {
                this.build_LoadProvince(i);
            } catch (final GdxRuntimeException e3) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e3);
                }
            }

            if (CFG.LOGS) {
                CFG.exceptionStack(e2);
            }
        }
    }

    private final void build_LoadProvince(final int i) {
        final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "update/" + i);
        final String[] tempSplit = file.readString().split(";");
        final String[] tempX = tempSplit[0].split(",");
        final String[] tempY = tempSplit[1].split(",");
        final List<Short> tempPointsX = new ArrayList<Short>();
        final List<Short> tempPointsY = new ArrayList<Short>();
        for (int j = 0; j < tempX.length; ++j) {
            tempPointsX.add((short) Integer.parseInt(tempX[j]));
            tempPointsY.add((short) Integer.parseInt(tempY[j]));
        }
        this.lProvinces[i] = (new Province(i, new Province_GameData2(-1, tempPointsX, tempPointsY, null, new ArrayList<Short>(), new ArrayList<Short>())));
        this.saveProvince_Info_GameData(i);
        this.buildGameProvinceData(i);
    }

    protected final void saveProvince_Info_GameData(final int nProvinceID) {
        Province_GameData2 tempGameData = new Province_GameData2();
        try {
            final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + nProvinceID);
            tempGameData = (Province_GameData2) CFG.deserialize(file.readBytes());
        } catch (final ClassNotFoundException | GdxRuntimeException | IOException e) {
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        }
        Province_Info_GameData3 province_Info_GameData = new Province_Info_GameData3();
        province_Info_GameData.fGrowthRate = this.getProvince(nProvinceID).getGrowthRate_Population();
        province_Info_GameData.sTerrainTAG = CFG.terrainTypesManager.getTag(this.getProvince(nProvinceID).getTerrainTypeID());
        province_Info_GameData.iContinentID = this.getProvince(nProvinceID).getContinent();
        province_Info_GameData.iRegionID = this.getProvince(nProvinceID).getRegion();
        if (tempGameData != null) {
            province_Info_GameData.iShiftX = tempGameData.provinceInfo.iShiftX;
            province_Info_GameData.iShiftY = tempGameData.provinceInfo.iShiftY;
        } else {
            province_Info_GameData.iShiftX = this.getProvince(nProvinceID).getShiftX();
            province_Info_GameData.iShiftY = this.getProvince(nProvinceID).getShiftY();
        }
        tempGameData.provinceInfo = province_Info_GameData;
        final OutputStream osProvince = null;
        try {
            final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + nProvinceID);
            fileProvince.writeBytes(CFG.serialize(tempGameData), false);
        } catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (osProvince != null) {
                try {
                    osProvince.close();
                } catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        } finally {
            if (osProvince != null) {
                try {
                    osProvince.close();
                } catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
        province_Info_GameData = null;
    }

    protected final void saveProvince_Info_GameData_SHIFTXY(final int nProvinceID) {
        Province_GameData2 tempGameData = new Province_GameData2();
        try {
            final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + nProvinceID);
            tempGameData = (Province_GameData2) CFG.deserialize(file.readBytes());
        } catch (final ClassNotFoundException | GdxRuntimeException | IOException e) {
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        }
        Province_Info_GameData3 province_Info_GameData = new Province_Info_GameData3();
        province_Info_GameData.fGrowthRate = this.getProvince(nProvinceID).getGrowthRate_Population();
        province_Info_GameData.sTerrainTAG = CFG.terrainTypesManager.getTag(this.getProvince(nProvinceID).getTerrainTypeID());
        province_Info_GameData.iContinentID = this.getProvince(nProvinceID).getContinent();
        province_Info_GameData.iRegionID = this.getProvince(nProvinceID).getRegion();
        province_Info_GameData.iShiftX = this.getProvince(nProvinceID).getShiftX() * CFG.map.getMapDefaultScale(CFG.map.getActiveMapID()) / CFG.map.getMapScale(CFG.map.getActiveMapID());
        province_Info_GameData.iShiftY = this.getProvince(nProvinceID).getShiftY() * CFG.map.getMapDefaultScale(CFG.map.getActiveMapID()) / CFG.map.getMapScale(CFG.map.getActiveMapID());
        tempGameData.provinceInfo = province_Info_GameData;
        final OutputStream osProvince = null;
        try {
            final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + nProvinceID);
            fileProvince.writeBytes(CFG.serialize(tempGameData), false);
        } catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (osProvince != null) {
                try {
                    osProvince.close();
                } catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        } finally {
            if (osProvince != null) {
                try {
                    osProvince.close();
                } catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
        province_Info_GameData = null;
    }

    protected final void checkLandBySeaProvincesBorders() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince()) {
                this.getProvince(i).checkLandBySeaProvinceBorders();
            }
        }
    }

    protected final void checkSeaBySeaProvincesBorders() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).checkSeaBySeaProvinceBorders();
        }
    }

    protected final void buildProvinceBorder() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            int j;
            if (this.getProvince(i).getSeaProvince()) {
                for (j = 0; j < this.getProvince(i).getProvinceBordersSeaBySeaSize(); ++j) {
                    ((Province_Border) this.getProvince(i).getProvinceBordersSeaBySea().get(j)).updateDrawProvinceBorderSeaBySea();
                }
            } else {
                for (j = 0; j < this.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
                    ((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).updateDrawProvinceBorder_Inner(i);
                }
            }
        }

    }

    protected final void buildDrawArmy() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).updateDrawArmy();
        }
    }

    protected final void buildDrawArmy_ShowIDs() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).updateDrawArmy_ShowsIDs();
            this.getProvince(i).getArmy_Obj(0).updateArmyWidth("" + i);
        }
    }

    //updateProvincesSize => refine provinces by removing ghost provinces
    //no need to track province size now bc primitive array
    //nulls from lProvinces removed using manual iteration bc older Java versions dont have lambda
    private static List<Integer> ghostProvinces = new ArrayList<Integer>();
    protected final void updateProvincesSize() {
        ghostProvinces.clear();

        //get ghost provinces (errored provinces that couldnt load)
        for (int i = 0; i < lProvinces.length; i++) {
            if (lProvinces[i] == null) {
                CFG.toast.setInView("Province " + i + " corrupted, removing...", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
                ghostProvinces.add(i);
            }
        }

        //make new mem array of valid province size
        Province[] newProvinces = new Province[lProvinces.length - ghostProvinces.size()];
        int i = 0;
        for (Province province : lProvinces) {
            //add valid provinces
            if (province != null) {
                newProvinces[i] = province;
                i++;
            }
        }

        //update list to have no ghost provinces,
        //plus ghostProvince list tracker for getProvinces
        //for correctly adjusting saved province IDs
        this.lProvinces = newProvinces;

        //old: this.getProvincesSize() = this.lProvinces.length;
    }

    protected final void disposeMapData() {
        this.setActiveProvinceID(-1);
        this.clearProvincesInView();
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).disposeProvinceBG();
            for (int j = 0; j < this.getProvince(i).getWonderSize(); ++j) {
                this.getProvince(i).getWonder(j).dispose();
            }
        }
        this.lRegions.clear();
        this.iRegionsSize = 0;
        this.disposeCivilizations(); //reshuffled - now before provinces set to null to avoid exception
        this.scenarioID = -1;
        this.gameScenarios.disposeScenarios();
        this.lProvinces = null;
    }

    protected final void loadProvinceTextures() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).loadProvinceBG();
        }
    }

    protected final void loadProvinceTexture(final int i) {
        try {
            this.getProvince(i).loadProvinceBG();
        } catch (final IndexOutOfBoundsException | NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void loadProvinceNames_ALL() {
        try {
            final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "province_names/" + "names");
            final String text = file.readString();
            final String[] sNames = text.split("\n");
            for (int i = 0, iSize = sNames.length; i < iSize; ++i) {
                if (sNames[i].length() < 2) {
                    this.getProvince(i).setName("");
                } else {
                    this.getProvince(i).setName(sNames[i]);
                }
            }
            for (int i = sNames.length; i < this.getProvincesSize(); ++i) {
                this.getProvince(i).setName("");
            }
        } catch (final GdxRuntimeException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void generateProvincesNames() {
        final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "province_names/" + "prov_names");
        final String text = file.readString();
        final String[] sNames = text.split("\n");
        for (int i = 0, iSize = sNames.length; i < iSize; ++i) {
            if (sNames[i].length() > 1) {
                final FileHandle fileSave = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "province_names/" + i);
                fileSave.writeString(sNames[i], false);
            }
        }
    }

    protected final void buildBasinsOfSeaProvinces() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).setBasin(-1);
        }
        CFG.map.iNumOfBasins = 0;
        int currBasin = 0;
        final List<Integer> recentlyAdded = new ArrayList<Integer>();
        final List<Integer> currProvinces = new ArrayList<Integer>();
        for (int p = 0; p < this.getProvincesSize(); ++p) {
            if (this.getProvince(p).getSeaProvince() && this.getProvince(p).getBasinID() < 0) {
                this.getProvince(p).setBasin(currBasin);
                recentlyAdded.add(p);
                while (recentlyAdded.size() > 0) {
                    currProvinces.clear();
                    for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                        boolean wasntAdded = true;
                        for (int j = currProvinces.size() - 1; j >= 0; --j) {
                            if (Objects.equals(currProvinces.get(j), recentlyAdded.get(a))) {
                                wasntAdded = false;
                                break;
                            }
                        }
                        if (wasntAdded) {
                            currProvinces.add(recentlyAdded.get(a));
                        }
                    }
                    recentlyAdded.clear();
                    for (int a = currProvinces.size() - 1; a >= 0; --a) {
                        for (int k = 0; k < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++k) {
                            if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(k)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(k)).getBasinID() < 0) {
                                CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(k)).setBasin(currBasin);
                                recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(k));
                            }
                        }
                    }
                }
                ++currBasin;
                recentlyAdded.clear();
                currProvinces.clear();
            }
        }
        CFG.map.iNumOfBasins = currBasin;
    }

    protected final void loadRegions() {
        this.lRegions.clear();
        this.iRegionsSize = 0;
        final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "regions");
        final String text = file.readString();
        final String[] sAllRegions = text.split("\n");
        final List<Boolean> tempAdded = new ArrayList<Boolean>();
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            tempAdded.add(false);
        }
        for (int i = 0; i < sAllRegions.length; ++i) {
            final String[] sRegion = sAllRegions[i].split(";");
            final Region newRegion = new Region();
            for (String s : sRegion) {
                newRegion.addProvince(Integer.parseInt(s));
            }
            for (int j = 0; j < sRegion.length; ++j) {
                tempAdded.set(newRegion.getProvince(j), true);
            }
            this.lRegions.add(newRegion);
            this.lRegions.get(i).buildRegionBounds();
        }
        final Region tempRegionOfProvincesWithoutIDs = new Region();
        for (int k = 0; k < CFG.game.getProvincesSize(); ++k) {
            if (!tempAdded.get(k)) {
                tempRegionOfProvincesWithoutIDs.addProvince(k);
            }
        }
        if (tempRegionOfProvincesWithoutIDs.getProvincesSize2() > 0) {
            this.lRegions.add(tempRegionOfProvincesWithoutIDs);
            this.lRegions.get(this.lRegions.size() - 1).buildRegionBounds();
        }
        this.iRegionsSize = this.lRegions.size();
    }

    protected final void initGameScenarios() {
        (this.gameScenarios = new Game_Scenarios()).loadGame_Scenarios(true);
    }

    protected final void initGameCities() {
        this.gameCities = new Game_Cities();
    }

    protected final void initGameMountains() {
        this.gameMountains = new Game_Mountains();
        this.gameWonders = new Game_Wonders();
    }

    protected final void initPlayers() {
        if (this.lPlayers != null) {
            this.lPlayers.clear();
            this.iPlayersSize = 0;
        }
        this.lPlayers = new ArrayList<Player>();
        this.addPlayer();
        CFG.menuManager.rebuildCivilizations_Info_Players();
    }

    protected final void initPlayers(final List<Save_Player_GameData> savedPlayers) {
        if (this.lPlayers != null) {
            this.lPlayers.clear();
            this.iPlayersSize = 0;
        }
        this.lPlayers = new ArrayList<Player>();
        for (Save_Player_GameData savedPlayer : savedPlayers) {
            this.lPlayers.add(new Player(savedPlayer));
        }
        this.iPlayersSize = this.lPlayers.size();
    }

    protected final void build_PreDefinedCivsBorders(final int nCivID) {
        if ("ran".equals(CFG.game.getCiv(nCivID).getCivTag()) || this.getCiv(nCivID).getNumOfProvinces() < 2) {
            return;
        }
        try {
            final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "predefined_borders/" + this.getCiv(nCivID).getCivTag());
            final PreDefined_Borders_GameData tempData = (PreDefined_Borders_GameData) CFG.deserialize(file.readBytes());
            for (int i = 0; i < tempData.getDataSize(); ++i) {
                if (tempData.getData(i).getProvincesSize() == this.getCiv(nCivID).getNumOfProvinces()) {
                    boolean tReturn = true;
                    for (int k = 0; k < this.getCiv(nCivID).getNumOfUnits(); ++k) {
                        if (!tempData.getData(i).hasProvinceID(this.getCiv(nCivID).getProvinceID(k))) {
                            tReturn = false;
                            break;
                        }
                    }
                    if (tReturn) {
                        return;
                    }
                }
            }
            tempData.addData(this.build_PreDefinedCivsBorders_CivData(nCivID));
            final OutputStream osProvince = null;
            try {
                final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "predefined_borders/" + this.getCiv(nCivID).getCivTag());
                fileProvince.writeBytes(CFG.serialize(tempData), false);
            } catch (final IOException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
                if (osProvince != null) {
                    try {
                        osProvince.close();
                    } catch (final Exception ex2) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex2);
                        }
                    }
                }
            } finally {
                if (osProvince != null) {
                    try {
                        osProvince.close();
                    } catch (final Exception ex3) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex3);
                        }
                    }
                }
            }
            return;
        } catch (final ClassNotFoundException | GdxRuntimeException | IOException e) {
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        }
        final PreDefined_Borders_GameData tempData2 = new PreDefined_Borders_GameData();
        tempData2.addData(this.build_PreDefinedCivsBorders_CivData(nCivID));
        final OutputStream osProvince2 = null;
        try {
            final FileHandle fileProvince2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "predefined_borders/" + this.getCiv(nCivID).getCivTag());
            fileProvince2.writeBytes(CFG.serialize(tempData2), false);
        } catch (final IOException ex5) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex5);
            }
            if (osProvince2 != null) {
                try {
                    osProvince2.close();
                } catch (final Exception ex6) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex6);
                    }
                }
            }
        } finally {
            if (osProvince2 != null) {
                try {
                    osProvince2.close();
                } catch (final Exception ex7) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex7);
                    }
                }
            }
        }
    }

    private final PreDefined_Borders_Data_GameData build_PreDefinedCivsBorders_CivData(final int nCivID) {
        final PreDefined_Borders_Data_GameData tempBorders = new PreDefined_Borders_Data_GameData();
        tempBorders.setCapitalProvinceID(this.getCiv(nCivID).getCapitalProvinceID());
        for (int i = 0; i < this.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tempBorders.addProvinceID(this.getCiv(nCivID).getProvinceID(i));
        }
        return tempBorders;
    }

    protected final void deleteSuggestedOwners() {
        final List<String> tempL = CFG.getFileNames("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/");
        for (String s : tempL) {
            Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + s).delete();
        }
    }

    protected final void build_SuggestedOwners() {
        for (int u = 0; u < Game_Scenarios.SCENARIOS_SIZE; ++u) {
            this.build_SuggestedOwners(u);
        }
        CFG.toast.setInView(CFG.langManager.get("Done") + " [" + Game_Scenarios.SCENARIOS_SIZE + "/" + Game_Scenarios.SCENARIOS_SIZE + "]");
    }

    protected final void build_SuggestedOwners(final int nScenarioID) {
        CFG.game.setScenarioID(nScenarioID);
        CFG.game.loadScenario(true);
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && !this.getCiv(i).getCivTag().equals("ran") && CFG.ideologiesManager.REBELS_ID != CFG.ideologiesManager.getIdeologyID(this.getCiv(i).getCivTag())) {
                try {
                    final FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.getCiv(i).getCapitalProvinceID());
                    final String sOwners = file.readString();
                    final String[] sRes = sOwners.split(";");
                    boolean bAdd = true;
                    boolean updateDate = false;
                    int j = 0;
                    while (j < sRes.length) {
                        if (sRes[j].equals(this.getCiv(i).getCivTag())) {
                            bAdd = false;
                            if (Game_Calendar.currentYear < Integer.parseInt(sRes[j + 1])) {
                                updateDate = true;
                                sRes[j + 1] = "" + Game_Calendar.currentYear;
                                break;
                            }
                            break;
                        } else {
                            j += 2;
                        }
                    }
                    if (bAdd) {
                        final FileHandle fileSaveSuggestedOwners = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.getCiv(i).getCapitalProvinceID());
                        fileSaveSuggestedOwners.writeString(sRes[0] + ";", false);
                        for (int k = 1; k < sRes.length; ++k) {
                            fileSaveSuggestedOwners.writeString(sRes[k] + ";", true);
                        }
                        fileSaveSuggestedOwners.writeString(this.getCiv(i).getCivTag() + ";" + Game_Calendar.currentYear + ";", true);
                    } else if (updateDate) {
                        final FileHandle fileSaveSuggestedOwners = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.getCiv(i).getCapitalProvinceID());
                        fileSaveSuggestedOwners.writeString(sRes[0] + ";", false);
                        for (int k = 1; k < sRes.length; ++k) {
                            fileSaveSuggestedOwners.writeString(sRes[k] + ";", true);
                        }
                    }
                } catch (final GdxRuntimeException ex) {
                    final FileHandle fileSaveSuggestedOwners2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.getCiv(i).getCapitalProvinceID());
                    fileSaveSuggestedOwners2.writeString(this.getCiv(i).getCivTag() + ";" + Game_Calendar.currentYear + ";", false);
                }
            }
        }
        CFG.toast.setInView(CFG.langManager.get("Done") + " #" + CFG.langManager.get(CFG.game.getGameScenarios().getScenarioName(nScenarioID)), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
    }

    protected final void loadScenario_RandomGame() {
        this.disposeCivilizations_WithoutWasteland();
        this.lCivs = this.gameScenarios.loadCivilizations_RandomGame();
        this.iCivsSize = this.lCivs.size();
        this.iAvailableCivilizations = this.iCivsSize - 1;
        CFG.holyRomanEmpire_Manager.initHolyRomanEmpire();
        final Random oR = new Random();
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() < 0) {
                CFG.gameNewGame.findRandomCapital(i, oR);
            }
        }
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince()) {
                for (int j = 0; j < this.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
                    ((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).setIsCivilizationBorder(this.getProvince(i).getCivID() != this.getProvince(((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).getWithProvinceID()).getCivID(), i);
                }
                if (this.getProvince(i).getCivID() > 0) {
                    this.getCiv(this.getProvince(i).getCivID()).addProvince_Just(i);
                }
            }
        }
        this.initPlayers();
        this.getPlayer(0).setCivID(1);
        for (int i = 1; i < CFG.randomGameManager.getPlayersSize(); ++i) {
            this.addPlayer(i + 1);
        }
        CFG.province_Cores_GameData = new Province_Cores_GameData();
        this.getGameScenarios().buildProvincePopulationAndEconomy(false, false);
        this.getGameScenarios().buildDiplomacy();
        this.build_Leaders(false);
        this.buildCivilizationsRegions();
        this.buildCivilizationsRegions_TextOver();
        CFG.gameAction.buildRank_Score();
        CFG.menuManager.updateCreateNewGame_Top();
        CFG.disposeActiveCivFlag();
    }

    protected final void loadSavedGame_NEW_1(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_1 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_1 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_1");
            } else {
                fileReadData_1 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_1");
            }
            Save_GameData_1 tempSavedGame = (Save_GameData_1) CFG.deserialize(fileReadData_1.readBytes());
            this.disposeCivilizations();
            CFG.FOG_OF_WAR = tempSavedGame.FOG_OF_WAR;
            CFG.SPECTATOR_MODE = tempSavedGame.SPECTATOR_MODE;
            CFG.DIFFICULTY = tempSavedGame.DIFFICULTY;
            CFG.SANDBOX_MODE = tempSavedGame.SANDBOX_MODE;

            //load capit setting
            try {
                CFG.CAPITULATION = tempSavedGame.CAPITULATION;
            } catch (NullPointerException ex) {
                Gdx.app.log("LOADSAVE", "No capitulation setting");
                CFG.CAPITULATION = 1;
            }

            Game_Calendar.TURN_ID = tempSavedGame.iTurnID;
            Game_Calendar.TURNS_SINCE_LAST_WAR = tempSavedGame.TURNS_SINCE_LAST_WAR;
            Game_Calendar.currentDay = tempSavedGame.iDay;
            Game_Calendar.currentMonth = tempSavedGame.iMonth;
            Game_Calendar.currentYear = tempSavedGame.iYear;
            Game_Calendar.CURRENT_AGEID = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
            Game_Calendar.ENABLE_COLONIZATION = tempSavedGame.ENABLE_COLONIZATION;
            Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = tempSavedGame.ENABLE_COLONIZATION_NEUTRAL_PROVINCES;
            Game_Calendar.COLONIZATION_TECH_LEVEL = tempSavedGame.COLONIZATION_TECH_LEVEL;
            Game_Calendar.GAME_SPEED = tempSavedGame.GAME_SPEED;
            CFG.game.getGameScenarios().setScenario_StartingPopulation(tempSavedGame.STARTING_POPULATION);
            CFG.game.getGameScenarios().setScenario_StartingEconomy(tempSavedGame.STARTING_ECONOMY);
            CFG.game.getGameScenarios().setScenario_PopulationGrowthRate_Modifier(tempSavedGame.POPULATION_GROWTH_RATE_MODIFIER);
            CFG.game.getGameScenarios().setScenario_EconomyGrowthRate_Modifier(tempSavedGame.ECONOMY_GROWTH_RATE_MODIFIER);
            CFG.game.getGameScenarios().setScenario_DiseasesDeathRate_Modifier(tempSavedGame.DISEASES_DEATH_REATE_MODIFIER);
            VicotryManager.VICTORY_CONTROL_PROVINCES_PERC = tempSavedGame.VICTORY_CONTROL_PROVINCES_PERC;
            VicotryManager.VICTORY_LIMIT_OF_TURNS = tempSavedGame.VICTORY_LIMIT_OF_TURNS;
            VicotryManager.VICTORY_TECHNOLOGY = tempSavedGame.VICTORY_TECHNOLOGY;
            CFG.game.getGameScenarios().sActiveScenarioTag = tempSavedGame.sActiveScenarioTag;
            tempSavedGame = null;
            this.lCivs.clear();
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_2(final int iLoadID, final String[] tSplted) {
        try {
            while (Menu_LoadSave.tFileID < 1000) {
                try {
                    FileHandle fileReadData_2 = null;
                    if (CFG.readLocalFiles()) {
                        fileReadData_2 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_2X" + ((Menu_LoadSave.tFileID == 0) ? "" : Integer.valueOf(Menu_LoadSave.tFileID)));
                    } else {
                        fileReadData_2 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_2X" + ((Menu_LoadSave.tFileID == 0) ? "" : Integer.valueOf(Menu_LoadSave.tFileID)));
                    }
                    Save_GameData_2 tempSavedGame_2 = (Save_GameData_2) CFG.deserialize(fileReadData_2.readBytes());
                    final List<Civilization> tempCivsLoad = this.gameScenarios.loadCivilizationsLoadGame(tempSavedGame_2.lCivsData, this.lCivs.size());
                    this.lCivs.addAll(tempCivsLoad);
                    ++Menu_LoadSave.tFileID;
                    tempSavedGame_2 = null;
                    continue;
                } catch (final Exception ex) {
                }
                break;
            }
            this.iCivsSize = this.lCivs.size();

            //loop through each civ/vassal, re-init vassalization, safechecking autonomy states
            //default to 0 if null (in civ.class setpuppet function)
            for (int i = 0; i < this.iCivsSize; i++) {
                for (int j = 0; j < this.getCiv(i).civGameData.iVassalsSize; j++) {
                    if (this.getCiv(i).civGameData.lVassals.get(j).autonomyStatus == null) {
                        int iVassal = this.getCiv(i).civGameData.lVassals.get(j).iCivID;
                        this.getCiv(i).removeVassal(iVassal);
                        //default
                        this.getCiv(iVassal).setPuppetOfCivID(i);
                    }
                }
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_3(final int iLoadID, final String[] tSplted) {
        try {
            for (int i = 0; i < this.getCivsSize(); ++i) {
                this.getCiv(i).buildRegroupLines_AfterLoading();
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_4(final int iLoadID, final String[] tSplted) {
        try {
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                CFG.game.getCiv(i).buildDiplomacy(false);
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_5(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_3 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_3 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_3");
            } else {
                fileReadData_3 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_3");
            }
            Save_GameData_3 tempSavedGame_3 = (Save_GameData_3) CFG.deserialize(fileReadData_3.readBytes());
            for (int i = 0; i < tempSavedGame_3.lCivsDiploData.size(); ++i) {
                for (int j = 0; j < tempSavedGame_3.lCivsDiploData.get(i).lNonAggressionPacts.size(); ++j) {
                    CFG.game.getCiv(i + 1).setNonAggressionPact(tempSavedGame_3.lCivsDiploData.get(i).lNonAggressionPacts.get(j).id, tempSavedGame_3.lCivsDiploData.get(i).lNonAggressionPacts.get(j).iValue);
                }
                for (int j = 0; j < tempSavedGame_3.lCivsDiploData.get(i).lDefensivePact.size(); ++j) {
                    CFG.game.getCiv(i + 1).setDefensivePact(tempSavedGame_3.lCivsDiploData.get(i).lDefensivePact.get(j).id, tempSavedGame_3.lCivsDiploData.get(i).lDefensivePact.get(j).iValue);
                }
                for (int j = 0; j < tempSavedGame_3.lCivsDiploData.get(i).lGuarantee.size(); ++j) {
                    CFG.game.getCiv(i + 1).setGuarantee(tempSavedGame_3.lCivsDiploData.get(i).lGuarantee.get(j).id, tempSavedGame_3.lCivsDiploData.get(i).lGuarantee.get(j).iValue);
                }
                for (int j = 0; j < tempSavedGame_3.lCivsDiploData.get(i).lMilitirayAccess.size(); ++j) {
                    CFG.game.getCiv(i + 1).setMilitaryAccess(tempSavedGame_3.lCivsDiploData.get(i).lMilitirayAccess.get(j).id, tempSavedGame_3.lCivsDiploData.get(i).lMilitirayAccess.get(j).iValue);
                }
                for (int j = 0; j < tempSavedGame_3.lCivsDiploData.get(i).lTruce.size(); ++j) {
                    CFG.game.getCiv(i + 1).setTruce(tempSavedGame_3.lCivsDiploData.get(i).lTruce.get(j).id, tempSavedGame_3.lCivsDiploData.get(i).lTruce.get(j).iValue);
                }
            }
            tempSavedGame_3 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_6(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_4 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_4 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_4");
            } else {
                fileReadData_4 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_4");
            }
            Save_GameData_4 tempSavedGame_4 = (Save_GameData_4) CFG.deserialize(fileReadData_4.readBytes());
            for (int i = 0; i < tempSavedGame_4.lProvincesData.size(); ++i) {
                CFG.game.getProvince(i).saveProvinceData = tempSavedGame_4.lProvincesData.get(i);
            }
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                if (CFG.game.getProvince(i).getCivID() > 0) {
                    if (CFG.game.getProvince(i).getSeaProvince()) {
                        CFG.game.getProvince(i).setCivID(0, false);
                    } else {
                        CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).addProvince_Just(i);
                    }
                }
            }
            tempSavedGame_4 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_7(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_11 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_11 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_11");
            } else {
                fileReadData_11 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_11");
            }
            final Save_GameData_11 tempSavedGame_11 = (Save_GameData_11) CFG.deserialize(fileReadData_11.readBytes());
            CFG.eventsManager.eventsGD = tempSavedGame_11.eventsGameData;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_8(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_10 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_10 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_10");
            } else {
                fileReadData_10 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_10");
            }
            Save_GameData_10 tempSavedGame_10 = (Save_GameData_10) CFG.deserialize(fileReadData_10.readBytes());
            CFG.holyRomanEmpire_Manager.holyRomanEmpire = tempSavedGame_10.holyRomanEmpire_GameData;
            tempSavedGame_10 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_9(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_6 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_6 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_6");
            } else {
                fileReadData_6 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_6");
            }
            Save_GameData_6 tempSavedGame_6 = (Save_GameData_6) CFG.deserialize(fileReadData_6.readBytes());
            CFG.game.buildAlliances(tempSavedGame_6.lAlliances);
            tempSavedGame_6 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_10(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_5 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_5 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_5");
            } else {
                fileReadData_5 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_5");
            }
            Save_GameData_5 tempSavedGame_5 = (Save_GameData_5) CFG.deserialize(fileReadData_5.readBytes());
            CFG.game.initPlayers(tempSavedGame_5.lPlayers);
            tempSavedGame_5 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_11(final int iLoadID, final String[] tSplted) {
        try {
            CFG.game.buildCivilizationsRegions();
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_12(final int iLoadID, final String[] tSplted) {
        try {
            CFG.game.buildWastelandLevels();
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                CFG.game.getProvince(i).updateProvinceBorder();
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_13(final int iLoadID, final String[] tSplted) {
        try {
            for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                CFG.game.getPlayer(i).loadPlayersFlag();
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_14(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_7 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_7 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_7");
            } else {
                fileReadData_7 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_7");
            }
            final Save_GameData_7 tempSavedGame_7 = (Save_GameData_7) CFG.deserialize(fileReadData_7.readBytes());
            this.lWars = new ArrayList<War_GameData>();
            this.lWars.addAll(tempSavedGame_7.lWars);
            this.iWarsSize = this.lWars.size();
            fileReadData_7 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_15(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_8 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_8 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_8");
            } else {
                fileReadData_8 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_8");
            }
            Save_GameData_8 tempSavedGame_8 = (Save_GameData_8) CFG.deserialize(fileReadData_8.readBytes());
            this.lPeaceTreaties = new ArrayList<PeaceTreaty_GameData_MessageData>();
            this.lPeaceTreaties.addAll(tempSavedGame_8.lPeaceTreaties);
            tempSavedGame_8 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_16(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData_9 = null;
            if (CFG.readLocalFiles()) {
                fileReadData_9 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_9");
            } else {
                fileReadData_9 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_9");
            }
            Save_GameData_9 tempSavedGame_9 = (Save_GameData_9) CFG.deserialize(fileReadData_9.readBytes());
            CFG.plagueManager.lPlagues_INGAME.clear();
            CFG.plagueManager.lPlagues_INGAME = new ArrayList<Plague_GameData>();
            CFG.plagueManager.lPlagues_INGAME.addAll(tempSavedGame_9.lPlagues_INGAME);
            tempSavedGame_9 = null;
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_17(final int iLoadID, final String[] tSplted) {
        try {
            CFG.game_NextTurnUpdate.buildLevelsOfCities();
            CFG.gameAction.buildRank_Score();
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_18(final int iLoadID, final String[] tSplted) {
        try {
            SaveManager.saveTag = tSplted[iLoadID];
            FileHandle fileReadData4 = null;
            if (CFG.readLocalFiles()) {
                fileReadData4 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_O");
            } else {
                fileReadData4 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_O");
            }
            CFG.timelapseManager.timelapseOwnersGameData = (Timelapse_Owners_GameData) CFG.deserialize(fileReadData4.readBytes());
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_19(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData2 = null;
            if (CFG.readLocalFiles()) {
                fileReadData2 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_T");
            } else {
                fileReadData2 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_T");
            }
            CFG.timelapseManager.timelapseGameData = (Timelapse_GameData) CFG.deserialize(fileReadData2.readBytes());
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_20(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData3 = null;
            if (CFG.readLocalFiles()) {
                fileReadData3 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_S");
            } else {
                fileReadData3 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + "TS/" + tSplted[iLoadID] + "_S");
            }
            CFG.timelapseManager.timelapseStatsGD = (Timelapse_Stats_GameData) CFG.deserialize(fileReadData3.readBytes());
            CFG.timelapseManager.timelapseTurnChanges = new Timelapse_TurnChanges_GameData();
            CFG.timelapseManager.timelapseTurnChanges.lTurnChanges.add(new ArrayList<Timelapse_TurnChanges>());
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void loadSavedGame_NEW_21(final int iLoadID, final String[] tSplted) {
        try {
            FileHandle fileReadData8 = null;
            if (CFG.readLocalFiles()) {
                fileReadData8 = Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_2");
            } else {
                fileReadData8 = Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + tSplted[iLoadID] + "/" + tSplted[iLoadID] + "_2");
            }
            final Save_GameData2 tempDataSav = (Save_GameData2) CFG.deserialize(fileReadData8.readBytes());
            Game_Calendar.AI_AGGRESSIVNESS = tempDataSav.AI_AGGRESSIVNESS;

            try {
                //clear dyn
                CFG.dynamicEventManager.clearData();

                //load new dyn events
                if (tempDataSav.dynamicEventManager == null) {
                    Gdx.app.log("LOADSAVE", "nullDynEvent");
                    //if from imcompatible save, default value
                    CFG.DYNAMIC_EVENTS = !CFG.SPECTATOR_MODE;
                    CFG.dynamicEventManager = new DynamicEventManager();
                } else {
                    Gdx.app.log("LOADSAVE", "validDynEvent");
                    //else get saved values
                    CFG.DYNAMIC_EVENTS = tempDataSav.DYNAMIC_EVENTS;
                    CFG.dynamicEventManager = tempDataSav.dynamicEventManager;
                    //load new leader events
                    if (tempDataSav.dynamicEventManager.eventManagerLeader == null) {
                        CFG.dynamicEventManager.eventManagerLeader = new DynamicEventManager_Leader();
                    }
                    if (tempDataSav.dynamicEventManager.eventManagerClass == null) {
                        CFG.dynamicEventManager.eventManagerClass = new DynamicEventManager_Class();
                    }
                }
                CFG.PLAYER_PEACE = tempDataSav.PLAYER_PEACE;
                CFG.AI_VASSALS = tempDataSav.AI_VASSALS;
                CFG.AI_DIPLOMACY = tempDataSav.AI_DIPLOMACY;

                Gdx.app.log("LOADSAVE", String.valueOf(CFG.DYNAMIC_EVENTS));
            } catch (final Exception ex) {
                //if from imcompatible save, default value
                CFG.DYNAMIC_EVENTS = !CFG.SPECTATOR_MODE;
                CFG.PLAYER_PEACE = false;
                CFG.AI_VASSALS = false;
                CFG.AI_DIPLOMACY = true;
                CFG.dynamicEventManager = new DynamicEventManager();
                Gdx.app.log("LOADSAVE", "defaultDynEvent");
            }
            try {
                Gdx.app.log("LOADSAVE", "loadDecisions");
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    if (CFG.game.getPlayer(i).getCivID() > 0) {
                        try {
                            for (int decision = 0; decision < CFG.gameDecisions.lDecisions.size(); decision++) {
                                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decision);
                            }
                        } catch (Exception ex) {
                            Gdx.app.log("LOADSAVE", "loadDecisions failed, updating");
                            CFG.gameAction.updatePlayerDecisions();
                            break;
                        }

                        try {
                            for (int decision = 0; decision < CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.getDecisionsCount(); decision++) {
                                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.getDecision(decision);
                            }
                        } catch (Exception ex) {
                            Gdx.app.log("LOADSAVE", "loadCivDecisions failed, updating");
                            CFG.gameAction.updatePlayerDecisions();
                            break;
                        }
                    }
                }
                Gdx.app.log("LOADSAVE", "loadClassViews");
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    if (CFG.game.getPlayer(i).getCivID() > 0) {
                        try {
                            if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(0) == -1.0F ||
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(1) == -1.0F ||
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(2) == -1.0F) {
                                Gdx.app.log("LOADSAVE", "loadClassViews default, updating");
                                CFG.gameAction.updateClassPerceptions();
                                break;
                            }
                        } catch (NullPointerException | GdxRuntimeException ex) {
                            Gdx.app.log("LOADSAVE", "loadClassViews failed, updating");
                            CFG.gameAction.updateClassPerceptions();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                CFG.exceptionStack(e);
                Gdx.app.log("LOADSAVE", "loadDecisions/Views failed, updating");
                CFG.gameAction.updatePlayerDecisions();
                CFG.gameAction.updateClassPerceptions();
            }
        } catch (final Exception ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void build_Leaders(final boolean nEditor) {
        final List<String> tempTags = new ArrayList<String>();
        try {
            if (CFG.readLocalFiles()) {
                try {
                    String[] tagsSPLITED = null;
                    if (CFG.isDesktop()) {
                        final List<String> tempFiles = CFG.getFileNames("game/leaders/");
                        for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                            if (tempFiles.get(i).equals("Age_of_Civilizations")) {
                                tempFiles.remove(i);
                                break;
                            }
                        }
                        tagsSPLITED = new String[tempFiles.size()];
                        for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                            tagsSPLITED[i] = tempFiles.get(i);
                        }
                    } else {
                        final FileHandle tempFileT = Gdx.files.internal("game/leaders/Age_of_Civilizations");
                        final String tempT = tempFileT.readString();
                        tagsSPLITED = tempT.split(";");
                    }
                    for (String s : tagsSPLITED) {
                        tempTags.add(s);
                    }
                } catch (final GdxRuntimeException ex2) {
                }
                try {
                    final FileHandle tempFileT2 = Gdx.files.local("game/leaders/Age_of_Civilizations");
                    final String tempT2 = tempFileT2.readString();
                    final String[] tagsSPLITED2 = tempT2.split(";");
                    for (String s : tagsSPLITED2) {
                        boolean add = true;
                        for (String tempTag : tempTags) {
                            if (s.equals(tempTag)) {
                                add = false;
                                break;
                            }
                        }
                        if (add) {
                            tempTags.add(s);
                        }
                    }
                } catch (final GdxRuntimeException ex3) {
                }
            } else {
                String[] tagsSPLITED = null;
                if (CFG.isDesktop()) {
                    final List<String> tempFiles = CFG.getFileNames("game/leaders/");
                    for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                        if (tempFiles.get(i).equals("Age_of_Civilizations")) {
                            tempFiles.remove(i);
                            break;
                        }
                    }
                    tagsSPLITED = new String[tempFiles.size()];
                    for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                        tagsSPLITED[i] = tempFiles.get(i);
                    }
                } else {
                    final FileHandle tempFileT = Gdx.files.internal("game/leaders/Age_of_Civilizations");
                    final String tempT = tempFileT.readString();
                    tagsSPLITED = tempT.split(";");
                }
                for (String s : tagsSPLITED) {
                    tempTags.add(s);
                }
            }
        } catch (final GdxRuntimeException ex4) {
        }
        List<String> tRealTags = new ArrayList<String>();
        List<Integer> tRandLeaders = new ArrayList<Integer>();
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            tRealTags.add(CFG.ideologiesManager.getRealTag(CFG.game.getCiv(i).getCivTag()));
            tRandLeaders.add(1);
        }
        for (String tempTag : tempTags) {
            try {
                try {
                    final FileHandle file = Gdx.files.local("game/leaders/" + tempTag);
                    CFG.leader_GameData = (Leader_GameData) CFG.deserialize(file.readBytes());
                } catch (final GdxRuntimeException ex) {
                    final FileHandle file = Gdx.files.internal("game/leaders/" + tempTag);
                    CFG.leader_GameData = (Leader_GameData) CFG.deserialize(file.readBytes());
                }
                if (DynamicEventManager_Leader.isValidLeader()) {
                    for (int m = CFG.leader_GameData.getCivsSize() - 1; m >= 0; --m) {
                        for (int k2 = 1; k2 < CFG.game.getCivsSize(); ++k2) {
                            if (CFG.game.getCiv(k2).getCivTag().equals(CFG.leader_GameData.getCiv(m)) || tRealTags.get(k2 - 1).equals(CFG.leader_GameData.getCiv(m))) {
                                if (CFG.game.getCiv(k2).civGameData.leaderData == null) {
                                    CFG.game.getCiv(k2).civGameData.setLeader(CFG.leader_GameData.getLeaderOfCiv());
                                    m = -1;
                                    break;
                                }
                                //if (!CFG.settingsManager.randomLeaders) {
                                //instead of arbitrary dates, use year of "in office", and prioritize leaders with more recent date
                                if (CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() && CFG.game.getCiv(k2).civGameData.leaderData.isIncumbentYear()) {
                                    if (Math.abs(CFG.leader_GameData.getLeaderOfCiv().getYear() - Game_Calendar.currentYear) > Math.abs(CFG.game.getCiv(k2).civGameData.leaderData.getYear() - Game_Calendar.currentYear)) {
                                        CFG.game.getCiv(k2).civGameData.setLeader(CFG.leader_GameData.getLeaderOfCiv());
                                        m = -1;
                                        break;
                                    }
                                } else if (!CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() && !CFG.game.getCiv(k2).civGameData.leaderData.isIncumbentYear()) {
                                    if (Math.abs(CFG.leader_GameData.getLeaderOfCiv().getYear() + 30 - Game_Calendar.currentYear) < Math.abs(CFG.game.getCiv(k2).civGameData.leaderData.getYear() + 40 - Game_Calendar.currentYear)) {
                                        CFG.game.getCiv(k2).civGameData.setLeader(CFG.leader_GameData.getLeaderOfCiv());
                                        m = -1;
                                        break;
                                    }
                                } else if (CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() && !CFG.game.getCiv(k2).civGameData.leaderData.isIncumbentYear()) {
                                    CFG.game.getCiv(k2).civGameData.setLeader(CFG.leader_GameData.getLeaderOfCiv());
                                    m = -1;
                                    break;
                                } else if (!CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() && CFG.game.getCiv(k2).civGameData.leaderData.isIncumbentYear()) {
                                    break;
                                }
                                //}
                                //no more random choosing of leaders
                                /* else {
                                    //if (CFG.leader_GameData.getLeaderOfCiv().getYear() > 1699) {
                                    //    break;
                                    //}
                                    tRandLeaders.set(k2 - 1, tRandLeaders.get(k2 - 1) + 1);
                                    if (CFG.oR.nextInt(100) < 100.0f / tRandLeaders.get(k2 - 1) || (Game_Calendar.currentYear < 1700 && CFG.game.getCiv(k2).civGameData.leaderData.getYear() > 1699)) {
                                        CFG.game.getCiv(k2).civGameData.setLeader(CFG.leader_GameData.getLeaderOfCiv());
                                        m = -1;
                                        break;
                                    }
                                }*/
                            }
                        }
                    }
                }
            } catch (final ClassNotFoundException | GdxRuntimeException | IOException ex5) {
            }
            CFG.leader_GameData = null;
        }

        //build random leaders for civs without leaders still
        for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
            try {
                if (CFG.game.getCiv(k).civGameData.leaderData == null) {
                    Gdx.app.log("BUILD_LEADERS", "Building random leader for civ: " + CFG.game.getCiv(k).getCivName());
                    if (!DynamicEventManager_Leader.buildRandomLeader(k, true)) {
                        Gdx.app.log("BUILD_LEADERS", "No random leader for " + CFG.game.getCiv(k).getCivName() + ", placeholder built");
                        DynamicEventManager_Leader.buildPlaceholderLeader(k);
                    }
                }
            } catch (final NullPointerException ex5) {
                CFG.exceptionStack(ex5);
            }
        }

        tRealTags.clear();
        tRealTags = null;
        tRandLeaders.clear();
        tRandLeaders = null;
    }

    protected final void loadScenario(boolean nEditor) {
        this.disposeCivilizations();
        this.lCivs = this.gameScenarios.loadCivilizations(nEditor);
        this.iCivsSize = this.lCivs.size();
        this.iAvailableCivilizations = this.iCivsSize - 1;
        Game_Calendar.TURN_ID = 1;
        CFG.PLAYER_TURNID = 0;
        Game_Calendar.currentDay = this.getGameScenarios().getScenarioDay(this.getScenarioID());
        Game_Calendar.currentMonth = this.getGameScenarios().getScenarioMonth(this.getScenarioID());
        Game_Calendar.currentYear = this.getGameScenarios().getScenarioYear(this.getScenarioID());
        Game_Calendar.CURRENT_AGEID = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
        VicotryManager.VICTORY_CONTROL_PROVINCES_PERC = 100;
        VicotryManager.VICTORY_LIMIT_OF_TURNS = 0;
        VicotryManager.VICTORY_TECHNOLOGY = VicotryManager.getDefault_VcitoryTechnology();

        int i;
        int numOfRandomCivs;
        for (i = 0; i < this.getProvincesSize(); ++i) {
            for (numOfRandomCivs = 0; numOfRandomCivs < this.getProvince(i).getProvinceBordersLandByLandSize(); ++numOfRandomCivs) {
                ((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(numOfRandomCivs)).setIsCivilizationBorder(this.getProvince(i).getCivID() != this.getProvince(((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(numOfRandomCivs)).getWithProvinceID()).getCivID(), i);
            }

            if (this.getProvince(i).getCivID() > 0) {
                this.getCiv(this.getProvince(i).getCivID()).addProvince_Just(i);
            }
        }

        if (nEditor) {
            i = 1;

            for (numOfRandomCivs = 1; i < this.getCivsSize(); ++i) {
                if (this.getCiv(i).getCivTag().equals("ran")) {
                    this.randomCivilizationColor(i);
                    this.getCiv(i).setCivName(this.getCiv(i).getCivName() + " " + numOfRandomCivs++);
                }
            }
        }

        this.getGameScenarios().loadProvincesData(nEditor);
        this.getGameScenarios().loadEventsData();
        this.buildWastelandLevels();
        this.getGameScenarios().loadDiplomacyData(nEditor);
        CFG.holyRomanEmpire_Manager.loadHolyRomanEmpire_ScenarioData();
        this.build_Leaders(nEditor);
        this.buildCivilizationsRegions();
        this.buildCivilizationsRegions_TextOver();
        if (this.getGameScenarios().getScenario_ActivePallet_TAG() != null) {
            for (i = 0; i < CFG.palletManager.getNumOfPallets(); ++i) {
                if (CFG.palletManager.getPalletTag(i).equals(this.getGameScenarios().getScenario_ActivePallet_TAG())) {
                    CFG.palletManager.setActivePalletID(i + 1);
                    CFG.palletManager.loadCivilizationsPaletteOfColors(CFG.palletManager.getActivePalletID());
                    break;
                }
            }
        }

        CFG.game_NextTurnUpdate.buildLevelsOfCities();
        CFG.gameAction.buildRank_Score();
        CFG.menuManager.updateCreateNewGame_Top();
        CFG.disposeActiveCivFlag();
    }

    private final void disposeCivilizations() {
        if (this.lCivs != null) {
            int i;
            try {
                for (i = 0; i < this.iCivsSize; ++i) {
                    try {
                        this.getCiv(i).getFlag().getTexture().dispose();
                    } catch (GdxRuntimeException | NullPointerException var3) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(var3);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException var5) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(var5);
                }
            }

            for (i = 0; i < this.getProvincesSize(); ++i) {
                this.getProvince(i).setCivID_LoadScenario(0);
                this.getProvince(i).setWasteland(-1);
                this.getProvince(i).setIsCapital(false);
                this.getProvince(i).setRevolutionaryRisk(0.0F);
                this.getProvince(i).setLevelOfFarm(0);
                this.getProvince(i).setLevelOfFort(0);
                this.getProvince(i).setLevelOfWatchTower(0);
                this.getProvince(i).setLevelOfSupply(0);
                this.getProvince(i).setLevelOfWorkshop(0);
                this.getProvince(i).setLevelOfLibrary(0);
                this.getProvince(i).setLevelOfArmoury(0);
                if (this.getProvince(i).getLevelOfPort() > 0) {
                    this.getProvince(i).setLevelOfPort(0);
                }
            }

            this.lAlliances.clear();
            this.iAlliancesSize = 0;
            this.lWars.clear();
            this.iWarsSize = 0;
            this.lPeaceTreaties.clear();
            CFG.plagueManager.lPlagues_INGAME.clear();
            this.lActive_CivRegion.clear();
            this.setActiveProvinceID(-1);
        }

    }

    private final void disposeCivilizations_WithoutWasteland() {
        if (this.lCivs != null) {
            for (int i = 0; i < this.iCivsSize; ++i) {
                this.getCiv(i).getFlag().getTexture().dispose();
            }
            for (int i = 0; i < this.getProvincesSize(); ++i) {
                this.getProvince(i).setCivID_LoadScenario(0);
                this.getProvince(i).resetArmies(0);
                this.getProvince(i).setIsCapital(false);
                this.getProvince(i).setLevelOfFarm(0);
                this.getProvince(i).setLevelOfFort(0);
                this.getProvince(i).setLevelOfWatchTower(0);
                this.getProvince(i).setLevelOfSupply(0);
                this.getProvince(i).setLevelOfWorkshop(0);
                this.getProvince(i).setLevelOfLibrary(0);
                this.getProvince(i).setLevelOfArmoury(0);
                if (this.getProvince(i).getLevelOfPort() > 0) {
                    this.getProvince(i).setLevelOfPort(0);
                }
            }
            this.lAlliances.clear();
            this.iAlliancesSize = 0;
            this.lWars.clear();
            this.iWarsSize = 0;
            this.lPeaceTreaties.clear();
            CFG.plagueManager.lPlagues_INGAME.clear();
            this.lActive_CivRegion.clear();
            this.setActiveProvinceID(-1);
        }
    }

    protected final void buildWastelandLevels() {
        final List<Integer> tWasteland = new ArrayList<Integer>();
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getWasteland() >= 0 && !this.getProvince(i).getSeaProvince()) {
                tWasteland.add(i);
                this.getProvince(i).setWasteland(0);
            }
        }
        this.buildWastelandLevels(tWasteland, 0, tWasteland.size());
    }

    private final void buildWastelandLevels(final List<Integer> tWasteland, final int nLevel, int nWastelandSize) {
        try {
            boolean rec = false;
            for (int i = 0; i < nWastelandSize; ++i) {
                if (this.getProvince(tWasteland.get(i)).getWasteland() != nLevel || this.getProvince(tWasteland.get(i)).getNeighboringSeaProvincesSize() > 0) {
                    tWasteland.remove(i);
                    --i;
                    nWastelandSize = tWasteland.size();
                } else {
                    boolean incLevel = true;
                    for (int j = 0; j < this.getProvince(tWasteland.get(i)).getNeighboringProvincesSize(); ++j) {
                        if (this.getProvince(this.getProvince(tWasteland.get(i)).getNeighboringProvinces(j)).getWasteland() < nLevel) {
                            incLevel = false;
                            break;
                        }
                    }
                    if (incLevel) {
                        this.getProvince(tWasteland.get(i)).setWasteland(nLevel + 1);
                        rec = true;
                    } else {
                        tWasteland.remove(i);
                        --i;
                        nWastelandSize = tWasteland.size();
                    }
                }
            }
            if (rec) {
                this.buildWastelandLevels(tWasteland, nLevel + 1, nWastelandSize);
            }
        } catch (final StackOverflowError ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void buildCivilizationsRegions() {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            this.getCiv(i).clearCivRegions();
        }
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getCivID() != 0 && !this.getCiv(this.getProvince(i).getCivID()).civRegionsContainsProvince(i)) {
                this.getCiv(this.getProvince(i).getCivID()).createCivilizationRegion(i);
            }
        }
        this.buildCivilizationsRegions_TextOver();
    }

    protected final void buildCivilizationRegions(final int nCivID) {
        this.getCiv(nCivID).clearCivRegions();
        for (int i = 0; i < this.getCiv(nCivID).getNumOfProvinces(); ++i) {
            if (CFG.game.getProvince(this.getCiv(nCivID).getProvinceID(i)).getCivRegionID() < 0) {
                this.getCiv(nCivID).createCivilizationRegion(this.getCiv(nCivID).getProvinceID(i));
            }
        }
        for (int j = 0; j < this.getCiv(nCivID).getCivRegionsSize(); ++j) {
            this.getCiv(nCivID).getCivRegion(j).buildRegionPath();
        }
    }

    protected final void buildCivilizationsRegions_TextOver() {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            this.buildCivilizationsRegions_TextOver(i);
        }
    }

    protected final void buildCivilizationsRegions_TextOver(final int i) {
        for (int j = 0; j < this.getCiv(i).getCivRegionsSize(); ++j) {
            this.getCiv(i).getCivRegion(j).buildRegionPath();
        }
    }

    protected final void disableNonPlayableCivilizations() {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (!this.getCiv(i).getIsAvailable()) {
                this.getProvince(this.getCiv(i).getCapitalProvinceID()).setIsCapital(false);
                this.getProvince(this.getCiv(i).getCapitalProvinceID()).updateDrawArmy();
                final int j = 0;
                while (j < this.getCiv(i).getNumOfProvinces()) {
                    for (int k = 1; k < this.getProvince(this.getCiv(i).getProvinceID(j)).getCivsSize(); ++k) {
                        this.getProvince(this.getCiv(i).getProvinceID(j)).removeArmy(k);
                    }
                    this.getProvince(this.getCiv(i).getProvinceID(j)).setCivID(0, false);
                }
            }
        }
    }

    protected final void updateNumOfAvailableCivilizations() {
        this.iAvailableCivilizations = 0;
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getIsAvailable()) {
                ++this.iAvailableCivilizations;
            }
        }
    }

    protected final void shuffleCivilizations() {
        CFG.game.disableDrawCivlizationsRegions_Players();
        final List<Boolean> lTaken = new ArrayList<Boolean>();
        final List<Integer> lTakeProvinceOfCivID = new ArrayList<Integer>();
        for (int i = 1; i < this.getCivsSize(); ++i) {
            lTaken.add(false);
        }
        final Random oR = new Random();
        while (lTakeProvinceOfCivID.size() != this.getCivsSize() - 1) {
            int nID;
            do {
                nID = oR.nextInt(this.getCivsSize() - 1) + 1;
            } while (lTaken.get(nID - 1));
            lTaken.set(nID - 1, true);
            lTakeProvinceOfCivID.add(nID);
        }
        Gdx.app.log("AoC", "lTakeProvinceOfCivID().size(): " + lTakeProvinceOfCivID.size());
        Gdx.app.log("AoC", "getCivsSize(): " + this.getCivsSize());
        List<List<Integer>> tempOwners = new ArrayList<List<Integer>>();
        final List<Integer> tempCapitals = new ArrayList<Integer>();
        for (int j = 1; j < this.getCivsSize(); ++j) {
            tempOwners.add(new ArrayList<Integer>());
            for (int k = 0; k < this.getCiv(j).getNumOfProvinces(); ++k) {
                tempOwners.get(j - 1).add(this.getCiv(j).getProvinceID(k));
            }
            tempCapitals.add(this.getCiv(j).getCapitalProvinceID());
        }
        for (int j = 1; j < this.getCivsSize(); ++j) {
            for (int k = 0; k < tempOwners.get(lTakeProvinceOfCivID.get(j - 1) - 1).size(); ++k) {
                this.getProvince(tempOwners.get(lTakeProvinceOfCivID.get(j - 1) - 1).get(k)).setCivID(j, false);
            }
        }
        for (int j = 1; j < this.getCivsSize(); ++j) {
            this.getCiv(j).setCapitalProvinceID(tempCapitals.get(lTakeProvinceOfCivID.get(j - 1) - 1));
        }
        tempOwners.clear();
        tempOwners = null;
        int j = 1;
        while (j < this.getPlayersSize()) {
            this.removePlayer(j);
        }
        if (this.getPlayer(0).getCivID() < 0) {
            this.setActiveProvinceID(this.getActiveProvinceID());
        } else {
            this.setActiveProvinceID(this.getCiv(this.getPlayer(0).getCivID()).getCapitalProvinceID());
        }
        CFG.game.buildCivilizationsRegions();
        CFG.game.enableDrawCivlizationsRegions_Players();
        CFG.map.getMapCoordinates().centerToProvinceID(this.getActiveProvinceID());
        CFG.setActiveCivInfo(this.getProvince(this.getActiveProvinceID()).getCivID());
        CFG.updateActiveCivInfo_CreateNewGame();
    }

    protected final void randomCivilizationColor(final int nCivID) {
        final Random oR = new Random();
        CFG.game.getCiv(nCivID).setR(oR.nextInt(256));
        CFG.game.getCiv(nCivID).setG(oR.nextInt(256));
        CFG.game.getCiv(nCivID).setB(oR.nextInt(256));
    }

    protected final void disableDrawCivlizationsRegions_Players() {
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            CFG.game.disableDrawCivilizationRegions(CFG.game.getPlayer(i).getCivID());
        }
        CFG.game.disableDrawCivilizationRegions(CFG.getActiveCivInfo());
    }

    protected final void enableDrawCivlizationsRegions_Players() {
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            CFG.game.enableDrawCivilizationRegions(CFG.game.getPlayer(i).getCivID(), 0);
        }
    }

    protected final void enableDrawCivlizationsRegions_Player(final int nID) {
        CFG.game.enableDrawCivilizationRegions(CFG.game.getPlayer(nID).getCivID(), 0);
    }

    protected final void enableDrawCivilizationRegions_ActiveProvince() {
        if (this.getActiveProvinceID() >= 0) {
            this.enableDrawCivilizationRegions(this.getProvince(this.getActiveProvinceID()).getCivID(), 0);
        }
    }

    protected final void enableDrawCivilizationRegions(final int nCivID, final int nRegionStyleID) {
        if (nCivID <= 0) {
            return;
        }
        this.addActiveCivRegion_CivID(nCivID, nRegionStyleID);
        for (int i = 0; i < this.getCiv(nCivID).getCivRegionsSize(); ++i) {
            for (int j = 0; j < this.getCiv(nCivID).getCivRegion(i).getProvincesSize(); ++j) {
                for (int k = 0; k < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvincesSize(); ++k) {
                    if (this.getCiv(nCivID).getCivRegion(i).getProvince(j) < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)) {
                        if (this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getProvinceBordersLandByLand(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getIsCivilizationBorder()) {
                            this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getCiv(nCivID).getCivRegion(i).getProvince(j), this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k));
                        }
                    } else if (this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getProvinceBordersLandByLand(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getIsCivilizationBorder()) {
                        this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k), this.getCiv(nCivID).getCivRegion(i).getProvince(j));
                    }
                }
            }
        }
    }

    protected final void enableDrawCivilizationRegions_FogOfWar(final int nCivID, final int nRegionStyleID) {
        if (nCivID <= 0) {
            return;
        }
        this.addActiveCivRegion_CivID(nCivID, nRegionStyleID);
        for (int i = 0; i < this.getCiv(nCivID).getCivRegionsSize(); ++i) {
            for (int j = 0; j < this.getCiv(nCivID).getCivRegion(i).getProvincesSize(); ++j) {
                for (int k = 0; k < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvincesSize(); ++k) {
                    if (CFG.getMetProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)) && CFG.getMetProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k))) {
                        if (this.getCiv(nCivID).getCivRegion(i).getProvince(j) < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)) {
                            if (this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getProvinceBordersLandByLand(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getIsCivilizationBorder()) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getCiv(nCivID).getCivRegion(i).getProvince(j), this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k));
                            }
                        } else if (this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getProvinceBordersLandByLand(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getIsCivilizationBorder()) {
                            this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k), this.getCiv(nCivID).getCivRegion(i).getProvince(j));
                        }
                    } else if ((CFG.getMetProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)) && this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getCivID() == nCivID) || (CFG.getMetProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)) && this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivID() == nCivID)) {
                        if (this.getCiv(nCivID).getCivRegion(i).getProvince(j) < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)) {
                            if (this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getProvinceBordersLandByLand(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getIsCivilizationBorder()) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getCiv(nCivID).getCivRegion(i).getProvince(j), this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k));
                            }
                        } else if (this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getProvinceBordersLandByLand(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getIsCivilizationBorder()) {
                            this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(nCivID)).getRegionStyleID(i)).updatePB(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k), this.getCiv(nCivID).getCivRegion(i).getProvince(j));
                        }
                    }
                }
            }
        }
    }

    protected final void disableDrawCivilizationRegions_ActiveProvince() {
        if (this.getActiveProvinceID() >= 0) {
            this.disableDrawCivilizationRegions(this.getProvince(this.getActiveProvinceID()).getCivID());
        }
    }

    protected final void disableDrawCivilizationRegions_Active() {
        try {
            while (this.lActive_CivRegion.size() > 0) {
                this.disableDrawCivilizationRegions(this.lActive_CivRegion.get(0).getCivID());
            }
        } catch (final StackOverflowError e) {
            this.lActive_CivRegion.clear();
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        }
    }

    protected final void disableDrawCivilizationRegions(final int nCivID) {
        if (nCivID <= 0) {
            return;
        }
        try {
            this.removeActiveCivRegion_CivID(nCivID);
            for (int i = 0; i < this.getCiv(nCivID).getCivRegionsSize(); ++i) {
                for (int j = 0; j < this.getCiv(nCivID).getCivRegion(i).getProvincesSize(); ++j) {
                    for (int k = 0; k < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvincesSize(); ++k) {
                        if (this.getCiv(nCivID).getCivRegion(i).getProvince(j) < this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)) {
                            if (this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getProvinceBordersLandByLand(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getIsCivilizationBorder()) {
                                if (!this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivID(), this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivRegionID())) {
                                    this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getProvinceBordersLandByLand(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).updateDrawProvinceBorder(this.getCiv(nCivID).getCivRegion(i).getProvince(j));
                                } else {
                                    this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivRegionID())).updatePB(this.getCiv(nCivID).getCivRegion(i).getProvince(j), this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k));
                                }
                            }
                        } else if (this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getProvinceBordersLandByLand(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getIsCivilizationBorder()) {
                            if (!this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivID(), this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivRegionID())) {
                                this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getProvinceBordersLandByLand(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).updateDrawProvinceBorder(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k));
                            } else {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k)).getCivRegionID())).updatePB(this.getProvince(this.getCiv(nCivID).getCivRegion(i).getProvince(j)).getNeighboringProvinces(k), this.getCiv(nCivID).getCivRegion(i).getProvince(j));
                            }
                        }
                    }
                }
            }
        } catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }

    private final void addActiveCivRegion_CivID(final int nCivID, final int nCivRegionStyle) {
        int i = this.lActive_CivRegion.size() - 1;
        while (i >= 0) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                if (this.lActive_CivRegion.get(i).getActiveRegionsSize() != this.getCiv(nCivID).getCivRegionsSize()) {
                    this.lActive_CivRegion.remove(i);
                    break;
                }
                return;
            } else {
                --i;
            }
        }
        for (i = 0; i < this.getCiv(nCivID).getCivRegionsSize(); ++i) {
            this.addActiveCivRegion_RegionID(nCivID, i, nCivRegionStyle);
        }
    }

    private final void addActiveCivRegion_RegionID(final int nCivID, final int nRegionID, final int nCivRegionStyle) {
        int i = this.lActive_CivRegion.size() - 1;
        while (i >= 0) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                if (this.lActive_CivRegion.get(i).isActive_RegionID(nRegionID)) {
                    return;
                }
                this.lActive_CivRegion.get(i).addRegion(nRegionID, nCivRegionStyle);
                return;
            } else {
                --i;
            }
        }
        this.lActive_CivRegion.add(new Civilization_Region_Active(nCivID, nRegionID, nCivRegionStyle));
    }

    private final void removeActiveCivRegion_CivID(final int nCivID) {
        for (int i = this.lActive_CivRegion.size() - 1; i >= 0; --i) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                this.lActive_CivRegion.remove(i);
                return;
            }
        }
    }

    private final void removeActiveCivRegion_RegionID(final int nCivID, final int nRegionID) {
        for (int i = this.lActive_CivRegion.size() - 1; i >= 0; --i) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                if (this.lActive_CivRegion.get(i).getActiveRegionsSize() == 1) {
                    this.lActive_CivRegion.remove(i);
                }
                this.lActive_CivRegion.get(i).removeRegion(nRegionID);
                return;
            }
        }
    }

    private final boolean isActiveCivRegion_CivID(final int nCivID, final int nRegionID) {
        for (int i = this.lActive_CivRegion.size() - 1; i >= 0; --i) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                return this.lActive_CivRegion.get(i).isActive_RegionID(nRegionID);
            }
        }
        return false;
    }

    protected final int getCivRegionID(final int nCivID) {
        for (int i = this.lActive_CivRegion.size() - 1; i >= 0; --i) {
            if (nCivID == this.lActive_CivRegion.get(i).getCivID()) {
                return i;
            }
        }
        return -1;
    }

    protected final void createScenarioClearCivilizations() {
        this.disposeCivilizations();
        this.lCivs.clear();
        this.lCivs.add(this.getNeutralCivilization());
        this.getCiv(0).setCivID(0);

        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).setCivID_LoadScenario(0);
            this.getProvince(i).setFromCivID(-1);
            this.getProvince(i).setIsCapital(false);
            this.getProvince(i).setWasteland(-1);

            int j;
            for (j = this.getProvince(i).getCivsSize() - 1; j > 0; --j) {
                this.getProvince(i).removeCiv(j);
            }

            CFG.holyRomanEmpire_Manager.initHolyRomanEmpire();
            this.getProvince(i).updateArmy(0);
            this.getProvince(i).setLevelOfFort(0);
            this.getProvince(i).setLevelOfWatchTower(0);
            this.getProvince(i).setLevelOfFarm(0);
            this.getProvince(i).setLevelOfWorkshop(0);
            this.getProvince(i).setLevelOfSupply(0);
            this.getProvince(i).setLevelOfLibrary(0);
            this.getProvince(i).setLevelOfArmoury(0);
            if (this.getProvince(i).getLevelOfPort() > 0) {
                this.getProvince(i).setLevelOfPort(0);
            }

            for (j = 0; j < this.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
                if (((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).getIsCivilizationBorder()) {
                    ((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).setIsCivilizationBorder(false, i);
                    ((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(j)).updateDrawProvinceBorder(i);
                }
            }
        }

        this.iCivsSize = this.lCivs.size();
    }

    protected final boolean isCivTagAvailable(final String nCivTag) {
        for (int i = 0; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCivTag().equals(nCivTag)) {
                return false;
            }
        }
        return true;
    }

    protected final void updateCivilizationIdeology(final int nCivID, final String nCivTag) {
        if (!nCivTag.equals("ran")) {
            for (int i = 0; i < this.getCivsSize(); ++i) {
                if (this.getCiv(i).getCivTag().equals(nCivTag)) {
                    return;
                }
            }
            try {
                Gdx.app.log("AoC", "updateCivilizationIdeology: " + nCivTag);
                FileHandle file = null;
                Civilization_GameData3 tempCiv = null;
                try {
                    file = Gdx.files.internal("game/civilizations/" + nCivTag);
                    tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                } catch (final GdxRuntimeException e) {
                    try {
                        file = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag(nCivTag));
                        tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                        final int tempIdeologyID = CFG.ideologiesManager.getIdeologyID(nCivTag);
                        final Color tempColor = CFG.getColorMixed(new Color(tempCiv.getR() / 255.0f, tempCiv.getG() / 255.0f, tempCiv.getB() / 255.0f, 0.775f), new Color(CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().r, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().g, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().b, 0.225f));
                        tempCiv.setR((int) (tempColor.r * 255.0f));
                        tempCiv.setG((int) (tempColor.g * 255.0f));
                        tempCiv.setB((int) (tempColor.b * 255.0f));
                    } catch (final GdxRuntimeException exr) {
                        try {
                            if (CFG.isAndroid()) {
                                try {
                                    file = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                    tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                                } catch (final GdxRuntimeException er) {
                                    file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                    tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                                }
                            } else {
                                file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                            }
                        } catch (final GdxRuntimeException r) {
                            file = Gdx.files.internal("game/civilizations/ran");
                            tempCiv = (Civilization_GameData3) CFG.deserialize(file.readBytes());
                        }
                    }
                }
                this.getCiv(nCivID).updateCivilizationIdeology(nCivTag, tempCiv.getR(), tempCiv.getG(), tempCiv.getB());
                if (CFG.palletManager.getActivePalletID() > 0) {
                    CFG.palletManager.loadCivilizationPalletColor(CFG.palletManager.getActivePalletID(), this.getCivsSize() - 1);
                }
                CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).updateVassalCivilizationsColor();
            } catch (final ClassNotFoundException | IOException e2) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e2);
                }
            }
        }
    }

    protected final int releaseAVasssal(String nTag, final List<Integer> nVassalProvinces, int nCapitalProvinceID, int nVassalOfCivID, final boolean canChangeTypeOfGoverment_AI) {
        return this.releaseAVasssal(nTag, nVassalProvinces, nCapitalProvinceID, nVassalOfCivID, canChangeTypeOfGoverment_AI, -1, true);
    }

    protected final int releaseAVasssal(String nTag, final List<Integer> nVassalProvinces, int nCapitalProvinceID, int nVassalOfCivID, final boolean canChangeTypeOfGoverment_AI, final boolean safeChecks) {
        return this.releaseAVasssal(nTag, nVassalProvinces, nCapitalProvinceID, nVassalOfCivID, canChangeTypeOfGoverment_AI, -1, safeChecks);
    }

    protected final int releaseAVasssal(String nTag, final List<Integer> nVassalProvinces, int nCapitalProvinceID, int nVassalOfCivID, final boolean canChangeTypeOfGoverment_AI, final int autonomy) {
        return this.releaseAVasssal(nTag, nVassalProvinces, nCapitalProvinceID, nVassalOfCivID, canChangeTypeOfGoverment_AI, autonomy, true);
    }

    protected final int releaseAVasssal(String nTag, final List<Integer> nVassalProvinces, int nCapitalProvinceID, int nVassalOfCivID, final boolean canChangeTypeOfGoverment_AI, final int autonomy, final boolean safeChecks) {
        try {
            nVassalOfCivID = this.getCiv(nVassalOfCivID).getPuppetOfCivID();
            if (safeChecks) {
                for (int tempArmy0 = nVassalProvinces.size() - 1; tempArmy0 >= 0; --tempArmy0) {
                    if (this.getProvince((Integer) nVassalProvinces.get(tempArmy0)).getIsCapital()) {
                        nVassalProvinces.remove(tempArmy0);
                    }
                }

                for (int tempArmy0 = nVassalProvinces.size() - 1; tempArmy0 >= 0; --tempArmy0) {
                    if (this.getProvince((Integer) nVassalProvinces.get(tempArmy0)).getCivID() != nVassalOfCivID || this.getProvince((Integer) nVassalProvinces.get(tempArmy0)).isOccupied()) {
                        nVassalProvinces.remove(tempArmy0);
                    }
                }
            }

            if (nVassalProvinces.size() == 0) {
                return -1;
            }

            int tempArmy0 = 0;
            int nCivID = -1;
            int nCivID_TRUE_Ideology_CivID = -1;

            for (int j = 1; j < this.getCivsSize(); ++j) {
                if (this.getCiv(j).getCivTag().equals(nTag)) {
                    nCivID_TRUE_Ideology_CivID = j;
                    break;
                }
            }

            String tempRealTag;
            if (canChangeTypeOfGoverment_AI) {
                tempRealTag = CFG.ideologiesManager.getRealTag(nTag) + CFG.ideologiesManager.getIdeology(this.getCiv(nVassalOfCivID).getIdeologyID()).getExtraTag();
                if (this.isCivTagAvailable(tempRealTag)) {
                    nTag = tempRealTag;
                }
            }

            if (!this.getCiv(nVassalOfCivID).getControlledByPlayer() && CFG.ideologiesManager.getIdeology(this.getCiv(nVassalOfCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
                int i = CFG.ideologiesManager.getIdeologyID(nTag);
                if (CFG.ideologiesManager.getIdeology(i).CAN_BECOME_CIVILIZED < 0) {
                    return -1;
                }
            }

            if (nCapitalProvinceID >= 0) {
                tempArmy0 = this.getProvince(nCapitalProvinceID).getArmy(0);
            } else {
                for (int j = 1; j < this.getCivsSize(); ++j) {
                    if (this.getCiv(j).getCivTag().equals(nTag)) {
                        nCivID = j;
                        break;
                    }
                }
                if (nCivID >= 0 && this.getCiv(nCivID).getCapitalProvinceID() >= 0) {
                    for (int j = nVassalProvinces.size() - 1; j >= 0; --j) {
                        if (nVassalProvinces.get(j) == this.getCiv(nCivID).getCapitalProvinceID()) {
                            nCapitalProvinceID = this.getCiv(nCivID).getCapitalProvinceID();
                            break;
                        }
                    }
                }
                if (nCapitalProvinceID < 0) {
                    if (nCivID_TRUE_Ideology_CivID >= 0 && this.getCiv(nCivID_TRUE_Ideology_CivID).getCapitalProvinceID() >= 0) {
                        for (int j = nVassalProvinces.size() - 1; j >= 0; --j) {
                            if (nVassalProvinces.get(j) == this.getCiv(nCivID_TRUE_Ideology_CivID).getCapitalProvinceID()) {
                                nCapitalProvinceID = this.getCiv(nCivID_TRUE_Ideology_CivID).getCapitalProvinceID();
                                break;
                            }
                        }
                    }
                    if (nCapitalProvinceID < 0) {
                        for (int j = nVassalProvinces.size() - 1; j >= 0; --j) {
                            if (this.getProvince(nVassalProvinces.get(j)).getCore().getHaveACore(nCivID_TRUE_Ideology_CivID)) {
                                if (nCapitalProvinceID < 0) {
                                    nCapitalProvinceID = nVassalProvinces.get(j);
                                }
                                else if (CFG.oR.nextInt(100) < 33) {
                                    nCapitalProvinceID = nVassalProvinces.get(j);
                                }
                            }
                        }
                    }
                    if (nCapitalProvinceID < 0) {
                        int tBest = 0;
                        for (int k = nVassalProvinces.size() - 1; k >= 0; --k) {
                            if (this.getProvince(nVassalProvinces.get(k)).getPopulationData().getPopulation() > this.getProvince(nVassalProvinces.get(tBest)).getPopulationData().getPopulation()) {
                                tBest = k;
                            }
                        }
                        nCapitalProvinceID = nVassalProvinces.get(tBest);
                    }
                }
            }

            if (this.createScenarioAddCivilization(nTag, nCapitalProvinceID, false, true, true)) {
                for (int j = 0; j < this.getPlayersSize(); ++j) {
                    this.getPlayer(j).addMetCivilization(true);
                }
            }

            if (nCivID < 0) {
                nCivID = this.getCivsSize() - 1;
                for (int j = 1; j < this.getCivsSize(); ++j) {
                    if (this.getCiv(j).getCivTag().equals(nTag)) {
                        nCivID = j;
                        break;
                    }
                }
            }
            this.getCiv(nCivID).setPuppetOfCivID(nVassalOfCivID, autonomy);
            this.getCiv(nCivID).setVassalLiberityDesire(0.0F);
            this.getCiv(nCivID).setMoney(92L);
            tempRealTag = CFG.ideologiesManager.getRealTag(nTag);

            int randPop;
            for(int i = 0; i < nVassalProvinces.size(); ++i) {
                int tArmyCivID = this.getProvince((Integer)nVassalProvinces.get(i)).getCivID();
                randPop = this.getProvince((Integer)nVassalProvinces.get(i)).getArmy(0);
                int tArmyOwner = this.getProvince((Integer)nVassalProvinces.get(i)).getArmyCivID(nVassalOfCivID);
                this.getProvince((Integer)nVassalProvinces.get(i)).updateArmy(0);
                this.getProvince((Integer)nVassalProvinces.get(i)).setTrueOwnerOfProvince(nCivID);
                this.getProvince((Integer)nVassalProvinces.get(i)).setCivID(nCivID, false);
                this.getProvince((Integer)nVassalProvinces.get(i)).updateArmy(tArmyCivID, randPop);
                this.getProvince((Integer)nVassalProvinces.get(i)).updateArmy(nVassalOfCivID, tArmyOwner);
                this.getProvince((Integer)nVassalProvinces.get(i)).setRevolutionaryRisk(this.getProvince((Integer)nVassalProvinces.get(i)).getRevolutionaryRisk() * 0.185F);
                this.getProvince((Integer)nVassalProvinces.get(i)).iIncome_Taxation = 1.0F;
                this.getProvince((Integer)nVassalProvinces.get(i)).iIncome_Production = 1.0F;
                this.getProvince((Integer)nVassalProvinces.get(i)).iAdministrationCost = 0.0F;
                this.getProvince((Integer)nVassalProvinces.get(i)).saveProvinceData.iNumOfTurnsWithBalanceOnMinus = 0;

                for(int j = this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getNationalitiesSize() - 1; j >= 0; --j) {
                    if (CFG.ideologiesManager.getRealTag(this.getCiv(this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getCivID(j)).getCivTag()).equals(tempRealTag)) {
                        this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().setPopulationOfCivID(nCivID, this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getPopulationID(j));
                        this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().setPopulationOfCivID(this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getCivID(j), 0);
                    } else {
                        randPop = (int)((float)this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getPopulationOfCivID(this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getCivID(j)) * (0.05F + (float)CFG.oR.nextInt(20) / 100.0F));
                        if (randPop > 0) {
                            this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().setPopulationOfCivID(this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getCivID(j), this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getPopulationOfCivID(this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getCivID(j)) - randPop);
                            this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().setPopulationOfCivID(nCivID, this.getProvince((Integer)nVassalProvinces.get(i)).getPopulationData().getPopulationOfCivID(nCivID) + randPop);
                        }
                    }
                }
            }

            for(int i = this.getProvince(nCapitalProvinceID).getPopulationData().getNationalitiesSize() - 1; i >= 0; --i) {
                if (this.getProvince(nCapitalProvinceID).getPopulationData().getCivID(i) != nCivID) {
                    randPop = (int)((float)this.getProvince(nCapitalProvinceID).getPopulationData().getPopulationOfCivID(this.getProvince(nCapitalProvinceID).getPopulationData().getCivID(i)) * (0.225F + (float)CFG.oR.nextInt(32) / 100.0F));
                    if (randPop > 0) {
                        this.getProvince(nCapitalProvinceID).getPopulationData().setPopulationOfCivID(this.getProvince(nCapitalProvinceID).getPopulationData().getCivID(i), this.getProvince(nCapitalProvinceID).getPopulationData().getPopulationOfCivID(this.getProvince(nCapitalProvinceID).getPopulationData().getCivID(i)) - randPop);
                        this.getProvince(nCapitalProvinceID).getPopulationData().setPopulationOfCivID(nCivID, this.getProvince(nCapitalProvinceID).getPopulationData().getPopulationOfCivID(nCivID) + randPop);
                    }
                }
            }

            //set color to lord
            this.getCiv(nVassalOfCivID).updateVassalCivilizationsColor();
            //load flag
            this.getCiv(nCivID).loadFlag(this.getCiv(nVassalOfCivID).getVassal_AutonomyStatus(nCivID).getFlagStatus());

            this.getProvince(nCapitalProvinceID).updateArmy(25);
            this.getProvince(nCapitalProvinceID).updateArmy(nVassalOfCivID, tempArmy0);
            this.getCiv(nCivID).setTechnologyLevel(this.getCiv(nVassalOfCivID).getTechnologyLevel() * (0.72F + (float)CFG.oR.nextInt(22) / 100.0F));
            this.getProvince(nCapitalProvinceID).getCore().addNewCore(nCivID, Game_Calendar.TURN_ID);
            this.getCiv(nCivID).buildNumOfUnits();
            CFG.gameAction.updateCivsHappiness(nCivID);
            CFG.gameAction.updateCivsMovementPoints(nCivID);
            CFG.gameAction.updateCivsDiplomacyPoints(nCivID);
            CFG.gameAction.buildRank_Score(nCivID);
            CFG.gameAction.buildRank_Score(nVassalOfCivID);
            CFG.gameAction.buildRank_Positions();
            final int tActiveProvince = CFG.game.getActiveProvinceID();
            this.setActiveProvinceID(-1);
            this.setActiveProvinceID(tActiveProvince);
            this.buildCivilizationRegions(nCivID);
            this.buildCivilizationRegions(nVassalOfCivID);
            this.setCivRelation_OfCivB(nVassalOfCivID, nCivID, Math.min(this.getCivRelation_OfCivB(nVassalOfCivID, nCivID) + 65.0F, 65.0F));
            this.setCivRelation_OfCivB(nCivID, nVassalOfCivID, Math.min(this.getCivRelation_OfCivB(nCivID, nVassalOfCivID) + 65.0F, 65.0F));
            CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(nVassalOfCivID, nCivID));
            return nCivID;
        } catch (IndexOutOfBoundsException var16) {
            CFG.exceptionStack(var16);
        } catch (NullPointerException var17) {
            CFG.exceptionStack(var17);
        }

        return -1;
    }
    
    protected final boolean createScenarioAddCivilization(final String nCivTag, final int nProvinceID, final boolean rebuildCores) {
        return this.createScenarioAddCivilization(nCivTag, nProvinceID, rebuildCores, true, false);
    }
    
    protected final boolean createScenarioAddCivilization(final String nCivTag, final int nProvinceID, final boolean rebuildCores, final boolean addCore, final boolean isInGame) {
        if (!nCivTag.equals("ran")) {
            for (int i = 0; i < this.getCivsSize(); ++i) {
                if (this.getCiv(i).getCivTag().equals(nCivTag)) {
                    if (this.getCiv(i).getCapitalProvinceID() >= 0) {
                        this.getProvince(this.getCiv(i).getCapitalProvinceID()).setIsCapital(false);
                    }
                    this.getCiv(i).setCapitalProvinceID(nProvinceID);
                    if (this.getCiv(i).getCapitalProvinceID() >= 0) {
                        this.getProvince(this.getCiv(i).getCapitalProvinceID()).setIsCapital(true);
                    }
                    return false;
                }
            }
        }
        try {
            FileHandle file = null;
            Civilization_GameData3 tempCiv = null;
            try {
                file = Gdx.files.internal("game/civilizations/" + nCivTag);
                tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
            }
            catch (final GdxRuntimeException e) {
                try {
                    file = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag(nCivTag));
                    tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                }
                catch (final GdxRuntimeException ex) {
                    try {
                        if (CFG.isAndroid()) {
                            try {
                                try {
                                    file = Gdx.files.local("game/civilizations_editor/" + nCivTag + "/" + nCivTag);
                                    tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                                }
                                catch (final GdxRuntimeException er) {
                                    file = Gdx.files.internal("game/civilizations_editor/" + nCivTag + "/" + nCivTag);
                                    tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                                }
                            }
                            catch (final GdxRuntimeException exw) {
                                try {
                                    file = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                    tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                                }
                                catch (final GdxRuntimeException exwq) {
                                    file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                    tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                                }
                            }
                        }
                        else {
                            try {
                                file = Gdx.files.internal("game/civilizations_editor/" + nCivTag + "/" + nCivTag);
                                tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                            }
                            catch (final GdxRuntimeException exqwe) {
                                file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nCivTag) + "/" + CFG.ideologiesManager.getRealTag(nCivTag));
                                tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                            }
                        }
                    }
                    catch (final GdxRuntimeException err) {
                        file = Gdx.files.internal("game/civilizations/ran");
                        tempCiv = (Civilization_GameData3)CFG.deserialize(file.readBytes());
                    }
                }
            }
            final int tempIdeologyID = CFG.ideologiesManager.getIdeologyID(nCivTag);
            final Color tempColor = CFG.getColorMixed(new Color(tempCiv.getR() / 255.0f, tempCiv.getG() / 255.0f, tempCiv.getB() / 255.0f, 0.775f), new Color(CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().r, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().g, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().b, 0.225f));
            tempCiv.setR((int)(tempColor.r * 255.0f));
            tempCiv.setG((int)(tempColor.g * 255.0f));
            tempCiv.setB((int)(tempColor.b * 255.0f));
            this.lCivs.add(new Civilization(nCivTag, tempCiv.getR(), tempCiv.getG(), tempCiv.getB(), nProvinceID, this.iCivsSize));
            this.iCivsSize = this.lCivs.size();
            this.getCiv(this.iCivsSize - 1).setCivID(this.iCivsSize - 1);
            if (this.getCiv(this.iCivsSize - 1).getCapitalProvinceID() >= 0) {
                this.getProvince(this.getCiv(this.iCivsSize - 1).getCapitalProvinceID()).setTrueOwnerOfProvince(this.iCivsSize - 1);
                this.getProvince(this.getCiv(this.iCivsSize - 1).getCapitalProvinceID()).setCivID(this.iCivsSize - 1, false, isInGame);
                this.getProvince(this.getCiv(this.iCivsSize - 1).getCapitalProvinceID()).updateArmy(this.getGameScenarios().getScenario_StartingArmyInCapitals());
            }
            this.getCiv(this.iCivsSize - 1).setMoney(-999999L);
            CFG.addCreateScenario_TechnologyLevelsByContinents_Civ();
            CFG.game.getCiv(this.getCivsSize() - 1).buildDiplomacy(true);
            for (int j = 0; j < this.getCivsSize(); ++j) {
                CFG.game.getCiv(j).updateDiplomacy_AfterAddingCivilization();
            }
            if (CFG.palletManager.getActivePalletID() > 0) {
                CFG.palletManager.loadCivilizationPalletColor(CFG.palletManager.getActivePalletID(), this.getCivsSize() - 1);
            }
            for (int j = 0; j < CFG.game.getPlayersSize(); ++j) {
                CFG.game.getPlayer(j).addMetCivilization(false);
            }
        }
        catch (final ClassNotFoundException | NullPointerException | GdxRuntimeException | IOException e2) {
            CFG.exceptionStack(e2);
        }
        if (nProvinceID >= 0) {
            if (rebuildCores) {
                this.getProvince(nProvinceID).buildProvinceCore();
                CFG.province_Cores_GameData.clearCoresData(nProvinceID);
            }
            else if (addCore) {
                this.getProvince(nProvinceID).getCore().addNewCore(this.iCivsSize - 1, Game_Calendar.TURN_ID);
            }
        }
        return true;
    }
    
    protected final void createScenarioRemoveCivilization(final int nRemoveCivID) {
        if (nRemoveCivID == 0) {
            return;
        }
        try {
            this.getCiv(nRemoveCivID).getFlag().getTexture().dispose();
            if (this.getCiv(nRemoveCivID).getCapitalProvinceID() >= 0) {
                this.getProvince(this.getCiv(nRemoveCivID).getCapitalProvinceID()).setIsCapital(false);
            }
            CFG.removeCreateScenario_TechnologyLevelsByContinents_Civ(nRemoveCivID - 1);
        }
        catch (final Exception ex) {}
        try {
            if (this.lCivs.get(nRemoveCivID).getAllianceID() > 0) {
                this.getAlliance(this.lCivs.get(nRemoveCivID).getAllianceID()).removeCivilization(nRemoveCivID);
                this.lCivs.get(nRemoveCivID).setAllianceID(0);
                this.checkAlliances();
            }
        }
        catch (final Exception ex2) {}
        try {
            for (int i = 1; i < this.getAlliancesSize(); ++i) {
                this.getAlliance(i).updateCivsIDs_AfterRemoveCiv(nRemoveCivID);
            }
        }
        catch (final Exception ex3) {}
        try {
            for (int i = 1; i < this.getCivsSize(); ++i) {
                CFG.game.getCiv(i).updateDiplomacy_AfterRemoveCivilization_Relations(nRemoveCivID - 1);
            }
        }
        catch (final Exception ex4) {}
        try {
            for (int i = 1; i < nRemoveCivID; ++i) {
                CFG.game.getCiv(i).updateDiplomacy_AfterRemoveCivilization(nRemoveCivID - 1 - i);
            }
        }
        catch (final Exception ex5) {}
        try {
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                for (int j = 1; j < CFG.game.getProvince(i).getCivsSize(); ++j) {
                    if (CFG.game.getProvince(i).getCivID(j) == nRemoveCivID) {
                        CFG.game.getProvince(i).updateArmy(CFG.game.getProvince(i).getCivID(j), 0);
                    }
                    else if (CFG.game.getProvince(i).getCivID(j) > nRemoveCivID) {
                        final List<Integer> tempCivs = new ArrayList<Integer>();
                        final List<Integer> tempArmies = new ArrayList<Integer>();
                        for (int k = 1; k < CFG.game.getProvince(i).getCivsSize(); ++k) {
                            if (CFG.game.getProvince(i).getCivID(k) != nRemoveCivID) {
                                tempCivs.add(CFG.game.getProvince(i).getCivID(k));
                                tempArmies.add(CFG.game.getProvince(i).getArmy(k));
                            }
                        }
                        for (int k = 0; k < tempCivs.size(); ++k) {
                            CFG.game.getProvince(i).updateArmy(tempCivs.get(k), 0);
                            if (tempCivs.get(k) > nRemoveCivID) {
                                tempCivs.set(k, tempCivs.get(k) - 1);
                            }
                        }
                        for (int k = 0; k < tempCivs.size(); ++k) {
                            CFG.game.getProvince(i).updateArmy(tempCivs.get(k), tempArmies.get(k));
                        }
                        break;
                    }
                }
            }
        }
        catch (final Exception ex6) {}
        try {
            CFG.holyRomanEmpire_Manager.getHRE().updateHRE_AfterRemoveCivilization(nRemoveCivID);
        }
        catch (final Exception ex7) {}

        //first create a map to store the puppet relations and autonomy
        //w adjusted civids for movement
        LinkedHashMap<Integer, int[]> hmTemp = new LinkedHashMap<Integer, int[]>();
        for (int i = 1; i < this.iCivsSize; ++i) {
            if (i == nRemoveCivID || !this.getCiv(i).getIsPupet()) {
                continue;
            }

            if (this.getCiv(i).getPuppetOfCivID() > nRemoveCivID) {
                try {
                    if (i > nRemoveCivID) {
                        hmTemp.put(i - 1, new int[] {this.getCiv(this.getCiv(i).getPuppetOfCivID()).getCivID() - 1, this.getCiv(this.getCiv(i).getPuppetOfCivID()).getVassal_AutonomyStatus(i).getIndexOf(), i - 1});
                    } else {
                        hmTemp.put(i, new int[] {this.getCiv(this.getCiv(i).getPuppetOfCivID()).getCivID() - 1, this.getCiv(this.getCiv(i).getPuppetOfCivID()).getVassal_AutonomyStatus(i).getIndexOf(), i});
                    }
                } catch (NullPointerException ex) {
                }
            } else if (this.getCiv(i).getPuppetOfCivID() == nRemoveCivID) {
                if (i > nRemoveCivID) {
                    hmTemp.put(i - 1, new int[] {i - 1, -1, i - 1});
                } else {
                    hmTemp.put(i, new int[] {i, -1, i});
                }
            }
        }

        for (int[] values : hmTemp.values()) {
            if (values[0] < 0 || values[2] < 0) {
                Gdx.app.log("PreRemoveCiv", "Skipping invalid puppet values: " + values[0] + ", " + values[2]);
                continue;
            }
            //Gdx.app.log("PreRemoveCiv", "Civ: " + CFG.game.getCiv(values[2]).getCivName() + " PuppetOfCiv: " + CFG.game.getCiv(values[0]).getCivName() + " Autonomy: " + CFG.gameAutonomy.getAutonomy(values[1]).getName());
        }

        //now actually remove the civ and adjust ids
        this.lCivs.remove(nRemoveCivID);
        this.iCivsSize = this.lCivs.size();

        //delete all vassal relations data in first pass
        //done seperately bc dont want to delete valid vassal data during second pass if lord civid is after puppets
        for (int i = 1; i < this.iCivsSize; ++i) {
            this.getCiv(i).setCivID_Just(i);
            this.getCiv(i).civGameData.iVassalsSize = 0;
            this.getCiv(i).civGameData.lVassals.clear();
            this.getCiv(i).setPuppetOfCivID(i);
        }

        Gdx.app.log("RemoveCiv", "Removing civilization with ID: " + nRemoveCivID + ", new size: " + this.iCivsSize);
        for (int[] values : hmTemp.values()) {
            if (values[0] < 0 || values[2] < 0) {
                Gdx.app.log("RemoveCiv", "Skipping invalid puppet values: " + values[0] + ", " + values[2]);
                continue;
            }
            //Gdx.app.log("AftRemoveCiv", "Civ: " + CFG.game.getCiv(values[2]).getCivName() + " PuppetOfCiv: " + CFG.game.getCiv(values[0]).getCivName() + " Autonomy: " + CFG.gameAutonomy.getAutonomy(values[1]).getName());
        }

        //second pass, set puppet relations to those seen in adjusted hashmap
        for (int i = 1; i < this.iCivsSize; ++i) {
            try {
                if (hmTemp.get(i) != null) {
                    int puppetOfCivID = hmTemp.get(i)[0];
                    int autonomyIndex = hmTemp.get(i)[1];
                    if (autonomyIndex >= 0) {
                        this.getCiv(i).setPuppetOfCivID(puppetOfCivID, autonomyIndex);
                        Gdx.app.log("RemoveCiv", "Setting puppet civ for " + this.getCiv(i).getCivName() + " to " + this.getCiv(puppetOfCivID).getCivName() + " with autonomy " + CFG.gameAutonomy.getAutonomy(autonomyIndex).getName());
                    } else {
                        this.getCiv(i).setPuppetOfCivID(puppetOfCivID);
                        Gdx.app.log("RemoveCiv", "Setting puppet civ for " + this.getCiv(i).getCivName() + " to " + this.getCiv(puppetOfCivID).getCivName() + " with no autonomy");
                    }
                }

            } catch (NullPointerException | IndexOutOfBoundsException ex) {
            }
        }

        hmTemp.clear();

        CFG.eventsManager.updateEventsAferRemoveCiv(nRemoveCivID);
        try {
            for (int i = 0; i < this.getProvincesSize(); ++i) {
                if (this.getProvince(i).getCivID() > nRemoveCivID) {
                    this.getProvince(i).setCivID_Just(this.getProvince(i).getCivID() - 1);
                    if (this.getProvince(i).getCore() != null) {
                        for (int j = 0; j < this.getProvince(i).getCore().getCivsSize(); ++j) {
                            if (this.getProvince(i).getCore().getCivID(j) > nRemoveCivID) {
                                this.getProvince(i).getCore().setCivID_Editor(j, this.getProvince(i).getCore().getCivID(j) - 1);
                            }
                            else if (this.getProvince(i).getCore().getCivID(j) == nRemoveCivID) {
                                this.getProvince(i).getCore().removeCore(nRemoveCivID);
                                --j;
                            }
                        }
                    }
                }
                else if (this.getProvince(i).getCivID() == nRemoveCivID) {
                    this.getProvince(i).setCivID_Just(0);
                    this.getProvince(i).resetCore();
                    this.getProvince(i).updateProvinceBorder();
                }
                else if (this.getProvince(i).getCore() != null) {
                    for (int j = 0; j < this.getProvince(i).getCore().getCivsSize(); ++j) {
                        if (this.getProvince(i).getCore().getCivID(j) > nRemoveCivID) {
                            this.getProvince(i).getCore().setCivID_Editor(j, this.getProvince(i).getCore().getCivID(j) - 1);
                        }
                        else if (this.getProvince(i).getCore().getCivID(j) == nRemoveCivID) {
                            this.getProvince(i).getCore().removeCore(nRemoveCivID);
                            --j;
                        }
                    }
                }
            }
        }
        catch (final Exception ex9) {}
        try {
            CFG.province_Cores_GameData.updateAfterRemove(nRemoveCivID);
            for (int i = CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.size() - 1; i >= 0; --i) {
                if (CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.get(i) == nRemoveCivID) {
                    CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.remove(i);
                }
                else if (CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.get(i) > nRemoveCivID) {
                    CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.set(i, CFG.lCREATE_SCENARIO_IS_PART_OF_CAMPAIGN_CIVSIDS.get(i) - 1);
                }
            }
        }
        catch (final Exception ex10) {}
    }
    
    protected final Civilization getNeutralCivilization() {
        return new Civilization("neu", 251, 251, 221, -1, 0);
    }
    
    protected final void sortCivilizationsAZ() {
        this.lCivsSortedAZ.clear();
        final List<Integer> lTempNames = new ArrayList<Integer>();
        for (int i = 1; i < this.getCivsSize(); ++i) {
            lTempNames.add(i);
        }
        while (lTempNames.size() > 0) {
            int toAddID = 0;
            for (int j = lTempNames.size() - 1; j > 0; --j) {
                if (CFG.compareAlphabetic_TwoString(this.getCiv(lTempNames.get(toAddID)).getCivName(), this.getCiv(lTempNames.get(j)).getCivName())) {
                    toAddID = j;
                }
            }
            this.lCivsSortedAZ.add(lTempNames.get(toAddID));
            lTempNames.remove(toAddID);
        }
    }
    
    protected final int getSortedCivsSize() {
        return this.lCivsSortedAZ.size();
    }
    
    protected final int getSortedCivsAZ(final int i) {
        return this.lCivsSortedAZ.get(i);
    }

    protected final void checkArmies() {
        for(int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getWasteland() < 0 && !this.getProvince(i).getSeaProvince()) {
                if (this.getProvince(i).getCivID() == 0) {
                    if (this.getProvince(i).getCivsSize() > 1) {
                        try {
                            while(this.getProvince(i).getCivsSize() > 1) {
                                this.getProvince(i).updateArmy(this.getProvince(i).getCivID(1), 0);
                            }
                        } catch (StackOverflowError | IndexOutOfBoundsException var3) {
                            if (CFG.LOGS) {
                                CFG.exceptionStack(var3);
                            }
                        }
                    }
                } else {
                    for(int j = 1; j < this.getProvince(i).getCivsSize(); ++j) {
                        if (!this.getSelectedProvinces().canAddArmy(this.getProvince(i).getCivID(j), i)) {
                            this.getProvince(i).updateArmy(this.getProvince(i).getCivID(j), 0);
                            --j;
                        } else if (this.getProvince(i).getCivID(j) == this.getProvince(i).getCivID()) {
                            this.getProvince(i).updateArmy(this.getProvince(i).getArmy(j));
                            this.getProvince(i).removeArmy_ID(j);
                            --j;
                        }
                    }
                }
            }
        }

    }
    
    protected final void loadCities() {
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).clearCities();
        }
        this.lCities = this.gameCities.loadCities();
        try {
            final FileHandle tempFileT = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + "Age_of_Civilizations");
            final String tempT = tempFileT.readString();
            final String[] tagsSPLITED = tempT.split(";");
            for (String s : tagsSPLITED) {
                final FileHandle fileData = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + s);
                try {
                    final City tempC = (City) CFG.deserialize(fileData.readBytes());
                    tempC.setCityLevel(CFG.getEditorCityLevel(tempC.getCityLevel()));
                    this.lCities.add(tempC);
                } catch (final ClassNotFoundException | IOException e) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(e);
                    }
                }
            }
        }
        catch (final GdxRuntimeException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        this.buildCitiesProvinceID();
    }
    
    protected final void buildCitiesProvinceID() {
        for (int i = this.lCities.size() - 1; i >= 0; --i) {
            for (int j = 0; j < CFG.game.getProvincesSize(); ++j) {
                if (this.getProvince(j).getMinX() <= this.lCities.get(i).getPosX() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMaxX() >= this.lCities.get(i).getPosX() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMinY() <= this.lCities.get(i).getPosY() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMaxY() >= this.lCities.get(i).getPosY() * CFG.map.getMapBG().getMapScale() && this.pathContains(j, this.lCities.get(i).getPosX() * CFG.map.getMapBG().getMapScale(), this.lCities.get(i).getPosY() * CFG.map.getMapBG().getMapScale())) {
                    this.getProvince(j).addCity(this.lCities.get(i));
                    break;
                }
            }
        }
        this.lCities.clear();
        this.lCities = null;
    }
    
    protected final void loadMountains() {
        this.lMountains = this.gameMountains.loadMountains();
        this.buildMountainsProvinceID();
        this.loadWonders();
    }
    
    protected final void buildMountainsProvinceID() {
        for (Mountain lMountain : this.lMountains) {
            for (int j = 0; j < CFG.game.getProvincesSize(); ++j) {
                if (this.getProvince(j).getMinX() <= lMountain.getPosX() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMaxX() >= lMountain.getPosX() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMinY() <= lMountain.getPosY() * CFG.map.getMapBG().getMapScale() && this.getProvince(j).getMaxY() >= lMountain.getPosY() * CFG.map.getMapBG().getMapScale() && this.pathContains(j, lMountain.getPosX() * CFG.map.getMapBG().getMapScale(), lMountain.getPosY() * CFG.map.getMapBG().getMapScale())) {
                    this.getProvince(j).addMountain(lMountain);
                    break;
                }
            }
        }
        this.lMountains.clear();
        this.lMountains = null;
    }
    
    protected final void loadWonders() {
        this.wondersManager.lWonders = this.gameWonders.loadWonders();
        this.wondersManager.buildWondersProvinceID();
    }
    
    protected final void addPlayer(final int nCivID) {
        this.lPlayers.add(new Player(nCivID));
        this.iPlayersSize = this.lPlayers.size();
    }
    
    protected final void addPlayer() {
        if (this.getPlayersSize() >= this.getGameScenarios().getNumOfCivs(this.getScenarioID())) {
            return;
        }
        final Random oR = new Random();
        final int tempResult = oR.nextInt(this.gameScenarios.getNumOfCivs(this.scenarioID)) + 1;
        try {
            if (this.getCiv(tempResult).getNumOfProvinces() == 0) {
                this.addPlayer();
                return;
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        for (int i = 0; i < this.iPlayersSize; ++i) {
            if (this.getPlayer(i).getCivID() == tempResult) {
                this.addPlayer();
                return;
            }
        }
        this.lPlayers.add(new Player(tempResult));
        this.iPlayersSize = this.lPlayers.size();
        CFG.menuManager.rebuildCivilizations_Info_Players();
    }
    
    protected final void removePlayer(final int i) {
        try {
            if (i == 0 && this.getPlayersSize() == 1) {
                return;
            }
            this.getPlayer(i).disposePlayersFlag();
            if (this.lPlayers.get(i).getCivID() > 0) {
                CFG.game.getCiv(this.lPlayers.get(i).getCivID()).setControlledByPlayer(false);
            }
            this.lPlayers.remove(i);
            this.iPlayersSize = this.lPlayers.size();
            if (CFG.menuManager.getInCreateNewGame()) {
                CFG.menuManager.rebuildCivilizations_Info_Players();
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final NullPointerException ex2) {}
    }
    
    protected final void checkPlayersCivilizations() {
        for (int i = 0; i < this.getPlayersSize(); ++i) {
            if (this.getPlayer(i).getCivID() > 0 && this.getCiv(this.getPlayer(i).getCivID()).getNumOfProvinces() == 0) {
                this.randomPlayerCivilizations(i);
            }
        }
    }
    
    protected final void update() {
        if (CFG.menuManager.getInGameView() && RTS.isEnabled() && !RTS.PAUSE) {
            RTS.updateTime();
            CFG.setRender_3(true);
        }
        if (this.getActiveProvinceID() >= 0 && (this.getProvince(this.getActiveProvinceID()).getDrawProvince() || this.checkHighlightedProvince() || this.lCurrentRegroupArmyLine.size() > 0)) {
            this.activeProvince_Animation_Data.update();
            this.highlightedProvince_Animation_UpdateOffset.updateOffset();
        }
        try {
            if ((this.currentMoveUnitsLine != null && (this.getProvince(this.currentMoveUnitsLine.getFromProvinceID()).getDrawProvince() || this.getProvince(this.currentMoveUnitsLine.getToProvinceID()).getDrawProvince())) || this.iHighlightedProvincesSize > 0 || this.lCurrentRegroupArmyLine.size() > 0 || this.drawMoveUnitsArmy_UpdateAnimation || this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize() > 0 || this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 || this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrateSize() > 0 || CFG.gameAction.getCurrentMoveunits() != null || this.iMoveUnits_JustDraw_AnotherArmiesSize > 0) {
                this.updatePath();
            }
        }
        catch (final IndexOutOfBoundsException e) {
            if (CFG.LOGS) {
                CFG.exceptionStack(e);
            }
        }
    }
    
    private final boolean checkHighlightedProvince() {
        for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
            if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince()) {
                return true;
            }
        }
        return false;
    }
    
    protected final void updateProvincesInView() {
        if (!this.updateProvincesInView) {
            return;
        }
        this.updateProvincesInView = false;
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).setDrawProvince(false);
        }
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).setDrawProvince(false);
        }
        for (int i = 0; i < CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getWastelandProvinceInViewID(i)).setDrawProvince(false);
        }
        CFG.NUM_OF_PROVINCES_IN_VIEW = 0;
        CFG.NUM_OF_SEA_PROVINCES_IN_VIEW = 0;
        CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW = 0;
        this.lProvincesInView.clear();
        this.lSeaProvincesInView.clear();
        this.lWastelandProvincesInView.clear();
        for (int i = 0; i < this.iRegionsSize; ++i) {
            if (this.inViewY(this.lRegions.get(i).getMinY(), this.lRegions.get(i).getMaxY())) {
                if (this.inViewX(this.lRegions.get(i).getMinX(), this.lRegions.get(i).getMaxX()) || this.inViewX2(this.lRegions.get(i).getMinX(), this.lRegions.get(i).getMaxX())) {
                    this.updateDrawRegionProvinces(i);
                }
                else if (this.lRegions.get(i).getBelowZero() && this.inViewXBelowZero(this.lRegions.get(i).getMinX(), this.lRegions.get(i).getMaxX())) {
                    this.updateDrawRegionProvinces(i);
                }
            }
        }
        CFG.NUM_OF_PROVINCES_IN_VIEW = this.lProvincesInView.size();
        CFG.NUM_OF_SEA_PROVINCES_IN_VIEW = this.lSeaProvincesInView.size();
        CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW = this.lWastelandProvincesInView.size();
    }
    
    protected final void clearProvincesInView() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            this.getProvince(i).setDrawProvince(false);
        }
        CFG.NUM_OF_PROVINCES_IN_VIEW = 0;
        CFG.NUM_OF_SEA_PROVINCES_IN_VIEW = 0;
        CFG.NUM_OF_WASTELAND_PROVINCES_IN_VIEW = 0;
        this.lProvincesInView.clear();
        this.lSeaProvincesInView.clear();
        this.lWastelandProvincesInView.clear();
    }
    
    private final void updateDrawRegionProvinces(final int iID) {
        if (this.inViewY_WholeRegion(this.lRegions.get(iID).getMinY(), this.lRegions.get(iID).getMaxY())) {
            if (this.inViewX_WholeRegion(this.lRegions.get(iID).getMinX(), this.lRegions.get(iID).getMaxX())) {
                for (int i = 0; i < this.lRegions.get(iID).getProvincesSize(); ++i) {
                    this.getProvince(this.lRegions.get(iID).getProvince(i)).setTranslateProvincePosX(CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() + CFG.map.getMapCoordinates().getPosX());
                    this.getProvince(this.lRegions.get(iID).getProvince(i)).setDrawProvince(true);
                    if (this.getProvince(this.lRegions.get(iID).getProvince(i)).getWasteland() >= 0) {
                        this.lWastelandProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                    else if (this.getProvince(this.lRegions.get(iID).getProvince(i)).getSeaProvince()) {
                        this.lSeaProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                    else {
                        this.lProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                }
                return;
            }
            if (this.inViewX_WholeRegion2(this.lRegions.get(iID).getMinX(), this.lRegions.get(iID).getMaxX())) {
                for (int i = 0; i < this.lRegions.get(iID).getProvincesSize(); ++i) {
                    this.getProvince(this.lRegions.get(iID).getProvince(i)).setTranslateProvincePosX(CFG.map.getMapCoordinates().getPosX());
                    this.getProvince(this.lRegions.get(iID).getProvince(i)).setDrawProvince(true);
                    if (this.getProvince(this.lRegions.get(iID).getProvince(i)).getWasteland() >= 0) {
                        this.lWastelandProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                    else if (this.getProvince(this.lRegions.get(iID).getProvince(i)).getSeaProvince()) {
                        this.lSeaProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                    else {
                        this.lProvincesInView.add(this.lRegions.get(iID).getProvince(i));
                    }
                }
                return;
            }
        }
        for (int i = 0; i < this.lRegions.get(iID).getProvincesSize(); ++i) {
            this.updateDrawProvince(this.lRegions.get(iID).getProvince(i));
        }
    }
    
    protected final void updateDrawProvince(final int nProvinceID) {
        if (this.inViewY(nProvinceID)) {
            if (this.inViewX(nProvinceID)) {
                this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() + CFG.map.getMapCoordinates().getPosX());
                this.getProvince(nProvinceID).setDrawProvince(true);
                if (this.getProvince(nProvinceID).getWasteland() >= 0) {
                    this.lWastelandProvincesInView.add(nProvinceID);
                }
                else if (this.getProvince(nProvinceID).getSeaProvince()) {
                    this.lSeaProvincesInView.add(nProvinceID);
                }
                else {
                    this.lProvincesInView.add(nProvinceID);
                }
            }
            else if (this.inViewX2(nProvinceID)) {
                this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapCoordinates().getPosX());
                this.getProvince(nProvinceID).setDrawProvince(true);
                if (this.getProvince(nProvinceID).getWasteland() >= 0) {
                    this.lWastelandProvincesInView.add(nProvinceID);
                }
                else if (this.getProvince(nProvinceID).getSeaProvince()) {
                    this.lSeaProvincesInView.add(nProvinceID);
                }
                else {
                    this.lProvincesInView.add(nProvinceID);
                }
            }
            else if (this.getProvince(nProvinceID).getBelowZero()) {
                if (this.inViewXBelowZero(nProvinceID)) {
                    this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapBG().getWidth() + CFG.map.getMapCoordinates().getPosX());
                    this.getProvince(nProvinceID).setDrawProvince(true);
                    if (this.getProvince(nProvinceID).getWasteland() >= 0) {
                        this.lWastelandProvincesInView.add(nProvinceID);
                    }
                    else if (this.getProvince(nProvinceID).getSeaProvince()) {
                        this.lSeaProvincesInView.add(nProvinceID);
                    }
                    else {
                        this.lProvincesInView.add(nProvinceID);
                    }
                }
                else {
                    this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapCoordinates().getPosX());
                    this.getProvince(nProvinceID).setDrawProvince(false);
                }
            }
            else {
                this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapCoordinates().getPosX());
                this.getProvince(nProvinceID).setDrawProvince(false);
            }
        }
        else {
            this.getProvince(nProvinceID).setTranslateProvincePosX(CFG.map.getMapCoordinates().getPosX());
            this.getProvince(nProvinceID).setDrawProvince(false);
        }
    }
    
    protected final void drawActiveProvince(final SpriteBatch oSB) {
        if (this.iActiveProvince >= 0 && this.getProvince(this.iActiveProvince).getDrawProvince()) {
            if (CFG.chooseProvinceMode) {
                oSB.setColor(new Color(CFG.getColorStep(255, 55, this.activeProvince_Animation_Data.getColorStepID(), 30), CFG.getColorStep(255, 55, this.activeProvince_Animation_Data.getColorStepID(), 30), CFG.getColorStep(255, 55, this.activeProvince_Animation_Data.getColorStepID(), 30), this.getProvince(this.iActiveProvince).getSeaProvince() ? ((this.activeProvince_Animation_Data.getAlpha() + 35) / 255.0f / 3.0f) : ((this.activeProvince_Animation_Data.getAlpha() + 35) / 255.0f * (this.fDashedLine_Percentage_HighlitedProvinceBorder / 100.0f))));
                this.getProvince(this.iActiveProvince).drawProvince_ActiveProvince(oSB);
            }
            else {
                if (this.getProvince(this.iActiveProvince).getSeaProvince()) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, this.activeProvince_Animation_Data.getAlpha() / 2.0f / 255.0f));
                }
                else {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, this.activeProvince_Animation_Data.getAlpha() / 255.0f));
                }
                this.getProvince(this.iActiveProvince).drawProvince_ActiveProvince(oSB);
            }
        }
        try {
            if (MenuManager.iHoveredProvinceID >= 0 && this.iActiveProvince != MenuManager.iHoveredProvinceID && this.getProvince(MenuManager.iHoveredProvinceID).getDrawProvince()) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, this.getProvince(MenuManager.iHoveredProvinceID).getSeaProvince() ? 0.02f : 0.05f));
                this.getProvince(MenuManager.iHoveredProvinceID).drawProvince_ActiveProvince(oSB);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawActiveProvince_HoverJust_WithoutDrawingActiveProvince(final SpriteBatch oSB) {
        try {
            if (MenuManager.iHoveredProvinceID >= 0) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, this.getProvince(MenuManager.iHoveredProvinceID).getSeaProvince() ? 0.02f : 0.05f));
                this.getProvince(MenuManager.iHoveredProvinceID).drawProvince_ActiveProvince(oSB);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawActiveProvinceFlag(final SpriteBatch oSB) {
        if (this.iActiveProvince >= 0 && this.getProvince(this.iActiveProvince).getDrawProvince()) {
            if (this.getProvince(this.iActiveProvince).getCivID() > 0) {
                oSB.setColor(Color.WHITE);
                this.getProvince(this.iActiveProvince).drawProvinceFlag(oSB);
            }
            else {
                this.drawActiveProvince(oSB);
            }
        }
    }
    
    protected final void drawHighlightProvince(final SpriteBatch oSB) {
        for (int i = 0, iSize = this.iHighlightedProvincesSize; i < iSize; ++i) {
            if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince()) {
                this.highlightedProvince_AnimationData.updateColor(oSB, this.lHighlightedProvinces.get(i));
                this.getProvince(this.lHighlightedProvinces.get(i)).drawProvince_ActiveProvince(oSB);
            }
        }
    }

    protected final void drawActiveProvinceBorder(SpriteBatch oSB) {
        try {
            for(int i = this.drawProvinceBorder_LandBySeaIDs.size() - 1; i >= 0; --i) {
                if (this.getProvince(this.drawProvinceBorder_LandBySeaIDs.get(i).iProvinceID).getDrawProvince()) {
                    ((Province_Border)this.getProvince(this.drawProvinceBorder_LandBySeaIDs.get(i).iProvinceID).getProvinceBordersLandBySea().get(this.drawProvinceBorder_LandBySeaIDs.get(i).withProvinceID)).drawProvince_Border.draw(oSB, this.getProvince(this.drawProvinceBorder_LandBySeaIDs.get(i).iProvinceID).getTranslateProvincePosX());
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException var3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var3);
            }
        }

        oSB.setColor(Color.WHITE);
    }
    
    protected final void updateHighlitghtProvinceBorder(final SpriteBatch oSB) {
        if (!this.highlightedProvinceBorder_Update) {
            return;
        }
        if (this.highlightedProvinceBorder_BackAnimation) {
            this.fDashedLine_Percentage_HighlitedProvinceBorder -= (System.currentTimeMillis() - this.lDashedLineTime_Percentage_HighlitedProvinceBorder) / 350.0f * 100.0f;
            if (this.fDashedLine_Percentage_HighlitedProvinceBorder <= 0.0f) {
                this.disableHighlightedProvinces();
                this.lHighlightedProvinces.clear();
                this.iHighlightedProvincesSize = 0;
                this.highlightedProvinceBorder_Update = false;
                CFG.gameAction.updateInGame_ProvinceInfo();
                return;
            }
        }
        else {
            this.fDashedLine_Percentage_HighlitedProvinceBorder += (System.currentTimeMillis() - this.lDashedLineTime_Percentage_HighlitedProvinceBorder) / 750.0f * 95.0f;
            if (this.fDashedLine_Percentage_HighlitedProvinceBorder > 100.0f) {
                this.fDashedLine_Percentage_HighlitedProvinceBorder = 100.0f;
                this.enableHighlightedProvinces_Classic();
                this.highlightedProvinceBorder_Update = false;
                return;
            }
        }
        this.lDashedLineTime_Percentage_HighlitedProvinceBorder = System.currentTimeMillis();
        CFG.setRender_3(true);
    }
    
    protected final void drawCivNameBG(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nWidth, final int nHeight) {
        ImageManager.getImage(Images.civ_name_bg).draw2(oSB, nPosX, nPosY - ImageManager.getImage(Images.civ_name_bg).getHeight(), nWidth - ImageManager.getImage(Images.civ_name_bg).getWidth(), nHeight - ImageManager.getImage(Images.civ_name_bg).getHeight());
        ImageManager.getImage(Images.civ_name_bg).draw(oSB, nPosX, nPosY + nHeight - ImageManager.getImage(Images.civ_name_bg).getHeight(), nWidth - ImageManager.getImage(Images.civ_name_bg).getWidth(), false, true);
        ImageManager.getImage(Images.civ_name_bg).draw2(oSB, nPosX + nWidth - ImageManager.getImage(Images.civ_name_bg).getWidth(), nPosY - ImageManager.getImage(Images.civ_name_bg).getHeight(), ImageManager.getImage(Images.civ_name_bg).getWidth(), nHeight - ImageManager.getImage(Images.civ_name_bg).getHeight(), true);
        ImageManager.getImage(Images.civ_name_bg).draw(oSB, nPosX + nWidth - ImageManager.getImage(Images.civ_name_bg).getWidth(), nPosY + nHeight - ImageManager.getImage(Images.civ_name_bg).getHeight(), true, true);
    }
    
    private final void drawCivFlagBG(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nWidth, final int nHeight) {
        ImageManager.getImage(Images.civ_flag_bg).draw2(oSB, nPosX, nPosY - ImageManager.getImage(Images.civ_flag_bg).getHeight(), nWidth - ImageManager.getImage(Images.civ_flag_bg).getWidth(), nHeight - ImageManager.getImage(Images.civ_flag_bg).getHeight());
        ImageManager.getImage(Images.civ_flag_bg).draw(oSB, nPosX, nPosY + nHeight - ImageManager.getImage(Images.civ_flag_bg).getHeight(), nWidth - ImageManager.getImage(Images.civ_flag_bg).getWidth(), false, true);
        ImageManager.getImage(Images.civ_flag_bg).draw2(oSB, nPosX + nWidth - ImageManager.getImage(Images.civ_flag_bg).getWidth(), nPosY - ImageManager.getImage(Images.civ_flag_bg).getHeight(), ImageManager.getImage(Images.civ_flag_bg).getWidth(), nHeight - ImageManager.getImage(Images.civ_flag_bg).getHeight(), true);
        ImageManager.getImage(Images.civ_flag_bg).draw(oSB, nPosX + nWidth - ImageManager.getImage(Images.civ_flag_bg).getWidth(), nPosY + nHeight - ImageManager.getImage(Images.civ_flag_bg).getHeight(), true, true);
    }
    
    protected final void drawProvinceArmyBackground(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nWidth, final int nHeight, final int ImageID) {
        ImageManager.getImage(ImageID).draw2(oSB, nPosX, nPosY - ImageManager.getImage(ImageID).getHeight(), nWidth - ImageManager.getImage(ImageID).getWidth(), nHeight);
        ImageManager.getImage(ImageID).draw2(oSB, nPosX + nWidth - ImageManager.getImage(ImageID).getWidth(), nPosY - ImageManager.getImage(ImageID).getHeight(), ImageManager.getImage(ImageID).getWidth(), nHeight, true, false);
    }
    
    protected final void drawProvinceArmyBackground_Capital(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nWidth, final int nHeight, final int ImageID) {
        ImageManager.getImage(ImageID).draw2(oSB, nPosX, nPosY - ImageManager.getImage(ImageID).getHeight(), nWidth, nHeight - ImageManager.getImage(ImageID).getHeight(), true);
        ImageManager.getImage(ImageID).draw2(oSB, nPosX, nPosY + nHeight - ImageManager.getImage(ImageID).getHeight() * 2, nWidth, ImageManager.getImage(ImageID).getHeight(), true, true);
    }
    
    protected final void drawProvincesInfo(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawProvinceInfo(oSB, nScale);
        }
    }
    
    protected final void drawProvincesInfo_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (CFG.getMetProvince(this.getProvinceInViewID(i))) {
                this.getProvince(this.getProvinceInViewID(i)).drawProvinceInfo(oSB, nScale);
            }
        }
    }
    
    protected final void drawProvinces_Ports_Build(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() > 0) {
                this.getProvince(this.getProvinceInViewID(i)).drawProvincePort(oSB, nScale);
            }
        }
    }
    
    protected final void drawProvinces_Ports_Build_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i)) && this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() > 0) {
                this.getProvince(this.getProvinceInViewID(i)).drawProvincePort(oSB, nScale);
            }
        }
    }
    
    protected final void drawProvinces_Ports(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawProvincePort(oSB, nScale);
        }
    }
    
    protected final void drawProvincesArmy(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawArmy(oSB, nScale);
        }
        this.drawSeaProvinceArmy(oSB, nScale);
        this.drawMoveUnitsArmy(oSB, nScale);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvincesBuildings(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawBuildings(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvincesBuildings_FogOfWar(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i))) {
                this.getProvince(this.getProvinceInViewID(i)).drawBuildings(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawFlagAndCrown_Emperor(final SpriteBatch oSB, final float nScale) {
        try {
            if (this.getProvince(this.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
                oSB.setColor(Color.WHITE);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawFlagAndCrown_Emperor_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            if (CFG.getMetProvince(this.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getCapitalProvinceID()) && this.getProvince(this.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
                oSB.setColor(Color.WHITE);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawCapitalsArmy_FlagAndCrown(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawCapitalsArmy_FlagAndCrown_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
                }
            }
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawAllianceMode_FlagAndCrown(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getAllianceID() > 0) {
                this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawAllianceMode_FlagAndCrown_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getAllianceID() > 0 && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawIcnomeMapMode_FlagAndCrown(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && (this.getProvince(this.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvincesArmy_SetUpArmy(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmy_SetUpArmy_Sea(oSB, nScale);
        }
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawArmy_SetUpArmy(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawSeaProvinceArmy(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmy(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_TechnologyLevels(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.getProvince(this.getProvinceInViewID(i)).drawTechnologyLevels(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_TechnologyLevels_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i))) {
                this.getProvince(this.getProvinceInViewID(i)).drawTechnologyLevels(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_Potential(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawPotential(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_Danger(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getDangerLevel() != 0) {
                this.getProvince(this.getProvinceInViewID(i)).drawDanger(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_Happiness(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.getProvince(this.getProvinceInViewID(i)).drawHappiness(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_StartingMoney(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.getProvince(this.getProvinceInViewID(i)).drawStartingMoney(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_GrowthRate(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawGrowthRate(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_ArmyPosition(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawArmyPosition(oSB, nScale);
        }
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmyPositionSea(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_Army_PeaceTreaty(final SpriteBatch oSB, final float nScale) {
        try {
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).isToTake) {
                    this.drawProvinceArmyWithFlag_ProvinceValue(oSB, (int)((this.getProvince(this.getProvinceInViewID(i)).getCenterX() + this.getProvince(this.getProvinceInViewID(i)).getShiftX() + this.getProvince(this.getProvinceInViewID(i)).getTranslateProvincePosX()) * nScale), (int)((this.getProvince(this.getProvinceInViewID(i)).getCenterY() + this.getProvince(this.getProvinceInViewID(i)).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).iCivID, CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).iProvinceValue, this.getProvince(this.getProvinceInViewID(i)).getArmyWidth(0), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                }
                else if (CFG.game.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                    this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
                }
            }
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_Army_PeaceTreaty_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.getMetProvince(CFG.game.getProvinceInViewID(i))) {
                    if (CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).isToTake) {
                        this.drawProvinceArmyWithFlag_ProvinceValue(oSB, (int)((this.getProvince(this.getProvinceInViewID(i)).getCenterX() + this.getProvince(this.getProvinceInViewID(i)).getShiftX() + this.getProvince(this.getProvinceInViewID(i)).getTranslateProvincePosX()) * nScale), (int)((this.getProvince(this.getProvinceInViewID(i)).getCenterY() + this.getProvince(this.getProvinceInViewID(i)).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).iCivID, CFG.peaceTreatyData.drawProvinceOwners.get(this.getProvinceInViewID(i)).iProvinceValue, this.getProvince(this.getProvinceInViewID(i)).getArmyWidth(0), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    }
                    else if (CFG.game.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                        this.drawProvinceFlag_Capital(oSB, this.getProvinceInViewID(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {}
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_ArmyPosition_Capitals(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.getProvince(this.getProvinceInViewID(i)).drawArmyPosition(oSB, nScale);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_SeaArmyBoxes(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmy(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_SeaArmyBoxes_Edit(final SpriteBatch oSB, final float nScale) {
        this.getProvince(CFG.MANAGE_DIPLOMACY_ADD_NEW_PACT_CIV1).drawArmy(oSB, nScale);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_SeaProvincesLevels(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawArmySeaProvincesLevels(oSB, nScale);
        }
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmySeaProvincesLevels(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_OptimizationRegions(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getProvinceInViewID(i)).drawArmyOptimizationRegions(oSB, nScale);
        }
        for (int i = 0; i < CFG.NUM_OF_SEA_PROVINCES_IN_VIEW; ++i) {
            this.getProvince(this.getSeaProvinceInViewID(i)).drawArmyOptimizationRegions(oSB, nScale);
        }
        oSB.setColor(Color.WHITE);
    }

    protected final Point_XY updateSeaProvince_CenterArmyPostion(int i, float nScale) {
        Point_XY tempBegin = null;
        Point_XY tempEnd = null;

        for(int j = this.getProvince(i).getProvinceArmyBoxes().size() - 1; j >= 0; --j) {
            if (-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight() <= ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosY() && (float)(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) + (float)(CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2) / CFG.map.getMapScale().getCurrentScale() >= (float)((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosY()) {
                if ((float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= (float)((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX() && -CFG.map.getMapCoordinates().getPosX() <= ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX()) {
                    tempBegin = new Point_XY(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX(), ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosY());
                    tempEnd = new Point_XY(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX(), ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosY());
                    if (((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX() < -CFG.map.getMapCoordinates().getPosX()) {
                        tempBegin.setPosX(-CFG.map.getMapCoordinates().getPosX());
                    }

                    if (((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosY() < -CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) {
                        tempBegin.setPosY(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight());
                    }

                    if ((float)((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX() > (float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale()) {
                        tempEnd.setPosX((int)((float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale()));
                    }

                    if ((float)((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosY() > (float)(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) + (float)(CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2) / CFG.map.getMapScale().getCurrentScale()) {
                        tempEnd.setPosY((int)((float)(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) + (float)(CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2) / CFG.map.getMapScale().getCurrentScale()));
                    }

                    return new Point_XY((int)(((float)(tempBegin.getPosX() + tempEnd.getPosX()) / 2.0F + (float)this.getProvince(i).getTranslateProvincePosX()) * nScale), (int)(((float)(tempBegin.getPosY() + tempEnd.getPosY()) / 2.0F + (float)CFG.map.getMapCoordinates().getPosY()) * nScale));
                }

                if ((float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= (float)(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapBG().getWidth()) && -CFG.map.getMapCoordinates().getPosX() <= ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX() + CFG.map.getMapBG().getWidth()) {
                    tempBegin = new Point_XY(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosY());
                    tempEnd = new Point_XY(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX() + CFG.map.getMapBG().getWidth(), ((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosY());
                    if (((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosX() + CFG.map.getMapBG().getWidth() < -CFG.map.getMapCoordinates().getPosX()) {
                        tempBegin.setPosX(-CFG.map.getMapCoordinates().getPosX());
                    }

                    if (((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getStartPosY() < -CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) {
                        tempBegin.setPosY(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight());
                    }

                    if ((float)(((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosX() + CFG.map.getMapBG().getWidth()) > (float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale()) {
                        tempEnd.setPosX((int)((float)(-CFG.map.getMapCoordinates().getPosX()) + (float)CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale()));
                    }

                    if ((float)((Province_ArmyBox)this.getProvince(i).getProvinceArmyBoxes().get(j)).getEndPosY() > (float)(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) + (float)(CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2) / CFG.map.getMapScale().getCurrentScale()) {
                        tempEnd.setPosY((int)((float)(-CFG.map.getMapCoordinates().getPosY() + ImageManager.getImage(Images.top_left).getHeight()) + (float)(CFG.GAME_HEIGHT - ImageManager.getImage(Images.top_left).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2) / CFG.map.getMapScale().getCurrentScale()));
                    }

                    return new Point_XY((int)(((float)(tempBegin.getPosX() + tempEnd.getPosX()) / 2.0F + (float)CFG.map.getMapCoordinates().getPosX()) * nScale), (int)(((float)(tempBegin.getPosY() + tempEnd.getPosY()) / 2.0F + (float)CFG.map.getMapCoordinates().getPosY()) * nScale));
                }
            }
        }

        return new Point_XY((int)((float)(this.getProvince(i).getCenterX() + this.getProvince(i).getShiftX() + this.getProvince(i).getTranslateProvincePosX()) * nScale), (int)((float)(this.getProvince(i).getCenterY() + this.getProvince(i).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale));
    }
    
    protected final void drawProvince_TechnologyLevels(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f * 27.0f / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f / 100.0f;
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
        CFG.drawArmyText(oSB, "" + (int)(this.getCiv(this.getProvince(nProvinceID).getCivID()).getTechnologyLevel() * 100.0f) / 100.0f, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        oSB.setColor(Color.WHITE);
        tCenterX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvince_Happiness(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f * 27.0f / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f / 100.0f;
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
        CFG.drawArmyText(oSB, "" + this.getCiv(this.getProvince(nProvinceID).getCivID()).getHappiness() + "%", tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        oSB.setColor(Color.WHITE);
        tCenterX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvince_StartingMoney(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f * 27.0f / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f / 100.0f;
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
        CFG.drawArmyText(oSB, "" + ((this.getCiv(this.getProvince(nProvinceID).getCivID()).getMoney() == -999999L) ? CFG.game.getGameScenarios().getScenario_StartingMoney() : this.getCiv(this.getProvince(nProvinceID).getCivID()).getMoney()), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        oSB.setColor(Color.WHITE);
        tCenterX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvince_GrowthRate(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + (int)(this.getProvince(nProvinceID).getGrowthRate_Population() * 100.0f) + "%", tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvince_ArmyPosition(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nProvinceID, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvince_OptimizationRegions(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + CFG.game.getRegionID(nProvinceID), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvince_SeaProvincesLevels(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getLevelOfPort(), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvince_Potential(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f * 27.0f / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f / 100.0f;
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
        try {
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getPotential() + "-" + this.getProvince(nProvinceID).getPotentialRegion(), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getPotential(), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        }
        oSB.setColor(Color.WHITE);
        tCenterX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvince_Danger(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f * 27.0f / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / 18.0f / 100.0f;
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getDangerLevel() + " - " + this.getProvince(nProvinceID).getDangerLevel_WithArmy(), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
        oSB.setColor(Color.WHITE);
        tCenterX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX, tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinceID(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nProvinceID, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Fort(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Armoury(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Fort(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Fort_NoArmy(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Fort(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_Fort_NoArmy_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvince_Fort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Tower(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Armoury(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Armoury(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Tower(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_Tower_NoArmy(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Tower(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_NoArmy_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Armoury_Just(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_Tower_NoArmy_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvince_Tower_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_TowerFort(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tWidth = this.getProvince(nProvinceID).getArmyWidth(0) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
        this.drawProvince_Armoury(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_TowerFort(oSB, tCenterX - tWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        oSB.setColor(bgColor);
        this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(0), tCenterX - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, armyColor);
    }
    
    protected final void drawProvinceArmy_TowerFort_NoArmy(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_TowerFort_Just(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceArmy_TowerFort_NoArmy_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvince_TowerFort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
    }
    
    protected final void drawProvinceFlag(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)Math.ceil(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (int)(CFG.CIV_FLAG_HEIGHT * fFlagScale) - CFG.CIV_FLAG_HEIGHT + 1 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), fFlagScale);
        }
    }
    
    protected final void drawProvinceArmyWithFlag(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int j;
        for (int nArmy0 = j = ((this.getProvince(nProvinceID).getArmy(0) <= 0) ? 1 : 0); j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Fort(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_Fort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Tower(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            }
            else {
                this.drawProvince_Armoury(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_Tower_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_TowerFort(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_TowerFort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int j;
        for (int nArmy0 = j = ((this.getProvince(nProvinceID).getArmy(0) <= 0) ? 1 : 0); j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_TowerFort(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_TowerFort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.towerfort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Fort(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_Fort_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.fort_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            }
            else {
                this.drawProvince_Armoury(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Tower(oSB, tCenterX, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Active_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        int nArmy0;
        if (this.getProvince(nProvinceID).getArmy(0) > 0) {
            nArmy0 = 0;
            this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
        }
        else {
            nArmy0 = 1;
            if (CFG.game.getProvince(nProvinceID).getCivsSize() > 1) {
                this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
                this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(1) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0));
            }
            else {
                this.drawProvince_Armoury_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
                this.drawProvince_Tower_Just(oSB, tCenterX - (ImageManager.getImage(Images.armoury_ico).getWidth() + ImageManager.getImage(Images.tower_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT) / 2 + ImageManager.getImage(Images.armoury_ico).getWidth() + CFG.ARMY_BG_EXTRA_HEIGHT, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
            }
        }
        for (int j = nArmy0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tWidth + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - ImageManager.getImage(Images.flag_rect).getHeight() - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (j - nArmy0) - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1 - nArmy0) / 2) : 0), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceFlag_Capital(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_FlagCivID(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale, final int nCivID) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvinceFlag_Capital_End_FlagCivID(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale, nCivID);
    }
    
    protected final void drawProvinceFlag_Capital_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvinceFlag_Capital_Begin(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT);
        this.drawProvinceFlag_Capital_End(oSB, nProvinceID, bgColor, armyColor, nScale, tCenterX, tCenterY, tFlagWidth, fFlagScale);
    }
    
    protected final void drawProvinceFlag_Capital_Begin(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale, int tCenterX, int tCenterY, int tFlagWidth, float fFlagScale) {
        tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + tFlagWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
    }
    
    protected final void drawProvinceFlag_Capital_End(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale, final int tCenterX, int tCenterY, final int tFlagWidth, final float fFlagScale) {
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().draw(oSB, tCenterX - tFlagWidth / 2, tCenterY - this.getCiv(this.getProvince(nProvinceID).getCivID(0)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX - (int)Math.floor(tFlagWidth / 2.0f), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth / 2.0f) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX - (int)Math.floor(tFlagWidth / 2.0f), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth / 2.0f) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinceFlag_Capital_End_FlagCivID(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale, final int tCenterX, int tCenterY, final int tFlagWidth, final float fFlagScale, final int nCivID) {
        tCenterY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT;
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - tFlagWidth / 2, tCenterY - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(bgColor);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX - (int)Math.floor(tFlagWidth / 2.0f), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth / 2.0f) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX - (int)Math.floor(tFlagWidth / 2.0f), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
        ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCenterX + (int)Math.ceil(tFlagWidth / 2.0f) - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCenterY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
        oSB.setColor(Color.WHITE);
    }
    
    protected final int getDrawProvinceArmy_EndPosY(final int nProvinceID, final float nScale) {
        int nArmies = 0;
        for (int i = 0; i < this.getProvince(nProvinceID).getCivsSize(); ++i) {
            if (this.getProvince(nProvinceID).getArmy(i) > 0 || this.getProvince(nProvinceID).getIsCapital()) {
                ++nArmies;
            }
        }
        return (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale) - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * nArmies - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
    }
    
    protected final void drawProvinceArmyWithFlag_Capital(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_TowerFortArmoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (this.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor(bgColor);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor(bgColor);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_TowerFort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_TowerFort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_TowerFort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_Fort(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_Fort_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Fort(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_Tower(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Capital_Active_Tower_Armoury(final SpriteBatch oSB, final int nProvinceID, final Color bgColor, final Color armyColor, final Color bgColor2, final Color armyColor2, final float nScale) {
        final int tCenterX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tCenterY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagWidth = (int)((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        this.drawProvince_Armoury(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
        this.drawProvince_Tower(oSB, tCenterX - tFlagWidth - this.getProvince(nProvinceID).getArmyWidth(0) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
        if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
                this.drawProvince_Capital_Crown_HRE(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (this.getProvince(nProvinceID).getCivID() == this.getCiv(this.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            this.drawProvince_Capital_Crown(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown_Vassal(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(0) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        for (int j = 0; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2;
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            this.drawProvinceArmyBackground_Capital(oSB, -1 + tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), tWidth + 1, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_capital);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), (j == CFG.activeCivilizationArmyID) ? armyColor : armyColor2);
            oSB.setColor(Color.WHITE);
            final int tCX = tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH;
            final int tCY = tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0);
            this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().draw(oSB, tCX, tCY - this.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            oSB.setColor((j == CFG.activeCivilizationArmyID) ? bgColor : bgColor2);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight());
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY - ImageManager.getImage(Images.army_capital_frame).getHeight(), ImageManager.getImage(Images.army_capital_frame).getWidth(), CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight(), true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX, tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), false, true);
            ImageManager.getImage(Images.army_capital_frame).draw2(oSB, tCX + tFlagWidth - ImageManager.getImage(Images.army_capital_frame).getWidth(), tCY + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - ImageManager.getImage(Images.army_capital_frame).getHeight() * 2, ImageManager.getImage(Images.army_capital_frame).getWidth(), ImageManager.getImage(Images.army_capital_frame).getHeight(), true, true);
            oSB.setColor(Color.WHITE);
        }
    }
    
    private final void drawProvince_Fort(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.fort_ico).draw(oSB, nPosX - ImageManager.getImage(Images.fort_ico).getWidth() / 2, nPosY - ImageManager.getImage(Images.fort_ico).getHeight() * 4 / 5);
    }
    
    private final void drawProvince_Fort_Just(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.fort_ico).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.fort_ico).getHeight() / 2);
    }
    
    private final void drawProvince_Tower(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.tower_ico).draw(oSB, nPosX - ImageManager.getImage(Images.tower_ico).getWidth() * 4 / 5, nPosY - ImageManager.getImage(Images.tower_ico).getHeight() * 4 / 5);
    }
    
    private final void drawProvince_Tower_Just(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.tower_ico).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.tower_ico).getHeight() / 2);
    }
    
    private final void drawProvince_TowerFort(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.towerfort_ico).draw(oSB, nPosX - ImageManager.getImage(Images.towerfort_ico).getWidth() / 2, nPosY - ImageManager.getImage(Images.towerfort_ico).getHeight() * 4 / 5);
    }
    
    private final void drawProvince_TowerFort_Just(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.towerfort_ico).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.towerfort_ico).getHeight() / 2);
    }
    
    private final void drawProvince_Armoury(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.armoury_ico).draw(oSB, nPosX - ImageManager.getImage(Images.armoury_ico).getWidth() * 3 / 4, nPosY - ImageManager.getImage(Images.armoury_ico).getHeight() / 2);
    }
    
    private final void drawProvince_Armoury_Just(final SpriteBatch oSB, final int nPosX, final int nPosY) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.armoury_ico).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.armoury_ico).getHeight() / 2);
    }
    
    private final void drawProvince_Capital_Crown_HRE(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nIdelogyID) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.hre_crown).draw(oSB, nPosX - ImageManager.getImage(Images.hre_crown).getWidth() / 2, nPosY - ImageManager.getImage(Images.hre_crown).getHeight() * 4 / 5);
    }
    
    private final void drawProvince_Capital_Crown_HRE_Vassal(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nIdelogyID) {
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.hre_crown_x).draw(oSB, nPosX - ImageManager.getImage(Images.hre_crown_x).getWidth() / 2, nPosY - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().getHeight() * 4 / 5 - (ImageManager.getImage(Images.hre_crown_x).getHeight() - ImageManager.getImage(Images.hre_crown).getHeight()));
    }
    
    private final void drawProvince_Capital_Crown(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nIdelogyID) {
        oSB.setColor(Color.WHITE);
        CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().draw(oSB, nPosX - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().getWidth() / 2, nPosY - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().getHeight() * 4 / 5);
    }
    
    private final void drawProvince_Capital_Crown_Vassal(final SpriteBatch oSB, final int nPosX, final int nPosY, final int nIdelogyID) {
        oSB.setColor(Color.WHITE);
        CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownVassalImage().draw(oSB, nPosX - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownVassalImage().getWidth() / 2, nPosY - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().getHeight() * 4 / 5 - (CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownVassalImage().getHeight() - CFG.ideologiesManager.getIdeology(nIdelogyID).getiCrownImage().getHeight()));
    }
    
    protected final void drawProvinceArmy_Sea(final SpriteBatch oSB, final int nProvinceID, final Color armyColor, final float nScale, final int tCenterX, final int tCenterY) {
        for (int j = 1; j < this.getProvince(nProvinceID).getCivsSize(); ++j) {
            final int tWidth = this.getProvince(nProvinceID).getArmyWidth(j) + CFG.ARMY_BG_EXTRA_WIDTH * 2 + ImageManager.getImage(Images.army_sea).getWidth();
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.army_16_seabg).draw2(oSB, tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0) - ImageManager.getImage(Images.army_16_seabg).getHeight(), tWidth, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2);
            CFG.drawArmyText(oSB, "" + this.getProvince(nProvinceID).getArmy(j), tCenterX - this.getProvince(nProvinceID).getArmyWidth(j) / 2, tCenterY - CFG.ARMY_HEIGHT / 2 + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0), armyColor);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.95f));
            oSB.setShader(AoCGame.shaderAlpha);
            ImageManager.getImage(Images.army_sea).getTexture().bind(2);
            CFG.game.getCiv(this.getProvince(nProvinceID).getCivID(j)).getFlag().getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            ImageManager.getImage(Images.army_sea).draw(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(j) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, 1 + tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
            oSB.flush();
            ImageManager.getImage(Images.army_sea).getTexture().bind(2);
            ImageManager.getImage(Images.flag_rect).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            ImageManager.getImage(Images.army_sea).draw(oSB, tCenterX + this.getProvince(nProvinceID).getArmyWidth(j) / 2 + CFG.ARMY_BG_EXTRA_WIDTH, 1 + tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT + (CFG.ARMY_BG_EXTRA_HEIGHT + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * j - ((this.getProvince(nProvinceID).getCivsSize() > 1) ? ((CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) * (this.getProvince(nProvinceID).getCivsSize() - 1) / 2) : 0));
            oSB.setShader(AoCGame.defaultShader);
        }
    }
    
    protected final void drawCivilization_Flag(final SpriteBatch oSB, final int nProvinceID, final int nImageID, final float nScale) {
        final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagW = (int)Math.ceil(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        if (this.iActiveProvince == nProvinceID) {
            oSB.setColor(new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
        }
        else {
            oSB.setColor(new Color(0.039215688f, 0.039215688f, 0.039215688f, 1.0f));
        }
        this.drawProvinceArmyBackground(oSB, tPosX - CFG.ARMY_BG_EXTRA_WIDTH - tFlagW / 2, tPosY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagW, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, nImageID);
        oSB.setColor(Color.WHITE);
        this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tPosX - tFlagW / 2, tPosY - CFG.ARMY_HEIGHT / 2 + (int)(CFG.CIV_FLAG_HEIGHT * (CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f)) - CFG.CIV_FLAG_HEIGHT + 1, CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f);
    }
    
    protected final void drawProvinceArmyWithFlag(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth) {
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - nArmyWidth / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - nArmyWidth / 2, tCenterY - CFG.ARMY_HEIGHT / 2, CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
        oSB.setColor(Color.WHITE);
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - nArmyWidth / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - nArmyWidth / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
    }
    
    protected final void drawProvinceArmyWithFlag_Plunder(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth) {
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        final float tPlunderScale = CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_plunder).getHeight() / 100.0f;
        final int tPlunderWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_plunder).getHeight() * ImageManager.getImage(Images.diplo_plunder).getWidth() / 100.0f);
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
        oSB.setColor(Color.WHITE);
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.diplo_plunder).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 + nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.diplo_plunder).getHeight(), (int)(ImageManager.getImage(Images.diplo_plunder).getWidth() * tPlunderScale), CFG.ARMY_HEIGHT);
    }
    
    protected final void drawProvinceBuildings(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nProvinceID) {
        Game.lBuildingsImages.clear();
        if (CFG.game.getProvince(nProvinceID).getLevelOfFort() > 0) {
            Game.lBuildingsImages.add(Images.b_fort);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfWatchTower() > 0) {
            Game.lBuildingsImages.add(Images.b_tower);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfPort() > 0) {
            Game.lBuildingsImages.add(Images.b_port);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfLibrary() > 0) {
            Game.lBuildingsImages.add(Images.b_library);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfFarm() > 0) {
            Game.lBuildingsImages.add(Images.b_farm);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfWorkshop() > 0) {
            Game.lBuildingsImages.add(Images.b_workshop);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfArmoury() > 0) {
            Game.lBuildingsImages.add(Images.b_armoury);
        }
        if (CFG.game.getProvince(nProvinceID).getLevelOfSupply() > 0) {
            Game.lBuildingsImages.add(Images.b_supply);
        }
        if (Game.lBuildingsImages.size() > 0) {
            final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
            Game.iBuildingsWidth = 0;
            for (int i = Game.lBuildingsImages.size() - 1; i >= 0; --i) {
                Game.iBuildingsWidth += (int)(ImageManager.getImage(Game.lBuildingsImages.get(i)).getWidth() * CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Game.lBuildingsImages.get(i)).getHeight() / 100.0f) + CFG.PADDING;
            }
            Game.iBuildingsWidth -= CFG.PADDING;
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_DARK_BLUE.r, CFG.COLOR_GRADIENT_DARK_BLUE.g, CFG.COLOR_GRADIENT_DARK_BLUE.b, 0.8f));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH - CFG.PADDING * 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ImageManager.getImage(Images.line_32_off1).getHeight() - 1, Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + CFG.PADDING * 4, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 + 2);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH - CFG.PADDING * 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ImageManager.getImage(Images.line_32_off1).getHeight(), Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + CFG.PADDING * 4, 1);
            ImageManager.getImage(Images.line_32_off1).draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH - CFG.PADDING * 2, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT - ImageManager.getImage(Images.line_32_off1).getHeight() + CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2 - 1, Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + CFG.PADDING * 4, 1);
            oSB.setColor(Color.WHITE);
            int i = Game.lBuildingsImages.size() - 1;
            int tX = CFG.ARMY_BG_EXTRA_WIDTH;
            while (i >= 0) {
                ImageManager.getImage(Game.lBuildingsImages.get(i)).draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 + tX, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Game.lBuildingsImages.get(i)).getHeight(), (int)(ImageManager.getImage(Game.lBuildingsImages.get(i)).getWidth() * CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Game.lBuildingsImages.get(i)).getHeight() / 100.0f), CFG.ARMY_HEIGHT);
                tX += (int)(ImageManager.getImage(Game.lBuildingsImages.get(i)).getWidth() * CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Game.lBuildingsImages.get(i)).getHeight() / 100.0f) + CFG.PADDING;
                --i;
            }
            this.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 - tFlagWidth, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - (Game.iBuildingsWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2) / 2 - tFlagWidth, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        }
    }
    
    protected final void drawProvinceArmyWithFlag_Attack(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth) {
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        final float tPlunderScale = CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_rivals).getHeight() / 100.0f;
        final int tPlunderWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_rivals).getHeight() * ImageManager.getImage(Images.diplo_rivals).getWidth() / 100.0f);
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
        oSB.setColor(Color.WHITE);
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.diplo_rivals).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 + nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.diplo_rivals).getHeight(), (int)(ImageManager.getImage(Images.diplo_rivals).getWidth() * tPlunderScale), CFG.ARMY_HEIGHT);
    }
    
    protected final void drawProvinceArmyWithFlag_Migrate(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth) {
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        final float tPlunderScale = CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.population).getHeight() / 100.0f;
        final int tPlunderWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.population).getHeight() * ImageManager.getImage(Images.population).getWidth() / 100.0f);
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, CFG.COLOR_TEXT_POPULATION);
        oSB.setColor(Color.WHITE);
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.population).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 + nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.population).getHeight(), (int)(ImageManager.getImage(Images.population).getWidth() * tPlunderScale), CFG.ARMY_HEIGHT);
    }
    
    protected final void drawProvinceArmyWithFlag_Recruit(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth) {
        final int tFlagWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final float fFlagScale = CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f;
        final float tPlunderScale = CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_army).getHeight() / 100.0f;
        final int tPlunderWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.diplo_army).getHeight() * ImageManager.getImage(Images.diplo_army).getWidth() / 100.0f);
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - CFG.ARMY_BG_EXTRA_WIDTH - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tFlagWidth + CFG.ARMY_BG_EXTRA_WIDTH + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, CFG.COLOR_TEXT_MODIFIER_POSITIVE);
        oSB.setColor(Color.WHITE);
        this.getCiv(nCivID).getFlag().draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - this.getCiv(nCivID).getFlag().getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - tFlagWidth - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagWidth, CFG.ARMY_HEIGHT);
        ImageManager.getImage(Images.diplo_army).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 + nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.diplo_army).getHeight(), (int)(ImageManager.getImage(Images.diplo_army).getWidth() * tPlunderScale), CFG.ARMY_HEIGHT);
    }
    
    protected final void drawProvinceArmyWithFlag_ProvinceValue(final SpriteBatch oSB, final int tCenterX, final int tCenterY, final int nCivID, final int nArmy, final int nArmyWidth, final Color nColor) {
        final float tPlunderScale = CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.victoryPoints).getHeight() / 100.0f;
        final int tPlunderWidth = (int)(CFG.ARMY_HEIGHT * 100.0f / ImageManager.getImage(Images.victoryPoints).getHeight() * ImageManager.getImage(Images.victoryPoints).getWidth() / 100.0f);
        oSB.setColor(CFG.COLOR_ARMY_BG);
        this.drawProvinceArmyBackground(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 - CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT, nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH * 2 + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH, CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2, Images.army_bg);
        CFG.drawArmyText(oSB, "" + nArmy, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2, tCenterY - CFG.ARMY_HEIGHT / 2, nColor);
        oSB.setColor(Color.WHITE);
        ImageManager.getImage(Images.victoryPoints).draw(oSB, tCenterX - (nArmyWidth + tPlunderWidth + CFG.ARMY_BG_EXTRA_WIDTH) / 2 + nArmyWidth + CFG.ARMY_BG_EXTRA_WIDTH, tCenterY - CFG.ARMY_HEIGHT / 2 - ImageManager.getImage(Images.victoryPoints).getHeight(), (int)(ImageManager.getImage(Images.victoryPoints).getWidth() * tPlunderScale), CFG.ARMY_HEIGHT);
    }
    
    protected final void drawAllProvinces_Name_Flag(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getCivID() != 0 && !this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                this.drawCivilization_Flag(oSB, this.getProvinceInViewID(i), nScale);
            }
        }
        this.drawAllCivilizations_Name_Flag_InCapitals(oSB, nScale);
    }
    
    protected final void drawCores_Flags(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getCivID() != 0) {
                try {
                    if (CFG.game.getProvince(this.getProvinceInViewID(i)).getCore().getCivsSize() > 1) {
                        this.drawCivilization_Flag_Cores(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
                catch (final NullPointerException ex) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex);
                    }
                }
            }
        }
    }
    
    protected final void drawAllCivilizations_Name_Flag_InCapitals(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawCivilization_Name_Flag(oSB, this.getCiv(i).getCapitalProvinceID(), nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Name_Flag_InCapitals_Crowns(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawCivilization_Name_Flag_Crowns(oSB, this.getCiv(i).getCapitalProvinceID(), nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Name_Flag_InCapitals_Vassals(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawCivilization_Name_Flag_Vassals(oSB, this.getCiv(i).getCapitalProvinceID(), nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawCivilization_Flag(oSB, this.getCiv(i).getCapitalProvinceID(), nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(i).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown_Sea(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(i).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        this.drawSeaProvinceArmy(oSB, nScale);
        this.drawMoveUnitsArmy(oSB, nScale);
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown_Timeline(final SpriteBatch oSB, final float nScale) {
        for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
            if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince()) {
                this.drawProvinceFlag_Capital_FlagCivID(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale, i + 1);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown_Timeline_FogOfWar(final SpriteBatch oSB, final float nScale) {
        for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
            if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince() && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i))) {
                this.drawProvinceFlag_Capital_FlagCivID(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale, i + 1);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getCiv(i).getCapitalProvinceID()) && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(i).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
    }
    
    protected final void drawAllCivilizations_Flag_InCapitals_WithCrown_FogOfWarDiscovery_Sea(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getCiv(i).getCapitalProvinceID()) && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getCivID() == i && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                this.drawProvinceFlag_Capital(oSB, this.getCiv(i).getCapitalProvinceID(), CFG.COLOR_ARMY_BG, CFG.COLOR_ARMY_TEXT, nScale);
            }
        }
        this.drawSeaProvinceArmy(oSB, nScale);
        this.drawMoveUnitsArmy(oSB, nScale);
    }
    
    protected final void drawAllCivilizations_Name_Flag_InCapitals_AvailableCivs(final SpriteBatch oSB, final float nScale) {
        for (int i = 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getCapitalProvinceID() >= 0 && this.getProvince(this.getCiv(i).getCapitalProvinceID()).getDrawProvince()) {
                if (this.getCiv(i).getIsAvailable()) {
                    this.drawCivilization_Name_Flag(oSB, this.getCiv(i).getCapitalProvinceID(), nScale);
                }
                else {
                    this.drawCivilization_Name_Flag(oSB, this.getCiv(i).getCapitalProvinceID(), nScale, 0.65f);
                }
            }
        }
    }
    
    protected final void drawCivilization_Flag(final SpriteBatch oSB, final int nProvinceID, final float nScale) {
        final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagW = (int)Math.ceil(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        if (this.iActiveProvince == nProvinceID) {
            oSB.setColor(new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
        }
        else {
            oSB.setColor(new Color(0.039215688f, 0.039215688f, 0.039215688f, 1.0f));
        }
        this.drawCivFlagBG(oSB, tPosX - CFG.CIV_NAME_BG_EXTRA_WIDTH_ARMY - tFlagW / 2, tPosY - CFG.ARMY_HEIGHT / 2 - CFG.CIV_NAME_BG_EXTRA_HEIGHT_ARMY, CFG.CIV_NAME_BG_EXTRA_WIDTH_ARMY * 2 + tFlagW, CFG.ARMY_HEIGHT + CFG.CIV_NAME_BG_EXTRA_HEIGHT_ARMY * 2);
        oSB.setColor(Color.WHITE);
        this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tPosX - tFlagW / 2, tPosY - CFG.ARMY_HEIGHT / 2 - this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().getHeight(), tFlagW, CFG.ARMY_HEIGHT);
    }
    
    protected final void drawCivilization_Flag_Cores(final SpriteBatch oSB, final int nProvinceID, final float nScale) {
        try {
            final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
            final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
            final int tFlagW = (int)Math.ceil(CFG.ARMY_HEIGHT * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
            if (this.iActiveProvince == nProvinceID) {
                oSB.setColor(new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
            }
            else {
                oSB.setColor(new Color(0.039215688f, 0.039215688f, 0.039215688f, 1.0f));
            }
            this.drawCivFlagBG(oSB, tPosX - CFG.CIV_NAME_BG_EXTRA_WIDTH_ARMY - (tFlagW * CFG.game.getProvince(nProvinceID).getCore().getCivsSize() + CFG.PADDING * (CFG.game.getProvince(nProvinceID).getCore().getCivsSize() - 1)) / 2, tPosY - CFG.ARMY_HEIGHT / 2 - CFG.CIV_NAME_BG_EXTRA_HEIGHT_ARMY, CFG.CIV_NAME_BG_EXTRA_WIDTH_ARMY * 2 + (tFlagW * CFG.game.getProvince(nProvinceID).getCore().getCivsSize() + CFG.PADDING * (CFG.game.getProvince(nProvinceID).getCore().getCivsSize() - 1)), CFG.ARMY_HEIGHT + CFG.CIV_NAME_BG_EXTRA_HEIGHT_ARMY * 2);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCore().getCivsSize(); ++i) {
                this.getCiv(CFG.game.getProvince(nProvinceID).getCore().getCivID(i)).getFlag().draw(oSB, tPosX - (tFlagW * CFG.game.getProvince(nProvinceID).getCore().getCivsSize() + CFG.PADDING * (CFG.game.getProvince(nProvinceID).getCore().getCivsSize() - 1)) / 2 + tFlagW * i + CFG.PADDING * i, tPosY - CFG.ARMY_HEIGHT / 2 - this.getCiv(CFG.game.getProvince(nProvinceID).getCore().getCivID(i)).getFlag().getHeight(), tFlagW, CFG.ARMY_HEIGHT);
            }
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawCivilization_Name_Flag(final SpriteBatch oSB, final int nProvinceID, final float nScale) {
        this.drawCivilization_Name_Flag(oSB, nProvinceID, nScale, 1.0f);
    }
    
    protected final void drawCivilization_Name_Flag_Crowns(final SpriteBatch oSB, final int nProvinceID, final float nScale) {
        this.drawCivilization_Name_Flag_Crowns(oSB, nProvinceID, nScale, 1.0f);
    }
    
    protected final void drawCivilization_Name_Flag_Vassals(final SpriteBatch oSB, final int nProvinceID, final float nScale) {
        this.drawCivilization_Name_Flag_Vassals(oSB, nProvinceID, nScale, 1.0f);
    }
    
    protected final void drawCivilization_Name_Flag(final SpriteBatch oSB, final int nProvinceID, final float nScale, final float fAlpha) {
        final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagW = (int)(this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final int tFlagH = (int)(CFG.CIV_FLAG_HEIGHT * this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f);
        if (this.iActiveProvince == nProvinceID) {
            oSB.setColor(new Color(0.99215686f, 0.9882353f, 0.9843137f, fAlpha));
        }
        else {
            oSB.setColor(new Color(0.015686275f, 0.015686275f, 0.015686275f, fAlpha));
        }
        this.drawCivNameBG(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - CFG.CIV_COLOR_WIDTH - tFlagW / 2 - CFG.CIV_NAME_BG_EXTRA_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - CFG.CIV_NAME_BG_EXTRA_HEIGHT, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() + CFG.CIV_NAME_BG_EXTRA_WIDTH * 2 + tFlagW + CFG.CIV_COLOR_WIDTH, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() + CFG.CIV_NAME_BG_EXTRA_HEIGHT * 2);
        CFG.drawText(oSB, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivName(), tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, (this.iActiveProvince == nProvinceID) ? new Color(0.12156863f, 0.12156863f, 0.12156863f, 1.0f) : new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, fAlpha));
        this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().getHeight(), tFlagW, tFlagH);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagW, tFlagH);
    }
    
    protected final void drawCivilization_Name_Flag_Crowns(final SpriteBatch oSB, final int nProvinceID, final float nScale, final float fAlpha) {
        final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagW = (int)(this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final int tFlagH = (int)(CFG.CIV_FLAG_HEIGHT * this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f);
        if (this.iActiveProvince == nProvinceID) {
            oSB.setColor(new Color(0.99215686f, 0.9882353f, 0.9843137f, fAlpha));
        }
        else {
            oSB.setColor(new Color(0.015686275f, 0.015686275f, 0.015686275f, fAlpha));
        }
        this.drawCivNameBG(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - CFG.CIV_COLOR_WIDTH - tFlagW / 2 - CFG.CIV_NAME_BG_EXTRA_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - CFG.CIV_NAME_BG_EXTRA_HEIGHT, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() + CFG.CIV_NAME_BG_EXTRA_WIDTH * 2 + tFlagW + CFG.CIV_COLOR_WIDTH, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() + CFG.CIV_NAME_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, fAlpha));
        if (CFG.game.getProvince(nProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_Vassal(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        else if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
            this.drawProvince_Capital_Crown_HRE(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        else {
            this.drawProvince_Capital_Crown(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
        }
        CFG.drawText(oSB, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivName(), tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, (this.iActiveProvince == nProvinceID) ? new Color(0.12156863f, 0.12156863f, 0.12156863f, 1.0f) : new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
        this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().getHeight(), tFlagW, tFlagH);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagW, tFlagH);
    }
    
    protected final void drawCivilization_Name_Flag_Vassals(final SpriteBatch oSB, final int nProvinceID, final float nScale, final float fAlpha) {
        final int tPosX = (int)((this.getProvince(nProvinceID).getCenterX() + this.getProvince(nProvinceID).getShiftX() + this.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
        final int tPosY = (int)((this.getProvince(nProvinceID).getCenterY() + this.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY()) * nScale);
        final int tFlagW = (int)(this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT * CFG.CIV_FLAG_WIDTH / 100.0f);
        final int tFlagH = (int)(CFG.CIV_FLAG_HEIGHT * this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() * 100.0f / CFG.CIV_FLAG_HEIGHT / 100.0f);
        if (this.iActiveProvince == nProvinceID) {
            oSB.setColor(new Color(0.99215686f, 0.9882353f, 0.9843137f, fAlpha));
        }
        else {
            oSB.setColor(new Color(0.015686275f, 0.015686275f, 0.015686275f, fAlpha));
        }
        this.drawCivNameBG(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - CFG.CIV_COLOR_WIDTH - tFlagW / 2 - CFG.CIV_NAME_BG_EXTRA_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - CFG.CIV_NAME_BG_EXTRA_HEIGHT, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() + CFG.CIV_NAME_BG_EXTRA_WIDTH * 2 + tFlagW + CFG.CIV_COLOR_WIDTH, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() + CFG.CIV_NAME_BG_EXTRA_HEIGHT * 2);
        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, fAlpha));
        if (CFG.game.getProvince(nProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID()) {
            if (CFG.game.getCiv(this.getProvince(nProvinceID).getCivID()).getIsPartOfHolyRomanEmpire()) {
                this.drawProvince_Capital_Crown_HRE_Vassal(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
            else {
                this.drawProvince_Capital_Crown_Vassal(oSB, tPosX + this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, this.getCiv(this.getProvince(nProvinceID).getCivID()).getIdeologyID());
            }
        }
        CFG.drawText(oSB, this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivName(), tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 + tFlagW / 2, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2, (this.iActiveProvince == nProvinceID) ? new Color(0.12156863f, 0.12156863f, 0.12156863f, 1.0f) : new Color(0.9843137f, 0.9843137f, 0.9843137f, 1.0f));
        this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - this.getCiv(this.getProvince(nProvinceID).getCivID()).getFlag().getHeight(), tFlagW, tFlagH);
        ImageManager.getImage(Images.flag_rect).draw(oSB, tPosX - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameWidth() / 2 - tFlagW / 2 - CFG.CIV_COLOR_WIDTH, tPosY - this.getCiv(this.getProvince(nProvinceID).getCivID()).getCivNameHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight(), tFlagW, tFlagH);
    }
    
    protected final void drawWonders(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getWonderSize(); ++j) {
                this.getProvince(this.getProvinceInViewID(i)).getWonder(j).draw(oSB, this.getProvinceInViewID(i), nScale);
            }
        }
    }
    
    protected final void drawWonders_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (CFG.getMetProvince(this.getProvinceInViewID(i))) {
                for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getWonderSize(); ++j) {
                    this.getProvince(this.getProvinceInViewID(i)).getWonder(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                }
            }
        }
    }
    
    protected final void drawMountains(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                for (int j = 0, jSize = this.getProvince(this.getProvinceInViewID(i)).getMountainsSize(); j < jSize; ++j) {
                    this.getProvince(this.getProvinceInViewID(i)).getMountain(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawMountains_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0, jSize = this.getProvince(this.getProvinceInViewID(i)).getMountainsSize(); j < jSize; ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getMountain(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_All(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                    this.getProvince(this.getProvinceInViewID(i)).getCity(j).drawInLine(oSB, this.getProvinceInViewID(i), nScale);
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_All_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).drawInLine(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities(final SpriteBatch oSB, final float nScale) {
        try {
            oSB.setColor(Color.WHITE);
            CFG.game.drawWonders_FogOfWarDiscovery(oSB, nScale);
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Images(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        try {
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).drawCityImage_Level(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
    }
    
    protected final void drawCities_ActiveProvince_Just(final SpriteBatch oSB, final float nScale) {
        CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
        oSB.setColor(Color.WHITE);
        try {
            final long tempTime = System.currentTimeMillis();
            if (this.lTIME_ACTIVE_CITIES > tempTime - 525L) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++j) {
                        this.getProvince(CFG.game.getActiveProvinceID()).getCity(j).draw(oSB, CFG.game.getActiveProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                    }
                }
                CFG.setRender_3(true);
            }
            else if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities()) {
                for (int j = 0; j < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++j) {
                    this.getProvince(CFG.game.getActiveProvinceID()).getCity(j).draw(oSB, CFG.game.getActiveProvinceID(), nScale);
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final IllegalArgumentException ex2) {}
        oSB.setColor(Color.WHITE);
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_ActiveProvince_Just_OnlyCapitalMode(final SpriteBatch oSB, final float nScale) {
        CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
        oSB.setColor(Color.WHITE);
        try {
            final long tempTime = System.currentTimeMillis();
            if (this.lTIME_ACTIVE_CITIES > tempTime - 525L) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()) {
                    for (int j = 0; j < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++j) {
                        this.getProvince(CFG.game.getActiveProvinceID()).getCity(j).draw(oSB, CFG.game.getActiveProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                    }
                }
                CFG.setRender_3(true);
            }
            else if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()) {
                for (int j = 0; j < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++j) {
                    this.getProvince(CFG.game.getActiveProvinceID()).getCity(j).draw(oSB, CFG.game.getActiveProvinceID(), nScale);
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        catch (final IllegalArgumentException ex2) {}
        oSB.setColor(Color.WHITE);
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_ActiveProvince(final SpriteBatch oSB, final float nScale) {
        CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
        oSB.setColor(Color.WHITE);
        try {
            final long tempTime = System.currentTimeMillis();
            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.TURN_ACTIONS && CFG.gameAction.getCurrentMoveunits() != null) {
                try {
                    for (int i = 0; i < CFG.gameAction.getCurrentMoveunits().getMoveUnitsSize(); ++i) {
                        if (this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCitiesSize(); ++j) {
                                this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCity(j).draw(oSB, CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                            }
                        }
                    }
                }
                catch (final IndexOutOfBoundsException | NullPointerException ex) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex);
                    }
                } catch (final IllegalArgumentException ex4) {}
            }
            if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                if (this.lTIME_ACTIVE_CITIES > tempTime - 525L) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                    if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && this.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && (!this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities() || (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()))) {
                        for (int k = 0; k < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++k) {
                            this.getProvince(CFG.game.getActiveProvinceID()).getCity(k).draw(oSB, CFG.game.getActiveProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                        }
                    }
                    CFG.setRender_3(true);
                }
                else if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && this.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && (!this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities() || (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()))) {
                    for (int k = 0; k < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++k) {
                        this.getProvince(CFG.game.getActiveProvinceID()).getCity(k).draw(oSB, CFG.game.getActiveProvinceID(), nScale);
                    }
                }
            }
            if (this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION) {
                if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 350L) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f - ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                    for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a - CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                            }
                        }
                    }
                    CFG.setRender_3(true);
                }
            }
            else if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 750L) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 750.0f)));
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 750.0f)));
                        }
                    }
                }
                CFG.setRender_3(true);
            }
            else if (CFG.chosenProvinceID >= 0) {
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, (this.lHighlightedProvinces.get(i) == CFG.chosenProvinceID) ? CFG.COLOR_CITY_NAME : new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale);
                        }
                    }
                }
            }
            try {
                if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 350L) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                    for (int i = 0; i < this.currentRegroupArmy.getRouteSize(); ++i) {
                        if (this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawProvince() && !this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(this.currentRegroupArmy.getRoute(i)).getCitiesSize(); ++j) {
                                this.getProvince(this.currentRegroupArmy.getRoute(i)).getCity(j).draw(oSB, this.currentRegroupArmy.getRoute(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                            }
                        }
                    }
                    CFG.setRender_3(true);
                }
                else {
                    for (int i = 0; i < this.currentRegroupArmy.getRouteSize(); ++i) {
                        if (this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawProvince() && !this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(this.currentRegroupArmy.getRoute(i)).getCitiesSize(); ++j) {
                                this.getProvince(this.currentRegroupArmy.getRoute(i)).getCity(j).draw(oSB, this.currentRegroupArmy.getRoute(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                            }
                        }
                    }
                }
            }
            catch (final NullPointerException | IllegalArgumentException ex5) {}
            oSB.setColor(Color.WHITE);
            if (CFG.SPECTATOR_MODE) {
                for (int o = 1; o < CFG.game.getCivsSize(); ++o) {
                    if (CFG.game.getCiv(o).getNumOfProvinces() > 0) {
                        for (int l = 0; l < CFG.game.getCiv(o).getMoveUnitsSize(); ++l) {
                            if (this.getProvince(CFG.game.getCiv(o).getMoveUnits(l).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(o).getMoveUnits(l).getToProvinceID()).getDrawCities()) {
                                for (int m = 0; m < this.getProvince(CFG.game.getCiv(o).getMoveUnits(l).getToProvinceID()).getCitiesSize(); ++m) {
                                    this.getProvince(CFG.game.getCiv(o).getMoveUnits(l).getToProvinceID()).getCity(m).draw(oSB, CFG.game.getCiv(o).getMoveUnits(l).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                                }
                            }
                        }
                        for (int l = 0; l < CFG.game.getCiv(o).getRegroupArmySize(); ++l) {
                            if (this.getProvince(CFG.game.getCiv(o).getRegroupArmy(l).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(o).getRegroupArmy(l).getToProvinceID()).getDrawCities()) {
                                for (int m = 0; m < this.getProvince(CFG.game.getCiv(o).getRegroupArmy(l).getToProvinceID()).getCitiesSize(); ++m) {
                                    this.getProvince(CFG.game.getCiv(o).getRegroupArmy(l).getToProvinceID()).getCity(m).draw(oSB, CFG.game.getCiv(o).getRegroupArmy(l).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                                }
                            }
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++i) {
                    if (this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCitiesSize(); ++j) {
                            this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCity(j).draw(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                        }
                    }
                }
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrateSize(); ++i) {
                    if (this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID()).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID()).getCitiesSize(); ++j) {
                            this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID()).getCity(j).draw(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                        }
                    }
                }
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmySize(); ++i) {
                    if (this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getCitiesSize(); ++j) {
                            this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getCity(j).draw(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                        }
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        catch (final IllegalArgumentException ex7) {}
        oSB.setColor(Color.WHITE);
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_ActiveProvince_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
        oSB.setColor(Color.WHITE);
        try {
            final long tempTime = System.currentTimeMillis();
            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.TURN_ACTIONS && CFG.gameAction.getCurrentMoveunits() != null) {
                try {
                    for (int i = 0; i < CFG.gameAction.getCurrentMoveunits().getMoveUnitsSize(); ++i) {
                        if (this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCitiesSize(); ++j) {
                                this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCity(j).draw(oSB, CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                            }
                        }
                    }
                }
                catch (final IndexOutOfBoundsException | NullPointerException ex) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex);
                    }
                } catch (final IllegalArgumentException ex4) {}
            }
            if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID())) {
                if (this.lTIME_ACTIVE_CITIES > tempTime - 525L) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                    if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && this.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && (!this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities() || (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()))) {
                        for (int k = 0; k < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++k) {
                            this.getProvince(CFG.game.getActiveProvinceID()).getCity(k).draw(oSB, CFG.game.getActiveProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_ACTIVE_CITIES) / 525.0f)));
                        }
                    }
                    CFG.setRender_3(true);
                }
                else if (this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && this.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && (!this.getProvince(CFG.game.getActiveProvinceID()).getDrawCities() || (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE && !this.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()))) {
                    for (int k = 0; k < this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize(); ++k) {
                        this.getProvince(CFG.game.getActiveProvinceID()).getCity(k).draw(oSB, CFG.game.getActiveProvinceID(), nScale);
                    }
                }
            }
            if (this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION) {
                if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 350L) {
                    oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f - ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                    for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                            for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a - CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                            }
                        }
                    }
                    CFG.setRender_3(true);
                }
            }
            else if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 750L) {
                oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 750.0f)));
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 750.0f)));
                        }
                    }
                }
                CFG.setRender_3(true);
            }
            else if (CFG.chosenProvinceID >= 0) {
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale, (this.lHighlightedProvinces.get(i) == CFG.chosenProvinceID) ? CFG.COLOR_CITY_NAME : new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getDrawProvince() && !this.getProvince(this.lHighlightedProvinces.get(i)).getDrawCities()) {
                        for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getCitiesSize(); ++j) {
                            this.getProvince(this.lHighlightedProvinces.get(i)).getCity(j).draw(oSB, this.lHighlightedProvinces.get(i), nScale);
                        }
                    }
                }
            }
            try {
                if (this.currentRegroupArmy != null) {
                    if (this.lTIME_HIGHLIGHTED_CITIES > tempTime - 350L) {
                        oSB.setColor(new Color(1.0f, 1.0f, 1.0f, ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                        for (int i = 0; i < this.currentRegroupArmy.getRouteSize(); ++i) {
                            if (this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.currentRegroupArmy.getRoute(i)) && this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawProvince() && !this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawCities()) {
                                for (int j = 0; j < this.getProvince(this.currentRegroupArmy.getRoute(i)).getCitiesSize(); ++j) {
                                    this.getProvince(this.currentRegroupArmy.getRoute(i)).getCity(j).draw(oSB, this.currentRegroupArmy.getRoute(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f * ((tempTime - this.lTIME_HIGHLIGHTED_CITIES) / 350.0f)));
                                }
                            }
                        }
                        CFG.setRender_3(true);
                    }
                    else {
                        for (int i = 0; i < this.currentRegroupArmy.getRouteSize(); ++i) {
                            if (this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.currentRegroupArmy.getRoute(i)) && this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawProvince() && !this.getProvince(this.currentRegroupArmy.getRoute(i)).getDrawCities()) {
                                for (int j = 0; j < this.getProvince(this.currentRegroupArmy.getRoute(i)).getCitiesSize(); ++j) {
                                    this.getProvince(this.currentRegroupArmy.getRoute(i)).getCity(j).draw(oSB, this.currentRegroupArmy.getRoute(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                                }
                            }
                        }
                    }
                }
            }
            catch (final NullPointerException e) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(e);
                }
            }
            catch (final IllegalArgumentException ex5) {}
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++i) {
                if (this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()) && this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCitiesSize(); ++j) {
                        this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCity(j).draw(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                    }
                }
            }
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmySize(); ++i) {
                if (this.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()) && this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getDrawProvince() && !this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getCitiesSize(); ++j) {
                        this.getProvince(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID()).getCity(j).draw(oSB, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID(), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * 0.4f));
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        catch (final IllegalArgumentException ex6) {}
        oSB.setColor(Color.WHITE);
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            oSB.setColor(Color.WHITE);
            CFG.game.drawWonders_FogOfWarDiscovery(oSB, nScale);
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getDrawCities()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (CFG.getMetProvince(this.getProvinceInViewID(i))) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_AlliancesMode(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getAllianceID() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_AlliancesMode_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getAllianceID() > 0 && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_Imperial(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getIsPartOfHolyRomanEmpire()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_StartTheGame(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.25f + 0.75f * (CFG.startTheGameData.getCapitalsAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * (CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_Imperial_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getIsPartOfHolyRomanEmpire() && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_StartTheGame_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(new Color(1.0f, 1.0f, 1.0f, 0.25f + 0.75f * (CFG.startTheGameData.getCapitalsAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, new Color(CFG.COLOR_CITY_NAME.r, CFG.COLOR_CITY_NAME.g, CFG.COLOR_CITY_NAME.b, CFG.COLOR_CITY_NAME.a * (CFG.startTheGameData.getProvincesAlpha() / (float)CFG.settingsManager.PROVINCE_ALPHA)));
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_IncomeMapMode(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || this.getCiv(this.getProvince(this.getProvinceInViewID(i)).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_Player(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getCivID() == this.getPlayer(CFG.PLAYER_TURNID).getCivID() && this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyFormableCivCapital(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            if (CFG.formableCivs_GameData.getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.formableCivs_GameData.getCapitalProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.formableCivs_GameData.getCapitalProvinceID()).getSeaProvince() && this.getProvince(CFG.formableCivs_GameData.getCapitalProvinceID()).getCitiesSize() > 0) {
                this.getProvince(CFG.formableCivs_GameData.getCapitalProvinceID()).getCity(0).draw(oSB, CFG.formableCivs_GameData.getCapitalProvinceID(), nScale, Images.city);
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Timeline(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            boolean isActiveProvinceCapital = false;
            if (CFG.game.getActiveProvinceID() < 0 || !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                isActiveProvinceCapital = true;
            }
            for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
                if (CFG.game.getActiveProvinceID() == CFG.timelapseManager.timelineOwners_Capitals.get(i)) {
                    isActiveProvinceCapital = true;
                }
                if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince() && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCitiesSize() > 0) {
                    this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCity(0).draw(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), nScale, Images.city);
                }
            }
            if (!isActiveProvinceCapital && CFG.game.getActiveProvinceID() >= 0 && this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize() > 0) {
                this.getProvince(CFG.game.getActiveProvinceID()).getCity(0).draw(oSB, CFG.game.getActiveProvinceID(), nScale, Images.city3);
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Timeline_FogOfWar(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            boolean isActiveProvinceCapital = false;
            if (CFG.game.getActiveProvinceID() < 0 || !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() || CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                isActiveProvinceCapital = true;
            }
            for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
                if (CFG.game.getActiveProvinceID() == CFG.timelapseManager.timelineOwners_Capitals.get(i)) {
                    isActiveProvinceCapital = true;
                }
                if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)) && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince() && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCitiesSize() > 0) {
                    this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCity(0).draw(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), nScale, Images.city);
                }
            }
            if (!isActiveProvinceCapital && CFG.game.getActiveProvinceID() >= 0 && this.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince() && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()) && this.getProvince(CFG.game.getActiveProvinceID()).getCitiesSize() > 0) {
                this.getProvince(CFG.game.getActiveProvinceID()).getCity(0).draw(oSB, CFG.game.getActiveProvinceID(), nScale, Images.city3);
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Timeline_FogOfWar_OnlyCapitalsImages(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
                if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)) && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince() && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCitiesSize() > 0) {
                    this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCity(0).drawCityImage_Level(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), nScale);
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Timeline_OnlyCapitalsImages(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = CFG.timelapseManager.timelineOwners_Capitals.size() - 1; i >= 0; --i) {
                if (CFG.timelapseManager.timelineOwners_Capitals.get(i) >= 0 && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getDrawProvince() && this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCitiesSize() > 0) {
                    this.getProvince(CFG.timelapseManager.timelineOwners_Capitals.get(i)).getCity(0).drawCityImage_Level(oSB, CFG.timelapseManager.timelineOwners_Capitals.get(i), nScale);
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_OnlyCapitals_Images(final SpriteBatch oSB, final float nScale) {
        try {
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital()) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).drawCityImage_Level(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
    }
    
    protected final void drawCities_OnlyCapitals_Images_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getIsCapital() && CFG.getMetProvince(this.getProvinceInViewID(i))) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        if (this.getProvince(this.getProvinceInViewID(i)).getCity(j).getCityLevel() == Images.city) {
                            this.getProvince(this.getProvinceInViewID(i)).getCity(j).drawCityImage_Level(oSB, this.getProvinceInViewID(i), nScale);
                            break;
                        }
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
    }
    
    protected final void drawCities_PortCities(final SpriteBatch oSB, final float nScale) {
        CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
        oSB.setColor(Color.WHITE);
        for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
            if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() >= 0) {
                for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                    this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                }
            }
        }
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_PortCities_L0(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() == 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_PortCities_L1(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_PortCities_L1_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i)) && this.getProvince(this.getProvinceInViewID(i)).getLevelOfPort() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Fort(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfFort() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_Fort_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i)) && this.getProvince(this.getProvinceInViewID(i)).getLevelOfFort() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_WatchTower(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (this.getProvince(this.getProvinceInViewID(i)).getLevelOfWatchTower() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawCities_WatchTower_FogOfWarDiscovery(final SpriteBatch oSB, final float nScale) {
        try {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            for (int i = 0; i < CFG.NUM_OF_PROVINCES_IN_VIEW; ++i) {
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.getProvinceInViewID(i)) && this.getProvince(this.getProvinceInViewID(i)).getLevelOfWatchTower() > 0) {
                    for (int j = 0; j < this.getProvince(this.getProvinceInViewID(i)).getCitiesSize(); ++j) {
                        this.getProvince(this.getProvinceInViewID(i)).getCity(j).draw(oSB, this.getProvinceInViewID(i), nScale, Color.WHITE);
                    }
                }
            }
        }
        catch (final IllegalArgumentException ex) {}
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final void drawEditorCity(final SpriteBatch oSB, final float nScale) {
        if (CFG.editorCity.getPosX() > 0) {
            CFG.fontMain.getData().setScale(CFG.settingsManager.CITIES_FONT_SCALE);
            oSB.setColor(Color.WHITE);
            CFG.editorCity.draw(oSB, CFG.game.getActiveProvinceID(), nScale);
            CFG.fontMain.getData().setScale(1.0f);
        }
    }
    
    protected final void drawProvinces(final SpriteBatch oSB, final int nPosX, final int nPosY, final float scale, final int nAlpha) {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getCivID() != 0) {
                this.getProvince(i).draw(oSB, nPosX, nPosY, scale, nAlpha);
            }
            else if (this.getProvince(i).getWasteland() >= 0) {
                this.getProvince(i).drawWasteland(oSB, nPosX, nPosY, scale, nAlpha);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void drawProvinces_FogOfWarDiscovery(final SpriteBatch oSB, final int nPosX, final int nPosY, final float scale, final int nAlpha) {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getCivID() != 0) {
                this.getProvince(i).draw_FogOfWarDiscovery(oSB, nPosX, nPosY, scale, nAlpha);
            }
            else if (this.getProvince(i).getWasteland() >= 0 && CFG.getMetProvince(i)) {
                this.getProvince(i).drawWasteland(oSB, nPosX, nPosY, scale, nAlpha);
            }
        }
        oSB.setColor(Color.WHITE);
    }
    
    protected final void updatePath() {
        if (this.lMoveUnitsLineTime < System.currentTimeMillis() - 90L) {
            this.lMoveUnitsLineTime = System.currentTimeMillis();
            if (MoveUnits_Line_Highlighted.MOVE_SRC_X2 <= 1) {
                MoveUnits_Line_Highlighted.MOVE_SRC_X2 = MoveUnits_Line_Highlighted.MOVE_WIDTH2;
            }
            else {
                --MoveUnits_Line_Highlighted.MOVE_SRC_X2;
            }
            if (MoveUnits_Line.MOVE_SRC_X <= 1) {
                MoveUnits_Line.MOVE_SRC_X = MoveUnits_Line.MOVE_WIDTH;
            }
            else {
                --MoveUnits_Line.MOVE_SRC_X;
            }
            if (MoveUnits_Line_Migrate.MOVE_SRC_X2 <= 1) {
                MoveUnits_Line_Migrate.MOVE_SRC_X2 = MoveUnits_Line_Migrate.MOVE_WIDTH2;
            }
            else {
                --MoveUnits_Line_Migrate.MOVE_SRC_X2;
            }
            CFG.setRender_3(true);
        }
    }
    
    protected final void buildMoveUnits_JustDraw_AnotherArmies() {
        this.clearMoveUnits_JustDraw_AnotherArmies();
        try {
            final int tPlayerCivID = CFG.SPECTATOR_MODE ? 0 : CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
            final int tPlayerAllianceID = (CFG.SPECTATOR_MODE || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() == 0) ? -1 : CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID();
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && i != tPlayerCivID && CFG.game.getCiv(i).getAllianceID() != tPlayerAllianceID && CFG.game.getCiv(i).getPuppetOfCivID() != tPlayerCivID && CFG.game.getCiv(tPlayerCivID).getPuppetOfCivID() != i) {
                    for (int j = 0; j < CFG.game.getCiv(i).getMoveUnitsSize(); ++j) {
                        if (CFG.game.getProvince(CFG.game.getCiv(i).getMoveUnits(j).getFromProvinceID()).getSeaProvince()) {
                            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getFogOfWar(CFG.game.getCiv(i).getMoveUnits(j).getFromProvinceID()) || CFG.game.getPlayer(CFG.PLAYER_TURNID).getFogOfWar(CFG.game.getCiv(i).getMoveUnits(j).getToProvinceID())) {
                                this.lMoveUnits_JustDraw_AnotherArmies.add(new Move_Units_JustDraw(i, CFG.game.getCiv(i).getMoveUnits(j)));
                            }
                        }
                        else if (!CFG.game.getCivsAtWar(tPlayerCivID, i) && (CFG.game.isAlly(tPlayerCivID, CFG.game.getProvince(CFG.game.getCiv(i).getMoveUnits(j).getFromProvinceID()).getCivID()) || CFG.game.isAlly(tPlayerCivID, CFG.game.getProvince(CFG.game.getCiv(i).getMoveUnits(j).getToProvinceID()).getCivID()))) {
                            this.lMoveUnits_JustDraw_AnotherArmies.add(new Move_Units_JustDraw(i, CFG.game.getCiv(i).getMoveUnits(j)));
                        }
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        this.iMoveUnits_JustDraw_AnotherArmiesSize = this.lMoveUnits_JustDraw_AnotherArmies.size();
    }
    
    protected final void clearMoveUnits_JustDraw_AnotherArmies() {
        this.lMoveUnits_JustDraw_AnotherArmies.clear();
        this.iMoveUnits_JustDraw_AnotherArmiesSize = 0;
    }
    
    protected final void drawMoveUnits(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        try {
            for (int i = 0; i < this.iMoveUnits_JustDraw_AnotherArmiesSize; ++i) {
                oSB.setColor(this.getCiv(this.lMoveUnits_JustDraw_AnotherArmies.get(i).iCivID).getRGB());
                if (this.getProvince(this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getFromProvinceID()).getDrawProvince()) {
                    this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.draw2(oSB, nScale);
                    this.drawMoveUnitsFlag(oSB, this.lMoveUnits_JustDraw_AnotherArmies.get(i).iCivID, this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getToProvinceID(), nScale, this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getMoveUnitsLine().getMovingPercentage());
                }
                else if (this.getProvince(this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getToProvinceID()).getDrawProvince()) {
                    this.updateDrawProvince(this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getFromProvinceID());
                    this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.draw2(oSB, nScale);
                    this.drawMoveUnitsFlag(oSB, this.lMoveUnits_JustDraw_AnotherArmies.get(i).iCivID, this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getToProvinceID(), nScale, this.lMoveUnits_JustDraw_AnotherArmies.get(i).moveUnitsData.getMoveUnitsLine().getMovingPercentage());
                }
                this.drawMoveUnitsArmy_UpdateAnimation = true;
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        try {
            this.drawMoveUnitsArmy_UpdateAnimation = false;
            if (this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                for (int i = 0; i < CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
                    if (CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) != this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                        oSB.setColor(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getRGB());
                        for (int j = 0; j < this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getRegroupArmySize(); ++j) {
                            for (int k = this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getCurrentRegroupArmyLine(j).size() - 1; k >= 0; --k) {
                                if (this.getProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                                    this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                                }
                                else if (this.getProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                                    this.updateDrawProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getFromProvinceID());
                                    this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                                }
                            }
                        }
                        for (int j = 0; j < this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnitsSize(); ++j) {
                            oSB.setColor(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getRGB());
                            if (this.getProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getFromProvinceID()).getDrawProvince()) {
                                this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).draw2(oSB, nScale);
                                this.drawMoveUnitsFlag(oSB, CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i), this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                            }
                            else if (this.getProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getToProvinceID()).getDrawProvince()) {
                                this.updateDrawProvince(this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getFromProvinceID());
                                this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).draw2(oSB, nScale);
                                this.drawMoveUnitsFlag(oSB, CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i), this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(CFG.game.getAlliance(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                            }
                            this.drawMoveUnitsArmy_UpdateAnimation = true;
                        }
                    }
                }
            }
            for (int i = CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size() - 1; i >= 0; --i) {
                oSB.setColor(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getRGB());
                for (int j = 0; j < this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getRegroupArmySize(); ++j) {
                    for (int k = this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getCurrentRegroupArmyLine(j).size() - 1; k >= 0; --k) {
                        if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                            this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                        }
                        else if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                            this.updateDrawProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getFromProvinceID());
                            this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                        }
                    }
                }
                for (int j = 0; j < this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnitsSize(); ++j) {
                    oSB.setColor(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getRGB());
                    if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getFromProvinceID()).getDrawProvince()) {
                        this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).draw2(oSB, nScale);
                        this.drawMoveUnitsFlag(oSB, CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID, this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                    }
                    else if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getToProvinceID()).getDrawProvince()) {
                        this.updateDrawProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getFromProvinceID());
                        this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).draw2(oSB, nScale);
                        this.drawMoveUnitsFlag(oSB, CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID, this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(i).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                    }
                    this.drawMoveUnitsArmy_UpdateAnimation = true;
                }
            }
            if (CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() != this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                oSB.setColor(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getRGB());
                for (int l = 0; l < this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getRegroupArmySize(); ++l) {
                    for (int m = this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getCurrentRegroupArmyLine(l).size() - 1; m >= 0; --m) {
                        if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getCurrentRegroupArmyLine(l).get(m).getFromProvinceID()).getDrawProvince()) {
                            this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getCurrentRegroupArmyLine(l).get(m).drawLine(oSB, nScale);
                        }
                        else if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getCurrentRegroupArmyLine(l).get(m).getFromProvinceID()).getDrawProvince()) {
                            this.updateDrawProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getFromProvinceID());
                            this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getCurrentRegroupArmyLine(l).get(m).drawLine(oSB, nScale);
                        }
                    }
                }
                for (int l = 0; l < this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnitsSize(); ++l) {
                    oSB.setColor(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getRGB());
                    if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getFromProvinceID()).getDrawProvince()) {
                        this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).draw2(oSB, nScale);
                        this.drawMoveUnitsFlag(oSB, CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getToProvinceID(), nScale, this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getMoveUnitsLine().getMovingPercentage());
                    }
                    else if (this.getProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getToProvinceID()).getDrawProvince()) {
                        this.updateDrawProvince(this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getFromProvinceID());
                        this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).draw2(oSB, nScale);
                        this.drawMoveUnitsFlag(oSB, CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getToProvinceID(), nScale, this.getCiv(CFG.game.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(l).getMoveUnitsLine().getMovingPercentage());
                    }
                    this.drawMoveUnitsArmy_UpdateAnimation = true;
                }
            }
            for (int i = 0; i < this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrateSize(); ++i) {
                if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getFromProvinceID()).getDrawProvince()) {
                    this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).draw(oSB, nScale);
                }
                else if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getToProvinceID()).getDrawProvince()) {
                    this.updateDrawProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).getFromProvinceID());
                    this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(i).draw(oSB, nScale);
                }
            }
            for (int i = 0; i < this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++i) {
                if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getDrawProvince()) {
                    this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).draw(oSB, nScale);
                    this.drawMoveUnitsFlag(oSB, this.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID(), nScale, this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage());
                }
                else if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                    this.updateDrawProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID());
                    this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).draw(oSB, nScale);
                    this.drawMoveUnitsFlag(oSB, this.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID(), nScale, this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage());
                }
            }
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
    }
    
    protected final void drawMoveUnits_Spectactor(final SpriteBatch oSB, final float nScale) {
        oSB.setColor(Color.WHITE);
        try {
            this.drawMoveUnitsArmy_UpdateAnimation = false;
            for (int i = 1; i < this.getCivsSize(); ++i) {
                if (this.getCiv(i).getNumOfProvinces() > 0) {
                    oSB.setColor(this.getCiv(i).getRGB());
                    for (int j = 0; j < this.getCiv(i).getRegroupArmySize(); ++j) {
                        for (int k = this.getCiv(i).getCurrentRegroupArmyLine(j).size() - 1; k >= 0; --k) {
                            if (this.getProvince(this.getCiv(i).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                                this.getCiv(i).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                            }
                            else if (this.getProvince(this.getCiv(i).getCurrentRegroupArmyLine(j).get(k).getFromProvinceID()).getDrawProvince()) {
                                this.updateDrawProvince(this.getCiv(i).getMoveUnits(j).getFromProvinceID());
                                this.getCiv(i).getCurrentRegroupArmyLine(j).get(k).drawLine(oSB, nScale);
                            }
                        }
                    }
                    for (int j = 0; j < this.getCiv(i).getMoveUnitsSize(); ++j) {
                        oSB.setColor(this.getCiv(i).getRGB());
                        if (this.getProvince(this.getCiv(i).getMoveUnits(j).getFromProvinceID()).getDrawProvince()) {
                            this.getCiv(i).getMoveUnits(j).draw2(oSB, nScale);
                            this.drawMoveUnitsFlag(oSB, i, this.getCiv(i).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(i).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                        }
                        else if (this.getProvince(this.getCiv(i).getMoveUnits(j).getToProvinceID()).getDrawProvince()) {
                            this.updateDrawProvince(this.getCiv(i).getMoveUnits(j).getFromProvinceID());
                            this.getCiv(i).getMoveUnits(j).draw2(oSB, nScale);
                            this.drawMoveUnitsFlag(oSB, i, this.getCiv(i).getMoveUnits(j).getToProvinceID(), nScale, this.getCiv(i).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage());
                        }
                        this.drawMoveUnitsArmy_UpdateAnimation = true;
                    }
                }
            }
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawMoveUnits_CurrentMove(final SpriteBatch oSB, final float nScale) {
        try {
            for (int i = 0; i < this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmySize(); ++i) {
                for (int j = this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCurrentRegroupArmyLine(i).size() - 1; j >= 0; --j) {
                    if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCurrentRegroupArmyLine(i).get(j).getFromProvinceID()).getDrawProvince()) {
                        this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCurrentRegroupArmyLine(i).get(j).drawLine(oSB, nScale);
                    }
                    else if (this.getProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCurrentRegroupArmyLine(i).get(j).getFromProvinceID()).getDrawProvince()) {
                        this.updateDrawProvince(this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID());
                        this.getCiv(this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCurrentRegroupArmyLine(i).get(j).drawLine(oSB, nScale);
                    }
                }
            }
            for (int i = this.lHighlightedProvinces_MoveUnits_Lines.size() - 1; i >= 0; --i) {
                if (this.getProvince(this.lHighlightedProvinces_MoveUnits_Lines.get(i).getFromProvinceID()).getDrawProvince()) {
                    if (CFG.chosenProvinceID != this.lHighlightedProvinces_MoveUnits_Lines.get(i).getToProvinceID()) {
                        this.lHighlightedProvinces_MoveUnits_Lines.get(i).drawLine(oSB, nScale);
                    }
                }
                else if (this.getProvince(this.lHighlightedProvinces_MoveUnits_Lines.get(i).getToProvinceID()).getDrawProvince() && CFG.chosenProvinceID != this.lHighlightedProvinces_MoveUnits_Lines.get(i).getToProvinceID()) {
                    this.updateDrawProvince(this.lHighlightedProvinces_MoveUnits_Lines.get(i).getFromProvinceID());
                    this.lHighlightedProvinces_MoveUnits_Lines.get(i).drawLine(oSB, nScale);
                }
            }
            for (int i = this.lCurrentRegroupArmyLine.size() - 1; i >= 0; --i) {
                if (this.getProvince(this.lCurrentRegroupArmyLine.get(i).getFromProvinceID()).getDrawProvince()) {
                    this.lCurrentRegroupArmyLine.get(i).drawLine(oSB, nScale);
                }
                else if (this.getProvince(this.lCurrentRegroupArmyLine.get(i).getToProvinceID()).getDrawProvince()) {
                    this.updateDrawProvince(this.lCurrentRegroupArmyLine.get(i).getFromProvinceID());
                    this.lCurrentRegroupArmyLine.get(i).drawLine(oSB, nScale);
                }
            }
            if (this.currentMoveUnitsLine != null) {
                if (this.getProvince(this.currentMoveUnitsLine.getFromProvinceID()).getDrawProvince()) {
                    this.currentMoveUnitsLine.drawLine(oSB, nScale);
                }
                else if (this.getProvince(this.currentMoveUnitsLine.getToProvinceID()).getDrawProvince()) {
                    this.updateDrawProvince(this.currentMoveUnitsLine.getFromProvinceID());
                    this.currentMoveUnitsLine.drawLine(oSB, nScale);
                }
            }
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void drawMoveUnitsFlag(final SpriteBatch oSB, final int nCivID, final int nProvinceID, final float nScale, final float nAlpha) {
        oSB.setColor(1.0f, 1.0f, 1.0f, nAlpha);
        if (CFG.game.getProvince(nProvinceID).getSeaProvince()) {
            ImageManager.getImage(Images.icon_move_sea).draw(oSB, (int)((CFG.game.getProvince(nProvinceID).getCenterX() + CFG.game.getProvince(nProvinceID).getShiftX() + CFG.game.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale), (int)((CFG.game.getProvince(nProvinceID).getCenterY() + CFG.game.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY() - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT) * nScale) - ImageManager.getImage(Images.icon_move_sea).getHeight() - CFG.ARMY_BG_EXTRA_HEIGHT);
        }
        else if (CFG.game.isAlly(nCivID, CFG.game.getProvince(nProvinceID).getCivID())) {
            ImageManager.getImage(Images.icon_move_ally).draw(oSB, (int)((CFG.game.getProvince(nProvinceID).getCenterX() + CFG.game.getProvince(nProvinceID).getShiftX() + CFG.game.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale), (int)((CFG.game.getProvince(nProvinceID).getCenterY() + CFG.game.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY() - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT) * nScale) - ImageManager.getImage(Images.icon_move_ally).getHeight() - CFG.ARMY_BG_EXTRA_HEIGHT);
        }
        else {
            ImageManager.getImage(Images.icon_move_attack).draw(oSB, (int)((CFG.game.getProvince(nProvinceID).getCenterX() + CFG.game.getProvince(nProvinceID).getShiftX() + CFG.game.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale), (int)((CFG.game.getProvince(nProvinceID).getCenterY() + CFG.game.getProvince(nProvinceID).getShiftY() + CFG.map.getMapCoordinates().getPosY() - CFG.ARMY_HEIGHT / 2 - CFG.ARMY_BG_EXTRA_HEIGHT) * nScale) - ImageManager.getImage(Images.icon_move_attack).getHeight() - CFG.ARMY_BG_EXTRA_HEIGHT);
        }
    }

    protected final void updateDrawMoveUnitsArmy() {
        if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.TURN_ACTIONS) {
            this.drawMoveUnitsArmy = new DrawMoveUnitsArmy() {
                public void drawMoveUnitsArmy(SpriteBatch oSB, float nScale) {
                    if (CFG.gameAction.getCurrentMoveunits() != null) {
                        for(int i = 0; i < CFG.gameAction.getCurrentMoveunits().getMoveUnitsSize(); ++i) {
                            if (Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                if (CFG.game.getCivsAtWar(CFG.gameAction.getCurrentMoveunits().getCivID(i), Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCivID())) {
                                    Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getShiftY())) * CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.gameAction.getCurrentMoveunits().getCivID(i), CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getNumOfUnits(), CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getUnitsWidth());
                                } else {
                                    Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getToProvinceID()).getShiftY())) * CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.gameAction.getCurrentMoveunits().getCivID(i), CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getNumOfUnits(), CFG.gameAction.getCurrentMoveunits().getMoveUnits(i).getUnitsWidth());
                                }
                            }
                        }
                    }

                }
            };
        } else if (CFG.settingsManager.DRAW_MOVE_UNITS_ARMY_IN_EVERYSINGLE_PROVINCE) {
            if (CFG.SPECTATOR_MODE) {
                this.drawMoveUnitsArmy = new DrawMoveUnitsArmy() {
                    public void drawMoveUnitsArmy(SpriteBatch oSB, float nScale) {
                        for(int j = 1; j < Game.this.getCivsSize(); ++j) {
                            if (Game.this.getCiv(j).getNumOfProvinces() > 0) {
                                for(int i = 0; i < Game.this.getCiv(j).getMoveUnitsSize(); ++i) {
                                    if (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                        if (CFG.game.getCivsAtWar(j, Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCivID())) {
                                            Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), j, Game.this.getCiv(j).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(j).getMoveUnits(i).getUnitsWidth());
                                        } else {
                                            Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), j, Game.this.getCiv(j).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(j).getMoveUnits(i).getUnitsWidth());
                                        }
                                    }
                                }
                            }
                        }

                    }
                };
            } else {
                this.drawMoveUnitsArmy = new DrawMoveUnitsArmy() {
                    public void drawMoveUnitsArmy(SpriteBatch oSB, float nScale) {
                        int j;
                        try {
                            for(j = 0; j < Game.this.iMoveUnits_JustDraw_AnotherArmiesSize; ++j) {
                                if (Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getDrawProvince()) {
                                    if (CFG.game.getCivsAtWar(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).iCivID, Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getCivID())) {
                                        Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getTranslateProvincePosX())) * Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getShiftY())) * Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).iCivID, Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getNumOfUnits(), Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getUnitsWidth());
                                    } else {
                                        Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getTranslateProvincePosX())) * Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getToProvinceID()).getShiftY())) * Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).iCivID, Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getNumOfUnits(), Game.this.lMoveUnits_JustDraw_AnotherArmies.get(j).moveUnitsData.getUnitsWidth());
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException var7) {
                            if (CFG.LOGS) {
                                CFG.exceptionStack(var7);
                            }
                        }

                        int i;
                        if (Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                            for(j = 0; j < Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++j) {
                                if (Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j) != Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                    for(i = 0; i < Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnitsSize(); ++i) {
                                        if (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                            if (CFG.game.getCivsAtWar(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j), Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getCivID())) {
                                                Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getUnitsWidth());
                                            } else {
                                                Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits(i).getUnitsWidth());
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        for(j = CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size() - 1; j >= 0; --j) {
                            for(i = 0; i < Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnitsSize(); ++i) {
                                if (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                    if (CFG.game.getCivsAtWar(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID, Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getCivID())) {
                                        Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID, Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getUnitsWidth());
                                    } else {
                                        Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID, Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getUnitsWidth());
                                    }
                                }
                            }
                        }

                        if (CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() != Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                            for(j = 0; j < Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnitsSize(); ++j) {
                                if (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getDrawProvince()) {
                                    if (CFG.game.getCivsAtWar(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getCivID())) {
                                        Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getUnitsWidth());
                                    } else {
                                        Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(j).getUnitsWidth());
                                    }
                                }
                            }
                        }

                        for(j = 0; j < Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrateSize(); ++j) {
                            if (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getDrawProvince()) {
                                Game.this.drawProvinceArmyWithFlag_Migrate(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getNumOfUnits(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMigrate(j).getUnitsWidth());
                            }
                        }

                        for(j = 0; j < Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++j) {
                            if (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getDrawProvince()) {
                                if (CFG.game.getCivsAtWar(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getCivID())) {
                                    Game.this.drawProvinceArmyWithFlag_Attack(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getNumOfUnits(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getUnitsWidth());
                                } else {
                                    Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getNumOfUnits(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(j).getUnitsWidth());
                                }
                            }
                        }

                        try {
                            if (!CFG.menuManager.getInGame_Plunder().getVisible()) {
                                for(j = 0; j < Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsPlunderSize(); ++j) {
                                    if (Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getDrawProvince()) {
                                        Game.this.drawProvinceArmyWithFlag_Plunder(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getNumOfUnits(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getUnitsWidth());
                                    }
                                }
                            } else {
                                for(j = 0; j < Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsPlunderSize(); ++j) {
                                    if (Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID() != Menu_InGame_Plunder.iProvinceID && Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getDrawProvince()) {
                                        Game.this.drawProvinceArmyWithFlag_Plunder(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getFromProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getNumOfUnits(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits_Plunder(j).getUnitsWidth());
                                    }
                                }

                                if (Menu_InGame_Plunder.iProvinceID >= 0) {
                                    j = CFG.menuManager.getInGame_Plunder().getMenuElement(1).getCurrent();
                                    CFG.glyphLayout.setText(CFG.fontArmy, "" + j);
                                    i = (int)CFG.glyphLayout.width;
                                    Game.this.drawProvinceArmyWithFlag_Plunder(oSB, (int)((float)(Game.this.getProvince(Menu_InGame_Plunder.iProvinceID).getCenterX() + Game.this.getProvince(Menu_InGame_Plunder.iProvinceID).getShiftX() + Game.this.getProvince(Menu_InGame_Plunder.iProvinceID).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Menu_InGame_Plunder.iProvinceID, nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), j, i);
                                }
                            }

                            if (Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                                for(j = 0; j < Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++j) {
                                    if (Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID() != Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)) {
                                        for(i = 0; i < Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnitsPlunderSize(); ++i) {
                                            if (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getFromProvinceID()).getDrawProvince()) {
                                                Game.this.drawProvinceArmyWithFlag_Plunder(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getFromProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getFromProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getNumOfUnits(), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getMoveUnits_Plunder(i).getUnitsWidth());
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException var6) {
                        }

                        try {
                            if (!CFG.menuManager.getInGame_ProvinceRecruit().getVisible() && !CFG.menuManager.getInGame_ProvinceRecruit_Instantly().getVisible()) {
                                for(j = 0; j < Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmySize(); ++j) {
                                    if (Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getDrawProvince()) {
                                        Game.this.drawProvinceArmyWithFlag_Recruit(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getArmy(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getArmyWidth());
                                    }
                                }
                            } else {
                                for(j = 0; j < Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmySize(); ++j) {
                                    if (Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID() != Game.this.getActiveProvinceID() && Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getDrawProvince()) {
                                        Game.this.drawProvinceArmyWithFlag_Recruit(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getArmy(), Game.this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(j).getArmyWidth());
                                    }
                                }

                                if (Game.this.getProvince(Game.this.getActiveProvinceID()).getDrawProvince()) {
                                    if (CFG.menuManager.getInGame_ProvinceRecruit().getVisible()) {
                                        j = CFG.menuManager.getInGame_ProvinceRecruit().getMenuElement(2).getCurrent();
                                        CFG.glyphLayout.setText(CFG.fontArmy, "" + j);
                                        i = (int)CFG.glyphLayout.width;
                                        Game.this.drawProvinceArmyWithFlag_Recruit(oSB, (int)((float)(Game.this.getProvince(Game.this.getActiveProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getActiveProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getActiveProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getActiveProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), j, i);
                                    } else if (CFG.menuManager.getInGame_ProvinceRecruit_Instantly().getVisible()) {
                                        j = CFG.menuManager.getInGame_ProvinceRecruit_Instantly().getMenuElement(2).getCurrent();
                                        CFG.glyphLayout.setText(CFG.fontArmy, "" + j);
                                        i = (int)CFG.glyphLayout.width;
                                        Game.this.drawProvinceArmyWithFlag_Recruit(oSB, (int)((float)(Game.this.getProvince(Game.this.getActiveProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getActiveProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getActiveProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getActiveProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), j, i);
                                    }
                                }
                            }

                            if (Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                                for(j = 0; j < Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++j) {
                                    if (Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID() != Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)) {
                                        for(i = 0; i < Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmySize(); ++i) {
                                            if (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getProvinceID()).getDrawProvince()) {
                                                Game.this.drawProvinceArmyWithFlag_Recruit(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getProvinceID()).getTranslateProvincePosX()) * nScale), Game.this.getDrawProvinceArmy_EndPosY(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getProvinceID(), nScale) + (CFG.ARMY_HEIGHT + CFG.ARMY_BG_EXTRA_HEIGHT * 2) / 2 + CFG.ARMY_BG_EXTRA_HEIGHT, Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getArmy(), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(j)).getRecruitArmy(i).getArmyWidth());
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException var5) {
                        }

                    }
                };
            }
        } else if (CFG.SPECTATOR_MODE) {
            this.drawMoveUnitsArmy = new DrawMoveUnitsArmy() {
                public void drawMoveUnitsArmy(SpriteBatch oSB, float nScale) {
                    for(int j = 1; j < Game.this.getCivsSize(); ++j) {
                        if (Game.this.getCiv(j).getNumOfProvinces() > 0) {
                            for(int i = 0; i < Game.this.getCiv(j).getMoveUnitsSize(); ++i) {
                                if (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                    Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(j).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(j).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), j, Game.this.getCiv(j).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(j).getMoveUnits(i).getUnitsWidth());
                                }
                            }
                        }
                    }

                }
            };
        } else {
            this.drawMoveUnitsArmy = new DrawMoveUnitsArmy() {
                public void drawMoveUnitsArmy(SpriteBatch oSB, float nScale) {
                    int i;
                    int ix;
                    if (Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0) {
                        for(i = 0; i < Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
                            if (Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i) != Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                                for(ix = 0; ix < Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnitsSize(); ++ix) {
                                    if ((Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID() == Game.this.getActiveProvinceID() || Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID() == Game.this.getActiveProvinceID() || Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID() == CFG.chosenProvinceID || Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID() == CFG.chosenProvinceID) && (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getDrawProvince())) {
                                        Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getNumOfUnits(), Game.this.getCiv(Game.this.getAlliance(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID()).getCivilization(i)).getMoveUnits(ix).getUnitsWidth());
                                    }
                                }
                            }
                        }
                    }

                    for(i = CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size() - 1; i >= 0; --i) {
                        for(ix = 0; ix < Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnitsSize(); ++ix) {
                            if (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getDrawProvince()) {
                                Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID, Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i).iCivID).getMoveUnits(ix).getUnitsWidth());
                            }
                        }
                    }

                    if (CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() != Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                        for(i = 0; i < Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnitsSize(); ++i) {
                            if (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getDrawProvince()) {
                                Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(CFG.game.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID()).getMoveUnits(i).getUnitsWidth());
                            }
                        }
                    }

                    for(i = 0; i < Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++i) {
                        if ((Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID() == Game.this.getActiveProvinceID() || Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID() == Game.this.getActiveProvinceID() || Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID() == CFG.chosenProvinceID || Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID() == CFG.chosenProvinceID) && (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getDrawProvince() || Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getDrawProvince())) {
                            Game.this.drawProvinceArmyWithFlag(oSB, (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX() + (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getTranslateProvincePosX() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getCenterX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getShiftX() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getTranslateProvincePosX())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F)) * nScale), (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getShiftY() - (int)((float)(Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID()).getShiftY() - (Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getCenterY() + Game.this.getProvince(Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID()).getShiftY())) * Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getMoveUnitsLine().getMovingPercentage() / 2.0F) + CFG.map.getMapCoordinates().getPosY()) * nScale), Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getNumOfUnits(), Game.this.getCiv(Game.this.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getUnitsWidth());
                        }
                    }

                }
            };
        }

    }
    
    protected final void drawMoveUnitsArmy(final SpriteBatch oSB, final float nScale) {
        try {
            this.drawMoveUnitsArmy.drawMoveUnitsArmy(oSB, nScale);
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            this.updateDrawMoveUnitsArmy();
        }
    }
    
    protected final void addHighlightedProvince_MoveUnits_Line(final int fromProvinceID, final int toProvinceID) {
        this.lHighlightedProvinces_MoveUnits_Lines.add(new MoveUnits_Line_Highlighted(fromProvinceID, toProvinceID));
    }
    
    protected final void addHighlightedProvince_MoveUnits_Line_Migrate(final int fromProvinceID, final int toProvinceID) {
        this.lHighlightedProvinces_MoveUnits_Lines.add(new MoveUnits_Line_MigrateHighlighted(fromProvinceID, toProvinceID));
    }
    
    protected final boolean inViewY(final int nProvinceID) {
        return -CFG.map.getMapCoordinates().getPosY() + CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale() >= this.getProvince(nProvinceID).getMinY() && -CFG.map.getMapCoordinates().getPosY() <= this.getProvince(nProvinceID).getMaxY();
    }
    
    protected final boolean inViewX(final int nProvinceID) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= this.getProvince(nProvinceID).getMinX() + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() && -CFG.map.getMapCoordinates().getPosX() <= this.getProvince(nProvinceID).getMaxX() + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX();
    }
    
    protected final boolean inViewX2(final int nProvinceID) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= this.getProvince(nProvinceID).getMinX() && -CFG.map.getMapCoordinates().getPosX() <= this.getProvince(nProvinceID).getMaxX();
    }
    
    protected final boolean inViewXBelowZero(final int nProvinceID) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= this.getProvince(nProvinceID).getMinX() + CFG.map.getMapBG().getWidth() && -CFG.map.getMapCoordinates().getPosX() <= this.getProvince(nProvinceID).getMaxX() + CFG.map.getMapBG().getWidth();
    }
    
    protected final boolean inViewY(final int nMinY, final int nMaxY) {
        return -CFG.map.getMapCoordinates().getPosY() + CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale() >= nMinY && -CFG.map.getMapCoordinates().getPosY() <= nMaxY;
    }
    
    protected final boolean inViewX(final int nMinX, final int nMaxX) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= nMinX + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() && -CFG.map.getMapCoordinates().getPosX() <= nMaxX + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX();
    }
    
    protected final boolean inViewX2(final int nMinX, final int nMaxX) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= nMinX && -CFG.map.getMapCoordinates().getPosX() <= nMaxX;
    }
    
    protected final boolean inViewXBelowZero(final int nMinX, final int nMaxX) {
        return -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale() >= nMinX + CFG.map.getMapBG().getWidth() && -CFG.map.getMapCoordinates().getPosX() <= nMaxX + CFG.map.getMapBG().getWidth();
    }
    
    protected final boolean inViewY_WholeRegion(final int nMinY, final int nMaxY) {
        return nMinY >= -CFG.map.getMapCoordinates().getPosY() && nMaxY <= -CFG.map.getMapCoordinates().getPosY() + CFG.GAME_HEIGHT / CFG.map.getMapScale().getCurrentScale();
    }
    
    protected final boolean inViewX_WholeRegion(final int nMinX, final int nMaxX) {
        return nMinX + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() >= -CFG.map.getMapCoordinates().getPosX() && nMaxX + CFG.map.getMapCoordinates().getSecondSideOfMap_MoveX() <= -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale();
    }
    
    protected final boolean inViewX_WholeRegion2(final int nMinX, final int nMaxX) {
        return nMinX >= -CFG.map.getMapCoordinates().getPosX() && nMaxX <= -CFG.map.getMapCoordinates().getPosX() + CFG.GAME_WIDTH / CFG.map.getMapScale().getCurrentScale();
    }
    
    protected final void actionMoveUnits() {
    }
    
    protected final int getActiveCivID() {
        return (this.getActiveProvinceID() >= 0) ? this.getPlayer(CFG.PLAYER_TURNID).getCivID() : 0;
    }
    
    protected final void setProvinceID_PPM(final int nPosX, final int nPosY) {
        final int nNewChosenProvinceID = this.setProvinceID_HoverAProvince((int)(nPosX / CFG.map.getMapScale().getCurrentScale()), (int)(nPosY / CFG.map.getMapScale().getCurrentScale()));
        if (!DiplomacyManager.canMoveToNaighbooringProvince(nNewChosenProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
            this.resetRegroupArmyData();
            if (!CFG.menuManager.getVisible_InGame_ProvinceAction()) {
                CFG.game.checkProvinceActionMenu();
            }
            return;
        }
        if (nNewChosenProvinceID >= 0 && CFG.game.getActiveProvinceID() != nNewChosenProvinceID) {
            if (CFG.chosenProvinceID == nNewChosenProvinceID) {
                CFG.menuManager.getInGame_ProvinceRegroupArmy_ConfirmMove();
                CFG.soundsManager.playSound(SoundsManager.SOUND_MOVE_REGROUP);
                return;
            }
            if (CFG.chooseProvinceMode || !CFG.regroupArmyMode) {
                this.resetChooseProvinceData();
            }
            CFG.regroupArmyMode = true;
            this.setCurrentRegroupArmyID(nNewChosenProvinceID);
            this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
            CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK, SoundsManager.PERC_VOLUME_SELECT_PROVINCE);
        }
        try {
            if (this.currentRegroupArmy.getRouteSize() == 0) {
                this.resetRegroupArmyData();
                if (!CFG.menuManager.getVisible_InGame_ProvinceAction()) {
                    CFG.game.checkProvinceActionMenu();
                }
            }
            else if (!CFG.menuManager.getVisible_InGame_ProvinceRegroupArmy()) {
                CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(true);
            }
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void setProvinceID(final int nPosX, final int nPosY) {
        if (CFG.chooseProvinceMode) {
            for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                if (this.pathContains(this.lHighlightedProvinces.get(i), (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                    this.setChosenProvinceID(this.lHighlightedProvinces.get(i));
                    return;
                }
            }
            if (Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) + 500 * CFG.map.getMapBG().getMapScale() / CFG.map.getMapScale().getCurrentScale() > CFG.map.getMapBG().getWidth()) {
                for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getBelowZero() && this.pathContains(this.lHighlightedProvinces.get(i), (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) - CFG.map.getMapBG().getWidth(), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                        this.setChosenProvinceID(this.lHighlightedProvinces.get(i));
                        return;
                    }
                }
            }
            if (this.getProvince(this.getActiveProvinceID()).getLevelOfPort() > 0) {
                for (int i = 0; i < this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvincesSize(); ++i) {
                    if (this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)).getBelowZero() && this.pathContains(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i), (int)(Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) - CFG.map.getMapBG().getWidth()), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                        this.setChosenProvinceID(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i));
                        return;
                    }
                }
            }
            if (this.pathContains(this.getActiveProvinceID(), (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                if (CFG.chosenProvinceID < 0) {
                    this.setActiveProvinceID(this.getActiveProvinceID());
                    this.resetChooseProvinceData();
                    if (!CFG.chooseProvinceMode) {
                        this.setProvinceID(nPosX, nPosY);
                    }
                    this.checkProvinceActionMenu();
                }
                return;
            }
            if (CFG.chosenProvinceID < 0) {
                this.setActiveProvinceID(this.getActiveProvinceID());
                this.resetChooseProvinceData();
                if (!CFG.chooseProvinceMode) {
                    this.setProvinceID(nPosX, nPosY);
                }
                this.checkProvinceActionMenu();
            }
        }
        else if (CFG.regroupArmyMode) {
            final int nNewChosenProvinceID = this.setProvinceID_HoverAProvince(nPosX, nPosY);
            if (nNewChosenProvinceID >= 0) {
                this.setCurrentRegroupArmyID(nNewChosenProvinceID);
                this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
            }
        }
        else {
            int i = 0;
            int tPosX = 0;
            while (i < this.getProvincesSize()) {
                tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
                if (this.getProvince(i).getMinX() <= tPosX && this.getProvince(i).getMaxX() >= tPosX && this.getProvince(i).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(i).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(i, tPosX, -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                    if (i != this.iActiveProvince) {
                        this.resetLastActiveProvince();
                        this.setActiveProvinceID(i);
                    }
                    return;
                }
                ++i;
            }
            if (Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) + -Game.MAX_BELOW_ZERO_POINT_X * CFG.map.getMapBG().getMapScale() / CFG.map.getMapScale().getCurrentScale() > CFG.map.getMapBG().getWidth()) {
                i = 0;
                tPosX = 0;
                while (i < this.getProvincesSize()) {
                    if (this.getProvince(i).getBelowZero()) {
                        tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
                        if (this.getProvince(i).getMinX() <= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMaxX() >= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(i).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(i, tPosX - CFG.map.getMapBG().getWidth(), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                            if (i != this.iActiveProvince) {
                                this.resetLastActiveProvince();
                                this.setActiveProvinceID(i);
                            }
                            return;
                        }
                    }
                    ++i;
                }
            }
            this.resetActiveProvincesINFO();
        }
    }
    
    protected final int setProvinceID_HoverAProvince(final int nPosX, final int nPosY) {
        int i = 0;
        int tPosX = 0;
        while (i < this.getProvincesSize()) {
            tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
            if (this.getProvince(i).getMinX() <= tPosX && this.getProvince(i).getMaxX() >= tPosX && this.getProvince(i).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(i).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(i, tPosX, -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                return i;
            }
            ++i;
        }
        if (Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) + -Game.MAX_BELOW_ZERO_POINT_X * CFG.map.getMapBG().getMapScale() / CFG.map.getMapScale().getCurrentScale() > CFG.map.getMapBG().getWidth()) {
            i = 0;
            tPosX = 0;
            while (i < this.getProvincesSize()) {
                if (this.getProvince(i).getBelowZero()) {
                    tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
                    if (this.getProvince(i).getMinX() <= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMaxX() >= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(i).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(i, tPosX - CFG.map.getMapBG().getWidth(), -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
                        return i;
                    }
                }
                ++i;
            }
        }
        return -1;
    }
    
    protected final boolean setProvinceID_IsMouseOverAProvinceID(final int nPosX, final int nPosY, final int nProvinceID) {
        int tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
        if (this.getProvince(nProvinceID).getMinX() <= tPosX && this.getProvince(nProvinceID).getMaxX() >= tPosX && this.getProvince(nProvinceID).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(nProvinceID).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(nProvinceID, tPosX, -CFG.map.getMapCoordinates().getPosY() + nPosY)) {
            return true;
        }
        if (Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX))) + -Game.MAX_BELOW_ZERO_POINT_X * CFG.map.getMapBG().getMapScale() / CFG.map.getMapScale().getCurrentScale() > CFG.map.getMapBG().getWidth() && this.getProvince(nProvinceID).getBelowZero()) {
            tPosX = (int)Math.abs(this.checkPosOfClickX((float)(CFG.map.getMapCoordinates().getPosX() - nPosX)));
            return this.getProvince(nProvinceID).getMinX() <= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(nProvinceID).getMaxX() >= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(nProvinceID).getMinY() <= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.getProvince(nProvinceID).getMaxY() >= -CFG.map.getMapCoordinates().getPosY() + nPosY && this.pathContains(nProvinceID, tPosX - CFG.map.getMapBG().getWidth(), -CFG.map.getMapCoordinates().getPosY() + nPosY);
        }
        return false;
    }
    
    protected final int setProvinceID_Point(final int nPosX, final int nPosY) {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getMinX() <= nPosX && this.getProvince(i).getMaxX() >= nPosX && this.getProvince(i).getMinY() <= nPosY && this.getProvince(i).getMaxY() >= nPosY && this.pathContains(i, nPosX, nPosY)) {
                return i;
            }
        }
        if (Math.abs(this.checkPosOfClickX((float)nPosX)) + -Game.MAX_BELOW_ZERO_POINT_X * CFG.map.getMapBG().getMapScale() / CFG.map.getMapScale().getCurrentScale() > CFG.map.getMapBG().getWidth()) {
            int i = 0;
            int tPosX = 0;
            while (i < this.getProvincesSize()) {
                if (this.getProvince(i).getBelowZero()) {
                    tPosX = (int)Math.abs(this.checkPosOfClickX((float)nPosX));
                    if (this.getProvince(i).getMinX() <= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMaxX() >= tPosX - CFG.map.getMapBG().getWidth() && this.getProvince(i).getMinY() <= nPosY && this.getProvince(i).getMaxY() >= nPosY && this.pathContains(i, tPosX - CFG.map.getMapBG().getWidth(), nPosY)) {
                        return i;
                    }
                }
                ++i;
            }
        }
        return -1;
    }
    
    protected final boolean pathContains(final int nProvinceID, final int nPosX, final int nPosY) {
        boolean output = false;
        int i = 0;
        final int iSize = this.getProvince(nProvinceID).getPointsSize();
        int j = iSize - 1;
        while (i < iSize) {
            if (this.getProvince(nProvinceID).getPointsY(i) > nPosY != this.getProvince(nProvinceID).getPointsY(j) > nPosY && nPosX < (this.getProvince(nProvinceID).getPointsX(j) - this.getProvince(nProvinceID).getPointsX(i)) * (nPosY - this.getProvince(nProvinceID).getPointsY(i)) / (this.getProvince(nProvinceID).getPointsY(j) - this.getProvince(nProvinceID).getPointsY(i)) + this.getProvince(nProvinceID).getPointsX(i)) {
                output = !output;
            }
            j = i++;
        }
        return output;
    }
    
    private final float checkPosOfClickX(float nPosX) {
        if (-nPosX > CFG.map.getMapBG().getWidth()) {
            while (-nPosX > CFG.map.getMapBG().getWidth()) {
                nPosX += CFG.map.getMapBG().getWidth();
            }
        }
        else if (nPosX > 0.0f) {
            while (nPosX > 0.0f) {
                nPosX -= CFG.map.getMapBG().getWidth();
            }
        }
        return nPosX;
    }
    
    protected final void resetRegroupArmyData() {
        this.resetActiveProvincesINFO();
        CFG.menuManager.setVisible_InGame_ActionInfo(false);
        CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(false);
        this.currentRegroupArmy = null;
    }
    
    protected final void checkProvinceActionMenu() {
        if (CFG.SPECTATOR_MODE) {
            CFG.menuManager.setVisible_InGame_ProvinceAction(true);
            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
        }
        else if (this.getActiveProvinceID() >= 0) {
            if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS && !CFG.menuManager.getVisible_InGame_FlagAction()) {
                if (!CFG.menuManager.getInGame_ProvinceRecruit_Visible() && !CFG.menuManager.getInGame_ProvinceDisband_Visible()) {
                    if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                        //else if puppet of civ and with no mil status
                        if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.gameAction.controlsArmyInProvince(CFG.game.getActiveProvinceID()) || CFG.gameAction.isMovingArmyFromProvince(CFG.game.getActiveProvinceID())) {
                            CFG.menuManager.setVisible_InGame_ProvinceAction(true);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                        } else if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && !CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).isMilitaryControl()) {
                            CFG.menuManager.setVisible_InGame_ProvinceAction(true);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                        } else if (!CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() == 0 && Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES) {
                            if (CFG.gameAction.canColonizieNeutral_Tech(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                                if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                }
                                else {
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                }
                            }
                            else {
                                CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                                if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                }
                                else {
                                    CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                }
                            }
                        }
                        else {
                            CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                        }
                    }
                    else if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
                        if (CFG.gameAction.canColonizieWasteland_Tech(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                            CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                            if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                            }
                            else {
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                            }
                        }
                        else {
                            CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                            if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                            }
                            else {
                                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(CFG.FOG_OF_WAR < 2 || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(CFG.game.getActiveProvinceID()));
                            }
                        }
                    }
                    else {
                        CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
                        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                    }
                }
            }
            else {
                CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
                CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
            }
        }
        else {
            CFG.menuManager.setVisible_InGame_ProvinceAction(false);
            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
            CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
        }
    }
    
    protected final void resetChooseProvinceData() {
        this.resetActiveProvincesINFO();
        CFG.menuManager.setVisible_InGame_ActionInfo(false);
        CFG.menuManager.setVisible_InGame_ProvinceMoveUnits(false);
        this.enableHighlightedProvinces_Percentage();
        this.highlightedProvinceBorder_BackAnimation = true;
        this.lDashedLineTime_Percentage_HighlitedProvinceBorder = System.currentTimeMillis();
        this.highlightedProvinceBorder_Update = true;
        this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION = true;
        this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
    }
    
    protected final void resetChooseProvinceData_Immediately() {
        this.disableHighlightedProvinces();
        this.lHighlightedProvinces.clear();
        this.iHighlightedProvincesSize = 0;
        CFG.menuManager.setVisible_InGame_ActionInfo(false);
        CFG.menuManager.setVisible_InGame_ProvinceMoveUnits(false);
        this.highlightedProvinceBorder_Update = false;
        CFG.gameAction.updateInGame_ProvinceInfo();
    }
    
    protected final void setChosenProvinceID(final int nProvinceID) {
        if (CFG.migrateMode) {
            CFG.gameAction.hideAllViews();
            CFG.menuManager.rebuildInGame_Migrate(CFG.game.getActiveProvinceID(), nProvinceID);
            this.resetChooseProvinceData_Immediately();
            CFG.game.setActiveProvinceID(nProvinceID);
        }
        else {
            if (CFG.chosenProvinceID < 0) {
                CFG.gameAction.hideAllViews();
                CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setMax(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setCurrent(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                CFG.menuManager.setVisible_InGame_ProvinceMoveUnits(true);
            }
            else if (CFG.chosenProvinceID == nProvinceID && CFG.menuManager.getInGame_ProvinceMoveUnits_Visible()) {
                CFG.menuManager.getInGame_ProvinceMoveUnits_Confrim();
                CFG.soundsManager.playSound(SoundsManager.SOUND_MOVE_ARMY);
                CFG.map.getMapTouchManager().setActionDownTime(0L);
                return;
            }
            this.updateProvinceNameWidth(CFG.chosenProvinceID = nProvinceID);
            boolean tFound = false;
            for (int i = 0; i < this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnitsSize(); ++i) {
                if (this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getFromProvinceID() == this.getActiveProvinceID() && this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getToProvinceID() == CFG.chosenProvinceID) {
                    CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setMax(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) + this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getNumOfUnits());
                    CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setCurrent(this.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoveUnits(i).getNumOfUnits());
                    tFound = true;
                    break;
                }
            }
            if (!tFound) {
                CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setMax(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                CFG.menuManager.getInGame_ProvinceMoveUnits_Slider().setCurrent(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
            }
            this.currentMoveUnitsLine = new MoveUnits_Line_Current(this.getActiveProvinceID(), nProvinceID);
            CFG.gameAction.updateInGame_ProvinceInfo();
            CFG.menuManager.setVisible_InGame_ActionInfo_Move();
        }
    }
    
    protected final void setCurrentRegroupArmyID(final int nToProvinceID) {
        if (CFG.chosenProvinceID < 0) {
            CFG.gameAction.hideAllViews();
            CFG.menuManager.getInGame_RegroupArmy_Slider().setMax(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
            CFG.menuManager.getInGame_RegroupArmy_Slider().setCurrent(this.getProvince(this.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
            CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(true);
        }
        this.currentRegroupArmy = null;
        if (!DiplomacyManager.canMoveToNaighbooringProvince(nToProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
            CFG.gameAction.hideAllViews();
            this.lCurrentRegroupArmyLine.clear();
            CFG.chosenProvinceID = -1;
            CFG.menuManager.setVisible_InGame_ActionInfo_RegroupArmy();
            return;
        }
        this.currentRegroupArmy = new RegroupArmy_Data(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), this.getActiveProvinceID(), nToProvinceID);
        if (this.currentRegroupArmy.getRouteSize() > 0) {
            CFG.chosenProvinceID = nToProvinceID;
            this.lCurrentRegroupArmyLine.clear();
            (this.lCurrentRegroupArmyLine = new ArrayList<MoveUnits_Line>()).add(new MoveUnits_Line_Current(this.currentRegroupArmy.getFromProvinceID(), this.currentRegroupArmy.getRoute(0)));
            for (int i = 0; i < this.currentRegroupArmy.getRouteSize() - 1; ++i) {
                this.lCurrentRegroupArmyLine.add(new MoveUnits_Line_Highlighted(this.currentRegroupArmy.getRoute(i), this.currentRegroupArmy.getRoute(i + 1)));
            }
            this.updateProvinceNameWidth(CFG.chosenProvinceID);
            CFG.gameAction.updateInGame_ProvinceInfo();
            CFG.menuManager.setVisible_InGame_ActionInfo_RegroupArmy_Move();
        }
        else {
            CFG.gameAction.hideAllViews();
            this.lCurrentRegroupArmyLine.clear();
            CFG.chosenProvinceID = -1;
            CFG.menuManager.setVisible_InGame_ActionInfo_RegroupArmy();
        }
    }
    
    protected final void autoBuildChooseProvinceMode(final boolean force) {
        if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS && !CFG.chooseProvinceMode && CFG.map.getMapScale().getCurrentScale() >= 1.0f && ((CFG.activeProvince_BEFORE != this.getActiveProvinceID() && !CFG.menuManager.getInGame_ProvinceBuild_Visible()) || force) && (CFG.viewsManager.getActiveViewID() < 0 || CFG.viewsManager.getActiveView().canMoveArmy) && !this.getProvince(this.getActiveProvinceID()).getSeaProvince() && this.getProvince(this.getActiveProvinceID()).getWasteland() < 0 && CFG.gameAction.controlsArmyInProvince(this.getActiveProvinceID())) {
            this.autoChooseProvinceMode(this.getActiveProvinceID());
        }
    }
    
    private final void autoChooseProvinceMode(final int nProvinceID) {
        if (CFG.regroupArmyMode || CFG.menuManager.getVisible_InGame_FlagAction()) {
            return;
        }
        this.currentMoveUnitsLine = null;
        if (this.getActiveProvinceID() < 0) {
            return;
        }
        CFG.activeCivilizationArmyID = 0;
        if (!CFG.SPECTATOR_MODE && CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CFG.game.getProvince(this.getActiveProvinceID()).getCivID()) {
            for (int i = 1; i < CFG.game.getProvince(this.getActiveProvinceID()).getCivsSize(); ++i) {
                if (CFG.game.getProvince(this.getActiveProvinceID()).getCivID(i) == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                    CFG.activeCivilizationArmyID = i;
                    break;
                }
            }
        }
        this.highlightedProvinceBorder_BackAnimation = false;
        CFG.chooseProvinceMode = true;
        CFG.chosenProvinceID = -1;
        this.updateHighlight_MoveUnitsProvinces(nProvinceID);
    }
    
    protected final void chooseProvinceMode() {
        if (CFG.regroupArmyMode) {
            this.resetRegroupArmyData();
        }
        this.autoChooseProvinceMode(this.getActiveProvinceID());
        if (this.getActiveProvinceID() < 0) {
            return;
        }
        CFG.menuManager.setVisible_InGame_ActionInfo_ChooseProvince();
        this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION = false;
        this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
    }
    
    protected final void regroupArmyMode() {
        CFG.regroupArmyMode = true;
        CFG.chosenProvinceID = -1;
        CFG.menuManager.setVisible_InGame_ActionInfo_RegroupArmy();
    }
    
    private final void resetActiveProvincesINFO() {
        this.lHighlightedProvinces_MoveUnits_Lines.clear();
        this.lCurrentRegroupArmyLine.clear();
        this.currentMoveUnitsLine = null;
        if (CFG.chooseProvinceMode) {
            CFG.chooseProvinceMode = false;
        }
        if (CFG.regroupArmyMode) {
            CFG.regroupArmyMode = false;
        }
        CFG.migrateMode = false;
        CFG.chosenProvinceID = -1;
    }
    
    protected final void resetLastActiveProvince() {
        if (this.iHighlightedProvincesSize > 0) {
            this.disableHighlightedProvinces();
            this.lHighlightedProvinces.clear();
            this.iHighlightedProvincesSize = 0;
        }
        this.lHighlightedProvinces_MoveUnits_Lines.clear();
        this.lCurrentRegroupArmyLine.clear();
        this.currentMoveUnitsLine = null;
    }
    
    protected final void addHighlightProvince_SelectProvinceMode(final int nProvinceID) {
        this.lHighlightedProvinces.add(nProvinceID);
        this.iHighlightedProvincesSize = this.lHighlightedProvinces.size();
        Gdx.app.log("AoC", "" + this.lHighlightedProvinces.size());
        this.fDashedLine_Percentage_HighlitedProvinceBorder = 5.0f;
        this.lDashedLineTime_Percentage_HighlitedProvinceBorder = System.currentTimeMillis();
        this.highlightedProvinceBorder_Update = true;
        this.highlightedProvince_AnimationData.resetData();
        this.enableHighlightedProvinces_Percentage();
    }
    
    protected final void updateHighlight_MoveUnitsProvinces(final int nProvinceID) {
        if (this.iHighlightedProvincesSize > 0) {
            this.disableHighlightedProvinces();
            this.lHighlightedProvinces.clear();
            this.iHighlightedProvincesSize = 0;
        }
        this.lHighlightedProvinces_MoveUnits_Lines.clear();
        if (CFG.game.getProvince(nProvinceID).getSeaProvince()) {
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if ((CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getSeaProvince() || (CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0 && (CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == 0 || CFG.game.getCiv(CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() == CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() || (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()).getAllianceID()) || CFG.game.getMilitaryAccess(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) > 0 || (int)CFG.game.getCivRelation_OfCivB(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) == -100) && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= CFG.gameAction.costOfMoveArmy(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i))))) && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= CFG.gameAction.costOfMoveArmy(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i))) && DiplomacyManager.canMoveToNaighbooringProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                    this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                    this.addHighlightedProvince_MoveUnits_Line(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i));
                }
            }
        }
        else if (CFG.migrateMode) {
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (uncivilizedCanMigrate(this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                    this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                    this.addHighlightedProvince_MoveUnits_Line_Migrate(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i));
                }
            }
        }
        else if (CFG.game.getProvince(nProvinceID).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > 0) {
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0 && (CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == 0 || CFG.game.getCiv(CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getPuppetOfCivID() == CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() || (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()).getAllianceID()) || CFG.game.getMilitaryAccess(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) > 0 || (int)CFG.game.getCivRelation_OfCivB(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.game.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) == -100) && (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= CFG.gameAction.costOfMoveArmy(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i))) && DiplomacyManager.canMoveToNaighbooringProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                    this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                    this.addHighlightedProvince_MoveUnits_Line(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i));
                }
            }
            if (this.getProvince(nProvinceID).getLevelOfPort() > 0) {
                for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ++i) {
                    if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= CFG.gameAction.costOfMoveArmy(nProvinceID, this.getProvince(nProvinceID).getNeighboringSeaProvinces(i), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) || CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringSeaProvinces(i))) {
                        this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringSeaProvinces(i));
                        this.addHighlightedProvince_MoveUnits_Line(nProvinceID, this.getProvince(nProvinceID).getNeighboringSeaProvinces(i));
                    }
                }
            }
        }
        else {
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i))) {
                    this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                    this.addHighlightedProvince_MoveUnits_Line(nProvinceID, this.getProvince(nProvinceID).getNeighboringProvinces(i));
                }
            }
            if (this.getProvince(nProvinceID).getLevelOfPort() > 0) {
                for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ++i) {
                    if (CFG.gameAction.getIsFreeMove(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nProvinceID, this.getProvince(nProvinceID).getNeighboringSeaProvinces(i))) {
                        this.lHighlightedProvinces.add(this.getProvince(nProvinceID).getNeighboringSeaProvinces(i));
                        this.addHighlightedProvince_MoveUnits_Line(nProvinceID, this.getProvince(nProvinceID).getNeighboringSeaProvinces(i));
                    }
                }
            }
        }
        this.iHighlightedProvincesSize = this.lHighlightedProvinces.size();
        this.fDashedLine_Percentage_HighlitedProvinceBorder = 5.0f;
        this.lDashedLineTime_Percentage_HighlitedProvinceBorder = System.currentTimeMillis();
        this.highlightedProvinceBorder_Update = true;
        this.highlightedProvince_AnimationData.resetData();
        this.enableHighlightedProvinces_Percentage();
        this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
        this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION = false;
    }
    
    private final void disableHighlightedProvinces() {
        for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
            if (this.getProvince(this.lHighlightedProvinces.get(i)).getSeaProvince()) {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getSeaProvince()) {
                                if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                    this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersSeaBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorderSeaBySea();
                                }
                                else {
                                    this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersSeaBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorderSeaBySea();
                                }
                            }
                            else if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.removeDrawProvinceBorder_LandBySea(this.lHighlightedProvinces.get(i), this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea_ID(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)));
                            }
                            else {
                                this.removeDrawProvinceBorder_LandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j), this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandBySea_ID(this.lHighlightedProvinces.get(i)));
                            }
                        }
                    }
                }
            }
            else {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandByLand(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder(this.lHighlightedProvinces.get(i));
                            }
                            else {
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandByLand(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j));
                            }
                        }
                    }
                }
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)) {
                                this.removeDrawProvinceBorder_LandBySea(this.lHighlightedProvinces.get(i), this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea_ID(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)));
                            }
                            else {
                                this.removeDrawProvinceBorder_LandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j), this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).getProvinceBordersLandBySea_ID(this.lHighlightedProvinces.get(i)));
                            }
                        }
                    }
                }
            }
        }
        this.lTIME_HIGHLIGHTED_CITIES = System.currentTimeMillis();
        this.HIGHLIGHTED_CITIES_DISABLE_ANIMATION = true;
        if (this.iActiveProvince < 0) {
            return;
        }
        if (this.getProvince(this.iActiveProvince).getLevelOfPort() == 0) {
            for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringSeaProvincesSize(); ++i) {
                if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)) {
                    this.removeDrawProvinceBorder_LandBySea(this.iActiveProvince, this.getProvince(this.iActiveProvince).getProvinceBordersLandBySea_ID(this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)));
                }
                else {
                    this.removeDrawProvinceBorder_LandBySea(this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)).getProvinceBordersLandBySea_ID(this.iActiveProvince));
                }
            }
        }
        if (this.getProvince(this.iActiveProvince).getSeaProvince()) {
            for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++i) {
                if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                    if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                        this.getProvince(this.iActiveProvince).getProvinceBordersSeaBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder_ActiveSea();
                    }
                }
                else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                    this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersSeaBySea(this.iActiveProvince).updateDrawProvinceBorder_ActiveSea();
                }
            }
        }
        else {
            for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++i) {
                boolean found = false;
                for (int l = 0; l < this.iHighlightedProvincesSize; ++l) {
                    if (this.getProvince(this.iActiveProvince).getNeighboringProvinces(i) == this.lHighlightedProvinces.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                        this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder_Active();
                    }
                    else {
                        this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.iActiveProvince).updateDrawProvinceBorder_Active();
                    }
                }
            }
        }
    }
    
    private final void enableHighlightedProvinces_Percentage() {
        for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
            if (this.getProvince(this.lHighlightedProvinces.get(i)).getSeaProvince()) {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getSeaProvince()) {
                                if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                    this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersSeaBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits_Percentage_Sea();
                                }
                                else {
                                    this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersSeaBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Percentage_Sea();
                                }
                            }
                            else if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.addDrawProvinceBorder_LandBySea(this.lHighlightedProvinces.get(i), this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea_ID(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)));
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                            }
                            else {
                                this.addDrawProvinceBorder_LandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j), this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandBySea_ID(this.lHighlightedProvinces.get(i)));
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                            }
                        }
                    }
                }
            }
            else {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandByLand(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits_Percentage();
                            }
                            else {
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandByLand(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Percentage();
                            }
                        }
                    }
                }
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)) {
                                this.addDrawProvinceBorder_LandBySea(this.lHighlightedProvinces.get(i), this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea_ID(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)));
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                            }
                            else {
                                this.addDrawProvinceBorder_LandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j), this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).getProvinceBordersLandBySea_ID(this.lHighlightedProvinces.get(i)));
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).getProvinceBordersLandBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                            }
                        }
                    }
                }
            }
        }
        try {
            if (this.getProvince(this.getActiveProvinceID()).getLevelOfPort() == 0) {
                for (int i = 0; i < this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvincesSize(); ++i) {
                    if (this.getActiveProvinceID() < this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)) {
                        this.addDrawProvinceBorder_LandBySea(this.getActiveProvinceID(), this.getProvince(this.getActiveProvinceID()).getProvinceBordersLandBySea_ID(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)));
                        this.getProvince(this.getActiveProvinceID()).getProvinceBordersLandBySea(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                    }
                    else {
                        this.addDrawProvinceBorder_LandBySea(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i), this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)).getProvinceBordersLandBySea_ID(this.getActiveProvinceID()));
                        this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringSeaProvinces(i)).getProvinceBordersLandBySea(this.getActiveProvinceID()).updateDrawProvinceBorder_MoveUnits_Percentage_LandBySea();
                    }
                }
            }
            if (this.getProvince(this.getActiveProvinceID()).getSeaProvince()) {
                for (int i = 0; i < this.getProvince(this.getActiveProvinceID()).getNeighboringProvincesSize(); ++i) {
                    if (this.getActiveProvinceID() < this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)) {
                        if (this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).getSeaProvince()) {
                            this.getProvince(this.getActiveProvinceID()).getProvinceBordersSeaBySea(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).updateDrawProvinceBorder_ActiveSeaBySea_Percentage();
                        }
                        else {
                            boolean canMoveTo = false;
                            for (int o = this.lHighlightedProvinces.size() - 1; o >= 0; --o) {
                                if (this.lHighlightedProvinces.get(o).equals(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i))) {
                                    canMoveTo = true;
                                    break;
                                }
                            }
                            if (canMoveTo) {
                                this.getProvince(this.getActiveProvinceID()).getProvinceBordersLandBySea(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).updateDrawProvinceBorder_ActiveLandBySea_Percentage();
                            }
                        }
                    }
                    else if (this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).getSeaProvince()) {
                        this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).getProvinceBordersSeaBySea(this.getActiveProvinceID()).updateDrawProvinceBorder_ActiveSeaBySea_Percentage();
                    }
                    else {
                        boolean canMoveTo = false;
                        for (int o = this.lHighlightedProvinces.size() - 1; o >= 0; --o) {
                            if (this.lHighlightedProvinces.get(o).equals(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i))) {
                                canMoveTo = true;
                                break;
                            }
                        }
                        if (canMoveTo) {
                            this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).getProvinceBordersLandBySea(this.getActiveProvinceID()).updateDrawProvinceBorder_ActiveLandBySea_Percentage();
                        }
                    }
                }
            }
            else {
                int i = 0;
                final int jSize = this.iHighlightedProvincesSize;
                while (i < this.getProvince(this.getActiveProvinceID()).getNeighboringProvincesSize()) {
                    boolean found = false;
                    for (int l = 0; l < jSize; ++l) {
                        if (this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i) == this.lHighlightedProvinces.get(l)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        if (this.getActiveProvinceID() < this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)) {
                            this.getProvince(this.getActiveProvinceID()).getProvinceBordersLandByLand(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).updateDrawProvinceBorder_Active_Percentage();
                        }
                        else {
                            this.getProvince(this.getProvince(this.getActiveProvinceID()).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.getActiveProvinceID()).updateDrawProvinceBorder_Active_Percentage();
                        }
                    }
                    ++i;
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    private final void enableHighlightedProvinces_Classic() {
        for (int i = 0; i < this.iHighlightedProvincesSize; ++i) {
            if (this.getProvince(this.lHighlightedProvinces.get(i)).getSeaProvince()) {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getSeaProvince()) {
                                if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                    this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersSeaBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits_Sea();
                                }
                                else {
                                    this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersSeaBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Sea();
                                }
                            }
                            else if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits_Sea();
                            }
                            else {
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Sea();
                            }
                        }
                    }
                }
            }
            else {
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandByLand(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).updateDrawProvinceBorder_MoveUnits();
                            }
                            else {
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringProvinces(j)).getProvinceBordersLandByLand(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits();
                            }
                        }
                    }
                }
                for (int j = 0; j < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvincesSize(); ++j) {
                    boolean updatePB = true;
                    for (int k = 0; k < this.iHighlightedProvincesSize; ++k) {
                        if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) == this.lHighlightedProvinces.get(k)) {
                            updatePB = false;
                            break;
                        }
                    }
                    if (this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j) != this.getActiveProvinceID()) {
                        if (updatePB) {
                            if (this.lHighlightedProvinces.get(i) < this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)) {
                                this.getProvince(this.lHighlightedProvinces.get(i)).getProvinceBordersLandBySea(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).updateDrawProvinceBorder_MoveUnits_Sea();
                            }
                            else {
                                this.getProvince(this.getProvince(this.lHighlightedProvinces.get(i)).getNeighboringSeaProvinces(j)).getProvinceBordersLandBySea(this.lHighlightedProvinces.get(i)).updateDrawProvinceBorder_MoveUnits_Sea();
                            }
                        }
                    }
                }
            }
        }
        if (this.getProvince(this.iActiveProvince).getLevelOfPort() == 0) {
            for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringSeaProvincesSize(); ++i) {
                if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)) {
                    this.getProvince(this.iActiveProvince).getProvinceBordersLandBySea(this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)).updateDrawProvinceBorder_MoveUnits_Sea();
                }
                else {
                    this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringSeaProvinces(i)).getProvinceBordersLandBySea(this.iActiveProvince).updateDrawProvinceBorder_MoveUnits_Sea();
                }
            }
        }
        if (this.getProvince(this.iActiveProvince).getSeaProvince()) {
            for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++i) {
                if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                    if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                        this.getProvince(this.iActiveProvince).getProvinceBordersSeaBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorderSeaBySea();
                    }
                }
                else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                    this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersSeaBySea(this.iActiveProvince).updateDrawProvinceBorderSeaBySea();
                }
            }
        }
        else {
            int i = 0;
            final int jSize = this.iHighlightedProvincesSize;
            while (i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize()) {
                boolean found = false;
                for (int l = 0; l < jSize; ++l) {
                    if (this.getProvince(this.iActiveProvince).getNeighboringProvinces(i) == this.lHighlightedProvinces.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                        this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder(this.iActiveProvince);
                    }
                    else {
                        this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.iActiveProvince).updateDrawProvinceBorder(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                    }
                }
                ++i;
            }
        }
    }
    
    protected final void buildWars() {
        this.lWars = new ArrayList<War_GameData>();
        this.iWarsSize = 0;
        this.lPeaceTreaties = new ArrayList<PeaceTreaty_GameData_MessageData>();
    }
    
    protected War_GameData getWar(final int i) {
        return this.lWars.get(i);
    }
    
    protected final void removeWarData(final int nWarID) {
        this.lWars.remove(nWarID);
        this.iWarsSize = this.lWars.size();
    }
    
    protected final void addWarData(final int nAggressor, final int nDefender) {
        if (nAggressor == 0 || nDefender == 0) {
            return;
        }
        for (int i = 0; i < this.getWarsSize(); ++i) {
            for (int j = 0; j < this.lWars.get(i).getAggressorsSize(); ++j) {
                if (this.lWars.get(i).getAggressorID(j).getCivID() == nAggressor) {
                    for (int k = 0; k < this.lWars.get(i).getDefendersSize(); ++k) {
                        if (this.lWars.get(i).getDefenderID(k).getCivID() == nDefender) {
                            return;
                        }
                    }
                }
                else if (this.lWars.get(i).getAggressorID(j).getCivID() == nDefender) {
                    for (int k = 0; k < this.lWars.get(i).getDefendersSize(); ++k) {
                        if (this.lWars.get(i).getDefenderID(k).getCivID() == nAggressor) {
                            return;
                        }
                    }
                }
            }
            for (int j = 0; j < this.lWars.get(i).getDefendersSize(); ++j) {
                if (this.lWars.get(i).getDefenderID(j).getCivID() == nAggressor) {
                    for (int k = 0; k < this.lWars.get(i).getAggressorsSize(); ++k) {
                        if (this.lWars.get(i).getAggressorID(k).getCivID() == nDefender) {
                            return;
                        }
                    }
                }
                else if (this.lWars.get(i).getDefenderID(j).getCivID() == nDefender) {
                    for (int k = 0; k < this.lWars.get(i).getAggressorsSize(); ++k) {
                        if (this.lWars.get(i).getAggressorID(k).getCivID() == nAggressor) {
                            return;
                        }
                    }
                }
            }
        }
        if (this.getCiv(nAggressor).getAllianceID() > 0) {
            for (int i = 0; i < this.getAlliance(this.getCiv(nAggressor).getAllianceID()).getCivilizationsSize(); ++i) {
                if (this.getAlliance(this.getCiv(nAggressor).getAllianceID()).getCivilization(i) != nAggressor) {
                    for (int j = 0; j < this.getWarsSize(); ++j) {
                        if (this.lWars.get(j).getIsAggressor(this.getAlliance(this.getCiv(nAggressor).getAllianceID()).getCivilization(i))) {
                            if (this.lWars.get(j).getIsDefender(nDefender)) {
                                this.lWars.get(j).addAggressor(nAggressor);
                                return;
                            }
                        }
                        else if (this.lWars.get(j).getIsDefender(this.getAlliance(this.getCiv(nAggressor).getAllianceID()).getCivilization(i)) && this.lWars.get(j).getIsAggressor(nDefender)) {
                            this.lWars.get(j).addDefender(nAggressor);
                            return;
                        }
                    }
                }
            }
        }
        if (this.getCiv(nDefender).getAllianceID() > 0) {
            for (int i = 0; i < this.getAlliance(this.getCiv(nDefender).getAllianceID()).getCivilizationsSize(); ++i) {
                if (this.getAlliance(this.getCiv(nDefender).getAllianceID()).getCivilization(i) != nDefender) {
                    for (int j = 0; j < this.getWarsSize(); ++j) {
                        if (this.lWars.get(j).getIsAggressor(this.getAlliance(this.getCiv(nDefender).getAllianceID()).getCivilization(i))) {
                            if (this.lWars.get(j).getIsDefender(nAggressor)) {
                                this.lWars.get(j).addAggressor(nDefender);
                                return;
                            }
                        }
                        else if (this.lWars.get(j).getIsDefender(this.getAlliance(this.getCiv(nDefender).getAllianceID()).getCivilization(i)) && this.lWars.get(j).getIsAggressor(nAggressor)) {
                            this.lWars.get(j).addDefender(nDefender);
                            return;
                        }
                    }
                }
            }
        }
        this.lWars.add(new War_GameData(nAggressor, nDefender));
        this.iWarsSize = this.lWars.size();
    }
    
    protected final int getWarsSize() {
        return this.iWarsSize;
    }
    
    protected final void updateWarStatistics(int iWarID, final int iCivA, final int iCivB, final int iCivilianDeaths, final int iEconomicLosses) {
        if (iCivA == 0 || iCivB == 0) {
            return;
        }
        if (iWarID < 0) {
            this.addWarData(iCivA, iCivB);
            iWarID = this.getWarID(iCivA, iCivB);
        }
        this.getWar(iWarID).addCivilianEconomicLosses(iCivB, iCivilianDeaths, iEconomicLosses);
    }
    
    protected final void updateWarStatistics_Casualties(int iWarID, final int iCivA, final int iCivB, final int iCasualties) {
        if (iCivA == 0 || iCivB == 0) {
            return;
        }
        if (iWarID < 0) {
            this.addWarData(iCivA, iCivB);
            iWarID = this.getWarID(iCivA, iCivB);
        }
        this.getWar(iWarID).addCasualties(iCivB, iCasualties);
    }
    
    protected final void updateWarStatistics_ConqueredProvinces(int iWarID, final int iCivA, final int iCivB) {
        if (iCivA == 0 || iCivB == 0) {
            return;
        }
        if (iWarID < 0) {
            this.addWarData(iCivA, iCivB);
            iWarID = this.getWarID(iCivA, iCivB);
        }
        this.getWar(iWarID).addConqueredProvinces(iCivA);
    }
    
    protected final int getWarID(final int iCivA, final int iCivB) {
        if (iCivA == 0 || iCivB == 0) {
            return -1;
        }
        for (int i = 0; i < this.getWarsSize(); ++i) {
            for (int j = 0; j < this.getWar(i).getAggressorsSize(); ++j) {
                if (this.getWar(i).getAggressorID(j).getCivID() == iCivA) {
                    for (int k = 0; k < this.getWar(i).getDefendersSize(); ++k) {
                        if (this.getWar(i).getDefenderID(k).getCivID() == iCivB) {
                            return i;
                        }
                    }
                }
                else if (this.getWar(i).getAggressorID(j).getCivID() == iCivB) {
                    for (int k = 0; k < this.getWar(i).getDefendersSize(); ++k) {
                        if (this.getWar(i).getDefenderID(k).getCivID() == iCivA) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    protected final void buildAlliances() {
        (this.lAlliances = new ArrayList<Alliance>()).add(new Alliance(""));
        this.iAlliancesSize = this.lAlliances.size();
    }
    
    protected final void buildAlliances(final List<Alliance> nAlliances) {
        this.lAlliances = new ArrayList<Alliance>();
        this.lAlliances.addAll(nAlliances);
        this.iAlliancesSize = this.lAlliances.size();
    }
    
    protected final Alliance getAlliance(final int iID) {
        return this.lAlliances.get(iID);
    }
    
    protected final int countAlliance_Provinces(final int iID) {
        int out = 0;
        for (int i = 0; i < this.getAlliance(iID).getCivilizationsSize(); ++i) {
            out += this.getCiv(this.getAlliance(iID).getCivilization(i)).getNumOfProvinces();
        }
        return out;
    }
    
    protected final int countAlliance_Population(final int iID) {
        int out = 0;
        for (int i = 0; i < this.getAlliance(iID).getCivilizationsSize(); ++i) {
            out += this.getCiv(this.getAlliance(iID).getCivilization(i)).countPopulation();
        }
        return out;
    }
    
    protected final int countAlliance_Economy(final int iID) {
        int out = 0;
        for (int i = 0; i < this.getAlliance(iID).getCivilizationsSize(); ++i) {
            out += this.getCiv(this.getAlliance(iID).getCivilization(i)).countEconomy();
        }
        return out;
    }
    
    protected final void addAlliance(final String sAllianceName) {
        this.lAlliances.add(new Alliance(sAllianceName.equals("") ? CFG.getRandomAllianceName(0) : sAllianceName));
        this.iAlliancesSize = this.lAlliances.size();
    }
    
    protected final void checkAlliances() {
        for (int i = this.lAlliances.size() - 1; i > 0; --i) {
            if (this.lAlliances.get(i).getCivilizationsSize() == 0) {
                this.lAlliances.remove(i);
                for (int j = 1; j < this.iCivsSize; ++j) {
                    if (this.getCiv(j).getAllianceID() >= i) {
                        this.getCiv(j).setAllianceID(this.getCiv(j).getAllianceID() - 1);
                    }
                }
            }
        }
        this.iAlliancesSize = this.lAlliances.size();
    }
    
    protected final int getAlliancesSize() {
        return this.iAlliancesSize;
    }
    
    protected final int countWorld_Population() {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getWasteland() < 0 && !this.getProvince(i).getSeaProvince()) {
                out += this.getProvince(i).getPopulationData().getPopulation();
            }
        }
        return out;
    }
    
    private final boolean checkClosedSea(final int nProvinceID) {
        for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ++i) {
            if (this.getProvince(this.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getLevelOfPort() == -2) {
                return true;
            }
        }
        return false;
    }
    
    protected final void setWasteland(final int nProvinceID, final boolean wasteland) {
        if (wasteland) {
            if (this.getProvince(nProvinceID).getWasteland() >= 0 != wasteland) {
                CFG.addUndoWastelandProvince(nProvinceID);
            }
            this.getProvince(nProvinceID).setWasteland(0);
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                    if (this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getNeighboringSeaProvincesSize() <= 0 || !this.checkClosedSea(nProvinceID)) {
                        final List<Integer> nWastelandProvinces = new ArrayList<Integer>();
                        nWastelandProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                        this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).setWasteland(0);
                        this.breakWasteland = false;
                        this.checkWastelandProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i), nWastelandProvinces);
                    }
                }
            }
        }
        else if (this.getProvince(nProvinceID).getNeighboringSeaProvincesSize() > 0 && this.checkClosedSea(nProvinceID)) {
            if (this.getProvince(nProvinceID).getWasteland() >= 0 != wasteland) {
                CFG.addUndoWastelandProvince(nProvinceID);
            }
            this.getProvince(nProvinceID).setWasteland(-1);
        }
        else {
            for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                if (this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                    if (this.getProvince(nProvinceID).getWasteland() >= 0 != wasteland) {
                        CFG.addUndoWastelandProvince(nProvinceID);
                    }
                    this.getProvince(nProvinceID).setWasteland(-1);
                    return;
                }
            }
        }
    }
    
    private final void checkWastelandProvince(final int nProvinceID, final List<Integer> nWastelandProvinces) {
        if (this.breakWasteland) {
            for (int a = nWastelandProvinces.size() - 1; a >= 0; --a) {
                this.getProvince(nWastelandProvinces.get(a)).setWasteland(-1);
            }
            return;
        }
        for (int i = 0; i < this.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                if (this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).getNeighboringSeaProvincesSize() > 0 && this.checkClosedSea(nProvinceID)) {
                    for (int a2 = nWastelandProvinces.size() - 1; a2 >= 0; --a2) {
                        this.getProvince(nWastelandProvinces.get(a2)).setWasteland(-1);
                    }
                    this.breakWasteland = true;
                }
                else {
                    this.getProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i)).setWasteland(0);
                    nWastelandProvinces.add(this.getProvince(nProvinceID).getNeighboringProvinces(i));
                    this.checkWastelandProvince(this.getProvince(nProvinceID).getNeighboringProvinces(i), nWastelandProvinces);
                }
            }
        }
    }
    
    protected final void checkProvinceConnections() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            for (int j = 0; j < this.getProvince(i).getNeighboringProvincesSize(); ++j) {
                boolean found = false;
                for (int k = 0; k < this.getProvince(this.getProvince(i).getNeighboringProvinces(j)).getNeighboringProvincesSize(); ++k) {
                    if (i == this.getProvince(this.getProvince(i).getNeighboringProvinces(j)).getNeighboringProvinces(k)) {
                        found = true;
                        break;
                    }
                }
                for (int k = 0; k < this.getProvince(this.getProvince(i).getNeighboringProvinces(j)).getNeighboringSeaProvincesSize(); ++k) {
                    if (i == this.getProvince(this.getProvince(i).getNeighboringProvinces(j)).getNeighboringSeaProvinces(k)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Gdx.app.log("AoC", "checkProvinceConnections: " + i + " - " + this.getProvince(i).getNeighboringProvinces(j));
                }
            }
        }
    }
    
    protected final void checkProvince_UselessPoints() {
        int uselessPoints = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            for (int j = 1; j < this.getProvince(i).getPointsSize() - 1; ++j) {
                if (this.getProvince(i).getPointsY(j - 1) == this.getProvince(i).getPointsY(j + 1) && this.getProvince(i).getPointsY(j - 1) == this.getProvince(i).getPointsY(j)) {
                    ++uselessPoints;
                    final List<Short> tempPointsX = new ArrayList<Short>();
                    final List<Short> tempPointsY = new ArrayList<Short>();
                    for (int a = 0; a < this.getProvince(i).getPointsSize(); ++a) {
                        if (a != j) {
                            tempPointsX.add((short)((short)this.getProvince(i).getPointsX(a) / CFG.map.getMapBG().getMapScale()));
                            tempPointsY.add((short)((short)this.getProvince(i).getPointsY(a) / CFG.map.getMapBG().getMapScale()));
                        }
                    }
                    this.getProvince(i).setPoints(tempPointsX, tempPointsY);
                    --j;
                    this.buildGameProvinceData(i);
                }
                else if (this.getProvince(i).getPointsX(j - 1) == this.getProvince(i).getPointsX(j + 1) && this.getProvince(i).getPointsX(j - 1) == this.getProvince(i).getPointsX(j)) {
                    ++uselessPoints;
                    final List<Short> tempPointsX = new ArrayList<Short>();
                    final List<Short> tempPointsY = new ArrayList<Short>();
                    for (int a = 0; a < this.getProvince(i).getPointsSize(); ++a) {
                        if (a != j) {
                            tempPointsX.add((short)((short)this.getProvince(i).getPointsX(a) / CFG.map.getMapBG().getMapScale()));
                            tempPointsY.add((short)((short)this.getProvince(i).getPointsY(a) / CFG.map.getMapBG().getMapScale()));
                        }
                    }
                    this.getProvince(i).setPoints(tempPointsX, tempPointsY);
                    --j;
                    this.buildGameProvinceData(i);
                }
                else if (Math.max(this.getProvince(i).getPointsX(j - 1), this.getProvince(i).getPointsX(j + 1)) - Math.min(this.getProvince(i).getPointsX(j - 1), this.getProvince(i).getPointsX(j + 1)) == Math.max(this.getProvince(i).getPointsY(j - 1), this.getProvince(i).getPointsY(j + 1)) - Math.min(this.getProvince(i).getPointsY(j - 1), this.getProvince(i).getPointsY(j + 1)) && Math.max(this.getProvince(i).getPointsX(j - 1), this.getProvince(i).getPointsX(j)) - Math.min(this.getProvince(i).getPointsX(j - 1), this.getProvince(i).getPointsX(j)) == Math.max(this.getProvince(i).getPointsY(j - 1), this.getProvince(i).getPointsY(j)) - Math.min(this.getProvince(i).getPointsY(j - 1), this.getProvince(i).getPointsY(j))) {
                    ++uselessPoints;
                    final List<Short> tempPointsX = new ArrayList<Short>();
                    final List<Short> tempPointsY = new ArrayList<Short>();
                    for (int a = 0; a < this.getProvince(i).getPointsSize(); ++a) {
                        if (a != j) {
                            tempPointsX.add((short)((short)this.getProvince(i).getPointsX(a) / CFG.map.getMapBG().getMapScale()));
                            tempPointsY.add((short)((short)this.getProvince(i).getPointsY(a) / CFG.map.getMapBG().getMapScale()));
                        }
                    }
                    this.getProvince(i).setPoints(tempPointsX, tempPointsY);
                    --j;
                    this.buildGameProvinceData(i);
                }
            }
        }
        Gdx.app.log("AoC", "uselessPoints2: " + uselessPoints);
    }
    
    protected final void deleteSeaPaths() {
        final List<String> tempL = CFG.getFileNames("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "sea_routes/");
        for (String s : tempL) {
            Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "sea_routes/" + s).delete();
        }
    }
    
    protected final void buildSeaPaths() {
        final List<Boolean> was = new ArrayList<Boolean>();
        for (int j = 0; j < this.getProvincesSize(); ++j) {
            was.add(false);
        }
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getSeaProvince()) {
                boolean seaFounds = false;
                boolean landFound = false;
                for (int k = 0; k < this.getProvince(i).getNeighboringProvincesSize(); ++k) {
                    if (this.getProvince(this.getProvince(i).getNeighboringProvinces(k)).getNeighboringSeaProvincesSize() > 0) {
                        landFound = true;
                    }
                    else {
                        seaFounds = true;
                    }
                    if (landFound && seaFounds) {
                        break;
                    }
                }
                if (landFound) {
                    if (seaFounds) {
                        for (int k = i + 1; k < this.getProvincesSize(); ++k) {
                            seaFounds = false;
                            landFound = false;
                            for (int l = 0; l < this.getProvince(k).getNeighboringProvincesSize(); ++l) {
                                if (this.getProvince(this.getProvince(k).getNeighboringProvinces(l)).getSeaProvince()) {
                                    seaFounds = true;
                                }
                                if (this.getProvince(this.getProvince(k).getNeighboringProvinces(l)).getNeighboringSeaProvincesSize() > 0) {
                                    landFound = true;
                                }
                                if (landFound && seaFounds) {
                                    break;
                                }
                            }
                            if (seaFounds) {
                                if (landFound) {
                                    for (int l = 0; l < this.getProvincesSize(); ++l) {
                                        was.set(l, false);
                                    }
                                    was.set(i, true);
                                    final List<Integer> in = new ArrayList<Integer>();
                                    final List<List<Integer>> inPath = new ArrayList<List<Integer>>();
                                    for (int m = 0; m < this.getProvince(i).getNeighboringProvincesSize(); ++m) {
                                        if (this.getProvince(this.getProvince(i).getNeighboringProvinces(m)).getSeaProvince()) {
                                            in.add(this.getProvince(this.getProvince(i).getNeighboringProvinces(m)).getProvinceID());
                                            final List<Integer> tP = new ArrayList<Integer>();
                                            tP.add(this.getProvince(this.getProvince(i).getNeighboringProvinces(m)).getProvinceID());
                                            inPath.add(tP);
                                            was.set(this.getProvince(this.getProvince(i).getNeighboringProvinces(m)).getProvinceID(), true);
                                        }
                                    }
                                    this.buildSeaPath(was, in, inPath, i, k);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private final boolean buildSeaPath(final List<Boolean> was, final List<Integer> in, final List<List<Integer>> inPath, final int from, final int lookingFor) {
        final List<Integer> nIN = new ArrayList<Integer>();
        final List<List<Integer>> nINPath = new ArrayList<List<Integer>>();
        for (Integer integer : in) {
            if (this.getProvince(integer).getProvinceID() == lookingFor) {
                return true;
            }
        }
        for (int i = 0; i < in.size(); ++i) {
            for (int j = 0; j < this.getProvince(in.get(i)).getNeighboringProvincesSize(); ++j) {
                if (!was.get(this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID()) && this.getProvince(this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID()).getSeaProvince()) {
                    if (this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID() == lookingFor) {
                        this.showPath(from, lookingFor, inPath.get(i));
                        return true;
                    }
                    nIN.add(this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID());
                    final List<Integer> tPL = new ArrayList<Integer>();
                    tPL.addAll(inPath.get(i));
                    tPL.add(this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID());
                    nINPath.add(tPL);
                    was.set(this.getProvince(this.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID(), true);
                }
            }
        }
        try {
            return this.buildSeaPath(was, nIN, nINPath, from, lookingFor);
        }
        catch (final StackOverflowError ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    private final void showPath(final int p1, final int p2, final List<Integer> lPath) {
        java.lang.StringBuilder pr = new StringBuilder();
        for (int i = 0; i < lPath.size(); ++i) {
            pr.append(lPath.get(i)).append((lPath.size() - 1 == i) ? "" : ",");
        }
        Gdx.app.log("AoC", "*" + p1 + "|" + p2 + "~" + pr + "@");
        final FileHandle fileSave = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "sea_routes/" + p1 + "_" + p2);
        fileSave.writeString(pr.toString(), false);
    }

    protected final void buildGameProvinceData(final int i) {
        final List<Short> tempPointsX = new ArrayList<Short>();
        final List<Short> tempPointsY = new ArrayList<Short>();
        for (int j = 0; j < this.getProvince(i).getPointsSize(); ++j) {
            tempPointsX.add((short)(this.getProvince(i).getPointsX(j) / CFG.map.getMapBG().getMapScale()));
            tempPointsY.add((short)(this.getProvince(i).getPointsY(j) / CFG.map.getMapBG().getMapScale()));
        }
        List<Province_Border_GameData> tempProvinceBorder = new ArrayList<Province_Border_GameData>();
        for (int k = 0; k < this.getProvince(i).getProvinceBordersLandByLandSize(); ++k) {
            final List<Short> tempX = new ArrayList<Short>();
            final List<Short> tempY = new ArrayList<Short>();
            for (int l = 0; l < ((Province_Border)this.getProvince(i).getProvinceBordersLandByLand().get(k)).lPointsX.size(); ++l) {
                tempX.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(k)).lPointsX.get(l)));
                tempY.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersLandByLand().get(k)).lPointsY.get(l)));
            }
            tempProvinceBorder.add(new Province_Border_GameData(((Province_Border)this.getProvince(i).getProvinceBordersLandByLand().get(k)).getWithProvinceID(), tempX, tempY));
        }
        for (int k = 0; k < this.getProvince(i).getProvinceBordersLandBySeaSize(); ++k) {
            final List<Short> tempX = new ArrayList<Short>();
            final List<Short> tempY = new ArrayList<Short>();
            for (int l = 0; l < ((Province_Border)this.getProvince(i).getProvinceBordersLandBySea().get(k)).lPointsX.size(); ++l) {
                tempX.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersLandBySea().get(k)).lPointsX.get(l)));
                tempY.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersLandBySea().get(k)).lPointsY.get(l)));
            }
            tempProvinceBorder.add(new Province_Border_GameData(((Province_Border)this.getProvince(i).getProvinceBordersLandBySea().get(k)).getWithProvinceID(), tempX, tempY));
        }
        for (int k = 0; k < this.getProvince(i).getProvinceBordersSeaBySeaSize(); ++k) {
            final List<Short> tempX = new ArrayList<Short>();
            final List<Short> tempY = new ArrayList<Short>();
            for (int l = 0; l < ((Province_Border)this.getProvince(i).getProvinceBordersSeaBySea().get(k)).lPointsX.size(); ++l) {
                tempX.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersSeaBySea().get(k)).lPointsX.get(l)));
                tempY.add((short)(((Province_Border) this.getProvince(i).getProvinceBordersSeaBySea().get(k)).lPointsY.get(l)));
            }
            tempProvinceBorder.add(new Province_Border_GameData(((Province_Border)this.getProvince(i).getProvinceBordersSeaBySea().get(k)).getWithProvinceID(), tempX, tempY));
        }
        if (tempProvinceBorder.size() == 0) {
            tempProvinceBorder = null;
        }
        final List<Short> tempNeighboringProvinces = new ArrayList<Short>();
        final List<Short> tempNeighboringSeaProvinces = new ArrayList<Short>();
        for (int m = 0; m < this.getProvince(i).getNeighboringProvincesSize(); ++m) {
            tempNeighboringProvinces.add((short)this.getProvince(i).getNeighboringProvinces(m));
        }
        for (int m = 0; m < this.getProvince(i).getNeighboringSeaProvincesSize(); ++m) {
            tempNeighboringSeaProvinces.add((short)this.getProvince(i).getNeighboringSeaProvinces(m));
        }
        final Province_GameData2 provinceData = new Province_GameData2((this.getProvince(i).getLevelOfPort() > 0) ? 0 : ((this.getProvince(i).getNeighboringSeaProvincesSize() > 0) ? 0 : this.getProvince(i).getLevelOfPort()), tempPointsX, tempPointsY, tempProvinceBorder, tempNeighboringProvinces, tempNeighboringSeaProvinces);
        provinceData.provinceInfo.iContinentID = 1;
        provinceData.provinceInfo.fGrowthRate = 1.0f;
        final OutputStream osProvince = null;
        try {
            final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);
            fileProvince.writeBytes(CFG.serialize(provinceData), false);
        }
        catch (final IOException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            if (osProvince != null) {
                try {
                    osProvince.close();
                }
                catch (final Exception ex2) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex2);
                    }
                }
            }
        }
        finally {
            if (osProvince != null) {
                try {
                    osProvince.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
    }

    protected final void convertProvincesArmyPositionToAnotherScale(int nScale) {
        if (nScale != CFG.map.getMapScale(CFG.map.getActiveMapID()) && nScale > 0) {
            for(int i = 0; i < this.getProvincesSize(); ++i) {
                if (this.getProvince(i).getShiftX() != 0 || this.getProvince(i).getShiftY() != 0) {
                    int nX = this.getProvince(i).getShiftX() * nScale / CFG.map.getMapScale(CFG.map.getActiveMapID());
                    int nY = this.getProvince(i).getShiftY() * nScale / CFG.map.getMapScale(CFG.map.getActiveMapID());
                    FileHandle fileProvinceData = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);

                    try {
                        Province_GameData2 tData = (Province_GameData2)CFG.deserialize(fileProvinceData.readBytes());
                        tData.provinceInfo.iShiftX = nX;
                        tData.provinceInfo.iShiftY = nY;
                        OutputStream osProvince = null;

                        try {
                            FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);
                            fileProvince.writeBytes(CFG.serialize(tData), false);
                        } catch (IOException var19) {
                            if (CFG.LOGS) {
                                CFG.exceptionStack(var19);
                            }
                        } finally {
                            if (osProvince != null) {
                                try {
                                    osProvince.close();
                                } catch (Exception var20) {
                                    if (CFG.LOGS) {
                                        CFG.exceptionStack(var20);
                                    }
                                }
                            }

                        }
                    } catch (ClassNotFoundException | IOException | GdxRuntimeException var22) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(var22);
                        }
                    }
                }
            }

            CFG.toast.setInView(CFG.langManager.get("Done"));
        }
    }
    
    protected final void convertProvincesPortPositionToAnotherScale(final int nScale) {
        if (nScale == CFG.map.getMapScale(CFG.map.getActiveMapID()) || nScale <= 0) {
            return;
        }
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getPortShiftX() != 0 || this.getProvince(i).getPortShiftY() != 0) {
                final int nX = this.getProvince(i).getPortShiftX() * nScale / CFG.map.getMapScale(CFG.map.getActiveMapID());
                final int nY = this.getProvince(i).getPortShiftY() * nScale / CFG.map.getMapScale(CFG.map.getActiveMapID());
                final FileHandle fileProvinceData = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);
                try {
                    final Province_GameData2 tData = (Province_GameData2)CFG.deserialize(fileProvinceData.readBytes());
                    tData.iPort_ShiftX = nX;
                    tData.iPort_ShiftY = nY;
                    final OutputStream osProvince = null;
                    try {
                        final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "provinces/" + i);
                        fileProvince.writeBytes(CFG.serialize(tData), false);
                    }
                    catch (final IOException ex) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex);
                        }
                        if (osProvince != null) {
                            try {
                                osProvince.close();
                            }
                            catch (final Exception ex2) {
                                if (CFG.LOGS) {
                                    CFG.exceptionStack(ex2);
                                }
                            }
                        }
                    }
                    finally {
                        if (osProvince != null) {
                            try {
                                osProvince.close();
                            }
                            catch (final Exception ex3) {
                                if (CFG.LOGS) {
                                    CFG.exceptionStack(ex3);
                                }
                            }
                        }
                    }
                }
                catch (final ClassNotFoundException | GdxRuntimeException | IOException e) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(e);
                    }
                }
            }
        }
        CFG.toast.setInView(CFG.langManager.get("Done"));
    }
    
    protected final void updateLevelOfPort() {
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince()) {
                if (this.getProvince(i).getNeighboringSeaProvincesSize() > 0) {
                    this.getProvince(i).setLevelOfPort(1);
                }
                else {
                    this.getProvince(i).setLevelOfPort(-1);
                }
                this.buildGameProvinceData(i);
            }
        }
    }
    
    protected final void saveScenario() {
        if (CFG.CREATE_SCENARIO_NAME == null || CFG.CREATE_SCENARIO_NAME.length() == 0) {
            CFG.CREATE_SCENARIO_NAME = "NO NAME";
        }
        if (CFG.CREATE_SCENARIO_AUTHOR == null || CFG.CREATE_SCENARIO_AUTHOR.length() == 0) {
            CFG.CREATE_SCENARIO_AUTHOR = "Anonymous";
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            try {
                if (CFG.game.getCiv(i).getCapitalProvinceID() < 0) {
                    if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                        CFG.game.getCiv(i).setCapitalProvinceID(CFG.game.getCiv(i).getProvinceID(0));
                    }
                    else {
                        boolean tRemove = true;
                        for (int j = 0; j < CFG.province_Cores_GameData.lProvinces.size(); ++j) {
                            for (int k = 0; k < CFG.province_Cores_GameData.lProvinces.get(j).lCores.size(); ++k) {
                                if (CFG.province_Cores_GameData.lProvinces.get(j).lCores.get(k).iCivID == i) {
                                    tRemove = false;
                                    j = CFG.province_Cores_GameData.lProvinces.size();
                                    break;
                                }
                            }
                        }
                        for (int j = 0; j < CFG.eventsManager.getEventsSize(); ++j) {
                            if (CFG.eventsManager.getEvent(j).getCivID() == i) {
                                tRemove = false;
                                break;
                            }
                            for (int k = 0; k < CFG.eventsManager.getEvent(j).lDecisions.size(); ++k) {
                                for (int o = 0; o < CFG.eventsManager.getEvent(j).lDecisions.get(k).lOutcomes.size(); ++o) {
                                    if (CFG.eventsManager.getEvent(j).lDecisions.get(k).lOutcomes.get(o).getCivID() == i || CFG.eventsManager.getEvent(j).lDecisions.get(k).lOutcomes.get(o).getCivID2() == i) {
                                        tRemove = false;
                                        k = CFG.eventsManager.getEvent(j).lDecisions.size();
                                        j = CFG.eventsManager.getEventsSize();
                                        break;
                                    }
                                }
                            }
                            for (int k = 0; k < CFG.eventsManager.getEvent(j).lTriggers.size(); ++k) {
                                for (int o = 0; o < CFG.eventsManager.getEvent(j).lTriggers.get(k).lConditions.size(); ++o) {
                                    if (CFG.eventsManager.getEvent(j).lTriggers.get(k).lConditions.get(o).getCivID() == i || CFG.eventsManager.getEvent(j).lTriggers.get(k).lConditions.get(o).getCivID2() == i) {
                                        tRemove = false;
                                        k = CFG.eventsManager.getEvent(j).lTriggers.size();
                                        j = CFG.eventsManager.getEventsSize();
                                        break;
                                    }
                                }
                            }
                        }
                        if (tRemove) {
                            CFG.game.createScenarioRemoveCivilization(i--);
                        }
                    }
                }
            }
            catch (final IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
        }
        final OutputStream os = null;
        try {
            final Scenario_GameData tempScenarioGameData = new Scenario_GameData();
            tempScenarioGameData.buildData();
            final FileHandle file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG);
            file.writeBytes(CFG.serialize(tempScenarioGameData), false);
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
        final OutputStream osProvince = null;
        try {
            final Scenario_GameData_Province2 createScenario_GameData_Province = new Scenario_GameData_Province2();
            createScenario_GameData_Province.buildProvinceOwners();
            final FileHandle fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_PD");
            fileProvince.writeBytes(CFG.serialize(createScenario_GameData_Province), false);
        }
        catch (final IOException ex5) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex5);
            }
            if (osProvince != null) {
                try {
                    osProvince.close();
                }
                catch (final Exception ex6) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex6);
                    }
                }
            }
        }
        finally {
            if (osProvince != null) {
                try {
                    osProvince.close();
                }
                catch (final Exception ex7) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex7);
                    }
                }
            }
        }
        final OutputStream osDiplomacy = null;
        try {
            final Scenario_GameData_Diplomacy2 tempScenarioGameData2 = new Scenario_GameData_Diplomacy2();
            tempScenarioGameData2.buildData();
            final FileHandle file2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_D");
            file2.writeBytes(CFG.serialize(tempScenarioGameData2), false);
        }
        catch (final IOException ex8) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex8);
            }
            if (osDiplomacy != null) {
                try {
                    osDiplomacy.close();
                }
                catch (final Exception ex9) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex9);
                    }
                }
            }
        }
        finally {
            if (osDiplomacy != null) {
                try {
                    osDiplomacy.close();
                }
                catch (final Exception ex10) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex10);
                    }
                }
            }
        }
        final OutputStream osHRE = null;
        try {
            final FileHandle fileProvince2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_HRE");
            fileProvince2.writeBytes(CFG.serialize(CFG.holyRomanEmpire_Manager.getHRE()), false);
        }
        catch (final IOException ex11) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex11);
            }
            if (osProvince != null) {
                try {
                    osHRE.close();
                }
                catch (final Exception ex12) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex12);
                    }
                }
            }
        }
        finally {
            if (osProvince != null) {
                try {
                    osHRE.close();
                }
                catch (final Exception ex13) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex13);
                    }
                }
            }
        }
        final OutputStream osWasteland = null;
        try {
            final List<Integer> tempProvinces = new ArrayList<Integer>();
            for (int l = 0; l < CFG.game.getProvincesSize(); ++l) {
                if (CFG.game.getProvince(l).getWasteland() >= 0) {
                    tempProvinces.add(l);
                }
            }
            final Scenario_WastelandProvinces_GameData scenario_WastelandProvinces_GameData = new Scenario_WastelandProvinces_GameData(tempProvinces);
            final FileHandle fileWasteland = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_W");
            fileWasteland.writeBytes(CFG.serialize(scenario_WastelandProvinces_GameData), false);
        }
        catch (final IOException ex14) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex14);
            }
            if (osWasteland != null) {
                try {
                    osWasteland.close();
                }
                catch (final Exception ex15) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex15);
                    }
                }
            }
        }
        finally {
            if (osWasteland != null) {
                try {
                    osWasteland.close();
                }
                catch (final Exception ex16) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex16);
                    }
                }
            }
        }
        final OutputStream osCores = null;
        try {
            final FileHandle fileCores = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_C");
            fileCores.writeBytes(CFG.serialize(CFG.province_Cores_GameData), false);
        }
        catch (final IOException ex17) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex17);
            }
            if (osCores != null) {
                try {
                    osCores.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
        finally {
            if (osCores != null) {
                try {
                    osCores.close();
                }
                catch (final Exception ex18) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex18);
                    }
                }
            }
        }
        final OutputStream osEvents = null;
        try {
            final FileHandle fileEvents = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + "events/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_E");
            fileEvents.writeBytes(CFG.serialize(CFG.eventsManager.eventsGD), false);
        }
        catch (final IOException ex19) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex19);
            }
            if (osEvents != null) {
                try {
                    osEvents.close();
                }
                catch (final Exception ex20) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex20);
                    }
                }
            }
        }
        finally {
            if (osEvents != null) {
                try {
                    osEvents.close();
                }
                catch (final Exception ex21) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex21);
                    }
                }
            }
        }
        try {
            FileHandle file3;
            if (CFG.readLocalFiles()) {
                file3 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
            }
            else {
                file3 = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
            }
            final String tempTags = file3.readString();
            if (tempTags.indexOf(CFG.CREATE_SCENARIO_GAME_DATA_TAG) < 0) {
                final FileHandle fileSave = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
                fileSave.writeString(tempTags + CFG.CREATE_SCENARIO_GAME_DATA_TAG + ";", false);
            }
        }
        catch (final GdxRuntimeException ex22) {
            final FileHandle fileSave2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
            fileSave2.writeString(CFG.CREATE_SCENARIO_GAME_DATA_TAG + ";", false);
        }
        try {
            this.checkArmies();
            final Scenario_GameData_Armies tempScenarioGameData_Armies = new Scenario_GameData_Armies();
            tempScenarioGameData_Armies.buildData();
            final FileHandle file4 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_A");
            file4.writeBytes(CFG.serialize(tempScenarioGameData_Armies), false);
        }
        catch (final IOException ex19) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex19);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex20) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex20);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex23) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex23);
                    }
                }
            }
        }
        this.saveScenario_Info();
        this.getGameScenarios().loadGame_Scenarios(false);
    }
    
    private final void saveScenario_Info() {
        final CFG.ConfigScenarioInfo configData = new CFG.ConfigScenarioInfo();
        configData.Age_of_Civilizations = "Data";
        ArrayList dataList = new ArrayList();
        dataList = new ArrayList();
        int tNumOfCivs = 0;
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                ++tNumOfCivs;
            }
        }
        final CFG.Data_Scenario_Info nDataTag = new CFG.Data_Scenario_Info();
        nDataTag.Name = CFG.CREATE_SCENARIO_NAME;
        nDataTag.Civs = tNumOfCivs;
        nDataTag.Author = CFG.CREATE_SCENARIO_AUTHOR;
        nDataTag.Wiki = CFG.CREATE_SCENARIO_WIKI;
        nDataTag.Age = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
        nDataTag.Year = Game_Calendar.currentYear;
        nDataTag.Month = Game_Calendar.currentMonth;
        nDataTag.Day = Game_Calendar.currentDay;
        dataList.add(nDataTag);
        configData.Data_Scenario_Info = dataList;
        final Json jsonSave = new Json();
        jsonSave.setOutputType(JsonWriter.OutputType.json);
        jsonSave.setElementType(CFG.ConfigScenarioInfo.class, "Data_Scenario_Info", CFG.Data_Scenario_Info.class);
        final FileHandle fileSave = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "/" + CFG.CREATE_SCENARIO_GAME_DATA_TAG + "_INFO" + ".json");
        fileSave.writeString(jsonSave.prettyPrint(configData), false);
    }
    
    protected final void saveAlliancesNamesPackage() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("game/alliance_names/" + CFG.CREATE_PACKAGE_ALLIANCE_NAMES_GAME_DATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.editorAlliancesNames_GameData), false);
        }
        catch (final IOException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex2) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex2);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        try {
            final FileHandle file = Gdx.files.internal("game/alliance_names/Age_of_Civilizations.json");
            final String fileContent = file.readString();
            final Json json = new Json();
            json.setElementType(CFG.ConfigAlliancesData.class, "Data_Random_Alliance_Names", CFG.Data_Random_Alliance_Names.class);
            CFG.ConfigAlliancesData data = new CFG.ConfigAlliancesData();
            data = json.fromJson(CFG.ConfigAlliancesData.class, fileContent);
            for (final Object e : data.Data_Random_Alliance_Names) {
                final CFG.Data_Random_Alliance_Names tempData = (CFG.Data_Random_Alliance_Names)e;
                if (tempData.Tag.equals(CFG.CREATE_PACKAGE_ALLIANCE_NAMES_GAME_DATA_TAG)) {
                    return;
                }
            }
            final CFG.ConfigAlliancesData configData = new CFG.ConfigAlliancesData();
            configData.Age_of_Civilizations = "Data";
            ArrayList dataList = new ArrayList();
            dataList = data.Data_Random_Alliance_Names;
            final CFG.Data_Random_Alliance_Names nDataTag = new CFG.Data_Random_Alliance_Names();
            nDataTag.Tag = CFG.CREATE_PACKAGE_ALLIANCE_NAMES_GAME_DATA_TAG;
            nDataTag.Enabled = true;
            dataList.add(nDataTag);
            configData.Data_Random_Alliance_Names = dataList;
            final Json jsonSave = new Json();
            jsonSave.setOutputType(JsonWriter.OutputType.json);
            jsonSave.setElementType(CFG.ConfigAlliancesData.class, "Data_Random_Alliance_Names", CFG.Data_Random_Alliance_Names.class);
            final FileHandle fileSave = Gdx.files.local("game/alliance_names/Age_of_Civilizations.json");
            fileSave.writeString(jsonSave.prettyPrint(configData), false);
        }
        catch (final GdxRuntimeException ex4) {
            final CFG.ConfigAlliancesData configData2 = new CFG.ConfigAlliancesData();
            configData2.Age_of_Civilizations = "Data";
            ArrayList dataList2 = new ArrayList();
            dataList2 = new ArrayList();
            final CFG.Data_Random_Alliance_Names nDataTag2 = new CFG.Data_Random_Alliance_Names();
            nDataTag2.Tag = CFG.CREATE_PACKAGE_ALLIANCE_NAMES_GAME_DATA_TAG;
            nDataTag2.Enabled = true;
            dataList2.add(nDataTag2);
            configData2.Data_Random_Alliance_Names = dataList2;
            final Json jsonSave2 = new Json();
            jsonSave2.setOutputType(JsonWriter.OutputType.json);
            jsonSave2.setElementType(CFG.ConfigAlliancesData.class, "Data_Random_Alliance_Names", CFG.Data_Random_Alliance_Names.class);
            final FileHandle fileSave2 = Gdx.files.local("game/alliance_names/Age_of_Civilizations.json");
            fileSave2.writeString(jsonSave2.prettyPrint(configData2), false);
        }
    }
    
    protected final void saveContinentPackage() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("map/data/continents/packges/" + CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.editor_Package_ContinentsData), false);
        }
        catch (final IOException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex2) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex2);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
    }
    
    protected final void saveContinentPackagesData() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("map/data/continents/packges_data/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.editor_Continent_GameData), false);
            try {
                final FileHandle file = Gdx.files.internal("map/data/continents/packges_data/Age_of_Civilizations");
                final String tempTags = file.readString();
                if (tempTags.indexOf(CFG.EDITOR_ACTIVE_GAMEDATA_TAG) < 0) {
                    final FileHandle fileSave = Gdx.files.local("map/data/continents/packges_data/Age_of_Civilizations");
                    fileSave.writeString(tempTags + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
                }
            }
            catch (final GdxRuntimeException ex) {
                final FileHandle fileSave2 = Gdx.files.local("map/data/continents/packges_data/Age_of_Civilizations");
                fileSave2.writeString(CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
            }
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
    }
    
    protected final void saveRegionPackage() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("map/data/regions/packges/" + CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.editor_Package_RegionsData), false);
        }
        catch (final IOException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex2) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex2);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
    }
    
    protected final void saveRegionPackagesData() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("map/data/regions/packges_data/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.editor_Region_GameData), false);
            try {
                final FileHandle file = Gdx.files.internal("map/data/regions/packges_data/Age_of_Civilizations");
                final String tempTags = file.readString();
                if (tempTags.indexOf(CFG.EDITOR_ACTIVE_GAMEDATA_TAG) < 0) {
                    final FileHandle fileSave = Gdx.files.local("map/data/regions/packges_data/Age_of_Civilizations");
                    fileSave.writeString(tempTags + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
                }
            }
            catch (final GdxRuntimeException ex) {
                final FileHandle fileSave2 = Gdx.files.local("map/data/regions/packges_data/Age_of_Civilizations");
                fileSave2.writeString(CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
            }
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
    }
    
    protected final void saveLinesData() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("game/lines/" + CFG.editorLine_GameData.getImageName());
            fileData.writeBytes(CFG.serialize(CFG.editorLine_GameData), false);
            try {
                final FileHandle file = Gdx.files.internal("game/lines/Age_of_Civilizations");
                final String tempTags = file.readString();
                if (tempTags.indexOf(CFG.editorLine_GameData.getImageName()) < 0) {
                    final FileHandle fileSave = Gdx.files.local("game/lines/Age_of_Civilizations");
                    fileSave.writeString(tempTags + CFG.editorLine_GameData.getImageName() + ";", false);
                }
            }
            catch (final GdxRuntimeException ex) {
                final FileHandle fileSave2 = Gdx.files.local("game/lines/Age_of_Civilizations");
                fileSave2.writeString(CFG.editorLine_GameData.getImageName() + ";", false);
            }
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
    }
    
    protected final void saveDiplomacyColors() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("game/diplomacy_colors/packages/" + CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG);
            fileData.writeBytes(CFG.serialize(CFG.diplomacyColors_GameData), false);
            try {
                final FileHandle file = Gdx.files.internal("game/diplomacy_colors/packages/Age_of_Civilizations");
                final String tempTags = file.readString();
                if (tempTags.indexOf(CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG) < 0) {
                    final FileHandle fileSave = Gdx.files.local("game/diplomacy_colors/packages/Age_of_Civilizations");
                    fileSave.writeString(tempTags + CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG + ";", false);
                }
            }
            catch (final GdxRuntimeException ex) {
                final FileHandle fileSave2 = Gdx.files.local("game/diplomacy_colors/packages/Age_of_Civilizations");
                fileSave2.writeString(CFG.CREATE_PACKAGE_CONTINENT_GAME_DATA_TAG + ";", false);
            }
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
        CFG.toast.setInView(CFG.langManager.get("Saved"));
    }
    
    protected final void saveCity() {
        final OutputStream os = null;
        try {
            final FileHandle fileData = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG);
            CFG.editorCity.setCityLevel(CFG.getEditorCityLevel_Ref(CFG.editorCity.getCityLevel()));
            fileData.writeBytes(CFG.serialize(CFG.editorCity), false);
            try {
                FileHandle file;
                if (CFG.readLocalFiles()) {
                    file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + "Age_of_Civilizations");
                }
                else {
                    file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + "Age_of_Civilizations");
                }
                final String tempTags = file.readString();
                if (!tempTags.contains(CFG.EDITOR_ACTIVE_GAMEDATA_TAG)) {
                    final FileHandle fileSave = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + "Age_of_Civilizations");
                    fileSave.writeString(tempTags + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
                }
            }
            catch (final GdxRuntimeException ex) {
                final FileHandle fileSave2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "data/" + "cities/" + "Age_of_Civilizations");
                fileSave2.writeString(CFG.EDITOR_ACTIVE_GAMEDATA_TAG + ";", false);
            }
        }
        catch (final IOException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex3) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex3);
                    }
                }
            }
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (final Exception ex4) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex4);
                    }
                }
            }
        }
    }
    
    protected final int getProvinceArmy(final int nProvinceID) {
        int out = 0;
        for (int i = 0; i < this.getProvince(nProvinceID).getCivsSize(); ++i) {
            out += this.getProvince(nProvinceID).getArmy(i);
        }
        return out;
    }
    
    protected final int getProvinceValue(final int nProvinceID) {
        int out = 1;
        out += this.getProvinceValue_Terrain(nProvinceID);
        out += this.getProvinceValue_Capital(nProvinceID);
        out += this.getProvinceValue_PopulationGrowthRate(nProvinceID);
        out += this.getProvinceValue_DevelopmentLevel(nProvinceID);
        return out;
    }
    
    protected final int getProvinceValue_Terrain(final int nProvinceID) {
        return CFG.terrainTypesManager.getBaseProvinceValue(this.getProvince(nProvinceID).getTerrainTypeID());
    }
    
    protected final int getProvinceValue_Capital(final int nProvinceID) {
        return this.getProvince(nProvinceID).getIsCapital() ? 2 : 0;
    }
    
    protected final int getProvinceValue_PopulationGrowthRate(final int nProvinceID) {
        return (int)(this.getProvince(nProvinceID).getGrowthRate_Population_WithFarm() * 6.0f);
    }
    
    protected final int getProvinceValue_DevelopmentLevel(final int nProvinceID) {
        return (int)(this.getProvince(nProvinceID).getDevelopmentLevel() * 4.0f);
    }
    
    protected final Province getProvince(int ID) {
        //adjust for null provinces when loading
        //maybe not necessary ??

        //if (CFG.menuManager.getInLoadMap() && !ghostProvinces.isEmpty()) {
        //    for (int x : ghostProvinces) {
        //        if (x < ID) {
        //            ID -= 1;
        //        }
        //    }
        //    try {
        //        return lProvinces[ID];
        //    } catch (ArrayIndexOutOfBoundsException e) {
        //        return lProvinces[0];
        //    }
        //}
        return lProvinces[ID];
    }
    
    protected final int getProvincesSize() {
        return lProvinces.length;
    }
    
    protected final int countLandProvinces_NotWasteland() {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince() && this.getProvince(i).getWasteland() < 0) {
                ++out;
            }
        }
        return out;
    }
    
    protected final int countLandProvinces() {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince()) {
                ++out;
            }
        }
        return out;
    }
    
    protected final int countSeaProvinces() {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getSeaProvince()) {
                ++out;
            }
        }
        return out;
    }
    
    protected final String countAvarageGrowthRate() {
        float out = 0.0f;
        int outSize = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince()) {
                out += this.getProvince(i).getGrowthRate_Population_WithFarm();
                ++outSize;
            }
        }
        return "" + (int)(out * 100.0f / outSize);
    }
    
    protected final String countAvarageGrowthRate(final int nCivID) {
        float out = 0.0f;
        int outSize = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            out += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getGrowthRate_Population_WithFarm();
            ++outSize;
        }
        return "" + (int)(out * 100.0f / outSize);
    }
    
    protected final int countContinentProvinces(final int nContinentID) {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (this.getProvince(i).getContinent() == nContinentID) {
                ++out;
            }
        }
        return out;
    }
    
    protected final int countRegionProvinces(final int nRegionID) {
        int out = 0;
        for (int i = 0; i < this.getProvincesSize(); ++i) {
            if (!this.getProvince(i).getSeaProvince() && this.getProvince(i).getRegion() == nRegionID) {
                ++out;
            }
        }
        return out;
    }
    
    protected final String countAvarageDevelopmentLevel(final int nCivID) {
        float out = 0.0f;
        int outSize = 0;
        for (int i = 0; i < this.getCiv(nCivID).getNumOfProvinces(); ++i) {
            out += this.getProvince(this.getCiv(nCivID).getProvinceID(i)).getDevelopmentLevel();
            ++outSize;
        }
        return CFG.getPercentage(out, (float)outSize, 4);
    }
    
    protected final float countAvarageDevelopmentLevel_Float(final int nCivID) {
        float out = 0.0f;
        int outSize = 0;
        for (int i = 0; i < this.getCiv(nCivID).getNumOfProvinces(); ++i) {
            out += this.getProvince(this.getCiv(nCivID).getProvinceID(i)).getDevelopmentLevel();
            ++outSize;
        }
        return CFG.game.getCiv(nCivID).fAverageDevelopment = out / outSize;
    }
    
    protected final MenuElement_Hover_v2 getHover_CapitalCity(final int nCivID) {
        final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
        if (((CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCitiesSize() > 0) ? CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(0).getCityName() : CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getName()).length() > 0) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCitiesSize() > 0) ? CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(0).getCityName() : CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID(), CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Capital"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        else {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID(), CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Capital"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvincePopulation") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getPopulationData().getPopulation()), CFG.COLOR_TEXT_POPULATION));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        return new MenuElement_Hover_v2(nElements);
    }
    
    protected final MenuElement_Hover_v2 getHover_CapitalCity_ByProvinceID(final int nProvinceID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            if (((CFG.game.getProvince(nProvinceID).getCitiesSize() > 0) ? CFG.game.getProvince(nProvinceID).getCity(0).getCityName() : CFG.game.getProvince(nProvinceID).getName()).length() > 0) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getProvince(nProvinceID).getCitiesSize() > 0) ? CFG.game.getProvince(nProvinceID).getCity(0).getCityName() : CFG.game.getProvince(nProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(nProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Capital"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }
            else {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(nProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Capital"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvincePopulation") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation()), CFG.COLOR_TEXT_POPULATION));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_LargestCity(final int nProvinceID) {
        final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
        if (((CFG.game.getProvince(nProvinceID).getCitiesSize() > 0) ? CFG.game.getProvince(nProvinceID).getCity(0).getCityName() : CFG.game.getProvince(nProvinceID).getName()).length() > 0) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getProvince(nProvinceID).getCitiesSize() > 0) ? CFG.game.getProvince(nProvinceID).getCity(0).getCityName() : CFG.game.getProvince(nProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(nProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LargestCity"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        else {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(nProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LargestCity"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvincePopulation") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation()), CFG.COLOR_TEXT_POPULATION));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();
        return new MenuElement_Hover_v2(nElements);
    }
    
    protected final boolean showTurnChangesInformation(final int nCivID) {
        return CFG.isDesktop() && (CFG.SPECTATOR_MODE || CFG.FOG_OF_WAR == 0 || CFG.game.isAlly(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), nCivID));
    }
    
    protected final MenuElement_Hover_v2 getHover_PopulationOfCiv(final int nCivID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(nCivID).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            final int tempTotalPop = CFG.game.getCiv(nCivID).countPopulation();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Population") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempTotalPop), CFG.COLOR_TEXT_POPULATION));
            if (CFG.game.showTurnChangesInformation(nCivID)) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                int turnPopChange = 0;
                for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                    turnPopChange += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).saveProvinceData.turnChange_Population;
                }
                if (turnPopChange > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + CFG.getNumberWithSpaces("" + turnPopChange), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                }
                else if (turnPopChange < 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getNumberWithSpaces("" + turnPopChange), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                }
                else {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + turnPopChange, CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                }
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
            }
            else {
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
            }
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            final List<Integer> tempNat = new ArrayList<Integer>();
            final List<Integer> tempNum = new ArrayList<Integer>();
            for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
                for (int k = 0; k < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getNationalitiesSize(); ++k) {
                    boolean addNew = true;
                    for (int l = 0; l < tempNat.size(); ++l) {
                        if (tempNat.get(l) == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getCivID(k)) {
                            tempNum.set(l, tempNum.get(l) + CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getPopulationID(k));
                            addNew = false;
                            break;
                        }
                    }
                    if (addNew) {
                        tempNat.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getCivID(k));
                        tempNum.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getPopulationID(k));
                    }
                }
            }
            final List<Integer> tempSortedNat = new ArrayList<Integer>();
            final List<Integer> tempSortedNum = new ArrayList<Integer>();
            while (tempNat.size() > 0) {
                int nMax = 0;
                for (int m = 1; m < tempNat.size(); ++m) {
                    if (tempNum.get(nMax) < tempNum.get(m)) {
                        nMax = m;
                    }
                }
                tempSortedNat.add(tempNat.get(nMax));
                tempSortedNum.add(tempNum.get(nMax));
                tempNat.remove(nMax);
                tempNum.remove(nMax);
            }
            if (CFG.menuManager.getInGameView() && CFG.FOG_OF_WAR == 2) {
                for (int i2 = 0; i2 < tempSortedNat.size(); ++i2) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getMetCiv(tempSortedNat.get(i2)) ? tempSortedNat.get(i2) : -1));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempSortedNum.get(i2)), CFG.COLOR_TEXT_POPULATION));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage(tempSortedNum.get(i2), tempTotalPop, 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + (CFG.getMetCiv(tempSortedNat.get(i2)) ? CFG.game.getCiv(tempSortedNat.get(i2)).getCivName() : CFG.langManager.get("Undiscovered")), CFG.COLOR_TEXT_RANK_HOVER));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            else {
                for (int i2 = 0; i2 < tempSortedNat.size(); ++i2) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(tempSortedNat.get(i2)));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempSortedNum.get(i2)), CFG.COLOR_TEXT_POPULATION));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage(tempSortedNum.get(i2), tempTotalPop, 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.game.getCiv(tempSortedNat.get(i2)).getCivName(), CFG.COLOR_TEXT_RANK_HOVER));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_PopulationOfCiv_CreateAVassal() {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            int tempTotalPop = 0;
            for (int i = 0; i < this.getSelectedProvinces().getProvincesSize(); ++i) {
                tempTotalPop += this.getProvince(this.getSelectedProvinces().getProvince(i)).getPopulationData().getPopulation();
            }
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Population") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempTotalPop), CFG.COLOR_TEXT_POPULATION));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            final List<Integer> tempNat = new ArrayList<Integer>();
            final List<Integer> tempNum = new ArrayList<Integer>();
            for (int j = 0; j < this.getSelectedProvinces().getProvincesSize(); ++j) {
                for (int k = 0; k < CFG.game.getProvince(this.getSelectedProvinces().getProvince(j)).getPopulationData().getNationalitiesSize(); ++k) {
                    boolean addNew = true;
                    for (int l = 0; l < tempNat.size(); ++l) {
                        if (tempNat.get(l) == CFG.game.getProvince(this.getSelectedProvinces().getProvince(j)).getPopulationData().getCivID(k)) {
                            tempNum.set(l, tempNum.get(l) + CFG.game.getProvince(this.getSelectedProvinces().getProvince(j)).getPopulationData().getPopulationID(k));
                            addNew = false;
                            break;
                        }
                    }
                    if (addNew) {
                        tempNat.add(CFG.game.getProvince(this.getSelectedProvinces().getProvince(j)).getPopulationData().getCivID(k));
                        tempNum.add(CFG.game.getProvince(this.getSelectedProvinces().getProvince(j)).getPopulationData().getPopulationID(k));
                    }
                }
            }
            final List<Integer> tempSortedNat = new ArrayList<Integer>();
            final List<Integer> tempSortedNum = new ArrayList<Integer>();
            while (tempNat.size() > 0) {
                int nMax = 0;
                for (int m = 1; m < tempNat.size(); ++m) {
                    if (tempNum.get(nMax) < tempNum.get(m)) {
                        nMax = m;
                    }
                }
                tempSortedNat.add(tempNat.get(nMax));
                tempSortedNum.add(tempNum.get(nMax));
                tempNat.remove(nMax);
                tempNum.remove(nMax);
            }
            if (CFG.menuManager.getInGameView() && CFG.FOG_OF_WAR == 2) {
                for (int i2 = 0; i2 < tempSortedNat.size(); ++i2) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getMetCiv(tempSortedNat.get(i2)) ? tempSortedNat.get(i2) : -1));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempSortedNum.get(i2)), CFG.COLOR_TEXT_POPULATION));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage(tempSortedNum.get(i2), tempTotalPop, 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + (CFG.getMetCiv(tempSortedNat.get(i2)) ? CFG.game.getCiv(tempSortedNat.get(i2)).getCivName() : CFG.langManager.get("Undiscovered")), CFG.COLOR_TEXT_RANK_HOVER));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            else {
                for (int i2 = 0; i2 < tempSortedNat.size(); ++i2) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(tempSortedNat.get(i2)));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempSortedNum.get(i2)), CFG.COLOR_TEXT_POPULATION));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage(tempSortedNum.get(i2), tempTotalPop, 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.game.getCiv(tempSortedNat.get(i2)).getCivName(), CFG.COLOR_TEXT_RANK_HOVER));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_TerrainTypeInfo(final int nTerrainID, final int nProvinceID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Color(CFG.terrainTypesManager.getColor(nTerrainID), 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(nTerrainID, 0, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(nTerrainID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            if (!CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getSeaProvince()) {
                //if (CFG.terrainTypesManager.getDefense(nTerrainID) != 0.0f || CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) != 0.0f || CFG.terrainTypesManager.getMovementCost(nTerrainID) != 0.0f || CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) != 0.0f || CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) != 0.0f || CFG.terrainTypesManager.getBuildCost(nTerrainID) != 0.0f) {}
                if (CFG.terrainTypesManager.getDefense(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getDefense(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getDefense(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getDefense(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getDefense(nTerrainID) > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getMilitaryUpkeep(nTerrainID) < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.terrainTypesManager.getMovementCost(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementCostModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getMovementCost(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getMovementCost(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getMovementCost(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getMovementCost(nTerrainID) < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getPopulationGrowth(nTerrainID) > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getEconomyGrowth(nTerrainID) > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.terrainTypesManager.getBuildCost(nTerrainID) != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildCostModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + ((CFG.terrainTypesManager.getBuildCost(nTerrainID) > 0.0f) ? "+" : "") + (int)(CFG.terrainTypesManager.getBuildCost(nTerrainID) * 100.0f) + "%", (CFG.terrainTypesManager.getBuildCost(nTerrainID) == 0.0f) ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : ((CFG.terrainTypesManager.getBuildCost(nTerrainID) < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_ProvincesOfCiv(final int nCivID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(nCivID).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            final int nTotalProvinces = CFG.game.countLandProvinces_NotWasteland();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NumberOfProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(nCivID).getNumOfProvinces(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("/" + nTotalProvinces, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.getPercentage(CFG.game.getCiv(nCivID).getNumOfProvinces(), nTotalProvinces, 4) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            final List<Integer> tempCont = new ArrayList<Integer>();
            final List<Integer> tempNum = new ArrayList<Integer>();
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                boolean addNew = true;
                for (int j = 0; j < tempCont.size(); ++j) {
                    if (tempCont.get(j) == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getContinent()) {
                        tempNum.set(j, tempNum.get(j) + 1);
                        addNew = false;
                        break;
                    }
                }
                if (addNew) {
                    tempCont.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getContinent());
                    tempNum.add(1);
                }
            }
            for (int i = 0; i < tempCont.size() && i < 10; ++i) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.map.getMapContinents().getName(tempCont.get(i)), CFG.map.getMapContinents().getColor(tempCont.get(i))));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tempNum.get(i)), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.provinces, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }

    protected final MenuElement_Hover_v2 getHover_Decision(final Decision_GameData decision) {
        final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();

        //split description into chucks of 55 characters to avoid cramming screen
        int start = 0;
        while (start < decision.getDesc().length()) {
            int end = Math.min(start + 55, decision.getDesc().length());
            if (end < decision.getDesc().length()) {
                int lastSpace = decision.getDesc().lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            nData.add(new MenuElement_Hover_v2_Element_Type_Text(decision.getDesc().substring(start, end).trim(), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();

            start = end;

            while (start < decision.getDesc().length() && decision.getDesc().charAt(start) == ' ') {
                start++; // skip spaces at the start of the next line
            }
        }

        if (decision.getInProgress()) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Expires") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (decision.getTurnLength() - decision.getTurnsProgress()), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        } else {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TurnLength") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + decision.getTurnLength(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Repeatable") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text((decision.isRepeatable()) ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), (decision.isRepeatable()) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(""));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text((decision.isCostEveryTurn()) ? CFG.langManager.get("ChargedEveryTurn") : CFG.langManager.get("ChargedOnce"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Gold") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text((decision.getGoldCost() >= 0.0F ? "-" : "+") + Math.abs(decision.getGoldCost()), (decision.getGoldCost() > 0.0F ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_POSITIVE)));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": "));
        nData.add(new MenuElement_Hover_v2_Element_Type_Text((decision.getDiploCost() >= 0.0F ? "-" : "+") + Math.abs(decision.getDiploCost()/10.0F), (decision.getDiploCost() > 0.0F ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_POSITIVE)));
        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        nData.add(new MenuElement_Hover_v2_Element_Type_Text(""));
        nElements.add(new MenuElement_Hover_v2_Element2(nData));
        nData.clear();

        if (decision.fModifier_UpperClass != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("UpperClass") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_UpperClass > 0.0f) ? "+" : "") + (int)(decision.fModifier_UpperClass * 100.0f) + "%", (decision.fModifier_UpperClass > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_MiddleClass != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MiddleClass") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_MiddleClass > 0.0f) ? "+" : "") + (int)(decision.fModifier_MiddleClass * 100.0f) + "%", (decision.fModifier_MiddleClass > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_LowerClass != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LowerClass") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_LowerClass > 0.0f) ? "+" : "") + (int)(decision.fModifier_LowerClass * 100.0f) + "%", (decision.fModifier_LowerClass > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_leaders, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }

        if (decision.fModifier_AttackBonus != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AttackBonus") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_AttackBonus > 0.0f) ? "+" : "") + (int)(decision.fModifier_AttackBonus * 100.0f) + "%", (decision.fModifier_AttackBonus > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_DefenseBonus != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_DefenseBonus > 0.0f) ? "+" : "") + (int)(decision.fModifier_DefenseBonus * 100.0f) + "%", (decision.fModifier_DefenseBonus > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_PopGrowth != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_PopGrowth > 0.0f) ? "+" : "") + (int)(decision.fModifier_PopGrowth * 100.0f) + "%", (decision.fModifier_PopGrowth > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_EconomyGrowth != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_EconomyGrowth > 0.0f) ? "+" : "") + (int)(decision.fModifier_EconomyGrowth * 100.0f) + "%", (decision.fModifier_EconomyGrowth > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_IncomeTaxation != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeTaxation") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_IncomeTaxation > 0.0f) ? "+" : "") + (int)(decision.fModifier_IncomeTaxation * 100.0f) + "%", (decision.fModifier_IncomeTaxation > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_IncomeProduction != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_IncomeProduction > 0.0f) ? "+" : "") + (int)(decision.fModifier_IncomeProduction * 100.0f) + "%", (decision.fModifier_IncomeProduction > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_Administration != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Administration") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_Administration > 0.0f) ? "+" : "") + (int)(decision.fModifier_Administration * 100.0f) + "%", (decision.fModifier_Administration < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold2, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_MilitaryUpkeep != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_MilitaryUpkeep > 0.0f) ? "+" : "") + (int)(decision.fModifier_MilitaryUpkeep * 100.0f) + "%", (decision.fModifier_MilitaryUpkeep < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_army, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_Research != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_Research > 0.0f) ? "+" : "") + (int)(decision.fModifier_Research * 100.0f) + "%", (decision.fModifier_Research > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }
        if (decision.fModifier_MovementPoints != 0.0f) {
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(((decision.fModifier_MovementPoints > 0.0f) ? "+" : "") + (int)(decision.fModifier_MovementPoints * 100.0f) + "%", (decision.fModifier_MovementPoints > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
        }

        return new MenuElement_Hover_v2(nElements);
    }

    protected final MenuElement_Hover_v2 getHover_Class(final int nCivID, final int iClassID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(nCivID).getCivName() + "  ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));

            final float fBoost = ((int)(10000.0f * Game_NextTurnUpdate.getClassPerceptionBoosts(CFG.game.getCiv(nCivID).civGameData.leaderData.getClassViews(iClassID)))) / 100.0F;
            if (iClassID == 0) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("UpperClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Administration") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold2, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            } else if (iClassID == 1) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MiddleClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            } else if (iClassID == 2) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LowerClass"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeTaxation") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();

                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(((fBoost > 0.0f) ? "+" : "") + fBoost + "%", (fBoost > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }

            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }

    protected final MenuElement_Hover_v2 getHover_LeaderOfCiv(final int nCivID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            if (CFG.game.getCiv(nCivID).civGameData.leaderData != null) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(nCivID).civGameData.leaderData.getName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.isIncumbentYear()) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InOffice") + ": "));
                } else {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Born") + ": "));
                }
                nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(nCivID).civGameData.leaderData.getDay() + " " + Game_Calendar.getMonthName(CFG.game.getCiv(nCivID).civGameData.leaderData.getMonth()) + " " + CFG.gameAges.getYear(CFG.game.getCiv(nCivID).civGameData.leaderData.getYear()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - " + CFG.gameAges.getAge(CFG.gameAges.getAgeOfYear(CFG.game.getCiv(nCivID).civGameData.leaderData.getYear())).getName(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_AttackBonus != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AttackBonus") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_AttackBonus > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_AttackBonus * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_AttackBonus > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_DefenseBonus != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_DefenseBonus > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_DefenseBonus * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_DefenseBonus > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_rivals, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_PopGrowth != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_PopGrowth > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_PopGrowth * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_PopGrowth > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_EconomyGrowth != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_EconomyGrowth > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_EconomyGrowth * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_EconomyGrowth > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeTaxation != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeTaxation") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeTaxation > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeTaxation * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeTaxation > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeProduction != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeProduction > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeProduction * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_IncomeProduction > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Administration != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Administration") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Administration > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Administration * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Administration < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold2, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MilitaryUpkeep != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MilitaryUpkeep > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MilitaryUpkeep * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MilitaryUpkeep < 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_army, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Research != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Research > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Research * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_Research > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MovementPoints != 0.0f) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(((CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MovementPoints > 0.0f) ? "+" : "") + (int)(CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MovementPoints * 100.0f) + "%", (CFG.game.getCiv(nCivID).civGameData.leaderData.fModifier_MovementPoints > 0.0f) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
                if (CFG.game.getCiv(nCivID).civGameData.leaderData.getWiki().length() > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wiki") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(nCivID).civGameData.leaderData.getWiki(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.wikipedia, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
            else {
                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(nCivID).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
            }
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_RankOfCiv(final int nCivID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CivRank") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(nCivID).getRankPosition() + "/" + CFG.game.getCivsSize(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.rank, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final MenuElement_Hover_v2 getHover_TechnologyLevel(final int nCivID) {
        try {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(nCivID).getTechnologyLevel(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("/2.0", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AverageDevelopment") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countAvarageDevelopmentLevel(nCivID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TechnologyPoints"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PopulationGrowthModifier") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_POP_GROWTH * 0.75f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_POP_GROWTH > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EconomyGrowthModifier") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ECONOMY_GROWTH * 0.75f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ECONOMY_GROWTH > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeTaxation") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_TAXATION * 0.2f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_TAXATION > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_PRODUCTION * 0.25f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_PRODUCTION > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Administration") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ADMINISTRATION * 0.3f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ADMINISTRATION > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold2, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_MILITARY_UPKEEP * 0.35f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_MILITARY_UPKEEP > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_army, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_RESEARCH * 0.75f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_RESEARCH > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ColonizationCost") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + String.format("%.1f", CFG.game.getCiv(nCivID).civGameData.skills.POINTS_COLONIZATION * 1.0f).replace(',', '.') + "%", (CFG.game.getCiv(nCivID).civGameData.skills.POINTS_COLONIZATION > 0) ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            return new MenuElement_Hover_v2(nElements);
        }
        catch (final IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    protected final int getScenarioID() {
        return this.scenarioID;
    }
    
    protected final void setScenarioID(final int scenarioID) {
        this.scenarioID = scenarioID;
        try {
            this.gameScenarios.sActiveScenarioTag = this.gameScenarios.getScenarioTag(this.getScenarioID());
        }
        catch (final IndexOutOfBoundsException ex) {
            this.gameScenarios.sActiveScenarioTag = "";
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void updateDaultScenarioID_ForMap() {
        int i = 0;
        while (true) {
            final int n = i;
            final Game_Scenarios gameScenarios = this.gameScenarios;
            if (n >= Game_Scenarios.SCENARIOS_SIZE) {
                break;
            }
            if (this.gameScenarios.getScenarioTag(i).equals(CFG.map.getMapDefaultScenario(CFG.map.getActiveMapID()))) {
                this.scenarioID = i;
                this.gameScenarios.sActiveScenarioTag = this.gameScenarios.getScenarioTag(this.getScenarioID());
                break;
            }
            ++i;
        }
    }
    
    protected final void whitePeace(final int iCivA, final int iCivB) {
        this.setCivRelation_OfCivB(iCivA, iCivB, 0.0f);
        this.setCivRelation_OfCivB(iCivB, iCivA, 0.0f);
    }
    
    protected final void whitePeace_ReturnProvincesToRightfulOwners(final int nCivA, final int nCivB) {
        for (int i = CFG.game.getCiv(nCivA).getNumOfProvinces() - 1; i >= 0; --i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivA).getProvinceID(i)).getTrueOwnerOfProvince() == nCivB) {
                final int nProvinceID = CFG.game.getCiv(nCivA).getProvinceID(i);
                final int nArmy0 = CFG.game.getProvince(nProvinceID).getArmyCivID(nCivA);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivA, 0);
                CFG.game.getProvince(nProvinceID).setCivID(nCivB, false, true);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivA, nArmy0);
                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(nCivA, nProvinceID);
            }
        }
        for (int i = CFG.game.getCiv(nCivB).getNumOfProvinces() - 1; i >= 0; --i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivB).getProvinceID(i)).getTrueOwnerOfProvince() == nCivA) {
                final int nProvinceID = CFG.game.getCiv(nCivB).getProvinceID(i);
                final int nArmy0 = CFG.game.getProvince(nProvinceID).getArmyCivID(nCivB);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivB, 0);
                CFG.game.getProvince(nProvinceID).setCivID(nCivA, false, true);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivB, nArmy0);
                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(nCivB, nProvinceID);
            }
        }
    }
    
    protected final void acceptPeaceOffer(final int iCivA, final int iCivB, final int nTruceNumOfTurns) {
        this.setCivRelation_OfCivB(iCivA, iCivB, Math.max(this.getCivRelation_OfCivB(iCivA, iCivB), 0.0f));
        this.setCivRelation_OfCivB(iCivB, iCivA, Math.max(this.getCivRelation_OfCivB(iCivB, iCivA), 0.0f));
        CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Truce(iCivB));
        CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Truce(iCivA));
        CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().messageBox.removeMessage_TypeFrom(iCivB, Message_Type.WE_CAN_SIGN_PEACE);
        CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().messageBox.removeMessage_TypeFrom(iCivB, Message_Type.WE_CAN_SIGN_PEACE_STATUS_QUO);
        CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().messageBox.removeMessage_TypeFrom(iCivB, Message_Type.WE_CAN_SIGN_PEACE);
        CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().messageBox.removeMessage_TypeFrom(iCivA, Message_Type.WE_CAN_SIGN_PEACE_STATUS_QUO);
        CFG.historyManager.addHistoryLog(new HistoryLog_Peace(iCivA, iCivB));
        this.setCivTruce(iCivA, iCivB, nTruceNumOfTurns);
    }
    
    protected final boolean canDeclareWar_TribalColonize_NeedsToBorder(final int iAgressorCivID, final int iCivB) {
        if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivB).getIdeologyID()).CAN_BECOME_CIVILIZED < 0) {
            return true;
        }
        if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iAgressorCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
            return true;
        }
        for (int i = 0; i < this.getCiv(iCivB).getNumOfProvinces(); ++i) {
            for (int j = 0; j < this.getProvince(this.getCiv(iCivB).getProvinceID(i)).getNeighboringProvincesSize(); ++j) {
                if (this.getProvince(this.getProvince(this.getCiv(iCivB).getProvinceID(i)).getNeighboringProvinces(j)).getCivID() == iAgressorCivID) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected final void declareWar(final int iAgressorCivID, final int iCivB, final boolean forceWar) {
        if (iAgressorCivID == 0 || iCivB == 0 || iAgressorCivID == iCivB || CFG.game.getCivsAtWar(iAgressorCivID, iCivB)) {
            return;
        }
        if (CFG.game.getCivTruce(iAgressorCivID, iCivB) > 0) {
            return;
        }
        if (!forceWar && !this.canDeclareWar_TribalColonize_NeedsToBorder(iAgressorCivID, iCivB)) {
            return;
        }
        final Save_Civ_GameData civGameData = CFG.game.getCiv(iAgressorCivID).civGameData;
        civGameData.civAggresionLevel += 0.025f + 0.575f * (CFG.game.getCiv(iAgressorCivID).getNumOfProvinces() / (float)CFG.oAI.PLAYABLE_PROVINCES / 5.0f);
        Game_Calendar.TURNS_SINCE_LAST_WAR = 0;
        if (iAgressorCivID == CFG.game.getCiv(iCivB).getPuppetOfCivID()) {
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (i != iAgressorCivID && i != iCivB && CFG.game.getCiv(i).getPuppetOfCivID() == iAgressorCivID) {
                    CFG.game.setCivRelation_OfCivB(i, iAgressorCivID, CFG.game.getCivRelation_OfCivB(i, iAgressorCivID) - 45.0f - CFG.oR.nextInt(22));
                }
            }
        }
        this.war_CheckDiplomacy(iAgressorCivID, iCivB);
        if (CFG.game.getCiv(iCivB).getPuppetOfCivID() != iCivB && this.getWarID(iAgressorCivID, CFG.game.getCiv(iCivB).getPuppetOfCivID()) >= 0) {
            this.joinWar(iCivB, iAgressorCivID, this.getWarID(iAgressorCivID, CFG.game.getCiv(iCivB).getPuppetOfCivID()));
            return;
        }
        for (int i = 0; i < CFG.game.getCiv(iCivB).civGameData.iVassalsSize; ++i) {
            if (this.getWarID(iAgressorCivID, CFG.game.getCiv(iCivB).civGameData.lVassals.get(i).iCivID) >= 0) {
                this.joinWar(iCivB, iAgressorCivID, this.getWarID(iAgressorCivID, CFG.game.getCiv(iCivB).civGameData.lVassals.get(i).iCivID));
                return;
            }
        }
        if (CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() != iAgressorCivID && this.getWarID(iCivB, CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID()) >= 0) {
            this.joinWar(iAgressorCivID, iCivB, this.getWarID(iCivB, CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID()));
            return;
        }
        for (int i = 0; i < CFG.game.getCiv(iAgressorCivID).civGameData.iVassalsSize; ++i) {
            if (this.getWarID(iCivB, CFG.game.getCiv(iAgressorCivID).civGameData.lVassals.get(i).iCivID) >= 0) {
                this.joinWar(iAgressorCivID, iCivB, this.getWarID(iCivB, CFG.game.getCiv(iAgressorCivID).civGameData.lVassals.get(i).iCivID));
                return;
            }
        }
        this.setCivRelation_OfCivB(iAgressorCivID, iCivB, -100.0f);
        this.setCivRelation_OfCivB(iCivB, iAgressorCivID, -100.0f);
        this.addWarData(iAgressorCivID, iCivB);
        for (int i = CFG.game.getCiv(iAgressorCivID).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
            if (CFG.game.getCiv(iAgressorCivID).civGameData.civPlans.warPreparations.get(i).onCivID == iCivB) {
                CFG.game.getCiv(iAgressorCivID).civGameData.civPlans.warPreparations.remove(i);
                CFG.game.getCiv(iAgressorCivID).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(iAgressorCivID).civGameData.civPlans.warPreparations.size();
            }
        }
        for (int i = CFG.game.getCiv(iCivB).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
            if (CFG.game.getCiv(iCivB).civGameData.civPlans.warPreparations.get(i).onCivID == iAgressorCivID) {
                CFG.game.getCiv(iCivB).civGameData.civPlans.warPreparations.remove(i);
                CFG.game.getCiv(iCivB).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(iCivB).civGameData.civPlans.warPreparations.size();
            }
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (i != iCivB && i != iAgressorCivID && CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                if (this.getDefensivePact(i, iCivB) > 0) {
                    this.joinWar(i, iAgressorCivID, this.getWarID(iAgressorCivID, iCivB));
                }
                else if (this.getGuarantee(i, iCivB) > 0) {
                    this.joinWar(i, iAgressorCivID, this.getWarID(iAgressorCivID, iCivB));
                }
            }
        }
        CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War(iAgressorCivID, iCivB));
        if (iAgressorCivID != CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() && CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() != iCivB) {
            CFG.game.getCiv(CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War_CivIsAtWar(iAgressorCivID, iCivB));
        }
        if (iCivB != CFG.game.getCiv(iCivB).getPuppetOfCivID() && CFG.game.getCiv(iCivB).getPuppetOfCivID() != iAgressorCivID) {
            CFG.game.getCiv(CFG.game.getCiv(iCivB).getPuppetOfCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War_CivIsAtWar(iCivB, iAgressorCivID));
        }
        DiplomacyManager.worldRecations(35, iAgressorCivID, iCivB);
        CFG.historyManager.addHistoryLog(new HistoryLog_WarDeclaration(iAgressorCivID, iCivB));
        if (!CFG.SPECTATOR_MODE) {
            try {
                if (CFG.game.getCiv(iAgressorCivID).getAllianceID() > 0) {
                    for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(iAgressorCivID).getAllianceID()).getCivilizationsSize(); ++i) {
                        if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(iAgressorCivID).getAllianceID()).getCivilization(i)).getNumOfProvinces() > 0 && CFG.game.getAlliance(CFG.game.getCiv(iAgressorCivID).getAllianceID()).getCivilization(i) != iAgressorCivID) {
                            for (int j = 0; j < CFG.game.getPlayersSize(); ++j) {
                                if (CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getNumOfProvinces() > 0 && CFG.game.getCiv(iAgressorCivID).getAllianceID() == CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getAllianceID() && iAgressorCivID != CFG.game.getPlayer(j).getCivID() && CFG.game.getAlliance(CFG.game.getCiv(iAgressorCivID).getAllianceID()).getCivilization(i) != CFG.game.getPlayer(j).getCivID()) {
                                    CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_JoinedAWar(iAgressorCivID, iCivB));
                                }
                            }
                        }
                    }
                }
                else if (CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() != CFG.game.getCiv(iAgressorCivID).getCivID()) {
                    for (int k = 0; k < CFG.game.getPlayersSize(); ++k) {
                        if (CFG.game.getCivsAtWar(CFG.game.getPlayer(k).getCivID(), iCivB) && CFG.game.getCiv(CFG.game.getPlayer(k).getCivID()).getNumOfProvinces() > 0 && CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() == CFG.game.getPlayer(k).getCivID()) {
                            CFG.game.getCiv(CFG.game.getPlayer(k).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_JoinedAWar(iAgressorCivID, iCivB));
                            break;
                        }
                    }
                }
                if (CFG.game.getCiv(iCivB).getAllianceID() > 0) {
                    for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(iCivB).getAllianceID()).getCivilizationsSize(); ++i) {
                        if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(iCivB).getAllianceID()).getCivilization(i)).getNumOfProvinces() > 0 && CFG.game.getAlliance(CFG.game.getCiv(iCivB).getAllianceID()).getCivilization(i) != iCivB) {
                            for (int j = 0; j < CFG.game.getPlayersSize(); ++j) {
                                if (CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getNumOfProvinces() > 0 && CFG.game.getCiv(iCivB).getAllianceID() == CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getAllianceID() && iCivB != CFG.game.getPlayer(j).getCivID() && CFG.game.getAlliance(CFG.game.getCiv(iCivB).getAllianceID()).getCivilization(i) != CFG.game.getPlayer(j).getCivID()) {
                                    CFG.game.getCiv(CFG.game.getPlayer(j).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_JoinedAWar(iCivB, iAgressorCivID));
                                }
                            }
                        }
                    }
                }
                else if (CFG.game.getCiv(iCivB).getPuppetOfCivID() != CFG.game.getCiv(iCivB).getCivID()) {
                    for (int k = 0; k < CFG.game.getPlayersSize(); ++k) {
                        if (CFG.game.getCivsAtWar(CFG.game.getPlayer(k).getCivID(), iAgressorCivID) && CFG.game.getCiv(CFG.game.getPlayer(k).getCivID()).getNumOfProvinces() > 0 && CFG.game.getCiv(iCivB).getPuppetOfCivID() == CFG.game.getPlayer(k).getCivID()) {
                            CFG.game.getCiv(CFG.game.getPlayer(k).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_JoinedAWar(iCivB, iAgressorCivID));
                            break;
                        }
                    }
                }
            }
            catch (final IndexOutOfBoundsException ex) {}
        }
        DiplomacyManager.updateFriendlyCiv(iAgressorCivID, iCivB);
    }
    
    protected final void joinWar(final int iCivA, final int iWarAgainstCivID, int nWarID) {
        if (iCivA == 0 || iWarAgainstCivID == 0 || iCivA == iWarAgainstCivID || CFG.game.getCivsAtWar(iCivA, iWarAgainstCivID)) {
            return;
        }
        this.war_CheckDiplomacy(iCivA, iWarAgainstCivID);
        if (nWarID >= 0 && nWarID < this.getWarsSize()) {
            this.setCivRelation_OfCivB(iCivA, iWarAgainstCivID, -100.0f);
            this.setCivRelation_OfCivB(iWarAgainstCivID, iCivA, -100.0f);
            for (int i = CFG.game.getCiv(iWarAgainstCivID).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
                if (CFG.game.getCiv(iWarAgainstCivID).civGameData.civPlans.warPreparations.get(i).onCivID == iCivA) {
                    CFG.game.getCiv(iWarAgainstCivID).civGameData.civPlans.warPreparations.remove(i);
                    CFG.game.getCiv(iWarAgainstCivID).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(iWarAgainstCivID).civGameData.civPlans.warPreparations.size();
                }
            }
            for (int i = CFG.game.getCiv(iCivA).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
                if (CFG.game.getCiv(iCivA).civGameData.civPlans.warPreparations.get(i).onCivID == iWarAgainstCivID) {
                    CFG.game.getCiv(iCivA).civGameData.civPlans.warPreparations.remove(i);
                    CFG.game.getCiv(iCivA).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(iCivA).civGameData.civPlans.warPreparations.size();
                }
            }
            CFG.historyManager.addHistoryLog(new HistoryLog_WarDeclaration(iCivA, iWarAgainstCivID));
            if (CFG.game.getCiv(iCivA).getAllianceID() > 0) {
                for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(iCivA).getAllianceID()).getCivilizationsSize(); ++i) {
                    if (CFG.game.getAlliance(CFG.game.getCiv(iCivA).getAllianceID()).getCivilization(i) != iCivA && CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(iCivA).getAllianceID()).getCivilization(i)).getNumOfProvinces() > 0 && CFG.game.getCivsAtWar(iWarAgainstCivID, CFG.game.getAlliance(CFG.game.getCiv(iCivA).getAllianceID()).getCivilization(i))) {
                        final int tempWar = CFG.game.getWarID(CFG.game.getAlliance(CFG.game.getCiv(iCivA).getAllianceID()).getCivilization(i), iWarAgainstCivID);
                        if (tempWar >= 0) {
                            nWarID = tempWar;
                            break;
                        }
                    }
                }
            }
            if (this.getWar(nWarID).getIsAggressor(iWarAgainstCivID)) {
                this.getWar(nWarID).addDefender(iCivA);
            }
            else {
                this.getWar(nWarID).addAggressor(iCivA);
            }
            CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War(iWarAgainstCivID, iCivA));
            CFG.game.getCiv(iWarAgainstCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War(iCivA, iWarAgainstCivID));
            if (iWarAgainstCivID != CFG.game.getCiv(iWarAgainstCivID).getPuppetOfCivID() && CFG.game.getCiv(iWarAgainstCivID).getPuppetOfCivID() != iCivA) {
                CFG.game.getCiv(CFG.game.getCiv(iWarAgainstCivID).getPuppetOfCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War_CivIsAtWar(iWarAgainstCivID, iCivA));
            }
            if (iCivA != CFG.game.getCiv(iCivA).getPuppetOfCivID() && CFG.game.getCiv(iCivA).getPuppetOfCivID() != iWarAgainstCivID) {
                CFG.game.getCiv(CFG.game.getCiv(iCivA).getPuppetOfCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_War_CivIsAtWar(iCivA, iWarAgainstCivID));
            }
        }
        DiplomacyManager.updateFriendlyCiv(iCivA, iWarAgainstCivID);
    }
    
    protected final void war_CheckDiplomacy(final int iAgressorCivID, final int iCivB) {
        if (this.getCivNonAggressionPact(iAgressorCivID, iCivB) > 0) {
            this.setCivNonAggressionPact(iAgressorCivID, iCivB, 0);
        }
        if (this.getMilitaryAccess(iAgressorCivID, iCivB) > 0) {
            this.setMilitaryAccess(iAgressorCivID, iCivB, 0);
            CFG.gameAction.accessLost_UpdateArmies(iCivB, iAgressorCivID);
        }
        if (this.getMilitaryAccess(iCivB, iAgressorCivID) > 0) {
            this.setMilitaryAccess(iCivB, iAgressorCivID, 0);
            CFG.gameAction.accessLost_UpdateArmies(iAgressorCivID, iCivB);
        }
        if (this.getDefensivePact(iAgressorCivID, iCivB) > 0) {
            this.setDefensivePact(iAgressorCivID, iCivB, 0);
        }
        if (this.getGuarantee(iAgressorCivID, iCivB) > 0) {
            this.setGuarantee(iAgressorCivID, iCivB, 0);
        }
        if (this.getGuarantee(iCivB, iAgressorCivID) > 0) {
            this.setGuarantee(iCivB, iAgressorCivID, 0);
        }
        if (CFG.game.getCiv(iAgressorCivID).getPuppetOfCivID() == iCivB) {
            CFG.game.getCiv(iAgressorCivID).setPuppetOfCivID(iAgressorCivID);
            CFG.gameAction.accessLost_UpdateArmies(iCivB, iAgressorCivID);
            CFG.gameAction.accessLost_UpdateArmies(iAgressorCivID, iCivB);
            if (CFG.game.getCiv(iCivB).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iCivB, iAgressorCivID);
            }
            if (CFG.game.getCiv(iAgressorCivID).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iAgressorCivID, iCivB);
            }
        }
        if (CFG.game.getCiv(iCivB).getPuppetOfCivID() == iAgressorCivID) {
            CFG.game.getCiv(iCivB).setPuppetOfCivID(iCivB);
            CFG.gameAction.accessLost_UpdateArmies(iCivB, iAgressorCivID);
            CFG.gameAction.accessLost_UpdateArmies(iAgressorCivID, iCivB);
            if (CFG.game.getCiv(iCivB).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iCivB, iAgressorCivID);
            }
            if (CFG.game.getCiv(iAgressorCivID).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iAgressorCivID, iCivB);
            }
        }
        if (CFG.game.getCiv(iAgressorCivID).getAllianceID() > 0 && CFG.game.getCiv(iAgressorCivID).getAllianceID() == CFG.game.getCiv(iCivB).getAllianceID()) {
            final int tAllianceID = CFG.game.getCiv(iAgressorCivID).getAllianceID();
            CFG.game.getAlliance(CFG.game.getCiv(iCivB).getAllianceID()).removeCivilization(iCivB);
            CFG.game.getCiv(iCivB).setAllianceID(0);
            CFG.game.getAlliance(CFG.game.getCiv(iAgressorCivID).getAllianceID()).removeCivilization(iAgressorCivID);
            CFG.game.getCiv(iAgressorCivID).setAllianceID(0);
            CFG.gameAction.accessLost_UpdateArmies(iCivB, iAgressorCivID);
            CFG.gameAction.accessLost_UpdateArmies(iAgressorCivID, iCivB);
            if (CFG.game.getCiv(iCivB).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iCivB, iAgressorCivID);
            }
            if (CFG.game.getCiv(iAgressorCivID).getControlledByPlayer()) {
                this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iAgressorCivID, iCivB);
            }
            for (int i = 0; i < CFG.game.getAlliance(tAllianceID).getCivilizationsSize(); ++i) {
                CFG.gameAction.accessLost_UpdateArmies(iCivB, CFG.game.getAlliance(tAllianceID).getCivilization(i));
                CFG.gameAction.accessLost_UpdateArmies(CFG.game.getAlliance(tAllianceID).getCivilization(i), iCivB);
                CFG.gameAction.accessLost_UpdateArmies(iAgressorCivID, CFG.game.getAlliance(tAllianceID).getCivilization(i));
                CFG.gameAction.accessLost_UpdateArmies(CFG.game.getAlliance(tAllianceID).getCivilization(i), iAgressorCivID);
                if (CFG.game.getCiv(iCivB).getControlledByPlayer()) {
                    this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iCivB, CFG.game.getAlliance(tAllianceID).getCivilization(i));
                }
                if (CFG.game.getCiv(iAgressorCivID).getControlledByPlayer()) {
                    this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(iAgressorCivID, CFG.game.getAlliance(tAllianceID).getCivilization(i));
                }
                if (CFG.game.getCiv(CFG.game.getAlliance(tAllianceID).getCivilization(i)).getControlledByPlayer()) {
                    this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(CFG.game.getAlliance(tAllianceID).getCivilization(i), iAgressorCivID);
                    this.war_CheckDiplomacy_AccessLost_UpdateFogOfWar(CFG.game.getAlliance(tAllianceID).getCivilization(i), iCivB);
                }
            }
            this.checkAlliances();
        }
    }
    
    protected final void war_CheckDiplomacy_AccessLost_UpdateFogOfWar(final int nPlayerCivID, final int inCivID) {
        try {
            if (CFG.FOG_OF_WAR > 0) {
                final int nPlayerID = CFG.game.getPlayerID_ByCivID(nPlayerCivID);
                if (nPlayerID >= 0) {
                    for (int i = 0; i < CFG.game.getCiv(inCivID).getNumOfProvinces(); ++i) {
                        CFG.game.getProvince(CFG.game.getCiv(inCivID).getProvinceID(i)).updateFogOfWar(nPlayerID);
                        CFG.game.getProvince(CFG.game.getCiv(inCivID).getProvinceID(i)).updateDrawArmy();
                    }
                    for (int i = 0; i < CFG.game.getCiv(inCivID).getArmyInAnotherProvinceSize(); ++i) {
                        CFG.game.getProvince(CFG.game.getCiv(inCivID).getArmyInAnotherProvince(i)).updateFogOfWar(nPlayerID);
                        CFG.game.getProvince(CFG.game.getCiv(inCivID).getArmyInAnotherProvince(i)).updateDrawArmy();
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
    }
    
    protected final void ressetVassal_CivID(final int iCivID) {
        this.getCiv(iCivID).setPuppetOfCivID(iCivID);
    }
    
    protected final void setVassal_OfCiv(final int iCivID_Lord, final int iCivID_Vassal) {
        if (this.getCiv(iCivID_Lord).getPuppetOfCivID() == iCivID_Vassal) {
            this.getCiv(iCivID_Lord).setPuppetOfCivID(iCivID_Lord);
        }
        this.getCiv(iCivID_Vassal).setPuppetOfCivID(iCivID_Lord);
    }


    protected final void setVassal_OfCiv(final int iCivID_Lord, final int iCivID_Vassal, final int autonomyLevel) {
        if (this.getCiv(iCivID_Lord).getPuppetOfCivID() == iCivID_Vassal) {
            this.getCiv(iCivID_Lord).setPuppetOfCivID(iCivID_Lord, autonomyLevel);
        }
        this.getCiv(iCivID_Vassal).setPuppetOfCivID(iCivID_Lord, autonomyLevel);
    }
    
    protected final float getCivRelation_OfCivB(final int iCivA, final int iCivB) {
        try {
            return this.getCiv(iCivA).getRelation(iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0.0f;
        }
    }
    
    protected final void setCivRelation_OfCivB(final int iCivA, final int iCivB, final float nOpinion) {
        try {
            this.getCiv(iCivA).setRelation(iCivB - 1, nOpinion);
            if ((int)nOpinion == -100) {
                this.getCiv(iCivB).setRelation(iCivA - 1, nOpinion);
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final int getGuarantee(final int iCivA, final int iCivB) {
        try {
            return this.getCiv(iCivA).getGuarantee(iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final boolean setGuarantee(final int iCivA, final int iCivB, final int nTurns) {
        try {
            return this.getCiv(iCivA).setGuarantee(iCivB - 1, nTurns);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final int getMilitaryAccess(final int iCivA, final int iCivB) {
        try {
            return this.getCiv(iCivA).getMilitaryAccess(iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final boolean setMilitaryAccess(final int iCivA, final int iCivB, final int nTurns) {
        try {
            return this.getCiv(iCivA).setMilitaryAccess(iCivB - 1, nTurns);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final int getCivNonAggressionPact(final int iCivA, final int iCivB) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).getNonAggressionPact(iCivB - iCivA - 1);
            }
            return this.getCiv(iCivB).getNonAggressionPact(iCivA - iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final int getCivTruce(final int iCivA, final int iCivB) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).getTruce(iCivB - iCivA - 1);
            }
            return this.getCiv(iCivB).getTruce(iCivA - iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final boolean setCivTruce(final int iCivA, final int iCivB, final int iNumOfTurns) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).setTruce(iCivB - iCivA - 1, iNumOfTurns);
            }
            return this.getCiv(iCivB).setTruce(iCivA - iCivB - 1, iNumOfTurns);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final boolean setCivNonAggressionPact(final int iCivA, final int iCivB, final int iNumOfTurns) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).setNonAggressionPact(iCivB - iCivA - 1, iNumOfTurns);
            }
            return this.getCiv(iCivB).setNonAggressionPact(iCivA - iCivB - 1, iNumOfTurns);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final int getDefensivePact(final int iCivA, final int iCivB) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).getDefensivePact(iCivB - iCivA - 1);
            }
            return this.getCiv(iCivB).getDefensivePact(iCivA - iCivB - 1);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final boolean setDefensivePact(final int iCivA, final int iCivB, final int iNumOfTurns) {
        try {
            if (iCivA < iCivB) {
                return this.getCiv(iCivA).setDefensivePact(iCivB - iCivA - 1, iNumOfTurns);
            }
            return this.getCiv(iCivB).setDefensivePact(iCivA - iCivB - 1, iNumOfTurns);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final boolean getCivsAtWar(final int iCivA, final int iCivB) {
        try {
            return (int)this.getCivRelation_OfCivB(iCivA, iCivB) <= -100;
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final boolean getCivsAreAllied(final int iCivA, final int iCivB) {
        try {
            return this.getCiv(iCivA).getAllianceID() > 0 && this.getCiv(iCivA).getAllianceID() == this.getCiv(iCivB).getAllianceID();
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected boolean isAlly(final int iCivA, final int nCivID) {
        return iCivA == nCivID || CFG.game.getCivsAreAllied(nCivID, iCivA) || CFG.game.getCiv(iCivA).getPuppetOfCivID() == nCivID || CFG.game.getCiv(nCivID).getPuppetOfCivID() == iCivA;
    }
    
    protected final boolean getSeaProvinceAttack(final int iCivA, final int nProvinceID) {
        for (int i = 1; i < this.getProvince(nProvinceID).getCivsSize(); ++i) {
            if (this.getCivsAtWar(iCivA, this.getProvince(nProvinceID).getCivID(i))) {
                return true;
            }
        }
        return false;
    }
    
    protected final boolean getCivsInAlliance(final int iCivA, final int iCivB) {
        try {
            return this.getCiv(iCivA).getAllianceID() > 0 && this.getCiv(iCivA).getAllianceID() == this.getCiv(iCivB).getAllianceID();
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return false;
        }
    }
    
    protected final boolean isAtPeace(final int nCivID) {
        for (int i = nCivID + 1; i < this.getCivsSize(); ++i) {
            if (this.getCiv(i).getNumOfProvinces() > 0 && (int)this.getCivRelation_OfCivB(nCivID, i) == -100) {
                return false;
            }
        }
        for (int i = nCivID - 1; i > 0; --i) {
            if (this.getCiv(i).getNumOfProvinces() > 0 && (int)this.getCivRelation_OfCivB(nCivID, i) == -100) {
                return false;
            }
        }
        return true;
    }
    
    protected final void moveCapitalToTheLargestCity(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
            int nLargestCity = CFG.game.getCiv(nCivID).getProvinceID(0);
            for (int i = 1; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation() > CFG.game.getProvince(nLargestCity).getPopulationData().getPopulation()) {
                    nLargestCity = CFG.game.getCiv(nCivID).getProvinceID(i);
                }
            }
            CFG.game.getCiv(nCivID).setCapitalProvinceID(nLargestCity);
        }
    }
    
    protected final boolean haveArmy_FogOfWarCheck(final int nProvinceID, final int nCivID) {
        for (int i = 0; i < this.getProvince(nProvinceID).getCivsSize(); ++i) {
            if (this.getProvince(nProvinceID).getCivID(i) == nCivID || this.getCiv(this.getProvince(nProvinceID).getCivID(i)).getPuppetOfCivID() == nCivID || this.getCiv(nCivID).getPuppetOfCivID() == this.getProvince(nProvinceID).getCivID(i) || (this.getCiv(nCivID).getAllianceID() > 0 && this.getCiv(nCivID).getAllianceID() == this.getCiv(this.getProvince(nProvinceID).getCivID(i)).getAllianceID())) {
                return true;
            }
        }
        return false;
    }
    
    protected final boolean isPlayerAlly_FogOfWarCheck(final int nPlayerCivID, final int nCivID) {
        return nPlayerCivID == nCivID || this.getCiv(nCivID).getPuppetOfCivID() == nPlayerCivID || this.getCiv(nPlayerCivID).getPuppetOfCivID() == nCivID || this.getCivsAreAllied(nPlayerCivID, nCivID);
    }
    
    protected final Civilization getCiv(final int i) {
        try {
            return this.lCivs.get(i);
        }
        catch (final IndexOutOfBoundsException ex) {
            return this.lCivs.get(0);
        }
    }
    
    protected final int getCivsSize() {
        return this.iCivsSize;
    }
    
    protected final Player getPlayer(final int i) {
        return this.lPlayers.get(i);
    }
    
    protected final int getPlayerID_ByCivID(final int nCivID) {
        for (int i = 0; i < this.getPlayersSize(); ++i) {
            if (this.getPlayer(i).getCivID() == nCivID) {
                return i;
            }
        }
        return -1;
    }
    
    protected final void setActiveProvinceID(final int nActiveProvinceID) {
        try {
            this.setActiveProvince_ExtraAction.extraAction(nActiveProvinceID);
            if (this.iActiveProvince >= 0) {
                if (this.getProvince(this.iActiveProvince).getSeaProvince()) {
                    for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++i) {
                        if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                            if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                                this.getProvince(this.iActiveProvince).getProvinceBordersSeaBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorderSeaBySea();
                            }
                            else {
                                this.removeDrawProvinceBorder_LandBySea(this.iActiveProvince, this.getProvince(this.iActiveProvince).getProvinceBordersLandBySea_ID(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)));
                                this.getProvince(this.iActiveProvince).getProvinceBordersLandBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder(this.iActiveProvince);
                            }
                        }
                        else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getSeaProvince()) {
                            this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersSeaBySea(this.iActiveProvince).updateDrawProvinceBorderSeaBySea();
                        }
                        else {
                            this.removeDrawProvinceBorder_LandBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandBySea_ID(this.iActiveProvince));
                            this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandBySea(this.iActiveProvince).updateDrawProvinceBorder(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                        }
                    }
                }
                else {
                    for (int i = 0; i < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++i) {
                        if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)) {
                            if (this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getIsCivilizationBorder()) {
                                if (this.isActiveCivRegion_CivID(this.getProvince(this.iActiveProvince).getCivID(), this.getProvince(this.iActiveProvince).getCivRegionID())) {
                                    this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.iActiveProvince).getCivID())).getRegionStyleID(this.getProvince(this.iActiveProvince).getCivRegionID())).updatePB(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                                }
                                else if (this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivID(), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivRegionID())) {
                                    this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivRegionID())).updatePB(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                                }
                                else {
                                    this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder(this.iActiveProvince);
                                }
                            }
                            else {
                                this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).updateDrawProvinceBorder(this.iActiveProvince);
                            }
                        }
                        else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.iActiveProvince).getIsCivilizationBorder()) {
                            if (this.isActiveCivRegion_CivID(this.getProvince(this.iActiveProvince).getCivID(), this.getProvince(this.iActiveProvince).getCivRegionID())) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.iActiveProvince).getCivID())).getRegionStyleID(this.getProvince(this.iActiveProvince).getCivRegionID())).updatePB(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i), this.iActiveProvince);
                            }
                            else if (this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivID(), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivRegionID())) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getCivRegionID())).updatePB(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i), this.iActiveProvince);
                            }
                            else {
                                this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.iActiveProvince).updateDrawProvinceBorder(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                            }
                        }
                        else {
                            this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i)).getProvinceBordersLandByLand(this.iActiveProvince).updateDrawProvinceBorder(this.getProvince(this.iActiveProvince).getNeighboringProvinces(i));
                        }
                    }
                }
            }
            final int oldActiveProvinceID = this.iActiveProvince;
            this.iActiveProvince = nActiveProvinceID;
            this.activeProvince_Animation_Data.resetAnimationData();
            if (oldActiveProvinceID >= 0) {
                this.getProvince(oldActiveProvinceID).updateDrawArmy();
            }
            if (this.iActiveProvince >= 0) {
                this.getProvince(this.iActiveProvince).updateDrawArmy();
                CFG.menuManager.getColorPicker().getColorPickerAction().setActiveProvince_Action();
            }
            if (CFG.menuManager.getInGameView()) {
                if (!CFG.chooseProvinceMode && !CFG.regroupArmyMode) {
                    this.checkProvinceActionMenu();
                }
                if (CFG.menuManager.getInGame_ProvinceBuild_Visible()) {
                    if (this.getActiveProvinceID() < 0) {
                        CFG.menuManager.setVisible_InGame_ProvinceBuild(false, false);
                    }
                    else if (this.getActiveProvinceID() != oldActiveProvinceID) {
                        if (CFG.game.getProvince(this.getActiveProvinceID()).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                            CFG.menuManager.setVisible_InGame_ProvinceBuild(false, false);
                        }
                        else {
                            BuildingsManager.iBuildInProvinceID = this.getActiveProvinceID();
                            CFG.menuManager.setVisible_InGame_ProvinceBuild(true, true);
                        }
                    }
                }
                if (CFG.viewsManager.getActiveViewID() >= 0) {
                    CFG.viewsManager.getActiveView().updateActiveProvinceID_ExtraAction(oldActiveProvinceID, this.getActiveProvinceID());
                }
                CFG.gameAction.updateInGame_ProvinceInfo();
            }
            if (CFG.isAndroid()) {
                CFG.menuManager.resetHoverActive();
            }
            this.lTIME_ACTIVE_CITIES = System.currentTimeMillis();
            if (this.iActiveProvince >= 0) {
                if (this.getProvince(this.iActiveProvince).getSeaProvince()) {
                    for (int j = 0; j < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++j) {
                        if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)) {
                            if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getSeaProvince()) {
                                this.activeProvinceBorder_SeaBySea_Style.update(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                            }
                            else {
                                this.addDrawProvinceBorder_LandBySea(this.iActiveProvince, this.getProvince(this.iActiveProvince).getProvinceBordersLandBySea_ID(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)));
                                this.activeProvinceBorder_LandBySea_Style.update(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                            }
                        }
                        else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getSeaProvince()) {
                            this.activeProvinceBorder_SeaBySea_Style.update(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                        }
                        else {
                            this.addDrawProvinceBorder_LandBySea(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getProvinceBordersLandBySea_ID(this.iActiveProvince));
                            this.activeProvinceBorder_LandBySea_Style.update(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                        }
                    }
                }
                else {
                    for (int j = 0; j < this.getProvince(this.iActiveProvince).getNeighboringProvincesSize(); ++j) {
                        if (this.iActiveProvince < this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)) {
                            if (this.getProvince(this.iActiveProvince).getProvinceBordersLandByLand(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getIsCivilizationBorder()) {
                                if (this.isActiveCivRegion_CivID(this.getProvince(this.iActiveProvince).getCivID(), this.getProvince(this.iActiveProvince).getCivRegionID())) {
                                    this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.iActiveProvince).getCivID())).getRegionStyleID(this.getProvince(this.iActiveProvince).getCivRegionID())).updatePB(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                                }
                                else if (this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivID(), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivRegionID())) {
                                    this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivRegionID())).updatePB(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                                }
                                else {
                                    this.activeProvinceBorder_LandByLand_Style.update(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                                }
                            }
                            else {
                                this.activeProvinceBorder_LandByLand_Style.update(this.iActiveProvince, this.getProvince(this.iActiveProvince).getNeighboringProvinces(j));
                            }
                        }
                        else if (this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getProvinceBordersLandByLand(this.iActiveProvince).getIsCivilizationBorder()) {
                            if (this.isActiveCivRegion_CivID(this.getProvince(this.iActiveProvince).getCivID(), this.getProvince(this.iActiveProvince).getCivRegionID())) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.iActiveProvince).getCivID())).getRegionStyleID(this.getProvince(this.iActiveProvince).getCivRegionID())).updatePB(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                            }
                            else if (this.isActiveCivRegion_CivID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivID(), this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivRegionID())) {
                                this.lCivRegion_Styles.get(this.lActive_CivRegion.get(this.getCivRegionID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivID())).getRegionStyleID(this.getProvince(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j)).getCivRegionID())).updatePB(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                            }
                            else {
                                this.activeProvinceBorder_LandByLand_Style.update(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                            }
                        }
                        else {
                            this.activeProvinceBorder_LandByLand_Style.update(this.getProvince(this.iActiveProvince).getNeighboringProvinces(j), this.iActiveProvince);
                        }
                    }
                }
            }
            CFG.setRender_3(true);
        }
        catch (final NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }
    
    protected final void updateProvinceNameWidth(final int nProvinceID) {
        try {
            if (nProvinceID >= 0) {
                CFG.fontMain.getData().setScale(0.8f);
                CFG.glyphLayout.setText(CFG.fontMain, this.getProvince(nProvinceID).getName());
                CFG.iProvinceNameWidth = (int)CFG.glyphLayout.width;
                CFG.fontMain.getData().setScale(1.0f);
            }
            else {
                CFG.iProvinceNameWidth = 0;
            }
        }
        catch (final NullPointerException | IllegalStateException | IndexOutOfBoundsException |
                     IllegalArgumentException ex) {
            CFG.iProvinceNameWidth = 0;
        }
    }
    
    protected final void updateProvinceNameWidth(final String sText) {
        CFG.fontMain.getData().setScale(0.8f);
        CFG.glyphLayout.setText(CFG.fontMain, sText);
        CFG.iProvinceNameWidth = (int)CFG.glyphLayout.width;
        CFG.fontMain.getData().setScale(1.0f);
    }
    
    protected final int getActiveProvinceID() {
        return this.iActiveProvince;
    }
    
    protected final Game_Scenarios getGameScenarios() {
        return this.gameScenarios;
    }
    
    protected final int getPlayersSize() {
        return this.iPlayersSize;
    }
    
    protected final List<Region> getRegions() {
        return this.lRegions;
    }
    
    protected final void updateRegionsSize() {
        this.iRegionsSize = this.lRegions.size();
    }
    
    protected final int getRegionID(final int nProvinceID) {
        for (int i = 0; i < this.iRegionsSize; ++i) {
            for (int j = 0; j < this.lRegions.get(i).getProvincesSize(); ++j) {
                if (this.lRegions.get(i).getProvince(j) == nProvinceID) {
                    return i;
                }
            }
        }
        Gdx.app.log("AoC", "REGION ERROR: " + nProvinceID);
        return 0;
    }
    
    protected final int getProvinceInViewID(final int iID) {
        try {
            return this.lProvincesInView.get(iID);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final int getSeaProvinceInViewID(final int iID) {
        try {
            return this.lSeaProvincesInView.get(iID);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final int getWastelandProvinceInViewID(final int iID) {
        try {
            return this.lWastelandProvincesInView.get(iID);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }
    
    protected final void addLoadArmiesWidth_ErrorIDs(final int nProvinceID) {
        for (int i = this.loadArmiesWidth_ErrorIDs.size() - 1; i >= 0; --i) {
            if (this.loadArmiesWidth_ErrorIDs.get(i) == nProvinceID) {
                return;
            }
        }
        this.loadArmiesWidth_ErrorIDs.add(nProvinceID);
    }
    
    protected final void updateLoadArmiesWidth_ErrorIDs(final SpriteBatch oSB) {
        for (int i = this.loadArmiesWidth_ErrorIDs.size() - 1; i >= 0; --i) {
            try {
                for (int j = 0; j < CFG.game.getProvince(this.loadArmiesWidth_ErrorIDs.get(i)).getCivsSize(); ++j) {
                    CFG.game.getProvince(this.loadArmiesWidth_ErrorIDs.get(i)).updateArmyWidth(j);
                }
            }
            catch (final IndexOutOfBoundsException ex) {}
            this.loadArmiesWidth_ErrorIDs.remove(i);
        }
    }

    protected final Province_Animation2 getProvinceAnimation_Active_Data() {
        return this.activeProvince_Animation_Data;
    }

    protected final Province_Animation_MoveUnits2 getProvinceAnimation_Highlighted_Data() {
        return this.highlightedProvince_AnimationData;
    }

    protected final int getAvailableCivilizations() {
        return this.iAvailableCivilizations;
    }

    protected final void setAvailableCivilizations(int iAvailableCivilizations) {
        this.iAvailableCivilizations = iAvailableCivilizations;
    }

    protected final void setUpdateProvincesInView(boolean updateProvincesInView) {
        this.updateProvincesInView = updateProvincesInView;
    }

    protected final RandomTurnOrder getRTO() {
        return this.oRTO;
    }

    protected final Selected_Provinces getSelectedProvinces() {
        return this.selectedProvinces;
    }

    protected int getPeaceTreaty_GameDataID(String sTag) {
        for(int i = this.lPeaceTreaties.size() - 1; i >= 0; --i) {
            if (this.lPeaceTreaties.get(i).PEACE_TREATY_TAG.equals(sTag)) {
                return i;
            }
        }

        return -1;
    }

    protected boolean getPeaceTreaty_GameData_AlreadySent(int nCivA, int nCivB) {
        for(int i = this.lPeaceTreaties.size() - 1; i >= 0; --i) {
            for(int j = 0; j < this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Defenders.size(); ++j) {
                int k;
                if (this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Defenders.get(j).iCivID == nCivA) {
                    for(k = 0; k < this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Aggressors.size(); ++k) {
                        if (this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Aggressors.get(k).iCivID == nCivB) {
                            return true;
                        }
                    }
                } else if (this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Defenders.get(j).iCivID == nCivB) {
                    for(k = 0; k < this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Aggressors.size(); ++k) {
                        if (this.lPeaceTreaties.get(i).peaceTreaty_GameData.lCivsData_Aggressors.get(k).iCivID == nCivA) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    interface ActiveProvinceBorderStyle {
        void update(int var1, int var2);
    }

    interface HighlightedProvince_Animation_UpdateOffset {
        void updateOffset();
    }
    
    static {
        Game.MAX_BELOW_ZERO_POINT_X = 0;
        Game.lBuildingsImages = new ArrayList<Integer>();
        Game.iBuildingsWidth = 0;
    }

    private static class DrawProvinceBorder_LandBySeaIDs {
        int iProvinceID;
        int withProvinceID;

        DrawProvinceBorder_LandBySeaIDs(int iProvinceID, int withProvinceID) {
            this.iProvinceID = iProvinceID;
            this.withProvinceID = withProvinceID;
        }
    }

    protected interface DrawMoveUnitsArmy
    {
        void drawMoveUnitsArmy(final SpriteBatch p0, final float p1);
    }
    
    protected interface SetActiveProvince_ExtraAction
    {
        void extraAction(final int p0);
    }
}
