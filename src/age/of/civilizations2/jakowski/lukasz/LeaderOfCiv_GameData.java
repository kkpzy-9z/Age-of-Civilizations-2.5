package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private boolean wasRandom = false;
    private boolean incumbentYear = false;
    private final List<Decision_GameData> decisions;
    private float[] lClassViews; //upper, middle, lower
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
        this.Year = 1;
        this.Month = 1;
        this.Day = 1;
        this.incumbentYear = true;
        this.wasRandom = false;
        this.decisions = new ArrayList<Decision_GameData>();
        this.lClassViews = new float[] { -1.0F, -1.0F, -1.0F };
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

    protected final boolean isRandom() {
        return this.wasRandom;
    }

    protected final void setRandom(final boolean wasRandom) {
        this.wasRandom = wasRandom;
    }

    protected final Decision_GameData getDecision(int index) {
        if (index < 0 || index >= this.decisions.size()) return null;
        return this.decisions.get(index);
    }

    protected final Decision_GameData getDecisionByName(String name) {
        for (Decision_GameData d : this.decisions) {
            if (d.getName().equals(name)) return d;
        }
        return null;
    }

    protected final int getDecisionsCount() {
        return this.decisions.size();
    }

    protected final void addDecision(final Decision_GameData decision) {
        if (decision == null) return;
        this.decisions.add(decision);
    }

    protected final void removeDecision(int index) {
        if (index < 0 || index >= this.decisions.size()) return;
        this.decisions.remove(index);
    }

    protected final void clearDecisions() {
        this.decisions.clear();
    }

    protected final float getClassViews(int classIndex) {
        if (classIndex < 0 || classIndex >= this.lClassViews.length) return 0;
        return this.lClassViews[classIndex];
    }

    protected final void setClassViews(int classIndex, float views) {
        if (classIndex < 0 || classIndex >= this.lClassViews.length) return;
        this.lClassViews[classIndex] = Math.max(0.0F, Math.min(views, 1.0F));
    }

    protected final void clearClassViews() {
        Arrays.fill(this.lClassViews, 0);
    }
}
