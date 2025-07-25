package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class Menu_Editor_GameCivs_Edit2 extends SliderMenu {
   private final String sCivTAG = "Civilization TAG";
   private int iSRID = 0;

   protected Menu_Editor_GameCivs_Edit2() {
      super();
      this.iSRID = 0;
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      menuElements.add(new Button_Menu_LR_Line((String)null, -1, 0, 0, CFG.GAME_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_LR_Line("", (int)(50.0F * CFG.GUI_SCALE), 0, CFG.PADDING, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true) {
         protected String getTextToDraw() {
            return "Civilization TAG: " + super.getTextToDraw();
         }
      });
      menuElements.add(new Button_Menu("", -1, 0, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true) {
         protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            oSB.setColor((float)CFG.editorCivilization_GameData.getR() / 255.0F, (float)CFG.editorCivilization_GameData.getG() / 255.0F, (float)CFG.editorCivilization_GameData.getB() / 255.0F, 1.0F);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, this.getTextWidth() - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + this.getTextWidth() - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
            oSB.setColor(Color.WHITE);
         }
      });
      menuElements.add(new Button_Menu("<<", -1, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 3, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_Classic("", -1, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 3, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 4, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_ReflectedBG(">>", -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 3, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT, true));
      this.iSRID = CFG.serviceRibbon_Manager.getSRID(CFG.editorCivilization_GameData.sr_GameData.getSRTAG());
      final int tempSRColorsSize = CFG.serviceRibbon_Manager.getSR(CFG.editorCivilization_GameData.sr_GameData.getSRTAG()).getSize();

      for (int i = 0; i < tempSRColorsSize; ++i) {
         menuElements.add(new Button_Menu(CFG.langManager.get("ServiceRibbon") + " - " + CFG.langManager.get("Color") + ": " + (i + 1), -1, 0, CFG.BUTTON_HEIGHT * (3 + i) + CFG.PADDING * (4 + i), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true) {
            int iCurrent;

            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               super.drawText(oSB, iTranslateX, iTranslateY, isActive);
               oSB.setColor(CFG.editorCivilization_GameData.sr_GameData.getColor(this.iCurrent).getR(), CFG.editorCivilization_GameData.sr_GameData.getColor(this.iCurrent).getG(), CFG.editorCivilization_GameData.sr_GameData.getColor(this.iCurrent).getB(), 1.0F);
               ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH, true, false);
               ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + CFG.PADDING + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, this.getTextWidth() - CFG.PADDING * 2, CFG.CIV_COLOR_WIDTH);
               ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getWidth() / 2 - this.getTextWidth() / 2 + this.getTextWidth() - CFG.PADDING + iTranslateX, this.getPosY() + Menu_Editor_GameCivs_Edit2.this.getMenuPosY() + this.getHeight() / 2 + this.getTextHeight() / 2 + CFG.CIV_COLOR_WIDTH, CFG.PADDING, CFG.CIV_COLOR_WIDTH);
               oSB.setColor(Color.WHITE);
            }

            protected void setCurrent(int nCurrent) {
               this.iCurrent = nCurrent;
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(i);
      }

      for (int i = CFG.editorCivilization_GameData.sr_GameData.getColors().size(); i < tempSRColorsSize; ++i) {
         if (i == 0) {
            CFG.editorCivilization_GameData.sr_GameData.getColors().add(new Color_GameData(0.9843137f, 0.015686275f, 0.0f));
         }
         else if (i == 1) {
            CFG.editorCivilization_GameData.sr_GameData.getColors().add(new Color_GameData(1.0f, 1.0f, 1.0f));
         }
         else if (i == 2) {
            CFG.editorCivilization_GameData.sr_GameData.getColors().add(new Color_GameData(0.15294118f, 0.3019608f, 0.60784316f));
         }
         else if (i == 3) {
            CFG.editorCivilization_GameData.sr_GameData.getColors().add(new Color_GameData(0.08627451f, 0.14901961f, 0.4509804f));
         }
         else {
            final Color tempColor = CFG.getRandomColor();
            CFG.editorCivilization_GameData.sr_GameData.getColors().add(new Color_GameData(tempColor.r, tempColor.g, tempColor.b));
         }
      }

      menuElements.add(new Button_Menu_LR_Line((String)null, -1, CFG.GAME_WIDTH / 2, CFG.PADDING, CFG.GAME_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
      this.initMenuWithBackButton(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements);
      this.getMenuElement(this.getMenuElementsSize() - 1).setPosY(this.getMenuElement(0).getPosY());
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getMenuElement(1).setText(CFG.editorCivilization_GameData.getCivTag());
      this.getMenuElement(2).setText(CFG.langManager.get("CivilizationColor"));
      this.getMenuElement(this.getMenuElementsSize() - 1).setText(CFG.langManager.get("Save"));
      this.getTitle().setText("Age of History II - " + CFG.langManager.get("GameCivilizations"));
   }

   @Override
   protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      final List<Color> tempColors = new ArrayList<Color>();
      for (int i = 0; i < CFG.editorCivilization_GameData.sr_GameData.getColors().size(); ++i) {
         tempColors.add(new Color(CFG.editorCivilization_GameData.sr_GameData.getColors().get(i).getR(), CFG.editorCivilization_GameData.sr_GameData.getColors().get(i).getG(), CFG.editorCivilization_GameData.sr_GameData.getColors().get(i).getB(), 1.0f));
      }
      int j = 0;
      final int tempWidth = CFG.SERVICE_RIBBON_WIDTH * 6 + CFG.PADDING * 5;
      while (j < 6) {
         CFG.serviceRibbon_Manager.drawSRLevel(oSB, CFG.GAME_WIDTH / 2 - tempWidth / 2 + (CFG.SERVICE_RIBBON_WIDTH + CFG.PADDING) * j + iTranslateX, this.getMenuElement(4).getPosY() + this.getMenuElement(4).getHeight() / 2 - CFG.SERVICE_RIBBON_HEIGHT / 2 + this.getMenuPosY(), j, 0, 0, this.iSRID, tempColors);
         ++j;
      }
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            this.onBackPressed();
            break;
         case 1:
            CFG.showKeyboard();
            break;
         case 2:
            if (CFG.menuManager.getColorPicker().getVisible() && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID == -1) {
               CFG.menuManager.getColorPicker().setVisible(false, (ColorPicker_AoC.PickerAction)null);
            } else {
               CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
               CFG.menuManager.getColorPicker().setActiveRGBColor((float)CFG.editorCivilization_GameData.getR() / 255.0F, (float)CFG.editorCivilization_GameData.getG() / 255.0F, (float)CFG.editorCivilization_GameData.getB() / 255.0F);
               CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.EDITOR_CIV_GAME_COLOR);
            }
            break;
         case 3:
            int tempSRID = CFG.serviceRibbon_Manager.getSRID(CFG.editorCivilization_GameData.sr_GameData.getSRTAG());
            --tempSRID;
            if (tempSRID <= 0) {
               tempSRID = CFG.serviceRibbon_Manager.getSRSize() - 1;
            }

            CFG.editorCivilization_GameData.setCivTag(this.getMenuElement(1).getText());
            CFG.editorCivilization_GameData.sr_GameData.setSRTAG(CFG.serviceRibbon_Manager.getTag(tempSRID));
            CFG.menuManager.setViewIDWithoutAnimation(Menu.eEDITOR_GAME_CIVS_EDIT);
            break;
         case 4:
            CFG.toast.setInView("ID: " + CFG.serviceRibbon_Manager.getTag(this.iSRID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE);
            break;
         case 5:
            int tempSRID2 = CFG.serviceRibbon_Manager.getSRID(CFG.editorCivilization_GameData.sr_GameData.getSRTAG());
            ++tempSRID2;
            if (tempSRID2 >= CFG.serviceRibbon_Manager.getSRSize()) {
               tempSRID2 = 0;
            }

            CFG.editorCivilization_GameData.setCivTag(this.getMenuElement(1).getText());
            CFG.editorCivilization_GameData.sr_GameData.setSRTAG(CFG.serviceRibbon_Manager.getTag(tempSRID2));
            CFG.menuManager.setViewIDWithoutAnimation(Menu.eEDITOR_GAME_CIVS_EDIT);
            break;
         default:
            if (iID == this.getMenuElementsSize() - 1) {
               if (this.getMenuElement(1).getText().isEmpty()) {
                  CFG.showKeyboard(1);
                  CFG.toast.setInView("Civilization TAG");
               } else {
                  CFG.editorCivilization_GameData.setCivTag(this.getMenuElement(1).getText());
                  this.saveData();
               }
            } else if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID == iID - 6 && CFG.menuManager.getColorPicker().getVisible()) {
               CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = -1;
               CFG.menuManager.getColorPicker().setVisible(false, (ColorPicker_AoC.PickerAction)null);
            } else {
               CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID = iID - 6;
               CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getR(), CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getG(), CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).getB());
               CFG.menuManager.getColorPicker().setVisible(true, ColorPicker_AoC.PickerAction.EDITOR_CIV_GAME_COLOR_SR);
            }
      }

   }

   private final void saveData() {
      final OutputStream os = null;
      try {
         while (CFG.serviceRibbon_Manager.getSR(this.iSRID).getSize() > CFG.editorCivilization_GameData.sr_GameData.getColors().size()) {
            CFG.editorCivilization_GameData.sr_GameData.getColors().remove(CFG.editorCivilization_GameData.sr_GameData.getColors().size() - 1);
         }
         final FileHandle file = Gdx.files.local("game/civilizations/" + CFG.editorCivilization_GameData.getCivTag());
         file.writeBytes(CFG.serialize(CFG.editorCivilization_GameData), false);
      }
      catch (final GdxRuntimeException ex) {
         CFG.toast.setInView("----- " + CFG.langManager.get("Error") + " -----", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         CFG.toast.setTimeInView(6000);
      }
      catch (final IOException ex2) {
         if (CFG.LOGS) {
            CFG.exceptionStack(ex2);
         }
      }
      catch (final NullPointerException ex3) {
         CFG.toast.setInView("----- " + CFG.langManager.get("Error") + " -----", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         CFG.toast.setTimeInView(6000);
         return;
      }
      finally {
         if (os != null) {
            try {
               os.close();
            }
            catch (final Exception ex4) {}
         }
      }
      try {
         final FileHandle file = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
         final String tempTags = file.readString();
         if (!tempTags.contains(CFG.editorCivilization_GameData.getCivTag())) {
            final FileHandle fileSave = Gdx.files.local("game/civilizations/Age_of_Civilizations");
            fileSave.writeString(tempTags + CFG.editorCivilization_GameData.getCivTag() + ";", false);
         }
         else {
            final String[] tempTagsSplited = tempTags.split(";");
            boolean tAdd = true;
             for (String s : tempTagsSplited) {
                 if (s.equals(CFG.editorCivilization_GameData.getCivTag())) {
                     tAdd = false;
                     break;
                 }
             }
            if (!tAdd) {
               this.onBackPressed();
               return;
            }
            final FileHandle fileSave2 = Gdx.files.local("game/civilizations/Age_of_Civilizations");
            fileSave2.writeString(tempTags + CFG.editorCivilization_GameData.getCivTag() + ";", false);
         }
      }
      catch (final GdxRuntimeException ex) {
         final FileHandle fileSave3 = Gdx.files.local("game/civilizations/Age_of_Civilizations");
         fileSave3.writeString(CFG.editorCivilization_GameData.getCivTag() + ";", false);
      }
      try {
         final FileHandle readFile = Gdx.files.internal("game/civilizations_informations/" + CFG.editorCivilization_GameData.getCivTag());
         readFile.readString();
      }
      catch (final GdxRuntimeException ex) {
         final FileHandle fileSave4 = Gdx.files.local("game/civilizations_informations/" + CFG.editorCivilization_GameData.getCivTag());
         fileSave4.writeString("" + CFG.langManager.get(this.getMenuElement(1).getText()).substring(CFG.langManager.get(this.getMenuElement(1).getText()).indexOf(45) + 2).replace(' ', '_'), false);
      }
      this.onBackPressed();
   }

   protected void onBackPressed() {
      CFG.menuManager.setViewID(Menu.eEDITOR_GAME_CIVS);
      CFG.menuManager.setBackAnimation(true);
      CFG.menuManager.getColorPicker().setVisible(false, (ColorPicker_AoC.PickerAction)null);
      Game_Render_Province.updateDrawProvinces();
   }
}
