package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.files.*;
import java.util.*;

class Game_Ages
{
    private List<Age> lAges;
    private int iAgesSize;
    private String sBC;
    
    protected Game_Ages() {
        super();
        this.loadAges();
    }
    
    protected final void loadAges() {
        this.lAges = new ArrayList<Age>();
        try {
            final FileHandle fileList = Gdx.files.internal("game/Ages.json");
            final String fileContent = fileList.readString();
            final Json json = new Json();
            //ignore unknownfields true so compatible with BE2 coding
            json.setIgnoreUnknownFields(true);
            json.setElementType(ConfigAgesData.class, "Age", Data_Ages.class);
            ConfigAgesData data = new ConfigAgesData();
            data = json.fromJson(ConfigAgesData.class, fileContent);
            for (final Object e : data.Age) {
                final Data_Ages tempData = (Data_Ages)e;
                this.lAges.add(new Age(tempData.Name, tempData.AGE_BeginningYear, tempData.AGE_EndYear, tempData.POPULATION_GROWTH, tempData.ECONOMY_GROWTH, tempData.FOG_OF_WAR_DISCOVERY_MET_PROVINCES, tempData.DEVELOPMENT_LEVEL_INCREASE, tempData.INCOME_TAXATION_MODIFIER, tempData.INCOME_PRODUCTION_MODIFIER, tempData.EXPENSES_ADMINSTRATION_MODIFIER, tempData.EXPENSES_MILITARY_UPKEEP_MODIFIER, tempData.BASE_MOVEMENT_POINTS, tempData.MOVEMENT_POINTS_MODIFIER, tempData.BASE_DIPLOMACY_POINTS, tempData.EXPENSES_ADMINSTRATION_DISTANCE, tempData.DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE, tempData.BASE_INCOME_TAXATION, tempData.INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER, tempData.BASE_MILITARY_UPKEEP, tempData.GAME_STARTING_DEVELOPMENT, tempData.GAME_DAYS_PER_TURN, tempData.BASE_INCOME_PRODUCTION, tempData.INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER, tempData.REVOLUTIONARY_RISK_MODIFIER, tempData.DISEASE_CHANCE, tempData.COLONIZATION_COST, tempData.COLONIZE_COST_MOVEMENT_POINTS, tempData.COLONIZE_COST_DIPLOMACY_POINTS));
            }
        }
        catch (final GdxRuntimeException ex) {
            CFG.exceptionStack(ex);
            this.lAges.add(new Age("AgeofCivilizations", -5000, -301, 0.3f, 0.2f));
            this.lAges.add(new Age("AgeofExpansion", -300, 499, 0.35f, 0.22f));
            this.lAges.add(new Age("AgeofDarkness", 500, 1065, 0.4f, 0.22f));
            this.lAges.add(new Age("AgeofFeudalism", 1066, 1491, 0.45f, 0.22f));
            this.lAges.add(new Age("AgeofDiscovery", 1492, 1749, 0.5f, 0.22f));
            this.lAges.add(new Age("AgeofRevolution", 1750, 1835, 0.55f, 0.22f));
            this.lAges.add(new Age("AgeofIndustrialisation", 1836, 1860, 0.6f, 0.22f));
            this.lAges.add(new Age("AgeofImperialism", 1861, 1918, 0.65f, 0.22f));
            this.lAges.add(new Age("AgeofConflict", 1919, 1946, 0.7f, 0.22f));
            this.lAges.add(new Age("AgeofBrinkmanship", 1947, 1990, 0.75f, 0.22f));
            this.lAges.add(new Age("AgeofInformation", 1991, 2049, 0.8f, 0.22f));
            this.lAges.add(new Age("AgeofTomorrow", 2050, 5000, 0.95f, 1.0f));
        }
        this.sBC = CFG.langManager.get("BeforeChrist");
        this.iAgesSize = this.lAges.size();
        for (int i = 0; i < this.iAgesSize; ++i) {
            this.lAges.get(i).setName(CFG.langManager.get(this.lAges.get(i).getName()));
        }
    }
    
    protected final void updateLanguage() {
        this.loadAges();
    }
    
    protected final String getYear(final int nYear) {
        return (nYear < 0) ? ("" + -nYear + " " + this.getBC()) : ("" + nYear);
    }
    
    protected final int getAgeOfYear(final int nYear) {
        for (int i = 0; i < this.lAges.size() - 1; ++i) {
            if (this.lAges.get(i).getBeginningYear() <= nYear && this.lAges.get(i).getEndYear() >= nYear) {
                return i;
            }
        }
        return this.lAges.size() - 1;
    }
    
    protected final float getAge_FogOfWarDiscovery_MetProvinces(final int nAgeID) {
        return this.lAges.get(nAgeID).FOG_OF_WAR_DISCOVERY_MET_PROVINCES;
    }
    
    protected final float getAge_Population_GrowthRate(final int nAgeID) {
        return this.lAges.get(nAgeID).getPopulationGrowthRate();
    }
    
    protected final float getAge_Economy_GrowthRate(final int nAgeID) {
        return this.lAges.get(nAgeID).getEconomyGrowthRate();
    }
    
    protected final float getAge_DevelopmentLevel_Increase(final int nAgeID) {
        return this.lAges.get(nAgeID).DEVELOPMENT_LEVEL_INCREASE;
    }
    
    protected final float getAge_TreasuryModifier(final int nAgeID) {
        return this.lAges.get(nAgeID).INCOME_TAXATION_MODIFIER;
    }
    
    protected final float getAge_TreasuryModifier_Production(final int nAgeID) {
        return this.lAges.get(nAgeID).INCOME_PRODUCTION_MODIFIER;
    }
    
    protected final float getAge_TreasuryModifier_Administration(final int nAgeID) {
        return this.lAges.get(nAgeID).EXPENSES_ADMINSTRATION_MODIFIER;
    }
    
    protected final float getAge_TreasuryModifier_MilitaryUpkeep(final int nAgeID) {
        return this.lAges.get(nAgeID).EXPENSES_MILITARY_UPKEEP_MODIFIER;
    }
    
    protected final int getAge_StartingMovementPoints(final int nAgeID) {
        return this.lAges.get(nAgeID).BASE_MOVEMENT_POINTS;
    }
    
    protected final float getAge_MovementPointsModifier(final int nAgeID) {
        return this.lAges.get(nAgeID).MOVEMENT_POINTS_MODIFIER;
    }
    
    protected final int getAge_StartingDiplomacyPoints(final int nAgeID) {
        return this.lAges.get(nAgeID).BASE_DIPLOMACY_POINTS;
    }
    
    protected final float getAge_AdministrationCost_Distance(final int nAgeID) {
        return this.lAges.get(nAgeID).EXPENSES_ADMINSTRATION_DISTANCE;
    }
    
    protected final float getAge_DistanceDiplomacy(final int nAgeID) {
        return (float)this.lAges.get(nAgeID).DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE;
    }
    
    protected final float getAge_IncomeTaxation_Base(final int nAgeID) {
        return this.lAges.get(nAgeID).BASE_INCOME_TAXATION;
    }
    
    protected final float getAge_IncomeTaxation_PerTechnology(final int nAgeID) {
        return this.lAges.get(nAgeID).INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER;
    }
    
    protected final float getAge_MilitaryUpkeep(final int nAgeID) {
        return this.lAges.get(nAgeID).BASE_MILITARY_UPKEEP;
    }
    
    protected final float getAge_StartingDevelopment(final int nAgeID) {
        return this.lAges.get(nAgeID).GAME_STARTING_DEVELOPMENT;
    }
    
    protected final float getAge_IncomeProduction_Base(final int nAgeID) {
        return this.lAges.get(nAgeID).BASE_INCOME_PRODUCTION;
    }
    
    protected final float getAge_IncomeProduction_PerDevelopment(final int nAgeID) {
        return this.lAges.get(nAgeID).INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER;
    }
    
    protected final float getAge_RevolutionaryRiskModifier(final int nAgeID) {
        return this.lAges.get(nAgeID).REVOLUTIONARY_RISK_MODIFIER;
    }
    
    protected final float getAge_DiseaseChance(final int nAgeID) {
        return this.lAges.get(nAgeID).DISEASE_CHANCE;
    }
    
    protected final int getAge_TurnDays(final int nAgeID) {
        return (int)(this.lAges.get(nAgeID).GAME_DAYS_PER_TURN * Game_Calendar.GAME_SPEED);
    }
    
    protected final Age getAge(final int i) {
        return this.lAges.get(i);
    }
    
    protected final String getBC() {
        return this.sBC;
    }
    
    protected final int getAgesSize() {
        return this.iAgesSize;
    }
    
    protected static class ConfigAgesData
    {
        protected String Age_of_Civilizations;
        protected ArrayList Age;
        
        protected ConfigAgesData() {
            super();
        }
    }
    
    protected static class Data_Ages
    {
        protected String Name;
        protected int AGE_BeginningYear;
        protected int AGE_EndYear;
        protected float POPULATION_GROWTH;
        protected float ECONOMY_GROWTH;
        protected float DEVELOPMENT_LEVEL_INCREASE;
        protected float INCOME_TAXATION_MODIFIER;
        protected float INCOME_PRODUCTION_MODIFIER;
        protected float EXPENSES_ADMINSTRATION_MODIFIER;
        protected float EXPENSES_MILITARY_UPKEEP_MODIFIER;
        protected int BASE_MOVEMENT_POINTS;
        protected float FOG_OF_WAR_DISCOVERY_MET_PROVINCES;
        protected float MOVEMENT_POINTS_MODIFIER;
        protected int BASE_DIPLOMACY_POINTS;
        protected float EXPENSES_ADMINSTRATION_DISTANCE;
        protected int DIPLOMACY_ALLIANCE_PROPOSAL_NAGATIVE_DISTANCE;
        protected float BASE_INCOME_TAXATION;
        protected float INCOME_TAXATION_PER_TECHNOLOGY_MODIFIER;
        protected float BASE_MILITARY_UPKEEP;
        protected float GAME_STARTING_DEVELOPMENT;
        protected int GAME_DAYS_PER_TURN;
        protected float BASE_INCOME_PRODUCTION;
        protected float INCOME_PRODUCTIONN_PER_DEVELOPMENT_MODIFIER;
        protected float REVOLUTIONARY_RISK_MODIFIER;
        protected float COLONIZATION_COST;
        protected int COLONIZE_COST_MOVEMENT_POINTS;
        protected int COLONIZE_COST_DIPLOMACY_POINTS;
        protected float DISEASE_CHANCE;
        
        protected Data_Ages() {
            super();
        }
    }
}
