package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Event_GameData implements Serializable {
   private static final long serialVersionUID = 0L;
   private String sEventName = "";
   private String sEventTag;
   private String sEventPicture = "";
   private String sEventSound = "";
   private boolean wasFired = false;
   private boolean wasTriedToRunOnce = false;
   private boolean repeatable = false;
   protected boolean superEvent = false;
   private int iCivID_Recipient = -1;
   private Event_PopUp event_PopUp = new Event_PopUp();
   protected List<Event_Trigger> lTriggers = new ArrayList<>();
   protected List<Event_Decision> lDecisions = new ArrayList<>();
   private int iTurn_Fired = -1;
   private Event_Date eventDate_Since = new Event_Date();
   private Event_Date eventDate_Until = new Event_Date();

   protected Event_GameData() {
      this.sEventTag = System.currentTimeMillis() + CFG.extraRandomTag();
      this.lDecisions = new ArrayList<>();
   }

   protected void setup_Event(String sEventName, String sEventPicture, String sEventTag, int iCivID_Recipient) {
      this.sEventName = sEventName;
      this.sEventPicture = (sEventPicture + ".png"); //append png
      this.sEventTag = sEventTag;

      this.iCivID_Recipient = iCivID_Recipient;
   }

   protected void setup_EventPopUp(boolean bPopUp, String sText) {
      this.event_PopUp.showPopUp = bPopUp;
      this.event_PopUp.sText = sText;
   }

   protected void setup_EventDecisions(String sTitle, Event_Outcome outcomes) {
      Event_Decision eventDecision = new Event_Decision();
      eventDecision.sTitle = sTitle;
      eventDecision.lOutcomes.add(outcomes);
      this.lDecisions.add(eventDecision);
   }

   protected void setup_EventDecisions_List(String sTitle, ArrayList<Event_Outcome> outcomes) {
      Event_Decision eventDecision = new Event_Decision();
      eventDecision.sTitle = sTitle;
      eventDecision.lOutcomes.addAll(outcomes);
      this.lDecisions.add(eventDecision);
   }

   protected void setup_EventTriggers(Event_Conditions conditions) {
      Event_Trigger eventTrigger = new Event_Trigger();
      eventTrigger.lConditions.add(conditions);
      this.lTriggers.add(eventTrigger);
   }

   protected final void checkTriggers() {
      for(int i = 0; i < this.getTriggersSize(); ++i) {
         if (this.getTrigger(i).lConditions.size() == 0) {
            this.removeTrigger(i--);
         }
      }
   }

   protected final void checkDecisions() {
      for(int i = 0; i < this.lDecisions.size(); ++i) {
         if (this.lDecisions.get(i).lOutcomes.size() == 0 && this.lDecisions.get(i).sTitle.equals("")) {
            this.lDecisions.remove(i--);
         }
      }
   }

   protected final String getEventTag() {
      return this.sEventTag;
   }

   protected final void setEventTag(String sEventTag) {
      this.sEventTag = sEventTag;
   }

   protected final String getEventName() {
      return this.sEventName;
   }

   protected final void setEventName(String sEventName) {
      this.sEventName = sEventName;
   }

   protected final int getCivID() {
      return this.iCivID_Recipient;
   }

   protected final void setCivID(int iCivID_Recipient) {
      this.iCivID_Recipient = iCivID_Recipient;
   }

   protected final boolean getWasFired() {
      return this.wasFired;
   }

   protected final void setWasFired(boolean wasFired) {
      this.wasFired = wasFired;
   }

   protected final Integer getFired() {
      return this.iTurn_Fired;
   }

   protected final void setFired(int iTurn_Fired) {
      this.iTurn_Fired = iTurn_Fired;
   }

   protected final Event_Date getEventDate_Since() {
      return this.eventDate_Since;
   }

   protected final void setEventDate_Since(int nDay, int nMonth, int nYear) {
      this.eventDate_Since.iEventDay = nDay;
      this.eventDate_Since.iEventMonth = nMonth;
      this.eventDate_Since.iEventYear = nYear;
   }

   protected final Event_Date getEventDate_Until() {
      return this.eventDate_Until;
   }

   protected final void setEventDate_Until(int nDay, int nMonth, int nYear) {
      this.eventDate_Until.iEventDay = nDay;
      this.eventDate_Until.iEventMonth = nMonth;
      this.eventDate_Until.iEventYear = nYear;
   }

   protected final Event_PopUp getEvent_PopUp() {
      return this.event_PopUp;
   }

   protected final int getTriggersSize() {
      return this.lTriggers.size();
   }

   protected final Event_Trigger getTrigger(int i) {
      return this.lTriggers.get(i);
   }

   protected final void addNewTrigger() {
      this.lTriggers.add(new Event_Trigger());
   }

   protected final void removeTrigger(int i) {
      this.lTriggers.remove(i);
   }

   protected final String getEventPicture() {
      return this.sEventPicture;
   }

   protected final void setEvent_PopUp(Event_PopUp eventPopUp) {
      this.event_PopUp = eventPopUp;
   }

   protected final void setEventPicture(String sEventPicture) {
      this.sEventPicture = sEventPicture;
   }
   //get and set event sound file
   protected final String getEventSound() {
      //if sound set return it, else return nothing
      if (this.sEventSound != null && this.sEventSound.length() > 0) {
         return this.sEventSound;
      } else {
         return "";
      }
   }
   protected final void setEventSound(String sEventSound) { this.sEventSound = sEventSound; }

   protected final boolean getWasTriedToRunOnce() {
      return this.wasTriedToRunOnce;
   }

   protected final void setWasTriedToRunOnce(boolean wasTriedToRunOnce) {
      this.wasTriedToRunOnce = wasTriedToRunOnce;
   }

   protected final boolean getRepeatable() {
      return this.repeatable;
   }

   protected final void setRepeatable(boolean repeatable) {
      this.repeatable = repeatable;
   }
}
