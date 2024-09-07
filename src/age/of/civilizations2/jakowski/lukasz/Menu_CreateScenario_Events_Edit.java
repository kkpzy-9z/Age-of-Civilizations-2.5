package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_CreateScenario_Events_Edit extends SliderMenu {
   private String sEventTitle;
   private int iEventTitleWidth = 0;
   private String sPicture;
   private int iPictureWidth = 0;
   private String sSound;
   private int iSoundWidth = 0;

   protected Menu_CreateScenario_Events_Edit() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4;
      int tempElemH = CFG.BUTTON_HEIGHT * 3 / 4;
      List menuElements = new ArrayList();
      int tPosY = CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left((String)null, -1, CFG.PADDING, tPosY, (tempW - CFG.PADDING * 2) / 2 + 1, (int)((float)CFG.BUTTON_HEIGHT * 0.5F), true));
      menuElements.add(new Button_NewGameStyle_Right((String)null, -1, tempW - (tempW - CFG.PADDING * 2) / 2 - CFG.PADDING, tPosY, (tempW - CFG.PADDING * 2) / 2, (int)((float)CFG.BUTTON_HEIGHT * 0.5F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle(CFG.eventsManager.lCreateScenario_Event.getEventName(), CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true) {
         protected void buildElementHover() {
            if (this.getText().length() > 0) {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            } else {
               this.menuElementHover = null;
            }

         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, Menu_CreateScenario_Events_Edit.this.sEventTitle, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_OPTIONS_NS);
            CFG.fontMain.getData().setScale(1.0F);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }

         protected int getTextPos() {
            return super.getTextPos() + Menu_CreateScenario_Events_Edit.this.iEventTitleWidth;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_CivID(CFG.eventsManager.lCreateScenario_Event.getCivID(), "", CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true) {
         protected String getTextToDraw() {
            return this.getText() + (this.getCurrent() > 0 ? ": " + CFG.game.getCiv(this.getCurrent()).getCivName() : "");
         }

         protected void buildElementHover() {
            ArrayList nElements;
            ArrayList nElementsx;
            if (this.getCurrent() > 0) {
               try {
                  nElements = new ArrayList();
                  nElementsx = new ArrayList();
                  nElementsx.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Recipient") + ": "));
                  nElementsx.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(this.getCurrent()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElementsx.add(new MenuElement_Hover_v2_Element_Type_Flag(this.getCurrent(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nElementsx));
                  nElementsx.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               } catch (IndexOutOfBoundsException var4) {
                  nElementsx = new ArrayList();
                  List nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectCivilization"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElementsx.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElementsx);
               }
            } else {
               nElements = new ArrayList();
               nElementsx = new ArrayList();
               nElementsx.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectCivilization"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nElementsx));
               nElementsx.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left((String)null, -1, CFG.PADDING, tPosY, (tempW - CFG.PADDING * 2) / 2 + 1, (int)((float)CFG.BUTTON_HEIGHT * 0.5F), true));
      menuElements.add(new Button_NewGameStyle_Right((String)null, -1, tempW - (tempW - CFG.PADDING * 2) / 2 - CFG.PADDING, tPosY, (tempW - CFG.PADDING * 2) / 2, (int)((float)CFG.BUTTON_HEIGHT * 0.5F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("Repeatable"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true, CFG.eventsManager.lCreateScenario_Event.getRepeatable()) {
         protected boolean getCheckboxState() {
            return CFG.eventsManager.lCreateScenario_Event.getRepeatable();
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //superevent toggle
      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("Superevent"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true, CFG.eventsManager.lCreateScenario_Event.superEvent) {
         protected boolean getCheckboxState() {
            return CFG.eventsManager.lCreateScenario_Event.superEvent;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Text_BudgetTitle(CFG.langManager.get("Triggers"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("AddNewTrigger"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      int i;
      for(i = 0; i < CFG.eventsManager.lCreateScenario_Event.getTriggersSize(); ++i) {
         menuElements.add(new Button_NewGameStyle_Left(CFG.eventsManager.lCreateScenario_Event.getTrigger(i).getTriggerText(), CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.6F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
         menuElements.add(new Button_NewGameStyle_Middle(CFG.eventsManager.getEventTypeText(CFG.eventsManager.lCreateScenario_Event.getTrigger(i).triggerType), -1, tempW - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F) * 2, tPosY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         menuElements.add(new Button_Game_NewGameBoxStyle_RIGHT_Remove(tempW - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), tPosY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Delete"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      menuElements.add(new Text_BudgetTitle(CFG.langManager.get("PopUp"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("ShowPopUp"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true, CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().showPopUp) {
         protected boolean getCheckboxState() {
            return CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().showPopUp;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      int var10005 = CFG.PADDING * 2;
      int var10007 = tempW - CFG.PADDING * 4;
      menuElements.add(new Text_Scrollable(CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().sText, var10005, tPosY, var10007, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL, 0.8F) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Description") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(this.getText())));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle(CFG.eventsManager.lCreateScenario_Event.getEventPicture(), CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();

            //recommended resoltuon
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RecommendedSR") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("512 x 96"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RecommendedSER") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("1200 x 833"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();

            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Path") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + "SCENARIO_TAG/" + "events/"));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Path") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("UI/events/" + this.getText()));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, Menu_CreateScenario_Events_Edit.this.sPicture, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_OPTIONS_NS);
            CFG.fontMain.getData().setScale(1.0F);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }

         protected int getTextPos() {
            return super.getTextPos() + Menu_CreateScenario_Events_Edit.this.iPictureWidth;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      //new event sound setting
      menuElements.add(new Button_NewGameStyle(CFG.eventsManager.lCreateScenario_Event.getEventSound(), CFG.PADDING * 2, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, CFG.eventsManager.lCreateScenario_Event.superEvent) {
         @Override
         protected boolean getClickable() {
            return CFG.eventsManager.lCreateScenario_Event.superEvent;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (!CFG.eventsManager.lCreateScenario_Event.superEvent) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EnableSE"), CFG.COLOR_BUTTON_GAME_TEXT_NOT_CLICKABLE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            } else {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RecAudio") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(".mp3 or .ogg"));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefAudio"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" UI/events/[EVENT_TAG].mp3 "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("UnlessSpecified"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Path") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("UI/events/" + this.getText()));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, Menu_CreateScenario_Events_Edit.this.sSound, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)this.getTextHeight() * 0.8F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_OPTIONS_NS);
            CFG.fontMain.getData().setScale(1.0F);
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
         }

         protected int getTextPos() {
            return super.getTextPos() + Menu_CreateScenario_Events_Edit.this.iSoundWidth;
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Text_BudgetTitle(CFG.langManager.get("Outcomes"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Button_NewGameStyle(CFG.langManager.get("AddNewOutcome"), -1, CFG.PADDING, tPosY, tempW - CFG.PADDING * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      for(i = 0; i < CFG.eventsManager.lCreateScenario_Event.lDecisions.size(); ++i) {
         int var10004 = CFG.PADDING * 2;
         var10007 = tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.6F);
         menuElements.add(new Button_NewGameStyle_Left(((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(i)).sTitle, var10004, CFG.PADDING, tPosY, var10007, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
         menuElements.add(new Button_Game_NewGameBoxStyle_RIGHT_Remove(tempW - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), tPosY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 5, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4, this.getHeight());
            oSB.setColor(new Color(0.78431374F, 0.0F, 0.0F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(0.78431374F, 0.0F, 0.0F, 0.375F));
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
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - (float)this.getTextWidth() * 0.8F) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH - tempW, CFG.BUTTON_HEIGHT + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 5, tempW, Math.min(tPosY, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 5) - CFG.PADDING * 2), menuElements);
      this.updateLanguage();
      this.setVisible(false);
   }

   protected void updateLanguage() {
      this.getTitle().setText(CFG.langManager.get("AddNewEvent"));
      this.sEventTitle = CFG.langManager.get("EventTitle") + ": ";
      CFG.glyphLayout.setText(CFG.fontMain, this.sEventTitle);
      this.iEventTitleWidth = (int)(CFG.glyphLayout.width * 0.8F);
      this.sPicture = CFG.langManager.get("Picture") + ": ";
      CFG.glyphLayout.setText(CFG.fontMain, this.sPicture);
      this.iPictureWidth = (int)(CFG.glyphLayout.width * 0.8F);

      //set sound
      this.sSound = CFG.langManager.get("Sound") + ": ";
      CFG.glyphLayout.setText(CFG.fontMain, this.sSound);
      this.iSoundWidth = (int)(CFG.glyphLayout.width * 0.8F);

      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getMenuElement(1).setText(CFG.langManager.get("SaveEvent"));
      this.getMenuElement(3).setText(CFG.langManager.get("Recipient"));
      CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventDay;
      CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventMonth;
      CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventYear;
      this.getMenuElement(4).setText(CFG.langManager.get("Since") + ": " + (CFG.eventsManager.iCreateEvent_Year == 9999999 ? CFG.langManager.get("NoDate") : Game_Calendar.getCurrentDate_CreateEvent()));
      CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventDay;
      CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventMonth;
      CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventYear;
      this.getMenuElement(5).setText(CFG.langManager.get("Until") + ": " + (CFG.eventsManager.iCreateEvent_Year == 9999999 ? CFG.langManager.get("NoDate") : Game_Calendar.getCurrentDate_CreateEvent()));
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

   private final void saveEditData() {
      CFG.eventsManager.lCreateScenario_Event.setEventName(this.getMenuElement(2).getText());
      CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().sText = this.getMenuElement(12 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3).getText();
      CFG.eventsManager.lCreateScenario_Event.setEventPicture(this.getMenuElement(13 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3).getText());
      CFG.eventsManager.lCreateScenario_Event.setEventSound(this.getMenuElement(14 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3).getText());
   }

   protected void actionElement(int iID) {
      if (iID >= 10 && iID < 10 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         iID -= 10;
         if (iID % 3 == 0) {
            CFG.eventsManager.iCreateEvent_EditTriggerID = iID / 3;
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_TRIGGER);
         } else if (iID % 3 == 1) {
            CFG.eventsManager.iCreateEvent_EditTriggerID = iID / 3;
            if (CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType == Event_Type.AND) {
               CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType = Event_Type.NOT;
            } else if (CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType == Event_Type.NOT) {
               CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType = Event_Type.OR;
            } else {
               CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType = Event_Type.AND;
            }

            this.getMenuElement(iID + 10).setText(CFG.eventsManager.getEventTypeText(CFG.eventsManager.lCreateScenario_Event.getTrigger(CFG.eventsManager.iCreateEvent_EditTriggerID).triggerType));
            CFG.toast.setInView(this.getMenuElement(iID + 10).getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
         } else {
            CFG.eventsManager.lCreateScenario_Event.removeTrigger(iID / 3);
            this.saveEditData();
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS);
            CFG.menuManager.setVisibleCreateScenario_Events_Edit(true);
         }

      } else if (iID == 11 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().showPopUp = !CFG.eventsManager.lCreateScenario_Event.getEvent_PopUp().showPopUp;
      } else if (iID == 12 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         CFG.showKeyboard();
      } else if (iID == 13 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         CFG.showKeyboard();
      } else if (iID == 14 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         CFG.showKeyboard();
      } else if (iID == 16 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         this.saveEditData();
         CFG.eventsManager.lCreateScenario_Event.lDecisions.add(new Event_Decision());
         CFG.eventsManager.iCreateEvent_EditTriggerID = CFG.eventsManager.lCreateScenario_Event.lDecisions.size() - 1;
         CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DECISION);
      } else if (iID >= 17 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3) {
         iID -= 17 + CFG.eventsManager.lCreateScenario_Event.getTriggersSize() * 3;
         if (iID % 2 == 0) {
            this.saveEditData();
            CFG.eventsManager.iCreateEvent_EditTriggerID = iID / 2;
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DECISION);
         } else {
            this.saveEditData();
            CFG.eventsManager.iCreateEvent_EditTriggerID = iID / 2;
            CFG.eventsManager.lCreateScenario_Event.lDecisions.remove(CFG.eventsManager.iCreateEvent_EditTriggerID);
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS);
            CFG.menuManager.setVisibleCreateScenario_Events_Edit(true);
         }

      } else {
         switch (iID) {
            case 0:
               CFG.menuManager.getKeyboard().setVisible(false);
               this.saveEditData();
               CFG.setDialogType(Dialog.CREATE_SCENARIO_EVENTS_EDIT_BACK);
               break;
            case 1:
               CFG.menuManager.getKeyboard().setVisible(false);
               this.saveEditData();
               CFG.setDialogType(Dialog.CREATE_SCENARIO_EVENTS_EDIT_SAVE);
               break;
            case 2:
               CFG.showKeyboard();
               break;
            case 3:
               this.saveEditData();
               CFG.eventsManager.eSelectCivAction = Event_SelectCivAction.SELECT_RECIPENT;
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_SELECT_CIV);
               break;
            case 4:
               this.saveEditData();
               CFG.eventsManager.setSinceDate = true;
               CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventDay;
               CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventMonth;
               CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventYear;
               CFG.eventsManager.iCreateEvent_Age = CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventYear == 9999999 ? Game_Calendar.CURRENT_AGEID : CFG.gameAges.getAgeOfYear(CFG.eventsManager.lCreateScenario_Event.getEventDate_Since().iEventYear);
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DATE);
               CFG.menuManager.updateCreateScanerio_Events_Slider();
               break;
            case 5:
               this.saveEditData();
               CFG.eventsManager.setSinceDate = false;
               CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventDay;
               CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventMonth;
               CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventYear;
               CFG.eventsManager.iCreateEvent_Age = CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventYear == 9999999 ? Game_Calendar.CURRENT_AGEID : CFG.gameAges.getAgeOfYear(CFG.eventsManager.lCreateScenario_Event.getEventDate_Until().iEventYear);
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DATE);
               CFG.menuManager.updateCreateScanerio_Events_Slider();
               break;
            case 6:
               CFG.eventsManager.lCreateScenario_Event.setRepeatable(!CFG.eventsManager.lCreateScenario_Event.getRepeatable());
               break;
            case 7:
               //toggle superevent
               CFG.eventsManager.lCreateScenario_Event.superEvent = !CFG.eventsManager.lCreateScenario_Event.superEvent;
               break;
            case 8:
            default:
               break;
            case 9:
               CFG.eventsManager.lCreateScenario_Event.addNewTrigger();
               CFG.eventsManager.iCreateEvent_EditTriggerID = CFG.eventsManager.lCreateScenario_Event.getTriggersSize() - 1;
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_TRIGGER);
               break;
         }

      }
   }
}
