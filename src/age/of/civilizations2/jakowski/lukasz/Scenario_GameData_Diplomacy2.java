/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  age.of.civilizations2.jakowski.lukasz.CFG
 *  age.of.civilizations2.jakowski.lukasz.Color_GameData
 *  age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_AlliancesData
 *  age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_Data
 *  age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_VassalsData
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Color_GameData;
import age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_AlliancesData;
import age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_Data;
import age.of.civilizations2.jakowski.lukasz.Scenario_GameData_Diplomacy_VassalsData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Scenario_GameData_Diplomacy2
implements Serializable {
    private static final long serialVersionUID = 0L;
    private List<Scenario_GameData_Diplomacy_AlliancesData> lAlliances;
    private List<Scenario_GameData_Diplomacy_Data> lRelations;
    private List<Scenario_GameData_Diplomacy_Data> lPacts;
    private List<Scenario_GameData_Diplomacy_VassalsData> lVassals;
    private List<Scenario_GameData_Diplomacy_Data> lMilitaryAccess;
    private List<Scenario_GameData_Diplomacy_Data> lDefensivePacts;
    private List<Scenario_GameData_Diplomacy_Data> lGuarantee;
    private List<Scenario_GameData_Diplomacy_Data> lTruces;

    Scenario_GameData_Diplomacy2() {
    }

    protected final void buildData() {
        int j;
        int i;
        this.lAlliances = new ArrayList<Scenario_GameData_Diplomacy_AlliancesData>();
        this.lRelations = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        this.lPacts = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        this.lVassals = new ArrayList<Scenario_GameData_Diplomacy_VassalsData>();
        this.lMilitaryAccess = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        this.lDefensivePacts = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        this.lGuarantee = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        this.lTruces = new ArrayList<Scenario_GameData_Diplomacy_Data>();
        for (i = 1; i < CFG.game.getAlliancesSize(); ++i) {
            this.lAlliances.add(new Scenario_GameData_Diplomacy_AlliancesData(CFG.game.getAlliance(i).getAllianceName(), new Color_GameData(CFG.game.getAlliance(i).getColorOfAlliance().getR(), CFG.game.getAlliance(i).getColorOfAlliance().getG(), CFG.game.getAlliance(i).getColorOfAlliance().getB())));
            for (j = 0; j < CFG.game.getAlliance(i).getCivilizationsSize(); ++j) {
                this.lAlliances.get(i - 1).addCiv(CFG.game.getAlliance(i).getCivilization(j));
            }
        }
        for (i = 1; i < CFG.game.getCivsSize(); ++i) {
            for (j = 1; j < CFG.game.getCivsSize(); ++j) {
                if (i == j) continue;
                if (CFG.game.getCivRelation_OfCivB(i, j) != 0.0f) {
                    this.lRelations.add(new Scenario_GameData_Diplomacy_Data(i, j, (int)CFG.game.getCivRelation_OfCivB(i, j)));
                }
                if (CFG.game.getGuarantee(i, j) > 0) {
                    this.lGuarantee.add(new Scenario_GameData_Diplomacy_Data(i, j, CFG.game.getGuarantee(i, j)));
                }
                if (CFG.game.getMilitaryAccess(i, j) <= 0) continue;
                this.lMilitaryAccess.add(new Scenario_GameData_Diplomacy_Data(i, j, CFG.game.getMilitaryAccess(i, j)));
            }
            if (CFG.game.getCiv(i).getCivID() == CFG.game.getCiv(i).getPuppetOfCivID()) continue;
            this.lVassals.add(new Scenario_GameData_Diplomacy_VassalsData(CFG.game.getCiv(i).getCivID(), CFG.game.getCiv(i).getPuppetOfCivID(), CFG.game.getCiv(CFG.game.getCiv(i).getPuppetOfCivID()).getVassal_AutonomyStatus(i).getIndexOf()));
        }
        i = 1;
        while (i < CFG.game.getCivsSize() - 1) {
            for (j = i + 1; j < CFG.game.getCivsSize(); ++j) {
                if (CFG.game.getCivNonAggressionPact(i, j) > 0) {
                    this.lPacts.add(new Scenario_GameData_Diplomacy_Data(i, j, CFG.game.getCivNonAggressionPact(i, j)));
                }
                if (CFG.game.getDefensivePact(i, j) > 0) {
                    this.lDefensivePacts.add(new Scenario_GameData_Diplomacy_Data(i, j, CFG.game.getDefensivePact(i, j)));
                }
                if (CFG.game.getCivTruce(i, j) <= 0) continue;
                this.lTruces.add(new Scenario_GameData_Diplomacy_Data(i, j, CFG.game.getCivTruce(i, j)));
            }
            ++i;
        }
    }

    protected final List<Scenario_GameData_Diplomacy_AlliancesData> getAlliances() {
        return this.lAlliances;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getRelations() {
        return this.lRelations;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getPacts() {
        return this.lPacts;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getTruces() {
        return this.lTruces;
    }

    protected final List<Scenario_GameData_Diplomacy_VassalsData> getVassals() {
        return this.lVassals;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getGuarantee() {
        return this.lGuarantee;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getMilitaryAccess() {
        return this.lMilitaryAccess;
    }

    protected final List<Scenario_GameData_Diplomacy_Data> getDefensivePacts() {
        return this.lDefensivePacts;
    }
}
