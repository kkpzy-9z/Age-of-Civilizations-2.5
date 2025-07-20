package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.*;

class DiplomacyManager
{
    protected static final int COST_OFFER_ALLIANCE = 20;
    protected static final int COST_OFFER_IMPROVERELATIONS = 5;
    protected static final int COST_OFFER_DECREASERELATIONS = 2;
    protected static final int IMPROVERELATIONS_MAX_NUM_OF_TURS = 35;
    protected static final int SUSPEND_DIPLOMATIC_RELATIONS_MAX = 50;
    protected static final int SUSPEND_DIPLOMATIC_RELATIONS_MIN = 15;
    protected static final int COST_OFFER_NONAGGRESSIONPACT = 8;
    protected static final int COST_OFFER_DEFENSIVEPACT = 10;
    protected static final int COST_OFFER_PROCLAIMINDEPENDENCE = 5;
    protected static final int COST_OFFER_FORMUNION = 22;
    protected static final int COST_ALLIANCE_LEAVE = 2;
    protected static final int COST_OFFER_SUPPORTREBELS = 34;
    protected static final int COST_OFFER_TRADEREQUEST = 10;
    protected static final int COST_OFFER_LIBERATEAVASSAL = 2;
    protected static final int COST_OFFER_VASSALIZATION = 16;
    protected static final int COST_OFFER_MILITARYACCESS_ASK = 10;
    protected static final int COST_OFFER_MILITARYACCESS_GIVE = 4;
    protected static final int COST_OFFER_GIFT = 8;
    protected static final int COST_CALL_TO_ARMS = 0;
    protected static final int COST_WAR_PREPARATIONS = 0;
    protected static final int COST_TAKE_LOAN = 6;
    protected static final int COST_ABADON = 0;
    protected static final int COST_ULTIMATUM = 24;
    protected static final int COST_TRANSFER_CONTROL = 4;
    protected static final int COST_INVEST_DEVLOPMENT = 8;
    protected static final int INVEST_NUM_OF_TURNS_DEVLOPMENT = 4;
    protected static final float COST_INVEST_ECONOMY_PER_GOLD_DEVELOPMENT = 1.075f;
    protected static final int COST_INVEST = 12;
    protected static final int INVEST_NUM_OF_TURNS = 4;
    protected static final float COST_INVEST_ECONOMY_PER_GOLD = 3.5f;
    protected static final float COST_INVEST_ECONOMY_PER_GOLD2 = 6.75f;
    protected static final int COLONIZE_NEW_COLONY_BONUS = 92;
    protected static final int COST_FESTIVAL = 8;
    protected static final int BASE_COST_OF_FESTIVAL = 500;
    protected static final int FESTIVAL_NUM_OF_TURNS = 7;
    protected static final int COST_ASSIMILATE = 6;
    protected static final int BASE_COST_OF_ASSIMILATE = 265;
    protected static final int ASSIMILATE_NUM_OF_TURNS_MIN = 10;
    protected static final int ASSIMILATE_NUM_OF_TURNS_MAX = 50;
    protected static final int SUPPORT_REBELS_NUM_OF_TURNS_MAX = 35;
    protected static final float SUPPORT_REBELS_ASSIMILATE_COST_MODIFIER = 1.6275f;
    protected static final float SUPPORT_REBELS_ASSIMILATE_PERC = 0.845f;
    protected static final float SUPPORT_REBELS_ASSIMILATE_PERC_EXTRA = 0.125f;
    protected static final int COST_CIVILIZE = 10;
    protected static final int DIPLOMAT_COST_ALLIANCE = 6;
    protected static final int DIPLOMAT_COST_NONAGGRESSION = 2;
    protected static final int DIPLOMAT_COST_GUARANTEE = 1;
    protected static final int DIPLOMAT_COST_DEFENSIVE_PACT = 3;
    protected static final int DIPLOMAT_COST_FRIENDLY_CIV = 3;
    protected static final int DIPLOMAT_COST_MILITARYACCESS = 1;
    protected static final int DIPLOMAT_COST_VASSAL = 1;
    protected static final int GOLDEN_AGE_EVERY_X_TURNS = 30;
    protected static final int CALL_TO_ARMS_RELATION_DENY = -15;
    protected static final int CALL_TO_ARMS_RELATION_DENY_INSULT = -20;
    protected static final int CALL_TO_ARMS_RELATION_ACCEPT = 10;
    protected static int WAR_PREPARATIONS_REFUSE_OPINION_CHANGE;
    protected static final float GIFT_MAX_PERC_OF_TREASURY = 0.25f;
    protected static final int GIFT_REMOVE_RECEIVED_GIFT_INFO_TURNS = 5;
    protected static final int GIFT_REFUSE_OPINION_CHANGE = -8;
    protected static final int ULTIMATUM_TRUCE_TURNS = 30;
    protected static final int ULTIMATUM_REQUIRED_RELATIONS = -10;
    protected static final int PEACETREATY_DEFAULT = 45;
    protected static final int PEACETREATY_MIN_DURATION = 30;
    protected static final int PEACETREATY_MAX_DURATION = 75;
    protected static final int WAR_REPARATIONS_LENGTH = 12;
    protected static int RELEASED_VASSAL_MIN_OPINION;
    protected static final int OUDATED_RELATIONS = 6;
    protected static final int OUDATED_RELATIONS_MAX = 15;
    protected static final int OUDATED_RELATIONS_MIN = -20;
    protected static final int FRIENDLY_MIN_RELATION = 44;
    protected static final int HATED_WAR = 85;
    protected static final int HATED_MIN_RELATION = -25;
    protected static final int HATED_INSULT = 20;
    protected static final int INSULT_DECREASE_RELATIONS = 30;
    protected static final int LOAN_MAX_NUM_OF_LOANS = 5;
    protected static final int LOAN_MIN_DURATION = 5;
    protected static final int LOAN_MAX_DURATION = 30;
    protected static final float LOAN_MAX_VALUE_OF_INCOME = 0.6f;
    protected static final float PLUNDER_INCOME_MULTIPLY = 1.45f;
    protected static final float PLUNDER_INCOME_HIGH_REV_RISK_MODIFIER = 0.625f;
    
    DiplomacyManager() {
        super();
    }
    
    protected static final float getLikelihoodScore(final int iScore) {
        return (Math.min(Math.max(iScore, -100), 100) + 100) / 200.0f;
    }
    
    protected static final void worldRecations(final int iModifier, final int iAgressorCivID, final int iCivB) {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && i != iAgressorCivID && i != iCivB && !CFG.game.getCivsAtWar(i, iAgressorCivID)) {
                final float tDistance = CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(i).getCapitalProvinceID(), CFG.game.getCiv(iCivB).getCapitalProvinceID());
                final float out = -((tDistance < 0.375f) ? (iModifier / 20.0f * (1.0f - tDistance)) : 0.0f) + iModifier * (-(CFG.game.getCivRelation_OfCivB(i, iCivB) + iModifier / 5) / 100.0f) * Math.max(1.0f - tDistance * 1.35f, 0.01f);
                CFG.game.setCivRelation_OfCivB(i, iAgressorCivID, (CFG.game.getCivRelation_OfCivB(i, iAgressorCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(i, iAgressorCivID) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(i, iAgressorCivID) + out));
                CFG.game.setCivRelation_OfCivB(iAgressorCivID, i, (CFG.game.getCivRelation_OfCivB(iAgressorCivID, i) > -100.0f && CFG.game.getCivRelation_OfCivB(iAgressorCivID, i) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iAgressorCivID, i) + out));
            }
        }
    }
    
    protected static final void sendTransferControl(final int iToCivID, final int iFromCivID, final int iProvinceID) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TransferControl(iFromCivID, iProvinceID));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints());
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.TRANSFER_CONTROL));
        }
    }
    
    protected static final void acceptTransferControl(final int iCivID, final int iFromCivID, final int iValue) {
        if (CFG.game.getProvince(iValue).getCivID() == iFromCivID && CFG.game.getProvince(iValue).isOccupied() && (CFG.game.getCivsAreAllied(iCivID, iFromCivID) || CFG.game.getCiv(iCivID).getPuppetOfCivID() == iFromCivID || CFG.game.getCiv(iFromCivID).getPuppetOfCivID() == iCivID || CFG.game.getProvince(iValue).getTrueOwnerOfProvince() == iCivID)) {
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TransferControl_Accepted(iCivID, iValue, iFromCivID));
            final int oldOwnerArmy = CFG.game.getProvince(iValue).getArmyCivID(iFromCivID);
            final int newOwnerArmy = CFG.game.getProvince(iValue).getArmyCivID(iCivID);
            if (oldOwnerArmy != 0) {
                CFG.game.getProvince(iValue).updateArmy(iFromCivID, 0);
            }
            if (newOwnerArmy != 0) {
                CFG.game.getProvince(iValue).updateArmy(iCivID, 0);
            }
            CFG.game.getProvince(iValue).setCivID(iCivID, false, true);
            if (oldOwnerArmy > 0) {
                CFG.game.getProvince(iValue).updateArmy(iFromCivID, oldOwnerArmy);
            }
            if (newOwnerArmy > 0) {
                CFG.game.getProvince(iValue).updateArmy(iCivID, newOwnerArmy);
            }
        }
    }

    protected static final boolean checkCapitulate(int occupyCivID) {
        //if not at war or no provinces owned, skip
        if (!CFG.game.getCiv(occupyCivID).isAtWar() || CFG.game.getCiv(occupyCivID).getNumOfProvinces() < 1) return false;
        //if player, skip
        if (!CFG.SPECTATOR_MODE && occupyCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) return false;

        float iWeight = 0.0F;

        //if capital province core, and occupied by warring, add weight
        float iCapWeight = 0.0F;
        if (CFG.game.getCiv(occupyCivID).getCoreCapitalProvinceID() > 0 && CFG.game.getProvince(CFG.game.getCiv(occupyCivID).getCoreCapitalProvinceID()).isOccupied()) {
            if (CFG.game.getCivsAtWar(occupyCivID, CFG.game.getProvince(CFG.game.getCiv(occupyCivID).getCoreCapitalProvinceID()).getCivID())) {
                iCapWeight += 20.0F;
            }
        }

        //add weight for each occupied province
        float iOccWeightTotal = 0.0F;
        float iEcoTotal = 0.0F;

        Dictionary<Integer, Integer> occupiers = new Hashtable<>();
        ArrayList<Integer> occProvinces = new ArrayList<Integer>();
        ArrayList<Integer> ownProvinces = new ArrayList<Integer>();
        //get all of civ's provinces, check occupied and add to lists accordingly
        for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == occupyCivID && !CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 1) {
                //also if CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID() != CFG.ideologiesManager.REBELS_ID
                if (CFG.game.getProvince(i).isOccupied() && CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), occupyCivID)) {
                    occProvinces.add(i);
                    //add weight to occupied
                    //add eco weight to total
                    float iOccWeight = (float)CFG.game.getProvince(i).iIncome_Taxation + ((float)CFG.game.getProvince(i).iIncome_Taxation * 2.0F);
                    iEcoTotal += iOccWeight;
                    iOccWeightTotal += iOccWeight;
                } else {
                    ownProvinces.add(i);
                    //add eco weight to total
                    float iOccWeight = (float)CFG.game.getProvince(i).iIncome_Taxation + ((float)CFG.game.getProvince(i).iIncome_Taxation * 2.0F);
                    iEcoTotal += iOccWeight;
                }

                //sort through each army in civ
                for(int j = 0; j < CFG.game.getProvince(i).saveProvinceData.iCivsSize; ++j) {
                    //if no army, army owned by base civ, or not at war, skip
                    if (CFG.game.getProvince(i).saveProvinceData.lArmies.get(j).getArmy() < 1 || (occupyCivID == ((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID()) || !CFG.game.getCivsAtWar(occupyCivID, ((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID())) continue;

                    //check if army's civ in dict, if exists, compile weight, if not create new data
                    if (occupiers.get(((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID()) != null) {
                        occupiers.put(((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID(), occupiers.get(((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID()) + CFG.game.getProvince(i).saveProvinceData.lArmies.get(j).getArmy());
                    } else {
                        occupiers.put(((Province_Army)CFG.game.getProvince(i).saveProvinceData.lArmies.get(j)).getCivID(), CFG.game.getProvince(i).saveProvinceData.lArmies.get(j).getArmy());
                    }
                }
            } else if (CFG.game.getProvince(i).getCivID() == occupyCivID) {
                //if occupied by civ, add
                float iOccWeight = (float)CFG.game.getProvince(i).iIncome_Taxation + ((float)CFG.game.getProvince(i).iIncome_Taxation * 2.0F);
                //add eco weight to total
                iEcoTotal += iOccWeight;
                ownProvinces.add(i);
            }
        }

        //divide occupation weight by amount of provinces
        iOccWeightTotal = iOccWeightTotal / iEcoTotal;
        iOccWeightTotal *= 100.0F;


        //inverse of stability added as weight, lower stab = higher weight
        float iStatsWeight = (75.0F * (1.0F - CFG.game.getCiv(occupyCivID).getStability()));
        iStatsWeight += (75.0F * (CFG.game.getCiv(occupyCivID).getWarWeariness()));
        iStatsWeight += (0.75F * (100.0F - CFG.game.getCiv(occupyCivID).getHappiness()));

        //compile weight
        iWeight += iCapWeight;
        iWeight += iOccWeightTotal;
        iWeight += iStatsWeight;

        //Gdx.app.log("AoC2.5", "Capitulation% " + CFG.game.getCiv(occupyCivID).getCivName() + " " + iOccWeightTotal + " " + iStatsWeight + " " + iWeight);
        //if weight to cap large, and civs are occupying, initiate occupy transfer
        if (iWeight >= 95.0F && occProvinces.size() > 0) {
            //set victor civ
            int victorCivID = -1;
            //push -1 id with -1 armies (so it gets eliminated)
            occupiers.put(-1, -1);

            //sort through dict and find most army civ
            Enumeration<Integer> ky = occupiers.keys();
            while (ky.hasMoreElements()) {
                Integer iCivID = ky.nextElement();
                if (occupiers.get(iCivID) > occupiers.get(victorCivID)) {
                    victorCivID = iCivID;
                }
            }
            //if no occupation, default
            if (victorCivID < 1) {
                victorCivID = CFG.game.getProvince(occProvinces.get(0)).getCivID();
            }

            //occupy civ
            CFG.game.getCiv(occupyCivID).clearMoveUnits();
            CFG.game.getCiv(occupyCivID).clearMoveUnits_Plunder();
            CFG.game.getCiv(occupyCivID).clearRegroupArmy();
            CFG.game.getCiv(occupyCivID).clearRecruitArmy();
            for (int k = 0; k < ownProvinces.size(); ++k) {
                if (CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID() != occupyCivID) continue;
                int nArmyNewOwnerArmy = CFG.game.getProvince((Integer) ownProvinces.get(k)).getArmyCivID(victorCivID);
                //CFG.game.getProvince((Integer)tempProvinces2.get(k)).updateArmy(0);
                CFG.game.getProvince((Integer) ownProvinces.get(k)).updateArmy(victorCivID, 0);
                CFG.game.getProvince((Integer) ownProvinces.get(k)).setCivID(victorCivID, false);
                CFG.game.getProvince((Integer) ownProvinces.get(k)).updateArmy(victorCivID, nArmyNewOwnerArmy);
                for (int j = CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivsSize() - 1; j >= 0; --j) {
                    if (CFG.game.getCiv(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j)).getPuppetOfCivID() == victorCivID || CFG.game.getCiv(victorCivID).getPuppetOfCivID() == CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j) || CFG.game.getCiv(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j)).getAllianceID() == CFG.game.getCiv(victorCivID).getAllianceID() || CFG.game.getMilitaryAccess(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j), victorCivID) > 0)
                        continue;
                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID(j), (Integer) ownProvinces.get(k));
                }
                CFG.game.buildCivilizationsRegions_TextOver(CFG.game.getProvince((Integer) ownProvinces.get(k)).getCivID());
            }
            CFG.game.getCiv(occupyCivID).buildNumOfUnits();
            ownProvinces.clear();
            CFG.game.buildCivilizationsRegions_TextOver(occupyCivID);
            CFG.game.buildCivilizationsRegions_TextOver(victorCivID);

            //invoke event
            if (CFG.DYNAMIC_EVENTS) {
                CFG.dynamicEventManager.eventManagerWar.capInvokeEvents(victorCivID, occupyCivID);
            }

            return true;
        }
        return false;
    }

    protected static final void declineTransferControl(final int iCivID, final int iFromCivID, final int iValue) {
        if (CFG.game.getProvince(iValue).getCivID() == iFromCivID && CFG.game.getProvince(iValue).isOccupied() && (CFG.game.getCivsAreAllied(iCivID, iFromCivID) || CFG.game.getCiv(iCivID).getPuppetOfCivID() == iFromCivID || CFG.game.getCiv(iFromCivID).getPuppetOfCivID() == iCivID)) {
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TransferControl_Refused(iCivID, iValue, iFromCivID));
        }
    }
    
    protected static final float invest_DevelopmentByGold(final int nProvinceID, final int nMoney) {
        return nMoney / (CFG.game.getGameScenarios().getScenario_StartingPopulation() * 1.075f) * (0.375f + 0.625f * (CFG.gameAges.getAge_Economy_GrowthRate(Game_Calendar.CURRENT_AGEID) * 100.0f));
    }
    
    protected static final int invest_MaxDevelopment_Gold(final int nProvinceID, final int nCivID) {
        return (int)Math.max(Math.min(Math.min(CFG.game.getCiv(nCivID).getTechnologyLevel() + 0.01f - CFG.game.getProvince(nProvinceID).getDevelopmentLevel(), Math.max(CFG.game.getProvince(nProvinceID).getDevelopmentLevel(), 0.1f) * 0.725f) * (CFG.game.getGameScenarios().getScenario_StartingPopulation() * 1.075f * (0.375f + 0.625f * (CFG.gameAges.getAge_Economy_GrowthRate(Game_Calendar.CURRENT_AGEID) * 100.0f))), (float)CFG.game.getCiv(nCivID).getMoney()), 0.0f);
    }
    
    protected static final boolean investDevelopment(final int nProvinceID, final int nCivID, int nMoney) {
        if (CFG.game.getProvince(nProvinceID).getCivID() == nCivID && CFG.game.getCiv(nCivID).getMovePoints() >= 8) {
            if (CFG.game.getCiv(nCivID).getMoney() < nMoney) {
                nMoney = (int)CFG.game.getCiv(nCivID).getMoney();
            }
            if (nMoney > 0) {
                final float ecoPoints = invest_DevelopmentByGold(nProvinceID, nMoney);
                if (ecoPoints > 0.0f) {
                    final float ecoPointsPerTurn = Math.max(ecoPoints / 4.0f, 1.0E-5f);
                    if (CFG.game.getCiv(nCivID).addInvest_Development(new CivInvest_Development(nProvinceID, 4, ecoPoints, ecoPointsPerTurn))) {
                        CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - 8);
                        CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - nMoney);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected static final int invest_EconomyByGold(final int nProvinceID, final int nMoney) {
        return (int)(nMoney / 3.5f * (0.875f + 0.125f * Math.min(1.0f, CFG.game.getProvince(nProvinceID).getDevelopmentLevel() * 1.75f)) * (0.375f + 0.625f * CFG.gameAges.getAge_Economy_GrowthRate(Game_Calendar.CURRENT_AGEID) * 10.0f));
    }
    
    protected static final int invest_MaxEconomy(final int nProvinceID, final int nCivID) {
        return (int)Math.min(CFG.game.getProvince(nProvinceID).getEconomy() * 0.375f, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * 0.26854f);
    }
    
    protected static final int invest_MaxEconomy_Gold(final int nProvinceID, final int nCivID) {
        return Math.max((int)Math.min(Math.min(CFG.game.getProvince(nProvinceID).getEconomy() * 0.325f, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * 0.265f) * (0.65f + 0.35f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel()) * 6.75f, (float)CFG.game.getCiv(nCivID).getMoney()), 0);
    }
    
    protected static final boolean invest(final int nProvinceID, final int nCivID, int nMoney) {
        if (CFG.game.getProvince(nProvinceID).getCivID() == nCivID && CFG.game.getCiv(nCivID).getMovePoints() >= 12) {
            if (CFG.game.getCiv(nCivID).getMoney() < nMoney) {
                nMoney = (int)CFG.game.getCiv(nCivID).getMoney();
            }
            if (nMoney > 0) {
                final int ecoPoints = invest_EconomyByGold(nProvinceID, nMoney);
                if (ecoPoints > 0) {
                    final int ecoPointsPerTurn = Math.max(ecoPoints / 4, 1);
                    if (CFG.game.getCiv(nCivID).addInvest(new CivInvest(nProvinceID, 4, ecoPoints, ecoPointsPerTurn))) {
                        CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - 12);
                        CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - nMoney);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected static final boolean canMoveToNaighbooringProvince(final int nProvinceID, final int nCivID) {
        return !Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES || CFG.game.getProvince(nProvinceID).getSeaProvince() || CFG.game.getProvince(nProvinceID).getCivID() > 0 || CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0;
    }
    
    protected static final int getColonizeCost(final int nProvinceID, final int nCivID) {
        return (int)(CFG.game.getGameScenarios().getScenario_StartingPopulation() * (CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_GOLD_PERC + 0.0845f * CFG.game.getProvince(nProvinceID).getGrowthRate_Population() + 0.1325f * ((CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) ? (3.475f * CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nProvinceID)) : 1.0f)) * getColonizeCost_OwnNeighboringProvincesModifier(nProvinceID, nCivID) * getColonizeCost_ContinentAndRegion_Modifier(nProvinceID, nCivID) * (1.0f - CFG.game.getCiv(nCivID).civGameData.fModifier_ColonizationCost) * ((CFG.game.getCiv(nCivID).getTechnologyLevel() < Game_Calendar.COLONIZATION_TECH_LEVEL) ? (2.675f + (Game_Calendar.COLONIZATION_TECH_LEVEL - CFG.game.getCiv(nCivID).getTechnologyLevel()) * 8.25f) : 1.0f));
    }
    
    protected static final int getColonizeCost_AI(final int nCivID) {
        return (int)(CFG.game.getGameScenarios().getScenario_StartingPopulation() * (CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_GOLD_PERC + 0.021125f + 0.0795f) * (1.0f - CFG.game.getCiv(nCivID).civGameData.fModifier_ColonizationCost) * ((CFG.game.getCiv(nCivID).getTechnologyLevel() < Game_Calendar.COLONIZATION_TECH_LEVEL) ? (2.675f + (Game_Calendar.COLONIZATION_TECH_LEVEL - CFG.game.getCiv(nCivID).getTechnologyLevel()) * 8.25f) : 1.0f));
    }
    
    protected static final float getColonizeCost_ContinentAndRegion_Modifier(final int nProvinceID, final int nCivID) {
        if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getContinent() == CFG.game.getProvince(nProvinceID).getContinent()) {
                if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getRegion() == CFG.game.getProvince(nProvinceID).getRegion()) {
                    return 0.815f;
                }
                return 0.865f;
            }
            else if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getRegion() == CFG.game.getProvince(nProvinceID).getRegion()) {
                return 0.915f;
            }
        }
        return 1.0f;
    }
    
    protected static final float getColonizeCost_OwnNeighboringProvincesModifier(final int nProvinceID, final int nCivID) {
        int ownsNeighboringProvinces = 0;
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
                ++ownsNeighboringProvinces;
            }
        }
        return 1.0f - 0.4f * ownsNeighboringProvinces / Math.max(CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(), 1);
    }
    
    protected static final int getColonizeCost_Movement(final int nProvinceID, final int nCivID) {
        return (int)Math.min(40.0f, CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_MOVEMENT_POINTS + CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_MOVEMENT_POINTS * ((CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) ? (1.6275f * CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nProvinceID)) : 2.0f));
    }
    
    protected static final boolean colonizeWastelandProvince(final int nProvinceID, final int nCivID) {
        if (CFG.game.getProvince(nProvinceID).getWasteland() < 0 && CFG.game.getProvince(nProvinceID).getCivID() != 0) {
            return false;
        }
        if (CFG.game.getCiv(nCivID).getMovePoints() < getColonizeCost_Movement(nProvinceID, nCivID)) {
            return false;
        }
        if (CFG.game.getCiv(nCivID).getDiplomacyPoints() < CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_DIPLOMACY_POINTS) {
            return false;
        }
        if (CFG.game.getCiv(nCivID).getMoney() < getColonizeCost(nProvinceID, nCivID)) {
            return false;
        }
        if (!CFG.gameAction.canColonizieWasteland_BorderOrArmy(nProvinceID, nCivID)) {
            return false;
        }
        final boolean wasWasteland = CFG.game.getProvince(nProvinceID).getWasteland() >= 0;
        CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - getColonizeCost_Movement(nProvinceID, nCivID));
        CFG.game.getCiv(nCivID).setDiplomacyPoints(CFG.game.getCiv(nCivID).getDiplomacyPoints() - CFG.gameAges.getAge(Game_Calendar.CURRENT_AGEID).COLONIZE_COST_DIPLOMACY_POINTS);
        CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - getColonizeCost(nProvinceID, nCivID));
        CFG.game.getProvince(nProvinceID).setWasteland(-1);
        CFG.game.getProvince(nProvinceID).resetArmies(0);
        CFG.game.getProvince(nProvinceID).setCivID(nCivID, false, true);
        final int ranArmy = 5 + CFG.oR.nextInt(15);
        CFG.game.getProvince(nProvinceID).updateArmy(nCivID, ranArmy);
        CFG.game.getCiv(nCivID).setNumOfUnits(CFG.game.getCiv(nCivID).getNumOfUnits() + ranArmy);
        CFG.game.getProvince(nProvinceID).getCore().addNewCore(nCivID, Game_Calendar.TURN_ID);
        CFG.game.getProvince(nProvinceID).setHappiness(Math.max(CFG.game.getProvince(nProvinceID).getHappiness(), (62 + CFG.oR.nextInt(31)) / 100.0f));
        CFG.game.getProvince(nProvinceID).setDevelopmentLevel(Math.max(CFG.game.getProvince(nProvinceID).getDevelopmentLevel(), CFG.game.getCiv(nCivID).getTechnologyLevel() * (0.125f + CFG.oR.nextInt(100) / 1000.0f)));
        CFG.game.getProvince(nProvinceID).saveProvinceData.iNewColonyBonus = 92;
        if (wasWasteland) {
            CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(nCivID, Math.max(299 + CFG.oR.nextInt(460), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(nCivID)));
            CFG.game.getProvince(nProvinceID).setEconomy(Math.max(CFG.game.getProvince(nProvinceID).getEconomy(), 42 + CFG.oR.nextInt(76)));
            CFG.game.buildWastelandLevels();
        }
        for (int i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
            if (CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) == 0) {
                final float randPop = 0.375f + CFG.oR.nextInt(35) / 100.0f;
                CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(nCivID, CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(nCivID) + (int)(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * randPop));
                CFG.game.getProvince(nProvinceID).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) - (int)(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i) * randPop));
                break;
            }
        }
        CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.add(new Civilization_Colonies(nProvinceID));
        CFG.oAI.buildProvinceData(nProvinceID);
        if (CFG.game.getActiveProvinceID() == nProvinceID) {
            CFG.game.setActiveProvinceID(-1);
            CFG.game.setActiveProvinceID(nProvinceID);
        }
        try {
            CFG.historyManager.addHistoryLog(new HistoryLog_NewColony(nCivID, nProvinceID));
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex) {}
        return true;
    }
    
    protected static final int festivalCost(final int nProvinceID) {
        return 500 + (int)((CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(nProvinceID) + CFG.game_NextTurnUpdate.getProvinceIncome_Production(nProvinceID)) * (0.6425f + 0.1625f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getTechnologyLevel() + 0.2f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getFestivalsSize()));
    }
    
    protected static final float festivalHappinessPerTurn(final int nProvinceID) {
        return 0.0145f + 0.006f * (1.0f - CFG.game.getProvince(nProvinceID).getHappiness());
    }
    
    protected static final float festivalHappinessPerTurn_NeighboringProvinces() {
        return 0.0045f;
    }
    
    protected static final boolean addFestival(final int nCivID, final int nProvinceID) {
        if (nCivID == CFG.game.getProvince(nProvinceID).getCivID() && CFG.game.getCiv(nCivID).getMovePoints() >= 8 && CFG.game.getCiv(nCivID).getMoney() >= festivalCost(nProvinceID) && CFG.game.getCiv(nCivID).addFestival(new CivFestival(nProvinceID, 7))) {
            CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - 8);
            CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - festivalCost(nProvinceID));
            return true;
        }
        return false;
    }
    
    protected static final int assimilateCost(final int nProvinceID, final int numOfTurns) {
        return (int)((265 + (int)((CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(nProvinceID) * 0.775f + CFG.game_NextTurnUpdate.getProvinceIncome_Production(nProvinceID) * 0.237f) * (0.665f + 0.412f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel() + 0.0825f * CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getAssimilatesSize()) * (1.0f + CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getCapitalProvinceID(), nProvinceID)) * (1.625f - CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(nProvinceID).getCivID()) / (float)CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation()))) / 10.0f * numOfTurns);
    }
    protected static final int assimilateCost(final ArrayList<Integer> lProvincesID, final int numOfTurns) {
        int totalInt = 0;
        for (int i = 0; i < lProvincesID.size(); i++) {
            totalInt += (int)((265 + (int)((CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(lProvincesID.get(i)) * 0.775f + CFG.game_NextTurnUpdate.getProvinceIncome_Production(lProvincesID.get(i)) * 0.237f) * (0.665f + 0.412f * CFG.game.getProvince(lProvincesID.get(i)).getDevelopmentLevel() + 0.0825f * (CFG.game.getCiv(CFG.game.getProvince(lProvincesID.get(i)).getCivID()).getAssimilatesSize() + i)) * (1.0f + CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(CFG.game.getProvince(lProvincesID.get(i)).getCivID()).getCapitalProvinceID(), lProvincesID.get(i))) * (1.625f - CFG.game.getProvince(lProvincesID.get(i)).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(lProvincesID.get(i)).getCivID()) / (float)CFG.game.getProvince(lProvincesID.get(i)).getPopulationData().getPopulation()))) / 10.0f * numOfTurns);
        }
        return totalInt;
    }
    protected static final int assimilateCost(final List<Integer> lProvincesID, final int numOfTurns) {
        int totalInt = 0;
        for (int i = 0; i < lProvincesID.size(); i++) {
            totalInt += (int)((265 + (int)((CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(lProvincesID.get(i)) * 0.775f + CFG.game_NextTurnUpdate.getProvinceIncome_Production(lProvincesID.get(i)) * 0.237f) * (0.665f + 0.412f * CFG.game.getProvince(lProvincesID.get(i)).getDevelopmentLevel() + 0.0825f * (CFG.game.getCiv(CFG.game.getProvince(lProvincesID.get(i)).getCivID()).getAssimilatesSize() + i)) * (1.0f + CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(CFG.game.getProvince(lProvincesID.get(i)).getCivID()).getCapitalProvinceID(), lProvincesID.get(i))) * (1.625f - CFG.game.getProvince(lProvincesID.get(i)).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(lProvincesID.get(i)).getCivID()) / (float)CFG.game.getProvince(lProvincesID.get(i)).getPopulationData().getPopulation()))) / 10.0f * numOfTurns);
        }
        return totalInt;
    }

    protected static final boolean addAssimilate(final int nCivID, final int nProvinceID, final int numOfTurns) {
        if (nCivID == CFG.game.getProvince(nProvinceID).getCivID() && !CFG.game.getProvince(nProvinceID).isOccupied() && CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 6 && CFG.game.getCiv(nCivID).getMoney() >= assimilateCost(nProvinceID, numOfTurns) && CFG.game.getCiv(nCivID).addAssimilate(new CivFestival(nProvinceID, numOfTurns))) {
            if (!CFG.SANDBOX_MODE) CFG.game.getCiv(nCivID).setDiplomacyPoints(CFG.game.getCiv(nCivID).getDiplomacyPoints() - 6);
            CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - Math.abs(assimilateCost(nProvinceID, numOfTurns)));
            return true;
        }
        return false;
    }
    
    protected static final SupportRebels_Data supportRebels(final int iOnCivID) {
        final SupportRebels_Data outCivs = new SupportRebels_Data();
        for (int i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivsSize(); ++j) {
                if (CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j)).getNumOfProvinces() <= 0) {
                    boolean tAdd = true;
                    for (int k = 0; k < outCivs.lMovements.size(); ++k) {
                        if (outCivs.lMovements.get(k) == CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j)) {
                            tAdd = false;
                            outCivs.lPopulation.set(k, outCivs.lPopulation.get(k) + CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j)));
                            outCivs.lUnrest.set(k, outCivs.lUnrest.get(k) + (int)(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getRevolutionaryRisk() * 100.0f));
                            outCivs.lProvinces.set(k, outCivs.lProvinces.get(k) + 1);
                            break;
                        }
                    }
                    if (tAdd) {
                        outCivs.lMovements.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j));
                        outCivs.lPopulation.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j)));
                        outCivs.lUnrest.add((int)(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getRevolutionaryRisk() * 100.0f));
                        outCivs.lProvinces.add(1);
                    }
                }
            }
        }
        return outCivs;
    }
    
    protected static final List<Integer> supportRebels_Provinces(final int iOnCivID, final int iRebelsID) {
        final List<Integer> outProvinces = new ArrayList<Integer>();
        for (int i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivsSize(); ++j) {
                if (CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getCivID(j) == iRebelsID) {
                    outProvinces.add(CFG.game.getCiv(iOnCivID).getProvinceID(i));
                    break;
                }
            }
        }
        return outProvinces;
    }
    
    protected static final float getSUPPORT_REBELS_ASSIMILATE_PERC(final int iNumOfSupporters) {
        if (iNumOfSupporters <= 1) {
            return 0.845f;
        }
        return 0.845f + 0.125f * Math.min(1.0f, (float)(iNumOfSupporters / 4));
    }
    
    protected static final int supportRebels_MaxGold(final List<Integer> nProvinces) {
        int out = 1;
        for (int i = 0, iSize = nProvinces.size(); i < iSize; ++i) {
            out += (int)(assimilateCost(nProvinces.get(i), 35) * 1.6275f);
        }
        return out * 2;
    }
    
    protected static final boolean supportRebels(final int byCivID, final int iOnCivID, final int supportCivID, int nMoney) {
        if (CFG.game.getCiv(byCivID).getMoney() < nMoney) {
            nMoney = (int)CFG.game.getCiv(byCivID).getMoney();
        }
        if (nMoney <= 0) {
            return false;
        }
        if (CFG.game.getCiv(byCivID).getDiplomacyPoints() < 34) {
            return false;
        }
        CFG.game.getCiv(byCivID).setDiplomacyPoints(CFG.game.getCiv(byCivID).getDiplomacyPoints() - 34);
        CFG.game.getCiv(byCivID).setMoney(CFG.game.getCiv(byCivID).getMoney() - nMoney);
        final List<Integer> supportedProvinces = new ArrayList<Integer>();
        final List<Integer> supportedPopulation = new ArrayList<Integer>();
        final List<Integer> supportCostPerTurn = new ArrayList<Integer>();
        int supportedPopulationTotal = 0;
        for (int i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
            if (CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getCore().getHaveACore(supportCivID)) {
                supportedProvinces.add(CFG.game.getCiv(iOnCivID).getProvinceID(i));
                supportedPopulation.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(supportCivID) + 1);
                supportCostPerTurn.add((int)(assimilateCost(CFG.game.getCiv(iOnCivID).getProvinceID(i), 1) * 1.6275f));
                supportedPopulationTotal += CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(supportCivID) + 1;
            }
        }
        try {
            CFG.game.getCiv(iOnCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_RebelsSupported(supportCivID, supportedProvinces.get(0)));
        }
        catch (final IndexOutOfBoundsException ex) {}
        while (supportedProvinces.size() > 0 && nMoney > 0) {
            final int nRandPop = CFG.oR.nextInt(supportedPopulationTotal);
            final int currPop = 0;
            int bestSuppProvID = 0;
            for (int j = 0; j < supportedProvinces.size(); ++j) {
                if (nRandPop >= currPop && nRandPop <= currPop + supportedPopulation.get(j)) {
                    bestSuppProvID = j;
                    break;
                }
            }
            if (Math.floor(nMoney / supportCostPerTurn.get(bestSuppProvID)) <= 0.0) {
                break;
            }
            int numOfTunrs = (int)Math.floor(nMoney / supportCostPerTurn.get(bestSuppProvID));
            if (numOfTunrs <= 1) {
                break;
            }
            numOfTunrs = 1 + CFG.oR.nextInt(numOfTunrs);
            if (numOfTunrs > 35) {
                numOfTunrs = 35;
            }
            final Province_SupportRebels_Help outHelp = CFG.game.getProvince(supportedProvinces.get(bestSuppProvID)).addSupportRebels(new Province_SupportRebels(byCivID, supportCivID, numOfTunrs));
            nMoney -= supportCostPerTurn.get(bestSuppProvID) * outHelp.iTurns;
            if (!outHelp.max) {
                continue;
            }
            supportedPopulationTotal -= supportedPopulation.get(bestSuppProvID);
            supportedProvinces.remove(bestSuppProvID);
            supportedPopulation.remove(bestSuppProvID);
            supportCostPerTurn.remove(bestSuppProvID);
        }
        supportedProvinces.clear();
        supportedPopulation.clear();
        supportedPopulationTotal = 0;
        for (int i = 0; i < CFG.game.getCiv(iOnCivID).getNumOfProvinces(); ++i) {
            if (CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(supportCivID) > 0) {
                supportedProvinces.add(CFG.game.getCiv(iOnCivID).getProvinceID(i));
                supportedPopulation.add(CFG.game.getProvince(CFG.game.getCiv(iOnCivID).getProvinceID(i)).getPopulationData().getPopulationOfCivID(supportCivID));
                supportedPopulationTotal += supportedPopulation.get(supportedPopulation.size() - 1);
            }
        }
        final float efficiency = nMoney / (supportedPopulationTotal * 0.11625f * 5.0f);
        for (int k = 0; k < supportedProvinces.size(); ++k) {
            final float tempPercOfPopulation = supportedPopulation.get(k) / (float)CFG.game.getProvince(supportedProvinces.get(k)).getPopulationData().getPopulation();
            CFG.game.getProvince(supportedProvinces.get(k)).setRevolutionaryRisk(CFG.gameAges.getAge_RevolutionaryRiskModifier(Game_Calendar.CURRENT_AGEID) * CFG.game.getProvince(supportedProvinces.get(k)).getRevolutionaryRisk() + 0.2f * efficiency * tempPercOfPopulation * (1.01f - CFG.game.getProvince(supportedProvinces.get(k)).getHappiness()));
        }
        return true;
    }
    
    protected static final boolean civilizeCiv(final int nCivID) {
        if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0 && CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 10 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CIVILIZE_TECH_LEVEL <= CFG.game.getCiv(nCivID).getTechnologyLevel()) {
            CFG.game.getCiv(nCivID).setIdeologyID(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED);
            CFG.game.getCiv(nCivID).setCivTag(CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getExtraTag());
            CFG.unionFlagsToGenerate_Manager.addFlagToLoad(nCivID);
            CFG.game.getCiv(nCivID).setDiplomacyPoints(CFG.game.getCiv(nCivID).getDiplomacyPoints() - 10);
            if (CFG.game.getPlayerID_ByCivID(nCivID) >= 0) {
                CFG.game.getPlayer(CFG.game.getPlayerID_ByCivID(nCivID)).loadPlayersFlag();
            }
            CFG.viewsManager.disableAllViews();
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).setFromCivID(0);
            }
            CFG.palletManager.loadCivilizationStandardColor(nCivID);
            if (CFG.game.getCiv(nCivID).getNumOfNeighboringNeutralProvinces() > 0) {
                final List<Integer> possibleProvinces = new ArrayList<Integer>();
                for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
                    for (int k = 0; k < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getNeighboringProvincesSize(); ++k) {
                        possibleProvinces.add(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getNeighboringProvinces(k));
                    }
                }
                if (possibleProvinces.size() > 0) {
                    CFG.game.getProvince(possibleProvinces.get(CFG.oR.nextInt(possibleProvinces.size()))).setCivID(nCivID, false);
                }
            }
            return true;
        }
        return false;
    }
    
    protected static void sendTechPointsMessages() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                if (CFG.game.getCiv(i).civGameData.skills.getPointsLeft(i) > 0) {
                    CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TechPoints(i));
                }
                CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_OpenBudget(i));
            }
        }
    }
    
    protected static void sendUncivilizedMessages() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getControlledByPlayer() && CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
                CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Uncivilized(i));
            }
        }
    }
    
    protected static void sendLowHappiness() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() >= 0) {
                if (CFG.game.getCiv(i).getHappiness() < 50) {
                    CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_LowHappiness(i, 0));
                }
                if (!CFG.game.getCiv(i).lProvincesWithLowStability.isEmpty()) {
                    for (int j = CFG.game.getCiv(i).lProvincesWithLowStability.size() - 1; j >= 0; --j) {
                        if (CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getTrueOwnerOfProvince() == i &&
                            CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getProvinceStability() < 80.0f)
                        {
                            CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_LowStability(i, 0));
                            break;
                        } else {
                            CFG.game.getCiv(i).lProvincesWithLowStability.remove(j);
                        }
                    }
                }
            }
        }
    }
    
    protected static final int getCostOfCurrentDiplomaticActions(final int nCivID) {
        int out = 0;
        if (CFG.game.getCiv(nCivID).getAllianceID() > 0 && CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize() > 1) {
            out += 6;
        }
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && i != nCivID) {
                if (CFG.game.getCivNonAggressionPact(nCivID, i) > 0) {
                    out += 2;
                }
                if (CFG.game.getGuarantee(nCivID, i) > 0) {
                    ++out;
                }
                if (CFG.game.getDefensivePact(nCivID, i) > 0) {
                    out += 3;
                }
                if (CFG.game.getMilitaryAccess(nCivID, i) > 0) {
                    ++out;
                }
                out += CFG.game.getCiv(nCivID).civGameData.iVassalsSize;
                out += getCostOfFriendlyCivs(nCivID);
            }
        }
        return out;
    }
    
    protected static final int getCostOfFriendlyCivs(final int nCivID) {
        return 3 * CFG.game.getCiv(nCivID).getFriendlyCivsSize();
    }
    
    protected static final int getCostOfCurrentDiplomaticActionsUpdate(final int nCivID) {
        int out = 0;
        if (CFG.game.getCiv(nCivID).getAllianceID() > 0 && CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize() > 1) {
            out += 6;
        }
        return out;
    }
    
    protected static final void updateGoldenAge() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                CFG.game.getCiv(i).setGoldenAge_Prosperity(CFG.game.getCiv(i).getGoldenAge_Prosperity() + (int)((CFG.game.getCiv(i).getSpendings_Goods() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)) * 100.0f) + (int)((CFG.game.getCiv(i).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS) * 100.0f));
                CFG.game.getCiv(i).setGoldenAge_Science(CFG.game.getCiv(i).getGoldenAge_Science() + (int)(CFG.game.getCiv(i).getSpendings_Research() * 100.0f));
                CFG.game.getCiv(i).setGoldenAge_Military(CFG.game.getCiv(i).getGoldenAge_Military() + CFG.game_NextTurnUpdate.getMilitarySpendings(i, CFG.game.getCiv(i).iBudget));
            }
        }
        if (Game_Calendar.TURN_ID % 30 == 10) {
            if (getNumOfCivsInTheGame() > 7) {
                int nAverageScore = 0;
                int nCivs = 0;
                for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getNumOfProvinces() > 0) {
                        nAverageScore += CFG.game.getCiv(j).getGoldenAge_Science();
                        ++nCivs;
                    }
                }
                final float fAverage = (float)Math.ceil(nAverageScore / (float)Math.max(nCivs, 1));
                final List<Integer> tCivs = new ArrayList<Integer>();
                int toRand = 0;
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    if (CFG.game.getCiv(k).getNumOfProvinces() > 0 && CFG.game.getCiv(k).getGoldenAge_Science() >= fAverage) {
                        toRand += CFG.game.getCiv(k).getGoldenAge_Science();
                        tCivs.add(k);
                    }
                }
                if (toRand > 0) {
                    toRand = CFG.oR.nextInt(toRand);
                    int k = 0;
                    int counted = 0;
                    while (k < tCivs.size()) {
                        if (toRand >= counted && toRand < counted + CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Science()) {
                            goldenAge_Science(tCivs.get(k));
                            CFG.game.getCiv(tCivs.get(k)).setGoldenAge_Science(0);
                            break;
                        }
                        counted += CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Science();
                        ++k;
                    }
                }
                tCivs.clear();
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    CFG.game.getCiv(k).setGoldenAge_Science((int)(CFG.game.getCiv(k).getGoldenAge_Science() * 0.3f));
                }
            }
            else {
                for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                    CFG.game.getCiv(i).setGoldenAge_Science((int)(CFG.game.getCiv(i).getGoldenAge_Science() * 0.15f));
                }
            }
        }
        else if (Game_Calendar.TURN_ID % 30 == 15) {
            if (getNumOfCivsInTheGame() > 7) {
                int nAverageScore = 0;
                int nCivs = 0;
                for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getNumOfProvinces() > 0) {
                        nAverageScore += CFG.game.getCiv(j).getGoldenAge_Military();
                        ++nCivs;
                    }
                }
                final float fAverage = (float)Math.ceil(nAverageScore / (float)Math.max(nCivs, 1));
                final List<Integer> tCivs = new ArrayList<Integer>();
                int toRand = 0;
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    if (CFG.game.getCiv(k).getNumOfProvinces() > 0 && CFG.game.getCiv(k).getGoldenAge_Military() >= fAverage) {
                        toRand += CFG.game.getCiv(k).getGoldenAge_Military();
                        tCivs.add(k);
                    }
                }
                if (toRand > 0) {
                    toRand = CFG.oR.nextInt(toRand);
                    int k = 0;
                    int counted = 0;
                    while (k < tCivs.size()) {
                        if (toRand >= counted && toRand < counted + CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Military()) {
                            goldenAge_Military(tCivs.get(k));
                            CFG.game.getCiv(tCivs.get(k)).setGoldenAge_Military(0);
                            break;
                        }
                        counted += CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Military();
                        ++k;
                    }
                }
                tCivs.clear();
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    CFG.game.getCiv(k).setGoldenAge_Military((int)(CFG.game.getCiv(k).getGoldenAge_Military() * 0.3f));
                }
            }
            else {
                for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                    CFG.game.getCiv(i).setGoldenAge_Military((int)(CFG.game.getCiv(i).getGoldenAge_Military() * 0.15f));
                }
            }
        }
        else if (Game_Calendar.TURN_ID % 30 == 20) {
            if (getNumOfCivsInTheGame() > 7) {
                int nAverageScore = 0;
                int nCivs = 0;
                for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getNumOfProvinces() > 0) {
                        nAverageScore += CFG.game.getCiv(j).getGoldenAge_Prosperity();
                        ++nCivs;
                    }
                }
                final float fAverage = (float)Math.ceil(nAverageScore / (float)Math.max(nCivs, 1));
                final List<Integer> tCivs = new ArrayList<Integer>();
                int toRand = 0;
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    if (CFG.game.getCiv(k).getNumOfProvinces() > 0 && CFG.game.getCiv(k).getGoldenAge_Prosperity() >= fAverage) {
                        toRand += CFG.game.getCiv(k).getGoldenAge_Prosperity();
                        tCivs.add(k);
                    }
                }
                if (toRand > 0) {
                    toRand = CFG.oR.nextInt(toRand);
                    int k = 0;
                    int counted = 0;
                    while (k < tCivs.size()) {
                        if (toRand >= counted && toRand < counted + CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Prosperity()) {
                            goldenAge_Prosperity(tCivs.get(k));
                            CFG.game.getCiv(tCivs.get(k)).setGoldenAge_Prosperity(0);
                            break;
                        }
                        counted += CFG.game.getCiv(tCivs.get(k)).getGoldenAge_Prosperity();
                        ++k;
                    }
                }
                tCivs.clear();
                for (int k = 1; k < CFG.game.getCivsSize(); ++k) {
                    CFG.game.getCiv(k).setGoldenAge_Prosperity((int)(CFG.game.getCiv(k).getGoldenAge_Prosperity() * 0.15f));
                }
            }
            else {
                for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                    CFG.game.getCiv(i).setGoldenAge_Prosperity((int)(CFG.game.getCiv(i).getGoldenAge_Prosperity() * 0.1f));
                }
            }
        }
    }
    
    protected static int getNumOfCivsInTheGame() {
        int nCivs = 0;
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                ++nCivs;
            }
        }
        return nCivs;
    }
    
    protected static final void goldenAge_Prosperity(final int nCivID) {
        Gdx.app.log("AoC", "PROSPERITY: " + CFG.game.getCiv(nCivID).getCivName());
        final CivBonus_GameData nGodlenAge = new CivBonus_GameData();
        nGodlenAge.iTurnsLeft = 8;
        nGodlenAge.BONUS_TYPE = CivBonus_Type.GOLDEN_AGE_PROSPERITY;
        nGodlenAge.fModifier_PopGrowth = 0.1f + CFG.oR.nextInt(10) / 100.0f;
        nGodlenAge.fModifier_EconomyGrowth = 0.08f + CFG.oR.nextInt(5) / 100.0f;
        nGodlenAge.fModifier_IncomeTaxation = 0.06f + CFG.oR.nextInt(6) / 100.0f;
        if (CFG.game.getCiv(nCivID).addNewBonus(nGodlenAge)) {
            CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_GoldenAge(nCivID, nGodlenAge.iTurnsLeft));
        }
    }
    
    protected static final void goldenAge_Military(final int nCivID) {
        Gdx.app.log("AoC", "MILITARY: " + CFG.game.getCiv(nCivID).getCivName());
        final CivBonus_GameData nGodlenAge = new CivBonus_GameData();
        nGodlenAge.iTurnsLeft = 10;
        nGodlenAge.BONUS_TYPE = CivBonus_Type.GOLDEN_AGE_MILITARY;
        nGodlenAge.fModifier_AttackBonus = 0.08f + CFG.oR.nextInt(6) / 100.0f;
        nGodlenAge.fModifier_MilitaryUpkeep = -0.14f - CFG.oR.nextInt(6) / 100.0f;
        nGodlenAge.fModifier_MovementPoints = 0.06f + CFG.oR.nextInt(10) / 100.0f;
        if (CFG.game.getCiv(nCivID).addNewBonus(nGodlenAge)) {
            CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_GoldenAgeMilitary(nCivID, nGodlenAge.iTurnsLeft));
        }
    }
    
    protected static final void goldenAge_Science(final int nCivID) {
        Gdx.app.log("AoC", "SCIENCE: " + CFG.game.getCiv(nCivID).getCivName());
        final CivBonus_GameData nGodlenAge = new CivBonus_GameData();
        nGodlenAge.iTurnsLeft = 8;
        nGodlenAge.BONUS_TYPE = CivBonus_Type.GOLDEN_AGE_SCIENCE;
        nGodlenAge.fModifier_Research = 0.15f + CFG.oR.nextInt(10) / 100.0f;
        nGodlenAge.fModifier_DefenseBonus = 0.1f + CFG.oR.nextInt(6) / 100.0f;
        nGodlenAge.fModifier_IncomeProduction = 0.06f + CFG.oR.nextInt(8) / 100.0f;
        if (CFG.game.getCiv(nCivID).addNewBonus(nGodlenAge)) {
            CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_GoldenAgeScience(nCivID, nGodlenAge.iTurnsLeft));
        }
    }
    
    protected static final void sendAllianceProposal(final int iToCivID, final int iFromCivID) {
        if (CFG.game.getCiv(iToCivID).getAllianceID() > 0 && CFG.game.getAlliance(CFG.game.getCiv(iToCivID).getAllianceID()).getCivilizationsSize() > 0) {
            CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(iToCivID).getAllianceID()).getCivilization(0)).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message(iFromCivID, 0));
        }
        else {
            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message(iFromCivID, 0));
        }
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 20);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.JOIN_ALLIANCE));
        }
    }
    
    protected static final void declineAllianceProposal(final int iCivID, final int iFromCivID) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Alliance_Denied(iCivID));
    }
    
    protected static final void acceptAllianceProposal(final int iCivID, final int iFromCivID) {
        if (CFG.game.getCiv(iCivID).getAllianceID() == 0 && CFG.game.getCiv(iFromCivID).getAllianceID() == 0) {
            CFG.game.addAlliance(CFG.getRandomAllianceName(0));
            final int tempAllianceID = CFG.game.getAlliancesSize() - 1;
            if (CFG.game.getCiv(iCivID).getControlledByPlayer()) {
                CFG.game.getAlliance(tempAllianceID).addCivilization(iCivID);
                CFG.game.getAlliance(tempAllianceID).addCivilization(iFromCivID);
            }
            else if (CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
                CFG.game.getAlliance(tempAllianceID).addCivilization(iFromCivID);
                CFG.game.getAlliance(tempAllianceID).addCivilization(iCivID);
            }
            else {
                CFG.game.getAlliance(tempAllianceID).addCivilization(iCivID);
                CFG.game.getAlliance(tempAllianceID).addCivilization(iFromCivID);
            }
            CFG.game.getCiv(iCivID).setAllianceID(tempAllianceID);
            CFG.game.getCiv(iFromCivID).setAllianceID(tempAllianceID);
            CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(iCivID, tempAllianceID));
            CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(iFromCivID, tempAllianceID));
        }
        else if (CFG.game.getCiv(iFromCivID).getAllianceID() > 0 && CFG.game.getCiv(iCivID).getAllianceID() == 0) {
            CFG.game.getAlliance(CFG.game.getCiv(iFromCivID).getAllianceID()).addCivilization(iCivID);
            CFG.game.getCiv(iCivID).setAllianceID(CFG.game.getCiv(iFromCivID).getAllianceID());
            CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(iCivID, CFG.game.getCiv(iFromCivID).getAllianceID()));
        }
        else if (CFG.game.getCiv(iCivID).getAllianceID() > 0 && CFG.game.getCiv(iFromCivID).getAllianceID() == 0) {
            CFG.game.getAlliance(CFG.game.getCiv(iCivID).getAllianceID()).addCivilization(iFromCivID);
            CFG.game.getCiv(iFromCivID).setAllianceID(CFG.game.getCiv(iCivID).getAllianceID());
            CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(iFromCivID, CFG.game.getCiv(iCivID).getAllianceID()));
        }
        else {
            CFG.game.getAlliance(CFG.game.getCiv(iCivID).getAllianceID()).removeCivilization(iCivID);
            CFG.game.getAlliance(CFG.game.getCiv(iFromCivID).getAllianceID()).addCivilization(iCivID);
            CFG.game.getCiv(iCivID).setAllianceID(CFG.game.getCiv(iFromCivID).getAllianceID());
            CFG.game.getCiv(iCivID).setAllianceID(CFG.game.getCiv(iFromCivID).getAllianceID());
        }
        if (CFG.game.getCiv(iCivID).getControlledByPlayer()) {
            CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iCivID));
            CFG.game.getPlayer(CFG.game.getPlayerID_ByCivID(iCivID)).buildMetProvincesAndCivs();
        }
        if (CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iFromCivID));
            CFG.game.getPlayer(CFG.game.getPlayerID_ByCivID(iFromCivID)).buildMetProvincesAndCivs();
        }
        //increase relation change//
        CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) + 15.0F);
        CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) + 15.0F);

        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Alliance_Accepted(iCivID));
    }
    
    protected static int getAllianceProposal_Positive(final int nCivA, final int nCivB) {
        int out = 0;
        out += getAllianceProposal_Positive_Opinion(nCivA, nCivB);
        out += getAllianceProposal_Positive_Goverment(nCivA, nCivB);
        if (getAllianceProposale_CivStrength(nCivA, nCivB) > 0) {
            out += getAllianceProposale_CivStrength(nCivA, nCivB);
        }
        out += getAllianceProposal_Positive_HRE(nCivA, nCivB);
        return out;
    }
    
    protected static int getAllianceProposal_Negative(final int nCivA, final int nCivB) {
        int out = 0;
        out += getAllianceProposal_Negative_Opinion(nCivA, nCivB);
        out += getAllianceProposal_Negative_Goverment(nCivA, nCivB);
        //out += getAllianceProposal_Negative_HRE(nCivA, nCivB);
        //out += getAllianceProposal_Negative_PowerfulAllies(nCivA, nCivB);
        //out += getAllianceProposal_Negative_PowerfulAllies(nCivB, nCivA);
        //out += getAllianceProposal_Negative_CivIsAtWar(nCivA);
        //out += getAllianceProposal_Negative_EmbassyClosed(nCivA, nCivB);
        //out += getAllianceProposal_Negative_HaveACore(nCivA, nCivB);
        //out += getAllianceProposal_Negative_IsAVassal(nCivA, nCivB);
        //out += getAllianceProposal_Negative_Distance(nCivA, nCivB);
        if (getAllianceProposale_CivStrength(nCivA, nCivB) < 0) {
            out += getAllianceProposale_CivStrength(nCivA, nCivB);
        }
        return out;
    }
    
    protected static int getAllianceProposal_Positive_HRE(final int nCivA, final int nCivB) {
        if (CFG.game.getCiv(nCivA).getIsPartOfHolyRomanEmpire() && CFG.game.getCiv(nCivB).getIsPartOfHolyRomanEmpire()) {
            return 4;
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Positive_Opinion(final int nCivA, final int nCivB) {
        if (CFG.game.getCivRelation_OfCivB(nCivB, nCivA) - CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_OPINION > 0.0f) {
            return (int)((CFG.game.getCivRelation_OfCivB(nCivB, nCivA) - CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_OPINION) / 1.94f);
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Positive_Goverment(final int nCivA, final int nCivB) {
        if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivA).getIdeologyID()).GOV_GROUP_ID != CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivB).getIdeologyID()).GOV_GROUP_ID) {
            return 0;
        }
        if (CFG.game.getCiv(nCivA).getIdeologyID() == CFG.game.getCiv(nCivB).getIdeologyID()) {
            return 6;
        }
        return 2;
    }
    
    protected static int getAllianceProposale_CivStrength(final int nCivA, final int nCivB) {
        return (int)(-CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_STRENTGH / 2.0f + CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_STRENTGH / 2.0f * Math.min(CFG.game.getCiv(nCivA).getRankScore() / (float)CFG.game.getCiv(nCivB).getRankScore(), 2.0f));
    }
    
    protected static int getAllianceProposal_Negative_Opinion(final int nCivA, final int nCivB) {
        if (CFG.game.getCivRelation_OfCivB(nCivB, nCivA) - CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_OPINION < 0.0f) {
            return (int)((CFG.game.getCivRelation_OfCivB(nCivB, nCivA) - CFG.game.getCiv(nCivB).civGameData.civPersonality.RESPONSE_ALLIANCE_OPINION) / 2.0f - ((CFG.game.getCivRelation_OfCivB(nCivB, nCivA) < 0.0f) ? 5 : 0));
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Negative_HRE(final int nCivA, final int nCivB) {
        if ((CFG.game.getCiv(nCivA).getIsPartOfHolyRomanEmpire() && !CFG.game.getCiv(nCivB).getIsPartOfHolyRomanEmpire()) || (!CFG.game.getCiv(nCivA).getIsPartOfHolyRomanEmpire() && CFG.game.getCiv(nCivB).getIsPartOfHolyRomanEmpire())) {
            return -6;
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Negative_Goverment(final int nCivA, final int nCivB) {
        if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivA).getIdeologyID()).GOV_GROUP_ID == CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivB).getIdeologyID()).GOV_GROUP_ID) {
            return 0;
        }
        if (CFG.game.getCiv(nCivA).getIdeologyID() != CFG.game.getCiv(nCivB).getIdeologyID()) {
            return -15;
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Negative_PowerfulAllies(final int nCivA, final int nCivB) {
        int out = 0;
        try {
            if (CFG.game.getCiv(nCivA).getAllianceID() > 0) {
                for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(nCivA).getAllianceID()).getCivilizationsSize(); ++i) {
                    if (nCivA != CFG.game.getAlliance(CFG.game.getCiv(nCivA).getAllianceID()).getCivilization(i)) {
                        out -= (int)Math.min(12.0f, 10.0f * (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(nCivA).getAllianceID()).getCivilization(i)).getRankScore() / (float)CFG.game.getCiv(nCivA).getRankScore()));
                        out += (int)(getAllianceProposal_Negative_Opinion(nCivB, CFG.game.getAlliance(CFG.game.getCiv(nCivA).getAllianceID()).getCivilization(i)) * 0.715f);
                    }
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
        }
        for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
            if (i != nCivA && CFG.game.getCiv(i).getPuppetOfCivID() == nCivA && CFG.game.getCiv(nCivA).getNumOfProvinces() > 0) {
                --out;
            }
        }
        return out;
    }
    
    protected static int getAllianceProposal_Negative_CivIsAtWar(final int nCivA) {
        if (CFG.game.getCiv(nCivA).isAtWar()) {
            return -250;
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Negative_EmbassyClosed(final int nCivA, final int nCivB) {
        if (CFG.game.getCiv(nCivA).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivB) || CFG.game.getCiv(nCivB).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivA)) {
            return -1000;
        }
        return 0;
    }
    
    protected static int getAllianceProposal_Negative_HaveACore(final int nCivA, final int nCivB) {
        int nNumOfCores = 0;
        for (int i = 0; i < CFG.game.getCiv(nCivA).getNumOfProvinces(); ++i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivA).getProvinceID(i)).getCore().getHaveACore(nCivB)) {
                ++nNumOfCores;
            }
        }
        return (nNumOfCores > 0) ? (-Math.min(15 + 5 * (nNumOfCores - 1), 30)) : 0;
    }
    
    protected static int getAllianceProposal_Negative_IsAVassal(final int nCivA, final int nCivB) {
        return (CFG.game.getCiv(nCivA).getPuppetOfCivID() != nCivA && CFG.game.getCiv(nCivA).getPuppetOfCivID() != nCivB) ? -250 : 0;
    }
    
    protected static int getAllianceProposal_Negative_Distance(final int nCivA, final int nCivB) {
        float minDistance = 1.0f;
        for (int i = 0; i < CFG.game.getCiv(nCivA).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(nCivB).getNumOfProvinces(); ++j) {
                minDistance = Math.min(minDistance, CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(nCivA).getProvinceID(i), CFG.game.getCiv(nCivB).getProvinceID(j)));
            }
        }
        return (int)(-CFG.gameAges.getAge_DistanceDiplomacy(Game_Calendar.CURRENT_AGEID) * minDistance);
    }
    
    protected static final void joinAWar(final int iCivID, final int iFromCivID, final int iValue) {
        final int tWarID = CFG.game.getWarID(iFromCivID, iValue);
        CFG.game.joinWar(iCivID, iValue, tWarID);
        if (CFG.game.getCivsAtWar(iCivID, iValue)) {
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_AllyJoinedAWar(iCivID, iValue, iFromCivID));
            CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) + 5.0f);
            CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) + 5.0f);
        }
    }
    
    protected static final List<Integer> callToArmsListOfCivs(final int byCivID, final int onCivID) {
        final List<Integer> alliesToCall = new ArrayList<Integer>();
        final int tWarID = CFG.game.getWarID(byCivID, onCivID);
        if (CFG.game.getCiv(byCivID).getAllianceID() > 0) {
            for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(byCivID).getAllianceID()).getCivilizationsSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(byCivID).getAllianceID()).getCivilization(i)).getNumOfProvinces() > 0 && CFG.game.getAlliance(CFG.game.getCiv(byCivID).getAllianceID()).getCivilization(i) != byCivID && !CFG.game.getCivsAtWar(CFG.game.getAlliance(CFG.game.getCiv(byCivID).getAllianceID()).getCivilization(i), onCivID)) {
                    alliesToCall.add(CFG.game.getAlliance(CFG.game.getCiv(byCivID).getAllianceID()).getCivilization(i));
                }
            }
        }
        for (int i = 0; i < CFG.game.getCiv(byCivID).civGameData.iVassalsSize; ++i) {
            if (!CFG.game.getWar(tWarID).getIsInDefenders(CFG.game.getCiv(byCivID).civGameData.lVassals.get(i).iCivID) && !CFG.game.getWar(tWarID).getIsAggressor(CFG.game.getCiv(byCivID).civGameData.lVassals.get(i).iCivID) && CFG.game.getCiv(CFG.game.getCiv(byCivID).civGameData.lVassals.get(i).iCivID).getNumOfProvinces() > 0) {
                boolean wasAdded = false;
                for (int j = 0; j < alliesToCall.size(); ++j) {
                    if (alliesToCall.get(j) == CFG.game.getCiv(byCivID).civGameData.lVassals.get(i).iCivID) {
                        wasAdded = true;
                        break;
                    }
                }
                if (!wasAdded) {
                    alliesToCall.add(CFG.game.getCiv(byCivID).civGameData.lVassals.get(i).iCivID);
                }
            }
        }
        if (CFG.game.getCiv(byCivID).getCivID() != CFG.game.getCiv(byCivID).getPuppetOfCivID() && !CFG.game.getWar(tWarID).getIsInDefenders(CFG.game.getCiv(byCivID).getPuppetOfCivID()) && !CFG.game.getWar(tWarID).getIsAggressor(CFG.game.getCiv(byCivID).getPuppetOfCivID()) && CFG.game.getCiv(CFG.game.getCiv(byCivID).getPuppetOfCivID()).getNumOfProvinces() > 0) {
            boolean wasAdded2 = false;
            for (Integer integer : alliesToCall) {
                if (integer == CFG.game.getCiv(byCivID).getPuppetOfCivID()) {
                    wasAdded2 = true;
                    break;
                }
            }
            if (!wasAdded2) {
                alliesToCall.add(CFG.game.getCiv(byCivID).getPuppetOfCivID());
            }
        }
        return alliesToCall;
    }
    
    protected static final void sendCallToArms(final int iToCivID, final int iFromCivID, final int warAgainstCivID) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_CallToArms(iFromCivID, warAgainstCivID));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints());
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.WAR_DECLARED_ON_ALLY));
        }
    }
    
    protected static final void acceptCallToArms(final int iCivID, final int iFromCivID, final int iValue) {
        final int tWarID = CFG.game.getWarID(iFromCivID, iValue);
        CFG.game.joinWar(iCivID, iValue, tWarID);

        //call dynamic event for war joining
        if (CFG.DYNAMIC_EVENTS) {
            CFG.dynamicEventManager.eventManagerWar.ctaInvokeEvents(iCivID, iFromCivID);
        }

        if (CFG.game.getCivsAtWar(iCivID, iValue)) {
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_CallToArms_Join(iCivID, iValue, iFromCivID));
            CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) + 10.0f);
            CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) + 10.0f);
        }
    }
    
    protected static final void declineCallToArms(final int iCivID, final int iFromCivID, final int iValue) {
        if (!CFG.game.getCivsAtWar(iCivID, iValue)) {
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_CallToArms_Deny(iCivID, iValue, iFromCivID));
            CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) - 15.0f <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) - 15.0f));
            CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) - 15.0f <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) - 15.0f));
        }
    }
    
    protected static final void callToArms_Denied_SendInsult(final int iCivID, final int iFromCivID, final int iValue) {
        decreaseRelation(iCivID, iFromCivID, 15);
    }
    
    protected static final void sendPrepareForWar(final int iToCivID, final int iFromCivID, final int warAgainstCivID, final int numOfTurns, final int iLeaderCivID) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PrepareForWar(iFromCivID, warAgainstCivID, Game_Calendar.TURN_ID + numOfTurns, iLeaderCivID));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints());
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.PREPARE_FOR_WAR));
        }
    }
    
    protected static final void acceptPrepareForWar(final int iLeaderCivID, final int iCivID, final int iFromCivID, final int warAgainstCivID, final int numOfTurns) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PrepareForWar_Accepted(iCivID, warAgainstCivID));
        CFG.game.getCiv(iFromCivID).civGameData.civPlans.addNewWarPreparations(iLeaderCivID, iFromCivID, warAgainstCivID, numOfTurns);
        CFG.game.getCiv(iCivID).civGameData.civPlans.addNewWarPreparations(iLeaderCivID, iCivID, warAgainstCivID, numOfTurns);
    }
    
    protected static final void declinePrepareForWar(final int iLeaderCivID, final int iCivID, final int iFromCivID, final int warAgainstCivID, final int numOfTurns) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PrepareForWar_Refused(iCivID, warAgainstCivID));
        CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) + DiplomacyManager.WAR_PREPARATIONS_REFUSE_OPINION_CHANGE <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) + DiplomacyManager.WAR_PREPARATIONS_REFUSE_OPINION_CHANGE));
        CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) + DiplomacyManager.WAR_PREPARATIONS_REFUSE_OPINION_CHANGE <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) + DiplomacyManager.WAR_PREPARATIONS_REFUSE_OPINION_CHANGE));
    }
    
    protected static final void sendUnionProposal(final int iToCivID, final int iFromCivID) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Union(iFromCivID, 0));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 22);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.UNION));
        }
    }
    
    protected static final void acceptUnionProposal(final int iCivID, final int iFromCivID) {
        if (iCivID != iFromCivID && CFG.game.getCiv(iCivID).getNumOfProvinces() > 0 && CFG.game.getCiv(iFromCivID).getNumOfProvinces() > 0) {
            final Save_Civ_GameData civGameData = CFG.game.getCiv(iCivID).civGameData;
            ++civGameData.numOfUnions;
            final Save_Civ_GameData civGameData2 = CFG.game.getCiv(iFromCivID).civGameData;
            ++civGameData2.numOfUnions;
            CFG.createUnion(iCivID, iFromCivID);
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Union_Accepted(iCivID, 0));
            CFG.game.getCiv(iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Union_Accepted(iFromCivID, 0));
        }
    }
    
    protected static final void declineUnionProposal(final int iCivID, final int iFromCivID) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Union_Refused(iCivID, 0));
    }
    
    protected static final void sendNonAggressionProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_NonAggressionPact(iFromCivID, iValue));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 8);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.NONAGGRESSIONPACT));
        }
    }
    
    protected static final void acceptNonAggressionPact(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.setCivNonAggressionPact(iCivID, iFromCivID, iValue);
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_NonAggressionPact_Accepted(iCivID));
        CFG.historyManager.addHistoryLog(new HistoryLog_SignedNonAggressionPact(iFromCivID, iCivID));
    }
    
    protected static final void declineNonAggressionPact(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_NonAggressionPact_Denied(iCivID));
    }
    
    protected static final void sendOfferVasalizationProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_OfferVasalization(iFromCivID, iValue));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 16);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.OFFERVASALIZATION));
        }
    }
    
    protected static final void acceptOfferVasalization(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iCivID).setPuppetOfCivID(iFromCivID);
        if (CFG.game.getCiv(iFromCivID).getControlledByPlayer() && CFG.FOG_OF_WAR > 0) {
            final int tPlayerID = CFG.game.getPlayerID_ByCivID(iFromCivID);
            if (tPlayerID >= 0) {
                for (int i = 0; i < CFG.game.getCiv(iCivID).getNumOfProvinces(); ++i) {
                    CFG.game.getProvince(CFG.game.getCiv(iCivID).getProvinceID(i)).updateFogOfWar(tPlayerID);
                }
            }
        }
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Vassalization_Accepted(iCivID));
        CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(iFromCivID, iCivID));
    }
    
    protected static final void declineOfferVasalization(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Vassalization_Rejected(iCivID));
    }

    protected static final void sendMilitaryAccess_AskProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        if (CFG.game.getCiv(iFromCivID).getDiplomacyPoints() >= 10) {
            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_MilitaryAccess_Ask(iFromCivID, iValue));
            CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 10);
            if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
                CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.MILITARY_ACCESS_ASK));
            }
        }
    }
    
    protected static final void acceptMilitaryAccess_Ask(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.setMilitaryAccess(iFromCivID, iCivID, iValue);
        if (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) > 0.0f) {
            CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) - Math.max(CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) / 9.325f, 1.127f));
        }
        if (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) > 0.0f) {
            CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) - Math.max(CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) / 9.325f, 1.127f));
        }
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_MilitaryAccess_Ask_Accepted(iCivID));
        CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iCivID, iFromCivID));
    }
    
    protected static final void declineMilitaryAccess_Ask(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_MilitaryAccess_Ask_Denied(iCivID));
    }
    
    protected static final void sendMilitaryAccess_GiveProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_MilitaryAccess_Give(iFromCivID, iValue));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 4);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.MILITARY_ACCESS_GIVE));
        }
    }
    
    protected static final void acceptMilitaryAccess_Give(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.setMilitaryAccess(iCivID, iFromCivID, iValue);
        CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iFromCivID, iCivID));
    }
    
    protected static final void declineMilitaryAccess_Give(final int iCivID, final int iFromCivID, final int iValue) {
    }
    
    protected static final void sendGuaranteeIndependence_AskProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Independence_Ask(iFromCivID, iValue));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 10);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.GUARANTEE_ASK));
        }
    }
    
    protected static final void acceptGuaranteeIndependence_Ask(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.setGuarantee(iFromCivID, iCivID, iValue);
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Independence_Ask_Accepted(iCivID));
        try {
            CFG.historyManager.addHistoryLog(new HistoryLog_Guarantee(iCivID, iFromCivID));
        }
        catch (final NullPointerException | IndexOutOfBoundsException ex) {}
    }
    
    protected static final void declineGuaranteeIndependence_Ask(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Independence_Ask_Denied(iCivID));
    }
    
    protected static final void sendDefensivePactProposal(final int iToCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_DefensivePact(iFromCivID, iValue));
        CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 10);
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.DEFENSIVEPACT));
        }
    }
    
    protected static final void acceptDefensivePact(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.setDefensivePact(iCivID, iFromCivID, iValue);
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_DefensivePact_Accepted(iCivID));
        CFG.historyManager.addHistoryLog(new HistoryLog_SignedDefensivePact(iFromCivID, iCivID));
    }
    
    protected static final void declineDefensivePact(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_DefensivePact_Denied(iCivID));
    }
    
    protected static final void sendGift(final int iToCivID, final int iFromCivID, int iValue) {
        if (CFG.game.getCiv(iFromCivID).getMoney() * 0.25f < iValue) {
            iValue = (int)Math.max(0.0f, CFG.game.getCiv(iFromCivID).getMoney() * 0.25f);
        }
        if (iValue > 0) {
            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Gift(iFromCivID, iValue));
            CFG.game.getCiv(iFromCivID).setMoney(CFG.game.getCiv(iFromCivID).getMoney() - iValue);
            CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 8);
            if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
                CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.GIFT));
            }
        }
    }
    
    protected static final void acceptGift(final int iCivID, final int iFromCivID, final int iValue) {
        if (iValue >= 0) {
            CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() + iValue);
            CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Gift_Accepted(iCivID, iValue));
            CFG.game.getCiv(iCivID).civGameData.addGift_Received(iFromCivID);
        }
    }
    
    protected static final void declineGift(final int iCivID, final int iFromCivID, final int iValue) {
        CFG.game.getCiv(iFromCivID).setMoney(CFG.game.getCiv(iFromCivID).getMoney() + iValue);
        CFG.game.setCivRelation_OfCivB(iCivID, iFromCivID, (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) - 8.0f <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iCivID, iFromCivID) - 8.0f));
        CFG.game.setCivRelation_OfCivB(iFromCivID, iCivID, (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) - 8.0f <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iFromCivID, iCivID) - 8.0f));
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Gift_Refused(iCivID, iValue));
    }
    
    protected static final boolean sendUltimatum(final int iToCivID, final int iFromCivID, final Ultimatum_GameData nUltimatum, final int nUnits) {
        if (CFG.game.getCivRelation_OfCivB(iToCivID, iFromCivID) > -10.0f) {
            return false;
        }
        if (CFG.game.getCiv(iToCivID).getPuppetOfCivID() == iToCivID || CFG.game.getCiv(iToCivID).getPuppetOfCivID() == iFromCivID) {
            if (CFG.game.getCiv(iFromCivID).getDiplomacyPoints() < 24) {
                return false;
            }
            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Ultimatum(iFromCivID, nUltimatum, nUnits));
            CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 24);
        }
        if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
            CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.ULTIMATUM));
        }
        return true;
    }
    
    protected static final void acceptUltimatum(final int iToCivID, final int iFromCivID, final Ultimatum_GameData ultimatum) {
        if (CFG.game.getCiv(iFromCivID).getPuppetOfCivID() == iFromCivID || CFG.game.getCiv(iFromCivID).getPuppetOfCivID() == iToCivID) {
            CFG.game.getCiv(iFromCivID).setVassalLiberityDesire(CFG.game.getCiv(iFromCivID).getVassalLiberityDesire() * 1.25f + 18.0f + CFG.oR.nextInt(36));
            if (ultimatum.demandAnexation) {
                final List<Integer> tempProvinces = new ArrayList<Integer>();
                for (int i = 0; i < CFG.game.getCiv(iFromCivID).getNumOfProvinces(); ++i) {
                    tempProvinces.add(CFG.game.getCiv(iFromCivID).getProvinceID(i));
                }
                for (Integer tempProvince : tempProvinces) {
                    if (CFG.game.getProvince(tempProvince).getCivID() == iFromCivID && CFG.game.getProvince(tempProvince).getTrueOwnerOfProvince() == iFromCivID) {
                        final int nArmyNewOwnerArmy = CFG.game.getProvince(tempProvince).getArmyCivID(iToCivID);
                        CFG.game.getProvince(tempProvince).updateArmy(0);
                        CFG.game.getProvince(tempProvince).updateArmy(iToCivID, 0);
                        CFG.game.getProvince(tempProvince).setTrueOwnerOfProvince(iToCivID);
                        CFG.game.getProvince(tempProvince).setCivID(iToCivID, false);
                        CFG.game.getProvince(tempProvince).updateArmy(iToCivID, nArmyNewOwnerArmy);
                        for (int j = CFG.game.getProvince(tempProvince).getCivsSize() - 1; j >= 0; --j) {
                            if (CFG.game.getCiv(CFG.game.getProvince(tempProvince).getCivID(j)).getPuppetOfCivID() != iToCivID && CFG.game.getCiv(iToCivID).getPuppetOfCivID() != CFG.game.getProvince(tempProvince).getCivID(j) && (CFG.game.getCiv(CFG.game.getProvince(tempProvince).getCivID(j)).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(tempProvince).getCivID(j)).getAllianceID() != CFG.game.getCiv(iToCivID).getAllianceID()) && CFG.game.getMilitaryAccess(CFG.game.getProvince(tempProvince).getCivID(j), iToCivID) <= 0) {
                                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(tempProvince).getCivID(j), tempProvince);
                            }
                        }
                    }
                }
                if (CFG.game.getCiv(iFromCivID).getCapitalProvinceID() >= 0) {
                    CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).setIsCapital(false);
                    for (int i = 0; i < CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCitiesSize(); ++i) {
                        if (CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCity(i).getCityLevel() == CFG.getEditorCityLevel(0)) {
                            CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
                        }
                    }
                }
                CFG.game.getCiv(iFromCivID).buildNumOfUnits();
                tempProvinces.clear();
                CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
                CFG.game.buildCivilizationsRegions_TextOver(iToCivID);
                CFG.game.getCiv(iFromCivID).setPuppetOfCivID(iFromCivID);
                CFG.historyManager.addHistoryLog(new HistoryLog_Annexation(iFromCivID, iToCivID));
            }
            if (ultimatum.demandVasalization) {
                CFG.game.getCiv(iFromCivID).setPuppetOfCivID(iToCivID);
                if (CFG.game.getCiv(iToCivID).getControlledByPlayer() && CFG.FOG_OF_WAR > 0) {
                    final int tPlayerID = CFG.game.getPlayerID_ByCivID(iToCivID);
                    if (tPlayerID >= 0) {
                        for (int i = 0; i < CFG.game.getCiv(iFromCivID).getNumOfProvinces(); ++i) {
                            CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getProvinceID(i)).updateFogOfWar(tPlayerID);
                        }
                    }
                }
                CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(iToCivID, iFromCivID));
            }
            if (ultimatum.demandMilitaryAccess) {
                CFG.game.setMilitaryAccess(iToCivID, iFromCivID, Math.max(CFG.game.getMilitaryAccess(iToCivID, iFromCivID), 40));
                CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iFromCivID, iToCivID));
            }
            if (ultimatum.demandLiberation.size() > 0) {
                for (int k = 0; k < ultimatum.demandLiberation.size(); ++k) {
                    liberateAVassal(iFromCivID, ultimatum.demandLiberation.get(k));
                    CFG.game.setCivTruce(iFromCivID, ultimatum.demandLiberation.get(k), 22);
                }
            }
            if (ultimatum.demandProvinces.size() > 0) {
                for (int k = 0; k < ultimatum.demandProvinces.size(); ++k) {
                    if (CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getCivID() == iFromCivID && CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getTrueOwnerOfProvince() == iFromCivID) {
                        final List<Integer> tempCivs = new ArrayList<Integer>();
                        final List<Integer> tempArmies = new ArrayList<Integer>();
                        for (int j = 0; j < CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getCivsSize(); ++j) {
                            tempCivs.add(CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getCivID(j));
                            tempArmies.add(CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getArmy(j));
                        }
                        final int nArmyNewOwnerArmy2 = CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getArmyCivID(iToCivID);
                        final int nOwnerArmy = CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getArmy(0);
                        final int nOwnerCivID = CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getCivID();
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).updateArmy(0);
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).updateArmy(iToCivID, 0);
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).setTrueOwnerOfProvince(iToCivID);
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).setCivID(iToCivID, false);
                        if (!CFG.game.getProvince(ultimatum.demandProvinces.get(k)).getIsCapital()) {
                            CFG.game.getProvince(ultimatum.demandProvinces.get(k)).removeCapitalCityIcon();
                        }
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).updateArmy(iToCivID, nArmyNewOwnerArmy2);
                        CFG.game.getProvince(ultimatum.demandProvinces.get(k)).updateArmy(nOwnerCivID, nOwnerArmy);
                        for (int l = 0; l < tempCivs.size(); ++l) {
                            if (CFG.game.getCiv(tempCivs.get(l)).getPuppetOfCivID() != iToCivID && CFG.game.getCiv(iToCivID).getPuppetOfCivID() != tempCivs.get(l) && (CFG.game.getCiv(tempCivs.get(l)).getAllianceID() <= 0 || CFG.game.getCiv(tempCivs.get(l)).getAllianceID() != CFG.game.getCiv(iToCivID).getAllianceID()) && CFG.game.getMilitaryAccess(tempCivs.get(l), iToCivID) <= 0) {
                                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(tempCivs.get(l), ultimatum.demandProvinces.get(k), tempArmies.get(l));
                            }
                        }
                    }
                }
                CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
                CFG.game.buildCivilizationsRegions_TextOver(iToCivID);
            }
            CFG.game.setCivTruce(iToCivID, iFromCivID, 30);
            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_UltimatumAccepted(iFromCivID));
        }
    }
    
    protected static final void refuseUltimatum(final int iToCivID, final int iFromCivID, final Ultimatum_GameData ultimatum) {
        CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_UltimatumRefused(iFromCivID));
    }
    
    protected static final void refuseUltimatum_AcceptWar(final int iFromCivID, final int iToCivID) {
        CFG.game.declareWar(iFromCivID, iToCivID, false);
    }
    
    protected static final void vassalDeclareIndependence_War(final int iFromCivID, final int iToCivID) {
        CFG.game.declareWar(iFromCivID, iToCivID, true);
    }
    
    protected static final void vassalDeclareIndependence_Fine(final int iFromCivID, final int iToCivID) {
        CFG.game.acceptPeaceOffer(iFromCivID, iToCivID, 30);
    }

    protected static final void sendPeaceTreaty(final boolean toDefenders, final int iFromCivID, final PeaceTreaty_GameData peaceTreaty_GameData) {
        try {
            CFG.peaceTreatyData.preparePeaceTreatyToSend(iFromCivID);
            CFG.game.lPeaceTreaties.add(new PeaceTreaty_GameData_MessageData(peaceTreaty_GameData));
            final String peaceTreatyTag = CFG.game.lPeaceTreaties.get(CFG.game.lPeaceTreaties.size() - 1).PEACE_TREATY_TAG;
            for (int i = 0; i < peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                if (!peaceTreaty_GameData.lCivsDemands_Defenders.get(i).peaceTreatyAccepted) {
                    CFG.game.getCiv(peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PeaceTreaty(iFromCivID, peaceTreatyTag));
                }
            }
            for (int i = 0; i < peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                if (!peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).peaceTreatyAccepted) {
                    CFG.game.getCiv(peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PeaceTreaty(iFromCivID, peaceTreatyTag));
                }
            }
        }
        catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
    }

    //original function args
    protected static final void acceptPeaceTreaty(final int iCivID, final String nTag) {
        DiplomacyManager.acceptPeaceTreaty(iCivID, nTag, false);
    }

    //added forcetreaty variable
    protected static final void acceptPeaceTreaty(final int iCivID, final String nTag, final boolean force) {
        final int peaceID = CFG.game.getPeaceTreaty_GameDataID(nTag);
        boolean everyoneAccepted = true;

        //if not force do safechecks
        if (!force) {
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                if (iCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) {
                    CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).peaceTreatyAccepted = true;
                }
                if (!CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).peaceTreatyAccepted) {
                    //accept for puppets
                    if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getPuppetOfCivID() == iCivID) {
                        CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).peaceTreatyAccepted = true;
                    } else {
                        Gdx.app.log("AoC", "DIPLOMANAGER => Treaty not accepted by " + CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getCivName());
                        everyoneAccepted = false;
                    }
                }
            }
            for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                if (iCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID) {
                    CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).peaceTreatyAccepted = true;
                }
                if (!CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).peaceTreatyAccepted) {
                    //accept for puppets
                    if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).getPuppetOfCivID() == iCivID) {
                        CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).peaceTreatyAccepted = true;
                    } else {
                        Gdx.app.log("AoC", "DIPLOMANAGER => Treaty not accepted by " + CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).getCivName());
                        everyoneAccepted = false;
                    }
                }
            }
        }

        if (everyoneAccepted) {
            Gdx.app.log("AoC", "DIPLOMANAGER => Treaty accepted by everyone");
            try {
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.size(); ++j) {
                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getCivID()).removePlunder_ProvinceID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j));
                        final int nArmy0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getArmy(0);
                        final int nCiv0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getCivID();
                        final int nArmyNewOwner = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getArmyCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                        final int nCivNewOwner = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID;
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).updateArmy(0);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).updateArmy(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, 0);
                        if (CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getCivID() == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) {
                            CFG.timelapseManager.addChange(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, false);
                        }
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).setTrueOwnerOfProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).setCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, false, true);
                        if (!CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).getIsCapital()) {
                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).removeCapitalCityIcon();
                        }
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).updateArmy(nCiv0, nArmy0);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lDemands.get(j)).updateArmy(nCivNewOwner, nArmyNewOwner);
                    }
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWarReparationsFromCivsID.size(); ++j) {
                        CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).addWarReparationsGets(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWarReparationsFromCivsID.get(j));
                        CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWarReparationsFromCivsID.get(j)).addWarReparationsPay(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                    }
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.size(); ++j) {
                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getCivID()).removePlunder_ProvinceID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j));
                        final int nArmy0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getArmy(0);
                        final int nCiv0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getCivID();
                        final int nArmyNewOwner = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getArmyCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                        final int nCivNewOwner = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID;
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).updateArmy(0);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).updateArmy(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, 0);
                        if (CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getCivID() == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID) {
                            CFG.timelapseManager.addChange(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, false);
                        }
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).setTrueOwnerOfProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).setCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, false, true);
                        if (!CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).getIsCapital()) {
                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).removeCapitalCityIcon();
                        }
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).updateArmy(nCiv0, nArmy0);
                        CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lDemands.get(j)).updateArmy(nCivNewOwner, nArmyNewOwner);
                    }
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWarReparationsFromCivsID.size(); ++j) {
                        CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).addWarReparationsGets(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWarReparationsFromCivsID.get(j));
                        CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWarReparationsFromCivsID.get(j)).addWarReparationsPay(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                    }
                }
                try {
                    for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                        for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.size(); ++j) {
                            CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j)).setPuppetOfCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                            CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, Math.max(CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID), 22.0f));
                            CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j), Math.max(CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j)), 22.0f));
                            CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lWillVassalizeCivsID.get(j)));
                        }
                    }
                }
                catch (final IndexOutOfBoundsException ex) {
                    CFG.exceptionStack(ex);
                }
                try {
                    for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                        for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.size(); ++j) {
                            CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j)).setPuppetOfCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                            CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, Math.max(CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID), 22.0f));
                            CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j), Math.max(CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j)), 22.0f));
                            CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lWillVassalizeCivsID.get(j)));
                        }
                    }
                }
                catch (final IndexOutOfBoundsException ex) {
                    CFG.exceptionStack(ex);
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.size(); ++j) {
                        for (int k = 0; k < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++k) {
                            if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iFromCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).iCivID) {
                                for (int o = 0; o < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.size(); ++o) {
                                    if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).iCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) {
                                        boolean zeroProvinces = CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID).getNumOfProvinces() == 0;
                                        for (int u = 0; u < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.size(); ++u) {
                                            final int tempArmy0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getArmy(0);
                                            final int tempCiv0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID();
                                            final int nArmyNewOwner2 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getArmyCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                                            final int nCivNewOwner2 = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID;
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(nCivNewOwner2, 0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).setTrueOwnerOfProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).setCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, false, true);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(tempCiv0, tempArmy0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(nCivNewOwner2, nArmyNewOwner2);
                                            if (zeroProvinces) {
                                                CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID).setPuppetOfCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                                                CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID));
                                                if (CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) < DiplomacyManager.RELEASED_VASSAL_MIN_OPINION) {
                                                    CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, (float)DiplomacyManager.RELEASED_VASSAL_MIN_OPINION);
                                                }
                                                if (CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) < DiplomacyManager.RELEASED_VASSAL_MIN_OPINION) {
                                                    CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, (float)DiplomacyManager.RELEASED_VASSAL_MIN_OPINION);
                                                }
                                                zeroProvinces = false;
                                            }
                                            for (int m = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivsSize() - 1; m >= 0; --m) {
                                                if (CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m) != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m) && (CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID()).getAllianceID())) {
                                                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //go through create vassal queue, make vassals
                    Enumeration<String> keyVals = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableVassals_PT.keys();
                    while (keyVals.hasMoreElements()) {
                        String key = keyVals.nextElement();
                        CFG.game.releaseAVasssal(key, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableVassals_PT.get(key), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).lReleasableVassals_PT.get(key).get(0), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, false, false);
                    }
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.size(); ++j) {
                        for (int k = 0; k < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++k) {
                            if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iFromCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).iCivID) {
                                for (int o = 0; o < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.size(); ++o) {
                                    if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).iCivID == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) {
                                        boolean zeroProvinces = CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID).getNumOfProvinces() == 0;
                                        for (int u = 0; u < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.size(); ++u) {
                                            final int tempArmy0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getArmy(0);
                                            final int tempCiv0 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID();
                                            final int nArmyNewOwner2 = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getArmyCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                                            final int nCivNewOwner2 = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID;
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(nCivNewOwner2, 0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).setTrueOwnerOfProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).setCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, false, true);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(tempCiv0, tempArmy0);
                                            CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).updateArmy(nCivNewOwner2, nArmyNewOwner2);
                                            if (zeroProvinces) {
                                                CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID).setPuppetOfCivID(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID);
                                                CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID));
                                                if (CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID) < DiplomacyManager.RELEASED_VASSAL_MIN_OPINION) {
                                                    CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, (float)DiplomacyManager.RELEASED_VASSAL_MIN_OPINION);
                                                }
                                                if (CFG.game.getCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) < DiplomacyManager.RELEASED_VASSAL_MIN_OPINION) {
                                                    CFG.game.setCivRelation_OfCivB(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID, (float)DiplomacyManager.RELEASED_VASSAL_MIN_OPINION);
                                                }
                                                zeroProvinces = false;
                                            }
                                            for (int m = CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivsSize() - 1; m >= 0; --m) {
                                                if (CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m) != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID()).getPuppetOfCivID() != CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m) && (CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getAllianceID() <= 0 || CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m)).getAllianceID() != CFG.game.getCiv(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID()).getAllianceID())) {
                                                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).getCivID(m), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //go through create vassal queue, make vassals
                    Enumeration<String> keyVals = CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableVassals_PT.keys();
                    while (keyVals.hasMoreElements()) {
                        String key = keyVals.nextElement();
                        CFG.game.releaseAVasssal(key, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableVassals_PT.get(key), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).lReleasableVassals_PT.get(key).get(0), CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID, false, false);
                    }
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
                    for (int j = 0; j < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++j) {
                        if (CFG.game.getCivsAtWar(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID)) {
                            CFG.game.acceptPeaceOffer(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.TRUCE_LENGTH + 1);

                            //decrease relation change//
                            DiplomacyManager.decreaseRelation(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID, Math.round((float)CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.TRUCE_LENGTH / 2.0F));
                            DiplomacyManager.decreaseRelation(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, Math.round((float)CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.TRUCE_LENGTH / 2.0F));

                            if (CFG.game.getMilitaryAccess(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID) <= 0 && CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getPuppetOfCivID() != CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID && CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID).getPuppetOfCivID() != CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) {
                                CFG.gameAction.accessLost_UpdateArmies(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID);
                            }
                            if (CFG.game.getMilitaryAccess(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) <= 0 && CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getPuppetOfCivID() != CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID && CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID).getPuppetOfCivID() != CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID) {
                                CFG.gameAction.accessLost_UpdateArmies(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(j).iCivID, CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID);
                            }
                        }
                    }
                }
                for (int l = 0; l < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++l) {
                    if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(l).iCivID).getNumOfProvinces() == 0) {
                        for (int i2 = 1; i2 < CFG.game.getCivsSize(); ++i2) {
                            if (CFG.game.getCiv(i2).getPuppetOfCivID() == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(l).iCivID) {
                                CFG.game.getCiv(i2).setPuppetOfCivID(i2);
                            }
                        }
                        if (CFG.holyRomanEmpire_Manager.getHRE().getIsElector(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(l).iCivID)) {
                            CFG.holyRomanEmpire_Manager.getHRE().removeElector(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(l).iCivID);
                            CFG.holyRomanEmpire_Manager.getHRE().addStrongestPrinceAsElector();
                        }
                    }
                }
                for (int l = 0; l < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++l) {
                    if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(l).iCivID).getNumOfProvinces() == 0) {
                        for (int i2 = 1; i2 < CFG.game.getCivsSize(); ++i2) {
                            if (CFG.game.getCiv(i2).getPuppetOfCivID() == CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(l).iCivID) {
                                CFG.game.getCiv(i2).setPuppetOfCivID(i2);
                            }
                            if (CFG.holyRomanEmpire_Manager.getHRE().getIsElector(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(l).iCivID)) {
                                CFG.holyRomanEmpire_Manager.getHRE().removeElector(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(l).iCivID);
                                CFG.holyRomanEmpire_Manager.getHRE().addStrongestPrinceAsElector();
                            }
                        }
                    }
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.size(); ++i) {
                    CFG.game.buildCivilizationRegions(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID);
                }
                for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.size(); ++i) {
                    CFG.game.buildCivilizationRegions(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID);
                }
                try {
                    for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.size(); ++i) {
                        if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID).getNumOfProvinces() == 0) {
                            for (int z = CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID).getArmyInAnotherProvinceSize() - 1; z >= 0; --z) {
                                CFG.game.getProvince(CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID).getArmyInAnotherProvince(z)).updateArmy(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID, 0);
                            }
                            CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Defenders.get(i).iCivID).setNumOfUnits(0);
                        }
                    }
                    for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.size(); ++i) {
                        if (CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID).getNumOfProvinces() == 0) {
                            for (int z = CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID).getArmyInAnotherProvinceSize() - 1; z >= 0; --z) {
                                CFG.game.getProvince(CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID).getArmyInAnotherProvince(z)).updateArmy(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID, 0);
                            }
                            CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsData_Aggressors.get(i).iCivID).setNumOfUnits(0);
                        }
                    }
                }
                catch (final IndexOutOfBoundsException ex4) {
                    CFG.exceptionStack(ex4);
                }
                int tWarID = -1;
                for (int i2 = 0; i2 < CFG.game.getWarsSize(); ++i2) {
                    if (CFG.game.getWar(i2).WAR_TAG.equals(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.WAR_TAG)) {
                        tWarID = i2;
                        break;
                    }
                }
                try {
                    if (tWarID >= 0) {
                        boolean everyoneAtPeace = true;
                        for (int i3 = 0; i3 < CFG.game.getWar(tWarID).getDefendersSize(); ++i3) {
                            for (int j2 = 0; j2 < CFG.game.getWar(tWarID).getAggressorsSize(); ++j2) {
                                if (CFG.game.getCivsAtWar(CFG.game.getWar(tWarID).getDefenderID(i3).getCivID(), CFG.game.getWar(tWarID).getAggressorID(j2).getCivID())) {
                                    everyoneAtPeace = false;
                                    i3 = CFG.game.getWar(tWarID).getDefendersSize();
                                    break;
                                }
                            }
                        }
                        if (everyoneAtPeace) {
                            CFG.game.removeWarData(tWarID);
                        }
                        else {
                            for (int i3 = CFG.game.getWar(tWarID).getDefendersSize() - 1; i3 >= 0; --i3) {
                                boolean isAtPeace = true;
                                for (int j3 = 0; j3 < CFG.game.getWar(tWarID).getAggressorsSize(); ++j3) {
                                    if (CFG.game.getCivsAtWar(CFG.game.getWar(tWarID).getDefenderID(i3).getCivID(), CFG.game.getWar(tWarID).getAggressorID(j3).getCivID())) {
                                        isAtPeace = false;
                                        break;
                                    }
                                }
                                if (isAtPeace) {
                                    CFG.game.getWar(tWarID).removeDefender(CFG.game.getWar(tWarID).getDefenderID(i3).getCivID());
                                }
                            }
                            for (int i3 = CFG.game.getWar(tWarID).getAggressorsSize() - 1; i3 >= 0; --i3) {
                                boolean isAtPeace = true;
                                for (int j3 = 0; j3 < CFG.game.getWar(tWarID).getDefendersSize(); ++j3) {
                                    if (CFG.game.getCivsAtWar(CFG.game.getWar(tWarID).getDefenderID(j3).getCivID(), CFG.game.getWar(tWarID).getAggressorID(i3).getCivID())) {
                                        isAtPeace = false;
                                        break;
                                    }
                                }
                                if (isAtPeace) {
                                    CFG.game.getWar(tWarID).removeAggressor(CFG.game.getWar(tWarID).getAggressorID(i3).getCivID());
                                }
                            }
                            if (CFG.game.getWar(tWarID).getDefendersSize() == 0 || CFG.game.getWar(tWarID).getAggressorsSize() == 0) {
                                CFG.game.removeWarData(tWarID);
                            }
                        }
                    }
                }
                catch (final IndexOutOfBoundsException ex2) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex2);
                    }
                }
                //refresh provinces
                for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                    if (!CFG.game.getProvince(i).getSeaProvince()
                            && CFG.game.getProvince(i).getWasteland() < 0
                            && CFG.game.getProvince(i).isOccupied()
                            && CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID() != CFG.ideologiesManager.REBELS_ID
                            && !CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(i).getTrueOwnerOfProvince())) {
                        int tempArmy0 = CFG.game.getProvince(i).getArmy(0);
                        int tempCiv0 = CFG.game.getProvince(i).getCivID();
                        int tempArmyNewOwner = CFG.game.getProvince(i).getArmyCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince());
                        CFG.game.getProvince(i).updateArmy(0);
                        CFG.game.getProvince(i).setCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince(), false);
                        CFG.game.getProvince(i).updateArmy(tempCiv0, tempArmy0);
                        CFG.game.getProvince(i).updateArmy(CFG.game.getProvince(i).getTrueOwnerOfProvince(), tempArmyNewOwner);
                        List<Integer> tempCivsLostAccess = new ArrayList<>();

                        for(int j = 0; j < CFG.game.getProvince(i).getCivsSize(); ++j) {
                            tempCivsLostAccess.add(CFG.game.getProvince(i).getCivID(j));
                        }

                        for (Integer civsLostAccess : tempCivsLostAccess) {
                            if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != CFG.game.getProvince(i).getTrueOwnerOfProvince()
                                    && CFG.game.getCiv(CFG.game.getProvince(i).getTrueOwnerOfProvince()).getPuppetOfCivID() != civsLostAccess
                                    && (
                                    CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0
                                            || CFG.game.getCiv(civsLostAccess).getAllianceID()
                                            != CFG.game.getCiv(CFG.game.getProvince(i).getTrueOwnerOfProvince()).getAllianceID()
                            )
                                    && CFG.game.getMilitaryAccess(civsLostAccess, CFG.game.getProvince(i).getTrueOwnerOfProvince()) <= 0) {
                                CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, i);
                            }
                        }
                    }
                }
            }
            catch (final IndexOutOfBoundsException | NullPointerException ex) {
                CFG.exceptionStack(ex);
            }
            CFG.game.lPeaceTreaties.remove(peaceID);
        }
    }
    
    protected static final void declinePeaceTreaty(final int iCivID, final String nTag) {
        final int peaceID = CFG.game.getPeaceTreaty_GameDataID(nTag);
        for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.size(); ++i) {
            if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).peaceTreatyAccepted) {
                CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Aggressors.get(i).iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PeaceTreaty_Rejected(iCivID));
            }
        }
        for (int i = 0; i < CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.size(); ++i) {
            if (CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).peaceTreatyAccepted) {
                CFG.game.getCiv(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData.lCivsDemands_Defenders.get(i).iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_PeaceTreaty_Rejected(iCivID));
            }
        }
        CFG.game.lPeaceTreaties.remove(peaceID);
    }
    
    protected static final boolean sendTradeRequest(final int iToCivID, final int iFromCivID, final TradeRequest_GameData tradeRequest) {
        if (CFG.game.getCiv(iFromCivID).getDiplomacyPoints() >= 10) {
            //trade bool change//
            try {
                if (CFG.game.getCiv(iFromCivID).civGameData.hasSentTrade) {
                    return false;
                }
                CFG.game.getCiv(iFromCivID).civGameData.hasSentTrade = true;
            } catch (Exception e) {
                Gdx.app.log("AoC2.5", "Trade Boolean Exception!");
            }

            CFG.game.getCiv(iToCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TradeReuest(iFromCivID, tradeRequest));
            CFG.game.getCiv(iFromCivID).setDiplomacyPoints(CFG.game.getCiv(iFromCivID).getDiplomacyPoints() - 10);
            if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
                CFG.game.getCiv(iFromCivID).addSentMessages(new Civilization_SentMessages(iToCivID, Message_Type.TRADE_REQUEST));
            }
            return true;
        }
        return false;
    }
    
    protected static final void acceptTradeRequest(final int iCivID, final int iFromCivID, final TradeRequest_GameData tradeRequest) {
        //trade bool change//
        try {
            CFG.game.getCiv(iFromCivID).civGameData.hasSentTrade = false;
        } catch (Exception e) {
            Gdx.app.log("AoC2.5", "Trade Boolean Accept Exception!");
        }

        if (tradeRequest.listLEFT.militaryAccess) {
            CFG.game.setMilitaryAccess(iCivID, iFromCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iFromCivID, iCivID));
        }
        if (tradeRequest.listRight.militaryAccess) {
            CFG.game.setMilitaryAccess(iFromCivID, iCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iCivID, iFromCivID));
        }
        if (tradeRequest.listLEFT.iGold > 0) {
            CFG.game.getCiv(iFromCivID).setMoney(CFG.game.getCiv(iFromCivID).getMoney() - tradeRequest.listLEFT.iGold);
            CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() + tradeRequest.listLEFT.iGold);
        }
        if (tradeRequest.listLEFT.lProvinces.size() > 0) {
            for (int i = 0; i < tradeRequest.listLEFT.lProvinces.size(); ++i) {
                if (CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getCivID() == iFromCivID && CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getTrueOwnerOfProvince() == iFromCivID) {
                    final int tempArmy0 = CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getArmy(0);
                    final int tempCiv0 = CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getCivID();
                    final int tempArmyNewOwner = CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getArmyCivID(iCivID);
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).updateArmy(0);
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).setTrueOwnerOfProvince(iCivID);
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).setCivID(iCivID, false);
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).updateArmy(tempCiv0, tempArmy0);
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).updateArmy(iCivID, tempArmyNewOwner);
                    final List<Integer> tempCivsLostAccess = new ArrayList<Integer>();
                    for (int j = 0; j < CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getCivsSize(); ++j) {
                        tempCivsLostAccess.add(CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getCivID(j));
                    }
                    for (Integer civsLostAccess : tempCivsLostAccess) {
                        if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != iCivID && CFG.game.getCiv(iCivID).getPuppetOfCivID() != civsLostAccess && (CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0 || CFG.game.getCiv(civsLostAccess).getAllianceID() != CFG.game.getCiv(iCivID).getAllianceID()) && CFG.game.getMilitaryAccess(civsLostAccess, iCivID) <= 0) {
                            CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, tradeRequest.listLEFT.lProvinces.get(i));
                        }
                    }
                    if (!CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getIsCapital()) {
                        CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).removeCapitalCityIcon();
                    }
                    CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(i)).getCore().removeCore(tradeRequest.iCivLEFT);
                }
            }
            CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
            CFG.game.buildCivilizationsRegions_TextOver(iCivID);
        }
        if (tradeRequest.listLEFT.iDeclarWarOnCivID > 0) {
            CFG.game.declareWar(iFromCivID, tradeRequest.listLEFT.iDeclarWarOnCivID, false);
        }
        if (tradeRequest.listLEFT.iFormCoalitionAgainst > 0) {
            CFG.game.declareWar(iFromCivID, tradeRequest.listLEFT.iFormCoalitionAgainst, false);
            CFG.game.declareWar(iCivID, tradeRequest.listLEFT.iFormCoalitionAgainst, false);
            CFG.game.setCivNonAggressionPact(iFromCivID, iCivID, 40);
            CFG.game.setMilitaryAccess(iFromCivID, iCivID, 40);
            CFG.game.setMilitaryAccess(iCivID, iFromCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iCivID, iFromCivID));
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iFromCivID, iCivID));
        }
        if (tradeRequest.listLEFT.defensivePact) {
            CFG.game.setDefensivePact(iFromCivID, iCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_SignedDefensivePact(iCivID, iFromCivID));
        }
        if (tradeRequest.listLEFT.nonAggressionPact) {
            CFG.game.setCivNonAggressionPact(iFromCivID, iCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_SignedNonAggressionPact(iCivID, iFromCivID));
        }
        if (tradeRequest.listLEFT.proclaimIndependence) {
            CFG.game.setGuarantee(iFromCivID, iCivID, 100);
            CFG.historyManager.addHistoryLog(new HistoryLog_Guarantee(iFromCivID, iCivID));
        }
        if (tradeRequest.listRight.iGold > 0) {
            CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() - tradeRequest.listRight.iGold);
            CFG.game.getCiv(iFromCivID).setMoney(CFG.game.getCiv(iFromCivID).getMoney() + tradeRequest.listRight.iGold);
        }
        if (tradeRequest.listRight.lProvinces.size() > 0) {
            for (int i = 0; i < tradeRequest.listRight.lProvinces.size(); ++i) {
                if (CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getCivID() == iCivID && CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getTrueOwnerOfProvince() == iCivID) {
                    final int tempArmy0 = CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getArmy(0);
                    final int tempCiv0 = CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getCivID();
                    final int tempArmyNewOwner = CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getArmyCivID(iCivID);
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).updateArmy(0);
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).setTrueOwnerOfProvince(iFromCivID);
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).setCivID(iFromCivID, false);
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).updateArmy(tempCiv0, tempArmy0);
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).updateArmy(iCivID, tempArmyNewOwner);
                    final List<Integer> tempCivsLostAccess = new ArrayList<Integer>();
                    for (int j = 0; j < CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getCivsSize(); ++j) {
                        tempCivsLostAccess.add(CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getCivID(j));
                    }
                    for (Integer civsLostAccess : tempCivsLostAccess) {
                        if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != iFromCivID && CFG.game.getCiv(iFromCivID).getPuppetOfCivID() != civsLostAccess && (CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0 || CFG.game.getCiv(civsLostAccess).getAllianceID() != CFG.game.getCiv(iFromCivID).getAllianceID()) && CFG.game.getMilitaryAccess(civsLostAccess, iFromCivID) <= 0) {
                            CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, tradeRequest.listRight.lProvinces.get(i));
                        }
                    }
                    if (!CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getIsCapital()) {
                        CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).removeCapitalCityIcon();
                    }
                    CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(i)).getCore().removeCore(tradeRequest.iCivRIGHT);
                }
            }
            CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
            CFG.game.buildCivilizationsRegions_TextOver(iCivID);
        }
        if (tradeRequest.listRight.iDeclarWarOnCivID > 0) {
            CFG.game.declareWar(iCivID, tradeRequest.listRight.iDeclarWarOnCivID, false);
        }
        if (tradeRequest.listRight.iFormCoalitionAgainst > 0) {
            CFG.game.declareWar(iFromCivID, tradeRequest.listRight.iFormCoalitionAgainst, false);
            CFG.game.declareWar(iCivID, tradeRequest.listRight.iFormCoalitionAgainst, false);
            CFG.game.setCivNonAggressionPact(iFromCivID, iCivID, 40);
            CFG.game.setMilitaryAccess(iFromCivID, iCivID, 40);
            CFG.game.setMilitaryAccess(iCivID, iFromCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iFromCivID, iCivID));
            CFG.historyManager.addHistoryLog(new HistoryLog_HaveMilitartyAccess(iCivID, iFromCivID));
        }
        if (tradeRequest.listRight.defensivePact) {
            CFG.game.setDefensivePact(iFromCivID, iCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_SignedDefensivePact(iFromCivID, iCivID));
        }
        if (tradeRequest.listRight.nonAggressionPact) {
            CFG.game.setCivNonAggressionPact(iFromCivID, iCivID, 40);
            CFG.historyManager.addHistoryLog(new HistoryLog_SignedNonAggressionPact(iCivID, iFromCivID));
        }
        if (tradeRequest.listRight.proclaimIndependence) {
            CFG.game.setGuarantee(iCivID, iFromCivID, 100);
            CFG.historyManager.addHistoryLog(new HistoryLog_Guarantee(iFromCivID, iCivID));
        }
        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TradeReuest_Accepted(iCivID));
    }
    
    protected static final void declineTradeRequest(final int iCivID, final int iFromCivID, final TradeRequest_GameData tradeRequest) {
        //trade bool change//
        try {
            CFG.game.getCiv(iFromCivID).civGameData.hasSentTrade = false;
        } catch (Exception e) {
            Gdx.app.log("AoC2.5", "Trade Boolean Decline Exception!");
        }

        CFG.game.getCiv(iFromCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TradeReuest_Denied(iCivID));
    }

    protected static void runRelationsOutDated() {
        for (int i = Game_Calendar.TURN_ID % 6; i < CFG.game.getCivsSize(); i += 6) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
                    if (CFG.game.getCiv(j).getNumOfProvinces() > 0) {
                        if (CFG.game.getCivRelation_OfCivB(i, j) > 15.0f) {
                            CFG.game.setCivRelation_OfCivB(i, j, CFG.game.getCivRelation_OfCivB(i, j) - 0.625f);
                        }
                        else if (CFG.game.getCivRelation_OfCivB(i, j) < -20.0f) {
                            CFG.game.setCivRelation_OfCivB(i, j, CFG.game.getCivRelation_OfCivB(i, j) + 0.535f);
                        }
                    }
                }
            }
        }
    }
    
    protected static final void buildFriendlyCivs() {
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).clearFreidnlyCivs();
        }
        for (int i = 1; i < CFG.game.getCivsSize() - 1; ++i) {
            for (int j = i + 1; j < CFG.game.getCivsSize(); ++j) {
                if (CFG.game.getCivRelation_OfCivB(i, j) > 44.0f) {
                    CFG.game.getCiv(i).addFriendlyCiv(j);
                }
                else if (CFG.game.getCivRelation_OfCivB(i, j) < -25.0f) {
                    CFG.game.getCiv(i).addHatedCiv(j);
                }
                if (CFG.game.getCivRelation_OfCivB(j, i) > 44.0f) {
                    CFG.game.getCiv(j).addFriendlyCiv(i);
                }
                else if (CFG.game.getCivRelation_OfCivB(j, i) < -25.0f) {
                    CFG.game.getCiv(j).addHatedCiv(i);
                }
            }
        }
    }
    
    protected static final void updatePlayersFriendlyCivs() {
        if (!CFG.SPECTATOR_MODE) {
            try {
                for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                    if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces() > 0) {
                        for (int z = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getFriendlyCivsSize() - 1; z >= 0; --z) {
                            if (CFG.game.getCivRelation_OfCivB(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getFriendlyCiv(z).iCivID, CFG.game.getPlayer(i).getCivID()) < 39.0f) {
                                CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).removeFriendlyCiv(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getFriendlyCiv(z).iCivID);
                            }
                        }
                    }
                }
            }
            catch (final IndexOutOfBoundsException ex) {}
        }
    }
    
    protected static final void checkCivsHatedCivilizations_IfStillExsits() {
        if (Game_Calendar.TURN_ID % 9 == 0) {
            for (int i = 1 + Game_Calendar.TURN_ID % 2; i < CFG.game.getCivsSize(); i += 2) {
                if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                    for (int z = CFG.game.getCiv(i).getHatedCivsSize() - 1; z >= 0; --z) {
                        if (CFG.game.getCiv(CFG.game.getCiv(i).getHatedCiv(z).iCivID).getNumOfProvinces() == 0) {
                            CFG.game.getCiv(i).removeHatedCiv(CFG.game.getCiv(i).getHatedCiv(z).iCivID);
                        }
                    }
                }
            }
        }
    }
    
    protected static final void updateFriendlyCiv(final int nCivA, final int nCivB) {
        if (CFG.game.getCivRelation_OfCivB(nCivA, nCivB) > 34.0f) {
            if (CFG.game.getCiv(nCivB).addFriendlyCiv(nCivA)) {
                CFG.game.getCiv(nCivA).removeHatedCiv(nCivB);
            }
        }
        else if (CFG.game.getCivRelation_OfCivB(nCivA, nCivB) < -25.0f && CFG.game.getCiv(nCivA).addHatedCiv(nCivB)) {
            CFG.game.getCiv(nCivB).removeFriendlyCiv(nCivA);
        }
        if (CFG.game.getCivRelation_OfCivB(nCivB, nCivA) > 34.0f) {
            if (CFG.game.getCiv(nCivA).addFriendlyCiv(nCivB)) {
                CFG.game.getCiv(nCivB).removeHatedCiv(nCivA);
            }
        }
        else if (CFG.game.getCivRelation_OfCivB(nCivB, nCivA) < -25.0f && CFG.game.getCiv(nCivB).addHatedCiv(nCivA)) {
            CFG.game.getCiv(nCivA).removeFriendlyCiv(nCivB);
        }
    }
    
    protected static boolean improveRelation(final int iCivA, final int iCivB) {
        if (CFG.game.getCiv(iCivA).getNumOfProvinces() == 0) {
            return false;
        }
        if (CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().isEmassyClosed(iCivA)) {
            return false;
        }
        if (CFG.game.getCiv(iCivA).getDiplomacyPoints() >= 5 && !CFG.game.getCivsAtWar(iCivA, iCivB)) {
            final float out = getImproveRelation(iCivA, iCivB);
            final float out2 = getImproveRelation(iCivB, iCivA) * 0.9175f;
            boolean updateFriendlyRelation = CFG.game.getCivRelation_OfCivB(iCivA, iCivB) < 44.0f;
            CFG.game.setCivRelation_OfCivB(iCivA, iCivB, CFG.game.getCivRelation_OfCivB(iCivA, iCivB) + out2);
            CFG.game.setCivRelation_OfCivB(iCivB, iCivA, CFG.game.getCivRelation_OfCivB(iCivB, iCivA) + out);
            if (updateFriendlyRelation) {
                updateFriendlyCiv(iCivA, iCivB);
            }
            return true;
        }
        return false;
    }
    
    protected static float getImproveRelation(final int iCivA, final int iCivB) {
        float out = 0.8425f + CFG.oR.nextInt(121) / 100.0f;
        out = 0.125f + out * (Math.min(CFG.game.getCivRelation_OfCivB(iCivB, iCivA) + 100.0f, 145.0f) / 200.0f) * Math.min(Math.max(0.325f, CFG.game.getCiv(iCivA).getRankScore() / (float)CFG.game.getCiv(iCivB).getRankScore()), 1.0f);
        return out;
    }
    
    protected static boolean decreaseRelation(final int iCivA, final int iCivB, final int nNumOfTurns) {
        if (CFG.game.getCiv(iCivA).getDiplomacyPoints() >= 2) {
            CFG.game.getCiv(iCivA).setDiplomacyPoints(CFG.game.getCiv(iCivA).getDiplomacyPoints() - 2);
            CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Relations_Insult(iCivA));
            CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().removeImproveRelations_WithCivID(iCivA, iCivB);
            CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().removeImproveRelations_WithCivID(iCivB, iCivA);
            CFG.game.getCiv(iCivA).getCivilization_Diplomacy_GameData().addEmbeassyClosed(new Civilization_ClosedEmbassy(iCivB, nNumOfTurns));
            CFG.game.getCiv(iCivB).getCivilization_Diplomacy_GameData().addEmbeassyClosed(new Civilization_ClosedEmbassy(iCivA, nNumOfTurns));
            float out = getDecreaseRelation(iCivA, iCivB);
            CFG.game.setCivRelation_OfCivB(iCivA, iCivB, (CFG.game.getCivRelation_OfCivB(iCivA, iCivB) > -100.0f && CFG.game.getCivRelation_OfCivB(iCivA, iCivB) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iCivA, iCivB) + out));
            out *= 0.415f;
            CFG.game.setCivRelation_OfCivB(iCivB, iCivA, (CFG.game.getCivRelation_OfCivB(iCivB, iCivA) > -100.0f && CFG.game.getCivRelation_OfCivB(iCivB, iCivA) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(iCivB, iCivA) + out));
            worldRecations((int)Math.min(30.0f, CFG.game.getCivRelation_OfCivB(iCivA, iCivB) + 100.0f) / 3, iCivA, iCivB);
            updateFriendlyCiv(iCivA, iCivB);
            return true;
        }
        return false;
    }
    
    protected static float getDecreaseRelation(final int iCivA, final int iCivB) {
        float out = -(26.25f + CFG.oR.nextInt(27) / 100.0f);
        out = out * 0.4f + out * 0.725f * ((CFG.game.getCivRelation_OfCivB(iCivB, iCivA) + 100.0f) / 200.0f);
        return out;
    }
    
    protected static final void liberateAVassal(final int iLord, final int iVassal) {
        if (CFG.game.getCiv(iVassal).getPuppetOfCivID() == iLord) {
            CFG.game.getCiv(iVassal).setPuppetOfCivID(iVassal);
            if (CFG.game.getMilitaryAccess(iLord, iVassal) <= 0) {
                CFG.gameAction.accessLost_UpdateArmies(iVassal, iLord);
            }
            if (CFG.game.getMilitaryAccess(iVassal, iLord) <= 0) {
                CFG.gameAction.accessLost_UpdateArmies(iLord, iVassal);
            }
            if (CFG.FOG_OF_WAR > 0) {
                if (CFG.game.getPlayerID_ByCivID(iLord) >= 0) {
                    CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iLord));
                }
                if (CFG.game.getPlayerID_ByCivID(iVassal) >= 0) {
                    CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iVassal));
                }
            }
            CFG.historyManager.addHistoryLog(new HistoryLog_IsNotVassal(iLord, iVassal));
            CFG.game.getCiv(iVassal).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Liberation(iLord));
            if (!CFG.game.getCiv(iLord).getControlledByPlayer()) {
                CFG.game.getCiv(iLord).addSentMessages(new Civilization_SentMessages(iVassal, Message_Type.LIBERATION_OF_VASSAL));
            }
        }
    }
    
    protected static final void declarationOfIndependeceByVassal(final int iLord, final int iVassal) {
        if (CFG.game.getCivTruce(iLord, iVassal) > 0) {
            return;
        }
        if (CFG.NO_LIBERITY) {
            return;
        }
        if (CFG.game.getCiv(iVassal).getPuppetOfCivID() == iLord) {
            CFG.game.getCiv(iVassal).setPuppetOfCivID(iVassal);
            CFG.game.getCiv(iVassal).setVassalLiberityDesire(0.0f);
            if (CFG.game.getMilitaryAccess(iLord, iVassal) <= 0) {
                CFG.gameAction.accessLost_UpdateArmies(iVassal, iLord);
            }
            if (CFG.game.getMilitaryAccess(iVassal, iLord) <= 0) {
                CFG.gameAction.accessLost_UpdateArmies(iLord, iVassal);
            }
            if (CFG.FOG_OF_WAR > 0) {
                if (CFG.game.getPlayerID_ByCivID(iLord) >= 0) {
                    CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iLord));
                }
                if (CFG.game.getPlayerID_ByCivID(iVassal) >= 0) {
                    CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(iVassal));
                }
            }
            CFG.historyManager.addHistoryLog(new HistoryLog_IsNotVassal(iLord, iVassal));
            CFG.game.getCiv(iVassal).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Liberation(iLord));
            CFG.game.getCiv(iLord).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_DeclarationOfIndependence_ByVassal(iVassal));
            if (!CFG.game.getCiv(iLord).getControlledByPlayer()) {
                CFG.game.getCiv(iLord).addSentMessages(new Civilization_SentMessages(iVassal, Message_Type.LIBERATION_OF_VASSAL));
            }
        }
    }
    
    protected static final boolean canTakeMoreLoans(final int nCivID) {
        return CFG.game.getCiv(nCivID).getLoansSize() < 5;
    }
    
    protected static final void takeLoan(final int iCivID, int iGold, final int iDuration) {
        if (canTakeMoreLoans(iCivID) && iGold > 0 && iDuration >= 5 && iDuration <= 30) {
            if (!canTakeMoreLoans(iCivID)) {
                return;
            }
            if (iGold > takeLoan_MaxValue(iCivID)) {
                iGold = takeLoan_MaxValue(iCivID);
            }
            CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() + iGold);
            CFG.game.getCiv(iCivID).addLoan((int)Math.max(Math.ceil((iGold + iGold * takeLoan_InterestRate(iCivID, iGold, iDuration) / 100.0f) / iDuration), 1.0), iDuration);
            CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() - 6);
        }
    }
    
    protected static final int takeLoan_MinValue() {
        return 30;
    }
    
    protected static final int takeLoan_MaxValue(final int iCivID) {
        return (int)Math.max((CFG.game.getCiv(iCivID).iIncomeTaxation + CFG.game.getCiv(iCivID).iIncomeProduction) * 0.6f, 35.0f);
    }
    
    protected static final float takeLoan_InterestRate(final int iCivID, final int iGold, final int iDuration) {
        if (iGold == 0) {
            return 0.0f;
        }
        return 7.25f + CFG.game.getCiv(iCivID).getLoansSize() * 0.7f + (8.0f + CFG.game.getCiv(iCivID).getLoansSize() / 4.0f) * (iDuration - 5) / 25.0f;
    }
    
    protected static final void repayLoan(final int iCivID, final int iLoanID) {
        try {
            CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() - (long) CFG.game.getCiv(iCivID).getLoan(iLoanID).iTurnsLeft * CFG.game.getCiv(iCivID).getLoan(iLoanID).iGoldPerTurn);
            CFG.game.getCiv(iCivID).removeLoan(iLoanID);
        }
        catch (final IndexOutOfBoundsException ex) {}
    }
    
    protected static final float plunderEfficiency(final int nCivID, final int nProvinceID, final int nArmy) {
        return Math.min(1.0f, nArmy / plunderEfficiency_RequiredMAX(nCivID, nProvinceID));
    }
    
    protected static final float plunderEfficiency_RequiredMAX(final int nCivID, final int nProvinceID) {
        return CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * (0.1375f - 0.035f * Math.min(CFG.game.getCiv(nCivID).getTechnologyLevel(), 1.0f));
    }
    
    protected static final int plunderProvinceIncome(final int nCivID, final int nProvinceID, final int nArmy) {
        return (int)(CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(nProvinceID) + CFG.game_NextTurnUpdate.getProvinceIncome_Production(nProvinceID));
    }
    
    protected static final int plunderTreasuryIncome(final int nCivID, final int nProvinceID, final int nArmy) {
        return (int)(plunderProvinceIncome(nCivID, nProvinceID, nArmy) * 1.45f * plunderEfficiency(nCivID, nProvinceID, nArmy) * (1.0f - 0.625f * CFG.game.getProvince(nProvinceID).getRevolutionaryRisk()));
    }
    
    protected static final float plunder_LossesEconomy_Perc(final int nCivID, final int nProvinceID, final int nArmy) {
        return (0.0425f + CFG.oR.nextInt(525) / 10000.0f) * plunderEfficiency(nCivID, nProvinceID, nArmy);
    }
    
    protected static final float plunder_LossesDevelopment_Perc(final int nCivID, final int nProvinceID, final int nArmy) {
        return (0.0875f + CFG.oR.nextInt(625) / 10000.0f) * plunderEfficiency(nCivID, nProvinceID, nArmy);
    }
    
    protected static final float plunder_Happiness(final int nCivID, final int nProvinceID, final int nArmy) {
        return (0.05728f + CFG.oR.nextInt(426) / 10000.0f) * plunderEfficiency(nCivID, nProvinceID, nArmy);
    }
    
    protected static final float plunder_RevolutionaryRisk(final int nCivID, final int nProvinceID, final int nArmy) {
        return Math.max((0.011861f + CFG.oR.nextInt(268) / 10000.0f) * plunderEfficiency(nCivID, nProvinceID, nArmy), 0.034378f);
    }
    
    protected static final int plunder_Population(final int nCivID, final int nProvinceID, final int nArmy) {
        return (int)Math.min(nArmy * (0.04864f + CFG.oR.nextInt(412) / 10000.0f), CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() * 0.0786f);
    }
    
    protected static final void plunderProvince(final int iCivID, final int nProvinceID, int nArmy) {
        if (CFG.game.getCiv(iCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).COST_OF_PLUNDER) {
            return;
        }
        if (nProvinceID < 0 || !CFG.game.getProvince(nProvinceID).isOccupied() || CFG.game.getProvince(nProvinceID).getSeaProvince()) {
            return;
        }
        int currPlunderArmy = 0;
        int i = 0;
        while (i < CFG.game.getCiv(iCivID).getMoveUnitsPlunderSize()) {
            if (CFG.game.getCiv(iCivID).getMoveUnits_Plunder(i).getFromProvinceID() == nProvinceID) {
                currPlunderArmy = CFG.game.getCiv(iCivID).getMoveUnits_Plunder(i).getNumOfUnits();
                if (nArmy == 0) {
                    CFG.game.getCiv(iCivID).removePlunder(i);
                    CFG.game.getProvince(nProvinceID).updateArmy(iCivID, CFG.game.getProvince(nProvinceID).getArmyCivID(iCivID) + currPlunderArmy);
                    if (currPlunderArmy > 0) {
                        CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).COST_OF_PLUNDER);
                    }
                    return;
                }
                break;
            }
            else {
                ++i;
            }
        }
        if (nArmy > CFG.game.getProvince(nProvinceID).getArmyCivID(iCivID) + currPlunderArmy) {
            nArmy = CFG.game.getProvince(nProvinceID).getArmyCivID(iCivID) + currPlunderArmy;
        }
        if (nArmy <= 0) {
            return;
        }
        CFG.game.getCiv(iCivID).newPlunder(nProvinceID, nArmy);
        CFG.game.getProvince(nProvinceID).updateArmy(iCivID, CFG.game.getProvince(nProvinceID).getArmyCivID(iCivID) + currPlunderArmy - nArmy);
        if (currPlunderArmy == 0) {
            CFG.game.getCiv(iCivID).setMovePoints(CFG.game.getCiv(iCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).COST_OF_PLUNDER);
        }
    }
    
    protected static final void plunder(final int iCivID, final int nProvinceID, final int nArmy) {
        if (CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince() == iCivID) {
            return;
        }
        final int nTreasury = plunderTreasuryIncome(iCivID, nProvinceID, nArmy);
        final float nHappiness = plunder_Happiness(iCivID, nProvinceID, nArmy);
        final int nEconomy = (int)(4.0 + Math.ceil(CFG.game.getProvince(nProvinceID).getEconomy() * plunder_LossesEconomy_Perc(iCivID, nProvinceID, nArmy)));
        final float nDevelopment = CFG.game.getProvince(nProvinceID).getDevelopmentLevel() * plunder_LossesDevelopment_Perc(iCivID, nProvinceID, nArmy);
        final float fRevolutionary = plunder_RevolutionaryRisk(iCivID, nProvinceID, nArmy);
        final int nPopulation = plunder_Population(iCivID, nProvinceID, nArmy);
        final int tempPopulationBefore = CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation();
        final int tempEconomyBefore = CFG.game.getProvince(nProvinceID).getEconomy();
        CFG.game.getCiv(iCivID).setMoney(CFG.game.getCiv(iCivID).getMoney() + nTreasury);
        CFG.game.getProvince(nProvinceID).setEconomy(CFG.game.getProvince(nProvinceID).getEconomy() - nEconomy);
        CFG.game.getProvince(nProvinceID).setDevelopmentLevel(CFG.game.getProvince(nProvinceID).getDevelopmentLevel() - nDevelopment);
        CFG.game.getProvince(nProvinceID).setHappiness(CFG.game.getProvince(nProvinceID).getHappiness() - nHappiness);
        CFG.game.getProvince(nProvinceID).setRevolutionaryRisk(CFG.game.getProvince(nProvinceID).getRevolutionaryRisk() + CFG.gameAges.getAge_RevolutionaryRiskModifier(Game_Calendar.CURRENT_AGEID) * fRevolutionary);
        CFG.gameAction.updatePopulationLosses(nProvinceID, nPopulation);
        final int tempWarID = CFG.game.getWarID(iCivID, CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince());
        if (tempWarID >= 0) {
            CFG.game.updateWarStatistics(tempWarID, iCivID, CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince(), Math.max(tempPopulationBefore - CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation(), 0), Math.max(tempEconomyBefore - CFG.game.getProvince(nProvinceID).getEconomy(), 0));
        }
        CFG.game.getCiv(iCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Plunder(iCivID, nProvinceID, nTreasury, nEconomy, nDevelopment, nHappiness, nPopulation));
        if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince()).getControlledByPlayer()) {
            CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince()).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Plunder_Plundred(iCivID, nProvinceID, nEconomy, nDevelopment, nHappiness, nPopulation));
        }
    }
    
    protected static final void leaveAlliance(final int nCivID) {
        if (CFG.game.getCiv(nCivID).getAllianceID() > 0 && CFG.game.getCiv(nCivID).getAllianceID() < CFG.game.getAlliancesSize()) {
            final int allianceID = CFG.game.getCiv(nCivID).getAllianceID();
            CFG.game.getAlliance(allianceID).removeCivilization(nCivID);
            CFG.game.getCiv(nCivID).setAllianceID(0);
            if (CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                final int tPlayerID = CFG.game.getPlayerID_ByCivID(nCivID);
                for (int i = 0; i < CFG.game.getAlliance(allianceID).getCivilizationsSize(); ++i) {
                    if (CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).getControlledByPlayer()) {
                        final int tPlayerID2 = CFG.game.getPlayerID_ByCivID(CFG.game.getAlliance(allianceID).getCivilization(i));
                        if (tPlayerID2 >= 0) {
                            for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
                                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).updateFogOfWar(tPlayerID2);
                            }
                            for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.lVassals.size(); ++j) {
                                for (int k = 0; k < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getNumOfProvinces(); ++k) {
                                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getProvinceID(j)).updateFogOfWar(tPlayerID2);
                                }
                            }
                        }
                    }
                    if (tPlayerID >= 0) {
                        for (int l = 0; l < CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).getNumOfProvinces(); ++l) {
                            CFG.game.getProvince(CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).getProvinceID(l)).updateFogOfWar(tPlayerID);
                        }
                        for (int l = 0; l < CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).civGameData.lVassals.size(); ++l) {
                            for (int m = 0; m < CFG.game.getCiv(CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).civGameData.lVassals.get(l).iCivID).getNumOfProvinces(); ++m) {
                                CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).civGameData.lVassals.get(l).iCivID).getProvinceID(l)).updateFogOfWar(tPlayerID);
                            }
                        }
                    }
                }
            }
            for (int i2 = 0; i2 < CFG.game.getAlliance(allianceID).getCivilizationsSize(); ++i2) {
                final int out = -10;
                CFG.game.setCivRelation_OfCivB(nCivID, CFG.game.getAlliance(allianceID).getCivilization(i2), (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getAlliance(allianceID).getCivilization(i2)) > -100.0f && CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getAlliance(allianceID).getCivilization(i2)) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getAlliance(allianceID).getCivilization(i2)) + out));
                CFG.game.setCivRelation_OfCivB(CFG.game.getAlliance(allianceID).getCivilization(i2), nCivID, (CFG.game.getCivRelation_OfCivB(CFG.game.getAlliance(allianceID).getCivilization(i2), nCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(CFG.game.getAlliance(allianceID).getCivilization(i2), nCivID) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(CFG.game.getAlliance(allianceID).getCivilization(i2), nCivID) + out));
                CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i2)).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_LeftAlliance(nCivID, allianceID));
            }
            CFG.historyManager.addHistoryLog(new HistoryLog_LeavesAlliance(nCivID, allianceID));
        }
    }
    
    protected static final void kickFromAlliance(final int nCivID, final int byCivID) {
        if (CFG.game.getCiv(nCivID).getAllianceID() > 0 && CFG.game.getCiv(nCivID).getAllianceID() < CFG.game.getAlliancesSize() && CFG.game.getCiv(nCivID).getAllianceID() == CFG.game.getCiv(byCivID).getAllianceID()) {
            final int allianceID = CFG.game.getCiv(nCivID).getAllianceID();
            CFG.game.getAlliance(allianceID).removeCivilization(nCivID);
            CFG.game.getCiv(nCivID).setAllianceID(0);
            for (int i = 0; i < CFG.game.getAlliance(allianceID).getCivilizationsSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).getControlledByPlayer()) {
                    if (CFG.game.getAlliance(allianceID).getCivilization(i) == nCivID) {
                        final int tPlayerID = CFG.game.getPlayerID_ByCivID(nCivID);
                        if (tPlayerID >= 0) {
                            for (int j = 0; j < CFG.game.getCiv(byCivID).getNumOfProvinces(); ++j) {
                                CFG.game.getProvince(CFG.game.getCiv(byCivID).getProvinceID(j)).updateFogOfWar(tPlayerID);
                            }
                            for (int j = 0; j < CFG.game.getCiv(byCivID).civGameData.lVassals.size(); ++j) {
                                for (int k = 0; k < CFG.game.getCiv(CFG.game.getCiv(byCivID).civGameData.lVassals.get(j).iCivID).getNumOfProvinces(); ++k) {
                                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(byCivID).civGameData.lVassals.get(j).iCivID).getProvinceID(j)).updateFogOfWar(tPlayerID);
                                }
                            }
                        }
                    }
                    if (CFG.game.getAlliance(allianceID).getCivilization(i) == nCivID) {
                        final int tPlayerID = CFG.game.getPlayerID_ByCivID(nCivID);
                        if (tPlayerID >= 0) {
                            for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
                                CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).updateFogOfWar(tPlayerID);
                            }
                            for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.lVassals.size(); ++j) {
                                for (int k = 0; k < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getNumOfProvinces(); ++k) {
                                    CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getProvinceID(j)).updateFogOfWar(tPlayerID);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < CFG.game.getAlliance(allianceID).getCivilizationsSize(); ++i) {
                if (CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(i)).getControlledByPlayer()) {
                    final int tPlayerID2 = CFG.game.getPlayerID_ByCivID(CFG.game.getAlliance(allianceID).getCivilization(i));
                    if (tPlayerID2 >= 0) {
                        for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
                            CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).updateFogOfWar(tPlayerID2);
                        }
                        for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.lVassals.size(); ++j) {
                            for (int k = 0; k < CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getNumOfProvinces(); ++k) {
                                CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(j).iCivID).getProvinceID(j)).updateFogOfWar(tPlayerID2);
                            }
                        }
                    }
                }
            }
            final int out = -25;
            CFG.game.setCivRelation_OfCivB(nCivID, byCivID, (CFG.game.getCivRelation_OfCivB(nCivID, byCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(nCivID, byCivID) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(nCivID, byCivID) + out));
            CFG.game.setCivRelation_OfCivB(byCivID, nCivID, (CFG.game.getCivRelation_OfCivB(byCivID, nCivID) > -100.0f && CFG.game.getCivRelation_OfCivB(byCivID, nCivID) + out <= -100.0f) ? -99.0f : (CFG.game.getCivRelation_OfCivB(byCivID, nCivID) + out));
            for (int l = 0; l < CFG.game.getAlliance(allianceID).getCivilizationsSize(); ++l) {
                CFG.game.getCiv(CFG.game.getAlliance(allianceID).getCivilization(l)).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_LeftAlliance(nCivID, allianceID));
            }
            CFG.historyManager.addHistoryLog(new HistoryLog_LeavesAlliance(nCivID, allianceID));
        }
    }
    
    protected static final boolean changeGovernmentType(final int nCivID, final int toGovType) {
        //if less money, govtype already same, of less diplopoints
        if (CFG.game.getCiv(nCivID).getMoney() < Ideologies_Manager.getChangeGovernmentCost(nCivID) || CFG.game.getCiv(nCivID).getIdeologyID() == toGovType || CFG.game.getCiv(nCivID).getMovePoints() < 22) return false;
        //dynamic event manager change//
        if (CFG.DYNAMIC_EVENTS) {
            //if already scanning for civil war
            if (CFG.dynamicEventManager.eventManagerCivilWar.inCivilWar(nCivID)) return false;
            CFG.dynamicEventManager.eventManagerCivilWar.changesGovernment(nCivID);
        }


        CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - Ideologies_Manager.getChangeGovernmentCost(nCivID));
        CFG.game.getCiv(nCivID).setMovePoints(CFG.game.getCiv(nCivID).getMovePoints() - 22);
        CFG.game.updateCivilizationIdeology(nCivID, CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(toGovType).getExtraTag());
        for (int i = 0; i < CFG.game.getCiv(nCivID).getCivRegionsSize(); ++i) {
            CFG.game.getCiv(nCivID).getCivRegion(i).buildScaleOfText();
        }
        return true;
    }

    //new autonomy function
    protected static final boolean changeAutonomyType(final int nCivID, final int toAuType) {
        int iLordCivID = CFG.game.getCiv(nCivID).getPuppetOfCivID();
        if (!CFG.game.getCiv(nCivID).getIsPupet() || CFG.game.getCiv(iLordCivID).getVassal_AutonomyStatus(nCivID).getIndexOf() == toAuType) return false;
        if (CFG.game.getCiv(iLordCivID).getMoney() < CFG.gameAutonomy.changeAutonomyCost(nCivID)) {
            CFG.toast.setInView(CFG.langManager.get("NotEnoughMoney") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
            CFG.toast.setTimeInView(4500);
            return false;
        } else if (CFG.game.getCiv(iLordCivID).getDiplomacyPoints() < CFG.gameAutonomy.lAutonomy.get(toAuType).getDiploCost()) {
            CFG.toast.setInView(CFG.langManager.get("NotEnoughDiplo") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
            CFG.toast.setTimeInView(4500);
            return false;
        } else if (CFG.game.getCiv(nCivID).getStability() < 0.85F) {
            CFG.toast.setInView(CFG.langManager.get("NotEnoughStab") + "!", CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
            CFG.toast.setTimeInView(4500);
            return false;
        }
        //dynamic event manager change//
        //if (CFG.DYNAMIC_EVENTS) {
        //    //if already scanning for civil war
        //    if (CFG.dynamicEventManager.eventManagerCivilWar.inCivilWar(nCivID)) return false;
        //    CFG.dynamicEventManager.eventManagerCivilWar.changesGovernment(nCivID);
        //}


        CFG.game.getCiv(iLordCivID).setMoney(CFG.game.getCiv(iLordCivID).getMoney() - CFG.gameAutonomy.changeAutonomyCost(nCivID));
        CFG.game.getCiv(iLordCivID).setDiplomacyPoints(CFG.game.getCiv(iLordCivID).getDiplomacyPoints() - CFG.gameAutonomy.lAutonomy.get(toAuType).getDiploCost());

        CFG.game.getCiv(iLordCivID).removeVassal(nCivID);
        CFG.game.getCiv(nCivID).setPuppetOfCivID(iLordCivID, toAuType);

        //CFG.game.updateCivilizationIdeology(nCivID, CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(toGovType).getExtraTag());
        for (int i = 0; i < CFG.game.getCiv(nCivID).getCivRegionsSize(); ++i) {
            CFG.game.getCiv(nCivID).getCivRegion(i).buildScaleOfText();
        }
        return true;
    }

    protected static ArrayList<String> getTradeRequest_Tooltips() {
        ArrayList<String> iTitles = new ArrayList<String>();
        iTitles.add(CFG.langManager.get("Weight"));
        iTitles.add(CFG.langManager.get("CivRelationFactor"));
        iTitles.add(CFG.langManager.get("WarFriendlyWeight"));
        iTitles.add(CFG.langManager.get("WarCivilizationWeight"));
        iTitles.add(CFG.langManager.get("TradeWarCivilizationWeight"));
        iTitles.add(CFG.langManager.get("ProvinceWeight"));
        iTitles.add(CFG.langManager.get("GoldWeight"));

        return iTitles;
    }

    protected static ArrayList<Float> calculateTradeRequestWeights(TradeRequest_GameData tradeRequest) {
        int nCivID = tradeRequest.iCivRIGHT;
        int iTraderCivID = tradeRequest.iCivLEFT;
        float iWeight = 0.0F;

        float iCivRelationFactor = 0.0F;
        iCivRelationFactor = -CFG.game.getCivRelation_OfCivB(nCivID, iTraderCivID);
        //adjust based on if vassal or if alliance to be more likely
        if (CFG.game.getCiv(nCivID).getPuppetOfCivID() == iTraderCivID) {
            iCivRelationFactor -= 35.0F;
        } else if (CFG.game.getCiv(iTraderCivID).getPuppetOfCivID() == nCivID) {
            iCivRelationFactor -= 20.0F;
        } else if (CFG.game.getCiv(iTraderCivID).getAllianceID() > 0 && CFG.game.getCiv(iTraderCivID).getAllianceID() == CFG.game.getCiv(nCivID).getAllianceID()) {
            iCivRelationFactor -= 30.0F;
        }

        //random chance
        //do int btwn weight and upper bound (100), therefore if low, low prob of acceptance

        //limit trade of a civ to once per (few) turn(s)?

        //sort through wars of trader, if at war with friendly, reduce chance by scale of half relation btwn ai civ and friendly
        //if AI is a puppet and civ is at war with puppet, detect however much from liberty desire
        //get total of all war civs and deduct from weight
        float iWarFriendlyWeight = 0.0F;
        for (int z3 = 0; z3 < CFG.game.getCiv(iTraderCivID).isAtWarWithCivs.size(); ++z3) {
            if (CFG.game.getCiv(nCivID).isFriendlyCiv(CFG.game.getCiv(iTraderCivID).isAtWarWithCivs.get(z3)) >= 0) {
                iWarFriendlyWeight += (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(iTraderCivID).isAtWarWithCivs.get(z3))/2.0F);
            } else if (CFG.game.getCiv(nCivID).getPuppetOfCivID() == CFG.game.getCiv(iTraderCivID).isAtWarWithCivs.get(z3)) {
                iWarFriendlyWeight += 100.0F - CFG.game.getCiv(nCivID).getVassalLiberityDesire();
            }
        }
        //iWeight = Math.min(Math.max((iWeight - iWarFriendlyWeight), -200.0F), 200.0F);

        //if requesting potential war-related actions that may draw AI into war, add weight based on mil size, if at war add even more weight
        float iWarCivilizationWeight = 0.0F;
        if (tradeRequest.listRight.iDeclarWarOnCivID > 0 ||
            tradeRequest.listRight.militaryAccess ||
            tradeRequest.listLEFT.militaryAccess ||
            tradeRequest.listRight.proclaimIndependence ||
            tradeRequest.listRight.defensivePact ||
            tradeRequest.listRight.iFormCoalitionAgainst > 0)
        {
            //if at war add more penalties
            if (CFG.game.getCiv(nCivID).isAtWar()) {
                //however if giving military access decrease weight slightly
                if (tradeRequest.listLEFT.militaryAccess) {
                    iWarCivilizationWeight -= 40.0F;
                }
                iWarCivilizationWeight += 20.0F;
            }

            //if not requesting non-coop mil service (declare war) subtract rank of trader (how good their military is), else add weight of civ to be fighting
            if (tradeRequest.listLEFT.militaryAccess || tradeRequest.listRight.proclaimIndependence || tradeRequest.listRight.defensivePact) {
                //maximum of prestige rank, between 35 and -15
                iWarCivilizationWeight -= (float)Math.min(Math.max(CFG.gameAction.buildRank_Score_Prestige(iTraderCivID), -15), 35);
            }
            if (tradeRequest.listRight.iDeclarWarOnCivID > 0) {
                //add more weight based on if civ's military is worse
                iWarCivilizationWeight -= ((float)CFG.gameAction.buildRank_Score_Prestige(nCivID) * 2.0F);

                //minimum of prestige rank and 10, and also factor in civ relation to war
                iWarCivilizationWeight += (float)Math.max(CFG.gameAction.buildRank_Score_Prestige(tradeRequest.listRight.iDeclarWarOnCivID) * 3.0F, 10);
                iWarCivilizationWeight += (float)CFG.game.getCivRelation_OfCivB(nCivID, tradeRequest.listRight.iDeclarWarOnCivID);
            }
            if (tradeRequest.listRight.iFormCoalitionAgainst > 0) {
                //add more weight based on if civ's military is worse
                iWarCivilizationWeight -= ((float)CFG.gameAction.buildRank_Score_Prestige(nCivID) * 2.0F);

                //minimum of prestige rank and 10, and also factor in civ relation to war
                iWarCivilizationWeight += (float)Math.max(CFG.gameAction.buildRank_Score_Prestige(tradeRequest.listRight.iFormCoalitionAgainst) * 3.0F, 10);
                iWarCivilizationWeight += (float)CFG.game.getCivRelation_OfCivB(nCivID, tradeRequest.listRight.iFormCoalitionAgainst);
            }
        }
        //iWeight = Math.min(Math.max((iWeight - iWarCivilizationWeight), -200.0F), 200.0F);

        float iTradeWarCivilizationWeight = 0.0F;
        //if trader is offering military services, check if at war with civ or if enemy
        if (tradeRequest.listLEFT.iDeclarWarOnCivID > 0) {
            int oCivID = tradeRequest.listLEFT.iDeclarWarOnCivID;
            //if at war add large boost, but if only rival add slightly smaller
            if (CFG.game.getCivsAtWar(nCivID, oCivID)) {
                iTradeWarCivilizationWeight -= 30.0F;
            } else if (CFG.game.getCiv(nCivID).isHatedCiv(oCivID)) {
                iTradeWarCivilizationWeight -= 20.0F;
            }
        }
        if (tradeRequest.listLEFT.iFormCoalitionAgainst > 0) {
            int oCivID = tradeRequest.listLEFT.iFormCoalitionAgainst;
            //if at war add large boost, but if only rival add slightly smaller
            if (CFG.game.getCivsAtWar(nCivID, oCivID)) {
                iTradeWarCivilizationWeight -= 30.0F;
            } else if (CFG.game.getCiv(nCivID).isHatedCiv(oCivID)) {
                iTradeWarCivilizationWeight -= 20.0F;
            }
        }
        //iWeight = Math.min(Math.max((iWeight - iTradeWarCivilizationWeight), -200.0F), 200.0F);

        //if provinces requested, add weights
        float iProvinceWeight = 0.0F;
        if (tradeRequest.listRight.lProvinces.size() > 0) {
            //initial weight based on sheer number of provinces requested
            iProvinceWeight += ((float)tradeRequest.listRight.lProvinces.size() / (float)CFG.game.getCiv(nCivID).getNumOfProvinces()) * 110.0F;

            int totalCost = 0;
            for (int z3 = 0; z3 < tradeRequest.listRight.lProvinces.size(); ++z3) {
                //secondary weight based on province income
                totalCost += (int)(CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(z3)).iIncome_Taxation + CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(z3)).iIncome_Production * 2.0f);

                //iProvinceWeight += (CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(z3)).getDevelopmentLevel() / 10.0F);
                if (CFG.game.getProvince(tradeRequest.listRight.lProvinces.get(z3)).getCore().getHaveACore(nCivID)) {
                    //secondary weight based on core status of province
                    iProvinceWeight += 10.0F;
                }
            }
            //divided by AI size and 5 scale
            totalCost /= (tradeRequest.listRight.lProvinces.size() + 5);
            iProvinceWeight += totalCost;
        }
        if (CFG.tradeRequest.listLEFT.lProvinces.size() > 0) {
            //initial weight based on sheer number of provinces requested
            iProvinceWeight -= ((float)tradeRequest.listLEFT.lProvinces.size() / (float)CFG.game.getCiv(iTraderCivID).getNumOfProvinces()) * 100.0F;

            int totalCost = 0;
            for (int z3 = 0; z3 < tradeRequest.listLEFT.lProvinces.size(); ++z3) {
                //secondary weight based on province income
                totalCost += (int)(CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(z3)).iIncome_Taxation + CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(z3)).iIncome_Production * 2.0f);

                if (CFG.game.getProvince(tradeRequest.listLEFT.lProvinces.get(z3)).getCore().getHaveACore(nCivID)) {
                    //secondary weight based on core status of province
                    iProvinceWeight -= 10.0F;
                }
            }
            //divided by AI size and 6 scale (little more value needed)
            totalCost /= (tradeRequest.listRight.lProvinces.size() + 6);
            iProvinceWeight -= totalCost;
        }
        //iWeight = Math.min(Math.max((iWeight - iProvinceWeight), -200.0F), 200.0F);


        //if giving gold remove weight
        float iGoldWeight = 0.0F;
        if (tradeRequest.listLEFT.iGold > 0) {
            //influence weight by gold divided by half of AI's budget (as gold approaches budget, likelihood increases)
            iGoldWeight -= ((float)tradeRequest.listLEFT.iGold / ((float)CFG.game.getCiv(nCivID).iBudget/20.0F));
        }
        //if requesting gold add weight
        if (tradeRequest.listRight.iGold > 0) {
            //influence weight by gold divided by half of AI's budget (as gold approaches budget, likelihood increases)
            iGoldWeight += ((float)tradeRequest.listRight.iGold / ((float)CFG.game.getCiv(nCivID).iBudget/30.0F));
        }
        //iWeight = Math.min(Math.max((iWeight - iGoldWeight), -200.0F), 200.0F);

        iWeight = iWeight - iWarFriendlyWeight - iCivRelationFactor - iWarCivilizationWeight - iTradeWarCivilizationWeight - iProvinceWeight - iGoldWeight;

        //Gdx.app.log("AoC2.5 iWarFriendlyWeight", String.valueOf(iWarFriendlyWeight));
        //Gdx.app.log("AoC2.5 iCivRelationFactor", String.valueOf(iCivRelationFactor));
        //Gdx.app.log("AoC2.5 iWarCivilizationWeight", String.valueOf(iWarCivilizationWeight));
        //Gdx.app.log("AoC2.5 iTradeWarCivilizationWeight", String.valueOf(iTradeWarCivilizationWeight));
        //Gdx.app.log("AoC2.5 iProvinceWeight", String.valueOf(iProvinceWeight));
        //Gdx.app.log("AoC2.5 iGoldWeight", String.valueOf(iGoldWeight));
        Gdx.app.log("AoC2.5 Trade Weight", String.valueOf(iWeight));

        ArrayList<Float> toReturn = new ArrayList<>();
        toReturn.add(iWeight);
        toReturn.add(-iCivRelationFactor);
        toReturn.add(-iWarFriendlyWeight);
        toReturn.add(-iWarCivilizationWeight);
        toReturn.add(-iTradeWarCivilizationWeight);
        toReturn.add(-iProvinceWeight);
        toReturn.add(-iGoldWeight);

        return toReturn;
    }

    static {
        DiplomacyManager.WAR_PREPARATIONS_REFUSE_OPINION_CHANGE = -10;
        DiplomacyManager.RELEASED_VASSAL_MIN_OPINION = 25;
    }
}
