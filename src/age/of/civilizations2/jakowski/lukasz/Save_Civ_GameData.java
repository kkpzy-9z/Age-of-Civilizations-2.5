package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Save_Civ_GameData implements Serializable {
   private static final long serialVersionUID = 0L;
   protected int iPuppetOfCivID;
   protected float fVassalLiberityDisere = 0.0f;
   protected LeaderOfCiv_GameData leaderData;
   protected List<Vassal_GameData> lVassals = new ArrayList<Vassal_GameData>();
   protected int iVassalsSize = 0;
   protected int iAI_Style = 0;
   protected CivPersonality civPersonality = new CivPersonality();
   protected CivPlans civPlans = new CivPlans();
   protected int iRegroupArmyAtPeace_CheckTurnID = 0;
   protected String sCivTag;
   protected String sCivName;
   protected int iCapitalProvinceID;
   protected int iCoreCapitalProvinceID = -1;
   protected int iCapitalMoved_LastTurnID = -50;
   protected boolean isPartOfHolyRomaEmpire = false;
   protected List<String> lEvents_DecisionsTaken = new ArrayList<String>();
   protected Civilization_Diplomacy_GameData civilization_Diplomacy_GameData;
   protected short iR;
   protected short iG;
   protected short iB;
   protected List<RegroupArmy_Data> lRegroupArmy;
   protected int iRegroupArmySize;
   protected List<Civilization_Colonies> lColonies_Founded = new ArrayList<Civilization_Colonies>();
   protected int iLockColonization_UntilTurnID = 1;
   protected Civ_Mission_ChangeTypeOfGoverment changeTypeOfGoverment = null;
   protected int iExpandNeutralProvinces_RangeCheck = 6;
   protected int iNextPossibleNavalInvastionTurnID = 0;
   protected List<AI_CivsInRange> civsInRange = new ArrayList<AI_CivsInRange>();
   protected int nextBuildCivsInRange_TurnID = 0;
   protected int holdLookingForEnemy_UntilTurnID = 0;
   protected int holdLookingForFriends_UntilTurnID = 1;
   protected List<AI_Influence> civsInfluenced = new ArrayList<AI_Influence>();
   protected int civsInfluencedSize = 0;
   protected List<AI_Rival> civRivals = new ArrayList<AI_Rival>();
   protected int civRivalsSize = 0;
   protected int declareWar_CheckNextTurnID = 0;
   protected int nextArmyRestREgroupment_TurnID = 0;
   protected int numOfUnions = 0;
   protected float civAggresionLevel = 0.0f;
   protected int allianceCheck_TurnID = 0;
   protected int allianceUpdate_TurnID = 0;
   protected int circledVassals_TurnID = 0;
   protected int checkFormCiv_TurnID = 0;
   protected int iPlunder_LastTurnID = 0;
   protected int iNextCheckMilitaryAccessTurnID = 0;
   protected int iNextCheckMilitaryAccessSeaTurnID = 0;
   protected int iSendGift_LastTurnID = 5;
   protected int iNumOfConqueredProvinces = 0;
   protected int iNumOfBuildingsConstructed = 0;
   protected int iRecruitedArmy = 0;
   protected int iLockTreasury = 1;
   protected int iNumOfRevolutions = 0;
   protected boolean moveAtWar_ArmyFullyRecruitedLastTurn = false;
   protected int moveAtWar_ProvincesLostAndConquered_LastTurn = 0;
   protected Skills_GameData skills = new Skills_GameData();
   protected float fModifier_PopGrowth = 0.0f;
   protected float fModifier_EconomyGrowth = 0.0f;
   protected float fModifier_IncomeTaxation = 0.0f;
   protected float fModifier_IncomeProduction = 0.0f;
   protected float fModifier_Administration = 0.0f;
   protected float fModifier_Research = 0.0f;
   protected float fModifier_MilitaryUpkeep = 0.0f;
   protected float fModifier_AttackBonus = 0.0f;
   protected float fModifier_DefenseBonus = 0.0f;
   protected float fModifier_MovementPoints = 0.0f;
   protected float fModifier_ColonizationCost = 0.0f;
   protected List<CivBonus_GameData> lBonuses = new ArrayList<CivBonus_GameData>();
   protected int iGoldenAge_Prosperity = 0;
   protected int iGoldenAge_Military = 0;
   protected int iGoldenAge_Science = 0;
   protected int iNumOfTurnsAtWar = 0;
   protected float fWarWeariness = 0.0f;
   protected long iMoney;
   protected List<Loan_GameData> lLoansTaken;
   protected List<WarReparations> lWarReparationsPay;
   protected List<WarReparations> lWarReparationsGets;
   protected int iDiplomacyPoints;
   protected int fTechnologyLevel;
   protected float fSpendings_Research = 0.0f;
   protected float fSpendings_Investments = 0.15f;
   protected float fSpendings_Goods = 0.2f;
   protected float fResearchProgress = 0.0f;
   protected float fTaxationLevel = 0.1f;
   protected List<CivFestival> lFestivals = new ArrayList<CivFestival>();
   protected List<CivFestival> lAssimilates = new ArrayList<CivFestival>();
   protected List<CivInvest> lInvest = new ArrayList<CivInvest>();
   protected List<CivInvest_Development> lInvest_Development = new ArrayList<CivInvest_Development>();
   protected List<Construction_GameData> lConstructions = new ArrayList<Construction_GameData>();
   protected int iAllianceID = 0;
   protected List<Float> lRelation = new ArrayList<Float>();
   protected int iRevolt_SinceTurn = 1;
   protected int iRevolt_LastTurnLostProvince = 1;
   protected List<Civilization_Hated_GameData> lHatedCivs = new ArrayList<Civilization_Hated_GameData>();
   protected int iHatedCivsSize = 0;
   protected List<Integer> lHatedCivs_By = new ArrayList<Integer>();
   protected int iHatedCivs_BySize = 0;
   protected List<Civilization_Friends_GameData> lFriendlyCivs = new ArrayList<Civilization_Friends_GameData>();
   protected List<Civilization_SentMessages> lSentMessages = new ArrayList<Civilization_SentMessages>();
   protected List<Civ_Gift_GameData> lGifts_Received = new ArrayList<Civ_Gift_GameData>();
   private final List<Civ_Decision_GameData> civDecisions = new ArrayList<>();

   //trade boolean change//
   protected boolean hasSentTrade = false;

   protected final Decision_GameData getDecision(int index) {
      if (index < 0 || index >= this.civDecisions.size()) return null;
      return this.civDecisions.get(index);
   }

   protected final Decision_GameData getDecisionByName(String name) {
      for (Decision_GameData d : this.civDecisions) {
         if (d.getName().equals(name)) return d;
      }
      return null;
   }

   protected final int getDecisionsCount() {
      return this.civDecisions.size();
   }

   protected final void addDecision(final Civ_Decision_GameData decision) {
      if (decision == null) return;
      this.civDecisions.add(decision);
   }

   protected final void removeDecision(int index) {
      if (index < 0 || index >= this.civDecisions.size()) return;
      this.civDecisions.remove(index);
   }

   protected final void clearDecisions() {
      this.civDecisions.clear();
   }

   protected final void addGift_Received(int iCivID) {
      for(int i = this.lGifts_Received.size() - 1; i >= 0; --i) {
         if (((Civ_Gift_GameData)this.lGifts_Received.get(i)).iFromCivID == iCivID) {
            ((Civ_Gift_GameData)this.lGifts_Received.get(i)).iTurnID = Game_Calendar.TURN_ID;
            return;
         }
      }

      this.lGifts_Received.add(new Civ_Gift_GameData(iCivID, Game_Calendar.TURN_ID));
   }

   protected final void updateGift_Received() {
      for(int i = this.lGifts_Received.size() - 1; i >= 0; --i) {
         if (((Civ_Gift_GameData)this.lGifts_Received.get(i)).iTurnID + 5 < Game_Calendar.TURN_ID) {
            this.lGifts_Received.remove(i);
         }
      }

   }

   protected final void setLeader(LeaderOfCiv_GameData nLeaderData) {
      if (this.leaderData != null) {
         this.fModifier_PopGrowth -= this.leaderData.fModifier_PopGrowth;
         this.fModifier_EconomyGrowth -= this.leaderData.fModifier_EconomyGrowth;
         this.fModifier_IncomeTaxation -= this.leaderData.fModifier_IncomeTaxation;
         this.fModifier_IncomeProduction -= this.leaderData.fModifier_IncomeProduction;
         this.fModifier_Administration -= this.leaderData.fModifier_Administration;
         this.fModifier_Research -= this.leaderData.fModifier_Research;
         this.fModifier_MilitaryUpkeep -= this.leaderData.fModifier_MilitaryUpkeep;
         this.fModifier_AttackBonus -= this.leaderData.fModifier_AttackBonus;
         this.fModifier_DefenseBonus -= this.leaderData.fModifier_DefenseBonus;
         this.fModifier_MovementPoints -= this.leaderData.fModifier_MovementPoints;
      }

      this.leaderData = new LeaderOfCiv_GameData();
      if (nLeaderData != null) {
         leaderData.setTag(nLeaderData.getTag());
         leaderData.setName(nLeaderData.getName());
         leaderData.setImage(nLeaderData.getImage());
         leaderData.setWiki(nLeaderData.getWiki());
         leaderData.setYear(nLeaderData.getYear());
         leaderData.setMonth(nLeaderData.getMonth());
         leaderData.setDay(nLeaderData.getDay());
         leaderData.setIncumbentYear(nLeaderData.isIncumbentYear());
         leaderData.setRandom(nLeaderData.isRandom());

         leaderData.fModifier_PopGrowth = nLeaderData.fModifier_PopGrowth;
         leaderData.fModifier_EconomyGrowth = nLeaderData.fModifier_EconomyGrowth;
         leaderData.fModifier_IncomeTaxation = nLeaderData.fModifier_IncomeTaxation;
         leaderData.fModifier_IncomeProduction = nLeaderData.fModifier_IncomeProduction;
         leaderData.fModifier_Administration = nLeaderData.fModifier_Administration;
         leaderData.fModifier_Research = nLeaderData.fModifier_Research;
         leaderData.fModifier_MilitaryUpkeep = nLeaderData.fModifier_MilitaryUpkeep;
         leaderData.fModifier_AttackBonus = nLeaderData.fModifier_AttackBonus;
         leaderData.fModifier_DefenseBonus = nLeaderData.fModifier_DefenseBonus;
         leaderData.fModifier_MovementPoints = nLeaderData.fModifier_MovementPoints;

         this.fModifier_PopGrowth += leaderData.fModifier_PopGrowth;
         this.fModifier_EconomyGrowth += leaderData.fModifier_EconomyGrowth;
         this.fModifier_IncomeTaxation += leaderData.fModifier_IncomeTaxation;
         this.fModifier_IncomeProduction += leaderData.fModifier_IncomeProduction;
         this.fModifier_Administration += leaderData.fModifier_Administration;
         this.fModifier_Research += leaderData.fModifier_Research;
         this.fModifier_MilitaryUpkeep += leaderData.fModifier_MilitaryUpkeep;
         this.fModifier_AttackBonus += leaderData.fModifier_AttackBonus;
         this.fModifier_DefenseBonus += leaderData.fModifier_DefenseBonus;
         this.fModifier_MovementPoints += leaderData.fModifier_MovementPoints;
      }
   }
}
