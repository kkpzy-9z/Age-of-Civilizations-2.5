package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_FlagAction_Budget_Vassals extends SliderMenu {
   protected static final int MAX_SHOW = 9;
   protected static int iCivID;

   protected static final MenuElement_Hover getIncomeTaxation() {
      final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
      final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
      final List<Integer> tempIDs = new ArrayList<Integer>();
      final List<Integer> tempIncome = new ArrayList<Integer>();

      for(int i = 0; i < CFG.game.getCiv(iCivID).getNumOfProvinces(); ++i) {
         tempIDs.add(CFG.game.getCiv(iCivID).getProvinceID(i));
         tempIncome.add((int)CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(CFG.game.getCiv(iCivID).getProvinceID(i)));
      }

      final List<Integer> sortedIDs = new ArrayList<Integer>();
      final List<Integer> sortedIncome = new ArrayList<Integer>();

      int rest;
      int i;
      while(tempIDs.size() > 0) {
         rest = 0;

         for(i = 1; i < tempIDs.size(); ++i) {
            if ((Integer)tempIncome.get(rest) < (Integer)tempIncome.get(i)) {
               rest = i;
            }
         }

         sortedIDs.add((Integer)tempIDs.get(rest));
         sortedIncome.add((Integer)tempIncome.get(rest));
         tempIDs.remove(rest);
         tempIncome.remove(rest);
      }

      for(rest = 0; rest < 9 && rest < sortedIDs.size(); ++rest) {
         nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince((Integer)sortedIDs.get(rest)).getName() + ": "));
         nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + sortedIncome.get(rest), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
         nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_Hover_v2_Element2(nData));
         nData.clear();
      }

      if (sortedIDs.size() > 9) {
         rest = 0;

         for(i = 9; i < sortedIDs.size(); ++i) {
            rest += (Integer)sortedIncome.get(i);
         }

         nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AndTheRest") + ": "));
         nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + rest, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
         nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_Hover_v2_Element2(nData));
         nData.clear();
      }

      return new MenuElement_Hover_v2(nElements);
   }

   protected Menu_InGame_FlagAction_Budget_Vassals(final int nCivID) {
      iCivID = nCivID;
      int tempHeight = 0;
      int tempWidth;
      int tY = 0;
      if (CFG.isAndroid() && !CFG.LANDSCAPE) {
         tempWidth = CFG.GAME_WIDTH - CFG.PADDING * 4;
      } else {
         tempWidth = CFG.GAME_WIDTH / 2 - CFG.PADDING * 2;
      }

      List menuElements = new ArrayList();
      CFG.game_NextTurnUpdate.getBalance_UpdateBudget_Prepare(iCivID);
      int tempBalance = 0;
      menuElements.add(new Text_EconomyTitle(CFG.langManager.get("Income"), -1, CFG.PADDING * 2, CFG.PADDING * 2, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      int tempValue = CFG.game.getCiv(iCivID).iIncomeTaxation;
      menuElements.add(new Text_Economy_Value("" + tempValue, CFG.langManager.get("Taxation"), CFG.PADDING * 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Population") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).countPopulation()), CFG.COLOR_TEXT_POPULATION));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Happiness") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(iCivID).getHappiness() + "%", CFG.getColorStep(CFG.COLOR_TEXT_HAPPINESS_MIN, CFG.COLOR_TEXT_HAPPINESS_MAX, CFG.game.getCiv(iCivID).getHappiness(), 100, 1.0F)));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.happiness, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      tempValue = CFG.game.getCiv(iCivID).iIncomeProduction;
      menuElements.add(new Text_Economy_Value("" + tempValue, CFG.langManager.get("Production"), CFG.PADDING * 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Economy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).countEconomy()), CFG.COLOR_TEXT_ECONOMY));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Technology") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AverageDevelopment") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countAvarageDevelopmentLevel(iCivID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + (int)(CFG.game.countAvarageDevelopmentLevel_Float(iCivID) / CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      tempValue = (int)CFG.game_NextTurnUpdate.getIncome_Debuff_IsVassal(iCivID) + (int)CFG.game_NextTurnUpdate.getIncome_Buff_WarReparations(iCivID) + (int)CFG.game_NextTurnUpdate.getIncome_Debuff_WarReparations(iCivID);
      menuElements.add(new Text_Economy_Value("" + tempValue, CFG.langManager.get("Others"), CFG.PADDING * 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Vassals"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Ideology_Vassal(CFG.game.getCiv(iCivID).getIdeologyID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();

            int i;
            if (CFG.game.getCiv(iCivID).getCivID() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Lord") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, CFG.PADDING));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)CFG.game_NextTurnUpdate.getIncome_Vassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iCivID), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_Tribute(iCivID) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            if (CFG.game.getCiv(iCivID).getWarReparationsGetsSize() > 0 || CFG.game.getCiv(iCivID).getWarReparationsPaysSize() > 0) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WarReparations"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_truce, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               for(i = 0; i < CFG.game.getCiv(iCivID).getWarReparationsGetsSize(); ++i) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getCiv(iCivID).getWarReparationsGets(i).iFromCivID));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getCiv(iCivID).getWarReparationsGets(i).iFromCivID).getCivName() + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getWarReparationsMoney(CFG.game.getCiv(iCivID).getWarReparationsGets(i).iFromCivID), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.langManager.get("TurnsX", CFG.game.getCiv(iCivID).getWarReparationsGets(i).iTurnsLeft), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               for(i = 0; i < CFG.game.getCiv(iCivID).getWarReparationsPaysSize(); ++i) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getCiv(iCivID).getWarReparationsPays(i).iFromCivID));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getCiv(iCivID).getWarReparationsPays(i).iFromCivID).getCivName() + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)CFG.game_NextTurnUpdate.getWarReparationsMoney(iCivID), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.langManager.get("TurnsX", CFG.game.getCiv(iCivID).getWarReparationsPays(i).iTurnsLeft), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      tempValue = (int)CFG.game_NextTurnUpdate.getIncome(iCivID);
      tempBalance = tempValue;
      menuElements.add(new Text_Economy_Total("" + CFG.getNumberWithSpaces("" + tempValue), CFG.langManager.get("TotalIncome"), CFG.PADDING * 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3));
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_EconomyTitle(CFG.langManager.get("Expenses"), -1, CFG.PADDING * 2 + (tempWidth - CFG.PADDING * 4) / 2, CFG.PADDING * 2, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4));
      tY = ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      tempValue = -CFG.game.getCiv(iCivID).iAdministrationCosts - (int)CFG.game_NextTurnUpdate.getInterestCost(iCivID) - (int)CFG.game_NextTurnUpdate.getInflation(iCivID) - CFG.game.getCiv(iCivID).getLoans_GoldTotalPerTurn();
      menuElements.add(new Text_Economy_Value("" + Math.abs(tempValue), CFG.langManager.get("Administration"), CFG.PADDING * 2 + (tempWidth - CFG.PADDING * 4) / 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AdministrationCost") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(iCivID).iAdministrationCosts, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Inflation") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getInflation(iCivID), (int)CFG.game_NextTurnUpdate.getInflation(iCivID) > 0 ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + (float)((int)(CFG.game_NextTurnUpdate.getInflationPerc(iCivID) * 10000.0F)) / 100.0F + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Interest") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getInterestCost(iCivID), (int)CFG.game_NextTurnUpdate.getInterestCost(iCivID) > 0 ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();

            for(int i = 0; i < CFG.game.getCiv(iCivID).getLoansSize(); ++i) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Loan") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(iCivID).getLoan(i).iGoldPerTurn, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, CFG.PADDING));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.langManager.get("TurnsX", CFG.game.getCiv(iCivID).getLoan(i).iTurnsLeft) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_loan, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      tempValue = -((int)CFG.game_NextTurnUpdate.getMilitaryUpkeep_Total(iCivID));
      menuElements.add(new Text_Economy_Value("" + Math.abs(tempValue), CFG.langManager.get("Military"), CFG.PADDING * 2 + (tempWidth - CFG.PADDING * 4) / 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Army") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).getNumOfUnits()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            int nUpkeep = (int)CFG.game_NextTurnUpdate.getMilitaryUpkeep_Total(iCivID);
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + nUpkeep, nUpkeep == 0 ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)((float)nUpkeep / (float)CFG.game.getCiv(iCivID).getNumOfUnits() * 100.0F)) / 100.0F, CFG.COLOR_INGAME_GOLD));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PerUnit")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WarWeariness") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(iCivID).getWarWeariness() * 10000.0F)) / 100.0F + "%", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_weariness, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BudgetSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game_NextTurnUpdate.getMilitarySpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      tempValue = (int)CFG.game_NextTurnUpdate.getInvestments_Total(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) + (int)CFG.game_NextTurnUpdate.getGoodsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2);
      menuElements.add(new Text_Economy_Value("" + Math.abs(tempValue), CFG.langManager.get("Spendings"), CFG.PADDING * 2 + (tempWidth - CFG.PADDING * 4) / 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Goods") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getGoodsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2), (int)CFG.game_NextTurnUpdate.getGoodsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) > 0 ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Research") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2), (int)CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) > 0 ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Investments") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getInvestmentsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2), (int)CFG.game_NextTurnUpdate.getInvestmentsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) > 0 ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE2 : CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      tempValue = -((int)CFG.game_NextTurnUpdate.getExpensesIsVassal(iCivID));
      tempBalance += tempValue;
      menuElements.add(new Text_Economy_Total("" + CFG.getNumberWithSpaces("" + Math.abs(tempValue)), CFG.langManager.get("TotalExpenses"), CFG.PADDING * 2 + (tempWidth - CFG.PADDING * 4) / 2, tY, (tempWidth - CFG.PADDING * 4) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 3));
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempValue);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_Economy_Balance("" + CFG.getNumberWithSpaces("" + tempBalance), CFG.langManager.get("Balance"), CFG.PADDING * 2, tY, tempWidth - CFG.PADDING * 4, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected int getSFX() {
            return SoundsManager.SOUND_GOLD;
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tempBalance);
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      for(int i = 0; i < menuElements.size(); ++i) {
         ((MenuElement)menuElements.get(i)).setCurrent(i % 2);
      }

      tY += CFG.PADDING * 2;
      float tFValue = CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation(iCivID);
      menuElements.add(new Slider_FlagAction_Taxes("" + (tFValue > 0.0F ? "+" : "") + tFValue, CFG.langManager.get("Taxes"), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 5, 0, 100, (int)(CFG.game.getCiv(iCivID).getTaxationLevel() * 100.0F)) {
         protected String getDrawText() {
            return this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return CFG.getColorStep(new Color(0.023529412F, 0.3254902F, 0.40392157F, 0.65F), new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, 0.65F), this.getCurrent(), 100, 0.65F);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_GOLD;
         }

         protected boolean getClickable() {
            return !CFG.SPECTATOR_MODE && super.getClickable();
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMax(tFValue >= 0.0F ? 0 : (tFValue <= -0.8F ? 2 : 1));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_BudgetTitle(CFG.langManager.get("BudgetSpendings"), -1, 2, tY, tempWidth - 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Budget") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.langManager.get("TotalIncome") + " ", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("- " + CFG.langManager.get("AdministrationCost"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Budget") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2), CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2 > 0 ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : (CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2 == 0 ? CFG.COLOR_TEXT_MODIFIER_NEUTRAL2 : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2)));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitarySpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game_NextTurnUpdate.getMilitarySpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GoodsSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Goods() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ResearchSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Research() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InvestmentsSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Investments() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Slider_FlagAction_Goods(CFG.langManager.get("Goods"), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 5, 0, 100, (int)(CFG.game.getCiv(iCivID).getSpendings_Goods() * 100.0F)) {
         protected String getDrawText() {
            return this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return this.getCurrent() < (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).getMin_Goods(iCivID) * 100.0F) ? CFG.getColorStep(new Color(0.54901963F, 0.078431375F, 0.078431375F, 0.65F), new Color(0.7058824F, 0.078431375F, 0.078431375F, 0.65F), (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).getMin_Goods(iCivID) * 100.0F) - this.getCurrent(), (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).getMin_Goods(iCivID) * 100.0F), 0.65F) : CFG.getColorStep(new Color(0.019607844F, 0.39215687F, 0.1764706F, 0.65F), new Color(0.039215688F, 0.5686275F, 0.29411766F, 0.65F), this.getCurrent(), 100, 0.65F);
         }

         protected Color getColor(boolean isActive) {
            return this.getCurrent() >= (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).getMin_Goods(iCivID) * 100.0F) ? super.getColor(isActive) : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2;
         }

         protected boolean getClickable() {
            return CFG.SPECTATOR_MODE ? false : super.getClickable();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_Investemnts_SliderDescGoods(CFG.langManager.get("AverageGrowthRate") + ": " + CFG.game.countAvarageGrowthRate(iCivID) + "%", CFG.langManager.get("Population") + ": ", CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).countPopulation()), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("hGoods"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("hGoods2")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("hGoods3", "" + (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).getMin_Goods(iCivID) * 100.0F))));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("hGoods4")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BudgetSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Goods() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Spendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + (int)CFG.game_NextTurnUpdate.getGoodsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2)), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction(CFG.langManager.get("Research"), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 5, 0, 100, (int)(CFG.game.getCiv(iCivID).getSpendings_Research() * 100.0F), CFG.game.getCiv(iCivID).getMoney() >= -500L) {
         protected String getDrawText() {
            return this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return CFG.getColorStep(new Color(0.07058824F, 0.18431373F, 0.3882353F, 0.65F), new Color(0.105882354F, 0.27450982F, 0.57254905F, 0.65F), this.getCurrent(), 100, 0.65F);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_TECHNOLOGY;
         }

         protected boolean getClickable() {
            return CFG.SPECTATOR_MODE ? false : super.getClickable();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_Economy_SliderDesc_Research(CFG.langManager.get("Progress") + ": ", "" + (int)CFG.game.getCiv(iCivID).getResearchProgress(), " / " + CFG.getNumberWithSpaces("" + TechnologyManager.getResearch_NextLevel(iCivID)) + " ", "[" + CFG.getPercentage_Max100((int)CFG.game.getCiv(iCivID).getResearchProgress(), TechnologyManager.getResearch_NextLevel(iCivID), 4) + "%]", CFG.langManager.get("TechnologyLevel") + ": " + (float)((int)(CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech1"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech2")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TechnologyLevel") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ResearchProgress") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game.getCiv(iCivID).getResearchProgress() + " / " + TechnologyManager.getResearch_NextLevel(iCivID), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BudgetSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Research() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Spendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + (int)CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2)), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected int getSFX() {
            return SoundsManager.SOUND_TECHNOLOGY;
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setMin((int)(CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) * (1.0F + CFG.game.getCiv(iCivID).getModifier_Research())));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Investments(CFG.langManager.get("Investments"), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 5, 0, 100, (int)(CFG.game.getCiv(iCivID).getSpendings_Investments() * 100.0F)) {
         protected String getDrawText() {
            return this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return this.getCurrent() < (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).MIN_INVESTMENTS * 100.0F) ? CFG.getColorStep(new Color(0.54901963F, 0.078431375F, 0.078431375F, 0.65F), new Color(0.7058824F, 0.078431375F, 0.078431375F, 0.65F), (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).MIN_INVESTMENTS * 100.0F) - this.getCurrent(), (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).MIN_INVESTMENTS * 100.0F), 0.65F) : CFG.getColorStep(new Color(0.105882354F, 0.16078432F, 0.2901961F, 0.65F), new Color(0.20392157F, 0.2784314F, 0.45490196F, 0.65F), this.getCurrent(), 100, 0.65F);
         }

         protected Color getColor(boolean isActive) {
            return this.getCurrent() >= (int)(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).MIN_INVESTMENTS * 100.0F) ? super.getColor(isActive) : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2;
         }

         protected boolean getClickable() {
            return CFG.SPECTATOR_MODE ? false : super.getClickable();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_Investemnts_SliderDesc(CFG.langManager.get("AverageDevelopment") + ": " + CFG.game.countAvarageDevelopmentLevel(iCivID) + " [" + (int)(CFG.game.countAvarageDevelopmentLevel_Float(iCivID) / CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F) + "%]", CFG.langManager.get("Economy") + ": " + CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).countEconomy()), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildYourEconomicPowerBySpendingGoldOnInvestments"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DevelopmentLevelAndEconomyWillBeIncreased")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AverageDevelopment") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.countAvarageDevelopmentLevel(iCivID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + (int)(CFG.game.countAvarageDevelopmentLevel_Float(iCivID) / CFG.game.getCiv(iCivID).getTechnologyLevel() * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, 0, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech4"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech5"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BudgetSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getCiv(iCivID).getSpendings_Investments() * 100.0F) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Spendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + (int)CFG.game_NextTurnUpdate.getInvestmentsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2)), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Slider_FlagAction_Military(CFG.langManager.get("Military"), CFG.PADDING * 3, tY, tempWidth - CFG.PADDING * 6, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, 0, 100, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2 < 0 && CFG.game.getCiv(iCivID).getNumOfUnits() > 0 ? 100 : CFG.game_NextTurnUpdate.getMilitarySpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2)) {
         protected String getDrawText() {
            return this.getCurrent() + "%";
         }

         protected Color getColorLEFT() {
            return new Color(0.39215687F, 0.078431375F, 0.078431375F, 0.65F);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Army") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(iCivID).getNumOfUnits()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(iCivID, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BudgetSpendings") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game_NextTurnUpdate.getMilitarySpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + (int)CFG.game_NextTurnUpdate.getMilitaryUpkeep_Total(iCivID)), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setClickable(false);
      var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING * 2;
      Menu_InGame_FlagAction_Bot.lTime = System.currentTimeMillis();
      menuElements.add(new Button_Transparent(0, 0, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, true));
      this.initMenu(new SliderMenuTitle(CFG.game.getCiv(iCivID).getCivName(), CFG.BUTTON_HEIGHT * 3 / 5, true, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color((float)CFG.game.getCiv(iCivID).getR() / 255.0F, (float)CFG.game.getCiv(iCivID).getG() / 255.0F, (float)CFG.game.getCiv(iCivID).getB() / 255.0F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color((float)CFG.game.getCiv(iCivID).getR() / 255.0F, (float)CFG.game.getCiv(iCivID).getG() / 255.0F, (float)CFG.game.getCiv(iCivID).getB() / 255.0F, 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth, CFG.PADDING, false, true);
            oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth - nWidth / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1, true, false);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.425F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY + 1 - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1, true, false);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth / 2 + CFG.PADDING + (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, nPosY + 1 - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.325F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY + 2 - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1, true, false);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth / 2 + CFG.PADDING + (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, nPosY + 2 - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1, true, false);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth / 2 + CFG.PADDING + (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, nPosY - this.getHeight() / 2 - ImageManager.getImage(Images.slider_gradient).getHeight(), (int)(((float)(nWidth - CFG.PADDING * 6) - (float)this.getTextWidth() * 0.8F) / 2.0F), 1);
            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(iCivID).getFlag().draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(iCivID).getFlag().getHeight(), CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - CFG.CIV_FLAG_HEIGHT / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.PADDING * 2 + AoCGame.LEFT, ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING + CFG.BUTTON_HEIGHT * 3 / 5, tempWidth, Math.min(((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + 1, CFG.GAME_HEIGHT - (ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING + CFG.BUTTON_HEIGHT * 3 / 5) - CFG.PADDING * 2), menuElements, false, true);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_FlagAction_Bot.lTime + 225L >= System.currentTimeMillis()) {
         Rectangle clipBounds = new Rectangle((float)(this.getPosX() - 2), (float)(CFG.GAME_HEIGHT - this.getPosY()), (float)(this.getWidth() + 4), (float)(-((int)((float)(this.getHeight() + CFG.PADDING) * ((float)(System.currentTimeMillis() - Menu_InGame_FlagAction_Bot.lTime) / 225.0F)))));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() + 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + this.getWidth() + 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.025F, 0.025F, 0.025F, 0.25F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING - 2, true, false);
         oSB.setColor(new Color(0.025F, 0.025F, 0.025F, 0.75F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 2, CFG.BUTTON_HEIGHT / 4);
         oSB.setColor(Color.WHITE);
         super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         CFG.setRender_3(true);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      } else {
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() + 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + this.getWidth() + 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.025F, 0.025F, 0.025F, 0.25F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING - 2, true, false);
         oSB.setColor(new Color(0.025F, 0.025F, 0.025F, 0.75F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 2, CFG.BUTTON_HEIGHT / 4);
         oSB.setColor(Color.WHITE);
         super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   private final void updateIncomeAndExpenses() {
      int tempBalance = 0;
      int tempValue = CFG.game.getCiv(iCivID).iIncomeTaxation;
      this.getMenuElement(1).setText("" + tempValue);
      this.getMenuElement(1).setMax(tempValue);
      tempValue = CFG.game.getCiv(iCivID).iIncomeProduction;
      this.getMenuElement(2).setText("" + tempValue);
      this.getMenuElement(2).setMax(tempValue);
      tempValue = (int)CFG.game_NextTurnUpdate.getIncome_FromVassalsOfCiv(iCivID) + (int)CFG.game_NextTurnUpdate.getIncome_Debuff_IsVassal(iCivID) + (int)CFG.game_NextTurnUpdate.getIncome_Debuff_WarReparations(iCivID) + (int)CFG.game_NextTurnUpdate.getIncome_Buff_WarReparations(iCivID);
      this.getMenuElement(3).setText("" + tempValue);
      this.getMenuElement(3).setMax(tempValue);
      tempValue = (int)CFG.game_NextTurnUpdate.getIncome(iCivID);
      tempBalance = tempValue;
      this.getMenuElement(4).setText("" + CFG.getNumberWithSpaces("" + Math.abs(tempValue)));
      this.getMenuElement(4).setMax(tempValue);
      tempValue = -CFG.game.getCiv(iCivID).iAdministrationCosts - (int)CFG.game_NextTurnUpdate.getInterestCost(iCivID) - (int)CFG.game_NextTurnUpdate.getInflation(iCivID) - CFG.game.getCiv(iCivID).getLoans_GoldTotalPerTurn();
      this.getMenuElement(6).setText("" + Math.abs(tempValue));
      this.getMenuElement(6).setMax(tempValue);
      tempValue = -((int)CFG.game_NextTurnUpdate.getInvestments_Total(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2)) - (int)CFG.game_NextTurnUpdate.getGoodsSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2);
      this.getMenuElement(8).setText("" + Math.abs(tempValue));
      this.getMenuElement(8).setMax(tempValue);
      tempValue = -((int)CFG.game_NextTurnUpdate.getExpensesIsVassal(iCivID));
      tempBalance += tempValue;
      this.getMenuElement(9).setText("" + CFG.getNumberWithSpaces("" + Math.abs(tempValue)));
      this.getMenuElement(9).setMax(tempValue);
      this.getMenuElement(10).setText("" + CFG.getNumberWithSpaces("" + tempBalance));
      this.getMenuElement(10).setMax(tempBalance);
      float tFValue = CFG.game_NextTurnUpdate.getHappinessChange_ByTaxation(iCivID);
      if ((double)tFValue < 0.001 && (double)tFValue > -0.001) {
         tFValue = 0.0F;
      }

      Gdx.app.log("AoC", "" + tFValue);
      this.getMenuElement(11).setText("" + (tFValue > 0.0F ? "+" : "") + tFValue);
      this.getMenuElement(11).setMax(tFValue >= 0.0F ? 0 : (tFValue <= -0.8F ? 2 : 1));
      this.getMenuElement(19).setCurrent(CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2 < 0 && CFG.game.getCiv(iCivID).getNumOfUnits() > 0 ? 100 : CFG.game_NextTurnUpdate.getMilitarySpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2));
      this.getMenuElement(13).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Goods() * 100.0F));
      this.getMenuElement(15).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Research() * 100.0F));
      this.getMenuElement(16).setMin((int)(CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) * (1.0F + CFG.game.getCiv(iCivID).getModifier_Research())));
      this.getMenuElement(17).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Investments() * 100.0F));
      Menu_InGame.updateOverBudget();
   }

   protected void onHovered() {
      CFG.menuManager.setOrderOfMenu_InGame_FlagAction();
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
         case 1:
         case 2:
         case 4:
         case 5:
         case 7:
         case 8:
         case 9:
         case 10:
         case 14:
         case 16:
         default:
            break;
         case 3:
            if (CFG.game.getCiv(iCivID).civGameData.lVassals.size() > 0) {
               CFG.menuManager.rebuildInGame_Tribute();
               CFG.toast.setInView(CFG.langManager.get("Vassals"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            }
            break;
         case 6:
            CFG.toast.setInView(CFG.langManager.get("hAdministrationCost"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            CFG.toast.setTimeInView(6000);
            break;
         case 11:
            CFG.game.getCiv(iCivID).setTaxationLevel((float)this.getMenuElement(iID).getCurrent() / 100.0F);
            CFG.game_NextTurnUpdate.getBalance_UpdateBudget_Prepare(iCivID);
            CFG.game_NextTurnUpdate.updateSpendingsOfVassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
            this.updateIncomeAndExpenses();
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_INCOME_MODE && CFG.menuManager.getVisible_InGame_View_Stats()) {
               CFG.menuManager.setVisible_InGame_ViewIncome(true);
            }
            break;
         case 12:
            CFG.toast.setInView(CFG.langManager.get("Budget") + ": " + CFG.getNumberWithSpaces("" + CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            CFG.toast.setTimeInView(6000);
            break;
         case 13:
            if (this.getMenuElement(13).getCurrent() + this.getMenuElement(15).getCurrent() + this.getMenuElement(17).getCurrent() + this.getMenuElement(19).getCurrent() > 200) {
               if (this.getMenuElement(15).getCurrent() + this.getMenuElement(17).getCurrent() > 0) {
                  CFG.game.getCiv(iCivID).setSpendings_Goods((float)this.getMenuElement(13).getCurrent() / 100.0F);
                  CFG.game_NextTurnUpdate.updateSpendingsOfVassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                  this.getMenuElement(13).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Goods() * 100.0F));
                  this.getMenuElement(15).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Research() * 100.0F));
                  this.getMenuElement(17).setCurrent((int)(CFG.game.getCiv(iCivID).getSpendings_Investments() * 100.0F));
                  if (this.getMenuElement(13).getCurrent() + this.getMenuElement(15).getCurrent() + this.getMenuElement(17).getCurrent() + this.getMenuElement(19).getCurrent() > 200) {
                     this.getMenuElement(13).setCurrent(200 - this.getMenuElement(19).getCurrent() - this.getMenuElement(15).getCurrent() - this.getMenuElement(17).getCurrent());
                  }
               } else {
                  this.getMenuElement(13).setCurrent(200 - this.getMenuElement(19).getCurrent() - this.getMenuElement(15).getCurrent() - this.getMenuElement(17).getCurrent());
               }
            }

            this.updateResearchAndIvestments();
            this.getMenuElement(13).setCurrent(this.getMenuElement(13).getCurrent());
            this.getMenuElement(17).setCurrent(this.getMenuElement(17).getCurrent());
            this.getMenuElement(16).setMin((int)(CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) * (1.0F + CFG.game.getCiv(iCivID).getModifier_Research())));
            break;
         case 15:
            if (this.getMenuElement(15).getCurrent() + this.getMenuElement(19).getCurrent() + this.getMenuElement(13).getCurrent() > 200) {
               this.getMenuElement(15).setCurrent(200 - this.getMenuElement(19).getCurrent() - this.getMenuElement(13).getCurrent());
            }

            if (this.getMenuElement(15).getCurrent() + this.getMenuElement(17).getCurrent() + this.getMenuElement(19).getCurrent() + this.getMenuElement(13).getCurrent() > 200) {
               this.getMenuElement(17).setCurrent(200 - this.getMenuElement(15).getCurrent() - this.getMenuElement(19).getCurrent() - this.getMenuElement(13).getCurrent());
            }

            this.updateResearchAndIvestments();
            this.getMenuElement(17).setCurrent(this.getMenuElement(17).getCurrent());
            this.getMenuElement(16).setMin((int)(CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) * (1.0F + CFG.game.getCiv(iCivID).getModifier_Research())));
            break;
         case 17:
            if (this.getMenuElement(17).getCurrent() + this.getMenuElement(19).getCurrent() + this.getMenuElement(13).getCurrent() > 200) {
               this.getMenuElement(17).setCurrent(200 - this.getMenuElement(19).getCurrent() - this.getMenuElement(13).getCurrent());
            }

            if (this.getMenuElement(15).getCurrent() + this.getMenuElement(17).getCurrent() + this.getMenuElement(19).getCurrent() + this.getMenuElement(13).getCurrent() > 200) {
               this.getMenuElement(15).setCurrent(200 - this.getMenuElement(17).getCurrent() - this.getMenuElement(19).getCurrent() - this.getMenuElement(13).getCurrent());
            }

            this.updateResearchAndIvestments();
            this.getMenuElement(17).setCurrent(this.getMenuElement(17).getCurrent());
            this.getMenuElement(16).setMin((int)(CFG.game_NextTurnUpdate.getResearchSpendings(iCivID, CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).iBudget/2) * (1.0F + CFG.game.getCiv(iCivID).getModifier_Research())));
      }

   }

   private final void updateResearchAndIvestments() {
      if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).iBudget <= 0) {
         this.getMenuElement(13).setCurrent(0);
         this.getMenuElement(15).setCurrent(0);
         this.getMenuElement(16).setMin(0);
         this.getMenuElement(17).setCurrent(0);
      }

      CFG.game.getCiv(iCivID).setSpendings_Goods((float)this.getMenuElement(13).getCurrent() / 100.0F);
      CFG.game.getCiv(iCivID).setSpendings_Research((float)this.getMenuElement(15).getCurrent() / 100.0F);
      CFG.game.getCiv(iCivID).setSpendings_Investments((float)this.getMenuElement(17).getCurrent() / 100.0F);
      this.updateIncomeAndExpenses();
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }
}
