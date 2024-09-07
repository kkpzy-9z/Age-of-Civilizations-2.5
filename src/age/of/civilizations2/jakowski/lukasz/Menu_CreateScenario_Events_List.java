package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Menu_CreateScenario_Events_List extends SliderMenu {
   //init search string
   private String nSearch = null;

   protected Menu_CreateScenario_Events_List() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4;
      int tempElemH = CFG.BUTTON_HEIGHT * 3 / 4;
      List menuElements = new ArrayList();
      int tPosY = CFG.PADDING;

      //default search string, add search button
      this.nSearch = CFG.langManager.get("Search");
      menuElements.add(new Button_NewGameStyle("", -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Search"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            if (isActive) {
               CFG.fontMain.getData().setScale(0.8F);
               CFG.drawText(oSB, this.getTextToDraw(), this.getPosX() + (CFG.PADDING * 2) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, this.getColor(isActive));
               CFG.fontMain.getData().setScale(1.0F);
            } else {
               CFG.fontMain.getData().setScale(0.8F);
               CFG.drawText(oSB, this.getTextToDraw(), this.getPosX() + (CFG.PADDING * 2) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, this.getColor(isActive));
               CFG.fontMain.getData().setScale(1.0F);
            }
         }

         protected String getTextToDraw() {
            return Menu_CreateScenario_Events_List.this.nSearch + ": " + super.getTextToDraw();
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("AddNewEvent"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      for(int i = 0; i < CFG.eventsManager.getEventsSize(); ++i) {
         //if search then skip if event name doesn't contain search query
         if (CFG.sSearch != null && CFG.sSearch.length() > 0) {
            if (!CFG.langManager.get(CFG.eventsManager.getEvent(i).getEventName()).toLowerCase().contains(CFG.sSearch.toLowerCase())) continue;
         }

         CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.getEvent(i).getEventDate_Since().iEventDay;
         CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.getEvent(i).getEventDate_Since().iEventMonth;
         CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.getEvent(i).getEventDate_Since().iEventYear;
         menuElements.add(new Button_New_Game_Players_CivID_LEFT(CFG.eventsManager.getEvent(i).getCivID(), CFG.langManager.get(CFG.eventsManager.getEvent(i).getEventName()) + (CFG.eventsManager.getEvent(i).getCivID() > 0 && CFG.eventsManager.getEvent(i).getCivID() < CFG.game.getCivsSize() ? ", " + CFG.game.getCiv(CFG.eventsManager.getEvent(i).getCivID()).getCivName() : "") + ", " + (CFG.eventsManager.iCreateEvent_Year == 9999999 ? CFG.langManager.get("NoDate") : Game_Calendar.getCurrentDate_CreateEvent()), CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
            int iCurrent = 0;

            protected int getCurrent() {
               return this.iCurrent;
            }

            protected void setCurrent(int nCurrent) {
               this.iCurrent = nCurrent;
            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Title") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.eventsManager.getEvent(this.getCurrent()).getEventName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               try {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RecipientCiv") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.eventsManager.getEvent(this.getCurrent()).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.eventsManager.getEvent(this.getCurrent()).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } catch (IndexOutOfBoundsException var4) {
               }

               CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Since().iEventDay;
               CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Since().iEventMonth;
               CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Since().iEventYear;
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Since") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.eventsManager.iCreateEvent_Year == 9999999 ? CFG.langManager.get("NoDate") : Game_Calendar.getCurrentDate_CreateEvent(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Until().iEventDay;
               CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Until().iEventMonth;
               CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.getEvent(this.getCurrent()).getEventDate_Until().iEventYear;
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Until") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.eventsManager.iCreateEvent_Year == 9999999 ? CFG.langManager.get("NoDate") : Game_Calendar.getCurrentDate_CreateEvent(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         menuElements.add(new Button_Game_NewGameBoxStyle_RIGHT_Remove(tempW - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), tPosY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
            int iCurrent = 0;

            protected int getCurrent() {
               return this.iCurrent;
            }

            protected void setCurrent(int nCurrent) {
               this.iCurrent = nCurrent;
            }
         });

         //set current to current event id
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i);
         ((MenuElement)menuElements.get(menuElements.size() - 2)).setCurrent(i);

         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 5, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4, this.getHeight());
            oSB.setColor(new Color(0.003921569F, 0.32941177F, 0.50980395F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(0.003921569F, 0.32941177F, 0.50980395F, 0.375F));
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
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.time).draw(oSB, nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 - CFG.PADDING - ImageManager.getImage(Images.time).getWidth() + iTranslateX, 2 + nPosY - this.getHeight() + this.getHeight() / 2 - ImageManager.getImage(Images.time).getHeight() / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH - tempW, CFG.BUTTON_HEIGHT + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 5, tempW, Math.min(tPosY, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 5) - CFG.PADDING * 2), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      //init search
      if (CFG.sSearch != null) {
         this.getMenuElement(0).setText(CFG.sSearch);
      }

      this.getTitle().setText(CFG.langManager.get("Events"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), false, true);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
   }

   //new onbackpressed override for search
   @Override
   protected void onBackPressed() {
      //reset search
      CFG.sSearch = null;

      CFG.eventsManager.selectCivBack();
      CFG.menuManager.clearCreateScenario_Events_AddCiv();
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.showKeyboard();
            return;
         case 1:
            CFG.eventsManager.addEvent(new Event_GameData());
            CFG.eventsManager.iCreateEvent_EditEventID = CFG.eventsManager.getEventsSize() - 1;
            CFG.eventsManager.lCreateScenario_Event = CFG.eventsManager.getEvent(CFG.eventsManager.iCreateEvent_EditEventID);
            CFG.menuManager.setVisibleCreateScenario_Events_Edit(true);

            //reset search
            CFG.sSearch = null;
            return;
         default:
            //CFG.eventsManager.iCreateEvent_EditEventID = iID / 2;
            //get object-saved event id
            CFG.eventsManager.iCreateEvent_EditEventID = this.getMenuElement(iID).getCurrent();

            iID -= 2;
            if (iID % 2 == 0) {
               CFG.eventsManager.lCreateScenario_Event = CFG.eventsManager.getEvent(CFG.eventsManager.iCreateEvent_EditEventID);
               CFG.menuManager.setVisibleCreateScenario_Events_Edit(true);
            } else {
               CFG.setDialogType(Dialog.CREATE_SCENARIO_REMOVE_EVENT);
            }

            //reset search
            CFG.sSearch = null;
      }
   }
}
