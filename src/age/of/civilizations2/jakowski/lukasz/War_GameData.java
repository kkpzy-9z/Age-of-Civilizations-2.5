package age.of.civilizations2.jakowski.lukasz;

import java.io.*;
import java.util.*;

class War_GameData implements Serializable
{
    private static final long serialVersionUID = 0L;
    private List<WarCiv_GameData> lAggressors;
    private List<WarCiv_GameData> lDefenders;
    private int iWarTurnID;
    protected int iLastFight_InTunrs;
    protected int iLastTurn_ConqueredProvince;
    protected boolean wasAnyAttack;
    protected boolean canEnd = true;
    protected String WAR_TAG;
    protected static final float WAR_SCORE_MODIFIER = 0.7f;
    protected static final float WAR_SCORE_MODIFIER2 = 0.2f;
    
    protected War_GameData(final int nAggressor, final int nDefender) {
        super();
        this.lAggressors = new ArrayList<WarCiv_GameData>();
        this.lDefenders = new ArrayList<WarCiv_GameData>();
        this.iWarTurnID = 1;
        this.iLastFight_InTunrs = 0;
        this.iLastTurn_ConqueredProvince = 0;
        this.wasAnyAttack = false;
        this.addAggressor(nAggressor);
        this.addDefender(nDefender);
        this.iWarTurnID = Game_Calendar.TURN_ID;
        this.canEnd = true;
        this.WAR_TAG = CFG.game.getCiv(nAggressor).getCivTag() + CFG.game.getCiv(nDefender).getCivTag() + CFG.extraRandomTag() + this.iWarTurnID;
    }
    
    protected final void addAggressor(final int nCivID) {
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.lAggressors.get(i).getCivID() == nCivID) {
                return;
            }
        }
        this.lAggressors.add(new WarCiv_GameData(nCivID));
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (!CFG.game.getCivsAtWar(nCivID, this.getDefenderID(i).getCivID())) {
                CFG.game.setCivRelation_OfCivB(nCivID, this.getDefenderID(i).getCivID(), -100.0f);
                CFG.game.setCivRelation_OfCivB(this.getDefenderID(i).getCivID(), nCivID, -100.0f);
            }
        }
        this.iLastFight_InTunrs = 0;
        this.iLastTurn_ConqueredProvince = Game_Calendar.TURN_ID;
    }
    
    protected final void removeAggressor(final int nCivID) {
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.lAggressors.get(i).getCivID() == nCivID) {
                this.lAggressors.remove(i);
                return;
            }
        }
    }
    
    protected final void addDefender(final int nCivID) {
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.lDefenders.get(i).getCivID() == nCivID) {
                return;
            }
        }
        this.lDefenders.add(new WarCiv_GameData(nCivID));
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (!CFG.game.getCivsAtWar(nCivID, this.getAggressorID(i).getCivID())) {
                CFG.game.setCivRelation_OfCivB(nCivID, this.getAggressorID(i).getCivID(), -100.0f);
                CFG.game.setCivRelation_OfCivB(this.getAggressorID(i).getCivID(), nCivID, -100.0f);
            }
        }
        this.iLastFight_InTunrs = 0;
        this.iLastTurn_ConqueredProvince = Game_Calendar.TURN_ID;
    }
    
    protected final void removeDefender(final int nCivID) {
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.lDefenders.get(i).getCivID() == nCivID) {
                this.lDefenders.remove(i);
                return;
            }
        }
    }
    
    protected final void updateAfterUnion(final int nCivA, final int nCivB) {
        if (this.getIsAggressor(nCivA) && this.getIsAggressor(nCivB)) {
            final int nID = this.getAggressorID_ByCivID(nCivA);
            final int nID2 = this.getAggressorID_ByCivID(nCivB);
            if (nID >= 0 && nID2 >= 0) {
                this.getAggressorID(nID).addCivilianDeaths(this.getAggressorID(nID2).getCivilianDeaths());
                this.getAggressorID(nID).addCasualties(this.getAggressorID(nID2).getCasualties());
                this.getAggressorID(nID).addEconomicLosses(this.getAggressorID(nID2).getEconomicLosses());
                this.removeAggressor(nCivB);
            }
        }
        else if (this.getIsDefender(nCivA) && this.getIsDefender(nCivB)) {
            final int nID = this.getDefenderID_ByCivID(nCivA);
            final int nID2 = this.getDefenderID_ByCivID(nCivB);
            if (nID >= 0 && nID2 >= 0) {
                this.getDefenderID(nID).addCivilianDeaths(this.getDefenderID(nID2).getCivilianDeaths());
                this.getDefenderID(nID).addCasualties(this.getDefenderID(nID2).getCasualties());
                this.getDefenderID(nID).addEconomicLosses(this.getDefenderID(nID2).getEconomicLosses());
                this.removeDefender(nCivB);
            }
        }
        else if (this.getIsAggressor(nCivB) && !this.getIsDefender(nCivA)) {
            final int nID = this.getAggressorID_ByCivID(nCivB);
            if (nID >= 0) {
                this.getAggressorID(nID).setCivID(nCivA);
            }
        }
        else if (this.getIsDefender(nCivB) && !this.getIsAggressor(nCivA)) {
            final int nID = this.getDefenderID_ByCivID(nCivB);
            if (nID >= 0) {
                this.getDefenderID(nID).setCivID(nCivA);
            }
        }
    }
    
    protected final boolean getIsAggressor(final int nCivID) {
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == nCivID) {
                return true;
            }
        }
        return false;
    }
    
    protected final boolean getIsDefender(final int nCivID) {
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == nCivID) {
                return true;
            }
        }
        return false;
    }
    
    protected final int getWarScore() {
        int tempNumOfProvincesInWar_Aggrersors = 0;
        int tempNumOfProvincesInWar_Defenders = 0;
        int tempControledEnemyProvinces_ByAggrersors = 0;
        int tempControledEnemyProvinces_ByDefenders = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getAggressorID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    tempNumOfProvincesInWar_Aggrersors += CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                }
                else {
                    for (int k = 0; k < this.getDefendersSize(); ++k) {
                        if (this.getDefenderID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            tempControledEnemyProvinces_ByAggrersors += CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getDefenderID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    tempNumOfProvincesInWar_Defenders += CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j));
                }
                else {
                    for (int k = 0; k < this.getAggressorsSize(); ++k) {
                        if (this.getAggressorID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            tempControledEnemyProvinces_ByDefenders += CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        int tempAggressorsPerc = 0;
        int tempDefendersPerc = 0;
        try {
            tempAggressorsPerc = (int)(tempControledEnemyProvinces_ByAggrersors / (float)(tempNumOfProvincesInWar_Defenders + tempControledEnemyProvinces_ByDefenders + tempControledEnemyProvinces_ByAggrersors) * 100.0f);
        }
        catch (final ArithmeticException ex) {
            tempAggressorsPerc = 0;
        }
        try {
            tempDefendersPerc = (int)(tempControledEnemyProvinces_ByDefenders / (float)(tempNumOfProvincesInWar_Aggrersors + tempControledEnemyProvinces_ByAggrersors + tempControledEnemyProvinces_ByDefenders) * 100.0f);
        }
        catch (final ArithmeticException ex) {
            tempDefendersPerc = 0;
        }
        return -tempAggressorsPerc + tempDefendersPerc;
    }
    
    protected final int getWarScore_PeaceTreaty() {
        int tempNumOfProvincesInWar_Aggrersors = 0;
        int tempNumOfProvincesInWar_Defenders = 0;
        int tempControledEnemyProvinces_ByAggrersors = 0;
        int tempControledEnemyProvinces_ByDefenders = 0;
        for (int i = 0; i < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.size(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID).getNumOfProvinces(); ++j) {
                if (CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID == CFG.game.getProvince(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    tempNumOfProvincesInWar_Aggrersors += CFG.game.getProvinceValue(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID).getProvinceID(j));
                }
                else {
                    for (int k = 0; k < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.size(); ++k) {
                        if (CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            tempControledEnemyProvinces_ByAggrersors += CFG.game.getProvinceValue(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.size(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID).getNumOfProvinces(); ++j) {
                if (CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID == CFG.game.getProvince(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    tempNumOfProvincesInWar_Defenders += CFG.game.getProvinceValue(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID).getProvinceID(j));
                }
                else {
                    for (int k = 0; k < CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.size(); ++k) {
                        if (CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Aggressors.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            tempControledEnemyProvinces_ByDefenders += CFG.game.getProvinceValue(CFG.game.getCiv(CFG.peaceTreatyData.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        int tempAggressorsPerc = 0;
        int tempDefendersPerc = 0;
        try {
            tempAggressorsPerc = (int)(tempControledEnemyProvinces_ByAggrersors / (float)(tempNumOfProvincesInWar_Defenders + tempControledEnemyProvinces_ByDefenders + tempControledEnemyProvinces_ByAggrersors) * 100.0f);
        }
        catch (final ArithmeticException ex) {
            tempAggressorsPerc = 0;
        }
        try {
            tempDefendersPerc = (int)(tempControledEnemyProvinces_ByDefenders / (float)(tempNumOfProvincesInWar_Aggrersors + tempControledEnemyProvinces_ByAggrersors + tempControledEnemyProvinces_ByDefenders) * 100.0f);
        }
        catch (final ArithmeticException ex) {
            tempDefendersPerc = 0;
        }
        return -tempAggressorsPerc + tempDefendersPerc;
    }
    
    protected final int getWarScore_DefendersInProvinceValue() {
        int outScore = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getAggressorID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    for (int k = 0; k < this.getDefendersSize(); ++k) {
                        if (this.getDefenderID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outScore += CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getDefenderID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    for (int k = 0; k < this.getAggressorsSize(); ++k) {
                        if (this.getAggressorID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outScore -= CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        return outScore;
    }
    
    protected final int getWarScore_DefendersInProvinceValue(final int id) {
        int outScore = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getAggressorID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    if (this.getDefenderID(id).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        outScore -= CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                    }
                }
            }
        }
        for (int k = 0; k < CFG.game.getCiv(this.getDefenderID(id).getCivID()).getNumOfProvinces(); ++k) {
            if (this.getDefenderID(id).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(k)).getTrueOwnerOfProvince()) {
                for (int l = 0; l < this.getAggressorsSize(); ++l) {
                    if (this.getAggressorID(l).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(k)).getTrueOwnerOfProvince()) {
                        outScore += CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(k));
                        break;
                    }
                }
            }
        }
        return outScore;
    }
    
    protected final int getWarScore_DefendersInProvinceValue_OnlyPositive(final int id, final List<Boolean> addDefender, final List<Boolean> addAggressor) {
        int outScore = 0;
        int iMinScore = 0;
        final List<War_Points> nPoints = new ArrayList<War_Points>();
        for (int k = 0; k < this.getAggressorsSize(); ++k) {
            nPoints.add(new War_Points(this.getAggressorID(k).getCivID()));
        }
        for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(id).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getDefenderID(id).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                for (int i = 0; i < this.getAggressorsSize(); ++i) {
                    if (addAggressor.get(i) && this.getAggressorID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        final int nValue = CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(j));
                        if (nValue > iMinScore) {
                            iMinScore = nValue;
                        }
                        nPoints.get(i).addPoints(nValue);
                        break;
                    }
                }
            }
        }
        int defenderNumOfTrueProvinces = 0;
        for (int l = 0; l < CFG.game.getCiv(this.getDefenderID(id).getCivID()).getNumOfProvinces(); ++l) {
            if (CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(id).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince() == this.getDefenderID(id).getCivID()) {
                ++defenderNumOfTrueProvinces;
            }
        }
        for (int l = nPoints.size() - 1; l >= 0; --l) {
            if (defenderNumOfTrueProvinces >= nPoints.get(l).getNumOfProvincesTotal() * 2.5f || nPoints.get(l).getNumOfProvincesTotal() <= 2 || nPoints.get(l).iNumOfLostProvinces <= 2) {
                outScore += nPoints.get(l).iPoints;
            }
            else {
                float fModifer = 1.0f;
                try {
                    if (nPoints.get(l).getNumOfProvincesTotal() == 3) {
                        fModifer = 0.7f + 0.3f * (1.0f - Math.min(this.getAggressorID(l).getConqueredProvinces() / (float)Math.max(this.getDefenderID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                    else if (defenderNumOfTrueProvinces < nPoints.get(l).getNumOfProvincesTotal()) {
                        fModifer = 0.2f + 0.1f * (nPoints.get(l).iNumOfLostProvinces / nPoints.get(l).getNumOfProvincesTotal()) + 0.2f * (1.0f - defenderNumOfTrueProvinces / (float)nPoints.get(l).getNumOfProvincesTotal()) + 0.35f * (1.0f - Math.min(this.getAggressorID(l).getConqueredProvinces() / (float)Math.max(this.getDefenderID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                    else {
                        fModifer = 0.2f + 0.1f * (nPoints.get(l).iNumOfLostProvinces / nPoints.get(l).getNumOfProvincesTotal()) + 0.35f * (1.0f - Math.min(this.getAggressorID(l).getConqueredProvinces() / (float)Math.max(this.getDefenderID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                }
                catch (final IllegalArgumentException ex) {
                    fModifer = 0.7f;
                    CFG.exceptionStack(ex);
                }
                outScore += (int)Math.max(Math.ceil(nPoints.get(l).iPoints * fModifer), nPoints.get(l).iMinScore);
            }
        }
        return Math.max(outScore, iMinScore);
    }
    
    protected final int getWarScore_AggressorsInProvinceValue() {
        int outScore = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getAggressorID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    for (int k = 0; k < this.getDefendersSize(); ++k) {
                        if (this.getDefenderID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outScore -= CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
                if (this.getDefenderID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                    for (int k = 0; k < this.getAggressorsSize(); ++k) {
                        if (this.getAggressorID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outScore += CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j));
                            break;
                        }
                    }
                }
            }
        }
        return outScore;
    }
    
    protected final int getWarScore_AggressorsInProvinceValue(final int id) {
        int outScore = 0;
        for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(id).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getAggressorID(id).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                for (int k = 0; k < this.getDefendersSize(); ++k) {
                    if (this.getDefenderID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        outScore += CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j));
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            for (int l = 0; l < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++l) {
                if (this.getDefenderID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince()) {
                    if (this.getAggressorID(id).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince()) {
                        outScore -= CFG.game.getProvinceValue(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(l));
                    }
                }
            }
        }
        return outScore;
    }
    
    protected final int getWarScore_AggressorsInProvinceValue_OnlyPositive(final int id, final List<Boolean> addDefender, final List<Boolean> addAggressor) {
        int outScore = 0;
        int iMinScore = 0;
        final List<War_Points> nPoints = new ArrayList<War_Points>();
        for (int k = 0; k < this.getDefendersSize(); ++k) {
            nPoints.add(new War_Points(this.getDefenderID(k).getCivID()));
        }
        for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(id).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getAggressorID(id).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                for (int i = 0; i < this.getDefendersSize(); ++i) {
                    if (addDefender.get(i) && this.getDefenderID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        final int nValue = CFG.game.getProvinceValue(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(j));
                        if (nValue > iMinScore) {
                            iMinScore = nValue;
                        }
                        nPoints.get(i).addPoints(nValue);
                        break;
                    }
                }
            }
        }
        int defenderNumOfTrueProvinces = 0;
        for (int l = 0; l < CFG.game.getCiv(this.getAggressorID(id).getCivID()).getNumOfProvinces(); ++l) {
            if (CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(id).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince() == this.getAggressorID(id).getCivID()) {
                ++defenderNumOfTrueProvinces;
            }
        }
        for (int l = nPoints.size() - 1; l >= 0; --l) {
            if (defenderNumOfTrueProvinces >= nPoints.get(l).getNumOfProvincesTotal() * 2.5f || nPoints.get(l).getNumOfProvincesTotal() <= 2 || nPoints.get(l).iNumOfLostProvinces <= 2) {
                outScore += nPoints.get(l).iPoints;
            }
            else {
                float fModifer = 1.0f;
                try {
                    if (nPoints.get(l).getNumOfProvincesTotal() == 3) {
                        fModifer = 0.7f + 0.3f * (1.0f - Math.min(this.getDefenderID(l).getConqueredProvinces() / (float)Math.max(this.getAggressorID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                    else if (defenderNumOfTrueProvinces < nPoints.get(l).getNumOfProvincesTotal()) {
                        fModifer = 0.2f + 0.1f * (nPoints.get(l).iNumOfLostProvinces / nPoints.get(l).getNumOfProvincesTotal()) + 0.2f * (1.0f - defenderNumOfTrueProvinces / (float)nPoints.get(l).getNumOfProvincesTotal()) + 0.35f * (1.0f - Math.min(this.getDefenderID(l).getConqueredProvinces() / (float)Math.max(this.getAggressorID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                    else {
                        fModifer = 0.2f + 0.1f * (nPoints.get(l).iNumOfLostProvinces / nPoints.get(l).getNumOfProvincesTotal()) + 0.35f * (1.0f - Math.min(this.getDefenderID(l).getConqueredProvinces() / (float)Math.max(this.getAggressorID(id).getConqueredProvinces(), 1), 1.0f));
                    }
                }
                catch (final IllegalArgumentException ex) {
                    fModifer = 0.7f;
                    CFG.exceptionStack(ex);
                }
                outScore += (int)Math.max(Math.ceil(nPoints.get(l).iPoints * fModifer), nPoints.get(l).iMinScore);
            }
        }
        return Math.max(outScore, iMinScore);
    }
    
    protected final PeaceTreaty_Civs getDefenders_ProvincesLost(final int id, final List<Boolean> addDefender, final List<Boolean> addAggressor) {
        final PeaceTreaty_Civs outPC = new PeaceTreaty_Civs(this.getDefenderID(id).getCivID());
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (addAggressor.get(i)) {
                for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
                    if (this.getAggressorID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        if (this.getDefenderID(id).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outPC.lProvincesLost.add(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j));
                        }
                    }
                }
            }
        }
        return outPC;
    }
    
    protected final PeaceTreaty_Civs getAggressors_ProvincesLost(final int id, final List<Boolean> addDefender, final List<Boolean> addAggressor) {
        final PeaceTreaty_Civs outPC = new PeaceTreaty_Civs(this.getAggressorID(id).getCivID());
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (addDefender.get(i)) {
                for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
                    if (this.getDefenderID(i).getCivID() != CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        if (this.getAggressorID(id).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                            outPC.lProvincesLost.add(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j));
                        }
                    }
                }
            }
        }
        return outPC;
    }
    
    protected final int getProvinces_Aggressor_Own(final int i) {
        int out = 0;
        for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getAggressorID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                ++out;
            }
        }
        for (int k = 0; k < this.getDefendersSize(); ++k) {
            for (int l = 0; l < CFG.game.getCiv(this.getDefenderID(k).getCivID()).getNumOfProvinces(); ++l) {
                if (this.getAggressorID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(k).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince()) {
                    ++out;
                }
            }
        }
        return out;
    }
    
    protected final int getProvinces_Aggressor_OwnTotal(final int i) {
        int out = 0;
        for (int j = 0; j < CFG.game.getCiv(this.getAggressorID(i).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getAggressorID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                ++out;
            }
            else {
                for (int k = 0; k < this.getDefendersSize(); ++k) {
                    if (this.getDefenderID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        ++out;
                        break;
                    }
                }
            }
        }
        return out;
    }
    
    protected final int getProvinces_Defender_Own(final int i) {
        int out = 0;
        for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getDefenderID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                ++out;
            }
        }
        for (int k = 0; k < this.getAggressorsSize(); ++k) {
            for (int l = 0; l < CFG.game.getCiv(this.getAggressorID(k).getCivID()).getNumOfProvinces(); ++l) {
                if (this.getDefenderID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getAggressorID(k).getCivID()).getProvinceID(l)).getTrueOwnerOfProvince()) {
                    ++out;
                }
            }
        }
        return out;
    }
    
    protected final int getProvinces_Defender_OwnTotal(final int i) {
        int out = 0;
        for (int j = 0; j < CFG.game.getCiv(this.getDefenderID(i).getCivID()).getNumOfProvinces(); ++j) {
            if (this.getDefenderID(i).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                ++out;
            }
            else {
                for (int k = 0; k < this.getAggressorsSize(); ++k) {
                    if (this.getAggressorID(k).getCivID() == CFG.game.getProvince(CFG.game.getCiv(this.getDefenderID(i).getCivID()).getProvinceID(j)).getTrueOwnerOfProvince()) {
                        ++out;
                        break;
                    }
                }
            }
        }
        return out;
    }
    
    protected final WarCiv_GameData getAggressorID(final int i) {
        return this.lAggressors.get(i);
    }
    
    protected final int getAggressorID_ByCivID(final int nCivID) {
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == nCivID) {
                return i;
            }
        }
        return -1;
    }
    
    protected final boolean getIsInAggressors(final int nCivID) {
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == nCivID) {
                return true;
            }
        }
        return false;
    }
    
    protected final int getAggressorsSize() {
        return this.lAggressors.size();
    }
    
    protected final WarCiv_GameData getDefenderID(final int i) {
        return this.lDefenders.get(i);
    }
    
    protected final int getDefenderID_ByCivID(final int nCivID) {
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == nCivID) {
                return i;
            }
        }
        return -1;
    }
    
    protected final boolean getIsInDefenders(final int nCivID) {
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == nCivID) {
                return true;
            }
        }
        return false;
    }
    
    protected final int getDefendersSize() {
        return this.lDefenders.size();
    }
    
    protected final int getParticipation_DefenderID(final int nID) {
        int out = 0;
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            out += this.getDefenderID(i).getCasualties();
        }
        if (out == 0) {
            return 100;
        }
        return (int)((nID == 0) ? Math.ceil(this.getDefenderID(nID).getCasualties() / (float)out * 100.0f) : Math.floor(this.getDefenderID(nID).getCasualties() / (float)out * 100.0f));
    }
    
    protected final int getParticipation_AggressorID(final int nID) {
        int out = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            out += this.getAggressorID(i).getCasualties();
        }
        if (out == 0) {
            return 100;
        }
        return (int)((nID == 0) ? Math.ceil(this.getAggressorID(nID).getCasualties() / (float)out * 100.0f) : Math.floor(this.getAggressorID(nID).getCasualties() / (float)out * 100.0f));
    }
    
    protected final void addConqueredProvinces(final int iCivID) {
        this.iLastTurn_ConqueredProvince = Game_Calendar.TURN_ID;
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == iCivID) {
                this.getDefenderID(i).addConqueredProvinces();
                return;
            }
        }
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == iCivID) {
                this.getAggressorID(i).addConqueredProvinces();
                return;
            }
        }
    }
    
    protected final void addCasualties(final int iCivID, final int iCasualties) {
        this.iLastFight_InTunrs = 0;
        this.wasAnyAttack = true;
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == iCivID) {
                this.getDefenderID(i).addCasualties(iCasualties);
                return;
            }
        }
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == iCivID) {
                this.getAggressorID(i).addCasualties(iCasualties);
                return;
            }
        }
    }
    
    protected final void addCivilianEconomicLosses(final int iCivID, final int iCivilianDeaths, final int iEconomicLosses) {
        this.iLastFight_InTunrs = 0;
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            if (this.getDefenderID(i).getCivID() == iCivID) {
                this.getDefenderID(i).addCivilianDeaths(iCivilianDeaths);
                this.getDefenderID(i).addEconomicLosses(iEconomicLosses);
                return;
            }
        }
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            if (this.getAggressorID(i).getCivID() == iCivID) {
                this.getAggressorID(i).addCivilianDeaths(iCivilianDeaths);
                this.getAggressorID(i).addEconomicLosses(iEconomicLosses);
                return;
            }
        }
    }
    
    protected final int getCasualties_Defenders() {
        int out = 0;
        for (int i = 0; i < this.getDefendersSize(); ++i) {
            out += this.getDefenderID(i).getCasualties();
            out += this.getDefenderID(i).getCivilianDeaths();
        }
        return out;
    }
    
    protected final int getCasualties_Aggressors() {
        int out = 0;
        for (int i = 0; i < this.getAggressorsSize(); ++i) {
            out += this.getAggressorID(i).getCasualties();
            out += this.getAggressorID(i).getCivilianDeaths();
        }
        return out;
    }
    
    protected final int getWarTurnID() {
        return this.iWarTurnID;
    }
    
    protected final void setWarTurnID(final int iWarTurnID) {
        this.iWarTurnID = iWarTurnID;
    }
}
