package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_Leader_Edit_Data extends SliderMenu {
   private String sName;
   private String sImage;
   private String sBorn;
   private String sOffice;
   private String sWiki;

   protected Menu_Leader_Edit_Data() {
       super();
       final int tempW = (int)(CFG.CIV_INFO_MENU_WIDTH * 1.5f);
       final int tempElemH = CFG.BUTTON_HEIGHT;
       final List<MenuElement> menuElements = new ArrayList<MenuElement>();
       int tY = CFG.PADDING;

      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getName(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sName + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getImage(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sImage + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getDay() + " " + Game_Calendar.getMonthName(CFG.leader_GameData.getLeaderOfCiv().getMonth()) + " " + CFG.gameAges.getYear(CFG.leader_GameData.getLeaderOfCiv().getYear()), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sBorn + ": " + super.getText();
         }
      });

       tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
       menuElements.add(new Button_New_Game_Players_Special((String)null, CFG.PADDING * 2, CFG.PADDING, tY, tempW - CFG.PADDING * 2 - 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
           protected String getTextToDraw() {
               return Menu_Leader_Edit_Data.this.sOffice + ": " + (CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"));
           }
       });

       tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getWiki(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sWiki + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_Leader_Edit_Data.this.getPosX() - 2 + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_Leader_Edit_Data.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.425F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_Leader_Edit_Data.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING, Menu_Leader_Edit_Data.this.getWidth(), CFG.PADDING, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_Leader_Edit_Data.this.getWidth());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_Leader_Edit_Data.this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.75F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 4, tempW, Math.min(((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4) - CFG.BUTTON_HEIGHT - CFG.PADDING * 3), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.sName = CFG.langManager.get("Name");
      this.sImage = CFG.langManager.get("ImageName");
      this.sBorn = CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() ? CFG.langManager.get("InOffice") : CFG.langManager.get("Born");
      this.sOffice = CFG.langManager.get("InOfficeYear");

      this.sWiki = CFG.langManager.get("Wiki");
      this.getTitle().setText(CFG.langManager.get("Leader"));
      this.getMenuElement(2).setText(Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth) + " " + CFG.gameAges.getYear(Game_Calendar.currentYear));
      this.getMenuElement(6).setText(CFG.langManager.get("PopulationGrowthModifier") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth * 100.0F) + "%");
      this.getMenuElement(9).setText(CFG.langManager.get("EconomyGrowthModifier") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth * 100.0F) + "%");
      this.getMenuElement(12).setText(CFG.langManager.get("IncomeTaxation") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation * 100.0F) + "%");
      this.getMenuElement(15).setText(CFG.langManager.get("IncomeProduction") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction * 100.0F) + "%");
      this.getMenuElement(18).setText(CFG.langManager.get("Administration") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration * 100.0F) + "%");
      this.getMenuElement(21).setText(CFG.langManager.get("Research") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_Research > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_Research * 100.0F) + "%");
      this.getMenuElement(24).setText(CFG.langManager.get("MilitaryUpkeep") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep * 100.0F) + "%");
      this.getMenuElement(27).setText(CFG.langManager.get("AttackBonus") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus * 100.0F) + "%");
      this.getMenuElement(30).setText(CFG.langManager.get("DefenseBonus") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus * 100.0F) + "%");
      this.getMenuElement(33).setText(CFG.langManager.get("MovementPoints") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints * 100.0F) + "%");
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), true, true);
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void actionElement(final int iID) {
      switch (iID) {
         case 0:
          case 1:
          case 4: {
            CFG.showKeyboard();
            return;
         }
          case 2: {
            CFG.menuManager.saveLeader_Edit_Data();
            CFG.backToMenu = Menu.eGAME_LEADERS_EDIT;
            CFG.menuManager.setViewID(Menu.eSCENARIO_AGE);
            CFG.menuManager.updateSelecetScenarioAge_Slider();
            return;
         }
          case 3: {
            CFG.leader_GameData.getLeaderOfCiv().setIncumbentYear(!CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear());
            this.updateLanguage();
            return;
          }
          case 6:
          case 9:
          case 12:
          case 15:
          case 18:
          case 21:
          case 24:
          case 27:
          case 30:
          case 5: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 7: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 8: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 10: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 11: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 13: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 14: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 16: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 17: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 19: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 20: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Research -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Research -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 22: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Research += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_Research += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 23: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 25: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 26: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 28: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 29: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 31: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus += 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 32: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints -= 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints -= 0.01f;
              }
              this.updateLanguage();
              return;
          }
          case 34: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints += 0.1f;
              } else {
                  CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints += 0.01f;
              }
              this.updateLanguage();
              return;
          }
         default: {}
      }
   }

}
