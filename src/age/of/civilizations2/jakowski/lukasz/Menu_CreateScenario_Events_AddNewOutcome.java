package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Menu_CreateScenario_Events_AddNewOutcome extends SliderMenu
{
   private static final int numberOfOutcomes = 36;
   protected Menu_CreateScenario_Events_AddNewOutcome() {
      super();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      int tY = CFG.PADDING;
      menuElements.add(new Button_Menu_LR_Line(null, -1, 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
      tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 1; i < numberOfOutcomes + 1; ++i) {
         menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
         tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }
      this.initMenu(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements);
      this.updateLanguage();
   }

   @Override
   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getMenuElement(1).setText(CFG.langManager.get("TriggerAnotherEvent"));
      this.getMenuElement(2).setText(CFG.langManager.get("Annexation"));
      this.getMenuElement(3).setText(CFG.langManager.get("Occupy"));
      this.getMenuElement(4).setText(CFG.langManager.get("AddCore"));
      this.getMenuElement(5).setText(CFG.langManager.get("RemoveCore"));
      this.getMenuElement(6).setText(CFG.langManager.get("DeclareWar"));
      this.getMenuElement(7).setText(CFG.langManager.get("WhitePeace"));
      this.getMenuElement(8).setText(CFG.langManager.get("UpdateRelation"));
      this.getMenuElement(9).setText(CFG.langManager.get("CreateAVassal"));
      this.getMenuElement(10).setText(CFG.langManager.get("LiberateAVassal"));
      this.getMenuElement(11).setText(CFG.langManager.get("JoinAlliance"));
      this.getMenuElement(12).setText(CFG.langManager.get("LeaveAlliance"));
      this.getMenuElement(13).setText(CFG.langManager.get("CreateUnion"));
      this.getMenuElement(14).setText(CFG.langManager.get("FormCivilization"));
      this.getMenuElement(15).setText(CFG.langManager.get("NonAggressionPact"));
      this.getMenuElement(16).setText(CFG.langManager.get("DefensivePact"));
      this.getMenuElement(17).setText(CFG.langManager.get("GuaranteeIndependence"));
      this.getMenuElement(18).setText(CFG.langManager.get("MilitaryAccess"));
      this.getMenuElement(19).setText(CFG.langManager.get("MoveCapital"));
      this.getMenuElement(20).setText(CFG.langManager.get("ChangeLeader"));
      this.getMenuElement(21).setText(CFG.langManager.get("ChangeIdeology"));
      this.getMenuElement(22).setText(CFG.langManager.get("AddArmy"));
      this.getMenuElement(23).setText(CFG.langManager.get("UpdatePopulation"));
      this.getMenuElement(24).setText(CFG.langManager.get("UpdatePopulationPercentage"));
      this.getMenuElement(25).setText(CFG.langManager.get("UpdatePopulationOfCiv"));
      this.getMenuElement(26).setText(CFG.langManager.get("UpdateEconomy"));
      this.getMenuElement(27).setText(CFG.langManager.get("UpdateEconomyPercentage"));
      this.getMenuElement(28).setText(CFG.langManager.get("UpdateEconomyOfCiv"));
      this.getMenuElement(29).setText(CFG.langManager.get("UpdateTechnologyLevel"));
      this.getMenuElement(30).setText(CFG.langManager.get("UpdateDevelopmentLevel"));
      this.getMenuElement(31).setText(CFG.langManager.get("UpdateHappiness"));
      this.getMenuElement(32).setText(CFG.langManager.get("UpdateHappinessOfCivilization"));
      this.getMenuElement(33).setText(CFG.langManager.get("UpdateMoney"));
      this.getMenuElement(34).setText(CFG.langManager.get("UpdateMovementPoints"));
      this.getMenuElement(35).setText(CFG.langManager.get("UpdateDiplomacyPoints"));
      this.getMenuElement(36).setText(CFG.langManager.get("UpdateWastelandProvinces"));
      this.getTitle().setText(CFG.langManager.get("AddNewOutcome"));
   }

   @Override
   protected final void actionElement(final int iID) {
      switch (iID) {
         case 0: {
            this.onBackPressed();
            break;
         }
         case 1: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_TriggerAnotherEvent());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 2: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_ChangeOwner());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 3: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_Occupy());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 4: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_AddCore());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 5: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_RemoveCore());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 6: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_DeclareWar());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 7: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_WhitePeace());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 8: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_IncreaseRelation());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 9: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_CreateVassal());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 10: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_LiberateVassal());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 11: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_JoinAlliance());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 12: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_LeaveAlliance());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 13: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_JoinUnion());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 14: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_FormCivilization());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 15: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_NonAggression());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 16: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_DefensivePact());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 17: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_Independence());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 18: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_MilitaryAccess());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 19: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_MoveCapital());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 20: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_ChangeLeader());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 21: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_ChangeIdeology());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 22: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_AddArmy());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 23: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdatePopulation());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 24: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdatePopulationPerc());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 25: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdatePopulationOfCiv());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 26: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdateEconomy());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 27: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdateEconomyPerc());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 28: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdateEconomyOfCiv());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 29: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_TechLevel());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 30: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_DevLevel());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 31: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdateHappiness());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 32: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_UpdateHappinessOfCiv());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 33: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_Money());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 34: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_MovementPoints());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 35: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_DiplomacyPoints());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
         case 36: {
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.add(new Event_Outcome_Wasteland());
            CFG.eventsManager.iCreateEvent_EditConditionID = CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.size() - 1;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).editViewID();
            break;
         }
      }
   }

   @Override
   protected final void onBackPressed() {
      CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DECISION);
      CFG.menuManager.setBackAnimation(true);
   }
}
