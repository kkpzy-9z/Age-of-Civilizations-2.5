//************** BUGFIX ONLY **************\\
package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class Province_Army implements Serializable {
   private static final long serialVersionUID = 0L;
   private int iCivID;
   private int iArmy;
   private int iArmyWidth;

   protected Province_Army(int nCivID, int nArmy, int nProvinceID) {
      this.iCivID = nCivID;
      this.setArmy(nArmy, nProvinceID);
   }

   protected final void updateArmyWidth_Just(int nProvinceID) {
      try {
         CFG.glyphLayout.setText(CFG.fontArmy, "" + this.iArmy);
         this.iArmyWidth = (int)CFG.glyphLayout.width;
      } catch (IndexOutOfBoundsException | IllegalArgumentException | IllegalStateException | NullPointerException var) {

         boolean detect = false;
         for(int i = CFG.game.loadArmiesWidth_ErrorIDs.size() - 1; i > 0; --i) {
            //sandboxcut bugfix change//
            try {
               if ((Integer)CFG.game.loadArmiesWidth_ErrorIDs.get(i) == nProvinceID) {
                  detect = true;
                  break;
               }
            } catch (NullPointerException var2) {
               if (CFG.LOGS) {
                  CFG.exceptionStack(var2);
               }
            }
         }
         if (detect) {
            CFG.game.loadArmiesWidth_ErrorIDs.add(nProvinceID);
         }

         this.iArmyWidth = 1;
         if (CFG.LOGS) {
            CFG.exceptionStack(var);
         }
      }
   }

   protected final void updateArmyWidth(int nValue) {
      try {
         CFG.glyphLayout.setText(CFG.fontArmy, "" + nValue);
         this.iArmyWidth = (int)CFG.glyphLayout.width;
      } catch (IndexOutOfBoundsException var3) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var3);
         }
      } catch (NullPointerException var4) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var4);
         }
      } catch (IllegalArgumentException var5) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var5);
         }
      } catch (IllegalStateException var6) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var6);
         }
      }
   }

   protected final void updateArmyWidth(String nValue) {
      try {
         CFG.glyphLayout.setText(CFG.fontArmy, "" + nValue);
         this.iArmyWidth = (int)CFG.glyphLayout.width;
      } catch (IndexOutOfBoundsException var3) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var3);
         }
      } catch (NullPointerException var4) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var4);
         }
      } catch (IllegalArgumentException var5) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var5);
         }
      } catch (IllegalStateException var6) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var6);
         }
      }
   }

   protected final void updateArmyWidth(float nValue) {
      try {
         CFG.glyphLayout.setText(CFG.fontArmy, "" + nValue);
         this.iArmyWidth = (int)CFG.glyphLayout.width;
      } catch (IndexOutOfBoundsException var3) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var3);
         }
      } catch (NullPointerException var4) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var4);
         }
      } catch (IllegalArgumentException var5) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var5);
         }
      } catch (IllegalStateException var6) {
         this.iArmyWidth = CFG.TEXT_HEIGHT * 2;
         if (CFG.LOGS) {
            CFG.exceptionStack(var6);
         }
      }
   }

   protected final int getCivID() {
      return this.iCivID;
   }

   protected final void setCivID(int nCivID) {
      this.iCivID = nCivID;
   }

   protected final int getArmy() {
      return this.iArmy;
   }

   protected final void setArmy(int nArmy, int nProvinceID) {
      this.iArmy = Math.max(0, nArmy);
      this.updateArmyWidth_Just(nProvinceID);
   }

   protected int getArmyWidth() {
      return this.iArmyWidth;
   }
}
