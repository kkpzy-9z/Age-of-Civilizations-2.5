package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import java.util.*;

class AI_Style
{
   protected static final int MIN_ARMY_TO_ATTACK = 10;
   protected String TAG;
   protected float PERSONALITY_MIN_MILITARY_SPENDINGS_DEFAULT;
   protected int PERSONALITY_MIN_MILITARY_SPENDINGS_RANDOM;
   protected float PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_DEFAULT;
   protected int PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_RANDOM;
   protected int PERSONALITY_MIN_HAPPINESS_DEFAULT;
   protected int PERSONALITY_MIN_HAPPINESS_RANDOM;
   protected float PERSONALITY_FORGIVNESS_DEFAULT;
   protected int PERSONALITY_FORGIVNESS_RANDOM;
   protected int USE_OF_BUDGET_FOR_SPENDINGS;
   protected int USE_OF_BUDGET_FOR_SPENDINGS_RANDOM;
   protected int PERSONALITY_GOODS_RANDOM;
   protected int PERSONALITY_INVESTMENTS_RANDOM;
   protected int PERSONALITY_RESEARCH_RANDOM;
   protected int PERSONALITY_PLUNDER_MIN;
   protected int PERSONALITY_PLUNDER_RANDOM;
   protected int PERSONALITY_PLUNDER_LOCK;
   protected float PERSONALITY_MIN_AGGRESION_DEFAULT;
   protected int PERSONALITY_MIN_AGGRESION_RANDOM;
   protected boolean armyOverBudget;
   protected int RIVAL_MIN_TURNS;
   protected int MIN_TURNS_TO_ABANDON_USELESS_PROVINCE;

   protected AI_Style() {
      super();
      this.PERSONALITY_MIN_MILITARY_SPENDINGS_DEFAULT = 0.16f;
      this.PERSONALITY_MIN_MILITARY_SPENDINGS_RANDOM = 20;
      this.PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_DEFAULT = 0.6f;
      this.PERSONALITY_MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY_RANDOM = 35;
      this.PERSONALITY_MIN_HAPPINESS_DEFAULT = 69;
      this.PERSONALITY_MIN_HAPPINESS_RANDOM = 24;
      this.PERSONALITY_FORGIVNESS_DEFAULT = 1.0f;
      this.PERSONALITY_FORGIVNESS_RANDOM = 50;
      this.USE_OF_BUDGET_FOR_SPENDINGS = 35;
      this.USE_OF_BUDGET_FOR_SPENDINGS_RANDOM = 65;
      this.PERSONALITY_GOODS_RANDOM = 100;
      this.PERSONALITY_INVESTMENTS_RANDOM = 100;
      this.PERSONALITY_RESEARCH_RANDOM = 100;
      this.PERSONALITY_PLUNDER_MIN = 0;
      this.PERSONALITY_PLUNDER_RANDOM = 45;
      this.PERSONALITY_PLUNDER_LOCK = 78;
      this.PERSONALITY_MIN_AGGRESION_DEFAULT = 0.2475f;
      this.PERSONALITY_MIN_AGGRESION_RANDOM = 4825;
      this.armyOverBudget = false;
      this.RIVAL_MIN_TURNS = 34;
      this.MIN_TURNS_TO_ABANDON_USELESS_PROVINCE = 25;
      this.TAG = "DEFAULT";
   }

   protected float getMinMilitarySpendings(final int nCivID) {
      return CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS;
   }

   protected void turnOrders(final int nCivID) {
      Gdx.app.log(this.TAG, "BEGIN, " + CFG.game.getCiv(nCivID).getCivName());
      this.armyOverBudget = false;
      this.relocateLostCapital(nCivID);
      this.changeTypeOfIdeology(nCivID);
      this.checkPeaceTreaties(nCivID);

      //if not puppet or puppet w mil control, adjust military
      if ((!CFG.game.getCiv(nCivID).getIsPupet()) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isMilitaryControl()) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize > 0) {
            CFG.game.getCiv(nCivID).civGameData.civPlans.checkWarPreparations(nCivID);
         }
         try {
            if (CFG.game.getCiv(nCivID).isAtWar()) {
               this.defendFromSeaInvasion(nCivID);
               this.moveAtWar(nCivID);
               this.armyOverBudget = true;
            }
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
               this.prepareForWar2(nCivID);
            }
         } catch (final IndexOutOfBoundsException | ArithmeticException | NullPointerException ex) {
            CFG.exceptionStack(ex);
         }
         CFG.oAI.expandNeutral.expandToNeutralProvinces(nCivID);
         Gdx.app.log("AoC", "MILITARY UPKEEP CHECK: " + (0.96f - CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC - CFG.game.getCiv(nCivID).getSpendings_Goods() - CFG.game.getCiv(nCivID).getSpendings_Investments()));
         if (this.getMinMilitarySpendings(nCivID) + 0.025f < CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC) {
            Gdx.app.log("AoC", "MILITARY UPKEEP CHECK: we need to disband");
            this.armyOverBudget_Disband(nCivID);
            this.armyOverBudget = true;
         }

         //if not puppet or puppet w eco control, adjust economy
         if ((!CFG.game.getCiv(nCivID).getIsPupet()) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isEconomicControl()) {
            if (CFG.game.getCiv(nCivID).getHappiness() < CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_CRISIS) {
               this.happinessCrisis(nCivID);
            } else if (!CFG.game.getCiv(nCivID).isAtWar()) {
               if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0 && CFG.game.getCiv(nCivID).getTaxationLevel() <= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION && CFG.game.getCiv(nCivID).getSpendings_Goods() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)) {
                  this.hostFestivals(nCivID, CFG.game.getCiv(nCivID).getNumOfProvinces());
               }
            } else if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0 && CFG.game.getCiv(nCivID).getTaxationLevel() <= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION && CFG.game.getCiv(nCivID).getSpendings_Goods() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)) {
               this.hostFestivals(nCivID, 1 + CFG.oR.nextInt(3));
            }
            if (CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() > 0) {
               this.assimilateProvinces(nCivID);
            }
         }

         if ((!this.armyOverBudget || CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) && CFG.game.getCiv(nCivID).getMoney() > 0L && this.getMinMilitarySpendings(nCivID) > CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC + 0.0275f) {
            this.recruitMilitary_MinSpendings(nCivID);
         }
         if (!this.armyOverBudget) {
            this.colonizeProvinces(nCivID);
         }
         if (!CFG.game.getCiv(nCivID).isAtWar() && !CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
            this.regroupArmy_AtPeace(nCivID);
         }
         this.regroupArmyAfterRecruitment(nCivID);
         if (CFG.game.getCiv(nCivID).lProvincesWithHighRevRisk.size() > 0) {
            this.prepareArmyForRevolution(nCivID);
         }
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
            this.prepareForWar_MoveReadyArmies(nCivID);
            for (int i = CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
               if (CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).iNumOfTurnsLeft-- <= 0) {
                  final int tOnCivID = CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).onCivID;
                  CFG.game.declareWar(nCivID, CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).onCivID, false);
                  for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
                     if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.PREAPARE_FOR_WAR && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_ID == tOnCivID) {
                        CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
                     }
                  }
                  try {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.remove(i);
                     CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.size();
                  }
                  catch (final IndexOutOfBoundsException ex4) {}
               }
            }
         }
         if (CFG.game.getCiv(nCivID).getMovePoints() >= 10 && CFG.game.getCiv(nCivID).getMoney() > 0L) {
            this.buildBuildings(nCivID);
         }
         Gdx.app.log(this.TAG, "AI -> turnOrders -> END, " + CFG.game.getCiv(nCivID).getCivName());
         CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn = 0;
         return;
      } else {
         //if puppet w eco control, just adjust economy
         if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isEconomicControl()) {
            if (CFG.game.getCiv(nCivID).getHappiness() < CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_CRISIS) {
               this.happinessCrisis(nCivID);
            } else if (!CFG.game.getCiv(nCivID).isAtWar()) {
               if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0 && CFG.game.getCiv(nCivID).getTaxationLevel() <= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION && CFG.game.getCiv(nCivID).getSpendings_Goods() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)) {
                  this.hostFestivals(nCivID, CFG.game.getCiv(nCivID).getNumOfProvinces());
               }
            } else if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0 && CFG.game.getCiv(nCivID).getTaxationLevel() <= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION && CFG.game.getCiv(nCivID).getSpendings_Goods() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)) {
               this.hostFestivals(nCivID, 1 + CFG.oR.nextInt(3));
            }
            if (CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() > 0) {
               this.assimilateProvinces(nCivID);
            }
         }
         if (CFG.game.getCiv(nCivID).getMovePoints() >= 10 && CFG.game.getCiv(nCivID).getMoney() > 0L) {
            this.buildBuildings(nCivID);
         }
         return;
      }

      //if ((!this.armyOverBudget || CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) && CFG.game.getCiv(nCivID).getMoney() > 0L && this.getMinMilitarySpendings(nCivID) > CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC + 0.0275f) {
      //   this.recruitMilitary_MinSpendings(nCivID);
      //}
      //if (!this.armyOverBudget) {
      //   this.colonizeProvinces(nCivID);
      //}
      //if (!CFG.game.getCiv(nCivID).isAtWar() && !CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
      //   this.regroupArmy_AtPeace(nCivID);
      //}
      //this.regroupArmyAfterRecruitment(nCivID);
      //if (CFG.game.getCiv(nCivID).lProvincesWithHighRevRisk.size() > 0) {
      //   this.prepareArmyForRevolution(nCivID);
      //}
      //if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
      //   this.prepareForWar_MoveReadyArmies(nCivID);
      //   for (int i = CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize - 1; i >= 0; --i) {
      //      if (CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).iNumOfTurnsLeft-- <= 0) {
      //         final int tOnCivID = CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).onCivID;
      //         CFG.game.declareWar(nCivID, CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(i).onCivID, false);
      //         for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
      //            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.PREAPARE_FOR_WAR && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_ID == tOnCivID) {
      //               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
      //            }
      //         }
      //         try {
      //            CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.remove(i);
      //            CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize = CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.size();
      //         }
      //         catch (final IndexOutOfBoundsException ex4) {}
      //      }
      //   }
      //}
      //if (CFG.game.getCiv(nCivID).getMovePoints() >= 10 && CFG.game.getCiv(nCivID).getMoney() > 0L) {
      //   this.buildBuildings(nCivID);
      //}
      //Gdx.app.log(this.TAG, "AI -> turnOrders -> END, " + CFG.game.getCiv(nCivID).getCivName());
      //CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn = 0;
   }

   protected final void turnOrdersEssential(final int nCivID) {
      this.respondToEvents(nCivID);
      this.updateSentMessages(nCivID);
      this.respondToMessages(nCivID);
      if ((!CFG.game.getCiv(nCivID).getIsPupet()) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isEconomicControl()) this.diplomacyActions(nCivID);
      if ((!CFG.game.getCiv(nCivID).getIsPupet()) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isEconomicControl()) this.manageBudget(nCivID);
      this.updateLiberityDesire(nCivID);
   }

   protected void diplomacyActions(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getNumOfProvinces() > 0) {
         Gdx.app.log("AoC", "diplomacyActions - > " + CFG.game.getCiv(nCivID).getCivName());
         this.diplomacyActions_BuildCivsInRange(nCivID);
         if (!CFG.game.getCiv(nCivID).isAtWar() && !CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
            this.diplomacyActions_RivalCiv(nCivID);
            this.diplomacyActions_FormCiv(nCivID);
            this.diplomacyActions_CircledVassals(nCivID);
         }
         this.diplomacyActions_FriendCiv(nCivID);
         if (!CFG.game.getCiv(nCivID).isAtWar() && !CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
            this.diplomacyActions_DeclareWar(nCivID);
         }
         this.diplomacyActions_Ally(nCivID);
         Gdx.app.log("AoC", "diplomacyActions - > END");
      }
   }

   protected final void diplomacyActions_FormCiv(final int nCivID) {
      if (Game_Calendar.TURN_ID >= CFG.game.getCiv(nCivID).civGameData.checkFormCiv_TurnID) {
         if (CFG.game.getCiv(nCivID).getTagsCanFormSize() > 0) {
            for (int i = 0; i < CFG.game.getCiv(nCivID).getTagsCanFormSize(); ++i) {
               if (CFG.canFormACiv(nCivID, CFG.game.getCiv(nCivID).getTagsCanForm(i), true)) {
                  CFG.loadFormableCiv_GameData(CFG.game.getCiv(nCivID).getTagsCanForm(i));
                  CFG.formCiv(nCivID);
               }
            }
            CFG.game.getCiv(nCivID).civGameData.checkFormCiv_TurnID = Game_Calendar.TURN_ID + 40 + CFG.oR.nextInt(60);
         }
         CFG.game.getCiv(nCivID).civGameData.checkFormCiv_TurnID = Game_Calendar.TURN_ID + 500;
      }
   }

   protected final void diplomacyActions_CircledVassals(final int nCivID) {
      try {
         if (CFG.game.getCiv(nCivID).civGameData.circledVassals_TurnID <= Game_Calendar.TURN_ID) {
            if (CFG.game.getCiv(nCivID).civGameData.iVassalsSize > 0) {
               for (int z = 0; z < CFG.game.getCiv(nCivID).civGameData.iVassalsSize; ++z) {
                  if (CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID - 1).size() == 0 && CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID).getSeaAccess() <= 0) {
                     if (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID) > -10.0f) {
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, 10);
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, 10);
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, 10);
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, 10);
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, 10);
                     }
                     final Ultimatum_GameData nUltimatum = new Ultimatum_GameData();
                     nUltimatum.demandAnexation = true;
                     DiplomacyManager.sendUltimatum(CFG.game.getCiv(nCivID).civGameData.lVassals.get(z).iCivID, nCivID, nUltimatum, CFG.game.getCiv(nCivID).getNumOfUnits());
                  }
               }
            }
            CFG.game.getCiv(nCivID).civGameData.circledVassals_TurnID = Game_Calendar.TURN_ID + 30 + CFG.oR.nextInt(25);
         }
      } catch (IndexOutOfBoundsException e) {
      }
   }

   protected final void diplomacyActions_DeclareWar(final int nCivID) {
      Gdx.app.log("AoC", "diplomacyActions_DeclareWar - > nCivID: " + CFG.game.getCiv(nCivID).getCivName() + ", declareWar_CheckNextTurnID: " + CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID);
      if (CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID <= Game_Calendar.TURN_ID) {
         if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
            CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + (int)((12 + CFG.oR.nextInt(35) + CFG.oR.nextInt(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME + 1) / 30) / Game_Calendar.AI_AGGRESSIVNESS);
         }
         else if (((CFG.game.getCiv(nCivID).getPuppetOfCivID() == nCivID) ? CFG.game.getCiv(nCivID).civGameData.civPersonality.AGGRESSION : (CFG.game.getCiv(nCivID).civGameData.civPersonality.AGGRESSION / 8.0f)) * Game_Calendar.AI_AGGRESSIVNESS >= CFG.oR.nextInt(10000) / 100.0f) {
            final List<Integer> possibleCivs = new ArrayList<Integer>();
            if (CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() > 0) {
               CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + 8 + (int)((CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() * 2 + CFG.oR.nextInt(14) + CFG.oR.nextInt(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME + 1) / 25) / Game_Calendar.AI_AGGRESSIVNESS);
               return;
            }
            if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0) {
               CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + 8 + (int)((CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() * 2 + CFG.oR.nextInt(14) + CFG.oR.nextInt(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME + 1) / 25) / Game_Calendar.AI_AGGRESSIVNESS);
               return;
            }
            for (int i = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; i >= 0; --i) {
               boolean wasAdded = false;
               for (int o = possibleCivs.size() - 1; o >= 0; --o) {
                  if (possibleCivs.get(o) == CFG.oAI.lFrontLines.get(nCivID - 1).get(i).iWithCivID) {
                     wasAdded = true;
                  }
               }
               if (!wasAdded) {
                  possibleCivs.add(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).iWithCivID);
               }
            }
            for (int i = 0; i < CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size(); ++i) {
               for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvincesSize(); ++j) {
                  for (int k = 0; k < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvincesSize(); ++k) {
                     if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getSeaProvince()) {
                        if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCivID() > 0) {
                           boolean wasAdded2 = false;
                           for (int o2 = possibleCivs.size() - 1; o2 >= 0; --o2) {
                              if (possibleCivs.get(o2) == CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCivID()) {
                                 wasAdded2 = true;
                              }
                           }
                           if (!wasAdded2) {
                              possibleCivs.add(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCivID());
                           }
                        }
                     }
                     else {
                        for (int z = 0; z < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvincesSize(); ++z) {
                           if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvinces(z)).getCivID() > 0) {
                              boolean wasAdded3 = false;
                              for (int o3 = possibleCivs.size() - 1; o3 >= 0; --o3) {
                                 if (possibleCivs.get(o3) == CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvinces(z)).getCivID()) {
                                    wasAdded3 = true;
                                 }
                              }
                              if (!wasAdded3) {
                                 possibleCivs.add(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvinces(z)).getCivID());
                              }
                           }
                        }
                     }
                  }
               }
            }
            if ((possibleCivs.size() == 0 || CFG.oR.nextInt(100) < 6) && CFG.game.getCiv(nCivID).getSeaAccess_PortProvinces_Size() > 0 && CFG.game.getCiv(nCivID).getNumOfProvinces() > 4) {
               for (int i = CFG.game.getCiv(nCivID).civGameData.civsInRange.size() - 1; i >= 0; --i) {
                  possibleCivs.add(CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID);
               }
            }
            if (possibleCivs.size() < 0) {
               for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.civRivalsSize; ++i) {
                  boolean wasAdded = false;
                  for (int o = possibleCivs.size() - 1; o >= 0; --o) {
                     if (possibleCivs.get(o) == CFG.game.getCiv(nCivID).civGameData.civRivals.get(i).iCivID) {
                        wasAdded = true;
                     }
                  }
                  if (!wasAdded) {
                     possibleCivs.add(CFG.game.getCiv(nCivID).civGameData.civRivals.get(i).iCivID);
                  }
               }
            }
            for (int i = possibleCivs.size() - 1; i >= 0; --i) {
               if (CFG.game.getCiv(possibleCivs.get(i)).getPuppetOfCivID() != possibleCivs.get(i)) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.getCiv(nCivID).isFriendlyCiv(possibleCivs.get(i)) >= 0) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.isAlly(nCivID, possibleCivs.get(i))) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.getGuarantee(nCivID, possibleCivs.get(i)) > 0 || CFG.game.getGuarantee(possibleCivs.get(i), nCivID) > 0) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.getCivNonAggressionPact(nCivID, possibleCivs.get(i)) > 0) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.getCivTruce(nCivID, possibleCivs.get(i)) > 0) {
                  possibleCivs.remove(i);
               }
               else if (CFG.game.getCivRelation_OfCivB(nCivID, possibleCivs.get(i)) > ((CFG.oAI.NUM_OF_CIVS_IN_THE_GAME < 10) ? 10.0f : Math.max(-50.0f, -50.0f / Game_Calendar.AI_AGGRESSIVNESS))) {
                  possibleCivs.remove(i);
               }
            }
            if (possibleCivs.size() > 0) {
               boolean done = false;
               if (CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.size() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED < 0) {
                  final List<Integer> tribalPossible = new ArrayList<Integer>();
                  for (int z2 = 0; z2 < possibleCivs.size(); ++z2) {
                     if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(possibleCivs.get(z2)).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
                        tribalPossible.add(possibleCivs.get(z2));
                     }
                  }
                  if (tribalPossible.size() > 0) {
                     CFG.game.declareWar(nCivID, tribalPossible.get(CFG.oR.nextInt(tribalPossible.size())), false);
                     done = true;
                  }
               }
               if (!done) {
                  final List<Float> lScores = new ArrayList<Float>();
                  final float modifier_Relation = 1.275f + CFG.oR.nextInt(975) / 1000.0f;
                  final float modifier_Budget = 0.125f + CFG.oR.nextInt(1755) / 1000.0f;
                  final float modifier_CivsSize = 0.625f;
                  for (int z3 = 0; z3 < possibleCivs.size(); ++z3) {
                     lScores.add(this.diplomacyActions_DeclareWar_Score(nCivID, possibleCivs.get(z3), modifier_Budget, modifier_CivsSize, modifier_Relation));
                  }
                  int tBest = 0;
                  for (int z4 = 1; z4 < possibleCivs.size(); ++z4) {
                     if (lScores.get(tBest) < lScores.get(z4)) {
                        tBest = z4;
                     }
                  }
                  int ownBudget = this.diplomacyActions_DeclareWar_Budgets(nCivID, false);
                  final int theirBudget = this.diplomacyActions_DeclareWar_Budgets(possibleCivs.get(tBest), true);
                  if (ownBudget > theirBudget * 0.695f) {
                     final int turns = 3 + CFG.oR.nextInt(4);
                     CFG.game.getCiv(nCivID).civGameData.civPlans.addNewWarPreparations(nCivID, nCivID, possibleCivs.get(tBest), turns);
                     final List<Integer> toCall = DiplomacyManager.callToArmsListOfCivs(nCivID, possibleCivs.get(tBest));
                     for (int z5 = 0; z5 < toCall.size(); ++z5) {
                        DiplomacyManager.sendPrepareForWar(toCall.get(z5), nCivID, possibleCivs.get(tBest), turns, nCivID);
                     }
                  }
                  else {
                     final List<Integer> possibleToJoin = new ArrayList<Integer>();
                     for (int a = 0; a < CFG.game.getCiv(possibleCivs.get(tBest)).getHatedCivs_BySize(); ++a) {
                        if (!CFG.game.getCiv(nCivID).isHatedCiv(CFG.game.getCiv(possibleCivs.get(tBest)).getHatedCiv_By(a)) && CFG.game.getCiv(CFG.game.getCiv(possibleCivs.get(tBest)).getHatedCiv_By(a)).getNumOfProvinces() > 0) {
                           possibleToJoin.add(CFG.game.getCiv(possibleCivs.get(tBest)).getHatedCiv_By(a));
                        }
                     }
                     for (int z6 = 0; z6 < possibleToJoin.size(); ++z6) {
                        ownBudget += this.diplomacyActions_DeclareWar_Budgets(possibleToJoin.get(z6), false);
                     }
                     if (ownBudget > theirBudget * 0.605f) {
                        for (int z6 = 0; z6 < possibleToJoin.size(); ++z6) {
                           if (possibleCivs.get(tBest) != possibleToJoin.get(z6)) {
                              if (possibleToJoin.get(z6) == nCivID) continue; //if possible coalition member is AI/self, skip, bugfix change//

                              final TradeRequest_GameData tradeRequest = new TradeRequest_GameData();
                              tradeRequest.iCivLEFT = nCivID;
                              tradeRequest.iCivRIGHT = possibleToJoin.get(z6);
                              tradeRequest.listRight.iFormCoalitionAgainst = possibleCivs.get(tBest);
                              tradeRequest.listLEFT.iGold = 10 + CFG.oR.nextInt(50);
                              DiplomacyManager.sendTradeRequest(possibleToJoin.get(z6), nCivID, tradeRequest);
                           }
                        }
                        final int turns2 = 3 + CFG.oR.nextInt(4);
                        CFG.game.getCiv(nCivID).civGameData.civPlans.addNewWarPreparations(nCivID, nCivID, possibleCivs.get(tBest), turns2);
                        final List<Integer> toCall2 = DiplomacyManager.callToArmsListOfCivs(nCivID, possibleCivs.get(tBest));
                        for (int z7 = 0; z7 < toCall2.size(); ++z7) {
                           DiplomacyManager.sendPrepareForWar(toCall2.get(z7), nCivID, possibleCivs.get(tBest), turns2, nCivID);
                        }
                     }
                     else {
                        DiplomacyManager.sendNonAggressionProposal(possibleCivs.get(tBest), nCivID, 40);
                     }
                  }
               }
               CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + (int)((10 + CFG.game.getCiv(possibleCivs.get(CFG.oR.nextInt(possibleCivs.size()))).getNumOfProvinces() + CFG.oR.nextInt(25) + CFG.oR.nextInt((int)(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME * 1.025f) + 1) / 25) / Game_Calendar.AI_AGGRESSIVNESS);
            }
            else {
               CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + (int)((10 + CFG.oR.nextInt(14) + CFG.oR.nextInt(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME + 1) / 20) / Game_Calendar.AI_AGGRESSIVNESS);
            }
         }
         else {
            CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Game_Calendar.TURN_ID + (int)((8 + CFG.oR.nextInt(14) + CFG.oR.nextInt(CFG.oAI.NUM_OF_CIVS_IN_THE_GAME + 1) / 30) / Game_Calendar.AI_AGGRESSIVNESS);
         }
      }
   }

   protected final float diplomacyActions_DeclareWar_Score(final int nCivID, final int onCivID, final float modifier_Budget, final float modifier_CivsSize, final float modifier_Relation) {
      return modifier_Budget * (1.0f - Math.min(CFG.game.getCiv(onCivID).iBudget / (float)CFG.game.getCiv(nCivID).iBudget, 0.95f)) + modifier_Relation * (1.0f + CFG.game.getCiv(onCivID).civGameData.civAggresionLevel / 4.0f) * (1.0f - Math.min(CFG.game.getCivRelation_OfCivB(nCivID, onCivID) + 100.0f, 200.0f) / 200.0f);
   }

   protected final int diplomacyActions_DeclareWar_Budgets(final int nCivID, final boolean defensivePacts) {
      int out = CFG.game.getCiv(nCivID).iBudget;
      if (CFG.game.getCiv(nCivID).getPuppetOfCivID() != nCivID) {
         out += CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).iBudget;
      }
      for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.iVassalsSize; ++i) {
         out += CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID).iBudget;
      }
      if (CFG.game.getCiv(nCivID).getAllianceID() > 0) {
         for (int i = 0; i < CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize(); ++i) {
            if (nCivID != CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(i) && nCivID != CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(i)).getPuppetOfCivID() && CFG.game.getCiv(nCivID).getPuppetOfCivID() != CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(i)) {
               out += CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(i)).iBudget;
            }
         }
      }
      try {
         if (defensivePacts) {
            for (int i = 1; i < nCivID; ++i) {
               if (CFG.game.getDefensivePact(nCivID, i) > 0) {
                  if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                     out += CFG.game.getCiv(i).iBudget;
                  }
               }
               else if (CFG.game.getGuarantee(i, nCivID) > 0 && CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                  out += CFG.game.getCiv(i).iBudget;
               }
            }
            for (int i = nCivID; i < CFG.game.getCivsSize(); ++i) {
               if (CFG.game.getDefensivePact(nCivID, i) > 0) {
                  if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                     out += CFG.game.getCiv(i).iBudget;
                  }
               }
               else if (CFG.game.getGuarantee(nCivID, i) > 0 && CFG.game.getCiv(i).getNumOfProvinces() > 0) {
                  out += CFG.game.getCiv(i).iBudget;
               }
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {}
      return out;
   }

   protected final void diplomacyActions_BuildCivsInRange(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && Game_Calendar.TURN_ID >= CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID) {
         CFG.game.getCiv(nCivID).civGameData.civsInRange.clear();
         CFG.game.getCiv(nCivID).civGameData.civsInRange = this.diplomacyActions_CivsInRange(nCivID);
         if (CFG.game.getCiv(nCivID).civGameData.civsInRange.size() > 0) {
            CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID = 55 + CFG.oR.nextInt(45);
         }
         else {
            CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID = 65 + CFG.oR.nextInt(Math.max(75, CFG.game.getCivsSize() / 4));
         }
      }
      else {
         CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID = 24 + CFG.oR.nextInt(26);
      }
   }

   protected final void diplomacyActions_Ally(final int nCivID) {
      //if not set, randomize
      if (CFG.game.getCiv(nCivID).civGameData.allianceCheck_TurnID == 0) {
         CFG.game.getCiv(nCivID).civGameData.allianceCheck_TurnID = Game_Calendar.TURN_ID + 7 + CFG.oR.nextInt(25);
      }
      
      //new alliance ai system change//
      //if time to scan
      if (CFG.game.getCiv(nCivID).civGameData.allianceCheck_TurnID <= Game_Calendar.TURN_ID) {
         //if (CFG.game.getCiv(nCivID).getNumOfProvinces() < 4 && CFG.game.getCiv(nCivID).getPuppetOfCivID() == nCivID && CFG.game.getCiv(nCivID).getAllianceID() == 0 && CFG.oR.nextInt(100) < 12 && CFG.game.getCiv(nCivID).getFriendlyCivsSize() > 0) {
         //top prestiges
         final ArrayList<Integer> topPrestige = getTopPrestige();

         //create weight
         float weight = 0.0F;

         //if no puppets, add weight, else remove weight depending on how many puppets exist
         if (CFG.game.getCiv(nCivID).getPuppetOfCivID() == nCivID) {
            weight += 15.0F;
         } else {
            weight = Math.max((weight - (float)CFG.game.getCiv(nCivID).civGameData.lVassals.size() * 15.0F), 15.0F);
         }

         //if not in alliance, significantly increase weight, decrease by factor of prestige
         if (CFG.game.getCiv(nCivID).getAllianceID() == 0) {
            weight += 25.0F;
         }

         weight += ((float)topPrestige.indexOf(nCivID) / (float)topPrestige.size()) * 25.0F;
         //if greater than 40 then look for alliance
         if (CFG.oR.nextInt(Math.round(weight)) > 22) {
            //set best to random
            //int iBestCiv = CFG.game.getCiv(nCivID).getFriendlyCiv(CFG.oR.nextInt(CFG.game.getCiv(nCivID).getFriendlyCivsSize())).iCivID;
            int iBestCiv = 0;

            float iBestWeight = 0;
            //if friendly civs, scan, else scan bordering civs with 1/5 chance if exists
            if (CFG.game.getCiv(nCivID).getFriendlyCivsSize() > 0) {
               iBestCiv = CFG.game.getCiv(nCivID).getFriendlyCiv(CFG.oR.nextInt(CFG.game.getCiv(nCivID).getFriendlyCivsSize())).iCivID;

               for (int i = 0; i < CFG.game.getCiv(nCivID).getFriendlyCivsSize(); i++) {
                  //if in alliance or puppet of civ, or no provinces/neutral, skip
                  if (CFG.game.isAlly(nCivID, CFG.game.getCiv(nCivID).getFriendlyCiv(i).iCivID) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getFriendlyCiv(i).iCivID).getNumOfProvinces() < 1 || CFG.game.getCiv(nCivID).getFriendlyCiv(i).iCivID < 1) continue;

                  int iCivID = CFG.game.getCiv(nCivID).getFriendlyCiv(i).iCivID;
                  //weight starts at absolute of 120 / relation, so higher prob for lower values
                  float iWeight = CFG.game.getCivRelation_OfCivB(nCivID, iCivID) * 1.5F;
                  //get 5 index of civ's prestige percentile, higher = big boost, max value 100
                  iWeight += (4.0F / ((float) topPrestige.indexOf(iCivID) / (float) CFG.game.getCivsSize()));
                  //subtract by distance which is mediated by factor of age globilization
                  iWeight += 500.0F / ((CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), CFG.game.getCiv(iCivID).getCapitalProvinceID()) * 100.0F) + (CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID) * 40.0F));

                  //rand factor
                  iWeight -= CFG.oR.nextInt(Math.max(Math.round(iWeight / 5.0F), 1));
                  //if current civ distance is less than closest, assign closest to current civ
                  if (iWeight > iBestWeight) {
                     iBestCiv = iCivID;
                     iBestWeight = iWeight;
                  }
               }
            } else if ((CFG.oR.nextInt(7) == 0) && CFG.game.getCiv(nCivID).iBorderWithCivsSize > 9991) {
               iBestCiv = CFG.game.getCiv(nCivID).lBorderWithCivs.get(CFG.oR.nextInt(CFG.game.getCiv(nCivID).iBorderWithCivsSize)).iWithCivID;

               for (int i = 0; i < CFG.game.getCiv(nCivID).iBorderWithCivsSize; i++) {
                  //if in alliance or puppet of civ, or no provinces/neutral, skip
                  if (CFG.game.isAlly(nCivID, CFG.game.getCiv(nCivID).getFriendlyCiv(i).iCivID) || CFG.game.getCiv(CFG.game.getCiv(nCivID).lBorderWithCivs.get(i).iWithCivID).getNumOfProvinces() < 1 || CFG.game.getCiv(nCivID).lBorderWithCivs.get(i).iWithCivID < 1) continue;

                  int iCivID = CFG.game.getCiv(nCivID).lBorderWithCivs.get(i).iWithCivID;
                  //weight starts at absolute of 120 / relation, so higher prob for lower values
                  float iWeight = CFG.game.getCivRelation_OfCivB(nCivID, iCivID) * 2.5F;
                  //get 5 index of civ's prestige percentile, higher = big boost, max value 100
                  iWeight += (4.0F / ((float) topPrestige.indexOf(iCivID) / (float) CFG.game.getCivsSize()));
                  //(less for bordering) subtract by distance which is mediated by factor of age globilization
                  iWeight += 175.0F / ((CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), CFG.game.getCiv(iCivID).getCapitalProvinceID()) * 100.0F) + (CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID) * 75.0F));

                  //if current civ distance is less than closest, assign closest to current civ
                  if (iWeight > iBestWeight) {
                     iBestCiv = iCivID;
                     iBestWeight = iWeight;
                  }
               }
            }

            //if not nuetral send alliance
            if (iBestCiv > 0) {
               DiplomacyManager.sendAllianceProposal(iBestCiv, nCivID);
               Gdx.app.log("AoC2.5", "AI Alliance Matched: " + CFG.game.getCiv(nCivID).getCivName() + " " + CFG.game.getCiv(iBestCiv).getCivName());
            }
         }

         //reset scan
         CFG.game.getCiv(nCivID).civGameData.allianceCheck_TurnID = Game_Calendar.TURN_ID + 7 + CFG.oR.nextInt(25);
      }
   }

   protected final void diplomacyActions_FriendCiv(final int nCivID) {
      this.diplomacyActions_InfluencedCiv_Update(nCivID);
      if (Game_Calendar.TURN_ID >= CFG.game.getCiv(nCivID).civGameData.holdLookingForFriends_UntilTurnID) {
         //if civs in range
         if (CFG.game.getCiv(nCivID).civGameData.civsInRange.size() > 0) {
            final ArrayList<Integer> possibleCivs = new ArrayList<Integer>();
            ArrayList<Float> lScores = new ArrayList<Float>();
            final ArrayList<Integer> topPrestige = getTopPrestige();

            for (int i = CFG.game.getCiv(nCivID).civGameData.civsInRange.size() - 1; i >= 0; --i) {
               //if at war or already influenced, skip
               if (this.diplomacyActions_IsInfluenced(nCivID, CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID) || CFG.game.getCivsAtWar(nCivID, CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID) || CFG.game.getCiv(CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID).getNumOfProvinces() < 1 || CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID < 1) continue;

               //random 1/4th chance of skipping country
               if (CFG.oR.nextInt(4) == 0) {
                  continue;
               }


               int iCivID = CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i).iCivID;
               //add civid to possible civs to increase relation with
               possibleCivs.add(iCivID);

               //weight starts at absolute of 120 / relation, so higher prob for lower values
               float iWeight = Math.abs(150.0F / CFG.game.getCivRelation_OfCivB(nCivID, iCivID));

               //get 5 index of civ's prestige percentile, higher = big boost, max value 100
               iWeight += (4.0F / ((float)topPrestige.indexOf(iCivID) / (float)CFG.game.getCivsSize()));

               //subtract by distance which is mediated by factor of age globilization
               iWeight += 350.0F / ((CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), CFG.game.getCiv(iCivID).getCapitalProvinceID()) * 100.0F) + (CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID) * 75.0F));

               lScores.add(iWeight);
            }
            Gdx.app.log("AoC", "diplomacyActions_FriendCiv -> " + CFG.game.getCiv(nCivID).getCivName() + ", possibleCivs.size: " + possibleCivs.size());
            if (possibleCivs.size() > 0) {
               //min number of civs to seek friendly relations rivals + 2, max provinces - influenced (friendly)
               int numOfCivsToAdd = Math.min(CFG.oAI.MIN_NUM_OF_RIVALS + 2, CFG.game.getCiv(nCivID).getNumOfProvinces()) - CFG.game.getCiv(nCivID).civGameData.civsInfluencedSize;

               //get best scores, sort in new table
               final List<Integer> sortedIDs = new ArrayList<Integer>();
               while (lScores.size() > 0 && sortedIDs.size() < numOfCivsToAdd) {
                  int tBest = 0;
                  for (int l = possibleCivs.size() - 1; l > 0; --l) {
                     if (lScores.get(l) > lScores.get(tBest)) {
                        tBest = l;
                     }
                  }
                  sortedIDs.add(possibleCivs.get(tBest));
                  possibleCivs.remove(tBest);
                  lScores.remove(tBest);
               }

               //maxcivs, random factor
               final int maxCivsInThisTurn = Math.min(CFG.oAI.MIN_NUM_OF_RIVALS + CFG.oR.nextInt(3), numOfCivsToAdd);

               //sort through best ids
               for (int l = 0; l < sortedIDs.size() && l < maxCivsInThisTurn; ++l) {
                  //add new improve relation, length based on weight
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().addImproveRelations(nCivID, sortedIDs.get(l), 7 + CFG.oR.nextInt(30));
                  CFG.game.getCiv(nCivID).civGameData.civsInfluenced.add(new AI_Influence(sortedIDs.get(l), 32 + CFG.oR.nextInt(40), Game_Calendar.TURN_ID + this.RIVAL_MIN_TURNS * 3 / 4 + CFG.oR.nextInt(40)));
               }
               CFG.game.getCiv(nCivID).civGameData.civsInfluencedSize = CFG.game.getCiv(nCivID).civGameData.civsInfluenced.size();

               lScores.clear();
               possibleCivs.clear();
               topPrestige.clear();
            }

         }
         //reset turn
         CFG.game.getCiv(nCivID).civGameData.holdLookingForFriends_UntilTurnID = Game_Calendar.TURN_ID + 12 + CFG.oR.nextInt(20);
      }
   }

   protected final ArrayList<Integer> getTopPrestige() {
      ArrayList<Integer> tSorted = new ArrayList<Integer>();
      ArrayList<Integer> tempIDS = new ArrayList<Integer>();
      ArrayList<Integer> tempScore = new ArrayList<Integer>();

      //safecheck
      if (CFG.game.getSortedCivsSize() != CFG.game.getCivsSize()) {
         CFG.game.sortCivilizationsAZ();
      }

      for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
         tempIDS.add(CFG.game.getSortedCivsAZ(j - 1));
         tempScore.add(CFG.gameAction.buildRank_Score_Prestige(CFG.game.getSortedCivsAZ(j - 1)));
      }
      int tAddID = 0;
      while (tempIDS.size() > 0) {
         tAddID = 0;
         for (int k = 1; k < tempIDS.size(); ++k) {
            if (tempScore.get(tAddID) < tempScore.get(k)) {
               tAddID = k;
            }
         }
         tSorted.add(tempIDS.get(tAddID));
         tempIDS.remove(tAddID);
         tempScore.remove(tAddID);
      }
      return tSorted;
   }

   protected final float diplomacyActions_FriendlyCiv_Score(final int civBudget, final int nCivID, final AI_CivsInRange withCiv, final float modifier_Budget, final float modifier_CivsSize) {
      return modifier_Budget * Math.min(civBudget, CFG.game.getCiv(withCiv.iCivID).iBudget) / Math.max(civBudget, CFG.game.getCiv(withCiv.iCivID).iBudget) + modifier_CivsSize * Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), CFG.game.getCiv(withCiv.iCivID).getNumOfProvinces()) / Math.max(CFG.game.getCiv(nCivID).getNumOfProvinces(), CFG.game.getCiv(withCiv.iCivID).getNumOfProvinces()) * (this.isRivalOfMyRival(nCivID, withCiv.iCivID) ? 1.0625f : 1.0f) * ((CFG.game.getCivRelation_OfCivB(nCivID, withCiv.iCivID) > 40.0f) ? 0.625f : 1.0f);
   }

   protected final boolean isRivalOfMyRival(final int nCivID, final int withCiv) {
      for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.civRivalsSize; ++i) {
         if (diplomacyActions_RivalCiv_IsRival(CFG.game.getCiv(nCivID).civGameData.civRivals.get(i).iCivID, withCiv)) {
            return true;
         }
      }
      return false;
   }

   protected final void diplomacyActions_RivalCiv(final int nCivID) {
      this.diplomacyActions_RivalCiv_Update(nCivID);
      if (Game_Calendar.TURN_ID >= CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID) {
         int numOfCivsToAdd = Math.min(CFG.oAI.MIN_NUM_OF_RIVALS, CFG.game.getCiv(nCivID).getNumOfProvinces()) - CFG.game.getCiv(nCivID).civGameData.civRivalsSize;
         if (numOfCivsToAdd > 0) {
            if (CFG.game.getCiv(nCivID).civGameData.civsInRange.size() > 0) {
               final List<AI_CivsInRange> possibleCivs = new ArrayList<AI_CivsInRange>();
               for (int i = CFG.game.getCiv(nCivID).civGameData.civsInRange.size() - 1; i >= 0; --i) {
                  possibleCivs.add(CFG.game.getCiv(nCivID).civGameData.civsInRange.get(i));
               }
               Gdx.app.log("AoC", "diplomacyActions_RivalCiv -> " + CFG.game.getCiv(nCivID).getCivName() + ", possibleCivs.size: " + possibleCivs.size());
               for (int i = possibleCivs.size() - 1; i >= 0; --i) {
                  if (diplomacyActions_RivalCiv_IsRival(nCivID, possibleCivs.get(i).iCivID)) {
                     possibleCivs.remove(i);
                  }
                  else if (this.diplomacyActions_IsInfluenced(nCivID, possibleCivs.get(i).iCivID)) {
                     possibleCivs.remove(i);
                  }
               }
               if (possibleCivs.size() > 0) {
                  final List<Float> lScores = new ArrayList<Float>();
                  final List<Integer> tempD = new ArrayList<Integer>();
                  final float modifier_Budget = 1.85f + CFG.oR.nextInt(475) / 1000.0f;
                  final float modifier_CivsSize = 0.625f + CFG.oR.nextInt(525) / 1000.0f;
                  final float modifier_Range = 0.2825f + 0.1475f * (CFG.game.getCiv(nCivID).getRankPosition() / (float)CFG.game.getCivsSize()) + CFG.oR.nextInt(65) / 1000.0f;
                  int civBudget = 0;
                  if (CFG.game.getCiv(nCivID).getNumOfProvinces() < 5 || CFG.game.getCiv(nCivID).iLeague > 6) {
                     civBudget = (int)(CFG.game.getCiv(nCivID).iBudget * (0.925f + CFG.oR.nextInt(15) / 100.0f));
                  }
                  else {
                     civBudget = (int)(CFG.game.getCiv(nCivID).iBudget * (0.775f + CFG.oR.nextInt(625) / 100.0f));
                  }
                  for (int j = 0, iSize = possibleCivs.size(); j < iSize; ++j) {
                     lScores.add(this.diplomacyActions_RivalCiv_Score(civBudget, nCivID, possibleCivs.get(j), modifier_Budget, modifier_CivsSize));
                     tempD.add(j);
                  }
                  final float tempDis = CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID);
                  for (int k = possibleCivs.size() - 1; k >= 0; --k) {
                     lScores.set(k, lScores.get(k) * (1.0f + (-modifier_Range + (0.25f + CFG.oR.nextInt(350) / 100.0f) * CFG.game.getCiv(possibleCivs.get(k).iCivID).civGameData.civAggresionLevel) * possibleCivs.get(k).fRange / ((tempDis + tempDis * 0.2675f) * CFG.game.getCiv(nCivID).getTechnologyLevel()) + 0.115f * (Math.min(CFG.game.getCivRelation_OfCivB(nCivID, possibleCivs.get(k).iCivID), 0.0f) / 100.0f)));
                  }
                  final List<Integer> sortedIDs = new ArrayList<Integer>();
                  while (tempD.size() > 0 && sortedIDs.size() < numOfCivsToAdd) {
                     int tBest = 0;
                     for (int l = tempD.size() - 1; l > 0; --l) {
                        if (lScores.get(tempD.get(l)) > lScores.get(tempD.get(tBest))) {
                           tBest = l;
                        }
                     }
                     sortedIDs.add(tempD.get(tBest));
                     tempD.remove(tBest);
                  }
                  final int maxCivsInThisTurn = Math.min(1 + CFG.oR.nextInt(2), numOfCivsToAdd);
                  for (int l = 0; l < possibleCivs.size() && l < maxCivsInThisTurn; ++l) {
                     final int turns = 15 + CFG.oR.nextInt(35);
                     DiplomacyManager.decreaseRelation(nCivID, possibleCivs.get(sortedIDs.get(l)).iCivID, turns);
                     CFG.game.getCiv(nCivID).civGameData.civRivals.add(new AI_Rival(possibleCivs.get(sortedIDs.get(l)).iCivID, Game_Calendar.TURN_ID + turns * 2 + CFG.oR.nextInt(20)));
                     --numOfCivsToAdd;
                  }
                  CFG.game.getCiv(nCivID).civGameData.civRivalsSize = CFG.game.getCiv(nCivID).civGameData.civRivals.size();
                  if (numOfCivsToAdd <= 0) {
                     CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = Game_Calendar.TURN_ID + (int)(this.RIVAL_MIN_TURNS / 10.0f + CFG.oR.nextInt(45) / Game_Calendar.AI_AGGRESSIVNESS);
                  }
                  else if (possibleCivs.size() - maxCivsInThisTurn > Math.min(CFG.oAI.MIN_NUM_OF_RIVALS, CFG.game.getCiv(nCivID).getNumOfProvinces()) - CFG.game.getCiv(nCivID).civGameData.civRivalsSize) {
                     CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = Game_Calendar.TURN_ID + 5 + (int)(CFG.oR.nextInt(12) / Game_Calendar.AI_AGGRESSIVNESS);
                  }
               }
               else {
                  CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = Game_Calendar.TURN_ID + (int)((this.RIVAL_MIN_TURNS / 10.0f + CFG.oR.nextInt(60)) / Game_Calendar.AI_AGGRESSIVNESS);
               }
            }
            else {
               CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID + 1;
            }
         }
         else {
            CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = CFG.game.getCiv(nCivID).civGameData.nextBuildCivsInRange_TurnID + 5 + CFG.oR.nextInt(25);
         }
      }
   }

   protected static final boolean diplomacyActions_RivalCiv_IsRival(final int nCivID, final int nRivalID) {
      for (int z = 0; z < CFG.game.getCiv(nCivID).civGameData.civRivalsSize; ++z) {
         if (CFG.game.getCiv(nCivID).civGameData.civRivals.get(z).iCivID == nRivalID) {
            return true;
         }
      }
      return false;
   }

   protected final boolean diplomacyActions_IsInfluenced(final int nCivID, final int nInfluenced) {
      for (int z = 0; z < CFG.game.getCiv(nCivID).civGameData.civsInfluencedSize; ++z) {
         if (CFG.game.getCiv(nCivID).civGameData.civsInfluenced.get(z).iCivID == nInfluenced) {
            return true;
         }
      }
      return false;
   }

   protected final void diplomacyActions_RivalCiv_Update(final int nCivID) {
      for (int z = CFG.game.getCiv(nCivID).civGameData.civRivalsSize - 1; z >= 0; --z) {
         if (CFG.game.getCiv(nCivID).civGameData.civRivals.get(z).iUntilTurnID <= Game_Calendar.TURN_ID) {
            CFG.game.getCiv(nCivID).civGameData.civRivals.remove(z);
            CFG.game.getCiv(nCivID).civGameData.civRivalsSize = CFG.game.getCiv(nCivID).civGameData.civRivals.size();
            CFG.game.getCiv(nCivID).civGameData.holdLookingForEnemy_UntilTurnID = Game_Calendar.TURN_ID + CFG.oR.nextInt(2);
         }
      }
   }

   protected final void diplomacyActions_InfluencedCiv_Update(final int nCivID) {
      for (int z = CFG.game.getCiv(nCivID).civGameData.civsInfluencedSize - 1; z >= 0; --z) {
         if (CFG.game.getCiv(nCivID).civGameData.civsInfluenced.get(z).iUntilTurnID <= Game_Calendar.TURN_ID) {
            CFG.game.getCiv(nCivID).civGameData.civsInfluenced.remove(z);
            CFG.game.getCiv(nCivID).civGameData.civsInfluencedSize = CFG.game.getCiv(nCivID).civGameData.civsInfluenced.size();
            CFG.game.getCiv(nCivID).civGameData.holdLookingForFriends_UntilTurnID = Game_Calendar.TURN_ID + CFG.oR.nextInt(2);
         }
      }
   }

   protected final float diplomacyActions_RivalCiv_Score(final int civBudget, final int nCivID, final AI_CivsInRange withCiv, final float modifier_Budget, final float modifier_CivsSize) {
      return modifier_Budget * Math.min(civBudget, CFG.game.getCiv(withCiv.iCivID).iBudget) / Math.max(civBudget, CFG.game.getCiv(withCiv.iCivID).iBudget) + modifier_CivsSize * Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), CFG.game.getCiv(withCiv.iCivID).getNumOfProvinces()) / Math.max(CFG.game.getCiv(nCivID).getNumOfProvinces(), CFG.game.getCiv(withCiv.iCivID).getNumOfProvinces());
   }

   protected final List<AI_CivsInRange> diplomacyActions_CivsInRange(final int nCivID) {
      final List<AI_CivsInRange> possibleCivs = new ArrayList<AI_CivsInRange>();
      float tDistanceBetweenCivs = 1.0f;
      final float tempDis = CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID);
      for (int i = 1; i < nCivID; ++i) {
         if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.game.getCiv(i).getCapitalProvinceID() > 0) {
            tDistanceBetweenCivs = CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), CFG.game.getCiv(i).getCapitalProvinceID());
            if (tDistanceBetweenCivs * 0.9f < (tempDis + tempDis * 0.2675f) * CFG.game.getCiv(nCivID).getTechnologyLevel()) {
               possibleCivs.add(new AI_CivsInRange(i, tDistanceBetweenCivs));
            }
         }
      }
      for (int i = nCivID + 1; i < CFG.game.getCivsSize(); ++i) {
         if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.game.getCiv(i).getCapitalProvinceID() > 0) {
            tDistanceBetweenCivs = CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), CFG.game.getCiv(i).getCapitalProvinceID());
            if (tDistanceBetweenCivs * 0.9f < (tempDis + tempDis * 0.2675f) * CFG.game.getCiv(nCivID).getTechnologyLevel()) {
               possibleCivs.add(new AI_CivsInRange(i, tDistanceBetweenCivs));
            }
         }
      }
      return possibleCivs;
   }

   protected final void colonizeProvinces(final int nCivID) {
      if (Game_Calendar.getColonizationOfWastelandIsEnabled() || Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES) {
         boolean isColonizing = false;
         for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.COLONIZE_PROVINCE) {
               isColonizing = true;
               if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).canMakeAction(nCivID, 0)) {
                  if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).action(nCivID)) {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
                     CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 2 + CFG.oR.nextInt(24), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
                  }
                  else if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).iObsolate <= 0) {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
                     CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 2 + CFG.oR.nextInt(4), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
                  }
               }
            }
         }
         if (isColonizing) {
            return;
         }
         CFG.game.getCiv(nCivID).civGameData.iLockTreasury = 1;
         if (CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID > Game_Calendar.TURN_ID) {
            return;
         }
         if (!Game_Calendar.getCanColonize_TechLevel(nCivID) && CFG.game.getCiv(nCivID).getTechnologyLevel() / Game_Calendar.COLONIZATION_TECH_LEVEL < 1.0f - 0.35f * Math.min(CFG.oAI.iNumOfColonizedProvinces / Math.min((float)(6 + Math.min((CFG.game.getCiv(nCivID).getRankPosition() - 1) * 3, 22)), CFG.game.getProvincesSize() * 0.01f), 1.0f)) {
            CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 12 + CFG.oR.nextInt(15), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
            return;
         }
         if (CFG.game.getCiv(nCivID).iBudget < 1) {
            CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 5 + CFG.oR.nextInt(10), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
            return;
         }
         if (DiplomacyManager.getColonizeCost_AI(nCivID) / CFG.game.getCiv(nCivID).iBudget > 22) {
            CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 8 + CFG.oR.nextInt(12), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
            return;
         }
         if (CFG.game.getCiv(nCivID).getRankPosition() < Math.max(CFG.game.getCivsSize() * 0.35f, 11.0f)) {
            try {
               if (CFG.game.getCiv(nCivID).isAtWar()) {
                  CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID = Math.max(Game_Calendar.TURN_ID + 8 + CFG.oR.nextInt(14), CFG.game.getCiv(nCivID).civGameData.iLockColonization_UntilTurnID);
                  return;
               }
               int numOfProvincesAbleToColonize = 0;
               if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
                  numOfProvincesAbleToColonize += CFG.oAI.lWastelandProvincesWithSeaAccess.size();
                  numOfProvincesAbleToColonize += CFG.game.getCiv(nCivID).lBordersWithWastelandProvincesID.size();
               }
               if (Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES) {
                  numOfProvincesAbleToColonize += CFG.oAI.lNeutralProvincesWithSeaAccess.size();
                  numOfProvincesAbleToColonize += CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.size();
               }
               if (numOfProvincesAbleToColonize > 0) {
                  boolean tryFoundNewColony = true;
                  if (CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.size() > 0) {
                     tryFoundNewColony = !this.colonizeProvinces_ExtendColony(nCivID);
                  }
                  if (tryFoundNewColony) {
                     this.colonizeProvinces_FoundNewColony(nCivID);
                  }
               }
            }
            catch (final IndexOutOfBoundsException ex) {
               CFG.exceptionStack(ex);
            }
         }
      }
   }

   protected final void colonizeProvinces_FoundNewColony(final int nCivID) {
      final List<AI_ProvinceValue> possibleProvinces = new ArrayList<AI_ProvinceValue>();
      final List<Boolean> haveAccessToBasins = new ArrayList<Boolean>();
      for (int i = 0; i < CFG.map.iNumOfBasins; ++i) {
         haveAccessToBasins.add(false);
      }
      for (int i = CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size() - 1; i >= 0; --i) {
         if (!CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).isOccupied()) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvincesSize(); ++j) {
               haveAccessToBasins.set(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(j)).getBasinID(), true);
            }
         }
      }
      for (int i = CFG.oAI.lNeutralProvincesWithSeaAccess.size() - 1; i >= 0; --i) {
         boolean canColonizeThisProvince = false;
         for (int k = 0; k < CFG.game.getProvince(CFG.oAI.lNeutralProvincesWithSeaAccess.get(i)).getNeighboringSeaProvincesSize(); ++k) {
            if (haveAccessToBasins.get(CFG.game.getProvince(CFG.game.getProvince(CFG.oAI.lNeutralProvincesWithSeaAccess.get(i)).getNeighboringSeaProvinces(k)).getBasinID())) {
               canColonizeThisProvince = true;
               break;
            }
         }
         if (canColonizeThisProvince) {
            possibleProvinces.add(new AI_ProvinceValue(CFG.oAI.lNeutralProvincesWithSeaAccess.get(i)));
         }
      }
      if (possibleProvinces.size() == 0 && Game_Calendar.getColonizationOfWastelandIsEnabled()) {
         for (int i = CFG.oAI.lWastelandProvincesWithSeaAccess.size() - 1; i >= 0; --i) {
            boolean canColonizeThisProvince = false;
            for (int k = 0; k < CFG.game.getProvince(CFG.oAI.lWastelandProvincesWithSeaAccess.get(i)).getNeighboringSeaProvincesSize(); ++k) {
               if (haveAccessToBasins.get(CFG.game.getProvince(CFG.game.getProvince(CFG.oAI.lWastelandProvincesWithSeaAccess.get(i)).getNeighboringSeaProvinces(k)).getBasinID())) {
                  canColonizeThisProvince = true;
                  break;
               }
            }
            if (canColonizeThisProvince) {
               possibleProvinces.add(new AI_ProvinceValue(CFG.oAI.lWastelandProvincesWithSeaAccess.get(i)));
            }
         }
      }
      if (possibleProvinces.size() > 0) {
         final int colonizeProvinceID = possibleProvinces.get(CFG.oR.nextInt(possibleProvinces.size())).iProvinceID;
         if (CFG.game.getProvince(colonizeProvinceID).getCivID() == 0) {
            CFG.game.getCiv(nCivID).civGameData.civPlans.addNewArmyMission(colonizeProvinceID, new CivArmyMission_ColonizeProvince(nCivID, colonizeProvinceID));
            CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Math.max(CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID, Game_Calendar.TURN_ID + (int)((8 + CFG.oR.nextInt(25) + CFG.oR.nextInt(CFG.game.getCivsSize() + 1) / 20) / Game_Calendar.AI_AGGRESSIVNESS));
         }
      }
   }

   protected final boolean colonizeProvinces_ExtendColony(final int nCivID) {
      try {
         final List<AI_ProvinceValue> possibleProvinces = new ArrayList<AI_ProvinceValue>();
         for (int i = CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.size() - 1; i >= 0; --i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i)).getCivID() == 0) {
               possibleProvinces.add(new AI_ProvinceValue(CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i), this.colonizeProvinces_ExtendColony_Score(nCivID, CFG.game.getCiv(nCivID).lBordersWithNeutralProvincesID.get(i))));
            }
         }
         for (int i = CFG.game.getCiv(nCivID).lBordersWithWastelandProvincesID.size() - 1; i >= 0; --i) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lBordersWithWastelandProvincesID.get(i)).getCivID() == 0) {
               possibleProvinces.add(new AI_ProvinceValue(CFG.game.getCiv(nCivID).lBordersWithWastelandProvincesID.get(i), this.colonizeProvinces_ExtendColony_Score(nCivID, CFG.game.getCiv(nCivID).lBordersWithWastelandProvincesID.get(i))));
            }
         }
         for (int i = CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.size() - 1; i >= 0; --i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.get(i).iProvinceID).getNeighboringProvincesSize(); ++j) {
               if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.get(i).iProvinceID).getNeighboringProvinces(j)).getCivID() == 0 || CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.get(i).iProvinceID).getNeighboringProvinces(j)).getWasteland() >= 0) {
                  possibleProvinces.add(new AI_ProvinceValue(CFG.game.getProvince(CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.get(i).iProvinceID).getNeighboringProvinces(j), this.colonizeProvinces_ExtendColony_Score(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.get(i).iProvinceID).getNeighboringProvinces(j))));
               }
            }
         }
         for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvincesSize(); ++j) {
               for (int k = 0; k < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvincesSize(); ++k) {
                  if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCivID() == 0) {
                     possibleProvinces.add(new AI_ProvinceValue(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k), (int)(this.colonizeProvinces_ExtendColony_Score(nCivID, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)) * ((CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvincesSize() == 0) ? 1.0f : 0.625f))));
                  }
               }
            }
         }
         if (possibleProvinces.size() > 0) {
            for (int i = possibleProvinces.size() - 1; i >= 0; --i) {
               for (int j = 0; j < CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvincesSize(); ++j) {
                  for (int k = 0; k < CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvincesSize(); ++k) {
                     if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvincesSize() == 0 && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCivID() == 0) {
                        possibleProvinces.add(new AI_ProvinceValue(CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k), this.colonizeProvinces_ExtendColony_Score(nCivID, CFG.game.getProvince(CFG.game.getProvince(possibleProvinces.get(i).iProvinceID).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k))));
                     }
                  }
               }
            }
            int colonizeProvinceID = 0;
            for (int l = possibleProvinces.size() - 1; l > 0; --l) {
               if (possibleProvinces.get(colonizeProvinceID).iValue < possibleProvinces.get(l).iValue) {
                  colonizeProvinceID = l;
               }
               else if (possibleProvinces.get(colonizeProvinceID).iValue == possibleProvinces.get(l).iValue && CFG.oR.nextInt(100) < 50) {
                  colonizeProvinceID = l;
               }
            }
            if (CFG.game.getProvince(possibleProvinces.get(colonizeProvinceID).iProvinceID).getCivID() == 0) {
               if (CFG.gameAction.canColonizieWasteland_BorderOrArmy(possibleProvinces.get(colonizeProvinceID).iProvinceID, nCivID)) {
                  CFG.game.getCiv(nCivID).civGameData.civPlans.addNewArmyMission(possibleProvinces.get(colonizeProvinceID).iProvinceID, new CivArmyMission_ColonizeProvince_Just(nCivID, possibleProvinces.get(colonizeProvinceID).iProvinceID));
                  CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Math.max(CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID, Game_Calendar.TURN_ID + (int)((8 + CFG.oR.nextInt(25) + CFG.oR.nextInt(CFG.game.getCivsSize() + 1) / 20) / Game_Calendar.AI_AGGRESSIVNESS));
               }
               else {
                  CFG.game.getCiv(nCivID).civGameData.civPlans.addNewArmyMission(possibleProvinces.get(colonizeProvinceID).iProvinceID, new CivArmyMission_ColonizeProvince(nCivID, possibleProvinces.get(colonizeProvinceID).iProvinceID));
                  CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID = Math.max(CFG.game.getCiv(nCivID).civGameData.declareWar_CheckNextTurnID, Game_Calendar.TURN_ID + (int)((8 + CFG.oR.nextInt(25) + CFG.oR.nextInt(CFG.game.getCivsSize() + 1) / 20) / Game_Calendar.AI_AGGRESSIVNESS));
               }
               return true;
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final IllegalArgumentException ex2) {
         CFG.exceptionStack(ex2);
      }
      return false;
   }

   protected final int colonizeProvinces_ExtendColony_Score(final int nCivID, final int nProvinceID) {
      float out = 1.0f;
      if (CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize() > 0) {
         int ownProvinces = 0;
         for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
               ++ownProvinces;
               out += 0.725f;
            }
         }
         out += CFG.game.getCiv(nCivID).civGameData.civPersonality.COLONIZATION_OWN_PROVINCES * (ownProvinces / Math.max(CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(), 1));
      }
      out += CFG.game.getCiv(nCivID).civGameData.civPersonality.COLONIZATION_DISTANCE * (1.0f - CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nProvinceID));
      if (CFG.game.getProvince(nProvinceID).getNeighboringSeaProvincesSize() > 0) {
         out += CFG.game.getCiv(nCivID).civGameData.civPersonality.COLONIZATION_SEA;
      }
      out += CFG.game.getCiv(nCivID).civGameData.civPersonality.COLONIZATION_GROWTH_RATE * CFG.game.getProvince(nProvinceID).getGrowthRate_Population();
      return (int)out;
   }

   protected final void checkPeaceTreaties(final int nCivID) {
      if (CFG.game.getCiv(nCivID).isAtWar()) {
         Gdx.app.log("AoC", "checkPeaceTreaties -> " + CFG.game.getCiv(nCivID).getCivName());
         if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) {}
      }
   }

   protected final void recruitMilitary_MinSpendings(final int nCivID) {
      try {
         Gdx.app.log("AoC", "recruitMilitary_MinSpendings -> " + CFG.game.getCiv(nCivID).getCivName() + " -> MIN_MILITARY: " + this.getMinMilitarySpendings(nCivID) + " -> PERC" + CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC);
         final int nUpkeepLeft = (int)(CFG.game.getCiv(nCivID).iBudget * this.getMinMilitarySpendings(nCivID) - CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC);
         Gdx.app.log("AoC", "recruitMilitary_MinSpendings -> " + CFG.game.getCiv(nCivID).getCivName() + " -> nUpkeepLeft: " + nUpkeepLeft);
         if (nUpkeepLeft > 0 && CFG.oAI.lFrontLines.get(nCivID - 1).size() > 0) {
            int tMaxDL = 1;
            float tMaxPotential = 1.0f;
            final List<AI_ProvinceInfo> tempFrontProvinces = new ArrayList<AI_ProvinceInfo>();
            for (int i = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; i >= 0; --i) {
               for (int j = CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.size() - 1; j >= 0; --j) {
                  boolean wasAdded = false;
                  for (int k = tempFrontProvinces.size() - 1; k >= 0; --k) {
                     if (tempFrontProvinces.get(k).iProvinceID == CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j)) {
                        wasAdded = true;
                        break;
                     }
                  }
                  if (!wasAdded) {
                     tempFrontProvinces.add(new AI_ProvinceInfo(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j), nCivID), CFG.gameAction.getRecruitableArmy(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j))));
                  }
               }
            }
            if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getNeighboringSeaProvincesSize() > 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID) {
               boolean aldAdded = false;
               for (int l = tempFrontProvinces.size() - 1; l >= 0; --l) {
                  if (tempFrontProvinces.get(l).iProvinceID == CFG.game.getCiv(nCivID).getCapitalProvinceID()) {
                     aldAdded = true;
                     break;
                  }
               }
               if (!aldAdded) {
                  tempFrontProvinces.add(new AI_ProvinceInfo(CFG.game.getCiv(nCivID).getCapitalProvinceID(), this.getPotential_BasedOnNeighboringProvs(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nCivID), CFG.gameAction.getRecruitableArmy(CFG.game.getCiv(nCivID).getCapitalProvinceID())));
               }
            }
            if (tempFrontProvinces.size() > 0) {
               int tMaxArmy = 1;
               float tMaxRegion_NumOfProvinces = 1.0f;
               float tMaxRegion_Potential = 1.0f;
               final List<Integer> tMovingArmy = new ArrayList<Integer>();
               int m = 0;
               final int iSize = tempFrontProvinces.size();
               int tempMovingArmy = 0;
               while (m < iSize) {
                  if (tempFrontProvinces.get(m).iValue > tMaxPotential) {
                     tMaxPotential = tempFrontProvinces.get(m).iValue;
                  }
                  if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                     tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy();
                  }
                  if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
                     tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces();
                  }
                  if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
                     tMaxRegion_Potential = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion();
                  }
                  tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(m).iProvinceID);
                  tMovingArmy.add(tempMovingArmy);
                  if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
                     tMaxArmy = CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getArmy(0) + tempMovingArmy;
                  }
                  ++m;
               }
               final int numOfUnitsToRecruit_MAX = (int)(nUpkeepLeft / (CFG.game_NextTurnUpdate.getMilitaryUpkeep_WithoutDefensivePosition(tempFrontProvinces.get(0).iProvinceID, 1000, nCivID) / 1000.0f));
               for (int i2 = 0, iSize2 = tempFrontProvinces.size(); i2 < iSize2; ++i2) {
                  tempFrontProvinces.get(i2).iValue = this.getValue_PositionOfArmy(nCivID, tempFrontProvinces, i2, tMovingArmy.get(i2), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, numOfUnitsToRecruit_MAX, tMaxRegion_NumOfProvinces);
               }
               final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
               while (tempFrontProvinces.size() > 0) {
                  int tBest = 0;
                  for (int i3 = 1, iSize3 = tempFrontProvinces.size(); i3 < iSize3; ++i3) {
                     if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(i3).iValue) {
                        tBest = i3;
                     }
                  }
                  sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
                  tempFrontProvinces.remove(tBest);
               }
               final int iNumOfMaxRecruitments = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT, CFG.game.getCiv(nCivID).getNumOfProvinces()));
               final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
               float totalValues = 0.0f;
               for (int i4 = 0; i4 < iNumOfMaxRecruitments && i4 < sortedFrontProvinces.size(); ++i4) {
                  tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i4));
                  totalValues += sortedFrontProvinces.get(i4).iValue;
               }
               final int tempMoneyPre = (int)CFG.game.getCiv(nCivID).getMoney();
               for (int i5 = 0; i5 < tRecruitArmiesForProvinces.size(); ++i5) {
                  final int tArmyToRecruit_PRE = (int)(Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(i5).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5)) * tRecruitArmiesForProvinces.get(i5).iValue / totalValues);
                  Gdx.app.log("AoC", "recruitMilitary_MinSpendings -> RECRUIT FOR: " + tRecruitArmiesForProvinces.get(i5).iProvinceID + " -> " + CFG.game.getProvince(tRecruitArmiesForProvinces.get(i5).iProvinceID).getName() + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
                  boolean notEnoughRecruits = false;
                  if (tRecruitArmiesForProvinces.get(i5).iRecruitable < tArmyToRecruit_PRE) {
                     notEnoughRecruits = true;
                  }
                  if (CFG.game.getProvince(tRecruitArmiesForProvinces.get(i5).iProvinceID).isOccupied() || notEnoughRecruits || CFG.oR.nextInt(100) < CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_RECRUIT_FROM_FAR_AWAY_CHANCE) {
                     final List<AI_NeighProvinces> listOfPossibleProvincesToRecruit = CFG.oAI.getAllNeighboringProvincesInRange_Recruit(tRecruitArmiesForProvinces.get(i5).iProvinceID, nCivID, 3, true, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
                     if (listOfPossibleProvincesToRecruit.size() > 0) {
                        int tempRand = 0;
                        if (notEnoughRecruits) {
                           int tBest2 = 0;
                           int tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tBest2).iProvinceID);
                           for (int k2 = 1; k2 < listOfPossibleProvincesToRecruit.size(); ++k2) {
                              if (tBestArmy < CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(k2).iProvinceID)) {
                                 tBest2 = k2;
                                 tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(k2).iProvinceID);
                              }
                           }
                           tempRand = tBest2;
                        }
                        else {
                           tempRand = CFG.oR.nextInt(listOfPossibleProvincesToRecruit.size());
                        }
                        final int tArmyToRecruit = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID), tempMoneyPre / ((CFG.game.getProvince(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(i5).iValue / totalValues);
                        CFG.game.getCiv(nCivID).recruitArmy_AI(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tArmyToRecruit);
                        final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID);
                        if (tempArmy > 0) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tRecruitArmiesForProvinces.get(i5).iProvinceID, tempArmy));
                        }
                     }
                  }
                  else {
                     final int tArmyToRecruit2 = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(CFG.gameAction.getRecruitableArmy(tRecruitArmiesForProvinces.get(i5).iProvinceID), tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(i5).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(i5).iValue / totalValues);
                     CFG.game.getCiv(nCivID).recruitArmy_AI(tRecruitArmiesForProvinces.get(i5).iProvinceID, tArmyToRecruit2);
                  }
               }
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
   }

   protected final void regroupArmy_AtPeace(final int nCivID) {
      try {
         if (CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID <= Game_Calendar.TURN_ID) {
            final List<AI_RegoupArmyData> armiesWithoutDanger = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesInAnotherTerritory = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesAtSea = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> rest = new ArrayList<AI_RegoupArmyData>();
            int numOfUnitsToRegoup = 0;
            for (int i = 0; i < CFG.game.getCiv(nCivID).iArmiesPositionSize; ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i));
               if (tArmyToRegroup > 0) {
                  if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getSeaProvince()) {
                     armiesAtSea.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
                  else if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getCivID() != nCivID) {
                     armiesInAnotherTerritory.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
                  else if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getDangerLevel() == 0) {
                     armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                     numOfUnitsToRegoup += tArmyToRegroup;
                  }
                  else {
                     rest.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
               }
            }
            for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i));
               if (tArmyToRegroup > 0) {
                  if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getSeaProvince()) {
                     armiesAtSea.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
                  else if ((CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getCivID() != nCivID && !CFG.game.isAlly(CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getCivID(), nCivID))) {
                     armiesInAnotherTerritory.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
                  else if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getDangerLevel() == 0) {
                     armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                     numOfUnitsToRegoup += tArmyToRegroup;
                  }
                  else {
                     rest.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
               }
            }
            Gdx.app.log("AoC", "regroupArmy_AtPeace -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesWithoutDanger.size: " + armiesWithoutDanger.size());
            Gdx.app.log("AoC", "regroupArmy_AtPeace -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesInAnotherTerritory.size: " + armiesInAnotherTerritory.size());
            Gdx.app.log("AoC", "regroupArmy_AtPeace -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesAtSea.size: " + armiesAtSea.size());
            if (armiesWithoutDanger.size() == CFG.game.getCiv(nCivID).getNumOfProvinces()) {
               armiesWithoutDanger.clear();
            }
            while (armiesWithoutDanger.size() > 0 || armiesAtSea.size() > 0 || armiesInAnotherTerritory.size() > 0) {
               int highestArmyID = -1;
               int highestArmy_Num = 0;
               int highestArmy_ListID = -1;
               for (int j = armiesWithoutDanger.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesWithoutDanger.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesWithoutDanger.get(j).iArmy;
                     highestArmy_ListID = 0;
                  }
               }
               for (int j = armiesAtSea.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesAtSea.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesAtSea.get(j).iArmy;
                     highestArmy_ListID = 1;
                  }
               }
               for (int j = armiesInAnotherTerritory.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesInAnotherTerritory.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesInAnotherTerritory.get(j).iArmy;
                     highestArmy_ListID = 2;
                  }
               }
               if (Game_Calendar.TURN_ID >= CFG.game.getCiv(nCivID).civGameData.nextArmyRestREgroupment_TurnID) {
                  for (int j = rest.size() - 1; j >= 0; --j) {
                     if (highestArmyID < 0 || highestArmy_Num < rest.get(j).iArmy) {
                        highestArmyID = j;
                        highestArmy_Num = rest.get(j).iArmy;
                        highestArmy_ListID = 3;
                     }
                  }
               }
               if (highestArmyID >= 0 && highestArmy_ListID >= 0 && highestArmy_Num > 0) {
                  switch (highestArmy_ListID) {
                     case 0: {
                        Gdx.app.log("AoC", "regroupArmy_AtPeace -> ACTION: 0");
                        this.regroupArmy_AtPeace_InOwnTerritory_WithoutDanger(nCivID, armiesWithoutDanger.get(highestArmyID), false);
                        armiesWithoutDanger.remove(highestArmyID);
                        break;
                     }
                     case 1: {
                        Gdx.app.log("AoC", "regroupArmy_AtPeace -> ACTION: 1");
                        this.regroupArmy_AtPeace_AtSea(nCivID, armiesAtSea.get(highestArmyID));
                        armiesAtSea.remove(highestArmyID);
                        break;
                     }
                     case 2: {
                        Gdx.app.log("AoC", "regroupArmy_AtPeace -> ACTION: 2");
                        this.regroupArmy_AtPeace_InAnotherTerritory(nCivID, armiesInAnotherTerritory.get(highestArmyID));
                        armiesInAnotherTerritory.remove(highestArmyID);
                        break;
                     }
                     case 3: {
                        Gdx.app.log("AoC", "regroupArmy_AtPeace -> ACTION: 3");
                        this.regroupArmy_AtPeace_InOwnTerritory_WithoutDanger(nCivID, rest.get(highestArmyID), true);
                        rest.remove(highestArmyID);
                        CFG.game.getCiv(nCivID).civGameData.nextArmyRestREgroupment_TurnID = Math.max(CFG.game.getCiv(nCivID).civGameData.nextArmyRestREgroupment_TurnID, Game_Calendar.TURN_ID + 3 + CFG.oR.nextInt(9));
                        break;
                     }
                  }
               }
               if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
                  break;
               }
               if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
                  break;
               }
            }
            if (armiesWithoutDanger.size() == CFG.game.getCiv(nCivID).getNumOfProvinces() || armiesAtSea.size() != 0) {
               CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID = Game_Calendar.TURN_ID + CFG.oR.nextInt(4) + (CFG.game.getCiv(nCivID).getIsPupet() ? 12 : 4);
            }
         }
      }
      catch (final IndexOutOfBoundsException | StackOverflowError ex) {
         CFG.exceptionStack(ex);
      }
   }

   protected final boolean regroupArmy_AtPeace_AtSea(final int nCivID, final AI_RegoupArmyData nArmy) {
      final List<AI_ProvinceInfo> possibleMoveTo = new ArrayList<AI_ProvinceInfo>();
      for (int i = 0; i < CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID) {
            possibleMoveTo.add(new AI_ProvinceInfo(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i), this.getPotential_BasedOnNeighboringProvs(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i), nCivID), 1));
         }
      }
      Gdx.app.log("AoC", "regroupArmy_AtPeace_AtSea -> " + CFG.game.getCiv(nCivID).getCivName() + ", Province: " + nArmy.iProvinceID + ", NAME: " + CFG.game.getProvince(nArmy.iProvinceID).getName() + ", nArmy: " + nArmy.iArmy + ", possibleMoveTo.size: " + possibleMoveTo.size());
      if (possibleMoveTo.size() > 0) {
         int tMaxArmy = 1;
         float tMaxPotential = 1.0f;
         float tMaxRegion_NumOfProvinces = 1.0f;
         float tMaxRegion_Potential = 1.0f;
         int tMaxDL = 1;
         final List<Integer> tMovingArmy = new ArrayList<Integer>();
         int j = 0;
         int iSize = possibleMoveTo.size();
         int tempMovingArmy = 0;
         while (j < iSize) {
            if (possibleMoveTo.get(j).iValue > tMaxPotential) {
               tMaxPotential = possibleMoveTo.get(j).iValue;
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
               tMaxDL = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
               tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
               tMaxRegion_Potential = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion();
            }
            tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, possibleMoveTo.get(j).iProvinceID);
            tMovingArmy.add(tempMovingArmy);
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
               tMaxArmy = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy;
            }
            ++j;
         }
         for (j = 0, iSize = possibleMoveTo.size(); j < iSize; ++j) {
            possibleMoveTo.get(j).iValue = this.getValue_PositionOfArmy(nCivID, possibleMoveTo, j, tMovingArmy.get(j), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, nArmy.iArmy, (float)nArmy.iArmy);
         }
         final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
         while (possibleMoveTo.size() > 0) {
            int tBest = 0;
            for (int k = 1, iSize2 = possibleMoveTo.size(); k < iSize2; ++k) {
               if (possibleMoveTo.get(tBest).iValue < possibleMoveTo.get(k).iValue) {
                  tBest = k;
               }
            }
            sortedFrontProvinces.add(possibleMoveTo.get(tBest));
            possibleMoveTo.remove(tBest);
         }
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         int iNumOfMaxMovements = 1;
         if (CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_DISBAND_IF_LESS_THAN_PERC > percOfArmyToRegroup) {
            iNumOfMaxMovements = 1;
         }
         else {
            iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * 2), 1 + CFG.oR.nextInt(3)));
            if (percOfArmyToRegroup > 0.54f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 4);
            }
            else if (percOfArmyToRegroup > 0.34f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 3);
            }
            else if (percOfArmyToRegroup > 0.14f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 2);
            }
            else {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 1);
            }
         }
         Gdx.app.log("AoC", "regroupArmy_AtPeace_AtSea -> 000 -> iNumOfMaxMovements: " + iNumOfMaxMovements);
         final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
         float totalValues = 0.0f;
         for (int l = 0; l < iNumOfMaxMovements && l < sortedFrontProvinces.size(); ++l) {
            tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(l));
            totalValues += sortedFrontProvinces.get(l).iValue;
         }
         Gdx.app.log("AoC", "regroupArmy_AtPeace_AtSea -> 000 -> tRecruitArmiesForProvinces.size: " + tRecruitArmiesForProvinces.size());
         for (int l = 0; l < tRecruitArmiesForProvinces.size(); ++l) {
            final int tArmyToRecruit_PRE = (int)Math.ceil(nArmy.iArmy * tRecruitArmiesForProvinces.get(l).iValue / totalValues);
            Gdx.app.log("AoC", "regroupArmy_AtPeace_AtSea -> 000 -> nArmy.iArmy: " + nArmy.iArmy + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
            if (tArmyToRecruit_PRE <= 0) {
               break;
            }
            final RegroupArmy_Data_AtPeace tryRegroupArmy = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, tRecruitArmiesForProvinces.get(l).iProvinceID);
            if (tryRegroupArmy.getRouteSize() > 0) {
               if (tryRegroupArmy.getRouteSize() == 1) {
                  if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, tRecruitArmiesForProvinces.get(l).iProvinceID, tArmyToRecruit_PRE, nCivID, true, false)) {}
               }
               else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToRecruit_PRE, nCivID, true, false)) {
                  tryRegroupArmy.setFromProvinceID(tryRegroupArmy.getRoute(0));
                  tryRegroupArmy.removeRoute(0);
                  tryRegroupArmy.setNumOfUnits(tArmyToRecruit_PRE);
                  CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy);
               }
            }
         }
         return true;
      }
      for (int i = CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size() - 1; i >= 0; --i) {
         for (int m = 0; m < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvincesSize(); ++m) {
            if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(m)).getBasinID() == CFG.game.getProvince(nArmy.iProvinceID).getBasinID()) {
               possibleMoveTo.add(new AI_ProvinceInfo(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i), this.getPotential_BasedOnNeighboringProvs(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i), nCivID), 1));
               break;
            }
         }
      }
      Gdx.app.log("AoC", "regroupArmy_AtPeace_AtSea ->  111, possibleMoveTo.size: " + possibleMoveTo.size());
      if (possibleMoveTo.size() > 0) {
         int tMaxArmy = 1;
         float tMaxPotential = 1.0f;
         float tMaxRegion_NumOfProvinces = 1.0f;
         float tMaxRegion_Potential = 1.0f;
         int tMaxDL = 1;
         final List<Integer> tMovingArmy = new ArrayList<Integer>();
         int j = 0;
         int iSize = possibleMoveTo.size();
         int tempMovingArmy = 0;
         while (j < iSize) {
            if (possibleMoveTo.get(j).iValue > tMaxPotential) {
               tMaxPotential = possibleMoveTo.get(j).iValue;
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
               tMaxDL = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
               tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
               tMaxRegion_Potential = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion();
            }
            tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, possibleMoveTo.get(j).iProvinceID);
            tMovingArmy.add(tempMovingArmy);
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
               tMaxArmy = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy;
            }
            ++j;
         }
         for (j = 0, iSize = possibleMoveTo.size(); j < iSize; ++j) {
            possibleMoveTo.get(j).iValue = this.getValue_PositionOfArmy(nCivID, possibleMoveTo, j, tMovingArmy.get(j), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, nArmy.iArmy, (float)nArmy.iArmy);
         }
         final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
         if (possibleMoveTo.size() > 0) {
            int tBest = 0;
            for (int k = 1, iSize2 = possibleMoveTo.size(); k < iSize2; ++k) {
               if (possibleMoveTo.get(tBest).iValue < possibleMoveTo.get(k).iValue) {
                  tBest = k;
               }
            }
            sortedFrontProvinces.add(possibleMoveTo.get(tBest));
            possibleMoveTo.remove(tBest);
         }
         final RegroupArmy_Data_AtPeace tryRegroupArmy2 = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, sortedFrontProvinces.get(0).iProvinceID);
         if (tryRegroupArmy2.getRouteSize() > 0) {
            if (tryRegroupArmy2.getRouteSize() == 1) {
               if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, sortedFrontProvinces.get(0).iProvinceID, nArmy.iArmy, nCivID, true, false)) {}
            }
            else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
               tryRegroupArmy2.setFromProvinceID(tryRegroupArmy2.getRoute(0));
               tryRegroupArmy2.removeRoute(0);
               tryRegroupArmy2.setNumOfUnits(nArmy.iArmy);
               CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy2);
            }
         }
         return true;
      }
      if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID) {
         final RegroupArmy_Data_AtPeace tryRegroupArmy3 = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID());
         if (tryRegroupArmy3.getRouteSize() > 0) {
            if (tryRegroupArmy3.getRouteSize() == 1) {
               if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID(), nArmy.iArmy, nCivID, true, false)) {}
            }
            else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy3.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
               tryRegroupArmy3.setFromProvinceID(tryRegroupArmy3.getRoute(0));
               tryRegroupArmy3.removeRoute(0);
               tryRegroupArmy3.setNumOfUnits(nArmy.iArmy);
               CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy3);
            }
         }
         else {
            CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
         }
      }
      else {
         CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
      }
      return true;
   }

   protected final boolean regroupArmy_AtPeace_InAnotherTerritory(final int nCivID, final AI_RegoupArmyData nArmy) {
      try {
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         final List<AI_NeighProvinces> listOfPossibleProvinces = CFG.oAI.getAllNeighboringProvincesInRange_OnlyOwn_Clear(nArmy.iProvinceID, nCivID, CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_MAX_PROVINCES + CFG.game.getCiv(nCivID).getNumOfProvinces() / 15, false, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
         Gdx.app.log("AoC", "regroupArmy_AtPeace_InAnotherTerritory - > listOfPossibleProvinces.size: " + listOfPossibleProvinces.size());
         if (listOfPossibleProvinces.size() > 0) {
            int nNumOfPossibleMovements = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            if (percOfArmyToRegroup > 0.275f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 2);
            }
            else {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 1);
            }
            Gdx.app.log("AoC", "regroupArmy_AtPeace_InAnotherTerritory -> 000");
            Gdx.app.log("AoC", "regroupArmy_AtPeace_InAnotherTerritory -> 1111");
            final List<Integer> tSortedIDs = new ArrayList<Integer>();
            final List<Integer> tData = new ArrayList<Integer>();
            for (int i = listOfPossibleProvinces.size() - 1; i >= 0; --i) {
               tData.add(i);
            }
            while (tData.size() > 0) {
               int tBest = 0;
               for (int j = tData.size() - 1; j > 0; --j) {
                  if (CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(tBest)).iProvinceID).getPotential() < CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(j)).iProvinceID).getPotential()) {
                     tBest = j;
                  }
               }
               tSortedIDs.add(tData.get(tBest));
               tData.remove(tBest);
            }
            int nDangerTotal = 0;
            for (int j = 0; j < nNumOfPossibleMovements && j < tSortedIDs.size(); ++j) {
               nDangerTotal += CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(j)).iProvinceID).getPotential();
            }
            int tIDOfFisrttSuccesfulMovement = -1;
            Gdx.app.log("AoC", "regroupArmy_AtPeace_InAnotherTerritory -> 222, nNumOfPossibleMovements: " + nNumOfPossibleMovements);
            for (int k = 0; k < nNumOfPossibleMovements && k < tSortedIDs.size() && nArmy.iArmy > 0; ++k) {
               RegroupArmy_Data_AtPeace tryRegroupArmy = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(k)).iProvinceID);
               if (tryRegroupArmy.getRouteSize() > 0) {
                  final int tArmyToMove = (k == nNumOfPossibleMovements || k == tSortedIDs.size() - 1) ? nArmy.iArmy : ((int)Math.ceil(nArmy.iArmy * (CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(k)).iProvinceID).getPotential() / (float)nDangerTotal)));
                  nArmy.iArmy -= tArmyToMove;
                  if (tArmyToMove <= 0) {
                     break;
                  }
                  if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToMove, nCivID, true, false)) {
                     if (tryRegroupArmy.getRouteSize() > 1) {
                        CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(k)).iProvinceID, tArmyToMove));
                     }
                     tIDOfFisrttSuccesfulMovement = k;
                  }
               }
               else if (tIDOfFisrttSuccesfulMovement >= 0) {
                  tryRegroupArmy = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID);
                  if (tryRegroupArmy.getRouteSize() > 0 && CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
                     if (tryRegroupArmy.getRouteSize() > 1) {
                        CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID, nArmy.iArmy));
                     }
                     return true;
                  }
               }
            }
            if (tIDOfFisrttSuccesfulMovement >= 0) {
               return true;
            }
         }
         else if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID) {
            final RegroupArmy_Data_AtPeace tryRegroupArmy2 = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID());
            if (tryRegroupArmy2.getRouteSize() > 0) {
               if (tryRegroupArmy2.getRouteSize() == 1) {
                  if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID(), nArmy.iArmy, nCivID, true, false)) {}
               }
               else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
                  tryRegroupArmy2.setFromProvinceID(tryRegroupArmy2.getRoute(0));
                  tryRegroupArmy2.removeRoute(0);
                  tryRegroupArmy2.setNumOfUnits(nArmy.iArmy);
                  CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy2);
               }
            }
            else {
               CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
            }
         }
         else {
            CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
      return false;
   }

   protected final boolean regroupArmy_AtPeace_InOwnTerritory_WithoutDanger(final int nCivID, final AI_RegoupArmyData nArmy, final boolean rebuildLine) {
      try {
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         try {
            if (CFG.game.getCiv(nCivID).getCivRegion(CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()).getProvincesSize() > 1) {
               Gdx.app.log("AoC", "regroupArmy_AtPeace_InOwnTerritory_WithoutDanger -> 111 -> " + nArmy.iArmy);
               int tMaxDL = 1;
               float tMaxPotential = 1.0f;
               final List<AI_ProvinceInfo> tempFrontProvinces = new ArrayList<AI_ProvinceInfo>();
               for (int i = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; i >= 0; --i) {
                  try {
                     if (CFG.game.getProvince(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(0)).getCivRegionID() == CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()) {
                        for (int j = CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.size() - 1; j >= 0; --j) {
                           boolean wasAdded = false;
                           for (int k = tempFrontProvinces.size() - 1; k >= 0; --k) {
                              if (tempFrontProvinces.get(k).iProvinceID == CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j)) {
                                 wasAdded = true;
                                 break;
                              }
                           }
                           if (!wasAdded) {
                              tempFrontProvinces.add(new AI_ProvinceInfo(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(j), nCivID), 1));
                           }
                        }
                     }
                  }
                  catch (final IndexOutOfBoundsException ex3) {}
               }
               Gdx.app.log("AoC", "regroupArmy_AtPeace_InOwnTerritory_WithoutDanger -> 111 -> tempFrontProvinces.size: " + tempFrontProvinces.size());
               if (tempFrontProvinces.size() > 0) {
                  int tMaxArmy = 1;
                  float tMaxRegion_NumOfProvinces = 1.0f;
                  float tMaxRegion_Potential = 1.0f;
                  final List<Integer> tMovingArmy = new ArrayList<Integer>();
                  int l = 0;
                  int iSize = tempFrontProvinces.size();
                  int tempMovingArmy = 0;
                  while (l < iSize) {
                     if (tempFrontProvinces.get(l).iValue > tMaxPotential) {
                        tMaxPotential = tempFrontProvinces.get(l).iValue;
                     }
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                        tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy();
                     }
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
                        tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getRegion_NumOfProvinces();
                     }
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
                        tMaxRegion_Potential = (float)CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getPotentialRegion();
                     }
                     tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(l).iProvinceID);
                     tMovingArmy.add(tempMovingArmy);
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
                        tMaxArmy = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy;
                     }
                     ++l;
                  }
                  for (l = 0, iSize = tempFrontProvinces.size(); l < iSize; ++l) {
                     tempFrontProvinces.get(l).iValue = this.getValue_PositionOfArmy(nCivID, tempFrontProvinces, l, tMovingArmy.get(l), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, nArmy.iArmy, tMaxRegion_NumOfProvinces);
                  }
                  final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
                  while (tempFrontProvinces.size() > 0) {
                     int tBest = 0;
                     for (int m = 1, iSize2 = tempFrontProvinces.size(); m < iSize2; ++m) {
                        if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(m).iValue) {
                           tBest = m;
                        }
                     }
                     sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
                     tempFrontProvinces.remove(tBest);
                  }
                  int iNumOfMaxMovements = 1;
                  if (rebuildLine) {
                     iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE, Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), 2 + CFG.oR.nextInt(3))));
                  }
                  else if (CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_DISBAND_IF_LESS_THAN_PERC > percOfArmyToRegroup) {
                     iNumOfMaxMovements = 1;
                  }
                  else {
                     iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE, Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), 2 + CFG.oR.nextInt(3))));
                  }
                  Gdx.app.log("AoC", "iNumOfMaxMovements: " + iNumOfMaxMovements);
                  final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
                  float totalValues = 0.0f;
                  for (int i2 = 0; i2 < iNumOfMaxMovements && i2 < sortedFrontProvinces.size(); ++i2) {
                     tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i2));
                     totalValues += sortedFrontProvinces.get(i2).iValue;
                  }
                  Gdx.app.log("AoC", "regroupArmy_AtPeace_InOwnTerritory_WithoutDanger -> 111 -> tRecruitArmiesForProvinces.size: " + tRecruitArmiesForProvinces.size());
                  for (int i2 = 0; i2 < tRecruitArmiesForProvinces.size(); ++i2) {
                     final int tArmyToRecruit_PRE = (int)Math.ceil(nArmy.iArmy * tRecruitArmiesForProvinces.get(i2).iValue / totalValues);
                     Gdx.app.log("AoC", "regroupArmy_AtPeace_InOwnTerritory_WithoutDanger -> 111 -> nArmy.iArmy: " + nArmy.iArmy + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
                     if (tArmyToRecruit_PRE <= 0) {
                        break;
                     }
                     final RegroupArmy_Data_AtPeace tryRegroupArmy = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID);
                     if (tryRegroupArmy.getRouteSize() > 0) {
                        if (tryRegroupArmy.getRouteSize() == 1) {
                           if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID, tArmyToRecruit_PRE, nCivID, true, false)) {}
                        }
                        else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToRecruit_PRE, nCivID, true, false)) {
                           tryRegroupArmy.setFromProvinceID(tryRegroupArmy.getRoute(0));
                           tryRegroupArmy.removeRoute(0);
                           tryRegroupArmy.setNumOfUnits(tArmyToRecruit_PRE);
                           CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy);
                        }
                     }
                  }
                  return true;
               }
            }
         }
         catch (final NullPointerException ex4) {}
         final List<AI_NeighProvinces> listOfPossibleProvinces = CFG.oAI.getAllNeighboringProvincesInRange_OnlyOwn_Clear(nArmy.iProvinceID, nCivID, Math.max(CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_MAX_PROVINCES, CFG.game.getCiv(nCivID).getNumOfProvinces() / 10), false, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
         if (listOfPossibleProvinces.size() > 0) {
            int nNumOfPossibleMovements = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            if (percOfArmyToRegroup > 0.375f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 4);
            }
            else if (percOfArmyToRegroup > 0.25f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 3);
            }
            else if (percOfArmyToRegroup > 0.1f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 2);
            }
            else {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 1);
            }
            boolean provincesWithDanger = false;
            for (int i = listOfPossibleProvinces.size() - 1; i >= 0; --i) {
               if (CFG.game.getProvince(listOfPossibleProvinces.get(i).iProvinceID).getDangerLevel() > 0) {
                  provincesWithDanger = true;
                  break;
               }
            }
            if (provincesWithDanger) {
               final List<Integer> tSortedIDs = new ArrayList<Integer>();
               final List<Integer> tData = new ArrayList<Integer>();
               for (int i3 = listOfPossibleProvinces.size() - 1; i3 >= 0; --i3) {
                  tData.add(i3);
               }
               while (tData.size() > 0) {
                  int tBest2 = 0;
                  for (int i4 = tData.size() - 1; i4 > 0; --i4) {
                     if (CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(tBest2)).iProvinceID).getDangerLevel_WithArmy() < CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(i4)).iProvinceID).getDangerLevel_WithArmy()) {
                        tBest2 = i4;
                     }
                  }
                  tSortedIDs.add(tData.get(tBest2));
                  tData.remove(tBest2);
               }
               int nDangerTotal = 0;
               for (int i4 = 0; i4 < nNumOfPossibleMovements && i4 < tSortedIDs.size(); ++i4) {
                  nDangerTotal += CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(i4)).iProvinceID).getDangerLevel_WithArmy();
               }
               int tIDOfFisrttSuccesfulMovement = -1;
               for (int l = 0; l < nNumOfPossibleMovements && l < tSortedIDs.size() && nArmy.iArmy > 0; ++l) {
                  RegroupArmy_Data_AtPeace tryRegroupArmy2 = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(l)).iProvinceID);
                  if (tryRegroupArmy2.getRouteSize() > 0) {
                     final int tArmyToMove = (l == nNumOfPossibleMovements || l == tSortedIDs.size() - 1) ? nArmy.iArmy : ((int)Math.ceil(nArmy.iArmy * (CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(l)).iProvinceID).getDangerLevel_WithArmy() / (float)nDangerTotal)));
                     nArmy.iArmy -= tArmyToMove;
                     if (tArmyToMove <= 0) {
                        break;
                     }
                     if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), tArmyToMove, nCivID, true, false)) {
                        if (tryRegroupArmy2.getRouteSize() > 1) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy2.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(l)).iProvinceID, tArmyToMove));
                        }
                        tIDOfFisrttSuccesfulMovement = l;
                     }
                  }
                  else if (tIDOfFisrttSuccesfulMovement >= 0) {
                     tryRegroupArmy2 = new RegroupArmy_Data_AtPeace(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID);
                     if (tryRegroupArmy2.getRouteSize() > 0 && CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
                        if (tryRegroupArmy2.getRouteSize() > 1) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy2.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID, nArmy.iArmy));
                        }
                        return true;
                     }
                  }
               }
               if (tIDOfFisrttSuccesfulMovement >= 0) {
                  return true;
               }
            }
            else {
               Gdx.app.log("AoC", "DISBAND -> 11111111 -> " + nArmy.iArmy);
               if (CFG.game.getProvince(nArmy.iProvinceID).getCivID() != nCivID) {
                  CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
               }
               else if (!CFG.game.getCiv(nCivID).getCivRegion(CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()).isKeyRegion) {
                  CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
               }
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
      return false;
   }

   protected final int getRegroupArmy_NumOfUnits(final int nCivID, final int nProvinceID) {
      int out = CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID);
      for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).iProvinceID == nProvinceID) {
            out -= CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).iArmy;
         }
      }
      return out;
   }

   protected final float getValue_PositionOfArmy(final int nCivID, final List<AI_ProvinceInfo> tempFrontProvinces, final int i, final int tMovingArmy, final float tMaxPotential, final float tMaxRegion_Potential, final int tMaxDL, final int tMaxArmy, final int numOfUnitsToRecruit_MAX, final float tMaxRegion_NumOfProvinces) {
      return CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_POTENTIAL * (tempFrontProvinces.get(i).iValue / tMaxPotential) + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_DANGER * (CFG.game.getProvince(tempFrontProvinces.get(i).iProvinceID).getDangerLevel_WithArmy() / (float)tMaxDL) * (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_NUM_OF_UNITS + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_NUM_OF_UNITS * (1.0f - (CFG.game.getProvince(tempFrontProvinces.get(i).iProvinceID).getArmy(0) + tMovingArmy) / (tMaxArmy + numOfUnitsToRecruit_MAX * CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_NUM_OF_UNITS_RECRUITMENT))) * (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_REGION_NUM_OF_PROVINCES + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_REGION_NUM_OF_PROVINCES * CFG.game.getProvince(tempFrontProvinces.get(i).iProvinceID).getRegion_NumOfProvinces() / tMaxRegion_NumOfProvinces - CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_REGION_POTENTIAL + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_REGION_POTENTIAL * CFG.game.getProvince(tempFrontProvinces.get(i).iProvinceID).getPotentialRegion() / tMaxRegion_Potential);
   }

   protected final void regroupArmyAfterRecruitment(final int nCivID) {
      for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.REGRUOP_AFTER_RECRUIT && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).canMakeAction(nCivID, 0) && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).action(nCivID)) {
            CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
         }
      }
   }

   protected final void defendFromSeaInvasion(final int nCivID) {
      final List<Integer> provincesToDefend = new ArrayList<Integer>();
      final List<Integer> toArmies = new ArrayList<Integer>();
      for (int i = CFG.game.getCiv(nCivID).isAtWarWithCivs.size() - 1; i >= 0; --i) {
         for (int j = 0; j < CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnitsSize(); ++j) {
            if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnits(j).getToProvinceID()).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnits(j).getFromProvinceID()).getSeaProvince() && !CFG.game.getProvince(CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnits(j).getToProvinceID()).isOccupied()) {
               provincesToDefend.add(CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnits(j).getToProvinceID());
               toArmies.add(CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getMoveUnits(j).getNumOfUnits());
            }
         }
      }
      while (provincesToDefend.size() > 0) {
         int tBest = 0;
         for (int j = 1; j < provincesToDefend.size(); ++j) {
            if (CFG.game.getProvince(provincesToDefend.get(tBest)).getPotential() < CFG.game.getProvince(provincesToDefend.get(j)).getPotential()) {
               tBest = j;
            }
         }
         if (CFG.game.getProvince(provincesToDefend.get(tBest)).getArmyCivID(nCivID) < toArmies.get(tBest)) {
            int requiredArmy = toArmies.get(tBest) - CFG.game.getProvince(provincesToDefend.get(tBest)).getArmyCivID(nCivID);
            requiredArmy = (int)Math.ceil(requiredArmy * (1.025f + CFG.oR.nextInt(85) / 1000.0f));
            if (CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT) {
               if (CFG.game.getCiv(nCivID).getMoney() < 5 * requiredArmy && CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT + 6) {
                  final int toTake = (int)(5 * requiredArmy - CFG.game.getCiv(nCivID).getMoney());
                  if (CFG.game.getCiv(nCivID).getMoney() + toTake > 5L) {
                     DiplomacyManager.takeLoan(nCivID, toTake, 5);
                  }
               }
               if (CFG.game.getCiv(nCivID).getMoney() <= 5L || CFG.game.getCiv(nCivID).recruitArmy_AI(provincesToDefend.get(tBest), requiredArmy)) {}
            }
         }
         provincesToDefend.remove(tBest);
         toArmies.remove(tBest);
         if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
            break;
         }
      }
   }

   protected final void moveAtWar(final int nCivID) {
      Gdx.app.log("AoC", "moveAtWar -> " + CFG.game.getCiv(nCivID).getCivName());
      try {
         boolean haveOwnFront = false;
         final List<AI_ProvinceInfo_War> tempFrontProvinces = new ArrayList<AI_ProvinceInfo_War>();
         for (int i = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; i >= 0; --i) {
            if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(nCivID - 1).get(i).iWithCivID)) {
               haveOwnFront = true;
               for (int k = CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.size() - 1; k >= 0; --k) {
                  boolean add = true;
                  for (int o = tempFrontProvinces.size() - 1; o >= 0; --o) {
                     if (tempFrontProvinces.get(o).iProvinceID == CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k)) {
                        add = false;
                        break;
                     }
                  }
                  if (add) {
                     tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k), nCivID), true));
                  }
               }
            }
         }
         for (int o2 = 0; o2 < CFG.game.getCiv(nCivID).civGameData.iVassalsSize; ++o2) {
            //bugfix change so no index out of bounds when transferring territory//
            try {
               for (int j = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).size() - 1; j >= 0; --j) {
                  if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).iWithCivID)) {
                     for (int l = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.size() - 1; l >= 0; --l) {
                        boolean add2 = true;
                        for (int u = tempFrontProvinces.size() - 1; u >= 0; --u) {
                           if (tempFrontProvinces.get(u).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l)) {
                              add2 = false;
                              break;
                           }
                        }
                        if (add2) {
                           tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l), CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID), false));
                        }
                     }
                  }
               }
            } catch (IndexOutOfBoundsException e) {
               continue;
            }
         }
         if (CFG.game.getCiv(nCivID).getPuppetOfCivID() != nCivID) {
            for (int i = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).size() - 1; i >= 0; --i) {
               if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).iWithCivID)) {
                  for (int k = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.size() - 1; k >= 0; --k) {
                     boolean add = true;
                     for (int o = tempFrontProvinces.size() - 1; o >= 0; --o) {
                        if (tempFrontProvinces.get(o).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k)) {
                           add = false;
                           break;
                        }
                     }
                     if (add) {
                        tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k), CFG.game.getCiv(nCivID).getPuppetOfCivID()), false));
                     }
                  }
               }
            }
         }
         if (CFG.game.getCiv(nCivID).getAllianceID() > 0) {
            for (int o2 = 0; o2 < CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize(); ++o2) {
               if (CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) != nCivID) {
                  for (int j = CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).size() - 1; j >= 0; --j) {
                     if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).iWithCivID)) {
                        for (int l = CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.size() - 1; l >= 0; --l) {
                           boolean add2 = true;
                           for (int u = tempFrontProvinces.size() - 1; u >= 0; --u) {
                              if (tempFrontProvinces.get(u).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l)) {
                                 add2 = false;
                                 break;
                              }
                           }
                           if (add2) {
                              tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l), CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2)), false));
                           }
                        }
                     }
                  }
               }
            }
         }
         Gdx.app.log("AoC", "CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.size(): " + CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.size());
         for (int e = CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.size() - 1; e >= 0; --e) {
            Gdx.app.log("AoC", "CFG.game.getCiv(nCivID).lOpt_MilitirayAccess" + e + ": " + CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e) + " " + CFG.game.getCiv(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).getCivName());
            for (int j = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).size() - 1; j >= 0; --j) {
               if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).get(j).iWithCivID)) {
                  for (int l = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).get(j).lProvinces.size() - 1; l >= 0; --l) {
                     boolean add2 = true;
                     for (int o3 = tempFrontProvinces.size() - 1; o3 >= 0; --o3) {
                        if (tempFrontProvinces.get(o3).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).get(j).lProvinces.get(l)) {
                           add2 = false;
                           break;
                        }
                     }
                     if (add2) {
                        tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).get(j).lProvinces.get(l), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e)).get(j).lProvinces.get(l), CFG.game.getCiv(nCivID).lOpt_MilitirayAccess.get(e) + 1), false));
                     }
                  }
               }
            }
         }
         Gdx.app.log("AoC", "moveAtWar -> tempFrontProvinces.size: " + tempFrontProvinces.size());
         if (tempFrontProvinces.size() > 0) {
            int tMaxDL = 1;
            float tMaxPotential = 1.0f;
            final List<Integer> tMovingArmy_toFrontProvince = new ArrayList<Integer>();
            int tMaxArmy = 1;
            float tMaxRegion_NumOfProvinces = 1.0f;
            float tMaxRegion_Potential = 1.0f;
            final List<Integer> lFrontIDsWithArmies = new ArrayList<Integer>();
            int m = tempFrontProvinces.size() - 1;
            int tempMovingArmy = 0;
            while (m >= 0) {
               if (tempFrontProvinces.get(m).iValue > tMaxPotential) {
                  tMaxPotential = tempFrontProvinces.get(m).iValue;
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                  tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy();
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
                  tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces();
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
                  tMaxRegion_Potential = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion();
               }
               tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(m).iProvinceID);
               tMovingArmy_toFrontProvince.add(tempMovingArmy);
               if (CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tempMovingArmy > tMaxArmy) {
                  tMaxArmy = CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tempMovingArmy;
               }
               --m;
            }
            for (m = tempFrontProvinces.size() - 1; m >= 0; --m) {
               tempFrontProvinces.get(m).iValue = (CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_POTENTIAL * (tempFrontProvinces.get(m).iValue / tMaxPotential) + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_DANGER * (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy() / (float)tMaxDL) + (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_NUM_OF_PROVINCES + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_NUM_OF_PROVINCES * CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces() / tMaxRegion_NumOfProvinces - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_POTENTIAL + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_POTENTIAL * CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion() / tMaxRegion_Potential)) * (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_ATTACK_DISTANCE * CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game_NextTurnUpdate.getAdministration_Capital(nCivID), tempFrontProvinces.get(m).iProvinceID)) + (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_NUM_OF_UNITS + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_NUM_OF_UNITS * (1.0f - (CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tMovingArmy_toFrontProvince.get(m)) / (float)tMaxArmy) * ((CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getNeighbooringProvinceOfCivWasLost() > 0) ? (0.55f + CFG.oR.nextInt(30) / 100.0f) : 1.0f));
            }
            final List<AI_ProvinceInfo_War> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo_War>();
            int tID = 0;
            while (tempFrontProvinces.size() > 0) {
               int tBest = 0;
               for (int i2 = 1, iSize = tempFrontProvinces.size(); i2 < iSize; ++i2) {
                  if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(i2).iValue) {
                     tBest = i2;
                  }
                  else if (tempFrontProvinces.get(tBest).iValue == tempFrontProvinces.get(i2).iValue && CFG.oR.nextInt(100) < 50) {
                     tBest = i2;
                  }
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(tBest).iProvinceID).getArmyCivID(nCivID) > 0) {
                  lFrontIDsWithArmies.add(tID);
               }
               sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
               tempFrontProvinces.remove(tBest);
               ++tID;
            }
            this.moveAtWar_Regroup(nCivID, sortedFrontProvinces, lFrontIDsWithArmies);
            Gdx.app.log("AoC", "moveAtWar -> BEFORE RECRUIT MP: " + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
            if (CFG.game.getCiv(nCivID).getMoney() > 5L && CFG.game.getCiv(nCivID).iBudget > 0) {
               final boolean canRecruitAndMove = lFrontIDsWithArmies.size() * 1.75f * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE <= CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT;
               if (canRecruitAndMove || CFG.game.getCiv(nCivID).getMoney() / 5L * ((CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn < 0) ? (0.16f + 0.03f * CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn) : (CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn ? 0.6f : 0.75f)) > CFG.game.getCiv(nCivID).getNumOfUnits() || CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn < -3 || CFG.game.getCiv(nCivID).getNumOfProvinces() < 3 || CFG.oR.nextInt(100) < 6) {
                  Gdx.app.log("AoC", "moveAtWar -> BEFORE RECRUIT 000: true");
                  this.moveAtWar_Recruit(nCivID, sortedFrontProvinces, lFrontIDsWithArmies, false);
               }
               else {
                  Gdx.app.log("AoC", "moveAtWar -> BEFORE RECRUIT 000: false");
               }
               CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn = false;
            }
            Gdx.app.log("AoC", "moveAtWar -> AFTER RECRUIT -> ATTACK MP: " + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
            final int numOfPossibleMoves = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            if (numOfPossibleMoves > 0) {
               final List<Float> lScores = new ArrayList<Float>();
               float score_MaxArmy = 1.0f;
               float score_MaxPotenialProvinces = 1.0f;
               for (int i3 = lFrontIDsWithArmies.size() - 1; i3 >= 0; --i3) {
                  if (CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getArmyCivID(nCivID) < 10) {
                     lFrontIDsWithArmies.remove(i3);
                  }
               }
               for (int i3 = lFrontIDsWithArmies.size() - 1; i3 >= 0; --i3) {
                  if (score_MaxArmy < CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getArmyCivID(nCivID)) {
                     score_MaxArmy = (float)CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getArmyCivID(nCivID);
                  }
                  if (score_MaxPotenialProvinces < CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getPotentialModified_WAR_MoveFrom(nCivID)) {
                     score_MaxPotenialProvinces = (float)CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getPotentialModified_WAR_MoveFrom(nCivID);
                  }
               }
               for (int i3 = 0; i3 < lFrontIDsWithArmies.size(); ++i3) {
                  lScores.add(CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_ATTACK_SCORE_ARMY * CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getArmyCivID(nCivID) / score_MaxArmy + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_ATTACK_SCORE_POTENTIAL * CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getPotentialModified_WAR_MoveFrom(nCivID) / score_MaxPotenialProvinces + ((CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getWasConquered() > 0) ? CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_ATTACK_SCORE_WAS_CONQUERED : 0.0f) + ((CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getIsNotSuppliedForXTurns() > 0) ? (0.275f + 2.5f * CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(i3)).iProvinceID).getArmyCivID(nCivID) / score_MaxArmy) : 0.0f));
               }
               final List<Integer> tSorted = new ArrayList<Integer>();
               final List<Integer> tempIDs = new ArrayList<Integer>();
               for (int i4 = lFrontIDsWithArmies.size() - 1; i4 >= 0; --i4) {
                  tempIDs.add(i4);
               }
               while (tempIDs.size() > 0) {
                  int tBest2 = 0;
                  for (int i5 = tempIDs.size() - 1; i5 > 0; --i5) {
                     if (lScores.get(tempIDs.get(tBest2)) < lScores.get(tempIDs.get(i5))) {
                        tBest2 = i5;
                     }
                  }
                  tSorted.add(tempIDs.get(tBest2));
                  tempIDs.remove(tBest2);
               }
               Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. MP: " + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
               Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. tSorted.size(): " + tSorted.size());
               for (int i4 = 0, iSize2 = tSorted.size(); i4 < iSize2; ++i4) {
                  lScores.clear();
                  tempIDs.clear();
                  if (CFG.oR.nextInt(100) < 65) {
                     for (int j2 = 0; j2 < CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvincesSize(); ++j2) {
                        if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2)).getCivID()) && !CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2))) {
                           tempIDs.add(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2));
                           lScores.add(this.moveAtWar_AttackTo_Score(nCivID, CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2)));
                        }
                     }
                  }
                  if (tempIDs.size() == 0) {
                     for (int j2 = 0; j2 < CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvincesSize(); ++j2) {
                        if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2)).getCivID())) {
                           tempIDs.add(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2));
                           lScores.add(this.moveAtWar_AttackTo_Score(nCivID, CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2)) * (CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getNeighboringProvinces(j2)) ? 0.625f : 1.0f));
                        }
                     }
                  }
                  Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. tempIDs.size(): " + tempIDs.size());
                  if (this.plunderProvince(nCivID, sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID)) {
                     int maxArmy = this.getRegroupArmy_NumOfUnits(nCivID, sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID);
                     maxArmy = (int)Math.max(DiplomacyManager.plunderEfficiency_RequiredMAX(nCivID, sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID), (float)maxArmy);
                     DiplomacyManager.plunderProvince(nCivID, sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID, maxArmy);
                  }
                  if (tempIDs.size() > 0) {
                     if (tempIDs.size() > 1 && CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * 2) {
                        Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 0000");
                        int tArmyToMove = this.getRegroupArmy_NumOfUnits(nCivID, sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID);
                        if (tArmyToMove > 0) {
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. tArmyToMove: " + tArmyToMove);
                           int numOfMoves = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. numOfMoves: " + numOfMoves);
                           final List<Integer> sortedMoveTo = new ArrayList<Integer>();
                           final List<Integer> tData = new ArrayList<Integer>();
                           for (int o4 = lScores.size() - 1; o4 >= 0; --o4) {
                              tData.add(o4);
                           }
                           while (tData.size() > 0) {
                              int tBest3 = 0;
                              for (int o5 = tData.size() - 1; o5 > 0; --o5) {
                                 if (lScores.get(tData.get(tBest3)) < lScores.get(tData.get(o5))) {
                                    tBest3 = o5;
                                 }
                              }
                              sortedMoveTo.add(tData.get(tBest3));
                              tData.remove(tBest3);
                           }
                           numOfMoves = Math.min(numOfMoves, tempIDs.size());
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. numOfMoves2: " + numOfMoves);
                           float totalScore = 0.0f;
                           for (int o5 = 0; o5 < sortedMoveTo.size(); ++o5) {
                              totalScore += lScores.get(sortedMoveTo.get(o5));
                           }
                           final List<Boolean> checkJoinProvinces = new ArrayList<Boolean>();
                           for (int o6 = 0; o6 < numOfMoves; ++o6) {
                              Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> ATTACK ID: " + o6 + ", lScores.get(o): " + lScores.get(o6) + ", totalScore: " + totalScore);
                              int armyToMove_PRE = (int)Math.ceil(tArmyToMove * lScores.get(o6) / totalScore);
                              if (CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getWasAttacked() > 0 || (CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getLevelOfWatchTower() > 0 && CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getLevelOfFort() <= 0)) {
                                 Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 000");
                                 final int enemyArmy = (int)((CFG.game.getProvinceArmy(tempIDs.get(sortedMoveTo.get(o6))) + this.getEnemyArmy_ExtraMovedArmy(tempIDs.get(sortedMoveTo.get(o6)))) * 1.05f);
                                 if (armyToMove_PRE < enemyArmy) {
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 111");
                                    if (CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID) > enemyArmy) {
                                       Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 222");
                                       armyToMove_PRE = (int)Math.min((float)CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID), CFG.game.getProvinceArmy(tempIDs.get(sortedMoveTo.get(o6))) * (1.04f + CFG.oR.nextInt(20) / 100.0f));
                                       tArmyToMove -= armyToMove_PRE;
                                       totalScore = Math.max(1.0f, totalScore - lScores.get(o6));
                                    }
                                    else {
                                       Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 333");
                                       if (enemyArmy < CFG.game.getProvinceArmy(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID)) {
                                          Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 444");
                                       }
                                       else {
                                          Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 555");
                                          int armyJoinProvinces = 0;
                                          for (int m2 = 0; m2 < CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getNeighboringProvincesSize(); ++m2) {
                                             if (CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getNeighboringProvinces(m2) != sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID) {
                                                armyJoinProvinces += CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getNeighboringProvinces(m2)).getArmyCivID(nCivID);
                                             }
                                          }
                                          if (enemyArmy >= armyToMove_PRE + armyJoinProvinces) {
                                             Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 777, CONTINUE");
                                             checkJoinProvinces.add(false);
                                             continue;
                                          }
                                          Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 666");
                                       }
                                    }
                                 }
                                 else {
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 888");
                                 }
                              }
                              else {
                                 Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> CHECK 999");
                              }
                              checkJoinProvinces.add(true);
                              if (!CFG.gameAction.moveArmy(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID, tempIDs.get(sortedMoveTo.get(o6)), armyToMove_PRE, nCivID, true, false)) {
                                 Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 -> ATTACK ID: " + o6 + ", break");
                                 break;
                              }
                              Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 FROM: " + sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID + " -> " + CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getName() + " TO: " + tempIDs.get(sortedMoveTo.get(o6)) + " -> " + CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o6))).getName());
                           }
                           if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_SAME_PROVINCE <= CFG.game.getCiv(nCivID).getMovePoints()) {
                              for (int k2 = 0; k2 < checkJoinProvinces.size(); ++k2) {
                                 if (checkJoinProvinces.get(k2)) {
                                    for (int o7 = 0; o7 < numOfMoves; ++o7) {
                                       for (int m3 = 0; m3 < CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvincesSize(); ++m3) {
                                          if (CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3)).getArmyCivID(nCivID) > 0 && (CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3)).getCivID() != nCivID || this.moveAtWar_NumOfNotCoveredNeighEnemyProvinces(nCivID, CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3)) <= 1)) {
                                             if (CFG.gameAction.moveArmy(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3), tempIDs.get(sortedMoveTo.get(o7)), CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3)).getArmyCivID(nCivID), nCivID, true, false)) {
                                                Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 000 JOIN: " + CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3) + " -> " + CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getNeighboringProvinces(m3)).getName() + " TO: " + tempIDs.get(sortedMoveTo.get(o7)) + " -> " + CFG.game.getProvince(tempIDs.get(sortedMoveTo.get(o7))).getName());
                                             }
                                             else if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_SAME_PROVINCE) {
                                                break;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                     else {
                        int tBestMoveTo = 0;
                        for (int o8 = lScores.size() - 1; o8 > 0; --o8) {
                           if (lScores.get(tBestMoveTo) < lScores.get(o8)) {
                              tBestMoveTo = o8;
                           }
                        }
                        Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 1111");
                        Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 1111 FROM: " + sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID + " -> " + CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getName() + ", TO: " + tempIDs.get(tBestMoveTo) + " -> " + CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getName() + ", ARMY: " + CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID));
                        float totalScore2 = 0.0f;
                        for (int k3 = tempIDs.size() - 1; k3 >= 0; --k3) {
                           if (!CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID(tempIDs.get(k3))) {
                              totalScore2 += lScores.get(k3);
                           }
                        }
                        int armyToMove_PRE2;
                        if (totalScore2 > 0.0f && CFG.oR.nextInt(100) < 90) {
                           armyToMove_PRE2 = (int)Math.ceil(CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID) * lScores.get(tBestMoveTo) / totalScore2);
                        }
                        else {
                           armyToMove_PRE2 = CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID);
                        }
                        if (CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getWasAttacked() > 0 || (CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getLevelOfWatchTower() > 0 && CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getLevelOfFort() <= 0)) {
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 000");
                           final int enemyArmy2 = (int)((CFG.game.getProvinceArmy(tempIDs.get(tBestMoveTo)) + this.getEnemyArmy_ExtraMovedArmy(tempIDs.get(tBestMoveTo))) * 1.05f);
                           if (armyToMove_PRE2 < enemyArmy2) {
                              Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 111");
                              if (CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID) > enemyArmy2) {
                                 Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 222");
                                 armyToMove_PRE2 = (int)Math.min((float)CFG.game.getProvince(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID).getArmyCivID(nCivID), CFG.game.getProvinceArmy(tempIDs.get(tBestMoveTo)) * (1.04f + CFG.oR.nextInt(20) / 100.0f));
                              }
                              else {
                                 Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 333");
                                 if (enemyArmy2 < CFG.game.getProvinceArmy(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID)) {
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 444");
                                 }
                                 else {
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 555");
                                    int armyJoinProvinces2 = 0;
                                    for (int m4 = 0; m4 < CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvincesSize(); ++m4) {
                                       if (CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m4) != sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID) {
                                          armyJoinProvinces2 += CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m4)).getArmyCivID(nCivID);
                                       }
                                    }
                                    if (enemyArmy2 >= armyToMove_PRE2 + armyJoinProvinces2) {
                                       Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 777, CONTINUE");
                                       continue;
                                    }
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 666");
                                 }
                              }
                           }
                           else {
                              Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 888");
                           }
                        }
                        else {
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 111 -> CHECK 999");
                        }
                        if (CFG.gameAction.moveArmy(sortedFrontProvinces.get(lFrontIDsWithArmies.get(tSorted.get(i4))).iProvinceID, tempIDs.get(tBestMoveTo), armyToMove_PRE2, nCivID, true, false)) {
                           Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 1111 MOVED");
                           for (int m5 = 0; m5 < CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvincesSize(); ++m5) {
                              if (CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5)).getArmyCivID(nCivID) > 0 && (CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5)).getCivID() != nCivID || this.moveAtWar_NumOfNotCoveredNeighEnemyProvinces(nCivID, CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5)) <= 1)) {
                                 if (CFG.gameAction.moveArmy(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5), tempIDs.get(tBestMoveTo), CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5)).getArmyCivID(nCivID), nCivID, true, false)) {
                                    Gdx.app.log("AoC", "moveAtWar -> START ATTACKING.. 222 FROM: " + CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5) + " -> " + CFG.game.getProvince(CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getNeighboringProvinces(m5)).getName() + " TO: " + tempIDs.get(tBestMoveTo) + " -> " + CFG.game.getProvince(tempIDs.get(tBestMoveTo)).getName());
                                 }
                                 else if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
                                    break;
                                 }
                              }
                           }
                        }
                        else if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
                           break;
                        }
                     }
                  }
                  Gdx.app.log("AoC", "moveAtWar -> START ATTACKING END");
               }
            }
         }
         else if (CFG.game.getCiv(nCivID).civGameData.iNextCheckMilitaryAccessTurnID <= Game_Calendar.TURN_ID && CFG.oR.nextInt(100) < 72) {
            final List<Integer> askForAccess = new ArrayList<Integer>();
            for (int j = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; j >= 0; --j) {
               if (!CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID)) {
                  for (int j3 = CFG.oAI.lFrontLines.get(CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID - 1).size() - 1; j3 >= 0; --j3) {
                     if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID - 1).get(j3).iWithCivID) && (CFG.game.getCiv(nCivID).iBudget > CFG.game.getCiv(CFG.oAI.lFrontLines.get(CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID - 1).get(j3).iWithCivID).iBudget || CFG.oR.nextInt(100) < 6)) {
                        boolean wasAdded = false;
                        for (int z = askForAccess.size() - 1; z >= 0; --z) {
                           if (askForAccess.get(z) == CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID) {
                              wasAdded = true;
                              break;
                           }
                        }
                        if (!wasAdded) {
                           askForAccess.add(CFG.oAI.lFrontLines.get(nCivID - 1).get(j).iWithCivID);
                        }
                     }
                  }
               }
            }
            if (askForAccess.size() > 0) {
               while (askForAccess.size() > 0 && CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 10) {
                  final int tRand = CFG.oR.nextInt(askForAccess.size());
                  if (CFG.game.getMilitaryAccess(nCivID, askForAccess.get(tRand)) <= 10 && !CFG.game.getCiv(nCivID).messageWasSent(askForAccess.get(tRand), Message_Type.MILITARY_ACCESS_ASK)) {
                     DiplomacyManager.sendMilitaryAccess_AskProposal(askForAccess.get(tRand), nCivID, 40);
                  }
                  askForAccess.remove(tRand);
               }
               CFG.game.getCiv(nCivID).civGameData.iNextCheckMilitaryAccessTurnID = Game_Calendar.TURN_ID + 6 + CFG.oR.nextInt(20);
            }
         }
         else if (CFG.game.getCiv(nCivID).civGameData.iNextCheckMilitaryAccessSeaTurnID <= Game_Calendar.TURN_ID) {
            final List<Integer> askForAccess2 = new ArrayList<Integer>();
            for (int j = CFG.game.getCiv(nCivID).isAtWarWithCivs.size() - 1; j >= 0; --j) {
               if (CFG.game.getCiv(nCivID).iBudget > CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j)).iBudget && CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j)).getSeaAccess() == 0) {
                  for (int z2 = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j) - 1).size() - 1; z2 >= 0; --z2) {
                     if (!CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j) - 1).get(z2).iWithCivID) && CFG.game.getCiv(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j) - 1).get(z2).iWithCivID).getSeaAccess() > 0) {
                        boolean wasAdded = false;
                        for (int o3 = askForAccess2.size() - 1; o3 >= 0; --o3) {
                           if (askForAccess2.get(o3) == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j) - 1).get(z2).iWithCivID) {
                              wasAdded = true;
                              break;
                           }
                        }
                        if (!wasAdded) {
                           askForAccess2.add(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(j) - 1).get(z2).iWithCivID);
                        }
                     }
                  }
               }
            }
            if (askForAccess2.size() > 0) {
               while (askForAccess2.size() > 0 && CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 10) {
                  final int tRand = CFG.oR.nextInt(askForAccess2.size());
                  if (CFG.game.getMilitaryAccess(nCivID, askForAccess2.get(tRand)) <= 10 && !CFG.game.getCiv(nCivID).messageWasSent(askForAccess2.get(tRand), Message_Type.MILITARY_ACCESS_ASK)) {
                     DiplomacyManager.sendMilitaryAccess_AskProposal(askForAccess2.get(tRand), nCivID, 40);
                  }
                  askForAccess2.remove(tRand);
               }
            }
            CFG.game.getCiv(nCivID).civGameData.iNextCheckMilitaryAccessSeaTurnID = Game_Calendar.TURN_ID + 6 + CFG.oR.nextInt(20);
         }
         this.moveAtWar_AtSea(nCivID);
      }
      catch (final IndexOutOfBoundsException ex) {
         Gdx.app.log("AoC", "moveAtWar -> ERRRORR");
         CFG.exceptionStack(ex);
      }
      catch (final ArithmeticException ex2) {
         Gdx.app.log("AoC", "moveAtWar -> ERRRORR");
         CFG.exceptionStack(ex2);
      }
      Gdx.app.log("AoC", "moveAtWar -> END, movementPoints:" + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
   }

   protected final boolean plunderProvince(final int nCivID, final int nProvinceID) {
      if (CFG.game.getProvince(nProvinceID).isOccupied() && CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) < CFG.game.getCiv(nCivID).getNumOfUnits() * 0.235f && CFG.game.getCiv(nCivID).civGameData.iPlunder_LastTurnID <= Game_Calendar.TURN_ID) {
         final int possibleArmy = this.getRegroupArmy_NumOfUnits(nCivID, nProvinceID);
         if (possibleArmy / DiplomacyManager.plunderEfficiency_RequiredMAX(nCivID, nProvinceID) > 0.45f && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_PLUNDER <= CFG.game.getCiv(nCivID).getMovePoints()) {
            if (CFG.game.getCiv(nCivID).civGameData.civPersonality.PLUNDER_CHANCE > CFG.oR.nextInt(1000) / 1000.0f) {
               if (CFG.oR.nextInt(100) < this.PERSONALITY_PLUNDER_LOCK) {
                  CFG.game.getCiv(nCivID).civGameData.iPlunder_LastTurnID = Game_Calendar.TURN_ID + 2 + CFG.oR.nextInt(4);
               }
               else {
                  CFG.game.getCiv(nCivID).civGameData.iPlunder_LastTurnID = Game_Calendar.TURN_ID + 2 + CFG.oR.nextInt(4);
               }
               return true;
            }
            return true;
         }
      }
      return false;
   }

   protected final int getEnemyArmy_ExtraMovedArmy(final int nProvinceID) {
      int out = 0;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getCivsSize(); ++i) {
         for (int j = 0; j < CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID(i)).getMoveUnitsSize(); ++j) {
            if (CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID(i)).getMoveUnits(j).getFromProvinceID() == nProvinceID) {
               out += CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID(i)).getMoveUnits(j).getNumOfUnits();
            }
         }
      }
      return out;
   }

   protected final int moveAtWar_AtSea_RunMissions(final int nCivID) {
      int outActiveMissions = 0;
      for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.NAVAL_INVASION && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).canMakeAction(nCivID, 0)) {
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).action(nCivID)) {
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
            }
            else if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).iObsolate <= 0) {
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
            }
            else {
               ++outActiveMissions;
            }
         }
      }
      return outActiveMissions;
   }

   protected final void moveAtWar_AtSea(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) {
         Gdx.app.log("AoC", "moveAtWar -> BY SEA");
         final int numOfCurrentInvasions = this.moveAtWar_AtSea_RunMissions(nCivID);
         Gdx.app.log("AoC", "moveAtWar -> BY SEA -> numOfCurrentInvasions: " + numOfCurrentInvasions);
         if (Game_Calendar.TURN_ID <= CFG.game.getCiv(nCivID).civGameData.iNextPossibleNavalInvastionTurnID) {
            return;
         }
         if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0 && numOfCurrentInvasions > 0) {
            return;
         }
         if (numOfCurrentInvasions >= Math.max(1.0f, CFG.game.getCiv(nCivID).getNumOfProvinces() / 10.0f)) {
            return;
         }
         if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) {
            Gdx.app.log("AoC", "moveAtWar -> BY SEA -> NO NEIGHBORING ENEMIES, LOOK FOR SEA PROVINCES");
            final boolean canBuildPort = BuildingsManager.canBuildPort(CFG.game.getCiv(nCivID).getProvinceID(0));
            if (CFG.game.getCiv(nCivID).getSeaAccess_PortProvinces_Size() == 0 && !canBuildPort) {
               Gdx.app.log("AoC", "moveAtWar -> BY SEA -> CANT GO TO THE SEA..");
               return;
            }
            if (CFG.game.getCiv(nCivID).getSeaAccess() > 0) {
               final List<Integer> civsAtWarWithSeaAccessToo = new ArrayList<Integer>();
               for (int i = CFG.game.getCiv(nCivID).isAtWarWithCivs.size() - 1; i >= 0; --i) {
                  if (CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).getSeaAccess() > 0) {
                     civsAtWarWithSeaAccessToo.add(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i));
                  }
               }
               if (civsAtWarWithSeaAccessToo.size() > 0) {
                  final List<Boolean> haveAccessToBasins = new ArrayList<Boolean>();
                  final List<List<Integer>> possibleProvinceMoveTo_OwnProvinces = new ArrayList<List<Integer>>();
                  final List<List<Integer>> possibleProvinceMoveTo = new ArrayList<List<Integer>>();
                  for (int j = 0; j < CFG.map.iNumOfBasins; ++j) {
                     haveAccessToBasins.add(false);
                     possibleProvinceMoveTo_OwnProvinces.add(new ArrayList<Integer>());
                     possibleProvinceMoveTo.add(new ArrayList<Integer>());
                  }
                  if (!canBuildPort) {
                     for (int j = CFG.game.getCiv(nCivID).getSeaAccess_PortProvinces_Size() - 1; j >= 0; --j) {
                        for (int k = 0; k < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_PortProvinces().get(j)).getNeighboringSeaProvincesSize(); ++k) {
                           haveAccessToBasins.set(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_PortProvinces().get(j)).getNeighboringSeaProvinces(k)).getBasinID(), true);
                        }
                     }
                  }
                  else {
                     for (int j = CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size() - 1; j >= 0; --j) {
                        for (int k = 0; k < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j)).getNeighboringSeaProvincesSize(); ++k) {
                           haveAccessToBasins.set(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j)).getNeighboringSeaProvinces(k)).getBasinID(), true);
                        }
                     }
                  }
                  int possibleMoveTo_OwnProvinces = 0;
                  int possibleMoveTo = 0;
                  for (int l = civsAtWarWithSeaAccessToo.size() - 1; l >= 0; --l) {
                     for (int m = CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces_Size() - 1; m >= 0; --m) {
                        for (int k2 = 0; k2 < CFG.game.getProvince(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m)).getNeighboringSeaProvincesSize(); ++k2) {
                           if (haveAccessToBasins.get(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m)).getNeighboringSeaProvinces(k2)).getBasinID())) {
                              if (CFG.game.getProvince(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m)).getTrueOwnerOfProvince() == nCivID) {
                                 possibleProvinceMoveTo_OwnProvinces.get(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m)).getNeighboringSeaProvinces(k2)).getBasinID()).add(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m));
                                 ++possibleMoveTo_OwnProvinces;
                              }
                              else {
                                 possibleProvinceMoveTo.get(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m)).getNeighboringSeaProvinces(k2)).getBasinID()).add(CFG.game.getCiv(civsAtWarWithSeaAccessToo.get(l)).getSeaAccess_Provinces().get(m));
                                 ++possibleMoveTo;
                              }
                           }
                        }
                     }
                  }
                  Gdx.app.log("AoC", "moveAtWar -> BY SEA -> possibleMoveTo_OwnProvinces: " + possibleMoveTo_OwnProvinces);
                  Gdx.app.log("AoC", "moveAtWar -> BY SEA -> possibleMoveTo: " + possibleMoveTo);
                  if (possibleMoveTo + possibleMoveTo_OwnProvinces == 0) {
                     return;
                  }
                  int iBestProvinceID_MoveTo = -1;
                  float fBestProvinceID_MoveTo_Score = -1.0f;
                  if (possibleMoveTo > 0 || possibleMoveTo_OwnProvinces > 0) {
                     float tempScore = 0.0f;
                     for (int i2 = possibleProvinceMoveTo_OwnProvinces.size() - 1; i2 >= 0; --i2) {
                        for (int j2 = possibleProvinceMoveTo_OwnProvinces.get(i2).size() - 1; j2 >= 0; --j2) {
                           tempScore = this.moveAtWar_AtSea_ToProvinceID_Score(nCivID, possibleProvinceMoveTo_OwnProvinces.get(i2).get(j2), true);
                           if (tempScore > fBestProvinceID_MoveTo_Score) {
                              iBestProvinceID_MoveTo = possibleProvinceMoveTo_OwnProvinces.get(i2).get(j2);
                              fBestProvinceID_MoveTo_Score = tempScore;
                           }
                        }
                     }
                     for (int i2 = possibleProvinceMoveTo.size() - 1; i2 >= 0; --i2) {
                        for (int j2 = possibleProvinceMoveTo.get(i2).size() - 1; j2 >= 0; --j2) {
                           tempScore = this.moveAtWar_AtSea_ToProvinceID_Score(nCivID, possibleProvinceMoveTo.get(i2).get(j2), false);
                           if (tempScore > fBestProvinceID_MoveTo_Score) {
                              iBestProvinceID_MoveTo = possibleProvinceMoveTo.get(i2).get(j2);
                              fBestProvinceID_MoveTo_Score = tempScore;
                           }
                        }
                     }
                     if (iBestProvinceID_MoveTo >= 0) {
                        if (!CFG.game.isAlly(nCivID, CFG.game.getProvince(iBestProvinceID_MoveTo).getCivID()) && CFG.game.getCiv(nCivID).getRankPosition() > CFG.game.getCiv(CFG.game.getProvince(iBestProvinceID_MoveTo).getCivID()).getRankPosition() && CFG.oR.nextInt(100) < 62) {
                           CFG.game.getCiv(nCivID).civGameData.iNextPossibleNavalInvastionTurnID = Game_Calendar.TURN_ID + 3 + CFG.oR.nextInt(4);
                           return;
                        }
                        this.moveAtWar_AtSea_ToProvinceID(nCivID, iBestProvinceID_MoveTo);
                     }
                     else {
                        Gdx.app.log("AoC", "moveAtWar -> BY SEA -> iBestProvinceID_MoveTo: " + iBestProvinceID_MoveTo);
                     }
                  }
               }
            }
         }
         Gdx.app.log("AoC", "moveAtWar -> BY SEA -> MP: " + CFG.game.getCiv(nCivID).getMovePoints());
         Gdx.app.log("AoC", "moveAtWar -> BY SEA END");
      }
   }

   protected final void moveAtWar_AtSea_ToProvinceID(final int nCivID, final int iBestProvinceID_MoveTo) {
      Gdx.app.log("AoC", "moveAtWar -> BY SEA -> iBestProvinceID_MoveTo: " + iBestProvinceID_MoveTo + ", NAME: " + CFG.game.getProvince(iBestProvinceID_MoveTo).getName());
      final List<Boolean> haveAccessToBasins = new ArrayList<Boolean>();
      for (int i = 0; i < CFG.map.iNumOfBasins; ++i) {
         haveAccessToBasins.add(false);
      }
      for (int i = 0; i < CFG.game.getProvince(iBestProvinceID_MoveTo).getNeighboringSeaProvincesSize(); ++i) {
         haveAccessToBasins.set(CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveTo).getNeighboringSeaProvinces(i)).getBasinID(), true);
      }
      int iBestProvinceID_MoveFrom = -1;
      float fBestProvinceID_MoveTo_Score = -1.0f;
      final int tempPossibleToRecruit = (int)((CFG.game.getCiv(nCivID).getMoney() > 5L && CFG.game.getCiv(nCivID).iBudget > 0) ? (CFG.game.getCiv(nCivID).getMoney() / 5L * 0.8f) : 0.0f);
      for (int j = CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size() - 1; j >= 0; --j) {
         int k = 0;
         while (k < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j)).getNeighboringSeaProvincesSize()) {
            if (haveAccessToBasins.get(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j)).getNeighboringSeaProvinces(k)).getBasinID())) {
               if (CFG.game.getCiv(nCivID).getCivRegion(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j)).getCivRegionID()).checkRegionBordersWithEnemy(nCivID)) {
                  break;
               }
               final float tempScore = this.moveAtWar_AtSea_FromProvinceID_Score(nCivID, CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j), iBestProvinceID_MoveTo, false, tempPossibleToRecruit);
               if (tempScore > fBestProvinceID_MoveTo_Score) {
                  iBestProvinceID_MoveFrom = CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(j);
                  fBestProvinceID_MoveTo_Score = tempScore;
                  break;
               }
               break;
            }
            else {
               ++k;
            }
         }
      }
      if (iBestProvinceID_MoveFrom >= 0) {
         Gdx.app.log("AoC", "moveAtWar -> BY SEA -> iBestProvinceID_MoveFrom: " + iBestProvinceID_MoveFrom + ", NAME: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getName() + " PORT: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getLevelOfPort());
         if (CFG.game.getProvince(iBestProvinceID_MoveFrom).getLevelOfPort() <= 0) {
            Gdx.app.log("AoC", "moveAtWar -> BY SEA -> TRY FIND NEIGHBORING PROVINCE WITH PORT");
            boolean newFound = false;
            for (int l = 0; l < CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvincesSize(); ++l) {
               if (CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getLevelOfPort() > 0) {
                  iBestProvinceID_MoveFrom = CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l);
                  newFound = true;
                  Gdx.app.log("AoC", "moveAtWar -> BY SEA -> UPDATE 00: iBestProvinceID_MoveFrom: " + iBestProvinceID_MoveFrom + ", NAME: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getName() + " PORT: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getLevelOfPort());
                  break;
               }
            }
            if (!newFound) {
               for (int l = 0; l < CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvincesSize(); ++l) {
                  if (CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getLevelOfPort() > 0) {
                     for (int m = 0; m < CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getNeighboringProvincesSize(); ++m) {
                        if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getNeighboringProvinces(m)).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getNeighboringProvinces(m)).getLevelOfPort() > 0) {
                           iBestProvinceID_MoveFrom = CFG.game.getProvince(CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvinces(l)).getNeighboringProvinces(m);
                           l = CFG.game.getProvince(iBestProvinceID_MoveFrom).getNeighboringProvincesSize();
                           Gdx.app.log("AoC", "moveAtWar -> BY SEA -> UPDATE 00: iBestProvinceID_MoveFrom: " + iBestProvinceID_MoveFrom + ", NAME: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getName() + " PORT: " + CFG.game.getProvince(iBestProvinceID_MoveFrom).getLevelOfPort());
                           break;
                        }
                     }
                  }
               }
            }
         }
         CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_NavalInvasion(nCivID, iBestProvinceID_MoveFrom, iBestProvinceID_MoveTo));
         CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1).action(nCivID);
      }
      else {
         Gdx.app.log("AoC", "moveAtWar -> BY SEA -> iBestProvinceID_MoveFrom: " + iBestProvinceID_MoveFrom);
      }
   }

   protected final float moveAtWar_AtSea_ToProvinceID_Score(final int nCivID, final int nProvince, final boolean ownProvince) {
      return (CFG.game.getProvince(nProvince).getPotential() + CFG.game.getProvince(nProvince).getPotentialRegion() * CFG.game.getCiv(CFG.game.getProvince(nProvince).getCivID()).getCivRegion(CFG.game.getProvince(nProvince).getCivRegionID()).getProvincesSize() / (float)CFG.game.getCiv(CFG.game.getProvince(nProvince).getCivID()).getNumOfProvinces()) * (ownProvince ? 1.625f : (CFG.game.getProvince(nProvince).isOccupied() ? 0.725f : 1.0f)) * (CFG.game.getProvince(nProvince).getIsCapital() ? ((CFG.game.getProvince(nProvince).getCivID() != nCivID) ? 0.725f : 1.45f) : 1.0f) * (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_ATTACK_NAVAL_DISTANCE * CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game_NextTurnUpdate.getAdministration_Capital(nCivID), nProvince));
   }

   protected final float moveAtWar_AtSea_FromProvinceID_Score(final int nCivID, final int nProvince, final int toProvinceID, final boolean ownProvince, final int tempPossibleToRecruit) {
      return CFG.game.getProvince(nProvince).getPotential() * (0.375f + 0.625f * ((CFG.game.getProvince(nProvince).getArmyCivID(nCivID) + (CFG.game.getProvince(nProvince).isOccupied() ? 0 : tempPossibleToRecruit)) / Math.max(1, CFG.game.getCiv(nCivID).getNumOfUnits() + tempPossibleToRecruit))) * ((CFG.game.getProvince(nProvince).getLevelOfPort() > 0) ? 1.5f : 1.0f) * (1.0f - CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(nProvince, toProvinceID) / 2.0f);
   }

   protected final int moveAtWar_NumOfNotCoveredNeighEnemyProvinces(final int nCivID, final int nProvinceID) {
      int out = 0;
      for (int j = 0; j < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++j) {
         if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j)).getCivID()) && !CFG.game.getCiv(nCivID).isMovingUnitsToProvinceID(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(j))) {
            ++out;
         }
      }
      return out;
   }

   protected final float moveAtWar_AttackTo_Score(final int nCivID, final int toProvinceID) {
      return (float)CFG.game.getProvince(toProvinceID).getPotentialModified_WAR_MoveTo(nCivID);
   }

   protected final void prepareForWar_Regroup(final int nCivID, final List<AI_ProvinceInfo_War> sortedFrontProvinces, final List<Integer> lFrontIDsWithArmies) {
      try {
         if (CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID <= Game_Calendar.TURN_ID) {
            final List<AI_RegoupArmyData> armiesWithoutDanger = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesInAnotherTerritory = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesAtSea = new ArrayList<AI_RegoupArmyData>();
            for (int i = 0; i < CFG.game.getCiv(nCivID).iArmiesPositionSize; ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i));
               if (tArmyToRegroup > 0 && !CFG.oAI.prepareForWar_BordersWithEnemy(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i))) {
                  armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
               }
            }
            for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i));
               if (tArmyToRegroup > 0 && !CFG.oAI.prepareForWar_BordersWithEnemy(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i))) {
                  boolean addN = true;
                  for (int z = armiesWithoutDanger.size() - 1; z >= 0; --z) {
                     if (armiesWithoutDanger.get(z).iProvinceID == CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)) {
                        addN = false;
                     }
                  }
                  if (addN) {
                     armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
               }
            }
            Gdx.app.log("AoC", "prepareForWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesWithoutDanger.size: " + armiesWithoutDanger.size());
            Gdx.app.log("AoC", "prepareForWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesInAnotherTerritory.size: " + armiesInAnotherTerritory.size());
            Gdx.app.log("AoC", "prepareForWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesAtSea.size: " + armiesAtSea.size());
            if (armiesWithoutDanger.size() == CFG.game.getCiv(nCivID).getNumOfProvinces()) {
               armiesWithoutDanger.clear();
            }
            while (armiesWithoutDanger.size() > 0 || armiesInAnotherTerritory.size() > 0) {
               int highestArmyID = -1;
               int highestArmy_Num = 0;
               int highestArmy_ListID = -1;
               for (int j = armiesWithoutDanger.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesWithoutDanger.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesWithoutDanger.get(j).iArmy;
                     highestArmy_ListID = 0;
                  }
               }
               for (int j = armiesInAnotherTerritory.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesInAnotherTerritory.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesInAnotherTerritory.get(j).iArmy;
                     highestArmy_ListID = 2;
                  }
               }
               if (highestArmyID >= 0 && highestArmy_ListID >= 0 && highestArmy_Num > 0) {
                  switch (highestArmy_ListID) {
                     case 0: {
                        Gdx.app.log("AoC", "prepareForWar_Regroup -> ACTION: 0");
                        this.regroupArmy_PrepareForWar_WithoutDanger(nCivID, armiesWithoutDanger.get(highestArmyID));
                        armiesWithoutDanger.remove(highestArmyID);
                        break;
                     }
                     case 2: {
                        Gdx.app.log("AoC", "prepareForWar_Regroup -> ACTION: 2");
                        this.regroupArmy_PrepareForWar_WithoutDanger(nCivID, armiesInAnotherTerritory.get(highestArmyID));
                        armiesInAnotherTerritory.remove(highestArmyID);
                        break;
                     }
                  }
               }
               if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE || CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
                  return;
               }
            }
            CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID = 0;
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final boolean moveAtWar_BordersWithEnemyCheck(final int nCivID, final int nProvinceID) {
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID())) {
            return true;
         }
      }
      return false;
   }

   protected final void moveAtWar_Regroup(final int nCivID, final List<AI_ProvinceInfo_War> sortedFrontProvinces, final List<Integer> lFrontIDsWithArmies) {
      try {
         if (CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID <= Game_Calendar.TURN_ID) {
            final List<AI_RegoupArmyData> armiesWithoutDanger = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesInAnotherTerritory = new ArrayList<AI_RegoupArmyData>();
            final List<AI_RegoupArmyData> armiesAtSea = new ArrayList<AI_RegoupArmyData>();
            for (int i = 0; i < CFG.game.getCiv(nCivID).iArmiesPositionSize; ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i));
               if (tArmyToRegroup > 0) {
                  if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getSeaProvince()) {
                     armiesAtSea.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
                  //if not province of civ and province owner is not puppet of civ
                  else if ((CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getCivID() != nCivID && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getCivID()).getPuppetOfCivID() != nCivID) && !CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getBordersWithEnemy() && !this.moveAtWar_BordersWithEnemyCheck(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i))) {
                     armiesInAnotherTerritory.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
                  else if (!CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getBordersWithEnemy() && !this.moveAtWar_BordersWithEnemyCheck(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i))) {
                     armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).lArmiesPosition.get(i), tArmyToRegroup));
                  }
               }
            }
            for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); ++i) {
               final int tArmyToRegroup = this.getRegroupArmy_NumOfUnits(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i));
               if (tArmyToRegroup > 0) {
                  if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getSeaProvince()) {
                     armiesAtSea.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
                  else if ((CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getCivID() != nCivID && !CFG.game.isAlly(CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getCivID(), nCivID)) && !CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getBordersWithEnemy() && !this.moveAtWar_BordersWithEnemyCheck(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i))) {
                     armiesInAnotherTerritory.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
                  else if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getDangerLevel() == 0 && !CFG.game.getProvince(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i)).getBordersWithEnemy() && !this.moveAtWar_BordersWithEnemyCheck(nCivID, CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i))) {
                     armiesWithoutDanger.add(new AI_RegoupArmyData(CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i), tArmyToRegroup));
                  }
               }
            }
            Gdx.app.log("AoC", "moveAtWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesWithoutDanger.size: " + armiesWithoutDanger.size());
            Gdx.app.log("AoC", "moveAtWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesInAnotherTerritory.size: " + armiesInAnotherTerritory.size());
            Gdx.app.log("AoC", "moveAtWar_Regroup -> " + CFG.game.getCiv(nCivID).getCivName() + ", armiesAtSea.size: " + armiesAtSea.size());
            if (armiesWithoutDanger.size() == CFG.game.getCiv(nCivID).getNumOfProvinces()) {
               armiesWithoutDanger.clear();
            }
            while (armiesAtSea.size() > 0) {
               int highestArmyID = -1;
               int highestArmy_Num = 0;
               int highestArmy_ListID = -1;
               for (int j = armiesAtSea.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesAtSea.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesAtSea.get(j).iArmy;
                     highestArmy_ListID = 1;
                  }
               }
               if (highestArmyID >= 0 && highestArmy_ListID >= 0 && highestArmy_Num > 0) {
                  switch (highestArmy_ListID) {
                     case 1: {
                        Gdx.app.log("AoC", "moveAtWar_Regroup -> ACTION: 1");
                        this.regroupArmy_AtWar_AtSea(nCivID, armiesAtSea.get(highestArmyID));
                        armiesAtSea.remove(highestArmyID);
                        break;
                     }
                  }
               }
               if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE || CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
                  return;
               }
            }
            while (armiesWithoutDanger.size() > 0 || armiesInAnotherTerritory.size() > 0) {
               int highestArmyID = -1;
               int highestArmy_Num = 0;
               int highestArmy_ListID = -1;
               for (int j = armiesWithoutDanger.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesWithoutDanger.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesWithoutDanger.get(j).iArmy;
                     highestArmy_ListID = 0;
                  }
               }
               for (int j = armiesInAnotherTerritory.size() - 1; j >= 0; --j) {
                  if (highestArmyID < 0 || highestArmy_Num < armiesInAnotherTerritory.get(j).iArmy) {
                     highestArmyID = j;
                     highestArmy_Num = armiesInAnotherTerritory.get(j).iArmy;
                     highestArmy_ListID = 2;
                  }
               }
               if (highestArmyID >= 0 && highestArmy_ListID >= 0 && highestArmy_Num > 0) {
                  switch (highestArmy_ListID) {
                     case 0: {
                        Gdx.app.log("AoC", "moveAtWar_Regroup -> ACTION: 0");
                        this.regroupArmy_AtWar_WithoutDanger(nCivID, armiesWithoutDanger.get(highestArmyID));
                        armiesWithoutDanger.remove(highestArmyID);
                        break;
                     }
                     case 2: {
                        Gdx.app.log("AoC", "moveAtWar_Regroup -> ACTION: 2");
                        this.regroupArmy_AtWar_WithoutDanger(nCivID, armiesInAnotherTerritory.get(highestArmyID));
                        armiesInAnotherTerritory.remove(highestArmyID);
                        break;
                     }
                  }
               }
               if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE || CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE) {
                  return;
               }
            }
            CFG.game.getCiv(nCivID).civGameData.iRegroupArmyAtPeace_CheckTurnID = 0;
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final boolean regroupArmy_AtWar_AtSea(final int nCivID, final AI_RegoupArmyData nArmy) {
      final List<AI_ProvinceInfo> possibleMoveTo = new ArrayList<AI_ProvinceInfo>();
      for (int i = 0; i < CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i)).getCivID() == nCivID || CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i)).getCivID())) {
            possibleMoveTo.add(new AI_ProvinceInfo(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i), this.getPotential_BasedOnNeighboringProvs(CFG.game.getProvince(nArmy.iProvinceID).getNeighboringProvinces(i), nCivID), 1));
         }
      }
      Gdx.app.log("AoC", "regroupArmy_AtWar_AtSea -> " + CFG.game.getCiv(nCivID).getCivName() + ", Province: " + nArmy.iProvinceID + ", NAME: " + CFG.game.getProvince(nArmy.iProvinceID).getName() + ", nArmy: " + nArmy.iArmy + ", possibleMoveTo.size: " + possibleMoveTo.size());
      if (possibleMoveTo.size() > 0) {
         int tMaxArmy = 1;
         float tMaxPotential = 1.0f;
         float tMaxRegion_NumOfProvinces = 1.0f;
         float tMaxRegion_Potential = 1.0f;
         int tMaxDL = 1;
         final List<Integer> tMovingArmy = new ArrayList<Integer>();
         int j = 0;
         int iSize = possibleMoveTo.size();
         int tempMovingArmy = 0;
         while (j < iSize) {
            if (possibleMoveTo.get(j).iValue > tMaxPotential) {
               tMaxPotential = possibleMoveTo.get(j).iValue;
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
               tMaxDL = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
               tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
               tMaxRegion_Potential = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion();
            }
            tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, possibleMoveTo.get(j).iProvinceID);
            tMovingArmy.add(tempMovingArmy);
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
               tMaxArmy = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy;
            }
            ++j;
         }
         for (j = 0, iSize = possibleMoveTo.size(); j < iSize; ++j) {
            possibleMoveTo.get(j).iValue = this.getValue_PositionOfArmy(nCivID, possibleMoveTo, j, tMovingArmy.get(j), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, nArmy.iArmy, (float)nArmy.iArmy);
         }
         final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
         while (possibleMoveTo.size() > 0) {
            int tBest = 0;
            for (int k = 1, iSize2 = possibleMoveTo.size(); k < iSize2; ++k) {
               if (possibleMoveTo.get(tBest).iValue < possibleMoveTo.get(k).iValue) {
                  tBest = k;
               }
            }
            sortedFrontProvinces.add(possibleMoveTo.get(tBest));
            possibleMoveTo.remove(tBest);
         }
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         int iNumOfMaxMovements = 1;
         if (CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_DISBAND_IF_LESS_THAN_PERC > percOfArmyToRegroup) {
            iNumOfMaxMovements = 1;
         }
         else {
            iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * 2), 1 + CFG.oR.nextInt(3)));
            if (percOfArmyToRegroup > 0.34f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 4);
            }
            else if (percOfArmyToRegroup > 0.24f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 3);
            }
            else if (percOfArmyToRegroup > 0.1f) {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 2);
            }
            else {
               iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 1);
            }
         }
         Gdx.app.log("AoC", "regroupArmy_AtWar_AtSea -> 000 -> iNumOfMaxMovements: " + iNumOfMaxMovements);
         final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
         float totalValues = 0.0f;
         for (int l = 0; l < iNumOfMaxMovements && l < sortedFrontProvinces.size(); ++l) {
            tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(l));
            totalValues += sortedFrontProvinces.get(l).iValue;
         }
         Gdx.app.log("AoC", "regroupArmy_AtWar_AtSea -> 000 -> tRecruitArmiesForProvinces.size: " + tRecruitArmiesForProvinces.size());
         for (int l = 0; l < tRecruitArmiesForProvinces.size(); ++l) {
            final int tArmyToRecruit_PRE = (int)Math.ceil(nArmy.iArmy * tRecruitArmiesForProvinces.get(l).iValue / totalValues);
            Gdx.app.log("AoC", "regroupArmy_AtWar_AtSea -> 000 -> nArmy.iArmy: " + nArmy.iArmy + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
            if (tArmyToRecruit_PRE <= 0) {
               break;
            }
            final RegroupArmy_Data_AtWar tryRegroupArmy = new RegroupArmy_Data_AtWar(nCivID, nArmy.iProvinceID, tRecruitArmiesForProvinces.get(l).iProvinceID);
            if (tryRegroupArmy.getRouteSize() > 0) {
               if (tryRegroupArmy.getRouteSize() == 1) {
                  if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, tRecruitArmiesForProvinces.get(l).iProvinceID, tArmyToRecruit_PRE, nCivID, true, false)) {}
               }
               else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToRecruit_PRE, nCivID, true, false)) {
                  tryRegroupArmy.setFromProvinceID(tryRegroupArmy.getRoute(0));
                  tryRegroupArmy.removeRoute(0);
                  tryRegroupArmy.setNumOfUnits(tArmyToRecruit_PRE);
                  CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy);
               }
            }
         }
         return true;
      }
      for (int i = CFG.game.getCiv(nCivID).getSeaAccess_Provinces_Size() - 1; i >= 0; --i) {
         for (int m = 0; m < CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvincesSize(); ++m) {
            if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i)).getNeighboringSeaProvinces(m)).getBasinID() == CFG.game.getProvince(nArmy.iProvinceID).getBasinID()) {
               possibleMoveTo.add(new AI_ProvinceInfo(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i), this.getPotential_BasedOnNeighboringProvs(CFG.game.getCiv(nCivID).getSeaAccess_Provinces().get(i), nCivID), 1));
               break;
            }
         }
      }
      Gdx.app.log("AoC", "regroupArmy_AtWar_AtSea ->  111, possibleMoveTo.size: " + possibleMoveTo.size());
      if (possibleMoveTo.size() > 0) {
         int tMaxArmy = 1;
         float tMaxPotential = 1.0f;
         float tMaxRegion_NumOfProvinces = 1.0f;
         float tMaxRegion_Potential = 1.0f;
         int tMaxDL = 1;
         final List<Integer> tMovingArmy = new ArrayList<Integer>();
         int j = 0;
         int iSize = possibleMoveTo.size();
         int tempMovingArmy = 0;
         while (j < iSize) {
            if (possibleMoveTo.get(j).iValue > tMaxPotential) {
               tMaxPotential = possibleMoveTo.get(j).iValue;
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
               tMaxDL = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getDangerLevel_WithArmy();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
               tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getRegion_NumOfProvinces();
            }
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
               tMaxRegion_Potential = (float)CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getPotentialRegion();
            }
            tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, possibleMoveTo.get(j).iProvinceID);
            tMovingArmy.add(tempMovingArmy);
            if (CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
               tMaxArmy = CFG.game.getProvince(possibleMoveTo.get(j).iProvinceID).getArmy(0) + tempMovingArmy;
            }
            ++j;
         }
         for (j = 0, iSize = possibleMoveTo.size(); j < iSize; ++j) {
            possibleMoveTo.get(j).iValue = this.getValue_PositionOfArmy(nCivID, possibleMoveTo, j, tMovingArmy.get(j), tMaxPotential, tMaxRegion_Potential, tMaxDL, tMaxArmy, nArmy.iArmy, (float)nArmy.iArmy);
         }
         final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
         if (possibleMoveTo.size() > 0) {
            int tBest = 0;
            for (int k = 1, iSize2 = possibleMoveTo.size(); k < iSize2; ++k) {
               if (possibleMoveTo.get(tBest).iValue < possibleMoveTo.get(k).iValue) {
                  tBest = k;
               }
            }
            sortedFrontProvinces.add(possibleMoveTo.get(tBest));
            possibleMoveTo.remove(tBest);
         }
         final RegroupArmy_Data_AtWar tryRegroupArmy2 = new RegroupArmy_Data_AtWar(nCivID, nArmy.iProvinceID, sortedFrontProvinces.get(0).iProvinceID);
         if (tryRegroupArmy2.getRouteSize() > 0) {
            if (tryRegroupArmy2.getRouteSize() == 1) {
               if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, sortedFrontProvinces.get(0).iProvinceID, nArmy.iArmy, nCivID, true, false)) {}
            }
            else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
               tryRegroupArmy2.setFromProvinceID(tryRegroupArmy2.getRoute(0));
               tryRegroupArmy2.removeRoute(0);
               tryRegroupArmy2.setNumOfUnits(nArmy.iArmy);
               CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy2);
            }
         }
         return true;
      }
      if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID) {
         final RegroupArmy_Data_AtWar tryRegroupArmy3 = new RegroupArmy_Data_AtWar(nCivID, nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID());
         if (tryRegroupArmy3.getRouteSize() > 0) {
            if (tryRegroupArmy3.getRouteSize() == 1) {
               if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID(), nArmy.iArmy, nCivID, true, false)) {}
            }
            else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy3.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
               tryRegroupArmy3.setFromProvinceID(tryRegroupArmy3.getRoute(0));
               tryRegroupArmy3.removeRoute(0);
               tryRegroupArmy3.setNumOfUnits(nArmy.iArmy);
               CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy3);
            }
         }
         else {
            CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
         }
      }
      else {
         CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
      }
      return true;
   }

   protected final boolean regroupArmy_PrepareForWar_WithoutDanger(final int nCivID, final AI_RegoupArmyData nArmy) {
      try {
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         try {
            if (CFG.game.getCiv(nCivID).getCivRegion(CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()).getProvincesSize() > 1) {
               Gdx.app.log("AoC", "regroupArmy_PrepareForWar_WithoutDanger -> 111 -> " + nArmy.iArmy);
               int tMaxDL = 1;
               float tMaxPotential = 1.0f;
               final List<AI_ProvinceInfo> tempFrontProvinces = new ArrayList<AI_ProvinceInfo>();
               for (int i = CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).size() - 1; i >= 0; --i) {
                  for (int u = 0; u < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u) {
                     if (CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u).onCivID) {
                        try {
                           if (CFG.game.getProvince(CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(0)).getCivRegionID() == CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()) {
                              for (int j = CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.size() - 1; j >= 0; --j) {
                                 boolean wasAdded = false;
                                 for (int k = tempFrontProvinces.size() - 1; k >= 0; --k) {
                                    if (tempFrontProvinces.get(k).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(j)) {
                                       wasAdded = true;
                                       break;
                                    }
                                 }
                                 if (!wasAdded) {
                                    tempFrontProvinces.add(new AI_ProvinceInfo(CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(j), 1, 1));
                                 }
                              }
                           }
                        }
                        catch (final IndexOutOfBoundsException ex3) {}
                     }
                  }
               }
               Gdx.app.log("AoC", "regroupArmy_PrepareForWar_WithoutDanger -> 111 -> tempFrontProvinces.size: " + tempFrontProvinces.size());
               if (tempFrontProvinces.size() > 0) {
                  int tMaxArmy = 1;
                  final List<Integer> tMovingArmy = new ArrayList<Integer>();
                  int l = 0;
                  int iSize = tempFrontProvinces.size();
                  int tempMovingArmy = 0;
                  while (l < iSize) {
                     if (tempFrontProvinces.get(l).iValue > tMaxPotential) {
                        tMaxPotential = tempFrontProvinces.get(l).iValue;
                     }
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                        tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy();
                     }
                     tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(l).iProvinceID);
                     tMovingArmy.add(tempMovingArmy);
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
                        tMaxArmy = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy;
                     }
                     ++l;
                  }
                  for (l = 0, iSize = tempFrontProvinces.size(); l < iSize; ++l) {
                     tempFrontProvinces.get(l).iValue = (((1.0f - (CFG.game.getProvinceArmy(tempFrontProvinces.get(l).iProvinceID) + tMovingArmy.get(l)) / (float)tMaxArmy + 0.2f * (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel() / (float)tMaxDL) + 0.2f * (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getPotentialModified_WAR_MoveFrom(nCivID) / tMaxPotential) + 0.2f * CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getNeighbooringProvinceOfCivWasLost()) * tempFrontProvinces.get(l).iRecruitable == 0.0f) ? 0.725f : 1.0f);
                  }
                  final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
                  while (tempFrontProvinces.size() > 0) {
                     int tBest = 0;
                     for (int m = 1, iSize2 = tempFrontProvinces.size(); m < iSize2; ++m) {
                        if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(m).iValue) {
                           tBest = m;
                        }
                     }
                     sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
                     tempFrontProvinces.remove(tBest);
                  }
                  int iNumOfMaxMovements = 1;
                  if (CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_DISBAND_IF_LESS_THAN_PERC > percOfArmyToRegroup) {
                     iNumOfMaxMovements = 1;
                  }
                  else {
                     iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE, Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), 2 + CFG.oR.nextInt(3))));
                     if (percOfArmyToRegroup > 0.4f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 4);
                     }
                     else if (percOfArmyToRegroup > 0.3f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 3);
                     }
                     else if (percOfArmyToRegroup > 0.2f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 2);
                     }
                     else {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 1);
                     }
                  }
                  Gdx.app.log("AoC", "regroupArmy_PrepareForWar_WithoutDanger -> iNumOfMaxMovements: " + iNumOfMaxMovements);
                  final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
                  float totalValues = 0.0f;
                  for (int i2 = 0; i2 < iNumOfMaxMovements && i2 < sortedFrontProvinces.size(); ++i2) {
                     tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i2));
                     totalValues += sortedFrontProvinces.get(i2).iValue;
                  }
                  Gdx.app.log("AoC", "regroupArmy_PrepareForWar_WithoutDanger -> 111 -> tRecruitArmiesForProvinces.size: " + tRecruitArmiesForProvinces.size());
                  for (int i2 = 0; i2 < tRecruitArmiesForProvinces.size(); ++i2) {
                     final int tArmyToRecruit_PRE = (int)Math.ceil(nArmy.iArmy * tRecruitArmiesForProvinces.get(i2).iValue / totalValues);
                     Gdx.app.log("AoC", "regroupArmy_PrepareForWar_WithoutDanger -> 111 -> nArmy.iArmy: " + nArmy.iArmy + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
                     if (tArmyToRecruit_PRE <= 0) {
                        break;
                     }
                     final RegroupArmy_Data tryRegroupArmy = new RegroupArmy_Data(nCivID, nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID);
                     if (tryRegroupArmy.getRouteSize() > 0) {
                        if (tryRegroupArmy.getRouteSize() == 1) {
                           if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID, tArmyToRecruit_PRE, nCivID, true, false)) {}
                        }
                        else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToRecruit_PRE, nCivID, true, false)) {
                           tryRegroupArmy.setFromProvinceID(tryRegroupArmy.getRoute(0));
                           tryRegroupArmy.removeRoute(0);
                           tryRegroupArmy.setNumOfUnits(tArmyToRecruit_PRE);
                           CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy);
                        }
                     }
                  }
                  return true;
               }
            }
         }
         catch (final NullPointerException ex4) {}
         final List<AI_NeighProvinces> listOfPossibleProvinces = this.getAllNeighboringProvincesInRange_RegroupPrepareForWAr(nArmy.iProvinceID, nCivID, CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_MAX_PROVINCES + CFG.game.getCiv(nCivID).getNumOfProvinces() / 15, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
         if (listOfPossibleProvinces.size() > 0) {
            int nNumOfPossibleMovements = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            if (percOfArmyToRegroup > 0.54f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 4);
            }
            else if (percOfArmyToRegroup > 0.35f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 3);
            }
            else if (percOfArmyToRegroup > 0.25f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 2);
            }
            else {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 1);
            }
            boolean provincesWithDanger = false;
            for (int i = listOfPossibleProvinces.size() - 1; i >= 0; --i) {
               if (CFG.game.getProvince(listOfPossibleProvinces.get(i).iProvinceID).getDangerLevel() > 0) {
                  provincesWithDanger = true;
                  break;
               }
            }
            if (provincesWithDanger) {
               final List<Integer> tSortedIDs = new ArrayList<Integer>();
               final List<Integer> tData = new ArrayList<Integer>();
               for (int l = listOfPossibleProvinces.size() - 1; l >= 0; --l) {
                  tData.add(l);
               }
               while (tData.size() > 0) {
                  int tBest2 = 0;
                  for (int i3 = tData.size() - 1; i3 > 0; --i3) {
                     if (CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(tBest2)).iProvinceID).getDangerLevel_WithArmy() < CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(i3)).iProvinceID).getDangerLevel_WithArmy()) {
                        tBest2 = i3;
                     }
                  }
                  tSortedIDs.add(tData.get(tBest2));
                  tData.remove(tBest2);
               }
               int nDangerTotal = 0;
               for (int i3 = 0; i3 < nNumOfPossibleMovements && i3 < tSortedIDs.size(); ++i3) {
                  nDangerTotal += CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(i3)).iProvinceID).getDangerLevel_WithArmy();
               }
               int tIDOfFisrttSuccesfulMovement = -1;
               for (int m = 0; m < nNumOfPossibleMovements && m < tSortedIDs.size() && nArmy.iArmy > 0; ++m) {
                  RegroupArmy_Data tryRegroupArmy2 = new RegroupArmy_Data(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID);
                  if (tryRegroupArmy2.getRouteSize() > 0) {
                     final int tArmyToMove = (m == nNumOfPossibleMovements || m == tSortedIDs.size() - 1) ? nArmy.iArmy : ((int)Math.ceil(nArmy.iArmy * (CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID).getDangerLevel_WithArmy() / (float)nDangerTotal)));
                     nArmy.iArmy -= tArmyToMove;
                     if (tArmyToMove <= 0) {
                        break;
                     }
                     if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), tArmyToMove, nCivID, true, false)) {
                        if (tryRegroupArmy2.getRouteSize() > 1) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy2.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID, tArmyToMove));
                        }
                        tIDOfFisrttSuccesfulMovement = m;
                     }
                  }
                  else if (tIDOfFisrttSuccesfulMovement >= 0) {
                     tryRegroupArmy2 = new RegroupArmy_Data(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID);
                     if (tryRegroupArmy2.getRouteSize() > 0 && CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
                        if (tryRegroupArmy2.getRouteSize() > 1) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, tryRegroupArmy2.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(tIDOfFisrttSuccesfulMovement)).iProvinceID, nArmy.iArmy));
                        }
                        return true;
                     }
                  }
               }
               if (tIDOfFisrttSuccesfulMovement >= 0) {
                  return true;
               }
            }
            else {
               Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> NONE -> 11111111 -> " + nArmy.iArmy);
            }
         }
         Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> NONE -> 222222 -> " + nArmy.iArmy);
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
      return false;
   }

   protected final boolean regroupArmy_AtWar_WithoutDanger(final int nCivID, final AI_RegoupArmyData nArmy) {
      try {
         final float percOfArmyToRegroup = Math.max(nArmy.iArmy / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.01f);
         try {
            if (CFG.game.getCiv(nCivID).getCivRegion(CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()).getProvincesSize() > 1) {
               Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> 111 -> " + nArmy.iArmy);
               int tMaxDL = 1;
               float tMaxPotential = 1.0f;
               final List<AI_ProvinceInfo> tempFrontProvinces = new ArrayList<AI_ProvinceInfo>();
               for (int i = CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).size() - 1; i >= 0; --i) {
                  if (CFG.game.getCivsAtWar(nCivID, CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).iWithCivID)) {
                     try {
                        if (CFG.game.getProvince(CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(0)).getCivRegionID() == CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()) {
                           for (int j = CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.size() - 1; j >= 0; --j) {
                              boolean wasAdded = false;
                              for (int k = tempFrontProvinces.size() - 1; k >= 0; --k) {
                                 if (tempFrontProvinces.get(k).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(j)) {
                                    wasAdded = true;
                                    break;
                                 }
                              }
                              if (!wasAdded) {
                                 tempFrontProvinces.add(new AI_ProvinceInfo(CFG.oAI.lFrontLines.get(CFG.game.getProvince(nArmy.iProvinceID).getCivID() - 1).get(i).lProvinces.get(j), 1, 1));
                              }
                           }
                        }
                     }
                     catch (final IndexOutOfBoundsException ex4) {}
                  }
               }
               Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> 111 -> tempFrontProvinces.size: " + tempFrontProvinces.size());
               if (tempFrontProvinces.size() > 0) {
                  int tMaxArmy = 1;
                  final List<Integer> tMovingArmy = new ArrayList<Integer>();
                  int l = 0;
                  int iSize = tempFrontProvinces.size();
                  int tempMovingArmy = 0;
                  while (l < iSize) {
                     if (tempFrontProvinces.get(l).iValue > tMaxPotential) {
                        tMaxPotential = tempFrontProvinces.get(l).iValue;
                     }
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                        tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel_WithArmy();
                     }
                     tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(l).iProvinceID);
                     tMovingArmy.add(tempMovingArmy);
                     if (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
                        tMaxArmy = CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getArmy(0) + tempMovingArmy;
                     }
                     ++l;
                  }
                  for (l = 0, iSize = tempFrontProvinces.size(); l < iSize; ++l) {
                     tempFrontProvinces.get(l).iValue = (((1.0f - (CFG.game.getProvinceArmy(tempFrontProvinces.get(l).iProvinceID) + tMovingArmy.get(l)) / (float)tMaxArmy + 0.2f * (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getDangerLevel() / (float)tMaxDL) + 0.2f * (CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getPotentialModified_WAR_MoveFrom(nCivID) / tMaxPotential) + 0.2f * CFG.game.getProvince(tempFrontProvinces.get(l).iProvinceID).getNeighbooringProvinceOfCivWasLost()) * tempFrontProvinces.get(l).iRecruitable == 0.0f) ? 0.725f : 1.0f);
                  }
                  final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
                  while (tempFrontProvinces.size() > 0) {
                     int tBest = 0;
                     for (int m = 1, iSize2 = tempFrontProvinces.size(); m < iSize2; ++m) {
                        if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(m).iValue) {
                           tBest = m;
                        }
                     }
                     sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
                     tempFrontProvinces.remove(tBest);
                  }
                  int iNumOfMaxMovements = 1;
                  if (CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_DISBAND_IF_LESS_THAN_PERC > percOfArmyToRegroup) {
                     iNumOfMaxMovements = 1;
                  }
                  else {
                     iNumOfMaxMovements = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE, Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), 2 + CFG.oR.nextInt(3))));
                     if (percOfArmyToRegroup > 0.34f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 4);
                     }
                     else if (percOfArmyToRegroup > 0.24f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 3);
                     }
                     else if (percOfArmyToRegroup > 0.1f) {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 2);
                     }
                     else {
                        iNumOfMaxMovements = Math.min(iNumOfMaxMovements, 1);
                     }
                  }
                  Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> iNumOfMaxMovements: " + iNumOfMaxMovements);
                  final List<AI_ProvinceInfo> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo>();
                  float totalValues = 0.0f;
                  for (int i2 = 0; i2 < iNumOfMaxMovements && i2 < sortedFrontProvinces.size(); ++i2) {
                     tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i2));
                     totalValues += sortedFrontProvinces.get(i2).iValue;
                  }
                  Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> 111 -> tRecruitArmiesForProvinces.size: " + tRecruitArmiesForProvinces.size());
                  for (int i2 = 0; i2 < tRecruitArmiesForProvinces.size(); ++i2) {
                     int tempArmyInThisMove = nArmy.iArmy;
                     if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0 && CFG.game.getProvince(nArmy.iProvinceID).getCivID() == nCivID && CFG.game.getProvince(tRecruitArmiesForProvinces.get(i2).iProvinceID).getCivID() != nCivID) {
                        tempArmyInThisMove = (int)Math.ceil(tempArmyInThisMove * (0.72f + CFG.oR.nextInt(12) / 100.0f));
                     }
                     final int tArmyToRecruit_PRE = (int)Math.ceil(tempArmyInThisMove * tRecruitArmiesForProvinces.get(i2).iValue / totalValues);
                     Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> 111 -> nArmy.iArmy: " + nArmy.iArmy + ", tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
                     if (tArmyToRecruit_PRE <= 0) {
                        break;
                     }
                     final RegroupArmy_Data_ToTheFront_Double tryRegroupArmy = new RegroupArmy_Data_ToTheFront_Double(nCivID, nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID);
                     if (tryRegroupArmy.getRouteSize() > 0) {
                        if (tryRegroupArmy.getRouteSize() == 1) {
                           if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, tRecruitArmiesForProvinces.get(i2).iProvinceID, tArmyToRecruit_PRE, nCivID, true, false)) {}
                        }
                        else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy.getRoute(0), tArmyToRecruit_PRE, nCivID, true, false)) {
                           tryRegroupArmy.setFromProvinceID(tryRegroupArmy.getRoute(0));
                           tryRegroupArmy.removeRoute(0);
                           tryRegroupArmy.setNumOfUnits(tArmyToRecruit_PRE);
                           CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy);
                        }
                     }
                  }
                  return true;
               }
            }
         }
         catch (final NullPointerException ex5) {}
         final List<AI_NeighProvinces> listOfPossibleProvinces = this.getAllNeighboringProvincesInRange_RegroupAtWar(nArmy.iProvinceID, nCivID, CFG.game.getCiv(nCivID).civGameData.civPersonality.REGROUP_AT_PEACE_MAX_PROVINCES + CFG.game.getCiv(nCivID).getNumOfProvinces() / 15, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
         Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> listOfPossibleProvinces.size: " + listOfPossibleProvinces.size());
         if (listOfPossibleProvinces.size() > 0) {
            int nNumOfPossibleMovements = CFG.game.getCiv(nCivID).getMovePoints() / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
            if (percOfArmyToRegroup > 0.54f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 4);
            }
            else if (percOfArmyToRegroup > 0.34f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 3);
            }
            else if (percOfArmyToRegroup > 0.19f) {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 2);
            }
            else {
               nNumOfPossibleMovements = Math.min(nNumOfPossibleMovements, 1);
            }
            boolean provincesWithDanger = false;
            for (int i = listOfPossibleProvinces.size() - 1; i >= 0; --i) {
               if (CFG.game.getProvince(listOfPossibleProvinces.get(i).iProvinceID).getDangerLevel() > 0) {
                  provincesWithDanger = true;
                  break;
               }
            }
            if (provincesWithDanger) {
               final List<Integer> tSortedIDs = new ArrayList<Integer>();
               final List<Integer> tData = new ArrayList<Integer>();
               for (int l = listOfPossibleProvinces.size() - 1; l >= 0; --l) {
                  tData.add(l);
               }
               while (tData.size() > 0) {
                  int tBest2 = 0;
                  for (int i3 = tData.size() - 1; i3 > 0; --i3) {
                     if (CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(tBest2)).iProvinceID).getDangerLevel_WithArmy() < CFG.game.getProvince(listOfPossibleProvinces.get(tData.get(i3)).iProvinceID).getDangerLevel_WithArmy()) {
                        tBest2 = i3;
                     }
                  }
                  tSortedIDs.add(tData.get(tBest2));
                  tData.remove(tBest2);
               }
               int nDangerTotal = 0;
               for (int i3 = 0; i3 < nNumOfPossibleMovements && i3 < tSortedIDs.size(); ++i3) {
                  nDangerTotal += CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(i3)).iProvinceID).getDangerLevel_WithArmy();
               }
               int tIDOfFisrttSuccesfulMovement = -1;
               for (int m = 0; m < nNumOfPossibleMovements && m < tSortedIDs.size() && nArmy.iArmy > 0; ++m) {
                  final RegroupArmy_Data_AtWar tryRegroupArmy2 = new RegroupArmy_Data_AtWar(nCivID, nArmy.iProvinceID, listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID);
                  if (tryRegroupArmy2.getRouteSize() > 0) {
                     int tempArmyInThisMove2 = nArmy.iArmy;
                     if (CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0 && CFG.game.getProvince(nArmy.iProvinceID).getCivID() == nCivID && CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID).getCivID() != nCivID) {
                        tempArmyInThisMove2 = (int)Math.ceil(tempArmyInThisMove2 * (0.72f + CFG.oR.nextInt(12) / 100.0f));
                     }
                     final int tArmyToMove = (m == nNumOfPossibleMovements || m == tSortedIDs.size() - 1) ? tempArmyInThisMove2 : ((int)Math.ceil(tempArmyInThisMove2 * (CFG.game.getProvince(listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID).getDangerLevel_WithArmy() / (float)nDangerTotal)));
                     nArmy.iArmy -= tArmyToMove;
                     if (tArmyToMove <= 0) {
                        break;
                     }
                     if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy2.getRoute(0), tArmyToMove, nCivID, true, false)) {
                        if (tryRegroupArmy2.getRouteSize() > 1) {
                           CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment_War_Double(nCivID, tryRegroupArmy2.getRoute(0), listOfPossibleProvinces.get(tSortedIDs.get(m)).iProvinceID, tArmyToMove));
                        }
                        tIDOfFisrttSuccesfulMovement = m;
                     }
                  }
               }
               if (tIDOfFisrttSuccesfulMovement >= 0) {
                  return true;
               }
            }
            else {
               Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> NOTHING 0000 -> " + nArmy.iArmy);
            }
         }
         if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getCiv(nCivID).getCapitalProvinceID() != nArmy.iProvinceID) {
            if (CFG.game.getCiv(nCivID).getBordersWithEnemy() > 0 || CFG.game.getProvince(nArmy.iProvinceID).getNeighboringSeaProvincesSize() <= 0 || CFG.oR.nextInt(100) >= 80) {
               if (CFG.oR.nextInt(100) < 15) {
                  Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> CAPITAL -> " + nArmy.iArmy);
                  if (percOfArmyToRegroup < 0.01f) {
                     CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
                     Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> DISBAND -> 11111111 -> " + nArmy.iArmy);
                  }
                  else {
                     final RegroupArmy_Data_AtWar tryRegroupArmy3 = new RegroupArmy_Data_AtWar(nCivID, nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID());
                     if (tryRegroupArmy3.getRouteSize() > 0) {
                        if (tryRegroupArmy3.getRouteSize() == 1) {
                           if (!CFG.gameAction.moveArmy(nArmy.iProvinceID, CFG.game.getCiv(nCivID).getCapitalProvinceID(), nArmy.iArmy, nCivID, true, false)) {}
                        }
                        else if (CFG.gameAction.moveArmy(nArmy.iProvinceID, tryRegroupArmy3.getRoute(0), nArmy.iArmy, nCivID, true, false)) {
                           tryRegroupArmy3.setFromProvinceID(tryRegroupArmy3.getRoute(0));
                           tryRegroupArmy3.removeRoute(0);
                           tryRegroupArmy3.setNumOfUnits(nArmy.iArmy);
                           CFG.game.getCiv(nCivID).addRegroupArmy(tryRegroupArmy3);
                        }
                     }
                     else if (!CFG.game.getCiv(CFG.game.getProvince(nArmy.iProvinceID).getCivID()).getCivRegion(CFG.game.getProvince(nArmy.iProvinceID).getCivRegionID()).isKeyRegion) {
                        CFG.gameAction.disbandArmy(nArmy.iProvinceID, nArmy.iArmy, nCivID);
                        Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> DISBAND -> 222222 -> " + nArmy.iArmy);
                     }
                  }
               }
            }
         }
         Gdx.app.log("AoC", "regroupArmy_AtWar_WithoutDanger -> NOTHING 1111 -> " + nArmy.iArmy);
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final StackOverflowError ex2) {
         CFG.exceptionStack(ex2);
      }
      catch (final NullPointerException ex3) {
         CFG.exceptionStack(ex3);
      }
      return false;
   }

   protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_RegroupAtWar(final int nProvinceID, final int nCivID, final int iRange, final List<AI_NeighProvinces> out, List<Integer> was) {
      List<Integer> recentlyAdded = new ArrayList<Integer>();
      recentlyAdded.add(nProvinceID);
      was.add(nProvinceID);
      CFG.game.getProvince(nProvinceID).was = true;
      final List<Integer> currProvinces = new ArrayList<Integer>();
      int nIteration_Distance = 0;
      int iFirstFoundRange = -1;
      while ((nIteration_Distance < iRange || out.size() == 0) && recentlyAdded.size() > 0) {
         currProvinces.clear();
         ++nIteration_Distance;
         for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
            boolean wasntAdded = true;
            for (int j = currProvinces.size() - 1; j >= 0; --j) {
               if (Objects.equals(currProvinces.get(j), recentlyAdded.get(a))) {
                  wasntAdded = false;
                  break;
               }
            }
            if (wasntAdded) {
               currProvinces.add(recentlyAdded.get(a));
            }
         }
         recentlyAdded.clear();
         for (int a = currProvinces.size() - 1; a >= 0; --a) {
            for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
               if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                  was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                  CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                  if (CFG.game.isAlly(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) || CFG.game.getMilitaryAccess(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) > 0) {
                     if (CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getBordersWithEnemy()) {
                        boolean bordersWithOurEnemy = false;
                        for (int z = 0; z < CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getNeighboringProvincesSize(); ++z) {
                           if (CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getNeighboringProvinces(z)).getCivID())) {
                              bordersWithOurEnemy = true;
                              break;
                           }
                        }
                        if (bordersWithOurEnemy) {
                           out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                           if (iFirstFoundRange < 0) {
                              iFirstFoundRange = nIteration_Distance;
                           }
                        }
                     }
                     else if (this.moveAtWar_BordersWithEnemyCheck(nCivID, CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i))) {
                        out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                        if (iFirstFoundRange < 0) {
                           iFirstFoundRange = nIteration_Distance;
                        }
                     }
                     recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                  }
               }
            }
         }
         if (iFirstFoundRange > 0 && iFirstFoundRange + 2 < nIteration_Distance) {
            break;
         }
      }
      for (int k = was.size() - 1; k >= 0; --k) {
         CFG.game.getProvince(was.get(k)).was = false;
      }
      recentlyAdded.clear();
      recentlyAdded = null;
      was.clear();
      was = null;
      return out;
   }

   protected final List<AI_NeighProvinces> getAllNeighboringProvincesInRange_RegroupPrepareForWAr(final int nProvinceID, final int nCivID, final int iRange, final List<AI_NeighProvinces> out, List<Integer> was) {
      List<Integer> recentlyAdded = new ArrayList<Integer>();
      recentlyAdded.add(nProvinceID);
      was.add(nProvinceID);
      CFG.game.getProvince(nProvinceID).was = true;
      final List<Integer> currProvinces = new ArrayList<Integer>();
      int nIteration_Distance = 0;
      int iFirstFoundRange = -1;
      while ((nIteration_Distance < iRange || out.size() == 0) && recentlyAdded.size() > 0) {
         currProvinces.clear();
         ++nIteration_Distance;
         for (int a = recentlyAdded.size() - 1; a >= 0; --a) {
            boolean wasntAdded = true;
            for (int j = currProvinces.size() - 1; j >= 0; --j) {
               if (currProvinces.get(j) == recentlyAdded.get(a)) {
                  wasntAdded = false;
                  break;
               }
            }
            if (wasntAdded) {
               currProvinces.add(recentlyAdded.get(a));
            }
         }
         recentlyAdded.clear();
         for (int a = currProvinces.size() - 1; a >= 0; --a) {
            for (int i = 0; i < CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvincesSize(); ++i) {
               if (!CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was) {
                  was.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                  CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).was = true;
                  if (CFG.game.isAlly(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) || CFG.game.getMilitaryAccess(nCivID, CFG.game.getProvince(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i)).getCivID()) > 0) {
                     if (CFG.oAI.prepareForWar_BordersWithEnemy(nCivID, CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i))) {
                        out.add(new AI_NeighProvinces(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i), nIteration_Distance));
                        if (iFirstFoundRange < 0) {
                           iFirstFoundRange = nIteration_Distance;
                        }
                     }
                     recentlyAdded.add(CFG.game.getProvince(currProvinces.get(a)).getNeighboringProvinces(i));
                  }
               }
            }
         }
         if (iFirstFoundRange > 0 && iFirstFoundRange + 2 < nIteration_Distance) {
            break;
         }
      }
      for (int k = was.size() - 1; k >= 0; --k) {
         CFG.game.getProvince(was.get(k)).was = false;
      }
      recentlyAdded.clear();
      recentlyAdded = null;
      was.clear();
      was = null;
      return out;
   }

   protected final void prepareForWar_Recruit(final int nCivID, final List<AI_ProvinceInfo_War> sortedFrontProvinces, final List<Integer> lFrontIDsWithArmies, final boolean forSeaInvasion) {
      if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT) {
         Gdx.app.log("AoC", "RECRUIT NO MOVEMNETS POINTS 0000");
         return;
      }
      if (lFrontIDsWithArmies.size() * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE > CFG.game.getCiv(nCivID).getMovePoints() && Math.max(CFG.game.getCiv(nCivID).getMoney() / 5L / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.001f) < 0.048f && CFG.oR.nextInt(100) < 85) {
         Gdx.app.log("AoC", "RECRUIT, IT IS NOT WORTH TO RECRUIT IN THIS TURN");
         return;
      }
      final int nUpkeepLeft = (int)(CFG.game.getCiv(nCivID).iBudget * (0.8f - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS) - CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC);
      if (nUpkeepLeft < 0) {
         Gdx.app.log("AoC", "RECRUIT, nUpkeepLeft: " + nUpkeepLeft);
         return;
      }
      if (!forSeaInvasion && CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getNeighboringSeaProvincesSize() > 0) {
         boolean aldAdded = false;
         for (int k = sortedFrontProvinces.size() - 1; k >= 0; --k) {
            if (sortedFrontProvinces.get(k).iProvinceID == CFG.game.getCiv(nCivID).getCapitalProvinceID()) {
               aldAdded = true;
               break;
            }
         }
         if (!aldAdded) {
            sortedFrontProvinces.add(new AI_ProvinceInfo_War(CFG.game.getCiv(nCivID).getCapitalProvinceID(), this.getPotential_BasedOnNeighboringProvs(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nCivID), true));
         }
      }
      final int numOfUnitsToRecruit_MAX = (int)(nUpkeepLeft / (CFG.game_NextTurnUpdate.getMilitaryUpkeep_WithoutDefensivePosition(sortedFrontProvinces.get(0).iProvinceID, 1000, nCivID) / 1000.0f));
      int iNumOfMaxRecruitments = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - ((lFrontIDsWithArmies.size() > 0) ? (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * lFrontIDsWithArmies.size()) : CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE)) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT, CFG.game.getCiv(nCivID).getNumOfProvinces()));
      Gdx.app.log("AoC", "prepareForWar_Recruit -> " + CFG.game.getCiv(nCivID).getCivName());
      Gdx.app.log("AoC", "prepareForWar_Recruit -> iNumOfMaxRecruitments: " + iNumOfMaxRecruitments);
      if (lFrontIDsWithArmies.size() > 1 && iNumOfMaxRecruitments > 1 && Math.min(CFG.game.getCiv(nCivID).getMoney() / 5L, numOfUnitsToRecruit_MAX) <= sortedFrontProvinces.get(0).getRecruitableArmy(nCivID) && Math.min(CFG.game.getCiv(nCivID).getMoney() / 5L, numOfUnitsToRecruit_MAX) < CFG.game.getCiv(nCivID).getNumOfUnits() * 0.35f && CFG.oR.nextInt(100) < 95) {
         iNumOfMaxRecruitments = 1;
      }
      Gdx.app.log("AoC", "prepareForWar_Recruit -> iNumOfMaxRecruitments: " + iNumOfMaxRecruitments);
      Gdx.app.log("AoC", "prepareForWar_Recruit -> numOfUnitsToRecruit_MAX: " + numOfUnitsToRecruit_MAX);
      final List<AI_ProvinceInfo_War> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo_War>();
      float totalValues = 0.0f;
      for (int i = 0; i < iNumOfMaxRecruitments && i < sortedFrontProvinces.size(); ++i) {
         tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i));
         totalValues += sortedFrontProvinces.get(i).iValue;
      }
      final int tempMoneyPre = (int)CFG.game.getCiv(nCivID).getMoney();
      boolean armyRecruited = false;
      for (int j = 0; j < tRecruitArmiesForProvinces.size(); ++j) {
         final int tArmyToRecruit_PRE = (int)(Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5)) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
         Gdx.app.log("AoC", "prepareForWar_Recruit -> RECRUIT MAX: " + CFG.game.getCiv(nCivID).getMoney() / 5L);
         Gdx.app.log("AoC", "prepareForWar_Recruit -> tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
         boolean notEnoughRecruits = false;
         if (tRecruitArmiesForProvinces.get(j).getRecruitableArmy(nCivID) < tArmyToRecruit_PRE) {
            notEnoughRecruits = true;
         }
         if (CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).isOccupied() || CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getCivID() != nCivID || notEnoughRecruits) {
            final List<AI_NeighProvinces> listOfPossibleProvincesToRecruit = CFG.oAI.getAllNeighboringProvincesInRange_RecruitAtWAr(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID, Math.max(10, CFG.game.getCiv(nCivID).getNumOfProvinces() / 8), true, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
            if (listOfPossibleProvincesToRecruit.size() > 0) {
               Gdx.app.log("AoC", "prepareForWar_Recruit -> RECRUIT MODE 000");
               int tempRand = 0;
               if (notEnoughRecruits || CFG.oR.nextInt(100) < 90) {
                  int tBest = 0;
                  int tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tBest).iProvinceID);
                  for (int l = 1; l < listOfPossibleProvincesToRecruit.size(); ++l) {
                     if (tBestArmy < CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(l).iProvinceID)) {
                        tBest = l;
                        tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(l).iProvinceID);
                     }
                  }
                  tempRand = tBest;
               }
               else {
                  tempRand = CFG.oR.nextInt(listOfPossibleProvincesToRecruit.size());
               }
               if (CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, nCivID) < tRecruitArmiesForProvinces.get(j).getRecruitableArmy(nCivID) * 1.2f) {
                  Gdx.app.log("AoC", "prepareForWar_Recruit -> RECRUIT MODE 000A -> ARMY CAN'T BE RECRUITED FROM NEIGH PROVINCES -> SEND BY SEA!");
                  final int tArmyToRecruit = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
                  Gdx.app.log("AoC", "prepareForWar_Recruit -> RECRUIT MODE 000A -> TO RECR ARMY: " + tArmyToRecruit + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID));
                  if (CFG.game.getCiv(nCivID).recruitArmy_AI(tRecruitArmiesForProvinces.get(j).iProvinceID, tArmyToRecruit)) {
                     armyRecruited = true;
                  }
               }
               else {
                  final int tArmyToRecruit = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
                  Gdx.app.log("AoC", "prepareForWar_Recruit -> RECRUIT MODE 0000B -> TO RECR ARMY: " + tArmyToRecruit + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, nCivID));
                  if (CFG.game.getCiv(nCivID).recruitArmy_AI(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tArmyToRecruit)) {
                     armyRecruited = true;
                  }
                  final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID);
                  if (tempArmy > 0) {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tRecruitArmiesForProvinces.get(j).iProvinceID, tempArmy));
                  }
               }
            }
         }
         else {
            Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 1111 -> PROV: " + tRecruitArmiesForProvinces.get(j).iProvinceID + " -> " + CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getName());
            final int tArmyToRecruit2 = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
            Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 1111 -> TO RECR ARMY: " + tArmyToRecruit2 + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID));
            if (CFG.game.getCiv(nCivID).recruitArmy_AI(tRecruitArmiesForProvinces.get(j).iProvinceID, tArmyToRecruit2)) {
               armyRecruited = true;
            }
         }
      }
      if (armyRecruited && CFG.game.getCiv(nCivID).getMoney() < 25L) {
         CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn = true;
      }
   }

   protected final void moveAtWar_Recruit(final int nCivID, final List<AI_ProvinceInfo_War> sortedFrontProvinces, final List<Integer> lFrontIDsWithArmies, final boolean forSeaInvasion) {
      if (CFG.game.getCiv(nCivID).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT) {
         Gdx.app.log("AoC", "RECRUIT NO MOVEMNETS POINTS 0000");
         return;
      }
      if (lFrontIDsWithArmies.size() * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE > CFG.game.getCiv(nCivID).getMovePoints() && Math.max(CFG.game.getCiv(nCivID).getMoney() / 5L / (float)CFG.game.getCiv(nCivID).getNumOfUnits(), 0.001f) < 0.048f && CFG.oR.nextInt(100) < 85) {
         Gdx.app.log("AoC", "RECRUIT, IT IS NOT WORTH TO RECRUIT IN THIS TURN");
         return;
      }
      final int nUpkeepLeft = (int)(CFG.game.getCiv(nCivID).iBudget * ((forSeaInvasion ? CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_NOT_BORDERING_WITH_ENEMY : ((CFG.game.getCiv(nCivID).getBordersWithEnemy() == 0) ? CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_NOT_BORDERING_WITH_ENEMY : CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_RECRUIT_AT_WAR)) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS) - CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC);
      if (nUpkeepLeft < 0) {
         Gdx.app.log("AoC", "RECRUIT, nUpkeepLeft: " + nUpkeepLeft);
         return;
      }
      if (!forSeaInvasion && CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getNeighboringSeaProvincesSize() > 0) {
         boolean aldAdded = false;
         for (int k = sortedFrontProvinces.size() - 1; k >= 0; --k) {
            if (sortedFrontProvinces.get(k).iProvinceID == CFG.game.getCiv(nCivID).getCapitalProvinceID()) {
               aldAdded = true;
               break;
            }
         }
         if (!aldAdded) {
            sortedFrontProvinces.add(new AI_ProvinceInfo_War(CFG.game.getCiv(nCivID).getCapitalProvinceID(), this.getPotential_BasedOnNeighboringProvs(CFG.game.getCiv(nCivID).getCapitalProvinceID(), nCivID), true));
         }
      }
      final int numOfUnitsToRecruit_MAX = (int)(nUpkeepLeft / (CFG.game_NextTurnUpdate.getMilitaryUpkeep_WithoutDefensivePosition(sortedFrontProvinces.get(0).iProvinceID, 1000, nCivID) / 1000.0f));
      int iNumOfMaxRecruitments = Math.max(1, Math.min((CFG.game.getCiv(nCivID).getMovePoints() - ((lFrontIDsWithArmies.size() > 0) ? (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE * lFrontIDsWithArmies.size()) : CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE_OWN_PROVINCE)) / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT, CFG.game.getCiv(nCivID).getNumOfProvinces()));
      Gdx.app.log("AoC", "moveAtWar_Recruit -> " + CFG.game.getCiv(nCivID).getCivName());
      Gdx.app.log("AoC", "moveAtWar_Recruit -> iNumOfMaxRecruitments: " + iNumOfMaxRecruitments);
      if (lFrontIDsWithArmies.size() > 1 && iNumOfMaxRecruitments > 1 && Math.min(CFG.game.getCiv(nCivID).getMoney() / 5L, numOfUnitsToRecruit_MAX) <= sortedFrontProvinces.get(0).getRecruitableArmy(nCivID) && Math.min(CFG.game.getCiv(nCivID).getMoney() / 5L, numOfUnitsToRecruit_MAX) < CFG.game.getCiv(nCivID).getNumOfUnits() * 0.35f && CFG.oR.nextInt(100) < 95) {
         iNumOfMaxRecruitments = 1;
      }
      Gdx.app.log("AoC", "moveAtWar_Recruit -> iNumOfMaxRecruitments: " + iNumOfMaxRecruitments);
      Gdx.app.log("AoC", "moveAtWar_Recruit -> numOfUnitsToRecruit_MAX: " + numOfUnitsToRecruit_MAX);
      final List<AI_ProvinceInfo_War> tRecruitArmiesForProvinces = new ArrayList<AI_ProvinceInfo_War>();
      float totalValues = 0.0f;
      for (int i = 0; i < iNumOfMaxRecruitments && i < sortedFrontProvinces.size(); ++i) {
         tRecruitArmiesForProvinces.add(sortedFrontProvinces.get(i));
         totalValues += sortedFrontProvinces.get(i).iValue;
      }
      final int tempMoneyPre = (int)CFG.game.getCiv(nCivID).getMoney();
      boolean armyRecruited = false;
      for (int j = 0; j < tRecruitArmiesForProvinces.size(); ++j) {
         final int tArmyToRecruit_PRE = (int)(Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5)) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
         Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MAX: " + CFG.game.getCiv(nCivID).getMoney() / 5L);
         Gdx.app.log("AoC", "moveAtWar_Recruit -> tArmyToRecruit_PRE: " + tArmyToRecruit_PRE);
         boolean notEnoughRecruits = false;
         if (tRecruitArmiesForProvinces.get(j).getRecruitableArmy(nCivID) < tArmyToRecruit_PRE) {
            notEnoughRecruits = true;
         }
         if (CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).isOccupied() || CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getCivID() != nCivID || notEnoughRecruits) {
            final List<AI_NeighProvinces> listOfPossibleProvincesToRecruit = CFG.oAI.getAllNeighboringProvincesInRange_RecruitAtWAr(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID, Math.max(10, CFG.game.getCiv(nCivID).getNumOfProvinces() / 8), true, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
            if (listOfPossibleProvincesToRecruit.size() > 0) {
               Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 000");
               int tempRand = 0;
               if (notEnoughRecruits || CFG.oR.nextInt(100) < 90) {
                  int tBest = 0;
                  int tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tBest).iProvinceID);
                  for (int l = 1; l < listOfPossibleProvincesToRecruit.size(); ++l) {
                     if (tBestArmy < CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(l).iProvinceID)) {
                        tBest = l;
                        tBestArmy = CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(l).iProvinceID);
                     }
                  }
                  tempRand = tBest;
               }
               else {
                  tempRand = CFG.oR.nextInt(listOfPossibleProvincesToRecruit.size());
               }
               if (CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, nCivID) < tRecruitArmiesForProvinces.get(j).getRecruitableArmy(nCivID) * 1.2f) {
                  Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 000A -> ARMY CAN'T BE RECRUITED FROM NEIGH PROVINCES -> SEND BY SEA!");
                  final int tArmyToRecruit = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
                  Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 000A -> TO RECR ARMY: " + tArmyToRecruit + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID));
                  if (CFG.game.getCiv(nCivID).recruitArmy_AI(tRecruitArmiesForProvinces.get(j).iProvinceID, tArmyToRecruit)) {
                     armyRecruited = true;
                  }
               }
               else {
                  final int tArmyToRecruit = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
                  Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 0000B -> TO RECR ARMY: " + tArmyToRecruit + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, nCivID));
                  if (CFG.game.getCiv(nCivID).recruitArmy_AI(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tArmyToRecruit)) {
                     armyRecruited = true;
                  }
                  final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID);
                  if (tempArmy > 0) {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment_War(nCivID, listOfPossibleProvincesToRecruit.get(tempRand).iProvinceID, tRecruitArmiesForProvinces.get(j).iProvinceID, tempArmy));
                  }
               }
            }
            else {
               Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 000 -> ARMY CAN'T BE RECRUITED FROM NEIGH PROVINCES -> SEND BY SEA!");
               if (!forSeaInvasion) {
                  boolean addMission = true;
                  for (int m = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; m >= 0; --m) {
                     if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(m).MISSION_TYPE == CivArmyMission_Type.NAVAL_INVASION && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(m).toProvinceID == tRecruitArmiesForProvinces.get(j).iProvinceID) {
                        addMission = false;
                        break;
                     }
                  }
                  if (addMission) {
                     int tMoveTo = tRecruitArmiesForProvinces.get(j).iProvinceID;
                     if (CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringSeaProvincesSize() == 0) {
                        final boolean provinceUpdated = false;
                        for (int z = 0; z < CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringSeaProvincesSize(); ++z) {
                           if (CFG.game.getProvince(CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringProvinces(z)).getLevelOfPort() >= 0 && (CFG.game.getProvince(CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringProvinces(z)).getCivID() == nCivID || CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringProvinces(z)).getCivID()))) {
                              if (provinceUpdated) {
                                 if (CFG.oR.nextInt(100) < 50) {
                                    tMoveTo = CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringProvinces(z);
                                 }
                              }
                              else {
                                 tMoveTo = CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getNeighboringProvinces(z);
                              }
                           }
                        }
                     }
                     this.moveAtWar_AtSea_ToProvinceID(nCivID, tMoveTo);
                  }
               }
            }
         }
         else {
            Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 1111 -> PROV: " + tRecruitArmiesForProvinces.get(j).iProvinceID + " -> " + CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getName());
            final int tArmyToRecruit2 = (int)(Math.min(numOfUnitsToRecruit_MAX, Math.min(numOfUnitsToRecruit_MAX, tempMoneyPre / ((CFG.game.getProvince(tRecruitArmiesForProvinces.get(j).iProvinceID).getLevelOfArmoury() > 0) ? 4 : 5))) * tRecruitArmiesForProvinces.get(j).iValue / totalValues);
            Gdx.app.log("AoC", "moveAtWar_Recruit -> RECRUIT MODE 1111 -> TO RECR ARMY: " + tArmyToRecruit2 + ", RECRITABLE MAX: " + CFG.gameAction.getRecruitableArmy(tRecruitArmiesForProvinces.get(j).iProvinceID, nCivID));
            if (CFG.game.getCiv(nCivID).recruitArmy_AI(tRecruitArmiesForProvinces.get(j).iProvinceID, tArmyToRecruit2)) {
               armyRecruited = true;
            }
         }
      }
      if (armyRecruited && CFG.game.getCiv(nCivID).getMoney() < 25L) {
         CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn = true;
      }
   }

   protected void buildStartingBuildings(final int nCivID) {
      try {
         if (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) {
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getTower_TechLevel(1) * 1.04f) {
               CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setLevelOfWatchTower(1);
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getFort_TechLevel(1) * 0.96f) {
               CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).setLevelOfFort(1);
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getPort_TechLevel(1) * 1.1f) {
               this.buildStartingBuildings_Port(nCivID);
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         if (CFG.LOGS) {
            CFG.exceptionStack(ex);
         }
      }
   }

   protected final void buildStartingBuildings_Port(final int nCivID) {
      int buildPortInProvinceID = -1;
      if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getLevelOfPort() >= 0) {
         buildPortInProvinceID = CFG.game.getCiv(nCivID).getCapitalProvinceID();
      }
      else {
         for (int j = 0; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
            if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getLevelOfPort() == 0) {
               if (buildPortInProvinceID < 0) {
                  buildPortInProvinceID = CFG.game.getCiv(nCivID).getProvinceID(j);
               }
               else if (CFG.game.getProvince(buildPortInProvinceID).getPopulationData().getPopulation() < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(j)).getPopulationData().getPopulation()) {
                  buildPortInProvinceID = CFG.game.getCiv(nCivID).getProvinceID(j);
               }
            }
         }
      }
      if (buildPortInProvinceID >= 0 && CFG.game.getProvince(buildPortInProvinceID).getLevelOfPort() >= 0) {
         CFG.game.getProvince(buildPortInProvinceID).setLevelOfPort(1);
      }
   }

   protected static final long getMoney_MinReserve_LockTreasury(final int nCivID) {
      if (CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment != null) {
         return Math.max(CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment.iCost, CFG.game.getCiv(nCivID).civGameData.iLockTreasury);
      }
      return CFG.game.getCiv(nCivID).civGameData.iLockTreasury;
   }

   protected static final long getMoney_MinReserve(final int nCivID) {
      return (long)Math.max((float)getMoney_MinReserve_LockTreasury(nCivID), CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE);
   }

   protected void manageBudget(final int nCivID) {
      CFG.game.getCiv(nCivID).setSpendings_Goods(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) + 0.01f, CFG.game.getCiv(nCivID).getSpendings_Goods()));
      CFG.game.getCiv(nCivID).setSpendings_Investments(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + 0.01f, CFG.game.getCiv(nCivID).getSpendings_Investments()));
      if (CFG.game.getCiv(nCivID).isAtWar() || CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) {
         if (CFG.game.getCiv(nCivID).isAtWarWithCivs.size() > 0) {
            int iBudgetOfEnemies = 0;
            for (int i = CFG.game.getCiv(nCivID).isAtWarWithCivs.size() - 1; i >= 0; --i) {
               iBudgetOfEnemies += (int)Math.max(1.0f, CFG.game.getCiv(CFG.game.getCiv(nCivID).isAtWarWithCivs.get(i)).iBudget * CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR_MODIFIER);
            }
            CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR = Math.max(Math.min(2.0f, iBudgetOfEnemies / (float)CFG.game.getCiv(nCivID).iBudget), CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS);
         }
         else {
            CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS;
         }
         float fHappinessLeft = 0.7f;
         float happinessDiff;
         if (CFG.game.getCiv(nCivID).getHappiness() - CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV < 0) {
            if (CFG.game.getCiv(nCivID).getHappiness() < 20) {
               fHappinessLeft = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 16.0f;
               happinessDiff = 0.0f;
            }
            else if (CFG.game.getCiv(nCivID).getHappiness() < 40) {
               fHappinessLeft = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 4.0f;
               happinessDiff = 0.0f;
            }
            else if (CFG.game.getCiv(nCivID).getHappiness() < 55) {
               fHappinessLeft = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 2.0f;
               happinessDiff = 0.0f;
            }
            else {
               happinessDiff = (1.0f - fHappinessLeft) * (CFG.game.getCiv(nCivID).getHappiness() / (float)CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV);
            }
         }
         else {
            happinessDiff = 1.0f - fHappinessLeft;
            if (CFG.game.getCiv(nCivID).getHappiness() > 80 && CFG.game.getCiv(nCivID).getHappiness() > CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_PROVINCE_HAPPINESS_RUN_FESTIVAL * 115.0f) {
               happinessDiff = 1.0f - fHappinessLeft + CFG.oR.nextInt(1450) / 1000.0f;
            }
         }
         CFG.game.getCiv(nCivID).setTaxationLevel((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * fHappinessLeft + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * happinessDiff) * CFG.game.getCiv(nCivID).civGameData.civPersonality.TAXATION_LEVEL);
         this.updateMilitarySpendings(nCivID);
         float reserveModifier = 0.675f;
         float nSpendingsLeft = 0.97f - CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS;
         if (CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR > CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC) {
            reserveModifier = reserveModifier - 0.4f - 0.375f * (1.0f - CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC / Math.min(1.0f, CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_MILITARY_SPENDINGS_WAR));
         }
         else {
            for (int j = CFG.game.getCiv(nCivID).isAtWarWithCivs.size() - 1; j >= 0; --j) {}
            reserveModifier = 0.25f;
         }
         if (CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f > 0.0f) {
            reserveModifier = 1.0f + CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f;
         }
         if (nSpendingsLeft > 0.0f) {
            if (CFG.game.getCiv(nCivID).getMoney() < 0L) {
               nSpendingsLeft *= 0.04f + CFG.oR.nextInt(46) / 100.0f;
               final float tTotal = CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET;
               CFG.game.getCiv(nCivID).setSpendings_Goods(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) + CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET / tTotal * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)));
               CFG.game.getCiv(nCivID).setSpendings_Investments(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET / tTotal * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS));
               CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
            }
            else {
               float extraDevelopment = 1.0f;
               if (CFG.game.getCiv(nCivID).fAverageDevelopment / CFG.game.getCiv(nCivID).getTechnologyLevel() < CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY) {
                  extraDevelopment = 1.0f - CFG.game.getCiv(nCivID).fAverageDevelopment / CFG.game.getCiv(nCivID).getTechnologyLevel();
               }
               final float tTotal2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET + (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET) * extraDevelopment + CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET;
               CFG.game.getCiv(nCivID).setSpendings_Goods(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) + CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier, CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)));
               CFG.game.getCiv(nCivID).setSpendings_Investments(Math.max((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET + (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET) * extraDevelopment) / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier, CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS));
               CFG.game.getCiv(nCivID).setSpendings_Research(CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier);
            }
         }
         else {
            CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
         }
      }
      else {
         float reserveModifier2 = 1.0f;
         if (CFG.game.getCiv(nCivID).getMoney() < getMoney_MinReserve_LockTreasury(nCivID)) {
            reserveModifier2 = 0.275f;
            if (CFG.game.getCiv(nCivID).getMoney() > 0L) {
               reserveModifier2 += 0.225f * (CFG.game.getCiv(nCivID).getMoney() / (float)getMoney_MinReserve_LockTreasury(nCivID));
            }
         }
         else {
            if (CFG.game.getCiv(nCivID).getMoney() < getMoney_MinReserve(nCivID)) {
               if (CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f > 0.0f) {
                  reserveModifier2 = 1.0f + CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f;
                  CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE = Math.max(2.25f, CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE - 0.75f);
               }
               else {
                  reserveModifier2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE_MODIFIER + (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE_MODIFIER) * CFG.game.getCiv(nCivID).getMoney() / (CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).civGameData.civPersonality.TREASURY_RESERVE);
               }
            }
            else if (CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f > 0.0f) {
               reserveModifier2 = 1.0f + CFG.game_NextTurnUpdate.getInflationPerc(nCivID) * 100.0f;
            }
            if (CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() > 0) {
               final int tAssimilateCost = DiplomacyManager.assimilateCost(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(0), 25) * CFG.game.getCiv(nCivID).lProvincesWithLowStability.size();
               reserveModifier2 = Math.min(reserveModifier2, 0.1f + 0.9f * CFG.game.getCiv(nCivID).getMoney() / tAssimilateCost);
            }
         }
         float fHappinessLeft2 = 0.7f;
         float happinessDiff2;
         if (CFG.game.getCiv(nCivID).getHappiness() - CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV < 0) {
            if (CFG.game.getCiv(nCivID).getHappiness() < 20) {
               fHappinessLeft2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 16.0f;
               happinessDiff2 = 0.0f;
            }
            else if (CFG.game.getCiv(nCivID).getHappiness() < 40) {
               fHappinessLeft2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 4.0f;
               happinessDiff2 = 0.0f;
            }
            else if (CFG.game.getCiv(nCivID).getHappiness() < 55) {
               fHappinessLeft2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV / 100.0f / 2.0f;
               happinessDiff2 = 0.0f;
            }
            else {
               happinessDiff2 = (1.0f - fHappinessLeft2) * (CFG.game.getCiv(nCivID).getHappiness() / (float)CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_HAPPINESS_FOR_CIV);
            }
         }
         else {
            happinessDiff2 = 1.0f - fHappinessLeft2;
            if (CFG.game.getCiv(nCivID).getHappiness() > 80 && CFG.game.getCiv(nCivID).getHappiness() > CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_PROVINCE_HAPPINESS_RUN_FESTIVAL * 115.0f) {
               happinessDiff2 = 1.0f - fHappinessLeft2 + CFG.oR.nextInt(1450) / 1000.0f;
            }
         }
         CFG.game.getCiv(nCivID).setTaxationLevel((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * fHappinessLeft2 + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).ACCEPTABLE_TAXATION * happinessDiff2) * CFG.game.getCiv(nCivID).civGameData.civPersonality.TAXATION_LEVEL);
         this.updateMilitarySpendings(nCivID);
         float nSpendingsLeft = 0.97f - CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS;
         if (nSpendingsLeft > 0.0f) {
            if (CFG.game.getCiv(nCivID).getMoney() < 0L) {
               nSpendingsLeft *= 0.04f + CFG.oR.nextInt(46) / 100.0f;
               final float tTotal = CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET;
               CFG.game.getCiv(nCivID).setSpendings_Goods(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) + CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET / tTotal * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)));
               CFG.game.getCiv(nCivID).setSpendings_Investments(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET / tTotal * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS));
               CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
            }
            else {
               float extraDevelopment = 1.0f;
               if (CFG.game.getCiv(nCivID).fAverageDevelopment / CFG.game.getCiv(nCivID).getTechnologyLevel() < CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY) {
                  extraDevelopment = 1.0f + (CFG.game.getCiv(nCivID).civGameData.civPersonality.MIN_DIFFERENCE_IN_DEVELOPMENT_TO_TECHNOLOGY - CFG.game.getCiv(nCivID).fAverageDevelopment / CFG.game.getCiv(nCivID).getTechnologyLevel()) / CFG.game.getCiv(nCivID).getTechnologyLevel();
               }
               final float tTotal2 = CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET * extraDevelopment + CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET;
               CFG.game.getCiv(nCivID).setSpendings_Goods(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) + CFG.game.getCiv(nCivID).civGameData.civPersonality.GOODS_EXTRA_PERC_OF_BUDGET / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier2, CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID)));
               CFG.game.getCiv(nCivID).setSpendings_Investments(Math.max(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS + CFG.game.getCiv(nCivID).civGameData.civPersonality.INVESTMENTS_EXTRA_PERC_OF_BUDGET * extraDevelopment / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier2, CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS));
               CFG.game.getCiv(nCivID).setSpendings_Research(CFG.game.getCiv(nCivID).civGameData.civPersonality.RESEARCH_PERC_OF_BUDGET / tTotal2 * (nSpendingsLeft * CFG.game.getCiv(nCivID).civGameData.civPersonality.USE_OF_BUDGET_FOR_SPENDINGS) * reserveModifier2);
            }
         }
         else {
            CFG.game.getCiv(nCivID).setSpendings_Research(0.0f);
         }
      }
   }

   protected final void manageVassalsTribute(final int nCivID) {
      try {
         for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.lVassals.size(); ++i) {
            if (CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).autonomyStatus.isEconomicControl()) {
               CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).setTribute((int) (20.0f * (CFG.game.getCiv(nCivID).civGameData.civPersonality.VASSALS_TRIBUTE_PERC - ((CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID) > 0.0f) ? (CFG.game.getCiv(nCivID).civGameData.civPersonality.VASSALS_TRIBUTE_PERC * CFG.game.getCiv(nCivID).civGameData.civPersonality.VASSALS_TRIBUTE_PERC_FRIENDLY * CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID) / 100.0f) : 0.0f) + CFG.oR.nextInt((int) (CFG.game.getCiv(nCivID).civGameData.civPersonality.VASSALS_TRIBUTE_PERC_RAND * 100.0f)) / 100.0f)));
            } else {
               this.manageBudget(CFG.game.getCiv(nCivID).civGameData.lVassals.get(i).iCivID);
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
   }

   protected final void happinessCrisis(final int nCivID) {
      try {
         if (CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() > 0 && CFG.game.getCiv(nCivID).getMovePoints() >= 8 && CFG.game.getCiv(nCivID).getMoney() >= 0.5f * DiplomacyManager.festivalCost(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(0))) {
            final List<Assimilate_Data> tempProvincesScore = new ArrayList<Assimilate_Data>();
            final int tempCapital = (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) ? CFG.game.getCiv(nCivID).getCapitalProvinceID() : CFG.game.getCiv(nCivID).getProvinceID(0);
            for (int i = CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() - 1; i >= 0; --i) {
               if (CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getHappiness() < Game_Action.RISE_REVOLT_RISK_HAPPINESS) {
                  tempProvincesScore.add(new Assimilate_Data(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i), CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getPopulationData().getPopulation() * (1.0f - CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getHappiness() / 4.0f)));
               }
               else {
                  tempProvincesScore.add(new Assimilate_Data(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i), CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getPopulationData().getPopulation() * (1.0f - CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getHappiness())));
               }
            }
            final List<Assimilate_Data> tempSorted = new ArrayList<Assimilate_Data>();
            while (tempProvincesScore.size() > 0) {
               int tBest = 0;
               for (int j = tBest + 1; j < tempProvincesScore.size(); ++j) {
                  if (tempProvincesScore.get(j).fScore > tempProvincesScore.get(tBest).fScore) {
                     tBest = j;
                  }
               }
               tempSorted.add(tempProvincesScore.get(tBest));
               tempProvincesScore.remove(tBest);
            }
            while (CFG.game.getCiv(nCivID).getMovePoints() >= 8) {
               if (tempSorted.size() == 0) {
                  break;
               }
               if (!DiplomacyManager.addFestival(nCivID, tempSorted.get(0).iProvinceID)) {
                  break;
               }
               tempSorted.remove(0);
            }
            tempSorted.clear();
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final NullPointerException ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected void updateMilitarySpendings(final int nCivID) {
      CFG.game.getCiv(nCivID).iMilitaryUpkeep_Total = (int)CFG.game_NextTurnUpdate.getMilitaryUpkeep_Total(nCivID);
      if (CFG.game.getCiv(nCivID).iBudget <= 0 && CFG.game.getCiv(nCivID).getNumOfUnits() > 0) {
         CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC = 100.0f;
      }
      else {
         CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC = Math.max(0.0f, CFG.game.getCiv(nCivID).iMilitaryUpkeep_Total / (float)CFG.game.getCiv(nCivID).iBudget);
      }
   }

   protected long build_GetMoney(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getMoney() < getMoney_MinReserve(nCivID)) {
         return 0L;
      }
      return CFG.game.getCiv(nCivID).getMoney() - getMoney_MinReserve(nCivID);
   }

   protected void buildBuildings(final int nCivID) {
      if (this.build_GetMoney(nCivID) > 0L) {
         List<AI_Build> buildingsScore = new ArrayList<AI_Build>();
         List<AI_Build_Option> buildingsOptions = new ArrayList<AI_Build_Option>();
         try {
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getFarm_TechLevel(1) && CFG.game.getCiv(nCivID).iNumOf_Farms_ProvincesPossibleToBuild * BuildingsManager.getWorkshop_MaxLevel_CanBuild(nCivID) > CFG.game.getCiv(nCivID).iNumOf_Farms) {
               buildingsOptions.add(new AI_Build_Option());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getWorkshop_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() * BuildingsManager.getWorkshop_MaxLevel_CanBuild(nCivID) > CFG.game.getCiv(nCivID).iNumOf_Workshops) {
               buildingsOptions.add(new AI_Build_Option_Workshop());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getLibrary_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() * BuildingsManager.getLibrary_MaxLevel_CanBuild(nCivID) > CFG.game.getCiv(nCivID).iNumOf_Libraries) {
               buildingsOptions.add(new AI_Build_Option_Library());
            }
            if (CFG.game.getCiv(nCivID).getSeaAccess() > 0 && CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getPort_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() > CFG.game.getCiv(nCivID).iNumOf_Ports) {
               buildingsOptions.add(new AI_Build_Option_Port());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getArmoury_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() > CFG.game.getCiv(nCivID).iNumOf_Armories) {
               buildingsOptions.add(new AI_Build_Option_Armoury());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getSupply_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() > CFG.game.getCiv(nCivID).iNumOf_SuppliesCamp) {
               buildingsOptions.add(new AI_Build_Option_Supplies());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getFort_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() * BuildingsManager.getFort_MaxLevel_CanBuild(nCivID) > CFG.game.getCiv(nCivID).iNumOf_Forts) {
               buildingsOptions.add(new AI_Build_Option_Fort());
            }
            if (CFG.game.getCiv(nCivID).getTechnologyLevel() >= BuildingsManager.getTower_TechLevel(1) && CFG.game.getCiv(nCivID).getNumOfProvinces() * BuildingsManager.getTower_MaxLevel_CanBuild(nCivID) > CFG.game.getCiv(nCivID).iNumOf_Towers) {
               buildingsOptions.add(new AI_Build_Option_Tower());
            }
            buildingsOptions.add(new AI_Build_Option_Invest());
            if (CFG.game.getCiv(nCivID).fAverageDevelopment / CFG.game.getCiv(nCivID).getTechnologyLevel() < 0.9f) {
               buildingsOptions.add(new AI_Build_Option_Invest_Development());
            }
            if (buildingsOptions.size() > 0) {
               int tBestScore = 0;
               for (int i = tBestScore + 1; i < buildingsOptions.size(); ++i) {
                  if (buildingsOptions.get(i).getScore(nCivID) > buildingsOptions.get(tBestScore).getScore(nCivID)) {
                     tBestScore = i;
                  }
               }
               buildingsScore.add(buildingsOptions.get(tBestScore).getData(nCivID));
               if (buildingsScore.get(0).build(nCivID, 0, false)) {
                  CFG.game.getCiv(nCivID).buildCivPersonality_Buildings();
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }
         catch (final NullPointerException ex2) {
            CFG.exceptionStack(ex2);
         }
         buildingsOptions.clear();
         buildingsOptions = null;
         buildingsScore.clear();
         buildingsScore = null;
      }
   }

   protected final void prepareArmyForRevolution(final int nCivID) {
   }

   protected final void assimilateProvinces(final int nCivID) {
      try {
         //assim threshold reduced
         //         if (CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 6 && CFG.game.getCiv(nCivID).getMoney() >= 1.225f * DiplomacyManager.assimilateCost(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(0), 10)) {
         if (CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 6 && CFG.game.getCiv(nCivID).getMoney() >= 1.125f * DiplomacyManager.assimilateCost(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(0), 10)) {
            final List<Assimilate_Data> tempAssimilateProvinces = new ArrayList<Assimilate_Data>();
            final int tempCapital = (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) ? CFG.game.getCiv(nCivID).getCapitalProvinceID() : CFG.game.getCiv(nCivID).getProvinceID(0);
            for (int i = CFG.game.getCiv(nCivID).lProvincesWithLowStability.size() - 1; i >= 0; --i) {
               tempAssimilateProvinces.add(new Assimilate_Data(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(i), CFG.game.getCiv(nCivID).civGameData.civPersonality.ASSIMILATE_PERC_POPULATION_SCORE * Math.min(CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(i)).getPopulationData().getPopulation() / (float)CFG.game.getGameScenarios().getScenario_StartingPopulation(), 1.0f) + CFG.game.getCiv(nCivID).civGameData.civPersonality.ASSIMILATE_PERC_DISTANCE_SCORE * CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(tempCapital, CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(i)) + CFG.game.getCiv(nCivID).civGameData.civPersonality.ASSIMILATE_PERC_LOW_STABILITY_SCORE * (1.0f - CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowStability.get(i)).getProvinceStability())));
            }
            final List<Assimilate_Data> tempSortedAssimilate = new ArrayList<Assimilate_Data>();
            while (tempAssimilateProvinces.size() > 0) {
               int tBest = 0;
               for (int j = tBest + 1; j < tempAssimilateProvinces.size(); ++j) {
                  if (tempAssimilateProvinces.get(j).fScore > tempAssimilateProvinces.get(tBest).fScore) {
                     tBest = j;
                  }
               }
               tempSortedAssimilate.add(tempAssimilateProvinces.get(tBest));
               tempAssimilateProvinces.remove(tBest);
            }
            while (CFG.game.getCiv(nCivID).getDiplomacyPoints() >= 6) {
               if (tempSortedAssimilate.size() == 0) {
                  break;
               }
               //               if (CFG.game.getCiv(nCivID).getMoney() < 1.225f * DiplomacyManager.assimilateCost(tempSortedAssimilate.get(0).iProvinceID, 10) || !DiplomacyManager.addAssimilate(nCivID, tempSortedAssimilate.get(0).iProvinceID, (int)Math.min(Math.min((100.0f - CFG.game.getProvince(tempSortedAssimilate.get(0).iProvinceID).getProvinceStability() * 100.0f) / 1.724f, (float)(CFG.game.getCiv(nCivID).getMoney() / DiplomacyManager.assimilateCost(tempSortedAssimilate.get(0).iProvinceID, 1))), 50.0f))) {
               if (CFG.game.getCiv(nCivID).getMoney() < 1.125f * DiplomacyManager.assimilateCost(tempSortedAssimilate.get(0).iProvinceID, 10) || !DiplomacyManager.addAssimilate(nCivID, tempSortedAssimilate.get(0).iProvinceID, (int)Math.min(Math.min((100.0f - CFG.game.getProvince(tempSortedAssimilate.get(0).iProvinceID).getProvinceStability() * 100.0f) / 1.724f, (float)(CFG.game.getCiv(nCivID).getMoney() / DiplomacyManager.assimilateCost(tempSortedAssimilate.get(0).iProvinceID, 1))), 50.0f))) {
                  break;
               }
               tempSortedAssimilate.remove(0);
            }
            tempSortedAssimilate.clear();
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final NullPointerException ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final void hostFestivals(final int nCivID, int iLimit) {
      try {
         if (CFG.game.getCiv(nCivID).getMovePoints() >= 8 && CFG.game.getCiv(nCivID).getMoney() >= 1.05f * DiplomacyManager.festivalCost(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(0))) {
            final List<Assimilate_Data> tempProvincesScore = new ArrayList<Assimilate_Data>();
            final int tempCapital = (CFG.game.getCiv(nCivID).getCapitalProvinceID() >= 0) ? CFG.game.getCiv(nCivID).getCapitalProvinceID() : CFG.game.getCiv(nCivID).getProvinceID(0);
            for (int i = CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.size() - 1; i >= 0; --i) {
               tempProvincesScore.add(new Assimilate_Data(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i), (CFG.game.getCiv(nCivID).civGameData.civPersonality.ASSIMILATE_PERC_POPULATION_SCORE * Math.min(CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getPopulationData().getPopulation() / (float)CFG.game.getGameScenarios().getScenario_StartingPopulation(), 1.0f) + CFG.game.getCiv(nCivID).civGameData.civPersonality.ASSIMILATE_PERC_DISTANCE_SCORE * CFG.game_NextTurnUpdate.getDistanceFromCapital_PercOfMax(tempCapital, CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i))) * CFG.game.getProvince(CFG.game.getCiv(nCivID).lProvincesWithLowHappiness.get(i)).getProvinceStability()));
            }
            final List<Assimilate_Data> tempSorted = new ArrayList<Assimilate_Data>();
            while (tempProvincesScore.size() > 0) {
               int tBest = 0;
               for (int j = tBest + 1; j < tempProvincesScore.size(); ++j) {
                  if (tempProvincesScore.get(j).fScore > tempProvincesScore.get(tBest).fScore) {
                     tBest = j;
                  }
               }
               tempSorted.add(tempProvincesScore.get(tBest));
               tempProvincesScore.remove(tBest);
            }
            while (CFG.game.getCiv(nCivID).getMovePoints() >= 8) {
               if (tempSorted.size() == 0) {
                  break;
               }
               if (CFG.game.getCiv(nCivID).getMoney() < 1.125f * DiplomacyManager.festivalCost(tempSorted.get(0).iProvinceID) || !DiplomacyManager.addFestival(nCivID, tempSorted.get(0).iProvinceID)) {
                  break;
               }
               tempSorted.remove(0);
               if (iLimit-- <= 0) {
                  return;
               }
            }
            tempSorted.clear();
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final NullPointerException ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final void changeTypeOfIdeology(final int nCivID) {
      if (CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment != null) {
         if (CFG.game.getCiv(nCivID).isAtWar()) {
            CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment = null;
         }
         else if (CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment.action(nCivID)) {
            CFG.game.getCiv(nCivID).civGameData.changeTypeOfGoverment = null;
         }
      }
   }

   protected final void relocateLostCapital(final int nCivID) {
      try {
         if (CFG.game.getCiv(nCivID).getCapitalProvinceID() != CFG.game.getCiv(nCivID).getCoreCapitalProvinceID() && CFG.game.getCiv(nCivID).getCoreCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(nCivID).getCoreCapitalProvinceID()).getCivID() == nCivID && !CFG.game.getProvince(CFG.game.getCiv(nCivID).getCoreCapitalProvinceID()).isOccupied() && (CFG.game.getCiv(nCivID).getCapitalProvinceID() < 0 || CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() == nCivID)) {
            if (CFG.game.getCiv(nCivID).getMoney() > CFG.gameAction.moveCapital_Cost(nCivID) * 4.76124f) {
               CFG.gameAction.moveCapital(nCivID, CFG.game.getCiv(nCivID).getCoreCapitalProvinceID());
            }
         }
         else if (CFG.game.getCiv(nCivID).getCapitalProvinceID() < 0 || (CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID() != nCivID && (!CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).isOccupied() || !CFG.game.getCivsAtWar(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).getCapitalProvinceID()).getCivID())))) {
            int bestProvinceID = CFG.game.getCiv(nCivID).getProvinceID(0);
            int bestScore = this.relocateLostCapital_ProvinceScore(nCivID, CFG.game.getCiv(nCivID).getProvinceID(0));
            for (int j = 1; j < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++j) {
               final int tempScore = this.relocateLostCapital_ProvinceScore(nCivID, CFG.game.getCiv(nCivID).getProvinceID(j));
               if (bestScore < tempScore) {
                  bestScore = tempScore;
                  bestProvinceID = CFG.game.getCiv(nCivID).getProvinceID(j);
               }
            }
            if (!CFG.game.getProvince(bestProvinceID).isOccupied()) {
               CFG.gameAction.moveCapital(nCivID, bestProvinceID);
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final NullPointerException ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final int relocateLostCapital_ProvinceScore(final int nCivID, final int nProvinceID) {
      return CFG.game.getProvince(nProvinceID).isOccupied() ? -1 : ((int)(CFG.game.getProvince(nCivID).getPopulationData().getPopulationOfCivID(nCivID) + CFG.game.getProvince(nCivID).getPopulationData().getPopulation() / 15.0f + CFG.game.getProvince(nCivID).getEconomy() / 6.0f));
   }

   protected final void respondToEvents(final int nCivID) {
      CFG.game.getCiv(nCivID).runNextEvent();
   }

   protected final void respondToMessages(final int nCivID) {
      try {
         if (Objects.equals(CFG.game.getCiv(nCivID).getCivName(), "Oregon")) {
            CFG.toast.setInView("Bastards");
         }
         for (int i = CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessagesSize() - 1; i >= 0; --i) {
            //if is puppet, message sent from lord, and no trade perms, accept
            if (CFG.game.getCiv(nCivID).getIsPupet() &&
                CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID == CFG.game.getCiv(nCivID).getPuppetOfCivID() &&
                CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isTradeIntegration()
            ) {
               if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).messageType == Message_Type.PEACE_TREATY_LIST_OF_DEMANDS) {
                  DiplomacyManager.acceptPeaceTreaty(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).TAG, true);
               } else {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
               }
               CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
               return;
            }

            switch (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).messageType) {
               case PEACE_TREATY_LIST_OF_DEMANDS: {
                  Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> " + CFG.game.getCiv(nCivID).getCivName());
                  try {

                     if (!CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).REVOLUTIONARY) {
                        final int peaceID = CFG.game.getPeaceTreaty_GameDataID(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).TAG);
                        if (peaceID >= 0) {
                           final PeaceTreaty_Data tempData = new PeaceTreaty_Data(CFG.game.lPeaceTreaties.get(peaceID).peaceTreaty_GameData);
                           final int warID = CFG.game.getWarID(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID);

                           boolean canEnd = (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getNumOfProvinces() == 0 || CFG.game.isAlly(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID));
                           //if (!canEnd) {
                           //   try {
                           //      canEnd = (Game_Calendar.TURN_ID > CFG.game.getWar(warID).getWarTurnID() + 39);
                           //   }
                           //   catch (final IndexOutOfBoundsException ex) {
                           //      CFG.exceptionStack(ex);
                           //   }
                           //}

                           //if canend war state on
                           canEnd = canEnd && CFG.game.getWar(warID).canEnd;

                           Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 000");
                           if (canEnd) {
                              Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 111");
                              CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                           } else if (CFG.game.lPeaceTreaties.size() > 0) {
                              //if puppet and lord in war, break
                              if (CFG.game.getCiv(nCivID).getIsPupet() && (CFG.game.getWar(warID).getIsAggressor(CFG.game.getCiv(nCivID).getPuppetOfCivID()) || CFG.game.getWar(warID).getIsDefender(CFG.game.getCiv(nCivID).getPuppetOfCivID()))) break;

                              Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 2222");
                              int powerLeft = 0;
                              int powerRight = 0;
                              boolean canEnd_V2 = false;
                              Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 3333");
                              try {
                                 for (int o = 0; o < tempData.peaceTreatyGameData.lCivsData_Defenders.size(); ++o) {
                                    if (CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Defenders.get(o).iCivID).getNumOfProvinces() > 0) {
                                       powerLeft += (int)(Math.max(CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Defenders.get(o).iCivID).getMoney(), 0L) / 5L + CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Defenders.get(o).iCivID).getNumOfUnits() + CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Defenders.get(o).iCivID).getNumOfProvinces());
                                    }
                                    else {
                                       canEnd_V2 = true;
                                    }
                                 }
                                 for (int o = 0; o < tempData.peaceTreatyGameData.lCivsData_Aggressors.size(); ++o) {
                                    if (CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Aggressors.get(o).iCivID).getNumOfProvinces() > 0) {
                                       powerRight += (int)(Math.max(CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Aggressors.get(o).iCivID).getMoney(), 0L) / 5L + CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Aggressors.get(o).iCivID).getNumOfUnits() + CFG.game.getCiv(tempData.peaceTreatyGameData.lCivsData_Aggressors.get(o).iCivID).getNumOfProvinces());
                                    }
                                    else {
                                       canEnd_V2 = true;
                                    }
                                 }
                              }
                              catch (final IndexOutOfBoundsException ex2) {
                                 canEnd_V2 = true;
                              }
                              Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 4444");
                              if (canEnd_V2) {
                                 Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 5555");
                                 CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                              }
                              else if (CFG.game.getWar(warID).getIsDefender(nCivID)) {
                                 Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 666");
                                 if (powerLeft > powerRight * 0.475f) {
                                    Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 7777");
                                    CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                                 }
                                 else {
                                    Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 8888");
                                    CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                                 }
                              }
                              else {
                                 Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 999");
                                 if (powerRight > powerLeft * 0.475f) {
                                    Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 10");
                                    CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                                 }
                                 else {
                                    Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 11");
                                    CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                                 }
                              }
                           }
                           else {
                              Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> 12");
                              CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                           }
                        }
                        Gdx.app.log("AoC", "respondToMessages -> PEACE_TREATY_LIST_OF_DEMANDS -> END");
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                     }
                  }
                  catch (final IndexOutOfBoundsException ex3) {
                     DiplomacyManager.acceptPeaceTreaty(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).TAG);
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);

                     if (CFG.LOGS) CFG.exceptionStack(ex3);
                  }
                  break;
               }
               case WE_CAN_SIGN_PEACE:
               case WE_CAN_SIGN_PEACE_STATUS_QUO: {
                  Gdx.app.log("AoC", "respondToMessages -> WE_CAN_SIGN_PEACE -> " + CFG.game.getCiv(nCivID).getCivName() + " -> ");
                  final int nWarID = CFG.game.getWarID(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID);
                  boolean playerTakesPartInPeaceTreaty = false;
                  if (nWarID >= 0) {
                     if (!CFG.game.getWar(nWarID).canEnd) break;
                     //if puppet and lord in war, break
                     if (CFG.game.getCiv(nCivID).getIsPupet() && (CFG.game.getWar(nWarID).getIsAggressor(CFG.game.getCiv(nCivID).getPuppetOfCivID()) || CFG.game.getWar(nWarID).getIsDefender(CFG.game.getCiv(nCivID).getPuppetOfCivID()))) break;

                     //if (CFG.game.getPeaceTreaty_GameData_AlreadySent(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     //   break;
                     //}
                     final List<Boolean> lDefenders = new ArrayList<Boolean>();
                     final List<Boolean> lAggressors = new ArrayList<Boolean>();
                     if (CFG.game.getWar(nWarID).getIsAggressor(nCivID)) {
                        for (int o2 = 0; o2 < CFG.game.getWar(nWarID).getAggressorsSize(); ++o2) {
                           lAggressors.add(true);
                           if (CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getControlledByPlayer()) {
                              boolean playerOccupiedProvincesInThisPeace = false;
                              for (int z = 0; z < CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getNumOfProvinces(); ++z) {
                                 if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getProvinceID(z)).isOccupied()) {
                                    for (int p = 0; p < CFG.game.getWar(nWarID).getDefendersSize(); ++p) {
                                       if (CFG.game.getWar(nWarID).getDefenderID(p).getCivID() == CFG.game.getProvince(CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getProvinceID(z)).getTrueOwnerOfProvince()) {
                                          playerOccupiedProvincesInThisPeace = true;
                                          z = CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getNumOfProvinces();
                                          break;
                                       }
                                    }
                                 }
                              }
                              if (playerOccupiedProvincesInThisPeace) {
                                 playerTakesPartInPeaceTreaty = true;
                              }
                           }
                        }
                        for (int o2 = 0; o2 < CFG.game.getWar(nWarID).getDefendersSize(); ++o2) {
                           lDefenders.add(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID() == CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID || CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getNumOfProvinces() == 0);
                        }
                     }
                     else {
                        for (int o2 = 0; o2 < CFG.game.getWar(nWarID).getAggressorsSize(); ++o2) {
                           lAggressors.add(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID() == CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID || CFG.game.getCiv(CFG.game.getWar(nWarID).getAggressorID(o2).getCivID()).getNumOfProvinces() == 0);
                        }
                        for (int o2 = 0; o2 < CFG.game.getWar(nWarID).getDefendersSize(); ++o2) {
                           lDefenders.add(true);
                           if (CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getControlledByPlayer()) {
                              //bugfix??? change//
                              //playerTakesPartInPeaceTreaty = true;
                              boolean playerOccupiedProvincesInThisPeace = false;
                              for (int z = 0; z < CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getNumOfProvinces(); ++z) {
                                 if (CFG.game.getProvince(CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getProvinceID(z)).isOccupied()) {
                                    for (int p = 0; p < CFG.game.getWar(nWarID).getAggressorsSize(); ++p) {
                                       if (CFG.game.getWar(nWarID).getAggressorID(p).getCivID() == CFG.game.getProvince(CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getProvinceID(z)).getTrueOwnerOfProvince()) {
                                          playerOccupiedProvincesInThisPeace = true;
                                          z = CFG.game.getCiv(CFG.game.getWar(nWarID).getDefenderID(o2).getCivID()).getNumOfProvinces();
                                          break;
                                       }
                                    }
                                 }
                              }
                              if (playerOccupiedProvincesInThisPeace) {
                                 playerTakesPartInPeaceTreaty = true;
                              }
                           }
                        }
                     }
                     if (playerTakesPartInPeaceTreaty) {
                        Gdx.app.log("AoC", "respondToMessages -> WE_CAN_SIGN_PEACE -> playerTakesPartInPeaceTreaty: " + playerTakesPartInPeaceTreaty);
                        break;
                     }
                     Menu_PeaceTreaty.WAR_ID = nWarID;
                     CFG.peaceTreatyData = new PeaceTreaty_Data(Menu_PeaceTreaty.WAR_ID, lDefenders, lAggressors, CFG.game.getWar(nWarID).getIsAggressor(nCivID));
                     Gdx.app.log("AoC", "respondToMessages -> WE_CAN_SIGN_PEACE -> 000");
                     CFG.peaceTreatyData.AI_UseVictoryPoints();
                     Gdx.app.log("AoC", "respondToMessages -> WE_CAN_SIGN_PEACE -> 111");
                     DiplomacyManager.sendPeaceTreaty(CFG.game.getWar(CFG.peaceTreatyData.peaceTreatyGameData.iWarID).getIsAggressor(nCivID), nCivID, CFG.peaceTreatyData.peaceTreatyGameData);
                  }
                  Gdx.app.log("AoC", "respondToMessages -> WE_CAN_SIGN_PEACE -> END");
                  break;
               }
               case TECHNOLOGY_POINTS: {
                  this.useTechnologyPoints(nCivID);
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case JOIN_ALLIANCE: {
                  ArrayList<Integer> topPrestige = getTopPrestige();
                  int iCivID = CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID;
                  //weight starts at relation / 2, higher for higher rel
                  float iWeight = CFG.game.getCivRelation_OfCivB(nCivID, iCivID) / 2.5F;
                  //get ratio of prestiges times size of civ for weight, higher prestige relative = higher weight
                  iWeight += Math.pow((float)topPrestige.indexOf(nCivID)/((0.1F * (float)CFG.game.getCivsSize()) + (0.9F * (float)topPrestige.indexOf(iCivID))), 2.0F);

                  //iWeight += ((float)topPrestige.indexOf(nCivID) / (float)topPrestige.indexOf(iCivID)) * ((float)CFG.game.getCivsSize() / 7.0F);

                  Gdx.app.log("AoC2.5", "Accept Alliance Weight " + iWeight);
                  //if weight and random above 65
                  if (Math.round(iWeight) > 0 && CFG.oR.nextInt(Math.round(iWeight)) > 10) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  } else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }

                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case NONAGGRESSIONPACT: {
                  if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > -35.0f) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case PREPARE_FOR_WAR: {
                  //if is puppet, message sent from lord, and auto-join war, accept
                  //or if requester is puppet of this and auto-join war
                  if (CFG.game.getCiv(nCivID).getIsPupet() &&
                          CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID == CFG.game.getCiv(nCivID).getPuppetOfCivID() &&
                          CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(nCivID).isAutoJoinWarPerms()
                  ) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                     break;
                  }

                  if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case DEFENSIVEPACT: {
                  if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 10.0f) {
                     boolean sameRivals = false;
                     for (int a = 0; a < CFG.game.getCiv(nCivID).getHatedCivsSize(); ++a) {
                        for (int b = 0; b < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getHatedCivsSize(); ++b) {
                           if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).isHatedCiv(CFG.game.getCiv(nCivID).getHatedCiv(a).iCivID)) {
                              sameRivals = true;
                              break;
                           }
                        }
                        if (sameRivals) {
                           break;
                        }
                     }
                     if (sameRivals) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case DEFENSIVEPACT_EXPIRED: {
                  if (!CFG.game.getCiv(nCivID).isAtWar() && !CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).isAtWar() && CFG.game.getCiv(nCivID).isFriendlyCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 0 && CFG.oR.nextInt(100) < 9) {
                     DiplomacyManager.sendDefensivePactProposal(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID, 40);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case NONAGGRESSIONPACT_EXPIRED: {
                  if (!CFG.game.getCivsAtWar(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) && CFG.game.getCiv(nCivID).isFriendlyCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 0 && CFG.oR.nextInt(100) < 9) {
                     DiplomacyManager.sendNonAggressionProposal(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID, 40);
                     break;
                  }
                  break;
               }
               case GIFT: {
                  final float nPercOfBudget = CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iValue / (float)CFG.game.getCiv(nCivID).iBudget;
                  if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().addImproveRelations(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 5 + CFG.oR.nextInt(10));
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).isHatedCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) || CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).isHatedCiv(nCivID)) {
                     if (nPercOfBudget > 0.785f + CFG.oR.nextInt(100) / 1000.0f) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().addImproveRelations(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 5 + CFG.oR.nextInt(10));
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else if (nPercOfBudget < 0.32f + CFG.oR.nextInt(85) / 1000.0f) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().addImproveRelations(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 5 + CFG.oR.nextInt(10));
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     if (nPercOfBudget > 0.21f + CFG.oR.nextInt(100) / 1000.0f) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().addImproveRelations(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 3 + CFG.oR.nextInt(6));
                     }
                     if ((CFG.game.getCiv(nCivID).isFriendlyCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) >= 0 || CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).isFriendlyCiv(nCivID) >= 0) && CFG.game.getCiv(nCivID).getNumOfProvinces() > 3 && CFG.game.getCiv(nCivID).getNumOfProvinces() > CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getNumOfProvinces() && CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getNumOfProvinces() < 5) {
                        boolean alreadyGuaratneed = false;
                        for (int z2 = 1; z2 < CFG.game.getCivsSize(); ++z2) {
                           if (CFG.game.getGuarantee(z2, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 0) {
                              alreadyGuaratneed = true;
                              break;
                           }
                        }
                        if (!alreadyGuaratneed) {
                           DiplomacyManager.sendGuaranteeIndependence_AskProposal(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID, 40);
                        }
                     }
                  }
                  //increase relations by factor of amount relative to budget
                  CFG.game.getCiv(nCivID).setRelation(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) + (nPercOfBudget * 50.0F));
                  DiplomacyManager.updateFriendlyCiv(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID);

                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case GUARANTEE_ASK: {
                  if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).isHatedCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).iBudget > CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget * 0.785f) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case MILITARY_ACCESS_ASK: {
                  if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else {
                     float nScore = -7.785f;
                     if (!CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                        float minDistance = 1.0f;
                        for (int k = 0; k < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++k) {
                           for (int j = 0; j < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getNumOfProvinces(); ++j) {
                              minDistance = Math.min(minDistance, CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(nCivID).getProvinceID(k), CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getProvinceID(j)));
                           }
                        }
                        nScore -= CFG.game.getCiv(nCivID).civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_DISTANCE_SCORE * minDistance;
                        nScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RELATION_SCORE * CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) / 100.0f;
                        nScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RANK_SCORE * (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getRankScore() / CFG.game.getCivsSize());
                        nScore -= CFG.game.getCiv(nCivID).civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_RANK_OWN_SCORE * (CFG.game.getCiv(nCivID).getRankScore() / CFG.game.getCivsSize());
                        if (CFG.game.getMilitaryAccess(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 0) {
                           nScore += 3.75f;
                        }
                        if (CFG.game.getMilitaryAccess(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID) > 0) {
                           nScore += 4.25f;
                        }
                        if (CFG.game.getGuarantee(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID) > 0) {
                           nScore += 12.75f;
                        }
                        if (CFG.game.getCiv(nCivID).getIsPartOfHolyRomanEmpire() || CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getIsPartOfHolyRomanEmpire()) {
                           if (CFG.game.getCiv(nCivID).getIsPartOfHolyRomanEmpire() && CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getIsPartOfHolyRomanEmpire()) {
                              if (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > -15.0f) {
                                 nScore += 12.85f;
                                 if (CFG.holyRomanEmpire_Manager.getHRE().getIsEmperor(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                                    nScore += 250.0f;
                                 }
                              }
                           }
                           else if (!CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getIsPartOfHolyRomanEmpire()) {
                              nScore -= 4.25f;
                           }
                        }
                        if (CFG.game.getCiv(nCivID).getAllianceID() > 0) {
                           for (int l = 0; l < CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize(); ++l) {
                              if (CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(l) != nCivID && CFG.game.getCivRelation_OfCivB(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(l), CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) < 0.0f) {
                                 nScore -= 7.475f * CFG.game.getCivRelation_OfCivB(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(l), CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) / 100.0f;
                              }
                              if (CFG.game.getCivsAtWar(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(l), CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                                 nScore = -1500.0f;
                              }
                           }
                        }
                        if (CFG.game.getCiv(nCivID).isAtWar()) {
                           for (int l = 0; l < CFG.game.getWarsSize(); ++l) {
                              if (CFG.game.getWar(l).getIsDefender(nCivID) && CFG.game.getWar(l).getIsDefender(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                                 nScore += 1000.0f;
                                 break;
                              }
                              if (CFG.game.getWar(l).getIsAggressor(nCivID) && CFG.game.getWar(l).getIsAggressor(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                                 nScore += 1000.0f;
                                 break;
                              }
                           }
                        }
                        try {
                           if (CFG.game.getCiv(nCivID).getDefensivePact(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) > 0) {
                              nScore += CFG.game.getCiv(nCivID).civGameData.civPersonality.RESPONSE_MILITARY_ACCESS_DEFENSIVE_PACT_SCORE;
                           }
                        }
                        catch (final IndexOutOfBoundsException ex6) {}
                        if (nScore > 0.0f) {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                        }
                        else {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        }
                     }
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case MILITARY_ACCESS_ASK_DENIED: {
                  if (CFG.game.getMilitaryAccess(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) <= 0 && !CFG.game.getCiv(nCivID).messageWasSent(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, Message_Type.TRADE_REQUEST)) {
                     final TradeRequest_GameData tradeRequest = new TradeRequest_GameData();
                     tradeRequest.iCivLEFT = nCivID;
                     tradeRequest.iCivRIGHT = CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID;
                     tradeRequest.listRight.militaryAccess = true;
                     tradeRequest.listLEFT.iGold = (int)(Math.min((float)CFG.game.getCiv(nCivID).iBudget, CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget * 1.375f) * 0.425f + CFG.oR.nextInt(325) / 1000.0f);
                     DiplomacyManager.sendTradeRequest(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID, tradeRequest);
                     break;
                  }
                  break;
               }
               case MILITARY_ACCESS_GIVE: {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case WAR_DECLARED_ON_ALLY: {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case TRADE_REQUEST: {
                  //new trade weights system
                  ArrayList<Float> iWeights = DiplomacyManager.calculateTradeRequestWeights(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).tradeRequest);
                  int max = (int) (iWeights.get(0) + 50);
                  int min = (int) (iWeights.get(0) - 50);
                  int rnd = (CFG.oR.nextInt((max - min) + 1) + min);

                  Gdx.app.log("AoC2.5", "RandomFactor Trading " + String.valueOf(rnd));
                  if (rnd <= 0) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  } else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }

                  //else if (CFG.oR.nextInt(100) < 10) {
                  //   CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  //}

                  //gold req
                  //if (tradeRequest.listLEFT.iGold > CFG.game.getCiv(nCivID).iBudget * 8.35f OR IF FRIENDLY  2.35f OR 0.175f) {
                  //   CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  //}

                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case ULTIMATUM: {
                  if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.demandAnexation) {
                     if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis < CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        DiplomacyManager.declarationOfIndependeceByVassal(CFG.game.getCiv(nCivID).getPuppetOfCivID(), nCivID);
                     }
                     else if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis * 0.42f > CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                  }
                  else if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.demandProvinces.size() > 0) {
                     if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis * 0.0465f > CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 50);
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        int totalBudget = CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget;
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID() > 0) {
                           for (int a = 0; a < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilizationsSize(); ++a) {
                              if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilization(a) != CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) {
                                 totalBudget += CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilization(a)).iBudget;
                              }
                           }
                        }
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.iVassalsSize > 0) {
                           for (int a = 0; a < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.iVassalsSize; ++a) {
                              totalBudget += CFG.game.getCiv(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.lVassals.get(a).iCivID).iBudget;
                           }
                        }
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivID() != CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getPuppetOfCivID()) {
                           totalBudget += CFG.game.getCiv(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getPuppetOfCivID()).iBudget;
                        }
                        if (CFG.game.getCiv(nCivID).iBudget * 0.9165f > totalBudget) {
                           CFG.game.declareWar(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, false);
                        }
                     }
                  }
                  else if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.demandMilitaryAccess) {
                     if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis * 0.875f > CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        DiplomacyManager.decreaseRelation(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, 15);
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                  }
                  else if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.demandVasalization) {
                     if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis * 0.22f > CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        int totalBudget = CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget;
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID() > 0) {
                           for (int a = 0; a < CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilizationsSize(); ++a) {
                              if (CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilization(a) != CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID) {
                                 totalBudget += CFG.game.getCiv(CFG.game.getAlliance(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getAllianceID()).getCivilization(a)).iBudget;
                              }
                           }
                        }
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.iVassalsSize > 0) {
                           for (int a = 0; a < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.iVassalsSize; ++a) {
                              totalBudget += CFG.game.getCiv(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.lVassals.get(a).iCivID).iBudget;
                           }
                        }
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivID() != CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getPuppetOfCivID()) {
                           totalBudget += CFG.game.getCiv(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getPuppetOfCivID()).iBudget;
                        }
                        if (CFG.game.getCiv(nCivID).iBudget * 0.865f > totalBudget) {
                           CFG.game.declareWar(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, false);
                        }
                     }
                  }
                  else if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.demandLiberation.size() > 0) {
                     if (CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).ultimatum.numOfUntis * 0.36f > CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).iBudget / 5, 0)) {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case UNION: {
                  if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).iLeague < 2 && CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iLeague < 2) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.isAlly(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID, nCivID)) {
                     if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.numOfUnions == 0 && CFG.game.getCiv(nCivID).civGameData.numOfUnions == 0) {
                        boolean sameRivals = false;
                        for (int a = 0; a < CFG.game.getCiv(nCivID).getHatedCivsSize(); ++a) {
                           for (int b = 0; b < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getHatedCivsSize(); ++b) {
                              if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).isHatedCiv(CFG.game.getCiv(nCivID).getHatedCiv(a).iCivID)) {
                                 sameRivals = true;
                                 break;
                              }
                           }
                           if (sameRivals) {
                              break;
                           }
                        }
                        if (sameRivals) {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                        }
                        else {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        }
                     }
                     else if (CFG.game.getCiv(nCivID).getNumOfProvinces() < 3) {
                        if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).civGameData.numOfUnions < 3 && CFG.game.getCiv(nCivID).civGameData.numOfUnions < 3) {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                        }
                        else {
                           CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                        }
                     }
                     else {
                        CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                     }
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case OFFERVASALIZATION: {
                  if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(nCivID)) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getPuppetOfCivID() == nCivID) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  else if (CFG.game.getCiv(nCivID).iBudget * 15 < CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget && !CFG.game.getCiv(nCivID).isHatedCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID)) { //AI/self bugdet * 15 check if lower, bugfix change//
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case TRANSFER_CONTROL: {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case HIGH_INFLATION: {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case DECLARATION_OF_INDEPENDENCE: {
                  if (CFG.game.getCiv(nCivID).iBudget > CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget * 0.25f) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case WAR: {
                  if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).getIdeologyID()).REVOLUTIONARY) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                     break;
                  }
                  final List<Integer> callToArms = DiplomacyManager.callToArmsListOfCivs(nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID);
                  for (int m = 0; m < callToArms.size(); ++m) {
                     DiplomacyManager.sendCallToArms(callToArms.get(m), nCivID, CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case RELATIONS_INSULT: {
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
               case DECLARATION_OF_INDEPENDENCE_BYVASSAl: {
                  if (CFG.game.getCiv(nCivID).iBudget > CFG.game.getCiv(CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).iFromCivID).iBudget * 0.25f) {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onAccept(nCivID);
                  }
                  else {
                     CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.getMessage(i).onDecline(nCivID);
                  }
                  CFG.game.getCiv(nCivID).getCivilization_Diplomacy_GameData().messageBox.removeMessage(i);
                  break;
               }
            }
         }
      }
      catch (final IndexOutOfBoundsException ex4) {
         CFG.exceptionStack(ex4);
      }
      catch (final NullPointerException ex5) {
         CFG.exceptionStack(ex5);
      }
   }

   protected final void updateSentMessages(final int nCivID) {
      try {
         for (int i = CFG.game.getCiv(nCivID).getSentMessagesSize() - 1; i >= 0; --i) {
            switch (CFG.game.getCiv(nCivID).getSentMessage(i).messageType) {
               case GIFT: {
                  break;
               }
               default: {
                  if (Game_Calendar.TURN_ID - CFG.game.getCiv(nCivID).getSentMessage(i).iSentInTurnID > CFG.game.getCiv(nCivID).getCivPersonality().SENT_MESSAGES_DEFAULT_TURNS) {
                     CFG.game.getCiv(nCivID).removeSentMessage(i);
                     break;
                  }
                  break;
               }
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
      catch (final NullPointerException ex2) {
         CFG.exceptionStack(ex2);
      }
   }

   protected final int getPrepareForWar_TurnsLeft(final int nCivID, final int onCivID) {
      for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++j) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(j).onCivID == onCivID) {
            return CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(j).iNumOfTurnsLeft;
         }
      }
      return -1;
   }

   protected final int getPrepareForWar_TurnsLeft_BasedOnNeighboors(final int nCivID, final int nProvinceID) {
      int out = 8;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() > 0 && CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() != nCivID) {
            for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++j) {
               if (CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(j).onCivID == CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID()) {
                  out = Math.max(CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(j).iNumOfTurnsLeft, out);
               }
            }
         }
      }
      return out;
   }

   protected final void prepareForWar_MoveReadyArmies(final int nCivID) {
      for (int i = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; i >= 0; --i) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).MISSION_TYPE == CivArmyMission_Type.PREAPARE_FOR_WAR) {
            int tempTurnsLeft;
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).MISSION_ID < 0) {
               tempTurnsLeft = 0;
            }
            else {
               tempTurnsLeft = this.getPrepareForWar_TurnsLeft(nCivID, CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).MISSION_ID);
            }
            if (tempTurnsLeft < 0) {
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(i);
            }
            else if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).canMakeAction(nCivID, tempTurnsLeft) && CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).action(nCivID)) {
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(i);
            }
         }
      }
   }

   protected final void prepareForWar2(final int nCivID) {
      Gdx.app.log("AoC", "moveAtWar -> " + CFG.game.getCiv(nCivID).getCivName());
      try {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize == 0) {
            return;
         }
         boolean haveOwnFront = false;
         final List<AI_ProvinceInfo_War> tempFrontProvinces = new ArrayList<AI_ProvinceInfo_War>();
         final List<Integer> tempFrontlinesIDs = new ArrayList<Integer>();
         for (int i = CFG.oAI.lFrontLines.get(nCivID - 1).size() - 1; i >= 0; --i) {
            for (int u = 0; u < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u) {
               if (CFG.oAI.lFrontLines.get(nCivID - 1).get(i).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u).onCivID) {
                  haveOwnFront = true;
                  for (int k = CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.size() - 1; k >= 0; --k) {
                     boolean add = true;
                     for (int o = tempFrontProvinces.size() - 1; o >= 0; --o) {
                        if (tempFrontProvinces.get(o).iProvinceID == CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k)) {
                           add = false;
                           break;
                        }
                     }
                     if (add) {
                        tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(nCivID - 1).get(i).lProvinces.get(k), nCivID), true));
                     }
                  }
               }
            }
         }
         for (int o2 = 0; o2 < CFG.game.getCiv(nCivID).civGameData.iVassalsSize; ++o2) {
            for (int j = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).size() - 1; j >= 0; --j) {
               for (int u2 = 0; u2 < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u2) {
                  if (CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u2).onCivID) {
                     for (int l = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.size() - 1; l >= 0; --l) {
                        boolean add2 = true;
                        for (int z = tempFrontProvinces.size() - 1; z >= 0; --z) {
                           if (tempFrontProvinces.get(z).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l)) {
                              add2 = false;
                              break;
                           }
                        }
                        if (add2) {
                           tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID - 1).get(j).lProvinces.get(l), CFG.game.getCiv(nCivID).civGameData.lVassals.get(o2).iCivID), false));
                        }
                     }
                  }
               }
            }
         }
         if (CFG.game.getCiv(nCivID).getPuppetOfCivID() != nCivID) {
            for (int i = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).size() - 1; i >= 0; --i) {
               for (int u = 0; u < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u) {
                  if (CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u).onCivID) {
                     for (int k = CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.size() - 1; k >= 0; --k) {
                        boolean add = true;
                        for (int o = tempFrontProvinces.size() - 1; o >= 0; --o) {
                           if (tempFrontProvinces.get(o).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k)) {
                              add = false;
                              break;
                           }
                        }
                        if (add) {
                           tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getCiv(nCivID).getPuppetOfCivID() - 1).get(i).lProvinces.get(k), CFG.game.getCiv(nCivID).getPuppetOfCivID()), false));
                        }
                     }
                  }
               }
            }
         }
         if (CFG.game.getCiv(nCivID).getAllianceID() > 0) {
            for (int o2 = 0; o2 < CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilizationsSize(); ++o2) {
               if (CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) != nCivID) {
                  for (int j = CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).size() - 1; j >= 0; --j) {
                     for (int u2 = 0; u2 < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++u2) {
                        if (CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(u2).onCivID) {
                           for (int l = CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.size() - 1; l >= 0; --l) {
                              boolean add2 = true;
                              for (int z = tempFrontProvinces.size() - 1; z >= 0; --z) {
                                 if (tempFrontProvinces.get(z).iProvinceID == CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l)) {
                                    add2 = false;
                                    break;
                                 }
                              }
                              if (add2) {
                                 tempFrontProvinces.add(new AI_ProvinceInfo_War(CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2) - 1).get(j).lProvinces.get(l), CFG.game.getAlliance(CFG.game.getCiv(nCivID).getAllianceID()).getCivilization(o2)), false));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
         Gdx.app.log("AoC", "prepareForWar2 -> tempFrontProvinces.size: " + tempFrontProvinces.size());
         if (tempFrontProvinces.size() > 0) {
            int tMaxDL = 1;
            float tMaxPotential = 1.0f;
            final List<Integer> tMovingArmy_toFrontProvince = new ArrayList<Integer>();
            int tMaxArmy = 1;
            float tMaxRegion_NumOfProvinces = 1.0f;
            float tMaxRegion_Potential = 1.0f;
            final List<Integer> lFrontIDsWithArmies = new ArrayList<Integer>();
            int m = tempFrontProvinces.size() - 1;
            int tempMovingArmy = 0;
            while (m >= 0) {
               if (tempFrontProvinces.get(m).iValue > tMaxPotential) {
                  tMaxPotential = tempFrontProvinces.get(m).iValue;
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy() > tMaxDL) {
                  tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy();
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces() > tMaxRegion_NumOfProvinces) {
                  tMaxRegion_NumOfProvinces = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces();
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion() > tMaxRegion_Potential) {
                  tMaxRegion_Potential = (float)CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion();
               }
               tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(m).iProvinceID);
               tMovingArmy_toFrontProvince.add(tempMovingArmy);
               if (CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tempMovingArmy > tMaxArmy) {
                  tMaxArmy = CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tempMovingArmy;
               }
               --m;
            }
            for (m = tempFrontProvinces.size() - 1; m >= 0; --m) {
               tempFrontProvinces.get(m).iValue = CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_POTENTIAL * (tempFrontProvinces.get(m).iValue / tMaxPotential) + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_DANGER * (CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getDangerLevel_WithArmy() / (float)tMaxDL) + (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_NUM_OF_UNITS + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_NUM_OF_UNITS * (1.0f - (CFG.game.getProvinceArmy(tempFrontProvinces.get(m).iProvinceID) + tMovingArmy_toFrontProvince.get(m)) / (float)tMaxArmy)) + (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_NUM_OF_PROVINCES + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_NUM_OF_PROVINCES * CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getRegion_NumOfProvinces() / tMaxRegion_NumOfProvinces - CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_POTENTIAL + CFG.game.getCiv(nCivID).civGameData.civPersonality.WAR_REGION_POTENTIAL * CFG.game.getProvince(tempFrontProvinces.get(m).iProvinceID).getPotentialRegion() / tMaxRegion_Potential);
            }
            final List<AI_ProvinceInfo_War> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo_War>();
            int tID = 0;
            while (tempFrontProvinces.size() > 0) {
               int tBest = 0;
               for (int i2 = 1, iSize = tempFrontProvinces.size(); i2 < iSize; ++i2) {
                  if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(i2).iValue) {
                     tBest = i2;
                  }
                  else if (tempFrontProvinces.get(tBest).iValue == tempFrontProvinces.get(i2).iValue && CFG.oR.nextInt(100) < 50) {
                     tBest = i2;
                  }
               }
               if (CFG.game.getProvince(tempFrontProvinces.get(tBest).iProvinceID).getArmyCivID(nCivID) > 0) {
                  lFrontIDsWithArmies.add(tID);
               }
               sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
               tempFrontProvinces.remove(tBest);
               ++tID;
            }
            this.prepareForWar_Regroup(nCivID, sortedFrontProvinces, lFrontIDsWithArmies);
            Gdx.app.log("AoC", "prepareForWar2 -> BEFORE RECRUIT MP: " + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
            if (CFG.game.getCiv(nCivID).getMoney() > 5L && CFG.game.getCiv(nCivID).iBudget > 0) {
               final boolean canRecruitAndMove = lFrontIDsWithArmies.size() * 1.75f * CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE <= CFG.game.getCiv(nCivID).getMovePoints() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT;
               if (canRecruitAndMove || CFG.game.getCiv(nCivID).getMoney() / 5L * ((CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn < 0) ? (0.16f + 0.03f * CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn) : (CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn ? 0.6f : 0.75f)) > CFG.game.getCiv(nCivID).getNumOfUnits() || CFG.game.getCiv(nCivID).civGameData.moveAtWar_ProvincesLostAndConquered_LastTurn < -3 || CFG.game.getCiv(nCivID).getNumOfProvinces() < 3) {
                  Gdx.app.log("AoC", "prepareForWar2 -> BEFORE RECRUIT 000: true");
                  this.prepareForWar_Recruit(nCivID, sortedFrontProvinces, lFrontIDsWithArmies, false);
               }
               else {
                  Gdx.app.log("AoC", "prepareForWar2 -> BEFORE RECRUIT 000: false");
               }
               CFG.game.getCiv(nCivID).civGameData.moveAtWar_ArmyFullyRecruitedLastTurn = false;
            }
         }
         this.moveAtWar_AtSea(nCivID);
      }
      catch (final IndexOutOfBoundsException ex) {
         Gdx.app.log("AoC", "prepareForWar2 -> ERRRORR");
         CFG.exceptionStack(ex);
      }
      catch (final ArithmeticException ex2) {
         Gdx.app.log("AoC", "prepareForWar2 -> ERRRORR");
         CFG.exceptionStack(ex2);
      }
      Gdx.app.log("AoC", "prepareForWar2 -> END, movementPoints:" + CFG.game.getCiv(nCivID).getMovePoints() / 10.0f);
   }

   protected final void prepareForWar(final int nCivID, final float fMovemnetPointsToUse) {
      if (CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize > 0) {
         final List<Integer> tempFrontlinesIDs = new ArrayList<Integer>();
         for (int i = 0; i < CFG.oAI.lFrontLines.get(nCivID - 1).size(); ++i) {
            for (int j = 0; j < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++j) {
               if (CFG.oAI.lFrontLines.get(nCivID - 1).get(i).iWithCivID == CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(j).onCivID) {
                  tempFrontlinesIDs.add(i);
                  break;
               }
            }
         }
         if (tempFrontlinesIDs.size() > 0) {
            final List<AI_ProvinceInfo> tempFrontProvinces = new ArrayList<AI_ProvinceInfo>();
            int tMaxDL = 1;
            float tMaxPotential = 1.0f;
            List<Integer> tempWithCivs = new ArrayList<Integer>();
            for (int k = 0; k < CFG.game.getCiv(nCivID).civGameData.civPlans.iWarPreparationsSize; ++k) {
               tempWithCivs.add(CFG.game.getCiv(nCivID).civGameData.civPlans.warPreparations.get(k).onCivID);
            }
            for (int l = 0; l < tempFrontlinesIDs.size(); ++l) {
               for (int m = 0, jSize = CFG.oAI.lFrontLines.get(nCivID - 1).get(tempFrontlinesIDs.get(l)).lProvinces.size(); m < jSize; ++m) {
                  boolean wasAdded = false;
                  for (int k2 = 0; k2 < tempFrontProvinces.size(); ++k2) {
                     if (tempFrontProvinces.get(k2).iProvinceID == CFG.oAI.lFrontLines.get(nCivID - 1).get(tempFrontlinesIDs.get(l)).lProvinces.get(m)) {
                        wasAdded = true;
                        break;
                     }
                  }
                  if (!wasAdded) {
                     tempFrontProvinces.add(new AI_ProvinceInfo(CFG.oAI.lFrontLines.get(nCivID - 1).get(tempFrontlinesIDs.get(l)).lProvinces.get(m), this.getPotential_BasedOnNeighboringProvs(CFG.oAI.lFrontLines.get(nCivID - 1).get(tempFrontlinesIDs.get(l)).lProvinces.get(m), nCivID, tempWithCivs), CFG.gameAction.getRecruitableArmy(CFG.oAI.lFrontLines.get(nCivID - 1).get(tempFrontlinesIDs.get(l)).lProvinces.get(m))));
                  }
               }
            }
            tempWithCivs.clear();
            tempWithCivs = null;
            if (tempFrontProvinces.size() > 0) {
               int tMaxArmy = 1;
               final List<Integer> tMovingArmy = new ArrayList<Integer>();
               int i2 = 0;
               int iSize = tempFrontProvinces.size();
               int tempMovingArmy = 0;
               while (i2 < iSize) {
                  if (tempFrontProvinces.get(i2).iValue > tMaxPotential) {
                     tMaxPotential = tempFrontProvinces.get(i2).iValue;
                  }
                  if (CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getDangerLevel() > tMaxDL) {
                     tMaxDL = CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getDangerLevel();
                  }
                  tempMovingArmy += this.getMovingArmyToProvinceID(nCivID, tempFrontProvinces.get(i2).iProvinceID);
                  tMovingArmy.add(tempMovingArmy);
                  if (CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getArmy(0) + tempMovingArmy > tMaxArmy) {
                     tMaxArmy = CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getArmy(0) + tempMovingArmy;
                  }
                  ++i2;
               }
               for (i2 = 0, iSize = tempFrontProvinces.size(); i2 < iSize; ++i2) {
                  tempFrontProvinces.get(i2).iValue = CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_POTENTIAL * (tempFrontProvinces.get(i2).iValue / tMaxPotential) + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_DANGER * (CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getDangerLevel() / (float)tMaxDL) * (1.0f - CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_NUM_OF_UNITS + CFG.game.getCiv(nCivID).civGameData.civPersonality.VALUABLE_NUM_OF_UNITS * (1.0f - (CFG.game.getProvince(tempFrontProvinces.get(i2).iProvinceID).getArmy(0) + tMovingArmy.get(i2)) / (float)tMaxArmy));
               }
               final List<AI_ProvinceInfo> sortedFrontProvinces = new ArrayList<AI_ProvinceInfo>();
               while (tempFrontProvinces.size() > 0) {
                  int tBest = 0;
                  for (int i3 = 1, iSize2 = tempFrontProvinces.size(); i3 < iSize2; ++i3) {
                     if (tempFrontProvinces.get(tBest).iValue < tempFrontProvinces.get(i3).iValue) {
                        tBest = i3;
                     }
                  }
                  sortedFrontProvinces.add(tempFrontProvinces.get(tBest));
                  tempFrontProvinces.remove(tBest);
               }
               final List<Integer> lArmiesToRegoup = new ArrayList<Integer>();
               for (int i3 = 0; i3 < CFG.game.getCiv(nCivID).iArmiesPositionSize; ++i3) {
                  if (!CFG.game.getCiv(nCivID).civGameData.civPlans.haveMission(CFG.game.getCiv(nCivID).lArmiesPosition.get(i3))) {
                     lArmiesToRegoup.add(CFG.game.getCiv(nCivID).lArmiesPosition.get(i3));
                  }
               }
               final List<AI_NeighProvinces> ab = CFG.oAI.getAllNeighboringProvincesInRange_Recruit(sortedFrontProvinces.get(0).iProvinceID, nCivID, 3, true, false, new ArrayList<AI_NeighProvinces>(), new ArrayList<Integer>());
               if (ab.size() > 0) {
                  final int tempRand = CFG.oR.nextInt(ab.size());
                  CFG.game.getCiv(nCivID).recruitArmy_AI(ab.get(tempRand).iProvinceID, CFG.gameAction.getRecruitableArmy(ab.get(tempRand).iProvinceID));
                  final int tempArmy = CFG.game.getCiv(nCivID).getRecruitArmy_BasedOnProvinceID(ab.get(tempRand).iProvinceID);
                  if (tempArmy > 0) {
                     CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.add(new CivArmyMission_RegroupAfterRecruitment(nCivID, ab.get(tempRand).iProvinceID, sortedFrontProvinces.get(0).iProvinceID, tempArmy));
                  }
               }
            }
         }
      }
   }

   protected final int getMovingArmyToProvinceID(final int nCivID, final int nProvinceID) {
      int out = 0;
      for (int i = 0; i < CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size(); ++i) {
         if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).toProvinceID == nProvinceID) {
            out += CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(i).iArmy;
         }
      }
      for (int i = 0; i < CFG.game.getCiv(nCivID).getRegroupArmySize(); ++i) {
         if (CFG.game.getCiv(nCivID).getRegroupArmy(i).getToProvinceID() == nProvinceID) {
            out += CFG.game.getCiv(nCivID).getRegroupArmy(i).getNumOfUnits();
         }
      }
      for (int i = 0; i < CFG.game.getCiv(nCivID).getMoveUnitsSize(); ++i) {
         if (CFG.game.getCiv(nCivID).getMoveUnits(i).getToProvinceID() == nProvinceID) {
            out += CFG.game.getCiv(nCivID).getMoveUnits(i).getNumOfUnits();
         }
      }
      return out;
   }

   protected final int getPotential_BasedOnNeighboringProvs(final int nProvinceID, final int nCivID) {
      int out = CFG.game.getProvince(nProvinceID).getPotential();
      int tSize = 1;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() != nCivID) {
            out += CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getPotentialModified(nCivID);
            ++tSize;
         }
      }
      return out / tSize;
   }

   protected final int getPotential_BasedOnNeighboringProvs(final int nProvinceID, final int nCivID, final int withCivID) {
      int out = CFG.game.getProvince(nProvinceID).getPotential();
      int tSize = 1;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == withCivID) {
            out += CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getPotentialModified(nCivID);
            ++tSize;
         }
      }
      return out / tSize;
   }

   protected final int getPotential_BasedOnNeighboringProvs(final int nProvinceID, final int nCivID, final List<Integer> withCivID) {
      int out = CFG.game.getProvince(nProvinceID).getPotential();
      int tSize = 1;
      final int jSize = withCivID.size();
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         for (int j = 0; j < jSize; ++j) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() == withCivID.get(j)) {
               out += CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getPotentialModified(nCivID);
               ++tSize;
            }
         }
      }
      return out / tSize;
   }

   protected boolean canMove(final int nCivID) {
      return CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE;
   }

   protected boolean canMoveAndRecruit(final int nCivID) {
      return CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_MOVE + CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT;
   }

   protected boolean canMoveArmyToProvinceID(final int nProvinceID, final int nCivID) {
      return CFG.game.getProvince(nProvinceID).getCivID() == nCivID || CFG.game.getCivsAreAllied(nCivID, CFG.game.getProvince(nProvinceID).getCivID()) || CFG.game.getCiv(CFG.game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID() == nCivID || CFG.game.getCiv(nCivID).getPuppetOfCivID() == CFG.game.getProvince(nProvinceID).getCivID() || CFG.game.getMilitaryAccess(nCivID, CFG.game.getProvince(nProvinceID).getCivID()) > 0;
   }

   protected boolean alliesAtWar(final int nCivID) {
      for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
         if (i != nCivID && CFG.game.isAlly(nCivID, i) && CFG.game.getCiv(i).isAtWar()) {
            return true;
         }
      }
      return false;
   }

   protected boolean canRecruit(final int nCivID, final int nProvinceID) {
      return CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_RECRUIT && CFG.game.getCiv(nCivID).getMoney() >= CFG.getCostOfRecruitArmyMoney(nProvinceID);
   }

   protected final int getRecruitableArmy(final int nProvinceID, final int nCivID) {
      return Math.min(CFG.gameAction.getRecruitableArmy(nProvinceID, nCivID), (int)(CFG.game.getCiv(nCivID).getMoney() / CFG.getCostOfRecruitArmyMoney(nProvinceID)));
   }

   protected final boolean doHaveAVisionInProvince(final int nProvinceID, final int nCivID) {
      if (CFG.FOG_OF_WAR == 0) {
         return true;
      }
      if (CFG.game.getProvince(nProvinceID).getLevelOfFort() == 0) {
         for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
            if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getLevelOfWatchTower() > 0 && CFG.game.isAlly(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(), nCivID)) {
               return true;
            }
         }
      }
      return false;
   }

   protected final int getEnemyArmyInNeighbooringProvinces_ArmyOnlyAtWar(final int nProvinceID, final int nCivID) {
      int nOut = 0;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() > 0) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivsSize(); ++j) {
               if (CFG.game.getCivsAtWar(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(j), nCivID)) {
                  nOut += CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getArmyCivID(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(j));
               }
            }
         }
      }
      return nOut;
   }

   protected final int getEnemyArmyInNeighbooringProvinces_Total(final int nProvinceID, final int nCivID) {
      int nOut = 0;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() > 0) {
            for (int j = 0; j < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivsSize(); ++j) {
               if (CFG.game.getCivsAtWar(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID(j), nCivID)) {
                  nOut += CFG.game.getProvinceArmy(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i));
                  break;
               }
            }
         }
      }
      return nOut;
   }

   protected final int getEnemyArmyInNeighbooringSeaProvinces_Total(final int nProvinceID, final int nCivID) {
      int nOut = 0;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringSeaProvincesSize(); ++i) {
         for (int j = 1; j < CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getCivsSize(); ++j) {
            if (CFG.game.getCivsAtWar(CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getCivID(j), nCivID)) {
               nOut += CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getArmy(j);
            }
         }
      }
      return nOut;
   }

   protected final boolean isUncivilzed(final int nCivID) {
      return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0;
   }

   protected final boolean canCivlize(final int nCivID) {
      return CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).CIVILIZE_TECH_LEVEL <= CFG.game.getCiv(nCivID).getTechnologyLevel();
   }

   protected final boolean civilize(final int nCivID) {
      if (this.isUncivilzed(nCivID) && this.canCivlize(nCivID)) {
         if (Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES && this.tryToExpandBeforeCivilize(nCivID) && CFG.oR.nextInt(100) > 2) {
            return false;
         }
         if (DiplomacyManager.civilizeCiv(nCivID)) {
            return true;
         }
      }
      return false;
   }

   protected final boolean tryToExpandBeforeCivilize(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getBordersWithEnemy() > 0) {
         return false;
      }
      if (CFG.game.getCiv(nCivID).getMoney() + CFG.game.getCiv(nCivID).iBudget > -1000L && CFG.game.getCiv(nCivID).getNumOfNeighboringNeutralProvinces() > 0) {
         for (int k = CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.size() - 1; k >= 0; --k) {
            if (CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).MISSION_TYPE == CivArmyMission_Type.EXPAND_NETURAL_PROVINCE) {
               if (!CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).action(nCivID)) {
                  return true;
               }
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.get(k).onRemove();
               CFG.game.getCiv(nCivID).civGameData.civPlans.lArmiesMissions.remove(k);
            }
         }
         if (CFG.game.getCiv(nCivID).getNumOfProvinces() < 6 + nCivID % 2) {
            int minArmy = -1;
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
               for (int j = 0; j < CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringProvincesSize(); ++j) {
                  if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringProvinces(j)).getCivID() == 0) {
                     if (minArmy < 0) {
                        minArmy = CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringProvinces(j)).getArmy(0);
                     }
                     else {
                        minArmy = Math.min(minArmy, CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getNeighboringProvinces(j)).getArmy(0));
                     }
                  }
               }
            }
            if (minArmy < 0) {
               return false;
            }
            minArmy -= (int)(CFG.game.getCiv(nCivID).getNumOfUnits() + Math.max(CFG.game.getCiv(nCivID).getMoney(), 0L) / 5L);
            if (minArmy <= 0) {
               CFG.oAI.expandToNeutralProvinces_Out(nCivID, false);
               return true;
            }
            final int willTakeNumOfTurns = (int)Math.ceil(minArmy / (float)(CFG.game.getCiv(nCivID).iBudget / 5));
            if (willTakeNumOfTurns < 50) {
               CFG.oAI.expandToNeutralProvinces_Out(nCivID, false);
               return true;
            }
         }
      }
      return false;
   }

   protected final void checkBalanceOfProvinces_Tribal(final int nCivID) {
      try {
         final List<Integer> lProvincesWithDeficit = new ArrayList<Integer>();
         final List<Integer> lProvincesWithDeficit_ALL = new ArrayList<Integer>();
         int totalBalanceOnMinus = 0;
         int totalBalancePositive = 0;
         for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); ++i) {
            if (!CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).isOccupied()) {
               if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getBalance_LastTurn() < 0) {
                  if (CFG.game.getCiv(nCivID).getProvinceID(i) != CFG.game.getCiv(nCivID).getCapitalProvinceID()) {
                     totalBalanceOnMinus += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getBalance_LastTurn();
                     lProvincesWithDeficit_ALL.add(CFG.game.getCiv(nCivID).getProvinceID(i));
                     if (CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).saveProvinceData.iNumOfTurnsWithBalanceOnMinus >= this.MIN_TURNS_TO_ABANDON_USELESS_PROVINCE) {
                        lProvincesWithDeficit.add(CFG.game.getCiv(nCivID).getProvinceID(i));
                     }
                  }
                  else {
                     totalBalanceOnMinus += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getBalance_LastTurn();
                  }
               }
               else {
                  totalBalancePositive += CFG.game.getProvince(CFG.game.getCiv(nCivID).getProvinceID(i)).getBalance_LastTurn();
               }
            }
         }
         if (lProvincesWithDeficit_ALL.size() > 0 && totalBalancePositive * 0.65f < Math.abs(totalBalanceOnMinus)) {
            float fAverage = 0.0f;
            for (int j = lProvincesWithDeficit_ALL.size() - 1; j >= 0; --j) {
               fAverage += CFG.game.getProvince(lProvincesWithDeficit_ALL.get(j)).getBalance_LastTurn();
            }
            fAverage /= lProvincesWithDeficit_ALL.size();
            final List<Integer> lProvincesToDoSomething = new ArrayList<Integer>();
            for (int k = lProvincesWithDeficit_ALL.size() - 1; k >= 0; --k) {
               if (fAverage * 0.375f > CFG.game.getProvince(lProvincesWithDeficit_ALL.get(k)).getBalance_LastTurn()) {
                  lProvincesToDoSomething.add(lProvincesWithDeficit_ALL.get(k));
               }
            }
            this.abandonOrReleaseAsVassalProvinces(nCivID, lProvincesToDoSomething, true);
         }
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
   }

   protected final boolean abandonOrReleaseAsVassalProvinces(final int nCivID, final List<Integer> tProvinces, final boolean canAbandon) {
      Gdx.app.log("AoC", "abandonOrReleaseAsVassalProvinces -> " + CFG.game.getCiv(nCivID).getCivName());
      final List<AI_ReleaseVassal> lCivsToRelease = new ArrayList<AI_ReleaseVassal>();
      for (int i = tProvinces.size() - 1; i >= 0; --i) {
         for (int j = 0; j < CFG.game.getProvince(tProvinces.get(i)).getCore().getCivsSize(); ++j) {
            if (CFG.game.getCiv(CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j)).getNumOfProvinces() == 0) {
               boolean addCiv = true;
               for (int k = lCivsToRelease.size() - 1; k >= 0; --k) {
                  if (lCivsToRelease.get(k).iCivID == CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j)) {
                     addCiv = false;
                     lCivsToRelease.get(k).addProvince(tProvinces.get(i));
                     break;
                  }
               }
               if (addCiv) {
                  lCivsToRelease.add(new AI_ReleaseVassal(CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j), tProvinces.get(i)));
               }
            }
         }
      }
      if (lCivsToRelease.size() > 0) {
         final int nNewVassalID = this.abandonOrReleaseAsVassalProvinces_ReleaseVassal(lCivsToRelease, tProvinces, nCivID);
         if (nNewVassalID >= 0) {
            for (int l = tProvinces.size() - 1; l >= 0; --l) {
               if (CFG.game.getCiv(nNewVassalID).controlsProvince(tProvinces.get(l))) {
                  tProvinces.remove(l);
               }
            }
            return this.abandonOrReleaseAsVassalProvinces(nCivID, tProvinces, canAbandon);
         }
      }
      lCivsToRelease.clear();
      for (int i = tProvinces.size() - 1; i >= 0; --i) {
         for (int j = 0; j < CFG.game.getProvince(tProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
            if (CFG.game.getProvince(CFG.game.getProvince(tProvinces.get(i)).getNeighboringProvinces(j)).getCivID() > 0 && CFG.game.getProvince(CFG.game.getProvince(tProvinces.get(i)).getNeighboringProvinces(j)).getCivID() != nCivID) {
               boolean addCiv = true;
               for (int k = lCivsToRelease.size() - 1; k >= 0; --k) {
                  if (lCivsToRelease.get(k).iCivID == CFG.game.getProvince(CFG.game.getProvince(tProvinces.get(i)).getNeighboringProvinces(j)).getCivID()) {
                     addCiv = false;
                     lCivsToRelease.get(k).addProvince(tProvinces.get(i));
                     break;
                  }
               }
               if (addCiv) {
                  lCivsToRelease.add(new AI_ReleaseVassal(CFG.game.getProvince(CFG.game.getProvince(tProvinces.get(i)).getNeighboringProvinces(j)).getCivID(), tProvinces.get(i)));
               }
            }
         }
         for (int j = 0; j < CFG.game.getProvince(tProvinces.get(i)).getCore().getCivsSize(); ++j) {
            if (CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j) != nCivID) {
               boolean addCiv = true;
               int k = lCivsToRelease.size() - 1;
               while (k >= 0) {
                  if (lCivsToRelease.get(k).iCivID == CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j)) {
                     addCiv = false;
                     if (!lCivsToRelease.get(k).haveProvince(tProvinces.get(i))) {
                        lCivsToRelease.get(k).addProvince(tProvinces.get(i));
                        break;
                     }
                     break;
                  }
                  else {
                     --k;
                  }
               }
               if (addCiv) {
                  lCivsToRelease.add(new AI_ReleaseVassal(CFG.game.getProvince(tProvinces.get(i)).getCore().getCivID(j), tProvinces.get(i)));
               }
            }
         }
      }
      final List<AI_ReleaseVassal> lAllies = new ArrayList<AI_ReleaseVassal>();
      for (int l = lCivsToRelease.size() - 1; l >= 0; --l) {
         if (CFG.game.isAlly(nCivID, lCivsToRelease.get(l).iCivID)) {
            lAllies.add(lCivsToRelease.get(l));
         }
      }
      for (int j = lAllies.size() - 1; j >= 0; --j) {
         for (int m = CFG.game.getCiv(nCivID).getSentMessagesSize() - 1; m >= 0; --m) {
            if (CFG.game.getCiv(nCivID).getSentMessage(m).messageType == Message_Type.TRADE_REQUEST_GIVE_PROVINCES && CFG.game.getCiv(nCivID).getSentMessage(m).iToCivID == lAllies.get(j).iCivID) {
               lAllies.remove(j);
            }
         }
      }
      while (lAllies.size() > 0) {
         int tBest = 0;
         for (int m = lAllies.size() - 1; m > 0; --m) {
            if (lAllies.get(tBest).lProvinces.size() < lAllies.get(m).lProvinces.size() || CFG.oR.nextInt(100) < 10) {
               tBest = m;
            }
         }
         final TradeRequest_GameData nTD = new TradeRequest_GameData();
         for (int i2 = lAllies.get(tBest).lProvinces.size() - 1; i2 >= 0; --i2) {
            nTD.listLEFT.lProvinces.add(lAllies.get(tBest).lProvinces.get(i2));
         }
         final boolean messageSent = DiplomacyManager.sendTradeRequest(lAllies.get(tBest).iCivID, nCivID, nTD);
         if (!messageSent) {
            break;
         }
         CFG.game.getCiv(nCivID).civGameData.lSentMessages.add(new Civilization_SentMessages(lAllies.get(tBest).iCivID, Message_Type.TRADE_REQUEST_GIVE_PROVINCES));
         CFG.game.getCiv(lAllies.get(tBest).iCivID).civGameData.lSentMessages.add(new Civilization_SentMessages(nCivID, Message_Type.TRADE_REQUEST_GIVE_PROVINCES));
         for (int j2 = nTD.listLEFT.lProvinces.size() - 1; j2 >= 0; --j2) {
            Gdx.app.log("AoC", "abandonOrReleaseAsVassalProvinces -> nTD.listLEFT.lProvinces: " + CFG.game.getProvince(nTD.listLEFT.lProvinces.get(j2)).getName());
            for (int i3 = tProvinces.size() - 1; i3 >= 0; --i3) {
               if (tProvinces.get(i3).equals(nTD.listLEFT.lProvinces.get(j2))) {
                  tProvinces.remove(i3);
                  break;
               }
            }
         }
         for (int i4 = lAllies.size() - 1; i4 >= 0; --i4) {
            if (i4 != tBest) {
               for (int j3 = lAllies.get(tBest).lProvinces.size() - 1; j3 >= 0; --j3) {
                  lAllies.get(i4).removeProvinceID(lAllies.get(tBest).lProvinces.get(j3));
               }
            }
         }
         lAllies.remove(tBest);
         for (int i4 = lAllies.size() - 1; i4 >= 0; --i4) {
            if (lAllies.get(i4).lProvinces.size() == 0) {
               lAllies.remove(i4);
            }
         }
      }
      for (int l = tProvinces.size() - 1; l >= 0; --l) {
         CFG.gameAction.abadonProvince(tProvinces.get(l), nCivID);
      }
      return true;
   }

   protected final int abandonOrReleaseAsVassalProvinces_ReleaseVassal(final List<AI_ReleaseVassal> lCivsToRelease, final List<Integer> tProvinces, final int nCivID) {
      int tBest = 0;
      for (int i = lCivsToRelease.size() - 1; i > 0; --i) {
         if (lCivsToRelease.get(tBest).lProvinces.size() < lCivsToRelease.get(i).lProvinces.size()) {
            tBest = i;
         }
         else if (lCivsToRelease.get(tBest).lProvinces.size() == lCivsToRelease.get(i).lProvinces.size() && CFG.oR.nextInt(100) < 50) {
            tBest = i;
         }
      }
      for (int i = tProvinces.size() - 1; i >= 0; --i) {
         CFG.game.getProvince(tProvinces.get(i)).was = true;
      }
      for (int i = lCivsToRelease.get(tBest).lProvinces.size() - 1; i >= 0; --i) {
         CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).was = false;
      }
      for (int i = 0; i < lCivsToRelease.get(tBest).lProvinces.size(); ++i) {
         for (int j = 0; j < CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvincesSize(); ++j) {
            if (CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j)).was) {
               boolean canBeAdded = true;
               for (int o = lCivsToRelease.size() - 1; o >= 0; --o) {
                  if (CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j)).getCore().getHaveACore(lCivsToRelease.get(o).iCivID)) {
                     canBeAdded = false;
                     break;
                  }
               }
               for (int m = 0; m < CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j)).getNeighboringProvincesSize(); ++m) {
                  for (int u = lCivsToRelease.size() - 1; u >= 0; --u) {
                     if (u != tBest && lCivsToRelease.get(u).haveProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j)).getNeighboringProvinces(m))) {
                        canBeAdded = false;
                        break;
                     }
                  }
               }
               if (canBeAdded) {
                  lCivsToRelease.get(tBest).addProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j));
                  CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringProvinces(j)).was = false;
               }
            }
         }
         for (int j = 0; j < CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvincesSize(); ++j) {
            for (int k = 0; k < CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvincesSize(); ++k) {
               if (!CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).was) {
                  boolean canBeAdded2 = true;
                  for (int o2 = lCivsToRelease.size() - 1; o2 >= 0; --o2) {
                     if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getCore().getHaveACore(lCivsToRelease.get(o2).iCivID)) {
                        canBeAdded2 = false;
                        break;
                     }
                  }
                  for (int l = 0; l < CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvincesSize(); ++l) {
                     for (int u2 = lCivsToRelease.size() - 1; u2 >= 0; --u2) {
                        if (u2 != tBest && lCivsToRelease.get(u2).haveProvince(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).getNeighboringProvinces(l))) {
                           canBeAdded2 = false;
                           break;
                        }
                     }
                  }
                  if (canBeAdded2) {
                     lCivsToRelease.get(tBest).addProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k));
                     CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(lCivsToRelease.get(tBest).lProvinces.get(i)).getNeighboringSeaProvinces(j)).getNeighboringProvinces(k)).was = false;
                  }
               }
            }
         }
      }
      this.clearWas(tProvinces);
      return CFG.game.releaseAVasssal(CFG.game.getCiv(lCivsToRelease.get(tBest).iCivID).getCivTag(), lCivsToRelease.get(tBest).lProvinces, -1, nCivID, true);
   }

   protected final void clearWas(final List<Integer> was) {
      for (int i = was.size() - 1; i >= 0; --i) {
         CFG.game.getProvince(was.get(i)).was = false;
      }
   }

   protected final void clearWas() {
      for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         CFG.game.getProvince(i).was = false;
      }
   }

   protected final float armyOverBudget_Disband_AtWar(final int nCivID) {
      return 0.9f - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getMin_Goods(nCivID) - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).MIN_INVESTMENTS;
   }

   protected void armyOverBudget_Disband(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_DISBAND) {
         boolean atWar = false;
         if ((CFG.game.getCiv(nCivID).isAtWar() || CFG.game.getCiv(nCivID).civGameData.civPlans.isPreparingForTheWar()) && CFG.game.getCiv(nCivID).iBudget > 0 && CFG.game.getCiv(nCivID).getMoney() + CFG.game.getCiv(nCivID).iBudget * 3 > 0L) {
            Gdx.app.log("AoC", "armyOverBudget_Disband -> 000, DONT DISBAND ARMY, WE NEEED IT!!!1");
            atWar = true;
            return;
         }
         Gdx.app.log("AoC", "armyOverBudget_Disband -> 000");
         final List<AI_ArmyUpkeep> armyUpkeep = new ArrayList<AI_ArmyUpkeep>();
         final int spendingsOnArmy = (int)(CFG.game.getCiv(nCivID).iBudget * (atWar ? this.armyOverBudget_Disband_AtWar(nCivID) : this.getMinMilitarySpendings(nCivID)));
         int budgetForArmyisOver = (int)Math.abs(CFG.game.getCiv(nCivID).iBudget * (atWar ? this.armyOverBudget_Disband_AtWar(nCivID) : this.getMinMilitarySpendings(nCivID)) - CFG.game.getCiv(nCivID).iBudget * CFG.game.getCiv(nCivID).iMilitaryUpkeep_PERC);
         Gdx.app.log("AoC", "armyOverBudget_Disband -> CFG.game.getCiv(nCivID).iMilitaryUpkeep_Total: " + CFG.game.getCiv(nCivID).iMilitaryUpkeep_Total);
         Gdx.app.log("AoC", "armyOverBudget_Disband -> spendingsOnArmy: " + spendingsOnArmy);
         Gdx.app.log("AoC", "armyOverBudget_Disband -> budgetForArmyisOver: " + budgetForArmyisOver);
         if (CFG.game.getCiv(nCivID).iMilitaryUpkeep_Total > spendingsOnArmy) {
            Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> AA00");
            Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> CFG.game.getCiv(nCivID).iArmiesPositionSize: " + CFG.game.getCiv(nCivID).iArmiesPositionSize);
            Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> CFG.game.getCiv(nCivID).lArmiesPosition.size(): " + CFG.game.getCiv(nCivID).lArmiesPosition.size());
            for (int i = 0; i < CFG.game.getCiv(nCivID).iArmiesPositionSize; ++i) {
               armyUpkeep.add(new AI_ArmyUpkeep(nCivID, CFG.game.getCiv(nCivID).lArmiesPosition.get(i)));
            }
            Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> armyUpkeep.SIZE: " + armyUpkeep.size());
            final List<AI_ArmyUpkeep> armiesOver = new ArrayList<AI_ArmyUpkeep>();
            for (int j = armyUpkeep.size() - 1; j >= 0; --j) {
               Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> armyUpkeep.get(i).iCost: " + armyUpkeep.get(j).iCost + ", budgetForArmyisOver: " + budgetForArmyisOver);
               if (armyUpkeep.get(j).iCost >= budgetForArmyisOver) {
                  armiesOver.add(armyUpkeep.get(j));
               }
            }
            Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> armiesOver.SIZE: " + armiesOver.size());
            if (armiesOver.size() > 0) {
               Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> AA11");
               int tBestID = 0;
               for (int k = tBestID + 1; k < armiesOver.size(); ++k) {
                  if (CFG.game.getProvince(armiesOver.get(tBestID).iProvinceID).getDangerLevel() > CFG.game.getProvince(armiesOver.get(k).iProvinceID).getDangerLevel()) {
                     tBestID = k;
                  }
               }
               final float costPerUnit = CFG.game_NextTurnUpdate.getMilitaryUpkeep(armiesOver.get(tBestID).iProvinceID, 1000, nCivID) / 1000.0f * 1.05f;
               final int maxDisbandArmy = CFG.game.getProvince(armiesOver.get(tBestID).iProvinceID).getArmyCivID(nCivID);
               Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> maxDisbandArmy: " + maxDisbandArmy);
               if (maxDisbandArmy > 0) {
                  Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> 33333 -> " + (int)Math.min(Math.ceil(budgetForArmyisOver / costPerUnit), maxDisbandArmy));
                  CFG.gameAction.disbandArmy(armiesOver.get(tBestID).iProvinceID, (int)Math.min(Math.ceil(budgetForArmyisOver / costPerUnit), maxDisbandArmy), nCivID);
               }
               else {
                  Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> 44444");
               }
            }
            else {
               Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND OVER BUDGET -> BB0");
               armiesOver.clear();
               for (int j = armyUpkeep.size() - 1; j >= 0; --j) {
                  if (CFG.game.getProvince(armyUpkeep.get(j).iProvinceID).getDangerLevel() == 0) {
                     armiesOver.add(armyUpkeep.get(j));
                  }
               }
               if (armiesOver.size() > 0) {
                  int tTotalCost = 0;
                  for (int k = armiesOver.size() - 1; k >= 0; --k) {
                     tTotalCost += armiesOver.get(k).iCost;
                  }
                  if (tTotalCost >= budgetForArmyisOver) {
                     while (CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_DISBAND && armiesOver.size() > 0) {
                        if (budgetForArmyisOver <= 0) {
                           return;
                        }
                        int tBest = 0;
                        for (int l = armiesOver.size() - 1; l > 0; --l) {
                           if (armiesOver.get(tBest).iCost < armiesOver.get(l).iCost) {
                              tBest = l;
                           }
                        }
                        final float costPerUnit2 = CFG.game_NextTurnUpdate.getMilitaryUpkeep(armiesOver.get(tBest).iProvinceID, 1000, nCivID) / 1000.0f * 1.05f;
                        Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND -> 44444");
                        CFG.gameAction.disbandArmy(armiesOver.get(tBest).iProvinceID, (int)Math.ceil(budgetForArmyisOver / costPerUnit2), nCivID);
                        budgetForArmyisOver -= armiesOver.get(tBest).iCost;
                        armiesOver.remove(tBest);
                     }
                  }
               }
               while (CFG.game.getCiv(nCivID).getMovePoints() >= CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).COST_OF_DISBAND && armyUpkeep.size() > 0) {
                  if (budgetForArmyisOver <= 0) {
                     return;
                  }
                  int tBest2 = 0;
                  for (int k = armyUpkeep.size() - 1; k > 0; --k) {
                     if (armyUpkeep.get(tBest2).iCost < armyUpkeep.get(k).iCost) {
                        tBest2 = k;
                     }
                  }
                  final float costPerUnit = CFG.game_NextTurnUpdate.getMilitaryUpkeep(armyUpkeep.get(tBest2).iProvinceID, 1000, nCivID) / 1000.0f * 1.05f;
                  Gdx.app.log("AoC", "armyOverBudget_Disband -> DISBAND -> 55555");
                  CFG.gameAction.disbandArmy(armyUpkeep.get(tBest2).iProvinceID, (int)Math.ceil(budgetForArmyisOver / costPerUnit), nCivID);
                  budgetForArmyisOver -= armyUpkeep.get(tBest2).iCost;
                  armyUpkeep.remove(tBest2);
               }
            }
         }
      }
      else {
         Gdx.app.log("AoC", "armyOverBudget_Disband -> NO MOVEMENT POITNS");
      }
   }

   protected final void useTechnologyPoints(final int nCivID) {
      if (CFG.game.getCiv(nCivID).civGameData.skills.getPointsLeft(nCivID) > 0) {
         final List<AI_Skills> nSkills = new ArrayList<AI_Skills>();
         nSkills.add(new AI_Skills(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_POP_GROWTH, 25));
         nSkills.add(new AI_Skills_Eco(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ECONOMY_GROWTH, 25));
         nSkills.add(new AI_Skills_Taxation(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_TAXATION, 25));
         nSkills.add(new AI_Skills_Production(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_INCOME_PRODUCTION, 25));
         nSkills.add(new AI_Skills_Administration(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_ADMINISTRATION, 20));
         nSkills.add(new AI_Skills_Military(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_MILITARY_UPKEEP, 30));
         nSkills.add(new AI_Skills_Research(CFG.game.getCiv(nCivID).civGameData.skills.POINTS_RESEARCH, 30));
         int pointsToUse = CFG.game.getCiv(nCivID).civGameData.skills.getPointsLeft(nCivID);
         final int nSkillsSize = nSkills.size();
         if (CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.size() > 0 && CFG.game.getCiv(nCivID).civGameData.skills.POINTS_COLONIZATION < 15 && CFG.game.getCiv(nCivID).civGameData.skills.POINTS_COLONIZATION < CFG.game.getCiv(nCivID).civGameData.lColonies_Founded.size()) {
            SkillsManager.add_Colonization(nCivID);
            --pointsToUse;
         }
         while (pointsToUse-- > 0) {
            int tBestID = 0;
            for (int i = tBestID + 1; i < nSkillsSize; ++i) {
               if (nSkills.get(tBestID).getScore(nCivID) < nSkills.get(i).getScore(nCivID)) {
                  tBestID = i;
               }
            }
            nSkills.get(tBestID).addPoint_CivID(nCivID);
         }
      }
   }

   protected final void updateLiberityDesire(final int nCivID) {
      if (CFG.game.getCiv(nCivID).getPuppetOfCivID() != nCivID) {
         boolean updateLiberity = true;
         try {
            if (CFG.game.getCiv(nCivID).getNumOfProvinces() > CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getNumOfProvinces() * 0.85f) {
               CFG.game.getCiv(nCivID).setVassalLiberityDesire(CFG.game.getCiv(nCivID).getVassalLiberityDesire() + (0.425f + CFG.oR.nextInt(825) / 1000.0f) * (CFG.game.getCiv(nCivID).getNumOfProvinces() / CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getNumOfProvinces() * 0.85f));
               updateLiberity = false;
            }
         }
         catch (final ArithmeticException ex) {}
         try {
            if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_Tribute(nCivID) > 20.0f * CFG.game.getCiv(nCivID).civGameData.civPersonality.LIBERITY_ACCEPTABLE_TRIBUTE) {
               CFG.game.getCiv(nCivID).setVassalLiberityDesire(CFG.game.getCiv(nCivID).getVassalLiberityDesire() + CFG.game.getCiv(nCivID).getVassalLiberityDesire() * 0.01f + (0.325f + CFG.oR.nextInt(100) / 100.0f) * (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_Tribute(nCivID) / 20));
               updateLiberity = false;
            }
            else if (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_Tribute(nCivID) < 20.0f * CFG.game.getCiv(nCivID).civGameData.civPersonality.LIBERITY_ACCEPTABLE_TRIBUTE * 0.5f) {
               CFG.game.getCiv(nCivID).setVassalLiberityDesire(CFG.game.getCiv(nCivID).getVassalLiberityDesire() - (0.075f + CFG.oR.nextInt(125) / 100.0f) * (CFG.game.getCiv(CFG.game.getCiv(nCivID).getPuppetOfCivID()).getVassal_Tribute(nCivID) / 20));
            }
         }
         catch (final ArithmeticException ex2) {}
         try {
            if (CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getPuppetOfCivID()) < -10.0f) {
               CFG.game.getCiv(nCivID).setVassalLiberityDesire(CFG.game.getCiv(nCivID).getVassalLiberityDesire() + 3.425f * Math.abs(CFG.game.getCivRelation_OfCivB(nCivID, CFG.game.getCiv(nCivID).getPuppetOfCivID()) / 100.0f));
               updateLiberity = false;
            }
         }
         catch (final ArithmeticException ex3) {}
         if (updateLiberity) {
            CFG.game.getCiv(nCivID).setVassalLiberityDesire(CFG.game.getCiv(nCivID).getVassalLiberityDesire() - CFG.game.getCiv(nCivID).getVassalLiberityDesire() * 0.01f);
         }
         if (CFG.game.getCiv(nCivID).getVassalLiberityDesire() > CFG.game.getCiv(nCivID).civGameData.civPersonality.LIBERITY_DECLARATION) {
            DiplomacyManager.declarationOfIndependeceByVassal(CFG.game.getCiv(nCivID).getPuppetOfCivID(), nCivID);
         }
      }
   }
}
