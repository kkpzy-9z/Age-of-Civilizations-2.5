package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

class EventsManager {
   protected Events_GameData eventsGD = new Events_GameData();
   protected Event_GameData lCreateScenario_Event = new Event_GameData();
   protected int iCreateEvent_Day = 1;
   protected int iCreateEvent_Month = 1;
   protected int iCreateEvent_Year = 0;
   protected int iCreateEvent_Age = 0;
   protected boolean setSinceDate = true;
   protected int iCreateEvent_EditEventID = 0;
   protected int iCreateEvent_EditTriggerID = 0;
   protected int iCreateEvent_EditConditionID = 0;
   protected Event_SelectCivAction eSelectCivAction;

   EventsManager() {
      this.eSelectCivAction = Event_SelectCivAction.SELECT_RECIPENT;
   }

   protected final void checkEvents() {
      try {
         Gdx.app.log("AoC", "checkEvents: " + this.eventsGD.iEventsSize);

         for(int i = 0; i < this.eventsGD.iEventsSize; ++i) {
            Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + ", wasFired:" + ((Event_GameData)this.eventsGD.lEvents.get(i)).getWasFired());
            if (!((Event_GameData)this.eventsGD.lEvents.get(i)).getWasFired()) {
               Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 000");
               Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " EventcurrYear: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventYear);
               Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " iEventMonth: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventMonth);
               Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " iEventDay: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventDay);
               if (((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventYear >= Game_Calendar.currentYear && (((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventYear != Game_Calendar.currentYear || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventMonth >= Game_Calendar.currentMonth) && (((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventYear != Game_Calendar.currentYear || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventMonth != Game_Calendar.currentMonth || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Since().iEventDay > Game_Calendar.currentDay)) {
                  Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " END");
               } else {
                  Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 111");
                  if (!((Event_GameData)this.eventsGD.lEvents.get(i)).getWasTriedToRunOnce()) {
                     Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 222");
                     ((Event_GameData)this.eventsGD.lEvents.get(i)).setWasTriedToRunOnce(true);
                     this.checkCondtsitionsAndTryRun(i);
                  } else {
                     Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 333");
                     if (((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventYear == 9999999 || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventYear > Game_Calendar.currentYear || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventYear == Game_Calendar.currentYear && ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventMonth > Game_Calendar.currentMonth || ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventYear == Game_Calendar.currentYear && ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventMonth == Game_Calendar.currentMonth && ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventDate_Until().iEventDay >= Game_Calendar.currentDay) {
                        Gdx.app.log("AoC", "checkEvents: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 444");
                        this.checkCondtsitionsAndTryRun(i);
                     }
                  }
               }
            }
         }
      } catch (NullPointerException var2) {
         CFG.exceptionStack(var2);
      }

   }

   protected final void checkCondtsitionsAndTryRun(int i) {
      boolean canRunEvent = false;
      Gdx.app.log("AoC", "checkCondtsitionsAndTryRun: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " 000");

      for(int j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.size(); ++j) {
         if (((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).triggerType == Event_Type.OR && ((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).getTriggerOut()) {
            canRunEvent = true;
            break;
         }
      }

      Gdx.app.log("AoC", "checkCondtsitionsAndTryRun: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " canRunEvent1: " + canRunEvent);
      if (!canRunEvent) {
         canRunEvent = true;
         boolean checked = false;

         for(int j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.size(); ++j) {
            if (((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).triggerType != Event_Type.OR) {
               if (((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).triggerType == Event_Type.AND) {
                  if (!((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).getTriggerOut()) {
                     canRunEvent = false;
                     break;
                  }

                  checked = true;
               } else if (((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).triggerType == Event_Type.NOT) {
                  if (((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).getTriggerOut()) {
                     canRunEvent = false;
                     break;
                  }

                  checked = true;
               }
            }
         }

         Gdx.app.log("AoC", "checkCondtsitionsAndTryRun: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " canRunEvent1.5: " + canRunEvent);
         Gdx.app.log("AoC", "checkCondtsitionsAndTryRun: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " checked: " + checked);
         if (!checked) {
            canRunEvent = false;
         }
      }

      Gdx.app.log("AoC", "checkCondtsitionsAndTryRun: name: " + ((Event_GameData)this.eventsGD.lEvents.get(i)).getEventName() + " canRunEvent2: " + canRunEvent);
      if (canRunEvent) {
         this.tryRunEvent(i);
      }

   }

   protected final void tryRunEvent(int i) {
      if (((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() > 0) {
         try {
            CFG.game.getCiv(((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID()).addEventToRunID(i);
            ((Event_GameData)this.eventsGD.lEvents.get(i)).setWasFired(!((Event_GameData)this.eventsGD.lEvents.get(i)).getRepeatable());
         } catch (IndexOutOfBoundsException var3) {
         }
      }

   }

   protected final void runEvent_Tag(String nTag) {
      int tID = -1;

      for(int i = 0; i < this.getEventsSize(); ++i) {
         if (this.getEvent(i).getEventTag().equals(nTag)) {
            tID = i;
            break;
         }
      }

      if (tID >= 0) {
         CFG.game.getCiv(((Event_GameData)this.eventsGD.lEvents.get(tID)).getCivID()).addEventToRunID(tID);
         ((Event_GameData)this.eventsGD.lEvents.get(tID)).setWasFired(!((Event_GameData)this.eventsGD.lEvents.get(tID)).getRepeatable());
      }

   }

   protected final void addEvent(Event_GameData nEvent) {
      this.eventsGD.lEvents.add(nEvent);
      this.eventsGD.iEventsSize = this.eventsGD.lEvents.size();
   }

   protected final void updateEventsAferRemoveCiv(int nRemovedCivID) {
      for(int i = 0; i < this.eventsGD.iEventsSize; ++i) {
         if (((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() == nRemovedCivID) {
            this.removeEvent(i--);
         } else {
            if (((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() > nRemovedCivID) {
               ((Event_GameData)this.eventsGD.lEvents.get(i)).setCivID(((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() - 1);
            }

            int j;
            int k;
            for(j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.size(); ++j) {
               for(k = 0; k < ((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.size(); ++k) {
                  ((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).updateCivIDAfterRemove(nRemovedCivID);
               }
            }

            for(j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.size(); ++j) {
               for(k = 0; k < ((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.size(); ++k) {
                  ((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).updateCivIDAfterRemove(nRemovedCivID);
               }
            }
         }
      }

   }

   protected final void swapIDsOfCivs(int nID_A, int nID_B) {
      for(int i = 0; i < this.eventsGD.iEventsSize; ++i) {
         if (((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() == nID_A) {
            ((Event_GameData)this.eventsGD.lEvents.get(i)).setCivID(nID_B);
         } else if (((Event_GameData)this.eventsGD.lEvents.get(i)).getCivID() == nID_B) {
            ((Event_GameData)this.eventsGD.lEvents.get(i)).setCivID(nID_A);
         }

         int j;
         int k;
         for(j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.size(); ++j) {
            for(k = 0; k < ((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.size(); ++k) {
               if (((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).getCivID() == nID_A) {
                  ((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).setCivID(nID_B);
               } else if (((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).getCivID() == nID_B) {
                  ((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).setCivID(nID_A);
               }

               if (((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).getCivID2() == nID_A) {
                  ((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).setCivID2(nID_B);
               } else if (((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).getCivID2() == nID_B) {
                  ((Event_Conditions)((Event_Trigger)((Event_GameData)this.eventsGD.lEvents.get(i)).lTriggers.get(j)).lConditions.get(k)).setCivID2(nID_A);
               }
            }
         }

         for(j = 0; j < ((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.size(); ++j) {
            for(k = 0; k < ((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.size(); ++k) {
               if (((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).getCivID() == nID_A) {
                  ((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).setCivID(nID_B);
               } else if (((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).getCivID() == nID_B) {
                  ((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).setCivID(nID_A);
               }

               if (((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).getCivID2() == nID_A) {
                  ((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).setCivID2(nID_B);
               } else if (((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).getCivID2() == nID_B) {
                  ((Event_Outcome)((Event_Decision)((Event_GameData)this.eventsGD.lEvents.get(i)).lDecisions.get(j)).lOutcomes.get(k)).setCivID2(nID_A);
               }
            }
         }
      }

   }

   protected final void sortEventsByDate() {
      for(int i = 0; i < this.getEventsSize() - 1; ++i) {
         for(int j = i + 1; j < this.getEventsSize(); ++j) {
            Event_GameData tempD;
            if (this.getEvent(i).getEventDate_Since().iEventYear > this.getEvent(j).getEventDate_Since().iEventYear) {
               tempD = this.getEvent(i);
               this.setEvent(i, this.getEvent(j));
               this.setEvent(j, tempD);
            } else if (this.getEvent(i).getEventDate_Since().iEventYear == this.getEvent(j).getEventDate_Since().iEventYear) {
               if (this.getEvent(i).getEventDate_Since().iEventMonth > this.getEvent(j).getEventDate_Since().iEventMonth) {
                  tempD = this.getEvent(i);
                  this.setEvent(i, this.getEvent(j));
                  this.setEvent(j, tempD);
               } else if (this.getEvent(i).getEventDate_Since().iEventMonth == this.getEvent(j).getEventDate_Since().iEventMonth && this.getEvent(i).getEventDate_Since().iEventDay > this.getEvent(j).getEventDate_Since().iEventDay) {
                  tempD = this.getEvent(i);
                  this.setEvent(i, this.getEvent(j));
                  this.setEvent(j, tempD);
               }
            }
         }
      }

   }

   protected final void setEvent(int i, Event_GameData tDate) {
      this.eventsGD.lEvents.set(i, tDate);
   }

   protected final void clearEvents() {
      this.eventsGD.lEvents.clear();
      this.eventsGD.iEventsSize = 0;
   }

   protected final void removeEvent(int i) {
      this.eventsGD.lEvents.remove(i);
      this.eventsGD.iEventsSize = this.eventsGD.lEvents.size();
   }

   protected final String getEventTypeText(Event_Type tType) {
      if (tType == Event_Type.AND) {
         return CFG.langManager.get("AND");
      } else {
         return tType == Event_Type.OR ? CFG.langManager.get("OR") : CFG.langManager.get("NOT");
      }
   }

   protected final void selectCivAction(int nCivID) {
      switch (this.eSelectCivAction) {
         case SELECT_RECIPENT:
            this.lCreateScenario_Event.setCivID(nCivID);
            break;
         case SELECT_COND_CIV_EXIST:
         case COND_SELECTCIV_DECISIONTAKEN:
         case SELECT_CIV_CONTROL_PROVINCES:
         case SELECT_CIV_CONTROL_OCCUPIED:
         case SELECT_CIV_HAVE_ARMY:
         case SELECT_CIV_CORE:
         case SELECT_CIV_ISCAPITAL:
         case SELECT_CIV_NUMOFPROVINCES:
         case SELECT_CIV_NUMOFPROVINCES_LOW:
         case SELECT_CIV_NUMOFUNITS:
         case SELECT_CIV_NUMOFUNITS_LOW:
         case SELECT_CIV_NUMOFVASSALS:
         case SELECT_CIV_NUMOFVASSALS_LOW:
         case SELECT_CIV_NUMOFWARS:
         case SELECT_CIV_NUMOFWARS_LOW:
         case SELECT_CIV_NUMOFALLIES:
         case SELECT_CIV_NUMOFALLIES_LOW:
         case SELECT_CIV_NUMOFNEIGHBORS_LOW:
         case SELECT_CIV_NUMOFNEIGHBORS:
         case SELECT_CIV_POPULATION:
         case SELECT_CIV_POPULATION_LOW:
         case SELECT_CIV_ECONOMY_LOW:
         case SELECT_CIV_ECONOMY:
         case SELECT_CIV_RELATION_LOW:
         case SELECT_CIV_RELATION:
         case SELECT_CIV_ISATWAR:
         case SELECT_CIV_ALLIES:
         case SELECT_CIV_ATWAR:
         case SELECT_CIV_DEFENSIVE:
         case SELECT_CIV_INDEPENDENCE:
         case SELECT_CIV_NONAGGRESSION:
         case SELECT_CIV_MILITARYACCESS:
         case SELECT_CIV_ISVASSAL:
         case SELECT_CIV_ISVASSALOFCIV:
         case SELECT_CIV_ISPARTOFHRE:
         case SELECT_CIV_IDEOLOGY:
         case SELECT_CIV_TECHNOLOGY:
         case SELECT_CIV_TECHNOLOGY_LOW:
         case SELECT_CIV_HAPPINESS:
         case SELECT_CIV_HAPPINESS_LOW:
         case SELECT_CIV_TREASURY:
         case SELECT_CIV_TREASURY_LOW:
         case SELECT_CIV_CONTROLLEDBYPLAYER:
            ((Event_Conditions)this.lCreateScenario_Event.getTrigger(this.iCreateEvent_EditTriggerID).lConditions.get(this.iCreateEvent_EditConditionID)).setCivID(nCivID);
            break;
         case SELECT_CIV_RELATION2:
         case SELECT_CIV_RELATION_LOW2:
         case SELECT_CIV_ALLIES2:
         case SELECT_CIV_ATWAR2:
         case SELECT_CIV_DEFENSIVE2:
         case SELECT_CIV_INDEPENDENCE2:
         case SELECT_CIV_NONAGGRESSION2:
         case SELECT_CIV_MILITARYACCESS2:
         case SELECT_CIV_ISVASSALOFCIV2:
            ((Event_Conditions)this.lCreateScenario_Event.getTrigger(this.iCreateEvent_EditTriggerID).lConditions.get(this.iCreateEvent_EditConditionID)).setCivID2(nCivID);
            break;
         case SELECT_CONTROLS_PROVINCES:
         case SELECT_OCCUPIED_PROVINCES:
         case SELECT_PROVINCES_HAVEARMY:
         case SELECT_PROVINCES_HAVECORE:
         case SELECT_PROVINCES_ISCAPITAL:
         case SELECT_PROVINCES_DEVELOPMENT:
         case SELECT_PROVINCES_DEVELOPMENT_LOW:
         case SELECT_PROVINCES_WASTELAND:
         case SELECT_PROVINCES_NEUTRAL:
         case SELECT_PROVINCES_WATCHTOWER:
         case SELECT_PROVINCES_FORT:
         case SELECT_PROVINCES_FARM:
         case SELECT_PROVINCES_PORT:
            ((Event_Conditions)this.lCreateScenario_Event.getTrigger(this.iCreateEvent_EditTriggerID).lConditions.get(this.iCreateEvent_EditConditionID)).setProvinces(CFG.game.getSelectedProvinces().getProvinces());
            break;
         case OUT_SELECTIDEOLOGY_COND_IDEOLOGY:
            ((Event_Conditions)this.lCreateScenario_Event.getTrigger(this.iCreateEvent_EditTriggerID).lConditions.get(this.iCreateEvent_EditConditionID)).setValue(nCivID);
            break;
         case OUT_SELECTCIV:
         case OUT_SELECTCIV_ADDCORE:
         case OUT_SELECTCIV_REMOVECORE:
         case OUT_SELECTCIV_DECLAREWAR_A:
         case OUT_SELECTCIV_WHITEPEACE_A:
         case OUT_SELECTCIV_INCRELATION_A:
         case OUT_SELECTCIV_DECRELATION_A:
         case OUT_SELECTCIV_CREATEVASSAL_A:
         case OUT_SELECTCIV_JOINALLIANCE_A:
         case OUT_SELECTCIV_LEAVEALLIANCE:
         case OUT_SELECTCIV_JOINUNION_A:
         case OUT_SELECTCIV_NONAGGRESSION_A:
         case OUT_SELECTCIV_MILITARY_A:
         case OUT_SELECTCIV_DEFENSIVE_A:
         case OUT_SELECTCIV_INDEPENDENCE_A:
         case OUT_SELECTCIV_MOVECAPITAL:
         case OUT_SELECTCIV_LIBERATEVASSAL:
         case OUT_SELECTCIV_CHANGEIDEOLOGY:
         case OUT_SELECTCIV_ADDARMY:
         case OUT_SELECTCIV_UPDATEPOPULAION:
         case OUT_SELECTCIV_UPDATEPOPULAION_PERC:
         case OUT_SELECTCIV_UPDATEECONOMY_PERC:
         case OUT_SELECTCIV_UPDATEECONOMY:
         case OUT_SELECTCIV_UPDATEECONOMY_OFCIV:
         case OUT_SELECTCIV_UPDATEPOPULAION_OFCIV:
         case OUT_SELECTCIV_TECHLEVEL:
         case OUT_SELECTCIV_DEVELOPMENT:
         case OUT_SELECTCIV_HAPPINESS:
         case OUT_SELECTCIV_HAPPINESS_OF_CIV:
         case OUT_SELECTCIV_MONEY:
         case OUT_SELECTCIV_DIPLOMACYPOINTS:
         case OUT_SELECTCIV_MOVEMENTPOINTS:
         case OUT_SELECTCIV_FORMCIV:
         case OUT_SELECTCIV_OCCUPY:
         case OUT_SELECTCIV_CHANGELEADER:
            ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setCivID(nCivID);
            break;
         case OUT_SELECTCIV2:
         case OUT_SELECTCIV_DECLAREWAR_B:
         case OUT_SELECTCIV_WHITEPEACE_B:
         case OUT_SELECTCIV_INCRELATION_B:
         case OUT_SELECTCIV_DECRELATION_B:
         case OUT_SELECTCIV_CREATEVASSAL_B:
         case OUT_SELECTCIV_JOINALLIANCE_B:
         case OUT_SELECTCIV_JOINUNION_B:
         case OUT_SELECTCIV_NONAGGRESSION_B:
         case OUT_SELECTCIV_MILITARY_B:
         case OUT_SELECTCIV_DEFENSIVE_B:
         case OUT_SELECTCIV_INDEPENDENCE_B:
         case OUT_SELECTCIV2_OCCUPY:
            ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setCivID2(nCivID);
            break;
         case OUT_SELECTPROVINCES:
         case OUT_SELECTPROVINCES_ADDCORE:
         case OUT_SELECTPROVINCES_REMOVECORE:
         case OUT_SELECTPROVINCES_CREATEVASSAL:
         case OUT_SELECTPROVICNES_ADDARMY:
         case OUT_SELECTPROVICNES_UPDATEPOPULAION:
         case OUT_SELECTPROVICNES_UPDATEPOPULAION_PERC:
         case OUT_SELECTPROVICNES_UPDATEECONOMY:
         case OUT_SELECTPROVICNES_UPDATEECONOMY_PERC:
         case OUT_SELECTPROVICNES_DEVELOPMENT:
         case OUT_SELECTPROVICNES_HAPPINESS:
         case OUT_SELECTPROVICNES_WASTELAND:
         case OUT_SELECTPROVINCES_OCCUPY:
            ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setProvinces(CFG.game.getSelectedProvinces().getProvinces());
            break;
         case OUT_SELECTPROVICNES_MOVECAPITAL:
            if (CFG.game.getSelectedProvinces().getProvinces().size() > 0) {
               ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setValue((Integer)CFG.game.getSelectedProvinces().getProvinces().get(0));
            } else {
               ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setValue(-1);
            }
            break;
         case OUT_SELECTIDEOLOGY_CHANGEIDEOLOGY:
            ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setValue(nCivID);
      }

   }

   protected final void selectCivBack() {
      switch (this.eSelectCivAction) {
         case SELECT_RECIPENT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS);
            CFG.menuManager.setVisibleCreateScenario_Events_Edit(true);
            break;
         case SELECT_COND_CIV_EXIST:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_CIVEXIST);
            break;
         case COND_SELECTCIV_DECISIONTAKEN:
         case COND_SELECTDECISION_DECISIONTAKEN:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN);
            break;
         case SELECT_CIV_CONTROL_PROVINCES:
         case SELECT_CONTROLS_PROVINCES:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_CONTROLS_PROVINCES);
            break;
         case SELECT_CIV_CONTROL_OCCUPIED:
         case SELECT_OCCUPIED_PROVINCES:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES);
            break;
         case SELECT_CIV_HAVE_ARMY:
         case SELECT_PROVINCES_HAVEARMY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_HAVEARMY);
            break;
         case SELECT_CIV_CORE:
         case SELECT_PROVINCES_HAVECORE:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_HAVECORE);
            break;
         case SELECT_CIV_ISCAPITAL:
         case SELECT_PROVINCES_ISCAPITAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ISCAPITAL);
            break;
         case SELECT_CIV_NUMOFPROVINCES:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES);
            break;
         case SELECT_CIV_NUMOFPROVINCES_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW);
            break;
         case SELECT_CIV_NUMOFUNITS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFUNITS);
            break;
         case SELECT_CIV_NUMOFUNITS_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW);
            break;
         case SELECT_CIV_NUMOFVASSALS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS);
            break;
         case SELECT_CIV_NUMOFVASSALS_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW);
            break;
         case SELECT_CIV_NUMOFWARS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFWARS);
            break;
         case SELECT_CIV_NUMOFWARS_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW);
            break;
         case SELECT_CIV_NUMOFALLIES:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFALLIES);
            break;
         case SELECT_CIV_NUMOFALLIES_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW);
            break;
         case SELECT_CIV_NUMOFNEIGHBORS_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW);
            break;
         case SELECT_CIV_NUMOFNEIGHBORS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS);
            break;
         case SELECT_CIV_POPULATION:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_POPULATION);
            break;
         case SELECT_CIV_POPULATION_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_POPULATION_LOW);
            break;
         case SELECT_CIV_ECONOMY_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW);
            break;
         case SELECT_CIV_ECONOMY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ECONOMY);
            break;
         case SELECT_CIV_RELATION_LOW:
         case SELECT_CIV_RELATION_LOW2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_RELATION_LOW);
            break;
         case SELECT_CIV_RELATION:
         case SELECT_CIV_RELATION2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_RELATION);
            break;
         case SELECT_CIV_ISATWAR:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ISATWAR);
            break;
         case SELECT_CIV_ALLIES:
         case SELECT_CIV_ALLIES2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ALLIES);
            break;
         case SELECT_CIV_ATWAR:
         case SELECT_CIV_ATWAR2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ATWAR);
            break;
         case SELECT_CIV_DEFENSIVE:
         case SELECT_CIV_DEFENSIVE2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_DEFENSIVE);
            break;
         case SELECT_CIV_INDEPENDENCE:
         case SELECT_CIV_INDEPENDENCE2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_INDEPENDENCE);
            break;
         case SELECT_CIV_NONAGGRESSION:
         case SELECT_CIV_NONAGGRESSION2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NONAGGRESSION);
            break;
         case SELECT_CIV_MILITARYACCESS:
         case SELECT_CIV_MILITARYACCESS2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_MILITARYACCESS);
            break;
         case SELECT_CIV_ISVASSAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ISVASSAL);
            break;
         case SELECT_CIV_ISVASSALOFCIV:
         case SELECT_CIV_ISVASSALOFCIV2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV);
            break;
         case SELECT_CIV_ISPARTOFHRE:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE);
            break;
         case SELECT_CIV_IDEOLOGY:
         case OUT_SELECTIDEOLOGY_COND_IDEOLOGY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_IDEOLOGY);
            break;
         case SELECT_CIV_TECHNOLOGY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_TECHNOLOGY);
            break;
         case SELECT_CIV_TECHNOLOGY_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW);
            break;
         case SELECT_CIV_HAPPINESS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_HAPPINESS);
            break;
         case SELECT_CIV_HAPPINESS_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW);
            break;
         case SELECT_CIV_TREASURY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_TREASURY);
            break;
         case SELECT_CIV_TREASURY_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_TREASURY_LOW);
            break;
         case SELECT_CIV_CONTROLLEDBYPLAYER:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER);
            break;
         case SELECT_PROVINCES_DEVELOPMENT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_DEVELOPMENT);
            break;
         case SELECT_PROVINCES_DEVELOPMENT_LOW:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW);
            break;
         case SELECT_PROVINCES_WASTELAND:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_WASTELAND);
            break;
         case SELECT_PROVINCES_NEUTRAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_NEUTRAL);
            break;
         case SELECT_PROVINCES_WATCHTOWER:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_WATCHTOWER);
            break;
         case SELECT_PROVINCES_FORT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_FORT);
            break;
         case SELECT_PROVINCES_FARM:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_FARM);
            break;
         case SELECT_PROVINCES_PORT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_COND_PORT);
            break;
         case OUT_SELECTCIV:
         case OUT_SELECTCIV2:
         case OUT_SELECTPROVINCES:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER);
            break;
         case OUT_SELECTCIV_ADDCORE:
         case OUT_SELECTPROVINCES_ADDCORE:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_ADDCORE);
            break;
         case OUT_SELECTCIV_REMOVECORE:
         case OUT_SELECTPROVINCES_REMOVECORE:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_ADDCORE);
            break;
         case OUT_SELECTCIV_DECLAREWAR_A:
         case OUT_SELECTCIV_DECLAREWAR_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_DECLAREWAR);
            break;
         case OUT_SELECTCIV_WHITEPEACE_A:
         case OUT_SELECTCIV_WHITEPEACE_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_WHITEPEACE);
            break;
         case OUT_SELECTCIV_INCRELATION_A:
         case OUT_SELECTCIV_INCRELATION_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_INCRELATION);
            break;
         case OUT_SELECTCIV_DECRELATION_A:
         case OUT_SELECTCIV_DECRELATION_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_DECRELATION);
            break;
         case OUT_SELECTCIV_CREATEVASSAL_A:
         case OUT_SELECTCIV_CREATEVASSAL_B:
         case OUT_SELECTPROVINCES_CREATEVASSAL:
         case OUT_SELECTAUTONOMY_CREATEVASSAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL);
            break;
         case OUT_SELECTCIV_JOINALLIANCE_A:
         case OUT_SELECTCIV_JOINALLIANCE_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE);
            break;
         case OUT_SELECTCIV_LEAVEALLIANCE:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE);
            break;
         case OUT_SELECTCIV_JOINUNION_A:
         case OUT_SELECTCIV_JOINUNION_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_JOINUNION);
            break;
         case OUT_SELECTCIV_NONAGGRESSION_A:
         case OUT_SELECTCIV_NONAGGRESSION_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION);
            break;
         case OUT_SELECTCIV_MILITARY_A:
         case OUT_SELECTCIV_MILITARY_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_MILITARY);
            break;
         case OUT_SELECTCIV_DEFENSIVE_A:
         case OUT_SELECTCIV_DEFENSIVE_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_DEFENSIVE);
            break;
         case OUT_SELECTCIV_INDEPENDENCE_A:
         case OUT_SELECTCIV_INDEPENDENCE_B:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_INDEPENENCE);
            break;
         case OUT_SELECTCIV_MOVECAPITAL:
         case OUT_SELECTPROVICNES_MOVECAPITAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL);
            break;
         case OUT_SELECTCIV_LIBERATEVASSAL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL);
            break;
         case OUT_SELECTCIV_CHANGEIDEOLOGY:
         case OUT_SELECTIDEOLOGY_CHANGEIDEOLOGY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY);
            break;
         case OUT_SELECTCIV_CHANGELEADER:
         case OUT_SELECTLEADER:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGELEADER);
            break;
         case OUT_SELECTCIV_ADDARMY:
         case OUT_SELECTPROVICNES_ADDARMY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_ADDARMY);
            break;
         case OUT_SELECTCIV_UPDATEPOPULAION:
         case OUT_SELECTPROVICNES_UPDATEPOPULAION:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION);
            break;
         case OUT_SELECTCIV_UPDATEPOPULAION_PERC:
         case OUT_SELECTPROVICNES_UPDATEPOPULAION_PERC:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC);
            break;
         case OUT_SELECTCIV_UPDATEECONOMY_PERC:
         case OUT_SELECTPROVICNES_UPDATEECONOMY_PERC:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMYPERC);
            break;
         case OUT_SELECTCIV_UPDATEECONOMY:
         case OUT_SELECTPROVICNES_UPDATEECONOMY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY);
            break;
         case OUT_SELECTCIV_UPDATEECONOMY_OFCIV:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV);
            break;
         case OUT_SELECTCIV_UPDATEPOPULAION_OFCIV:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV);
            break;
         case OUT_SELECTCIV_TECHLEVEL:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_TECHLEVEL);
            break;
         case OUT_SELECTCIV_DEVELOPMENT:
         case OUT_SELECTPROVICNES_DEVELOPMENT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_DEVELOPEMNT);
            break;
         case OUT_SELECTCIV_HAPPINESS:
         case OUT_SELECTPROVICNES_HAPPINESS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_HAPPINESS);
            break;
         case OUT_SELECTCIV_HAPPINESS_OF_CIV:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV);
            break;
         case OUT_SELECTCIV_MONEY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_MONEY);
            break;
         case OUT_SELECTCIV_DIPLOMACYPOINTS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS);
            break;
         case OUT_SELECTCIV_MOVEMENTPOINTS:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS);
            break;
         case OUT_SELECTCIV_FORMCIV:
         case OUT_SELECTCIV_FORMCIV2:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_FORM_CIV);
            break;
         case OUT_SELECTCIV_OCCUPY:
         case OUT_SELECTCIV2_OCCUPY:
         case OUT_SELECTPROVINCES_OCCUPY:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_OCCUPY);
            break;
         case OUT_SELECTPROVICNES_WASTELAND:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_WASTELAND);
            break;
         case OUT_SELECTEVENT:
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT);
      }

   }

   protected final Event_GameData getEvent(int i) {
      return (Event_GameData)this.eventsGD.lEvents.get(i);
   }

   protected final int getEventsSize() {
      return this.eventsGD.iEventsSize;
   }
}
