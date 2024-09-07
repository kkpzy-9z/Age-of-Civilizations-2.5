//************** BUGFIX ONLY **************\\
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TimelapseManager {
   protected Timelapse_GameData timelapseGameData = new Timelapse_GameData();
   protected Timelapse_Owners_GameData timelapseOwnersGameData = new Timelapse_Owners_GameData();
   protected Timelapse_TurnChanges_GameData timelapseTurnChanges = new Timelapse_TurnChanges_GameData();
   protected Timelapse_Stats_GameData timelapseStatsGD = new Timelapse_Stats_GameData();
   protected static int SOURCE = 0;
   protected static final int[] TIME_REQUIRED_TO_ACTION = new int[]{1, 2000, 1500, 1000, 750, 500, 250};
   protected static final int MAX_SPEED = 6;
   protected static int SPEED = 1;
   protected static boolean PAUSE = true;
   protected static long TIME_PAST = 0L;
   protected static long TIME_LAST_UPDATE = 0L;
   protected List<Integer> timelineOwners = new ArrayList<>();
   protected List<Boolean> timelineOwners_IsOccupied = new ArrayList<>();
   protected List<Integer> timelineOwners_Capitals = new ArrayList<>();
   protected Timelapse_TurnChanges_GameData timelineOwners_Changes = new Timelapse_TurnChanges_GameData();
   protected int iTimelineTurnID = 0;

   protected final void updateTime() {
      try {
         TIME_PAST += System.currentTimeMillis() - TIME_LAST_UPDATE;
         TIME_LAST_UPDATE = System.currentTimeMillis();
         if (this.timePasted()) {
            TIME_PAST = 0L;
            this.loadNextTurn();
         }
      } catch (NullPointerException var2) {
         if (CFG.LOGS) {
            CFG.exceptionStack(var2);
         }
      } catch (IndexOutOfBoundsException var3) {
         if (CFG.LOGS) {
            CFG.exceptionStack(var3);
         }
      }
   }

   private final boolean timePasted() {
      return TIME_PAST > (long)this.getRequiredTime();
   }

   protected final int getRequiredTime() {
      return TIME_REQUIRED_TO_ACTION[SPEED];
   }

   protected final float getTimePerc() {
      return Math.min((float)TIME_PAST / (float)this.getRequiredTime(), 1.0F);
   }

   protected final void resetTime() {
      TIME_PAST = 0L;
      TIME_LAST_UPDATE = System.currentTimeMillis();
   }

   protected void pauseUnpause() {
      PAUSE = !PAUSE;
      if (!PAUSE) {
         if (this.iTimelineTurnID >= this.timelineOwners_Changes.lTurnChanges.size() - 1) {
            this.buildTimeline();
            PAUSE = false;
            if (CFG.menuManager.getInGame_Timeline()) {
               CFG.menuManager.getInGame_Timeline_UpdateLanguage();
            } else if (CFG.menuManager.getInVictory()) {
               CFG.menuManager.getInGame_Victory_UpdateLanguage();
            }
         }

         TIME_LAST_UPDATE = System.currentTimeMillis();
      }
   }

   protected void updateSpeed(int nDiff) {
      float tempTimePastPerc = this.getTimePerc();
      SPEED += nDiff;
      if (SPEED < 1) {
         SPEED = 1;
      } else if (SPEED > 6) {
         SPEED = 6;
      }

      TIME_PAST = (long)((float)TIME_REQUIRED_TO_ACTION[SPEED] * tempTimePastPerc);
   }

   protected final void buildTimeline() {
      this.clearTimeline();
      this.iTimelineTurnID = 0;
      this.resetTime();
      PAUSE = true;
      if (CFG.FOG_OF_WAR == 2) {
         for(int i = 0; i < this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.size(); ++i) {
            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(i)) {
               this.timelineOwners.add(this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.get(i));
            } else {
               this.timelineOwners.add(0);
            }

            this.timelineOwners_IsOccupied.add(false);
         }

         for(int i = 0; i < this.timelapseGameData.lCivsCapitals.size(); ++i) {
            int tempCapital = this.timelapseGameData.lCivsCapitals.get(i).getCapitalID(this.iTimelineTurnID + 1);
            if (tempCapital >= 0 && this.timelineOwners.get(tempCapital) == i + 1 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(i + 1)) {
               this.timelineOwners_Capitals.add(tempCapital);
            } else {
               this.timelineOwners_Capitals.add(-1);
            }
         }
      } else {
         for(int i = 0; i < this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.size(); ++i) {
            this.timelineOwners.add(this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.get(i));
            this.timelineOwners_IsOccupied.add(false);
         }

         for(int i = 0; i < this.timelapseGameData.lCivsCapitals.size(); ++i) {
            int tempCapital = this.timelapseGameData.lCivsCapitals.get(i).getCapitalID(this.iTimelineTurnID + 1);
            if (tempCapital >= 0 && this.timelineOwners.get(tempCapital) == i + 1) {
               this.timelineOwners_Capitals.add(tempCapital);
            } else {
               this.timelineOwners_Capitals.add(-1);
            }
         }
      }

      if (SaveManager.saveTag != null) {
         int turnSavesID = 0;

         try {
            FileHandle fileReadData3 = null;
            if (CFG.readLocalFiles()) {
               fileReadData3 = Gdx.files
                  .local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + SaveManager.saveTag + "/" + "TS/" + "TURN/" + "Age_of_Civilizations");
            } else {
               fileReadData3 = Gdx.files
                  .internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + SaveManager.saveTag + "/" + "TS/" + "TURN/" + "Age_of_Civilizations");
            }

            String tRead = fileReadData3.readString();
            turnSavesID = Integer.parseInt(tRead) + 1;

            for(int i = 0; i < turnSavesID; ++i) {
               FileHandle fileReadData4 = null;
               if (CFG.readLocalFiles()) {
                  fileReadData4 = Gdx.files
                     .local(
                        "saves/games/" + CFG.map.getFile_ActiveMap_Path() + SaveManager.saveTag + "/" + "TS/" + "TURN/" + SaveManager.saveTag + "_C" + "_" + i
                     );
               } else {
                  fileReadData4 = Gdx.files
                     .internal(
                        "saves/games/" + CFG.map.getFile_ActiveMap_Path() + SaveManager.saveTag + "/" + "TS/" + "TURN/" + SaveManager.saveTag + "_C" + "_" + i
                     );
               }

               Timelapse_TurnChanges_GameData tempChangesData = (Timelapse_TurnChanges_GameData)CFG.deserialize(fileReadData4.readBytes());

               for(int j = 0; j < tempChangesData.lTurnChanges.size(); ++j) {
                  this.timelineOwners_Changes.lTurnChanges.add(tempChangesData.lTurnChanges.get(j));
               }
            }
         } catch (GdxRuntimeException var8) {
            CFG.exceptionStack(var8);
         } catch (ClassNotFoundException var9) {
            CFG.exceptionStack(var9);
         } catch (IOException var10) {
            CFG.exceptionStack(var10);
         }
      }

      for(int j = 0; j < this.timelapseTurnChanges.lTurnChanges.size(); ++j) {
         this.timelineOwners_Changes.lTurnChanges.add(this.timelapseTurnChanges.lTurnChanges.get(j));
      }
   }

   protected final void clearTimeline() {
      this.timelineOwners.clear();
      this.timelineOwners_IsOccupied.clear();
      this.timelineOwners_Capitals.clear();
      this.timelineOwners_Changes.lTurnChanges.clear();
   }

   protected final void clearTimeline_Statistics() {
      this.timelapseStatsGD.lProvinces.clear();
      this.timelapseStatsGD.lPopulation.clear();
      this.timelapseStatsGD.lRank.clear();
      this.timelapseStatsGD.lTechnologyLevel.clear();
      this.timelapseStatsGD.lPlayers_Income.clear();
      this.timelapseStatsGD.lPlayers_Balance.clear();
      this.timelapseStatsGD.lPlayers_MilitarySpendings.clear();
      this.timelapseStatsGD.lHistory = new ArrayList<>();
   }

   protected final void loadNextTurn() {
      if (this.iTimelineTurnID < this.timelineOwners_Changes.lTurnChanges.size() - 1) {
         this.timelineOwners_Capitals.clear();
         if (CFG.FOG_OF_WAR == 2) {
            for(int i = 0; i < this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).size(); ++i) {
               if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iProvinceID)
                  )
                {
                  this.timelineOwners
                     .set(
                        this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iProvinceID,
                        this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iToCivID
                     );
                  this.timelineOwners_IsOccupied
                     .set(
                        this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iProvinceID,
                        this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).isOccupied
                     );
               }
            }

            for(int i = 0; i < this.timelapseGameData.lCivsCapitals.size(); ++i) {
               int tempCapital = this.timelapseGameData.lCivsCapitals.get(i).getCapitalID(this.iTimelineTurnID + 1);
               if (tempCapital >= 0 && this.timelineOwners.get(tempCapital) == i + 1 && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(i + 1)) {
                  this.timelineOwners_Capitals.add(tempCapital);
               } else {
                  this.timelineOwners_Capitals.add(-1);
               }
            }
         } else {
            for(int i = 0; i < this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).size(); ++i) {
               this.timelineOwners
                  .set(
                     this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iProvinceID,
                     this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iToCivID
                  );
               this.timelineOwners_IsOccupied
                  .set(
                     this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).iProvinceID,
                     this.timelineOwners_Changes.lTurnChanges.get(this.iTimelineTurnID).get(i).isOccupied
                  );
            }

            for(int i = 0; i < this.timelapseGameData.lCivsCapitals.size(); ++i) {
               int tempCapital = this.timelapseGameData.lCivsCapitals.get(i).getCapitalID(this.iTimelineTurnID + 1);
               if (tempCapital >= 0 && this.timelineOwners.get(tempCapital) == i + 1) {
                  this.timelineOwners_Capitals.add(tempCapital);
               } else {
                  this.timelineOwners_Capitals.add(-1);
               }
            }
         }

         ++this.iTimelineTurnID;
         if (CFG.menuManager.getInGame_Timeline()) {
            CFG.menuManager.getInGame_Timeline_UpdateLanguage();
         } else if (CFG.menuManager.getInVictory()) {
            CFG.menuManager.getInGame_Victory_UpdateLanguage();
            CFG.map.getMapCoordinates().centerToCivilizationBox_Timeline(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), true);
         }
      } else {
         PAUSE = true;
      }
   }

   protected final void newGame() {
      this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.clear();
      this.timelapseGameData.lCivsCapitals.clear();
      this.timelapseTurnChanges.lTurnChanges.clear();
      this.clearTimeline();
      this.clearTimeline_Statistics();

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (CFG.game.getProvince(i).getSeaProvince()) {
            this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.add(0);
         } else if (CFG.game.getProvince(i).getWasteland() >= 0) {
            this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.add(-1);
         } else {
            this.timelapseOwnersGameData.lProvinceOwnersAtBeginning.add(CFG.game.getProvince(i).getCivID());
         }
      }

      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
         this.timelapseGameData.lCivsCapitals.add(new Timelapse_Capitals(CFG.game.getCiv(i).getCapitalProvinceID(), 1));
      }

      this.timelapseTurnChanges.lTurnChanges.add(new ArrayList());
      List<Integer> tempProvinces = new ArrayList<>();
      List<Integer> tempPopulation = new ArrayList<>();
      List<Integer> tempRank = new ArrayList<>();
      List<Integer> tempTechnology = new ArrayList<>();

      for(int i = 0; i < CFG.game.getCivsSize(); ++i) {
         tempProvinces.add(CFG.game.getCiv(i).getNumOfProvinces());
         tempPopulation.add(CFG.game.getCiv(i).countPopulation() + CFG.game.getCiv(i).getNumOfUnits());
         tempRank.add(CFG.game.getCiv(i).getRankScore());
         tempTechnology.add((int)(CFG.game.getCiv(i).getTechnologyLevel() * 100.0F));
      }

      this.timelapseStatsGD.addProvinces(tempProvinces);
      this.timelapseStatsGD.addPopulation(tempPopulation);
      this.timelapseStatsGD.addRank(tempRank);
      this.timelapseStatsGD.addTechLevel(tempTechnology);
   }

   protected final void newTurn() {
      this.timelapseTurnChanges.lTurnChanges.add(new ArrayList());
      this.updateTurnStatistics();
   }

   protected final void addChange(int nProvinceID, int toCivID, boolean isOccupied) {
      //sandboxcut bugfix change//
      int iSize = this.timelapseTurnChanges.lTurnChanges.size() - 1;
      if (iSize < 0) {
         this.newTurn();
         iSize = 0;
      }

      for (int i = 0; i < this.timelapseTurnChanges.lTurnChanges.get(iSize).size(); ++i) {
         if (this.timelapseTurnChanges.lTurnChanges.get((int)iSize).get((int)i).iProvinceID != nProvinceID) continue;
         this.timelapseTurnChanges.lTurnChanges.get((int)iSize).get((int)i).iToCivID = toCivID;
         this.timelapseTurnChanges.lTurnChanges.get((int)iSize).get((int)i).isOccupied = isOccupied;
         return;
      }
      this.timelapseTurnChanges.lTurnChanges.get(iSize).add(new Timelapse_TurnChanges(nProvinceID, toCivID, isOccupied));
   }

   protected final void updateTurnStatistics() {
      try {
         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            this.timelapseGameData.lCivsCapitals.get(i - 1).updateCapital(CFG.game.getCiv(i).getCapitalProvinceID(), Game_Calendar.TURN_ID);
         }
      } catch (IndexOutOfBoundsException var9) {
         for(int i = this.timelapseGameData.lCivsCapitals.size() + 1; i < CFG.game.getCivsSize(); ++i) {
            this.timelapseGameData.lCivsCapitals.add(new Timelapse_Capitals(CFG.game.getCiv(i).getCapitalProvinceID(), Game_Calendar.TURN_ID));
         }
      }

      List<Integer> tempProvinces = new ArrayList<>();
      List<Integer> tempPopulation = new ArrayList<>();
      List<Integer> tempRank = new ArrayList<>();
      List<Integer> tempTechnology = new ArrayList<>();

      for(int i = 0; i < CFG.game.getCivsSize(); ++i) {
         tempProvinces.add(CFG.game.getCiv(i).getNumOfProvinces());
         tempPopulation.add(CFG.game.getCiv(i).countPopulation() + CFG.game.getCiv(i).getNumOfUnits());
         tempRank.add(CFG.game.getCiv(i).getRankScore());
         tempTechnology.add((int)(CFG.game.getCiv(i).getTechnologyLevel() * 100.0F));
      }

      this.timelapseStatsGD.addProvinces(tempProvinces);
      this.timelapseStatsGD.addPopulation(tempPopulation);
      this.timelapseStatsGD.addRank(tempRank);
      this.timelapseStatsGD.addTechLevel(tempTechnology);
      List<Integer> tempIncome = new ArrayList<>();
      List<Integer> tempBalance = new ArrayList<>();
      List<Integer> tempMilitarySpendings = new ArrayList<>();

      for(int i = 0; i < CFG.game.getPlayersSize(); ++i) {
         tempIncome.add(
            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).iIncomeTaxation + CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).iIncomeProduction
         );
         tempMilitarySpendings.add((int)CFG.game_NextTurnUpdate.getMilitaryUpkeep_Total(CFG.game.getPlayer(i).getCivID()));
         tempBalance.add(
            (int)CFG.game_NextTurnUpdate.getIncome(CFG.game.getPlayer(i).getCivID())
               - (int)CFG.game_NextTurnUpdate.getExpenses(CFG.game.getPlayer(i).getCivID())
         );
      }

      this.timelapseStatsGD.lPlayers_Income.add(tempIncome);
      this.timelapseStatsGD.lPlayers_Balance.add(tempBalance);
      this.timelapseStatsGD.lPlayers_MilitarySpendings.add(tempMilitarySpendings);
      if (this.timelapseStatsGD.lPlayers_Income.size() > CFG.settingsManager.GRAPH_DATA_LIMIT_PLAYER_DATA) {
         this.timelapseStatsGD.lPlayers_Income.remove(0);
      }

      if (this.timelapseStatsGD.lPlayers_Balance.size() > CFG.settingsManager.GRAPH_DATA_LIMIT_PLAYER_DATA) {
         this.timelapseStatsGD.lPlayers_Balance.remove(0);
      }

      if (this.timelapseStatsGD.lPlayers_MilitarySpendings.size() > CFG.settingsManager.GRAPH_DATA_LIMIT_PLAYER_DATA) {
         this.timelapseStatsGD.lPlayers_MilitarySpendings.remove(0);
      }
   }

   protected final int getNumOfProvinces(int nCivID) {
      int out = 0;

      try {
         for(int i = this.timelineOwners.size() - 1; i >= 0; --i) {
            if (this.timelineOwners.get(i) == nCivID) {
               ++out;
            }
         }
      } catch (IndexOutOfBoundsException var4) {
      } catch (NullPointerException var5) {
      }

      return out;
   }

   protected final int getPlayerIncome(int iPlayerID, int iTurnID) {
      try {
         return this.timelapseStatsGD.lPlayers_Income.get(iTurnID).get(iPlayerID);
      } catch (IndexOutOfBoundsException var6) {
         try {
            if (iTurnID >= this.timelapseStatsGD.lPlayers_Income.size()) {
               return this.timelapseStatsGD.lPlayers_Income.get(this.timelapseStatsGD.lPlayers_Income.size() - 1).get(iPlayerID);
            }
         } catch (IndexOutOfBoundsException var5) {
         }

         return 0;
      }
   }
}
