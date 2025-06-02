package age.of.civilizations2.jakowski.lukasz;

class Age {
   private String sName;
   private int iAgeBeginningYear;
   private int iAgeEndYear;
   private float fPopulationGrowthRate;
   private float fEconomyGrowthRate;
   protected float FOG_OF_WAR_DISCOVERY_MET_PROVINCES = 1.0F;
   protected int BASE_MOVEMENT_POINTS = 20;
   protected float DEVELOPMENT_LEVEL_INCREASE = 1.0F;
   protected float BASE_INCOME_TAXATION = 0.004654F;
   protected float INCOME_TAXATION_MODIFIER = 1.0F;
   protected float INCOME_PRODUCTION_MODIFIER = 1.0F;
   protected float EXPENSES_ADMINSTRATION_MODIFIER = 1.0F;
   protected float EXPENSES_MILITARY_UPKEEP_MODIFIER = 1.0F;
   protected float MOVEMENT_POINTS_MODIFIER = 1.0F;
   protected int BASE_DIPLOMACY_POINTS = 10;
   protected float EXPENSES_ADMINSTRATION_DISTANCE = 3.5F;
   protected int DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE = 350;
   protected float INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER = 4.1254E-4F;
   protected float BASE_MILITARY_UPKEEP = 0.109189F;
   protected float GAME_STARTING_DEVELOPMENT = 0.44215F;
   protected int GAME_DAYS_PER_TURN = 34;
   protected float BASE_INCOME_PRODUCTION = 0.015954F;
   protected float INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER = 0.0015456F;
   protected float REVOLUTIONARY_RISK_MODIFIER = 1.0F;
   protected float DISEASE_CHANCE = 0.05F;
   protected int COLONIZE_COST_MOVEMENT_POINTS = 16;
   protected int COLONIZE_COST_DIPLOMACY_POINTS = 14;
   protected float COLONIZE_COST_GOLD_PERC = 0.1675F;

   protected Age(String sName, int iAgeBeginningYear, int iAgeEndYear, float fPopulationGrowthRate, float fEconomyGrowthRate) {
      this.sName = CFG.langManager.get(sName);
      this.iAgeBeginningYear = iAgeBeginningYear;
      this.iAgeEndYear = iAgeEndYear;
      this.fPopulationGrowthRate = fPopulationGrowthRate;
      this.fEconomyGrowthRate = fEconomyGrowthRate;
   }

   protected Age(String sName, int iAgeBeginningYear, int iAgeEndYear, float fPopulationGrowthRate, float fEconomyGrowthRate, float FOG_OF_WAR_DISCOVERY_MET_PROVINCES, float DEVELOPMENT_LEVEL_INCREASE, float INCOME_TAXATION_MODIFIER, float INCOME_PRODUCTION_MODIFIER, float EXPENSES_ADMINSTRATION_MODIFIER, float EXPENSES_MILITARY_UPKEEP_MODIFIER, int BASE_MOVEMENT_POINTS, float MOVEMENT_POINTS_MODIFIER, int BASE_DIPLOMACY_POINTS, float EXPENSES_ADMINSTRATION_DISTANCE, int DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE, float BASE_INCOME_TAXATION, float INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER, float BASE_MILITARY_UPKEEP, float GAME_STARTING_DEVELOPMENT, int GAME_DAYS_PER_TURN, float BASE_INCOME_PRODUCTION, float INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER, float REVOLUTIONARY_RISK_MODIFIER, float DISEASE_CHANCE, float COLONIZE_COST_GOLD_PERC, int COLONIZE_COST_MOVEMENT_POINTS, int COLONIZE_COST_DIPLOMACY_POINTS) {
      this.sName = CFG.langManager.get(sName);
      this.iAgeBeginningYear = iAgeBeginningYear;
      this.iAgeEndYear = iAgeEndYear;
      this.fPopulationGrowthRate = fPopulationGrowthRate;
      this.fEconomyGrowthRate = fEconomyGrowthRate;
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
   }

   protected final String getName() {
      return this.sName;
   }

   protected final void setName(String sName) {
      this.sName = CFG.langManager.get(sName);
   }

   protected final int getBeginningYear() {
      return this.iAgeBeginningYear;
   }

   protected final int getEndYear() {
      return this.iAgeEndYear;
   }

   protected final float getPopulationGrowthRate() {
      return this.fPopulationGrowthRate;
   }

   protected final void setPopulationGrowthRate(float fPopulationGrowthRate) {
      this.fPopulationGrowthRate = fPopulationGrowthRate;
   }

   protected final float getEconomyGrowthRate() {
      return this.fEconomyGrowthRate;
   }

   protected final void setEconomyGrowthRate(float fEconomyGrowthRate) {
      this.fEconomyGrowthRate = fEconomyGrowthRate;
   }
}
