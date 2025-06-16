package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class Game_Scenarios {
   protected static int SCENARIOS_SIZE;
   private List lScenarios_TagsList = new ArrayList();
   private List isInternal = new ArrayList();
   private List lScenarios_Names = new ArrayList();
   private List lScenarios_CivNum = new ArrayList();
   private List lScenarios_Authors = new ArrayList();
   private List lScenarios_Age = new ArrayList();
   private List lScenarios_Year = new ArrayList();
   private List lScenarios_Month = new ArrayList();
   private List lScenarios_Day = new ArrayList();
   private List lScenarios_Wikis = new ArrayList();
   private int iScenario_StartingArmyInCapitals = 750;
   private int iScenario_NeutralArmy = 150;
   private int iScenario_StartingPopulation = 65000;
   private int iScenario_StartingEconomy = 32000;
   private int iScenario_StartingMoney = 4500;
   private float iScenario_PopulationGrowthRate_Modifier = 0.0F;
   private float iScenario_EconomyGrowthRate_Modifier = 0.0F;
   private float iScenario_DiseasesDeathRate_Modifier = 0.0F;
   private String sScenario_ActivePallet_TAG = null;
   protected String sActiveScenarioTag = "";
   protected static final float PERC_OF_POPULATION_REQUIRED_TO_GET_A_CORE = 0.18F;

   protected final void loadGame_Scenarios(boolean initMap) {
      if (SCENARIOS_SIZE > 0 || this.lScenarios_TagsList.size() > 0) {
         this.disposeScenarios();
      }

      String defaultScenario = null;
      String[] tagsSPLITED = null;
      if (CFG.isDesktop()) {
         List tempFiles = CFG.getFileNames("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/");
         int i = 0;

         int iSize;
         for(iSize = tempFiles.size(); i < iSize; ++i) {
            if (((String)tempFiles.get(i)).equals("Age_of_Civilizations")) {
               tempFiles.remove(i);
               break;
            }
         }

         tagsSPLITED = new String[tempFiles.size()];
         i = 0;

         for(iSize = tempFiles.size(); i < iSize; ++i) {
            tagsSPLITED[i] = (String)tempFiles.get(i);
         }
      } else {
         FileHandle tempFileT = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
         String tempT = tempFileT.readString();
         tagsSPLITED = tempT.split(";");
      }

      List tempScenarios_TagsList = new ArrayList();
      List tempIsInternal = new ArrayList();
      List tempScenarios_Names = new ArrayList();
      List tempScenarios_CivNum = new ArrayList();
      List tempScenarios_Authors = new ArrayList();
      List tempScenarios_Age = new ArrayList();
      List tempScenarios_Year = new ArrayList();
      List tempScenarios_Month = new ArrayList();
      List tempScenarios_Day = new ArrayList();
      List tempScenarios_Wikis = new ArrayList();
      int i = 0;

      for(int i2 = tagsSPLITED.length; i < i2; ++i) {
         tempScenarios_TagsList.add(tagsSPLITED[i]);
         tempIsInternal.add(true);
      }

      for(i = 0; i < tempScenarios_TagsList.size(); ++i) {
         try {
            new CFG.ConfigScenarioInfo();
            Json json = new Json();
            json.setElementType(CFG.ConfigScenarioInfo.class, "Data_Scenario_Info", CFG.Data_Scenario_Info.class);
            CFG.ConfigScenarioInfo data = (CFG.ConfigScenarioInfo)json.fromJson(CFG.ConfigScenarioInfo.class, Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)tempScenarios_TagsList.get(i) + "/" + (String)tempScenarios_TagsList.get(i) + "_INFO" + ".json").reader("UTF8"));
            Iterator var17 = data.Data_Scenario_Info.iterator();
            if (var17.hasNext()) {
               Object e = var17.next();
               CFG.Data_Scenario_Info tempData = (CFG.Data_Scenario_Info)e;
               tempScenarios_CivNum.add(tempData.Civs);
               tempScenarios_Names.add(tempData.Name);
               tempScenarios_Authors.add(tempData.Author);
               tempScenarios_Wikis.add(tempData.Wiki);
               tempScenarios_Age.add(tempData.Age);
               tempScenarios_Year.add(tempData.Year);
               tempScenarios_Month.add(tempData.Month);
               tempScenarios_Day.add(tempData.Day);
            }
         } catch (GdxRuntimeException var27) {
            if (CFG.LOGS) {
               CFG.exceptionStack(var27);
            }

            tempScenarios_CivNum.add(0);
            tempScenarios_Names.add("ERROR");
            tempScenarios_Authors.add("ERROR");
            tempScenarios_Wikis.add("");
            tempScenarios_Age.add(0);
            tempScenarios_Year.add(0);
            tempScenarios_Month.add(0);
            tempScenarios_Day.add(0);
         }
      }

      if (CFG.readLocalFiles()) {
         try {
            FileHandle tempFileT2 = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "Age_of_Civilizations");
            String tempT2 = tempFileT2.readString();
            String[] tagsSPLITED2 = tempT2.split(";");
            int nStart = tempScenarios_TagsList.size();
            i = 0;

            for(int iSize = tagsSPLITED2.length; i < iSize; ++i) {
               tempScenarios_TagsList.add(tagsSPLITED2[i]);
               tempIsInternal.add(false);
            }

            for(i = nStart; i < tempScenarios_TagsList.size(); ++i) {
               FileHandle file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)tempScenarios_TagsList.get(i) + "/" + (String)tempScenarios_TagsList.get(i) + "_INFO" + ".json");
               String fileContent = file.readString();
               Json json = new Json();
               json.setElementType(CFG.ConfigScenarioInfo.class, "Data_Scenario_Info", CFG.Data_Scenario_Info.class);
               new CFG.ConfigScenarioInfo();
               CFG.ConfigScenarioInfo data = (CFG.ConfigScenarioInfo)json.fromJson(CFG.ConfigScenarioInfo.class, fileContent);
               Iterator var23 = data.Data_Scenario_Info.iterator();
               if (var23.hasNext()) {
                  Object e = var23.next();
                  CFG.Data_Scenario_Info tempData = (CFG.Data_Scenario_Info)e;
                  tempScenarios_CivNum.add(tempData.Civs);
                  tempScenarios_Names.add(tempData.Name);
                  tempScenarios_Authors.add(tempData.Author);
                  tempScenarios_Wikis.add(tempData.Wiki);
                  tempScenarios_Age.add(tempData.Age);
                  tempScenarios_Year.add(tempData.Year);
                  tempScenarios_Month.add(tempData.Month);
                  tempScenarios_Day.add(tempData.Day);
               }
            }
         } catch (GdxRuntimeException var26) {
         }
      }

      if (CFG.game.getScenarioID() == -1) {
         defaultScenario = (String)tempScenarios_TagsList.get(0);
         CFG.game.setScenarioID(0);
      }

      while(tempScenarios_TagsList.size() > 0) {
         i = 0;

         for(int i2 = 1; i2 < tempScenarios_TagsList.size(); ++i2) {
            if ((Integer)tempScenarios_Year.get(i) < (Integer)tempScenarios_Year.get(i2)) {
               i = i2;
            }
         }

         this.lScenarios_TagsList.add((String)tempScenarios_TagsList.get(i));
         tempScenarios_TagsList.remove(i);
         this.isInternal.add((Boolean)tempIsInternal.get(i));
         tempIsInternal.remove(i);
         this.lScenarios_CivNum.add((Integer)tempScenarios_CivNum.get(i));
         tempScenarios_CivNum.remove(i);
         this.lScenarios_Names.add((String)tempScenarios_Names.get(i));
         tempScenarios_Names.remove(i);
         this.lScenarios_Authors.add((String)tempScenarios_Authors.get(i));
         tempScenarios_Authors.remove(i);
         this.lScenarios_Wikis.add((String)tempScenarios_Wikis.get(i));
         tempScenarios_Wikis.remove(i);
         this.lScenarios_Age.add((Integer)tempScenarios_Age.get(i));
         tempScenarios_Age.remove(i);
         this.lScenarios_Year.add((Integer)tempScenarios_Year.get(i));
         tempScenarios_Year.remove(i);
         this.lScenarios_Month.add((Integer)tempScenarios_Month.get(i));
         tempScenarios_Month.remove(i);
         this.lScenarios_Day.add((Integer)tempScenarios_Day.get(i));
         tempScenarios_Day.remove(i);
      }

      if (defaultScenario != null) {
         for(i = 0; i < this.lScenarios_TagsList.size(); ++i) {
            if (defaultScenario.equals(this.lScenarios_TagsList.get(i))) {
               CFG.game.setScenarioID(i);
               break;
            }
         }
      }

      SCENARIOS_SIZE = this.lScenarios_TagsList.size();
      if (initMap) {
         CFG.game.updateDaultScenarioID_ForMap();
      }

   }

   protected final void disposeScenarios() {
      this.lScenarios_TagsList.clear();
      this.lScenarios_TagsList = new ArrayList();
      this.lScenarios_Names.clear();
      this.lScenarios_Names = new ArrayList();
      this.lScenarios_CivNum.clear();
      this.lScenarios_CivNum = new ArrayList();
      this.lScenarios_Authors.clear();
      this.lScenarios_Authors = new ArrayList();
      this.lScenarios_Wikis.clear();
      this.lScenarios_Wikis = new ArrayList();
      this.lScenarios_Age.clear();
      this.lScenarios_Age = new ArrayList();
      this.lScenarios_Year.clear();
      this.lScenarios_Year = new ArrayList();
      this.lScenarios_Month.clear();
      this.lScenarios_Month = new ArrayList();
      this.lScenarios_Day.clear();
      this.lScenarios_Day = new ArrayList();
      this.isInternal.clear();
      this.isInternal = new ArrayList();
      SCENARIOS_SIZE = 0;
   }

   protected final List loadCivilizations_RandomGame() {
      Random oR = new Random();
      List lCivs = new ArrayList();
      lCivs.add(CFG.game.getNeutralCivilization());
      ((Civilization)lCivs.get(0)).setCivID(0);
      List lRandomGameCivsTags = new ArrayList();
      FileHandle tempFileT;
      String tempT;
      String[] tagsSPLITED;
      String[] tagsSPLITED_ED;
      FileHandle tempFileT_ED;
      String tempTag;
      int o;
      int i = 0;
      int iNumOfItterations;
      int tRandID;
      int nTagIDToAdd;
      ArrayList nCivsTags;
      boolean found;
      if (CFG.RANDOM_PLACMENT) {
         tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
         tempT = tempFileT.readString();
         tagsSPLITED = tempT.split(";");
         tagsSPLITED_ED = new String[0];

         try {
            tempFileT_ED = null;
            if (CFG.isAndroid()) {
               tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
            } else {
               tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
            }

            tempTag = tempFileT_ED.readString();
            tagsSPLITED_ED = tempTag.split(";");
         } catch (GdxRuntimeException var49) {
         }

         nCivsTags = new ArrayList();

         for(o = tagsSPLITED.length; i < o; ++i) {
            if (!CFG.randomGameManager.isTagTaken(tagsSPLITED[i])) {
               nCivsTags.add(tagsSPLITED[i]);
            }
         }

         i = 0;

         for(o = tagsSPLITED_ED.length; i < o; ++i) {
            if (!CFG.randomGameManager.isTagTaken(tagsSPLITED_ED[i])) {
               nCivsTags.add(tagsSPLITED[i]);
            }
         }

         for(i = 0; i < CFG.randomGameManager.getPlayersSize(); ++i) {
            if (CFG.randomGameManager.getPlayer(i).getTag() != null) {
               lRandomGameCivsTags.add(CFG.randomGameManager.getPlayer(i).getTag());
            } else {
               o = oR.nextInt(nCivsTags.size());
               lRandomGameCivsTags.add((String)nCivsTags.get(o));
               nCivsTags.remove(o);
            }
         }

         boolean add;
         try {
            i = 0;

            for(add = false; i < CFG.randomGameManager.getCivilizationsSize(); ++i) {
               o = oR.nextInt(nCivsTags.size());
               lRandomGameCivsTags.add((String)nCivsTags.get(o));
               nCivsTags.remove(o);
            }
         } catch (IllegalArgumentException var59) {
         }

         try {
            tempTag = null;
            add = true;
            i = 0;

            for(int i2 = lRandomGameCivsTags.size(); i < i2; ++i) {
               iNumOfItterations = oR.nextInt(CFG.ideologiesManager.getIdeologiesSize());

               for(tRandID = 0; (CFG.ideologiesManager.getIdeology(iNumOfItterations).REVOLUTIONARY || CFG.ideologiesManager.getIdeology(iNumOfItterations).CAN_BECOME_CIVILIZED >= 0) && tRandID++ < 8; iNumOfItterations = oR.nextInt(CFG.ideologiesManager.getIdeologiesSize())) {
               }

               add = true;
               tempTag = CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)) + CFG.ideologiesManager.getIdeology(iNumOfItterations).getExtraTag();

               int tCapital;
               for(tCapital = i + 1; tCapital < i; ++tCapital) {
                  if (tempTag.equals(lRandomGameCivsTags.get(tCapital))) {
                     add = false;
                     break;
                  }
               }

               if (add) {
                  for(tCapital = i - 1; tCapital >= 0; --tCapital) {
                     if (tempTag.equals(lRandomGameCivsTags.get(tCapital))) {
                        add = false;
                        break;
                     }
                  }

                  if (add) {
                     lRandomGameCivsTags.set(i, tempTag);
                  }
               }

               FileHandle fileCiv;
               Civilization_GameData3 tempCivData;
               try {
                  try {
                     fileCiv = Gdx.files.internal("game/civilizations/" + (String)lRandomGameCivsTags.get(i));
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  } catch (GdxRuntimeException var47) {
                     fileCiv = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)));
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                     o = CFG.ideologiesManager.getIdeologyID((String)lRandomGameCivsTags.get(i));
                     Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(o).getColor().r, CFG.ideologiesManager.getIdeology(o).getColor().g, CFG.ideologiesManager.getIdeology(o).getColor().b, 0.225F));
                     tempCivData.setR((int)(tempColor.r * 255.0F));
                     tempCivData.setG((int)(tempColor.g * 255.0F));
                     tempCivData.setB((int)(tempColor.b * 255.0F));
                  }
               } catch (GdxRuntimeException var48) {
                  try {
                     fileCiv = Gdx.files.local("game/civilizations/" + (String)lRandomGameCivsTags.get(i));
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  } catch (GdxRuntimeException var46) {
                     try {
                        fileCiv = Gdx.files.local("game/civilizations/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)));
                        tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                        nTagIDToAdd = CFG.ideologiesManager.getIdeologyID((String)lRandomGameCivsTags.get(i));
                        Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(nTagIDToAdd).getColor().r, CFG.ideologiesManager.getIdeology(nTagIDToAdd).getColor().g, CFG.ideologiesManager.getIdeology(nTagIDToAdd).getColor().b, 0.225F));
                        tempCivData.setR((int)(tempColor.r * 255.0F));
                        tempCivData.setG((int)(tempColor.g * 255.0F));
                        tempCivData.setB((int)(tempColor.b * 255.0F));
                     } catch (GdxRuntimeException var45) {
                        try {
                           if (CFG.isAndroid()) {
                              try {
                                 fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)) + "/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)));
                                 tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              } catch (GdxRuntimeException var43) {
                                 fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)) + "/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)));
                                 tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              }
                           } else {
                              fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)) + "/" + CFG.ideologiesManager.getRealTag((String)lRandomGameCivsTags.get(i)));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           }
                        } catch (GdxRuntimeException var44) {
                           Color tempC = CFG.getRandomColor();
                           tempCivData = new Civilization_GameData3("ran", (int)(tempC.r * 255.0F), (int)(tempC.g * 255.0F), (int)(tempC.b * 255.0F));
                        }
                     }
                  }
               }

               found = false;
               if (i < CFG.randomGameManager.getPlayersSize() && CFG.randomGameManager.getPlayer(i).getCapitalProvinceID() >= 0) {
                  tCapital = CFG.randomGameManager.getPlayer(i).getCapitalProvinceID();
               } else {
                  tCapital = -1;
               }

               lCivs.add(new Civilization((String)lRandomGameCivsTags.get(i), tempCivData.getR(), tempCivData.getG(), tempCivData.getB(), tCapital, i + 1));
               ((Civilization)lCivs.get(i + 1)).setCivID(i + 1);
               ((Civilization)lCivs.get(i + 1)).setTechnologyLevel((float)(20 + Math.min(5 * Game_Calendar.CURRENT_AGEID, 25) + oR.nextInt(10)) / 100.0F);
               ((Civilization)lCivs.get(i + 1)).setHappiness(68 + oR.nextInt(16));
               if (tCapital >= 0) {
                  CFG.game.getProvince(((Civilization)lCivs.get(i + 1)).getCapitalProvinceID()).setCivID_LoadScenario(i + 1);
               }

               ((Civilization)lCivs.get(i + 1)).setMoney((long)CFG.game.getGameScenarios().getScenario_StartingMoney());
            }
         } catch (ClassNotFoundException var57) {
         } catch (IOException var58) {
         }
      } else {
         tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
         tempT = tempFileT.readString();
         tagsSPLITED = tempT.split(";");
         tagsSPLITED_ED = new String[0];

         try {
            tempFileT_ED = null;
            if (CFG.isAndroid()) {
               tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
            } else {
               tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
            }

            tempTag = tempFileT_ED.readString();
            tagsSPLITED_ED = tempTag.split(";");
         } catch (GdxRuntimeException var42) {
         }

         nCivsTags = new ArrayList();
         List civsToAdd = new ArrayList();
         o = 0;

         for(i = tagsSPLITED.length; o < i; ++o) {
            if (!CFG.randomGameManager.isTagTaken(tagsSPLITED[o])) {
               nCivsTags.add(tagsSPLITED[o]);
            }
         }

         o = 0;

         for(i = tagsSPLITED_ED.length; o < i; ++o) {
            if (!CFG.randomGameManager.isTagTaken(tagsSPLITED_ED[o])) {
               nCivsTags.add(tagsSPLITED[o]);
            }
         }

         for(o = 0; o < CFG.randomGameManager.getPlayersSize(); ++o) {
            if (CFG.randomGameManager.getPlayer(o).getTag() != null) {
               civsToAdd.add(new RandomGame_AoCMode(CFG.randomGameManager.getPlayer(o).getTag(), CFG.randomGameManager.getPlayer(o).getCapitalProvinceID()));
            } else if (CFG.randomGameManager.getPlayer(o).getCapitalProvinceID() >= 0) {
               i = oR.nextInt(nCivsTags.size());
               civsToAdd.add(new RandomGame_AoCMode((String)nCivsTags.get(i), CFG.randomGameManager.getPlayer(o).getCapitalProvinceID()));
               nCivsTags.remove(i);
            }
         }

         int tempCapitalID;
         for(o = 0; o < civsToAdd.size(); ++o) {
            try {
               Civilization_GameData3 tempCivData;
               FileHandle fileCiv;
               try {
                  try {
                     fileCiv = Gdx.files.internal("game/civilizations/" + ((RandomGame_AoCMode)civsToAdd.get(o)).sTag);
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  } catch (GdxRuntimeException var38) {
                     fileCiv = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag));
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                     tempCapitalID = CFG.ideologiesManager.getIdeologyID(((RandomGame_AoCMode)civsToAdd.get(o)).sTag);
                     Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(tempCapitalID).getColor().r, CFG.ideologiesManager.getIdeology(tempCapitalID).getColor().g, CFG.ideologiesManager.getIdeology(tempCapitalID).getColor().b, 0.225F));
                     tempCivData.setR((int)(tempColor.r * 255.0F));
                     tempCivData.setG((int)(tempColor.g * 255.0F));
                     tempCivData.setB((int)(tempColor.b * 255.0F));
                  }
               } catch (GdxRuntimeException var39) {
                  try {
                     fileCiv = Gdx.files.local("game/civilizations/" + ((RandomGame_AoCMode)civsToAdd.get(o)).sTag);
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  } catch (GdxRuntimeException var37) {
                     try {
                        fileCiv = Gdx.files.local("game/civilizations/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag));
                        tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                        iNumOfItterations = CFG.ideologiesManager.getIdeologyID(((RandomGame_AoCMode)civsToAdd.get(o)).sTag);
                        Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(iNumOfItterations).getColor().r, CFG.ideologiesManager.getIdeology(iNumOfItterations).getColor().g, CFG.ideologiesManager.getIdeology(iNumOfItterations).getColor().b, 0.225F));
                        tempCivData.setR((int)(tempColor.r * 255.0F));
                        tempCivData.setG((int)(tempColor.g * 255.0F));
                        tempCivData.setB((int)(tempColor.b * 255.0F));
                     } catch (GdxRuntimeException var36) {
                        try {
                           if (CFG.isAndroid()) {
                              try {
                                 fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag) + "/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag));
                                 tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              } catch (GdxRuntimeException var34) {
                                 fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag) + "/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag));
                                 tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              }
                           } else {
                              fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag) + "/" + CFG.ideologiesManager.getRealTag(((RandomGame_AoCMode)civsToAdd.get(o)).sTag));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           }
                        } catch (GdxRuntimeException var35) {
                           Color tempC = CFG.getRandomColor();
                           tempCivData = new Civilization_GameData3("ran", (int)(tempC.r * 255.0F), (int)(tempC.g * 255.0F), (int)(tempC.b * 255.0F));
                        }
                     }
                  }
               }

               int tCapital = ((RandomGame_AoCMode)civsToAdd.get(o)).iCapitalID;
               lCivs.add(new Civilization(((RandomGame_AoCMode)civsToAdd.get(o)).sTag, tempCivData.getR(), tempCivData.getG(), tempCivData.getB(), tCapital, o + 1));
               ((Civilization)lCivs.get(o + 1)).setCivID(o + 1);
               ((Civilization)lCivs.get(o + 1)).setTechnologyLevel((float)(20 + Math.min(5 * Game_Calendar.CURRENT_AGEID, 25) + oR.nextInt(10)) / 100.0F);
               ((Civilization)lCivs.get(o + 1)).setHappiness(68 + oR.nextInt(16));
               if (tCapital >= 0) {
                  CFG.game.getProvince(((Civilization)lCivs.get(o + 1)).getCapitalProvinceID()).setCivID_LoadScenario(o + 1);
               }

               ((Civilization)lCivs.get(o + 1)).setMoney((long)CFG.game.getGameScenarios().getScenario_StartingMoney());
            } catch (ClassNotFoundException var40) {
            } catch (IOException var41) {
            }
         }

         List lPossibleCapitals = new ArrayList();

         for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince()) {
               CFG.game.getProvince(i).setIsCapital(false);
            }
         }

         for(i = 0; i < CFG.randomGameManager.getPlayersSize(); ++i) {
            if (CFG.randomGameManager.getPlayer(i).getCapitalProvinceID() >= 0) {
               CFG.game.getProvince(CFG.randomGameManager.getPlayer(i).getCapitalProvinceID()).setIsCapital(true);
            }
         }

         for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
            if (!CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0 && !CFG.game.getProvince(i).getIsCapital()) {
               try {
                  if (Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + i).exists()) {
                     lPossibleCapitals.add(i);
                  }
               } catch (GdxRuntimeException var33) {
               }
            }
         }

         try {
            i = civsToAdd.size() - CFG.randomGameManager.getPlayersSize();
            i = 0;

            for(int nR = 0; i < CFG.randomGameManager.getCivilizationsSize() + i && lPossibleCapitals.size() > 0; ++i) {
               try {
                  tempCapitalID = 0;
                  iNumOfItterations = 0;

                  while(true) {
                     int tCapital;
                     int tCivID;
                     ArrayList lPossibleCapitals_Tags;
                     while(true) {
                        label360:
                        do {
                           while(true) {
                              tRandID = CFG.oR.nextInt(lPossibleCapitals.size());
                              tempCapitalID = (Integer)lPossibleCapitals.get(tRandID);
                              ++iNumOfItterations;
                              if (!CFG.game.getProvince(tempCapitalID).getIsCapital()) {
                                 found = true;

                                 for(o = 0; o < CFG.game.getProvince(tempCapitalID).getNeighboringProvincesSize(); ++o) {
                                    if (CFG.game.getProvince(CFG.game.getProvince(tempCapitalID).getNeighboringProvinces(o)).getIsCapital_Just()) {
                                       found = false;
                                       continue label360;
                                    }
                                 }
                                 break;
                              }

                              lPossibleCapitals.remove(tRandID);
                           }
                        } while(!found && iNumOfItterations <= 18);

                        found = false;
                        lPossibleCapitals_Tags = new ArrayList();

                        try {
                           FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "suggested_owners/" + lPossibleCapitals.get(tRandID));
                           String sOwners = file.readString();
                           String[] sRes = sOwners.split(";");

                           for(tCapital = 0; tCapital < sRes.length; tCapital += 2) {
                              tCivID = CFG.ideologiesManager.getIdeologyID(sRes[tCapital]);
                              if (CFG.ideologiesManager.getIdeology(tCivID).CAN_BECOME_CIVILIZED >= 0) {
                                 lPossibleCapitals_Tags.add(CFG.ideologiesManager.getRealTag(sRes[tCapital]));
                              } else {
                                 lPossibleCapitals_Tags.add(sRes[tCapital]);
                              }
                           }

                           for(tCapital = lPossibleCapitals_Tags.size() - 1; tCapital >= 0; --tCapital) {
                              for(tCivID = civsToAdd.size() - 1; tCivID >= 0; --tCivID) {
                                 if (((RandomGame_AoCMode)civsToAdd.get(tCivID)).sTag.equals(lPossibleCapitals_Tags.get(tCapital))) {
                                    lPossibleCapitals_Tags.remove(tCapital);
                                    break;
                                 }
                              }
                           }

                           if (lPossibleCapitals_Tags.size() != 0) {
                              found = true;
                              break;
                           }

                           lPossibleCapitals.remove(tRandID);
                        } catch (GdxRuntimeException var52) {
                           lPossibleCapitals.remove(tRandID);
                        }
                     }

                     if (!found) {
                        break;
                     }

                     try {
                        nTagIDToAdd = CFG.oR.nextInt(lPossibleCapitals_Tags.size());

                        FileHandle fileCiv;
                        Civilization_GameData3 tempCivData;
                        try {
                           try {
                              fileCiv = Gdx.files.internal("game/civilizations/" + (String)lPossibleCapitals_Tags.get(nTagIDToAdd));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           } catch (GdxRuntimeException var31) {
                              fileCiv = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              tCivID = CFG.ideologiesManager.getIdeologyID((String)lPossibleCapitals_Tags.get(nTagIDToAdd));
                              Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(tCivID).getColor().r, CFG.ideologiesManager.getIdeology(tCivID).getColor().g, CFG.ideologiesManager.getIdeology(tCivID).getColor().b, 0.225F));
                              tempCivData.setR((int)(tempColor.r * 255.0F));
                              tempCivData.setG((int)(tempColor.g * 255.0F));
                              tempCivData.setB((int)(tempColor.b * 255.0F));
                           }
                        } catch (GdxRuntimeException var32) {
                           try {
                              fileCiv = Gdx.files.local("game/civilizations/" + (String)lPossibleCapitals_Tags.get(nTagIDToAdd));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           } catch (GdxRuntimeException var30) {
                              try {
                                 fileCiv = Gdx.files.local("game/civilizations/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)));
                                 tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                                 int tempIdeologyID = CFG.ideologiesManager.getIdeologyID((String)lPossibleCapitals_Tags.get(nTagIDToAdd));
                                 Color tempColor = CFG.getColorMixed(new Color((float)tempCivData.getR() / 255.0F, (float)tempCivData.getG() / 255.0F, (float)tempCivData.getB() / 255.0F, 0.775F), new Color(CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().r, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().g, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().b, 0.225F));
                                 tempCivData.setR((int)(tempColor.r * 255.0F));
                                 tempCivData.setG((int)(tempColor.g * 255.0F));
                                 tempCivData.setB((int)(tempColor.b * 255.0F));
                              } catch (GdxRuntimeException var29) {
                                 try {
                                    if (CFG.isAndroid()) {
                                       try {
                                          fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)) + "/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)));
                                          tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                                       } catch (GdxRuntimeException var27) {
                                          fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)) + "/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)));
                                          tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                                       }
                                    } else {
                                       fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)) + "/" + CFG.ideologiesManager.getRealTag((String)lPossibleCapitals_Tags.get(nTagIDToAdd)));
                                       tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                                    }
                                 } catch (GdxRuntimeException var28) {
                                    Color tempC = CFG.getRandomColor();
                                    tempCivData = new Civilization_GameData3("ran", (int)(tempC.r * 255.0F), (int)(tempC.g * 255.0F), (int)(tempC.b * 255.0F));
                                 }
                              }
                           }
                        }

                        tCapital = (Integer)lPossibleCapitals.get(tRandID);
                        civsToAdd.add(new RandomGame_AoCMode((String)lPossibleCapitals_Tags.get(nTagIDToAdd), tCapital));
                        tCivID = lCivs.size();
                        lCivs.add(new Civilization((String)lPossibleCapitals_Tags.get(nTagIDToAdd), tempCivData.getR(), tempCivData.getG(), tempCivData.getB(), tCapital, tCivID));
                        ((Civilization)lCivs.get(tCivID)).setCivID(tCivID);
                        ((Civilization)lCivs.get(tCivID)).setTechnologyLevel((float)(20 + Math.min(5 * Game_Calendar.CURRENT_AGEID, 25) + oR.nextInt(10)) / 100.0F);
                        ((Civilization)lCivs.get(tCivID)).setHappiness(68 + oR.nextInt(16));
                        if (tCapital >= 0) {
                           CFG.game.getProvince(((Civilization)lCivs.get(tCivID)).getCapitalProvinceID()).setCivID_LoadScenario(tCivID);
                           CFG.game.getProvince(tCapital).setIsCapital(true);
                        }

                        ((Civilization)lCivs.get(tCivID)).setMoney((long)CFG.game.getGameScenarios().getScenario_StartingMoney());
                        lPossibleCapitals.remove(tRandID);
                        break;
                     } catch (ClassNotFoundException var50) {
                        lPossibleCapitals.remove(tRandID);
                     } catch (IOException var51) {
                        lPossibleCapitals.remove(tRandID);
                     }
                  }
               } catch (StackOverflowError var53) {
                  CFG.exceptionStack(var53);
               }
            }
         } catch (IllegalArgumentException var54) {
            CFG.exceptionStack(var54);
         } catch (IndexOutOfBoundsException var55) {
            CFG.exceptionStack(var55);
         } catch (NullPointerException var56) {
            CFG.exceptionStack(var56);
         }
      }

      return lCivs;
   }

   protected final List loadCivilizationsLoadGame(List nCivsData, int startCivID) {
      Game_Calendar.updateAge(false);
      List lCivs = new ArrayList();
      if (startCivID == 0) {
         lCivs.add(CFG.game.getNeutralCivilization());
         ((Civilization)lCivs.get(0)).setCivID(0);
      }

      for(int i = 0; i < nCivsData.size(); ++i) {
         lCivs.add(new Civilization((Save_Civ_GameData)nCivsData.get(i), startCivID + i + (startCivID == 0 ? 1 : 0)));
      }

      CFG.map.getMapBG().disposeMinimapOfCivilizations();
      return lCivs;
   }

   protected final List<Civilization> loadCivilizations(final boolean nEditor) {
      CFG.FILL_THE_MAP = true;
      Game_Calendar.CURRENT_AGEID = this.getScenarioAge(CFG.game.getScenarioID());
      final List<Civilization> lCivs = new ArrayList<Civilization>();
      lCivs.add(CFG.game.getNeutralCivilization());
      lCivs.get(0).setCivID(0);
      FileHandle file;
      FileHandle fileProvince;
      if ((boolean)this.isInternal.get(CFG.game.getScenarioID())) {
         file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()));
         fileProvince = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_PD");
      }
      else {
         file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()));
         fileProvince = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_PD");
      }
      try {
         Scenario_GameData tempScenarioGameData = (Scenario_GameData)CFG.deserialize(file.readBytes());
         this.setScenario_StartingArmyInCapitals(tempScenarioGameData.getStartingArmyInCapitals());
         this.setScenario_NeutralArmy(tempScenarioGameData.getNeutralArmy());
         this.setScenario_StartingPopulation(tempScenarioGameData.getStartingPopulation());
         this.setScenario_StartingEconomy(tempScenarioGameData.getStartingEconomy());
         this.setScenario_StartingMoney(tempScenarioGameData.getStartingMoney());
         this.setScenario_PopulationGrowthRate_Modifier(tempScenarioGameData.getPopulationGrowthRate_Modifier());
         this.setScenario_EconomyGrowthRate_Modifier(tempScenarioGameData.getEconomyGrowthRate_Modifier());
         this.setScenario_DiseasesDeathRate_Modifier(tempScenarioGameData.getDiseasesDeathRate_Modifier());
         this.setScenario_ActivePallet_TAG(tempScenarioGameData.getActivePalletOfColors_TAG());
         Game_Calendar.ENABLE_COLONIZATION = tempScenarioGameData.getColonization();
         Game_Calendar.ENABLE_COLONIZATION_NEUTRAL_PROVINCES = tempScenarioGameData.ENABLE_COLONIZATION_NEUTRAL_PROVINCES;
         Game_Calendar.COLONIZATION_TECH_LEVEL = tempScenarioGameData.COLONIZATION_TECH_LEVEL;
         for (int i = 0; i < tempScenarioGameData.getCivSize(); ++i) {
            Civilization_GameData3 tempCivData;
            try {
               try {
                  final FileHandle fileCiv = Gdx.files.internal("game/civilizations/" + tempScenarioGameData.getCivTag(i));
                  tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
               }
               catch (final GdxRuntimeException e) {
                  final FileHandle fileCiv = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)));
                  tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  final int tempIdeologyID = CFG.ideologiesManager.getIdeologyID(tempScenarioGameData.getCivTag(i));
                  final Color tempColor = CFG.getColorMixed(new Color(tempCivData.getR() / 255.0f, tempCivData.getG() / 255.0f, tempCivData.getB() / 255.0f, 0.775f), new Color(CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().r, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().g, CFG.ideologiesManager.getIdeology(tempIdeologyID).getColor().b, 0.225f));
                  tempCivData.setR((int)(tempColor.r * 255.0f));
                  tempCivData.setG((int)(tempColor.g * 255.0f));
                  tempCivData.setB((int)(tempColor.b * 255.0f));
               }
            }
            catch (final GdxRuntimeException ex) {
               try {
                  final FileHandle fileCiv = Gdx.files.local("game/civilizations/" + tempScenarioGameData.getCivTag(i));
                  tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
               }
               catch (final GdxRuntimeException e2) {
                  try {
                     final FileHandle fileCiv = Gdx.files.local("game/civilizations/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)));
                     tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                     final int tempIdeologyID2 = CFG.ideologiesManager.getIdeologyID(tempScenarioGameData.getCivTag(i));
                     final Color tempColor2 = CFG.getColorMixed(new Color(tempCivData.getR() / 255.0f, tempCivData.getG() / 255.0f, tempCivData.getB() / 255.0f, 0.775f), new Color(CFG.ideologiesManager.getIdeology(tempIdeologyID2).getColor().r, CFG.ideologiesManager.getIdeology(tempIdeologyID2).getColor().g, CFG.ideologiesManager.getIdeology(tempIdeologyID2).getColor().b, 0.225f));
                     tempCivData.setR((int)(tempColor2.r * 255.0f));
                     tempCivData.setG((int)(tempColor2.g * 255.0f));
                     tempCivData.setB((int)(tempColor2.b * 255.0f));
                  }
                  catch (final GdxRuntimeException eee) {
                     try {
                        if (CFG.isAndroid()) {
                           try {
                              final FileHandle fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)) + "/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           }
                           catch (final GdxRuntimeException erq) {
                              final FileHandle fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)) + "/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)));
                              tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           }
                        }
                        else {
                           final FileHandle fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)) + "/" + CFG.ideologiesManager.getRealTag(tempScenarioGameData.getCivTag(i)));
                           tempCivData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                        }
                     }
                     catch (final GdxRuntimeException exr) {
                        final Color tempC = CFG.getRandomColor();
                        tempCivData = new Civilization_GameData3("ran", (int)(tempC.r * 255.0f), (int)(tempC.g * 255.0f), (int)(tempC.b * 255.0f));
                     }
                  }
               }
            }
            lCivs.add(new Civilization(tempScenarioGameData.getCivTag(i), tempCivData.getR(), tempCivData.getG(), tempCivData.getB(), tempScenarioGameData.getCivCapital(i), i + 1));
            lCivs.get(i + 1).setCivID(i + 1);
            lCivs.get(i + 1).setTechnologyLevel(tempScenarioGameData.getTechnologyLevel(i));
            lCivs.get(i + 1).setHappiness(tempScenarioGameData.getHappiness(i));
            if (nEditor) {
               lCivs.get(i + 1).setMoney((-999999 == tempScenarioGameData.getStartingMoneyCiv(i)) ? -999999 : tempScenarioGameData.getStartingMoneyCiv(i));
            }
            else {
               lCivs.get(i + 1).setMoney((-999999 == tempScenarioGameData.getStartingMoneyCiv(i)) ? ((CFG.ideologiesManager.getIdeology(lCivs.get(i + 1).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) ? (tempScenarioGameData.getStartingMoney() / 10) : tempScenarioGameData.getStartingMoney()) : tempScenarioGameData.getStartingMoneyCiv(i));
               //patch for previous version where vassals put lord in debt
               if (lCivs.get(i + 1).getMoney() < 0) {
                  lCivs.get(i + 1).setMoney(tempScenarioGameData.getStartingMoney());
               }
            }
            if (lCivs.get(i + 1).getCapitalProvinceID() >= 0) {
               CFG.game.getProvince(lCivs.get(i + 1).getCapitalProvinceID()).setCivID_LoadScenario(i + 1);
            }
         }
         CFG.initCreateScenario_TechnologyLevelsByContinents_Civ();
         for (int i = 0; i < tempScenarioGameData.getCivSize(); ++i) {
            CFG.addCreateScenario_TechnologyLevelsByContinents_Civ(tempScenarioGameData.getTechnologyByContinents(i));
         }
         tempScenarioGameData = null;
         Scenario_GameData_Province2 scenario_GameData_Province = (Scenario_GameData_Province2)CFG.deserialize(fileProvince.readBytes());
         if (scenario_GameData_Province.getProvinceOwners() != null) {
            try {
               for (int j = 0, iSize = scenario_GameData_Province.getProvinceOwners().size(); j < iSize; ++j) {
                  CFG.game.getProvince(j).setCivID_LoadScenario(scenario_GameData_Province.getProvinceOwners().get(j));
               }
            }
            catch (final IndexOutOfBoundsException ex3) {}
         }
         if (scenario_GameData_Province.getProvinceOccupiers() != null) {
            try {
               for (int j = 0, iSize = scenario_GameData_Province.getProvinceOccupiers().size(); j < iSize; ++j) {
                  CFG.game.getProvince(j).setTrueOwnerOfProvince(scenario_GameData_Province.getProvinceOccupiers().get(j));
               }
            }
            catch (final IndexOutOfBoundsException ex3) {}
         }
         scenario_GameData_Province = null;
      }
      catch (final ClassNotFoundException ex4) {}
      catch (final IOException ex5) {}
      if (!nEditor) {
         boolean foundRandomCivilization = false;
         for (int i = 1, iSize2 = lCivs.size(); i < iSize2; ++i) {
            if (lCivs.get(i).getCivTag().equals("ran")) {
               foundRandomCivilization = true;
               break;
            }
         }
         if (foundRandomCivilization) {
            final FileHandle tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
            final String tempT = tempFileT.readString();
            final String[] tagsSPLITED = tempT.split(";");
            final Random oR = new Random();
            for (int k = 1, iSize3 = lCivs.size(); k < iSize3; ++k) {
               if (lCivs.get(k).getCivTag().equals("ran")) {
                  int tempTagID;
                  while (true) {
                     tempTagID = oR.nextInt(tagsSPLITED.length);
                     if (tagsSPLITED[tempTagID].equals("ran")) {
                        continue;
                     }
                     if (!CFG.isInTheGame(tagsSPLITED[tempTagID])) {
                        break;
                     }
                  }
                  final FileHandle fileCiv2 = Gdx.files.internal("game/civilizations/" + tagsSPLITED[tempTagID]);
                  try {
                     Civilization_GameData3 tempCivData2 = (Civilization_GameData3)CFG.deserialize(fileCiv2.readBytes());
                     lCivs.get(k).setCivTag(tempCivData2.getCivTag());
                     lCivs.get(k).setCivName(CFG.langManager.getCiv(tempCivData2.getCivTag()));
                     lCivs.get(k).setR(tempCivData2.getR());
                     lCivs.get(k).setG(tempCivData2.getG());
                     lCivs.get(k).setB(tempCivData2.getB());
                     lCivs.get(k).disposeFlag();
                     lCivs.get(k).loadFlag();
                     tempCivData2 = null;
                  }
                  catch (final ClassNotFoundException ex6) {}
                  catch (final IOException ex7) {}
               }
            }
         }
      }
      CFG.map.getMapBG().disposeMinimapOfCivilizations();
      try {
         this.sActiveScenarioTag = this.getScenarioTag(CFG.game.getScenarioID());
      }
      catch (final IndexOutOfBoundsException ex2) {
         this.sActiveScenarioTag = "";
      }
      return lCivs;
   }

   protected final void loadProvincesData(boolean nEditor) {
      FileHandle file;
      if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
         file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_W");
      } else {
         file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_W");
      }

      try {
         Scenario_WastelandProvinces_GameData scenario_WastelandProvinces_GameData = (Scenario_WastelandProvinces_GameData)CFG.deserialize(file.readBytes());
         int i = 0;

         for(int iSize = scenario_WastelandProvinces_GameData.getWastelandProvincesSize(); i < iSize; ++i) {
            CFG.game.getProvince(scenario_WastelandProvinces_GameData.getWastelandProvinceID(i)).setWasteland(0);
         }

         scenario_WastelandProvinces_GameData = null;
      } catch (ClassNotFoundException var6) {
      } catch (IOException var7) {
      }

      this.buildProvincePopulationAndEconomy(true, nEditor);
   }

   protected final void loadEventsData() {
      try {
         FileHandle file;
         if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
            file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + "events/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_E");
         } else {
            file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + "events/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_E");
         }

         try {
            CFG.eventsManager.eventsGD = (Events_GameData)CFG.deserialize(file.readBytes());
         } catch (ClassNotFoundException var3) {
         } catch (IOException var4) {
         }
      } catch (GdxRuntimeException var5) {
         CFG.eventsManager.eventsGD = new Events_GameData();
      }

   }

   protected final void loadCoresData() {
      try {
         FileHandle file;
         if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
            file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_C");
         } else {
            file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_C");
         }

         try {
            CFG.province_Cores_GameData = (Province_Cores_GameData)CFG.deserialize(file.readBytes());
         } catch (ClassNotFoundException var3) {
         } catch (IOException var4) {
         }
      } catch (GdxRuntimeException var5) {
         CFG.province_Cores_GameData = new Province_Cores_GameData();
      }

   }

   protected final void loadCoresData_Editor() {
      try {
         FileHandle file;
         if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
            file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_C");
         } else {
            file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_C");
         }

         try {
            CFG.province_Cores_GameData = (Province_Cores_GameData)CFG.deserialize(file.readBytes());

            for(int i = 0; i < CFG.province_Cores_GameData.getProvincesSize(); ++i) {
               CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).buildProvinceCore();

               for(int j = 0; j < ((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).lCores.size(); ++j) {
                  CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).getCore().addNewCore(((Province_Cores_Civs_GameData)((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).lCores.get(j)).iCivID, Game_Calendar.TURN_ID);
               }
            }
         } catch (ClassNotFoundException var4) {
         } catch (IOException var5) {
         }
      } catch (GdxRuntimeException var6) {
         CFG.province_Cores_GameData = new Province_Cores_GameData();
      }

   }

   protected final void buildDiplomacy() {
      CFG.game.buildAlliances();
      CFG.game.buildWars();

      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
         CFG.game.getCiv(i).buildDiplomacy(true);
      }

   }

   protected final void loadDiplomacyData(boolean editor) {
      this.buildDiplomacy();
      FileHandle file;
      if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
         file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_D");
      } else {
         file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_D");
      }

      try {
         Scenario_GameData_Diplomacy2 scenario_GameData_Diplomacy = (Scenario_GameData_Diplomacy2)CFG.deserialize(file.readBytes());

         int i;
         for(i = 0; i < scenario_GameData_Diplomacy.getVassals().size(); ++i) {
            if (((Scenario_GameData_Diplomacy_VassalsData)scenario_GameData_Diplomacy.getVassals().get(i)).getCivLordID() < CFG.game.getCivsSize()) {
               //set puppet with civ autonomy index
               CFG.game.getCiv(((Scenario_GameData_Diplomacy_VassalsData) scenario_GameData_Diplomacy.getVassals().get(i)).getCivID()).setPuppetOfCivID(((Scenario_GameData_Diplomacy_VassalsData) scenario_GameData_Diplomacy.getVassals().get(i)).getCivLordID(), ((Scenario_GameData_Diplomacy_VassalsData) scenario_GameData_Diplomacy.getVassals().get(i)).getCivAutonomy());
            }
         }

         int j;
         for(i = 0; i < scenario_GameData_Diplomacy.getAlliances().size(); ++i) {
            Gdx.app.log("AoC", ((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getName());
            CFG.game.addAlliance(((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getName());

            for(j = 0; j < ((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getCivs().size(); ++j) {
               CFG.game.getAlliance(i + 1).addCivilization((Integer)((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getCivs().get(j));
               CFG.game.getCiv((Integer)((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getCivs().get(j)).setAllianceID(i + 1);
            }

            CFG.game.getAlliance(i + 1).setColorOfAlliance(new Color_GameData(((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getColor().getR(), ((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getColor().getG(), ((Scenario_GameData_Diplomacy_AlliancesData)scenario_GameData_Diplomacy.getAlliances().get(i)).getColor().getB()));
         }

         if (editor) {
            for(i = 0; i < scenario_GameData_Diplomacy.getRelations().size(); ++i) {
               CFG.game.setCivRelation_OfCivB(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getCivB(), (float)((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getValue());
            }
         } else {
            for(i = 0; i < scenario_GameData_Diplomacy.getRelations().size(); ++i) {
               CFG.game.setCivRelation_OfCivB(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getCivB(), (float)((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getRelations().get(i)).getValue());
            }

            for(i = 1; i < CFG.game.getCivsSize() - 1; ++i) {
               for(j = i + 1; j < CFG.game.getCivsSize(); ++j) {
                  if ((int)CFG.game.getCivRelation_OfCivB(i, j) == 0) {
                     CFG.game.setCivRelation_OfCivB(i, j, (float)(CFG.oR.nextInt(20) - 10));
                  }

                  if ((int)CFG.game.getCivRelation_OfCivB(j, i) == 0) {
                     CFG.game.setCivRelation_OfCivB(j, i, (float)(CFG.oR.nextInt(20) - 10));
                  }
               }
            }
         }

         for(i = 0; i < scenario_GameData_Diplomacy.getMilitaryAccess().size(); ++i) {
            CFG.game.setMilitaryAccess(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getMilitaryAccess().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getMilitaryAccess().get(i)).getCivB(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getMilitaryAccess().get(i)).getValue());
         }

         for(i = 0; i < scenario_GameData_Diplomacy.getGuarantee().size(); ++i) {
            CFG.game.setGuarantee(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getGuarantee().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getGuarantee().get(i)).getCivB(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getGuarantee().get(i)).getValue());
         }

         for(i = 0; i < scenario_GameData_Diplomacy.getPacts().size(); ++i) {
            CFG.game.setCivNonAggressionPact(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getPacts().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getPacts().get(i)).getCivB(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getPacts().get(i)).getValue());
         }

         for(i = 0; i < scenario_GameData_Diplomacy.getDefensivePacts().size(); ++i) {
            CFG.game.setDefensivePact(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getDefensivePacts().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getDefensivePacts().get(i)).getCivB(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getDefensivePacts().get(i)).getValue());
         }

         for(i = 0; i < scenario_GameData_Diplomacy.getTruces().size(); ++i) {
            CFG.game.setCivTruce(((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getTruces().get(i)).getCivA(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getTruces().get(i)).getCivB(), ((Scenario_GameData_Diplomacy_Data)scenario_GameData_Diplomacy.getTruces().get(i)).getValue());
         }

         scenario_GameData_Diplomacy = null;
      } catch (ClassNotFoundException var6) {
         CFG.toast.setInView("Error - Diplomacy Data");
      } catch (IOException var7) {
      } catch (GdxRuntimeException var8) {
      } catch (NullPointerException var9) {
      }

      DiplomacyManager.buildFriendlyCivs();
   }

   protected final void loadArmiesData() {
      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         CFG.game.getProvince(i).resetArmies_NewGame(0);
         //if not puppet or if mil control add armies
         if ((!CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIsPupet() || CFG.game.getCiv(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getPuppetOfCivID()).getVassal_AutonomyStatus(CFG.game.getProvince(i).getCivID()).isMilitaryControl()) && !CFG.game.getProvince(i).getSeaProvince() && CFG.game.getProvince(i).getWasteland() < 0) {
            if (CFG.game.getProvince(i).getCivID() == 0) {
               CFG.game.getProvince(i).updateArmy(this.getScenario_NeutralArmy());
            } else if (CFG.game.getProvince(i).getIsCapital()) {
               if (CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) {
                  CFG.game.getProvince(i).updateArmy(this.getScenario_StartingArmyInCapitals() / 10);
               } else {
                  CFG.game.getProvince(i).updateArmy(this.getScenario_StartingArmyInCapitals());
               }
            }
         }
      }

      try {
         FileHandle file;
         if ((Boolean)this.isInternal.get(CFG.game.getScenarioID())) {
            file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_A");
         } else {
            file = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_A");
         }

         try {
            Scenario_GameData_Armies scenario_GameData_Armies = (Scenario_GameData_Armies)CFG.deserialize(file.readBytes());
            int i = 0;

            for(int iSize = scenario_GameData_Armies.lArmies.size(); i < iSize; ++i) {
               try {
                  if (CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getWasteland() < 0 && (CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getCivID() == ((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID() || CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getSeaProvince() || CFG.game.getCiv(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID()).getAllianceID() > 0 == CFG.game.getCiv(CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getCivID()).getAllianceID() > 0 || CFG.game.getCiv(CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getCivID()).getPuppetOfCivID() == ((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID() || CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getCivID() == CFG.game.getCiv(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID()).getPuppetOfCivID() || CFG.game.getMilitaryAccess(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID(), CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).getCivID()) > 0)) {
                     CFG.game.getProvince(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getProvinceID()).updateArmy(((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getCivID(), ((Scenario_GameData_Army)scenario_GameData_Armies.lArmies.get(i)).getArmy());
                  }
               } catch (IndexOutOfBoundsException var6) {
               } catch (NullPointerException var7) {
               }
            }

            scenario_GameData_Armies = null;
         } catch (ClassNotFoundException var8) {
         } catch (IOException var9) {
         } catch (GdxRuntimeException var10) {
         }
      } catch (GdxRuntimeException var11) {
      }

   }

   protected final void buildProvincePopulationAndEconomy(boolean loadCoresData, boolean nEditor) {
      Random oR = new Random();
      CFG.game.getCiv(0).setTechnologyLevel(0.1F);

      int i;
      for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!CFG.game.getProvince(i).getSeaProvince()) {
            CFG.game.getProvince(i).getPopulationData().clearData();
            CFG.game.getProvince(i).setEconomy(0);
            CFG.game.getProvince(i).iIncome_Taxation = 1.0F;
            CFG.game.getProvince(i).iIncome_Production = 1.0F;
            CFG.game.getProvince(i).iAdministrationCost = 0.0F;
            CFG.game.getProvince(i).saveProvinceData.iNumOfTurnsWithBalanceOnMinus = 0;
         }

         CFG.game.getProvince(i).setIsPartOfHolyRomanEmpire(false);
         CFG.game.getProvince(i).saveProvinceData.resetData();
      }

      for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
         CFG.game.getProvince(i).buildProvinceCore();
      }

      int tRan;
      if (loadCoresData) {
         CFG.game.getGameScenarios().loadCoresData();

         for(i = 0; i < CFG.province_Cores_GameData.getProvincesSize(); ++i) {
            try {
               if (!CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).getSeaProvince() && CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).getWasteland() < 0 && CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).getCivID() > 0) {
                  for(tRan = 0; tRan < ((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).lCores.size(); ++tRan) {
                     CFG.game.getProvince(((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).iProvinceID).getCore().addNewCore(((Province_Cores_Civs_GameData)((Province_Cores_Provinces_GameData)CFG.province_Cores_GameData.lProvinces.get(i)).lCores.get(tRan)).iCivID, 1);
                  }
               }
            } catch (IndexOutOfBoundsException var8) {
            }
         }
      }

      if (CFG.province_Cores_GameData == null) {
         CFG.province_Cores_GameData = new Province_Cores_GameData();
      }

      for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!CFG.game.getProvince(i).getSeaProvince()) {
            float tDevelopment = CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getTechnologyLevel();
            tDevelopment = tDevelopment * ((1.0f - CFG.gameAges.getAge_StartingDevelopment(Game_Calendar.CURRENT_AGEID)) * (CFG.game.getProvince(i).getIsCapital() ? 0.7646841f : 0.5746985f)) + tDevelopment * CFG.gameAges.getAge_StartingDevelopment(Game_Calendar.CURRENT_AGEID) * CFG.game.getProvince(i).getGrowthRate_Population();
            if (CFG.game.getProvince(i).getCivID() > 0) {
               tDevelopment = tDevelopment * CFG.getCreateScenario_TechnologyLevelsByContinents_Continent(CFG.game.getProvince(i).getCivID() - 1, CFG.game.getProvince(i).getRegion()) / 100.0f;
            }
            tDevelopment *= 0.875f + CFG.oR.nextInt(2000) / 10000.0f + CFG.terrainTypesManager.getBaseDevelopmentModifier(CFG.game.getProvince(i).getTerrainTypeID());
            CFG.game.getProvince(i).setDevelopmentLevel(tDevelopment);
            if (CFG.game.getProvince(i).getCivID() == 0) {
               CFG.game.getProvince(i).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(i).getCivID(), (int)(this.getScenario_StartingPopulation() * 0.18275f * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID())))) + oR.nextInt(1 + (int)Math.ceil(this.getScenario_StartingPopulation() * (oR.nextInt(25) / 100.0f) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID()))))) / 4);
               CFG.game.getProvince(i).setEconomy((int)(this.getScenario_StartingEconomy() * (0.05275f + CFG.game.getProvince(i).getNeighboringSeaProvincesSize() * 0.0015f) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getEconomyGrowth(CFG.game.getProvince(i).getTerrainTypeID())))) + oR.nextInt(1 + (int)Math.ceil(this.getScenario_StartingEconomy() * (oR.nextInt(10) / 100.0f) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getEconomyGrowth(CFG.game.getProvince(i).getTerrainTypeID())) * CFG.game.getProvince(i).getDevelopmentLevel()))));
               CFG.game.getProvince(i).setHappiness(0.48f + oR.nextInt(2400) / 10000.0f);
            }
            else {
               if (CFG.game.getProvince(i).getCore().getCivsSize() >= 1) {
                  final int tempPop = (int)(((int)(this.getScenario_StartingPopulation() * (0.85f + (CFG.game.getProvince(i).getIsCapital() ? 0.0725f : 0.0f)) * ((CFG.game.getProvince(i).getIsCapital() ? Math.max(0.2675f, CFG.game.getProvince(i).getGrowthRate_Population()) : CFG.game.getProvince(i).getGrowthRate_Population()) * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID())))) + oR.nextInt(1 + (int)Math.ceil(this.getScenario_StartingPopulation() * 0.15f * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID())))))) * ((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) ? ((CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getCapitalProvinceID() == i) ? 0.4f : 0.275f) : 1.0f) * (0.725f + 0.275f * CFG.getCreateScenario_TechnologyLevelsByContinents_Continent(CFG.game.getProvince(i).getCivID() - 1, CFG.game.getProvince(i).getRegion()) / 100.0f));
                  CFG.game.getProvince(i).getPopulationData().clearData();
                  for (int k = 0; k < CFG.game.getProvince(i).getCore().getCivsSize(); ++k) {
                     CFG.game.getProvince(i).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(i).getCore().getCivID(k), (int)(tempPop * CFG.province_Cores_GameData.getPercOfPop(i, CFG.game.getProvince(i).getCore().getCivID(k))));
                  }
                  for (int k = 0; k < CFG.game.getProvince(i).getCore().getCivsSize() && k < 1; ++k) {
                     if (CFG.province_Cores_GameData.getPercOfPop(i, CFG.game.getProvince(i).getCore().getCivID(k)) < 0.18f) {
                        CFG.game.getProvince(i).getCore().removeCore(CFG.game.getProvince(i).getCore().getCivID(k));
                     }
                  }
               }
               else {
                  CFG.game.getProvince(i).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(i).getCivID(), (int)(((int)(this.getScenario_StartingPopulation() * (0.85f + (CFG.game.getProvince(i).getIsCapital() ? 0.05f : 0.0f)) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID())))) + oR.nextInt(1 + (int)Math.ceil(this.getScenario_StartingPopulation() * 0.15f * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(i).getTerrainTypeID())))))) * ((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) ? ((CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getCapitalProvinceID() == i) ? 0.4f : 0.275f) : 1.0f) * (0.725f + 0.275f * CFG.getCreateScenario_TechnologyLevelsByContinents_Continent(CFG.game.getProvince(i).getCivID() - 1, CFG.game.getProvince(i).getRegion()) / 100.0f)));
               }
               CFG.game.getProvince(i).setEconomy((int)((int)(this.getScenario_StartingEconomy() * (CFG.game.getProvince(i).getDevelopmentLevel() * 1.064498f + CFG.game.getProvince(i).getNeighboringSeaProvincesSize() * 0.035f) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getEconomyGrowth(CFG.game.getProvince(i).getTerrainTypeID())))) + oR.nextInt(1 + Math.max((int)Math.ceil(this.getScenario_StartingEconomy() * (1.0f - CFG.game.getProvince(i).getDevelopmentLevel()) * (CFG.game.getProvince(i).getGrowthRate_Population() * (1.0f + CFG.terrainTypesManager.getEconomyGrowth(CFG.game.getProvince(i).getTerrainTypeID())) * CFG.game.getProvince(i).getDevelopmentLevel())), 0)) * ((CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0) ? ((CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getCapitalProvinceID() == i) ? 0.95f : 0.725f) : 1.0f)));
               CFG.game.getProvince(i).setHappiness((CFG.game.getCiv(CFG.game.getProvince(i).getCivID()).getHappiness() + oR.nextInt(12) - 6) / 100.0f);
            }
            for (int l = 0; l < CFG.game.getProvince(i).getNeighboringProvincesSize(); ++l) {
               if (CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID() > 0 && CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID() != CFG.game.getProvince(i).getCivID()) {
                  CFG.game.getProvince(i).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(CFG.game.getProvince(i).getNeighboringProvinces(l)).getCivID(), (int)(CFG.game.getProvince(i).getPopulationData().getPopulation() * (0.00874f + CFG.oR.nextInt(345) / 10000.0f)));
               }
            }
         }
      }

      if (!nEditor) {
         for(i = 1; i < CFG.game.getCivsSize(); ++i) {
            if (CFG.game.getCiv(i).getNumOfProvinces() > 0 && CFG.ideologiesManager.getIdeology(CFG.game.getCiv(i).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0 && CFG.game.getCiv(i).getCapitalProvinceID() >= 0) {
               for(tRan = 0; tRan < CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getNeighboringProvincesSize(); ++tRan) {
                  if (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getNeighboringProvinces(tRan)).getWasteland() < 0 && (CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getNeighboringProvinces(tRan)).getCivID() == 0 || CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getNeighboringProvinces(tRan)).getCivID()).getIdeologyID()).CAN_BECOME_CIVILIZED >= 0)) {
                     CFG.game.getProvince(CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getNeighboringProvinces(tRan)).getCore().addNewCore(i, 1);
                  }
               }

               tRan = CFG.oR.nextInt(10);

               for(int j = 0; j < tRan; ++j) {
                  CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).getCore().increaseOwnership(i, CFG.game.getCiv(i).getCapitalProvinceID());
               }
            }
         }
      }

      CFG.province_Cores_GameData = null;
      CFG.game_NextTurnUpdate.updateCities();
   }

   protected final void disableFillTheMap() {
      int i;
      for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!CFG.game.getProvince(i).getIsCapital()) {
            CFG.game.getProvince(i).setCivID_LoadScenario(0);
            CFG.game.getProvince(i).setCivRegionID(-1);
         }
      }

      for(i = 1; i < CFG.game.getCivsSize(); ++i) {
         CFG.game.getCiv(i).clearProvinces_FillTheMap(CFG.game.getCiv(i).getNumOfProvinces() > 0);
      }

      for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
         for(int j = 0; j < CFG.game.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
            ((Province_Border)CFG.game.getProvince(i).getProvinceBordersLandByLand().get(j)).setIsCivilizationBorder(false, i);
         }
      }

      for(i = 1; i < CFG.game.getCivsSize(); ++i) {
         CFG.game.getProvince(CFG.game.getCiv(i).getCapitalProvinceID()).updateProvinceBorder();
      }

      CFG.game.buildCivilizationsRegions();
      CFG.map.getMapBG().disposeMinimapOfCivilizations();
   }

   protected final void enableFillTheMap() {
      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
         CFG.game.getCiv(i).clearProvinces_FillTheMap(false);
      }

      FileHandle file = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()));
      FileHandle fileProvince = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "/" + (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID()) + "_PD");

      try {
         Scenario_GameData tempScenarioGameData = (Scenario_GameData)CFG.deserialize(file.readBytes());

         for(int i = 0; i < tempScenarioGameData.getCivSize(); ++i) {
            CFG.game.getCiv(i + 1).setCapitalProvinceID(tempScenarioGameData.getCivCapital(i));
         }

         Scenario_GameData_Province2 scenario_GameData_Province = (Scenario_GameData_Province2)CFG.deserialize(fileProvince.readBytes());
         int i;
         int j;
         if (scenario_GameData_Province.getProvinceOwners() != null) {
            i = 0;

            for(j = scenario_GameData_Province.getProvinceOwners().size(); i < j; ++i) {
               CFG.game.getProvince(i).setCivID_LoadScenario((Integer)scenario_GameData_Province.getProvinceOwners().get(i));
               CFG.game.getCiv((Integer)scenario_GameData_Province.getProvinceOwners().get(i)).addProvince_Just(i);
            }
         }

         for(i = 0; i < CFG.game.getProvincesSize(); ++i) {
            for(j = 0; j < CFG.game.getProvince(i).getProvinceBordersLandByLandSize(); ++j) {
               ((Province_Border)CFG.game.getProvince(i).getProvinceBordersLandByLand().get(j)).setIsCivilizationBorder(CFG.game.getProvince(i).getCivID() != CFG.game.getProvince(((Province_Border)CFG.game.getProvince(i).getProvinceBordersLandByLand().get(j)).getWithProvinceID()).getCivID(), i);
            }
         }

         CFG.game.buildCivilizationsRegions();
      } catch (ClassNotFoundException var7) {
      } catch (IOException var8) {
      } catch (GdxRuntimeException var9) {
      }

      CFG.map.getMapBG().disposeMinimapOfCivilizations();
   }

   protected final void editScenario(int iID) {
      Game_Calendar.TURN_ID = 1;
      CFG.game.setScenarioID(iID);
      CFG.game.loadScenario(true);
      CFG.game.getGameScenarios().loadCoresData_Editor();
      CFG.CREATE_SCENARIO_GAME_DATA_TAG = (String)this.lScenarios_TagsList.get(CFG.game.getScenarioID());
      CFG.CREATE_SCENARIO_NAME = this.getScenarioName(CFG.game.getScenarioID());
      CFG.CREATE_SCENARIO_AUTHOR = this.getScenarioAuthor(CFG.game.getScenarioID());
      CFG.CREATE_SCENARIO_AGE = this.getScenarioAge(CFG.game.getScenarioID());
      CFG.CREATE_SCENARIO_WIKI = this.getScenarioWiki(CFG.game.getScenarioID());
      Game_Calendar.currentYear = this.getScenarioYear(CFG.game.getScenarioID());
      Game_Calendar.currentMonth = this.getScenarioMonth(CFG.game.getScenarioID());
      Game_Calendar.currentDay = this.getScenarioDay(CFG.game.getScenarioID());
   }

   protected final int getNumOfCivs(int i) {
      return (Integer)this.lScenarios_CivNum.get(i);
   }

   protected final void setNumOfCivs(int i, int nNumCivs) {
      try {
         this.lScenarios_CivNum.set(i, nNumCivs);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final String getScenarioName(int i) {
      return (String)this.lScenarios_Names.get(i);
   }

   protected final void setScenarioName(int i, String nName) {
      try {
         this.lScenarios_Names.set(i, nName);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final String getScenarioWiki(int i) {
      return (String)this.lScenarios_Wikis.get(i);
   }

   protected final String getScenarioAuthor(int i) {
      return (String)this.lScenarios_Authors.get(i);
   }

   protected final void setScenarioAuthor(int i, String nAuthor) {
      try {
         this.lScenarios_Authors.set(i, nAuthor);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final String getScenarioTag(int i) {
      return (String)this.lScenarios_TagsList.get(i);
   }

   protected final int getScenarioAge(int i) {
      return (Integer)this.lScenarios_Age.get(i);
   }

   protected final void setScenarioAge(int i, int nAge) {
      try {
         this.lScenarios_Age.set(i, nAge);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final int getScenarioYear(int i) {
      return (Integer)this.lScenarios_Year.get(i);
   }

   protected final int getScenarioMonth(int i) {
      return (Integer)this.lScenarios_Month.get(i);
   }

   protected final int getScenarioDay(int i) {
      return (Integer)this.lScenarios_Day.get(i);
   }

   protected final void setScenarioDay(int i, int nDay) {
      try {
         this.lScenarios_Day.set(i, nDay);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final void setScenarioMonth(int i, int nMonth) {
      try {
         this.lScenarios_Month.set(i, nMonth);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final void setScenarioYear(int i, int nYear) {
      try {
         this.lScenarios_Year.set(i, nYear);
      } catch (IndexOutOfBoundsException var4) {
      }

   }

   protected final int getScenario_StartingArmyInCapitals() {
      return this.iScenario_StartingArmyInCapitals;
   }

   protected final void setScenario_StartingArmyInCapitals(int iScenario_StartingArmyInCapitals) {
      this.iScenario_StartingArmyInCapitals = iScenario_StartingArmyInCapitals;
   }

   protected final float getScenario_PopulationGrowthRate_Modifier() {
      return this.iScenario_PopulationGrowthRate_Modifier;
   }

   protected final void setScenario_PopulationGrowthRate_Modifier(float iScenario_PopulationGrowthRate_Modifier) {
      this.iScenario_PopulationGrowthRate_Modifier = iScenario_PopulationGrowthRate_Modifier;
   }

   protected final float getScenario_EconomyGrowthRate_Modifier() {
      return this.iScenario_EconomyGrowthRate_Modifier;
   }

   protected final void setScenario_EconomyGrowthRate_Modifier(float iScenario_EconomyGrowthRate_Modifier) {
      this.iScenario_EconomyGrowthRate_Modifier = iScenario_EconomyGrowthRate_Modifier;
   }

   protected final float getScenario_DiseasesDeathRate_Modifier() {
      return this.iScenario_DiseasesDeathRate_Modifier;
   }

   protected final void setScenario_DiseasesDeathRate_Modifier(float iScenario_DiseasesDeathRate_Modifier) {
      this.iScenario_DiseasesDeathRate_Modifier = iScenario_DiseasesDeathRate_Modifier;
   }

   protected final int getScenario_NeutralArmy() {
      return this.iScenario_NeutralArmy;
   }

   protected final void setScenario_NeutralArmy(int iScenario_NeutralArmy) {
      this.iScenario_NeutralArmy = iScenario_NeutralArmy;
   }

   protected final int getScenario_StartingPopulation() {
      return this.iScenario_StartingPopulation;
   }

   protected final void setScenario_StartingPopulation(int iScenario_StartingPopulation) {
      this.iScenario_StartingPopulation = iScenario_StartingPopulation;
   }

   protected final int getScenario_StartingEconomy() {
      return this.iScenario_StartingEconomy;
   }

   protected final void setScenario_StartingEconomy(int iScenario_StartingEconomy) {
      this.iScenario_StartingEconomy = iScenario_StartingEconomy;
   }

   protected final int getScenario_StartingMoney() {
      return this.iScenario_StartingMoney;
   }

   protected final void setScenario_StartingMoney(int iScenario_StartingMoney) {
      this.iScenario_StartingMoney = iScenario_StartingMoney;
   }

   protected final String getScenario_ActivePallet_TAG() {
      return this.sScenario_ActivePallet_TAG;
   }

   public void setScenario_ActivePallet_TAG(String sScenario_ActivePallet_TAG) {
      this.sScenario_ActivePallet_TAG = sScenario_ActivePallet_TAG;
   }

   protected final boolean getScenarioIsInternal(int i) {
      return (Boolean)this.isInternal.get(i);
   }

   class RandomGame_AoCMode {
      protected String sTag;
      protected int iCapitalID = -1;

      protected RandomGame_AoCMode(String sTag) {
         this.sTag = sTag;
         this.iCapitalID = -1;
      }

      protected RandomGame_AoCMode(String sTag, int iCapitalID) {
         this.sTag = sTag;
         this.iCapitalID = iCapitalID;
      }
   }
}
