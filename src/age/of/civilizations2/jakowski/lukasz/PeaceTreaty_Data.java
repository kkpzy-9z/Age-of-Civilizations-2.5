package age.of.civilizations2.jakowski.lukasz;

import java.io.Console;
import java.io.IOException;
import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.sun.javaws.exceptions.CacheAccessException;
import org.lwjgl.Sys;

class PeaceTreaty_Data
{
   protected PeaceTreaty_GameData peaceTreatyGameData;
   protected List<PeaceTreaty_DrawData> drawProvinceOwners;
   protected List<Integer> provincesLeftToTake;
   protected int iProvincesLeftToTakeSize;
   protected boolean scoreCountDefenders;
   protected int iBrushCivID;
   protected int iPlayerTurnID;
   protected int iLastTakenID;
   protected static final float VASSALIZE_COST = 0.4f;
   protected static final float WAR_REPARATIONS_COST = 0.1f;
   private int recursionDepth = 0;
   private static final int MAX_RECURSION_DEPTH = 8500; // to prevent stack overflow

   protected PeaceTreaty_Data() {
      super();
      this.peaceTreatyGameData = new PeaceTreaty_GameData();
      this.drawProvinceOwners = new ArrayList<PeaceTreaty_DrawData>();
      this.provincesLeftToTake = new ArrayList<Integer>();
      this.iProvincesLeftToTakeSize = 0;
      this.scoreCountDefenders = false;
      this.iBrushCivID = -1;
      this.iPlayerTurnID = 0;
      this.iLastTakenID = -1;
   }

   protected PeaceTreaty_Data(final PeaceTreaty_GameData nPeaceTreaty) {
      super();
      this.peaceTreatyGameData = new PeaceTreaty_GameData();
      this.drawProvinceOwners = new ArrayList<PeaceTreaty_DrawData>();
      this.provincesLeftToTake = new ArrayList<Integer>();
      this.iProvincesLeftToTakeSize = 0;
      this.scoreCountDefenders = false;
      this.iBrushCivID = -1;
      this.iPlayerTurnID = 0;
      this.iLastTakenID = -1;
      this.peaceTreatyGameData = nPeaceTreaty;
      this.iBrushCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
      this.iPlayerTurnID = CFG.PLAYER_TURNID;
      this.prepareProvinceData(false);
      this.prepareDemansVassalsData();
   }

   protected boolean isPlayerInvolved() {
      if ((CFG.PLAYER_PEACE || CFG.SPECTATOR_MODE) && (CFG.sandbox_task == Menu.eINGAME_PEACE_TREATY)) {
         return Menu_PeaceTreaty.WAR_ID == this.peaceTreatyGameData.iWarID;
      }
      return false;
   }

   protected PeaceTreaty_Data(final int iWarID, final boolean scoreCountDefenders) {
      super();
      this.peaceTreatyGameData = new PeaceTreaty_GameData();
      this.drawProvinceOwners = new ArrayList<PeaceTreaty_DrawData>();
      this.provincesLeftToTake = new ArrayList<Integer>();
      this.iProvincesLeftToTakeSize = 0;
      this.scoreCountDefenders = false;
      this.iBrushCivID = -1;
      this.iPlayerTurnID = 0;
      this.iLastTakenID = -1;
      final List<Boolean> addDefender = new ArrayList<Boolean>();
      final List<Boolean> addAggressor = new ArrayList<Boolean>();
      for (int i = 0; i < CFG.game.getWar(iWarID).getDefendersSize(); ++i) {
         addDefender.add(true);
      }
      for (int i = 0; i < CFG.game.getWar(iWarID).getAggressorsSize(); ++i) {
         addAggressor.add(true);
      }

      this.initPeaceTreatyData(iWarID, addDefender, addAggressor, scoreCountDefenders);
   }

   protected PeaceTreaty_Data(final int iWarID, final List<Boolean> addDefender, final List<Boolean> addAggressor, final boolean scoreCountDefenders) {
      super();
      this.peaceTreatyGameData = new PeaceTreaty_GameData();
      this.drawProvinceOwners = new ArrayList<PeaceTreaty_DrawData>();
      this.provincesLeftToTake = new ArrayList<Integer>();
      this.iProvincesLeftToTakeSize = 0;
      this.scoreCountDefenders = false;
      this.iBrushCivID = -1;
      this.iPlayerTurnID = 0;
      this.iLastTakenID = -1;
      this.initPeaceTreatyData(iWarID, addDefender, addAggressor, scoreCountDefenders);
   }

   protected final void AI_UseVictoryPoints() {
      try {
         Gdx.app.log("AoC", "AI_UseVictoryPoints -> provincesLeftToTake.size: " + this.iProvincesLeftToTakeSize);
         if (this.iProvincesLeftToTakeSize > 0) {
            int iBestCivID = -1;
            int tBestPoints = -1;
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft > tBestPoints) {
                  iBestCivID = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID;
                  tBestPoints = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft;
               }
               else if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft == tBestPoints && CFG.oR.nextInt(100) < 50) {
                  iBestCivID = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID;
                  tBestPoints = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft;
               }
            }
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft > tBestPoints) {
                  iBestCivID = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID;
                  tBestPoints = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft;
               }
               else if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft == tBestPoints && CFG.oR.nextInt(100) < 50) {
                  iBestCivID = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID;
                  tBestPoints = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft;
               }
            }
            Gdx.app.log("AoC", "AI_UseVictoryPoints -> iBestCivID: " + iBestCivID + ((iBestCivID >= 0) ? (", " + CFG.game.getCiv(iBestCivID).getCivName()) : "") + ", tBestPoints: " + tBestPoints);
            //if optimal civ to take optimal province, and civ both not controlled by player, make AI choose provinces
            //if player led peace conferences, don't let AI take provinces change//
            if (iBestCivID > 0 && tBestPoints > 0 && !CFG.game.getCiv(iBestCivID).getControlledByPlayer() && (!this.isPlayerInvolved())) {
               if (recursionDepth++ > MAX_RECURSION_DEPTH) {
                  Gdx.app.log("AoC", "AI_UseVictoryPoints: Max recursion depth reached, aborting.");
                  recursionDepth = 0;
                  return;
               }
               Gdx.app.log("AoC", "AI_UseVictoryPoints -> AI TAKE PROVINCE");
               recursionDepth++;
               CFG.toast.setInView("Calculating Peace Treaty for " + CFG.game.getCiv(iBestCivID).getCivName() + ", " + recursionDepth);
               this.AI_UseVictoryPoints_CivID(iBestCivID, tBestPoints);
            }
         }
      }
      catch (final GdxRuntimeException stackOverflowError) {}
   }

   protected final void AI_UseVictoryPoints_CivID_TakeVassal(final int nCivID, final int pointsLeft, final boolean clearPoints) {
      try {
         final List<Integer> canVassalizeCivs = new ArrayList<Integer>();
         boolean doneCheck = false;
         for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
            if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
               for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++o) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(o).iWillBecomeVassalOfCivID < 0 && this.getVassalization_Cost(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(o).iCivID) <= pointsLeft) {
                     canVassalizeCivs.add(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(o).iCivID);
                  }
               }
               doneCheck = true;
               break;
            }
         }
         if (!doneCheck) {
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
                  for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++o) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(o).iWillBecomeVassalOfCivID < 0 && this.getVassalization_Cost(this.peaceTreatyGameData.lCivsDemands_Defenders.get(o).iCivID) <= pointsLeft) {
                        canVassalizeCivs.add(this.peaceTreatyGameData.lCivsDemands_Defenders.get(o).iCivID);
                     }
                  }
                  doneCheck = true;
                  break;
               }
            }
         }
         if (canVassalizeCivs.size() > 0) {
            this.takeVassalize(canVassalizeCivs.get(CFG.oR.nextInt(canVassalizeCivs.size())), nCivID, nCivID);
            this.AI_UseVictoryPoints();
            return;
         }
         if (clearPoints) {
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft = 0;
                  this.AI_UseVictoryPoints();
                  return;
               }
            }
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft = 0;
                  this.AI_UseVictoryPoints();
                  return;
               }
            }
         }
      }
      catch (final StackOverflowError stackOverflowError) {}
   }

   protected final void AI_UseVictoryPoints_CivID(final int nCivID, final int pointsLeft) {
      try {
         final int provincesSize = this.iProvincesLeftToTakeSize;
         final int[] toTakeArr = new int[provincesSize];
         final float[] lScoresArr = new float[provincesSize];
         final boolean[] lNeighArr = new boolean[provincesSize];
         int toTakeCount = 0;
         boolean canTakeNieghProvince = false;
         float maxDistance = 1.0E-4f;
         // Precompute province values and neighbors
         for (int i = 0; i < provincesSize; ++i) {
            int provinceIdx = this.provincesLeftToTake.get(i);
            PeaceTreaty_DrawData provinceOwner = this.drawProvinceOwners.get(provinceIdx);
            if (pointsLeft >= provinceOwner.iProvinceValue) {
               float dist = CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(nCivID).getCapitalProvinceID(), provinceIdx);
               if (dist > maxDistance) maxDistance = dist;
               if (CFG.game.getProvince(provinceIdx).getTrueOwnerOfProvince() == nCivID) {
                  lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 10.0f);
                  lNeighArr[toTakeCount] = true;
                  toTakeArr[toTakeCount++] = provinceIdx;
                  canTakeNieghProvince = true;
               } else {
                  boolean tempProvinceAdded = false;
                  int neighSize = CFG.game.getProvince(provinceIdx).getNeighboringProvincesSize();
                  for (int j = 0; j < neighSize; ++j) {
                     int neighIdx = CFG.game.getProvince(provinceIdx).getNeighboringProvinces(j);
                     if (this.drawProvinceOwners.get(neighIdx).iCivID == nCivID) {
                        if (CFG.game.getProvince(provinceIdx).getCore().getHaveACore(nCivID)) {
                           tempProvinceAdded = true;
                           lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 5.0f);
                           lNeighArr[toTakeCount] = true;
                           toTakeArr[toTakeCount++] = provinceIdx;
                           canTakeNieghProvince = true;
                           break;
                        }
                        lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 4.25f);
                        lNeighArr[toTakeCount] = true;
                        toTakeArr[toTakeCount++] = provinceIdx;
                        tempProvinceAdded = true;
                        canTakeNieghProvince = true;
                        break;
                     }
                  }
                  if (!tempProvinceAdded) {
                     if (CFG.game.getProvince(provinceIdx).getCore().getHaveACore(nCivID)) {
                        lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 1.75f);
                        lNeighArr[toTakeCount] = true;
                        toTakeArr[toTakeCount++] = provinceIdx;
                        canTakeNieghProvince = true;
                     }
                     else if (CFG.game.getProvince(provinceIdx).getNeighboringSeaProvincesSize() > 0) {
                        lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 0.325f);
                        lNeighArr[toTakeCount] = true;
                        toTakeArr[toTakeCount++] = provinceIdx;
                        canTakeNieghProvince = true;
                     }
                     else {
                        lScoresArr[toTakeCount] = this.AI_UseVictoryPoints_CivID_Score(nCivID, provinceIdx, 0.025f);
                        lNeighArr[toTakeCount] = false;
                        toTakeArr[toTakeCount++] = provinceIdx;
                     }
                  }
               }
            }
         }
         if (toTakeCount == 0) {
            Gdx.app.log("AoC", "AI_UseVictoryPoints -> AI TAKE PROVINCE -> lNeigh.size(): 0");
            this.AI_UseVictoryPoints_CivID_TakeVassal(nCivID, pointsLeft, true);
            return;
         }
         boolean hasNeigh = false;
         for (int i = 0; i < toTakeCount; ++i) {
            if (lNeighArr[i]) {
               hasNeigh = true;
               break;
            }
         }
         if (!hasNeigh) {
            Gdx.app.log("AoC", "AI_UseVictoryPoints -> AI TAKE PROVINCE -> canTakeNieghProvince: false");
            this.AI_UseVictoryPoints_CivID_TakeVassal(nCivID, pointsLeft, true);
            return;
         }
         // Score adjustment
         for (int k = 0; k < toTakeCount; ++k) {
            float dist = CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(nCivID).getCapitalProvinceID(), toTakeArr[k]);
            float distFactor = (0.8f + 0.2f * (1.0f - dist / maxDistance));
            float lastTakenFactor = (this.iLastTakenID == toTakeArr[k]) ? 0.05f : 1.0f;
            lScoresArr[k] = lScoresArr[k] * distFactor * lastTakenFactor;
         }
         // Find best
         int tBest = 0;
         float bestScore = lScoresArr[0];
         for (int k = 1; k < toTakeCount; ++k) {
            if (lScoresArr[k] > bestScore) {
               tBest = k;
               bestScore = lScoresArr[k];
            }
         }
         boolean tookProvince = false;
         if (lNeighArr[tBest]) {
            tookProvince = this.takeProvince(toTakeArr[tBest], nCivID, nCivID);
         } else if (toTakeCount == 1) {
            tookProvince = this.takeProvince(toTakeArr[tBest], nCivID, nCivID);
         }
         if (!tookProvince) {
            int i;
            boolean found = false;
            for (i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft = 0;
                  this.AI_UseVictoryPoints();
                  found = true;
                  break;
               }
            }
            if (!found) {
               for (i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft = 0;
                     this.AI_UseVictoryPoints();
                     break;
                  }
               }
            }
         }
      }
      catch (final StackOverflowError stackOverflowError) {}
   }

   protected final float AI_UseVictoryPoints_CivID_Score(final int nCivID, final int nProvinceID, final float modifier) {
      int neigh_OwnProvinces = 0;
      int neigh_OtherCivsProvinces = 0;
      for (int i = 0; i < CFG.game.getProvince(nProvinceID).getNeighboringProvincesSize(); ++i) {
         if (!CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getSeaProvince() && CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).getCivID() > 0) {
            if (this.drawProvinceOwners.get(CFG.game.getProvince(nProvinceID).getNeighboringProvinces(i)).iCivID == nCivID) {
               ++neigh_OwnProvinces;
            }
            else {
               ++neigh_OtherCivsProvinces;
            }
         }
      }
      if (CFG.game.getProvince(nProvinceID).getNeighboringSeaProvincesSize() > 0) {
         ++neigh_OwnProvinces;
      }
      neigh_OtherCivsProvinces = Math.max(1, neigh_OtherCivsProvinces);

      float numDistance = (CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(nCivID).getPuppetOfCivID(), nProvinceID));
      //float mediatedMapSize = (float)CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize());
      numDistance = (10.0F * (numDistance / (float)CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize())));

      return numDistance + modifier + (neigh_OwnProvinces * (modifier * 0.125f) + modifier * (neigh_OwnProvinces / (neigh_OwnProvinces + neigh_OtherCivsProvinces)) + 0.125f * CFG.game.getProvince(nProvinceID).getPopulationData().getPopulation() / CFG.game.getGameScenarios().getScenario_StartingPopulation() + 0.05f * CFG.game.getProvince(nProvinceID).getEconomy() / CFG.game.getGameScenarios().getScenario_StartingEconomy() + 0.0075f * CFG.game.getProvince(nProvinceID).getDevelopmentLevel());
   }

   protected final void addProvincesLeftToTake(final int nProvinceID) {
      Gdx.app.log("AoC", "addProvincesLeftToTake: nCivID: , nProvinceID: " + CFG.game.getProvince(nProvinceID).getName());
      for (int i = 0; i < this.iProvincesLeftToTakeSize; ++i) {
         if (this.provincesLeftToTake.get(i) == nProvinceID) {
            return;
         }
      }
      this.provincesLeftToTake.add(nProvinceID);
      this.iProvincesLeftToTakeSize = this.provincesLeftToTake.size();
   }

   protected final void removeProvincesLeftToTake(final int nProvinceID) {
      Gdx.app.log("AoC", "removeProvincesLeftToTake: nCivID: , nProvinceID: " + CFG.game.getProvince(nProvinceID).getName());
      for (int i = 0; i < this.iProvincesLeftToTakeSize; ++i) {
         if (this.provincesLeftToTake.get(i) == nProvinceID) {
            this.provincesLeftToTake.remove(i);
            this.iProvincesLeftToTakeSize = this.provincesLeftToTake.size();
            return;
         }
      }
   }

   private final void initPeaceTreatyData(final int iWarID, final List<Boolean> addDefender, final List<Boolean> addAggressor, final boolean scoreCountDefenders) {
      try {
         this.peaceTreatyGameData.iWarID = iWarID;
         this.peaceTreatyGameData.WAR_TAG = CFG.game.getWar(iWarID).WAR_TAG;
         this.scoreCountDefenders = scoreCountDefenders;
         this.iBrushCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
         this.iPlayerTurnID = CFG.PLAYER_TURNID;

         //give unlimited warscore and skip player addition
         if (this.isPlayerInvolved()) {
            //if player-led peace, give player infinite points on both sides
            //this.peaceTreatyGameData.lCivsData_Defenders.add(new PeaceTreaty_Civs(iBrushCivID));
            //this.peaceTreatyGameData.lCivsDemands_Defenders.add(new PeaceTreaty_Demands(iBrushCivID, 999999));
            //this.peaceTreatyGameData.lCivsData_Aggressors.add(new PeaceTreaty_Civs(iBrushCivID));
            //this.peaceTreatyGameData.lCivsDemands_Aggressors.add(new PeaceTreaty_Demands(iBrushCivID, 999999));
            boolean PLAYER_IN_WAR = false;

            for (int i = 0; i < CFG.game.getWar(iWarID).getDefendersSize(); ++i) {
               if (addDefender.get(i)) {
                  this.peaceTreatyGameData.lCivsData_Defenders.add(CFG.game.getWar(iWarID).getDefenders_ProvincesLost(i, addDefender, addAggressor));
                  this.peaceTreatyGameData.lCivsDemands_Defenders.add(new PeaceTreaty_Demands(CFG.game.getWar(iWarID).getDefenderID(i).getCivID(), 999999999));

                  //enable player bool
                  if (CFG.game.getWar(iWarID).getDefenderID(i).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) PLAYER_IN_WAR = true;
               }
            }
            for (int i = 0; i < CFG.game.getWar(iWarID).getAggressorsSize(); ++i) {
               if (addAggressor.get(i)) {
                  this.peaceTreatyGameData.lCivsData_Aggressors.add(CFG.game.getWar(iWarID).getAggressors_ProvincesLost(i, addDefender, addAggressor));
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.add(new PeaceTreaty_Demands(CFG.game.getWar(iWarID).getAggressorID(i).getCivID(), 999999999));

                  //enabe player bool
                  if (CFG.game.getWar(iWarID).getAggressorID(i).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) PLAYER_IN_WAR = true;
               }
            }

            //if player peace and player not in war, set brush-civ to first defender and refresh menus
            if (CFG.PLAYER_PEACE && !PLAYER_IN_WAR) {
               this.prepareProvinceData(true);
               CFG.peaceTreatyData = this;
               this.iBrushCivID = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(0).iCivID;
               CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
               CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
               return;
            }
         } else {
            for (int i = 0; i < CFG.game.getWar(iWarID).getDefendersSize(); ++i) {
               if (addDefender.get(i)) {
                  this.peaceTreatyGameData.lCivsData_Defenders.add(CFG.game.getWar(iWarID).getDefenders_ProvincesLost(i, addDefender, addAggressor));
                  this.peaceTreatyGameData.lCivsDemands_Defenders.add(new PeaceTreaty_Demands(CFG.game.getWar(iWarID).getDefenderID(i).getCivID(), CFG.game.getWar(iWarID).getWarScore_DefendersInProvinceValue_OnlyPositive(i, addDefender, addAggressor)));
               }
            }
            for (int i = 0; i < CFG.game.getWar(iWarID).getAggressorsSize(); ++i) {
               if (addAggressor.get(i)) {
                  this.peaceTreatyGameData.lCivsData_Aggressors.add(CFG.game.getWar(iWarID).getAggressors_ProvincesLost(i, addDefender, addAggressor));
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.add(new PeaceTreaty_Demands(CFG.game.getWar(iWarID).getAggressorID(i).getCivID(), CFG.game.getWar(iWarID).getWarScore_AggressorsInProvinceValue_OnlyPositive(i, addDefender, addAggressor)));
               }
            }

            this.iBrushCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
            this.iPlayerTurnID = CFG.PLAYER_TURNID;
         }
         this.prepareProvinceData(true);
      }
      catch (final IndexOutOfBoundsException ex) {
         CFG.exceptionStack(ex);
      }
   }

   protected final void prepareProvinceData(final boolean buildProvincesLost) {
      this.drawProvinceOwners.clear();
      this.drawProvinceOwners = new ArrayList<PeaceTreaty_DrawData>();


      final List<Boolean> tempParticipants = new ArrayList<Boolean>();
      for (int i = 0; i < CFG.game.getCivsSize(); ++i) {
         tempParticipants.add(false);
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsData_Defenders.size(); ++i) {
         //if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(this.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID) < 1) {
         //   //re-detect warid if not curciv not in war
         //   this.peaceTreatyGameData.iWarID = CFG.game.getWarID(this.peaceTreatyGameData.lCivsData_Aggressors.get(0).iCivID, this.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID);
         //}
         tempParticipants.set(this.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID, true);
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsData_Aggressors.size(); ++i) {
         //if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID) < 1) {
         //   //re-detect warid if not curciv not in war
         //   this.peaceTreatyGameData.iWarID = CFG.game.getWarID(this.peaceTreatyGameData.lCivsData_Defenders.get(0).iCivID, this.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID);
         //}
         tempParticipants.set(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID, true);
      }
      for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (tempParticipants.get(CFG.game.getProvince(i).getCivID())) {
            this.drawProvinceOwners.add(new PeaceTreaty_DrawData(CFG.game.getProvince(i).getCivID(), CFG.game.getProvinceValue(i), false));
         }
         else {
            this.drawProvinceOwners.add(new PeaceTreaty_DrawData(CFG.game.getProvince(i).getCivID() * -1, CFG.game.getProvinceValue(i), false));
         }
      }
      //add all provinces to be taken
      if (this.isPlayerInvolved()) {
         try {
            for (int i = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; i >= 0; --i) {
               //this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.clear();
               for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getNumOfProvinces(); ++j) {
                  //this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.add(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j));
                  this.drawProvinceOwners.get(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).isToTake = true;
                  this.drawProvinceOwners.get(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).iCivID = CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince();
                  final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                  peaceTreaty_Demands2.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j));
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }
         try {
            for (int i = this.peaceTreatyGameData.lCivsData_Aggressors.size() - 1; i >= 0; --i) {
               //this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.clear();
               for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getNumOfProvinces(); ++j) {
                  //this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.add(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j));
                  this.drawProvinceOwners.get(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).isToTake = true;
                  this.drawProvinceOwners.get(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).iCivID = CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getTrueOwnerOfProvince();
                  final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                  peaceTreaty_Demands2.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j));
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }

         for (int i = this.drawProvinceOwners.size() - 1; i >= 0; --i) {
            if (this.drawProvinceOwners.get(i).isToTake) {
               this.provincesLeftToTake.add(i);
            }
         }
         for (int i = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; i >= 0; --i) {
            this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs = new ArrayList<PeaceTreaty_ReleaseableVassals>();
            for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getNumOfProvinces(); ++j) {
               if (!CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).isOccupied()) {
                  for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivsSize(); ++u) {
                     if (CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u) != CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)).getNumOfProvinces() == 0) {
                        boolean tAdd = true;
                        for (int k = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                           if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)) {
                              tAdd = false;
                              this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).addProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j));
                              break;
                           }
                        }
                        if (tAdd) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u), CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)));
                        }
                     }
                  }
               }
            }
            for (int o = this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.size() - 1; o >= 0; --o) {
               for (int l = 0; l < CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivsSize(); ++l) {
                  if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l) != CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l)).getNumOfProvinces() == 0) {
                     boolean tAdd = true;
                     for (int k = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l)) {
                           tAdd = false;
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).addProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o));
                           break;
                        }
                     }
                     if (tAdd) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l), this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)));
                     }
                  }
               }
            }
         }
         for (int i = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; i >= 0; --i) {
            this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs = new ArrayList<PeaceTreaty_ReleaseableVassals>();
            for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getNumOfProvinces(); ++j) {
               if (!CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).isOccupied()) {
                  for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivsSize(); ++u) {
                     if (CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u) != CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)).getNumOfProvinces() == 0) {
                        boolean tAdd = true;
                        for (int k = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                           if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)) {
                              tAdd = false;
                              this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).addProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j));
                              break;
                           }
                        }
                        if (tAdd) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u), CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)));
                        }
                     }
                  }
               }
            }
            for (int o = this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.size() - 1; o >= 0; --o) {
               for (int l = 0; l < CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivsSize(); ++l) {
                  if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l) != CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l)).getNumOfProvinces() == 0) {
                     boolean tAdd = true;
                     for (int k = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l)) {
                           tAdd = false;
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).addProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o));
                           break;
                        }
                     }
                     if (tAdd) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l), this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)));
                     }
                  }
               }
            }
         }
         for (int i = this.drawProvinceOwners.size() - 1; i >= 0; --i) {
            if (this.drawProvinceOwners.get(i).isToTake) {
               this.provincesLeftToTake.add(i);
            }
         }
         this.iProvincesLeftToTakeSize = this.provincesLeftToTake.size();
      } else if (buildProvincesLost) {
         try {
            for (int i = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; i >= 0; --i) {
               for (int j = this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.size() - 1; j >= 0; --j) {
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j)).isToTake = true;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j)).getTrueOwnerOfProvince();
                  final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                  peaceTreaty_Demands.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j));
               }
               for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getNumOfProvinces(); ++j) {
                  final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                  peaceTreaty_Demands2.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j));
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }
         try {
            for (int i = this.peaceTreatyGameData.lCivsData_Aggressors.size() - 1; i >= 0; --i) {
               for (int j = this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.size() - 1; j >= 0; --j) {
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j)).isToTake = true;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j)).getTrueOwnerOfProvince();
                  final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                  peaceTreaty_Demands3.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j));
               }
               for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getNumOfProvinces(); ++j) {
                  final PeaceTreaty_Demands peaceTreaty_Demands4 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                  peaceTreaty_Demands4.iTotalNumOfVicotryPoints += CFG.game.getProvinceValue(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j));
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }
         for (int i = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; i >= 0; --i) {
            this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs = new ArrayList<PeaceTreaty_ReleaseableVassals>();
            for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getNumOfProvinces(); ++j) {
               if (!CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).isOccupied()) {
                  for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivsSize(); ++u) {
                     if (CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u) != CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)).getNumOfProvinces() == 0) {
                        boolean tAdd = true;
                        for (int k = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                           if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)) {
                              tAdd = false;
                              this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).addProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j));
                              break;
                           }
                        }
                        if (tAdd) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u), CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID).getProvinceID(j)));
                        }
                     }
                  }
               }
            }
            for (int o = this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.size() - 1; o >= 0; --o) {
               for (int l = 0; l < CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivsSize(); ++l) {
                  if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l) != CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l)).getNumOfProvinces() == 0) {
                     boolean tAdd = true;
                     for (int k = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l)) {
                           tAdd = false;
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(k).addProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o));
                           break;
                        }
                     }
                     if (tAdd) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)).getCore().getCivID(l), this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(o)));
                     }
                  }
               }
            }
         }
         for (int i = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; i >= 0; --i) {
            this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs = new ArrayList<PeaceTreaty_ReleaseableVassals>();
            for (int j = 0; j < CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getNumOfProvinces(); ++j) {
               if (!CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).isOccupied()) {
                  for (int u = 0; u < CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivsSize(); ++u) {
                     if (CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u) != CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)).getNumOfProvinces() == 0) {
                        boolean tAdd = true;
                        for (int k = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                           if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u)) {
                              tAdd = false;
                              this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).addProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j));
                              break;
                           }
                        }
                        if (tAdd) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)).getCore().getCivID(u), CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID).getProvinceID(j)));
                        }
                     }
                  }
               }
            }
            for (int o = this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.size() - 1; o >= 0; --o) {
               for (int l = 0; l < CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivsSize(); ++l) {
                  if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l) != CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCivID() && CFG.game.getCiv(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l)).getNumOfProvinces() == 0) {
                     boolean tAdd = true;
                     for (int k = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.size() - 1; k >= 0; --k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).iCivID == CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l)) {
                           tAdd = false;
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(k).addProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o));
                           break;
                        }
                     }
                     if (tAdd) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.add(new PeaceTreaty_ReleaseableVassals(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)).getCore().getCivID(l), this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(o)));
                     }
                  }
               }
            }
         }
         for (int i = this.drawProvinceOwners.size() - 1; i >= 0; --i) {
            if (this.drawProvinceOwners.get(i).isToTake) {
               this.provincesLeftToTake.add(i);
            }
         }
         this.iProvincesLeftToTakeSize = this.provincesLeftToTake.size();
      }
      else {
         try {
            final List<Boolean> addDefender = new ArrayList<Boolean>();
            final List<Boolean> addAggressor = new ArrayList<Boolean>();
            for (int m = 0; m < CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefendersSize(); ++m) {
               boolean addCiv = false;
               for (int j2 = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; j2 >= 0; --j2) {
                  if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID(m).getCivID() == this.peaceTreatyGameData.lCivsData_Defenders.get(j2).iCivID) {
                     addCiv = true;
                     break;
                  }
               }
               addDefender.add(addCiv);
            }
            for (int m = 0; m < CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorsSize(); ++m) {
               boolean addCiv = false;
               for (int j2 = this.peaceTreatyGameData.lCivsData_Aggressors.size() - 1; j2 >= 0; --j2) {
                  if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID(m).getCivID() == this.peaceTreatyGameData.lCivsData_Aggressors.get(j2).iCivID) {
                     addCiv = true;
                     break;
                  }
               }
               addAggressor.add(addCiv);
            }
            for (int m = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; m >= 0; --m) {
               try {
                  final PeaceTreaty_Civs tempLost = CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenders_ProvincesLost(CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(this.peaceTreatyGameData.lCivsData_Defenders.get(m).iCivID), addDefender, addAggressor);
                  for (int j2 = tempLost.lProvincesLost.size() - 1; j2 >= 0; --j2) {
                     boolean isAdded = false;
                     for (int k2 = this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.size() - 1; k2 >= 0; --k2) {
                        if (tempLost.lProvincesLost.get(j2).equals(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(k2))) {
                           isAdded = true;
                           break;
                        }
                     }
                     if (!isAdded) {
                        this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.add(tempLost.lProvincesLost.get(j2));
                        this.makeDemand_Province(tempLost.lProvincesLost.get(j2), this.peaceTreatyGameData.lCivsData_Defenders.get(m).iCivID, this.peaceTreatyGameData.lCivsData_Defenders.get(m).iCivID, true);
                     }
                     tempLost.lProvincesLost.remove(j2);
                  }
               } catch (IndexOutOfBoundsException ex) {
               }
            }
            for (int m = 0; m < this.peaceTreatyGameData.lCivsData_Aggressors.size(); ++m) {
               try {
                  final PeaceTreaty_Civs tempLost = CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressors_ProvincesLost(CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).iCivID), addDefender, addAggressor);
                  for (int j2 = tempLost.lProvincesLost.size() - 1; j2 >= 0; --j2) {
                     boolean isAdded = false;
                     for (int k2 = this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.size() - 1; k2 >= 0; --k2) {
                        if (tempLost.lProvincesLost.get(j2).equals(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(k2))) {
                           isAdded = true;
                           break;
                        }
                     }
                     if (!isAdded) {
                        this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.add(tempLost.lProvincesLost.get(j2));
                        this.makeDemand_Province(tempLost.lProvincesLost.get(j2), this.peaceTreatyGameData.lCivsData_Aggressors.get(m).iCivID, this.peaceTreatyGameData.lCivsData_Aggressors.get(m).iCivID, true);
                     }
                     tempLost.lProvincesLost.remove(j2);
                  }
               } catch (IndexOutOfBoundsException ex) {
               }
            }
            for (int m = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; m >= 0; --m) {
               if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsAggressor(this.peaceTreatyGameData.lCivsData_Defenders.get(m).iCivID) || CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsDefender(this.peaceTreatyGameData.lCivsData_Defenders.get(m).iCivID)) {
                  for (int j3 = this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.size() - 1; j3 >= 0; --j3) {
                     if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).isOccupied()) {
                        if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsDefender(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).getCivID()) || CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsAggressor(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).getCivID())) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).isToTake = true;
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)).getTrueOwnerOfProvince();
                        }
                        else {
                           boolean removed = false;
                           for (int k3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; k3 >= 0; --k3) {
                              for (int o2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)) {
                                    this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.remove(o2);
                                    k3 = -1;
                                    removed = true;
                                    break;
                                 }
                              }
                           }
                           if (!removed) {
                              for (int k3 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; k3 >= 0; --k3) {
                                 for (int o2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                    if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)) {
                                       this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.remove(o2);
                                       k3 = -1;
                                       removed = true;
                                       break;
                                    }
                                 }
                              }
                           }
                           this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.remove(j3);
                        }
                     }
                     else {
                        boolean removed = false;
                        for (int k3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; k3 >= 0; --k3) {
                           for (int o2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                              if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)) {
                                 this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.remove(o2);
                                 k3 = -1;
                                 removed = true;
                                 break;
                              }
                           }
                        }
                        if (!removed) {
                           for (int k3 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; k3 >= 0; --k3) {
                              for (int o2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.get(j3)) {
                                    this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.remove(o2);
                                    k3 = -1;
                                    removed = true;
                                    break;
                                 }
                              }
                           }
                        }
                        this.peaceTreatyGameData.lCivsData_Defenders.get(m).lProvincesLost.remove(j3);
                     }
                  }
               }
               else {
                  this.peaceTreatyGameData.lCivsData_Defenders.remove(m);
                  this.peaceTreatyGameData.lCivsDemands_Defenders.remove(m);
               }
            }
            for (int m = this.peaceTreatyGameData.lCivsData_Aggressors.size() - 1; m >= 0; --m) {
               if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsAggressor(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).iCivID) || CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsDefender(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).iCivID)) {
                  for (int j3 = this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.size() - 1; j3 >= 0; --j3) {
                     if (CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).isOccupied()) {
                        if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsDefender(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).getCivID()) || CFG.game.getWar(this.peaceTreatyGameData.iWarID).getIsAggressor(CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).getCivID())) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).isToTake = true;
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)).getTrueOwnerOfProvince();
                        }
                        else {
                           boolean removed = false;
                           for (int k3 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; k3 >= 0; --k3) {
                              for (int o2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)) {
                                    this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.remove(o2);
                                    k3 = -1;
                                    removed = true;
                                    break;
                                 }
                              }
                           }
                           if (!removed) {
                              for (int k3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; k3 >= 0; --k3) {
                                 for (int o2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                    if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)) {
                                       this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.remove(o2);
                                       k3 = -1;
                                       removed = true;
                                       break;
                                    }
                                 }
                              }
                           }
                           this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.remove(j3);
                        }
                     }
                     else {
                        boolean removed = false;
                        for (int k3 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; k3 >= 0; --k3) {
                           for (int o2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                              if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)) {
                                 this.peaceTreatyGameData.lCivsDemands_Defenders.get(k3).lDemands.remove(o2);
                                 k3 = -1;
                                 removed = true;
                                 break;
                              }
                           }
                        }
                        if (!removed) {
                           for (int k3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; k3 >= 0; --k3) {
                              for (int o2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.size() - 1; o2 >= 0; --o2) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.get(o2) == this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.get(j3)) {
                                    this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k3).lDemands.remove(o2);
                                    k3 = -1;
                                    removed = true;
                                    break;
                                 }
                              }
                           }
                        }
                        this.peaceTreatyGameData.lCivsData_Aggressors.get(m).lProvincesLost.remove(j3);
                     }
                  }
               }
               else {
                  this.peaceTreatyGameData.lCivsData_Aggressors.remove(m);
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.remove(m);
               }
            }
            for (int m = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; m >= 0; --m) {
               for (int j3 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(m).lDemands.size() - 1; j3 >= 0; --j3) {
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(m).lDemands.get(j3)).isTaken = this.peaceTreatyGameData.lCivsDemands_Defenders.get(m).iCivID;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(m).lDemands.get(j3)).iCivID = this.peaceTreatyGameData.lCivsDemands_Defenders.get(m).iCivID;
               }
            }
            for (int m = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; m >= 0; --m) {
               for (int j3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(m).lDemands.size() - 1; j3 >= 0; --j3) {
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(m).lDemands.get(j3)).isTaken = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(m).iCivID;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(m).lDemands.get(j3)).iCivID = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(m).iCivID;
               }
            }
         }
         catch (final IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
         }
      }
   }

   protected final void prepareDemansVassalsData() {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         for (int j = 0; j < this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.size(); ++j) {
            for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iFromCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                  for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.size(); ++o) {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).iCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) {
                        for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.size(); ++u) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(o).lProvinces.get(u)).iCivID = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID;
                        }
                     }
                  }
               }
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         for (int j = 0; j < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.size(); ++j) {
            for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iFromCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
                  for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.size(); ++o) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID) {
                        for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.size(); ++u) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(o).lProvinces.get(u)).iCivID = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs_TakeControl.get(j).iVassalCivID;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected final int takeReleaseVassal(final int iFromCivID, final int nReleaseCivID, final int nCivID, final int pointsUsedByCivID) {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == iFromCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Aggressors.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iCivID == nCivID) {
                  int nID = -1;
                  for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.size(); ++o) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(o).iCivID == nReleaseCivID) {
                        nID = o;
                        break;
                     }
                  }
                  if (nID < 0) {
                     i = this.peaceTreatyGameData.lCivsDemands_Defenders.size();
                     break;
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).iReleasesToCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).iReleasesToCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).iReleasesToCivID = -1;
                        final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                        peaceTreaty_Demands.iVictoryPointsLeft += this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).getScoreValue();
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).removeReleaseVassal_TakeControl(iFromCivID, nReleaseCivID);
                        for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.size(); ++k) {
                           if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isToTake) {
                              if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken > 0) {
                                 if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken != this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iCivID) {
                                    for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++u) {
                                       if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                          this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                       }
                                       if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                          final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u);
                                          peaceTreaty_Demands2.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                       }
                                    }
                                    for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++u) {
                                       if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                          this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                       }
                                       if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                          final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(u);
                                          peaceTreaty_Demands3.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                       }
                                    }
                                    final PeaceTreaty_Demands peaceTreaty_Demands4 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                                    peaceTreaty_Demands4.iVictoryPointsLeft -= this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              else {
                                 final PeaceTreaty_Demands peaceTreaty_Demands5 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                                 peaceTreaty_Demands5.iVictoryPointsLeft -= this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                              }
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = -1;
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getCivID();
                           }
                           else {
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getCivID();
                           }
                        }
                        return 0;
                     }
                     return this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).iReleasesToCivID;
                  }
                  else {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iVictoryPointsLeft < this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).getScoreValue()) {
                        return 0;
                     }
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).iReleasesToCivID = nCivID;
                     final PeaceTreaty_Demands peaceTreaty_Demands6 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                     peaceTreaty_Demands6.iVictoryPointsLeft -= this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).getScoreValue();
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).addReleaseVassal_TakeControl(iFromCivID, nReleaseCivID);
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.size(); ++k) {
                        if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isToTake) {
                           if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken > 0) {
                              for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++u) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                    this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                 }
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                    final PeaceTreaty_Demands peaceTreaty_Demands7 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                                    peaceTreaty_Demands7.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++u) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                    this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                 }
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                    final PeaceTreaty_Demands peaceTreaty_Demands8 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                                    peaceTreaty_Demands8.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = -1;
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getTrueOwnerOfProvince();
                           }
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = nCivID;
                        }
                        this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = nReleaseCivID;
                     }
                     return nCivID;
                  }
               }
               else {
                  ++j;
               }
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == iFromCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Defenders.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iCivID == nCivID) {
                  int nID = -1;
                  for (int o = 0; o < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.size(); ++o) {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(o).iCivID == nReleaseCivID) {
                        nID = o;
                        break;
                     }
                  }
                  if (nID < 0) {
                     i = this.peaceTreatyGameData.lCivsDemands_Aggressors.size();
                     break;
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).iReleasesToCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).iReleasesToCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).iReleasesToCivID = -1;
                        final PeaceTreaty_Demands peaceTreaty_Demands9 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                        peaceTreaty_Demands9.iVictoryPointsLeft += this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).getScoreValue();
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).removeReleaseVassal_TakeControl(iFromCivID, nReleaseCivID);
                        for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.size(); ++k) {
                           if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isToTake) {
                              if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken > 0) {
                                 if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken != this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iCivID) {
                                    for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++u) {
                                       if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                          this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                       }
                                       if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                          final PeaceTreaty_Demands peaceTreaty_Demands10 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(u);
                                          peaceTreaty_Demands10.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                       }
                                    }
                                    for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++u) {
                                       if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                          this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                       }
                                       if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                          final PeaceTreaty_Demands peaceTreaty_Demands11 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(u);
                                          peaceTreaty_Demands11.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                       }
                                    }
                                    final PeaceTreaty_Demands peaceTreaty_Demands12 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                                    peaceTreaty_Demands12.iVictoryPointsLeft -= this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              else {
                                 final PeaceTreaty_Demands peaceTreaty_Demands13 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                                 peaceTreaty_Demands13.iVictoryPointsLeft -= this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                              }
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = -1;
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getCivID();
                           }
                           else {
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getCivID();
                           }
                        }
                        return 0;
                     }
                     return this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).iReleasesToCivID;
                  }
                  else {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).getScoreValue() > this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iVictoryPointsLeft) {
                        return 0;
                     }
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).iReleasesToCivID = nCivID;
                     final PeaceTreaty_Demands peaceTreaty_Demands14 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                     peaceTreaty_Demands14.iVictoryPointsLeft -= this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).getScoreValue();
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).addReleaseVassal_TakeControl(iFromCivID, nReleaseCivID);
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.size(); ++k) {
                        if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isToTake) {
                           if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken > 0) {
                              for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++u) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                    this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                 }
                                 if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                    final PeaceTreaty_Demands peaceTreaty_Demands15 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                                    peaceTreaty_Demands15.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              for (int u = 0; u < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++u) {
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID) {
                                    this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k));
                                 }
                                 if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken) {
                                    final PeaceTreaty_Demands peaceTreaty_Demands16 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                                    peaceTreaty_Demands16.iVictoryPointsLeft += this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iProvinceValue;
                                 }
                              }
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = -1;
                              this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).getTrueOwnerOfProvince();
                           }
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).isTaken = nCivID;
                        }
                        this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).lReleasableCivs.get(nID).lProvinces.get(k)).iCivID = nReleaseCivID;
                     }
                     return nCivID;
                  }
               }
               else {
                  ++j;
               }
            }
         }
      }
      return pointsUsedByCivID;
   }

   protected final int getVassalization_Cost(final int nCivID) {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
            return (int)Math.max(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iTotalNumOfVicotryPoints * 0.4f, 1.0f);
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
            return (int)Math.max(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iTotalNumOfVicotryPoints * 0.4f, 1.0f);
         }
      }
      return 1;
   }

   protected final int takeVassalize(final int nVasslizeCivID, int nCivID, final int pointsUsedByCivID) {
      /*Gdx.app.log("FUCK THIs", CFG.game.getCiv(nCivID).getCivName());
      boolean remove = false;
      boolean found = false;

      if (CFG.PLAYER_PEACE || (CFG.SPECTATOR_MODE && CFG.sandbox_task == Menu.eINGAME_PEACE_TREATY)) {
         for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
            if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID != -1) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID == nCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = -1;
                  remove = true;
                  break;
               }
            } else if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nVasslizeCivID) {
               this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = nCivID;
               found = true;
               break;
            }
         }
         if (!(found || remove)) {
            for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID != -1) {
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID == nCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = -1;
                     remove = true;
                     break;
                  }
               } else if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nVasslizeCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID = nCivID;
                  found = true;
                  break;
               }
            }
         }

         if (!(found || remove)) {
            return 0;
         }

         int j = 0;
         while (j < this.peaceTreatyGameData.lCivsDemands_Aggressors.size()) {
            if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iCivID == nCivID) {
               if (remove) {
                  final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                  peaceTreaty_Demands.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).removeWillVassalizeCivID(nVasslizeCivID);
                  if (CFG.menuManager.getInGame_PeaceTreaty()) {
                     if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                     }
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                  }
                  return 0;
               }

               final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
               peaceTreaty_Demands3.iVictoryPointsLeft -= this.getVassalization_Cost(nVasslizeCivID);
               this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).addWillVassalizeCivID(nVasslizeCivID);
               if (CFG.menuManager.getInGame_PeaceTreaty()) {
                  if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                  }
                  CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
               }
               return nCivID;
            } else {
               ++j;
            }
         }
         j = 0;
         while (j < this.peaceTreatyGameData.lCivsDemands_Defenders.size()) {
            if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iCivID == nCivID) {
               if (remove) {
                  final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                  peaceTreaty_Demands.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).removeWillVassalizeCivID(nVasslizeCivID);
                  if (CFG.menuManager.getInGame_PeaceTreaty()) {
                     if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                     }
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                  }
                  return 0;
               }

               final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
               peaceTreaty_Demands3.iVictoryPointsLeft -= this.getVassalization_Cost(nVasslizeCivID);
               this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).addWillVassalizeCivID(nVasslizeCivID);
               if (CFG.menuManager.getInGame_PeaceTreaty()) {
                  if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                  }
                  CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
               }
               return nCivID;
            } else {
               ++j;
            }
         }
         return 0;
      }*/

      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nVasslizeCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Aggressors.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iCivID == nCivID) {
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = 0;
                        final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                        peaceTreaty_Demands.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).removeWillVassalizeCivID(nVasslizeCivID);
                        if (CFG.menuManager.getInGame_PeaceTreaty()) {
                           if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                              CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                           }
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                        }
                        return 0;
                     }
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iVictoryPointsLeft < this.getVassalization_Cost(nVasslizeCivID)) {
                        if (CFG.menuManager.getInGame_PeaceTreaty()) {
                           if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                              CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                           }
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                        }
                        return 0;
                     }
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = 0;
                           final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k);
                           peaceTreaty_Demands2.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).removeWillVassalizeCivID(nVasslizeCivID);
                           break;
                        }
                     }
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iVictoryPointsLeft < this.getVassalization_Cost(nVasslizeCivID)) {
                     if (CFG.menuManager.getInGame_PeaceTreaty()) {
                        if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                        }
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                     }
                     return 0;
                  }
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iWillBecomeVassalOfCivID = nCivID;
                  final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                  peaceTreaty_Demands3.iVictoryPointsLeft -= this.getVassalization_Cost(nVasslizeCivID);
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).addWillVassalizeCivID(nVasslizeCivID);
                  if (CFG.menuManager.getInGame_PeaceTreaty()) {
                     if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                     }
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                  }
                  return nCivID;
               }
               else {
                  ++j;
               }
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nVasslizeCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Defenders.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iCivID == nCivID) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID = 0;
                        final PeaceTreaty_Demands peaceTreaty_Demands4 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                        peaceTreaty_Demands4.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).removeWillVassalizeCivID(nVasslizeCivID);
                        if (CFG.menuManager.getInGame_PeaceTreaty()) {
                           if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                              CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                           }
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                        }
                        return 0;
                     }
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iVictoryPointsLeft < this.getVassalization_Cost(nVasslizeCivID)) {
                        if (CFG.menuManager.getInGame_PeaceTreaty()) {
                           if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                              CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                           }
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                        }
                        return 0;
                     }
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID = 0;
                           final PeaceTreaty_Demands peaceTreaty_Demands5 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k);
                           peaceTreaty_Demands5.iVictoryPointsLeft += this.getVassalization_Cost(nVasslizeCivID);
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).removeWillVassalizeCivID(nVasslizeCivID);
                           break;
                        }
                     }
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iVictoryPointsLeft < this.getVassalization_Cost(nVasslizeCivID)) {
                     if (CFG.menuManager.getInGame_PeaceTreaty()) {
                        if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                           CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                        }
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                     }
                     return 0;
                  }
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iWillBecomeVassalOfCivID = nCivID;
                  final PeaceTreaty_Demands peaceTreaty_Demands6 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                  peaceTreaty_Demands6.iVictoryPointsLeft -= this.getVassalization_Cost(nVasslizeCivID);
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).addWillVassalizeCivID(nVasslizeCivID);
                  if (CFG.menuManager.getInGame_PeaceTreaty()) {
                     if (!CFG.game.getCiv(nCivID).getControlledByPlayer()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Provinces();
                     }
                     CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                  }
                  return nCivID;
               }
               else {
                  ++j;
               }
            }
         }
      }
      return pointsUsedByCivID;
   }

   protected final int getWarReparation_Cost(final int nCivID) {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
            return (int)Math.max(this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iTotalNumOfVicotryPoints * 0.1f, 1.0f);
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
            return (int)Math.max(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iTotalNumOfVicotryPoints * 0.1f, 1.0f);
         }
      }
      return 1;
   }

   protected final int takeWarReparations(final int nWarRepartionsFromCivID, final int nCivID, final int pointsUsedByCivID) {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nWarRepartionsFromCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Aggressors.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iCivID == nCivID) {
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID = 0;
                        final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                        peaceTreaty_Demands.iVictoryPointsLeft += this.getWarReparation_Cost(nWarRepartionsFromCivID);
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).removeWarReparationsFromCivID(nWarRepartionsFromCivID);
                        return 0;
                     }
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iVictoryPointsLeft < this.getWarReparation_Cost(nWarRepartionsFromCivID)) {
                        return 0;
                     }
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID = 0;
                           final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k);
                           peaceTreaty_Demands2.iVictoryPointsLeft += this.getWarReparation_Cost(nWarRepartionsFromCivID);
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).removeWarReparationsFromCivID(nWarRepartionsFromCivID);
                           break;
                        }
                     }
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).iVictoryPointsLeft < this.getWarReparation_Cost(nWarRepartionsFromCivID)) {
                     return 0;
                  }
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iPaysWarReparationsToCivID = nCivID;
                  final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j);
                  peaceTreaty_Demands3.iVictoryPointsLeft -= this.getWarReparation_Cost(nWarRepartionsFromCivID);
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(j).addWarReparationsFromCivID(nWarRepartionsFromCivID);
                  return nCivID;
               }
               else {
                  ++j;
               }
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nWarRepartionsFromCivID) {
            int j = 0;
            while (j < this.peaceTreatyGameData.lCivsDemands_Defenders.size()) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iCivID == nCivID) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID > 0) {
                     if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID == nCivID) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID = 0;
                        final PeaceTreaty_Demands peaceTreaty_Demands4 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                        peaceTreaty_Demands4.iVictoryPointsLeft += this.getWarReparation_Cost(nWarRepartionsFromCivID);
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).removeWarReparationsFromCivID(nWarRepartionsFromCivID);
                        return 0;
                     }
                     if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iVictoryPointsLeft < this.getWarReparation_Cost(nWarRepartionsFromCivID)) {
                        return 0;
                     }
                     for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID = 0;
                           final PeaceTreaty_Demands peaceTreaty_Demands5 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k);
                           peaceTreaty_Demands5.iVictoryPointsLeft += this.getWarReparation_Cost(nWarRepartionsFromCivID);
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).removeWarReparationsFromCivID(nWarRepartionsFromCivID);
                           break;
                        }
                     }
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).iVictoryPointsLeft < this.getWarReparation_Cost(nWarRepartionsFromCivID)) {
                     return 0;
                  }
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iPaysWarReparationsToCivID = nCivID;
                  final PeaceTreaty_Demands peaceTreaty_Demands6 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(j);
                  peaceTreaty_Demands6.iVictoryPointsLeft -= this.getWarReparation_Cost(nWarRepartionsFromCivID);
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(j).addWarReparationsFromCivID(nWarRepartionsFromCivID);
                  return nCivID;
               }
               else {
                  ++j;
               }
            }
         }
      }
      return pointsUsedByCivID;
   }

   protected final boolean takeProvince(final int nProvinceID, final int nCivID, final int pointsUsedByCivID) {
      if (nProvinceID >= 0 && !CFG.game.getProvince(nProvinceID).getSeaProvince() && this.drawProvinceOwners.get(nProvinceID).isToTake) {
         this.iLastTakenID = nProvinceID;
         if (this.drawProvinceOwners.get(nProvinceID).isTaken > 0) {
            if (this.drawProvinceOwners.get(nProvinceID).iCivID == nCivID) {
               for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).removeDemandOnProvince(nProvinceID);
                     this.addProvincesLeftToTake(nProvinceID);
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).isTaken) {
                     final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                     peaceTreaty_Demands.iVictoryPointsLeft += this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
                     if (CFG.menuManager.getInGame_PeaceTreaty()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                     }
                  }
               }
               for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).removeDemandOnProvince(nProvinceID);
                     this.addProvincesLeftToTake(nProvinceID);
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).isTaken) {
                     final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                     peaceTreaty_Demands2.iVictoryPointsLeft += this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
                     if (CFG.menuManager.getInGame_PeaceTreaty()) {
                        CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
                     }
                  }
               }
               this.drawProvinceOwners.get(nProvinceID).isTaken = -1;
               this.drawProvinceOwners.get(nProvinceID).iCivID = CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince();
            }
            else {
               for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).removeDemandOnProvince(nProvinceID);
                     this.addProvincesLeftToTake(nProvinceID);
                  }
                  final PeaceTreaty_Demands peaceTreaty_Demands3 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
                  peaceTreaty_Demands3.iVictoryPointsLeft += this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
               }
               for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).removeDemandOnProvince(nProvinceID);
                     this.addProvincesLeftToTake(nProvinceID);
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).isTaken) {
                     final PeaceTreaty_Demands peaceTreaty_Demands4 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
                     peaceTreaty_Demands4.iVictoryPointsLeft += this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
                  }
               }
               this.drawProvinceOwners.get(nProvinceID).isTaken = -1;
               if (this.makeDemand_Province(nProvinceID, nCivID, pointsUsedByCivID)) {
                  this.removeProvincesLeftToTake(nProvinceID);
                  CFG.game.setActiveProvinceID(-1);
                  this.AI_UseVictoryPoints();
                  return true;
               }
               CFG.game.setActiveProvinceID(-1);
               this.AI_UseVictoryPoints();
               return false;
            }
         }
         else {
            if (this.makeDemand_Province(nProvinceID, nCivID, pointsUsedByCivID)) {
               this.removeProvincesLeftToTake(nProvinceID);
               CFG.game.setActiveProvinceID(-1);
               this.AI_UseVictoryPoints();
               return true;
            }
            CFG.game.setActiveProvinceID(-1);
            this.AI_UseVictoryPoints();
            return false;
         }
      }
      return false;
   }

   protected final boolean makeDemand_Province(final int nProvinceID, final int nCivID, final int pointsUsedByCivID) {
      return this.makeDemand_Province(nProvinceID, nCivID, pointsUsedByCivID, false);
   }

   protected final boolean makeDemand_Province(final int nProvinceID, final int nCivID, int pointsUsedByCivID, final boolean free_ToTrueOwner) {
      Gdx.app.log("AoC", "makeDemand_Province: nCivID: " + CFG.game.getCiv(nCivID).getCivName() + ", nProvinceID: " + CFG.game.getProvince(nProvinceID).getName());
      if (nCivID != pointsUsedByCivID && CFG.game.getProvince(nProvinceID).getTrueOwnerOfProvince() == nCivID) {
         pointsUsedByCivID = nCivID;
      }
      if (!free_ToTrueOwner) {
         for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
            if (this.drawProvinceOwners.get(nProvinceID).isTaken > 0) {
               if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == pointsUsedByCivID) {
                  if (this.drawProvinceOwners.get(nProvinceID).isTaken == pointsUsedByCivID) {
                     break;
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft < this.drawProvinceOwners.get(nProvinceID).iProvinceValue) {
                     return false;
                  }
               }
            }
            else if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == pointsUsedByCivID && this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iVictoryPointsLeft < this.drawProvinceOwners.get(nProvinceID).iProvinceValue) {
               return false;
            }
         }
         for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
            if (this.drawProvinceOwners.get(nProvinceID).isTaken > 0) {
               if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == pointsUsedByCivID) {
                  if (this.drawProvinceOwners.get(nProvinceID).isTaken == pointsUsedByCivID) {
                     break;
                  }
                  if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft < this.drawProvinceOwners.get(nProvinceID).iProvinceValue) {
                     return false;
                  }
               }
            }
            else if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == pointsUsedByCivID && this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iVictoryPointsLeft < this.drawProvinceOwners.get(nProvinceID).iProvinceValue) {
               return false;
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == nCivID) {
            this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).addDemandOnProvince(nProvinceID);
         }
         if (this.drawProvinceOwners.get(nProvinceID).isTaken > 0) {
            if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
               this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).removeDemandOnProvince(nProvinceID);
            }
         }
         else if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(i).iCivID == pointsUsedByCivID) {
            final PeaceTreaty_Demands peaceTreaty_Demands = this.peaceTreatyGameData.lCivsDemands_Defenders.get(i);
            peaceTreaty_Demands.iVictoryPointsLeft -= this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++i) {
         if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == nCivID) {
            this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).addDemandOnProvince(nProvinceID);
         }
         if (this.drawProvinceOwners.get(nProvinceID).isTaken > 0) {
            if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == this.drawProvinceOwners.get(nProvinceID).iCivID) {
               this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).removeDemandOnProvince(nProvinceID);
            }
         }
         else if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i).iCivID == pointsUsedByCivID) {
            final PeaceTreaty_Demands peaceTreaty_Demands2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(i);
            peaceTreaty_Demands2.iVictoryPointsLeft -= this.drawProvinceOwners.get(nProvinceID).iProvinceValue;
         }
      }
      this.drawProvinceOwners.get(nProvinceID).isTaken = pointsUsedByCivID;
      this.drawProvinceOwners.get(nProvinceID).iCivID = nCivID;
      if (CFG.menuManager.getInGame_PeaceTreaty()) {
         CFG.menuManager.rebuildInGame_PeaceTreaty_Scores();
      }
      return true;
   }

   protected final boolean purifyTreaty(final int iFromCivID) {
      boolean updateData = false;
      for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
         if (!CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getControlledByPlayer()) {
            for (int l = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.size() - 1; l >= 0; --l) {
               int numOfConnections_Own = 0;
               int numOfConnections_PT = 0;
               int numOfConnections_Enemies = 0;
               int numOfConnections_Neutral = 0;

               for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                  //instead of checking if province in peace treaty is owned by civ, get cumulative amount of provinces in peace treaty connections to actual territory.
                  //then check but add to seperate int for differential evaluation
                  if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
                     ++numOfConnections_Own;
                  } else if (this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
                     ++numOfConnections_PT;
                  } else if (CFG.game.getCivsAtWar(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID, Math.abs(this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID))) {
                     ++numOfConnections_Enemies;
                  } else if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getSeaProvince()) {
                     ++numOfConnections_Neutral;
                  }
               }

               float numDistance = (CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)));
               //dist factor
               float mediatedMapSize = (float)CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize());
               //float sizeFactor = (1.0F - ((float) CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getNumOfProvinces() / (float) CFG.game.countLandProvinces_NotWasteland()));

               // ((s - n) + o) + (c * 3))
               // ------------------------
               //     (s + (n * 1.6))

               float sTotal = (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize());
               float numerator;
               float denominator;
               if (sTotal > 0) {
                  float nNotOwned = numOfConnections_Neutral + numOfConnections_Enemies;
                  float oOwned = numOfConnections_PT + numOfConnections_Own;

                  numerator = ((sTotal - nNotOwned) + (oOwned) + (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringSeaProvincesSize() * 3));

                  denominator = (sTotal - (nNotOwned * 1.65F));
               } else {
                  //if island (no neighboring)
                  if (numDistance < 150) {
                     numerator = 1.0F;
                  } else {
                     numerator = 0.0F;
                  }
                  denominator = 1.0F;
               }

               //eval distance, if more than 7% away, or if surrounded by enemy, or if little friendly connections
               if ((numerator / denominator) <= 0.45F) {
                  //if little treaty connections and not high value, revoke claim
                  //else if treaty connections but no mainland connections create vassal in power vacuum
                  //if owner is self
                  if (Math.abs(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID) == CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getTrueOwnerOfProvince()) {
                     int optimal = -1;
                     for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID == CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) continue;
                        if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0 ||
                                CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0) {
                           optimal = CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID();
                           break;
                        }
                     }

                     if (optimal > -1) {
                        int i = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l);
                        this.drawProvinceOwners.get(i).iCivID = optimal;
                        this.drawProvinceOwners.get(i).isTaken = optimal;
                        this.drawProvinceOwners.get(i).isToTake = false;
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.remove(l);

                        for (int l2 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; l2 >= 0; --l2) {
                           if (optimal == this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).iCivID) {
                              this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).lDemands.add(i);
                           }
                        }
                        for (int l2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; l2 >= 0; --l2) {
                           if (optimal == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).iCivID) {
                              this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).lDemands.add(i);
                           }
                        }

                        updateData = true;
                        Gdx.app.log("AoC2.5", "Corrected Peace Treaty loneprovince");
                        continue;
                     }
                  }

                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).isTaken = -1;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID = CFG.game.getProvince(k).getCivID() * -1;
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                  updateData = true;
               } else if ((CFG.AI_VASSALS) && ((numerator / denominator) <= 0.5F || (10.0F * ((numDistance) / mediatedMapSize)) > 30.0F) && !CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).saveProvinceData.oProvinceCore.getHaveACore(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID)) {
                  int civID = k;
                  if (CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getIsPupet() && (CFG.SPECTATOR_MODE || !CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).getControlledByPlayer())) {
                     for (int k2 = 0; k2 < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k2) {
                        if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k2).iCivID == CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()) {
                           civID = k2;
                           break;
                        }
                     }
                  }

                  Gdx.app.log("AoC2.5", "AI Peace Vassalization");
                  //try build vassal
                  //imported filehandle

                  float found = -1.0F;
                  int foundVassal = -1;

                  //look through existing in-war vassals first
                  try {
                     for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).civGameData.iVassalsSize; i++) {
                        int iVassal = CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).civGameData.lVassals.get(i).iCivID;

                        float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(iVassal).getCivID(), this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l))) / (mediatedMapSize));
                        if (dist <= 30.0F) {
                           //if already found vassal, check which is closer
                           if (found < 0.0F || dist < found) {
                              found = dist;
                              foundVassal = iVassal;
                           }
                        }
                     }
                  } catch (IndexOutOfBoundsException e) {
                  }

                  String foundID = "";
                  //look through drafted vassals
                  Enumeration<String> keyVals = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.keys();
                  while (keyVals.hasMoreElements()) {
                     String key = keyVals.nextElement();
                     for (Integer provinceID : this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.get(key)) {
                        //if any of vassals provinces closer than 7%, assign province to vassal and end
                        float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(provinceID, this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l))) / (mediatedMapSize));
                        if (dist <= 30.0F) {
                           //if already found vassal, check which is closer
                           if (found < 0.0F || dist < found) {
                              found = dist;
                              foundID = key;
                           }
                        }
                     }
                  }

                  //if found, add province to found vassal's id, if not add new vassal with new province list
                  if (found > -1.0F) {
                     Gdx.app.log("AoC2.5", "AI Peace Vassal Annex");
                     //if string length (so drafter vassal)
                     //else use created vassal
                     if (foundID.length() > 0) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.get(foundID).add(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                     } else if (foundVassal > 0) {
                        this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID = foundVassal;
                     }
                  } else {
                     Gdx.app.log("AoC2.5", "AI Peace Vassal Create");
                     //try to get civ tags from suggested owners
                     ArrayList<String> lCivsTags = new ArrayList<String>();
                     try {
                        FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                        String sOwners = file.readString();
                        String[] sRes = sOwners.split(";");

                        for (int i = 0; i < sRes.length; i += 2) {
                           boolean bContinue = false;

                           //if civ already in game, signal to skip
                           for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                              if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                 bContinue = true;
                                 break;
                              }
                           }

                           //skip, don't add if civ already detected in game
                           if (bContinue) continue;

                           lCivsTags.add(sRes[i]);
                        }

                        //memory dump
                        sRes = null;
                        sOwners = null;
                        file = null;
                     } catch (GdxRuntimeException e) {
                     }
                     try {
                        //if no civ tags, use random civ
                        if (lCivsTags.size() < 1) {
                           FileHandle file = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
                           String sOwners = file.readString();
                           String[] sRes = sOwners.split(";");

                           for (int i = 0; i < sRes.length; i++) {
                              boolean bContinue = false;

                              //if civ already in game, signal to skip
                              for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                 if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                    bContinue = true;
                                    break;
                                 }
                              }

                              //skip, don't add if civ already detected in game
                              if (bContinue) continue;
                              lCivsTags.add(sRes[i]);
                           }

                           //memory dump
                           sRes = null;
                           sOwners = null;
                           file = null;
                        }
                     } catch (GdxRuntimeException e) {
                        CFG.exceptionStack(e);
                     }

                     //init vassal's province list with this province
                     ArrayList<Integer> provinces = new ArrayList<Integer>();
                     provinces.add(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));

                     //if suggested owners, select random one and pass to be created as vassal. If not then use random civ
                     if (lCivsTags.size() > 0) {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).addReleasableVassal_PT((lCivsTags.get((int) (Math.random() * lCivsTags.size()))), provinces);
                     } else {
                        this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).addReleasableVassal_PT("ran", provinces);
                     }
                  }
               }
            }
         }
      }
      for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
         if (!CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getControlledByPlayer()) {
            for (int l = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.size() - 1; l >= 0; --l) {
               int numOfConnections_Own = 0;
               int numOfConnections_PT = 0;
               int numOfConnections_Enemies = 0;
               int numOfConnections_Neutral = 0;

               for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                  //instead of checking if province in peace treaty is owned by civ, get cumulative amount of provinces in peace treaty connections to actual territory.
                  //then check but add to seperate int for differential evaluation
                  if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                     ++numOfConnections_Own;
                  } else if (this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                     ++numOfConnections_PT;
                  } else if (CFG.game.getCivsAtWar(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID, Math.abs(this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID))) {
                     ++numOfConnections_Enemies;
                  } else if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getSeaProvince()) {
                     ++numOfConnections_Neutral;
                  }
               }

               float numDistance = (CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)));
               //dist factor
               float mediatedMapSize = (float)CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize());
               //float sizeFactor = (1.0F - ((float) CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getNumOfProvinces() / (float) CFG.game.countLandProvinces_NotWasteland()));

               // ((s - n) + o) + (c * 3))
               // ------------------------
               //     (s + (n * 1.6))

               float sTotal = (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize());
               float numerator;
               float denominator;
               if (sTotal > 0) {
                  float nNotOwned = numOfConnections_Neutral + numOfConnections_Enemies;
                  float oOwned = numOfConnections_PT + numOfConnections_Own;

                  numerator = ((sTotal - nNotOwned) + (oOwned) + (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringSeaProvincesSize() * 3));

                  denominator = (sTotal - (nNotOwned * 1.65F));
               } else {
                  //if island (no neighboring)
                  if (numDistance < 150) {
                     numerator = 1.0F;
                  } else {
                     numerator = 0.0F;
                  }
                  denominator = 1.0F;
               }

               //eval distance, if more than 7% away, or if surrounded by enemy, or if little friendly connections
               if ((numerator / denominator) <= 0.45F) {
                  //if little treaty connections and not high value, revoke claim
                  //else if treaty connections but no mainland connections create vassal in power vacuum
                  //if owner is self
                  if (Math.abs(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID) == CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getTrueOwnerOfProvince()) {
                     int optimal = -1;
                     for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID == CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) continue;
                        if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0 ||
                                CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0) {
                           optimal = CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID();
                           break;
                        }
                     }

                     if (optimal > -1) {
                        int i = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l);
                        this.drawProvinceOwners.get(i).iCivID = optimal;
                        this.drawProvinceOwners.get(i).isTaken = optimal;
                        this.drawProvinceOwners.get(i).isToTake = false;
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.remove(l);

                        for (int l2 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; l2 >= 0; --l2) {
                           if (optimal == this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).iCivID) {
                              this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).lDemands.add(i);
                              break;
                           }
                        }
                        for (int l2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; l2 >= 0; --l2) {
                           if (optimal == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).iCivID) {
                              this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).lDemands.add(i);
                              break;
                           }
                        }

                        Gdx.app.log("AoC2.5", "Corrected Peace Treaty loneprovince");
                        updateData = true;
                        continue;
                     }
                  }

                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).isTaken = -1;
                  this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID = CFG.game.getProvince(k).getCivID() * -1;
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                  updateData = true;
               } else if ((CFG.AI_VASSALS) && ((numerator / denominator) <= 0.5F || (10.0F * ((numDistance) / mediatedMapSize)) > 30.0F) && !CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).saveProvinceData.oProvinceCore.getHaveACore(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID)) {
                  int civID = k;
                  if (CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getIsPupet() && (CFG.SPECTATOR_MODE || !CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).getControlledByPlayer())) {
                     for (int k2 = 0; k2 < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k2) {
                        if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k2).iCivID == CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()) {
                           civID = k2;
                           break;
                        }
                     }
                  }

                  Gdx.app.log("AoC2.5", "AI Peace Vassalization");
                  //try build vassal
                  //imported filehandle

                  float found = -1.0F;
                  int foundVassal = -1;

                  //look through existing in-war vassals first
                  try {
                     for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).civGameData.iVassalsSize; i++) {
                        int iVassal = CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).civGameData.lVassals.get(i).iCivID;

                        float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(iVassal).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l))) / (mediatedMapSize));
                        if (dist <= 30.0F) {
                           //if already found vassal, check which is closer
                           if (found < 0.0F || dist < found) {
                              found = dist;
                              foundVassal = iVassal;
                           }
                        }
                     }
                  } catch (IndexOutOfBoundsException e) {
                  }

                  String foundID = "";
                  //look through drafted vassals
                  Enumeration<String> keyVals = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.keys();
                  while (keyVals.hasMoreElements()) {
                     String key = keyVals.nextElement();
                     for (Integer provinceID : this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.get(key)) {
                        //if any of vassals provinces closer than 7%, assign province to vassal and end
                        float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(provinceID, this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l))) / (mediatedMapSize));
                        if (dist <= 30.0F) {
                           //if already found vassal, check which is closer
                           if (found < 0.0F || dist < found) {
                              found = dist;
                              foundID = key;
                           }
                        }
                     }
                  }

                  //if found, add province to found vassal's id, if not add new vassal with new province list
                  if (found > -1.0F) {
                     Gdx.app.log("AoC2.5", "AI Peace Vassal Annex");
                     //if string length (so drafter vassal)
                     //else use created vassal
                     if (foundID.length() > 0) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.get(foundID).add(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                     } else if (foundVassal > 0) {
                        this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID = foundVassal;
                     }
                  } else {
                     Gdx.app.log("AoC2.5", "AI Peace Vassal Create");
                     //try to get civ tags from suggested owners
                     ArrayList<String> lCivsTags = new ArrayList<String>();
                     try {
                        FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                        String sOwners = file.readString();
                        String[] sRes = sOwners.split(";");

                        for (int i = 0; i < sRes.length; i += 2) {
                           boolean bContinue = false;

                           //if civ already in game, signal to skip
                           for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                              if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                 bContinue = true;
                                 break;
                              }
                           }

                           //skip, don't add if civ already detected in game
                           if (bContinue) continue;

                           lCivsTags.add(sRes[i]);
                        }

                        //memory dump
                        sRes = null;
                        sOwners = null;
                        file = null;
                     } catch (GdxRuntimeException e) {
                     }
                     try {
                        //if no civ tags, use random civ
                        if (lCivsTags.size() < 1) {
                           FileHandle file = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
                           String sOwners = file.readString();
                           String[] sRes = sOwners.split(";");

                           for (int i = 0; i < sRes.length; i++) {
                              boolean bContinue = false;

                              //if civ already in game, signal to skip
                              for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                 if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                    bContinue = true;
                                    break;
                                 }
                              }

                              //skip, don't add if civ already detected in game
                              if (bContinue) continue;
                              lCivsTags.add(sRes[i]);
                           }

                           //memory dump
                           sRes = null;
                           sOwners = null;
                           file = null;
                        }
                     } catch (GdxRuntimeException e) {
                        CFG.exceptionStack(e);
                     }

                     //init vassal's province list with this province
                     ArrayList<Integer> provinces = new ArrayList<Integer>();
                     provinces.add(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));

                     //if suggested owners, select random one and pass to be created as vassal. If not then use random civ
                     if (lCivsTags.size() > 0) {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).addReleasableVassal_PT((lCivsTags.get((int) (Math.random() * lCivsTags.size()))), provinces);
                     } else {
                        this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).addReleasableVassal_PT("ran", provinces);
                     }
                  }
               }
            }
         }
      }
      return updateData;
   }

   protected final void preparePeaceTreatyToSend(final int iFromCivID) {
      for (int i = 0; i < this.peaceTreatyGameData.lCivsData_Defenders.size(); ++i) {
         for (int j = 0; j < this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.size(); ++j) {
            if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j)).isTaken < 0) {
               this.makeDemand_Province(this.peaceTreatyGameData.lCivsData_Defenders.get(i).lProvincesLost.get(j), this.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID, this.peaceTreatyGameData.lCivsData_Defenders.get(i).iCivID, true);
            }
         }
      }
      for (int i = 0; i < this.peaceTreatyGameData.lCivsData_Aggressors.size(); ++i) {
         for (int j = 0; j < this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.size(); ++j) {
            if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j)).isTaken < 0) {
               this.makeDemand_Province(this.peaceTreatyGameData.lCivsData_Aggressors.get(i).lProvincesLost.get(j), this.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID, this.peaceTreatyGameData.lCivsData_Aggressors.get(i).iCivID, true);
            }
         }
      }

      if (!CFG.game.getCiv(iFromCivID).getControlledByPlayer()) {
         boolean updateData = false;
         for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
            if (!CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getControlledByPlayer()) {
               for (int l = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.size() - 1; l >= 0; --l) {
                  int numOfConnections_Own = 0;
                  int numOfConnections_PT = 0;
                  int numOfConnections_Enemies = 0;
                  int numOfConnections_Neutral = 0;

                  for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                     //instead of checking if province in peace treaty is owned by civ, get cumulative amount of provinces in peace treaty connections to actual territory.
                     //then check but add to seperate int for differential evaluation
                     if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
                        ++numOfConnections_Own;
                     } else if (this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
                        ++numOfConnections_PT;
                     } else if (CFG.game.getCivsAtWar(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID, Math.abs(this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID))) {
                        ++numOfConnections_Enemies;
                     } else if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getSeaProvince()) {
                        ++numOfConnections_Neutral;
                     }
                  }

                  float numDistance = (CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)));
                  //dist factor
                  float mediatedMapSize = (float) CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize());
                  //float sizeFactor = (1.0F - ((float) CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getNumOfProvinces() / (float) CFG.game.countLandProvinces_NotWasteland()));

                  // ((s - n) + o) + (c * 3))
                  // ------------------------
                  //     (s + (n * 1.6))

                  float sTotal = (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize());
                  float numerator;
                  float denominator;
                  if (sTotal > 0) {
                     float nNotOwned = numOfConnections_Neutral + numOfConnections_Enemies;
                     float oOwned = numOfConnections_PT + numOfConnections_Own;

                     numerator = ((sTotal - nNotOwned) + (oOwned) + (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringSeaProvincesSize() * 3));
                     numerator += (((numDistance) / mediatedMapSize));

                     denominator = (sTotal - (nNotOwned * 1.65F) + numOfConnections_Neutral);
                  } else {
                     //if island (no neighboring)
                     if (numDistance < 150) {
                        numerator = 1.0F;
                     } else {
                        numerator = 0.0F;
                     }
                     denominator = 1.0F;
                  }

                  //eval distance, if more than 7% away, or if surrounded by enemy, or if little friendly connections
                  if ((numerator / denominator) <= 0.45F) {
                     //if little treaty connections and not high value, revoke claim
                     //else if treaty connections but no mainland connections create vassal in power vacuum
                     //if owner is self
                     if (Math.abs(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID) == CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getTrueOwnerOfProvince()) {
                        int optimal = -1;
                        for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                           if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID == CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID())
                              continue;
                           if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0 ||
                                   CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0) {
                              optimal = CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID();
                              break;
                           }
                        }

                        if (optimal > -1) {
                           int i = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l);
                           this.drawProvinceOwners.get(i).iCivID = optimal;
                           this.drawProvinceOwners.get(i).isTaken = optimal;
                           this.drawProvinceOwners.get(i).isToTake = false;
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.remove(l);

                           for (int l2 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; l2 >= 0; --l2) {
                              if (optimal == this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).iCivID) {
                                 this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).lDemands.add(i);
                              }
                           }
                           for (int l2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; l2 >= 0; --l2) {
                              if (optimal == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).iCivID) {
                                 this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).lDemands.add(i);
                              }
                           }

                           updateData = true;
                           Gdx.app.log("AoC2.5", "Corrected Peace Treaty loneprovince");
                           continue;
                        }
                     }

                     this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).isTaken = -1;
                     this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID = CFG.game.getProvince(k).getCivID() * -1;
                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                     updateData = true;
                  } else if ((CFG.AI_VASSALS) && ((numerator / denominator) <= 0.5F || (10.0F * ((numDistance) / mediatedMapSize)) > 30.0F) && !CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).saveProvinceData.oProvinceCore.getHaveACore(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID)) {
                     int civID = k;
                     if (CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getIsPupet() && (CFG.SPECTATOR_MODE || !CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).getControlledByPlayer())) {
                        for (int k2 = 0; k2 < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k2) {
                           if (this.peaceTreatyGameData.lCivsDemands_Defenders.get(k2).iCivID == CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()) {
                              civID = k2;
                              break;
                           }
                        }
                     }

                     Gdx.app.log("AoC2.5", "AI Peace Vassalization");
                     //try build vassal
                     //imported filehandle

                     float found = -1.0F;
                     int foundVassal = -1;

                     //look through existing in-war vassals first
                     try {
                        for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).civGameData.iVassalsSize; i++) {
                           int iVassal = CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID).getPuppetOfCivID()).civGameData.lVassals.get(i).iCivID;

                           float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(iVassal).getCivID(), this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l))) / (mediatedMapSize));
                           if (dist <= 30.0F) {
                              //if already found vassal, check which is closer
                              if (found < 0.0F || dist < found) {
                                 found = dist;
                                 foundVassal = iVassal;
                              }
                           }
                        }
                     } catch (IndexOutOfBoundsException e) {
                     }

                     String foundID = "";
                     //look through drafted vassals
                     Enumeration<String> keyVals = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.keys();
                     while (keyVals.hasMoreElements()) {
                        String key = keyVals.nextElement();
                        for (Integer provinceID : this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.get(key)) {
                           //if any of vassals provinces closer than 7%, assign province to vassal and end
                           float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(provinceID, this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l))) / (mediatedMapSize));
                           if (dist <= 30.0F) {
                              //if already found vassal, check which is closer
                              if (found < 0.0F || dist < found) {
                                 found = dist;
                                 foundID = key;
                              }
                           }
                        }
                     }

                     //if found, add province to found vassal's id, if not add new vassal with new province list
                     if (found > 0.0F) {
                        Gdx.app.log("AoC2.5", "AI Peace Vassal Annex");
                        //if string length (so drafter vassal)
                        //else use created vassal
                        if (foundID.length() > 0) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableVassals_PT.get(foundID).add(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                        } else if (foundVassal > 0) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l)).iCivID = foundVassal;
                        }
                     } else {
                        Gdx.app.log("AoC2.5", "AI Peace Vassal Create");
                        //try to get civ tags from suggested owners
                        ArrayList<String> lCivsTags = new ArrayList<String>();
                        try {
                           FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));
                           String sOwners = file.readString();
                           String[] sRes = sOwners.split(";");

                           for (int i = 0; i < sRes.length; i += 2) {
                              boolean bContinue = false;

                              //if civ already in game, signal to skip
                              for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                 if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                    bContinue = true;
                                    break;
                                 }
                              }

                              //skip, don't add if civ already detected in game
                              if (bContinue) continue;

                              lCivsTags.add(sRes[i]);
                           }

                           //memory dump
                           sRes = null;
                           sOwners = null;
                           file = null;
                        } catch (GdxRuntimeException e) {
                        }
                        try {
                           //if no civ tags, use random civ
                           if (lCivsTags.size() < 1) {
                              FileHandle file = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
                              String sOwners = file.readString();
                              String[] sRes = sOwners.split(";");

                              for (int i = 0; i < sRes.length; i++) {
                                 boolean bContinue = false;

                                 //if civ already in game, signal to skip
                                 for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                    if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                       bContinue = true;
                                       break;
                                    }
                                 }

                                 //skip, don't add if civ already detected in game
                                 if (bContinue) continue;
                                 lCivsTags.add(sRes[i]);
                              }

                              //memory dump
                              sRes = null;
                              sOwners = null;
                              file = null;
                           }
                        } catch (GdxRuntimeException e) {
                           CFG.exceptionStack(e);
                        }

                        //init vassal's province list with this province
                        ArrayList<Integer> provinces = new ArrayList<Integer>();
                        provinces.add(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lDemands.get(l));

                        //if suggested owners, select random one and pass to be created as vassal. If not then use random civ
                        if (lCivsTags.size() > 0) {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).addReleasableVassal_PT((lCivsTags.get((int) (Math.random() * lCivsTags.size()))), provinces);
                        } else {
                           this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).addReleasableVassal_PT("ran", provinces);
                        }
                     }
                  }
               }
            }
         }
         for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
            if (!CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getControlledByPlayer()) {
               for (int l = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.size() - 1; l >= 0; --l) {
                  int numOfConnections_Own = 0;
                  int numOfConnections_PT = 0;
                  int numOfConnections_Enemies = 0;
                  int numOfConnections_Neutral = 0;

                  for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                     //instead of checking if province in peace treaty is owned by civ, get cumulative amount of provinces in peace treaty connections to actual territory.
                     //then check but add to seperate int for differential evaluation
                     if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                        ++numOfConnections_Own;
                     } else if (this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                        //for (int m2 = 0; m2 < CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getNeighboringProvincesSize(); ++m2) {
                        //if neighboring province connected to mainland, add, or if peace treaty owner is civ, add
                        //if (this.drawProvinceOwners.get(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getNeighboringProvinces(m2)).iCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                        //   if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getNeighboringProvinces(m2)).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
                        //      ++numOfConnections_Own;
                        //   } else {
                        //      ++numOfConnections_PT;
                        //   }
                        //}
                        //}
                        ++numOfConnections_PT;
                     } else if (CFG.game.getCivsAtWar(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID, Math.abs(this.drawProvinceOwners.get(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).iCivID))) {
                        ++numOfConnections_Enemies;
                     } else if (CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getSeaProvince()) {
                        ++numOfConnections_Neutral;
                     }
                  }

                  float numDistance = (CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)));
                  //dist factor
                  float mediatedMapSize = (float) CFG.map.getMapBG().getMaxDistance() / (100.0F * (float) CFG.game.countLandProvinces_NotWasteland() / (float) CFG.game.getProvincesSize());
                  //float sizeFactor = (1.0F - ((float) CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getNumOfProvinces() / (float) CFG.game.countLandProvinces_NotWasteland()));

                  // ((s - n) + o) + (c * 3))
                  // ------------------------
                  //     (s + (n * 1.6))

                  float sTotal = (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize());
                  float numerator;
                  float denominator;
                  if (sTotal > 0) {
                     float nNotOwned = numOfConnections_Neutral + numOfConnections_Enemies;
                     float oOwned = numOfConnections_PT + numOfConnections_Own;

                     numerator = ((sTotal - nNotOwned) + (oOwned) + (CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringSeaProvincesSize() * 3));
                     numerator += (((numDistance) / mediatedMapSize));

                     denominator = (sTotal - (nNotOwned * 1.65F) + numOfConnections_Neutral);
                  } else {
                     //if island (no neighboring)
                     if (numDistance < 150) {
                        numerator = 1.0F;
                     } else {
                        numerator = 0.0F;
                     }
                     denominator = 1.0F;
                  }

                  //eval distance, if more than 7% away, or if surrounded by enemy, or if little friendly connections
                  if ((numerator / denominator) <= 0.45F) {
                     //if little treaty connections and not high value, revoke claim
                     //else if treaty connections but no mainland connections create vassal in power vacuum
                     //if owner is self
                     if (Math.abs(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID) == CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getTrueOwnerOfProvince()) {
                        int optimal = -1;
                        for (int m = 0; m < CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvincesSize(); ++m) {
                           if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID == CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID())
                              continue;
                           if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0 ||
                                   CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID()) >= 0) {
                              optimal = CFG.game.getProvince(CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).getNeighboringProvinces(m)).getCivID();
                              break;
                           }
                        }

                        if (optimal > -1) {
                           int i = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l);
                           this.drawProvinceOwners.get(i).iCivID = optimal;
                           this.drawProvinceOwners.get(i).isTaken = optimal;
                           this.drawProvinceOwners.get(i).isToTake = false;
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.remove(l);

                           for (int l2 = this.peaceTreatyGameData.lCivsDemands_Defenders.size() - 1; l2 >= 0; --l2) {
                              if (optimal == this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).iCivID) {
                                 this.peaceTreatyGameData.lCivsDemands_Defenders.get(l2).lDemands.add(i);
                                 break;
                              }
                           }
                           for (int l2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.size() - 1; l2 >= 0; --l2) {
                              if (optimal == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).iCivID) {
                                 this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l2).lDemands.add(i);
                                 break;
                              }
                           }

                           Gdx.app.log("AoC2.5", "Corrected Peace Treaty loneprovince");
                           updateData = true;
                           continue;
                        }
                     }

                     this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).isTaken = -1;
                     this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID = CFG.game.getProvince(k).getCivID() * -1;
                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).removeDemandOnProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                     updateData = true;
                  } else if ((CFG.AI_VASSALS) && ((numerator / denominator) <= 0.5F || (10.0F * ((numDistance) / mediatedMapSize)) > 30.0F) && !CFG.game.getProvince(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).saveProvinceData.oProvinceCore.getHaveACore(this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID)) {
                     int civID = k;
                     if (CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getIsPupet() && (CFG.SPECTATOR_MODE || !CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).getControlledByPlayer())) {
                        for (int k2 = 0; k2 < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k2) {
                           if (this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k2).iCivID == CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()) {
                              civID = k2;
                              break;
                           }
                        }
                     }

                     Gdx.app.log("AoC2.5", "AI Peace Vassalization");
                     //try build vassal
                     //imported filehandle

                     float found = -1.0F;
                     int foundVassal = -1;

                     //look through existing in-war vassals first
                     try {
                        for (int i = 0; i < CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).civGameData.iVassalsSize; i++) {
                           int iVassal = CFG.game.getCiv(CFG.game.getCiv(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID).getPuppetOfCivID()).civGameData.lVassals.get(i).iCivID;

                           float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(iVassal).getCapitalProvinceID(), this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l))) / (mediatedMapSize));
                           if (dist <= 30.0F) {
                              //if already found vassal, check which is closer
                              if (found < 0.0F || dist < found) {
                                 found = dist;
                                 foundVassal = iVassal;
                              }
                           }
                        }
                     } catch (IndexOutOfBoundsException e) {
                     }

                     String foundID = "";
                     //look through drafted vassals
                     Enumeration<String> keyVals = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.keys();
                     while (keyVals.hasMoreElements()) {
                        String key = keyVals.nextElement();
                        for (Integer provinceID : this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.get(key)) {
                           //if any of vassals provinces closer than 7%, assign province to vassal and end
                           float dist = 10.0F * ((CFG.game_NextTurnUpdate.getDistanceFromCapital(provinceID, this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l))) / (mediatedMapSize));
                           if (dist <= 30.0F) {
                              //if already found vassal, check which is closer
                              if (found < 0.0F || dist < found) {
                                 found = dist;
                                 foundID = key;
                              }
                           }
                        }
                     }

                     //if found, add province to found vassal's id, if not add new vassal with new province list
                     if (found > -1.0F) {
                        Gdx.app.log("AoC2.5", "AI Peace Vassal Annex");
                        //if string length (so drafter vassal)
                        //else use created vassal
                        if (foundID.length() > 0) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableVassals_PT.get(foundID).add(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                        } else if (foundVassal > 0) {
                           this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l)).iCivID = foundVassal;
                        }
                     } else {
                        Gdx.app.log("AoC2.5", "AI Peace Vassal Create");
                        //try to get civ tags from suggested owners
                        ArrayList<String> lCivsTags = new ArrayList<String>();
                        try {
                           FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));
                           String sOwners = file.readString();
                           String[] sRes = sOwners.split(";");

                           for (int i = 0; i < sRes.length; i += 2) {
                              boolean bContinue = false;

                              //if civ already in game, signal to skip
                              for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                 if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                    bContinue = true;
                                    break;
                                 }
                              }

                              //skip, don't add if civ already detected in game
                              if (bContinue) continue;

                              lCivsTags.add(sRes[i]);
                           }

                           //memory dump
                           sRes = null;
                           sOwners = null;
                           file = null;
                        } catch (GdxRuntimeException e) {
                        }
                        try {
                           //if no civ tags, use random civ
                           if (lCivsTags.size() < 1) {
                              FileHandle file = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
                              String sOwners = file.readString();
                              String[] sRes = sOwners.split(";");

                              for (int i = 0; i < sRes.length; i++) {
                                 boolean bContinue = false;

                                 //if civ already in game, signal to skip
                                 for (int j = 0; j < CFG.game.getCivsSize(); ++j) {
                                    if (CFG.game.getCiv(j).getCivTag().equals(sRes[i])) {
                                       bContinue = true;
                                       break;
                                    }
                                 }

                                 //skip, don't add if civ already detected in game
                                 if (bContinue) continue;
                                 lCivsTags.add(sRes[i]);
                              }

                              //memory dump
                              sRes = null;
                              sOwners = null;
                              file = null;
                           }
                        } catch (GdxRuntimeException e) {
                           CFG.exceptionStack(e);
                        }

                        //init vassal's province list with this province
                        ArrayList<Integer> provinces = new ArrayList<Integer>();
                        provinces.add(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lDemands.get(l));

                        //if suggested owners, select random one and pass to be created as vassal. If not then use random civ
                        if (lCivsTags.size() > 0) {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).addReleasableVassal_PT((lCivsTags.get((int) (Math.random() * lCivsTags.size()))), provinces);
                        } else {
                           this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).addReleasableVassal_PT("ran", provinces);
                        }
                     }
                  }
               }
            }
         }
         if (updateData) {
            for (int k = 0; k < this.peaceTreatyGameData.lCivsData_Defenders.size(); ++k) {
               for (int l = 0; l < this.peaceTreatyGameData.lCivsData_Defenders.get(k).lProvincesLost.size(); ++l) {
                  if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Defenders.get(k).lProvincesLost.get(l)).isTaken < 0) {
                     this.makeDemand_Province(this.peaceTreatyGameData.lCivsData_Defenders.get(k).lProvincesLost.get(l), this.peaceTreatyGameData.lCivsData_Defenders.get(k).iCivID, this.peaceTreatyGameData.lCivsData_Defenders.get(k).iCivID, true);
                  }
               }
            }
            for (int k = 0; k < this.peaceTreatyGameData.lCivsData_Aggressors.size(); ++k) {
               for (int l = 0; l < this.peaceTreatyGameData.lCivsData_Aggressors.get(k).lProvincesLost.size(); ++l) {
                  if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsData_Aggressors.get(k).lProvincesLost.get(l)).isTaken < 0) {
                     this.makeDemand_Province(this.peaceTreatyGameData.lCivsData_Aggressors.get(k).lProvincesLost.get(l), this.peaceTreatyGameData.lCivsData_Aggressors.get(k).iCivID, this.peaceTreatyGameData.lCivsData_Aggressors.get(k).iCivID, true);
                  }
               }
            }


            //make claims on self
            //for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
            //   if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
            //      try {
            //         if (CFG.game.getWar(this.peaceTreatyGameData.iWarID).getAggressorID_ByCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince()) >= 0 ||
            //            CFG.game.getWar(this.peaceTreatyGameData.iWarID).getDefenderID_ByCivID(CFG.game.getProvince(i).getTrueOwnerOfProvince()) >= 0) {
//
            //            if (!this.drawProvinceOwners.get(i).isToTake) {
            //               this.drawProvinceOwners.get(i).iProvinceValue = 0;
            //               this.drawProvinceOwners.get(i).isToTake = true;
            //               this.drawProvinceOwners.get(i).iCivID = CFG.game.getProvince(i).getTrueOwnerOfProvince();
            //               this.drawProvinceOwners.get(i).isTaken = CFG.game.getProvince(i).getTrueOwnerOfProvince();
//
            //               this.makeDemand_Province(i, CFG.game.getProvince(i).getTrueOwnerOfProvince(), CFG.game.getProvince(i).getTrueOwnerOfProvince(), true);
            //            } else if (Math.abs(this.drawProvinceOwners.get(i).iCivID) == CFG.game.getProvince(i).getTrueOwnerOfProvince() || Math.abs(this.drawProvinceOwners.get(i).isTaken) == CFG.game.getProvince(i).getTrueOwnerOfProvince()) {
            //               this.drawProvinceOwners.get(i).iProvinceValue = 0;
            //               this.drawProvinceOwners.get(i).isToTake = true;
            //               this.drawProvinceOwners.get(i).iCivID = CFG.game.getProvince(i).getTrueOwnerOfProvince();
            //               this.drawProvinceOwners.get(i).isTaken = CFG.game.getProvince(i).getTrueOwnerOfProvince();
//
            //               for (int l = this.peaceTreatyGameData.lCivsData_Defenders.size() - 1; l >= 0; --l) {
            //                  if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Defenders.get(l).iCivID) {
            //                     this.peaceTreatyGameData.lCivsDemands_Defenders.get(l).lDemands.add(i);
            //                     break;
            //                  }
            //               }
            //               for (int l = this.peaceTreatyGameData.lCivsData_Aggressors.size() - 1; l >= 0; --l) {
            //                  if (CFG.game.getProvince(i).getTrueOwnerOfProvince() == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l).iCivID) {
            //                     this.peaceTreatyGameData.lCivsDemands_Aggressors.get(l).lDemands.add(i);
            //                     break;
            //                  }
            //               }
            //            }
//
            //         }
            //      } catch (IndexOutOfBoundsException e) {
            //         CFG.exceptionStack(e);
            //         continue;
            //      }
            //   }
            //}
            //updateData = true;

            for (int i = 0; i < 11; i++) {
               //refine treaty up to 10 times or up to revoke
               if (!this.purifyTreaty(iFromCivID)) break;
            }

         }
      }

      for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Defenders.size(); ++k) {
         for (int l = 0; l < this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.size(); ++l) {
            for (int k2 = this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(l).lProvinces.size() - 1; k2 >= 0; --k2) {
               if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(l).lProvinces.get(k2)).iCivID != this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(l).iCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).lReleasableCivs.get(l).lProvinces.remove(k2);
               }
            }
         }
         if (iFromCivID == this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).iCivID) {
            this.peaceTreatyGameData.lCivsDemands_Defenders.get(k).peaceTreatyAccepted = true;
         }
      }
      for (int k = 0; k < this.peaceTreatyGameData.lCivsDemands_Aggressors.size(); ++k) {
         for (int l = 0; l < this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.size(); ++l) {
            for (int k2 = this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(l).lProvinces.size() - 1; k2 >= 0; --k2) {
               if (this.drawProvinceOwners.get(this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(l).lProvinces.get(k2)).iCivID != this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(l).iCivID) {
                  this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).lReleasableCivs.get(l).lProvinces.remove(k2);
               }
            }
         }
         if (iFromCivID == this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).iCivID) {
            this.peaceTreatyGameData.lCivsDemands_Aggressors.get(k).peaceTreatyAccepted = true;
         }
      }
   }

   protected static int getProposal_Positive(final boolean scoreCountDefenders) {
      final int out = 0;
      return out;
   }

   protected static int getProposal_Negative(final boolean scoreCountDefenders) {
      final int out = 0;
      return out;
   }
}
