package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event_Outcome implements Serializable {
   private static final long serialVersionUID = 0L;
   private List tData2;
   protected boolean tData2Enabled = false;

   protected int getCivID() {
      return -1;
   }

   protected void setCivID(int nCivID) {
   }

   protected int getCivID2() {
      return -1;
   }

   protected void setCivID2(int nCivID) {
   }

   protected int getValue() {
      return -1;
   }

   protected void setValue(int nValue) {
   }

   protected List getProvinces() {
      return new ArrayList();
   }

   protected void setProvinces(List nProvinces) {
   }

   protected String getText() {
      return "";
   }

   protected void setText(String nText) {
   }

   protected boolean updateCivIDAfterRemove(int nRemovedCivID) {
      return false;
   }

   protected void outcomeAction() {
   }

   protected List getHoverText() {
      List tElements = new ArrayList();
      return tElements;
   }

   protected void setSecondaryHoverText(List<MenuElement_Hover_v2_Element_Type> tData) {
      tData2Enabled = true;
      tData2 = new ArrayList<>();
      for (MenuElement_Hover_v2_Element_Type el: tData) {
         tData2.add(el);
      }
      tData.clear();
   }
   protected List getSecondaryHoverText() {
      return tData2;
   }

   protected String getConditionText() {
      return "";
   }

   protected void editViewID() {
      CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER);
   }
}
