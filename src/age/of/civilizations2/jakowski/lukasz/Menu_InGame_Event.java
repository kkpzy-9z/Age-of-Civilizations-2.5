package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Menu_InGame_Event extends SliderMenu {
   protected static int EVENT_ID = 0;
   private Image lPicture = null;
   protected static final float DATE_FONT_SCALE = 0.65F;
   private String sEventDate = "";
   private int iEventDateWidth = 0;

   protected Menu_InGame_Event(int tInit) {
      List menuElements = new ArrayList();
      int tempWidth = (int)(512.0F * CFG.GUI_SCALE) + CFG.PADDING * 2;
      if (tempWidth > CFG.GAME_WIDTH) {
         tempWidth = CFG.GAME_WIDTH - CFG.PADDING * 4;
      }

      int tempMenuPosY = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 + CFG.BUTTON_HEIGHT * 3 / 5 + CFG.PADDING * 2;
      this.initMenu((SliderMenuTitle)null, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, 5, menuElements, false, false);
   }

   protected Menu_InGame_Event() {
      try {
         if (CFG.eventsManager.getEvent(EVENT_ID).getEventPicture().length() == 0) {
            this.lPicture = new Image(new Texture(Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.game.getGameScenarios().sActiveScenarioTag + "/" + "events/" + "default.png")), Texture.TextureFilter.Linear);
         } else {
            this.lPicture = new Image(new Texture(Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "scenarios/" + CFG.game.getGameScenarios().sActiveScenarioTag + "/" + "events/" + CFG.eventsManager.getEvent(EVENT_ID).getEventPicture())), Texture.TextureFilter.Linear);
         }
      } catch (GdxRuntimeException var10) {
         try {
            this.lPicture = new Image(new Texture(Gdx.files.internal("UI/events/" + CFG.eventsManager.getEvent(EVENT_ID).getEventPicture())), Texture.TextureFilter.Linear);
         } catch (GdxRuntimeException var9) {
            try {
               //if superevent default to large superevent image, else default to normal
               try {
                  if (CFG.eventsManager.getEvent(EVENT_ID).superEvent) {
                     this.lPicture = new Image(new Texture(Gdx.files.internal("UI/events/defaultsuperevent.png")), Texture.TextureFilter.Linear);
                  } else {
                     this.lPicture = new Image(new Texture(Gdx.files.internal("UI/events/default.png")), Texture.TextureFilter.Linear);
                  }
               } catch (Exception e) {
                  this.lPicture = new Image(new Texture(Gdx.files.internal("UI/events/default.png")), Texture.TextureFilter.Linear);
               }
            } catch (GdxRuntimeException var8) {
               this.lPicture = null;
            }
         }
      }

      List menuElements = new ArrayList();
      int tempWidth = (int)(512.0F * CFG.GUI_SCALE) + CFG.PADDING * 2;
      if (tempWidth > CFG.GAME_WIDTH) {
         tempWidth = CFG.GAME_WIDTH - CFG.PADDING * 4;
      }

      int tY = CFG.PADDING;
      menuElements.add(new Button_Transparent(CFG.PADDING, tY, tempWidth - CFG.PADDING * 2, (int)(96.0F * CFG.GUI_SCALE), true));

      //if superevent, change padding to make image larger (minimum between game height/2 or image size), and emit noise
      if (this.lPicture != null && CFG.eventsManager.getEvent(EVENT_ID).superEvent) {
         ((MenuElement)menuElements.get(0)).setHeight(Math.min((int)((float)this.lPicture.getHeight() * CFG.GUI_SCALE), CFG.GAME_HEIGHT/2));
         //((MenuElement)menuElements.get(0)).setWidth(Math.min((int)((float)this.lPicture.getWidth() * CFG.GUI_SCALE), tempWidth));
         //tempWidth = ((MenuElement) menuElements.get(0)).getWidth();

         //play sound
         CFG.soundsManager.playEventMusic("UI/events/" + (!Objects.equals(CFG.eventsManager.getEvent(EVENT_ID).getEventSound(), "") ? CFG.eventsManager.getEvent(EVENT_ID).getEventSound() : CFG.eventsManager.getEvent(EVENT_ID).getEventTag() + ".mp3"));
      }
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      menuElements.add(new TextSlider(CFG.PADDING, tY, tempWidth - CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 4, CFG.BUTTON_HEIGHT * 2, 0.8F) {
         protected Color getColor(boolean isActive) {
            //if superevent make text color solid white
            if (CFG.eventsManager.getEvent(EVENT_ID).superEvent) {
               return Color.WHITE;
            } else if (isActive) {
               return CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_ACTIVE;
            } else {
               return (this.getIsHovered() ? CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT_HOVER : CFG.COLOR_BUTTON_GAME_TEXT_IMPORTANT);
            }
         }

         protected void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.25F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
            oSB.setColor(Color.WHITE);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight() * 3 / 5, false, false);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.275F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 4, this.getHeight(), false, false);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() / 4 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 4, this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.3F));
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING, false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.15F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth() - 4, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.7F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth() - 4, 1);
            oSB.setColor(Color.BLACK);
            ImageManager.getImage(Images.line_32_vertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_vertical).getHeight() + iTranslateY, 1, this.getHeight());
            ImageManager.getImage(Images.line_32_vertical).draw(oSB, this.getPosX() + this.getWidth() - 1 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_vertical).getHeight() + iTranslateY, 1, this.getHeight());
            oSB.setColor(Color.WHITE);
         }
      });

      //if no text, make above text element invisible, else set text and padding
      if (CFG.langManager.get(CFG.eventsManager.getEvent(EVENT_ID).getEvent_PopUp().sText).length() < 1) {
         ((MenuElement)menuElements.get((menuElements.size() - 1))).setVisible(false);
      } else {
         ((MenuElement)menuElements.get((menuElements.size() - 1))).addText(CFG.langManager.get(CFG.eventsManager.getEvent(EVENT_ID).getEvent_PopUp().sText), CFG.PADDING);
         //if superevent move text up by itself and some padding (so text is overlaying picture rather than under), else don't and add text length to translationY so buttons appear under
         if (CFG.eventsManager.getEvent(EVENT_ID).superEvent) {
            ((MenuElement)menuElements.get((menuElements.size() - 1))).setPosY(tY - (((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING));
         } else {
            tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight();
         }
         tY += CFG.PADDING * 2;
      }


      //add decisions through iteration
      int tempMenuPosY;
      for(tempMenuPosY = 0; tempMenuPosY < CFG.eventsManager.getEvent(EVENT_ID).lDecisions.size(); ++tempMenuPosY) {
         menuElements.add(new Button_New_Game_Players_Anim(CFG.langManager.get(((Event_Decision)CFG.eventsManager.getEvent(EVENT_ID).lDecisions.get(tempMenuPosY)).sTitle), -1, CFG.PADDING, tY, tempWidth - CFG.PADDING * 2, CFG.BUTTON_HEIGHT / 2, true) {
            int iCurrent = 0;

            protected Color getColor(boolean isActive) {
               return isActive ? CFG.COLOR_TEXT_OPTIONS_LEFT_NS_ACTIVE : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_LEFT_NS_HOVER : CFG.COLOR_TEXT_OPTIONS_LEFT_NS) : CFG.COLOR_BUTTON_MENU_TEXT_NOT_CLICKABLE);
            }

            protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.getIsHovered() ? 1.0F : 0.8F));
               super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
               oSB.setColor(Color.WHITE);
            }

            protected int getCurrent() {
               return this.iCurrent;
            }

            protected void setCurrent(int nCurrent) {
               this.iCurrent = nCurrent;
            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(this.getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               for(int i = 0; i < ((Event_Decision)CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.get(this.getCurrent())).lOutcomes.size(); ++i) {
                  List tempElements = ((Event_Outcome)((Event_Decision)CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.get(this.getCurrent())).lOutcomes.get(i)).getHoverText();

                  for (Object tempElement : tempElements) {
                     nElements.add((MenuElement_Hover_v2_Element2) tempElement);
                  }

                  tempElements.clear();
                  tempElements = null;

                  //add/init globals
                  if (!CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.get(this.getCurrent()).lOutcomes.get(i).tData2Enabled) continue;
                  nElements.add(new MenuElement_Hover_v2_Element2(CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.get(this.getCurrent()).lOutcomes.get(i).getSecondaryHoverText()));
               }


               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tempMenuPosY);
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      tempMenuPosY = ImageManager.getImage(Images.top_flag_frame).getHeight() + CFG.PADDING * 4 + CFG.BUTTON_HEIGHT * 3 / 5 + CFG.PADDING * 2;
      this.initMenu(new SliderMenuTitle("", CFG.BUTTON_HEIGHT * 3 / 5, true, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color(0.06666667F, 0.3764706F, 0.7529412F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(0.06666667F, 0.3764706F, 0.7529412F, 0.375F));
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
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().draw(oSB, nPosX + (int)((float)nWidth - ((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.flag_rect).getWidth() + (float)CFG.PADDING)) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().getHeight(), CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, nPosX + (int)((float)nWidth - ((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.flag_rect).getWidth() + (float)CFG.PADDING)) / 2 + iTranslateX, 2 + nPosY - this.getHeight() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - ((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.flag_rect).getWidth() + (float)CFG.PADDING)) / 2 + ImageManager.getImage(Images.flag_rect).getWidth() + CFG.PADDING + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      try {
         this.getTitle().setText(CFG.langManager.get(CFG.eventsManager.getEvent(EVENT_ID).getEventName()));
      } catch (NullPointerException var4) {
         try {
            this.getTitle().setText(CFG.langManager.get("Event"));
         } catch (NullPointerException var3) {
         }
      }

      if (CFG.eventsManager.getEvent(EVENT_ID).getEventDate_Until().iEventYear == 9999999) {
         if (CFG.eventsManager.getEvent(EVENT_ID).getEventDate_Since().iEventYear == 9999999) {
            this.sEventDate = Game_Calendar.getCurrentDate();
         } else {
            CFG.eventsManager.iCreateEvent_Day = CFG.eventsManager.getEvent(EVENT_ID).getEventDate_Since().iEventDay;
            CFG.eventsManager.iCreateEvent_Month = CFG.eventsManager.getEvent(EVENT_ID).getEventDate_Since().iEventMonth;
            CFG.eventsManager.iCreateEvent_Year = CFG.eventsManager.getEvent(EVENT_ID).getEventDate_Since().iEventYear;
            this.sEventDate = Game_Calendar.getCurrentDate_CreateEvent();
         }
      } else {
         this.sEventDate = Game_Calendar.getCurrentDate();
      }

      CFG.glyphLayout.setText(CFG.fontMain, this.sEventDate);
      this.iEventDateWidth = (int)(CFG.glyphLayout.width * 0.65F);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
      oSB.setColor(Color.WHITE);
      this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);

      try {
         if (this.getMenuElement(0).getIsHovered()) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
         }

         this.lPicture.draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() - this.lPicture.getHeight() + iTranslateY, this.getMenuElement(0).getWidth(), this.getMenuElement(0).getHeight());
         //this.lPicture.draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() / 2 - (int)((float)this.lPicture.getWidth() * CFG.GUI_SCALE) / 2 + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() + this.getMenuElement(0).getHeight() / 2 - (int)((float)this.lPicture.getHeight() * CFG.GUI_SCALE) / 2 - this.lPicture.getHeight() + iTranslateY, (int)((float)this.lPicture.getWidth() * CFG.GUI_SCALE), (int)((float)this.lPicture.getHeight() * CFG.GUI_SCALE));
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getMenuElement(0).getWidth(), (int)((float)CFG.TEXT_HEIGHT * 0.65F) + CFG.PADDING * 2);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getMenuElement(0).getWidth() / 3, (int)((float)CFG.TEXT_HEIGHT * 0.65F) + CFG.PADDING * 2);
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() - this.getMenuElement(0).getWidth() / 3 + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getMenuElement(0).getWidth() / 3, (int)((float)CFG.TEXT_HEIGHT * 0.65F) + CFG.PADDING * 2, true, false);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + iTranslateX, this.getPosY() + (int)((float)CFG.TEXT_HEIGHT * 0.65F) + CFG.PADDING * 2 - 1 + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getMenuElement(0).getWidth() / 3, 1);
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() - this.getMenuElement(0).getWidth() / 3 + iTranslateX, this.getPosY() + (int)((float)CFG.TEXT_HEIGHT * 0.65F) + CFG.PADDING * 2 - 1 + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getMenuElement(0).getWidth() / 3, 1, true, false);
         oSB.setColor(Color.WHITE);
         CFG.fontMain.getData().setScale(0.65F);
         //if not superevent show capital
         if (!CFG.eventsManager.getEvent(EVENT_ID).superEvent && CFG.eventsManager.getEvent(EVENT_ID).getCivID() > 0 && CFG.eventsManager.getEvent(EVENT_ID).getCivID() < CFG.game.getCivsSize() && CFG.game.getCiv(CFG.eventsManager.getEvent(EVENT_ID).getCivID()).getCapitalProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getCiv(CFG.eventsManager.getEvent(EVENT_ID).getCivID()).getCapitalProvinceID()).getCitiesSize() > 0) {
            CFG.drawText(oSB, CFG.game.getProvince(CFG.game.getCiv(CFG.eventsManager.getEvent(EVENT_ID).getCivID()).getCapitalProvinceID()).getCity(0).getCityName(), this.getPosX() + this.getMenuElement(0).getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() + CFG.PADDING + iTranslateY, new Color(1.0F, 1.0F, 1.0F, 0.8F));
         }

         CFG.drawText(oSB, this.sEventDate, this.getPosX() + this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() - CFG.PADDING - this.iEventDateWidth + iTranslateX, this.getPosY() + this.getMenuElement(0).getPosY() + CFG.PADDING + iTranslateY, new Color(1.0F, 1.0F, 1.0F, 0.8F));
         CFG.fontMain.getData().setScale(1.0F);
      } catch (NullPointerException var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setColor(Color.WHITE);
      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.toast.setInView(this.getTitle().getText(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            return;
         case 1:
            CFG.toast.setInView(CFG.langManager.get(CFG.eventsManager.getEvent(EVENT_ID).getEvent_PopUp().sText), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            return;
         default:
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2);
            iID -= 2;

            try {
               if (CFG.eventsManager.getEvent(EVENT_ID).getCivID() >= 0) {
                  CFG.game.getCiv(CFG.eventsManager.getEvent(EVENT_ID).getCivID()).addEvent_DecisionTaken(CFG.eventsManager.getEvent(EVENT_ID).getEventTag() + "_" + iID);
               }

               ((Event_Decision)CFG.eventsManager.getEvent(EVENT_ID).lDecisions.get(iID)).executeDecision();
            } catch (IndexOutOfBoundsException var3) {
            }

            CFG.menuManager.setVisibleInGame_Event(false);
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).runNextEvent();
      }
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible && this.lPicture != null) {
         this.lPicture.getTexture().dispose();
         this.lPicture = null;
      }

   }
}
