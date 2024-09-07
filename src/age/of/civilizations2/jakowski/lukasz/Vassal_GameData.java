package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class Vassal_GameData implements Serializable {
   private static final long serialVersionUID = 0L;
   protected int iCivID;
   protected int iTribute;
   protected AutonomyStatus autonomyStatus = null;
   protected Vassal_GameData(int iCivID) {
      this.iCivID = iCivID;
      this.setTribute(9);
   }

   protected Vassal_GameData(int iCivID, AutonomyStatus autonomyStatus) {
      this.iCivID = iCivID;
      this.setTribute(9);
      this.autonomyStatus = autonomyStatus;
   }

   protected final void setTribute(int iTribute) {
      if (iTribute > 20) {
         iTribute = 20;
      } else if (iTribute < 0) {
         iTribute = 0;
      }

      this.iTribute = iTribute;
   }

   protected final void setAutonomyStatus(int iAutonomyStatus) {
      //if less than 0, default to first and return
      if (iAutonomyStatus < 0) {
         this.autonomyStatus = CFG.gameAutonomy.getAutonomy(0);
         return;
      }

      this.autonomyStatus = CFG.gameAutonomy.getAutonomy(iAutonomyStatus);
   }
}
