package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Game_Action {
    private TurnStates activeTurnAction;
    private MoveUnits_TurnData currentMoveUnits;
    private int iPlayerAttack_ShowArmyInProvinceID;
    protected boolean SHOW_REPORT;
    private Turn_Actions turnActions;
    private Turn_NewTurn turnNewTurn;
    protected boolean updatePosOfMap_NewTurn;
    protected static final float POINTS_PER_ENEMY = 6.0F;
    protected static float RISE_REVOLT_RISK_HAPPINESS = 0.56F;
    protected static float RISE_REVOLT_RISK_STABILITY = 0.62F;
    protected int eRTO_START;
    protected int eRTO_START2;
    protected int eRTO_START3;
    protected static final int MAX_RELATION = 30;
    protected static final int MIN_RELATION = -20;
    protected static final float MAX_RELATION_DIFF = 0.295F;
    protected static final float MIN_RELATION_DIFF = 0.0145F;
    protected static final float UPRISE_MIN = 0.16F;
    protected static final float UPRISE_IGNITE = 0.64F;
    protected static final int SPAWN_REVOLUTIONARY_ARMY_MIN = 10;
    protected static final int SPAWN_REVOLUTIONARY_ARMY_RANDOM = 50;
    protected int diceAggressors;
    protected int diceDefenders;
    protected int diceAggressorsCivID;
    protected int diceDefendersCivID;
    protected static final float DICE_ROLL_BONUS = 2.5F;
    protected static final int TECHNOLGY_LEVEL_BONUS_ARMY = 18;
    protected static final float DEFENSE_BONUS_LOSS_PER_TURN_FOR_NOT_SUPPLIED_PROVINCE = 0.1F;
    protected static final int NOT_SUPPLIED_PROVINCE_STRAVE_START_NUM_OF_TURNS = 2;
    protected static final float NOT_SUPPLIED_PROVINCE_STRAVE__PERC_PER_TURN = 0.04F;
    protected static final int NOT_SUPPLIED_PROVINCE_LOOSE_CONTROL = 10;
    protected static boolean gameEnded = false;
    protected static final float RECRUITABLE_ARMY_OWN_POP = 0.175F;
    protected static final float RECRUITABLE_ARMY_OTHER_POP_ALLIANCE = 0.125F;
    protected static final float RECRUITABLE_ARMY_NEUTRAL_POP = 0.0675F;
    protected static final float RECRUITABLE_ARMY_OTHER_POP = 0.00725F;
    protected static final float RECRUITABLE_ARMY_OTHER_POP_ATWAR = 0.0025F;
    protected static final float RECRUIT_HAPPINESS_CHANGE = 0.1375F;
    protected static final float RECRUIT_ECONOMY_CHANGE = 0.575F;
    protected static final float RECRUIT_DEVELOPMENT_CHANGE = 0.1625F;
    protected static final float DISBAND_PERC_POP = 0.05F;
    protected static final float MOVE_CAPITAL_HAPPINESS_CHANGE_OLD = 0.12168F;
    protected static final float MOVE_CAPITAL_HAPPINESS_CHANGE_NEW = 0.025F;
    protected static final int MOVE_CAPITAL_LOCK_MOVING_FOR_X_TURNS = 50;
    protected static final float BASE_COST_OF_MOVE_CAPITAL_PERC = 0.1925F;
    protected static final float BASE_COST_OF_MOVE_CAPITAL_POP_OF_CAPITAL_PERC = 0.125F;

    Game_Action() {
        this.activeTurnAction = TurnStates.INPUT_ORDERS;
        this.currentMoveUnits = null;
        this.iPlayerAttack_ShowArmyInProvinceID = -1;
        this.SHOW_REPORT = false;
        this.updatePosOfMap_NewTurn = false;
        this.eRTO_START = 0;
        this.eRTO_START2 = 0;
        this.eRTO_START3 = 0;
    }

    protected final void tryToTakeNexTurn() {
        if (CFG.menuManager.getVisibleInGame_Event()) {
            boolean skipEvents = CFG.settingsManager.autoskipEvents;
            while (skipEvents) {
                skipEvents = CFG.menuManager.closeInGame_Event();
                CFG.toast.setInView(CFG.langManager.get("SkippedSingleEvent") + "!");
            }

            //if still event, center
            if (CFG.menuManager.getVisibleInGame_Event()) {
                CFG.menuManager.centerInGame_Event();
            }

            return;
        }

        if (!CFG.SPECTATOR_MODE && this.activeTurnAction == TurnStates.INPUT_ORDERS) {
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessagesSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).requestsResponse && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iNumOfTurnsLeft <= 1) {
                    this.checkMessages_PauseRTS();
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAction(i);
                    CFG.toast.setInView(CFG.langManager.get("TheMessageRequiresAResponse"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                    CFG.toast.setTimeInView(3000);
                    return;
                }
            }

            this.checkMessages_PauseRTS();
        }

        this.nextTurn();


    }

    protected final void checkMessages_PauseRTS() {
        if (!CFG.SPECTATOR_MODE) {
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessagesSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).willPauseTheGame) {
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).willPauseTheGame = false;
                    if (!RTS.PAUSE) {
                        RTS.pauseUnpause();
                        return;
                    }
                }
            }
        }
    }

    protected final void nextTurn() {
        this.resetTurnData();
        this.hideAllViews();
        switch (this.activeTurnAction) {
            case INPUT_ORDERS: {
                CFG.game.resetLastActiveProvince();
                if (CFG.game.getPlayersSize() == 1) {
                    this.updatePlayerData();
                    this.endOfInputOrders();
                } else {
                    this.inputOrders();
                }
                return;
            }
            case LOAD_AI_RTO: {
                CFG.menuManager.updateInGameRTO(false);
                this.turnMoves();
                Gdx.app.log("AoC", "GA, LOAD_AI_RTO -> AI end");
                return;
            }
            case TURN_ACTIONS: {
                this.turnMoves();
                return;
            }
            case LOADING_NEXT_TURN: {
                this.startNewTurn_End();
            }
            case START_NEXT_TURN: {
            }
            default: {
            }
        }
    }

    private final void endOfInputOrders() {
        CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).setClickable(false);
        this.activeTurnAction = TurnStates.LOAD_AI_RTO;
        if (this.getNumOfPlayersInGame() > 1) {
            CFG.menuManager.updateInGame_TOP_All_NextTurnActions(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        }

        this.eRTO_START = 0;
        this.eRTO_START2 = 0;
        this.eRTO_START3 = 0;
        CFG.game.getRTO().buildRandomOrder();
        CFG.menuManager.updateInGameRTO(true);
        if (!CFG.isDesktop()) {
            Turn_Actions.runRevolts();
        }

        this.turnActions = new Turn_Actions();
        this.turnActions.start();
    }

    protected final void startNewTurn() {
        Menu_InGame.TIME_CONTINUE = -1L;
        CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).setClickable(false);
        if (CFG.isAndroid()) {
            Turn_NewTurn.doAction();
            this.startNewTurn_End();
        } else {
            this.activeTurnAction = TurnStates.LOADING_NEXT_TURN;
            this.turnNewTurn = new Turn_NewTurn();
            this.turnNewTurn.start();
        }

    }

    protected final void startNewTurn_End() {
        CFG.PLAYER_TURNID = 0;
        CFG.gameAction.loadActivePlayerData();
        this.updatePosOfMap_NewTurn = false;
        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        CFG.map.getMapBG().disposeMinimapOfCivilizations();
        CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).setText(CFG.langManager.get("NextTurn"));
        CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).setClickable(true);
        CFG.gameAction.setActiveTurnState(TurnStates.INPUT_ORDERS);
        Game_Render.updateDrawMoveUnits();
        CFG.game.updateDrawMoveUnitsArmy();
        Menu_InGame_Messages.START_ANIMATION = true;
        if (Game_Calendar.TURN_ID % 100 == 92 && !CFG.SPECTATOR_MODE) {
            CFG.soundsManager.playSound(SoundsManager.SOUND_CROW);
        }
        Game_Render.updateRenderer();
        CFG.game.checkProvinceActionMenu();
    }

    private final void updatePlayerData() {
        CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_PosX = CFG.map.getMapCoordinates().getPosX();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_PosY = CFG.map.getMapCoordinates().getPosY();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).fBefore_Scale = CFG.map.getMapScale().getCurrentScale();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).iACTIVE_VIEW_MODE = CFG.viewsManager.getActiveViewID();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_CivInfo = CFG.menuManager.getVisible_InGame_CivInfo() ? CFG.getActiveCivInfo() : -1;
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_ManageInfo = CFG.menuManager.getVisible_InGame_ManageInfo() ? true : false;
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Outliner = CFG.menuManager.getVisible_Menu_InGame_Outliner();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_CensusOfProvince = CFG.menuManager.getVisibleInGame_CensusOfProvince() ? Menu_InGame_CensusOfProvince.PROVINCE_ID : -1;
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Wars = CFG.menuManager.getVisibleInGame_Wars();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_WarStats = CFG.menuManager.getVisibleInGame_WarDetails() ? Menu_InGame_WarDetails.WAR_ID : -1;
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Alliances = CFG.menuManager.getVisibleInGame_MilitaryAlliances();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Alliance = CFG.menuManager.getVisible_InGame_Alliance() ? Menu_InGame_Alliance.ALLIANCE_ID : -1;
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Rank = CFG.menuManager.getVisibleInGame_Rank();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_ConqueredProvinces = CFG.menuManager.getVisibleInGame_ConquredProvinces();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_VictoryConditions = CFG.menuManager.getVisibleInGame_VictoryConditions();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_BuildingsConstructed = CFG.menuManager.getVisibleInGame_BuildingsConstructed();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_RecruitedArmy = CFG.menuManager.getVisibleInGame_RecruitedArmy();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Tribute = CFG.menuManager.getVisibleInGame_Tribute();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Technology = CFG.menuManager.getVisibleInGame_Technology();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_Army = CFG.menuManager.getVisibleInGame_Army();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_WorldPop = CFG.menuManager.getVisibleInGame_WorldPopulation();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_MapModes = CFG.menuManager.getVisible_InGame_MapModes();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_History = CFG.menuManager.getVisibleInGame_History();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_BuildingsMore = CFG.menuManager.getInGame_ProvinceBuild_Visible();
        CFG.game.getPlayer(CFG.PLAYER_TURNID).visible_HRE = CFG.menuManager.getVisibleInGame_HRE();
        this.hideExtraViews();
    }

    protected final void hideExtraViews() {
        try {
            CFG.menuManager.setVisible_InGame_CivInfo(false);
            CFG.menuManager.setVisible_InGame_FlagAction(false);
            CFG.menuManager.setVisible_InGame_CivManage(false);
            CFG.menuManager.setVisibleInGame_WarDetails(false);
            CFG.menuManager.setVisibleInGame_Wars(false);
            CFG.menuManager.setVisibleInGame_CensusOfProvince(false);
            CFG.menuManager.setVisibleInGame_Rank(false);
            CFG.menuManager.setVisibleInGame_MilitaryAlliances(false);
            CFG.menuManager.setVisible_InGame_Alliance(false);
            CFG.menuManager.setVisible_Menu_InGame_Outliner(false);
            CFG.menuManager.setVisibleInGame_WorldPopulation(false);
            CFG.menuManager.setVisible_InGame_MapModes(false);
            CFG.menuManager.setVisibleInGame_Playlist(false);
            CFG.menuManager.setVisibleInGame_WarPreparations(false);
            CFG.menuManager.setVisibleInGame_ConquredProvinces(false);
            CFG.menuManager.setVisibleInGame_VictoryConditions(false);
            CFG.menuManager.setVisibleInGame_BuildingsConstructed(false);
            CFG.menuManager.setVisibleInGame_RecruitedArmy(false);
            CFG.menuManager.setVisibleInGame_Tribute(false);
            CFG.menuManager.setVisibleInGame_Technology(false);
            CFG.menuManager.setVisibleInGame_Wonders(false);
            CFG.menuManager.setVisibleInGame_SendMessage(false);
            CFG.menuManager.setVisibleInGame_Plunder(false);
            CFG.menuManager.setVisibleInGame_MessageView(false);
            CFG.menuManager.setVisible_Menu_InGame_War(false);
            CFG.menuManager.setVisible_Menu_InGame_CapitalMoved(false);
            CFG.menuManager.setVisible_Menu_InGame_VassalReleased(false);
            CFG.menuManager.setVisible_Menu_InGame_CityHaveBeenFounded(false);
            CFG.menuManager.setVisible_Menu_InGame_AllianceInfo(false);
            CFG.menuManager.setVisible_InGame_Budget(false);
            CFG.menuManager.setVisible_Menu_InGame_CurrentWars(false);
            CFG.menuManager.setVisible_InGame_HRE(false);
            CFG.menuManager.setVisible_InGame_HRE_VoteFor(false);
            CFG.menuManager.setVisible_Menu_InGame_Graph(false);
            CFG.menuManager.setVisibleInGame_History(false);
        } catch (IndexOutOfBoundsException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }
        }

    }

    private final void inputOrders() {
        this.updatePlayerData();
        if (CFG.PLAYER_TURNID == CFG.game.getPlayersSize() - 1) {
            this.endOfInputOrders();
        } else {
            ++CFG.PLAYER_TURNID;
            this.updatePosOfMap_NewTurn = true;
            this.loadActivePlayerData();
            if (CFG.FOG_OF_WAR == 2) {
                CFG.map.getMapBG().disposeMinimapOfCivilizations();
            }
        }

    }

    protected final void updateIsSupplied() {
        try {
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                    for (int j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
                        if (!CFG.game.getCiv(i).getCivRegion(j).setIsSupplied(CFG.game.getCiv(i).getCivRegion(j).getSeaAccess() || CFG.game.getCiv(i).getCivRegion(j).getHaveNotOccupiedProvince())) {
                            try {
                                for (int k = 0; k < CFG.game.getCiv(i).getCivRegion(j).getProvincesSize(); ++k) {
                                    for (int o = 0; o < CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvincesSize(); ++o) {
                                        if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getWasteland() < 0) {
                                            if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID() == 0) {
                                                CFG.game.getCiv(i).getCivRegion(j).setIsSupplied(true);
                                                k = CFG.game.getCiv(i).getCivRegion(j).getProvincesSize();
                                                break;
                                            }

                                            if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID() != i && (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID()).getCivRegion(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivRegionID()).getSeaAccess() || CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID()).getCivRegion(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivRegionID()).getHaveNotOccupiedProvince()) && (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID()).getPuppetOfCivID() == i || CFG.game.getCiv(i).getPuppetOfCivID() == CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID() || CFG.game.getCiv(i).getAllianceID() > 0 && CFG.game.getCiv(i).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID()).getAllianceID() || CFG.game.getMilitaryAccess(i, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k)).getNeighboringProvinces(o)).getCivID()) > 0)) {
                                                CFG.game.getCiv(i).getCivRegion(j).setIsSupplied(true);
                                                k = CFG.game.getCiv(i).getCivRegion(j).getProvincesSize();
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (IndexOutOfBoundsException var5) {
                                CFG.exceptionStack(var5);
                            }
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException var6) {
        }

        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0 && CFG.game.getProvince(i).getCivID() > 0) {
                CFG.game.getProvince(i).updateIsNotSuppliedForXTurns();
                CFG.game.getProvince(i).updateDefensivePosition();
            }
        }

    }

    protected final void updateCivsHappiness() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.updateCivsHappiness(i);
        }

    }

    protected final void updateCivsHappiness(int nCivID) {
        CFG.game.getCiv(nCivID).setHappiness((int) (this.getCivHappiness(nCivID) * 100.0F));
    }

    protected final float getCivHappiness(int nCivID) {
        float tHappiness = 0.0F;
        CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.clear();

        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tHappiness += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getHappiness();
            if (CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_PROVINCE_HAPPINESS_RUN_FESTIVAL > CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getHappiness()) {
                CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.add(CFG.game.getCiv(nCivID).getProvinceID(i));
            }
        }

        for (int i = CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() - 1; i >= 0; --i) {
            if (CFG.game.getCiv(nCivID).isFestivalOrganized((Integer) CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i))) {
                CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.remove(i);
            }
        }

        return tHappiness / (float) CFG.game.getCiv(nCivID).getNumOfProvinces();
    }

    protected final void updateCivsMovementPoints() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.updateCivsMovementPoints(i);
        }

    }

    protected final void updateCivsMovementPoints(int nCivID) {
        CFG.game.getCiv(nCivID).setMovePoints(this.getMovementPoints_BaseValue(nCivID) + this.getMovementPoints_FromCivSize(nCivID) + this.getMovementPoints_FromTechnology(nCivID));
    }

    protected final int getMovementPoints_BaseValue(int nCivID) {
        return 6 + (int) ((float) CFG.gameAges.getAge_StartingMovementPoints(Game_Calendar.CURRENT_AGEID) * this.modifierMovementPoints_CivID(nCivID) * (1.0F + CFG.game.getCiv(nCivID).getModifier_MovementPoints()));
    }

    protected final int getMovementPoints_FromCivSize(int nCivID) {
        return (int) ((float) CFG.game.getCiv(nCivID).getNumOfProvinces() * CFG.gameAges.getAge_MovementPointsModifier(Game_Calendar.CURRENT_AGEID) * Math.min(CFG.game.getCiv(nCivID).getTechnologyLevel() * 1.213854F, 1.0F) * this.modifierMovementPoints_CivID(nCivID) * (1.0F + CFG.game.getCiv(nCivID).getModifier_MovementPoints()));
    }

    protected final int getMovementPoints_FromTechnology(int nCivID) {
        return (int) ((float) CFG.gameAges.getAge_StartingMovementPoints(Game_Calendar.CURRENT_AGEID) * CFG.game.getCiv(nCivID).getTechnologyLevel() * 2.143798F * (1.0F + CFG.game.getCiv(nCivID).getModifier_MovementPoints()));
    }

    protected final void updateCivsDiplomacyPoints_StartTheGame() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.updateCivsDiplomacyPoints(i);
            CFG.game.getCiv(i).setDiplomacyPoints((int) Math.max((float) CFG.game.getCiv(i).getDiplomacyPoints() * 2.65F, 22.0F));
        }

    }

    protected final void updateCivsDiplomacyPoints() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.updateCivsDiplomacyPoints(i);
        }

    }

    protected final void updateCivsDiplomacyPoints(int nCivID) {
        CFG.game.getCiv(nCivID).setDiplomacyPoints(CFG.game.getCiv(nCivID).getDiplomacyPoints() + this.getUpdateCivsDiplomacyPoints(nCivID));
    }

    protected final int getUpdateCivsDiplomacyPoints(int nCivID) {
        return Math.max(this.getDiplomacyPoints_BaseValue(nCivID) + this.getDiplomacyPoints_FromEnemies(nCivID) + this.getDiplomacyPoints_FromRank(nCivID) + this.getDiplomacyPoints_FromTechnology(nCivID) - DiplomacyManager.getCostOfCurrentDiplomaticActionsUpdate(nCivID), 5);
    }

    protected final void updateCivDecisions(int iCivID) {
        String sCivTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(iCivID).getCivTag());
        CFG.civDecision_GameData = null;
        try {
            String[] tagsSPLITED = null;
            if (CFG.isDesktop()) {
                final List<String> tempFiles = CFG.getFileNames("game/decisions/");
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
                FileHandle tempFileT = Gdx.files.internal("game/decisions/Age_of_Civilizations");
                if (CFG.readLocalFiles()) {
                    tempFileT = Gdx.files.local("game/decisions/Age_of_Civilizations");
                }
                final String tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
            }

            for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                try {
                    try {
                        final FileHandle file = Gdx.files.local("game/decisions/" + tagsSPLITED[j]);
                        CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                    } catch (final GdxRuntimeException ex) {
                        final FileHandle file = Gdx.files.internal("game/decisions/" + tagsSPLITED[j]);
                        CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                    }

                    //if decision is applied to this civ, add to active civ decisions
                    if (CFG.civDecision_GameData.containsCiv(sCivTag)) {
                        CFG.game.getCiv(iCivID).civGameData.addDecision(CFG.civDecision_GameData.copy());
                    }
                } catch (final ClassNotFoundException | IOException | NullPointerException e) {
                    CFG.exceptionStack(e);
                }
            }
        } catch (final GdxRuntimeException ex6) {
        }
    }

    //add decisions.json decisions to all player civ leaders
    protected final void updatePlayerDecisions() {
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getPlayer(i).getCivID() > 0) {
                try {
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.clearDecisions();
                    for (Decision_GameData decision : CFG.gameDecisions.lDecisions) {
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.addDecision(decision.copy());
                    }
                    updateCivDecisions(CFG.game.getPlayer(i).getCivID());
                } catch (NullPointerException | GdxRuntimeException ex) {
                    DynamicEventManager_Leader.safeReplaceLeader(CFG.game.getPlayer(i).getCivID());

                    for (Decision_GameData decision : CFG.gameDecisions.lDecisions) {
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.addDecision(decision.copy());
                    }
                    updateCivDecisions(CFG.game.getPlayer(i).getCivID());
                }
            }
        }
    }

    //initialize class perceptions of leader
    protected final void updateClassPerceptions() {
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getPlayer(i).getCivID() > 0) {
                try {
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.clearClassViews();

                    float stabFactor = (0.50F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getStability());
                    stabFactor += (-0.70F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getTaxationLevel());

                    stabFactor += (0.70F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments());
                    Game_NextTurnUpdate.updateClassPerceptionBoosts(CFG.game.getPlayer(i).getCivID(), 0, stabFactor, false);
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(0, stabFactor);
                    stabFactor -= (0.70F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments());

                    stabFactor += (0.70F * ((CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Goods() + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments()) / 2.0F));
                    Game_NextTurnUpdate.updateClassPerceptionBoosts(CFG.game.getPlayer(i).getCivID(), 1, stabFactor, false);
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(1, stabFactor);
                    stabFactor -= (0.70F * ((CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Goods() + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments()) / 2.0F));

                    stabFactor += (0.70F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Goods());
                    Game_NextTurnUpdate.updateClassPerceptionBoosts(CFG.game.getPlayer(i).getCivID(), 2, stabFactor, false);
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(2, stabFactor);
                } catch (NullPointerException | GdxRuntimeException ex) {
                    DynamicEventManager_Leader.safeReplaceLeader(CFG.game.getPlayer(i).getCivID());

                    try {
                        float stabFactor = (0.80F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getStability());
                        stabFactor += (-1.30F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getTaxationLevel());

                        stabFactor += (0.55F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments());
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(0, stabFactor);
                        stabFactor -= (0.55F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Investments());
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(1, stabFactor);
                        stabFactor += (0.55F * CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getSpendings_Goods());
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(2, stabFactor);
                    } catch (NullPointerException | GdxRuntimeException ex2) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex2);
                        }
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(0, 50);
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(1, 50);
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(2, 50);
                    }
                }
            }
        }
    }

    protected final int getDiplomacyPoints_BaseValue(int nCivID) {
        return 1 + (int) ((float) CFG.gameAges.getAge_StartingDiplomacyPoints(Game_Calendar.CURRENT_AGEID) * this.modifierMovementPoints_CivID(nCivID) * 0.375F);
    }

    protected final int getDiplomacyPoints_FromTechnology(int nCivID) {
        return (int) ((float) CFG.gameAges.getAge_StartingDiplomacyPoints(Game_Calendar.CURRENT_AGEID) * CFG.game.getCiv(nCivID).getTechnologyLevel() * 2.75F);
    }

    protected final int getDiplomacyPoints_FromRank(int nCivID) {
        return (int) ((float) CFG.gameAges.getAge_StartingDiplomacyPoints(Game_Calendar.CURRENT_AGEID) * (1.0F - (float) CFG.game.getCiv(nCivID).getRankPosition() / (float) CFG.game.getCivsSize()) * 0.775F);
    }

    protected final int getDiplomacyPoints_FromEnemies(int nCivID) {
        return (int) (-6.0F + (float) Math.min(CFG.oAI.MIN_NUM_OF_RIVALS, CFG.game.getCiv(nCivID).getHatedCivsSize()) * 6.0F);
    }

    protected float modifierMovementPoints_CivID(int nCivID) {
        if (CFG.game.getCiv(nCivID).getControlledByPlayer()) {
            switch (CFG.DIFFICULTY) {
                case 0:
                    return 1.3F;
                case 1:
                    return 1.15F;
                case 2:
                    return 1.0F;
                case 3:
                    return 0.95F;
            }
        }

        switch (CFG.DIFFICULTY) {
            case 0:
                return 0.8F;
            case 1:
                return 0.95F;
            case 2:
                return 1.05F;
            case 3:
                return 1.15F;
            default:
                return 1.0F;
        }
    }

    protected final void turnMoves() {
        if (this.currentMoveUnits != null && this.currentMoveUnits.getMoveUnitsSize() > 0) {
            this.turnMoves_MoveCurrentArmy();
            return;
        }
        if (CFG.menuManager.getInGame_Report_Visible()) {
            CFG.menuManager.setInGame_Report_Visible(false);
        }
        for (int e = this.eRTO_START2; e < CFG.game.getRTO().getRTOSize(); ++e, ++this.eRTO_START2) {
            this.turnMoves_UpdatePlayersFogOfWar(CFG.game.getRTO().getRTO(e));
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnitsSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getNumOfUnits() > 39 && CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID() > 0 && !CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getFromProvinceID()).isOccupied() && CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).isOccupied() && CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID())) {
                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e))) {
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e)));
                    }
                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getNumOfUnits() > 0) {
                        (this.currentMoveUnits = new MoveUnits_TurnData(CFG.game.getRTO().getRTO(e))).addMoveUnits(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i), CFG.game.getRTO().getRTO(e));
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(i--);
                        if (!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince()) {
                            for (int k = i + 1; k < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnitsSize(); ++k) {
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getToProvinceID()) {
                                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e))) {
                                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e)));
                                    }
                                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getNumOfUnits() > 0) {
                                        this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k), CFG.game.getRTO().getRTO(e));
                                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(k--);
                                    }
                                }
                            }
                            if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID() > 0) {
                                for (int a = 0; a < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilizationsSize(); ++a) {
                                    if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a) != CFG.game.getRTO().getRTO(e)) {
                                        for (int j = 0; j < CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnitsSize(); ++j) {
                                            if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getToProvinceID()) {
                                                if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getFromProvinceID()).getArmyCivID(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a))) {
                                                    CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getFromProvinceID()).getArmyCivID(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)));
                                                }
                                                if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getNumOfUnits() > 0) {
                                                    this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a));
                                                    CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).removeMove(j--);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            for (int a = 1; a < CFG.game.getCivsSize(); ++a) {
                                if (a != CFG.game.getRTO().getRTO(e) && (CFG.game.getCiv(a).getPuppetOfCivID() == CFG.game.getRTO().getRTO(e) || a == CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getPuppetOfCivID())) {
                                    for (int j = 0; j < CFG.game.getCiv(a).getMoveUnitsSize(); ++j) {
                                        if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(a).getMoveUnits(j).getToProvinceID()) {
                                            if (CFG.game.getCiv(a).getMoveUnits(j).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(a).getMoveUnits(j).getFromProvinceID()).getArmyCivID(a)) {
                                                CFG.game.getCiv(a).getMoveUnits(j).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(a).getMoveUnits(j).getFromProvinceID()).getArmyCivID(a));
                                            }
                                            if (CFG.game.getCiv(a).getMoveUnits(j).getNumOfUnits() > 0) {
                                                this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(a).getMoveUnits(j), a);
                                                CFG.game.getCiv(a).removeMove(j--);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        int attackingArmy = 0;
                        for (int o = 0; o < this.currentMoveUnits.getMoveUnitsSize(); ++o) {
                            attackingArmy += this.currentMoveUnits.getMoveUnits(o).getNumOfUnits();
                        }
                        Gdx.app.log("AoC", "attackingArmy: " + attackingArmy);
                        Gdx.app.log("AoC", "MIN_ARMY_TO_ATTACK: 10");
                        Gdx.app.log("AoC", "atWar: " + CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()));
                        if (attackingArmy < 10 && CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() > 0 && CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID())) {
                            Gdx.app.log("AoC", "attackingArmy: remove");
                            this.currentMoveUnits = null;
                        } else {
                            for (int o = 0; o < this.currentMoveUnits.getMoveUnitsSize(); ++o) {
                                this.currentMoveUnits.getMoveUnits(o).getMoveUnitsLine().updateMoveTime();
                            }

                            if (!RTS.SHOW_BATTLE_RESULTS) {
                                this.turnMoves_MoveCurrentArmy();
                            } else if (CFG.SHOW_ALL_MOVES || CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getControlledByPlayer() || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getControlledByPlayer()) {
                                if (!CFG.SHOW_ONLY_COMBAT_MOVES) {
                                    CFG.map.getMapCoordinates().centerToProvinceID(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                                    if (CFG.viewsManager.getActiveViewID() >= 0) {
                                        CFG.viewsManager.disableAllViews();
                                    }
                                    return;
                                }
                                if (((CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince() && CFG.game.getSeaProvinceAttack(this.currentMoveUnits.getCivID(0), this.currentMoveUnits.getMoveUnits(0).getToProvinceID())) || (!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince() && this.turnMoves_IsACombatMove(this.currentMoveUnits.getCivID(0), this.currentMoveUnits.getMoveUnits(0).getToProvinceID()))) && (!RTS.isEnabled() || (!RTS.PAUSE && RTS.showReport()) || RTS.PAUSE)) {
                                    this.SHOW_REPORT = true;
                                    this.iPlayerAttack_ShowArmyInProvinceID = this.currentMoveUnits.getMoveUnits(0).getToProvinceID();
                                    CFG.game.getPlayer(CFG.PLAYER_TURNID).setFogOfWar(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), true);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateDrawArmy();
                                    this.diceDefendersCivID = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID();
                                    this.diceAggressorsCivID = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getCivID();
                                    CFG.menuManager.setVisible_InGame_Dices(!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince());
                                    CFG.map.getMapCoordinates().centerToProvinceID(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                                    if (CFG.viewsManager.getActiveViewID() >= 0) {
                                        CFG.viewsManager.disableAllViews();
                                    }
                                    return;
                                }
                                this.turnMoves_MoveCurrentArmy();
                            } else {
                                this.turnMoves_MoveCurrentArmy();
                            }
                        }
                    } else {
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(i--);
                    }
                }
            }
        }
        for (int e = this.eRTO_START; e < CFG.game.getRTO().getRTOSize(); ++e, ++this.eRTO_START) {
            this.turnMoves_UpdatePlayersFogOfWar(CFG.game.getRTO().getRTO(e));
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnitsSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID() == 0 || CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID()) || CFG.game.getMilitaryAccess(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID()) > 0 || CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID() == CFG.game.getRTO().getRTO(e) || CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID()).getPuppetOfCivID() == CFG.game.getRTO().getRTO(e) || CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getPuppetOfCivID() == CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID() || (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getToProvinceID()).getCivID()).getAllianceID())) {
                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e))) {
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e)));
                    }
                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i).getNumOfUnits() > 0) {
                        (this.currentMoveUnits = new MoveUnits_TurnData(CFG.game.getRTO().getRTO(e))).addMoveUnits(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(i), CFG.game.getRTO().getRTO(e));
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(i--);
                        if (!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince()) {
                            for (int k = i + 1; k < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnitsSize(); ++k) {
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getToProvinceID()) {
                                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e))) {
                                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e)));
                                    }
                                    if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k).getNumOfUnits() > 0) {
                                        this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits(k), CFG.game.getRTO().getRTO(e));
                                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(k--);
                                    }
                                }
                            }
                            if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID() > 0) {
                                for (int a = 0; a < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilizationsSize(); ++a) {
                                    if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a) != CFG.game.getRTO().getRTO(e)) {
                                        for (int j = 0; j < CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnitsSize(); ++j) {
                                            if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getToProvinceID()) {
                                                if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getFromProvinceID()).getArmyCivID(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a))) {
                                                    CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getFromProvinceID()).getArmyCivID(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)));
                                                }
                                                if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j).getNumOfUnits() > 0) {
                                                    this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).getMoveUnits(j), CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a));
                                                    CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getAllianceID()).getCivilization(a)).removeMove(j--);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            for (int a = 1; a < CFG.game.getCivsSize(); ++a) {
                                if (a != CFG.game.getRTO().getRTO(e) && (CFG.game.getCiv(a).getPuppetOfCivID() == CFG.game.getRTO().getRTO(e) || a == CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getPuppetOfCivID())) {
                                    for (int j = 0; j < CFG.game.getCiv(a).getMoveUnitsSize(); ++j) {
                                        if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getCiv(a).getMoveUnits(j).getToProvinceID()) {
                                            if (CFG.game.getCiv(a).getMoveUnits(j).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(a).getMoveUnits(j).getFromProvinceID()).getArmyCivID(a)) {
                                                CFG.game.getCiv(a).getMoveUnits(j).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(a).getMoveUnits(j).getFromProvinceID()).getArmyCivID(a));
                                            }
                                            if (CFG.game.getCiv(a).getMoveUnits(j).getNumOfUnits() > 0) {
                                                this.currentMoveUnits.addMoveUnits(CFG.game.getCiv(a).getMoveUnits(j), a);
                                                CFG.game.getCiv(a).removeMove(j--);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        int attackingArmy = 0;
                        for (int o = 0; o < this.currentMoveUnits.getMoveUnitsSize(); ++o) {
                            attackingArmy += this.currentMoveUnits.getMoveUnits(o).getNumOfUnits();
                        }
                        Gdx.app.log("AoC", "attackingArmy: " + attackingArmy);
                        Gdx.app.log("AoC", "MIN_ARMY_TO_ATTACK: 10");
                        Gdx.app.log("AoC", "atWar: " + CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()));
                        if (attackingArmy < 10 && CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() > 0 && CFG.game.getCivsAtWar(CFG.game.getRTO().getRTO(e), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID())) {
                            Gdx.app.log("AoC", "attackingArmy: remove");
                            this.currentMoveUnits = null;
                        } else {
                            for (int o = 0; o < this.currentMoveUnits.getMoveUnitsSize(); ++o) {
                                this.currentMoveUnits.getMoveUnits(o).getMoveUnitsLine().updateMoveTime();
                            }
                            this.rollDices();

                            if (!RTS.SHOW_BATTLE_RESULTS) {
                                this.turnMoves_MoveCurrentArmy();
                            } else if (CFG.SHOW_ALL_MOVES || this.currentMoveUnits.isPlayerMoving() || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getControlledByPlayer()) {
                                if (!CFG.SHOW_ONLY_COMBAT_MOVES) {
                                    CFG.map.getMapCoordinates().centerToProvinceID(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                                    if (CFG.viewsManager.getActiveViewID() >= 0) {
                                        CFG.viewsManager.disableAllViews();
                                    }
                                    return;
                                }
                                if (((CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince() && CFG.game.getSeaProvinceAttack(this.currentMoveUnits.getCivID(0), this.currentMoveUnits.getMoveUnits(0).getToProvinceID())) || (!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince() && this.turnMoves_IsACombatMove(this.currentMoveUnits.getCivID(0), this.currentMoveUnits.getMoveUnits(0).getToProvinceID()))) && (!RTS.isEnabled() || (!RTS.PAUSE && RTS.showReport()) || RTS.PAUSE)) {
                                    this.SHOW_REPORT = true;
                                    this.iPlayerAttack_ShowArmyInProvinceID = this.currentMoveUnits.getMoveUnits(0).getToProvinceID();
                                    CFG.game.getPlayer(CFG.PLAYER_TURNID).setFogOfWar(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), true);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateDrawArmy();
                                    this.diceDefendersCivID = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID();
                                    this.diceAggressorsCivID = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getCivID();
                                    CFG.menuManager.setVisible_InGame_Dices(!CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince());
                                    CFG.map.getMapCoordinates().centerToProvinceID(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                                    if (CFG.viewsManager.getActiveViewID() >= 0) {
                                        CFG.viewsManager.disableAllViews();
                                    }
                                    return;
                                }
                                this.turnMoves_MoveCurrentArmy();
                            } else {
                                this.turnMoves_MoveCurrentArmy();
                            }
                        }
                    } else {
                        CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(i--);
                    }
                } else {
                    CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMove(i--);
                }
            }
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnitsPlunderSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getNumOfUnits() > CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e))) {
                    CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).setNumOfUnits(CFG.game.getProvince(CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getFromProvinceID()).getArmyCivID(CFG.game.getRTO().getRTO(e)));
                }
                if (CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getNumOfUnits() > 0) {
                    DiplomacyManager.plunder(CFG.game.getRTO().getRTO(e), CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getFromProvinceID(), CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMoveUnits_Plunder(i).getNumOfUnits());
                }
                CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removePlunder(i--);
            }
            for (int i = 0; i < CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMigrateSize(); ++i) {
                this.migrateFromTo(CFG.game.getRTO().getRTO(e), CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMigrate(i).getFromProvinceID(), CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).getMigrate(i).getToProvinceID());
                CFG.game.getCiv(CFG.game.getRTO().getRTO(e)).removeMigrate(i--);
            }
        }
        CFG.PROVINCE_BORDER_ANIMATION_TIME.clear();
        this.currentMoveUnits = null;
        this.diceDefenders = 1;
        this.diceAggressors = 1;
        ++Game_Calendar.TURN_ID;
        CFG.gameAction.updateInGame_Date();
        try {
            for (int l = 1; l < CFG.game.getCivsSize(); ++l) {
                CFG.game.getCiv(l).clearMoveUnits();
                CFG.game.getCiv(l).clearMoveUnits_Plunder();
            }
        } catch (final IndexOutOfBoundsException ex) {
        }
        this.startNewTurn();
    }

    protected final void updateRelations() {
        ArrayList<Integer> tempCivs = new ArrayList<Integer>();

        int i;
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                tempCivs.add(i);
            }
        }

        i = 0;

        for (int iSize = tempCivs.size(); i < iSize - 1; ++i) {
            for (int j = i + 1; j < iSize; ++j) {
                if (CFG.game.getCivRelation_OfCivB(i, j) > 30.0F) {
                    CFG.game.setCivRelation_OfCivB(i, j, CFG.game.getCivRelation_OfCivB(i, j) - 0.295F);
                } else if (CFG.game.getCivRelation_OfCivB(i, j) < -20.0F && !CFG.game.getCivsAtWar(i, j)) {
                    CFG.game.setCivRelation_OfCivB(i, j, CFG.game.getCivRelation_OfCivB(i, j) + 0.0145F);
                }

                if (CFG.game.getCivRelation_OfCivB(j, i) > 30.0F) {
                    CFG.game.setCivRelation_OfCivB(j, i, CFG.game.getCivRelation_OfCivB(j, i) - 0.295F);
                } else if (CFG.game.getCivRelation_OfCivB(j, i) < -20.0F && !CFG.game.getCivsAtWar(j, i)) {
                    CFG.game.setCivRelation_OfCivB(j, i, CFG.game.getCivRelation_OfCivB(j, i) + 0.0145F);
                }
            }
        }

        tempCivs.clear();
        tempCivs = null;
    }

    protected final boolean isEmperorInTheGame() {
        try {
            return CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getNumOfProvinces() > 0 && CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()).getPuppetOfCivID() == CFG.holyRomanEmpire_Manager.getHRE().getEmperor();
        } catch (IndexOutOfBoundsException | NullPointerException var2) {
        }

        return true;
    }

    protected final void updateHRE_Elections() {
        try {
            CFG.holyRomanEmpire_Manager.getHRE().setNextElectionsIn(CFG.holyRomanEmpire_Manager.getHRE().getNextElectionsIn() - 1);
            if (CFG.holyRomanEmpire_Manager.getHRE().getNextElectionsIn() > 0 && this.isEmperorInTheGame()) {
                if (CFG.holyRomanEmpire_Manager.getHRE().getNextElectionsIn() == 1) {
                    for (int i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getElectorsSize(); ++i) {
                        if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i))).getControlledByPlayer()) {
                            CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i))).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_HRE_ElectionsInNextTurn(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()));
                        }
                    }

                    this.updateHRE_VotesFor();
                } else if (Game_Calendar.TURN_ID % 6 == 0) {
                    this.updateHRE_VotesFor();
                }
            } else {
                ArrayList<Integer> lNumOfVotes = new ArrayList<Integer>();

                int maxVotes;
                for (maxVotes = 0; maxVotes < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++maxVotes) {
                    lNumOfVotes.add(0);
                }

                for (maxVotes = 0; maxVotes < CFG.holyRomanEmpire_Manager.getHRE().getElectorsSize(); ++maxVotes) {
                    for (int i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++i) {
                        if (CFG.holyRomanEmpire_Manager.getHRE().getPrince(i) == (Integer) CFG.holyRomanEmpire_Manager.getHRE().lVotesFor.get(maxVotes)) {
                            lNumOfVotes.set(i, (Integer) lNumOfVotes.get(i) + 1);
                            break;
                        }
                    }
                }

                maxVotes = 0;

                for (int i = 0; i < lNumOfVotes.size(); ++i) {
                    if ((Integer) lNumOfVotes.get(i) > maxVotes) {
                        maxVotes = (Integer) lNumOfVotes.get(i);
                    }
                }

                ArrayList<Integer> nCivsWithMaxVotes = new ArrayList<Integer>();

                for (int i = 0; i < lNumOfVotes.size(); ++i) {
                    if ((Integer) lNumOfVotes.get(i) == maxVotes) {
                        nCivsWithMaxVotes.add(i);
                    }
                }

                if (nCivsWithMaxVotes.size() > 0) {
                    int newEmperorID = 0;
                    int oldEmperorID = CFG.holyRomanEmpire_Manager.getHRE().getEmperor();
                    boolean wasElector = false;
                    if (nCivsWithMaxVotes.size() == 1) {
                        wasElector = CFG.holyRomanEmpire_Manager.getHRE().getIsElector(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(0)));
                        CFG.holyRomanEmpire_Manager.getHRE().setEmperor(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(0)));
                    } else {
                        boolean emperorVoted = false;

                        int tBest;
                        for (tBest = 0; tBest < nCivsWithMaxVotes.size(); ++tBest) {
                            if (CFG.holyRomanEmpire_Manager.getHRE().getEmperor() == CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(tBest))) {
                                emperorVoted = true;
                                break;
                            }
                        }

                        if (!emperorVoted) {
                            tBest = 0;

                            for (int i = tBest + 1; i < nCivsWithMaxVotes.size(); ++i) {
                                if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(tBest))).countPopulation() < CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(i))).countPopulation()) {
                                    tBest = i;
                                }
                            }

                            wasElector = CFG.holyRomanEmpire_Manager.getHRE().getIsElector(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(tBest)));
                            CFG.holyRomanEmpire_Manager.getHRE().setEmperor(CFG.holyRomanEmpire_Manager.getHRE().getPrince((Integer) nCivsWithMaxVotes.get(tBest)));
                        }
                    }

                    if (CFG.holyRomanEmpire_Manager.getHRE().getEmperor() != oldEmperorID && wasElector) {
                        CFG.holyRomanEmpire_Manager.getHRE().addElector(oldEmperorID);
                    }
                }

                for (int i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++i) {
                    if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getControlledByPlayer()) {
                        CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_HRE_Elections_NewEmperor(CFG.holyRomanEmpire_Manager.getHRE().getEmperor()));
                    }
                }

                CFG.holyRomanEmpire_Manager.getHRE().randomNextElections();
                this.updateHRE_VotesFor();
            }
        } catch (IndexOutOfBoundsException var10) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var10);
            }
        }

    }

    protected final void updateHRE_VotesFor() {
        boolean rebuildVotes = CFG.holyRomanEmpire_Manager.getHRE().lVotesFor == null || CFG.holyRomanEmpire_Manager.getHRE().lVotesFor.size() != CFG.holyRomanEmpire_Manager.getHRE().getElectorsSize();

        int nMaxProvinces;
        for (nMaxProvinces = CFG.holyRomanEmpire_Manager.getHRE().getElectorsSize() - 1; nMaxProvinces >= 0; --nMaxProvinces) {
            if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(nMaxProvinces))).getNumOfProvinces() == 0) {
                CFG.holyRomanEmpire_Manager.getHRE().removeElector(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(nMaxProvinces)));
                CFG.holyRomanEmpire_Manager.getHRE().addStrongestPrinceAsElector();
                rebuildVotes = true;
            }
        }

        if (rebuildVotes) {
            CFG.holyRomanEmpire_Manager.getHRE().buildVotesFor();
        }

        nMaxProvinces = 1;
        int nMaxScore = 1;

        for (int i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++i) {
            if (nMaxProvinces < CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getNumOfProvinces()) {
                nMaxProvinces = CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getNumOfProvinces();
            }

            if (nMaxScore < CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getRankScore()) {
                nMaxScore = CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getRankScore();
            }
        }

        try {
            for (int i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getElectorsSize(); ++i) {
                if (!CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i))).getControlledByPlayer()) {
                    ArrayList<Float> tempScores = new ArrayList<Float>();
                    int tBestID;
                    for (tBestID = 0; tBestID < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++tBestID) {
                        float nScore = 0.0F;
                        if (CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID) == CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i))) {
                            nScore += 16.0F;
                        } else {
                            nScore += 10.0F * CFG.game.getCivRelation_OfCivB(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i)), CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)) / 100.0F;
                        }

                        nScore += CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).civGameData.civPersonality.HRE_VOTE_FOR_PROVINCES * (float) CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).getNumOfProvinces() / (float) nMaxProvinces * (0.4F + 0.6F * CFG.game.getCivRelation_OfCivB(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i)), CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)) / 100.0F);
                        nScore += CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).civGameData.civPersonality.HRE_VOTE_FOR_RANK * (float) CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).getRankScore() / (float) nMaxScore * (0.5F + 0.55F * CFG.game.getCivRelation_OfCivB(CFG.holyRomanEmpire_Manager.getHRE().getPrince(CFG.holyRomanEmpire_Manager.getHRE().getElector(i)), CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)) / 100.0F);
                        if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).getPuppetOfCivID() != CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)) {
                            nScore = -500.0F;
                        }

                        if (CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID)).getNumOfProvinces() <= 0) {
                            nScore = -10000.0F;
                        }

                        tempScores.add(nScore);
                    }

                    if (tempScores.size() > 0) {
                        tBestID = 0;

                        for (int j = tBestID + 1; j < tempScores.size(); ++j) {
                            if ((Float) tempScores.get(tBestID) < (Float) tempScores.get(j)) {
                                tBestID = j;
                            }
                        }

                        CFG.holyRomanEmpire_Manager.getHRE().lVotesFor.set(i, CFG.holyRomanEmpire_Manager.getHRE().getPrince(tBestID));
                    }

                    tempScores.clear();
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException var8) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var8);
            }
        }

    }

    protected final void revoltDeclareIndependence() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).REVOLUTIONARY && Game_Calendar.TURN_ID - CFG.game.getCiv(i).civGameData.iRevolt_SinceTurn >= 10 + CFG.oR.nextInt(10) && (Game_Calendar.TURN_ID - CFG.game.getCiv(i).civGameData.iRevolt_LastTurnLostProvince > 2 || Game_Calendar.TURN_ID - CFG.game.getCiv(i).civGameData.iRevolt_SinceTurn > 49)) {
                this.rebels_DeclareIndependence(i);
            }
        }

    }

    protected final void rebels_DeclareIndependence(int nCivID) {
        ArrayList<Integer> tempPopulation = new ArrayList<Integer>();
        ArrayList<Integer> tempPossibleCivs = new ArrayList<Integer>();

        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getNationalitiesSize(); ++j) {
                if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getCivID(j)).getNumOfProvinces() == 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getCivID(j) != CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getTrueOwnerOfProvince()) {
                    boolean wasAdded = false;

                    for (int o = 0; o < tempPossibleCivs.size(); ++o) {
                        if ((Integer) tempPossibleCivs.get(o) == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getCivID(j)) {
                            wasAdded = true;
                            tempPopulation.set(o, (Integer) tempPopulation.get(o) + CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulationID(j));
                            break;
                        }
                    }

                    if (!wasAdded) {
                        tempPossibleCivs.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getCivID(j));
                        tempPopulation.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulationID(j));
                    }
                }
            }
        }

        ArrayList<String> possibleNewCivsByTags = new ArrayList<String>();
        ArrayList<Integer> possibleNewCivsByTags_Capitals = new ArrayList<Integer>();
        int i;
        int declareCivID;
        if (tempPossibleCivs.size() == 0 || CFG.oR.nextInt(100) < 33) {
            for (declareCivID = 0; declareCivID < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++declareCivID) {
                try {
                    FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + CFG.game.getCiv(nCivID).getProvinceID(declareCivID));
                    String sOwners = file.readString();
                    String[] sRes = sOwners.split(";");

                    for (int j = 0; j < sRes.length; j += 2) {
                        boolean canBeAdded = true;
                        i = CFG.ideologiesManager.getIdeologyID(sRes[j]);
                        if (CFG.ideologiesManager.getIdeology(i).REVOLUTIONARY || CFG.ideologiesManager.getIdeology(i).AVAILABLE_SINCE_AGE_ID <= Game_Calendar.CURRENT_AGEID) {
                            String realTag = CFG.ideologiesManager.getRealTag(sRes[j]);

                            try {
                                if (Game_Calendar.currentYear < Integer.parseInt(sRes[j + 1])) {
                                    canBeAdded = false;
                                }
                            } catch (NumberFormatException var15) {
                                CFG.exceptionStack(var15);
                            } catch (IndexOutOfBoundsException var16) {
                                CFG.exceptionStack(var16);
                            }

                            int k;
                            if (canBeAdded) {
                                for (k = 0; k < CFG.game.getCivsSize(); ++k) {
                                    if (CFG.ideologiesManager.getRealTag(CFG.game.getCiv(k).getCivTag()).equals(realTag)) {
                                        canBeAdded = false;
                                        break;
                                    }
                                }
                            }

                            if (canBeAdded) {
                                for (k = 0; k < possibleNewCivsByTags.size(); ++k) {
                                    if (((String) possibleNewCivsByTags.get(k)).equals(sRes[j])) {
                                        canBeAdded = false;
                                        break;
                                    }
                                }

                                if (canBeAdded) {
                                    possibleNewCivsByTags.add(sRes[j]);
                                    possibleNewCivsByTags_Capitals.add(CFG.game.getCiv(nCivID).getProvinceID(declareCivID));
                                }
                            }
                        }
                    }
                } catch (GdxRuntimeException var20) {
                }
            }
        }

        try {
            if (tempPossibleCivs.size() > 0 || possibleNewCivsByTags.size() > 0) {
                if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) {
                    CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setIsCapital(false);

                    for (declareCivID = 0; declareCivID < CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCitiesSize(); ++declareCivID) {
                        if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(declareCivID).getCityLevel() == CFG.getEditorCityLevel(0)) {
                            CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(declareCivID).setCityLevel(CFG.getEditorCityLevel(1));
                        }
                    }
                }

                declareCivID = -1;
                ArrayList<Integer> joinProvinces = new ArrayList<Integer>();

                int tHighestPop;
                for (tHighestPop = 0; tHighestPop < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++tHighestPop) {
                    joinProvinces.add(CFG.game.getCiv(nCivID).getProvinceID(tHighestPop));
                }

                if (possibleNewCivsByTags.size() > 0) {
                    tHighestPop = CFG.oR.nextInt(possibleNewCivsByTags.size());
                    String newTag = (String) possibleNewCivsByTags.get(tHighestPop);
                    float nTech = CFG.game.getCiv(CFG.game.getProvince((Integer) possibleNewCivsByTags_Capitals.get(tHighestPop)).getTrueOwnerOfProvince()).getTechnologyLevel();
                    CFG.game.createScenarioAddCivilization(newTag, (Integer) possibleNewCivsByTags_Capitals.get(tHighestPop), false, false, true);

                    int nPop;
                    for (nPop = CFG.game.getCivsSize() - 1; nPop >= 0; --nPop) {
                        if (CFG.game.getCiv(nPop).getCivTag().equals(newTag)) {
                            declareCivID = nPop;
                            CFG.game.getCiv(nPop).setTechnologyLevel(nTech * (0.625F + (float) CFG.oR.nextInt(375) / 1000.0F));
                            break;
                        }
                    }

                    if (declareCivID < 0) {
                        declareCivID = (Integer) tempPossibleCivs.get(0);
                    }

                    nPop = 0;

                    try {
                        CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCoreCapitalProvinceID()).setHappiness(Math.max(CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getHappiness(), 0.75F));
                        CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCoreCapitalProvinceID()).setRevolutionaryRisk(0.0F);

                        for (i = CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().getNationalitiesSize() - 1; i >= 0; --i) {
                            int nDiff = (int) Math.ceil((double) ((float) CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().getPopulationID(i) * (0.625F + (float) CFG.oR.nextInt(325) / 1000.0F)));
                            CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().getCivID(i), CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().getPopulationID(i) - nDiff);
                            nPop += nDiff;
                        }
                    } catch (IndexOutOfBoundsException var17) {
                    }

                    CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getPopulationData().setPopulationOfCivID(declareCivID, nPop);
                    this.rebels_DeclareIndependence_Civ(nCivID, declareCivID, joinProvinces, true);
                } else if (tempPossibleCivs.size() <= 1) {
                    declareCivID = (Integer) tempPossibleCivs.get(0);
                    this.rebels_DeclareIndependence_Civ(nCivID, declareCivID, joinProvinces, false);
                } else if (CFG.oR.nextInt(100) < 85) {
                    tHighestPop = 0;

                    for (i = tHighestPop + 1; i < tempPopulation.size(); ++i) {
                        if ((Integer) tempPopulation.get(tHighestPop) < (Integer) tempPopulation.get(i)) {
                            tHighestPop = i;
                        }
                    }

                    declareCivID = (Integer) tempPossibleCivs.get(tHighestPop);
                    this.rebels_DeclareIndependence_Civ(nCivID, declareCivID, joinProvinces, false);
                } else {
                    declareCivID = (Integer) tempPossibleCivs.get(CFG.oR.nextInt(tempPossibleCivs.size()));
                    this.rebels_DeclareIndependence_Civ(nCivID, declareCivID, joinProvinces, false);
                }
            }
        } catch (IndexOutOfBoundsException var18) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var18);
            }
        } catch (NullPointerException var19) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var19);
            }
        }

    }

    protected final void rebels_DeclareIndependence_Civ(int nCivID, int declareCivID, List<Integer> joinProvinces, boolean newCivilization) {
        try {
            boolean updateCapital = true;
            if (CFG.game.getCiv(declareCivID).getCapitalProvinceID() >= 0) {
                for (int i = 0; i < joinProvinces.size(); ++i) {
                    if ((Integer) joinProvinces.get(i) == CFG.game.getCiv(declareCivID).getCapitalProvinceID()) {
                        updateCapital = false;
                        break;
                    }
                }
            }

            if (updateCapital) {
                int newCapital = 0;
                for (int j = 1; j < joinProvinces.size(); ++j) {
                    if (CFG.game.getProvince(joinProvinces.get(j)).getPopulationData().getPopulationOfCivID(declareCivID) > CFG.game.getProvince(joinProvinces.get(newCapital)).getPopulationData().getPopulationOfCivID(declareCivID)) {
                        newCapital = j;
                    }
                }
                CFG.game.getCiv(declareCivID).setCapitalProvinceID(joinProvinces.get(newCapital));
            }

            for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
                if (i != nCivID && CFG.game.getCivsAtWar(i, nCivID)) {
                    CFG.game.whitePeace(nCivID, i);
                    CFG.game.getCiv(i).civGameData.civilization_Diplomacy_GameData.messageBox.addMessage(new Message_DeclarationOfIndependence(declareCivID, CFG.game.getCiv(declareCivID).getCapitalProvinceID()));
                }
            }

            int tempPop;
            if (joinProvinces.size() > 1) {
                for (int i = joinProvinces.size() - 1; i >= 0; --i) {
                    boolean removeNotConnected = (Integer) joinProvinces.get(i) != CFG.game.getCiv(declareCivID).getCapitalProvinceID();
                    if (removeNotConnected) {
                        for (tempPop = 0; tempPop < CFG.game.getProvince((Integer) joinProvinces.get(i)).getNeighboringProvincesSize(); ++tempPop) {
                            if (CFG.game.getProvince(CFG.game.getProvince((Integer) joinProvinces.get(i)).getNeighboringProvinces(tempPop)).getCivID() == CFG.game.getProvince((Integer) joinProvinces.get(i)).getCivID()) {
                                removeNotConnected = false;
                                break;
                            }
                        }
                    }

                    if (removeNotConnected) {
                        CFG.game.getProvince((Integer) joinProvinces.get(i)).setCivID(CFG.game.getProvince((Integer) joinProvinces.get(i)).getTrueOwnerOfProvince(), false);
                        CFG.game.getProvince((Integer) joinProvinces.get(i)).setRevolutionaryRisk(CFG.game.getProvince((Integer) joinProvinces.get(i)).getRevolutionaryRisk() * 0.15F);
                        joinProvinces.remove(i);
                    }
                }
            }

            int tempPopCiv;
            for (int i = 0; i < joinProvinces.size(); ++i) {
                CFG.game.getProvince((Integer) joinProvinces.get(i)).setTrueOwnerOfProvince(declareCivID);
                if (CFG.game.getProvince((Integer) joinProvinces.get(i)).getCivID() != declareCivID) {
                    CFG.game.getProvince((Integer) joinProvinces.get(i)).setCivID(declareCivID, false, true);
                    CFG.game.getProvince((Integer) joinProvinces.get(i)).setRevolutionaryRisk(0.0F);
                    if (CFG.game.getProvince((Integer) joinProvinces.get(i)).getHappiness() < 0.7F) {
                        CFG.game.getProvince((Integer) joinProvinces.get(i)).setHappiness(0.7F + (float) CFG.oR.nextInt(20) / 100.0F);
                    } else {
                        CFG.game.getProvince((Integer) joinProvinces.get(i)).setHappiness(CFG.game.getProvince((Integer) joinProvinces.get(i)).getHappiness() * 1.1775F);
                    }
                }

                if (newCivilization) {
                    int j = 0;

                    try {
                        for (tempPop = CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().getNationalitiesSize() - 1; i >= 0; --tempPop) {
                            tempPopCiv = (int) Math.ceil((double) ((float) CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().getPopulationID(tempPop) * (0.325F + (float) CFG.oR.nextInt(350) / 1000.0F)));
                            CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().setPopulationOfCivID(CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().getCivID(tempPop), CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().getPopulationID(tempPop) - tempPopCiv);
                            j += tempPopCiv;
                        }
                    } catch (IndexOutOfBoundsException var11) {
                    }

                    CFG.game.getProvince((Integer) joinProvinces.get(i)).getPopulationData().setPopulationOfCivID(declareCivID, j);
                    DynamicEventManager_Leader.buildUniqueLeader(declareCivID, nCivID);
                }

                CFG.game.getProvince((Integer) joinProvinces.get(i)).saveProvinceData.iNumOfRevolutions = 0;
            }

            if (CFG.game.getCiv(declareCivID).getCapitalProvinceID() >= 0) {
                CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).setIsCapital(true);
                if (CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getCitiesSize() > 0) {
                    CFG.game.getProvince(CFG.game.getCiv(declareCivID).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
                }
            }

            for (int i = 0; i < CFG.game.getCiv(declareCivID).getNumOfProvinces(); ++i) {
                CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).updateDrawArmy();
                if (!CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getCore().getHaveACore(declareCivID)) {
                    CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getCore().addNewCore(declareCivID, Game_Calendar.TURN_ID);
                }

                for (int j = CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().getNationalitiesSize() - 1; j >= 0; --j) {
                    tempPop = CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(j);
                    tempPopCiv = CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().getCivID(j);
                    int tRand = (int) Math.floor((double) (0.0625F + (float) CFG.oR.nextInt(63) / 100.0F * (float) tempPop));
                    if (tRand > 0) {
                        CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().setPopulationOfCivID(tempPopCiv, tempPop - tRand);
                        CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().setPopulationOfCivID(declareCivID, tempPop - CFG.game.getProvince(CFG.game.getCiv(declareCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(declareCivID) + tRand);
                    }
                }
            }

            if (CFG.FOG_OF_WAR == 2) {
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    for (int l = 0; l < CFG.game.getCiv(declareCivID).getNumOfProvinces(); ++l) {
                        if (CFG.game.getPlayer(i).getMetProvince(CFG.game.getCiv(declareCivID).getProvinceID(l))) {
                            CFG.game.getPlayer(i).setMetCivilization(declareCivID, true);
                            break;
                        }
                    }
                }
            }

            CFG.game.getCiv(declareCivID).buildNumOfUnits();
            CFG.game.getCiv(declareCivID).setMoney(Math.max(50L, CFG.game.getCiv(declareCivID).getMoney()));
        } catch (IndexOutOfBoundsException var12) {
        }

    }

    protected final boolean canAnyCivUprise(int nProvinceID) {
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCore().getCivsSize(); ++i) {
            if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCore().getCivID(i)).getNumOfProvinces() == 0 && CFG.game.getProvince(nProvinceID).getCore().getCivID(i) != CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince()) {
                return true;
            }
        }

        return false;
    }

    protected final void startUprising() {
        Gdx.app.log("AoC", "GA -> startUprising: BEGIN");
        ArrayList<Integer> tempPossibleUprising = new ArrayList<Integer>();
        ArrayList<Integer> tempPossibleUprising_CheckSuggest = new ArrayList<Integer>();
        ArrayList<Integer> overMin = new ArrayList<Integer>();
        int numOfTrueOwnerProvinces;

        for (int i = 1 + Game_Calendar.TURN_ID % 3; i < CFG.game.getCivsSize(); i += 3) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).CAN_BECOME_CIVILIZED < 0 && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).REVOLUTIONARY) {
                tempPossibleUprising.clear();
                tempPossibleUprising_CheckSuggest.clear();
                overMin.clear();
                numOfTrueOwnerProvinces = 0;

                for (int j = 0; j < CFG.game.getCiv(i).getNumOfProvinces(); ++j) {
                    if (CFG.game.getProvince(CFG.game.getCiv(i).getProvinceID(j)).getCivID() == CFG.game.getProvince(CFG.game.getCiv(i).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        ++numOfTrueOwnerProvinces;
                        if (CFG.game.getProvince(CFG.game.getCiv(i).getProvinceID(j)).getRevolutionaryRisk() > 0.16F && !CFG.game.getProvince(CFG.game.getCiv(i).getProvinceID(j)).getIsCapital()) {
                            if (this.getModifiedRevolutionsRisk(CFG.game.getCiv(i).getProvinceID(j)) > 0.64F * (0.4F + 0.6F * CFG.game.getCiv(i).getStability()) && CFG.oR.nextInt((int) (this.getModifiedRevolutionsRisk(CFG.game.getCiv(i).getProvinceID(j)) * 100.0F)) > 40) {
                                if (this.canAnyCivUprise(CFG.game.getCiv(i).getProvinceID(j))) {
                                    tempPossibleUprising.add(CFG.game.getCiv(i).getProvinceID(j));
                                } else {
                                    tempPossibleUprising_CheckSuggest.add(CFG.game.getCiv(i).getProvinceID(j));
                                }
                            }

                            overMin.add(CFG.game.getCiv(i).getProvinceID(j));
                        }
                    }
                }

                if (tempPossibleUprising.size() == 0 && tempPossibleUprising_CheckSuggest.size() > 0) {
                    for (int j = tempPossibleUprising_CheckSuggest.size() - 1; j >= 0; --j) {
                        try {
                            FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + tempPossibleUprising_CheckSuggest.get(j));
                            String sOwners = file.readString();
                            String[] sRes = sOwners.split(";");

                            for (int k = 0; k < sRes.length; k += 2) {
                                boolean canBeAdded = true;
                                int tempIdeologyID = CFG.ideologiesManager.getIdeologyID(sRes[k]);
                                if (!CFG.ideologiesManager.getIdeology(tempIdeologyID).REVOLUTIONARY && CFG.ideologiesManager.getIdeology(tempIdeologyID).AVAILABLE_SINCE_AGE_ID <= Game_Calendar.CURRENT_AGEID) {
                                    String realTag = CFG.ideologiesManager.getRealTag(sRes[k]);

                                    for (int o = 0; o < CFG.game.getCivsSize(); ++o) {
                                        if (CFG.ideologiesManager.getRealTag(CFG.game.getCiv(o).getCivTag()).equals(realTag)) {
                                            canBeAdded = false;
                                            break;
                                        }
                                    }

                                    if (canBeAdded) {
                                        tempPossibleUprising.add((Integer) tempPossibleUprising_CheckSuggest.get(j));
                                        break;
                                    }
                                }
                            }
                        } catch (GdxRuntimeException var15) {
                        }
                    }
                }

                if (tempPossibleUprising.size() > 0 || overMin.size() > 0) {
                    this.spawnRevolution(i, tempPossibleUprising, overMin, numOfTrueOwnerProvinces);
                }
            }
        }

        tempPossibleUprising.clear();
        tempPossibleUprising = null;
        tempPossibleUprising_CheckSuggest.clear();
        tempPossibleUprising_CheckSuggest = null;
        overMin.clear();
        overMin = null;
    }

    protected final float getModifiedRevolutionsRisk(int nProvinceID) {
        return CFG.game.getProvince(nProvinceID).getRevolutionaryRisk() * (1.0F + (float) CFG.game.getProvince(nProvinceID).getCore().getCivsSize() / 10.0F) - (float) CFG.game.getProvinceArmy(nProvinceID) / (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * 50.0F;
    }

    protected final void spawnRevolution(int nCivID, List nProvinces, List<Integer> nOverMin, int numOfTrueOwnerProvinces) {
        Gdx.app.log("AoC", "GA -> spawnRevolution: BEGIN: " + CFG.game.getCiv(nCivID).getCivName());
        ArrayList<Integer> tempSorted = new ArrayList<Integer>();

        while (nProvinces.size() > 0) {
            int tBest = 0;

            for (int revoltCivID = nProvinces.size() - 1; revoltCivID > 0; --revoltCivID) {
                if (CFG.game.getProvince((Integer) nProvinces.get(revoltCivID)).getRevolutionaryRisk() > CFG.game.getProvince((Integer) nProvinces.get(tBest)).getRevolutionaryRisk()) {
                    tBest = revoltCivID;
                }
            }

            tempSorted.add((Integer) nProvinces.get(tBest));
            nProvinces.remove(tBest);
        }

        Gdx.app.log("AoC", "GA -> spawnRevolution: 000");
        int numOfRevoltProvincesMax;
        int theBestProvinceID;
        int theBestConnections;
        if ((float) numOfTrueOwnerProvinces * 0.63F < (float) nOverMin.size() && CFG.oR.nextInt(1000) < 47) {
            Gdx.app.log("AoC", "GA -> spawnRevolution: 111");
            ArrayList<Integer> possibleIdeologies = new ArrayList<Integer>();
            ArrayList<Integer> possibleCivsExisting = new ArrayList<Integer>();

            boolean wasAdded;
            for (int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); ++i) {
                if (CFG.ideologiesManager.getIdeology(i).CAN_BECOME_CIVILIZED < 0 && !CFG.ideologiesManager.getIdeology(i).REVOLUTIONARY && Game_Calendar.CURRENT_AGEID >= CFG.ideologiesManager.getIdeology(i).AVAILABLE_SINCE_AGE_ID) {
                    String tempTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(i).getExtraTag();
                    wasAdded = CFG.game.getCiv(nCivID).getCivTag().equals(tempTag);
                    if (!wasAdded) {
                        for (numOfRevoltProvincesMax = 0; numOfRevoltProvincesMax < CFG.game.getCivsSize(); ++numOfRevoltProvincesMax) {
                            if (CFG.game.getCiv(numOfRevoltProvincesMax).getCivTag().equals(tempTag)) {
                                if (CFG.game.getCiv(numOfRevoltProvincesMax).getNumOfProvinces() > 0) {
                                    wasAdded = true;
                                } else {
                                    possibleCivsExisting.add(numOfRevoltProvincesMax);
                                }
                                break;
                            }
                        }

                        if (!wasAdded) {
                            possibleIdeologies.add(i);
                        }
                    }
                }
            }

            Gdx.app.log("AoC", "GA -> spawnRevolution: 222");
            if (possibleIdeologies.size() > 0 || possibleCivsExisting.size() > 0) {
                Gdx.app.log("AoC", "GA -> spawnRevolution: 333");
                ArrayList<Integer> allProvincesSorted = new ArrayList<Integer>();

                for (theBestProvinceID = tempSorted.size() - 1; theBestProvinceID >= 0; --theBestProvinceID) {
                    allProvincesSorted.add((Integer) tempSorted.get(theBestProvinceID));
                }

                for (theBestProvinceID = nOverMin.size() - 1; theBestProvinceID >= 0; --theBestProvinceID) {
                    wasAdded = false;

                    for (numOfRevoltProvincesMax = 0; numOfRevoltProvincesMax < allProvincesSorted.size(); ++numOfRevoltProvincesMax) {
                        if (allProvincesSorted.get(numOfRevoltProvincesMax) == nOverMin.get(theBestProvinceID)) {
                            wasAdded = true;
                            break;
                        }
                    }

                    if (!wasAdded) {
                        allProvincesSorted.add((Integer) nOverMin.get(theBestProvinceID));
                    }
                }

                Gdx.app.log("AoC", "GA -> spawnRevolution: 444");
                ArrayList<Integer> revoltProvinces = new ArrayList<Integer>();
                theBestConnections = 0;

                for (numOfRevoltProvincesMax = 0; numOfRevoltProvincesMax < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++numOfRevoltProvincesMax) {
                    if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(numOfRevoltProvincesMax)).getCivID() == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(numOfRevoltProvincesMax)).getTrueOwnerOfProvince()) {
                        ++theBestConnections;
                    }
                }

                Gdx.app.log("AoC", "GA -> spawnRevolution: 555");
                numOfRevoltProvincesMax = (int) ((float) theBestConnections * (0.3F + (float) CFG.oR.nextInt(25) / 100.0F));
                if (numOfRevoltProvincesMax > 0 && allProvincesSorted.size() > 0) {
                    Gdx.app.log("AoC", "GA -> spawnRevolution: 666");
                    int a = (Integer) allProvincesSorted.get(CFG.oR.nextInt(allProvincesSorted.size()));
                    revoltProvinces.add(a);
                    Gdx.app.log("AoC", "GA -> spawnRevolution: 777");
                    int nRebelsCivID;
                    if (numOfRevoltProvincesMax > revoltProvinces.size()) {
                        Gdx.app.log("AoC", "GA -> spawnRevolution: 888");

                        for (int i = 0; i < CFG.game.getProvince(a).getNeighboringProvincesSize(); ++i) {
                            if (CFG.game.getProvince(CFG.game.getProvince(a).getNeighboringProvinces(i)).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getProvince(a).getNeighboringProvinces(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvince(a).getNeighboringProvinces(i)).getTrueOwnerOfProvince() && !CFG.game.getProvince(CFG.game.getProvince(a).getNeighboringProvinces(i)).getIsCapital() && CFG.game.getProvince(CFG.game.getProvince(a).getNeighboringProvinces(i)).getRevolutionaryRisk() > 0.16F) {
                                revoltProvinces.add(CFG.game.getProvince(a).getNeighboringProvinces(i));
                                if (numOfRevoltProvincesMax <= revoltProvinces.size()) {
                                    break;
                                }
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 999");
                        if (numOfRevoltProvincesMax > revoltProvinces.size()) {
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 10");
                            for (int i2 = allProvincesSorted.size() - 1; i2 >= 0; --i2) {
                                for (int j3 = revoltProvinces.size() - 1; j3 >= 0; --j3) {
                                    if (Objects.equals(allProvincesSorted.get(i2), revoltProvinces.get(j3))) {
                                        allProvincesSorted.remove(i2);
                                        break;
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 11");
                            while (numOfRevoltProvincesMax > revoltProvinces.size() && !allProvincesSorted.isEmpty()) {
                                int counter = 0;
                                int nRand = 0;
                                while (counter++ < 8) {
                                    nRand = CFG.oR.nextInt(allProvincesSorted.size());
                                    boolean endRand = false;
                                    for (int o = revoltProvinces.size() - 1; o >= 0; --o) {
                                        for (int p = 0; p < CFG.game.getProvince(allProvincesSorted.get(nRand)).getNeighboringProvincesSize(); ++p) {
                                            if (CFG.game.getProvince(allProvincesSorted.get(nRand)).getNeighboringProvinces(p) == revoltProvinces.get(o)) {
                                                endRand = true;
                                                o = -1;
                                                break;
                                            }
                                        }
                                    }
                                    if (endRand) {
                                        break;
                                    }
                                }
                                revoltProvinces.add(allProvincesSorted.get(nRand));
                                allProvincesSorted.remove(nRand);
                            }
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 12");
                        }
                    }

                    Gdx.app.log("AoC", "GA -> spawnRevolution: 13");
                    boolean spawnedCivWithDifferentGovernment = false;
                    if (revoltProvinces.size() > 0) {
                        Gdx.app.log("AoC", "GA -> spawnRevolution: 14");
                        String nRevTag = "";
                        final List<Province_Army> tempArmies = new ArrayList<Province_Army>();
                        final List<Integer> tempArmiesProvinces = new ArrayList<Integer>();
                        if (possibleCivsExisting.size() > 0 && (CFG.oR.nextInt(10) < 5 || possibleIdeologies.size() == 0)) {
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 15");
                            final int randCiv = CFG.oR.nextInt(possibleCivsExisting.size());
                            nRevTag = CFG.game.getCiv(possibleCivsExisting.get(randCiv)).getCivTag();
                            CFG.game.getCiv(possibleCivsExisting.get(randCiv)).setCapitalProvinceID(revoltProvinces.get(0));
                            if (CFG.game.getProvince(revoltProvinces.get(0)).getArmy(0) > 0) {
                                tempArmies.add(new Province_Army(nCivID, CFG.game.getProvince(revoltProvinces.get(0)).getArmy(0), revoltProvinces.get(0)));
                                tempArmiesProvinces.add(revoltProvinces.get(0));
                            }
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 16");
                        } else if (possibleIdeologies.size() > 0) {
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 17");
                            nRevTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(possibleIdeologies.get(CFG.oR.nextInt(possibleIdeologies.size()))).getExtraTag();
                            if (CFG.game.getProvince(revoltProvinces.get(0)).getArmy(0) > 0) {
                                tempArmies.add(new Province_Army(nCivID, CFG.game.getProvince(revoltProvinces.get(0)).getArmy(0), revoltProvinces.get(0)));
                                tempArmiesProvinces.add(revoltProvinces.get(0));
                            }
                            CFG.game.createScenarioAddCivilization(nRevTag, revoltProvinces.get(0), false, false, true);
                            spawnedCivWithDifferentGovernment = true;
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 18");
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 19");
                        nRebelsCivID = -1;

                        for (int i = CFG.game.getCivsSize() - 1; i > 0; --i) {
                            if (CFG.game.getCiv(i).getCivTag().equals(nRevTag)) {
                                nRebelsCivID = i;
                                break;
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 20");
                        if (nRebelsCivID > 0) {
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 21");

                            for (int i = 0; i < revoltProvinces.size(); ++i) {
                                if (CFG.game.getProvince((Integer) revoltProvinces.get(i)).getCivID() != nRebelsCivID) {
                                    if (CFG.game.getProvince((Integer) revoltProvinces.get(i)).getArmy(0) > 0) {
                                        tempArmies.add(new Province_Army(nCivID, CFG.game.getProvince((Integer) revoltProvinces.get(i)).getArmy(0), (Integer) revoltProvinces.get(i)));
                                        tempArmiesProvinces.add((Integer) revoltProvinces.get(i));
                                    }

                                    if (spawnedCivWithDifferentGovernment) {
                                        CFG.game.getProvince((Integer) revoltProvinces.get(i)).setTrueOwnerOfProvince(nRebelsCivID);
                                        CFG.game.getProvince((Integer) revoltProvinces.get(i)).setCivID(nRebelsCivID, true);
                                    } else {
                                        CFG.game.getProvince((Integer) revoltProvinces.get(i)).setCivID(nRebelsCivID, true);
                                        CFG.game.getProvince((Integer) revoltProvinces.get(i)).setTrueOwnerOfProvince(nRebelsCivID);
                                    }

                                    this.updateProvinceAfterRevolution((Integer) revoltProvinces.get(i));
                                    this.spawnRevolutionaryArmy((Integer) revoltProvinces.get(i), nCivID, nRebelsCivID);
                                }
                            }

                            Gdx.app.log("AoC", "GA -> spawnRevolution: 22");
                            for (int i3 = 0; i3 < tempArmies.size(); ++i3) {
                                CFG.game.getProvince(tempArmiesProvinces.get(i3)).updateArmy(tempArmies.get(i3).getCivID(), tempArmies.get(i3).getArmy());
                                CFG.game.getCiv(tempArmies.get(i3).getCivID()).newMove(tempArmiesProvinces.get(i3), tempArmiesProvinces.get(i3), tempArmies.get(i3).getArmy(), true);
                                for (a = CFG.game.getProvince(tempArmiesProvinces.get(i3)).getCivsSize() - 1; a >= 0; --a) {
                                    if (CFG.game.getProvince(tempArmiesProvinces.get(i3)).getCivID(a) != nCivID && CFG.game.getProvince(tempArmiesProvinces.get(i3)).getCivID(a) != nRebelsCivID) {
                                        this.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(tempArmiesProvinces.get(i3)).getCivID(a), tempArmiesProvinces.get(i3));
                                    }
                                }
                            }

                            CFG.game.getCiv(nCivID).setNumOfUnits(0);
                            CFG.game.getCiv(nCivID).buildNumOfUnits();
                            CFG.game.getCiv(nRebelsCivID).setNumOfUnits(0);
                            CFG.game.getCiv(nRebelsCivID).buildNumOfUnits();
                            Color nColor = CFG.getRandomColor();
                            CFG.game.getCiv(nRebelsCivID).setR((int) (nColor.r * 255.0F));
                            CFG.game.getCiv(nRebelsCivID).setG((int) (nColor.g * 255.0F));
                            CFG.game.getCiv(nRebelsCivID).setB((int) (nColor.b * 255.0F));
                            CFG.game.getCiv(nRebelsCivID).setMoney(Math.max(CFG.game.getCiv(nRebelsCivID).getMoney(), 50L));
                            CFG.game.getCiv(nRebelsCivID).setTechnologyLevel(CFG.game.getCiv(nCivID).getTechnologyLevel() * (0.845F + (float) CFG.oR.nextInt(125) / 1000.0F));
                            if (CFG.game.getCiv(nCivID).getCivID() != CFG.game.getCiv(nCivID).getPuppetOfCivID()) {
                                CFG.game.getCiv(nRebelsCivID).setPuppetOfCivID(CFG.game.getCiv(nCivID).getPuppetOfCivID());
                            }

                            try {
                                for (int p2 = 0; p2 < CFG.game.getPlayersSize(); ++p2) {
                                    if (!CFG.game.getPlayer(p2).getMetCivilization(nRebelsCivID)) {
                                        for (int o2 = 0; o2 < CFG.game.getCiv(nRebelsCivID).getNumOfProvinces(); ++o2) {
                                            if (CFG.game.getPlayer(p2).getMetProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(o2))) {
                                                CFG.game.getPlayer(p2).setMetCivilization(nRebelsCivID, true);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (IndexOutOfBoundsException var23) {
                                CFG.exceptionStack(var23);
                            }

                            Gdx.app.log("AoC", "GA -> spawnRevolution: 23");
                            CFG.game.getCiv(nCivID).civGameData.civilization_Diplomacy_GameData.messageBox.addMessage(new Message_Revolt(nRebelsCivID, (Integer) revoltProvinces.get(0)));
                            CFG.game.declareWar(nRebelsCivID, nCivID, true);
                            ++CFG.game.getCiv(nCivID).civGameData.iNumOfRevolutions;
                            Gdx.app.log("AoC", "GA -> spawnRevolution: 24");
                        }

                        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setRevolutionaryRisk(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getRevolutionaryRisk() * (0.7F + (float) CFG.oR.nextInt(300) / 1000.0F));
                        }

                        for (int i = 0; i < CFG.game.getCiv(nRebelsCivID).getNumOfProvinces(); ++i) {
                            CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i)).setRevolutionaryRisk(0.0F);
                            CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i)).setHappiness(Math.max(0.66F + (float) CFG.oR.nextInt(24) / 100.0F, CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i)).getHappiness()));
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 25");
                        if (spawnedCivWithDifferentGovernment) {
                            for (int i3 = 0; i3 < CFG.game.getCiv(nRebelsCivID).getNumOfProvinces(); ++i3) {
                                if (!CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i3)).getCore().getHaveACore(nRebelsCivID)) {
                                    CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i3)).getCore().addNewCore(nRebelsCivID, Game_Calendar.TURN_ID);
                                }
                                final int popOfNativeCiv = CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i3)).getPopulationData().getPopulationOfCivID(nCivID);
                                if (popOfNativeCiv > 0) {
                                    final float randPerc = CFG.oR.nextInt(625) / 1000.0f;
                                    CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i3)).getPopulationData().setPopulationOfCivID(nCivID, (int) (popOfNativeCiv * randPerc));
                                    CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i3)).getPopulationData().setPopulationOfCivID(nRebelsCivID, (int) (popOfNativeCiv * (1.0f - randPerc)));
                                }
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 26");

                        for (int i = 0; i < CFG.game.getCiv(nRebelsCivID).getNumOfProvinces(); ++i) {
                            CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(i)).updateDrawArmy();
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 27");

                        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCivID() == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getTrueOwnerOfProvince()) {
                                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setRevolutionaryRisk(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getRevolutionaryRisk() * 0.645F);
                                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setHappiness((CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getHappiness() + 0.08F) * 1.124F);
                                if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getHappiness() < 0.32F) {
                                    CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setHappiness(0.32F + CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getHappiness() * 0.1F);
                                }
                            } else {
                                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setRevolutionaryRisk(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getRevolutionaryRisk() * 0.4638F);
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 28");
                        if (CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID() >= 0) {
                            CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).setIsCapital(true);
                            boolean updateCapitalLevel = true;

                            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCitiesSize(); ++j) {
                                if (CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCity(j).getCityLevel() == CFG.getEditorCityLevel(0)) {
                                    updateCapitalLevel = false;
                                    break;
                                }
                            }

                            if (updateCapitalLevel && CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCitiesSize() > 0) {
                                CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 29");
                        if (CFG.FOG_OF_WAR == 2) {
                            for (int i3 = 0; i3 < CFG.game.getPlayersSize(); ++i3) {
                                for (int j4 = 0; j4 < CFG.game.getCiv(nRebelsCivID).getNumOfProvinces(); ++j4) {
                                    if (CFG.game.getPlayer(i3).getMetProvince(CFG.game.getCiv(nRebelsCivID).getProvinceID(j4))) {
                                        CFG.game.getPlayer(i3).setMetCivilization(nRebelsCivID, true);
                                        break;
                                    }
                                }
                            }
                        }

                        Gdx.app.log("AoC", "GA -> spawnRevolution: 30");
                        return;
                    }
                }
            }
        }

        Gdx.app.log("AoC", "GA -> spawnRevolution: SECOND 0000");
        String nRevTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(CFG.ideologiesManager.REBELS_ID).getExtraTag();
        int revoltCivID = -1;

        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getIdeologyID() == CFG.ideologiesManager.REBELS_ID && this.getSpawnRebels_CivRebelsTag(i).equals(nRevTag)) {
                if (CFG.game.getCiv(i).getNumOfProvinces() == 0) {
                    revoltCivID = i;
                } else {
                    if (CFG.game.getCiv(i).getNumOfProvinces() > 1 && CFG.oR.nextInt(1500) % 100 < Math.min(50, 20 + 10 * (Game_Calendar.TURN_ID - CFG.game.getCiv(i).civGameData.iRevolt_LastTurnLostProvince)) && CFG.game.getCiv(i).getNumOfProvinces() < CFG.game.getCiv(nCivID).getNumOfProvinces() - 1) {
                        theBestProvinceID = -1;
                        theBestConnections = 0;

                        for (int k = 0; k < tempSorted.size(); ++k) {
                            int currentConnections = 0;
                            int ownProvincesConnection = 0;
                            for (int k2 = 0; k2 < CFG.game.getProvince(tempSorted.get(k)).getNeighboringProvincesSize(); ++k2) {
                                if (CFG.game.getProvince(CFG.game.getProvince(tempSorted.get(k)).getNeighboringProvinces(k2)).getCivID() == i) {
                                    ++currentConnections;
                                } else if (CFG.game.getProvince(CFG.game.getProvince(tempSorted.get(k)).getNeighboringProvinces(k2)).getCivID() == CFG.game.getProvince(tempSorted.get(k)).getCivID()) {
                                    ++ownProvincesConnection;
                                }
                            }
                            if (currentConnections > 0) {
                                if (ownProvincesConnection == 0) {
                                    currentConnections += 2;
                                } else if (ownProvincesConnection == 1) {
                                    ++currentConnections;
                                }
                            }
                            if (currentConnections > theBestConnections || (currentConnections > 0 && currentConnections == theBestConnections && CFG.oR.nextInt(150) % 2 == 1)) {
                                theBestProvinceID = tempSorted.get(k);
                                theBestConnections = currentConnections;
                            }
                        }

                        if (theBestProvinceID < 0) {
                            for (int k = 0; k < nOverMin.size(); ++k) {
                                int currentConnections = 0;
                                int ownProvincesConnection = 0;
                                for (int k2 = 0; k2 < CFG.game.getProvince(nOverMin.get(k)).getNeighboringProvincesSize(); ++k2) {
                                    if (CFG.game.getProvince(CFG.game.getProvince(nOverMin.get(k)).getNeighboringProvinces(k2)).getCivID() == i) {
                                        ++currentConnections;
                                    } else if (CFG.game.getProvince(CFG.game.getProvince(nOverMin.get(k)).getNeighboringProvinces(k2)).getCivID() == CFG.game.getProvince(nOverMin.get(k)).getCivID()) {
                                        ++ownProvincesConnection;
                                    }
                                }
                                if (currentConnections > 0) {
                                    if (ownProvincesConnection == 0) {
                                        currentConnections += 2;
                                    } else if (ownProvincesConnection == 1) {
                                        ++currentConnections;
                                    }
                                }
                                if (currentConnections > theBestConnections || (currentConnections > 0 && currentConnections == theBestConnections && CFG.oR.nextInt(150) % 2 == 1)) {
                                    theBestProvinceID = nOverMin.get(k);
                                    theBestConnections = currentConnections;
                                }
                            }
                        }

                        if (theBestProvinceID >= 0) {
                            for (numOfRevoltProvincesMax = tempSorted.size() - 1; numOfRevoltProvincesMax >= 0; --numOfRevoltProvincesMax) {
                                if ((Integer) tempSorted.get(numOfRevoltProvincesMax) == theBestProvinceID) {
                                    tempSorted.remove(numOfRevoltProvincesMax);
                                    break;
                                }
                            }

                            numOfRevoltProvincesMax = CFG.game.getProvince(theBestProvinceID).getArmy(0);
                            CFG.game.getProvince(theBestProvinceID).setCivID(i, false, true);
                            this.updateProvinceAfterRevolution(theBestProvinceID);
                            this.spawnRevolutionaryArmy(theBestProvinceID, nCivID, i);
                            if (numOfRevoltProvincesMax > 0) {
                                CFG.game.getProvince(theBestProvinceID).updateArmy(nCivID, numOfRevoltProvincesMax);
                                CFG.game.getCiv(nCivID).newMove(theBestProvinceID, theBestProvinceID, numOfRevoltProvincesMax, true);

                                for (int a = CFG.game.getProvince(theBestProvinceID).getCivsSize() - 1; a >= 0; --a) {
                                    if (CFG.game.getProvince(theBestProvinceID).getCivID(a) != nCivID && CFG.game.getProvince(theBestProvinceID).getCivID(a) != i) {
                                        this.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(theBestProvinceID).getCivID(a), theBestProvinceID);
                                    }
                                }
                            }

                            CFG.game.getCiv(nCivID).civGameData.civilization_Diplomacy_GameData.messageBox.addMessage(new Message_Revolt(i, theBestProvinceID));
                        }
                    }

                    if (tempSorted.size() == 0) {
                        return;
                    }
                }
            }
        }

        if (tempSorted.size() != 0) {
            if (revoltCivID <= 0) {
                for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getIdeologyID() == CFG.ideologiesManager.REBELS_ID && CFG.game.getCiv(j).getNumOfProvinces() == 0) {
                        revoltCivID = j;
                    }
                }
            }

            try {
                this.spawnRevolutionInProvinceID(nCivID, revoltCivID, (Integer) tempSorted.get(0), tempSorted, nOverMin);
            } catch (IndexOutOfBoundsException | StackOverflowError var21) {
                CFG.exceptionStack(var21);
            }

        }
    }

    protected final String getSpawnRebels_CivRebelsTag(int nCivID) {
        return CFG.game.getCiv(nCivID).getCivTag().lastIndexOf(95) > 0 ? CFG.game.getCiv(nCivID).getCivTag().substring(0, CFG.game.getCiv(nCivID).getCivTag().lastIndexOf(95) + 2) : CFG.game.getCiv(nCivID).getCivTag();
    }

    protected final int getSpawnRebels_CivRebelsTag_GetID(int nCivID) {
        if (CFG.game.getCiv(nCivID).getCivTag().lastIndexOf(95) > 0) {
            try {
                return Integer.parseInt(CFG.game.getCiv(nCivID).getCivTag().substring(CFG.game.getCiv(nCivID).getCivTag().lastIndexOf(95) + 2, CFG.game.getCiv(nCivID).getCivTag().length()));
            } catch (NumberFormatException var3) {
                CFG.exceptionStack(var3);
            }
        }

        return 0;
    }

    protected final void updateMetCivilization(int nProvinceID) {
        try {
            if (CFG.FOG_OF_WAR == 2) {
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    if (CFG.game.getPlayer(i).getMetProvince(nProvinceID)) {
                        CFG.game.getPlayer(i).setMetCivilization(CFG.game.getProvince(nProvinceID).getCivID(), true);
                    }
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException var3) {
        }

    }

    protected final void spawnRevolutionInProvinceID(int nCivID, int nRebelsCivID, int nProvinceID, List nProvinces, List nOverMin) {
        String nRevTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(CFG.ideologiesManager.REBELS_ID).getExtraTag();
        int nLastID = -1;

        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (this.getSpawnRebels_CivRebelsTag(i).equals(nRevTag)) {
                int tID = this.getSpawnRebels_CivRebelsTag_GetID(i);
                if (tID >= nLastID) {
                    nLastID = tID + 1;
                }
            }
        }

        if (nLastID >= 0) {
            nRevTag = CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(CFG.ideologiesManager.REBELS_ID).getExtraTag() + nLastID;
        }

        ArrayList<Province_Army> tempArmies = new ArrayList<Province_Army>();
        ArrayList<Integer> tempArmiesProvinces = new ArrayList<Integer>();
        if (CFG.game.getProvince(nProvinceID).getArmy(0) > 0) {
            tempArmies.add(new Province_Army(nCivID, CFG.game.getProvince(nProvinceID).getArmy(0), nProvinceID));
            tempArmiesProvinces.add(nProvinceID);
            CFG.game.getProvince(nProvinceID).updateArmy(0);
        }

        int i;
        if (nRebelsCivID <= 0) {
            CFG.game.createScenarioAddCivilization(nRevTag, nProvinceID, false, false, true);

            for (i = CFG.game.getCivsSize() - 1; i > 0; --i) {
                if (CFG.game.getCiv(i).getIdeologyID() == CFG.ideologiesManager.REBELS_ID && CFG.game.getCiv(i).getCivTag().equals(nRevTag)) {
                    nRebelsCivID = i;
                    break;
                }
            }

            this.spawnRevolution_UpdateCivData(nCivID, nRebelsCivID, nRevTag);
        } else if (!CFG.game.getCiv(nRebelsCivID).getCivTag().equals(nRevTag)) {
            this.spawnRevolution_UpdateCivData(nCivID, nRebelsCivID, nRevTag);
        } else {
            this.spawnRevolution_UpdateCivData(nCivID, nRebelsCivID, nRevTag);
        }

        CFG.game.getCiv(nRebelsCivID).civGameData.iRevolt_SinceTurn = Game_Calendar.TURN_ID;
        CFG.game.getCiv(nRebelsCivID).civGameData.iRevolt_LastTurnLostProvince = Game_Calendar.TURN_ID;
        CFG.game.getCiv(nRebelsCivID).setCapitalProvinceID(nProvinceID);
        CFG.game.getProvince(nProvinceID).setIsCapital(true);
        if (CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCitiesSize() > 0) {
            for (i = 0; i < CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCitiesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCity(i).getCityLevel() == CFG.getEditorCityLevel(0)) {
                    CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
                }
            }

            CFG.game.getProvince(CFG.game.getCiv(nRebelsCivID).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
        }

        CFG.game.getProvince(nProvinceID).setCivID(nRebelsCivID, true);
        CFG.game.getProvince(nProvinceID).setTrueOwnerOfProvince(nCivID);
        this.updateProvinceAfterRevolution(nProvinceID);
        CFG.game.getProvince(nProvinceID).updateArmy(nRebelsCivID, 0);
        CFG.game.getCiv(nRebelsCivID).setNumOfUnits(0);
        this.spawnRevolutionaryArmy(nProvinceID, nCivID, nRebelsCivID);
        CFG.game.getCiv(nCivID).civGameData.civilization_Diplomacy_GameData.messageBox.addMessage(new Message_Revolt(nRebelsCivID, nProvinceID));
        i = 0;

        int revelsMaxPercOfProvinces;
        for (revelsMaxPercOfProvinces = 0; revelsMaxPercOfProvinces < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++revelsMaxPercOfProvinces) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(revelsMaxPercOfProvinces)).getCivID() == CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(revelsMaxPercOfProvinces)).getTrueOwnerOfProvince()) {
                ++i;
            }
        }

        revelsMaxPercOfProvinces = (int) Math.ceil((double) ((float) i * (0.12F + (float) CFG.oR.nextInt(15) / 100.0F)));
        ArrayList<Integer> tempRevCivsIDs = new ArrayList<Integer>();

        for (i = 0; i < CFG.game.getProvince(nProvinceID).getCore().getCivsSize(); ++i) {
            if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCore().getCivID(i)).getNumOfProvinces() == 0) {
                tempRevCivsIDs.add(CFG.game.getProvince(nProvinceID).getCore().getCivID(i));
            }
        }

        ArrayList<Integer> joinProvinces = new ArrayList<Integer>();

        for (i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (!CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getIsCapital() && CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID && this.getModifiedRevolutionsRisk(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)) > 0.16F) {
                joinProvinces.add(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i));
            }
        }

        int j;
        if (revelsMaxPercOfProvinces <= joinProvinces.size() + 1) {
            for (i = joinProvinces.size() - 1; i >= 0; --i) {
                boolean bRemove = true;

                for (j = 0; j < tempRevCivsIDs.size(); ++j) {
                    if (CFG.game.getProvince((Integer) joinProvinces.get(i)).getCore().getHaveACore((Integer) tempRevCivsIDs.get(j))) {
                        bRemove = false;
                    }
                }

                if (bRemove) {
                    joinProvinces.remove(i);
                    if (revelsMaxPercOfProvinces >= joinProvinces.size() + 1) {
                        break;
                    }
                }
            }

            if (revelsMaxPercOfProvinces <= joinProvinces.size() + 1) {
                while (joinProvinces.size() > 0 && revelsMaxPercOfProvinces <= joinProvinces.size() + 1) {
                    joinProvinces.remove(CFG.oR.nextInt(joinProvinces.size()));
                }
            }
        } else {
            final List<Integer> tempPossibleToAdd = new ArrayList<Integer>();
            for (int i3 = 0; i3 < joinProvinces.size(); ++i3) {
                for (int j2 = 0; j2 < CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvincesSize(); ++j2) {
                    for (int k2 = 0; k2 < tempRevCivsIDs.size(); ++k2) {
                        if (CFG.game.getProvince(CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2)).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2)).getCore().getHaveACore(tempRevCivsIDs.get(k2))) {
                            boolean canBeAdded = CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2) != nProvinceID;
                            if (canBeAdded) {
                                for (int o = 0; o < joinProvinces.size(); ++o) {
                                    if (CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2) == joinProvinces.get(o)) {
                                        canBeAdded = false;
                                        break;
                                    }
                                }
                                if (canBeAdded) {
                                    for (int o = 0; o < tempPossibleToAdd.size(); ++o) {
                                        if (tempPossibleToAdd.get(o) == CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2)) {
                                            canBeAdded = false;
                                            break;
                                        }
                                    }
                                    if (canBeAdded) {
                                        tempPossibleToAdd.add(CFG.game.getProvince(joinProvinces.get(i3)).getNeighboringProvinces(j2));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            final List<Integer> sortedPossibleToAdd = new ArrayList<Integer>();

            while (tempPossibleToAdd.size() > 0) {
                int tBest = 0;
                for (int i4 = 1; i4 < tempPossibleToAdd.size(); ++i4) {
                    if (CFG.game.getProvince(tempPossibleToAdd.get(i4)).getPopulationData().getPopulation() * CFG.game.getProvince(tempPossibleToAdd.get(i4)).getRevolutionaryRisk() > CFG.game.getProvince(tempPossibleToAdd.get(tBest)).getPopulationData().getPopulation() * CFG.game.getProvince(tempPossibleToAdd.get(tBest)).getRevolutionaryRisk()) {
                        tBest = i4;
                    }
                }
                sortedPossibleToAdd.add(tempPossibleToAdd.get(tBest));
                tempPossibleToAdd.remove(tBest);
            }
            for (int i5 = 0; i5 < sortedPossibleToAdd.size() && revelsMaxPercOfProvinces > joinProvinces.size() + 1; ++i5) {
                joinProvinces.add(sortedPossibleToAdd.get(i5));
            }
        }

        for (i = 0; i < joinProvinces.size(); ++i) {
            if (CFG.game.getProvince((Integer) joinProvinces.get(i)).getCivID() != nRebelsCivID) {
                if (CFG.game.getProvince((Integer) joinProvinces.get(i)).getArmy(0) > 0) {
                    tempArmies.add(new Province_Army(nCivID, CFG.game.getProvince((Integer) joinProvinces.get(i)).getArmy(0), (Integer) joinProvinces.get(i)));
                    tempArmiesProvinces.add((Integer) joinProvinces.get(i));
                    CFG.game.getProvince((Integer) joinProvinces.get(i)).updateArmy(0);
                }

                CFG.game.getProvince((Integer) joinProvinces.get(i)).setCivID(nRebelsCivID, true);
                this.spawnRevolutionaryArmy((Integer) joinProvinces.get(i), nCivID, nRebelsCivID);
                this.updateProvinceAfterRevolution((Integer) joinProvinces.get(i));
            }
        }

        CFG.game.getCiv(nRebelsCivID).buildCivPersonality();

        for (int i2 = 0; i2 < tempArmies.size(); ++i2) {
            CFG.game.getProvince(tempArmiesProvinces.get(i2)).updateArmy(tempArmies.get(i2).getCivID(), tempArmies.get(i2).getArmy());
            CFG.game.getCiv(tempArmies.get(i2).getCivID()).newMove(tempArmiesProvinces.get(i2), tempArmiesProvinces.get(i2), tempArmies.get(i2).getArmy(), true);
            for (int a = CFG.game.getProvince(tempArmiesProvinces.get(i2)).getCivsSize() - 1; a >= 0; --a) {
                if (CFG.game.getProvince(tempArmiesProvinces.get(i2)).getCivID(a) != nCivID && CFG.game.getProvince(tempArmiesProvinces.get(i2)).getCivID(a) != nRebelsCivID) {
                    this.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(tempArmiesProvinces.get(i2)).getCivID(a), tempArmiesProvinces.get(i2));
                }
            }
        }

    }

    protected final void spawnRevolution_UpdateCivData(int nCivID, int nRebelsCivID, String nRevTag) {
        CFG.game.getCiv(nRebelsCivID).setCivTag(nRevTag);
        Color nColor = CFG.getRandomColor();
        CFG.game.getCiv(nRebelsCivID).setR((int) (nColor.r * 255.0F));
        CFG.game.getCiv(nRebelsCivID).setG((int) (nColor.g * 255.0F));
        CFG.game.getCiv(nRebelsCivID).setB((int) (nColor.b * 255.0F));
        if (CFG.game.getCiv(nRebelsCivID).getMoney() < 100L) {
            CFG.game.getCiv(nRebelsCivID).setMoney(100L);
        }

        CFG.game.getCiv(nRebelsCivID).setCivName(CFG.langManager.get("Rebels"));
        CFG.game.getCiv(nRebelsCivID).setTechnologyLevel(CFG.game.getCiv(nCivID).getTechnologyLevel() * (0.575F + (float) CFG.oR.nextInt(25) / 100.0F));
        CFG.game.declareWar(nRebelsCivID, nCivID, true);
    }

    protected final void spawnRevolutionaryArmy(int nProvinceID, int nCivID, int nRebelsCivID) {
        int revolutionaryPop = 10 + CFG.oR.nextInt(50);

        int i;
        for (i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == nCivID) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.00125F);
            } else if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)).getNumOfProvinces() == 0) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * (0.0125F + (float) CFG.oR.nextInt(35) / 1000.0F));
            } else if (CFG.game.getCivsAtWar(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), nCivID)) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.0145F);
            } else {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 8.5E-4F);
            }
        }

        i = (int) ((float) revolutionaryPop * Math.min(0.1F + 0.15F * (float) CFG.game.getProvince(nProvinceID).saveProvinceData.iNumOfRevolutions + 0.075F * (float) CFG.game.getCiv(nCivID).civGameData.iNumOfRevolutions + (float) CFG.oR.nextInt(90) / 1000.0F, 10.0F));
        CFG.game.getProvince(nProvinceID).updateArmy(nRebelsCivID, i);
        CFG.game.getCiv(nRebelsCivID).setNumOfUnits(CFG.game.getCiv(nRebelsCivID).getNumOfUnits() + i);
        ++CFG.game.getProvince(nProvinceID).saveProvinceData.iNumOfRevolutions;
    }

    protected final int getSpawnRevolutionaryArmy_MAX(int nProvinceID, int nCivID) {
        int revolutionaryPop = 10 + CFG.oR.nextInt(50);

        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == nCivID) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.00125F);
            } else if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)).getNumOfProvinces() == 0) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.0475F);
            } else if (CFG.game.getCivsAtWar(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), nCivID)) {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.0145F);
            } else {
                revolutionaryPop += (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 8.5E-4F);
            }
        }

        return (int) ((float) revolutionaryPop * 0.17199999F) + 2;
    }

    protected final void updateProvinceAfterRevolution(int nProvinceID) {
        CFG.game.getProvince(nProvinceID).setRevolutionaryRisk(CFG.game.getProvince(nProvinceID).getRevolutionaryRisk() * (0.42241F + (float) CFG.oR.nextInt(400) / 1000.0F));
        CFG.game.getProvince(nProvinceID).setHappiness(CFG.game.getProvince(nProvinceID).getHappiness() * (1.075F + (float) CFG.oR.nextInt(52) / 100.0F));
        CFG.game.getProvince(nProvinceID).setEconomy((int) ((float) CFG.game.getProvince(nProvinceID).getEconomy() * (0.98244F - (float) CFG.oR.nextInt(78) / 1000.0F)));
        CFG.game.getProvince(nProvinceID).setDevelopmentLevel(CFG.game.getProvince(nProvinceID).getDevelopmentLevel() * (0.93244F - (float) CFG.oR.nextInt(184) / 1000.0F));
        if (CFG.game.getProvince(nProvinceID).getLevelOfLibrary() > 0 && CFG.oR.nextInt(100) < 64) {
            CFG.game.getProvince(nProvinceID).setLevelOfLibrary(0);
        }

        this.updateMetCivilization(nProvinceID);
    }

    protected final void moveRegroupArmy() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            try {
                CFG.game.getCiv(i).moveRegroupArmy();
            } catch (IndexOutOfBoundsException e) {
                Gdx.app.log("AoC", "GA -> moveRegroupArmy: IndexOutOfBoundsException for civID=" + i);
                CFG.exceptionStack(e);
            }
        }

    }

    private final void migrateFromTo(int nCivID, int fromProvinceID, int toProvinceID) {
        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() == nCivID) {
                Game var10000 = CFG.game;
                if (Game.uncivilizedCanMigrate(toProvinceID, nCivID)) {
                    ArrayList<Integer> tCivs = new ArrayList<Integer>();
                    ArrayList<Integer> tArmies = new ArrayList<Integer>();

                    int minus_A;
                    for (minus_A = 0; minus_A < CFG.game.getProvince(fromProvinceID).getCivsSize(); ++minus_A) {
                        tCivs.add(CFG.game.getProvince(fromProvinceID).getCivID(minus_A));
                        tArmies.add(CFG.game.getProvince(fromProvinceID).getArmy(minus_A));
                    }

                    minus_A = CFG.game.getProvince(fromProvinceID).saveProvinceData.iNumOfTurnsWithBalanceOnMinus;
                    CivFestival tFestival = CFG.game.getCiv(nCivID).isFestivalOrganized_GET(fromProvinceID);
                    if (tFestival != null) {
                        CFG.game.getCiv(nCivID).removeFestival_ProvinceID(fromProvinceID);
                    }

                    CivFestival tAssimilate = CFG.game.getCiv(nCivID).isAssimilateOrganized_GET(fromProvinceID);
                    if (tAssimilate != null) {
                        CFG.game.getCiv(nCivID).removeAssimilate_ProvinceID(fromProvinceID);
                    }

                    CivInvest tInvest = CFG.game.getCiv(nCivID).isInvestOrganized_GET(fromProvinceID);
                    if (tInvest != null) {
                        CFG.game.getCiv(nCivID).removeInvest_ProvinceID(fromProvinceID);
                    }

                    int tNeutral = CFG.game.getProvince(toProvinceID).getArmy(0);

                    int j;
                    for (j = CFG.game.getProvince(fromProvinceID).getCivsSize() - 1; j >= 0; --j) {
                        CFG.game.getProvince(fromProvinceID).updateArmy(CFG.game.getProvince(fromProvinceID).getCivID(j), 0);
                    }

                    for (j = CFG.game.getProvince(toProvinceID).getCivsSize() - 1; j >= 0; --j) {
                        CFG.game.getProvince(toProvinceID).updateArmy(CFG.game.getProvince(toProvinceID).getCivID(j), 0);
                    }

                    CFG.game.getProvince(fromProvinceID).setTrueOwnerOfProvince(nCivID);
                    CFG.game.getProvince(toProvinceID).setCivID(nCivID, false);
                    CFG.game.getProvince(fromProvinceID).setTrueOwnerOfProvince(0);
                    CFG.game.getProvince(fromProvinceID).setCivID(0, false);
                    CFG.game.getProvince(toProvinceID).saveProvinceData.iNumOfTurnsWithBalanceOnMinus = minus_A;
                    if (CFG.game.getCiv(nCivID).getCapitalProvinceID() == fromProvinceID) {
                        CFG.game.getProvince(toProvinceID).setIsCapital(true);
                        CFG.game.getProvince(fromProvinceID).setIsCapital(false);
                        CFG.game.getCiv(nCivID).setCapitalProvinceID(toProvinceID);

                        try {
                            CFG.game.getProvince(fromProvinceID).getCity(0).setCityLevel(CFG.getEditorCityLevel(3));
                        } catch (IndexOutOfBoundsException var16) {
                        }

                        try {
                            CFG.game.getProvince(toProvinceID).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
                        } catch (IndexOutOfBoundsException var15) {
                        }
                    }

                    CFG.game.getProvince(fromProvinceID).setDrawCities(false);
                    CFG.game.getProvince(toProvinceID).setDrawCities(true);
                    Province_Population tempD = CFG.game.getProvince(toProvinceID).getPopulationData();
                    CFG.game.getProvince(toProvinceID).setPopulationData(CFG.game.getProvince(fromProvinceID).getPopulationData());
                    CFG.game.getProvince(fromProvinceID).setPopulationData(tempD);
                    int tData = CFG.game.getProvince(toProvinceID).getEconomy();
                    CFG.game.getProvince(toProvinceID).setEconomy(CFG.game.getProvince(fromProvinceID).getEconomy());
                    CFG.game.getProvince(fromProvinceID).setEconomy(tData);
                    float fData = CFG.game.getProvince(toProvinceID).getHappiness();
                    CFG.game.getProvince(toProvinceID).setHappiness(CFG.game.getProvince(fromProvinceID).getHappiness());
                    CFG.game.getProvince(fromProvinceID).setHappiness(fData);
                    fData = CFG.game.getProvince(toProvinceID).getDevelopmentLevel();
                    CFG.game.getProvince(toProvinceID).setDevelopmentLevel(CFG.game.getProvince(fromProvinceID).getDevelopmentLevel());
                    CFG.game.getProvince(fromProvinceID).setDevelopmentLevel(fData);
                    if (tFestival != null) {
                        tFestival.iProvinceID = toProvinceID;
                        CFG.game.getCiv(nCivID).addFestival(tFestival);
                    }

                    if (tAssimilate != null) {
                        tAssimilate.iProvinceID = toProvinceID;
                        CFG.game.getCiv(nCivID).addAssimilate(tAssimilate);
                    }

                    if (tInvest != null) {
                        tInvest.iProvinceID = toProvinceID;
                        CFG.game.getCiv(nCivID).addInvest(tInvest);
                    }

                    for (j = 0; j < tCivs.size(); ++j) {
                        CFG.game.getProvince(toProvinceID).updateArmy((Integer) tCivs.get(j), (Integer) tArmies.get(j));
                    }

                    CFG.game.getProvince(fromProvinceID).updateArmy(0, tNeutral);
                    CFG.game.getProvince(toProvinceID).iIncome_Taxation = CFG.game.getProvince(fromProvinceID).iIncome_Taxation;
                    CFG.game.getProvince(toProvinceID).iIncome_Production = CFG.game.getProvince(fromProvinceID).iIncome_Production;
                    CFG.game.getProvince(toProvinceID).iAdministrationCost = CFG.game.getProvince(fromProvinceID).iAdministrationCost;
                    CFG.game.getProvince(fromProvinceID).getCore().resetOwnership(nCivID);
                    CFG.game.getProvince(toProvinceID).getCore().resetOwnership(nCivID);
                    CFG.game.getProvince(fromProvinceID).updateDrawArmy();
                    CFG.game.getProvince(toProvinceID).updateDrawArmy();
                    TechnologyManager.updateCivs_ResearchProgress_Migrate(nCivID, toProvinceID);
                }
            }
        } catch (IndexOutOfBoundsException var17) {
            CFG.exceptionStack(var17);
        } catch (NullPointerException var18) {
            CFG.exceptionStack(var18);
        }

    }

    private final boolean turnMoves_IsACombatMove(int nCivID, int toProvinceID) {
        if (nCivID != CFG.game.getProvince(toProvinceID).getCivID() && nCivID != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() && CFG.game.getProvince(toProvinceID).getCivID() != CFG.game.getCiv(nCivID).getPuppetOfCivID() && CFG.game.getMilitaryAccess(nCivID, CFG.game.getProvince(toProvinceID).getCivID()) == 0 && (CFG.game.getCiv(nCivID).getAllianceID() <= 0 || CFG.game.getCiv(nCivID).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getAllianceID())) {
            return true;
        } else {
            for (int i = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() - 1; i > 0; --i) {
                if (CFG.game.getCivsAtWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    private final void turnMoves_UpdatePlayersFogOfWar(int nCivID) {
        if (CFG.game.getCiv(nCivID).getControlledByPlayer() && CFG.PLAYER_TURNID != CFG.game.getPlayerID_ByCivID(nCivID)) {
            CFG.PLAYER_TURNID = CFG.game.getPlayerID_ByCivID(nCivID);
            if (CFG.FOG_OF_WAR > 0) {
                for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                    CFG.game.getProvince(i).updateDrawArmy();
                }
            }

            if (this.getNumOfPlayersInGame() > 1) {
                CFG.menuManager.updateInGame_TOP_All_NextTurnActions(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            }
        }

    }

    private final void turnMoves_MoveCurrentArmy() {
        CFG.menuManager.setVisible_InGame_Dices(false);

        //new capitulation check (instead of turn_newturn)
        DiplomacyManager.checkCapitulate(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID());

        if (this.currentMoveUnits.getCivID(0) != CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() && this.currentMoveUnits.getCivID(0) != CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getPuppetOfCivID() && CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() != CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getPuppetOfCivID() && !CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince()) {
            if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() > 0 && (CFG.game.getCivsInAlliance(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) || CFG.game.getMilitaryAccess(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) > 0)) {
                for (int i = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() - 1; i > 0; --i) {
                    if (CFG.game.getCivsAtWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i))) {
                        int losses = Math.min(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i), this.currentMoveUnits.getMoveUnits_TotalNumOfUnits());
                        if (losses > 0) {
                            final int tWarID = CFG.game.getWarID(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i));
                            int tempArmy = Math.min(losses, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i));
                            if (tWarID >= 0) {
                                CFG.game.getWar(tWarID).addCasualties(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i), tempArmy);
                            }
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i) - losses);
                            for (int j = 0; j < this.currentMoveUnits.getMoveUnitsSize(); ++j) {
                                if (this.currentMoveUnits.getMoveUnits(j).getNumOfUnits() > 0) {
                                    tempArmy = Math.min(this.currentMoveUnits.getMoveUnits(j).getNumOfUnits(), losses);
                                    if (tWarID >= 0) {
                                        CFG.game.getWar(tWarID).addCasualties(this.currentMoveUnits.getCivID(j), tempArmy);
                                    }
                                    this.currentMoveUnits.getMoveUnits(j).setNumOfUnits(Math.max(this.currentMoveUnits.getMoveUnits(j).getNumOfUnits() - losses, 0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(j).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(j), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(j).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(j)) - losses);
                                    losses -= tempArmy;
                                    if (losses <= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                this.turnMoves_MoveCurrentArmy_JustMove();
            } else {
                try {
                    Gdx.app.log("AoC", "ATTACK: 111");
                    if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() != 0 && !CFG.game.getCivsAtWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID())) {
                        int tNumOfCivs = 1;
                        for (int c = 1; c < this.currentMoveUnits.getMoveUnitsSize(); ++c) {
                            if (this.currentMoveUnits.getCivID(0) != this.currentMoveUnits.getCivID(c)) {
                                ++tNumOfCivs;
                                break;
                            }
                        }
                        if (tNumOfCivs == 1) {
                            this.turnMoves_MoveCurrentArmy_JustMove();
                            this.currentMoveUnits = null;
                            return;
                        }
                        if (!CFG.game.isAlly(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID())) {
                            CFG.game.declareWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(), false);
                        }
                    }
                    if (this.SHOW_REPORT) {
                        CFG.reportData = new Report_Data();
                        CFG.reportData.iBattleOfProvinceID = this.currentMoveUnits.getMoveUnits(0).getToProvinceID();
                    }
                    Gdx.app.log("AoC", "ATTACK: 222");
                    int tempNumOfUnits = 0;
                    for (int k = 0; k < this.currentMoveUnits.getMoveUnitsSize(); ++k) {
                        tempNumOfUnits += this.currentMoveUnits.getMoveUnits(k).getNumOfUnits();
                    }
                    final int tempPopulationBefore = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getPopulationData().getPopulation();
                    final int tempEconomyBefore = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getEconomy();
                    if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID() == 0) {
                        this.updatePopulationLosses(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), (int) Math.min(tempNumOfUnits * 0.0375f, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getPopulationData().getPopulation() * 0.0025f));
                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateProvinceEconomyLosses(tempNumOfUnits, 0.0575f);
                    } else {
                        this.updatePopulationLosses(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), (int) Math.min(tempNumOfUnits * (0.0305f + 0.0065f * CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getTechnologyLevel()), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getPopulationData().getPopulation() * 0.0025f));
                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateProvinceEconomyLosses(tempNumOfUnits, 0.0575f);
                    }
                    Gdx.app.log("AoC", "ATTACK: 333");
                    if (this.SHOW_REPORT) {
                        CFG.reportData.iPopulationLosses = tempPopulationBefore - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getPopulationData().getPopulation();
                        CFG.reportData.iEconomyLosses = tempEconomyBefore - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getEconomy();
                    }
                    final int tempWarID = CFG.game.getWarID(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID());
                    CFG.game.updateWarStatistics(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(), tempPopulationBefore - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getPopulationData().getPopulation(), tempEconomyBefore - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getEconomy());
                    Gdx.app.log("AoC", "ATTACK: 444");
                    if (this.turnMoves_MoveCurrentArmy_AttackResult(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), tempNumOfUnits)) {
                        Gdx.app.log("AoC", "WON: 111");
                        int attackersArmy = tempNumOfUnits;
                        int defendersArmy = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setWasAttacked(2);
                        Gdx.app.log("AoC", "WONBEFORE: attackersArmy: " + attackersArmy);
                        Gdx.app.log("AoC", "WONBEFORE: defendersArmy: " + defendersArmy);
                        attackersArmy = (int) Math.ceil(attackersArmy);
                        defendersArmy = (int) Math.ceil(defendersArmy * (1.0f - this.turnMoves_MoveCurrentArmy_Attack_DefensiveModifiers(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()) + this.turnMoves_MoveCurrentArmy_Attack_OffensiveModifiers(this.currentMoveUnits.getMoveUnits(0).getToProvinceID())));
                        Gdx.app.log("AoC", "WON: attackersArmy: " + attackersArmy);
                        Gdx.app.log("AoC", "WON: defendersArmy: " + defendersArmy);
                        CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                        Gdx.app.log("AoC", "WON: 222");
                        if (this.SHOW_REPORT) {
                            CFG.reportData.attackersWon = true;
                            CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                            CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                            CFG.reportData.lDefenders_ArmiesLost.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                            CFG.soundsManager.playSound(CFG.soundsManager.playMoveArmy());
                        }
                        CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0)).getNumOfUnits() - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                        Gdx.app.log("AoC", "WON: 333");
                        for (int l = 1, iBreak = 0; l < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() && iBreak < 50; ++iBreak) {
                            if ((int) CFG.game.getCivRelation_OfCivB(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l)) != -100 && !CFG.game.isAlly(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l))) {
                                CFG.game.declareWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), false);
                            }
                            CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l));
                            if (this.SHOW_REPORT && (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l) > 0 || CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() == 1)) {
                                CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l));
                                CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l));
                                CFG.reportData.lDefenders_ArmiesLost.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l));
                            }
                            CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l)).getNumOfUnits() - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l));
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), 0);
                        }
                        Gdx.app.log("AoC", "WON: 444");
                        final List<Integer> tempAttackersCivID = new ArrayList<Integer>();
                        final List<Integer> tempAttackersArmy = new ArrayList<Integer>();
                        for (int m = 0; m < this.currentMoveUnits.getMoveUnitsSize(); ++m) {
                            boolean tempAdd = true;
                            for (int j2 = 0; j2 < tempAttackersCivID.size(); ++j2) {
                                if (tempAttackersCivID.get(j2) == this.currentMoveUnits.getCivID(m)) {
                                    tempAdd = false;
                                    tempAttackersArmy.set(j2, tempAttackersArmy.get(j2) + this.currentMoveUnits.getMoveUnits(m).getNumOfUnits());
                                    break;
                                }
                            }
                            if (tempAdd) {
                                tempAttackersCivID.add(this.currentMoveUnits.getCivID(m));
                                tempAttackersArmy.add(this.currentMoveUnits.getMoveUnits(m).getNumOfUnits());
                            }
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(m).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(m), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(m).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(m)) - this.currentMoveUnits.getMoveUnits(m).getNumOfUnits());
                        }
                        Gdx.app.log("AoC", "WON: 555");
                        if (tempAttackersCivID.size() > 1) {
                            Gdx.app.log("AoC", "WON: 666A");
                            for (int m = 0, iSize = tempAttackersCivID.size(); m < iSize - 1; ++m) {
                                int tempBiggestArmyID = m;
                                for (int j3 = m + 1; j3 < iSize; ++j3) {
                                    if (tempAttackersArmy.get(tempBiggestArmyID) < tempAttackersArmy.get(j3)) {
                                        tempBiggestArmyID = j3;
                                    }
                                }
                                if (tempBiggestArmyID != m) {
                                    final int tempC = tempAttackersCivID.get(m);
                                    final int tempA = tempAttackersArmy.get(m);
                                    tempAttackersCivID.set(m, tempAttackersCivID.get(tempBiggestArmyID));
                                    tempAttackersArmy.set(m, tempAttackersArmy.get(tempBiggestArmyID));
                                    tempAttackersCivID.set(tempBiggestArmyID, tempC);
                                    tempAttackersArmy.set(tempBiggestArmyID, tempA);
                                }
                            }
                            CFG.game.updateWarStatistics_Casualties(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), tempAttackersCivID.get(0), tempAttackersArmy.get(0) - (int) Math.ceil(tempAttackersArmy.get(0) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                            if (this.SHOW_REPORT) {
                                CFG.reportData.lAttackers_IDs.add(tempAttackersCivID.get(0));
                                CFG.reportData.lAttackers_Armies.add(tempAttackersArmy.get(0));
                                CFG.reportData.lAttackers_Armies_Lost.add(tempAttackersArmy.get(0) - (int) Math.ceil(tempAttackersArmy.get(0) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                            }
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy((int) Math.ceil(tempAttackersArmy.get(0) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                            CFG.game.getCiv(tempAttackersCivID.get(0)).setNumOfUnits(CFG.game.getCiv(tempAttackersCivID.get(0)).getNumOfUnits() - Math.min(tempAttackersArmy.get(0), tempAttackersArmy.get(0) - (int) Math.ceil(tempAttackersArmy.get(0) / (float) tempNumOfUnits * (attackersArmy - defendersArmy))));
                            for (int m = 1; m < tempAttackersCivID.size(); ++m) {
                                if ((int) CFG.game.getCivRelation_OfCivB(tempAttackersCivID.get(m), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0)) != -100 && !CFG.game.isAlly(tempAttackersCivID.get(m), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0))) {
                                    CFG.game.declareWar(tempAttackersCivID.get(m), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), false);
                                }
                                CFG.game.updateWarStatistics_Casualties(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), tempAttackersCivID.get(m), tempAttackersArmy.get(m) - (int) Math.floor(tempAttackersArmy.get(m) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                                if (this.SHOW_REPORT) {
                                    CFG.reportData.lAttackers_IDs.add(tempAttackersCivID.get(m));
                                    CFG.reportData.lAttackers_Armies.add(tempAttackersArmy.get(m));
                                    CFG.reportData.lAttackers_Armies_Lost.add(tempAttackersArmy.get(m) - (int) Math.floor(tempAttackersArmy.get(m) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                                }
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(tempAttackersCivID.get(m), (int) Math.floor(tempAttackersArmy.get(m) / (float) tempNumOfUnits * (attackersArmy - defendersArmy)));
                                CFG.game.getCiv(tempAttackersCivID.get(m)).setNumOfUnits(CFG.game.getCiv(tempAttackersCivID.get(m)).getNumOfUnits() - Math.min(tempAttackersArmy.get(m), tempAttackersArmy.get(m) - (int) Math.floor(tempAttackersArmy.get(m) / (float) tempNumOfUnits * (attackersArmy - defendersArmy))));
                            }
                            if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                                if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() != this.currentMoveUnits.getCivID(0) && !CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), this.currentMoveUnits.getCivID(0))) {
                                    final int tArmy = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                    final int tArmyTrueOwner = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmyCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince());
                                    final int tTrueOwner = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince();
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(tTrueOwner, tArmyTrueOwner);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                } else {
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                }
                            } else if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() < 1 || CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() == tempAttackersCivID.get(0)) {
                                CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, tempAttackersCivID.get(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(tempAttackersCivID.get(0), true);
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                    this.updateInGame_ProvinceInfo();
                                }
                            } else if (CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) && (CFG.game.getCivsAreAllied(tempAttackersCivID.get(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()) || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()).getPuppetOfCivID() == tempAttackersCivID.get(0) || CFG.game.getCivsAreAllied(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()).getPuppetOfCivID(), tempAttackersCivID.get(0)) || CFG.game.getCiv(tempAttackersCivID.get(0)).getPuppetOfCivID() == CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() || CFG.game.getCivsAreAllied(CFG.game.getCiv(tempAttackersCivID.get(0)).getPuppetOfCivID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()))) {
                                final int tArmy = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                final int tArmyTrue = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmyCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince());
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), 0);
                                CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), tArmyTrue);
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy);
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                    this.updateInGame_ProvinceInfo();
                                }
                            } else {
                                boolean ownerChanged = false;
                                int i2 = 0;
                                while (i2 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivsSize()) {
                                    if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2) == tempAttackersCivID.get(0)) {
                                        CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, tempAttackersCivID.get(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(tempAttackersCivID.get(0), true);
                                        ownerChanged = true;
                                        if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                            this.updateInGame_ProvinceInfo();
                                            break;
                                        }
                                        break;
                                    } else {
                                        ++i2;
                                    }
                                }
                                if (!ownerChanged) {
                                    i2 = 0;
                                    while (i2 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivsSize()) {
                                        if (CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) && (CFG.game.getCivsAreAllied(tempAttackersCivID.get(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2)) || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2)).getPuppetOfCivID() == tempAttackersCivID.get(0) || CFG.game.getCivsAreAllied(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2)).getPuppetOfCivID(), tempAttackersCivID.get(0)) || CFG.game.getCiv(tempAttackersCivID.get(0)).getPuppetOfCivID() == CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2) || CFG.game.getCivsAreAllied(CFG.game.getCiv(tempAttackersCivID.get(0)).getPuppetOfCivID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2)))) {
                                            final int tArmy2 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                            final int tArmyTrue2 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmyCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince());
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), 0);
                                            CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i2), true);
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), tArmyTrue2);
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy2);
                                            ownerChanged = true;
                                            if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                                this.updateInGame_ProvinceInfo();
                                                break;
                                            }
                                            break;
                                        } else {
                                            ++i2;
                                        }
                                    }
                                }
                                if (!ownerChanged) {
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, tempAttackersCivID.get(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(tempAttackersCivID.get(0), true);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "WON: 777A END");
                        } else {
                            Gdx.app.log("AoC", "WON: 666B");
                            int tempDefendersArmyLeft = defendersArmy;
                            for (int i2 = 0; i2 < this.currentMoveUnits.getMoveUnitsSize(); ++i2) {
                                CFG.game.updateWarStatistics_Casualties(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), this.currentMoveUnits.getCivID(i2), (this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits() > tempDefendersArmyLeft) ? tempDefendersArmyLeft : this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits());
                                tempDefendersArmyLeft -= this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits();
                                if (tempDefendersArmyLeft < 0) {
                                    tempDefendersArmyLeft = 0;
                                }
                            }
                            if (this.SHOW_REPORT) {
                                tempDefendersArmyLeft = defendersArmy;
                                for (int i2 = 0; i2 < this.currentMoveUnits.getMoveUnitsSize(); ++i2) {
                                    CFG.reportData.lAttackers_IDs.add(this.currentMoveUnits.getCivID(i2));
                                    CFG.reportData.lAttackers_Armies.add(this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits());
                                    CFG.reportData.lAttackers_Armies_Lost.add((this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits() > tempDefendersArmyLeft) ? tempDefendersArmyLeft : this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits());
                                    tempDefendersArmyLeft -= this.currentMoveUnits.getMoveUnits(i2).getNumOfUnits();
                                    if (tempDefendersArmyLeft < 0) {
                                        tempDefendersArmyLeft = 0;
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "WON: 777B");
                            Gdx.app.log("AoC", "WON: 777B: attackersArmy: " + attackersArmy);
                            Gdx.app.log("AoC", "WON: 777B: defendersArmy: " + defendersArmy);
                            Gdx.app.log("AoC", "WON: 777B: FROM ARMY: " + CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getArmy(0));
                            Gdx.app.log("AoC", "WON: 777B: TO ARMY: " + CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(attackersArmy - defendersArmy);
                            CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).setNumOfUnits(CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getNumOfUnits() - defendersArmy);
                            Gdx.app.log("AoC", "WON: 777C");
                            Gdx.app.log("AoC", "WON: 777B: FROM ARMY: " + CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getArmy(0));
                            Gdx.app.log("AoC", "WON: 777B: TO ARMY: " + CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                            if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                                if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() != this.currentMoveUnits.getCivID(0) && !CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), this.currentMoveUnits.getCivID(0))) {
                                    final int tArmy3 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                    final int tArmyTrueOwner2 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmyCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince());
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(tArmyTrueOwner2);
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy3);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                } else {
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                }
                            } else if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() < 1 || CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() == this.currentMoveUnits.getCivID(0)) {
                                CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(this.currentMoveUnits.getCivID(0), true);
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                    this.updateInGame_ProvinceInfo();
                                }
                            } else if (CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) && (CFG.game.getCivsAreAllied(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()) || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()).getPuppetOfCivID() == this.currentMoveUnits.getCivID(0) || CFG.game.getCivsAreAllied(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()).getPuppetOfCivID(), this.currentMoveUnits.getCivID(0)) || CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getPuppetOfCivID() == CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince() || CFG.game.getCivsAreAllied(CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getPuppetOfCivID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince()))) {
                                final int tArmy3 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getTrueOwnerOfProvince(), true);
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy3);
                                if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                    this.updateInGame_ProvinceInfo();
                                }
                            } else {
                                boolean ownerChanged2 = false;
                                int i3 = 0;
                                while (i3 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivsSize()) {
                                    if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3) == this.currentMoveUnits.getCivID(0)) {
                                        CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(this.currentMoveUnits.getCivID(0), true);
                                        ownerChanged2 = true;
                                        if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                            this.updateInGame_ProvinceInfo();
                                            break;
                                        }
                                        break;
                                    } else {
                                        ++i3;
                                    }
                                }
                                if (!ownerChanged2) {
                                    i3 = 0;
                                    while (i3 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivsSize()) {
                                        if (CFG.game.getCivsAtWar(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()) && (CFG.game.getCivsAreAllied(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3)) || CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3)).getPuppetOfCivID() == this.currentMoveUnits.getCivID(0) || CFG.game.getCivsAreAllied(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3)).getPuppetOfCivID(), this.currentMoveUnits.getCivID(0)) || CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getPuppetOfCivID() == CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3) || CFG.game.getCivsAreAllied(CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getPuppetOfCivID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3)))) {
                                            final int tArmy4 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0);
                                            CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCore().getCivID(i3), true);
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(0);
                                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), tArmy4);
                                            ownerChanged2 = true;
                                            if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                                this.updateInGame_ProvinceInfo();
                                                break;
                                            }
                                            break;
                                        } else {
                                            ++i3;
                                        }
                                    }
                                }
                                if (!ownerChanged2) {
                                    CFG.game.updateWarStatistics_ConqueredProvinces(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setCivID(this.currentMoveUnits.getCivID(0), true);
                                    if (this.currentMoveUnits.getMoveUnits(0).getToProvinceID() == CFG.game.getActiveProvinceID()) {
                                        this.updateInGame_ProvinceInfo();
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "WON: 777B END");
                        }
                    } else {
                        Gdx.app.log("AoC", "LOSS: 111");
                        if (this.SHOW_REPORT) {
                            CFG.reportData.attackersWon = false;
                        }
                        CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).setWasAttacked(2);
                        int attackersArmy = tempNumOfUnits;
                        final int defendersArmy = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                        final int numOfDefenders = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefenders(this.currentMoveUnits.getMoveUnits(0).getToProvinceID());
                        attackersArmy = (int) Math.ceil(attackersArmy * (1.0f - this.turnMoves_MoveCurrentArmy_Attack_OffensiveModifiers(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()) + this.turnMoves_MoveCurrentArmy_Attack_DefensiveModifiers(this.currentMoveUnits.getMoveUnits(0).getToProvinceID())));
                        for (int i4 = 0; i4 < this.currentMoveUnits.getMoveUnitsSize(); ++i4) {
                            CFG.game.updateWarStatistics_Casualties(tempWarID, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), this.currentMoveUnits.getCivID(i4), this.currentMoveUnits.getMoveUnits(i4).getNumOfUnits());
                            if (this.SHOW_REPORT) {
                                CFG.reportData.lAttackers_IDs.add(this.currentMoveUnits.getCivID(i4));
                                CFG.reportData.lAttackers_Armies.add(this.currentMoveUnits.getMoveUnits(i4).getNumOfUnits());
                                CFG.reportData.lAttackers_Armies_Lost.add(this.currentMoveUnits.getMoveUnits(i4).getNumOfUnits());
                            }
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i4).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(i4), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i4).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(i4)) - this.currentMoveUnits.getMoveUnits(i4).getNumOfUnits());
                            CFG.game.getCiv(this.currentMoveUnits.getCivID(i4)).setNumOfUnits(CFG.game.getCiv(this.currentMoveUnits.getCivID(i4)).getNumOfUnits() - this.currentMoveUnits.getMoveUnits(i4).getNumOfUnits());
                        }
                        Gdx.app.log("AoC", "LOSS: 222");
                        if (numOfDefenders > 1) {
                            Gdx.app.log("AoC", "LOSS: 333A");
                            Gdx.app.log("AoC", "defendersArmy: " + defendersArmy);
                            Gdx.app.log("AoC", "attackersArmy: " + attackersArmy);
                            CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0), (int) Math.ceil(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) / (float) defendersArmy * attackersArmy));
                            if (this.SHOW_REPORT) {
                                CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0));
                                CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                                CFG.reportData.lDefenders_ArmiesLost.add((int) Math.ceil(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) / (float) defendersArmy * attackersArmy));
                            }
                            Gdx.app.log("AoC", "LOSS: 333A - 111");
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy((int) (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) - Math.ceil(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) / (float) defendersArmy * attackersArmy)));
                            CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(0)).getNumOfUnits() - (int) Math.ceil(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) / (float) defendersArmy * attackersArmy));
                            Gdx.app.log("AoC", "LOSS: 333A - 222");
                            final List<Integer> tempIDs = new ArrayList<Integer>();
                            final List<Integer> tempArmies = new ArrayList<Integer>();
                            final List<Integer> tempArmies_Lost = new ArrayList<Integer>();
                            for (int i3 = 1; i3 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize(); ++i3) {
                                if (this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_IsDefender(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3))) {
                                    if ((int) CFG.game.getCivRelation_OfCivB(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3)) != -100 && !CFG.game.isAlly(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3))) {
                                        CFG.game.declareWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3), false);
                                    }
                                    CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3), (int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                    if (this.SHOW_REPORT) {
                                        CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3));
                                        CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3));
                                        CFG.reportData.lDefenders_ArmiesLost.add((int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                    }
                                    tempIDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3));
                                    tempArmies.add((int) (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) - Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy)));
                                    tempArmies_Lost.add((int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                }
                            }
                            for (int i3 = 0; i3 < tempIDs.size(); ++i3) {
                                CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(tempIDs.get(i3), tempArmies.get(i3));
                                CFG.game.getCiv(tempIDs.get(i3)).setNumOfUnits(CFG.game.getCiv(tempIDs.get(i3)).getNumOfUnits() - tempArmies_Lost.get(i3));
                            }
                            for (int i3 = 1; i3 < CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize(); ++i3) {
                                if (this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_IsDefender(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3))) {
                                    if ((int) CFG.game.getCivRelation_OfCivB(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3)) != -100 && !CFG.game.isAlly(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3))) {
                                        CFG.game.declareWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3), false);
                                    }
                                    CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3), (int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                    if (this.SHOW_REPORT) {
                                        CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3));
                                        CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3));
                                        CFG.reportData.lDefenders_ArmiesLost.add((int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                    }
                                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3), (int) (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) - Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy)));
                                    CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3)).getNumOfUnits() - (int) Math.floor(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i3) / (float) defendersArmy * attackersArmy));
                                    if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmyCivID(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i3)) == 0) {
                                        --i3;
                                        Gdx.app.log("AoC", "LOSS: 333A - 222-----");
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "LOSS: 333A END");
                        } else {
                            Gdx.app.log("AoC", "LOSS: 333B");
                            CFG.game.updateWarStatistics_Casualties(tempWarID, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(), attackersArmy);
                            if (this.SHOW_REPORT) {
                                CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID());
                                CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0));
                                CFG.reportData.lDefenders_ArmiesLost.add(attackersArmy);
                            }
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(0) - attackersArmy);
                            CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID()).getNumOfUnits() - attackersArmy);
                            Gdx.app.log("AoC", "LOSS: 333B END");
                        }
                    }
                    if (this.SHOW_REPORT) {
                        CFG.menuManager.rebuildInGame_Report();
                        this.SHOW_REPORT = false;
                    }
                    if (this.iPlayerAttack_ShowArmyInProvinceID >= 0 && this.iPlayerAttack_ShowArmyInProvinceID < CFG.game.getProvincesSize()) {
                        CFG.game.getProvince(this.iPlayerAttack_ShowArmyInProvinceID).updateFogOfWar(CFG.PLAYER_TURNID);
                    }
                } catch (final IndexOutOfBoundsException ex) {
                    CFG.exceptionStack(ex);
                } catch (final NullPointerException ex2) {
                    CFG.exceptionStack(ex2);
                }
            }
        } else if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getSeaProvince() && CFG.game.getSeaProvinceAttack(this.currentMoveUnits.getCivID(0), this.currentMoveUnits.getMoveUnits(0).getToProvinceID())) {
            Gdx.app.log("AoC", "SEA ATTACK");
            try {
                Gdx.app.log("AoC", "ATTACK: 111");
                if (this.SHOW_REPORT) {
                    CFG.reportData = new Report_Data();
                    CFG.reportData.iBattleOfProvinceID = this.currentMoveUnits.getMoveUnits(0).getToProvinceID();
                }
                Gdx.app.log("AoC", "ATTACK: 222");
                int tempNumOfUnits = 0;
                for (int k = 0; k < this.currentMoveUnits.getMoveUnitsSize(); ++k) {
                    tempNumOfUnits += this.currentMoveUnits.getMoveUnits(k).getNumOfUnits();
                }
                Gdx.app.log("AoC", "ATTACK: 333");
                if (this.SHOW_REPORT) {
                    CFG.reportData.iPopulationLosses = 0;
                    CFG.reportData.iEconomyLosses = 0;
                }
                int tempWarID2 = CFG.game.getWarID(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID());
                Gdx.app.log("AoC", "ATTACK: 444");
                if (this.turnMoves_MoveCurrentArmy_AttackResult_SEA(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), tempNumOfUnits, this.currentMoveUnits.getCivID(0))) {
                    final int defendersArmy2 = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_SEA(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), this.currentMoveUnits.getCivID(0));
                    final int attackersArmy2 = this.currentMoveUnits.getMoveUnits(0).getNumOfUnits();
                    if (this.SHOW_REPORT) {
                        CFG.reportData.attackersWon = true;
                        CFG.soundsManager.playSound(CFG.soundsManager.playMoveArmy());
                    }
                    for (int i5 = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() - 1; i5 >= 1; --i5) {
                        if (CFG.game.getCivsAtWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5))) {
                            if (this.SHOW_REPORT) {
                                tempWarID2 = CFG.game.getWarID(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5));
                                if (tempWarID2 >= 0) {
                                    CFG.game.updateWarStatistics_Casualties(tempWarID2, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i5));
                                    CFG.game.updateWarStatistics_Casualties(tempWarID2, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5), this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i5));
                                }
                                CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5));
                                CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i5));
                                CFG.reportData.lDefenders_ArmiesLost.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i5));
                            }
                            CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(i5)).getNumOfUnits() - CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(i5));
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).removeArmy_ID(i5);
                        }
                    }
                    int tempDefendersArmyLeft2 = defendersArmy2;
                    if (this.SHOW_REPORT) {
                        tempDefendersArmyLeft2 = defendersArmy2;
                        for (int i6 = 0; i6 < this.currentMoveUnits.getMoveUnitsSize(); ++i6) {
                            CFG.reportData.lAttackers_IDs.add(this.currentMoveUnits.getCivID(i6));
                            CFG.reportData.lAttackers_Armies.add(this.currentMoveUnits.getMoveUnits(i6).getNumOfUnits());
                            CFG.reportData.lAttackers_Armies_Lost.add((this.currentMoveUnits.getMoveUnits(i6).getNumOfUnits() > tempDefendersArmyLeft2) ? tempDefendersArmyLeft2 : this.currentMoveUnits.getMoveUnits(i6).getNumOfUnits());
                            tempDefendersArmyLeft2 -= this.currentMoveUnits.getMoveUnits(i6).getNumOfUnits();
                            if (tempDefendersArmyLeft2 < 0) {
                                tempDefendersArmyLeft2 = 0;
                            }
                        }
                    }
                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(0)) - this.currentMoveUnits.getMoveUnits(0).getNumOfUnits());
                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), attackersArmy2 - defendersArmy2);
                    CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).setNumOfUnits(CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getNumOfUnits() - defendersArmy2);
                } else {
                    final int defendersArmy2 = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_SEA(this.currentMoveUnits.getMoveUnits(0).getToProvinceID(), this.currentMoveUnits.getCivID(0));
                    final int attackersArmy2 = this.currentMoveUnits.getMoveUnits(0).getNumOfUnits();
                    if (this.SHOW_REPORT) {
                        CFG.reportData.attackersWon = false;
                        CFG.soundsManager.playSound(CFG.soundsManager.playMoveArmy());
                    }
                    int tempDefendersArmyLeft2 = attackersArmy2;
                    boolean firstCeil = true;
                    for (int l = CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivsSize() - 1; l >= 1; --l) {
                        if (CFG.game.getCivsAtWar(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l))) {
                            final float tempCurrentLosses = attackersArmy2 * (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l) / (float) defendersArmy2);
                            final int currentLosses = (int) (firstCeil ? Math.ceil(tempCurrentLosses) : Math.floor(tempCurrentLosses));
                            firstCeil = false;
                            if (this.SHOW_REPORT) {
                                tempWarID2 = CFG.game.getWarID(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l));
                                if (tempWarID2 >= 0) {
                                    CFG.game.updateWarStatistics_Casualties(tempWarID2, this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), currentLosses);
                                    CFG.game.updateWarStatistics_Casualties(tempWarID2, CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), this.currentMoveUnits.getCivID(0), currentLosses);
                                }
                                CFG.reportData.lDefenders_IDs.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l));
                                CFG.reportData.lDefenders_Armies.add(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l));
                                CFG.reportData.lDefenders_ArmiesLost.add(currentLosses);
                            }
                            CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l)).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l)).getNumOfUnits() - currentLosses);
                            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).updateArmy(CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getCivID(l), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getToProvinceID()).getArmy(l) - currentLosses);
                            tempDefendersArmyLeft2 -= currentLosses;
                            if (tempDefendersArmyLeft2 < 0) {
                                tempDefendersArmyLeft2 = 0;
                            }
                        }
                    }
                    if (this.SHOW_REPORT) {
                        tempDefendersArmyLeft2 = defendersArmy2;
                        for (int l = 0; l < this.currentMoveUnits.getMoveUnitsSize(); ++l) {
                            CFG.reportData.lAttackers_IDs.add(this.currentMoveUnits.getCivID(l));
                            CFG.reportData.lAttackers_Armies.add(this.currentMoveUnits.getMoveUnits(l).getNumOfUnits());
                            CFG.reportData.lAttackers_Armies_Lost.add(this.currentMoveUnits.getMoveUnits(l).getNumOfUnits());
                            tempDefendersArmyLeft2 -= this.currentMoveUnits.getMoveUnits(l).getNumOfUnits();
                            if (tempDefendersArmyLeft2 < 0) {
                                tempDefendersArmyLeft2 = 0;
                            }
                        }
                    }
                    CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(0), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(0).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(0)) - this.currentMoveUnits.getMoveUnits(0).getNumOfUnits());
                    CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).setNumOfUnits(CFG.game.getCiv(this.currentMoveUnits.getCivID(0)).getNumOfUnits() - attackersArmy2);
                }
                if (this.SHOW_REPORT) {
                    CFG.menuManager.rebuildInGame_Report();
                    this.SHOW_REPORT = false;
                }
                if (this.iPlayerAttack_ShowArmyInProvinceID >= 0 && this.iPlayerAttack_ShowArmyInProvinceID < CFG.game.getProvincesSize()) {
                    CFG.game.getProvince(this.iPlayerAttack_ShowArmyInProvinceID).updateFogOfWar(CFG.PLAYER_TURNID);
                }
            } catch (final IndexOutOfBoundsException ex) {
                CFG.exceptionStack(ex);
            } catch (final NullPointerException ex2) {
                CFG.exceptionStack(ex2);
            }
        } else {
            this.turnMoves_MoveCurrentArmy_JustMove();
        }
        this.currentMoveUnits = null;
    }

    private final void rollDices() {
        this.diceAggressors = CFG.oR.nextInt(725) % 6 + 1;
        this.diceDefenders = CFG.oR.nextInt(600) % 6 + 1;
    }

    protected final float diceRollBonus(boolean defenders) {
        int tDifference = defenders ? this.diceDefenders - this.diceAggressors : this.diceAggressors - this.diceDefenders;
        return tDifference > 0 ? 2.5F * (float) tDifference : 0.0F;
    }

    private final boolean turnMoves_MoveCurrentArmy_AttackResult(int toProvinceID, int numOfAttackers) {
        int numOfDefenders = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits(toProvinceID);
        float fDefensiveArmyModifiers = 1.0F;
        float fOffensiveArmyModifiers = 1.0F;
        fDefensiveArmyModifiers += this.turnMoves_MoveCurrentArmy_Attack_OffensiveModifiers(toProvinceID);
        fOffensiveArmyModifiers += this.turnMoves_MoveCurrentArmy_Attack_DefensiveModifiers(toProvinceID);
        if (fDefensiveArmyModifiers < 0.01F) {
            fDefensiveArmyModifiers = 0.01F;
        }

        if (fOffensiveArmyModifiers < 0.01F) {
            fOffensiveArmyModifiers = 0.01F;
        }

        return (float) numOfAttackers * fOffensiveArmyModifiers > (float) numOfDefenders * fDefensiveArmyModifiers;
    }

    private final boolean turnMoves_MoveCurrentArmy_AttackResult_SEA(int toProvinceID, int numOfAttackers, int attackersCivID) {
        int numOfDefenders = this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_SEA(toProvinceID, attackersCivID);
        return numOfAttackers > numOfDefenders;
    }

    private final int turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits(int toProvinceID) {
        int numOfDefenders = CFG.game.getProvince(toProvinceID).getArmy(0);

        for (int i = 1; i < CFG.game.getProvince(toProvinceID).getCivsSize(); ++i) {
            if (this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_IsDefender(toProvinceID, CFG.game.getProvince(toProvinceID).getCivID(i))) {
                numOfDefenders += CFG.game.getProvince(toProvinceID).getArmy(i);
            }
        }

        return numOfDefenders;
    }

    private final int turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_SEA(int toProvinceID, int attackersCivID) {
        int numOfDefenders = 0;

        for (int i = 1; i < CFG.game.getProvince(toProvinceID).getCivsSize(); ++i) {
            if (CFG.game.getCivsAtWar(CFG.game.getProvince(toProvinceID).getCivID(i), attackersCivID)) {
                numOfDefenders += CFG.game.getProvince(toProvinceID).getArmy(i);
            }
        }

        return numOfDefenders;
    }

    private final int turnMoves_MoveCurrentArmy_Attack_NumOfDefenders(int toProvinceID) {
        int numOfDefenders = 1;

        for (int i = 1; i < CFG.game.getProvince(toProvinceID).getCivsSize(); ++i) {
            if (this.turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_IsDefender(toProvinceID, CFG.game.getProvince(toProvinceID).getCivID(i))) {
                ++numOfDefenders;
            }
        }

        return numOfDefenders;
    }

    private final boolean turnMoves_MoveCurrentArmy_Attack_NumOfDefeningUnits_IsDefender(int toProvinceID, int nCivID) {
        return CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID(0)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID(0)).getAllianceID() == CFG.game.getCiv(nCivID).getAllianceID();
    }

    protected final float getDefenseBonusFromTechnology(int nCivID) {
        return nCivID > 0 ? Math.min(CFG.game.getCiv(nCivID).getTechnologyLevel() * 18.0F * 1.75F, 31.5F) : 0.0F;
    }

    protected final int moveArmyModifiers_Defenders(int fromProvinceID, int toProvinceID) {
        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() && CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getMilitaryAccess(CFG.game.getProvince(fromProvinceID).getCivID(), CFG.game.getProvince(toProvinceID).getCivID()) <= 0 && (CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getAllianceID())) {
                float fOut = (float) CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getIdeologyID()).DEFENSE_BONUS / 100.0F;
                fOut += (float) ((int) this.getDefenseBonusFromTechnology(CFG.game.getProvince(toProvinceID).getCivID())) / 100.0F;
                if (CFG.game.getProvince(toProvinceID).getIsCapital()) {
                    fOut += 0.15F;
                }

                fOut += (float) BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfFort()) / 100.0F;
                fOut += (float) BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfWatchTower()) / 100.0F;
                fOut += CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID());
                fOut += CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus();
                if (CFG.game.getProvince(toProvinceID).getIsNotSuppliedForXTurns() > 0) {
                    fOut -= this.getDefenseBonusLossPerTurnForNotSuppliedProvince(toProvinceID);
                }

                return (int) (fOut * 100.0F);
            } else {
                return 0;
            }
        } catch (IndexOutOfBoundsException var4) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var4);
            }

            return 0;
        }
    }

    protected final List getMoveArmyModifiers_Defenders_Hover(int fromProvinceID, int toProvinceID) {
        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
        List nData = new ArrayList();

        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() && CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getMilitaryAccess(CFG.game.getProvince(fromProvinceID).getCivID(), CFG.game.getProvince(toProvinceID).getCivID()) <= 0 && (CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getAllianceID())) {
                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BaseValue") + ": "));
                nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getIdeologyID()).DEFENSE_BONUS + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                nData.clear();
                if (CFG.game.getProvince(toProvinceID).getIsCapital()) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseOfTheCapital") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+15%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                int fTech = (int) this.getDefenseBonusFromTechnology(CFG.game.getProvince(toProvinceID).getCivID());
                if (fTech > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + fTech + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(toProvinceID).getCivID(), CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfFort()) > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getFort_Name(CFG.game.getProvince(toProvinceID).getLevelOfFort())) + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfFort()) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.b_fort, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfWatchTower()) > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getTower_Name(CFG.game.getProvince(toProvinceID).getLevelOfWatchTower())) + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfWatchTower()) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.b_tower, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus() != 0.0F) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Bonus") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus() > 0.0F ? "+" : "") + (int) (CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus() * 100.0F) + "%", CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus() > 0.0F ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(toProvinceID).getCivID(), CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) != 0.0F) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) > 0.0F ? "+" : "") + (int) (CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) * 100.0F) + "%", CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) > 0.0F ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(toProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (CFG.game.getProvince(toProvinceID).getIsNotSuppliedForXTurns() > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvinceIsNotSupplied") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int) (this.getDefenseBonusLossPerTurnForNotSuppliedProvince(toProvinceID) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.difficulty_hell, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
        } catch (IndexOutOfBoundsException var6) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var6);
            }
        }

        return nElements;
    }

    protected final float getAttackersBonusFromTechnology(int nCivID) {
        return Math.min(CFG.game.getCiv(nCivID).getTechnologyLevel() * 18.0F, 18.0F);
    }

    protected final int moveArmyModifiers_Attackers(int fromProvinceID, int toProvinceID, int iCivID) {
        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() && CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getMilitaryAccess(CFG.game.getProvince(fromProvinceID).getCivID(), CFG.game.getProvince(toProvinceID).getCivID()) <= 0 && (CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getAllianceID())) {
                float fOut = 0.0F;
                if (CFG.game.getProvince(fromProvinceID).getIsCapital()) {
                    fOut += 0.1F;
                }

                fOut += this.getAttackersBonusFromTechnology(iCivID) / 100.0F;
                fOut += CFG.game.getCiv(iCivID).getModifier_AttackBonus();
                return (int) (fOut * 100.0F);
            } else {
                return 0;
            }
        } catch (IndexOutOfBoundsException var5) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var5);
            }

            return 0;
        }
    }

    protected final List getMoveArmyModifiers_Attackers_Hover(int fromProvinceID, int toProvinceID, int iCivID) {
        List nElements = new ArrayList();
        List nData = new ArrayList();

        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getProvince(fromProvinceID).getCivID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() && CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getMilitaryAccess(CFG.game.getProvince(fromProvinceID).getCivID(), CFG.game.getProvince(toProvinceID).getCivID()) <= 0 && (CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(fromProvinceID).getCivID()).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getAllianceID())) {
                if (CFG.game.getProvince(fromProvinceID).getIsCapital()) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AttackFromCapital") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+10%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                int fTech = (int) this.getAttackersBonusFromTechnology(iCivID);
                if (fTech > 0) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + fTech + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }

                if (CFG.game.getCiv(iCivID).getModifier_AttackBonus() != 0.0F) {
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Bonus") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text((CFG.game.getCiv(iCivID).getModifier_AttackBonus() > 0.0F ? "+" : "") + (int) (CFG.game.getCiv(iCivID).getModifier_AttackBonus() * 100.0F) + "%", CFG.game.getCiv(iCivID).getModifier_AttackBonus() > 0.0F ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID, CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                }
            }
        } catch (IndexOutOfBoundsException var7) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var7);
            }
        }

        return nElements;
    }

    protected final float turnMoves_MoveCurrentArmy_Attack_OffensiveModifiers(int toProvinceID) {
        float fOffensiveArmyModifiers = (float) CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getIdeologyID()).DEFENSE_BONUS / 100.0F;
        if (CFG.game.getProvince(toProvinceID).getIsCapital()) {
            fOffensiveArmyModifiers += 0.15F;
        }

        fOffensiveArmyModifiers += (float) BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfFort()) / 100.0F;
        fOffensiveArmyModifiers += this.diceRollBonus(true) / 100.0F;
        fOffensiveArmyModifiers += (float) BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(toProvinceID).getLevelOfWatchTower()) / 100.0F;
        if (CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) > 0.0F) {
            fOffensiveArmyModifiers += CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID());
        }

        fOffensiveArmyModifiers += this.getDefenseBonusFromTechnology(CFG.game.getProvince(toProvinceID).getCivID()) / 100.0F;
        fOffensiveArmyModifiers += CFG.game.getCiv(CFG.game.getProvince(toProvinceID).getCivID()).getModifier_DefenseBonus();
        return fOffensiveArmyModifiers;
    }

    protected final float getDefenseBonusLossPerTurnForNotSuppliedProvince(int toProvinceID) {
        return Math.min(0.1F * (float) CFG.game.getProvince(toProvinceID).getIsNotSuppliedForXTurns(), 0.85F);
    }

    protected final float turnMoves_MoveCurrentArmy_Attack_DefensiveModifiers(int toProvinceID) {
        float fDefensiveArmyModifiers = 0.0F;
        if (CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID()) < 0.0F) {
            fDefensiveArmyModifiers += CFG.terrainTypesManager.getDefense(CFG.game.getProvince(toProvinceID).getTerrainTypeID());
        }

        if (CFG.game.getProvince(toProvinceID).getIsNotSuppliedForXTurns() > 0) {
            fDefensiveArmyModifiers += this.getDefenseBonusLossPerTurnForNotSuppliedProvince(toProvinceID);
        }

        fDefensiveArmyModifiers += this.diceRollBonus(false) / 100.0F;

        for (int i = 0; i < this.currentMoveUnits.getMoveUnitsSize(); ++i) {
            if (CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i).getFromProvinceID()).getIsCapital()) {
                fDefensiveArmyModifiers += 0.1F;
                break;
            }
        }

        float fBest = 0.0F;

        for (int i = 0; i < this.currentMoveUnits.getMoveUnitsSize(); ++i) {
            if (CFG.game.getCiv(this.currentMoveUnits.getCivID(i)).getModifier_AttackBonus() > fBest) {
                fBest = CFG.game.getCiv(this.currentMoveUnits.getCivID(i)).getModifier_AttackBonus();
            }
        }

        fDefensiveArmyModifiers += fBest;
        fBest = 0.0F;

        for (int i = 0; i < this.currentMoveUnits.getMoveUnitsSize(); ++i) {
            if (this.getAttackersBonusFromTechnology(this.currentMoveUnits.getCivID(i)) / 100.0F > fBest) {
                fBest = this.getAttackersBonusFromTechnology(this.currentMoveUnits.getCivID(i)) / 100.0F;
            }
        }

        fDefensiveArmyModifiers += fBest;
        return fDefensiveArmyModifiers;
    }

    private final void turnMoves_MoveCurrentArmy_JustMove() {
        for (int i = 0; i < this.currentMoveUnits.getMoveUnitsSize(); ++i) {
            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i).getFromProvinceID()).updateArmy(this.currentMoveUnits.getCivID(i), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i).getFromProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(i)) - this.currentMoveUnits.getMoveUnits(i).getNumOfUnits());
            CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i).getToProvinceID()).updateArmy(this.currentMoveUnits.getCivID(i), CFG.game.getProvince(this.currentMoveUnits.getMoveUnits(i).getToProvinceID()).getArmyCivID(this.currentMoveUnits.getCivID(i)) + this.currentMoveUnits.getMoveUnits(i).getNumOfUnits());
        }

    }

    protected final void loadActivePlayerData() {
        Gdx.app.log("AoC", "loadActivePlayerData: 00000");
        if (CFG.FOG_OF_WAR > 0) {
            if (CFG.FOG_OF_WAR == 2) {
                for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                    CFG.game.getProvince(i).updateProvinceBorder();
                }

                Game_Render.updateDrawCivRegionNames_FogOfWar();
            }

            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                CFG.game.getProvince(i).updateDrawArmy();
            }
        }

        Gdx.app.log("AoC", "loadActivePlayerData: 1111");
        CFG.menuManager.rebuildInGame_Messages();
        Gdx.app.log("AoC", "loadActivePlayerData: 222");
        CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);
        Gdx.app.log("AoC", "loadActivePlayerData: 333");
        CFG.game.buildMoveUnits_JustDraw_AnotherArmies();
        Gdx.app.log("AoC", "loadActivePlayerData: 444");

        try {
            if (!CFG.SPECTATOR_MODE && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() == 0 && this.showDefeatView(CFG.PLAYER_TURNID) && !gameEnded) {
                CFG.menuManager.setViewID(Menu.eDEFEAT);
                CFG.map.getMapBG().updateWorldMap_Shaders();
                CFG.toast.setInView(CFG.langManager.get("Defeat"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                gameEnded = true;
            } else if (CFG.settingsManager.CONFIRM_NEXT_PLAYER_TURN) {
                CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                if ((!RTS.isEnabled() || RTS.PAUSE) && !CFG.SPECTATOR_MODE && this.showNextPlayerTurnView_NextTurn()) {
                    CFG.menuManager.setViewIDWithoutAnimation(Menu.eNEXT_PLAYER_TURN);
                    CFG.game.enableDrawCivilizationRegions(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), 0);
                    CFG.map.getMapBG().updateWorldMap_Shaders();
                } else {
                    Menu_NextPlayerTurn.clickEnd();
                }

                Menu_InGame_Messages.START_ANIMATION = true;
            }
        } catch (IndexOutOfBoundsException var2) {
            Menu_NextPlayerTurn.clickEnd();
        } catch (NullPointerException var3) {
            Menu_NextPlayerTurn.clickEnd();
        } catch (StackOverflowError var4) {
            Menu_NextPlayerTurn.clickEnd();
        } catch (ArithmeticException var5) {
            Menu_NextPlayerTurn.clickEnd();
        }

        Gdx.app.log("AoC", "loadActivePlayerData: END");
    }

    protected final void checkGameEnd() {
        if (!CFG.SPECTATOR_MODE && !gameEnded) {
            for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                int numOfProvinces = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces();
                Gdx.app.log("AoC", "checkGameEnd: numOfProvinces1: " + numOfProvinces);

                for (int z = 0; z < CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.iVassalsSize; ++z) {
                    numOfProvinces += CFG.game.getCiv(((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.lVassals.get(z)).iCivID).getNumOfProvinces();
                }

                Gdx.app.log("AoC", "checkGameEnd: numOfProvinces2: " + numOfProvinces);
                if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID() > 0) {
                    for (int z = 0; z < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID()).getCivilizationsSize(); ++z) {
                        if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID()).getCivilization(z) != CFG.game.getPlayer(i).getCivID() && CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID()).getCivilization(z)).getPuppetOfCivID() != CFG.game.getPlayer(i).getCivID()) {
                            numOfProvinces += CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAllianceID()).getCivilization(z)).getNumOfProvinces();
                        }
                    }
                }

                Gdx.app.log("AoC", "checkGameEnd: numOfProvinces3: " + numOfProvinces);
                Gdx.app.log("AoC", "checkGameEnd: VIC CFG.oAI.PLAYABLE_PROVINCES: " + CFG.oAI.PLAYABLE_PROVINCES);
                Gdx.app.log("AoC", "checkGameEnd: CFG.oAI.NUM_OF_CIVS_IN_THE_GAME: " + CFG.oAI.NUM_OF_CIVS_IN_THE_GAME);
                if (VicotryManager.VICTORY_LIMIT_OF_TURNS != 0 && VicotryManager.VICTORY_LIMIT_OF_TURNS < Game_Calendar.TURN_ID) {
                    Gdx.app.log("AoC", "checkGameEnd: VIC 0000");
                    CFG.menuManager.setViewID(Menu.eVICTORY);
                    CFG.map.getMapBG().updateWorldMap_Shaders();
                    CFG.toast.setInView("TurnsLimit", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                    CFG.toast.setTimeInView(4500);
                    gameEnded = true;
                } else if (CFG.oAI.PLAYABLE_PROVINCES > numOfProvinces && !((float) CFG.oAI.PLAYABLE_PROVINCES <= (float) numOfProvinces * ((float) VicotryManager.VICTORY_CONTROL_PROVINCES_PERC / 100.0F)) && CFG.oAI.NUM_OF_CIVS_IN_THE_GAME >= 2) {
                    Gdx.app.log("AoC", "checkGameEnd: VIC 2222");
                    if (VicotryManager.VICTORY_TECHNOLOGY > 0.0F) {
                        for (int z = 1; z < CFG.game.getCivsSize(); ++z) {
                            Gdx.app.log("AoC", "checkGameEnd: VIC 222: CIV: " + CFG.game.getCiv(z).getCivName());
                            Gdx.app.log("AoC", "checkGameEnd: VIC 222: CFG.game.getCiv(z).getTechnologyLevel(): " + CFG.game.getCiv(z).getTechnologyLevel());
                            Gdx.app.log("AoC", "checkGameEnd: VIC 222:  VicotryManager.VICTORY_TECHNOLOGY: " + VicotryManager.VICTORY_TECHNOLOGY);
                            if (CFG.game.getCiv(z).getNumOfProvinces() > 0 && CFG.game.getCiv(z).getTechnologyLevel() >= VicotryManager.VICTORY_TECHNOLOGY) {
                                if (CFG.game.getCiv(z).getControlledByPlayer()) {
                                    CFG.menuManager.setViewID(Menu.eVICTORY);
                                    CFG.map.getMapBG().updateWorldMap_Shaders();
                                    CFG.toast.setInView("Technology: " + VicotryManager.VICTORY_TECHNOLOGY, CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                                    CFG.toast.setTimeInView(4500);
                                    gameEnded = true;
                                } else {
                                    CFG.menuManager.setViewID(Menu.eDEFEAT);
                                    CFG.map.getMapBG().updateWorldMap_Shaders();
                                    CFG.toast.setInView("Technology: " + VicotryManager.VICTORY_TECHNOLOGY, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                                    CFG.toast.setTimeInView(4500);
                                    gameEnded = true;
                                }
                            }
                        }
                    }
                } else {
                    Gdx.app.log("AoC", "checkGameEnd: VIC 1111");
                    CFG.menuManager.setViewID(Menu.eVICTORY);
                    CFG.map.getMapBG().updateWorldMap_Shaders();
                    gameEnded = true;
                }
            }
        }

    }

    protected final boolean showDefeatView(int nPlayerID) {
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0 && CFG.game.getProvince(i).getTrueOwnerOfProvince() == CFG.game.getPlayer(nPlayerID).getCivID()) {
                return false;
            }
        }

        if (!CFG.game.getPlayer(nPlayerID).savePlayer.lostNextTurn) {
            CFG.game.getPlayer(nPlayerID).savePlayer.lostNextTurn = true;
            return false;
        } else {
            return true;
        }
    }

    protected final boolean showNextPlayerTurnView() {
        return CFG.settingsManager.showNextPlayerView || SaveManager.gameWillBeSavedInThisTurn() || this.getNumOfPlayersInGame() > 1;
    }

    protected final boolean showNextPlayerTurnView_NextTurn() {
        return CFG.settingsManager.showNextPlayerView || SaveManager.forceShowNextPlayerTurnView || this.getNumOfPlayersInGame() > 1;
    }

    protected int getNumOfPlayersInGame() {
        int out = 0;

        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces() > 0) {
                ++out;
            }
        }

        return out;
    }

    protected final void buildFogOfWar(int nPlayerID) {
        try {
            for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                CFG.game.getPlayer(nPlayerID).setFogOfWar(i, false);
            }

            this.buildFogOfWar_CivID(nPlayerID, CFG.game.getPlayer(nPlayerID).getCivID());
            if (CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID() > 0) {
                for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID()).getCivilizationsSize(); ++i) {
                    if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID()).getCivilization(i) != CFG.game.getPlayer(nPlayerID).getCivID()) {
                        this.buildFogOfWar_CivID(nPlayerID, CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getAllianceID()).getCivilization(i));
                    }
                }
            }

            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (i != CFG.game.getPlayer(nPlayerID).getCivID() && CFG.game.getCiv(i).getPuppetOfCivID() == CFG.game.getPlayer(nPlayerID).getCivID()) {
                    this.buildFogOfWar_CivID(nPlayerID, i);
                }
            }

            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (i != CFG.game.getPlayer(nPlayerID).getCivID() && CFG.game.getCiv(CFG.game.getPlayer(nPlayerID).getCivID()).getPuppetOfCivID() == i) {
                    this.buildFogOfWar_CivID(nPlayerID, i);
                }
            }
        } catch (IndexOutOfBoundsException var3) {
            CFG.exceptionStack(var3);
        }

    }

    protected final void buildFogOfWar_CivID(int nPlayerID, int nCivID) {
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getCiv(nCivID).getProvinceID(i), true);

            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvincesSize(); ++j) {
                CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j), true);
            }

            this.buildFogOfWar_WatchTower(nPlayerID, CFG.game.getCiv(nCivID).getProvinceID(i));
        }

        for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
            CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), true);
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getSeaProvince()) {
                for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getNeighboringProvincesSize(); ++j) {
                    if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getNeighboringProvinces(j)).getSeaProvince()) {
                        CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getNeighboringProvinces(j), true);
                    }
                }
            }
        }

    }

    protected final void buildFogOfWar_WatchTower(int nPlayerID, int nProvinceID) {
        if (CFG.game.getProvince(nProvinceID).getLevelOfWatchTower() > 0) {
            if (CFG.game.getProvince(nProvinceID).getLevelOfWatchTower() == 1) {
                for (int j = 0; j < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++j) {
                    if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getLevelOfFort() < 1) {
                        CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j), true);
                    }
                }
            } else {
                for (int j = 0; j < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++j) {
                    if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getLevelOfFort() < 1) {
                        CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j), true);

                        for (int k = 0; k < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getNeighboringProvincesSize(); ++k) {
                            if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getNeighboringProvinces(k)).getLevelOfFort() < 1) {
                                CFG.game.getPlayer(nPlayerID).setFogOfWar(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getNeighboringProvinces(k), true);
                            }
                        }
                    }
                }
            }
        }

    }

    protected final boolean hasArmyInProvince(int nProvinceID, int nCivID) {
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCivsSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getCivID(i) == nCivID) {
                if (CFG.game.getProvince(nProvinceID).getArmy(i) > 0) {
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    protected final boolean hasArmyInProvince_AllianceID(int nProvinceID, int nAllianceID) {
        if (nAllianceID == 0) {
            return false;
        } else {
            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCivsSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID(i)).getAllianceID() == nAllianceID) {
                    return true;
                }
            }

            return false;
        }
    }

    protected final boolean isMovingArmyFromProvince(int nProvinceID) {
        return this.isMovingArmyFromProvince(nProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
    }

    protected final boolean isMovingArmyFromProvince(int nProvinceID, int nCivID) {
        for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsSize(); ++i) {
            if (CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID() == nProvinceID) {
                return true;
            }
        }

        return false;
    }

    protected final boolean controlsArmyInProvince(int nProvinceID) {
        return this.controlsArmyInProvince(nProvinceID, CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
    }

    protected final boolean controlsArmyInProvince(int nProvinceID, int nCivID) {
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCivsSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getCivID(i) == nCivID && CFG.game.getProvince(nProvinceID).getArmy(i) > 0) {
                CFG.activeCivilizationArmyID = i;
                return true;
            }
        }

        CFG.activeCivilizationArmyID = 0;
        return false;
    }

    protected final boolean canColonizieWasteland_Tech(int nProvinceID, int nCivID) {
        if (!Game_Calendar.getColonizationOfWastelandIsEnabled()) {
            return false;
        } else {
            return Game_Calendar.getCanColonize_TechLevel(nCivID);
        }
    }

    protected final boolean canColonizieNeutral_Tech(int nProvinceID, int nCivID) {
        return Game_Calendar.getCanColonize_TechLevel(nCivID);
    }

    protected final boolean canColonizieWasteland_BorderOrArmy(int nProvinceID, int nCivID) {
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                    return true;
                }

                for (int j = 0; j < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivsSize(); ++j) {
                    if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(j) == nCivID) {
                        return true;
                    }
                }
            }
        }

        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ++i) {
            for (int j = 1; j < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getCivsSize(); ++j) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getCivID(j) == nCivID) {
                    return true;
                }
            }
        }

        return false;
    }

    protected final void resetTurnData() {
        if (TurnStates.INPUT_ORDERS == this.activeTurnAction) {
            CFG.game.getPlayer(CFG.PLAYER_TURNID).iBefore_ActiveProvince = CFG.game.getActiveProvinceID();
        }

        if (CFG.chooseProvinceMode) {
            CFG.game.resetChooseProvinceData();
        }

        if (CFG.regroupArmyMode) {
            CFG.game.resetRegroupArmyData();
        }

    }

    protected final void hideAllProvinceActionViews() {
        CFG.menuManager.setVisible_InGame_ActionInfo(false);
        CFG.menuManager.setVisible_InGame_ProvinceAction(false);
        CFG.menuManager.setVisible_InGame_ProvinceMoveUnits(false);
        CFG.menuManager.setVisible_InGame_ProvinceRecruit(false);
        CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(false);
        CFG.menuManager.setVisible_InGame_ProvinceRegroupArmy(false);
        CFG.menuManager.setVisible_InGame_ProvinceDisband(false);
        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize(false);
        CFG.menuManager.setVisible_InGame_ProvinceAction_Colonize_TechRequired(false);
    }

    protected final void hideAllViews() {
        this.hideAllProvinceActionViews();
        CFG.menuManager.updateInGameRTO(false);
        if (CFG.menuManager.getColorPicker().getVisible()) {
            CFG.menuManager.getColorPicker().setVisible(false, (ColorPicker_AoC.PickerAction) null);
        }

    }

    protected final boolean canMigrate_MovementPoints(int iCivID) {
        return CFG.game.getCiv(iCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).COST_OF_MOVE;
    }

    protected final boolean migrateToProvince(int fromProvinceID, int toProvinceID, int iCivID, boolean buildLine) {
        if (!this.canMigrate_MovementPoints(iCivID)) {
            return false;
        } else {
            Game var10000 = CFG.game;
            if (!Game.uncivilizedCanMigrate_FromProvince(fromProvinceID, iCivID)) {
                return false;
            } else if (CFG.game.getCiv(iCivID).migratesFromProvinceID(fromProvinceID)) {
                return false;
            } else {
                CFG.game.getCiv(iCivID).newMigrate(fromProvinceID, toProvinceID, buildLine);
                CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).COST_OF_MOVE);
                return true;
            }
        }
    }

    protected final boolean moveArmy(int fromProvinceID, int toProvinceID, int nNumOfUnits, int iCivID, boolean regroupMode, boolean buildLine) {
        try {
            if (nNumOfUnits == 0) {
                for (int i = 0; i < CFG.game.getCiv(iCivID).getMoveUnitsSize(); ++i) {
                    if (CFG.game.getCiv(iCivID).getMoveUnits(i).getFromProvinceID() == fromProvinceID && CFG.game.getCiv(iCivID).getMoveUnits(i).getToProvinceID() == toProvinceID) {
                        CFG.game.getProvince(fromProvinceID).updateArmy(iCivID, CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID) + CFG.game.getCiv(iCivID).getMoveUnits(i).getNumOfUnits());
                        CFG.game.getCiv(iCivID).removeMove(i);

                        for (int j = 0; j < CFG.game.getCiv(iCivID).getRegroupArmySize(); ++j) {
                            if (CFG.game.getCiv(iCivID).getRegroupArmy(j).getFromProvinceID() == toProvinceID) {
                                CFG.game.getCiv(iCivID).removeRegroupArmy(j--);
                            }
                        }

                        CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() + this.costOfMoveArmy(fromProvinceID, toProvinceID, iCivID));
                        return false;
                    }
                }

                return false;
            } else {
                for (int i = 0; i < CFG.game.getCiv(iCivID).getMoveUnitsSize(); ++i) {
                    if (CFG.game.getCiv(iCivID).getMoveUnits(i).getFromProvinceID() == fromProvinceID && CFG.game.getCiv(iCivID).getMoveUnits(i).getToProvinceID() == toProvinceID) {
                        if (regroupMode) {
                            if (CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID) < nNumOfUnits) {
                                nNumOfUnits = CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID);
                            }

                            CFG.game.getProvince(fromProvinceID).updateArmy(iCivID, CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID) - nNumOfUnits);
                            CFG.game.getCiv(iCivID).getMoveUnits(i).setNumOfUnits(CFG.game.getCiv(iCivID).getMoveUnits(i).getNumOfUnits() + nNumOfUnits);
                        } else {
                            CFG.game.getProvince(fromProvinceID).updateArmy(iCivID, CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID) - (nNumOfUnits - CFG.game.getCiv(iCivID).getMoveUnits(i).getNumOfUnits()));
                            CFG.game.getCiv(iCivID).getMoveUnits(i).setNumOfUnits(nNumOfUnits);
                        }

                        return true;
                    }
                }

                if (CFG.game.getCiv(iCivID).getMovePoints() < this.costOfMoveArmy(fromProvinceID, toProvinceID, iCivID)) {
                    return false;
                } else if (!CFG.game.getProvince(fromProvinceID).getSeaProvince() && CFG.game.getProvince(toProvinceID).getSeaProvince() && CFG.game.getProvince(fromProvinceID).getLevelOfPort() < 1) {
                    return false;
                } else {
                    if (nNumOfUnits > CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID)) {
                        nNumOfUnits = CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID);
                    }

                    if (nNumOfUnits <= 0) {
                        return false;
                    } else {
                        CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() - this.costOfMoveArmy(fromProvinceID, toProvinceID, iCivID));
                        CFG.game.getCiv(iCivID).newMove(fromProvinceID, toProvinceID, nNumOfUnits, buildLine);
                        CFG.game.getProvince(fromProvinceID).updateArmy(iCivID, CFG.game.getProvince(fromProvinceID).getArmyCivID(iCivID) - nNumOfUnits);
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException var9) {
            CFG.exceptionStack(var9);
            return false;
        }
    }

    protected final int costOfMoveArmy(int fromProvinceID, int toProvinceID, int nCivID) {
        try {
            if (CFG.game.getProvince(fromProvinceID).getCivID() > 0 && CFG.game.getProvince(toProvinceID).getCivID() > 0 && CFG.game.getProvince(fromProvinceID).getCivID() == CFG.game.getProvince(toProvinceID).getCivID() && CFG.game.getProvince(fromProvinceID).getCivID() == nCivID) {
                return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE;
            } else if (CFG.game.getProvince(fromProvinceID).getSeaProvince()) {
                return CFG.game.getProvince(toProvinceID).getSeaProvince() ? (int) ((float) CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * 1.5F) : CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * 2;
            } else {
                for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsSize(); ++i) {
                    if (CFG.game.getCiv(nCivID).getMoveUnits(i).getToProvinceID() == toProvinceID) {
                        return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_SAME_PROVINCE;
                    }
                }

                return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            }
        } catch (IndexOutOfBoundsException var5) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var5);
            }

            return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
        }
    }

    protected final boolean getIsFreeMove(int iCivID, int fromProvinceID, int toProvinceID) {
        for (int i = 0; i < CFG.game.getCiv(iCivID).getMoveUnitsSize(); ++i) {
            if (CFG.game.getCiv(iCivID).getMoveUnits(i).getFromProvinceID() == fromProvinceID && CFG.game.getCiv(iCivID).getMoveUnits(i).getToProvinceID() == toProvinceID) {
                return true;
            }
        }

        return false;
    }

    protected final void updatePopulationLosses(int nProvinceID, int iLosses) {
        int nRecuritedPop = CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation();

        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == 0) {
                if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) iLosses * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                    --i;
                }
            } else if (CFG.game.getProvince(nProvinceID).getCivID() == CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) {
                if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.ceil((double) ((float) iLosses * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                    --i;
                }
            } else if ((int) CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(nProvinceID).getCivID(), CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) == -100) {
                if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) iLosses * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                    --i;
                }
            } else if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)).getAllianceID()) {
                if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) iLosses * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                    --i;
                }
            } else if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) iLosses * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                --i;
            }
        }

        nRecuritedPop -= CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation();
        if (nRecuritedPop < iLosses) {
            nRecuritedPop = iLosses - nRecuritedPop;
            int i = 0;

            for (int tPop = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
                tPop = Math.min(nRecuritedPop, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i));
                if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.min(nRecuritedPop, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i)))) {
                    --i;
                }

                nRecuritedPop -= tPop;
                if (nRecuritedPop <= 0) {
                    break;
                }
            }
        }

    }

    protected final void recruitArmyInstantly(int nProvinceID, int nNumOfUnits, int nCivID) {
        if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT) {
            if ((long) nNumOfUnits >= CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getMoney() / (long) CFG.getCostOfRecruitArmyMoney_Instantly(nProvinceID)) {
                nNumOfUnits = (int) CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getMoney() / CFG.getCostOfRecruitArmyMoney_Instantly(nProvinceID);
            }

            if (nNumOfUnits >= this.getRecruitableArmy(nProvinceID)) {
                nNumOfUnits = this.getRecruitableArmy(nProvinceID);
            }

            if (nNumOfUnits > 0) {
                CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT);
                CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - (long) (nNumOfUnits * CFG.getCostOfRecruitArmyMoney_Instantly(nProvinceID)));
                this.recruitArmy(nProvinceID, nNumOfUnits, nCivID);
            }

        }
    }

    protected final void recruitArmy(int nProvinceID, int nNumOfUnits, int nCivID) {
        if (nNumOfUnits >= this.getRecruitableArmy(nProvinceID)) {
            nNumOfUnits = this.getRecruitableArmy(nProvinceID);
        }

        if (nNumOfUnits > 0) {
            int tempProvincePopulation = CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation();
            CFG.game.getProvince(nProvinceID).setHappiness(CFG.game.getProvince(nProvinceID).getHappiness() - 0.1375F * ((float) nNumOfUnits / (float) tempProvincePopulation));
            CFG.game.getProvince(nProvinceID).setEconomy((int) ((float) CFG.game.getProvince(nProvinceID).getEconomy() - (float) CFG.game.getProvince(nProvinceID).getEconomy() * (CFG.game.getProvince(nProvinceID).getIsCapital() ? 0.2875F : 0.575F + (float) CFG.oR.nextInt(175) / 1000.0F) * ((float) nNumOfUnits / (float) tempProvincePopulation)));
            CFG.game.getProvince(nProvinceID).setDevelopmentLevel(CFG.game.getProvince(nProvinceID).getDevelopmentLevel() - CFG.game.getProvince(nProvinceID).getDevelopmentLevel() * (CFG.game.getProvince(nProvinceID).getIsCapital() ? 0.08125F : 0.1625F + (float) CFG.oR.nextInt(125) / 1000.0F) * ((float) nNumOfUnits / (float) tempProvincePopulation));
            CFG.game.getProvince(nProvinceID).updateArmy(nCivID, CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) + Math.max(nNumOfUnits, 0));
            Save_Civ_GameData var10000 = CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).civGameData;
            var10000.iRecruitedArmy += Math.max(nNumOfUnits, 0);
            int nRecuritedPop;
            if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getControlledByPlayer()) {
                nRecuritedPop = CFG.game.getPlayerID_ByCivID(CFG.game.getProvince(nProvinceID).getCivID());

                try {
                    CFG.game.getPlayer(nRecuritedPop).statistics_Civ_GameData.setRecruitedArmy(CFG.game.getPlayer(nRecuritedPop).statistics_Civ_GameData.getRecruitedArmy() + Math.max(nNumOfUnits, 0));
                } catch (IndexOutOfBoundsException | NullPointerException var8) {
                    CFG.exceptionStack(var8);
                }
            }

            CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getNumOfUnits() + nNumOfUnits);
            nRecuritedPop = tempProvincePopulation;

            for (int i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
                if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == 0) {
                    if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) nNumOfUnits * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                        --i;
                        Gdx.app.log("GameAction", "recruit--1");
                    }
                } else if (CFG.game.getProvince(nProvinceID).getCivID() == CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) {
                    if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.ceil((double) ((float) nNumOfUnits * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                        --i;
                        Gdx.app.log("GameAction", "recruit--2");
                    }
                } else if ((int) CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(nProvinceID).getCivID(), CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) == -100) {
                    if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) nNumOfUnits * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                        --i;
                        Gdx.app.log("GameAction", "recruit--3");
                    }
                } else if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)).getAllianceID()) {
                    if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) nNumOfUnits * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                        --i;
                        Gdx.app.log("GameAction", "recruit--4");
                    }
                } else if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((double) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.floor((double) ((float) nNumOfUnits * ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) / (float) nRecuritedPop)))))) {
                    --i;
                    Gdx.app.log("GameAction", "recruit--5");
                }
            }

            nRecuritedPop -= CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation();
            if (nRecuritedPop < nNumOfUnits) {
                nRecuritedPop = nNumOfUnits - nRecuritedPop;
                int i = 0;

                for (int tPop = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
                    tPop = Math.min(nRecuritedPop, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i));
                    if (CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - Math.min(nRecuritedPop, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i)))) {
                        --i;
                    }

                    nRecuritedPop -= tPop;
                    if (nRecuritedPop <= 0) {
                        break;
                    }
                }
            }
        }

    }

    protected final int getRecruitableArmy(int nProvinceID) {
        return this.getRecruitableArmy(nProvinceID, CFG.game.getProvince(nProvinceID).getCivID());
    }

    protected final int getRecruitableArmy(int nProvinceID, int nCivID) {
        int nOut = 0;

        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == 0) {
                nOut = (int) ((float) nOut + (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.0675F);
            } else if (nCivID == CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) {
                nOut = (int) ((float) nOut + (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.175F);
            } else if ((int) CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) == -100) {
                nOut = (int) ((float) nOut + (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.0025F);
            } else if (CFG.game.getCiv(nCivID).getAllianceID() > 0 && CFG.game.getCiv(nCivID).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)).getAllianceID()) {
                nOut = (int) ((float) nOut + (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.125F);
            } else {
                nOut = (int) ((float) nOut + (float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * 0.00725F);
            }
        }

        //add recruitable puppet civs
        for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.iVassalsSize; i++) {
            try {
                if (!CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).autonomyStatus.isMilitaryControl()) {
                    nOut += getRecruitableArmy(nProvinceID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID);
                }
            } catch (IndexOutOfBoundsException | NullPointerException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
        }
        return nOut;
    }

    protected final void updateRecruitSlider() {
        try {
            int tMaxRecruit = (int) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() / CFG.getCostOfRecruitArmyMoney(CFG.game.getActiveProvinceID());
            if (tMaxRecruit < 0) {
                tMaxRecruit = 0;
            } else if (tMaxRecruit > this.getRecruitableArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                tMaxRecruit = this.getRecruitableArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            }

            int isRecruiting = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isRecruitingArmyInProvinceID(CFG.game.getActiveProvinceID());
            if (isRecruiting >= 0) {
                tMaxRecruit += CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(isRecruiting).getArmy();
                if (tMaxRecruit > this.getRecruitableArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                    tMaxRecruit = this.getRecruitableArmy(CFG.game.getActiveProvinceID(), CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                }

                CFG.menuManager.getInGame_ProvinceRecruit_Slider().setMax(tMaxRecruit);
                CFG.menuManager.getInGame_ProvinceRecruit_Slider().setCurrent(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRecruitArmy(isRecruiting).getArmy());
            } else {
                CFG.menuManager.getInGame_ProvinceRecruit_Slider().setMax(tMaxRecruit);
                CFG.menuManager.getInGame_ProvinceRecruit_Slider().setCurrent(tMaxRecruit);
            }
        } catch (IndexOutOfBoundsException var3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var3);
            }

            CFG.menuManager.getInGame_ProvinceRecruit_Slider().setMax(0);
            CFG.menuManager.getInGame_ProvinceRecruit_Slider().setCurrent(0);
        }

    }

    protected final void updateRecruitSlider_Instantly() {
        try {
            int tMaxRecruit = (int) CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getMoney() / CFG.getCostOfRecruitArmyMoney_Instantly(CFG.game.getActiveProvinceID());
            if (tMaxRecruit < 0) {
                tMaxRecruit = 0;
            } else if (tMaxRecruit > this.getRecruitableArmy(CFG.game.getActiveProvinceID())) {
                tMaxRecruit = this.getRecruitableArmy(CFG.game.getActiveProvinceID());
            }

            CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setMax(tMaxRecruit);
            CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setCurrent(tMaxRecruit);
        } catch (IndexOutOfBoundsException var2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(var2);
            }

            CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setMax(0);
            CFG.menuManager.getInGame_ProvinceRecruitInstantly_Slider().setCurrent(0);
        }

    }

    protected final void disbandArmy(int nProvinceID, int nNumOfUnits, int nCivID) {
        if (nNumOfUnits >= 0) {
            if (nNumOfUnits > CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID)) {
                nNumOfUnits = CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID);
            }

            if (nNumOfUnits > 0) {
                if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_DISBAND) {
                    return;
                }

                CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_DISBAND);
                nNumOfUnits = Math.min(CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID), nNumOfUnits);
                if (nNumOfUnits <= 0) {
                    return;
                }

                CFG.game.getCiv(nCivID).setNumOfUnits(CFG.game.getCiv(nCivID).getNumOfUnits() - nNumOfUnits);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivID, CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) - nNumOfUnits);
                int nNeightboring = 1;

                int nPop;
                for (nPop = 0; nPop < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++nPop) {
                    if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(nPop)).getCivID() == nCivID) {
                        ++nNeightboring;
                    }
                }

                nPop = (int) Math.ceil((double) ((float) nNumOfUnits * 0.05F));
                nNumOfUnits -= nPop;
                CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(nCivID, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(nCivID) + (int) Math.ceil((double) (nPop / nNeightboring)));
                nPop -= (int) Math.ceil((double) (nPop / nNeightboring));
                --nNeightboring;
                if (nNeightboring > 0) {
                    for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
                        if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                            CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) + nPop / nNeightboring);
                        }
                    }

                    nNumOfUnits += nPop - nPop / nNeightboring * nNeightboring;
                }

                if (CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
                    nPop = (int) Math.floor((double) (nNumOfUnits / CFG.game.getCiv(nCivID).getNumOfProvinces()));
                    CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(0)).getPopulationData().setPopulationOfCivID(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(0)).getPopulationData().getPopulationOfCivID(nCivID) + (int) Math.ceil((double) (nNumOfUnits / CFG.game.getCiv(nCivID).getNumOfProvinces())));

                    for (int i = 1; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                        CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().setPopulationOfCivID(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(nCivID) + nPop);
                    }
                } else {
                    CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(nCivID, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(nCivID) + nNumOfUnits);
                }
            }

        }
    }

    protected final void updateDisbandSlider() {
        CFG.menuManager.getInGame_ProvinceDisband_Slider().setMax(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        CFG.menuManager.getInGame_ProvinceDisband_Slider().setCurrent(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
    }

    protected final void updateInGame_Date() {
        CFG.menuManager.getInGame().getMenuElement(4).setWidth(1);
        CFG.menuManager.getInGame().getMenuElement(4).setText(Game_Calendar.getCurrentDate());
        CFG.menuManager.getInGame().getMenuElement(5).setWidth(1);
        CFG.menuManager.getInGame().getMenuElement(5).setText(CFG.langManager.get("Turn") + ": " + Game_Calendar.TURN_ID);
    }

    protected final void updateInGame_ProvinceInfo() {
        try {
            CFG.ACTIVE_PROVINCE_INFO = CFG.chosenProvinceID >= 0 ? CFG.chosenProvinceID : CFG.game.getActiveProvinceID();
            if (CFG.ACTIVE_PROVINCE_INFO < 0) {
                Menu_InGame_ProvinceInfo.iMaxWidth = 0;
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(5).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(6).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setVisible(false);
                return;
            }

            if (CFG.FOG_OF_WAR == 2 && !CFG.getMetProvince(CFG.ACTIVE_PROVINCE_INFO)) {
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setText(CFG.langManager.get("Undiscovered"));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setCurrent(-3);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(6).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(5).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setVisible(false);
            } else if (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getWasteland() >= 0) {
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setText(CFG.langManager.get("Wasteland"));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setCurrent(-2);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(6).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(5).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setText("" + (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringProvincesSize() + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringSeaProvincesSize()));
            } else if (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getSeaProvince()) {
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setText(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName().length() > 0 ? CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName() : CFG.langManager.get("Sea"));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setCurrent(-1);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(6).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(5).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(false);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setText("" + (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringProvincesSize() + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringSeaProvincesSize()));
            } else {
                if (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName().length() > 0) {
                    CFG.game.updateProvinceNameWidth(CFG.ACTIVE_PROVINCE_INFO);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(true);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setText(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName());
                } else {
                    CFG.game.updateProvinceNameWidth("Fokus");
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(true);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setText(CFG.langManager.get("Fokus"));
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setText(CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).getCivName());
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).setCurrent(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID());
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setCurrent(CFG.ACTIVE_PROVINCE_INFO);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setText("" + CFG.game.getProvinceValue(CFG.ACTIVE_PROVINCE_INFO));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setCurrent(CFG.ACTIVE_PROVINCE_INFO);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getPopulation());
                CFG.menuManager.updateInGame_ProvinceInfoGraph(CFG.ACTIVE_PROVINCE_INFO);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(6).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setCurrent((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getGrowthRate_Population_WithFarm_WithTerrain() * 100.0F));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setText("" + (int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getGrowthRate_Population_WithFarm_WithTerrain() * 100.0F) + "%");
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getEconomy());
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setCurrent((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getDevelopmentLevel() * 100.0F));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setText("" + (float) ((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getDevelopmentLevel() * 100.0F)) / 100.0F);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setCurrent((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getHappiness() * 100.0F));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setText("" + (int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getHappiness() * 100.0F) + "%");
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isFestivalOrganized(CFG.ACTIVE_PROVINCE_INFO));
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setText("" + CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isFestivalOrganized_TurnsLeft(CFG.ACTIVE_PROVINCE_INFO));
                }

                if (CFG.menuManager.getVisibleInGame_CensusOfProvince()) {
                    CFG.menuManager.rebuildInGame_CensusOfProvince(CFG.ACTIVE_PROVINCE_INFO);
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setCurrent((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getProvinceStability() * 100.0F));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setText("" + (int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getProvinceStability() * 100.0F) + "%");
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(true);
                Menu_InGame_ProvinceInfo.updateBuildingsList(CFG.ACTIVE_PROVINCE_INFO);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setCurrent((int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getRevolutionaryRisk() * 100.0F));
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setText("" + (int) (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getRevolutionaryRisk() * 100.0F) + "%");
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isAssimilateOrganized(CFG.ACTIVE_PROVINCE_INFO));
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setText("" + CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isAssimialateOrganized_TurnsLeft(CFG.ACTIVE_PROVINCE_INFO));
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isInvestOrganized(CFG.ACTIVE_PROVINCE_INFO));
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setText("" + CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isInvestOrganized_TurnsLeft(CFG.ACTIVE_PROVINCE_INFO));
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isInvestOrganized_Development(CFG.ACTIVE_PROVINCE_INFO));
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setText("" + CFG.game.getCiv(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID()).isInvestOrganized_TurnsLeft_Development(CFG.ACTIVE_PROVINCE_INFO));
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(!CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getIsSupplied() && CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getIsNotSuppliedForXTurns() > 0);
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getIsNotSuppliedForXTurns());
                }

                if (!CFG.SPECTATOR_MODE && CFG.FOG_OF_WAR != 0 && !CFG.game.getPlayer(CFG.PLAYER_TURNID).getFogOfWar(CFG.ACTIVE_PROVINCE_INFO)) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                } else {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getDefensivePosition() > 0);
                    if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getVisible()) {
                        CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getDefensivePosition());
                    }
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.provincePlague != null);
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.provincePlague.iDeaths);
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.iNewColonyBonus > 0);
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setText("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.iNewColonyBonus);
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.iSupportRebelsSize > 0);
                if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).getVisible()) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setCurrent(CFG.ACTIVE_PROVINCE_INFO);
                }

                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setVisible(true);
                CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(24).setText("" + (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringProvincesSize() + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getNeighboringSeaProvincesSize()));
                if (!CFG.SPECTATOR_MODE && Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES && CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID() == 0) {
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(1).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(14).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setVisible(false);
                    CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setVisible(false);
                }
            }
        } catch (IndexOutOfBoundsException var2) {
            CFG.exceptionStack(var2);
        }

        this.updateInGame_ProvinceInfo_PosX();
    }

    protected final void updateInGame_ProvinceInfo_PosX() {
        try {
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).getWidth() - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).getWidth() - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(4).getWidth() + CFG.PADDING);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(7).getWidth() + CFG.PADDING);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).getWidth() + CFG.PADDING);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth() - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).getWidth() + CFG.PADDING);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).setPosX(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).getWidth() + CFG.PADDING);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth()) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth())) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth()))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth())))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth()))))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth())))))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth()))))))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).setPosX((CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth() : (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible() ? CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth() : CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth())))))))) - ImageManager.getImage(Images.bot_left).getWidth() / 2);
            Menu_InGame_ProvinceInfo.iMaxWidth = 1;
            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(2).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(3).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(8).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(9).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(10).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(11).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(12).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(13).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(15).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(16).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(17).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(18).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(19).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(20).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(21).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(22).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            if (CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).getVisible()) {
                Menu_InGame_ProvinceInfo.iMaxWidth = Math.max(CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).getPosX() + CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(23).getWidth(), Menu_InGame_ProvinceInfo.iMaxWidth);
            }

            Menu_InGame_ProvinceInfo.iMaxWidth += CFG.PADDING * 2;
            if ((float) (Menu_InGame_ProvinceInfo.iMaxWidth + CFG.GAME_WIDTH - CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(5).getPosX() + CFG.PADDING * 2) >= (float) CFG.GAME_WIDTH * 0.8F) {
                Menu_InGame_ProvinceInfo.iMaxWidth = -1;
            }
        } catch (IndexOutOfBoundsException var2) {
        } catch (NullPointerException var3) {
        }

    }

    protected final void buildRank_Score() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.buildRank_Score(i);
        }

        this.buildRank_Positions();
    }

    protected final void buildRank_Score(int nCivID) {
        CFG.game.getCiv(nCivID).setRankScore(this.buildRank_Score_Population(nCivID) + this.buildRank_Score_Economy(nCivID) + this.buildRank_Score_Prestige(nCivID));
    }

    protected final void buildRank_Positions() {
        final List<Integer> tCivIDs = new ArrayList<Integer>();
        if (CFG.game.getSortedCivsSize() > 0) {
            if (CFG.game.getSortedCivsSize() != CFG.game.getCivsSize() - 1) {
                CFG.game.sortCivilizationsAZ();
            }
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                tCivIDs.add(CFG.game.getSortedCivsAZ(i - 1));
            }
        } else {
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                tCivIDs.add(i);
            }
        }
        int tRank = 1;
        int tAddID = 0;
        while (tCivIDs.size() > 0) {
            tAddID = 0;
            for (int j = tCivIDs.size() - 1; j > 0; --j) {
                if (CFG.game.getCiv(tCivIDs.get(tAddID)).getRankScore() < CFG.game.getCiv(tCivIDs.get(j)).getRankScore()) {
                    tAddID = j;
                }
            }
            CFG.game.getCiv(tCivIDs.get(tAddID)).setRankPosition(tRank++);
            tCivIDs.remove(tAddID);
        }
    }

    protected final int buildRank_Score_Population(final int nCivID) {
        float nScore = 0.0f;
        final float nTech = Math.min(1.0f, CFG.game.getCiv(nCivID).getTechnologyLevel());
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getNationalitiesSize(); ++j) {
                nScore += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulationID(j) / (CFG.game.getGameScenarios().getScenario_StartingPopulation() / 2.65f) * ((CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getCivID(j) == nCivID) ? 1.0f : 0.275f) * (0.6f + 0.4f * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getProvinceStability()) * (0.625f + 0.375f * nTech) * (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).isOccupied() ? 0.15f : 1.0f);
            }
        }
        return (int) Math.ceil(nScore);
    }

    protected final int buildRank_Score_Economy(final int nCivID) {
        float nScore = 0.0f;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            nScore += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getEconomy() / (CFG.game.getGameScenarios().getScenario_StartingEconomy() / 16.25f) * (0.425f + 0.675f * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getDevelopmentLevel()) * (0.275f + 0.725f * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getProvinceStability());
        }
        return (int) Math.ceil(nScore);
    }

    protected final int buildRank_Score_Prestige(final int nCivID) {
        float nScore = 0.0f;
        final float nTech = Math.min(1.0f, CFG.game.getCiv(nCivID).getTechnologyLevel());
        if (CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                nScore += 2.25f * (0.125f + 0.875f * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getGrowthRate_Population_WithFarm()) * (0.785f + 0.215f * nTech) * (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCore().getHaveACore(nCivID) ? 1.0f : 0.475f) * (0.375f + 0.625f * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getDevelopmentLevel());
            }
            nScore += 17.5f * CFG.game.getCiv(nCivID).getTechnologyLevel();
        }
        return (int) Math.ceil(nScore);
    }

    protected final boolean moveCapital(int nCivID, int toProvinceID) {
        if (nCivID >= 1 && toProvinceID >= 0) {
            if (!this.moveCapital_CanMove(nCivID)) {
                return false;
            } else if ((CFG.game.getCiv(nCivID).getCapitalProvinceID() < 0 || CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID || !CFG.game.getCiv(nCivID).isAtWar() || !CFG.game.getCivsAtWar(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID(), nCivID)) && CFG.game.getProvince(toProvinceID).getTrueOwnerOfProvince() == nCivID && CFG.game.getProvince(toProvinceID).getCivID() == nCivID && CFG.game.getCiv(nCivID).getCapitalProvinceID() != toProvinceID && CFG.game.getCiv(nCivID).getMoney() >= (long) this.moveCapital_Cost(nCivID)) {
                CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - (long) this.moveCapital_Cost(nCivID));
                CFG.game.getCiv(nCivID).setCapitalMoved_LastTurnID(Game_Calendar.TURN_ID);
                int tempOld = CFG.game.getCiv(nCivID).getCapitalProvinceID();
                CFG.game.getCiv(nCivID).setCapitalProvinceID(toProvinceID);
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setIsCapital(false);
                if (tempOld >= 0) {
                    CFG.game.getProvince(tempOld).setIsCapital(false);
                    CFG.game.getProvince(tempOld).updateDrawArmy();
                    CFG.game.getProvince(tempOld).setHappiness(CFG.game.getProvince(tempOld).getHappiness() - CFG.game.getProvince(tempOld).getHappiness() * 0.12168F - 0.12168F);

                    try {
                        CFG.game.getProvince(tempOld).getCity(0).setCityLevel(CFG.getEditorCityLevel(1));
                    } catch (IndexOutOfBoundsException var6) {
                    }
                }

                CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setIsCapital(true);
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).updateDrawArmy();
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setHappiness(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getHappiness() + 0.025F);

                try {
                    CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
                } catch (IndexOutOfBoundsException var5) {
                }

                CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setDrawCities(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected final boolean moveCapital_CanMove(int nCivID) {
        if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID || CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).isOccupied() && CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID()))) {
            return CFG.game.getCiv(nCivID).getCapitalMoved_LastTurnID() <= Game_Calendar.TURN_ID - 50;
        } else {
            return true;
        }
    }

    protected final int moveCapital_Cost(int nCivID) {
        return CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID || CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).isOccupied() && CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID())) ? 1 + (int) ((float) CFG.game.getGameScenarios().getScenario_StartingPopulation() * 0.1925F + (float) CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getPopulationData().getPopulation() * 0.125F + (CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(CFG.game.getCiv(nCivID).getCapitalProvinceID()) + CFG.game_NextTurnUpdate.getProvinceIncome_Production(CFG.game.getCiv(nCivID).getCapitalProvinceID())) * (2.1348F + 1.86584F * CFG.game.getCiv(nCivID).getTechnologyLevel())) : 25;
    }

    protected final boolean abadonProvince(int nProvinceID, int nCivID) {
        if (CFG.game.getProvince(nProvinceID).getCivID() == nCivID && CFG.game.getCiv(nCivID).getCapitalProvinceID() != nProvinceID && !CFG.game.getProvince(nProvinceID).isOccupied() && CFG.game.getCiv(nCivID).getNumOfProvinces() > 1) {
            for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsSize(); ++i) {
                if (CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID() == nProvinceID) {
                    CFG.game.getCiv(nCivID).removeMove(i--);
                }
            }

            for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsPlunderSize(); ++i) {
                if (CFG.game.getCiv(nCivID).getMoveUnits_Plunder(i).getFromProvinceID() == nProvinceID) {
                    CFG.game.getCiv(nCivID).removePlunder(i--);
                }
            }

            for (int i = 0; i < CFG.game.getCiv(nCivID).getMigrateSize(); ++i) {
                if (CFG.game.getCiv(nCivID).getMigrate(i).getFromProvinceID() == nProvinceID) {
                    CFG.game.getCiv(nCivID).removeMigrate(i--);
                }
            }

            for (int i = 0; i < CFG.game.getCiv(nCivID).getRecruitArmySize(); ++i) {
                if (CFG.game.getCiv(nCivID).getRecruitArmy(i).getProvinceID() == nProvinceID) {
                    CFG.game.getCiv(nCivID).removeRecruitArmy(i--);
                }
            }

            for (int i = CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize() - 1; i >= 0; --i) {
                CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), (int) ((float) CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) * (0.05F + (float) CFG.oR.nextInt(20) / 100.0F)));
            }

            CFG.game.getProvince(nProvinceID).setEconomy((int) ((float) CFG.game.getProvince(nProvinceID).getEconomy() * (0.025F + (float) CFG.oR.nextInt(15) / 100.0F)));
            CFG.game.getProvince(nProvinceID).setDevelopmentLevel((float) ((int) (CFG.game.getProvince(nProvinceID).getDevelopmentLevel() * (0.045F + (float) CFG.oR.nextInt(20) / 100.0F))));
            CFG.game.getProvince(nProvinceID).setTrueOwnerOfProvince(0);
            CFG.game.getProvince(nProvinceID).setCivID(0, false);

            try {
                CFG.game.getProvince(nProvinceID).resetArmies(CFG.oR.nextInt(CFG.game.getGameScenarios().getScenario_NeutralArmy() / 2) + CFG.game.getGameScenarios().getScenario_NeutralArmy() / 2);
                CFG.game.getProvince(nProvinceID).updateDrawArmy();
            } catch (IllegalArgumentException var4) {
            }

            return true;
        } else {
            return false;
        }
    }

    protected final void accessLost_UpdateArmies(int inCivID, int nCivID) {
        ArrayList<Integer> tempProvincesToMove = new ArrayList<Integer>();

        for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getCivID() == inCivID) {
                tempProvincesToMove.add(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i));
            }
        }

        for (int i = CFG.game.getCiv(nCivID).getMoveUnitsSize() - 1; i >= 0; --i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID()).getCivID() == inCivID) {
                tempProvincesToMove.add(CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID());
                this.moveArmy(CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID(), CFG.game.getCiv(nCivID).getMoveUnits(i).getToProvinceID(), 0, nCivID, false, false);
            }
        }

        for (int i = 0; i < tempProvincesToMove.size(); ++i) {
            this.accessLost_MoveArmyToClosetsProvince(nCivID, (Integer) tempProvincesToMove.get(i));
        }

    }

    protected final void accessLost_MoveArmyToClosetsProvince(int nCivID, int nProvinceID) {
        this.accessLost_MoveArmyToClosetsProvince(nCivID, nProvinceID, CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID));
    }

    protected final void accessLost_MoveArmyToClosetsProvince(int nCivID, int nProvinceID, int nArmy) {
        if (nArmy > 0) {
            if (CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
                try {
                    int toProvinceID = CFG.game.getCiv(nCivID).getProvinceID(0);
                    float fMinDistance = CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(nProvinceID, toProvinceID);
                    float tempDistance = 0.0F;

                    for (int i = 1; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                        tempDistance = CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(nProvinceID, CFG.game.getCiv(nCivID).getProvinceID(i));
                        if (fMinDistance > tempDistance) {
                            toProvinceID = CFG.game.getCiv(nCivID).getProvinceID(i);
                            fMinDistance = tempDistance;
                        }
                    }

                    CFG.game.getProvince(nProvinceID).updateArmy(nCivID, 0);
                    CFG.game.getProvince(toProvinceID).updateArmy(nCivID, CFG.game.getProvince(toProvinceID).getArmyCivID(nCivID) + nArmy);
                } catch (IndexOutOfBoundsException var8) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(var8);
                    }

                    CFG.game.getCiv(nCivID).setNumOfUnits(CFG.game.getCiv(nCivID).getNumOfUnits() - nArmy);
                    CFG.game.getProvince(nProvinceID).updateArmy(nCivID, 0);
                }
            } else {
                CFG.game.getCiv(nCivID).setNumOfUnits(CFG.game.getCiv(nCivID).getNumOfUnits() - nArmy);
                CFG.game.getProvince(nProvinceID).updateArmy(nCivID, 0);
            }
        }

    }

    protected final TurnStates getActiveTurnState() {
        return this.activeTurnAction;
    }

    protected final void setActiveTurnState(TurnStates nState) {
        this.activeTurnAction = nState;
    }

    protected final MoveUnits_TurnData getCurrentMoveunits() {
        return this.currentMoveUnits;
    }

    protected final void resetCurrentMoveUnits() {
        this.currentMoveUnits = null;
    }

    static enum TurnStates {
        INPUT_ORDERS,
        LOAD_AI_RTO,
        TURN_ACTIONS,
        LOADING_NEXT_TURN,
        START_NEXT_TURN,
        SAVE_THE_GAME,
        RESULTS_STANDINGS,
        END_OF_THE_GAME;

        // $FF: synthetic method
        private static TurnStates[] $values() {
            return new TurnStates[]{INPUT_ORDERS, LOAD_AI_RTO, TURN_ACTIONS, LOADING_NEXT_TURN, START_NEXT_TURN, SAVE_THE_GAME, RESULTS_STANDINGS, END_OF_THE_GAME};
        }
    }
}
