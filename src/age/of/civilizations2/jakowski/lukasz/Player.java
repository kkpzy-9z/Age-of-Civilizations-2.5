package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Player {
   protected Save_Player_GameData savePlayer = new Save_Player_GameData();
   private Image flagOfCivilization = null;
   private boolean noOrders;
   private List<Boolean> fogOfWar;
   protected Statistics_Civ_GameData statistics_Civ_GameData;
   protected int iTurnPopulation = 0;
   protected int iTurnEconomy = 0;
   protected int iBefore_PosX;
   protected int iBefore_PosY = -999999;
   protected float fBefore_Scale;
   protected int iBefore_ActiveProvince;
   protected int iACTIVE_VIEW_MODE = -1;
   protected int visible_CivInfo = -1;
   protected boolean visible_ManageInfo = false;
   protected boolean visible_Outliner = false;
   protected int visible_CensusOfProvince = -1;
   protected boolean visible_Wars = false;
   protected int visible_WarStats = -1;
   protected boolean visible_Alliances = false;
   protected int visible_Alliance = -1;
   //new automove feature
   protected boolean armyAImovement = false;
   protected boolean visible_Rank = false;
   protected boolean visible_WorldPop = false;
   protected boolean visible_VictoryConditions = false;
   protected boolean visible_ConqueredProvinces = false;
   protected boolean visible_BuildingsConstructed = false;
   protected boolean visible_RecruitedArmy = false;
   protected boolean visible_Army = false;
   protected boolean visible_Tribute = false;
   protected boolean visible_Technology = false;
   protected boolean visible_MapModes = false;
   protected boolean visible_BuildingsMore = false;
   protected boolean visible_History = false;
   protected boolean visible_HRE = false;

   protected Player(int iCivID) {
      this.setCivID(iCivID);
      this.noOrders = true;
      this.initFogOfWar();
      this.initMetProvince(true);
      this.initMetCivilization(true);
   }

   protected Player(Save_Player_GameData savedPlayer) {
      this.setCivID(savedPlayer.iCivID);
      this.savePlayer = savedPlayer;
      this.noOrders = true;
      this.initFogOfWar();
   }

   protected final float buildPlayerScore() {
      float out = 1.0F;

      for(int i = 0; i < CFG.game.getCiv(this.getCivID()).getNumOfProvinces(); ++i) {
         out += 2.45F * (float)CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(i)).getPopulationData().getPopulation() / (float)CFG.game.getGameScenarios().getScenario_StartingPopulation();
         out += 2.25F * (float)CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(i)).getEconomy() / (float)CFG.game.getGameScenarios().getScenario_StartingEconomy();
      }

      out += 0.075F * (float)CFG.game.getCiv(this.getCivID()).civGameData.iNumOfConqueredProvinces;
      return out;
   }

   protected final void initMetProvince(boolean nValue) {
      this.savePlayer.metProvince = new ArrayList<Boolean>();

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         this.savePlayer.metProvince.add(nValue);
      }

   }

   protected final void initMetCivilization(boolean nValue) {
      this.savePlayer.metCivilization = new ArrayList<Boolean>();

      for(int i = 0; i < CFG.game.getCivsSize(); ++i) {
         this.savePlayer.metCivilization.add(nValue);
      }

   }

   protected final void initFogOfWar() {
      this.fogOfWar = new ArrayList<Boolean>();

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         this.fogOfWar.add(false);
      }

   }

   protected final void buildMetProvincesAndCivs() {
      this.initMetProvince(false);
      this.initMetCivilization(false);

      int i;
      int j;
      for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (this.getFogOfWar(i)) {
            this.savePlayer.metProvince.set(i, true);
            this.savePlayer.metCivilization.set(CFG.game.getProvince(i).getCivID(), true);
            if (!CFG.game.getProvince(i).getSeaProvince() && (CFG.game.getProvince(i).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getAllianceID() == CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getAllianceID()) || CFG.gameAction.hasArmyInProvince(i, this.getCivID()) || CFG.gameAction.hasArmyInProvince_AllianceID(i, CFG.game.getCiv(this.getCivID()).getAllianceID())) {
               for(j = 0; j < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++j) {
                  this.savePlayer.metProvince.set(CFG.game.getProvince(i).getNeighboringProvinces(j), true);
                  this.savePlayer.metCivilization.set(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(j)).getCivID(), true);
               }
            }
         }
      }

      if (CFG.game.getCiv(this.getCivID()).getIsPartOfHolyRomanEmpire()) {
         for(i = 0; i < CFG.holyRomanEmpire_Manager.getHRE().getPrincesSize(); ++i) {
            for(j = 0; j < CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getNumOfProvinces(); ++j) {
               if (CFG.game.getProvince(CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getProvinceID(j)).getIsPartOfHolyRomanEmpire()) {
                  this.savePlayer.metProvince.set(CFG.game.getCiv(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i)).getProvinceID(j), true);
               }
            }

            this.savePlayer.metCivilization.set(CFG.holyRomanEmpire_Manager.getHRE().getPrince(i), true);
         }
      }

      this.buildMetProvinces_BasedOnDistance();

      for(i = 1; i < CFG.game.getCivsSize(); ++i) {
         for(j = 0; j < CFG.game.getCiv(i).getCivRegionsSize(); ++j) {
            int regionMet = 0;
            int regionNotMet = 0;

            int k;
            for(k = 0; k < CFG.game.getCiv(i).getCivRegion(j).getProvincesSize(); ++k) {
               if (this.getMetProvince(CFG.game.getCiv(i).getCivRegion(j).getProvince(k))) {
                  ++regionMet;
               } else {
                  ++regionNotMet;
               }
            }

            if (regionMet > 0 && regionNotMet < 4) {
               for(k = 0; k < CFG.game.getCiv(i).getCivRegion(j).getProvincesSize(); ++k) {
                  this.savePlayer.metProvince.set(CFG.game.getCiv(i).getCivRegion(j).getProvince(k), true);
               }
            }
         }
      }

      for(i = 1; i < CFG.game.getCivsSize(); ++i) {
         if (CFG.game.getCiv(i).getNumOfProvinces() == 0) {
            this.savePlayer.metCivilization.set(i, true);
         }
      }

      for(i = 0; i < CFG.game.getCiv(this.getCivID()).getNumOfProvinces(); ++i) {
         for(j = 0; j < CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(i)).getPopulationData().getNationalitiesSize(); ++j) {
            this.savePlayer.metCivilization.set(CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(i)).getPopulationData().getCivID(j), true);
         }
      }

   }

   protected final void buildMetProvinces_BasedOnDistance() {
      float tempDis = CFG.gameAges.getAge_FogOfWarDiscovery_MetProvinces(Game_Calendar.CURRENT_AGEID);

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!this.getMetProvince(i)) {
            if (!Game_Calendar.getColonizationOfWastelandIsEnabled() && CFG.game.getProvince(i).getWasteland() >= 0) {
               this.savePlayer.metProvince.set(i, true);
               this.savePlayer.metCivilization.set(CFG.game.getProvince(i).getCivID(), true);
            }

            for(int j = 0; j < CFG.game.getCiv(this.getCivID()).getNumOfProvinces(); ++j) {
               if (CFG.game_NextTurnUpdate.getDistanceFromAToB_PercOfMax(CFG.game.getCiv(this.getCivID()).getProvinceID(j), i) * (CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(j)).getContinent() == CFG.game.getProvince(i).getContinent() ? 0.715F : 1.0F) < (tempDis + tempDis * 0.325F * (1.0F - Math.min((float)Math.abs(CFG.game.getProvince(CFG.game.getCiv(this.getCivID()).getProvinceID(j)).getCenterY() - CFG.game.getProvince(i).getCenterY()) / ((float)CFG.map.getMapBG().getHeight() / 10.0F), 1.0F))) * CFG.game.getCiv(this.getCivID()).getTechnologyLevel()) {
                  this.savePlayer.metProvince.set(i, true);
                  this.savePlayer.metCivilization.set(CFG.game.getProvince(i).getCivID(), true);
                  break;
               }
            }
         }
      }

   }

   protected final void loadPlayersFlag(Image tFlag) {
      this.disposePlayersFlag();
      this.flagOfCivilization = tFlag;
   }

   protected final void loadPlayersFlag() {
      this.disposePlayersFlag();
      if (CFG.game.getCiv(this.savePlayer.iCivID).getCivTag().indexOf(59) > 0) {
         CFG.unionFlagsToGenerate_Manager.lFlags.add(new UnionFlagsToGenerate());
         int tGenerateID = CFG.unionFlagsToGenerate_Manager.lFlags.size() - 1;
         String[] tempD = CFG.game.getCiv(this.savePlayer.iCivID).getCivTag().split(";");

         for(int i = 0; i < tempD.length; ++i) {
            ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).lTags.add(tempD[i]);
         }

         ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).typeOfAction = UnionFlagsToGenerate_TypesOfAction.PLAYER_ID;
         ((UnionFlagsToGenerate)CFG.unionFlagsToGenerate_Manager.lFlags.get(tGenerateID)).iID = this.getCivID();
      } else {
         try {
            try {
               this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/flagsH/" + CFG.game.getCiv(this.savePlayer.iCivID).getCivTag() + ".png")), Texture.TextureFilter.Linear);
            } catch (GdxRuntimeException var6) {
               if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(this.savePlayer.iCivID).getIdeologyID()).REVOLUTIONARY) {
                  this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/flagsH/rb" + (CFG.game.getCiv(this.savePlayer.iCivID).getCivID() + CFG.game.getCiv(this.savePlayer.iCivID).getCivTag().charAt(0)) % 6 + ".png")), Texture.TextureFilter.Nearest);
                  return;
               }

               try {
                  this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/flagsH/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + ".png")), Texture.TextureFilter.Linear);
               } catch (GdxRuntimeException var5) {
                  if (CFG.isAndroid()) {
                     try {
                        this.flagOfCivilization = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "_FLH.png")), Texture.TextureFilter.Linear);
                     } catch (GdxRuntimeException var4) {
                        this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "_FLH.png")), Texture.TextureFilter.Linear);
                     }
                  } else {
                     this.flagOfCivilization = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag()) + "_FLH.png")), Texture.TextureFilter.Linear);
                  }
               }
            }
         } catch (RuntimeException | OutOfMemoryError var7) {
            this.disposePlayersFlag();
         }

      }
   }

   protected final void disposePlayersFlag() {
      try {
         if (this.flagOfCivilization != null) {
            this.flagOfCivilization.getTexture().dispose();
            this.flagOfCivilization = null;
         }
      } catch (RuntimeException var2) {
         if (CFG.LOGS) {
            CFG.exceptionStack(var2);
         }
      }

   }

   protected final int getCivID() {
      return this.savePlayer.iCivID;
   }

   protected final void setCivID(int nCivID) {
      try {
         if (this.savePlayer.iCivID >= 0 && this.savePlayer.iCivID < CFG.game.getCivsSize()) {
            CFG.game.getCiv(this.savePlayer.iCivID).setControlledByPlayer(false);
         }

         this.savePlayer.iCivID = nCivID;
         if (this.savePlayer.iCivID >= 0 && this.savePlayer.iCivID < CFG.game.getCivsSize()) {
            CFG.game.getCiv(this.savePlayer.iCivID).setControlledByPlayer(true);
            this.statistics_Civ_GameData = CFG.serviceRibbon_Manager.loadStatistics_Civ(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag());
         }
      } catch (IndexOutOfBoundsException | NullPointerException var3) {
         this.savePlayer.iCivID = nCivID;
      }

   }

   protected final void tryLoadStats() {
      this.statistics_Civ_GameData = CFG.serviceRibbon_Manager.loadStatistics_Civ(CFG.game.getCiv(this.savePlayer.iCivID).getCivTag());
   }

   protected final boolean getNoOrders() {
      return this.noOrders;
   }

   protected final void setNoOrders(boolean noOrders) {
      this.noOrders = noOrders;
   }

   protected final Image getFlag() {
      return this.flagOfCivilization == null ? CFG.game.getCiv(this.savePlayer.iCivID).getFlag() : this.flagOfCivilization;
   }

   protected final boolean getMetProvince(int i) {
      try {
         return (Boolean)this.savePlayer.metProvince.get(i);
      } catch (IndexOutOfBoundsException var3) {
         return true;
      }
   }

   protected final void setMetProvince(int i, boolean met) {
      try {
         this.savePlayer.metProvince.set(i, met);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final boolean getMetCivilization(int i) {
      try {
         return (Boolean)this.savePlayer.metCivilization.get(i);
      } catch (IndexOutOfBoundsException var3) {
         return true;
      }
   }

   protected final void setMetCivilization(int i, boolean met) {
      try {
         this.savePlayer.metCivilization.set(i, met);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final void addMetCivilization(boolean metCiv) {
      this.savePlayer.metCivilization.add(metCiv);
   }

   protected final boolean getMetAlliance(int nAllianceID) {
      for(int i = 0; i < CFG.game.getAlliance(nAllianceID).getCivilizationsSize(); ++i) {
         if (this.getMetCivilization(CFG.game.getAlliance(nAllianceID).getCivilization(i))) {
            return true;
         }
      }

      return false;
   }

   protected final boolean getFogOfWar(int i) {
      try {
         return (Boolean)this.fogOfWar.get(i);
      } catch (IndexOutOfBoundsException var3) {
         CFG.exceptionStack(var3);
         return true;
      }
   }

   protected final void setFogOfWar(int i, boolean isVisible) {
      try {
         this.fogOfWar.set(i, isVisible);
      } catch (IndexOutOfBoundsException var4) {
         CFG.exceptionStack(var4);
      }

   }

   protected final void setFogOfWar_ExtraCheck(int i, boolean isVisible) {
      try {
         this.fogOfWar.set(i, isVisible || CFG.game.getProvince(i).getArmyCivID(this.getCivID()) > 0);
      } catch (IndexOutOfBoundsException var4) {
         CFG.exceptionStack(var4);
      }

   }
}
