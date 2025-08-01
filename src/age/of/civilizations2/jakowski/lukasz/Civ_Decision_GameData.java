package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Civ_Decision_GameData extends Decision_GameData implements Serializable {
    private List<String> sCivs = new ArrayList<>();
    private String sTag;

    Civ_Decision_GameData() {
        super();
        this.sCivs = new ArrayList<>();
    }

    protected final String getTag() {
        return this.sTag;
    }

    protected final void setTag(final String sTag) {
        this.sTag = sTag;
    }

    protected final void addCiv(final String nTag) {
        if (this.sCivs == null) {
            this.sCivs = new ArrayList<>();
        } else if (this.sCivs.contains(nTag)) {
            return;
        }

        this.sCivs.add(nTag);
    }

    protected final void removeCiv(final int i) {
        this.sCivs.remove(i);
    }

    protected final void setCivs(final List<String> civs) {
        this.sCivs = civs;
    }

    protected final String getCiv(final int i) {
        return this.sCivs.get(i);
    }

    protected final int getCivsSize() {
        return this.sCivs.size();
    }

    protected final boolean containsCiv(final String civTag) {
        return this.sCivs.contains(civTag);
    }

    @Override
    protected Civ_Decision_GameData copy() {
        Civ_Decision_GameData d = new Civ_Decision_GameData();

        d.setName(this.getName());
        d.setDesc(this.getDesc());

        d.setYear(this.getYear());
        d.setMonth(this.getMonth());
        d.setDay(this.getDay());

        d.setTurnsProgress(this.getTurnsProgress());
        d.setTurnLength(this.getTurnLength());

        d.setGoldCost(this.getGoldCost());
        d.setDiploCost(this.getDiploCost());
        d.setInProgress(this.getInProgress());
        d.setRepeatable(this.isRepeatable());
        d.setCostEveryTurn(this.isCostEveryTurn());

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

        d.setCivs(this.sCivs);
        d.setTag(this.sTag);

        return d;
    }
}
