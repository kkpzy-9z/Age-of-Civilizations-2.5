package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.files.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.xml.crypto.Data;
import java.util.*;

class Game_Decisions
{
    protected List<Decision_GameData> lDecisions;

    protected Game_Decisions() {
        super();
        this.loadDecisions();
    }
    
    protected final void loadDecisions() {
        this.lDecisions = new ArrayList<Decision_GameData>();
        try {
            final FileHandle fileList = Gdx.files.internal("game/Decisions.json");
            final String fileContent = fileList.readString();
            final Json json = new Json();
            //ignore unknownfields true so compatible with BE2 coding
            json.setIgnoreUnknownFields(true);
            json.setElementType(ConfigDecisionData.class, "Decisions", Data_Decisions.class);
            ConfigDecisionData data = new ConfigDecisionData();
            data = json.fromJson(ConfigDecisionData.class, fileContent);

            int i = 0;
            for (final Object e : data.Decisions) {
                final Data_Decisions tempData = (Data_Decisions)e;
                Decision_GameData tempDecision = new Decision_GameData();

                tempDecision.setName(tempData.Name);
                tempDecision.setDesc(tempData.Description);
                tempDecision.setTurnLength(tempData.TurnLength);

                tempDecision.setYear(tempData.YearAvailable);
                tempDecision.setMonth(tempData.MonthAvailable);
                tempDecision.setDay(tempData.DayAvailable);

                tempDecision.setRepeatable(tempData.Repeatable);
                tempDecision.setGoldCost(tempData.Cost_Gold);
                tempDecision.setDiploCost(tempData.Cost_Diplomacy * 10.0F);
                tempDecision.setCostEveryTurn(tempData.Cost_Every_Turn);

                tempDecision.fModifier_UpperClass = tempData.Modifier_UpperClass;
                tempDecision.fModifier_MiddleClass = tempData.Modifier_MiddleClass;
                tempDecision.fModifier_LowerClass = tempData.Modifier_LowerClass;

                tempDecision.fModifier_PopGrowth = tempData.Modifier_PopGrowth;
                tempDecision.fModifier_EconomyGrowth = tempData.Modifier_EconomyGrowth;
                tempDecision.fModifier_IncomeTaxation = tempData.Modifier_IncomeTaxation;
                tempDecision.fModifier_IncomeProduction = tempData.Modifier_IncomeProduction;
                tempDecision.fModifier_Administration = tempData.Modifier_Administration;
                tempDecision.fModifier_Research = tempData.Modifier_Research;
                tempDecision.fModifier_MilitaryUpkeep = tempData.Modifier_MilitaryUpkeep;
                tempDecision.fModifier_AttackBonus = tempData.Modifier_AttackBonus;
                tempDecision.fModifier_DefenseBonus = tempData.Modifier_DefenseBonus;
                tempDecision.fModifier_MovementPoints = tempData.Modifier_MovementPoint;

                this.lDecisions.add(tempDecision);
                i++;
            }
        }
        catch (final GdxRuntimeException ex) {
            Gdx.app.log("AoC2.5", "Vassal JSON not detected!");
            //todo manually input placeholder decisions.json like autonomy
            //github fucking sucks just commit already
        }
        for (Decision_GameData decision : this.lDecisions) {
            decision.setName(CFG.langManager.get(decision.getName()));
        }
    }

    protected final void updateLanguage() {
        this.loadDecisions();
    }

    protected final Decision_GameData getDecision(final int i) {
        return this.lDecisions.get(i);
    }


    protected static class ConfigDecisionData
    {
        protected String Age_of_Civilizations;
        protected ArrayList Decisions;
        
        protected ConfigDecisionData() {
            super();
        }
    }
    
    protected static class Data_Decisions
    {
        protected String Name;
        protected String Description;
        protected int TurnLength;

        protected int YearAvailable;
        protected int MonthAvailable;
        protected int DayAvailable;

        protected boolean Repeatable;
        protected float Cost_Gold;
        protected float Cost_Diplomacy;
        protected boolean Cost_Every_Turn;

        protected float Modifier_UpperClass;
        protected float Modifier_MiddleClass;
        protected float Modifier_LowerClass;

        protected float Modifier_PopGrowth;
        protected float Modifier_EconomyGrowth;
        protected float Modifier_IncomeTaxation;
        protected float Modifier_IncomeProduction;
        protected float Modifier_Administration;
        protected float Modifier_Research;
        protected float Modifier_MilitaryUpkeep;
        protected float Modifier_AttackBonus;
        protected float Modifier_DefenseBonus;
        protected float Modifier_MovementPoint;

        protected Data_Decisions() {
            super();
        }
    }
}
