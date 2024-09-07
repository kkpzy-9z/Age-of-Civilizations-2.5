package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.*;

class AI
{
    protected boolean doneLoadingOrders;
    protected int iLoadingTurnActionsOfCivID;
    private List<AI_Style> lAI_Styles;
    protected int NUM_OF_CIVS_IN_THE_GAME;
    protected int PLAYABLE_PROVINCES;
    protected int MIN_NUM_OF_RIVALS;
    protected List<List<AI_Frontline>> lFrontLines;
    protected List<Integer> lNeutralProvincesWithSeaAccess;
    protected int iNeutralProvincesWithSeaAccessSize;
    protected List<Integer> lWastelandProvincesWithSeaAccess;
    protected static int REBUILD_PERSONALITY;
    protected static final int STATUS_QUO_TURNS = 39;
    protected static final int STATUS_QUO_TURNS_NO_ONE_ATTACKED = 19;
    protected static final int STATUS_QUO_NO_PROGRESS = 49;
    protected static final int STATUS_QUO_TOO_LONG = 299;
    protected int iNumOfColonizedProvinces;
    protected final int DANGER_EXTRA_AT_WAR = 450;
    protected Expand expandNeutral;
    
    protected AI() {
        super();
        this.doneLoadingOrders = false;
        this.iLoadingTurnActionsOfCivID = 0;
        this.lAI_Styles = new ArrayList<AI_Style>();
        this.NUM_OF_CIVS_IN_THE_GAME = 0;
        this.PLAYABLE_PROVINCES = 1;
        this.MIN_NUM_OF_RIVALS = 1;
        this.lFrontLines = new ArrayList<List<AI_Frontline>>();
        this.lNeutralProvincesWithSeaAccess = new ArrayList<Integer>();
        this.iNeutralProvincesWithSeaAccessSize = 0;
        this.lWastelandProvincesWithSeaAccess = new ArrayList<Integer>();
        this.iNumOfColonizedProvinces = 0;
        this.updateExpand();
        this.lAI_Styles.add(new AI_Style());
        this.lAI_Styles.add(new AI_Style_Communism());
        this.lAI_Styles.add(new AI_Style_Horde());
        this.lAI_Styles.add(new AI_Style_Fascism());
        this.lAI_Styles.add(new AI_Style_CityState());
        this.lAI_Styles.add(new AI_Style_Tribal());
        this.lAI_Styles.add(new AI_Style_Rebels());
        this.build_RebuildPersonality();
    }
    
    protected final int getAIStyle_ByTag(final String nTag) {
        for (int i = 0; i < this.lAI_Styles.size(); ++i) {
            if (this.lAI_Styles.get(i).TAG.equals(nTag)) {
                return i;
            }
        }
        return 0;
    }
    
    protected final AI_Style getAI_Style(final int i) {
        try {
            return this.lAI_Styles.get(i);
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return this.lAI_Styles.get(0);
        }
    }
    
    protected final void resetNeutralProvincesWithSeaAccess() {
        this.lNeutralProvincesWithSeaAccess.clear();
        this.iNeutralProvincesWithSeaAccessSize = 0;
    }
    
    protected final void addNeutralProvincesWithSeaAccess(final int nProvinceID) {
        this.lNeutralProvincesWithSeaAccess.add(nProvinceID);
    }
    
    protected final void resetWastelandProvincesWithSeaAccess() {
        this.lWastelandProvincesWithSeaAccess.clear();
    }
    
    protected final void addWastelandProvincesWithSeaAccess(final int nProvinceID) {
        this.lWastelandProvincesWithSeaAccess.add(nProvinceID);
    }
    
    protected final void build_RebuildPersonality() {
        AI.REBUILD_PERSONALITY = 79 + CFG.oR.nextInt(20);
    }
    
    protected final void checkCurrentWars_LookingForPeace() {
        try {
            for (int i = 0; i < CFG.game.getWarsSize(); ++i) {
                for (int j = 0; j < CFG.game.getWar(i).getDefendersSize(); ++j) {
                    if (CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(j).getCivID()).getNumOfProvinces() == 0 && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(j).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                        for (int k = 0; k < CFG.game.getWar(i).getAggressorsSize(); ++k) {
                            if (!CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getAggressorID(k).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                                CFG.game.getCiv(CFG.game.getWar(i).getAggressorID(k).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_WeCanSignPeace(CFG.game.getWar(i).getDefenderID(j).getCivID()));
                            }
                        }
                    }
                }
                for (int j = 0; j < CFG.game.getWar(i).getAggressorsSize(); ++j) {
                    if (CFG.game.getCiv(CFG.game.getWar(i).getAggressorID(j).getCivID()).getNumOfProvinces() == 0 && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getAggressorID(j).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                        for (int k = 0; k < CFG.game.getWar(i).getDefendersSize(); ++k) {
                            if (!CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(k).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                                CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(k).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_WeCanSignPeace(CFG.game.getWar(i).getAggressorID(j).getCivID()));
                            }
                        }
                    }
                }
                if (CFG.game.getWar(i).iLastFight_InTunrs > (CFG.game.getWar(i).wasAnyAttack ? 39 : 19) || CFG.game.getWar(i).iLastTurn_ConqueredProvince < Game_Calendar.TURN_ID - 49 || CFG.game.getWar(i).getWarTurnID() < Game_Calendar.TURN_ID - (299 + CFG.game.getCivsSize())) {
                    for (int j = 0; j < CFG.game.getWar(i).getAggressorsSize(); ++j) {
                        if (!CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getAggressorID(j).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                            for (int k = 0; k < CFG.game.getWar(i).getDefendersSize(); ++k) {
                                if (!CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(k).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                                    CFG.game.getCiv(CFG.game.getWar(i).getDefenderID(k).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_WeCanSignPeace(CFG.game.getWar(i).getAggressorID(j).getCivID()));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
    }
    
    protected final void turnOrders() {
        this.doneLoadingOrders = false;
        try {
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                if (!CFG.game.getCiv(i).getControlledByPlayer()) {
                    if (Game_Calendar.TURN_ID % AI.REBUILD_PERSONALITY == i % AI.REBUILD_PERSONALITY && CFG.oR.nextInt(100) > 54) {
                        CFG.game.getCiv(i).buildCivPersonality();
                    }
                    else if (Game_Calendar.TURN_ID % CFG.game.getCiv(i).civGameData.civPersonality.REBUILD_PERSONALITY_MORE_OFTEN == i % CFG.game.getCiv(i).civGameData.civPersonality.REBUILD_PERSONALITY_MORE_OFTEN && CFG.oR.nextInt(100) > 28) {
                        CFG.game.getCiv(i).buildCivPersonality_MoreOften();
                    }
                }
            }
            this.checkCurrentWars_LookingForPeace();
            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                try {
                    if (!CFG.game.getCiv(i).getControlledByPlayer()) {
                        if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                            this.iLoadingTurnActionsOfCivID = i;
                            CFG.setRender_3(true);
                            this.lAI_Styles.get(CFG.game.getCiv(i).getAI_Style()).turnOrdersEssential(i);
                            this.lAI_Styles.get(CFG.game.getCiv(i).getAI_Style()).turnOrders(i);
                        }
                        else {
                            this.lAI_Styles.get(CFG.game.getCiv(i).getAI_Style()).respondToMessages(i);
                        }
                    }
                }
                catch (final IndexOutOfBoundsException ex) {
                    CFG.exceptionStack(ex);
                }
                catch (final ArithmeticException ex2) {
                    CFG.exceptionStack(ex2);
                }
                catch (final NullPointerException ex3) {
                    CFG.exceptionStack(ex3);
                }
            }
            for (int i = Game_Calendar.TURN_ID % 6; i < CFG.game.getCivsSize(); i += 6) {
                if (!CFG.game.getCiv(i).getControlledByPlayer()) {
                    this.lAI_Styles.get(CFG.game.getCiv(i).getAI_Style()).manageVassalsTribute(i);
                }
            }
        }
        finally {
            this.doneLoadingOrders = true;
        }
    }
    
    protected final void updateMinRivals() {
        this.MIN_NUM_OF_RIVALS = (int)Math.min(3.0, Math.ceil((CFG.oAI.NUM_OF_CIVS_IN_THE_GAME - 1) / 2.0f));
    }
    
    protected final void buildAIData() {
        this.resetNeutralProvincesWithSeaAccess();
        this.resetWastelandProvincesWithSeaAccess();
        this.iNumOfColonizedProvinces = 0;
        this.NUM_OF_CIVS_IN_THE_GAME = 0;
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).setSeaAccess(0);
            CFG.game.getCiv(i).clearSeaAccess_Provinces();
            CFG.game.getCiv(i).clearSeaAccess_PortProvinces();
            CFG.game.getCiv(i).setBordersWithEnemy(0);
            CFG.game.getCiv(i).setIsAtWar(false);
            CFG.game.getCiv(i).setCanExpandOnContinent(false);
            CFG.game.getCiv(i).setNumOfNeighboringNeutralProvinces(0);
            CFG.game.getCiv(i).lArmiesPosition.clear();
            CFG.game.getCiv(i).iArmiesPositionSize = 0;
            CFG.game.getCiv(i).lBorderWithCivs.clear();
            CFG.game.getCiv(i).iBorderWithCivsSize = 0;
            CFG.game.getCiv(i).iAveragePopulation = 1;
            CFG.game.getCiv(i).lBordersWithNeutralProvincesID.clear();
            CFG.game.getCiv(i).lBordersWithWastelandProvincesID.clear();
            CFG.game.getCiv(i).civGameData.civPlans.updateObsolateMissions();
            CFG.game.countAvarageDevelopmentLevel_Float(i);
            CFG.game.getCiv(i).lProvincesWithHighRevRisk.clear();
            CFG.game.getCiv(i).isAtWarWithCivs.clear();
            CFG.game.getCiv(i).iNumOf_Forts = 0;
            CFG.game.getCiv(i).iNumOf_Towers = 0;
            CFG.game.getCiv(i).iNumOf_Ports = 0;
            CFG.game.getCiv(i).iNumOf_Farms = 0;
            CFG.game.getCiv(i).iNumOf_Farms_ProvincesPossibleToBuild = 0;
            CFG.game.getCiv(i).iNumOf_Workshops = 0;
            CFG.game.getCiv(i).iNumOf_Libraries = 0;
            CFG.game.getCiv(i).iNumOf_Armories = 0;
            CFG.game.getCiv(i).iNumOf_SuppliesCamp = 0;
            this.iNumOfColonizedProvinces += CFG.game.getCiv(i).civGameData.lColonies_Founded.size();
        }
        final ViewsManager viewsManager = CFG.viewsManager;
        ViewsManager.updateMaxPopulation();
        final ViewsManager viewsManager2 = CFG.viewsManager;
        ViewsManager.updateMaxEconomy();
        for (int i = 1; i < CFG.game.getCivsSize() - 1; ++i) {
            for (int j = i + 1; j < CFG.game.getCivsSize(); ++j) {
                if (CFG.game.getCivsAtWar(i, j)) {
                    CFG.game.getCiv(i).setIsAtWar(true);
                    CFG.game.getCiv(j).setIsAtWar(true);
                    CFG.game.getCiv(i).isAtWarWithCivs.add(j);
                    CFG.game.getCiv(j).isAtWarWithCivs.add(i);
                }
            }
            for (int j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
                CFG.game.getCiv(i).getCivRegion(j).iAveragePotential = 0;
            }
        }
        for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                ++this.NUM_OF_CIVS_IN_THE_GAME;
                if (CFG.game.getCiv(i).isAtWar()) {
                    final Save_Civ_GameData civGameData = CFG.game.getCiv(i).civGameData;
                    ++civGameData.iNumOfTurnsAtWar;
                }
                else {
                    final Save_Civ_GameData civGameData2 = CFG.game.getCiv(i).civGameData;
                    civGameData2.iNumOfTurnsAtWar -= 2;
                    if (CFG.game.getCiv(i).civGameData.iNumOfTurnsAtWar < 0) {
                        CFG.game.getCiv(i).civGameData.iNumOfTurnsAtWar = 0;
                    }
                }
            }
        }
        this.updateMinRivals();
        this.PLAYABLE_PROVINCES = 0;
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince()) {
                if (CFG.game.getProvince(i).getWasteland() >= 0) {
                    if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
                        for (int j = 0; j < CFG.game.getProvince(i).getNeighboringSeaProvincesSize(); ++j) {
                            if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getLevelOfPort() == -2) {
                                this.addWastelandProvincesWithSeaAccess(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    this.buildProvinceData(i);
                    ++this.PLAYABLE_PROVINCES;
                }
            }
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
                if (CFG.game.getCiv(i).getCivRegion(j).getProvincesSize() > 0) {
                    CFG.game.getCiv(i).getCivRegion(j).iAveragePotential /= CFG.game.getCiv(i).getCivRegion(j).getProvincesSize();
                }
            }
            CFG.game.getCiv(i).iArmiesPositionSize = CFG.game.getCiv(i).lArmiesPosition.size();
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                CFG.game.getCiv(i).iAveragePopulation /= CFG.game.getCiv(i).getNumOfProvinces();
            }
            else {
                CFG.game.getCiv(i).iAveragePopulation = 1;
            }
            for (int j = 0; j < CFG.game.getCiv(i).getConstructionsSize(); ++j) {
                if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.FARM) {
                    final Civilization civ = CFG.game.getCiv(i);
                    ++civ.iNumOf_Farms;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.ARMOURY) {
                    final Civilization civ2 = CFG.game.getCiv(i);
                    ++civ2.iNumOf_Armories;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.TOWER) {
                    final Civilization civ3 = CFG.game.getCiv(i);
                    ++civ3.iNumOf_Towers;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.LIBRARY) {
                    final Civilization civ4 = CFG.game.getCiv(i);
                    ++civ4.iNumOf_Libraries;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.PORT) {
                    final Civilization civ5 = CFG.game.getCiv(i);
                    ++civ5.iNumOf_Ports;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.FORT) {
                    final Civilization civ6 = CFG.game.getCiv(i);
                    ++civ6.iNumOf_Forts;
                }
                else if (CFG.game.getCiv(i).getConstruction(j).constructionType == ConstructionType.SUPPLY) {
                    final Civilization civ7 = CFG.game.getCiv(i);
                    ++civ7.iNumOf_SuppliesCamp;
                }
            }
        }
        this.lFrontLines.clear();
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            final List<AI_Frontline> nFrontline = new ArrayList<AI_Frontline>();
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                for (int k = 0; k < CFG.game.getCiv(i).getCivRegionsSize(); ++k) {
                    for (int l = 0; l < CFG.game.getCiv(i).getCivRegion(k).getProvincesSize(); ++l) {
                        if (CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getDangerLevel() > 0) {
                            for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvincesSize(); ++u) {
                                if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() > 0) { //&& CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() != i && !CFG.game.getCivsAreAllied(i, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()) && CFG.game.getCiv(i).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()).getPuppetOfCivID() != i && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()).getPuppetOfCivID() != CFG.game.getCiv(i).getPuppetOfCivID()) {
                                    boolean addNew = true;
                                    int o = 0;
                                    while (o < nFrontline.size()) {
                                        if (nFrontline.get(o).iRegionID == k && nFrontline.get(o).iWithCivID == CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()) {
                                            addNew = false;
                                            nFrontline.get(o).lProvinces.add(CFG.game.getCiv(i).getCivRegion(k).getProvince(l));
                                            if (CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getBordersWithEnemy()) {
                                                nFrontline.get(o).bordersWithEnemy = true;
                                                break;
                                            }
                                            break;
                                        }
                                        else {
                                            ++o;
                                        }
                                    }
                                    if (addNew) {
                                        nFrontline.add(new AI_Frontline(CFG.game.getCiv(i).getCivRegion(k).getProvince(l), k, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID(), CFG.game.getProvince(CFG.game.getCiv(i).getCivRegion(k).getProvince(l)).getBordersWithEnemy()));
                                    }
                                }
                            }
                        }
                    }
                }
                //look through vassals as well
                for (int j = 0; j < CFG.game.getCiv(i).civGameData.iVassalsSize; j++) {
                    int iVassal = CFG.game.getCiv(i).civGameData.lVassals.get(j).iCivID;
                    if (CFG.game.getCiv(iVassal).getNumOfProvinces() > 0 && !CFG.game.getCiv(i).getVassal_AutonomyStatus(iVassal).isMilitaryControl()) {
                        for (int k = 0; k < CFG.game.getCiv(iVassal).getCivRegionsSize(); ++k) {
                            for (int l = 0; l < CFG.game.getCiv(iVassal).getCivRegion(k).getProvincesSize(); ++l) {
                                if (CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getDangerLevel() > 0) {
                                    for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvincesSize(); ++u) {
                                        if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() > 0) { //&& CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() != iVassal && !CFG.game.getCivsAreAllied(iVassal, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()) && CFG.game.getCiv(iVassal).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()).getPuppetOfCivID() != iVassal && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()).getPuppetOfCivID() != CFG.game.getCiv(iVassal).getPuppetOfCivID()) {
                                            boolean addNew = true;
                                            int o = 0;
                                            while (o < nFrontline.size()) {
                                                if (nFrontline.get(o).iRegionID == k && nFrontline.get(o).iWithCivID == CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID()) {
                                                    addNew = false;
                                                    nFrontline.get(o).lProvinces.add(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l));
                                                    if (CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getBordersWithEnemy()) {
                                                        nFrontline.get(o).bordersWithEnemy = true;
                                                        break;
                                                    }
                                                    break;
                                                }
                                                else {
                                                    ++o;
                                                }
                                            }
                                            if (addNew) {
                                                nFrontline.add(new AI_Frontline(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l), k, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getNeighboringProvinces(u)).getCivID(), CFG.game.getProvince(CFG.game.getCiv(iVassal).getCivRegion(k).getProvince(l)).getBordersWithEnemy()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.lFrontLines.add(nFrontline);
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                for (int j = 0; j < CFG.game.getCiv(i).civGameData.civPlans.iWarPreparationsSize; ++j) {
                    for (int f = 0; f < this.lFrontLines.get(i - 1).size(); ++f) {
                        if (this.lFrontLines.get(i - 1).get(f).iWithCivID == CFG.game.getCiv(i).civGameData.civPlans.warPreparations.get(j).onCivID) {
                            for (int e = 0; e < this.lFrontLines.get(i - 1).get(f).lProvinces.size(); ++e) {
                                CFG.game.getProvince(this.lFrontLines.get(i - 1).get(f).lProvinces.get(e)).addDangerLevel((int)(450.0f * (0.675f + 0.325f / CFG.game.getCiv(i).civGameData.civPlans.warPreparations.get(j).iNumOfTurnsLeft)));
                            }
                        }
                    }
                }
            }
        }
        this.iNeutralProvincesWithSeaAccessSize = this.lNeutralProvincesWithSeaAccess.size();
        Gdx.app.log("AI", "--------- TURN: " + Game_Calendar.TURN_ID + " ---------");
        CFG.setRender_3(true);
    }
    
    protected final void buildProvinceData(final int i) {
        CFG.game.getProvince(i).setBordersWithEnemy(false);
        CFG.game.getProvince(i).setDangerLevel(0);
        CFG.game.getProvince(i).setPotential(245);
        CFG.game.getProvince(i).setNumOfNeighboringNeutralProvinces(0);
        CFG.game.getProvince(i).was = false;
        CFG.game.getProvince(i).buildRecruitableArmyPoints();
        if (CFG.game.getProvince(i).getRevolutionaryRisk() > 0.56f) {
            CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).lProvincesWithHighRevRisk.add(i);
        }
        if (CFG.game.getProvince(i).getCivID() > 0) {
            final Civilization civ = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ.iNumOf_Forts += CFG.game.getProvince(i).getLevelOfFort();
            final Civilization civ2 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ2.iNumOf_Towers += CFG.game.getProvince(i).getLevelOfWatchTower();
            if (CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID()) >= 0.0f) {
                final Civilization civ3 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
                civ3.iNumOf_Farms += CFG.game.getProvince(i).getLevelOfFarm();
                final Civilization civ4 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
                ++civ4.iNumOf_Farms_ProvincesPossibleToBuild;
            }
            final Civilization civ5 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ5.iNumOf_Workshops += CFG.game.getProvince(i).getLevelOfWorkshop();
            final Civilization civ6 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ6.iNumOf_Libraries += CFG.game.getProvince(i).getLevelOfLibrary();
            final Civilization civ7 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ7.iNumOf_Armories += CFG.game.getProvince(i).getLevelOfArmoury();
            final Civilization civ8 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ8.iNumOf_SuppliesCamp += CFG.game.getProvince(i).getLevelOfSupply();
            if (CFG.game.getProvince(i).getLevelOfPort() > 0) {
                final Civilization civ9 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
                civ9.iNumOf_Ports += CFG.game.getProvince(i).getLevelOfPort();
                CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).addSeaAccess_PortProvinces(i);
            }
            if (CFG.game.getProvince(i).getNeighboringSeaProvincesSize() > 0) {
                CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).addSeaAccess_Provinces(i);
            }
            for (int k = 0; k < CFG.game.getProvince(i).getCivsSize(); ++k) {
                if (CFG.game.getProvince(i).getArmy(k) > 0) {
                    CFG.game.getCiv(CFG.game.getProvince(i).getCivID(k)).lArmiesPosition.add(i);
                }
            }
            for (int j = 0; j < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++j) {
                if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID() > 0) {
                    CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).addBordersWithCivID(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID());
                }
                else if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getWasteland() >= 0) {
                    CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).lBordersWithWastelandProvincesID.add(CFG.game.getProvince(i).getNeighboringProvinces(j));
                }
                else if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID() == 0) {
                    CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).lBordersWithNeutralProvincesID.add(CFG.game.getProvince(i).getNeighboringProvinces(j));
                }
            }
        }
        else if (CFG.game.getProvince(i).getSeaProvince()) {
            for (int k = 1; k < CFG.game.getProvince(i).getCivsSize(); ++k) {
                if (CFG.game.getProvince(i).getArmy(k) > 0) {
                    CFG.game.getCiv(CFG.game.getProvince(i).getCivID(k)).lArmiesPosition.add(i);
                }
            }
        }
        if (CFG.game.getProvince(i).getWasAttacked() > 0) {
            CFG.game.getProvince(i).addDangerLevel((int)(CFG.game.getProvince(i).getIsCapital() ? 45.0f : (10.0f * ((100.0f - 35 * CFG.game.getProvince(i).getArmy(0) / (float)CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getNumOfUnits()) / 100.0f))));
            CFG.game.getProvince(i).setArmyWasRecruited(0);
            CFG.game.getProvince(i).setWasAttacked(CFG.game.getProvince(i).getWasAttacked() - 1);
        }
        CFG.game.getProvince(i).addPotential(CFG.game.getProvince(i).getNeighboringProvincesSize());
        CFG.game.getProvince(i).addPotential(CFG.game.getProvince(i).getNeighboringSeaProvincesSize());
        final Province province = CFG.game.getProvince(i);
        final float n = (float)(235 * CFG.game.getProvince(i).getPopulationData().getPopulation());
        final ViewsManager viewsManager = CFG.viewsManager;
        province.addPotential((int)(n / ViewsManager.POPULATION_MAX));
        CFG.game.getProvince(i).addPotential((int)(185.0f * CFG.game.getProvince(i).getGrowthRate_Population_WithFarm()));
        final Province province2 = CFG.game.getProvince(i);
        final float n2 = (float)(175 * CFG.game.getProvince(i).getEconomy());
        final ViewsManager viewsManager2 = CFG.viewsManager;
        province2.addPotential((int)(n2 / ViewsManager.ECONOMY_MAX));
        CFG.game.getProvince(i).addPotential((int)(115.0f * CFG.game.getProvince(i).getDevelopmentLevel()));
        CFG.game.getProvince(i).addDangerLevel((int)CFG.game.getProvince(i).getRevolutionaryRisk());
        if (CFG.game.getProvince(i).getCivID() == 0) {
            CFG.game.getProvince(i).addPotential(225 + (int)((375.0f + 275.0f * (0.5f + 0.1f * CFG.game.getProvince(i).getNeighboringProvincesSize())) * CFG.game.getProvince(i).getGrowthRate_Population_WithFarm()));
            for (int j = 0; j < CFG.game.getProvince(i).getNeighboringSeaProvincesSize(); ++j) {
                if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getLevelOfPort() == -2) {
                    this.addNeutralProvincesWithSeaAccess(i);
                    break;
                }
            }
        }
        else {
            final Civilization civ10 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
            civ10.iAveragePopulation += CFG.game.getProvince(i).getPopulationData().getPopulation();
            if (CFG.game.getProvince(i).getLevelOfWatchTower() > 0) {
                CFG.game.getProvince(i).addPotential(4 * CFG.game.getProvince(i).getLevelOfWatchTower() * CFG.game.getProvince(i).getNeighboringProvincesSize());
            }
            CFG.game.getProvince(i).addPotential(6 * CFG.game.getProvince(i).getLevelOfPort() * CFG.game.getProvince(i).getNeighboringProvincesSize());
            CFG.game.getProvince(i).addPotential(5 * CFG.game.getProvince(i).getLevelOfFort());
            CFG.game.getProvince(i).addPotential(3 * CFG.game.getProvince(i).getLevelOfFarm());
            CFG.game.getProvince(i).addPotential(4 * CFG.game.getProvince(i).getLevelOfWorkshop());
            if (CFG.game.getProvince(i).getNeighboringSeaProvincesSize() > 0) {
                CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).setSeaAccess(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSeaAccess() + 1);
            }
            int nNeighbooringOwnProvinces = 0;
            for (int l = 0; l < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++l) {
                if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID() > 0) {
                    if (CFG.game.getProvince(i).getCivID() != CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID() != CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()).getPuppetOfCivID()) {
                        if (CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID())) {
                            CFG.game.getProvince(i).setBordersWithEnemy(true);
                            CFG.game.getProvince(i).addDangerLevel((int)((CFG.game.getProvince(i).getIsCapital() ? 64 : 24) * ((CFG.game.getProvince(i).getWasAttacked() > 0) ? 1.775f : 1.0f) * (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getWasConquered() + 1)));
                        }
                        if (!CFG.game.getCivsAreAllied(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) && CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID() != CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()).getPuppetOfCivID() && CFG.game.getDefensivePact(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) == 0 && CFG.game.getGuarantee(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) == 0 && CFG.game.getCivNonAggressionPact(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) == 0 && CFG.game.getCivTruce(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) < 4) {
                            CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).setCanExpandOnContinent(true);
                            CFG.game.getProvince(i).addDangerLevel(CFG.game.getProvince(i).getIsCapital() ? 14 : 6);
                            CFG.game.getProvince(i).addDangerLevel((int)((CFG.game.getProvince(i).getIsCapital() ? 48.75f : 33.45f) * (CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) ? (4.875f * (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getWasConquered() + 1)) : Math.max(0.75f, 1.55f - CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()) / 25.0f)) * (0.625f + Math.min(1.42f, CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID()).getNumOfProvinces() / (float)CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getNumOfProvinces() / CFG.game.getProvince(i).getNeighboringProvincesSize()))));
                        }
                        final Province province3 = CFG.game.getProvince(i);
                        final float n3 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.POTENTIAL_POPULATION * 0.85f * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getPopulationData().getPopulation();
                        final ViewsManager viewsManager3 = CFG.viewsManager;
                        province3.addPotential(-(int)(n3 / ViewsManager.POPULATION_MAX));
                        final Province province4 = CFG.game.getProvince(i);
                        final float n4 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.POTENTIAL_ECONOMY * 0.85f * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getEconomy();
                        final ViewsManager viewsManager4 = CFG.viewsManager;
                        province4.addPotential(-(int)(n4 / ViewsManager.ECONOMY_MAX));
                        CFG.game.getProvince(i).addPotential(-24);
                    }
                    else {
                        CFG.game.getProvince(i).addPotential(24);
                        ++nNeighbooringOwnProvinces;
                    }
                }
                else {
                    CFG.game.getProvince(i).setNumOfNeighboringNeutralProvinces(CFG.game.getProvince(i).getNumOfNeighboringNeutralProvinces() + 1);
                    CFG.game.getProvince(i).addPotential(5 + (int)(4.0f + 46.0f * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getGrowthRate_Population()));
                }
                final Province province5 = CFG.game.getProvince(i);
                final float n5 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.POTENTIAL_POPULATION * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getPopulationData().getPopulation();
                final ViewsManager viewsManager5 = CFG.viewsManager;
                province5.addPotential((int)(n5 / ViewsManager.POPULATION_MAX));
                final Province province6 = CFG.game.getProvince(i);
                final float n6 = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.POTENTIAL_ECONOMY * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getEconomy();
                final ViewsManager viewsManager6 = CFG.viewsManager;
                province6.addPotential((int)(n6 / ViewsManager.ECONOMY_MAX));
            }
            if (nNeighbooringOwnProvinces > 0) {
                CFG.game.getProvince(i).setDangerLevel((int)(CFG.game.getProvince(i).getDangerLevel() + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.DANGER_EXTRA_PER_OWN_PROVINCE * nNeighbooringOwnProvinces * CFG.game.getProvince(i).getDangerLevel()));
            }
            if (CFG.game.getProvince(i).getBordersWithEnemy()) {
                CFG.game.getProvince(i).addDangerLevel(450);
            }
            if (CFG.game.getProvince(i).getIsCapital()) {
                CFG.game.getProvince(i).addPotential(25);
                if (CFG.game.getProvince(i).getNeighboringSeaProvincesSize() > 0) {
                    CFG.game.getProvince(i).addDangerLevel(125 + 25 * CFG.game.getProvince(i).getNeighboringSeaProvincesSize());
                }
            }
        }
        for (int j = 0; j < CFG.game.getProvince(i).getNeighboringSeaProvincesSize(); ++j) {
            for (int m = 1; m < CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getCivsSize(); ++m) {
                if (CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getCivID(m))) {
                    CFG.game.getProvince(i).addDangerLevel((int)((CFG.game.getProvince(i).getIsCapital() ? 28.75f : 14.87f) * Math.min(1.0f * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getArmy(m) / Math.max((float)CFG.game.getProvince(i).getArmy(0), 1.0f), 2.0f)));
                }
                else if (CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getCivID(m)) < 0.0f) {
                    CFG.game.getProvince(i).addDangerLevel((int)((CFG.game.getProvince(i).getIsCapital() ? 8.75f : 4.87f) * Math.min(1.0f * CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getArmy(m) / Math.max((float)CFG.game.getProvince(i).getArmy(0), 1.0f), 2.0f) * (-CFG.game.getCivRelation_OfCivB(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringSeaProvinces(j)).getCivID(m)) / 100.0f)));
                }
            }
        }
        try {
            if (CFG.game.getProvince(i).getArmy(0) > 0) {
                CFG.game.getProvince(i).setDangerLevel_WithArmy((int)Math.ceil(CFG.game.getProvince(i).getDangerLevel() * (1.0f - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.DANGER_PERC_OF_UNITS * CFG.game.getProvince(i).getArmy(0) / CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getNumOfUnits())));
            }
            else {
                CFG.game.getProvince(i).setDangerLevel_WithArmy(CFG.game.getProvince(i).getDangerLevel());
            }
        }
        catch (final IllegalArgumentException ex) {
            CFG.game.getProvince(i).setDangerLevel_WithArmy(CFG.game.getProvince(i).getDangerLevel());
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        if (CFG.game.getProvince(i).getLevelOfFort() > 0) {
            CFG.game.getProvince(i).setPotential((int)Math.ceil(CFG.game.getProvince(i).getPotential() * 0.9566f));
        }
        if (CFG.game.getProvince(i).getCivID() > 0) {
            try {
                final Civilization_Region civRegion = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getCivRegion(CFG.game.getProvince(i).getCivRegionID());
                civRegion.iAveragePotential += CFG.game.getProvince(i).getPotential();
            }
            catch (final IndexOutOfBoundsException ex2) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex2);
                }
            }
            catch (final NullPointerException ex3) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex3);
                }
            }
        }
        try {
            if (CFG.game.getProvince(i).getCivID() > 0 && CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getCivRegion(CFG.game.getProvince(i).getCivRegionID()).isKeyRegion) {
                CFG.game.getProvince(i).setDangerLevel((int)(CFG.game.getProvince(i).getDangerLevel() * CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.civPersonality.DANGER_EXTRA_KEY_REGION));
            }
        }
        catch (final IndexOutOfBoundsException ex2) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex2);
            }
        }
        catch (final NullPointerException ex3) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex3);
            }
        }
        if (CFG.game.getProvince(i).getNeighbooringProvinceOfCivWasLost() > 0) {
            CFG.game.getProvince(i).addDangerLevel((int)(CFG.game.getProvince(i).getDangerLevel() * 0.15f * CFG.game.getProvince(i).getNeighbooringProvinceOfCivWasLost()));
        }
        if (CFG.game.getProvince(i).getArmyWasRecruited() > 0) {
            CFG.game.getProvince(i).setArmyWasRecruited(CFG.game.getProvince(i).getArmyWasRecruited() - 1);
        }
        if (CFG.game.getProvince(i).getBordersWithEnemy()) {
            CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).setBordersWithEnemy(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getBordersWithEnemy() + 1);
        }
        if (CFG.game.getProvince(i).getNumOfNeighboringNeutralProvinces() > 0) {
            CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).setNumOfNeighboringNeutralProvinces(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getNumOfNeighboringNeutralProvinces() + CFG.game.getProvince(i).getNumOfNeighboringNeutralProvinces());
        }
        CFG.game.getProvince(i).setWasConquered((byte)(CFG.game.getProvince(i).getWasConquered() - 1));
        CFG.game.getProvince(i).setNeighbooringProvinceOfCivWasLost((byte)(CFG.game.getProvince(i).getNeighbooringProvinceOfCivWasLost() - 1));
    }
    
    protected final List<AI_NeighProvinces_Army> getAllNeighboringProvincesInRange_WithArmyToRegroup(final int nProvinceID, final int nCivID, int iRange, final boolean onlyTrueOwner, final boolean dontBreakIfNotFoundRecentlyProvince, final List<AI_NeighProvinces_Army> out, List<Integer> was, final int nRequiredArmy) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        int nArmyCollected = 0;
        while (iRange-- > 0 && (dontBreakIfNotFoundRecentlyProvince || recentlyAdded.size() > 0)) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() == nCivID && (!onlyTrueOwner || CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getTrueOwnerOfProvince())) {
                            if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getArmyCivID(nCivID) - CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission_Army(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)) > 0) {
                                final int tArmy = CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getArmyCivID(nCivID) - CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission_Army(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                                nArmyCollected += tArmy;
                                out.add(new AI_NeighProvinces_Army(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance, tArmy));
                            }
                            recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        }
                    }
                }
            }
            if (nArmyCollected >= nRequiredArmy) {
                break;
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_RecruitAtWAr(final int nProvinceID, final int nCivID, int iRange, final boolean onlyTrueOwner, final boolean dontBreakIfNotFoundRecentlyProvince, final List<AI_NeighProvinces> out, List<Integer> was) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        int iFirstFoundRange = -1;
        while ((nIteration_Distance < iRange || out.size() == 0) && recentlyAdded.size() > 0) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.isAlly(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) || CFG.game.getMilitaryAccess(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) > 0) {
                            if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).isOccupied() && nCivID == CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() && CFG.game.getCiv(nCivID).isRecruitingArmyInProvinceID(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)) < 0) {
                                out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                                if (iFirstFoundRange < 0) {
                                    iFirstFoundRange = nIteration_Distance;
                                    iRange += 4;
                                }
                            }
                            recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        }
                    }
                }
            }
            if (iFirstFoundRange > 0 && iFirstFoundRange + 8 < nIteration_Distance) {
                break;
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_Recruit(final int nProvinceID, final int nCivID, int iRange, final boolean onlyTrueOwner, final boolean dontBreakIfNotFoundRecentlyProvince, final List<AI_NeighProvinces> out, List<Integer> was) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        while (iRange-- > 0 && (dontBreakIfNotFoundRecentlyProvince || recentlyAdded.size() > 0)) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() == nCivID) {
                            if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).isOccupied() && CFG.game.getCiv(nCivID).isRecruitingArmyInProvinceID(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)) < 0) {
                                out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                            }
                            recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        }
                    }
                }
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_Clear(final int nProvinceID, final int nCivID, int iRange, final boolean onlyTrueOwner, final boolean dontBreakIfNotFoundRecentlyProvince, final List<AI_NeighProvinces> out, List<Integer> was) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        while (iRange-- > 0 && (dontBreakIfNotFoundRecentlyProvince || recentlyAdded.size() > 0)) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() == nCivID && (!onlyTrueOwner || CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID() == CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getTrueOwnerOfProvince())) {
                            out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                            recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        }
                    }
                }
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_OnlyOwn_Clear(final int nProvinceID, final int nCivID, int iRange, final boolean onlyTrueOwner, final boolean dontBreakIfNotFoundRecentlyProvince, final List<AI_NeighProvinces> out, List<Integer> was) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        int iFirstFoundRange = -1;
        while (iRange-- > 0) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.isAlly(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID())) {
                            out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                            iFirstFoundRange = nIteration_Distance;
                        }
                        recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                    }
                }
            }
            if (iFirstFoundRange > 0 && iFirstFoundRange + 4 < nIteration_Distance) {
                break;
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final int getLoadingTurnActionsOfCivID() {
        return this.iLoadingTurnActionsOfCivID;
    }
    
    protected final void setLoadingTurnActionsOfCivID(final int iLoadingTurnActionsOfCivID) {
        this.iLoadingTurnActionsOfCivID = iLoadingTurnActionsOfCivID;
    }

    protected final void updateExpand() {
        if (!Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES) {
            this.expandNeutral = new Expand() {
                public boolean expandToNeutralProvinces(int nCivID) {
                    return AI.this.expandToNeutralProvinces_Out(nCivID, true);
                }
            };
        } else {
            this.expandNeutral = new Expand() {
                public boolean expandToNeutralProvinces(int nCivID) {
                    return false;
                }
            };
        }

    }
    
    protected final void expandToNeutralProvinces_Run(final int nCivID) {
        for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.EXPAND_NETURAL_PROVINCE && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).action(nCivID)) {
                CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).onRemove();
                CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
            }
        }
    }

    protected final boolean expandToNeutralProvinces_Out(final int nCivID, boolean maybeGoToTheSea) {
        try {
            if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) {
                Gdx.app.log("AoC", "expandToNeutralProvinces_Out -> " + CFG.game.getCiv(nCivID).getCivName());
                this.expandToNeutralProvinces_Run(nCivID);
                Gdx.app.log("AoC", "expandToNeutralProvinces_Out -> movepoints" + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
                if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
                    return false;
                }
                if (CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.size() > 0 && (this.iNeutralProvincesWithSeaAccessSize <= 0 || !maybeGoToTheSea || CFG.oR.nextInt(100) >= 5 || CFG.game.getCiv(nCivID).getMoney() <= BuildingsManager.getPort_BuildCost(1, CFG.game.getCiv(nCivID).getProvinceID(0)))) {
                    final int recruitableArmyMax = (int)(CFG.game.getCiv(nCivID).getMoney() / 5L);
                    final List<NeutralProvinces> possibleProvinces = new ArrayList<NeutralProvinces>();
                    for (int i = CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.size() - 1; i >= 0; --i) {
                        if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i)).getArmy(0) + 2 < recruitableArmyMax + CFG.game.getCiv(nCivID).getNumOfUnits()) {
                            possibleProvinces.add(new NeutralProvinces(CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i), nCivID));
                        }
                    }
                    Gdx.app.log("AoC", "EXPAND -> 000 possibleProvinces.size: " + possibleProvinces.size());
                    if (possibleProvinces.size() > 0) {
                        final List<Integer> sorted = new ArrayList<Integer>();
                        final List<Integer> tempIDs = new ArrayList<Integer>();
                        for (int j = 0, iSize = possibleProvinces.size(); j < iSize; ++j) {
                            tempIDs.add(j);
                        }
                        while (tempIDs.size() > 0) {
                            int tBest = 0;
                            for (int k = tempIDs.size() - 1; k > 0; --k) {
                                if (possibleProvinces.get(tempIDs.get(tBest)).iScore < possibleProvinces.get(tempIDs.get(k)).iScore) {
                                    tBest = k;
                                }
                            }
                            sorted.add(tempIDs.get(tBest));
                            tempIDs.remove(tBest);
                        }
                        for (int j = 0, iSize = sorted.size(); j < iSize; ++j) {
                            final List<Integer> possibleFrom = new ArrayList<Integer>();
                            for (int l = 0; l < CFG.game.getProvince(possibleProvinces.get(sorted.get(j)).iProvinceID).getNeighboringProvincesSize(); ++l) {
                                if (CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(sorted.get(j)).iProvinceID).getNeighboringProvinces(l)).getCivID() == nCivID) {
                                    possibleFrom.add(CFG.game.getProvince(possibleProvinces.get(sorted.get(j)).iProvinceID).getNeighboringProvinces(l));
                                }
                            }
                            final List<Integer> canMoveImmediately = new ArrayList<Integer>();
                            for (int m = possibleFrom.size() - 1; m >= 0; --m) {
                                if (CFG.game.getProvince(possibleFrom.get(m)).getArmyCivID(nCivID) - CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission_Army(possibleFrom.get(m)) > CFG.game.getProvince(possibleProvinces.get(sorted.get(j)).iProvinceID).getArmy(0)) {
                                    canMoveImmediately.add(possibleFrom.get(m));
                                }
                            }
                            Gdx.app.log("AoC", "EXPAND -> 000 canMoveImmediately.size: " + canMoveImmediately.size());
                            if (canMoveImmediately.size() > 0) {
                                final int randID = CFG.oR.nextInt(canMoveImmediately.size());
                                int numOfNeutral = 0;
                                for (int k2 = 0; k2 < CFG.game.getProvince(canMoveImmediately.get(randID)).getNeighboringProvincesSize(); ++k2) {
                                    if (CFG.game.getProvince(CFG.game.getProvince(canMoveImmediately.get(randID)).getNeighboringProvinces(k2)).getCivID() == 0 && !CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID(CFG.game.getProvince(canMoveImmediately.get(randID)).getNeighboringProvinces(k2))) {
                                        ++numOfNeutral;
                                    }
                                }
                                int tArmyToMove = CFG.game.getProvince(canMoveImmediately.get(randID)).getArmyCivID(nCivID);
                                if (numOfNeutral > 1) {
                                    tArmyToMove = CFG.game.getProvince(possibleProvinces.get(sorted.get(j)).iProvinceID).getArmy(0) + 5 + CFG.oR.nextInt(5);
                                }
                                Gdx.app.log("AoC", "EXPAND -> 000 movearmy: TOPROVINCEID: " + possibleProvinces.get(sorted.get(j)).iProvinceID);
                                if (!CFG.gameAction.moveArmy(canMoveImmediately.get(randID), possibleProvinces.get(sorted.get(j)).iProvinceID, tArmyToMove, nCivID, true, false)) {
                                    break;
                                }
                            }
                            else {
                                Gdx.app.log("AoC", "EXPAND -> 000 ADDMISION: TOPROVINCEID: " + possibleProvinces.get(sorted.get(j)).iProvinceID);
                                if (CFG.game.getCiv(nCivID).civGameData.civPlans.addNewArmyMission(possibleProvinces.get(sorted.get(j)).iProvinceID, new CivArmyMission_ExpandNeutralProvince(nCivID, possibleProvinces.get(sorted.get(j)).iProvinceID))) {}
                            }
                        }
                    }
                    else {
                        for (int i = CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.size() - 1; i >= 0; --i) {
                            possibleProvinces.add(new NeutralProvinces(CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i), nCivID));
                        }
                        int tBest2 = 0;
                        for (int i2 = possibleProvinces.size() - 1; i2 > 0; --i2) {
                            if (possibleProvinces.get(tBest2).iScore < possibleProvinces.get(i2).iScore) {
                                tBest2 = i2;
                            }
                        }
                        CFG.game.getCiv(nCivID).civGameData.civPlans.addNewArmyMission(possibleProvinces.get(tBest2).iProvinceID, new CivArmyMission_ExpandNeutralProvince(nCivID, possibleProvinces.get(tBest2).iProvinceID));
                    }
                }
                else if (maybeGoToTheSea) {
                    Gdx.app.log("AoC", "iNeutralProvincesWithSeaAccessSize: " + this.iNeutralProvincesWithSeaAccessSize);
                    maybeGoToTheSea = false;
                    if (this.iNeutralProvincesWithSeaAccessSize > 0) {
                        final List<NeutralProvinces> possibleTo = new ArrayList<NeutralProvinces>();
                        final List<Integer> possibleTo_MoveFrom = new ArrayList<Integer>();
                        Gdx.app.log("AoC", "EXPAND EXTRA -> begin");
                        for (int i = 0; i < CFG.game.getCiv(nCivID).getCivRegionsSize(); ++i) {
                            if (CFG.game.getCiv(nCivID).getCivRegion(i).getSeaAccess()) {
                                for (int j2 = 0; j2 < CFG.game.getCiv(nCivID).getCivRegion(i).getProvincesSize(); ++j2) {
                                    if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getLevelOfPort() > 0) {
                                        for (int k3 = 0; k3 < CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvincesSize(); ++k3) {
                                            for (int o = 0; o < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvincesSize(); ++o) {
                                                if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getCivID() == 0) {
                                                    possibleTo.add(new NeutralProvinces(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o), nCivID));
                                                    possibleTo_MoveFrom.add(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < CFG.game.getCiv(nCivID).getCivRegionsSize(); ++i) {
                            if (CFG.game.getCiv(nCivID).getCivRegion(i).getSeaAccess()) {
                                for (int j2 = 0; j2 < CFG.game.getCiv(nCivID).getCivRegion(i).getProvincesSize(); ++j2) {
                                    if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getLevelOfPort() > 0) {
                                        for (int k3 = 0; k3 < CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvincesSize(); ++k3) {
                                            for (int o = 0; o < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvincesSize(); ++o) {
                                                if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getSeaProvince()) {
                                                    for (int z = 0; z < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getNeighboringProvincesSize(); ++z) {
                                                        if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getNeighboringProvinces(z)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getNeighboringProvinces(z)).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getNeighboringProvinces(z)).getCivID() == 0) {
                                                            possibleTo.add(new NeutralProvinces(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2)).getNeighboringSeaProvinces(k3)).getNeighboringProvinces(o)).getNeighboringProvinces(z), nCivID));
                                                            possibleTo_MoveFrom.add(CFG.game.getCiv(nCivID).getCivRegion(i).getProvince(j2));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Gdx.app.log("AoC", "EXPAND EXTRA -> 11 -> possibleTo.size: " + possibleTo.size());
                        if (possibleTo.size() > 0) {
                            int tBest2 = 0;
                            for (int i2 = possibleTo.size() - 1; i2 > 0; --i2) {
                                if (possibleTo.get(tBest2).iScore < possibleTo.get(i2).iScore) {
                                    tBest2 = i2;
                                }
                            }
                            final int neutralArmy = CFG.game.getProvince(possibleTo.get(tBest2).iProvinceID).getArmy(0) + 6 - CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID_Num(possibleTo.get(tBest2).iProvinceID) - CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission_Army(possibleTo.get(tBest2).iProvinceID);
                            if (neutralArmy >= 0) {
                                if (CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID) > neutralArmy) {
                                    final RegroupArmy_Data tryRegroupArmy = new RegroupArmy_Data(nCivID, possibleTo_MoveFrom.get(tBest2), possibleTo.get(tBest2).iProvinceID);
                                    if (tryRegroupArmy.getRouteSize() > 0 && CFG.gameAction.moveArmy(possibleTo_MoveFrom.get(tBest2), tryRegroupArmy.getRoute(0), neutralArmy, nCivID, true, false)) {
                                        if (tryRegroupArmy.getRouteSize() > 1) {
                                            CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_ExpandNeutral_Check(nCivID, tryRegroupArmy.getRoute(0), possibleTo.get(tBest2).iProvinceID, neutralArmy));
                                        }
                                        return false;
                                    }
                                }
                                else {
                                    final int tArmyToRecruit = neutralArmy - CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID);
                                    CFG.game.getCiv(nCivID).recruitArmy_AI(possibleTo_MoveFrom.get(tBest2), tArmyToRecruit);
                                    final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(possibleTo_MoveFrom.get(tBest2)) + CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID);
                                    if (tempArmy > 0) {
                                        CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_ExpandNeutral_Check(nCivID, possibleTo_MoveFrom.get(tBest2), possibleTo.get(tBest2).iProvinceID, tempArmy));
                                    }
                                }
                            }
                        }
                        else {
                            possibleTo_MoveFrom.clear();
                            possibleTo.clear();
                            for (int z2 = 0; z2 < CFG.game.getCiv(nCivID).getCivRegionsSize(); ++z2) {
                                if (CFG.game.getCiv(nCivID).getCivRegion(z2).getSeaAccess()) {
                                    for (int j2 = 0; j2 < CFG.game.getCiv(nCivID).getCivRegion(z2).getProvincesSize(); ++j2) {
                                        if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2)).getLevelOfPort() >= 0) {
                                            List<Integer> recentlyAdded = new ArrayList<Integer>();
                                            List<Integer> was = new ArrayList<Integer>();
                                            for (int k4 = 0; k4 < CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2)).getNeighboringSeaProvincesSize(); ++k4) {
                                                recentlyAdded.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2)).getNeighboringSeaProvinces(k4));
                                                was.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2)).getNeighboringSeaProvinces(k4));
                                                CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2)).getNeighboringSeaProvinces(k4)).was = true;
                                            }
                                            final List<Integer> currProvinces = new ArrayList<Integer>();
                                            int nIteration_Distance = 0;
                                            boolean foundProvince = false;
                                            while (nIteration_Distance < CFG.game.getCiv(nCivID).civGameData.iExpandNeutralProvinces_RangeCheck && recentlyAdded.size() > 0) {
                                                currProvinces.clear();
                                                ++nIteration_Distance;
                                                for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                                                    boolean wasntAdded = true;
                                                    for (int p = currProvinces.size() - 1; p >= 0; --p) {
                                                        if (currProvinces.get(p) == recentlyAdded.get(a)) {
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
                                                    for (int i3 = 0; i3 < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i3) {
                                                        if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3)).was) {
                                                            was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3));
                                                            CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3)).was = true;
                                                            if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3)).getSeaProvince()) {
                                                                recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3));
                                                            }
                                                            else if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3)).getCivID() == 0 && CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3)).getWasteland() < 0) {
                                                                possibleTo.add(new NeutralProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3), nCivID));
                                                                possibleTo_MoveFrom.add(CFG.game.getCiv(nCivID).getCivRegion(z2).getProvince(j2));
                                                                foundProvince = true;
                                                                recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i3));
                                                            }
                                                        }
                                                    }
                                                }
                                                if (foundProvince) {
                                                    break;
                                                }
                                            }
                                            for (int p2 = was.size() - 1; p2 >= 0; --p2) {
                                                CFG.game.getProvince(was.get(p2)).was = false;
                                            }
                                            recentlyAdded.clear();
                                            recentlyAdded = null;
                                            was.clear();
                                            was = null;
                                        }
                                    }
                                }
                            }
                            Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> possibleTo.size: " + possibleTo.size());
                            if (possibleTo.size() == 0) {
                                CFG.game.getCiv(nCivID).civGameData.iExpandNeutralProvinces_RangeCheck = Math.max(CFG.game.getCiv(nCivID).civGameData.iExpandNeutralProvinces_RangeCheck + 1, CFG.game.getProvincesSize() / 15);
                                this.expandToNeutralProvinces_Out(nCivID, false);
                            }
                            else {
                                int tBest2 = 0;
                                for (int i2 = possibleTo.size() - 1; i2 > 0; --i2) {
                                    if (possibleTo.get(tBest2).iScore < possibleTo.get(i2).iScore) {
                                        tBest2 = i2;
                                    }
                                }
                                Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION 0000 -> ProvinceID_TO: " + possibleTo.get(tBest2).iProvinceID);
                                Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION 0000 -> ProvinceID_FROM: " + possibleTo_MoveFrom.get(tBest2));
                                final int neutralArmy = CFG.game.getProvince(possibleTo.get(tBest2).iProvinceID).getArmy(0) + 10 - CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID_Num(possibleTo.get(tBest2).iProvinceID) - CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission_Army(possibleTo.get(tBest2).iProvinceID);
                                if (neutralArmy >= 0) {
                                    Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION 1111");
                                    if (CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID) > neutralArmy) {
                                        Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION 2222");
                                        final RegroupArmy_Data_PortToBuild tryRegroupArmy2 = new RegroupArmy_Data_PortToBuild(nCivID, possibleTo_MoveFrom.get(tBest2), possibleTo.get(tBest2).iProvinceID);
                                        if (tryRegroupArmy2.getRouteSize() > 0 && CFG.gameAction.moveArmy(possibleTo_MoveFrom.get(tBest2), tryRegroupArmy2.getRoute(0), neutralArmy, nCivID, true, false)) {
                                            if (tryRegroupArmy2.getRouteSize() > 1) {
                                                CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_Expand_BuildPort(nCivID, tryRegroupArmy2.getRoute(0), possibleTo.get(tBest2).iProvinceID, neutralArmy));
                                            }
                                            return false;
                                        }
                                    }
                                    else {
                                        Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION 3333");
                                        final int tArmyToRecruit = neutralArmy - CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID);
                                        Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION tArmyToRecruit: " + tArmyToRecruit);
                                        CFG.game.getCiv(nCivID).recruitArmy_AI(possibleTo_MoveFrom.get(tBest2), tArmyToRecruit);
                                        final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(possibleTo_MoveFrom.get(tBest2)) + CFG.game.getProvince(possibleTo_MoveFrom.get(tBest2)).getArmyCivID(nCivID);
                                        Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION tempArmy: " + tempArmy);
                                        if (tempArmy > 0) {
                                            Gdx.app.log("AoC", "EXPAND EXTRA -> 22 -> ACTION ADDMISION");
                                            CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_Expand_BuildPort(nCivID, possibleTo_MoveFrom.get(tBest2), possibleTo.get(tBest2).iProvinceID, tempArmy));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
        }
        return false;
    }
    
    protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_Regroup_ForNavalInvasion(final int nProvinceID, final int nCivID, final int iRange, final List<AI_NeighProvinces> out, List<Integer> was) {
        List<Integer> recentlyAdded = new ArrayList<Integer>();
        recentlyAdded.add(nProvinceID);
        was.add(nProvinceID);
        CFG.game.getProvince(nProvinceID).was = true;
        final List<Integer> currProvinces = new ArrayList<Integer>();
        int nIteration_Distance = 0;
        int iFirstFoundRange = -1;
        while ((nIteration_Distance < iRange || out.size() == 0) && recentlyAdded.size() > 0) {
            currProvinces.clear();
            ++nIteration_Distance;
            for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
                boolean wasntAdded = true;
                for (int j = currProvinces.size() - 1; j >= 0; --j) {
                    if (currProvinces.get(j) == recentlyAdded.get(a)) {
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
                for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                        was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                        if (CFG.game.isAlly(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID())) {
                            if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getArmyCivID(nCivID) > 0) {
                                out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                                if (iFirstFoundRange < 0) {
                                    iFirstFoundRange = nIteration_Distance;
                                }
                            }
                            recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                        }
                    }
                }
            }
            if (iFirstFoundRange > 0 && iFirstFoundRange + 2 < nIteration_Distance) {
                break;
            }
        }
        for (int k = was.size() - 1; k >= 0; --k) {
            CFG.game.getProvince(was.get(k)).was = false;
        }
        recentlyAdded.clear();
        recentlyAdded = null;
        was.clear();
        was = null;
        return out;
    }
    
    protected final boolean prepareForWar_BordersWithEnemy(final int nCivID, final int nProvinceID) {
        if (CFG.game.getProvince(nProvinceID).getBordersWithEnemy()) {
            for (int z = 0; z < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++z) {
                if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(z)).getCivID())) {
                    return true;
                }
            }
        }
        return this.prepareForWar_BordersWithEnemy_Just(nCivID, nProvinceID);
    }
    
    protected final boolean prepareForWar_BordersWithEnemy_Just(final int nCivID, final int nProvinceID) {
        for (int u = 0; u < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u) {
            for (int k = 0; k < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++k) {
                if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(k)).getCivID() == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u).onCivID) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        AI.REBUILD_PERSONALITY = 87;
    }

    class NeutralProvinces {
        protected int iProvinceID;
        protected float iScore;

        protected NeutralProvinces(int nProvinceID, int nCivID) {
            this.iProvinceID = nProvinceID;
            this.buildScore(nCivID);
        }

        protected final void buildScore(int nCivID) {
            int neighboring_NeutralProvinces = 0;
            int neighboring_CivProvinces = 0;
            int neighboring_OtherCivProvinces = 0;

            for(int i = 0; i < CFG.game.getProvince(this.iProvinceID).getNeighboringProvincesSize(); ++i) {
                if (CFG.game.getProvince(CFG.game.getProvince(this.iProvinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
                    if (CFG.game.getProvince(CFG.game.getProvince(this.iProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                        if (CFG.game.getProvince(this.iProvinceID).getNeighboringProvinces(i) == CFG.game.getCiv(nCivID).getCapitalProvinceID()) {
                            this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_CAPITAL;
                        } else {
                            this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_OWN_PROVINCE;
                        }

                        ++neighboring_CivProvinces;
                    } else if (CFG.game.getProvince(CFG.game.getProvince(this.iProvinceID).getNeighboringProvinces(i)).getCivID() == 0) {
                        ++neighboring_NeutralProvinces;
                        this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_MORE_NEUTRAL;
                    } else {
                        ++neighboring_OtherCivProvinces;
                        this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_OTHER_CIV;
                    }
                }
            }

            this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_GROWTH_RATE * CFG.game.getProvince(this.iProvinceID).getGrowthRate_Population();
            if (CFG.game.getProvince(this.iProvinceID).getNeighboringSeaProvincesSize() > 0) {
                this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_SEA_ACCESS + CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_SEA_ACCESS_EXTRA * (float)CFG.game.getProvince(this.iProvinceID).getNeighboringSeaProvincesSize();
            }

            this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_NEIGHBORING_PROVINCES * (float)(neighboring_CivProvinces + neighboring_NeutralProvinces + neighboring_OtherCivProvinces);
            this.iScore += (float)((int)(CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_NEIGHBORING_PROVINCES_POTENITAL * (float)(neighboring_NeutralProvinces + neighboring_CivProvinces) / (float)(neighboring_CivProvinces + neighboring_NeutralProvinces + neighboring_OtherCivProvinces)));
            if (neighboring_NeutralProvinces == 0 && CFG.game.getProvince(this.iProvinceID).getNeighboringProvincesSize() > 0) {
                this.iScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.NEUTRAL_EXPAND_LAST_PROVINCE;
            } else if (neighboring_CivProvinces <= 1) {
                this.iScore *= 0.725F;
            }

        }
    }
    interface Expand
    {
        boolean expandToNeutralProvinces(final int p0);
    }
}
