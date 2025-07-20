package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

public class Decision_GameData implements Serializable {
    private static final long serialVersionUID = 0L;
    private String sName;
    private String sDesc;
    private int Year;
    private int Month;
    private int Day;
    private int progress;
    private int turnLength;
    private boolean inProgress = false;
    private boolean repeatable = false;

    protected float fModifier_UpperClass;
    protected float fModifier_MiddleClass;
    protected float fModifier_LowerClass;

    protected float fModifier_PopGrowth;
    protected float fModifier_EconomyGrowth;
    protected float fModifier_IncomeTaxation;
    protected float fModifier_IncomeProduction;
    protected float fModifier_Administration;
    protected float fModifier_Research;
    protected float fModifier_MilitaryUpkeep;
    protected float fModifier_AttackBonus;
    protected float fModifier_DefenseBonus;
    protected float fModifier_MovementPoints;

    Decision_GameData() {
        super();
        this.sName = "";
        this.sDesc = "";
        this.Year = 3;
        this.Month = 1;
        this.Day = 1;
        this.progress = 0;
        this.turnLength = 10;
        this.inProgress = false;
        this.repeatable = false;

        this.fModifier_UpperClass = 0.0f;
        this.fModifier_MiddleClass = 0.0f;
        this.fModifier_LowerClass = 0.0f;

        this.fModifier_PopGrowth = 0.0f;
        this.fModifier_EconomyGrowth = 0.0f;
        this.fModifier_IncomeTaxation = 0.0f;
        this.fModifier_IncomeProduction = 0.0f;
        this.fModifier_Administration = 0.0f;
        this.fModifier_Research = 0.0f;
        this.fModifier_MilitaryUpkeep = 0.0f;
        this.fModifier_AttackBonus = 0.0f;
        this.fModifier_DefenseBonus = 0.0f;
        this.fModifier_MovementPoints = 0.0f;
    }

    protected final String getName() {
        //made to return langmanager of name
        return CFG.langManager.get(this.sName);
    }

    protected final void setName(final String sName) {
        this.sName = sName;
    }

    protected final String getDesc() {
        return this.sDesc;
    }

    protected final void setDesc(final String sDesc) {
        this.sDesc = sDesc;
    }

    protected final int getTurnLength() {
        return this.turnLength;
    }

    protected final void setTurnLength(final int turnLength) {
        this.turnLength = Math.max(1, turnLength);
    }

    protected final int getMonth() {
        return this.Month;
    }

    protected final void setMonth(final int month) {
        this.Month = month;
    }

    protected final int getYear() {
        return this.Year;
    }

    protected final void setYear(final int year) {
        this.Year = year;
    }

    protected final int getDay() {
        return this.Day;
    }

    protected final void setDay(final int day) {
        this.Day = day;
    }

    protected final int getTurnsProgress() {
        return this.progress;
    }

    protected final void setTurnsProgress(final int progress) {
        this.progress = Math.max(0, Math.min(turnLength, progress));
        if (this.progress >= this.turnLength) {
            this.inProgress = true;
        }
    }

    protected final boolean getInProgress() {
        return this.inProgress;
    }

    protected final void setInProgress(final boolean inProgress) {
        this.inProgress = inProgress;
    }

    protected final boolean isRepeatable() {
        return this.repeatable;
    }

    protected final void setRepeatable(final boolean repeatable) {
        this.repeatable = repeatable;
    }

    protected final Decision_GameData copy() {
        Decision_GameData d = new Decision_GameData();
        d.sName = this.sName;
        d.sDesc = this.sDesc;
        d.Year = this.Year;
        d.Month = this.Month;
        d.Day = this.Day;
        d.progress = this.progress;
        d.turnLength = this.turnLength;
        d.inProgress = this.inProgress;
        d.repeatable = this.repeatable;

        d.fModifier_UpperClass = this.fModifier_UpperClass;
        d.fModifier_MiddleClass = this.fModifier_MiddleClass;
        d.fModifier_LowerClass = this.fModifier_LowerClass;

        d.fModifier_PopGrowth = this.fModifier_PopGrowth;
        d.fModifier_EconomyGrowth = this.fModifier_EconomyGrowth;
        d.fModifier_IncomeTaxation = this.fModifier_IncomeTaxation;
        d.fModifier_IncomeProduction = this.fModifier_IncomeProduction;
        d.fModifier_Administration = this.fModifier_Administration;
        d.fModifier_Research = this.fModifier_Research;
        d.fModifier_MilitaryUpkeep = this.fModifier_MilitaryUpkeep;
        d.fModifier_AttackBonus = this.fModifier_AttackBonus;
        d.fModifier_DefenseBonus = this.fModifier_DefenseBonus;
        d.fModifier_MovementPoints = this.fModifier_MovementPoints;

        return d;
    }
}
