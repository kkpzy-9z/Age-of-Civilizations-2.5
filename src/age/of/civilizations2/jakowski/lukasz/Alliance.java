package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Alliance implements Serializable {
   private static final long serialVersionUID = 0L;
   private String sAllianceName;
   private Color_GameData allianceColor;
   private List<Integer> lCivilizations;
   private int iCivilizationsSize;
   private int iFormationTurnID = 1;

   protected Alliance(String sAllianceName) {
      this.sAllianceName = sAllianceName;
      this.lCivilizations = new ArrayList<>();
      this.iCivilizationsSize = 0;
      this.allianceColor = CFG.getRandomColorGameData();
   }

   protected final void addCivilization(int nCivID) {
      for(int i = 0; i < this.iCivilizationsSize; ++i) {
         if (this.lCivilizations.get(i) == nCivID) {
            return;
         }
      }

      this.lCivilizations.add(nCivID);
      this.iCivilizationsSize = this.lCivilizations.size();

      for(int i = 0; i < this.iCivilizationsSize - 1; ++i) {
         CFG.game
            .setCivRelation_OfCivB(this.lCivilizations.get(i), nCivID, Math.min(CFG.game.getCivRelation_OfCivB(this.lCivilizations.get(i), nCivID), 65.0F));
         CFG.game
            .setCivRelation_OfCivB(nCivID, this.lCivilizations.get(i), Math.min(CFG.game.getCivRelation_OfCivB(nCivID, this.lCivilizations.get(i)), 65.0F));
      }

      if (CFG.isDesktop()
         && (CFG.menuManager.getInGameView() || CFG.menuManager.getInNextPlayerTurn())
         && CFG.game.getCiv(nCivID).getControlledByPlayer()
         && AoCGame.steamGame != null) {
         AoCGame.steamGame.uploadAlliance();
      }
   }

   protected final void removeCivilization(int nCivID) {
      for(int i = 0; i < this.iCivilizationsSize; ++i) {
         if (this.lCivilizations.get(i) == nCivID) {
            this.lCivilizations.remove(i);
            this.iCivilizationsSize = this.lCivilizations.size();

            for(int a = 0; a < this.iCivilizationsSize; ++a) {
               if (CFG.game.getCivRelation_OfCivB(this.lCivilizations.get(a), nCivID) > 0.0F
                  || CFG.game.getCivRelation_OfCivB(nCivID, this.lCivilizations.get(a)) > 0.0F) {
                  CFG.game.setCivRelation_OfCivB(this.lCivilizations.get(a), nCivID, 0.0F);
                  CFG.game.setCivRelation_OfCivB(nCivID, this.lCivilizations.get(a), 0.0F);
               }
            }

            return;
         }
      }
   }

   protected final void updateCivilizationID(int i, int nNewCivID) {
      try {
         this.lCivilizations.set(i, nNewCivID);
      } catch (IndexOutOfBoundsException var4) {
         if (CFG.LOGS) {
            CFG.exceptionStack(var4);
         }
      }
   }

   protected final void moveUp(int iID) {
      if (iID != 0) {
         int tempCivID = this.lCivilizations.get(iID - 1);
         this.lCivilizations.set(iID - 1, this.lCivilizations.get(iID));
         this.lCivilizations.set(iID, tempCivID);
      }
   }

   protected final void moveDown(int iID) {
      int tempCivID = this.lCivilizations.get(iID + 1);
      this.lCivilizations.set(iID + 1, this.lCivilizations.get(iID));
      this.lCivilizations.set(iID, tempCivID);
   }

   protected final void updateCivsIDs_AfterRemoveCiv(int nRemovedCivID) {
      for(int i = 0; i < this.getCivilizationsSize(); ++i) {
         if (this.getCivilization(i) > nRemovedCivID) {
            this.lCivilizations.set(i, this.lCivilizations.get(i) - 1);
         }
      }
   }

   protected final String getAllianceName() {
      return this.sAllianceName;
   }

   protected final void setAllianceName(String sAllianceName) {
      this.sAllianceName = sAllianceName;
   }

   protected final int getCivilization(int iID) {
      try {
         return this.lCivilizations.get(iID);
      } catch (IndexOutOfBoundsException e) {
         //sandboxmod bugfix change//
         Gdx.app.log("Exception Alliance Array Revert", String.valueOf(iID));
         return (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
      }
   }

   protected final int getCivilizationsSize() {
      return this.iCivilizationsSize;
   }

   protected final Color_GameData getColorOfAlliance() {
      return this.allianceColor;
   }

   protected final void setColorOfAlliance(Color_GameData allianceColor) {
      this.allianceColor = allianceColor;
   }

   protected final int getFormationTurnID() {
      return this.iFormationTurnID;
   }

   protected final void setFormationTurnID(int iFormationTurnID) {
      this.iFormationTurnID = iFormationTurnID;
   }

   protected final int countProvinces() {
      int out = 0;

      for(int i = 0; i < this.getCivilizationsSize(); ++i) {
         out += CFG.game.getCiv(this.getCivilization(i)).getNumOfProvinces();
      }

      return out;
   }

   protected final int countPopulation() {
      int out = 0;

      for(int i = 0; i < this.getCivilizationsSize(); ++i) {
         out += CFG.game.getCiv(this.getCivilization(i)).countPopulation();
      }

      return out;
   }

   protected final int countEconomy() {
      int out = 0;

      for(int i = 0; i < this.getCivilizationsSize(); ++i) {
         out += CFG.game.getCiv(this.getCivilization(i)).countEconomy();
      }

      return out;
   }
}
