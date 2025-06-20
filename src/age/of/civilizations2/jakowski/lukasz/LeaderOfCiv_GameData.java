package age.of.civilizations2.jakowski.lukasz;

import java.io.*;

class LeaderOfCiv_GameData implements Serializable
{
    private static final long serialVersionUID = 0L;
    private String sTag;
    private String sName;
    private String sImage;
    private String sWiki;
    private int Year;
    private int Month;
    private int Day;
    private boolean incumbentYear = false;
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
    
    LeaderOfCiv_GameData() {
        super();
        this.sTag = "";
        this.sName = "";
        this.sImage = "";
        this.sWiki = "";
        this.Year = 3;
        this.Month = 2;
        this.Day = 1;
        this.incumbentYear = true;
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
    
    protected final String getImage() {
        return this.sImage;
    }
    
    protected final void setImage(final String sImage) {
        this.sImage = sImage;
    }
    
    protected final String getWiki() {
        return this.sWiki;
    }
    
    protected final void setWiki(final String sWiki) {
        this.sWiki = sWiki;
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
    
    protected final String getTag() {
        return this.sTag;
    }
    
    protected final void setTag(final String sTag) {
        this.sTag = sTag;
    }

    protected final boolean isIncumbentYear() {
        return this.incumbentYear;
    }

    protected final void setIncumbentYear(final boolean incumbentYear) {
        this.incumbentYear = incumbentYear;
    }
}
