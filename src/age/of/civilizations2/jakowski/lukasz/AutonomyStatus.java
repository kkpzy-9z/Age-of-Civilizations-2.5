package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class AutonomyStatus extends Object implements Serializable {
   private static final long serialVersionUID = 0L;
   private String sName;
   private boolean joinWarPerms;
   private boolean militaryControl;
   private boolean economicControl;
   private boolean tradeType;
   private double colorStatus;
   private int flagStatus;
   private int ageID;
   private int diploCost;
   private int index;
   private int preIndex;
   private int afterIndex;

   protected AutonomyStatus(int index, String sName, boolean joinWar, boolean milControl, boolean ecoControl, boolean trading, double color, int flagStat, int age, int diplo, int precede, int follow) {
      this.index = index;

      this.sName = sName;
      this.joinWarPerms = joinWar;
      this.militaryControl = milControl;
      this.economicControl = ecoControl;
      this.tradeType = trading;
      this.colorStatus = Math.max(Math.min(color, 2.0), -1.0);
      this.flagStatus = flagStat;
      this.ageID = age;
      this.diploCost = diplo * 10;

      this.preIndex = precede;
      this.afterIndex = follow;
   }

   /*protected AutonomyStatus(String sName, int iAgeBeginningYear, int iAgeEndYear, float fPopulationGrowthRate, float fEconomyGrowthRate, float FOG_OF_WAR_DISCOVERY_MET_PROVINCES, float DEVELOPMENT_LEVEL_INCREASE, float INCOME_TAXATION_MODIFIER, float INCOME_PRODUCTION_MODIFIER, float EXPENSES_ADMINSTRATION_MODIFIER, float EXPENSES_MILITARY_UPKEEP_MODIFIER, int BASE_MOVEMENT_POINTS, float MOVEMENT_POINTS_MODIFIER, int BASE_DIPLOMACY_POINTS, float EXPENSES_ADMINSTRATION_DISTANCE, int DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE, float BASE_INCOME_TAXATION, float INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER, float BASE_MILITARY_UPKEEP, float GAME_STARTING_DEVELOPMENT, int GAME_DAYS_PER_TURN, float BASE_INCOME_PRODUCTION, float INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER, float REVOLUTIONARY_RISK_MODIFIER, float DISEASE_CHANCE, float COLONIZE_COST_GOLD_PERC, int COLONIZE_COST_MOVEMENT_POINTS, int COLONIZE_COST_DIPLOMACY_POINTS) {
      this.sName = CFG.langManager.get(sName);
      this.joinWarPerms = joinWar;
      this.militaryIntegration = milControl;
      this.economicIntegration = ecoControl;
      this.tradeType = trading;
      this.colorStatus = color;

      this.FOG_OF_WAR_DISCOVERY_MET_PROVINCES = FOG_OF_WAR_DISCOVERY_MET_PROVINCES;
      this.DEVELOPMENT_LEVEL_INCREASE = DEVELOPMENT_LEVEL_INCREASE;
      this.INCOME_TAXATION_MODIFIER = INCOME_TAXATION_MODIFIER;
      this.INCOME_PRODUCTION_MODIFIER = INCOME_PRODUCTION_MODIFIER;
      this.EXPENSES_ADMINSTRATION_MODIFIER = EXPENSES_ADMINSTRATION_MODIFIER;
      this.EXPENSES_MILITARY_UPKEEP_MODIFIER = EXPENSES_MILITARY_UPKEEP_MODIFIER;
      this.BASE_MOVEMENT_POINTS = BASE_MOVEMENT_POINTS;
      this.MOVEMENT_POINTS_MODIFIER = MOVEMENT_POINTS_MODIFIER;
      this.BASE_DIPLOMACY_POINTS = BASE_DIPLOMACY_POINTS;
      this.EXPENSES_ADMINSTRATION_DISTANCE = EXPENSES_ADMINSTRATION_DISTANCE;
      this.DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE = DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE;
      this.BASE_INCOME_TAXATION = BASE_INCOME_TAXATION;
      this.INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER = INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER;
      this.BASE_MILITARY_UPKEEP = BASE_MILITARY_UPKEEP;
      this.GAME_STARTING_DEVELOPMENT = GAME_STARTING_DEVELOPMENT;
      this.GAME_DAYS_PER_TURN = GAME_DAYS_PER_TURN;
      this.BASE_INCOME_PRODUCTION = BASE_INCOME_PRODUCTION;
      this.INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER = INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER;
      this.REVOLUTIONARY_RISK_MODIFIER = REVOLUTIONARY_RISK_MODIFIER;
      this.COLONIZE_COST_GOLD_PERC = COLONIZE_COST_GOLD_PERC;
      this.COLONIZE_COST_MOVEMENT_POINTS = COLONIZE_COST_MOVEMENT_POINTS;
      this.COLONIZE_COST_DIPLOMACY_POINTS = COLONIZE_COST_DIPLOMACY_POINTS;
      this.DISEASE_CHANCE = DISEASE_CHANCE;
   }*/

   protected final String getName() {
      return this.sName;
   }

   protected final void setName(String sName) {
      this.sName = CFG.langManager.get(sName);
   }

   protected final boolean isAutoJoinWarPerms() {
      return this.joinWarPerms;
   }
   protected final boolean isMilitaryControl() {
      return this.militaryControl;
   }
   protected final boolean isEconomicControl() {
      return this.economicControl;
   }
   protected final boolean isTradeIntegration() {
      return this.tradeType;
   }
   protected final double getColorStatus() {
      return this.colorStatus;
   }
   protected final int getFlagStatus() {
      return this.flagStatus;
   }
   protected final int getAge() {
      return this.ageID;
   }
   protected final int getDiploCost() {
      return this.diploCost;
   }
   protected final int getIndexOf() {
      return this.index;
   }
   protected final int getIndexBefore() {
      return this.preIndex;
   }
   protected final int getIndexAfter() {
      return this.afterIndex;
   }
}
