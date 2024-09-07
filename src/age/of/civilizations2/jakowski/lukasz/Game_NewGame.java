/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  age.of.civilizations2.jakowski.lukasz.CFG
 *  age.of.civilizations2.jakowski.lukasz.DiplomacyManager
 *  age.of.civilizations2.jakowski.lukasz.FormableCivs_GameData
 *  age.of.civilizations2.jakowski.lukasz.Game_Action
 *  age.of.civilizations2.jakowski.lukasz.Game_Action$TurnStates
 *  age.of.civilizations2.jakowski.lukasz.Game_Calendar
 *  age.of.civilizations2.jakowski.lukasz.Game_Render
 *  age.of.civilizations2.jakowski.lukasz.Menu
 *  age.of.civilizations2.jakowski.lukasz.Menu_LoadSave
 *  age.of.civilizations2.jakowski.lukasz.Message
 *  age.of.civilizations2.jakowski.lukasz.Message_War
 *  age.of.civilizations2.jakowski.lukasz.Province_Border
 *  age.of.civilizations2.jakowski.lukasz.SaveManager
 *  age.of.civilizations2.jakowski.lukasz.TechnologyManager
 *  age.of.civilizations2.jakowski.lukasz.VicotryManager
 *  age.of.civilizations2.jakowski.lukasz.ViewsManager
 *  com.badlogic.gdx.Gdx
 *  com.badlogic.gdx.files.FileHandle
 *  com.badlogic.gdx.utils.GdxRuntimeException
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.DiplomacyManager;
import age.of.civilizations2.jakowski.lukasz.FormableCivs_GameData;
import age.of.civilizations2.jakowski.lukasz.Game_Action;
import age.of.civilizations2.jakowski.lukasz.Game_Calendar;
import age.of.civilizations2.jakowski.lukasz.Game_Render;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.Menu_LoadSave;
import age.of.civilizations2.jakowski.lukasz.Message;
import age.of.civilizations2.jakowski.lukasz.Message_War;
import age.of.civilizations2.jakowski.lukasz.Province_Border;
import age.of.civilizations2.jakowski.lukasz.SaveManager;
import age.of.civilizations2.jakowski.lukasz.TechnologyManager;
import age.of.civilizations2.jakowski.lukasz.VicotryManager;
import age.of.civilizations2.jakowski.lukasz.ViewsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Game_NewGame {
    Game_NewGame() {
    }

    protected final void updateDeclareWar() {
        int i = 1;
        while (i < CFG.game.getCivsSize()) {
            CFG.game.getCiv((int)i).civGameData.declareWar_CheckNextTurnID = 8 + (int)((float)(CFG.oR.nextInt(45) + (int)((float)CFG.oR.nextInt(CFG.game.getCivsSize()) / 30.0f)) / Game_Calendar.AI_AGGRESSIVNESS);
            CFG.game.getCiv((int)i).civGameData.holdLookingForEnemy_UntilTurnID = (int)(2.0f + ((float)CFG.oR.nextInt(8) + (float)CFG.oR.nextInt(CFG.game.getCivsSize() + 1) / 50.0f) / Game_Calendar.AI_AGGRESSIVNESS);
            CFG.game.getCiv((int)i).civGameData.holdLookingForFriends_UntilTurnID = (int)(2.0f + ((float)CFG.oR.nextInt(8) + (float)CFG.oR.nextInt(CFG.game.getCivsSize() + 1) / 50.0f) / Game_Calendar.AI_AGGRESSIVNESS);
            CFG.game.getCiv((int)i).civGameData.allianceUpdate_TurnID = (int)(26.0f + (float)CFG.oR.nextInt(40) / Game_Calendar.AI_AGGRESSIVNESS);
            CFG.game.getCiv((int)i).civGameData.circledVassals_TurnID = (int)(60.0f + (float)CFG.oR.nextInt(60) / Game_Calendar.AI_AGGRESSIVNESS);
            ++i;
        }
    }

    protected final void newGame() {
        int i;
        int i2;
        Game_Calendar.TURN_ID = 1;
        CFG.PLAYER_TURNID = 0;
        Game_Action.gameEnded = false;
        Gdx.app.log("AoC", "newGame: 0");
        CFG.gameAction.setActiveTurnState(Game_Action.TurnStates.INPUT_ORDERS);
        SaveManager.saveRequest = false;
        CFG.game.clearMoveUnits_JustDraw_AnotherArmies();
        Gdx.app.log("AoC", "newGame: 1");
        CFG.menuManager.setVisibleInGame_Event(false);
        CFG.viewsManager.disableAllViews();
        CFG.viewsManager.clearData();
        CFG.viewsManager = new ViewsManager();
        CFG.dynamicEventManager.clearData(); //sandboxcut init eventmanager change//
        CFG.dynamicEventManager = new DynamicEventManager(); //sandboxcut init eventmanager change//
        Gdx.app.log("AoC", "newGame: 2");
        if (CFG.RANDOM_PLACMENT || CFG.RANDOM_FILL) {
            for (i2 = 0; i2 < CFG.game.getProvincesSize(); ++i2) {
                CFG.game.getProvince(i2).resetArmies(0);
            }
        }
        Gdx.app.log("AoC", "newGame: 3");
        if (!CFG.FILL_THE_MAP) {
            Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = false;
        }
        if (CFG.RANDOM_PLACMENT) {
            this.randomPlacment();
        }
        if (CFG.RANDOM_FILL) {
            this.randomFill();
        }
        Gdx.app.log("AoC", "newGame: 4");
        if (!CFG.FILL_THE_MAP) {
            Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = false;
        }
        Gdx.app.log("AoC", "newGame: 5");
        if (CFG.RANDOM_FILL || CFG.RANDOM_PLACMENT || !CFG.FILL_THE_MAP) {
            CFG.game.getGameScenarios().buildProvincePopulationAndEconomy(false, false);
        } else {
            Gdx.app.log("AoC", "newGame: 5A");
            CFG.game.getGameScenarios().loadArmiesData();
        }
        Gdx.app.log("AoC", "newGame: 6");
        if (CFG.TOTAL_WAR_MODE) {
            for (i2 = 1; i2 < CFG.game.getCivsSize() - 1; ++i2) {
                if (CFG.game.getCiv(i2).getNumOfProvinces() <= 0) continue;
                for (int j = i2 + 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getNumOfProvinces() <= 0 || CFG.game.getCiv(i2).getAllianceID() > 0 && CFG.game.getCiv(i2).getAllianceID() == CFG.game.getCiv(j).getAllianceID()) continue;
                    CFG.game.setCivNonAggressionPact(i2, j, 0);
                    CFG.game.setCivRelation_OfCivB(i2, j, -100.0f);
                    CFG.game.setCivRelation_OfCivB(j, i2, -100.0f);
                }
            }
        }
        Gdx.app.log("AoC", "newGame: 7");
        Gdx.app.log("AoC", "newGame: 8");
        CFG.game.sortCivilizationsAZ();
        Game_NewGame.buildFormableCivilizations();
        if (CFG.SPECTATOR_MODE) {
            //disable sandbox mode
            CFG.SANDBOX_MODE = false;
            //disable pt mode
            CFG.PLAYER_PEACE = false;
            //disable dy events
            CFG.DYNAMIC_EVENTS = false;
            //reset sandboxtask
            CFG.sandbox_task = Menu.eGAMES;
            Game_NewGame.newGame_InitPlayers_SpectatorMode();
        } else {
            this.newGame_InitPlayers();
        }
        Gdx.app.log("AoC", "newGame: 9");
        try {
            if (!CFG.SPECTATOR_MODE) {
                CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(0).getCivID()).getCapitalProvinceID());
            }
        }
        catch (IndexOutOfBoundsException i3) {
            // empty catch block
        }
        this.build_StartingBuildings();
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).buildNumOfUnits();
        }
        CFG.map.getMapCoordinates().setDisableMovingMap(false);
        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() >= 0) {
            CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
        }
        CFG.gameAction.updateCivsMovementPoints();
        CFG.gameAction.updateCivsDiplomacyPoints_StartTheGame();
        Gdx.app.log("AoC", "newGame: 10");
        CFG.gameAction.updateIsSupplied();
        Game_NewGame.build_ArmyInAnotherProvince();
        if (CFG.FOG_OF_WAR > 0) {
            if (CFG.FOG_OF_WAR == 2) {
                for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    CFG.PLAYER_TURNID = i;
                    CFG.gameAction.buildFogOfWar(i);
                    CFG.game.getPlayer(i).buildMetProvincesAndCivs();
                }
                CFG.PLAYER_TURNID = 0;
                for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
                    CFG.game.getProvince(i).updateProvinceBorder();
                }
                Game_Render.updateDrawCivRegionNames_FogOfWar();
            } else {
                for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    CFG.PLAYER_TURNID = i;
                    CFG.gameAction.buildFogOfWar(i);
                }
                CFG.PLAYER_TURNID = 0;
            }
        }
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).updateDrawArmy();
        }
        Gdx.app.log("AoC", "newGame: 11");
        CFG.gameAction.updateCivsHappiness();
        CFG.game_NextTurnUpdate.updateProvinceStability();
        Gdx.app.log("AoC", "newGame: 12");
        Game_NewGame.updateBudgetSpendings();
        Gdx.app.log("AoC", "newGame: 13");
        CFG.game_NextTurnUpdate.updateInflationPeakValue();
        CFG.game_NextTurnUpdate.updatePlayableProvinces();
        TechnologyManager.updateAverageTechLevel();
        Gdx.app.log("AoC", "newGame: 14");
        if (!CFG.TOTAL_WAR_MODE) {
            this.buildCurrentWars();
        }
        DiplomacyManager.sendUncivilizedMessages();
        DiplomacyManager.sendTechPointsMessages();
        this.buildAIPersonalities();
        if (CFG.SANDBOX_MODE && !CFG.SPECTATOR_MODE) {
            Game_NewGame.sandboxMode();
        }
        CFG.holyRomanEmpire_Manager.getHRE().randomNextElections();
        Gdx.app.log("AoC", "newGame: 15");
        CFG.setActiveCivInfo((int)0);
        CFG.map.getMapBG().disposeMinimapOfCivilizations();
        CFG.timelapseManager.newGame();
        SaveManager.newGame();
        SaveManager.gameCanBeContinued = true;
        DiplomacyManager.buildFriendlyCivs();
        Gdx.app.log("AoC", "newGame: 16");
        VicotryManager.checkVictoryConditions();
        CFG.oAI.updateExpand();
        this.updateDeclareWar();
        Gdx.app.log("AoC", "newGame: END");
    }

    protected final void loadGame(int iLoadID) {
        CFG.game.setActiveProvinceID(-1);
        CFG.game.clearMoveUnits_JustDraw_AnotherArmies();
        CFG.gameAction.setActiveTurnState(Game_Action.TurnStates.INPUT_ORDERS);
        Game_Calendar.TURN_ID = 1;
        CFG.PLAYER_TURNID = 0;
        Game_Action.gameEnded = false;
        CFG.menuManager.setVisibleInGame_Event(false);
        CFG.viewsManager.disableAllViews();
        CFG.viewsManager.clearData();
        CFG.viewsManager = new ViewsManager();
        //now incorporated in menu_loadsave load games
        //CFG.dynamicEventManager.clearData(); //sandboxcut init eventmanager change//
        //CFG.dynamicEventManager = new DynamicEventManager(); //sandboxcut init eventmanager change//
        Menu_LoadSave.iLoadID = iLoadID;
        Menu_LoadSave.loadStepID = 0;
        CFG.menuManager.setViewIDWithoutAnimation(Menu.eLOAD_SAVE);
    }

    protected static final void build_ArmyInAnotherProvince() {
        int i = 0;
        while (i < CFG.game.getProvincesSize()) {
            CFG.game.getProvince(i).build_ArmyInAnotherProvince();
            ++i;
        }
    }

    protected final void buildAIPersonalities() {
        int i;
        int i2;
        ArrayList<Integer> lUncivilizedCivs = new ArrayList<Integer>();
        for (int i3 = 1; i3 < CFG.game.getCivsSize(); ++i3) {
            if (CFG.game.getCiv(i3).getNumOfProvinces() <= 0 || CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)i3).getIdeologyID()).CAN_BECOME_CIVILIZED < 0) continue;
            lUncivilizedCivs.add(i3);
        }
        ArrayList<Integer> lUncivilizedSorted = new ArrayList<Integer>();
        while (lUncivilizedCivs.size() > 0) {
            int tBestID = 0;
            for (i2 = 1; i2 < lUncivilizedCivs.size(); ++i2) {
                if (!(CFG.game.getCiv(((Integer)lUncivilizedCivs.get(tBestID)).intValue()).getTechnologyLevel() < CFG.game.getCiv(((Integer)lUncivilizedCivs.get(i2)).intValue()).getTechnologyLevel())) continue;
                tBestID = i2;
            }
            lUncivilizedSorted.add((Integer)lUncivilizedCivs.get(tBestID));
            lUncivilizedCivs.remove(tBestID);
        }
        for (i = 0; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)i).getIdeologyID()).CAN_BECOME_CIVILIZED < 0) continue;
            CFG.game.getCiv((int)i).getCivPersonality().UNCIVILIZED_MIGRATE = 10 + CFG.oR.nextInt(65);
            CFG.game.getCiv((int)i).getCivPersonality().UNCIVILIZED_WILLING_TO_CIVILIZE = 15 + CFG.oR.nextInt(75);
        }
        if (lUncivilizedSorted.size() > 0) {
            int top10 = Math.max(1, lUncivilizedSorted.size() / 10);
            for (i2 = 0; i2 < top10 && i2 < lUncivilizedSorted.size(); ++i2) {
                CFG.game.getCiv((int)((Integer)lUncivilizedSorted.get((int)i2)).intValue()).getCivPersonality().UNCIVILIZED_MIGRATE = 55 + CFG.oR.nextInt(45);
                CFG.game.getCiv((int)((Integer)lUncivilizedSorted.get((int)i2)).intValue()).getCivPersonality().UNCIVILIZED_WILLING_TO_CIVILIZE = 55 + CFG.oR.nextInt(45);
            }
        }
        i = 1;
        while (i < CFG.game.getCivsSize()) {
            CFG.game.getCiv(i).setCoreCapitalProvinceID(CFG.game.getCiv(i).getCapitalProvinceID());
            ++i;
        }
    }

    protected final void buildCurrentWars() {
        int i = 1;
        while (i < CFG.game.getCivsSize() - 1) {
            for (int j = i + 1; j < CFG.game.getCivsSize(); ++j) {
                if ((int)CFG.game.getCivRelation_OfCivB(i, j) != -100) continue;
                CFG.game.addWarData(i, j);
                CFG.game.getCiv(i).setIsAtWar(true);
                CFG.game.getCiv(j).setIsAtWar(true);
                CFG.game.getCiv((int)i).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_War(j, i));
                CFG.game.getCiv((int)j).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_War(i, j));
            }
            ++i;
        }
    }

    protected final void build_StartingBuildings() {
        int i = 1;
        while (i < CFG.game.getCivsSize()) {
            CFG.oAI.getAI_Style(CFG.game.getCiv(i).getAI_Style()).buildStartingBuildings(i);
            ++i;
        }
    }

    protected final void newRandomGame() {
        int i;
        Game_Calendar.TURN_ID = 1;
        CFG.PLAYER_TURNID = 0;
        SaveManager.saveRequest = false;
        Game_Action.gameEnded = false;
        CFG.gameAction.setActiveTurnState(Game_Action.TurnStates.INPUT_ORDERS);
        CFG.game.clearMoveUnits_JustDraw_AnotherArmies();
        CFG.menuManager.setVisibleInGame_Event(false);
        CFG.viewsManager.disableAllViews();
        CFG.viewsManager.clearData();
        CFG.viewsManager = new ViewsManager();
        CFG.dynamicEventManager.clearData(); //sandboxcut init eventmanager change//
        CFG.dynamicEventManager = new DynamicEventManager(); //sandboxcut init eventmanager change//
        Game_Calendar.updateAge();
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).resetArmies(0);
        }
        if (CFG.RANDOM_FILL) {
            this.randomFill();
            CFG.game.getGameScenarios().buildProvincePopulationAndEconomy(false, false);
        }
        CFG.game.sortCivilizationsAZ();
        Game_NewGame.buildFormableCivilizations();
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (CFG.game.getProvince(i).getSeaProvince()) continue;
            CFG.game.getProvince(i).buildProvinceCore();
        }
        if (CFG.SPECTATOR_MODE) {
            //disable sandbox mode
            CFG.SANDBOX_MODE = false;
            //disable pt mode
            CFG.PLAYER_PEACE = false;
            //disable dy events
            CFG.DYNAMIC_EVENTS = false;
            //reset sandboxtask
            CFG.sandbox_task = Menu.eGAMES;
            Game_NewGame.newGame_InitPlayers_SpectatorMode();
        } else {
            this.newGame_InitPlayers();
            CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(0).getCivID()).getCapitalProvinceID());
        }
        this.build_StartingBuildings();
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getCapitalProvinceID() >= 0) {
                CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).updateArmy(CFG.game.getGameScenarios().getScenario_StartingArmyInCapitals());
            }
            CFG.game.getCiv(i).buildNumOfUnits();
        }
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (CFG.game.getProvince(i).getSeaProvince() || CFG.game.getProvince(i).getWasteland() >= 0 || CFG.game.getProvince(i).getCivID() != 0) continue;
            CFG.game.getProvince(i).updateArmy(CFG.randomGameManager.getNeutralArmy());
        }
        CFG.map.getMapCoordinates().setDisableMovingMap(false);
        CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
        this.buildAIPersonalities();
        DiplomacyManager.sendUncivilizedMessages();
        DiplomacyManager.sendTechPointsMessages();
        CFG.gameAction.updateCivsMovementPoints();
        CFG.gameAction.updateCivsDiplomacyPoints_StartTheGame();
        CFG.game.buildWastelandLevels();
        CFG.gameAction.updateIsSupplied();
        Game_NewGame.build_ArmyInAnotherProvince();
        if (CFG.FOG_OF_WAR > 0) {
            if (CFG.FOG_OF_WAR == 2) {
                for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    CFG.PLAYER_TURNID = i;
                    CFG.gameAction.buildFogOfWar(i);
                    CFG.game.getPlayer(i).buildMetProvincesAndCivs();
                }
                CFG.PLAYER_TURNID = 0;
                for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
                    CFG.game.getProvince(i).updateProvinceBorder();
                }
                Game_Render.updateDrawCivRegionNames_FogOfWar();
            } else {
                for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    CFG.PLAYER_TURNID = i;
                    CFG.gameAction.buildFogOfWar(i);
                }
                CFG.PLAYER_TURNID = 0;
            }
        }
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).updateDrawArmy();
        }
        CFG.gameAction.updateCivsHappiness();
        CFG.game_NextTurnUpdate.updateProvinceStability();
        Game_NewGame.updateBudgetSpendings();
        CFG.game_NextTurnUpdate.updateInflationPeakValue();
        CFG.game_NextTurnUpdate.updatePlayableProvinces();
        TechnologyManager.updateAverageTechLevel();
        if (CFG.SANDBOX_MODE && !CFG.SPECTATOR_MODE) {
            CFG.SPECTATOR_MODE = false;
            Game_NewGame.sandboxMode();
        }
        CFG.setActiveCivInfo((int)0);
        CFG.game_NextTurnUpdate.buildLevelsOfCities();
        CFG.map.getMapBG().disposeMinimapOfCivilizations();
        CFG.timelapseManager.newGame();
        SaveManager.newGame();
        SaveManager.gameCanBeContinued = true;
        VicotryManager.checkVictoryConditions();
        CFG.oAI.updateExpand();
        this.updateDeclareWar();
    }

    protected final void updateTrueOwners() {
        int i = 1;
        while (i < CFG.game.getCivsSize()) {
            for (int j = 0; j < CFG.game.getCiv(i).getNumOfProvinces(); ++j) {
                CFG.game.getProvince(CFG.game.getCiv(i).getProvinceID(j)).setTrueOwnerOfProvince(i);
            }
            ++i;
        }
    }

    protected static final void updateBudgetSpendings() {
        int i;
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game_NextTurnUpdate.getBalance_UpdateBudget_Prepare(i);
        }
        i = 1;
        while (i < CFG.game.getCivsSize()) {
            CFG.game_NextTurnUpdate.updateSpendingsOfCiv(i, CFG.game.getCiv((int)i).iBudget);
            ++i;
        }
    }

    protected static final void sandboxMode() {
        if (CFG.SPECTATOR_MODE) return;
        int i = 0;
        while (i < CFG.game.getPlayersSize()) {
            if (CFG.game.getPlayer(i).getCivID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces() > 0) {
                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).setMoney(9999999L);
                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).setMovePoints(999);
                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).setDiplomacyPoints(999);
            }
            ++i;
        }
    }

    private final void newGame_InitPlayers() {
        int i;
        for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getPlayer(i).getCivID() > 0) continue;
            CFG.game.randomPlayerCivilizations(i);
        }
        for (i = 0; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).setControlledByPlayer(false);
        }
        for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (CFG.game.getPlayer(i).getCivID() <= 0) continue;
            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).setControlledByPlayer(true);
        }
        i = 0;
        while (i < CFG.game.getPlayersSize()) {
            CFG.game.getPlayer(i).loadPlayersFlag();
            ++i;
        }
    }

    protected static final void newGame_InitPlayers_SpectatorMode() {
        int i;
        CFG.game.initPlayers();
        CFG.game.getPlayer(0).setCivID(1);
        CFG.FOG_OF_WAR = 0;
        for (i = 0; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).setControlledByPlayer(false);
        }
        i = 0;
        while (i < CFG.game.getPlayersSize()) {
            CFG.game.getPlayer(i).loadPlayersFlag();
            ++i;
        }
    }

    protected final void randomPlacment() {
        int j;
        int i;
        Random oR = new Random();
        ArrayList<Integer> lExistingCivs = new ArrayList<Integer>();
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getIsAvailable()) {
                lExistingCivs.add(i);
            }
            CFG.game.getCiv(i).clearProvinces_FillTheMap(false);
        }
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).setCivID_Just(0);
            CFG.game.getProvince(i).setIsCapital(false);
            j = 1;
            while (j < CFG.game.getProvince(i).getCivsSize()) {
                CFG.game.getProvince(i).removeArmy(j);
            }
        }
        for (i = 0; i < lExistingCivs.size(); ++i) {
            Gdx.app.log("AoC", "RP: 2 -> " + i);
            this.findRandomCapital((Integer)lExistingCivs.get(i), oR);
        }
        if (!CFG.RANDOM_FILL) {
            for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
                for (j = 0; j < CFG.game.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
                    ((Province_Border)CFG.game.getProvince(i).getProvinceBordersLandByLand().get(j)).setIsCivilizationBorder(false, i);
                }
            }
        }
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).updateProvinceBorder();
        }
        i = 0;
        while (i < lExistingCivs.size()) {
            CFG.game.getCiv(((Integer)lExistingCivs.get(i)).intValue()).addProvince(CFG.game.getCiv(((Integer)lExistingCivs.get(i)).intValue()).getCapitalProvinceID());
            ++i;
        }
    }

    /*
     * Enabled force condition propagation
     */
    protected final void findRandomCapital(int nCivID, Random oR) {
        try {
            int tempCapitalID = 0;
            int iNumOfItterations = 0;
            do {
                if (CFG.game.getProvince(tempCapitalID = this.getRandomLandProvinceID(oR)).getIsCapital()) continue;
                boolean found = true;
                for (int i = 0; i < CFG.game.getProvince(tempCapitalID).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(tempCapitalID).getNeighboringProvinces(i)).getIsCapital()) continue;
                    found = false;
                    break;
                }
                if (!found) continue;
                CFG.game.getCiv(nCivID).setCapitalProvinceID(tempCapitalID);
                CFG.game.getProvince(tempCapitalID).setCivID_LoadScenario(nCivID);
                CFG.game.getProvince(tempCapitalID).setIsCapital(true);
                return;
            } while (++iNumOfItterations <= 100);
            int i = 0;
            while (i < CFG.game.getProvincesSize()) {
                if (!CFG.game.getProvince(i).getSeaProvince() && !CFG.game.getProvince(i).getIsCapital() && CFG.game.getProvince(i).getWasteland() < 0) {
                    CFG.game.getCiv(nCivID).setCapitalProvinceID(i);
                    CFG.game.getProvince(i).setCivID_LoadScenario(nCivID);
                    CFG.game.getProvince(i).setIsCapital(true);
                    return;
                }
                ++i;
            }
            return;
        }
        catch (StackOverflowError ex) {
            Gdx.app.log("AoC", "NEWGMAE RANDOM PLACE");
            int i = 0;
            while (i < CFG.game.getProvincesSize()) {
                if (!CFG.game.getProvince(i).getSeaProvince() && !CFG.game.getProvince(i).getIsCapital() && CFG.game.getProvince(i).getWasteland() < 0) {
                    CFG.game.getCiv(nCivID).setCapitalProvinceID(i);
                    CFG.game.getProvince(i).setCivID_LoadScenario(nCivID);
                    CFG.game.getProvince(i).setIsCapital(true);
                    return;
                }
                ++i;
            }
            return;
        }
    }

    private final int getRandomLandProvinceID(Random oR) {
        int tID = oR.nextInt(CFG.game.getProvincesSize());
        if (CFG.game.getProvince(tID).getSeaProvince()) return this.getRandomLandProvinceID(oR);
        if (CFG.game.getProvince(tID).getWasteland() < 0) return tID;
        return this.getRandomLandProvinceID(oR);
    }

    protected final void randomFill() {
        int i;
        boolean changeOwner;
        Gdx.app.log("AoC", "RF 1");
        ArrayList<Integer> lLandProvinces = new ArrayList<Integer>();
        ArrayList<Integer> lWas = new ArrayList<Integer>();
        for (int i2 = 0; i2 < CFG.game.getProvincesSize(); ++i2) {
            if (CFG.game.getProvince(i2).getSeaProvince() || CFG.game.getProvince(i2).getIsCapital() || CFG.game.getProvince(i2).getWasteland() >= 0) continue;
            lLandProvinces.add(i2);
            lWas.add(0);
            CFG.game.getProvince(i2).setCivID(0, false, false);
            int j = 1;
            while (j < CFG.game.getProvince(i2).getCivsSize()) {
                CFG.game.getProvince(i2).removeArmy(j);
            }
        }
        int tProvinceID = 0;
        int tCivID = 0;
        ArrayList<Integer> lExistingCivs = new ArrayList<Integer>();
        for (int i3 = 1; i3 < CFG.game.getCivsSize(); ++i3) {
            if (!CFG.game.getCiv(i3).getIsAvailable()) continue;
            lExistingCivs.add(i3);
        }
        Gdx.app.log("AoC", "RF 2");
        Random oR = new Random();
        ArrayList<Integer> lNeighCivs = new ArrayList<Integer>();
        while (true) {
            block18: {
                if (lLandProvinces.size() <= 0) {
                    Gdx.app.log("AoC", "RF 3");
                    changeOwner = true;
                    break;
                }
                tProvinceID = oR.nextInt(lLandProvinces.size());
                lNeighCivs.clear();
                int i4 = 0;
                while (true) {
                    block19: {
                        if (i4 >= CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvincesSize()) break;
                        if (CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID() == 0) break block19;
                        lNeighCivs.add(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID());
                        lNeighCivs.add(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID());
                        if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID()).getCapitalProvinceID() != ((Integer)lLandProvinces.get(tProvinceID)).intValue()) break block19;
                        for (int u = 0; u < 5 + 30 / CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID()).getNumOfProvinces() + (CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getIsCapital() ? 60 : 0); ++u) {
                            lNeighCivs.add(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringProvinces(i4)).getCivID());
                        }
                    }
                    ++i4;
                }
                for (i4 = 0; i4 < CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringSeaProvincesSize(); ++i4) {
                    for (int j = 0; j < CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringSeaProvinces(i4)).getNeighboringProvincesSize(); ++j) {
                        if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringSeaProvinces(i4)).getNeighboringProvinces(j)).getCivID() == 0 || CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringSeaProvinces(i4)).getNeighboringProvinces(j)).getSeaProvince()) continue;
                        lNeighCivs.add(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).getNeighboringSeaProvinces(i4)).getNeighboringProvinces(j)).getCivID());
                    }
                }
                if (lNeighCivs.size() == 0) {
                    if ((Integer)lWas.get(tProvinceID) > 4) {
                        tCivID = (Integer)lExistingCivs.get(oR.nextInt(lExistingCivs.size()));
                        break block18;
                    } else {
                        lWas.set(tProvinceID, (Integer)lWas.get(tProvinceID) + 1);
                        continue;
                    }
                }
                tCivID = (Integer)lNeighCivs.get(oR.nextInt(lNeighCivs.size()));
            }
            CFG.game.getProvince(((Integer)lLandProvinces.get(tProvinceID)).intValue()).setCivID(tCivID, false, false);
            lLandProvinces.remove(tProvinceID);
            lWas.remove(tProvinceID);
        }
        for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (CFG.game.getProvince(i).getSeaProvince() || CFG.game.getProvince(i).getIsCapital() || CFG.game.getProvince(i).getWasteland() >= 0 || CFG.game.getProvince(i).getNeighboringProvincesSize() <= 0) continue;
            changeOwner = true;
            for (int j = 0; j < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++j) {
                if (CFG.game.getProvince(i).getCivID() == CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID()) {
                    changeOwner = false;
                    break;
                }
                if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(0)).getCivID() == CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID()) continue;
                changeOwner = false;
                break;
            }
            if (!changeOwner) continue;
            CFG.game.getProvince(i).setCivID(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(0)).getCivID(), false, false);
        }
        i = 1;
        while (true) {
            if (i >= CFG.game.getCivsSize()) {
                Gdx.app.log("AoC", "RF 4");
                this.updateTrueOwners();
                CFG.game.buildCivilizationsRegions();
                Gdx.app.log("AoC", "RF END");
                return;
            }
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.game.getCiv(i).getCapitalProvinceID() < 0) {
                CFG.game.getCiv(i).setCapitalProvinceID(CFG.game.getCiv(i).getProvinceID(0));
            }
            ++i;
        }
    }

    protected static final void buildFormableCivilizations() {
        ArrayList<String> tempTags;
        block20: {
            tempTags = new ArrayList<String>();
            try {
                String[] tagsSPLITED;
                String tempT;
                FileHandle tempFileT;
                int i;
                int iSize;
                if (CFG.readLocalFiles()) {
                    try {
                        FileHandle tempFileT2 = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                        String tempT2 = tempFileT2.readString();
                        String[] tagsSPLITED2 = tempT2.split(";");
                        iSize = tagsSPLITED2.length;
                        for (i = 0; i < iSize; ++i) {
                            tempTags.add(tagsSPLITED2[i]);
                        }
                    }
                    catch (GdxRuntimeException tempFileT2) {
                        // empty catch block
                    }
                    try {
                        tempFileT = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                        tempT = tempFileT.readString();
                        tagsSPLITED = tempT.split(";");
                        iSize = tagsSPLITED.length;
                        for (i = 0; i < iSize; ++i) {
                            boolean add = true;
                            for (int j = 0; j < tempTags.size(); ++j) {
                                if (!tagsSPLITED[i].equals(tempTags.get(j))) continue;
                                add = false;
                            }
                            if (!add) continue;
                            tempTags.add(tagsSPLITED[i]);
                        }
                        break block20;
                    }
                    catch (GdxRuntimeException tempFileT2) {
                        break block20;
                    }
                }
                tempFileT = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
                iSize = tagsSPLITED.length;
                for (i = 0; i < iSize; ++i) {
                    tempTags.add(tagsSPLITED[i]);
                }
            }
            catch (GdxRuntimeException tempFileT) {
                // empty catch block
            }
        }
        int i = tempTags.size() - 1;
        while (i >= 0) {
            try {
                FileHandle file;
                try {
                    file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + (String)tempTags.get(i));
                    CFG.formableCivs_GameData = (FormableCivs_GameData)CFG.deserialize((byte[])file.readBytes());
                }
                catch (GdxRuntimeException ex) {
                    file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + (String)tempTags.get(i));
                    CFG.formableCivs_GameData = (FormableCivs_GameData)CFG.deserialize((byte[])file.readBytes());
                }
                if (CFG.formableCivs_GameData != null) {
                    for (int j = CFG.formableCivs_GameData.getClaimantsSize() - 1; j >= 0; --j) {
                        for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                            if (!CFG.game.getCiv(k).getCivTag().equals(CFG.formableCivs_GameData.getClaimant(j)) && (!CFG.ideologiesManager.getRealTag(CFG.game.getCiv(k).getCivTag()).equals(CFG.formableCivs_GameData.getClaimant(j)) || CFG.ideologiesManager.getRealTag(CFG.game.getCiv(k).getCivTag()).equals(CFG.ideologiesManager.getRealTag(CFG.formableCivs_GameData.getFormableCivTag())))) continue;
                            CFG.game.getCiv(k).addTagsCanForm(CFG.formableCivs_GameData.getFormableCivTag());
                        }
                    }
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
            catch (IOException iOException) {
                // empty catch block
            }
            CFG.formableCivs_GameData.clearProvinces();
            CFG.formableCivs_GameData = null;
            --i;
        }
    }

    protected final void updateFormableCivilizations(int nCivID) {
        ArrayList<String> tempTags;
        block19: {
            tempTags = new ArrayList<String>();
            try {
                String[] tagsSPLITED;
                String tempT;
                FileHandle tempFileT;
                int i;
                int iSize;
                if (CFG.readLocalFiles()) {
                    try {
                        FileHandle tempFileT2 = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                        String tempT2 = tempFileT2.readString();
                        String[] tagsSPLITED2 = tempT2.split(";");
                        iSize = tagsSPLITED2.length;
                        for (i = 0; i < iSize; ++i) {
                            tempTags.add(tagsSPLITED2[i]);
                        }
                    }
                    catch (GdxRuntimeException tempFileT2) {
                        // empty catch block
                    }
                    try {
                        tempFileT = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                        tempT = tempFileT.readString();
                        tagsSPLITED = tempT.split(";");
                        iSize = tagsSPLITED.length;
                        for (i = 0; i < iSize; ++i) {
                            boolean add = true;
                            for (int j = 0; j < tempTags.size(); ++j) {
                                if (!tagsSPLITED[i].equals(tempTags.get(j))) continue;
                                add = false;
                            }
                            if (!add) continue;
                            tempTags.add(tagsSPLITED[i]);
                        }
                        break block19;
                    }
                    catch (GdxRuntimeException tempFileT2) {
                        break block19;
                    }
                }
                tempFileT = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
                tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
                iSize = tagsSPLITED.length;
                for (i = 0; i < iSize; ++i) {
                    tempTags.add(tagsSPLITED[i]);
                }
            }
            catch (GdxRuntimeException tempFileT) {
                // empty catch block
            }
        }
        CFG.game.getCiv(nCivID).clearTagsCanForm();
        int i = 0;
        int iSize = tempTags.size();
        while (i < iSize) {
            try {
                FileHandle file;
                try {
                    file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + (String)tempTags.get(i));
                    CFG.formableCivs_GameData = (FormableCivs_GameData)CFG.deserialize((byte[])file.readBytes());
                }
                catch (GdxRuntimeException ex) {
                    file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + (String)tempTags.get(i));
                    CFG.formableCivs_GameData = (FormableCivs_GameData)CFG.deserialize((byte[])file.readBytes());
                }
                if (CFG.formableCivs_GameData != null) {
                    for (int j = CFG.formableCivs_GameData.getClaimantsSize() - 1; j >= 0; --j) {
                        if (!CFG.game.getCiv(nCivID).getCivTag().equals(CFG.formableCivs_GameData.getClaimant(j)) && (!CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()).equals(CFG.formableCivs_GameData.getClaimant(j)) || CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()).equals(CFG.ideologiesManager.getRealTag(CFG.formableCivs_GameData.getFormableCivTag())))) continue;
                        CFG.game.getCiv(nCivID).addTagsCanForm(CFG.formableCivs_GameData.getFormableCivTag());
                    }
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
            catch (IOException iOException) {
                // empty catch block
            }
            CFG.formableCivs_GameData.clearProvinces();
            CFG.formableCivs_GameData = null;
            ++i;
        }
    }
}
