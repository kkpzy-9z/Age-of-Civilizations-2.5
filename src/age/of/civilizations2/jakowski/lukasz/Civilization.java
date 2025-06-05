package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.AI_BordersWith;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.CivBonus_GameData;
import age.of.civilizations2.jakowski.lukasz.CivBonus_Type;
import age.of.civilizations2.jakowski.lukasz.CivFestival;
import age.of.civilizations2.jakowski.lukasz.CivInvest;
import age.of.civilizations2.jakowski.lukasz.CivInvest_Development;
import age.of.civilizations2.jakowski.lukasz.CivPersonality;
import age.of.civilizations2.jakowski.lukasz.CivPlans;
import age.of.civilizations2.jakowski.lukasz.Civilization_Diplomacy_GameData;
import age.of.civilizations2.jakowski.lukasz.Civilization_Friends_GameData;
import age.of.civilizations2.jakowski.lukasz.Civilization_Hated_GameData;
import age.of.civilizations2.jakowski.lukasz.Civilization_Region;
import age.of.civilizations2.jakowski.lukasz.Civilization_SentMessages;
import age.of.civilizations2.jakowski.lukasz.Commands;
import age.of.civilizations2.jakowski.lukasz.ConstructionType;
import age.of.civilizations2.jakowski.lukasz.Construction_GameData;
import age.of.civilizations2.jakowski.lukasz.DiplomacyManager;
import age.of.civilizations2.jakowski.lukasz.Event_Decision;
import age.of.civilizations2.jakowski.lukasz.Game_Action;
import age.of.civilizations2.jakowski.lukasz.Game_Calendar;
import age.of.civilizations2.jakowski.lukasz.HistoryLog;
import age.of.civilizations2.jakowski.lukasz.HistoryLog_FriendlyCivs;
import age.of.civilizations2.jakowski.lukasz.Image;
import age.of.civilizations2.jakowski.lukasz.ImageManager;
import age.of.civilizations2.jakowski.lukasz.Images;
import age.of.civilizations2.jakowski.lukasz.Loan_GameData;
import age.of.civilizations2.jakowski.lukasz.Menu_InGame_Event;
import age.of.civilizations2.jakowski.lukasz.Message;
import age.of.civilizations2.jakowski.lukasz.Message_AssimilationEnd;
import age.of.civilizations2.jakowski.lukasz.Message_FestivalIsOver;
import age.of.civilizations2.jakowski.lukasz.Message_InvestDone;
import age.of.civilizations2.jakowski.lukasz.Message_InvestDone_Development;
import age.of.civilizations2.jakowski.lukasz.Message_Relations_Friendly;
import age.of.civilizations2.jakowski.lukasz.Message_Repaid;
import age.of.civilizations2.jakowski.lukasz.Message_Type;
import age.of.civilizations2.jakowski.lukasz.Message_WarReparationsRepaid;
import age.of.civilizations2.jakowski.lukasz.Message_WarReparationsRepaid_Green;
import age.of.civilizations2.jakowski.lukasz.MoveUnits_Line;
import age.of.civilizations2.jakowski.lukasz.MoveUnits_Line_Highlighted;
import age.of.civilizations2.jakowski.lukasz.Move_Units;
import age.of.civilizations2.jakowski.lukasz.Move_Units_Plunder;
import age.of.civilizations2.jakowski.lukasz.RecruitArmy_Request;
import age.of.civilizations2.jakowski.lukasz.RegroupArmy_Data;
import age.of.civilizations2.jakowski.lukasz.Save_Civ_GameData;
import age.of.civilizations2.jakowski.lukasz.UnionFlagsToGenerate;
import age.of.civilizations2.jakowski.lukasz.UnionFlagsToGenerate_TypesOfAction;
import age.of.civilizations2.jakowski.lukasz.Vassal_GameData;
import age.of.civilizations2.jakowski.lukasz.WarReparations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Civilization {
    protected Save_Civ_GameData civGameData = new Save_Civ_GameData();
    private int iCivID;
    private boolean controlledByPlayer = false;
    private boolean isAvailable = true;
    private List<Character> lCivNameChars;
    private int iCivNameLength = 0;
    private int iCivNameWidth;
    private int iCivNameHeight;
    private Image civFlag = null;
    private List<String> sTagsCanForm = null;
    private List<Integer> lEventsToRun = new ArrayList<Integer>();
    private int iMovePoints;
    private int iHappiness;
    private int iIdeologyID = 0;
    private int iNumOfProvinces;
    private int iNumOfUnits;
    private List<Integer> lProvinces = new ArrayList<Integer>();
    private List<Integer> lArmyInAnotherProvince = new ArrayList<Integer>();
    private int iArmyInAnotherProvinceSize = 0;
    protected List<Integer> lArmiesPosition = new ArrayList<Integer>();
    protected int iArmiesPositionSize = 0;
    protected List<AI_BordersWith> lBorderWithCivs = new ArrayList<AI_BordersWith>();
    protected int iBorderWithCivsSize = 0;
    protected List<Integer> lBordersWithWastelandProvincesID = new ArrayList<Integer>();
    protected List<Integer> lBordersWithNeutralProvincesID = new ArrayList<Integer>();
    private List<Civilization_Region> lCivRegions = new ArrayList<Civilization_Region>();
    private int iCivRegionsSize;
    private boolean updateRegions = false;
    protected int iLeague = 0;
    protected int iBudget = 0;
    protected int iIncomeTaxation = 0;
    protected int iIncomeProduction = 0;
    protected int iAdministrationCosts = 0;
    protected int iMilitaryUpkeep_Total = 0;
    protected float iMilitaryUpkeep_PERC = 0.0f;
    protected int iAveragePopulation = 0;
    protected List<Integer> lProvincesWithLowStability = new ArrayList<Integer>();
    protected List<Integer> lProvincesWithLowHappiness = new ArrayList<Integer>();
    protected List<Integer> lProvincesWithHighRevRisk = new ArrayList<Integer>();
    protected float fStability = 1.0f;
    protected float fAverageDevelopment = 1.0f;
    protected int iNumOf_Forts = 0;
    protected int iNumOf_Towers = 0;
    protected int iNumOf_Ports = 0;
    protected int iNumOf_Farms = 0;
    protected int iNumOf_Farms_ProvincesPossibleToBuild = 0;
    protected int iNumOf_Workshops = 0;
    protected int iNumOf_Libraries = 0;
    protected int iNumOf_Armories = 0;
    protected int iNumOf_SuppliesCamp = 0;
    protected List<Integer> lNonAggressionPact = new ArrayList<Integer>();
    protected List<Integer> lOpt_NonAggressionPact = new ArrayList<Integer>();
    protected List<Integer> lTruce = new ArrayList<Integer>();
    protected List<Integer> lOpt_Truce = new ArrayList<Integer>();
    protected List<Integer> lDefensivePact = new ArrayList<Integer>();
    protected List<Integer> lOpt_DefensivePact = new ArrayList<Integer>();
    protected List<Byte> lGuarantee = new ArrayList<Byte>();
    protected List<Integer> lOpt_Guarantee = new ArrayList<Integer>();
    protected List<Byte> lMilitirayAccess = new ArrayList<Byte>();
    protected List<Integer> lOpt_MilitirayAccess = new ArrayList<Integer>();
    private int seaAccess = 0;
    private List<Integer> seaAccess_Provinces = new ArrayList<Integer>();
    private List<Integer> seaAccess_Port = new ArrayList<Integer>();
    private int bordersWithEnemy = 0;
    private boolean isAtWar = false;
    protected List<Integer> isAtWarWithCivs = new ArrayList<Integer>();
    private int iNumOfNeighboringNeutralProvinces = 0;
    private boolean canExpandOnContinent = false;
    private int iRankPosition = 1;
    private int iRankScore = 1;
    private List<Move_Units> lMoveUnits;
    private int iMoveUnitsSize;
    private List<Move_Units_Plunder> lMove_Units_Plunder;
    private int iMove_Units_PlunderSize;
    private List<List<MoveUnits_Line>> lCurrentRegroupArmyLine = new ArrayList<List<MoveUnits_Line>>();
    private List<RecruitArmy_Request> lRecruitArmy;
    private int iRecruitArmySize;
    private List<Move_Units> lMigrate;
    private int iMigrateSize;
    protected static final int ADD_CIV_DEFAULT_TECH_LEVEL = 45;
    protected static final int MIN_MONEY_REQUIRED_TO_ENABLE_RESEARCH = -500;

    protected Civilization(String nCivTag, int iR, int iG, int iB, int nCapitalProvinceID, int nCivID) {
        this.setCivID(nCivID);
        this.initCivilization(nCivTag, iR, iG, iB, nCapitalProvinceID);
    }

    protected Civilization(Save_Civ_GameData nCivData, int nCivID) {
        this.setCivID(nCivID);
        this.setCivName(nCivData.sCivName);
        this.civGameData = nCivData;
        this.updateCivilizationIdeology();
        this.sTagsCanForm = new ArrayList<String>();
        this.lMoveUnits = new ArrayList<Move_Units>();
        this.iMoveUnitsSize = 0;
        this.lMove_Units_Plunder = new ArrayList<Move_Units_Plunder>();
        this.iMove_Units_PlunderSize = 0;
        this.lRecruitArmy = new ArrayList<RecruitArmy_Request>();
        this.iRecruitArmySize = 0;
        this.lMigrate = new ArrayList<Move_Units>();
        this.iMigrateSize = 0;
        this.lCurrentRegroupArmyLine.clear();
        this.controlledByPlayer = false;
        this.isAvailable = true;
        this.iHappiness = 75;
        this.lEventsToRun.clear();
        this.lEventsToRun = new ArrayList<Integer>();
        this.civGameData.lEvents_DecisionsTaken.clear();
        this.civGameData.lEvents_DecisionsTaken = new ArrayList();
        this.loadFlag();
    }

    private final void initCivilization(String nCivTag, int iR, int iG, int iB, int nCapitalProvinceID) {
        this.setCivName(CFG.langManager.getCiv(nCivTag));
        this.civGameData.sCivTag = nCivTag;
        this.updateCivilizationIdeology();
        this.civGameData.iCapitalProvinceID = nCapitalProvinceID;
        if (nCapitalProvinceID >= 0) {
            CFG.game.getProvince(nCapitalProvinceID).setIsCapital(true);
        }
        this.civGameData.iR = (short)iR;
        this.civGameData.iG = (short)iG;
        this.civGameData.iB = (short)iB;
        this.civGameData.civilization_Diplomacy_GameData = new Civilization_Diplomacy_GameData();
        this.buildCivPersonality();
        this.sTagsCanForm = new ArrayList<String>();
        this.civGameData.lLoansTaken = new ArrayList();
        this.civGameData.lWarReparationsGets = new ArrayList();
        this.civGameData.lWarReparationsPay = new ArrayList();
        this.lMoveUnits = new ArrayList<Move_Units>();
        this.iMoveUnitsSize = 0;
        this.lMove_Units_Plunder = new ArrayList<Move_Units_Plunder>();
        this.iMove_Units_PlunderSize = 0;
        this.lRecruitArmy = new ArrayList<RecruitArmy_Request>();
        this.iRecruitArmySize = 0;
        this.civGameData.lRegroupArmy = new ArrayList();
        this.civGameData.iRegroupArmySize = 0;
        this.lMigrate = new ArrayList<Move_Units>();
        this.iMigrateSize = 0;
        this.lCurrentRegroupArmyLine.clear();
        this.controlledByPlayer = false;
        this.isAvailable = true;
        this.civGameData.fTechnologyLevel = 45;
        this.iHappiness = 75;
        this.lEventsToRun.clear();
        this.lEventsToRun = new ArrayList<Integer>();
        this.civGameData.lEvents_DecisionsTaken.clear();
        this.civGameData.lEvents_DecisionsTaken = new ArrayList();
        this.loadFlag();
    }

    protected final void buildCivPersonality_MoreOften() {
        this.civGameData.civPersonality.REBUILD_PERSONALITY_MORE_OFTEN = 7 + CFG.oR.nextInt(15);
        this.civGameData.civPersonality.TAXATION_LEVEL = 0.9f + (float)CFG.oR.nextInt(100) / 1000.0f;
        this.civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS = (float)CFG.oAI.getAI_Style((int)this.getAI_Style()).USE_OF_BUDGET_FOR_SPENDINGS / 100.0f + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).USE_OF_BUDGET_FOR_SPENDINGS_RANDOM * 10) / 1000.0f;
        this.civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET = 0.04f + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_GOODS_RANDOM) / 100.0f;
        this.civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET = 0.04f + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_INVESTMENTS_RANDOM) / 100.0f;
        this.civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET = 0.0f + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_RESEARCH_RANDOM) / 100.0f;
    }

    protected final void buildCivPersonality() {
        this.civGameData.civPersonality.WAR_CLOSE_REGION_PROVINCES = 3 + CFG.oR.nextInt(4);
        this.civGameData.civPersonality.WAR_CLOSE_REGION_EXTRA_SCORE = 1.115f + (float)CFG.oR.nextInt(675) / 1000.0f;
        this.civGameData.civPersonality.MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY = CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_DEFAULT + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_RANDOM) / 100.0f;
        this.buildCivPersonality_MoreOften();
        this.civGameData.civPersonality.TREASURY_RESERVE = 3.85f + (float)CFG.oR.nextInt(625) / 100.0f;
        this.civGameData.civPersonality.TREASURY_RESERVE_MODIFIER = 0.05f + (float)CFG.oR.nextInt(150) / 1000.0f;
        this.civGameData.civPersonality.PLUNDER_CHANCE = (float)CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_PLUNDER_MIN + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_PLUNDER_RANDOM) / 1000.0f;
        this.civGameData.civPersonality.WAR_POTENTIAL = 0.275f + (float)CFG.oR.nextInt(375) / 1000.0f;
        this.civGameData.civPersonality.WAR_DANGER = 0.525f + (float)CFG.oR.nextInt(525) / 1000.0f;
        this.civGameData.civPersonality.WAR_REGION_NUM_OF_PROVINCES = 0.0375f + (float)CFG.oR.nextInt(275) / 1000.0f;
        this.civGameData.civPersonality.WAR_REGION_POTENTIAL = 0.425f + (float)CFG.oR.nextInt(625) / 1000.0f;
        this.civGameData.civPersonality.WAR_NUM_OF_UNITS = 0.925f - (float)CFG.oR.nextInt(725) / 1000.0f;
        this.civGameData.civPersonality.WAR_ATTACK_NAVAL_DISTANCE = 0.695f - (float)CFG.oR.nextInt(335) / 1000.0f;
        this.civGameData.civPersonality.WAR_ATTACK_DISTANCE = 0.625f - (float)CFG.oR.nextInt(275) / 1000.0f;
        this.civGameData.civPersonality.WAR_ATTACK_SCORE_ARMY = 0.315f - (float)CFG.oR.nextInt(350) / 1000.0f;
        this.civGameData.civPersonality.WAR_ATTACK_SCORE_POTENTIAL = 0.325f - (float)CFG.oR.nextInt(350) / 1000.0f;
        this.civGameData.civPersonality.WAR_ATTACK_SCORE_WAS_CONQUERED = 0.275f - (float)CFG.oR.nextInt(725) / 1000.0f;
        this.civGameData.civPersonality.WAR_REGROUP_SPLIT_MIN = 1 + CFG.oR.nextInt(2);
        this.civGameData.civPersonality.WAR_REGROUP_SPLIT_EXTRA = 1 + CFG.oR.nextInt(3);
        this.civGameData.civPersonality.VALUABLE_POTENTIAL = 0.275f + (float)CFG.oR.nextInt(375) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_POTENTIAL_MODIFIED_OWN_LOST_PROVINCE = 1.625f + (float)CFG.oR.nextInt(1750) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_DANGER = 0.575f + (float)CFG.oR.nextInt(625) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_REGION_NUM_OF_PROVINCES = 0.025f + (float)CFG.oR.nextInt(175) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_REGION_POTENTIAL = 0.275f + (float)CFG.oR.nextInt(625) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_NUM_OF_UNITS = 0.925f - (float)CFG.oR.nextInt(425) / 1000.0f;
        this.civGameData.civPersonality.VALUABLE_NUM_OF_UNITS_RECRUITMENT = 0.0f - (float)CFG.oR.nextInt(475) / 1000.0f;
        this.civGameData.civPersonality.MIN_MILITARY_SPENDINGS = CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_MILITARY_SPENDINGS_DEFAULT + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_MILITARY_SPENDINGS_RANDOM) / 100.0f;
        this.civGameData.civPersonality.VALUABLE_RECRUIT_FROM_FAR_AWAY_CHANCE = 1 + CFG.oR.nextInt(54);
        this.civGameData.civPersonality.AGGRESSION = CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_AGGRESION_DEFAULT + (float)CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_AGGRESION_RANDOM) / 100.0f;
        this.civGameData.civPersonality.MIN_MILITARY_SPENDINGS_RECRUIT_AT_WAR = 0.905f + (float)CFG.oR.nextInt(675) / 1000.0f;
        this.civGameData.civPersonality.MIN_MILITARY_SPENDINGS_NOT_BORDERING_WITH_ENEMY = 0.49f + (float)CFG.oR.nextInt(260) / 1000.0f;
        this.civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR_MODIFIER = 1.02f + (float)CFG.oR.nextInt(165) / 1000.0f;
        this.civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV = CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_HAPPINESS_DEFAULT + CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_MIN_HAPPINESS_RANDOM);
        this.civGameData.civPersonality.MIN_HAPPINESS_CRISIS = 54 + CFG.oR.nextInt(12);
        this.civGameData.civPersonality.MIN_PROVINCE_HAPPINESS_RUN_FESTIVAL = Game_Action.RISE_REVOLT_RISK_HAPPINESS + (float)CFG.oR.nextInt(23) / 100.0f;
        this.civGameData.civPersonality.DEFENSE = 20 + CFG.oR.nextInt(40);
        this.civGameData.civPersonality.FORGIVENESS = CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_FORGIVNESS_DEFAULT + (float)(CFG.oR.nextInt(CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_FORGIVNESS_RANDOM) - CFG.oAI.getAI_Style((int)this.getAI_Style()).PERSONALITY_FORGIVNESS_RANDOM / 2) / 100.0f;
        this.civGameData.civPersonality.MIN_PROVINCE_STABILITY = this.getControlledByPlayer() ? 0.71f : 0.42f + (float)CFG.oR.nextInt(58) / 100.0f;
        this.civGameData.civPersonality.MIN_HAPPINESS_TO_ASSMILIATE_PROVINCE = 0.45f + (float)CFG.oR.nextInt(20) / 100.0f;
        this.civGameData.civPersonality.ASSIMILATE_PERC_DISTANCE_SCORE = 0.1f + (float)CFG.oR.nextInt(65) / 100.0f;
        this.civGameData.civPersonality.ASSIMILATE_PERC_LOW_STABILITY_SCORE = 0.1f + (float)CFG.oR.nextInt(65) / 100.0f;
        this.civGameData.civPersonality.ASSIMILATE_PERC_POPULATION_SCORE = 0.1f + (float)CFG.oR.nextInt(75) / 100.0f;
        this.civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_DISTANCE_SCORE = 6.95f + (float)CFG.oR.nextInt(90) / 100.0f;
        this.civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RELATION_SCORE = 32.325f + (float)CFG.oR.nextInt(28);
        this.civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RANK_SCORE = 5.15f + (float)CFG.oR.nextInt(65) / 10.0f;
        this.civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RANK_OWN_SCORE = 5.25f + (float)CFG.oR.nextInt(65) / 10.0f;
        this.civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_DEFENSIVE_PACT_SCORE = 16.25f + (float)CFG.oR.nextInt(150) / 10.0f;
        this.civGameData.civPersonality.HRE_VOTE_FOR_RANK = 14.0f + (float)CFG.oR.nextInt(16);
        this.civGameData.civPersonality.HRE_VOTE_FOR_PROVINCES = 16.0f + (float)CFG.oR.nextInt(16);
        this.civGameData.civPersonality.RESPONSE_ALLIANCE_OPINION = 32.5f + (float)CFG.oR.nextInt(220) / 10.0f;
        this.civGameData.civPersonality.RESPONSE_ALLIANCE_STRENTGH = 25.75f + (float)CFG.oR.nextInt(265) / 10.0f;
        this.civGameData.civPersonality.BUILD_MIN_STABILITY = 0.64f + (float)CFG.oR.nextInt(26) / 100.0f;
        this.civGameData.civPersonality.BUILD_STABILITY_SCORE = 0.52f + (float)CFG.oR.nextInt(36) / 100.0f;
        this.civGameData.civPersonality.BUILD_MAX_REV_RISK = 0.0f + (float)CFG.oR.nextInt(10) / 100.0f;
        this.civGameData.civPersonality.BUILD_DANGER_SCORE = 0.01f + (float)CFG.oR.nextInt(34) / 100.0f;
        this.buildCivPersonality_Buildings();
        this.buildCivPersonality_Colonization();
        this.civGameData.civPersonality.TECH_POP = 0.15f + (float)CFG.oR.nextInt(85) / 100.0f;
        this.civGameData.civPersonality.TECH_ECO = 0.15f + (float)CFG.oR.nextInt(85) / 100.0f;
        this.civGameData.civPersonality.TECH_TAXATION = 0.1f + (float)CFG.oR.nextInt(90) / 100.0f;
        this.civGameData.civPersonality.TECH_PRODUCTION = 0.1f + (float)CFG.oR.nextInt(90) / 100.0f;
        this.civGameData.civPersonality.TECH_ADMINISTARTION = 0.01f + (float)CFG.oR.nextInt(75) / 100.0f;
        this.civGameData.civPersonality.TECH_MILITARY_UPKEEP = 0.01f + (float)CFG.oR.nextInt(75) / 100.0f;
        this.civGameData.civPersonality.TECH_RESEARCH = 0.01f + (float)CFG.oR.nextInt(70) / 100.0f;
        this.civGameData.civPersonality.LIBERITY_DECLARATION = 66 + CFG.oR.nextInt(32);
        this.civGameData.civPersonality.LIBERITY_ACCEPTABLE_TRIBUTE = 0.45f + (float)CFG.oR.nextInt(40) / 100.0f;
        this.civGameData.civPersonality.VASSALS_TRIBUTE_PERC = 0.1f + (float)CFG.oR.nextInt(60) / 100.0f;
        this.civGameData.civPersonality.VASSALS_TRIBUTE_PERC_RAND = 0.05f + (float)CFG.oR.nextInt(25) / 100.0f;
        this.civGameData.civPersonality.VASSALS_TRIBUTE_PERC_FRIENDLY = 0.3f + (float)CFG.oR.nextInt(65) / 100.0f;
        this.civGameData.civPersonality.POTENTIAL_POPULATION = 24.25f + (float)CFG.oR.nextInt(9999) / 1000.0f;
        this.civGameData.civPersonality.POTENTIAL_ECONOMY = 21.5f + (float)CFG.oR.nextInt(9999) / 1000.0f;
        this.civGameData.civPersonality.DANGER_EXTRA_KEY_REGION = 1.825f + (float)CFG.oR.nextInt(325) / 1000.0f;
        this.civGameData.civPersonality.DANGER_EXTRA_PER_OWN_PROVINCE = 0.025f + (float)CFG.oR.nextInt(175) / 1000.0f;
        this.civGameData.civPersonality.DANGER_PERC_OF_UNITS = 0.35f + (float)CFG.oR.nextInt(425) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_CAPITAL = 14.25f + (float)CFG.oR.nextInt(25250) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_OWN_PROVINCE = 7.25f + (float)CFG.oR.nextInt(9500) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_MORE_NEUTRAL = 2.75f + (float)CFG.oR.nextInt(8000) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_OTHER_CIV = 2.5f + (float)CFG.oR.nextInt(8750) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_GROWTH_RATE = 41.75f + (float)CFG.oR.nextInt(50000) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_LAST_PROVINCE = 46.75f + (float)CFG.oR.nextInt(50000) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_SEA_ACCESS = 8.75f + (float)CFG.oR.nextInt(19000) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_SEA_ACCESS_EXTRA = 1.75f + (float)CFG.oR.nextInt(3500) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_NEIGHBORING_PROVINCES = 1.65f + (float)CFG.oR.nextInt(3750) / 1000.0f;
        this.civGameData.civPersonality.NEUTRAL_EXPAND_NEIGHBORING_PROVINCES_POTENITAL = 17.85f + (float)CFG.oR.nextInt(25000) / 1000.0f;
    }

    protected final void buildCivPersonality_Colonization() {
        this.civGameData.civPersonality.COLONIZATION_SEA = 4 + CFG.oR.nextInt(14) / Math.max(this.civGameData.lColonies_Founded.size(), 1);
        this.civGameData.civPersonality.COLONIZATION_OWN_PROVINCES = 30.0f + (float)CFG.oR.nextInt(40);
        this.civGameData.civPersonality.COLONIZATION_GROWTH_RATE = 12.0f + (float)CFG.oR.nextInt(11);
        this.civGameData.civPersonality.COLONIZATION_DISTANCE = 4.0f + (float)CFG.oR.nextInt(11);
    }

    protected final void buildCivPersonality_Buildings() {
        this.civGameData.civPersonality.BUILD_FORT = 0.001f + (float)CFG.oR.nextInt(21) / 100.0f;
        this.civGameData.civPersonality.BUILD_TOWER = 2.5E-4f + (float)CFG.oR.nextInt(6) / 100.0f;
        this.civGameData.civPersonality.BUILD_PORT = 0.01f + (float)CFG.oR.nextInt(18) / 100.0f;
        this.civGameData.civPersonality.BUILD_FARM = 0.1f + (float)CFG.oR.nextInt(100) / 100.0f;
        this.civGameData.civPersonality.BUILD_WORKSHOP = 0.1f + (float)CFG.oR.nextInt(95) / 100.0f;
        this.civGameData.civPersonality.BUILD_WORKSHOP_POP_SCORE = 0.51f + (float)CFG.oR.nextInt(42) / 100.0f;
        this.civGameData.civPersonality.BUILD_WORKSHOP_ECO_SCORE = 0.82f + (float)CFG.oR.nextInt(35) / 100.0f;
        this.civGameData.civPersonality.BUILD_LIBRARY = 0.075f + (float)CFG.oR.nextInt(90) / 100.0f;
        this.civGameData.civPersonality.BUILD_ARMOURY = 0.02f + (float)CFG.oR.nextInt(30) / 100.0f;
        this.civGameData.civPersonality.BUILD_ARMOURY_RECRUITABLE_SCORE = 0.375f + (float)CFG.oR.nextInt(325) / 1000.0f;
        this.civGameData.civPersonality.BUILD_SUPPLYLINE = 0.01f + (float)CFG.oR.nextInt(20) / 100.0f;
        this.civGameData.civPersonality.BUILD_INVEST = 0.1275f + (float)CFG.oR.nextInt(100) / 100.0f;
        this.civGameData.civPersonality.BUILD_INVEST_DEVELOPMENT = 0.0125f + (float)CFG.oR.nextInt(75) / 100.0f;
        this.civGameData.civPersonality.BUILD_INVEST_POP_SCORE = 0.175f + (float)CFG.oR.nextInt(275) / 1000.0f;
        this.civGameData.civPersonality.BUILD_INVEST_DEVELOPMENT_SCORE = 0.15f + (float)CFG.oR.nextInt(275) / 1000.0f;
        this.civGameData.civPersonality.BUILD_INVEST_POP_ECO_DIFFERENCE_SCORE = 0.875f + (float)CFG.oR.nextInt(2825) / 1000.0f;
        this.civGameData.civPersonality.BUILD_INVEST_SECOND_INVEST_MAX_PERC = 0 + CFG.oR.nextInt(40);
        this.civGameData.civPersonality.BUILD_INVEST_SECOND_INVEST_CHANCE = 45 + CFG.oR.nextInt(55);
        this.civGameData.civPersonality.BUILD_RESRVE_RAND = 1 + CFG.oR.nextInt(4);
    }

    protected final void createCivilizationRegion(int nProvinceID) {
        this.lCivRegions.add(new Civilization_Region(nProvinceID, this.iCivRegionsSize));
        this.iCivRegionsSize = this.lCivRegions.size();
        CFG.game.getProvince(nProvinceID).setCivRegionID(this.iCivRegionsSize - 1);
        CFG.game.getProvince((int)nProvinceID).was = true;
        this.buildCivilizationRegion(nProvinceID, this.iCivRegionsSize - 1);
        int i = 0;
        while (i < this.getNumOfProvinces()) {
            CFG.game.getProvince((int)this.getProvinceID((int)i)).was = false;
            ++i;
        }
    }

    private final void buildCivilizationRegion(int nProvinceID, int nCivRegionID) {
        int i = 0;
        while (i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize()) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == this.iCivID && !CFG.game.getProvince((int)CFG.game.getProvince((int)nProvinceID).getNeighboringProvinces((int)i)).was) {
                CFG.game.getProvince((int)CFG.game.getProvince((int)nProvinceID).getNeighboringProvinces((int)i)).was = true;
                this.lCivRegions.get(nCivRegionID).addProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i));
                CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).setCivRegionID(nCivRegionID);
                this.buildCivilizationRegion(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i), nCivRegionID);
            }
            ++i;
        }
    }

    protected final boolean civRegionsContainsProvince(int nProvinceID) {
        int i = 0;
        while (i < this.iCivRegionsSize) {
            if (this.lCivRegions.get(i).containsProvince(nProvinceID)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    private final void removeCivRegionID(int id) {
        int i;
        for (i = 0; i < this.lCivRegions.get(id).getProvincesSize(); ++i) {
            CFG.game.getProvince(this.lCivRegions.get(id).getProvince(i)).setCivRegionID(-1);
        }
        this.lCivRegions.remove(id);
        this.iCivRegionsSize = this.lCivRegions.size();
        i = 0;
        while (i < this.iCivRegionsSize) {
            this.lCivRegions.get(i).setRegionID(i);
            ++i;
        }
    }

    protected final void clearCivRegions() {
        for(int i = 0; i < this.getNumOfProvinces(); ++i) {
            CFG.game.getProvince(this.getProvinceID(i)).setCivRegionID(-1);
        }

        this.lCivRegions.clear();
        this.iCivRegionsSize = 0;
    }

    protected final void updateCivilizationColor(final int iR, final int iG, final int iB, final int amount) {
        switch (amount) {
            case 0: {
                this.civGameData.iR = (short) iR;
                this.civGameData.iG = (short) iG;
                this.civGameData.iB = (short) iB;
                break;
            }
            case 1: {
                this.civGameData.iR = (short) (iR / 1.2);
                this.civGameData.iG = (short) (iG / 1.2);
                this.civGameData.iB = (short) (iB / 1.2);
                break;
            }
            case 2: {
                this.civGameData.iR = (short) (iR / 1.6);
                this.civGameData.iG = (short) (iG / 1.6);
                this.civGameData.iB = (short) (iB / 1.6);
                break;
            }
            default: {
                CFG.palletManager.loadCivilizationStandardColor(this.iCivID);
                break;
            }
        }

        //clear civtag, change ideology
        //String tempCivTag = "" + this.getCivTag();
        //this.setCivTag("ran");
        //CFG.game.updateCivilizationIdeology(this.iCivID, tempCivTag);
    }

    protected final void updateVassalCivilizationsColor() {
        //update vassal colors
        for (int i = 0; i < this.civGameData.iVassalsSize; i++) {
            if (CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getVassal_AutonomyStatus(this.iCivID) != null && CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getVassal_AutonomyStatus(this.iCivID).getColorStatus() > 2) continue;
            //don't apply if dominion
            CFG.game.getCiv(this.civGameData.lVassals.get(i).iCivID).updateCivilizationColor(this.civGameData.iR, this.civGameData.iG, this.civGameData.iB, this.getVassal_AutonomyStatus(this.civGameData.lVassals.get(i).iCivID).getColorStatus());
        }
    }


    protected final void updateCivilizationIdeology(String nCivTag, int iR, int iG, int iB) {
        this.setCivTag(nCivTag);
        //if not puppet, or puppet but color more than 2, change
        if (!this.getIsPupet() || (CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getVassal_AutonomyStatus(this.iCivID).getColorStatus() > 2)) {
            this.civGameData.iR = (short) iR;
            this.civGameData.iG = (short) iG;
            this.civGameData.iB = (short) iB;
        }

        //update vassal colors
        this.updateVassalCivilizationsColor();

        this.updateCivilizationIdeology();
        this.loadFlag();
    }

    protected final void updateCivilizationIdeology() {
        this.setIdeologyID(CFG.ideologiesManager.getIdeologyID(this.getCivTag()));
    }

    protected final void buildDiplomacy(boolean buildRelations) {
        int i;
        if (buildRelations) {
            this.civGameData.lRelation.clear();
            for (i = 1; i < CFG.game.getCivsSize(); ++i) {
                this.civGameData.lRelation.add(Float.valueOf(0.0f));
            }
        }
        this.lGuarantee.clear();
        this.lMilitirayAccess.clear();
        this.lNonAggressionPact.clear();
        this.lTruce.clear();
        this.lDefensivePact.clear();
        this.lOpt_Truce.clear();
        this.lOpt_MilitirayAccess.clear();
        this.lOpt_DefensivePact.clear();
        this.lOpt_Guarantee.clear();
        this.lOpt_NonAggressionPact.clear();
        if (buildRelations) {
            this.civGameData.iAllianceID = 0;
        }
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.lGuarantee.add((byte)0);
            this.lMilitirayAccess.add((byte)0);
        }
        i = this.iCivID + 1;
        while (i < CFG.game.getCivsSize()) {
            this.lNonAggressionPact.add(0);
            this.lTruce.add(0);
            this.lDefensivePact.add(0);
            ++i;
        }
    }

    protected final void updateDiplomacy_AfterRemoveCivilization_Relations(int nIDToRemove) {
        int i;
        this.civGameData.lRelation.remove(nIDToRemove);
        this.lGuarantee.remove(nIDToRemove);
        this.lMilitirayAccess.remove(nIDToRemove);
        for (i = 0; i < this.lOpt_Guarantee.size(); ++i) {
            if (this.lOpt_Guarantee.get(i) != nIDToRemove) continue;
            this.lOpt_Guarantee.remove(i);
            break;
        }
        i = 0;
        while (i < this.lOpt_MilitirayAccess.size()) {
            if (this.lOpt_MilitirayAccess.get(i) == nIDToRemove) {
                this.lOpt_MilitirayAccess.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void updateDiplomacy_AfterRemoveCivilization(int nIDToRemove) {
        int i;
        this.lNonAggressionPact.remove(nIDToRemove);
        this.lTruce.remove(nIDToRemove);
        this.lDefensivePact.remove(nIDToRemove);
        for (i = 0; i < this.lOpt_Truce.size(); ++i) {
            if (this.lOpt_Truce.get(i) != nIDToRemove) continue;
            this.lOpt_Truce.remove(i);
            break;
        }
        for (i = 0; i < this.lOpt_DefensivePact.size(); ++i) {
            if (this.lOpt_DefensivePact.get(i) != nIDToRemove) continue;
            this.lOpt_DefensivePact.remove(i);
            break;
        }
        i = 0;
        while (i < this.lOpt_NonAggressionPact.size()) {
            if (this.lOpt_NonAggressionPact.get(i) == nIDToRemove) {
                this.lOpt_NonAggressionPact.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void updateDiplomacy_AfterAddingCivilization() {
        this.civGameData.lRelation.add(Float.valueOf(0.0f));
        this.lGuarantee.add((byte)0);
        this.lMilitirayAccess.add((byte)0);
        this.lNonAggressionPact.add(0);
        this.lTruce.add(0);
        this.lDefensivePact.add(0);
    }

    protected final void newMove(int fromProvinceID, int toProvinceID, int nNumOfUnits, boolean buildLine) {
        this.lMoveUnits.add(new Move_Units(fromProvinceID, toProvinceID, nNumOfUnits, buildLine));
        this.iMoveUnitsSize = this.lMoveUnits.size();
    }

    protected final void removeMove(int i) {
        this.lMoveUnits.remove(i);
        this.iMoveUnitsSize = this.lMoveUnits.size();
    }

    protected final void clearMoveUnits() {
        this.lMoveUnits.clear();
        this.iMoveUnitsSize = this.lMoveUnits.size();
    }

    protected final void newMigrate(int fromProvinceID, int toProvinceID, boolean buildLine) {
        for (int i = 0; i < this.iMigrateSize; ++i) {
            if (this.lMigrate.get(i).getFromProvinceID() != fromProvinceID) continue;
            this.removeMigrate(i);
            this.setMovePoints(this.getMovePoints() + CFG.ideologiesManager.getIdeology((int)this.getIdeologyID()).COST_OF_MOVE);
            break;
        }
        this.lMigrate.add(new Move_Units(fromProvinceID, toProvinceID, CFG.game.getProvince(fromProvinceID).getPopulationData().getPopulation(), buildLine, true));
        this.iMigrateSize = this.lMigrate.size();
    }

    protected final void removeMigrate(int i) {
        this.lMigrate.remove(i);
        this.iMigrateSize = this.lMigrate.size();
    }

    protected final void clearMigrate() {
        this.lMigrate.clear();
        this.iMigrateSize = this.lMigrate.size();
    }

    protected final boolean migratesFromProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.iMigrateSize) {
            if (this.lMigrate.get(i).getFromProvinceID() == nProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final void newPlunder(int fromProvinceID, int nNumOfUnits) {
        for(int i = 0; i < this.iMove_Units_PlunderSize; ++i) {
            if (((Move_Units_Plunder)this.lMove_Units_Plunder.get(i)).getFromProvinceID() == fromProvinceID) {
                ((Move_Units_Plunder)this.lMove_Units_Plunder.get(i)).setNumOfUnits(nNumOfUnits);
                return;
            }
        }

        this.lMove_Units_Plunder.add(new Move_Units_Plunder(fromProvinceID, nNumOfUnits));
        this.iMove_Units_PlunderSize = this.lMove_Units_Plunder.size();
    }

    protected final void removePlunder(int i) {
        this.lMove_Units_Plunder.remove(i);
        this.iMove_Units_PlunderSize = this.lMove_Units_Plunder.size();
    }

    protected final void removePlunder_ProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.iMove_Units_PlunderSize) {
            if (this.lMove_Units_Plunder.get(i).getFromProvinceID() == nProvinceID) {
                CFG.game.getProvince(this.lMove_Units_Plunder.get(i).getFromProvinceID()).updateArmy(this.getCivID(), CFG.game.getProvince(this.lMove_Units_Plunder.get(i).getFromProvinceID()).getArmyCivID(this.getCivID()) + this.lMove_Units_Plunder.get(i).getNumOfUnits());
                this.lMove_Units_Plunder.remove(i);
                this.iMove_Units_PlunderSize = this.lMove_Units_Plunder.size();
                return;
            }
            ++i;
        }
    }

    protected final void clearMoveUnits_Plunder() {
        this.lMove_Units_Plunder.clear();
        this.iMove_Units_PlunderSize = this.lMove_Units_Plunder.size();
    }

    protected final boolean isPlundred(int nProvinceID) {
        int i = 0;
        while (i < this.iMove_Units_PlunderSize) {
            if (this.lMove_Units_Plunder.get(i).getFromProvinceID() == nProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final boolean addFestival(CivFestival nFestival) {
        for(int i = 0; i < this.civGameData.lFestivals.size(); ++i) {
            if (nFestival.iProvinceID == ((CivFestival)this.civGameData.lFestivals.get(i)).iProvinceID) {
                return false;
            }
        }

        this.civGameData.lFestivals.add(nFestival);
        return true;
    }

    protected final CivFestival getFestival(int i) {
        return (CivFestival)this.civGameData.lFestivals.get(i);
    }

    protected final void removeFestival(int i) {
        this.civGameData.lFestivals.remove(i);
    }

    protected final void removeFestival_ProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lFestivals.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID) {
                this.civGameData.lFestivals.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void runFestivals() {
        int i = 0;
        while (i < this.civGameData.lFestivals.size()) {
            if (CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).getCivID() == this.getCivID()) {
                --((CivFestival)this.civGameData.lFestivals.get((int)i)).iTurnsLeft;
                CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).setHappiness(CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).getHappiness() + DiplomacyManager.festivalHappinessPerTurn((int)((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID));
                for (int j = 0; j < CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).getNeighboringProvincesSize(); ++j) {
                    CFG.game.getProvince(CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).getNeighboringProvinces(j)).setHappiness(CFG.game.getProvince(CFG.game.getProvince(((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID).getNeighboringProvinces(j)).getHappiness() + DiplomacyManager.festivalHappinessPerTurn_NeighboringProvinces());
                }
                if (((CivFestival)this.civGameData.lFestivals.get((int)i)).iTurnsLeft <= 0) {
                    CFG.game.getCiv((int)this.iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_FestivalIsOver(this.iCivID, ((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID));
                    this.civGameData.lFestivals.remove(i--);
                }
            } else {
                this.civGameData.lFestivals.remove(i--);
            }
            ++i;
        }
    }

    protected final boolean isFestivalOrganized(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lFestivals.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final CivFestival isFestivalOrganized_GET(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lFestivals.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID) {
                return (CivFestival)this.civGameData.lFestivals.get(i);
            }
            ++i;
        }
        return null;
    }

    protected final int isFestivalOrganized_TurnsLeft(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lFestivals.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lFestivals.get((int)i)).iProvinceID) {
                return ((CivFestival)this.civGameData.lFestivals.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final int getFestivalsSize() {
        return this.civGameData.lFestivals.size();
    }

    protected final boolean addAssimilate(CivFestival nFestival) {
        for(int i = 0; i < this.civGameData.lAssimilates.size(); ++i) {
            if (nFestival.iProvinceID == ((CivFestival)this.civGameData.lAssimilates.get(i)).iProvinceID) {
                return false;
            }
        }

        this.civGameData.lAssimilates.add(nFestival);
        return true;
    }

    protected final CivFestival getAssimilate(int i) {
        return (CivFestival)this.civGameData.lAssimilates.get(i);
    }

    protected final void removeAssimilate(int i) {
        this.civGameData.lAssimilates.remove(i);
    }

    protected final void removeAssimilate_ProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lAssimilates.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID) {
                this.civGameData.lAssimilates.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void runAssimilates() {
        int i = 0;
        while (i < this.civGameData.lAssimilates.size()) {
            if (CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getCivID() == this.getCivID()) {
                --((CivFestival)this.civGameData.lAssimilates.get((int)i)).iTurnsLeft;
                int popToAssimilate = 0;
                int ownerPop = 1 + CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getPopulationOfCivID(this.getCivID());
                for (int j = 0; j < CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getNationalitiesSize(); ++j) {
                    if (CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getCivID(j) == CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getCivID()) continue;
                    popToAssimilate += CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getPopulationID(j);
                }
                int assimilatedPop = 0;
                int tCurrentPopChange = 0;
                for (int j = CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getNationalitiesSize() - 1; j >= 0; --j) {
                    if (CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getCivID(j) == CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getCivID()) continue;
                    float tPerc = (0.00425f + (0.04971f + (float)CFG.oR.nextInt(1087) / 10000.0f) * ((float)ownerPop / (float)(popToAssimilate + ownerPop)) * CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getHappiness() * Math.min(1.0f - CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getDevelopmentLevel() / 3.75f, 1.0f)) * (1.0f - 0.225f * (1.0f - CFG.game.getCiv(this.getCivID()).getStability()) - 0.075f * CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getRevolutionaryRisk()) * 0.8f;
                    tCurrentPopChange = (int)((float)CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getPopulationID(j) * tPerc);
                    if (tCurrentPopChange == 0) {
                        tCurrentPopChange = CFG.oR.nextInt(2);
                    }
                    assimilatedPop += tCurrentPopChange;
                    CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getCivID(j), CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getPopulationID(j) - tCurrentPopChange);
                }
                CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().setPopulationOfCivID(this.getCivID(), CFG.game.getProvince(((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID).getPopulationData().getPopulationOfCivID(this.getCivID()) + assimilatedPop);
                if (((CivFestival)this.civGameData.lAssimilates.get((int)i)).iTurnsLeft <= 0) {
                    CFG.game.getCiv((int)this.iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_AssimilationEnd(this.iCivID, ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID));
                    this.civGameData.lAssimilates.remove(i--);
                }
            } else {
                this.civGameData.lAssimilates.remove(i--);
            }
            ++i;
        }
    }

    protected final CivFestival isAssimilateOrganized_GET(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lAssimilates.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID) {
                return (CivFestival)this.civGameData.lAssimilates.get(i);
            }
            ++i;
        }
        return null;
    }

    protected final boolean isAssimilateOrganized(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lAssimilates.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final int isAssimialateOrganized_TurnsLeft(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lAssimilates.size()) {
            if (nProvinceID == ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iProvinceID) {
                return ((CivFestival)this.civGameData.lAssimilates.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final int getAssimilatesSize() {
        return this.civGameData.lAssimilates.size();
    }

    protected final boolean addInvest(CivInvest nInvest) {
        for(int i = 0; i < this.civGameData.lInvest.size(); ++i) {
            if (nInvest.iProvinceID == ((CivInvest)this.civGameData.lInvest.get(i)).iProvinceID) {
                return false;
            }
        }

        this.civGameData.lInvest.add(nInvest);
        return true;
    }

    protected final CivInvest getInvest(int i) {
        return (CivInvest)this.civGameData.lInvest.get(i);
    }

    protected final void removeInvest(int i) {
        this.civGameData.lInvest.remove(i);
    }

    protected final void removeInvest_ProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (nProvinceID == ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID) {
                this.civGameData.lInvest.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void runInvests() {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (CFG.game.getProvince(((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID).getCivID() == this.getCivID()) {
                --((CivInvest)this.civGameData.lInvest.get((int)i)).iTurnsLeft;
                int ecoToAdd = Math.min(((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyPerTurn, ((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyLeft);
                if (((CivInvest)this.civGameData.lInvest.get((int)i)).iTurnsLeft == 0) {
                    ecoToAdd = ((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyLeft;
                }
                CFG.game.getProvince(((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID).setEconomy(CFG.game.getProvince(((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID).getEconomy() + ecoToAdd);
                ((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyLeft -= ecoToAdd;
                if (((CivInvest)this.civGameData.lInvest.get((int)i)).iTurnsLeft <= 0 || ((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyLeft <= 0) {
                    CFG.game.getCiv((int)this.iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_InvestDone(this.iCivID, ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID));
                    this.civGameData.lInvest.remove(i--);
                }
            } else {
                this.civGameData.lInvest.remove(i--);
            }
            ++i;
        }
    }

    protected final boolean isInvestOrganized(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (nProvinceID == ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final int isInvestOrganized_TurnsLeft(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (nProvinceID == ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID) {
                return ((CivInvest)this.civGameData.lInvest.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final CivInvest isInvestOrganized_GET(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (nProvinceID == ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID) {
                return (CivInvest)this.civGameData.lInvest.get(i);
            }
            ++i;
        }
        return null;
    }

    protected final int isInvestOrganized_EconomyLeft(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest.size()) {
            if (nProvinceID == ((CivInvest)this.civGameData.lInvest.get((int)i)).iProvinceID) {
                return ((CivInvest)this.civGameData.lInvest.get((int)i)).iEconomyLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final int getInvestsSize() {
        return this.civGameData.lInvest.size();
    }

    protected final boolean addInvest_Development(CivInvest_Development nInvest) {
        for(int i = 0; i < this.civGameData.lInvest_Development.size(); ++i) {
            if (nInvest.iProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get(i)).iProvinceID) {
                return false;
            }
        }

        this.civGameData.lInvest_Development.add(nInvest);
        return true;
    }

    protected final CivInvest_Development getInvest_Development(int i) {
        return (CivInvest_Development)this.civGameData.lInvest_Development.get(i);
    }

    protected final void removeInvest_Development(int i) {
        this.civGameData.lInvest_Development.remove(i);
    }

    protected final void removeInvest_ProvinceID_Development(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (nProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID) {
                this.civGameData.lInvest_Development.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final void runInvests_Development() {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (CFG.game.getProvince(((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID).getCivID() == this.getCivID()) {
                --((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iTurnsLeft;
                float ecoToAdd = Math.min(((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntPerTurn, ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntLeft);
                if (((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iTurnsLeft == 0) {
                    ecoToAdd = ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntLeft;
                }
                CFG.game.getProvince(((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID).setDevelopmentLevel(CFG.game.getProvince(((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID).getDevelopmentLevel() + ecoToAdd);
                ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntLeft -= ecoToAdd;
                if (((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iTurnsLeft <= 0 || ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntLeft <= 0.0f) {
                    CFG.game.getCiv((int)this.iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_InvestDone_Development(this.iCivID, ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID));
                    this.civGameData.lInvest_Development.remove(i--);
                }
            } else {
                this.civGameData.lInvest_Development.remove(i--);
            }
            ++i;
        }
    }

    protected final boolean isInvestOrganized_Development(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (nProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final int isInvestOrganized_TurnsLeft_Development(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (nProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID) {
                return ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final CivInvest_Development isInvestOrganized_GET_Development(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (nProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID) {
                return (CivInvest_Development)this.civGameData.lInvest_Development.get(i);
            }
            ++i;
        }
        return null;
    }

    protected final float isInvestOrganized_EconomyLeft_Development(int nProvinceID) {
        int i = 0;
        while (i < this.civGameData.lInvest_Development.size()) {
            if (nProvinceID == ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iProvinceID) {
                return ((CivInvest_Development)this.civGameData.lInvest_Development.get((int)i)).iDevelopemntLeft;
            }
            ++i;
        }
        return 0.0f;
    }

    protected final int getInvestsSize_Development() {
        return this.civGameData.lInvest_Development.size();
    }

    protected final void addNewConstruction(Construction_GameData nConstruction) {
        for(int i = 0; i < this.civGameData.lConstructions.size(); ++i) {
            if (((Construction_GameData)this.civGameData.lConstructions.get(i)).iProvinceID == nConstruction.iProvinceID && ((Construction_GameData)this.civGameData.lConstructions.get(i)).constructionType == nConstruction.constructionType) {
                return;
            }
        }

        this.civGameData.lConstructions.add(nConstruction);
    }

    protected final int getConstructionsSize() {
        return this.civGameData.lConstructions.size();
    }

    protected final Construction_GameData getConstruction(int i) {
        return (Construction_GameData)this.civGameData.lConstructions.get(i);
    }

    protected final void runConstruction() {
        int i = 0;
        while (i < this.civGameData.lConstructions.size()) {
            if (CFG.game.getProvince(((Construction_GameData)this.civGameData.lConstructions.get((int)i)).iProvinceID).getCivID() != this.getCivID()) {
                this.civGameData.lConstructions.remove(i--);
            } else {
                --((Construction_GameData)this.civGameData.lConstructions.get((int)i)).iNumOfTurnsLeft;
                if (((Construction_GameData)this.civGameData.lConstructions.get((int)i)).iNumOfTurnsLeft <= 0) {
                    ((Construction_GameData)this.civGameData.lConstructions.get(i)).onConstructed(this.getCivID());
                    this.civGameData.lConstructions.remove(i--);
                }
            }
            ++i;
        }
    }

    protected final int isInConstruction(int nProvinceID, ConstructionType nType) {
        int i = 0;
        while (i < this.civGameData.lConstructions.size()) {
            if (((Construction_GameData)this.civGameData.lConstructions.get((int)i)).iProvinceID == nProvinceID && ((Construction_GameData)this.civGameData.lConstructions.get((int)i)).constructionType == nType) {
                return ((Construction_GameData)this.civGameData.lConstructions.get((int)i)).iNumOfTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final void clearConstructions() {
        this.civGameData.lConstructions.clear();
    }

    protected final void recruitArmy_NewTurn() {
        for(int i = 0; i < this.iRecruitArmySize; ++i) {
            try {
                if (CFG.game.getProvince(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID()).getCivID() == this.getCivID()) {
                    CFG.gameAction.recruitArmy(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID(), ((RecruitArmy_Request)this.lRecruitArmy.get(i)).getArmy(), this.getCivID());
                } else if (CFG.game.getCiv(CFG.game.getProvince(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID()).getCivID()).getPuppetOfCivID() == this.iCivID && (!this.getVassal_AutonomyStatus(CFG.game.getProvince(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID()).getCivID()).isMilitaryControl())) {
                    CFG.gameAction.recruitArmy(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID(), ((RecruitArmy_Request)this.lRecruitArmy.get(i)).getArmy(), this.getCivID());
                } else {
                    CFG.game.getCiv(this.getCivID()).setMoney(CFG.game.getCiv(this.getCivID()).getMoney() + (long)((int)((float)(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getArmy() * CFG.getCostOfRecruitArmyMoney(((RecruitArmy_Request)this.lRecruitArmy.get(i)).getProvinceID())) * 0.725F)));
                }
            } catch (IllegalArgumentException var3) {
            } catch (IndexOutOfBoundsException var4) {
            }
        }

        this.clearRecruitArmy();
    }

    protected final boolean recruitArmy_AI(int nProvinceID, int nArmy) {
        int i = 0;
        while (i < this.iRecruitArmySize) {
            if (this.lRecruitArmy.get(i).getProvinceID() == nProvinceID) {
                return this.recruitArmy(nProvinceID, Math.max(this.lRecruitArmy.get(i).getArmy(), nArmy));
            }
            ++i;
        }
        return this.recruitArmy(nProvinceID, nArmy);
    }

    protected final boolean recruitArmy(int nProvinceID, int nArmy) {
        int i;
        block7: {
            if (CFG.game.getProvince(nProvinceID).getCivID() != CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince()) {
                if (!CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIsPupet() || (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(CFG.game.getProvince(nProvinceID).getCivID()).isMilitaryControl())) {
                    Gdx.app.log("AoC", "recruitArmy: NOT TRUE OWNER, OCCUPIED PROVINCE -> " + CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCivName() + ", " + CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince()).getCivName());
                    return false;
                }
            }
            if (nArmy >= CFG.gameAction.getRecruitableArmy(nProvinceID)) {
                nArmy = CFG.gameAction.getRecruitableArmy(nProvinceID);
            }
            for (i = 0; i < this.iRecruitArmySize; ++i) {
                if (this.lRecruitArmy.get(i).getProvinceID() != nProvinceID) continue;
                if (nArmy == 0 && this.lRecruitArmy.get(i).getArmy() > 0) {
                    CFG.game.getCiv(this.getCivID()).setMovePoints(CFG.game.getCiv(this.getCivID()).getMovePoints() + CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)this.getCivID()).getIdeologyID()).COST_OF_RECRUIT);
                    CFG.game.getCiv(this.getCivID()).setMoney(CFG.game.getCiv(this.getCivID()).getMoney() + (long)(this.lRecruitArmy.get(i).getArmy() * CFG.getCostOfRecruitArmyMoney((int)nProvinceID)));
                    this.removeRecruitArmy(i);
                    return true;
                }
                break block7;
            }
            if (CFG.game.getCiv(this.getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)this.getCivID()).getIdeologyID()).COST_OF_RECRUIT) {
                Gdx.app.log("AoC", "RECRUIT NO MOVEMNETS POINTS 1111");
                return false;
            }
            if ((long)nArmy >= CFG.game.getCiv(this.getCivID()).getMoney() / (long)CFG.getCostOfRecruitArmyMoney((int)nProvinceID)) {
                nArmy = (int)CFG.game.getCiv(this.getCivID()).getMoney() / CFG.getCostOfRecruitArmyMoney((int)nProvinceID);
            }
            if (nArmy <= 0) {
                return false;
            }
            CFG.game.getCiv(this.getCivID()).setMovePoints(CFG.game.getCiv(this.getCivID()).getMovePoints() - CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)this.getCivID()).getIdeologyID()).COST_OF_RECRUIT);
            CFG.game.getCiv(this.getCivID()).setMoney(CFG.game.getCiv(this.getCivID()).getMoney() - (long)(nArmy * CFG.getCostOfRecruitArmyMoney((int)nProvinceID)));
            this.lRecruitArmy.add(new RecruitArmy_Request(nProvinceID, nArmy));
            this.iRecruitArmySize = this.lRecruitArmy.size();
            return true;
        }
        int tDiff = this.lRecruitArmy.get(i).getArmy() - nArmy;
        this.lRecruitArmy.get(i).setArmy(nArmy);
        CFG.game.getCiv(this.getCivID()).setMoney(CFG.game.getCiv(this.getCivID()).getMoney() + (long)(tDiff * CFG.getCostOfRecruitArmyMoney((int)nProvinceID)));
        return true;
    }

    protected final void removeRecruitArmy(int i) {
        this.lRecruitArmy.remove(i);
        this.iRecruitArmySize = this.lRecruitArmy.size();
    }

    protected final void clearRecruitArmy() {
        this.lRecruitArmy.clear();
        this.iRecruitArmySize = this.lRecruitArmy.size();
    }

    protected final int isRecruitingArmyInProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.iRecruitArmySize) {
            if (this.lRecruitArmy.get(i).getProvinceID() == nProvinceID) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    protected final void buildRegroupLines_AfterLoading() {
        for (int j = 0; j < this.civGameData.iRegroupArmySize; ++j) {
            final List<MoveUnits_Line> tMoveUnitsLine = new ArrayList<MoveUnits_Line>();
            tMoveUnitsLine.add(new MoveUnits_Line_Highlighted(this.civGameData.lRegroupArmy.get(j).getFromProvinceID(), this.civGameData.lRegroupArmy.get(j).getRoute(0)));
            for (int i = 0; i < this.civGameData.lRegroupArmy.get(j).getRouteSize() - 1; ++i) {
                tMoveUnitsLine.add(new MoveUnits_Line_Highlighted(this.civGameData.lRegroupArmy.get(j).getRoute(i), this.civGameData.lRegroupArmy.get(j).getRoute(i + 1)));
            }
            this.lCurrentRegroupArmyLine.add(tMoveUnitsLine);
        }
    }

    protected final void addRegroupArmy(final RegroupArmy_Data nData) {
        this.civGameData.lRegroupArmy.add(nData);
        this.civGameData.iRegroupArmySize = this.civGameData.lRegroupArmy.size();
        final List<MoveUnits_Line> tMoveUnitsLine = new ArrayList<MoveUnits_Line>();
        tMoveUnitsLine.add(new MoveUnits_Line_Highlighted(nData.getFromProvinceID(), nData.getRoute(0)));
        for (int i = 0; i < nData.getRouteSize() - 1; ++i) {
            tMoveUnitsLine.add(new MoveUnits_Line_Highlighted(nData.getRoute(i), nData.getRoute(i + 1)));
        }
        this.lCurrentRegroupArmyLine.add(tMoveUnitsLine);
    }

    protected final void moveRegroupArmy() {
        for (int i = 0; i < this.civGameData.iRegroupArmySize; ++i) {
            try {
                if (!RegroupArmy_Data.canBeUsedInPath(this.getCivID(), this.civGameData.lRegroupArmy.get(i).getRoute(0), false, this.civGameData.lRegroupArmy.get(i).getToProvinceID())) {
                    this.removeRegroupArmy(i);
                    --i;
                }
                else if (!this.civGameData.lRegroupArmy.get(i).continueMovingArmy(this.getCivID())) {
                    this.removeRegroupArmy(i);
                    --i;
                }
                else if (this.civGameData.lRegroupArmy.get(i).getObsolate() < 0) {
                    this.removeRegroupArmy(i);
                    --i;
                }
                else {
                    this.civGameData.lRegroupArmy.get(i).updateObsolate();
                    if (CFG.game.getProvince(this.civGameData.lRegroupArmy.get(i).getFromProvinceID()).getArmyCivID(this.getCivID()) <= this.civGameData.lRegroupArmy.get(i).getNumOfUnits()) {
                        if (CFG.game.getProvince(this.civGameData.lRegroupArmy.get(i).getFromProvinceID()).getArmyCivID(this.getCivID()) <= 0) {
                            this.removeRegroupArmy(i);
                            --i;
                            continue;
                        }
                        this.civGameData.lRegroupArmy.get(i).setNumOfUnits(CFG.game.getProvince(this.civGameData.lRegroupArmy.get(i).getFromProvinceID()).getArmyCivID(this.getCivID()));
                    }
                    if (CFG.gameAction.moveArmy(this.civGameData.lRegroupArmy.get(i).getFromProvinceID(), this.civGameData.lRegroupArmy.get(i).getRoute(0), this.civGameData.lRegroupArmy.get(i).getNumOfUnits(), this.getCivID(), true, true)) {
                        this.civGameData.lRegroupArmy.get(i).setFromProvinceID(this.civGameData.lRegroupArmy.get(i).getRoute(0));
                        this.civGameData.lRegroupArmy.get(i).removeRoute(0);
                        this.lCurrentRegroupArmyLine.get(i).remove(0);
                        if (this.civGameData.lRegroupArmy.get(i).getRouteSize() == 0) {
                            this.removeRegroupArmy(i);
                            --i;
                        }
                    }
                }
            }
            catch (final IndexOutOfBoundsException ex) {
                this.removeRegroupArmy(i);
                --i;
            }
            catch (final NullPointerException ex2) {
                this.removeRegroupArmy(i);
                --i;
            }
        }
    }

    protected final void removeRegroupArmy(int i) {
        this.civGameData.lRegroupArmy.remove(i);
        this.lCurrentRegroupArmyLine.remove(i);
        this.civGameData.iRegroupArmySize = this.civGameData.lRegroupArmy.size();
    }

    protected final void clearRegroupArmy() {
        this.civGameData.lRegroupArmy.clear();
        this.lCurrentRegroupArmyLine.clear();
        this.civGameData.iRegroupArmySize = this.civGameData.lRegroupArmy.size();
    }

    protected final void addProvince_Just(int nProvinceID) {
        for(int i = 0; i < this.iNumOfProvinces; ++i) {
            if ((Integer)this.lProvinces.get(i) == nProvinceID) {
                return;
            }
        }

        this.lProvinces.add(nProvinceID);
        this.iNumOfProvinces = this.lProvinces.size();
    }

    protected final void addProvince(int nProvinceID) {
        for(int i = 0; i < this.iNumOfProvinces; ++i) {
            if ((Integer)this.lProvinces.get(i) == nProvinceID) {
                return;
            }
        }

        this.lProvinces.add(nProvinceID);
        this.iNumOfProvinces = this.lProvinces.size();
        CFG.game.getProvince(nProvinceID).setCivRegionID(-1);
    }

    protected final void removeProvince(int nProvinceID) {
        for (int i = 0; i < this.iNumOfProvinces; ++i) {
            if (this.lProvinces.get(i) != nProvinceID) continue;
            this.lProvinces.remove(i);
            this.iNumOfProvinces = this.lProvinces.size();
            break;
        }
        CFG.game.getProvince(nProvinceID).setCivRegionID(-1);
    }

    protected final void clearProvinces_FillTheMap(boolean addCapital) {
        this.lCivRegions.clear();
        this.iCivRegionsSize = 0;
        this.lProvinces.clear();
        if (addCapital) {
            this.lProvinces.add(this.getCapitalProvinceID());
            this.iNumOfProvinces = this.lProvinces.size();
            this.createCivilizationRegion(this.getCapitalProvinceID());
        } else {
            this.iNumOfProvinces = this.lProvinces.size();
        }
    }

    protected final int getProvinceID(int i) {
        try {
            return this.lProvinces.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            if (!CFG.LOGS) return -1;
            CFG.exceptionStack((Throwable)ex);
            return -1;
        }
    }

    protected final boolean controlsProvince(int nProvinceID) {
        int i = 0;
        while (i < this.getNumOfProvinces()) {
            if (nProvinceID == this.getProvinceID(i)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final int getNumOfProvinces() {
        return this.iNumOfProvinces;
    }

    protected final void setCivName(String sCivName) {
        if (sCivName.length() <= 0) {
            sCivName = "A";
        }

        this.civGameData.sCivName = sCivName;
        CFG.glyphLayout.setText(CFG.fontMain, sCivName);
        this.iCivNameWidth = (int)CFG.glyphLayout.width;
        this.iCivNameHeight = (int)CFG.glyphLayout.height;
        this.lCivNameChars = new ArrayList();
        sCivName = sCivName.toUpperCase();

        for(int i = 0; i < this.civGameData.sCivName.length(); ++i) {
            this.lCivNameChars.add(sCivName.charAt(i));
        }

        this.iCivNameLength = this.lCivNameChars.size();
    }

    protected final int getCivNameWidth() {
        return this.iCivNameWidth;
    }

    protected final int getCivNameHeight() {
        return this.iCivNameHeight;
    }

    protected final char getCivNameCharacter(int id) {
        return this.lCivNameChars.get(id).charValue();
    }

    protected final int getCivNameLength() {
        return this.iCivNameLength;
    }

    protected final int getR() {
        return this.civGameData.iR;
    }

    protected final void setR(int iR) {
        this.civGameData.iR = (short)iR;
    }

    protected final int getG() {
        return this.civGameData.iG;
    }

    protected final void setG(int iG) {
        this.civGameData.iG = (short)iG;
    }

    protected final int getB() {
        return this.civGameData.iB;
    }

    protected final void setB(int iB) {
        this.civGameData.iB = (short)iB;

        //change vassals if color change
        if (this.civGameData.iVassalsSize > 0) {
            this.updateVassalCivilizationsColor();
        }
    }

    protected final Color getRGB() {
        return this.getRGB(1.0f);
    }

    protected final Color getRGB(float nAlpha) {
        return new Color((float)this.getR() / 255.0f, (float)this.getG() / 255.0f, (float)this.getB() / 255.0f, nAlpha);
    }

    protected final int getMovePoints() {
        return this.iMovePoints;
    }

    protected final void setMovePoints(int iMovePoints) {
        this.iMovePoints = iMovePoints;
    }

    protected final long getMoney() {
        return this.civGameData.iMoney;
    }

    protected final void setMoney(long iMoney) {
        this.civGameData.iMoney = iMoney;
    }

    protected final int getCapitalProvinceID() {
        return this.civGameData.iCapitalProvinceID;
    }

    protected final void setCapitalProvinceID(int iCapitalProvinceID) {
        this.civGameData.iCapitalProvinceID = iCapitalProvinceID;
    }

    protected final int getCoreCapitalProvinceID() {
        return this.civGameData.iCoreCapitalProvinceID;
    }

    protected final void setCoreCapitalProvinceID(int iCoreCapitalProvinceID) {
        this.civGameData.iCoreCapitalProvinceID = iCoreCapitalProvinceID;
    }

    protected final int getCapitalMoved_LastTurnID() {
        return this.civGameData.iCapitalMoved_LastTurnID;
    }

    protected final void setCapitalMoved_LastTurnID(int iCapitalMoved_LastTurnID) {
        this.civGameData.iCapitalMoved_LastTurnID = iCapitalMoved_LastTurnID;
    }

    protected final int getCivID() {
        return this.iCivID;
    }

    protected final void setCivID(int iCivID) {
        this.iCivID = iCivID;
        this.civGameData.iPuppetOfCivID = iCivID;
        this.iRankPosition = iCivID;
    }

    protected final void setCivID_Just(int iCivID) {
        this.iCivID = iCivID;
    }

    protected final String getCivName() {
        return this.civGameData.sCivName;
    }

    protected final String getCivTag() {
        return this.civGameData.sCivTag;
    }

    protected final void setCivTag(String sCivTag) {
        this.civGameData.sCivTag = sCivTag;
        if (sCivTag.indexOf(59) <= 0) {
            this.setCivName(CFG.langManager.getCiv(sCivTag));
        } else {
            String[] tempTags = sCivTag.split(";");
            String tempName = "";
            for (int i = 0; i < tempTags.length; ++i) {
                tempName = tempName + CFG.langManager.getCiv(tempTags[i]) + (i < tempTags.length - 1 ? "-" : "");
            }
            this.setCivName(tempName);
        }
    }

    protected final int getHappiness() {
        return this.iHappiness;
    }

    protected final void setHappiness(int nHappiness) {
        this.iHappiness = nHappiness;
        if (this.iHappiness > 100) {
            this.iHappiness = 100;
        } else {
            if (this.iHappiness >= 0) return;
            this.iHappiness = 0;
        }
    }

    protected final int getNumOfUnits() {
        return this.iNumOfUnits;
    }

    protected final void setNumOfUnits(int iNumOfUnits) {
        this.iNumOfUnits = Math.max(iNumOfUnits, 0);
    }

    protected final void buildNumOfUnits() {
        int i;
        this.iNumOfUnits = 0;
        for (i = 0; i < this.getNumOfProvinces(); this.iNumOfUnits += CFG.game.getProvince(this.getProvinceID(i)).getArmyCivID(this.getCivID()), ++i) {
        }
        for (i = 0; i < this.getMoveUnitsSize(); this.iNumOfUnits += this.getMoveUnits(i).getNumOfUnits(), ++i) {
        }
        i = 0;
        while (i < this.getArmyInAnotherProvinceSize()) {
            this.iNumOfUnits += CFG.game.getProvince(this.getArmyInAnotherProvince(i)).getArmyCivID(this.getCivID());
            ++i;
        }
    }

    protected final int getArmyInAnotherProvince(int i) {
        try {
            return this.lArmyInAnotherProvince.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            if (!CFG.LOGS) return -1;
            CFG.exceptionStack((Throwable)ex);
            return -1;
        }
    }

    protected final void addArmyInAnotherProvince(int nProvinceID) {
        Gdx.app.log("AoC", "addArmyInAnotherProvince: " + this.getCivName() + ", nProvinceID: " + nProvinceID + " -> " + CFG.game.getProvince(nProvinceID).getName() + ", " + this.iArmyInAnotherProvinceSize);

        for(int i = 0; i < this.getArmyInAnotherProvinceSize(); ++i) {
            if (this.getArmyInAnotherProvince(i) == nProvinceID) {
                return;
            }
        }

        this.lArmyInAnotherProvince.add(nProvinceID);
        this.iArmyInAnotherProvinceSize = this.lArmyInAnotherProvince.size();
    }

    protected final void removeArmyInAnotherProvince(int nProvinceID) {
        Gdx.app.log("AoC", "removeArmyInAnotherProvince: " + this.getCivName() + ", nProvinceID: " + nProvinceID + " -> " + CFG.game.getProvince(nProvinceID).getName() + ", , " + this.iArmyInAnotherProvinceSize);
        int i = 0;
        while (i < this.getArmyInAnotherProvinceSize()) {
            if (this.getArmyInAnotherProvince(i) == nProvinceID) {
                this.lArmyInAnotherProvince.remove(i);
                this.iArmyInAnotherProvinceSize = this.lArmyInAnotherProvince.size();
                return;
            }
            ++i;
        }
    }

    protected final int getArmyInAnotherProvinceSize() {
        return this.iArmyInAnotherProvinceSize;
    }

    protected final float getRelation(int i) {
        try {
            return ((Float)this.civGameData.lRelation.get(i)).floatValue();
        }
        catch (IndexOutOfBoundsException ex) {
            this.civGameData.lRelation.add(Float.valueOf(10.0f));
            return 10.0f;
        }
    }

    protected final void setRelation(int iID, float nOpinion) {
        try {
            if (nOpinion > 100.0f) {
                nOpinion = 100.0f;
            } else if (nOpinion < -100.0f) {
                nOpinion = -100.0f;
            }
            this.civGameData.lRelation.set(iID, Float.valueOf(nOpinion));
        }
        catch (IndexOutOfBoundsException ex) {
            this.civGameData.lRelation.add(Float.valueOf(0.0f));
        }
    }

    protected final int getNonAggressionPact(int i) {
        try {
            return this.lNonAggressionPact.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            this.lNonAggressionPact.add(0);
            return 0;
        }
    }

    protected final boolean setNonAggressionPact(final int iID, int iNumOfTurns) {
        try {
            if (iNumOfTurns < 0) {
                iNumOfTurns = 0;
            }
            else if (iNumOfTurns > 40) {
                iNumOfTurns = 40;
            }
            this.lNonAggressionPact.set(iID, iNumOfTurns);
            if (iNumOfTurns > 0) {
                for (int i = 0; i < this.lOpt_NonAggressionPact.size(); ++i) {
                    if (this.lOpt_NonAggressionPact.get(i) == iID) {
                        return false;
                    }
                }
                this.lOpt_NonAggressionPact.add(iID);
            }
            else {
                for (int i = 0; i < this.lOpt_NonAggressionPact.size(); ++i) {
                    if (this.lOpt_NonAggressionPact.get(i) == iID) {
                        this.lOpt_NonAggressionPact.remove(i);
                        return true;
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.lNonAggressionPact.add(0);
        }
        return false;
    }

    protected final int getTruce(int i) {
        try {
            return this.lTruce.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            this.lTruce.add(0);
            return 0;
        }
    }

    protected final boolean setTruce(final int iID, int iNumOfTurns) {
        try {
            if (iNumOfTurns < 0) {
                iNumOfTurns = 0;
            }
            else if (iNumOfTurns > 50) {
                iNumOfTurns = 50;
            }
            this.lTruce.set(iID, iNumOfTurns);
            if (iNumOfTurns > 0) {
                for (int i = 0; i < this.lOpt_Truce.size(); ++i) {
                    if (this.lOpt_Truce.get(i) == iID) {
                        return false;
                    }
                }
                this.lOpt_Truce.add(iID);
            }
            else {
                for (int i = 0; i < this.lOpt_Truce.size(); ++i) {
                    if (this.lOpt_Truce.get(i) == iID) {
                        this.lOpt_Truce.remove(i);
                        return true;
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.lTruce.add(0);
        }
        return false;
    }

    protected final int getDefensivePact(int i) {
        try {
            return this.lDefensivePact.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            this.lDefensivePact.add(0);
            return 0;
        }
    }

    protected final boolean setDefensivePact(final int iID, int iNumOfTurns) {
        try {
            if (iNumOfTurns < 0) {
                iNumOfTurns = 0;
            }
            else if (iNumOfTurns > 40) {
                iNumOfTurns = 40;
            }
            this.lDefensivePact.set(iID, iNumOfTurns);
            if (iNumOfTurns > 0) {
                for (int i = 0; i < this.lOpt_DefensivePact.size(); ++i) {
                    if (this.lOpt_DefensivePact.get(i) == iID) {
                        return false;
                    }
                }
                this.lOpt_DefensivePact.add(iID);
            }
            else {
                for (int i = 0; i < this.lOpt_DefensivePact.size(); ++i) {
                    if (this.lOpt_DefensivePact.get(i) == iID) {
                        this.lOpt_DefensivePact.remove(i);
                        return true;
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.lDefensivePact.add(0);
        }
        return false;
    }

    protected final int getGuarantee(int i) {
        try {
            return this.lGuarantee.get(i).byteValue();
        }
        catch (IndexOutOfBoundsException ex) {
            this.lGuarantee.add((byte)0);
            return 0;
        }
    }

    protected final boolean setGuarantee(final int iID, int iNumOfTurns) {
        try {
            if (iNumOfTurns < 0) {
                iNumOfTurns = 0;
            }
            else if (iNumOfTurns > 100) {
                iNumOfTurns = 100;
            }
            this.lGuarantee.set(iID, (byte)iNumOfTurns);
            if (iNumOfTurns > 0) {
                for (int i = 0; i < this.lOpt_Guarantee.size(); ++i) {
                    if (this.lOpt_Guarantee.get(i) == iID) {
                        return false;
                    }
                }
                this.lOpt_Guarantee.add(iID);
            }
            else {
                for (int i = 0; i < this.lOpt_Guarantee.size(); ++i) {
                    if (this.lOpt_Guarantee.get(i) == iID) {
                        this.lOpt_Guarantee.remove(i);
                        return true;
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.lGuarantee.add((byte)0);
        }
        return false;
    }

    protected final int getMilitaryAccess(int i) {
        try {
            return this.lMilitirayAccess.get(i).byteValue();
        }
        catch (IndexOutOfBoundsException ex) {
            this.lMilitirayAccess.add((byte)0);
            return 0;
        }
    }

    protected final boolean setMilitaryAccess(final int iID, int iNumOfTurns) {
        try {
            if (iNumOfTurns < 0) {
                iNumOfTurns = 0;
            }
            else if (iNumOfTurns > 40) {
                iNumOfTurns = 40;
            }
            this.lMilitirayAccess.set(iID, (byte)iNumOfTurns);
            if (iNumOfTurns > 0) {
                for (int i = 0; i < this.lOpt_MilitirayAccess.size(); ++i) {
                    if (this.lOpt_MilitirayAccess.get(i) == iID) {
                        return false;
                    }
                }
                this.lOpt_MilitirayAccess.add(iID);
            }
            else {
                for (int i = 0; i < this.lOpt_MilitirayAccess.size(); ++i) {
                    if (this.lOpt_MilitirayAccess.get(i) == iID) {
                        this.lOpt_MilitirayAccess.remove(i);
                        return true;
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            this.lMilitirayAccess.add((byte)0);
        }
        return false;
    }

    protected final void disposeFlag() {
        if (this.civFlag == null) return;
        this.civFlag.getTexture().dispose();
        this.civFlag = null;
    }

    protected final void setFlag(Image nFlag) {
        this.disposeFlag();
        this.civFlag = nFlag;
    }

    protected final boolean loadFlag(final int flagStatus) {
        if (!this.getIsPupet()) return this.loadFlag();

        switch (flagStatus) {
            case 1: {
                //load owner flag
                try {
                    if (this.civFlag != null) {
                        this.disposeFlag();
                    }
                } catch (RuntimeException var11) {
                    CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.getCivID());
                    return false;
                }

                try {
                    try {
                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag + ".png")), Texture.TextureFilter.Nearest);
                    } catch (GdxRuntimeException var8) {
                        if (CFG.ideologiesManager.getIdeology(this.getIdeologyID()).REVOLUTIONARY) {
                            this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/rb" + (this.civGameData.iPuppetOfCivID + this.getCivTag().charAt(0)) % 6 + ".png")), Texture.TextureFilter.Nearest);
                            return true;
                        }

                        try {
                            this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + ".png")), Texture.TextureFilter.Nearest);
                        } catch (GdxRuntimeException var7) {
                            if (CFG.isAndroid()) {
                                try {
                                    this.civFlag = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                                } catch (GdxRuntimeException var4) {
                                    this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                                }
                            } else {
                                this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                            }
                        }
                    }
                } catch (GdxRuntimeException var9) {
                    this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
                } catch (RuntimeException var10) {
                    CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.civGameData.iPuppetOfCivID);
                    return false;
                }
                return true;
            }
            case 2: {
                //load unified flag (1/4 owner, rest this)
                try {
                    CFG.unionFlagsToGenerate_Manager.lFlags.add(new VassalFlagsToGenerate());
                    int tGenerateID = CFG.unionFlagsToGenerate_Manager.lFlags.size() - 1;
                    String[] tempD = this.getCivTag().split(";");

                    ((VassalFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lordID = (CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getCivID());
                    for(int i = 0; i < tempD.length; ++i) {
                        (CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.add(tempD[i]);
                        if (i >= 2) break;
                    }
                    while ((CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.size() < 4) {
                        (CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.add(tempD[0]);
                    }

                    (CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).typeOfAction = UnionFlagsToGenerate_TypesOfAction.CIV_ID_SMALL;
                    (CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).iID = this.getCivID();
                    //this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
                } catch (RuntimeException var6) {
                    CFG.exceptionStack(var6);
                }
                return true;
            }
            case 3: {
                if (this.getCivTag().indexOf(";") > 0) {
                    try {
                        CFG.unionFlagsToGenerate_Manager.lFlags.add(new UnionFlagsToGenerate());
                        int tGenerateID = CFG.unionFlagsToGenerate_Manager.lFlags.size() - 1;
                        String[] tempD = this.getCivTag().split(";");

                        for(int i = 0; i < tempD.length; ++i) {
                            ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.add(tempD[i]);
                        }

                        ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).typeOfAction = UnionFlagsToGenerate_TypesOfAction.CIV_ID_SMALL;
                        ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).iID = this.getCivID();
                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
                    } catch (GdxRuntimeException var5) {
                        CFG.exceptionStack(var5);
                    } catch (RuntimeException var6) {
                        CFG.exceptionStack(var6);
                    }
                } else {
                    try {
                        if (this.civFlag != null) {
                            this.disposeFlag();
                        }
                    } catch (RuntimeException var11) {
                        CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.getCivID());
                        return false;
                    }

                    try {
                        try {
                            this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + this.civGameData.sCivTag + ".png")), Texture.TextureFilter.Nearest);
                        } catch (GdxRuntimeException var8) {
                            if (CFG.ideologiesManager.getIdeology(this.getIdeologyID()).REVOLUTIONARY) {
                                this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/rb" + (this.getCivID() + this.getCivTag().charAt(0)) % 6 + ".png")), Texture.TextureFilter.Nearest);
                                return true;
                            }

                            try {
                                this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + ".png")), Texture.TextureFilter.Nearest);
                            } catch (GdxRuntimeException var7) {
                                if (CFG.isAndroid()) {
                                    try {
                                        this.civFlag = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                                    } catch (GdxRuntimeException var4) {
                                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                                    }
                                } else {
                                    this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                                }
                            }
                        }
                    } catch (GdxRuntimeException var9) {
                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
                    } catch (RuntimeException var10) {
                        CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.getCivID());
                        return false;
                    }
                }
                return true;
            }
            default: {
                return false;
            }
        }
    }

    protected final boolean loadFlag() {
        if (this.getIsPupet() && CFG.menuManager.getInGameView()) return this.loadFlag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getVassal_AutonomyStatus(this.iCivID).getFlagStatus());

        if (this.getCivTag().indexOf(";") > 0) {
            try {
                CFG.unionFlagsToGenerate_Manager.lFlags.add(new UnionFlagsToGenerate());
                int tGenerateID = CFG.unionFlagsToGenerate_Manager.lFlags.size() - 1;
                String[] tempD = this.getCivTag().split(";");

                for(int i = 0; i < tempD.length; ++i) {
                    ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.add(tempD[i]);
                }

                ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).typeOfAction = UnionFlagsToGenerate_TypesOfAction.CIV_ID_SMALL;
                ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).iID = this.getCivID();
                this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
            } catch (GdxRuntimeException var5) {
                CFG.exceptionStack(var5);
            } catch (RuntimeException var6) {
                CFG.exceptionStack(var6);
            }

        } else {
            try {
                if (this.civFlag != null) {
                    this.disposeFlag();
                }
            } catch (RuntimeException var11) {
                CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.getCivID());
                return false;
            }

            try {
                try {
                    this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + this.civGameData.sCivTag + ".png")), Texture.TextureFilter.Nearest);
                } catch (GdxRuntimeException var8) {
                    if (CFG.ideologiesManager.getIdeology(this.getIdeologyID()).REVOLUTIONARY) {
                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/rb" + (this.getCivID() + this.getCivTag().charAt(0)) % 6 + ".png")), Texture.TextureFilter.Nearest);
                        return true;
                    }

                    try {
                        this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + ".png")), Texture.TextureFilter.Nearest);
                    } catch (GdxRuntimeException var7) {
                        if (CFG.isAndroid()) {
                            try {
                                this.civFlag = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                            } catch (GdxRuntimeException var4) {
                                this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                            }
                        } else {
                            this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "/" + CFG.ideologiesManager.getRealTag(this.civGameData.sCivTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                        }
                    }
                }
            } catch (GdxRuntimeException var9) {
                this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
            } catch (RuntimeException var10) {
                CFG.unionFlagsToGenerate_Manager.addFlagToLoad(this.getCivID());
                return false;
            }

        }
        return true;
    }

    protected final Image getFlag() {
        return this.civFlag == null ? ImageManager.getImage((int)Images.randomCivilizationFlag) : this.civFlag;
    }

    protected final boolean getFlag_IsNull() {
        return this.civFlag == null;
    }

    protected final Civilization_Region getCivRegion(int i) {
        try {
            return this.lCivRegions.get(i);
        }
        catch (IndexOutOfBoundsException ex) {
            this.updateRegions = true;
            return new Civilization_Region();
        }
    }

    protected final int getCivRegionsSize() {
        return this.iCivRegionsSize;
    }

    protected final boolean getUpdateRegions() {
        return this.updateRegions;
    }

    protected final void setUpdateRegions(boolean updateRegions) {
        this.updateRegions = updateRegions;
    }

    protected final int getPuppetOfCivID() {
        return this.civGameData.iPuppetOfCivID;
    }

    protected final void setPuppetOfCivID(int iPuppetOfCivID) {
        //if (this.civGameData.iPuppetOfCivID != this.iCivID && this.civGameData.iPuppetOfCivID != iPuppetOfCivID) {
        //    CFG.game.getCiv(this.civGameData.iPuppetOfCivID).removeVassal(this.iCivID);
        //}
        //this.civGameData.iPuppetOfCivID = iPuppetOfCivID;
        //if (this.civGameData.iPuppetOfCivID == this.iCivID) return;
        //CFG.game.getCiv(this.civGameData.iPuppetOfCivID).addVassal(this.iCivID);

        //set puppet with default autonomy status
        setPuppetOfCivID(iPuppetOfCivID, -1);
    }

    protected final void setPuppetOfCivID(int iPuppetOfCivID, int iAutonomyStatus) {
        if (this.civGameData.iPuppetOfCivID != this.iCivID && this.civGameData.iPuppetOfCivID != iPuppetOfCivID) {
            CFG.game.getCiv(this.civGameData.iPuppetOfCivID).removeVassal(this.iCivID);
        }
        this.civGameData.iPuppetOfCivID = iPuppetOfCivID;

        //if freed, reset color, return
        if (iPuppetOfCivID == this.iCivID) {
            CFG.palletManager.loadCivilizationStandardColor(this.iCivID);
            return;
        }

        //integrated
        //CFG.game.getCiv(this.civGameData.iPuppetOfCivID).addVassal(this.iCivID);
        int index = CFG.game.getCiv(this.civGameData.iPuppetOfCivID).addVassal(this.iCivID);
        CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.lVassals.get(index).setAutonomyStatus(iAutonomyStatus);

        //set color
        this.updateCivilizationColor(CFG.game.getCiv(iPuppetOfCivID).getR(), CFG.game.getCiv(iPuppetOfCivID).getG(), CFG.game.getCiv(iPuppetOfCivID).getB(), CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.lVassals.get(index).autonomyStatus.getColorStatus());

        //set this civ's vassals to be overseen by new lord
        if (CFG.menuManager.getInManageDiplomacy() || CFG.menuManager.getInCreateScenario_Civilizations()) {
            return;
        }
        for (int i = 0; i < this.civGameData.iVassalsSize; i++) {
            CFG.game.getCiv(this.civGameData.lVassals.get(i).iCivID).setPuppetOfCivID(iPuppetOfCivID);
        }

        //if no mil control disband
        if (!CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.lVassals.get(index).autonomyStatus.isMilitaryControl()) {
            this.clearMoveUnits();
            this.clearMoveUnits_Plunder();
            this.clearRegroupArmy();
            this.clearRecruitArmy();

            ArrayList<Integer> tempProvinces = new ArrayList<>();
            for (int k = 0; k < this.getNumOfProvinces(); ++k) {
                tempProvinces.add(this.getProvinceID(k));
            }
            for (Integer tempProvince : tempProvinces) {
                if (CFG.game.getProvince((Integer) tempProvince).getCivID() != this.iCivID || CFG.game.getProvince((Integer) tempProvince).getTrueOwnerOfProvince() != this.iCivID)
                    continue;
                int nArmyNewOwnerArmy = CFG.game.getProvince((Integer) tempProvince).getArmyCivID(iPuppetOfCivID);
                int nArmyOldOwnerArmy = CFG.game.getProvince((Integer) tempProvince).getArmyCivID(this.iCivID);
                CFG.game.getProvince((Integer) tempProvince).updateArmy(0);
                CFG.game.getProvince((Integer) tempProvince).updateArmy(this.iCivID, 0);
                CFG.game.getProvince((Integer) tempProvince).updateArmy(iPuppetOfCivID, 0);
                CFG.game.getProvince((Integer) tempProvince).updateArmy(iPuppetOfCivID, nArmyNewOwnerArmy + nArmyOldOwnerArmy);

            }

            CFG.game.buildCivilizationsRegions_TextOver(this.iCivID);
            this.buildNumOfUnits();
        }

        //if no eco control transfer money, remove tribute
        if (!CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.lVassals.get(index).autonomyStatus.isEconomicControl()) {
            this.clearLoans();
            CFG.oAI.getAI_Style(this.civGameData.iAI_Style).manageBudget(this.iCivID);
            CFG.game.getCiv(iPuppetOfCivID).setMoney(CFG.game.getCiv(iPuppetOfCivID).getMoney() + this.getMoney());
            this.setMoney(0L);
            CFG.game.getCiv(this.civGameData.iPuppetOfCivID).civGameData.lVassals.get(index).setTribute(0);
        }

        //update stab if in game
        if (CFG.menuManager.getInGameView()) {
            this.lProvincesWithLowStability.clear();
            this.fStability = 0.0F;

            for (int i : this.lProvinces) {
                CFG.game.getProvince(i).updateProvinceStability();
                this.fStability += CFG.game.getProvince(i).getProvinceStability();
            }
            this.setStability(this.fStability);
        }

        //load flag
        this.loadFlag(CFG.game.getCiv(this.civGameData.iPuppetOfCivID).getVassal_AutonomyStatus(this.iCivID).getFlagStatus());
    }

    protected final float getVassalLiberityDesire() {
        return this.civGameData.fVassalLiberityDisere;
    }

    protected final void setVassalLiberityDesire(float fLiberityDesire) {
        if (fLiberityDesire < 0.0f) {
            fLiberityDesire = 0.0f;
        } else if (fLiberityDesire > 100.0f) {
            fLiberityDesire = 100.0f;
        }
        this.civGameData.fVassalLiberityDisere = fLiberityDesire;
    }

    protected final int addVassal(int nCivID) {
        for(int i = 0; i < this.civGameData.lVassals.size(); ++i) {
            if (((Vassal_GameData)this.civGameData.lVassals.get(i)).iCivID == nCivID) {
                return i;
            }
        }

        this.civGameData.lVassals.add(new Vassal_GameData(nCivID));
        this.civGameData.iVassalsSize = this.civGameData.lVassals.size();
        return this.civGameData.iVassalsSize - 1;
    }

    protected final void removeVassal(int nCivID) {
        for(int i = 0; i < this.civGameData.lVassals.size(); ++i) {
            if (((Vassal_GameData)this.civGameData.lVassals.get(i)).iCivID == nCivID) {
                //clear autonomy
                this.civGameData.lVassals.get(i).autonomyStatus = null;
                this.civGameData.lVassals.remove(i);
                this.civGameData.iVassalsSize = this.civGameData.lVassals.size();
                return;
            }
        }
    }

    protected final int getVassal_Tribute(int nCivID) {
        for(int i = 0; i < this.civGameData.lVassals.size(); ++i) {
            if (((Vassal_GameData)this.civGameData.lVassals.get(i)).iCivID == nCivID) {
                return ((Vassal_GameData)this.civGameData.lVassals.get(i)).iTribute;
            }
        }

        this.civGameData.lVassals.add(new Vassal_GameData(nCivID));
        this.civGameData.iVassalsSize = this.civGameData.lVassals.size();
        return 9;
    }

    protected final void setVassal_Tribute(int nCivID, int nTribute) {
        for(int i = 0; i < this.civGameData.lVassals.size(); ++i) {
            if (((Vassal_GameData)this.civGameData.lVassals.get(i)).iCivID == nCivID) {
                ((Vassal_GameData)this.civGameData.lVassals.get(i)).setTribute(nTribute);
                return;
            }
        }

        this.civGameData.lVassals.add(new Vassal_GameData(nCivID));
        this.civGameData.iVassalsSize = this.civGameData.lVassals.size();
    }

    //new index vassal function
    protected final AutonomyStatus getVassal_AutonomyStatus(int nCivID) {
        for(int i = 0; i < this.civGameData.lVassals.size(); ++i) {
            if (((Vassal_GameData)this.civGameData.lVassals.get(i)).iCivID == nCivID) {
                //return index after searching
                return ((Vassal_GameData)this.civGameData.lVassals.get(i)).autonomyStatus;
            }
        }

        //return null
        return null;
    }

    protected final boolean getIsPupet() {
        return this.iCivID != this.civGameData.iPuppetOfCivID;
    }

    protected final Move_Units getMoveUnits(int i) {
        return this.lMoveUnits.get(i);
    }

    protected final boolean isMovingUnitsFromProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.getMoveUnitsSize()) {
            if (this.getMoveUnits(i).getFromProvinceID() == nProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final boolean isMovingUnitsToProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.getMoveUnitsSize()) {
            if (this.getMoveUnits(i).getToProvinceID() == nProvinceID) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final int isMovingUnitsToProvinceID_Num(int nProvinceID) {
        int i = 0;
        while (i < this.getMoveUnitsSize()) {
            if (this.getMoveUnits(i).getToProvinceID() == nProvinceID) {
                return this.getMoveUnits(i).getNumOfUnits();
            }
            ++i;
        }
        return 0;
    }

    protected final Move_Units getMigrate(int i) {
        return this.lMigrate.get(i);
    }

    protected final Move_Units_Plunder getMoveUnits_Plunder(int i) {
        return this.lMove_Units_Plunder.get(i);
    }

    protected final RecruitArmy_Request getRecruitArmy(int i) {
        return this.lRecruitArmy.get(i);
    }

    protected final int getRecruitArmy_BasedOnProvinceID(int nProvinceID) {
        int i = 0;
        while (i < this.iRecruitArmySize) {
            if (this.lRecruitArmy.get(i).getProvinceID() == nProvinceID) {
                return this.lRecruitArmy.get(i).getArmy();
            }
            ++i;
        }
        return 0;
    }

    protected final int getRecruitArmySize() {
        return this.iRecruitArmySize;
    }

    protected final int getMoveUnitsSize() {
        return this.iMoveUnitsSize;
    }

    protected final int getMigrateSize() {
        return this.iMigrateSize;
    }

    protected final int getMoveUnitsPlunderSize() {
        return this.iMove_Units_PlunderSize;
    }

    protected final List<MoveUnits_Line> getCurrentRegroupArmyLine(int i) {
        return this.lCurrentRegroupArmyLine.get(i);
    }

    protected final RegroupArmy_Data getRegroupArmy(int i) {
        return (RegroupArmy_Data)this.civGameData.lRegroupArmy.get(i);
    }

    protected final int isRegoupingArmy_ToProvinceID(int toProvinceID) {
        int i = 0;
        while (i < this.civGameData.iRegroupArmySize) {
            if (((RegroupArmy_Data)this.civGameData.lRegroupArmy.get(i)).getToProvinceID() == toProvinceID) {
                return ((RegroupArmy_Data)this.civGameData.lRegroupArmy.get(i)).getNumOfUnits();
            }
            ++i;
        }
        return 0;
    }

    protected final int getRegroupArmySize() {
        return this.civGameData.iRegroupArmySize;
    }

    protected final int getAllianceID() {
        return this.civGameData.iAllianceID;
    }

    protected final void setAllianceID(int iAllianceID) {
        this.civGameData.iAllianceID = iAllianceID;
    }

    protected final boolean getControlledByPlayer() {
        return !CFG.SPECTATOR_MODE && this.controlledByPlayer;
    }

    protected final void setControlledByPlayer(boolean controlledByPlayer) {
        this.controlledByPlayer = controlledByPlayer;
    }

    protected final int getAI_Style() {
        return this.civGameData.iAI_Style;
    }

    protected final void setAI_Style(int iAI_Style) {
        this.civGameData.iAI_Style = iAI_Style;
    }

    protected final CivPersonality getCivPersonality() {
        return this.civGameData.civPersonality;
    }

    protected final CivPlans getCivPlans() {
        return this.civGameData.civPlans;
    }

    protected final boolean getIsAvailable() {
        return this.isAvailable;
    }

    protected final void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    protected final int countPopulation() {
        int nPopulation = 0;
        int i = 0;
        while (i < this.iNumOfProvinces) {
            nPopulation += CFG.game.getProvince(this.getProvinceID(i)).getPopulationData().getPopulation();
            ++i;
        }
        return nPopulation;
    }

    protected final int countPopulation_WithoutOccupied() {
        int nPopulation = 0;
        int i = 0;
        while (i < this.iNumOfProvinces) {
            if (!CFG.game.getProvince(this.getProvinceID(i)).isOccupied()) {
                nPopulation += CFG.game.getProvince(this.getProvinceID(i)).getPopulationData().getPopulation();
            }
            ++i;
        }
        return nPopulation;
    }

    protected final int countEconomy() {
        int nEconomy = 0;
        int i = 0;
        while (i < this.iNumOfProvinces) {
            nEconomy += CFG.game.getProvince(this.getProvinceID(i)).getEconomy();
            ++i;
        }
        return nEconomy;
    }

    protected final int countEconomy_WithoutOccupied() {
        int nEconomy = 0;
        int i = 0;
        while (i < this.iNumOfProvinces) {
            if (!CFG.game.getProvince(this.getProvinceID(i)).isOccupied()) {
                nEconomy += CFG.game.getProvince(this.getProvinceID(i)).getEconomy();
            }
            ++i;
        }
        return nEconomy;
    }

    protected final float getTechnologyLevel() {
        return (float)this.civGameData.fTechnologyLevel / 100.0f;
    }

    protected final int getTechnologyLevel_INT() {
        return this.civGameData.fTechnologyLevel;
    }

    protected final void setTechnologyLevel(float nTechnologyLevel) {
        this.civGameData.fTechnologyLevel = (int)(nTechnologyLevel * 100.0f);
        if (!((float)this.civGameData.fTechnologyLevel > 200.0f)) return;
        this.civGameData.fTechnologyLevel = 200;
    }

    protected final void setTechnologyLevel_INT(int nTechnologyLevel) {
        this.civGameData.fTechnologyLevel = nTechnologyLevel;
        if (!((float)this.civGameData.fTechnologyLevel > 200.0f)) return;
        this.civGameData.fTechnologyLevel = 200;
    }


    protected final float getSpendings_Research() {
        return this.civGameData.fSpendings_Research;
    }

    protected final void setSpendings_Research(float fSpendings_Research) {
        if (this.getMoney() < -500L) {
            this.civGameData.fSpendings_Research = 0.0f;
        } else {
            this.civGameData.fSpendings_Research = fSpendings_Research;
            if (this.civGameData.fSpendings_Research < 0.0f) {
                this.civGameData.fSpendings_Research = 0.0f;
            } else if (this.civGameData.fSpendings_Research > 1.0f) {
                this.civGameData.fSpendings_Research = 1.0f;
            }
        }
    }

    protected final float getSpendings_Investments() {
        return this.civGameData.fSpendings_Investments;
    }

    protected final void setSpendings_Investments(float fSpendings_Investments) {
        this.civGameData.fSpendings_Investments = fSpendings_Investments;
        if (this.civGameData.fSpendings_Investments < 0.0f) {
            this.civGameData.fSpendings_Investments = 0.0f;
        } else if (this.civGameData.fSpendings_Investments > 1.0f) {
            this.civGameData.fSpendings_Investments = 1.0f;
        }
    }

    protected final float getSpendings_Goods() {
        return this.civGameData.fSpendings_Goods;
    }

    protected final void setSpendings_Goods(float fSpendings_Goods) {
        this.civGameData.fSpendings_Goods = fSpendings_Goods;
        if (this.civGameData.fSpendings_Goods < 0.0f) {
            this.civGameData.fSpendings_Goods = 0.0f;
        } else if (this.civGameData.fSpendings_Goods > 1.0f) {
            this.civGameData.fSpendings_Goods = 1.0f;
        }
    }

    protected final float getTaxationLevel() {
        return this.civGameData.fTaxationLevel;
    }

    protected final void setTaxationLevel(float fTaxationLevel) {
        this.civGameData.fTaxationLevel = fTaxationLevel;
        if (this.civGameData.fTaxationLevel < 0.0f) {
            this.civGameData.fTaxationLevel = 0.0f;
        } else {
            if (!(this.civGameData.fTaxationLevel > 1.0f)) return;
            this.civGameData.fTaxationLevel = 1.0f;
        }
    }

    protected final int getDiplomacyPoints() {
        return this.civGameData.iDiplomacyPoints;
    }

    protected final void setDiplomacyPoints(int nDiplomacyPoints) {
        if ((float)nDiplomacyPoints > 85.0f + 85.0f * this.getTechnologyLevel() / 4.0f && nDiplomacyPoints > this.civGameData.iDiplomacyPoints && (nDiplomacyPoints = this.civGameData.iDiplomacyPoints + 1) > 200) {
            nDiplomacyPoints = 200;
        }
        this.civGameData.iDiplomacyPoints = nDiplomacyPoints;
    }

    protected final int getRankPosition() {
        return this.iRankPosition;
    }

    protected final void setRankPosition(int iRankPosition) {
        this.iRankPosition = iRankPosition;
    }

    protected final int getRankScore() {
        return this.iRankScore;
    }

    protected final void setRankScore(int iRankScore) {
        this.iRankScore = iRankScore;
    }

    protected final int getIdeologyID() {
        return this.iIdeologyID;
    }

    protected final void setIdeologyID(int iIdeologyID) {
        this.iIdeologyID = iIdeologyID;
        this.setAI_Style(CFG.oAI.getAIStyle_ByTag(CFG.ideologiesManager.getIdeology((int)this.getIdeologyID()).AI_TYPE));
    }

    protected final int getSeaAccess() {
        return this.seaAccess;
    }

    protected final void setSeaAccess(int seaAccess) {
        this.seaAccess = seaAccess;
    }

    protected final void clearSeaAccess_Provinces() {
        this.seaAccess_Provinces.clear();
    }

    protected final void addSeaAccess_Provinces(int nProvinceID) {
        this.seaAccess_Provinces.add(nProvinceID);
    }

    protected final List<Integer> getSeaAccess_Provinces() {
        return this.seaAccess_Provinces;
    }

    protected final int getSeaAccess_Provinces_Size() {
        return this.seaAccess_Provinces.size();
    }

    protected final void clearSeaAccess_PortProvinces() {
        this.seaAccess_Port.clear();
    }

    protected final void addSeaAccess_PortProvinces(int nProvinceID) {
        this.seaAccess_Port.add(nProvinceID);
    }

    protected final List<Integer> getSeaAccess_PortProvinces() {
        return this.seaAccess_Port;
    }

    protected final int getSeaAccess_PortProvinces_Size() {
        return this.seaAccess_Port.size();
    }

    protected final int getBordersWithEnemy() {
        return this.bordersWithEnemy;
    }

    protected final void setBordersWithEnemy(int bordersWithEnemy) {
        this.bordersWithEnemy = bordersWithEnemy;
    }

    protected final boolean isAtWar() {
        return this.isAtWar;
    }

    protected final void setIsAtWar(boolean isAtWar) {
        this.isAtWar = isAtWar;
    }

    protected final boolean getCanExpandOnContinent() {
        return this.canExpandOnContinent;
    }

    protected final void setCanExpandOnContinent(boolean canExpandOnContinent) {
        this.canExpandOnContinent = canExpandOnContinent;
    }

    protected final int getNumOfNeighboringNeutralProvinces() {
        return this.iNumOfNeighboringNeutralProvinces;
    }

    protected final void setNumOfNeighboringNeutralProvinces(int iNumOfNeighboringNeutralProvinces) {
        this.iNumOfNeighboringNeutralProvinces = iNumOfNeighboringNeutralProvinces;
    }

    protected final void clearTagsCanForm() {
        this.sTagsCanForm.clear();
    }

    protected final int getTagsCanFormSize() {
        return this.sTagsCanForm.size();
    }

    protected final String getTagsCanForm(int i) {
        return this.sTagsCanForm.get(i);
    }

    protected final void addTagsCanForm(String nTag) {
        for (String s : this.sTagsCanForm) {
            if (((String) s).equals(nTag)) {
                return;
            }
        }

        this.sTagsCanForm.add(nTag);
    }

    protected final void removeTagsCanForm(int i) {
        this.sTagsCanForm.remove(i);
    }

    protected final void removeTagsCanForm(String nTag) {
        int i = 0;
        while (i < this.sTagsCanForm.size()) {
            if (this.sTagsCanForm.get(i).equals(nTag)) {
                this.sTagsCanForm.remove(i);
                return;
            }
            ++i;
        }
    }

    protected final float getResearchProgress() {
        return this.civGameData.fResearchProgress;
    }

    protected final void addResearchProgress(float fAdd) {
        this.civGameData.fResearchProgress += fAdd;
    }

    protected final void setResearchProgress(float fResearchProgress) {
        this.civGameData.fResearchProgress = fResearchProgress;
    }

    protected final boolean getIsPartOfHolyRomanEmpire() {
        return this.civGameData.isPartOfHolyRomaEmpire;
    }

    protected final void setIsPartOfHolyRomanEmpire(boolean isPartOfHolyRomaEmpire) {
        this.civGameData.isPartOfHolyRomaEmpire = isPartOfHolyRomaEmpire;
    }

    protected final void runNextEvent() {
        try {
            if (this.getControlledByPlayer()) {
                if (this.getEventsToRunSize() > 0) {
                    Menu_InGame_Event.EVENT_ID = this.getEventsToRun(0);
                    this.removeEventToRun(0);
                    CFG.menuManager.rebuildInGame_Event();
                }
            }
            else {
                for (int i = this.getEventsToRunSize() - 1; i >= 0; --i) {
                    Commands.addMessage("runEvent: " + this.getCivName() + ": " + CFG.eventsManager.getEvent(this.getEventsToRun(i)).getEventName());
                    try {
                        int decistionTaken = 0;
                        int tempAIChanceTotal = 0;
                        for (int j = 0; j < CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.size(); ++j) {
                            tempAIChanceTotal += CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.get(j).iAIChance;
                        }
                        final int randNum = CFG.oR.nextInt(tempAIChanceTotal);
                        int k = 0;
                        int countChance = 0;
                        while (k < CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.size()) {
                            if (randNum >= countChance && randNum < countChance + CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.get(k).iAIChance) {
                                decistionTaken = k;
                                break;
                            }
                            countChance += CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.get(k).iAIChance;
                            ++k;
                        }
                        if (CFG.eventsManager.getEvent(this.getEventsToRun(i)).getCivID() >= 0) {
                            CFG.game.getCiv(CFG.eventsManager.getEvent(this.getEventsToRun(i)).getCivID()).addEvent_DecisionTaken(CFG.eventsManager.getEvent(this.getEventsToRun(i)).getEventTag() + "_" + decistionTaken);
                        }
                        Commands.addMessage("runEvent: " + this.getCivName() + ": Decision: " + CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.get(decistionTaken).sTitle);
                        CFG.eventsManager.getEvent(this.getEventsToRun(i)).lDecisions.get(decistionTaken).executeDecision();
                        this.removeEventToRun(0);
                    }
                    catch (final IndexOutOfBoundsException ex) {
                        CFG.exceptionStack(ex);
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex2) {}
        catch (final NullPointerException ex3) {}
        catch (final StackOverflowError stackOverflowError) {}
        catch (final IllegalArgumentException ex4) {}
    }

    protected final int getEventsToRun(int i) {
        return this.lEventsToRun.get(i);
    }

    protected final void addEventToRunID(int id) {
        this.lEventsToRun.add(id);
    }

    protected final void removeEventToRun(int i) {
        this.lEventsToRun.remove(i);
    }

    protected final int getEventsToRunSize() {
        return this.lEventsToRun.size();
    }

    protected final void addEvent_DecisionTaken(String nEventDecTAG) {
        this.civGameData.lEvents_DecisionsTaken.add(nEventDecTAG);
    }

    protected final boolean getEvent_TookDecision(String nEventDecTAG) {
        int i = 0;
        while (i < this.civGameData.lEvents_DecisionsTaken.size()) {
            if (((String)this.civGameData.lEvents_DecisionsTaken.get(i)).equals(nEventDecTAG)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    protected final Civilization_Diplomacy_GameData getCivilization_Diplomacy_GameData() {
        return this.civGameData.civilization_Diplomacy_GameData;
    }

    protected final Loan_GameData getLoan(int i) {
        return (Loan_GameData)this.civGameData.lLoansTaken.get(i);
    }

    protected final int getLoansSize() {
        return this.civGameData.lLoansTaken.size();
    }

    protected final void addLoan(int iGoldPerTurn, int iDuration) {
        this.civGameData.lLoansTaken.add(new Loan_GameData(iGoldPerTurn, iDuration));
    }

    protected final void updateLoansNextTurn() {
        int i = 0;
        while (i < this.civGameData.lLoansTaken.size()) {
            --((Loan_GameData)this.civGameData.lLoansTaken.get((int)i)).iTurnsLeft;
            if (((Loan_GameData)this.civGameData.lLoansTaken.get((int)i)).iTurnsLeft <= 0) {
                this.civGameData.lLoansTaken.remove(i--);
                this.getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_Repaid(this.getCivID(), 0));
            }
            ++i;
        }
    }

    protected final int getLoans_GoldTotalPerTurn() {
        int out = 0;
        int i = 0;
        while (i < this.civGameData.lLoansTaken.size()) {
            out += ((Loan_GameData)this.civGameData.lLoansTaken.get((int)i)).iGoldPerTurn;
            ++i;
        }
        return out;
    }

    protected final void clearLoans() {
        this.civGameData.lLoansTaken.clear();
    }

    protected final void removeLoan(int i) {
        this.civGameData.lLoansTaken.remove(i);
    }

    protected final void addWarReparationsGets(int nCivID) {
        for(int i = 0; i < this.civGameData.lWarReparationsGets.size(); ++i) {
            if (((WarReparations)this.civGameData.lWarReparationsGets.get(i)).iFromCivID == nCivID) {
                ((WarReparations)this.civGameData.lWarReparationsGets.get(i)).iTurnsLeft = 12;
                return;
            }
        }

        this.civGameData.lWarReparationsGets.add(new WarReparations(nCivID, 12));
    }

    protected final void addWarReparationsPay(int nCivID) {
        for(int i = 0; i < this.civGameData.lWarReparationsPay.size(); ++i) {
            if (((WarReparations)this.civGameData.lWarReparationsPay.get(i)).iFromCivID == nCivID) {
                ((WarReparations)this.civGameData.lWarReparationsPay.get(i)).iTurnsLeft = 12;
                return;
            }
        }

        this.civGameData.lWarReparationsPay.add(new WarReparations(nCivID, 12));
    }

    protected final WarReparations getWarReparationsPays(int i) {
        return (WarReparations)this.civGameData.lWarReparationsPay.get(i);
    }

    protected final int getWarReparationsPays_TurnsLeft(int nCivID) {
        int i = 0;
        while (i < this.civGameData.lWarReparationsPay.size()) {
            if (((WarReparations)this.civGameData.lWarReparationsPay.get((int)i)).iFromCivID == nCivID) {
                return ((WarReparations)this.civGameData.lWarReparationsPay.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final WarReparations getWarReparationsGets(int i) {
        return (WarReparations)this.civGameData.lWarReparationsGets.get(i);
    }

    protected final int getWarReparationsGets_TurnsLeft(int nCivID) {
        int i = 0;
        while (i < this.civGameData.lWarReparationsGets.size()) {
            if (((WarReparations)this.civGameData.lWarReparationsGets.get((int)i)).iFromCivID == nCivID) {
                return ((WarReparations)this.civGameData.lWarReparationsGets.get((int)i)).iTurnsLeft;
            }
            ++i;
        }
        return 0;
    }

    protected final void runWarReparations() {
        int i;
        for (i = this.civGameData.lWarReparationsPay.size() - 1; i >= 0; --i) {
            if (((WarReparations)this.civGameData.lWarReparationsPay.get((int)i)).iTurnsLeft-- > 0) continue;
            this.getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_WarReparationsRepaid_Green(((WarReparations)this.civGameData.lWarReparationsPay.get((int)i)).iFromCivID));
            this.civGameData.lWarReparationsPay.remove(i);
        }
        i = this.civGameData.lWarReparationsGets.size() - 1;
        while (i >= 0) {
            if (((WarReparations)this.civGameData.lWarReparationsGets.get((int)i)).iTurnsLeft-- <= 0) {
                this.getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_WarReparationsRepaid(((WarReparations)this.civGameData.lWarReparationsGets.get((int)i)).iFromCivID));
                this.civGameData.lWarReparationsGets.remove(i);
            }
            --i;
        }
    }

    protected final int getWarReparationsPaysSize() {
        return this.civGameData.lWarReparationsPay.size();
    }

    protected final int getWarReparationsGetsSize() {
        return this.civGameData.lWarReparationsGets.size();
    }

    protected final boolean addNewBonus(CivBonus_GameData nBonus) {
        block7: {
            block8: {
                block6: {
                    block4: {
                        block5: {
                            block3: {
                                if (nBonus.BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_PROSPERITY) break block3;
                                break block4;
                            }
                            if (nBonus.BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_SCIENCE) break block5;
                            break block6;
                        }
                        if (nBonus.BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_MILITARY) break block7;
                        break block8;
                    }
                    for (int i = 0; i < this.civGameData.lBonuses.size(); ++i) {
                        if (((CivBonus_GameData)this.civGameData.lBonuses.get((int)i)).BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_PROSPERITY) continue;
                        return false;
                    }
                    break block7;
                }
                for (int i = 0; i < this.civGameData.lBonuses.size(); ++i) {
                    if (((CivBonus_GameData)this.civGameData.lBonuses.get((int)i)).BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_SCIENCE) continue;
                    return false;
                }
                break block7;
            }
            for (int i = 0; i < this.civGameData.lBonuses.size(); ++i) {
                if (((CivBonus_GameData)this.civGameData.lBonuses.get((int)i)).BONUS_TYPE != CivBonus_Type.GOLDEN_AGE_MILITARY) continue;
                return false;
            }
        }
        this.civGameData.lBonuses.add(nBonus);
        this.applyBonusChanges(nBonus);
        return true;
    }

    protected final void updateBonuses() {
        int i = 0;
        while (i < this.civGameData.lBonuses.size()) {
            --((CivBonus_GameData)this.civGameData.lBonuses.get((int)i)).iTurnsLeft;
            if (((CivBonus_GameData)this.civGameData.lBonuses.get((int)i)).iTurnsLeft <= 0) {
                this.applyBonusChanges_Expired((CivBonus_GameData)this.civGameData.lBonuses.get(i));
                this.civGameData.lBonuses.remove(i--);
            }
            ++i;
        }
    }

    protected final CivBonus_GameData getBonus(int i) {
        return (CivBonus_GameData)this.civGameData.lBonuses.get(i);
    }

    protected final int getBonusesSize() {
        return this.civGameData.lBonuses.size();
    }

    private final void applyBonusChanges(CivBonus_GameData nBonus) {
        this.civGameData.fModifier_PopGrowth += nBonus.fModifier_PopGrowth;
        this.civGameData.fModifier_EconomyGrowth += nBonus.fModifier_EconomyGrowth;
        this.civGameData.fModifier_IncomeTaxation += nBonus.fModifier_IncomeTaxation;
        this.civGameData.fModifier_IncomeProduction += nBonus.fModifier_IncomeProduction;
        this.civGameData.fModifier_Research += nBonus.fModifier_Research;
        this.civGameData.fModifier_MilitaryUpkeep += nBonus.fModifier_MilitaryUpkeep;
        this.civGameData.fModifier_AttackBonus += nBonus.fModifier_AttackBonus;
        this.civGameData.fModifier_DefenseBonus += nBonus.fModifier_DefenseBonus;
        this.civGameData.fModifier_MovementPoints += nBonus.fModifier_MovementPoints;
    }

    private final void applyBonusChanges_Expired(CivBonus_GameData nBonus) {
        this.civGameData.fModifier_PopGrowth -= nBonus.fModifier_PopGrowth;
        this.civGameData.fModifier_EconomyGrowth -= nBonus.fModifier_EconomyGrowth;
        this.civGameData.fModifier_IncomeTaxation -= nBonus.fModifier_IncomeTaxation;
        this.civGameData.fModifier_IncomeProduction -= nBonus.fModifier_IncomeProduction;
        this.civGameData.fModifier_Research -= nBonus.fModifier_Research;
        this.civGameData.fModifier_MilitaryUpkeep -= nBonus.fModifier_MilitaryUpkeep;
        this.civGameData.fModifier_AttackBonus -= nBonus.fModifier_AttackBonus;
        this.civGameData.fModifier_DefenseBonus -= nBonus.fModifier_DefenseBonus;
        this.civGameData.fModifier_MovementPoints -= nBonus.fModifier_MovementPoints;
    }

    protected final float getModifier_PopGrowth() {
        return this.civGameData.fModifier_PopGrowth;
    }

    protected final void setModifier_PopGrowth(float fModifier_PopGrowth) {
        this.civGameData.fModifier_PopGrowth = fModifier_PopGrowth;
    }

    protected final float getModifier_EconomyGrowth() {
        return this.civGameData.fModifier_EconomyGrowth;
    }

    protected final void setModifier_EconomyGrowth(float fModifier_EconomyGrowth) {
        this.civGameData.fModifier_EconomyGrowth = fModifier_EconomyGrowth;
    }

    protected final float getModifier_IncomeTaxation() {
        return this.civGameData.fModifier_IncomeTaxation;
    }

    protected final float getModifier_Administation() {
        return this.civGameData.fModifier_Administration;
    }

    protected final void setModifier_IncomeTaxation(float fModifier_IncomeTaxation) {
        this.civGameData.fModifier_IncomeTaxation = fModifier_IncomeTaxation;
    }

    protected final float getModifier_IncomeProduction() {
        return this.civGameData.fModifier_IncomeProduction;
    }

    protected final void setModifier_IncomeProduction(float fModifier_IncomeProduction) {
        this.civGameData.fModifier_IncomeProduction = fModifier_IncomeProduction;
    }

    protected final float getModifier_Research() {
        return this.civGameData.fModifier_Research;
    }

    protected final void setModifier_Research(float fModifier_Research) {
        this.civGameData.fModifier_Research = fModifier_Research;
    }

    protected final float getModifier_MilitaryUpkeep() {
        return this.civGameData.fModifier_MilitaryUpkeep;
    }

    protected final void setModifier_MilitaryUpkeep(float fModifier_MilitaryUpkeep) {
        this.civGameData.fModifier_MilitaryUpkeep = fModifier_MilitaryUpkeep;
    }

    protected final float getModifier_AttackBonus() {
        return this.civGameData.fModifier_AttackBonus;
    }

    protected final void setModifier_AttackBonus(float fModifier_AttackBonus) {
        this.civGameData.fModifier_AttackBonus = fModifier_AttackBonus;
    }

    protected final float getModifier_DefenseBonus() {
        return this.civGameData.fModifier_DefenseBonus;
    }

    protected final void setModifier_DefenseBonus(float fModifier_DefenseBonus) {
        this.civGameData.fModifier_DefenseBonus = fModifier_DefenseBonus;
    }

    protected final float getModifier_MovementPoints() {
        return this.civGameData.fModifier_MovementPoints;
    }

    protected final void setModifier_MovementPoints(float fModifier_MovementPoints) {
        this.civGameData.fModifier_MovementPoints = fModifier_MovementPoints;
    }

    public int getGoldenAge_Science() {
        return this.civGameData.iGoldenAge_Science;
    }

    public void setGoldenAge_Science(int iGoldenAge_Science) {
        this.civGameData.iGoldenAge_Science = iGoldenAge_Science;
    }

    public int getGoldenAge_Military() {
        return this.civGameData.iGoldenAge_Military;
    }

    //fixed golden age of military
    public void setGoldenAge_Military(int iGoldenAge_Military) {
        this.civGameData.iGoldenAge_Military = this.civGameData.iGoldenAge_Military;
    }

    public int getGoldenAge_Prosperity() {
        return this.civGameData.iGoldenAge_Prosperity;
    }

    public void setGoldenAge_Prosperity(int iGoldenAge_Prosperity) {
        this.civGameData.iGoldenAge_Prosperity = iGoldenAge_Prosperity;
    }

    protected final float getWarWeariness() {
        return this.civGameData.fWarWeariness;
    }

    protected final void setWarWeariness(float fWarWeariness) {
        if (fWarWeariness > 1.0f) {
            fWarWeariness = 1.0f;
        } else if (fWarWeariness < 0.0f) {
            fWarWeariness = 0.0f;
        }
        this.civGameData.fWarWeariness = fWarWeariness;
    }

    protected final void addBordersWithCivID(int nCivID) {
        for(int i = 0; i < this.iBorderWithCivsSize; ++i) {
            if (((AI_BordersWith)this.lBorderWithCivs.get(i)).iWithCivID == nCivID) {
                ++((AI_BordersWith)this.lBorderWithCivs.get(i)).iNumOfConnections;
                return;
            }
        }

        this.lBorderWithCivs.add(new AI_BordersWith(nCivID));
        ++this.iBorderWithCivsSize;
    }

    protected final boolean addHatedCiv(int nCivID) {
        for(int i = 0; i < this.getHatedCivsSize(); ++i) {
            if (nCivID == ((Civilization_Hated_GameData)this.civGameData.lHatedCivs.get(i)).iCivID) {
                return false;
            }
        }

        CFG.game.getCiv(nCivID).addHatedCiv_By(this.getCivID());
        this.civGameData.lHatedCivs.add(new Civilization_Hated_GameData(nCivID));
        this.civGameData.iHatedCivsSize = this.civGameData.lHatedCivs.size();
        return true;
    }

    protected final int getHatedCivsSize() {
        return this.civGameData.iHatedCivsSize;
    }

    protected final Civilization_Hated_GameData getHatedCiv(int i) {
        return (Civilization_Hated_GameData)this.civGameData.lHatedCivs.get(i);
    }

    protected final boolean isHatedCiv(int nCivID) {
        int i = this.getHatedCivsSize() - 1;
        while (i >= 0) {
            if (((Civilization_Hated_GameData)this.civGameData.lHatedCivs.get((int)i)).iCivID == nCivID) {
                return true;
            }
            --i;
        }
        return false;
    }

    protected final void clearHatedCivs() {
        for(int i = 0; i < this.getHatedCivsSize(); ++i) {
            CFG.game.getCiv(((Civilization_Hated_GameData)this.civGameData.lHatedCivs.get(i)).iCivID).removeHatedCiv_BY(this.getCivID());
        }

        this.civGameData.lHatedCivs.clear();
        this.civGameData.iHatedCivsSize = 0;
    }

    protected final void removeHatedCiv(int nCivID) {
        int i = this.getHatedCivsSize() - 1;
        while (i >= 0) {
            if (((Civilization_Hated_GameData)this.civGameData.lHatedCivs.get((int)i)).iCivID == nCivID) {
                CFG.game.getCiv(((Civilization_Hated_GameData)this.civGameData.lHatedCivs.get((int)i)).iCivID).removeHatedCiv_BY(this.getCivID());
                this.civGameData.lHatedCivs.remove(i);
                this.civGameData.iHatedCivsSize = this.civGameData.lHatedCivs.size();
                return;
            }
            --i;
        }
    }

    protected final int getHatedCivs_BySize() {
        return this.civGameData.iHatedCivs_BySize;
    }

    protected final int getHatedCiv_By(int i) {
        return (Integer)this.civGameData.lHatedCivs_By.get(i);
    }

    protected final void addHatedCiv_By(int nCivID) {
        for(int i = 0; i < this.getHatedCivs_BySize(); ++i) {
            if ((Integer)this.civGameData.lHatedCivs_By.get(i) == nCivID) {
                return;
            }
        }

        this.civGameData.lHatedCivs_By.add(nCivID);
        this.civGameData.iHatedCivs_BySize = this.civGameData.lHatedCivs_By.size();
    }

    protected final void removeHatedCiv_BY(int nCivID) {
        int i = this.getHatedCivs_BySize() - 1;
        while (i >= 0) {
            if ((Integer)this.civGameData.lHatedCivs_By.get(i) == nCivID) {
                this.civGameData.lHatedCivs_By.remove(i);
                this.civGameData.iHatedCivs_BySize = this.civGameData.lHatedCivs_By.size();
                return;
            }
            --i;
        }
    }

    protected final boolean addFriendlyCiv(int nCivID) {
        for (int i = 0; i < this.civGameData.lFriendlyCivs.size(); ++i) {
            if (nCivID != ((Civilization_Friends_GameData)this.civGameData.lFriendlyCivs.get((int)i)).iCivID) continue;
            return false;
        }
        this.civGameData.lFriendlyCivs.add(new Civilization_Friends_GameData(nCivID, Game_Calendar.TURN_ID));
        this.getCivilization_Diplomacy_GameData().messageBox.addMessage((Message)new Message_Relations_Friendly(nCivID));
        try {
            CFG.historyManager.addHistoryLog((HistoryLog)new HistoryLog_FriendlyCivs(this.getCivID(), nCivID));
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        return true;
    }

    protected final void updateFriendlyCiv() {
    }

    protected final int getFriendlyCivsSize() {
        return this.civGameData.lFriendlyCivs.size();
    }

    protected final Civilization_Friends_GameData getFriendlyCiv(int i) {
        return (Civilization_Friends_GameData)this.civGameData.lFriendlyCivs.get(i);
    }

    protected final int isFriendlyCiv(int nCivID) {
        int i = this.civGameData.lFriendlyCivs.size() - 1;
        while (i >= 0) {
            if (((Civilization_Friends_GameData)this.civGameData.lFriendlyCivs.get((int)i)).iCivID == nCivID) {
                return (int)Math.ceil(((Civilization_Friends_GameData)this.civGameData.lFriendlyCivs.get((int)i)).iSinceTurnID);
            }
            --i;
        }
        return -1;
    }

    protected final void clearFreidnlyCivs() {
        this.civGameData.lFriendlyCivs.clear();
    }

    protected final void removeFriendlyCiv(int nCivID) {
        int i = this.civGameData.lFriendlyCivs.size() - 1;
        while (i >= 0) {
            if (((Civilization_Friends_GameData)this.civGameData.lFriendlyCivs.get((int)i)).iCivID == nCivID) {
                this.civGameData.lFriendlyCivs.remove(i);
                return;
            }
            --i;
        }
    }

    protected final void addSentMessages(Civilization_SentMessages nSentMessage) {
        for(int i = this.civGameData.lSentMessages.size() - 1; i >= 0; --i) {
            if (((Civilization_SentMessages)this.civGameData.lSentMessages.get(i)).iToCivID == nSentMessage.iToCivID && ((Civilization_SentMessages)this.civGameData.lSentMessages.get(i)).messageType == nSentMessage.messageType) {
                ((Civilization_SentMessages)this.civGameData.lSentMessages.get(i)).iSentInTurnID = Game_Calendar.TURN_ID;
                return;
            }
        }

        this.civGameData.lSentMessages.add(nSentMessage);
    }


    protected final void clearSentMessages() {
        this.civGameData.lSentMessages.clear();
    }

    protected final void removeSentMessages(Message_Type nMessageType) {
        int i = this.civGameData.lSentMessages.size() - 1;
        while (i >= 0) {
            if (((Civilization_SentMessages)this.civGameData.lSentMessages.get((int)i)).messageType == nMessageType) {
                this.civGameData.lSentMessages.remove(i);
            }
            --i;
        }
    }

    protected final void removeSentMessage(int i) {
        this.civGameData.lSentMessages.remove(i);
    }

    protected final boolean messageWasSent(int nToCivID, Message_Type nMessageType) {
        int i = this.civGameData.lSentMessages.size() - 1;
        while (i >= 0) {
            if (((Civilization_SentMessages)this.civGameData.lSentMessages.get((int)i)).iToCivID == nToCivID && ((Civilization_SentMessages)this.civGameData.lSentMessages.get((int)i)).messageType == nMessageType) {
                return true;
            }
            --i;
        }
        return false;
    }

    protected final boolean messageWasSent(int nToCivID) {
        int i = this.civGameData.lSentMessages.size() - 1;
        while (i >= 0) {
            if (((Civilization_SentMessages)this.civGameData.lSentMessages.get((int)i)).iToCivID == nToCivID) {
                return true;
            }
            --i;
        }
        return false;
    }

    protected final boolean messageWasSent(Message_Type nMessageType) {
        int i = this.civGameData.lSentMessages.size() - 1;
        while (i >= 0) {
            if (((Civilization_SentMessages)this.civGameData.lSentMessages.get((int)i)).messageType == nMessageType) {
                return true;
            }
            --i;
        }
        return false;
    }

    protected final int getSentMessagesSize() {
        return this.civGameData.lSentMessages.size();
    }

    protected final Civilization_SentMessages getSentMessage(int i) {
        return (Civilization_SentMessages)this.civGameData.lSentMessages.get(i);
    }

    protected final float getStability() {
        return this.fStability;
    }

    protected final void setStability(float nStability) {
        this.fStability = nStability = Math.min(Math.max(nStability, 0.01f), 1.0f);
    }
}
