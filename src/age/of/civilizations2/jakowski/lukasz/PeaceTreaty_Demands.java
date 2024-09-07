package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

class PeaceTreaty_Demands implements Serializable {
   private static final long serialVersionUID = 0L;
   protected int iCivID;
   protected int iVictoryPointsLeft;
   protected boolean peaceTreatyAccepted = false;
   protected List<Integer> lDemands = new ArrayList<Integer>();
   protected int iTotalNumOfVicotryPoints = 0;
   protected List<Integer> lWarReparationsFromCivsID = new ArrayList<Integer>();
   protected int iPaysWarReparationsToCivID = -1;
   protected List<Integer> lWillVassalizeCivsID = new ArrayList<Integer>();
   protected int iWillBecomeVassalOfCivID = -1;
   protected List<PeaceTreaty_ReleaseableVassals> lReleasableCivs = new ArrayList<PeaceTreaty_ReleaseableVassals>();
   protected List<PeaceTreaty_ReleaseableVassals_TakeConrol> lReleasableCivs_TakeControl = new ArrayList<PeaceTreaty_ReleaseableVassals_TakeConrol>();
   //dictionary for peace treaty civs
   //imported dict, hashtable
   protected Dictionary<String, List<Integer>> lReleasableVassals_PT = new Hashtable<>();

   protected PeaceTreaty_Demands(int iCivID, int iVictoryPointsLeft) {
      this.iCivID = iCivID;
      this.iVictoryPointsLeft = iVictoryPointsLeft;
   }

   protected final void addDemandOnProvince(int nProvinceID) {
      for(int i = 0; i < this.lDemands.size(); ++i) {
         if ((Integer)this.lDemands.get(i) == nProvinceID) {
            return;
         }
      }

      this.lDemands.add(nProvinceID);
   }

   protected final void removeDemandOnProvince(int nProvinceID) {
      for(int i = 0; i < this.lDemands.size(); ++i) {
         if ((Integer)this.lDemands.get(i) == nProvinceID) {
            this.lDemands.remove(i);
            break;
         }
      }

   }

   protected final void removeWarReparationsFromCivID(int nID) {
      for(int i = 0; i < this.lWarReparationsFromCivsID.size(); ++i) {
         if ((Integer)this.lWarReparationsFromCivsID.get(i) == nID) {
            this.lWarReparationsFromCivsID.remove(i);
            return;
         }
      }

   }

   protected final void addWarReparationsFromCivID(int nID) {
      for(int i = 0; i < this.lWarReparationsFromCivsID.size(); ++i) {
         if ((Integer)this.lWarReparationsFromCivsID.get(i) == nID) {
            return;
         }
      }

      this.lWarReparationsFromCivsID.add(nID);
   }

   protected final void removeWillVassalizeCivID(int nID) {
      for(int i = 0; i < this.lWillVassalizeCivsID.size(); ++i) {
         if ((Integer)this.lWillVassalizeCivsID.get(i) == nID) {
            this.lWillVassalizeCivsID.remove(i);
            return;
         }
      }

   }

   protected final void addWillVassalizeCivID(int nID) {
      //if sizeable safecheck
      if (this.lWillVassalizeCivsID.size() > 0) {
         for (int i = 0; i < this.lWillVassalizeCivsID.size(); ++i) {
            //fixed (was war reps for some reason?)
            if ((Integer) this.lWillVassalizeCivsID.get(i) == nID) {
               return;
            }
         }
      }

      this.lWillVassalizeCivsID.add(nID);
   }

   protected final void removeReleaseVassal_TakeControl(int nFromCivID, int nVassalID) {
      for(int i = 0; i < this.lReleasableCivs_TakeControl.size(); ++i) {
         if (((PeaceTreaty_ReleaseableVassals_TakeConrol)this.lReleasableCivs_TakeControl.get(i)).iFromCivID == nFromCivID && ((PeaceTreaty_ReleaseableVassals_TakeConrol)this.lReleasableCivs_TakeControl.get(i)).iVassalCivID == nVassalID) {
            this.lReleasableCivs_TakeControl.remove(i);
            return;
         }
      }

   }

   protected final void addReleaseVassal_TakeControl(int nFromCivID, int nVassalID) {
      for (PeaceTreaty_ReleaseableVassals_TakeConrol peaceTreatyReleaseableVassalsTakeConrol : this.lReleasableCivs_TakeControl) {
         if (((PeaceTreaty_ReleaseableVassals_TakeConrol) peaceTreatyReleaseableVassalsTakeConrol).iFromCivID == nFromCivID && ((PeaceTreaty_ReleaseableVassals_TakeConrol) peaceTreatyReleaseableVassalsTakeConrol).iVassalCivID == nVassalID) {
            return;
         }
      }

      this.lReleasableCivs_TakeControl.add(new PeaceTreaty_ReleaseableVassals_TakeConrol(nFromCivID, nVassalID));
   }

   //push to dictionary
   protected final void addReleasableVassal_PT(String civTag, ArrayList<Integer> lProvinces) {
      lReleasableVassals_PT.put(civTag, lProvinces);
   }
}
