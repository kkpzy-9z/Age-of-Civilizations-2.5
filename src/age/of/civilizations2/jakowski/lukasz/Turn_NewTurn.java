package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

class Turn_NewTurn extends Thread {
   private static long tempTime;
   private static long tempTimeTotal;
   protected static float ageRiskModifier = 1.0F;
   protected static float ageDevMod = 1.0F;
   protected static List<PopulationGrowth> tempCivs = new ArrayList<>();
   protected static List<Float> happinessChange_ByTaxation = new ArrayList<>();
   protected static List<Float> happinessChange_ByTaxation_Occupied = new ArrayList<>();
   protected static List<Float> goodsUpdate = new ArrayList<>();
   protected static List<Float> devUpdate = new ArrayList<>();
   protected static List<Float> ecoUpdate = new ArrayList<>();

   @Override
   public void run() {
      Gdx.app.log("Turn_NewTurn", "Turn_NewTurn...");
      doAction();
      Gdx.app.log("Turn_NewTurn", "Turn_NewTurn END");
   }

   protected static final void checkOccupiedProvincesIfAreAtWar() {
      //change scope (memory efficiency) and reuse list instead of reallocating
      List<Integer> tempCivsLostAccess = new ArrayList<>();

      if (Game_Calendar.TURN_ID % 5 == 0) {
         for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince()
               && CFG.game.getProvince(i).getWasteland() < 0
               && CFG.game.getProvince(i).isOccupied()
               && CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID() != CFG.ideologiesManager.REBELS_ID
               && !CFG.game.getCivsAtWar(CFG.game.getProvince(i).getCivID(), CFG.game.getProvince(i).getTrueOwnerOfProvince())) {
               CFG.game
                  .getCiv(CFG.game.getProvince(i).getCivID())
                  .getCivilization_Diplomacy_GameData()
                  .messageBox
                  .addMessage(new Message_ProvincesOccupiedNotAtWar_LostControl(CFG.game.getProvince(i).getTrueOwnerOfProvince(), i));
               int tempArmy0 = CFG.game.getProvince(i).getArmy(0);
               int tempCiv0 = CFG.game.getProvince(i).getCivID();
               int tempArmyNewOwner = CFG.game.getProvince(i).getArmyCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince());
               CFG.game.getProvince(i).updateArmy(0);
               CFG.game.getProvince(i).setCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince(), false);
               CFG.game.getProvince(i).updateArmy(tempCiv0, tempArmy0);
               CFG.game.getProvince(i).updateArmy(CFG.game.getProvince(i).getTrueOwnerOfProvince(), tempArmyNewOwner);
               //List<Integer> tempCivsLostAccess = new ArrayList<>();
               tempCivsLostAccess.clear();

               for(int j = 0; j < CFG.game.getProvince(i).getCivsSize(); ++j) {
                  tempCivsLostAccess.add(CFG.game.getProvince(i).getCivID(j));
               }

               for(int j = 0; j < tempCivsLostAccess.size(); ++j) {
                  if (CFG.game.getCiv(tempCivsLostAccess.get(j)).getPuppetOfCivID() != CFG.game.getProvince(i).getTrueOwnerOfProvince()
                     && CFG.game.getCiv(CFG.game.getProvince(i).getTrueOwnerOfProvince()).getPuppetOfCivID() != tempCivsLostAccess.get(j)
                     && (
                        CFG.game.getCiv(tempCivsLostAccess.get(j)).getAllianceID() <= 0
                           || CFG.game.getCiv(tempCivsLostAccess.get(j)).getAllianceID()
                              != CFG.game.getCiv(CFG.game.getProvince(i).getTrueOwnerOfProvince()).getAllianceID()
                     )
                     && CFG.game.getMilitaryAccess(tempCivsLostAccess.get(j), CFG.game.getProvince(i).getTrueOwnerOfProvince()) <= 0) {
                     CFG.gameAction.accessLost_MoveArmyToClosetsProvince(tempCivsLostAccess.get(j), i);
                  }
               }
            }
         }
      }
   }

   protected static final void doAction() {
      try {
         tempTime = System.currentTimeMillis();
         tempTimeTotal = System.currentTimeMillis();
         CFG.game_NextTurnUpdate.updateCivs_Money();
         Gdx.app.log("AoC", "STA41MON: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA41MON: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).runFestivals();
            CFG.game.getCiv(i).runInvests_Development();
            CFG.game.getCiv(i).runInvests();
            CFG.game.getCiv(i).runAssimilates();
            CFG.game.getCiv(i).runWarReparations();

            //run capitulates change//
            //DiplomacyManager.checkCapitulate(i);
         }

         Gdx.app.log("AoC", "STA1: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA1: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         CFG.plagueManager.runPlagues();
         checkOccupiedProvincesIfAreAtWar();
         Gdx.app.log("AoC", "PLAGUES: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("PLAGUES: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         Gdx.app.log("AoC", "STA1: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA1: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         updateGameData();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.updateNextTurn(i);
         }

         Gdx.app.log("AoC", "STA3: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA3: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         CFG.gameAction.updateCivsMovementPoints();
         Gdx.app.log("AoC", "STA41MOVE: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA41MOVE: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         CFG.gameAction.updateCivsDiplomacyPoints();
         Gdx.app.log("AoC", "STA41: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA41: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            for(int j = CFG.game.getCiv(i).lOpt_DefensivePact.size() - 1; j >= 0; --j) {
               CFG.game.getCiv(i).setDiplomacyPoints(CFG.game.getCiv(i).getDiplomacyPoints() - 3);
               CFG.game
                  .getCiv(CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1)
                  .setDiplomacyPoints(CFG.game.getCiv(CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1).getDiplomacyPoints() - 3);
               if (CFG.game.getDefensivePact(i, CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1) == 1) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_DefensivePact_Expired(CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1));
                  CFG.game
                     .getCiv(CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_DefensivePact_Expired(i));
               }

               CFG.game
                  .setDefensivePact(
                     i,
                     CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1,
                     CFG.game.getDefensivePact(i, CFG.game.getCiv(i).lOpt_DefensivePact.get(j) + i + 1) - 1
                  );
            }

            for(int j = CFG.game.getCiv(i).lOpt_NonAggressionPact.size() - 1; j >= 0; --j) {
               CFG.game.getCiv(i).setDiplomacyPoints(CFG.game.getCiv(i).getDiplomacyPoints() - 2);
               CFG.game
                  .getCiv(CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1)
                  .setDiplomacyPoints(CFG.game.getCiv(CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1).getDiplomacyPoints() - 2);
               if (CFG.game.getCivNonAggressionPact(i, CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1) == 1) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_NonAggressionPact_Expired(CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1));
                  CFG.game
                     .getCiv(CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_NonAggressionPact_Expired(i));
               }

               CFG.game
                  .setCivNonAggressionPact(
                     i,
                     CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1,
                     CFG.game.getCivNonAggressionPact(i, CFG.game.getCiv(i).lOpt_NonAggressionPact.get(j) + i + 1) - 1
                  );
            }

            for(int j = CFG.game.getCiv(i).lOpt_Guarantee.size() - 1; j >= 0; --j) {
               CFG.game.getCiv(i).setDiplomacyPoints(CFG.game.getCiv(i).getDiplomacyPoints() - 1);
               CFG.game
                  .getCiv(CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1)
                  .setDiplomacyPoints(CFG.game.getCiv(CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1).getDiplomacyPoints() - 1);
               if (CFG.game.getGuarantee(i, CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1) == 1) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_IndependenceFrom_Expired(CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1));
                  CFG.game
                     .getCiv(CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_Independence_Expired(i));
               }

               CFG.game
                  .setGuarantee(i, CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1, CFG.game.getGuarantee(i, CFG.game.getCiv(i).lOpt_Guarantee.get(j) + 1) - 1);
            }

            for(int j = CFG.game.getCiv(i).lOpt_MilitirayAccess.size() - 1; j >= 0; --j) {
               CFG.game.getCiv(i).setDiplomacyPoints(CFG.game.getCiv(i).getDiplomacyPoints() - 1);
               CFG.game
                  .getCiv(CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1)
                  .setDiplomacyPoints(CFG.game.getCiv(CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1).getDiplomacyPoints() - 1);
               if (CFG.game.getMilitaryAccess(i, CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1) == 1) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_MilitaryAccess_Expired(CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1));
                  CFG.gameAction.accessLost_UpdateArmies(CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1, i);
               } else if (CFG.game.getMilitaryAccess(i, CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1) < 4) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(
                        new Message_MilitaryAccess_ExpireSoon(
                           CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1,
                           CFG.game.getMilitaryAccess(i, CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1) - 1
                        )
                     );
               }

               CFG.game
                  .setMilitaryAccess(
                     i,
                     CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1,
                     CFG.game.getMilitaryAccess(i, CFG.game.getCiv(i).lOpt_MilitirayAccess.get(j) + 1) - 1
                  );
            }

            for(int j = CFG.game.getCiv(i).lOpt_Truce.size() - 1; j >= 0; --j) {
               if (CFG.game.getCivTruce(i, CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1) == 1
                  && CFG.game.getCiv(i).getNumOfProvinces() > 0
                  && CFG.game.getCiv(CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1).getNumOfProvinces() > 0) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_Truce_Expired(CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1));
                  CFG.game
                     .getCiv(CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(new Message_Truce_Expired(i));
               }

               CFG.game
                  .setCivTruce(i, CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1, CFG.game.getCivTruce(i, CFG.game.getCiv(i).lOpt_Truce.get(j) + i + 1) - 1);
            }
         }

         Gdx.app.log("AoC", "STA45DIPLOM: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA45DIPLOM: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         CFG.gameAction.updateCivsHappiness();
         CFG.game_NextTurnUpdate.updateProvinceStability();
         CFG.game_NextTurnUpdate.updateInflationPeakValue();
         CFG.game_NextTurnUpdate.updateClassPerceptions();
         CFG.game_NextTurnUpdate.updateDecisions();
         CFG.game_NextTurnUpdate.updateCivDecisions();
         Game_Calendar.updateDateNextTurn();
         Gdx.app.log("AoC", "STA42: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA42: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).runConstruction();
            if (CFG.game.getCiv(i).getMoney() < -500L) {
               CFG.game.getCiv(i).setSpendings_Research(0.0F);
            }

            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
               if (CFG.game.getCiv(i).getSpendings_Goods() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(
                        new Message_GoodsLow(i, (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i) * 100.0F))
                     );
               }

               if (CFG.game.getCiv(i).getSpendings_Investments() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS) {
                  CFG.game
                     .getCiv(i)
                     .getCivilization_Diplomacy_GameData()
                     .messageBox
                     .addMessage(
                        new Message_InvestmentsLow(i, (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS * 100.0F))
                     );
               }

               if (CFG.game.getCiv(i).civGameData.skills.getPointsLeft(i) > 0) {
                  CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_TechPoints(i));
               }
            }
         }

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
               if (CFG.game.getCiv(i).isAtWar()) {
                  CFG.game
                     .getCiv(i)
                     .setWarWeariness(
                        CFG.game.getCiv(i).getWarWeariness()
                           + 0.00215173F * Math.min(1.5F, (float)CFG.game.getCiv(i).civGameData.iNumOfTurnsAtWar / (18.37154F * Game_Calendar.GAME_SPEED))
                     );
               } else {
                  CFG.game.getCiv(i).setWarWeariness(CFG.game.getCiv(i).getWarWeariness() - 9.5E-4F);
               }
            }
         }

         Gdx.app.log("AoC", "STA43: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA43: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
               for(int j = CFG.game.getCiv(i).lProvincesWithLowStability.size() - 1; j >= 0; --j) {
                  if (CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getProvinceStability()
                        < Game_Action.RISE_REVOLT_RISK_STABILITY
                     && !CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).isOccupied()
                     && CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getRevolutionaryRisk() < 0.55F) {
                     CFG.game
                        .getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j))
                        .setRevolutionaryRisk(
                           CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getRevolutionaryRisk()
                              + ageRiskModifier
                                 * (
                                    Game_Action.RISE_REVOLT_RISK_STABILITY
                                       - CFG.game.getProvince(CFG.game.getCiv(i).lProvincesWithLowStability.get(j)).getProvinceStability()
                                 )
                                 * 0.0155F
                        );
                  }
               }
            }
         }

         Gdx.app.log("AoC", "STASTABILITY: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STASTABILITY: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         if (!CFG.SPECTATOR_MODE) {
            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
               try {
                  if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces() > 0) {
                     CFG.game.getPlayer(i).statistics_Civ_GameData.setTurns(CFG.game.getPlayer(i).statistics_Civ_GameData.getTurns() + 1);
                     //if player turn AI movement, auto move
                     if (CFG.game.getPlayer(i).armyAImovement) {
                        if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).isAtWar()) {
                           CFG.oAI.getAI_Style(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAI_Style()).defendFromSeaInvasion(CFG.game.getPlayer(i).getCivID());
                           CFG.oAI.getAI_Style(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAI_Style()).moveAtWar(CFG.game.getPlayer(i).getCivID());
                        } else {
                           CFG.oAI.getAI_Style(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getAI_Style()).regroupArmy_AtPeace(CFG.game.getPlayer(i).getCivID());
                        }
                     }
                  }
               } catch (NullPointerException var21) {
                  CFG.game.getPlayer(i).tryLoadStats();
               }
            }
         }

         Gdx.app.log("AoC", "ERR: 0000, fontMain" + CFG.fontMain.getData().scaleY);

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).updateBonuses();
            CFG.game.getCiv(i).civGameData.updateGift_Received();
         }

         Gdx.app.log("AoC", "ERR: 1111, fontMain" + CFG.fontMain.getData().scaleY);
         DiplomacyManager.updateGoldenAge();
         Gdx.app.log("AoC", "ERR: 222, fontMain" + CFG.fontMain.getData().scaleY);
         DiplomacyManager.sendUncivilizedMessages();
         Gdx.app.log("AoC", "ERR: 3333, fontMain" + CFG.fontMain.getData().scaleY);
         DiplomacyManager.sendLowHappiness();
         Gdx.app.log("AoC", "ERR: 444, fontMain" + CFG.fontMain.getData().scaleY);
         if (!CFG.SPECTATOR_MODE) {
            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
               if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getNumOfProvinces() > 0) {
                  for(int j = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.iWarPreparationsSize - 1; j >= 0; --j) {
                     if (--CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.warPreparations.get(j).iNumOfTurnsLeft <= 0) {
                        int tOnCivID = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.warPreparations.get(j).onCivID;
                        CFG.game
                           .declareWar(
                              CFG.game.getPlayer(i).getCivID(),
                              CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.warPreparations.get(j).onCivID,
                              false
                           );
                        CFG.game
                           .getCiv(CFG.game.getPlayer(i).getCivID())
                           .civGameData
                           .civilization_Diplomacy_GameData
                           .messageBox
                           .addMessage(new Message_War(tOnCivID, CFG.game.getPlayer(i).getCivID()));

                        try {
                           CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.warPreparations.remove(j);
                           CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).civGameData.civPlans.iWarPreparationsSize = CFG.game
                              .getCiv(CFG.game.getPlayer(i).getCivID())
                              .civGameData
                              .civPlans
                              .warPreparations
                              .size();
                        } catch (IndexOutOfBoundsException var20) {
                        }
                     }
                  }

                  if (CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCapitalProvinceID() < 0
                     || CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCapitalProvinceID()).getCivID()
                           != CFG.game.getPlayer(i).getCivID()
                        && !CFG.game.getProvince(CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).getCapitalProvinceID()).isOccupied()) {
                     CFG.game
                        .getCiv(CFG.game.getPlayer(i).getCivID())
                        .civGameData
                        .civilization_Diplomacy_GameData
                        .messageBox
                        .addMessage(new Message_RelocateCapital(CFG.game.getPlayer(i).getCivID()));
                  }
               }
            }
         }

         Gdx.app.log("AoC", "ERR: 5555, fontMain" + CFG.fontMain.getData().scaleY);
         CFG.gameAction.updateHRE_Elections();
         Gdx.app.log("AoC", "ERR: 666, fontMain" + CFG.fontMain.getData().scaleY);
         if (CFG.SANDBOX_MODE && !CFG.SPECTATOR_MODE) {
            CFG.gameNewGame.sandboxMode();
         }

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().updateEmbassyClosed();
            CFG.game.getCiv(i).getCivilization_Diplomacy_GameData().runImproveRelations(i);
         }

         if (Game_Calendar.TURN_ID % 4 == 0) {
            CFG.gameAction.updateRelations();
         }

         DiplomacyManager.checkCivsHatedCivilizations_IfStillExsits();
         DiplomacyManager.updatePlayersFriendlyCivs();

         for(int i = 0; i < CFG.game.getWarsSize(); ++i) {
            ++CFG.game.getWar(i).iLastFight_InTunrs;
         }

         Gdx.app.log("AoC-", "STA TOTAL: " + (System.currentTimeMillis() - tempTimeTotal));
         Commands.addMessage("STA TOTAL: " + (System.currentTimeMillis() - tempTimeTotal));
         Gdx.app.log("AoC", "STA4BFSAVE: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA4BFSAVE: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         SaveManager.trySaveGame();
         Gdx.app.log("AoC", "SAVE: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("SAVE: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
            CFG.game.getPlayer(CFG.PLAYER_TURNID).setNoOrders(true);
         }

         CFG.gameAction.moveRegroupArmy();

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getUpdateRegions()) {
               try {
                  CFG.game.getCiv(i).setUpdateRegions(false);
                  CFG.game.buildCivilizationRegions(i);
               } catch (IndexOutOfBoundsException var16) {
               } catch (NullPointerException var17) {
               } catch (ArithmeticException var18) {
               } catch (StackOverflowError var19) {
               }
            }
         }

         CFG.gameAction.updateIsSupplied();
         Gdx.app.log("AoC", "STA5: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA5: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
         CFG.eventsManager.checkEvents();
         ++Game_Calendar.TURNS_SINCE_LAST_WAR;
         CFG.gameAction.buildRank_Score();

         if (!CFG.SPECTATOR_MODE) {
            for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
               try {
                  CFG.game.getPlayer(i).iTurnPopulation = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).countPopulation_WithoutOccupied()
                     - CFG.game.getPlayer(i).iTurnPopulation;
                  CFG.game.getPlayer(i).iTurnEconomy = CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).countEconomy_WithoutOccupied()
                     - CFG.game.getPlayer(i).iTurnEconomy;
               } catch (IndexOutOfBoundsException var15) {
                  CFG.game.getPlayer(i).iTurnPopulation = 0;
                  CFG.game.getPlayer(i).iTurnEconomy = 0;
               }
            }
         }

         //dynamic event manager change//
         if (CFG.DYNAMIC_EVENTS) {
            CFG.dynamicEventManager.newTurnInvokeEvents();
         }

         CFG.historyManager.addNewTurn();
         CFG.timelapseManager.newTurn();
         if (Game_Calendar.TURN_ID % (CFG.isDesktop() ? 6 : 12) == 0) {
            CFG.game_NextTurnUpdate.updateCities();
            Gdx.app.log("AoC", "CITIES UPDATE: " + (System.currentTimeMillis() - tempTime));
            Commands.addMessage("CITIES UPDATE: " + (System.currentTimeMillis() - tempTime));
         }

         Gdx.app.log("AoC", "STA6: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA6: " + (System.currentTimeMillis() - tempTime));
         updateGameData_TurnChanges();
         CFG.gameAction.checkGameEnd();
      } catch (Exception var22) {
         CFG.exceptionStack(var22);
      } finally {
         CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).setClickable(true);
         Menu_InGame.TIME_CONTINUE = System.currentTimeMillis();
         CFG.setRender_3(true);
      }
   }

   protected static final void updateGameData_TurnChanges() {
      if (CFG.isDesktop()) {
         for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            CFG.game.getProvince(i).saveProvinceData.turnChange_Population = CFG.game.getProvince(i).getPopulationData().getPopulation()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_Population;
            CFG.game.getProvince(i).saveProvinceData.turnChange_Economy = CFG.game.getProvince(i).getEconomy()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_Economy;
            CFG.game.getProvince(i).saveProvinceData.turnChange_Development = CFG.game.getProvince(i).getDevelopmentLevel()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_Development;
            CFG.game.getProvince(i).saveProvinceData.turnChange_Happiness = CFG.game.getProvince(i).getHappiness()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_Happiness;
            CFG.game.getProvince(i).saveProvinceData.turnChange_Stability = CFG.game.getProvince(i).getProvinceStability()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_Stability;
            CFG.game.getProvince(i).saveProvinceData.turnChange_RevRisk = CFG.game.getProvince(i).getRevolutionaryRisk()
               - CFG.game.getProvince(i).saveProvinceData.turnChange_RevRisk;
         }

         Gdx.app.log("AoC", "STA7PROVINCESS: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA7PROVINCESS: " + (System.currentTimeMillis() - tempTime));
      }
   }

   protected static final void updateGameData() {
      if (CFG.isDesktop()) {
         //instead of reallocating, clear and reuse lists for memory
         //tempCivs = new ArrayList<>();
         //happinessChange_ByTaxation = new ArrayList<>();
         //happinessChange_ByTaxation_Occupied = new ArrayList<>();
         //goodsUpdate = new ArrayList<>();
         //devUpdate = new ArrayList<>();
         //ecoUpdate = new ArrayList<>();
         tempCivs.clear();
         happinessChange_ByTaxation.clear();
         happinessChange_ByTaxation_Occupied.clear();
         goodsUpdate.clear();
         devUpdate.clear();
         ecoUpdate.clear();

         ageRiskModifier = CFG.gameAges.getAge_RevolutionaryRiskModifier(Game_Calendar.CURRENT_AGEID);
         ageDevMod = CFG.gameAges.getAge_DevelopmentLevel_Increase(Game_Calendar.CURRENT_AGEID);

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
               happinessChange_ByTaxation.add(CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation(i));
               happinessChange_ByTaxation_Occupied.add(CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation_Occupied(i));
               goodsUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Goods() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                     ? -0.0192864F
                        * (
                           (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i) - CFG.game.getCiv(i).getSpendings_Goods())
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                        )
                     : (
                           -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                              + 0.013845F
                              + CFG.game.getCiv(i).getSpendings_Goods()
                        )
                        * CFG.gameAges.getAge_Population_GrowthRate(Game_Calendar.CURRENT_AGEID)
               );
               devUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Investments() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                     ? -0.642864F
                        * (
                           (
                                 CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                                    - CFG.game.getCiv(i).getSpendings_Investments()
                              )
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        )
                     : -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        + 0.01F
                        + CFG.game.getCiv(i).getSpendings_Investments()
               );
               ecoUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Investments() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                     ? -0.0192864F
                        * (
                           (
                                 CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                                    - CFG.game.getCiv(i).getSpendings_Investments()
                              )
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        )
                     : (
                           -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                              + 0.01F
                              + CFG.game.getCiv(i).getSpendings_Investments()
                        )
                        * CFG.gameAges.getAge_Economy_GrowthRate(Game_Calendar.CURRENT_AGEID)
               );
            } else {
               happinessChange_ByTaxation.add(1.0F);
               happinessChange_ByTaxation_Occupied.add(1.0F);
               goodsUpdate.add(1.0F);
               devUpdate.add(1.0F);
               ecoUpdate.add(1.0F);
            }

            CFG.game.getCiv(i).civGameData.civAggresionLevel = Math.max(0.0F, CFG.game.getCiv(i).civGameData.civAggresionLevel - 0.005F);
         }

         float modifiedStartingPop = (float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 0.1875F;
         float modifiedStartingEco = (float)CFG.game.getGameScenarios().getScenario_StartingEconomy() * 0.0925F;
         Gdx.app.log("AoC", "STA2_PREP: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA2_PREP: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
               if (CFG.game.getProvince(i).getCivID() > 0) {
                  if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == CFG.game.getProvince(i).getCivID()
                     && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                     CFG.game.getProvince(i).getCore().increaseOwnership(CFG.game.getProvince(i).getCivID(), i);
                  }

                  if (CFG.game.getProvince(i).getDevelopmentLevel() < 1.0F) {
                     if (CFG.game.getProvince(i).getCivID() == CFG.game.getProvince(i).getTrueOwnerOfProvince()) {
                        float tempDevelopmentChange = ageDevMod
                           * devUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                           * Math.min(CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.45F, 0.3705F);
                        CFG.game.getProvince(i).setDevelopmentLevel(CFG.game.getProvince(i).getDevelopmentLevel() + tempDevelopmentChange);
                     } else {
                        CFG.game.getProvince(i).setDevelopmentLevel(CFG.game.getProvince(i).getDevelopmentLevel() - (float)CFG.oR.nextInt(275) / 100000.0F);
                     }
                  }

                  float tempPopGrowth = (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                     * (
                        0.2F
                           + (
                              CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    < CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                                 ? (float)CFG.oR.nextInt(50) / 100.0F
                                 : 0.5F
                           )
                     )
                     * goodsUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                     * (
                        0.01F
                           + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain()
                           + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getModifier_PopGrowth()
                     )
                     * 0.485F
                     * (
                        1.0F
                           + CFG.game.getProvince(i).getDevelopmentLevel() / 63.3468F
                           + CFG.game.getGameScenarios().getScenario_PopulationGrowthRate_Modifier()
                     )
                     * Game_Calendar.GAME_SPEED;
                  if (tempPopGrowth > 0.0F) {
                     if ((float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                        < modifiedStartingPop * CFG.game.getProvince(i).getGrowthRate_Population()) {
                        tempPopGrowth += (float)CFG.game.getGameScenarios().getScenario_StartingPopulation()
                           * 0.00725F
                           * (
                              1.0F
                                 - (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / (float)CFG.game.getGameScenarios().getScenario_StartingPopulation()
                           )
                           * CFG.game.getProvince(i).getGrowthRate_Population()
                           * Math.min(CFG.game.getProvince(i).getDevelopmentLevel() * 2.7469234F, 1.0F);
                     }

                     tempPopGrowth = 1.0F
                        + tempPopGrowth
                           * Math.max(
                              0.0865F,
                              1.0F
                                 - 0.4F
                                    * (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / ((float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 1.825F)
                           );
                     if (tempPopGrowth > 0.0F) {
                        tempPopGrowth = tempPopGrowth * 0.1F
                           + (float)CFG.oR.nextInt(Math.max((int)(tempPopGrowth * 1.0F * 100.0F), 1)) / 100.0F
                           - (float)CFG.oR.nextInt(Math.max((int)(tempPopGrowth * 0.325F * 100.0F), 1)) / 100.0F;
                     }
                  }

                  if ((int)tempPopGrowth != 0) {
                     if (tempPopGrowth > -10.0F && tempPopGrowth < 16.0F) {
                        CFG.game
                           .getProvince(i)
                           .getPopulationData()
                           .setPopulationOfCivID(
                              CFG.game.getProvince(i).getCivID(),
                              CFG.game.getProvince(i).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(i).getCivID()) + (int)tempPopGrowth
                           );
                     } else {
                        tempCivs.clear();
                        tempCivs.add(new PopulationGrowth(CFG.game.getProvince(i).getCivID(), 8.13F * CFG.game.getProvince(i).getProvinceStability()));
                        if (CFG.game.getProvince(i).getCivID() != CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID()) {
                           tempCivs.add(new PopulationGrowth(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID(), 5.4378F));
                        }

                        if (CFG.game.getProvince(i).isOccupied()) {
                           tempCivs.add(new PopulationGrowth(CFG.game.getProvince(i).getTrueOwnerOfProvince(), 6.241138F));
                        }

                        for(int j = 0; j < CFG.game.getProvince(i).getCore().getCivsSize(); ++j) {
                           tempCivs.add(new PopulationGrowth(CFG.game.getProvince(i).getCore().getCivID(j), 6.7861F));
                        }

                        int tempPop = CFG.game.getProvince(i).getPopulationData().getPopulation();

                        for(int j = 0; j < CFG.game.getProvince(i).getPopulationData().getNationalitiesSize(); ++j) {
                           tempCivs.add(
                              new PopulationGrowth(
                                 CFG.game.getProvince(i).getPopulationData().getCivID(j),
                                 (float)CFG.game.getProvince(i).getPopulationData().getPopulationID(j) / (float)tempPop * 100.0F
                              )
                           );
                        }

                        for(int j = 0; j < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++j) {
                           if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID() > 0) {
                              tempCivs.add(new PopulationGrowth(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID(), 2.0F));
                           }
                        }

                        float tempTotalPoints = 0.0F;

                        for(int j = tempCivs.size() - 1; j >= 0; --j) {
                           tempTotalPoints += tempCivs.get(j).fPerc;
                        }

                        for(int j = tempCivs.size() - 1; j >= 0; --j) {
                           tempCivs.get(j).fPerc /= tempTotalPoints;
                           CFG.game
                              .getProvince(i)
                              .getPopulationData()
                              .setPopulationOfCivID(
                                 tempCivs.get(j).iCivID,
                                 CFG.game.getProvince(i).getPopulationData().getPopulationOfCivID(tempCivs.get(j).iCivID)
                                    + (int)(tempPopGrowth * tempCivs.get(j).fPerc)
                              );
                        }

                        tempCivs.clear();
                     }
                  }

                  if (CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                     < CFG.ideologiesManager
                        .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                        .getMin_Goods(CFG.game.getProvince(i).getCivID())) {
                     float tempHapp = -0.01225F
                        * (
                           (
                                 CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                              )
                              / CFG.ideologiesManager
                                 .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                 .getMin_Goods(CFG.game.getProvince(i).getCivID())
                        )
                        * (
                           0.01F
                              + CFG.game.getProvince(i).getDevelopmentLevel() * 1.25F
                              + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm() * 0.135F
                        );
                     if (tempHapp > 0.0F) {
                        tempHapp *= 1.0F - 0.625F * CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.fWarWeariness;
                     }

                     CFG.game.getProvince(i).setHappiness(CFG.game.getProvince(i).getHappiness() + tempHapp);
                     float tempEcoPop = (float)CFG.game.getProvince(i).getEconomy()
                        * -0.00625F
                        * (
                           (
                                 CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                              )
                              / CFG.ideologiesManager
                                 .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                 .getMin_Goods(CFG.game.getProvince(i).getCivID())
                        )
                        * (
                           0.01F
                              + CFG.game.getProvince(i).getDevelopmentLevel() * 0.5475F
                              + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.195F
                        );
                     CFG.game.getProvince(i).setEconomy((int)((float)CFG.game.getProvince(i).getEconomy() + tempEcoPop));
                  }

                  float tempEco = Math.max(
                        (float)CFG.game.getProvince(i).getEconomy(),
                        (float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 0.0825F * CFG.game.getProvince(i).getGrowthRate_Population()
                     )
                     * ecoUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                     * (
                        0.65F
                           + 0.275F
                              * (CFG.game.getProvince(i).getDevelopmentLevel() / CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTechnologyLevel())
                           + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.075F
                     )
                     * (
                        1.0F
                           + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getModifier_EconomyGrowth()
                           + CFG.game.getGameScenarios().getScenario_EconomyGrowthRate_Modifier()
                     )
                     * Game_Calendar.GAME_SPEED;
                  if (tempEco > 0.0F) {
                     if ((float)CFG.game.getProvince(i).getEconomy() < modifiedStartingEco * CFG.game.getProvince(i).getGrowthRate_Population()) {
                        tempEco += (float)CFG.game.getGameScenarios().getScenario_StartingEconomy()
                           * 0.00425F
                           * (
                              1.0F
                                 - (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / (float)CFG.game.getGameScenarios().getScenario_StartingEconomy()
                           )
                           * CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain()
                           * 0.7548F
                           * Math.min(CFG.game.getProvince(i).getDevelopmentLevel() * 3.165134F, 1.0F);
                     }

                     tempEco *= Math.max(
                        0.086486F,
                        1.0F
                           - 0.4F * (float)CFG.game.getProvince(i).getEconomy() / ((float)CFG.game.getGameScenarios().getScenario_StartingEconomy() * 1.4681F)
                     );
                     if (tempEco > 0.0F) {
                        tempEco = tempEco * 0.1F
                           + (float)CFG.oR.nextInt(Math.max((int)(tempEco * 1.0F * 100.0F), 1)) / 100.0F
                           - (float)CFG.oR.nextInt(Math.max((int)(tempEco * 0.25F * 100.0F), 1)) / 100.0F;
                     }
                  }

                  CFG.game.getProvince(i).setEconomy((int)((float)CFG.game.getProvince(i).getEconomy() + tempEco));
                  if (CFG.game.getProvince(i).getCivID() == CFG.game.getProvince(i).getTrueOwnerOfProvince()) {
                     if (happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1) > 0.0F) {
                        CFG.game
                           .getProvince(i)
                           .setHappiness(
                              CFG.game.getProvince(i).getHappiness()
                                 + (float)CFG.oR
                                       .nextInt((int)(Math.max(happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1), 0.01F) * 100.0F))
                                    / 10000.0F
                           );
                     } else {
                        CFG.game
                           .getProvince(i)
                           .setHappiness(
                              CFG.game.getProvince(i).getHappiness()
                                 + (
                                       happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1)
                                          + happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1)
                                             * (0.2F - 0.2F * CFG.game.getProvince(i).getProvinceStability())
                                    )
                                    / 100.0F
                           );
                     }
                  } else if (happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) > 0.0F) {
                     CFG.game
                        .getProvince(i)
                        .setHappiness(
                           CFG.game.getProvince(i).getHappiness()
                              + (float)CFG.oR
                                    .nextInt(Math.max(1, (int)(happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) * 100.0F)))
                                 / 10000.0F
                        );
                  } else {
                     CFG.game
                        .getProvince(i)
                        .setHappiness(
                           CFG.game.getProvince(i).getHappiness() + happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) / 100.0F
                        );
                  }

                  if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                     CFG.game.getProvince(i).setRevolutionaryRisk(0.0F);
                  } else {
                     float fRisk = CFG.game.getProvince(i).getRevolutionaryRisk();
                     if (fRisk > 0.0075F) {
                        fRisk -= Math.min(fRisk / 10.0F, 0.012437F) * (1.0F - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getWarWeariness());
                     }

                     if (CFG.game.getProvince(i).getHappiness() < Game_Action.RISE_REVOLT_RISK_HAPPINESS) {
                        float nModifier;
                        if (CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getMoney() < -1000L) {
                           nModifier = 1.0F;
                        } else {
                           nModifier = Math.min(
                              0.0725F
                                 + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTaxationLevel()
                                    / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).ACCEPTABLE_TAXATION,
                              1.0F
                           );
                        }

                        fRisk += nModifier * ageRiskModifier * (Game_Action.RISE_REVOLT_RISK_HAPPINESS - CFG.game.getProvince(i).getHappiness()) / 14.0F;
                     }

                     CFG.game.getProvince(i).setRevolutionaryRisk(fRisk);
                  }

                  CFG.game.getProvince(i).runSupportRebels();
                  CFG.game.getProvince(i).updateNewColony();
               } else if (CFG.oR.nextInt(50) > 38) {
                  CFG.game.getProvince(i).updateArmy(0, CFG.game.getProvince(i).getArmyCivID(0) - 4 + CFG.oR.nextInt(11));
               }
            }
         }

         tempCivs.clear();
         //tempCivs = null;
         happinessChange_ByTaxation.clear();
         happinessChange_ByTaxation_Occupied.clear();
         goodsUpdate.clear();
         devUpdate.clear();
         ecoUpdate.clear();
         Gdx.app.log("AoC", "STA2: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA2: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
      } else {
         tempCivs = new ArrayList<>();
         happinessChange_ByTaxation = new ArrayList<>();
         happinessChange_ByTaxation_Occupied = new ArrayList<>();
         goodsUpdate = new ArrayList<>();
         devUpdate = new ArrayList<>();
         ecoUpdate = new ArrayList<>();
         ageRiskModifier = CFG.gameAges.getAge_RevolutionaryRiskModifier(Game_Calendar.CURRENT_AGEID);
         ageDevMod = CFG.gameAges.getAge_DevelopmentLevel_Increase(Game_Calendar.CURRENT_AGEID);

         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0) {
               happinessChange_ByTaxation.add(CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation(i));
               happinessChange_ByTaxation_Occupied.add(CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation_Occupied(i));
               goodsUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Goods() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                     ? -0.0192864F
                        * (
                           (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i) - CFG.game.getCiv(i).getSpendings_Goods())
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                        )
                     : (
                           -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).getMin_Goods(i)
                              + 0.013845F
                              + CFG.game.getCiv(i).getSpendings_Goods()
                        )
                        * CFG.gameAges.getAge_Population_GrowthRate(Game_Calendar.CURRENT_AGEID)
               );
               devUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Investments() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                     ? -0.642864F
                        * (
                           (
                                 CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                                    - CFG.game.getCiv(i).getSpendings_Investments()
                              )
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        )
                     : -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        + 0.01F
                        + CFG.game.getCiv(i).getSpendings_Investments()
               );
               ecoUpdate.add(
                  CFG.game.getCiv(i).getSpendings_Investments() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                     ? -0.0192864F
                        * (
                           (
                                 CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                                    - CFG.game.getCiv(i).getSpendings_Investments()
                              )
                              / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                        )
                     : (
                           -CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).MIN_INVESTMENTS
                              + 0.01F
                              + CFG.game.getCiv(i).getSpendings_Investments()
                        )
                        * CFG.gameAges.getAge_Economy_GrowthRate(Game_Calendar.CURRENT_AGEID)
               );
            } else {
               happinessChange_ByTaxation.add(1.0F);
               happinessChange_ByTaxation_Occupied.add(1.0F);
               goodsUpdate.add(1.0F);
               devUpdate.add(1.0F);
               ecoUpdate.add(1.0F);
            }

            CFG.game.getCiv(i).civGameData.civAggresionLevel = Math.max(0.0F, CFG.game.getCiv(i).civGameData.civAggresionLevel - 0.005F);
         }

         float modifiedStartingPop = (float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 0.1875F;
         float modifiedStartingEco = (float)CFG.game.getGameScenarios().getScenario_StartingEconomy() * 0.0925F;
         Gdx.app.log("AoC", "STA2_PREP: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA2_PREP: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();

         for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
               if (CFG.game.getProvince(i).getCivID() > 0) {
                  if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == CFG.game.getProvince(i).getCivID()
                     && !CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                     CFG.game.getProvince(i).getCore().increaseOwnership(CFG.game.getProvince(i).getCivID(), i);
                  }

                  if (CFG.game.getProvince(i).getDevelopmentLevel() < 1.0F) {
                     if (CFG.game.getProvince(i).getCivID() == CFG.game.getProvince(i).getTrueOwnerOfProvince()) {
                        float tempDevelopmentChange = ageDevMod
                           * devUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                           * Math.min(CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.45F, 0.3705F);
                        CFG.game.getProvince(i).setDevelopmentLevel(CFG.game.getProvince(i).getDevelopmentLevel() + tempDevelopmentChange);
                     } else {
                        CFG.game.getProvince(i).setDevelopmentLevel(CFG.game.getProvince(i).getDevelopmentLevel() - (float)CFG.oR.nextInt(275) / 100000.0F);
                     }
                  }

                  float tempPopGrowth = (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                     * (
                        0.2F
                           + (
                              CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    < CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                                 ? (float)CFG.oR.nextInt(50) / 100.0F
                                 : 0.5F
                           )
                     )
                     * goodsUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                     * (
                        0.01F
                           + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain()
                           + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getModifier_PopGrowth()
                     )
                     * 0.485F
                     * (
                        1.0F
                           + CFG.game.getProvince(i).getDevelopmentLevel() / 63.3468F
                           + CFG.game.getGameScenarios().getScenario_PopulationGrowthRate_Modifier()
                     )
                     * Game_Calendar.GAME_SPEED;
                  if (tempPopGrowth > 0.0F) {
                     if ((float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                        < modifiedStartingPop * CFG.game.getProvince(i).getGrowthRate_Population()) {
                        tempPopGrowth += (float)CFG.game.getGameScenarios().getScenario_StartingPopulation()
                           * 0.00725F
                           * (
                              1.0F
                                 - (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / (float)CFG.game.getGameScenarios().getScenario_StartingPopulation()
                           )
                           * CFG.game.getProvince(i).getGrowthRate_Population()
                           * Math.min(CFG.game.getProvince(i).getDevelopmentLevel() * 2.7469234F, 1.0F);
                     }

                     tempPopGrowth = 1.0F
                        + tempPopGrowth
                           * Math.max(
                              0.0865F,
                              1.0F
                                 - 0.4F
                                    * (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / ((float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 1.825F)
                           );
                     if (tempPopGrowth > 0.0F) {
                        tempPopGrowth = tempPopGrowth * 0.1F
                           + (float)CFG.oR.nextInt(Math.max((int)(tempPopGrowth * 1.0F * 100.0F), 1)) / 100.0F
                           - (float)CFG.oR.nextInt(Math.max((int)(tempPopGrowth * 0.325F * 100.0F), 1)) / 100.0F;
                     }
                  }

                  if ((int)tempPopGrowth != 0) {
                     CFG.game
                        .getProvince(i)
                        .getPopulationData()
                        .setPopulationOfCivID(
                           CFG.game.getProvince(i).getCivID(),
                           CFG.game.getProvince(i).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(i).getCivID()) + (int)tempPopGrowth
                        );
                  }

                  if (CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                     < CFG.ideologiesManager
                        .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                        .getMin_Goods(CFG.game.getProvince(i).getCivID())) {
                     float tempHapp = -0.01225F
                        * (
                           (
                                 CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                              )
                              / CFG.ideologiesManager
                                 .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                 .getMin_Goods(CFG.game.getProvince(i).getCivID())
                        )
                        * (
                           0.01F
                              + CFG.game.getProvince(i).getDevelopmentLevel() * 1.25F
                              + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm() * 0.135F
                        );
                     if (tempHapp > 0.0F) {
                        tempHapp *= 1.0F - 0.625F * CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).civGameData.fWarWeariness;
                     }

                     CFG.game.getProvince(i).setHappiness(CFG.game.getProvince(i).getHappiness() + tempHapp);
                     float tempEcoPop = (float)CFG.game.getProvince(i).getEconomy()
                        * -0.00625F
                        * (
                           (
                                 CFG.ideologiesManager
                                       .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                       .getMin_Goods(CFG.game.getProvince(i).getCivID())
                                    - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getSpendings_Goods()
                              )
                              / CFG.ideologiesManager
                                 .getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID())
                                 .getMin_Goods(CFG.game.getProvince(i).getCivID())
                        )
                        * (
                           0.01F
                              + CFG.game.getProvince(i).getDevelopmentLevel() * 0.5475F
                              + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.195F
                        );
                     CFG.game.getProvince(i).setEconomy((int)((float)CFG.game.getProvince(i).getEconomy() + tempEcoPop));
                  }

                  float tempEco = Math.max(
                        (float)CFG.game.getProvince(i).getEconomy(),
                        (float)CFG.game.getGameScenarios().getScenario_StartingPopulation() * 0.0825F * CFG.game.getProvince(i).getGrowthRate_Population()
                     )
                     * ecoUpdate.get(CFG.game.getProvince(i).getCivID() - 1)
                     * (
                        0.65F
                           + 0.275F
                              * (CFG.game.getProvince(i).getDevelopmentLevel() / CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTechnologyLevel())
                           + CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain() * 0.075F
                     )
                     * (
                        1.0F
                           + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getModifier_EconomyGrowth()
                           + CFG.game.getGameScenarios().getScenario_EconomyGrowthRate_Modifier()
                     )
                     * Game_Calendar.GAME_SPEED;
                  if (tempEco > 0.0F) {
                     if ((float)CFG.game.getProvince(i).getEconomy() < modifiedStartingEco * CFG.game.getProvince(i).getGrowthRate_Population()) {
                        tempEco += (float)CFG.game.getGameScenarios().getScenario_StartingEconomy()
                           * 0.00425F
                           * (
                              1.0F
                                 - (float)CFG.game.getProvince(i).getPopulationData().getPopulation()
                                    / (float)CFG.game.getGameScenarios().getScenario_StartingEconomy()
                           )
                           * CFG.game.getProvince(i).getGrowthRate_Population_WithFarm_WithTerrain()
                           * 0.7548F
                           * Math.min(CFG.game.getProvince(i).getDevelopmentLevel() * 3.165134F, 1.0F);
                     }

                     tempEco *= Math.max(
                        0.086486F,
                        1.0F
                           - 0.4F * (float)CFG.game.getProvince(i).getEconomy() / ((float)CFG.game.getGameScenarios().getScenario_StartingEconomy() * 1.4681F)
                     );
                     if (tempEco > 0.0F) {
                        tempEco = tempEco * 0.1F
                           + (float)CFG.oR.nextInt(Math.max((int)(tempEco * 1.0F * 100.0F), 1)) / 100.0F
                           - (float)CFG.oR.nextInt(Math.max((int)(tempEco * 0.25F * 100.0F), 1)) / 100.0F;
                     }
                  }

                  CFG.game.getProvince(i).setEconomy((int)((float)CFG.game.getProvince(i).getEconomy() + tempEco));
                  if (CFG.game.getProvince(i).getCivID() == CFG.game.getProvince(i).getTrueOwnerOfProvince()) {
                     if (happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1) > 0.0F) {
                        CFG.game
                           .getProvince(i)
                           .setHappiness(
                              CFG.game.getProvince(i).getHappiness()
                                 + (float)CFG.oR
                                       .nextInt((int)(Math.max(happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1), 0.01F) * 100.0F))
                                    / 10000.0F
                           );
                     } else {
                        CFG.game
                           .getProvince(i)
                           .setHappiness(
                              CFG.game.getProvince(i).getHappiness()
                                 + (
                                       happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1)
                                          + happinessChange_ByTaxation.get(CFG.game.getProvince(i).getCivID() - 1)
                                             * (0.2F - 0.2F * CFG.game.getProvince(i).getProvinceStability())
                                    )
                                    / 100.0F
                           );
                     }
                  } else if (happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) > 0.0F) {
                     CFG.game
                        .getProvince(i)
                        .setHappiness(
                           CFG.game.getProvince(i).getHappiness()
                              + (float)CFG.oR
                                    .nextInt(Math.max(1, (int)(happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) * 100.0F)))
                                 / 10000.0F
                        );
                  } else {
                     CFG.game
                        .getProvince(i)
                        .setHappiness(
                           CFG.game.getProvince(i).getHappiness() + happinessChange_ByTaxation_Occupied.get(CFG.game.getProvince(i).getCivID() - 1) / 100.0F
                        );
                  }

                  if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).REVOLUTIONARY) {
                     CFG.game.getProvince(i).setRevolutionaryRisk(0.0F);
                  } else {
                     float fRisk = CFG.game.getProvince(i).getRevolutionaryRisk();
                     if (fRisk > 0.0075F) {
                        fRisk -= Math.min(fRisk / 10.0F, 0.012437F) * (1.0F - CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getWarWeariness());
                     }

                     if (CFG.game.getProvince(i).getHappiness() < Game_Action.RISE_REVOLT_RISK_HAPPINESS) {
                        float nModifier;
                        if (CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getMoney() < -1000L) {
                           nModifier = 1.0F;
                        } else {
                           nModifier = Math.min(
                              0.0725F
                                 + CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTaxationLevel()
                                    / CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).ACCEPTABLE_TAXATION,
                              1.0F
                           );
                        }

                        fRisk += nModifier * ageRiskModifier * (Game_Action.RISE_REVOLT_RISK_HAPPINESS - CFG.game.getProvince(i).getHappiness()) / 14.0F;
                     }

                     CFG.game.getProvince(i).setRevolutionaryRisk(fRisk);
                  }

                  CFG.game.getProvince(i).runSupportRebels();
                  CFG.game.getProvince(i).updateNewColony();
               } else if (CFG.oR.nextInt(50) > 38) {
                  CFG.game.getProvince(i).updateArmy(0, CFG.game.getProvince(i).getArmyCivID(0) - 4 + CFG.oR.nextInt(11));
               }
            }
         }

         tempCivs.clear();
         //tempCivs = null;
         happinessChange_ByTaxation.clear();
         happinessChange_ByTaxation_Occupied.clear();
         goodsUpdate.clear();
         devUpdate.clear();
         ecoUpdate.clear();
         Gdx.app.log("AoC", "STA2: " + (System.currentTimeMillis() - tempTime));
         Commands.addMessage("STA2: " + (System.currentTimeMillis() - tempTime));
         tempTime = System.currentTimeMillis();
      }
   }
}
