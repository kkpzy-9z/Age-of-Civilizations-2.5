package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import java.util.*;

class Game_NextTurnUpdate
{
    protected static int INFLATION_PEAK_VALUE = 100;
    protected static final float INFLATION_STARTS_AT = 0.235f;
    protected static final float INFLATION_MODIFIER = 18.12746f;
    protected static float LEAGUE_BUDGET = 1.0F;
    protected static final float TAXES_INFLUENCE_POP = 0.3f;
    protected static final float TAXES_INFLUENCE_PRODUCTION = 0.175f;
    protected static final int PERCENTAGE_OF_INCOME_FOR_LORD_DEFAULT = 9;
    protected static final int PERCENTAGE_OF_INCOME_FOR_LORD_MAX = 20;
    protected static final int PERCENTAGE_OF_INCOME_FOR_LORD_MIN = 0;
    protected static final float PERCENTAGE_OF_INCOME_FOR_WAR_REPARATIONS = 0.08f;
    protected static final float EMPLOYEMENT_PER_ECONOMY = 1.025f;
    protected static final float EMPLOYEMENT_PER_ECONOMY_OLD = 1.775f;
    protected static final float DEFENSIVE_POSITION_MILITARY_UPKEEP_PER_TUR = 0.008f;
    protected static final int BUDGET_MAX = 200;
    
    Game_NextTurnUpdate() {
        super();
    }

    protected final void updateDecisions() {
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            for (int decIndex = 0; decIndex < CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecisionsCount(); decIndex++) {
                if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).getInProgress()) {
                    CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).setTurnsProgress(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).getTurnsProgress() + 1);
                    if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).getTurnsProgress() >= CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).getTurnLength()) {
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).applyDecisionChange_Expired(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex));

                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(0, (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(0) + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).fModifier_UpperClass));
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(1, (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(1) + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).fModifier_MiddleClass));
                        CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.setClassViews(2, (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getClassViews(2) + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).fModifier_LowerClass));

                        if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).isRepeatable()) {
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).setInProgress(false);
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.getDecision(decIndex).setTurnsProgress(0);
                        } else {
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.leaderData.removeDecision(decIndex);
                            --decIndex;
                        }
                        //CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.messageBox.addMessage(new Message_DecisionCompleted(CFG.game.getPlayer(i).getCivID(), decIndex));
                    }
                }
            }
        }
    }

    protected final void updatePlayableProvinces() {
        CFG.oAI.PLAYABLE_PROVINCES = 0;
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
                final AI oai = CFG.oAI;
                ++oai.PLAYABLE_PROVINCES;
            }
        }
        CFG.oAI.NUM_OF_CIVS_IN_THE_GAME = 0;
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                final AI oai2 = CFG.oAI;
                ++oai2.NUM_OF_CIVS_IN_THE_GAME;
            }
        }
        CFG.oAI.NUM_OF_CIVS_IN_THE_GAME = Math.max(1, CFG.oAI.NUM_OF_CIVS_IN_THE_GAME);
        CFG.oAI.updateMinRivals();
    }
    
    protected final void updateInflationPeakValue() {
        Game_NextTurnUpdate.INFLATION_PEAK_VALUE = 1;
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                Game_NextTurnUpdate.INFLATION_PEAK_VALUE = Math.max(Game_NextTurnUpdate.INFLATION_PEAK_VALUE, CFG.game.getCiv(i).iIncomeTaxation + CFG.game.getCiv(i).iIncomeProduction);
                Game_NextTurnUpdate.LEAGUE_BUDGET = (float)(int)Math.max(Game_NextTurnUpdate.LEAGUE_BUDGET, (float)(CFG.game.getCiv(i).iIncomeTaxation + CFG.game.getCiv(i).iIncomeProduction - CFG.game.getCiv(i).iAdministrationCosts));
            }
        }
        Game_NextTurnUpdate.LEAGUE_BUDGET = (float)(int)(Game_NextTurnUpdate.LEAGUE_BUDGET * 0.9f);
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                CFG.game.getCiv(i).iLeague = Math.min((int)(Math.max(CFG.game.getCiv(i).iIncomeTaxation + CFG.game.getCiv(i).iIncomeProduction - CFG.game.getCiv(i).iAdministrationCosts, 0) / Game_NextTurnUpdate.LEAGUE_BUDGET * 10.0f), 10);
            }
        }
        for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            if (this.getInflationPerc(CFG.game.getPlayer(i).getCivID()) > 0.0049f) {
                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_HighInflation(CFG.game.getPlayer(i).getCivID(), 0));
            }
        }
    }
    
    protected final void updateCivs_Money() {
        Gdx.app.log("AoC", "updateCivs_Money 0000");
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.getBalance_UpdateBudget_Prepare(i);
        }
        Gdx.app.log("AoC", "updateCivs_Money 11111");
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                //if puppet and no eco control add budget to lord civ
                if (CFG.game.getCiv(i).getIsPupet() && !CFG.game.getCiv(CFG.game.getCiv(i).getPuppetOfCivID()).getVassal_AutonomyStatus(i).isEconomicControl()) {
                    CFG.game.getCiv(CFG.game.getCiv(i).getPuppetOfCivID()).setMoney(CFG.game.getCiv(CFG.game.getCiv(i).getPuppetOfCivID()).getMoney() + this.getBalance(i));
                } else {
                    CFG.game.getCiv(i).setMoney(CFG.game.getCiv(i).getMoney() + this.getBalance(i));
                    CFG.game.getCiv(i).updateLoansNextTurn();
                }
            }
        }
        Gdx.app.log("AoC", "updateCivs_Money END");
    }
    
    protected final void updateProvinceStability() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).lProvincesWithLowStability.clear();
            CFG.game.getCiv(i).fStability = 0.0f;
        }
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
                CFG.game.getProvince(i).updateProvinceStability();
                if (CFG.game.getProvince(i).getCivID() > 0) {
                    final Civilization civ = CFG.game.getCiv(CFG.game.getProvince(i).getCivID());
                    civ.fStability += CFG.game.getProvince(i).getProvinceStability();
                }
            }
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            for (int j = CFG.game.getCiv(i).lProvincesWithLowStability.size() - 1; j >= 0; --j) {
                if (CFG.game.getCiv(i).isAssimilateOrganized(CFG.game.getCiv(i).lProvincesWithLowStability.get(j))) {
                    CFG.game.getCiv(i).lProvincesWithLowStability.remove(j);
                }
            }
            CFG.game.getCiv(i).setStability(CFG.game.getCiv(i).fStability / CFG.game.getCiv(i).getNumOfProvinces());
        }
    }
    
    protected final int getBalance(final int nCivID) {
        return (int)(this.getIncome(nCivID) - this.getExpenses(nCivID));
    }
    
    protected final int getAdministration_Capital(final int nCivID) {
        return (CFG.game.getCiv(nCivID).getCapitalProvinceID() < 0) ? ((CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) ? CFG.game.getCiv(nCivID).getProvinceID(0) : 0) : CFG.game.getCiv(nCivID).getCapitalProvinceID();
    }
    
    protected final void getBalance_UpdateBudget_Prepare(final int nCivID) {
        CFG.game.getCiv(nCivID).iIncomeTaxation = 0;
        CFG.game.getCiv(nCivID).iIncomeProduction = 0;
        CFG.game.getCiv(nCivID).iAdministrationCosts = 0;
        final int nCapital = this.getAdministration_Capital(nCivID);
        final float incomeModifer = this.taxIncome_Modifier(nCivID);
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Taxation = this.getProvinceIncome_Taxation(CFG.game.getCiv(nCivID).getProvinceID(i), nCivID, incomeModifer);
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Production = this.getProvinceIncome_Production(CFG.game.getCiv(nCivID).getProvinceID(i), nCivID, incomeModifer);
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iAdministrationCost = Math.min(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Taxation + CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Production, this.getProvinceAdministration(CFG.game.getCiv(nCivID).getProvinceID(i), nCapital));
            final Civilization civ = CFG.game.getCiv(nCivID);
            civ.iIncomeTaxation += (int)CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Taxation;
            final Civilization civ2 = CFG.game.getCiv(nCivID);
            civ2.iIncomeProduction += (int)CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iIncome_Production;
            final Civilization civ3 = CFG.game.getCiv(nCivID);
            //new vassal order
            if (CFG.game.getCiv(nCivID).getIsPupet() && !CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isEconomicControl()) {
                //admin * (n stability + (globilization * distance percent))
                civ3.iAdministrationCosts += (int) ((int) CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iAdministrationCost * ((1.0F - CFG.game.getCiv(nCivID).getStability()) +
                                        ((1.25F - CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID)) * CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getProvinceID(i), CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getCapitalProvinceID()))));

                //new equation w distance percent, still too expensive
                //civ3.iAdministrationCosts += (int) (0.5F * CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iAdministrationCost * ((1.25F - CFG.game.getCiv(nCivID).getStability())));
            } else {
                civ3.iAdministrationCosts += (int) CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).iAdministrationCost;
            }

            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getBalance_LastTurn() < 0) {
                final Save_Provinces_GameData saveProvinceData = CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).saveProvinceData;
                ++saveProvinceData.iNumOfTurnsWithBalanceOnMinus;
            }
            else {
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).saveProvinceData.iNumOfTurnsWithBalanceOnMinus = 0;
            }
        }
        CFG.game.getCiv(nCivID).iBudget = (int)(this.getIncome(nCivID) - CFG.game.getCiv(nCivID).iAdministrationCosts);
    }
    
    protected final float getHappinessChange_ByTaxation(final int nCivID) {
        return 0.042f + ((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * CFG.game.getCiv(nCivID).getTechnologyLevel() / 21.73f) * 100.0f - CFG.game.getCiv(nCivID).getTaxationLevel() * 100.0f) * ((CFG.game.getCiv(nCivID).getTaxationLevel() > CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION) ? 1.45f : 1.0f) * 0.02675f;
    }
    
    protected final float getHappinessChange_ByTaxation_Occupied(final int nCivID) {
        return 0.034f + ((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * CFG.game.getCiv(nCivID).getTechnologyLevel() / 21.73f) * 100.0f - CFG.game.getCiv(nCivID).getTaxationLevel() * 100.0f) * ((CFG.game.getCiv(nCivID).getTaxationLevel() > CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION) ? 1.45f : 1.0f) * 0.02675f;
    }
    
    protected float taxIncome_Modifier(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getControlledByPlayer()) {
            switch (CFG.DIFFICULTY) {
                case 0: {
                    return 1.25f;
                }
                case 2: {
                    return 1.0f;
                }
                case 3: {
                    return 0.975f;
                }
                case 4: {
                    return 0.95f;
                }
                default: {
                    return 1.025f;
                }
            }
        }
        else {
            switch (CFG.DIFFICULTY) {
                case 0: {
                    return 0.925f;
                }
                case 2: {
                    return 1.025f;
                }
                case 3: {
                    return 1.04f;
                }
                case 4: {
                    return 1.065f;
                }
                default: {
                    return 1.0f;
                }
            }
        }
    }
    
    protected final int getMilitarySpendings(final int nCivID, final int iBudget) {
        return Math.max(0, (int)(this.getMilitaryUpkeep_Total(nCivID) / iBudget * 100.0f));
    }
    
    protected final float getIncome(final int nCivID) {
        float tempTotal = 0.0f;
        tempTotal += CFG.game.getCiv(nCivID).iIncomeTaxation;
        tempTotal += CFG.game.getCiv(nCivID).iIncomeProduction;
        tempTotal += this.getIncome_FromVassalsOfCiv(nCivID);
        tempTotal += this.getIncome_Debuff_IsVassal(nCivID);
        tempTotal += this.getIncome_Buff_WarReparations(nCivID);
        tempTotal += this.getIncome_Debuff_WarReparations(nCivID);
        return (float)(int)tempTotal;
    }
    
    protected final float getIncome_TaxesLevel(final int nCivID) {
        return this.getIncome_TaxesLevel_Taxation(nCivID) + this.getIncome_TaxesLevel_Production(nCivID);
    }
    
    protected final float getIncome_TaxesLevel_Taxation(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tempTotal += this.getProvinceIncome_Taxation(CFG.game.getCiv(nCivID).getProvinceID(i));
        }
        return tempTotal;
    }
    
    protected final float getIncome_TaxesLevel_Production(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tempTotal += this.getProvinceIncome_Production(CFG.game.getCiv(nCivID).getProvinceID(i));
        }
        return tempTotal;
    }
    
    protected final float getIncome_Debuff_IsVassal(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getPuppetOfCivID() != nCivID) {
            return -this.getIncome_Vassals(CFG.game.getCiv(nCivID).getPuppetOfCivID(), nCivID);
        }
        return 0.0f;
    }
    
    protected final float getIncome_FromVassalsOfCiv(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = CFG.game.getCiv(nCivID).civGameData.lVassals.size() - 1; i >= 0; --i) {
            //if int eco add total income of civ
            //else add tribute
            if (!CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).autonomyStatus.isEconomicControl()) {
                tempTotal += this.getIncome(CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID);
            } else {
                tempTotal += this.getIncome_Vassals(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID);
            }
        }
        return tempTotal;
    }
    
    protected final float getIncome_Debuff_WarReparations(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = CFG.game.getCiv(nCivID).getWarReparationsPaysSize() - 1; i >= 0; --i) {
            tempTotal -= this.getWarReparationsMoney(nCivID);
        }
        return tempTotal;
    }
    
    protected final float getIncome_Buff_WarReparations(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = CFG.game.getCiv(nCivID).getWarReparationsGetsSize() - 1; i >= 0; --i) {
            tempTotal += this.getWarReparationsMoney(CFG.game.getCiv(nCivID).getWarReparationsGets(i).iFromCivID);
        }
        return tempTotal;
    }
    
    protected final float getIncome_Vassals(final int nForCivID, final int nIsVassal) {
        if (CFG.game.getCiv(nIsVassal).getPuppetOfCivID() == nForCivID) {
            //only add tribute if unintegrated vassal economy
            if (CFG.game.getCiv(nForCivID).getVassal_AutonomyStatus(nIsVassal) == null ||
                CFG.game.getCiv(nForCivID).getVassal_AutonomyStatus(nIsVassal).isEconomicControl()) {
                return this.getVassalizationMoney(nIsVassal);
            }
        }
        return 0.0f;
    }
    
    protected final float getVassalizationMoney(final int nVassalID) {
        return CFG.game.getCiv(nVassalID).iIncomeTaxation * (CFG.game.getCiv(CFG.game.getCiv(nVassalID).getPuppetOfCivID()).getVassal_Tribute(nVassalID) / 100.0f);
    }
    
    protected final float getWarReparationsMoney(final int nCivID) {
        return CFG.game.getCiv(nCivID).iIncomeTaxation * 0.08f;
    }
    
    protected final float getProvinceIncomeAndExpenses_Total(final int nProvinceID) {
        return this.getProvinceIncome_Taxation(nProvinceID) + this.getProvinceIncome_Production(nProvinceID) - ((CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCapitalProvinceID() >= 0) ? this.getProvinceAdministration(nProvinceID, CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCapitalProvinceID()) : 0.0f);
    }
    
    protected final float getProvinceIncome_Taxation(final int nProvinceID) {
        return this.getProvinceIncome_Taxation(nProvinceID, CFG.game.getProvince(nProvinceID).getCivID(), this.taxIncome_Modifier(CFG.game.getProvince(nProvinceID).getCivID()));
    }
    
    protected final float getProvinceIncome_Taxation(final int nProvinceID, final int nCivID, final float incomeModifer) {
        if (CFG.game.getProvince(nProvinceID).isOccupied()) {
            return this.getProvinceAdministration(nProvinceID, CFG.game_NextTurnUpdate.getAdministration_Capital(nCivID));
        }
        return (float)(Math.pow(this.getProvince_EmploymentPopulation(nProvinceID) * (CFG.gameAges.getAge_IncomeTaxation_Base(Game_Calendar.CURRENT_AGEID) + CFG.gameAges.getAge_IncomeTaxation_PerTechnology(Game_Calendar.CURRENT_AGEID) * CFG.game.getCiv(nCivID).getTechnologyLevel() * 21.923813f), 0.8386) + Math.pow(this.getProvince_UnemploymentPopulation(nProvinceID) * (CFG.gameAges.getAge_IncomeTaxation_Base(Game_Calendar.CURRENT_AGEID) + CFG.gameAges.getAge_IncomeTaxation_PerTechnology(Game_Calendar.CURRENT_AGEID) * CFG.game.getCiv(nCivID).getTechnologyLevel() * 21.923813f), 0.7936)) * CFG.gameAges.getAge_TreasuryModifier(Game_Calendar.CURRENT_AGEID) * (0.675f + 0.325f * CFG.game.getProvince(nProvinceID).getProvinceStability()) * (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).INCOME_TAXATION + CFG.game.getCiv(nCivID).getModifier_IncomeTaxation() + (CFG.game.getProvince(nProvinceID).getIsCapital() ? 0.1f : 0.0f) - 0.16584f + 0.3674786f * CFG.game.getProvince(nProvinceID).getHappiness()) * (0.7f + 0.3f * CFG.game.getCiv(nCivID).getTaxationLevel()) * incomeModifer * Game_Calendar.GAME_SPEED;
    }
    
    protected final int getProvince_EmploymentPopulation(final int nProvinceID) {
        return (int)Math.min((float)CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation(), CFG.game.getProvince(nProvinceID).getEconomy() * (1.775f + 0.1725f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() + 0.0925f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel()));
    }
    
    protected final int getProvince_UnemploymentPopulation(final int nProvinceID) {
        return Math.max(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() - this.getProvince_EmploymentPopulation(nProvinceID), 0);
    }
    
    protected final int getEmploymentPopulation(final int nCivID) {
        int out = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            out += this.getProvince_EmploymentPopulation(CFG.game.getCiv(nCivID).getProvinceID(i));
        }
        return out;
    }
    
    protected final int getUnemploymentPopulation(final int nCivID) {
        int out = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            out += this.getProvince_UnemploymentPopulation(CFG.game.getCiv(nCivID).getProvinceID(i));
        }
        return out;
    }
    
    protected final float getProvinceIncome_Production(final int nProvinceID) {
        return this.getProvinceIncome_Production(nProvinceID, CFG.game.getProvince(nProvinceID).getCivID(), this.taxIncome_Modifier(CFG.game.getProvince(nProvinceID).getCivID()));
    }
    
    protected final float getProvinceIncome_Production(final int nProvinceID, final int nCivID, final float incomeModifer) {
        if (CFG.game.getProvince(nProvinceID).isOccupied()) {
            return (int)Math.min(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * (1.025f + 0.1725f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() + 0.0425f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel()), (float)CFG.game.getProvince(nProvinceID).getEconomy()) * (CFG.gameAges.getAge_IncomeProduction_Base(Game_Calendar.CURRENT_AGEID) + CFG.gameAges.getAge_IncomeProduction_PerDevelopment(Game_Calendar.CURRENT_AGEID) * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()) * (0.0685f + 0.575f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel() + 0.8625f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()) * (0.425f + 0.575f * CFG.game.getProvince(nProvinceID).getProvinceStability()) * CFG.gameAges.getAge_TreasuryModifier_Production(Game_Calendar.CURRENT_AGEID) * (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID()).INCOME_PRODUCTION + BuildingsManager.getPort_IncomeProduction(CFG.game.getProvince(nProvinceID).getLevelOfPort()) + CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getModifier_IncomeProduction() + (CFG.game.getProvince(nProvinceID).getIsCapital() ? 0.2f : 0.0f) + BuildingsManager.getWorkshop_IncomeProduction(CFG.game.getProvince(nProvinceID).getLevelOfWorkshop())) * (0.825f + 0.175f * CFG.game.getCiv(nCivID).getTaxationLevel()) * incomeModifer * Game_Calendar.GAME_SPEED * 0.1f;
        }
        return (int)Math.min(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * (1.025f + 0.1725f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() + 0.0425f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel()), (float)CFG.game.getProvince(nProvinceID).getEconomy()) * (CFG.gameAges.getAge_IncomeProduction_Base(Game_Calendar.CURRENT_AGEID) + CFG.gameAges.getAge_IncomeProduction_PerDevelopment(Game_Calendar.CURRENT_AGEID) * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()) * (0.0685f + 0.575f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel() + 0.8625f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()) * (0.425f + 0.575f * CFG.game.getProvince(nProvinceID).getProvinceStability()) * CFG.gameAges.getAge_TreasuryModifier_Production(Game_Calendar.CURRENT_AGEID) * (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID()).INCOME_PRODUCTION + BuildingsManager.getPort_IncomeProduction(CFG.game.getProvince(nProvinceID).getLevelOfPort()) + CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getModifier_IncomeProduction() + (CFG.game.getProvince(nProvinceID).getIsCapital() ? 0.2f : 0.0f) + BuildingsManager.getWorkshop_IncomeProduction(CFG.game.getProvince(nProvinceID).getLevelOfWorkshop())) * (0.825f + 0.175f * CFG.game.getCiv(nCivID).getTaxationLevel()) * incomeModifer * Game_Calendar.GAME_SPEED;
    }
    
    protected final float getExpenses(final int nCivID) {
        float tempTotal = 0.0f;
        tempTotal += CFG.game.getCiv(nCivID).iAdministrationCosts;
        //new vassals expense
        tempTotal += this.getExpense_FromVassalsOfCiv(nCivID);
        tempTotal += this.getMilitaryUpkeep_Total(nCivID);
        tempTotal += this.getInvestments_Total(nCivID, CFG.game.getCiv(nCivID).iBudget);
        tempTotal += this.getGoodsSpendings(nCivID, CFG.game.getCiv(nCivID).iBudget);
        tempTotal += this.getInterestCost(nCivID);
        tempTotal += this.getInflation(nCivID);
        tempTotal += CFG.game.getCiv(nCivID).getLoans_GoldTotalPerTurn();
        return (float)(int)Math.ceil(tempTotal);
    }

    protected final float getExpensesIsVassal(final int nCivID) {
        float tempTotal = 0.0f;
        //admin cost is bloated for some reason

        //admin * (n stability + distance percent)
        tempTotal += CFG.game.getCiv(nCivID).iAdministrationCosts;
        tempTotal += this.getMilitaryUpkeep_Total(nCivID);
        //reduced by factor now directly in get spendings functions
        tempTotal += this.getInvestments_Total(nCivID, CFG.game.getCiv(nCivID).iBudget);
        tempTotal += this.getGoodsSpendings(nCivID, CFG.game.getCiv(nCivID).iBudget);
        //tempTotal += this.getInterestCost(nCivID);
        //tempTotal += this.getInflation(nCivID);
        //tempTotal += CFG.game.getCiv(nCivID).getLoans_GoldTotalPerTurn();
        return (float)(int)Math.ceil(tempTotal);
    }
    
    protected final float getExpenses_Budget(final int nCivID) {
        float tempTotal = 0.0f;
        tempTotal += CFG.game.getCiv(nCivID).iAdministrationCosts;
        return (float)(int)Math.ceil(tempTotal);
    }

    //new vassal expense function
    protected final float getExpense_FromVassalsOfCiv(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = CFG.game.getCiv(nCivID).civGameData.lVassals.size() - 1; i >= 0; --i) {
            //if integrated eco add total income of civ
            //tribute added already in admin cost (?), getBalance_UpdateBudget_Prepare function
            if (!CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).autonomyStatus.isEconomicControl()) {
                //add this vassals expenses
                tempTotal += this.getExpensesIsVassal(CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID);
            }
        }
        return tempTotal;
    }

    //new vassals milupkeep
    protected final float getMilitaryUpkeep_FromVassalsOfCiv(final int nCivID) {
        float tempTotal = 0.0f;
        for (int j = CFG.game.getCiv(nCivID).civGameData.lVassals.size() - 1; j >= 0; --j) {
            //if mil add upkeep of civ
            //else add tribute
            if (CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).autonomyStatus.isMilitaryControl()) {
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getNumOfProvinces(); ++i) {
                    tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getProvinceID(i), CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID);
                }
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getArmyInAnotherProvinceSize(); ++i) {
                    tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getArmyInAnotherProvince(i), CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID);
                }
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnitsSize(); ++i) {
                    tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getFromProvinceID(), CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnits(i).getNumOfUnits(), CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID);
                }
                for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnitsPlunderSize(); ++i) {
                    tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnits_Plunder(i).getFromProvinceID(), CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getMoveUnits_Plunder(i).getNumOfUnits(), CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID);
                }
                return (float)(int)Math.ceil(tempTotal);
            }
        }
        return tempTotal;
    }

    protected final float getInflation(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getMoney() < 0L) {
            return 0.0f;
        }
        try {
            //if sandbox return no inflation
            if (CFG.SANDBOX_MODE && nCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                return 0.0f;
            }

            if (CFG.game.getCiv(nCivID).getMoney() / ((Game_NextTurnUpdate.INFLATION_PEAK_VALUE * 1.1275f + (CFG.game.getCiv(nCivID).iIncomeTaxation + CFG.game.getCiv(nCivID).iIncomeProduction) * 0.4f) * 18.12746f) > 0.235f) {
                return 1.0f + CFG.game.getCiv(nCivID).getMoney() * (CFG.game.getCiv(nCivID).getMoney() / (Game_NextTurnUpdate.INFLATION_PEAK_VALUE * 18.12746f) - 0.235f) * 0.0679248f;
            }
        }
        catch (final ArithmeticException ex) {
            return 0.0f;
        }
        return 0.0f;
    }
    
    protected final float getInflationPerc(final int nCivID) {
        return Math.max(this.getInflation(nCivID) / CFG.game.getCiv(nCivID).getMoney(), 0.0f);
    }
    
    protected final float getInterestCost(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getMoney() < 0L) {
            return Math.min(Math.abs(CFG.game.getCiv(nCivID).getMoney()) * 0.01274f, Math.abs(CFG.game.getCiv(nCivID).iBudget * 0.075f));
        }
        return 0.0f;
    }
    
    protected final float getAdministrationCost_Update(final int nCivID) {
        float tempTotal = 0.0f;
        try {
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                tempTotal += this.getProvinceAdministration(CFG.game.getCiv(nCivID).getProvinceID(i), CFG.game_NextTurnUpdate.getAdministration_Capital(nCivID));
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        return tempTotal;
    }
    
    protected final float getProvinceAdministration(final int nProvinceID, final int nCapital) {
        return (float)Math.pow(CFG.game.getProvince(nProvinceID).getEconomy() * Math.min(1.0f, CFG.game.getProvince(nProvinceID).getEconomy() / (float)CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation()) * 0.003248f + CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * (0.0024f + 7.25E-4f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()), 0.93478) * (1.0f + (this.getDistanceFromCapital_PercOfMax(nCapital, nProvinceID) / (1.5275f + CFG.game.getProvince(nProvinceID).getProvinceStability() / 8.0f) * CFG.gameAges.getAge_AdministrationCost_Distance(Game_Calendar.CURRENT_AGEID) + 0.13468f - 0.13468f * CFG.game.getProvince(nProvinceID).getHappiness()) * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID()).ADMINISTRATION_COST_DISTANCE) * (0.9325f + 0.0715f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTaxationLevel() + 0.0325f * (1.0f - CFG.game.getProvince(nProvinceID).getProvinceStability())) * CFG.gameAges.getAge_TreasuryModifier_Administration(Game_Calendar.CURRENT_AGEID) * (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID()).ADMINISTRATION_COST + CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getModifier_Administation()) * ((nProvinceID == nCapital) ? CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getIdeologyID()).ADMINISTRATION_COST_CAPITAL : 1.0f) * Game_Calendar.GAME_SPEED;
    }
    
    protected final float getDistanceFromCapital(final int nCapital, final int toProvinceID) {
        try {
            if (CFG.map.getMapWorldMap(CFG.map.getActiveMapID())) {
                return Math.min(Math.min((float)Math.sqrt(Math.pow(CFG.game.getProvince(toProvinceID).getCenterX_Real() + CFG.map.getMapBG().getWidth_Real() - CFG.game.getProvince(nCapital).getCenterX_Real(), 2.0) + Math.pow(CFG.game.getProvince(toProvinceID).getCenterY_Real() - CFG.game.getProvince(nCapital).getCenterY_Real(), 2.0)), (float)Math.sqrt(Math.pow(CFG.game.getProvince(toProvinceID).getCenterX_Real() - (CFG.game.getProvince(nCapital).getCenterX_Real() + CFG.map.getMapBG().getWidth_Real()), 2.0) + Math.pow(CFG.game.getProvince(toProvinceID).getCenterY_Real() - CFG.game.getProvince(nCapital).getCenterY_Real(), 2.0))), (float)Math.sqrt(Math.pow(CFG.game.getProvince(toProvinceID).getCenterX_Real() - CFG.game.getProvince(nCapital).getCenterX_Real(), 2.0) + Math.pow(CFG.game.getProvince(toProvinceID).getCenterY_Real() - CFG.game.getProvince(nCapital).getCenterY_Real(), 2.0)));
            }
            return (float)Math.sqrt(Math.pow(CFG.game.getProvince(toProvinceID).getCenterX_Real() - CFG.game.getProvince(nCapital).getCenterX_Real(), 2.0) + Math.pow(CFG.game.getProvince(toProvinceID).getCenterY_Real() - CFG.game.getProvince(nCapital).getCenterY_Real(), 2.0));
        }
        catch (final IndexOutOfBoundsException ex) {
            return (float)CFG.map.getMapBG().getMaxDistance();
        }
    }
    
    protected final float getDistanceFromCapital_PercOfMax(final int nCapital, final int toProvinceID) {
        return this.getDistanceFromCapital(nCapital, toProvinceID) / CFG.map.getMapBG().getMaxDistance();
    }
    
    protected final float getDistanceFromAToB_PercOfMax(final int nProvinceA, final int nProvinceB) {
        return this.getDistanceFromCapital(nProvinceA, nProvinceB) / CFG.map.getMapBG().getMaxDistance();
    }
    
    protected final float getMilitaryUpkeep_Total(final int nCivID) {
        float tempTotal = 0.0f;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getProvinceID(i), nCivID);
        }
        for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
            tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), nCivID);
        }
        for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsSize(); ++i) {
            tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getMoveUnits(i).getFromProvinceID(), CFG.game.getCiv(nCivID).getMoveUnits(i).getNumOfUnits(), nCivID);
        }
        for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsPlunderSize(); ++i) {
            tempTotal += this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getMoveUnits_Plunder(i).getFromProvinceID(), CFG.game.getCiv(nCivID).getMoveUnits_Plunder(i).getNumOfUnits(), nCivID);
        }
        return (float)(int)Math.ceil(tempTotal);
    }
    
    protected final float getMilitaryUpkeep(final int nProvinceID, final int nCivID) {
        return this.getMilitaryUpkeep(nProvinceID, CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID), nCivID);
    }
    
    protected final float getMilitaryUpkeep_WithAllRecruitmentsInProcess(final int nProvinceID, final int nArmy, final int nCivID) {
        int out = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getRecruitArmySize(); ++i) {
            if (CFG.game.getCiv(nCivID).getRecruitArmy(i).getProvinceID() != nProvinceID) {
                out += (int)this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getRecruitArmy(i).getProvinceID(), CFG.game.getCiv(nCivID).getRecruitArmy(i).getArmy(), nCivID);
            }
        }
        return out + this.getMilitaryUpkeep(nProvinceID, nArmy, nCivID);
    }
    
    protected final float getMilitaryUpkeep_WithAllRecruitmentsInProcess_Disband(final int nProvinceID, final int nArmy, final int nCivID) {
        int out = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getRecruitArmySize(); ++i) {
            if (CFG.game.getCiv(nCivID).getRecruitArmy(i).getProvinceID() != nProvinceID) {
                out += (int)this.getMilitaryUpkeep(CFG.game.getCiv(nCivID).getRecruitArmy(i).getProvinceID(), CFG.game.getCiv(nCivID).getRecruitArmy(i).getArmy(), nCivID);
            }
        }
        return out - this.getMilitaryUpkeep(nProvinceID, nArmy, nCivID);
    }
    
    protected final float getMilitaryUpkeep(final int nProvinceID, final int nArmy, final int nCivID) {
        //if sandbox return no upkeep
        if (CFG.SANDBOX_MODE && nCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
            return 0.0F;
        }

        return (float)Math.pow(nArmy * CFG.gameAges.getAge_MilitaryUpkeep(Game_Calendar.CURRENT_AGEID), 1.03f - 0.1275f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() - 0.10479f * CFG.game.getCiv(nCivID).getTechnologyLevel()) * (1.0f + CFG.terrainTypesManager.getMilitaryUpkeep(CFG.game.getProvince(nProvinceID).getTerrainTypeID())) * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MILITARY_UPKEEP * CFG.gameAges.getAge_TreasuryModifier_MilitaryUpkeep(Game_Calendar.CURRENT_AGEID) * (1.0f + CFG.game.getCiv(nCivID).getNumOfProvinces() / (float)CFG.game.getProvincesSize() * 0.425f + CFG.game.getCiv(nCivID).getWarWeariness() + CFG.game.getCiv(nCivID).getModifier_MilitaryUpkeep() - BuildingsManager.getSupply_Bonus(CFG.game.getProvince(nProvinceID).getLevelOfSupply())) * Game_Calendar.GAME_SPEED * (1.0f - this.getMilitaryUpkeep_DefensivePosition(nProvinceID));
    }
    
    protected final float getMilitaryUpkeep_WithoutDefensivePosition(final int nProvinceID, final int nArmy, final int nCivID) {
        return (float)Math.pow(nArmy * CFG.gameAges.getAge_MilitaryUpkeep(Game_Calendar.CURRENT_AGEID), 1.03f - 0.1275f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() - 0.10479f * CFG.game.getCiv(nCivID).getTechnologyLevel()) * (1.0f + CFG.terrainTypesManager.getMilitaryUpkeep(CFG.game.getProvince(nProvinceID).getTerrainTypeID())) * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MILITARY_UPKEEP * CFG.gameAges.getAge_TreasuryModifier_MilitaryUpkeep(Game_Calendar.CURRENT_AGEID) * (1.0f + CFG.game.getCiv(nCivID).getNumOfProvinces() / (float)CFG.game.getProvincesSize() * 0.425f + CFG.game.getCiv(nCivID).getWarWeariness() + CFG.game.getCiv(nCivID).getModifier_MilitaryUpkeep() - BuildingsManager.getSupply_Bonus(CFG.game.getProvince(nProvinceID).getLevelOfSupply())) * Game_Calendar.GAME_SPEED;
    }
    
    protected final float getMilitaryUpkeep_DefensivePosition(final int nProvinceID) {
        return 0.008f * CFG.game.getProvince(nProvinceID).getDefensivePosition();
    }
    
    protected final float getResearchSpendings(final int nCivID, final int iBudget) {
        return iBudget * CFG.game.getCiv(nCivID).getSpendings_Research();
    }

    protected final float getInvestments_Total(final int nCivID, final int iBudget) {
        return this.getResearchSpendings(nCivID, iBudget) + this.getInvestmentsSpendings(nCivID, iBudget);
    }

    protected final float getGoodsSpendings(final int nCivID, final int iBudget) {
        return iBudget * CFG.game.getCiv(nCivID).getSpendings_Goods();
    }
    
    protected final float getInvestmentsSpendings(final int nCivID, final int iBudget) {
        return iBudget * CFG.game.getCiv(nCivID).getSpendings_Investments();
    }
    
    protected final void updateSpendingsOfCiv(final int nCivID, final int iBudget) {
        if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
            if (CFG.game.getCiv(nCivID).getMoney() < -500L) {
                CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
            }
            if (iBudget <= 0) {
                CFG.game.getCiv(nCivID).setSpendings_Goods(0.0f);
                CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
                CFG.game.getCiv(nCivID).setSpendings_Investments(0.0f);
            }
            int tempMilitary = this.getMilitarySpendings(nCivID, iBudget);
            if (tempMilitary + (int)(CFG.game.getCiv(nCivID).getSpendings_Goods() * 100.0f) > 200) {
                CFG.game.getCiv(nCivID).setSpendings_Goods((200 - tempMilitary) / 100.0f);
            }
            tempMilitary += (int)(CFG.game.getCiv(nCivID).getSpendings_Goods() * 100.0f);
            if (tempMilitary + (int)(CFG.game.getCiv(nCivID).getSpendings_Research() * 100.0f) + (int)(CFG.game.getCiv(nCivID).getSpendings_Investments() * 100.0f) > 200) {
                if (tempMilitary > 200) {
                    CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
                    CFG.game.getCiv(nCivID).setSpendings_Investments(0.0f);
                    return;
                }
                int overBudget = (int)(CFG.game.getCiv(nCivID).getSpendings_Research() * 100.0f) + (int)(CFG.game.getCiv(nCivID).getSpendings_Investments() * 100.0f) + tempMilitary - 200;
                final int tempBef = (int)(CFG.game.getCiv(nCivID).getSpendings_Research() * 100.0f);
                CFG.game.getCiv(nCivID).setSpendings_Research(CFG.game.getCiv(nCivID).getSpendings_Research() - overBudget / 2.0f / 100.0f);
                overBudget -= (int)(tempBef - CFG.game.getCiv(nCivID).getSpendings_Research() * 100.0f);
                if (overBudget < CFG.game.getCiv(nCivID).getSpendings_Investments() * 100.0f) {
                    CFG.game.getCiv(nCivID).setSpendings_Investments(CFG.game.getCiv(nCivID).getSpendings_Investments() - overBudget / 100.0f);
                }
                else {
                    overBudget -= (int)(CFG.game.getCiv(nCivID).getSpendings_Investments() * 100.0f);
                    CFG.game.getCiv(nCivID).setSpendings_Investments(0.0f);
                    CFG.game.getCiv(nCivID).setSpendings_Research(CFG.game.getCiv(nCivID).getSpendings_Research() - overBudget / 100.0f);
                }
            }
        }
    }

    protected final void updateSpendingsOfVassals(final int iLordID) {

        for (int i = 0; i < CFG.game.getCiv(iLordID).civGameData.iVassalsSize; i++) {
            if (!CFG.game.getCiv(iLordID).civGameData.lVassals.get(i).autonomyStatus.isEconomicControl()) {

                int nCivID = CFG.game.getCiv(iLordID).civGameData.lVassals.get(i).iCivID;
                final int iBudget = CFG.game.getCiv(nCivID).iBudget;

                //update vassal if no eco control
                if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
                    if (CFG.game.getCiv(iLordID).getMoney() < -500L) {
                        CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
                    }
                    if (iBudget <= 0) {
                        CFG.game.getCiv(nCivID).setSpendings_Goods(0.0f);
                        CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
                        CFG.game.getCiv(nCivID).setSpendings_Investments(0.0f);
                    }
                }
            }
        }

    }

    protected final void updateCities() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.updateCities(i);
        }
    }
    
    protected final void updateCities(final int nCivID) {
        final int tempNumOfCities = (int)Math.ceil(CFG.game.getCiv(nCivID).getNumOfProvinces() * CFG.settingsManager.PERCETANGE_OF_CITIES_ON_MAP / 100.0f);
        int tMaxPopulation = 1;
        final List<Integer> tempProvinces = new ArrayList<Integer>();
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            tempProvinces.add(CFG.game.getCiv(nCivID).getProvinceID(i));
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setDrawCities(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getLevelOfPort() > 0 || CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getLevelOfArmoury() > 0);
            if (!CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).isOccupied() && tMaxPopulation < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation()) {
                tMaxPopulation = CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation();
            }
        }
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCitiesSize(); ++j) {
                if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCity(j).getCityLevel() != CFG.getEditorCityLevel(0)) {
                    CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCity(j).setCityLevel(this.getLevelOfCity(tMaxPopulation, CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation(), j));
                }
            }
        }
        for (int k = 0; k < tempNumOfCities; ++k) {
            int largestProvinceID = 0;
            int largestPopulation = CFG.game.getProvince(tempProvinces.get(largestProvinceID)).getPopulationData().getPopulation();
            for (int l = 1, iSize = tempProvinces.size(); l < iSize; ++l) {
                if (largestPopulation < CFG.game.getProvince(tempProvinces.get(l)).getPopulationData().getPopulation()) {
                    largestProvinceID = l;
                    largestPopulation = CFG.game.getProvince(tempProvinces.get(largestProvinceID)).getPopulationData().getPopulation();
                }
            }
            CFG.game.getProvince(tempProvinces.get(largestProvinceID)).setDrawCities(true);
            tempProvinces.remove(largestProvinceID);
        }
        if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) {
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setDrawCities(true);
        }
        tempProvinces.clear();
    }
    
    protected int getLevelOfCity(final int nMaxPopulation, final int nPopulation, final int nCityID) {
        final float nScore = nPopulation / (float)nMaxPopulation;
        int out = 4;
        if (nScore >= 0.765f) {
            out = 1;
        }
        else if (nScore >= 0.575f) {
            out = 2;
        }
        else if (nScore >= 0.325f) {
            out = 3;
        }
        else {
            out = 4;
        }
        return CFG.getEditorCityLevel(out);
    }
    
    protected final void buildLevelsOfCities() {
        for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(i).getCitiesSize(); ++j) {
                CFG.game.getProvince(i).getCity(j).setCityLevel(CFG.getEditorCityLevel(4));
            }
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.buildLevelsOfCities(i);
        }
    }
    
    protected final void buildLevelsOfCities(final int nCivID) {
        int tMaxPop = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            if (tMaxPop < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation()) {
                tMaxPop = CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation();
            }
        }
        for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCitiesSize(); ++j) {
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getCity(j).setCityLevel(CFG.getCityLevel_Population((float)tMaxPop, CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getPopulationData().getPopulation(), j));
            }
        }
        try {
            CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCity(0).setCityLevel(CFG.getEditorCityLevel(0));
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
    }


}
