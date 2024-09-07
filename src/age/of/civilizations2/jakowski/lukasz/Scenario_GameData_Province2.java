/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  age.of.civilizations2.jakowski.lukasz.CFG
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.CFG;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Scenario_GameData_Province2
implements Serializable {
    private static final long serialVersionUID = 0L;
    private List<Integer> lProvinceOwners = null;
    //occupied data change//
    private List<Integer> lProvinceOccupiers = null;

    Scenario_GameData_Province2() {
    }
    //called in game savescenario after clicking save on scenario, bypassing game by adding occupied data here
    protected final void buildProvinceOwners() {
        //init occupied list alongside owners
        this.lProvinceOwners = new ArrayList<Integer>();
        this.lProvinceOccupiers = new ArrayList<Integer>();
        int i = 0;
        while (i < CFG.game.getProvincesSize()) {
            this.lProvinceOwners.add(CFG.game.getProvince(i).getCivID());
            this.lProvinceOccupiers.add(CFG.game.getProvince(i).getTrueOwnerOfProvince());
            ++i;
        }
    }

    protected final List<Integer> getProvinceOwners() {
        return this.lProvinceOwners;
    }
    //getoccupiers function
    protected final List<Integer> getProvinceOccupiers() {
        return this.lProvinceOccupiers;
    }
}
