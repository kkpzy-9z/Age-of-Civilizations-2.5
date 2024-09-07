package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

class Menu_MapEditor_FormableCivs_SelectFormable_List extends SliderMenu
{
   private List<String> lCivsTags;
   private List<Image> lFlags;
   private List<Integer> lLoadedFlags_TagsIDs;

   protected Menu_MapEditor_FormableCivs_SelectFormable_List() {
      super();
      this.lCivsTags = null;
      this.lFlags = new ArrayList<Image>();
      this.lLoadedFlags_TagsIDs = new ArrayList<Integer>();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      String tempTADDED = null;
      ArrayList<String> tagsSPLITED_ADDED = new ArrayList<String>();
      //now reads both local and internal to get all data, bugfix change//
      try {
         FileHandle tempFileTADDED;
         tempFileTADDED = Gdx.files.internal("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
         tempTADDED = tempFileTADDED.readString();
         java.util.Collections.addAll(tagsSPLITED_ADDED, tempTADDED.split(";"));
      }
      catch (final GdxRuntimeException ex) {}
      try {
         FileHandle tempFileTADDED;
         tempFileTADDED = Gdx.files.local("map/" + CFG.map.getFile_ActiveMap_Path() + "formable_civs/" + "Age_of_Civilizations");
         tempTADDED = tempFileTADDED.readString();
         java.util.Collections.addAll(tagsSPLITED_ADDED, tempTADDED.split(";"));
      }
      catch (final GdxRuntimeException ex) {}

      final FileHandle tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
      final String tempT = tempFileT.readString();
      final String[] tagsSPLITED = tempT.split(";");
      ArrayList<String> tagsSPLITED_ED = new ArrayList<String>();
      try {
         FileHandle tempFileT_ED = null;
         try {
            tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
            final String tempT_ED = tempFileT_ED.readString();
            tagsSPLITED_ED.addAll(Arrays.asList(tempT_ED.split(";")));
         }
         catch (final GdxRuntimeException ex) {}
         if (CFG.isAndroid()) {
            try {
               tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
               final String tempT_ED = tempFileT_ED.readString();
               tagsSPLITED_ED.addAll(Arrays.asList(tempT_ED.split(";")));
            } catch (final GdxRuntimeException ex) {
            }
         }
         //if (CFG.isAndroid()) {
         //   tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
         //}
         //else {
         //   tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
         //}

      }
      catch (final GdxRuntimeException ex2) {}
      this.lCivsTags = new ArrayList<String>();
      final List<String> lTempNames = new ArrayList<String>();
      final List<String> lTempTags = new ArrayList<String>();
      if (CFG.sSearch != null && CFG.sSearch.length() > 0) {
         for (int i = 0, iSize = tagsSPLITED.length; i < iSize; ++i) {
            if (CFG.langManager.getCiv(tagsSPLITED[i]).toLowerCase().contains(CFG.sSearch.toLowerCase()) && !CFG.isInFormableCivs(tagsSPLITED[i])) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED[i])) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED[i]));
                  lTempTags.add(tagsSPLITED[i]);
               }
            }
         }
         for (int i = 0, iSize = tagsSPLITED_ED.size(); i < iSize; ++i) {
            if (CFG.langManager.getCiv(tagsSPLITED_ED.get(i)).toLowerCase().contains(CFG.sSearch.toLowerCase()) && !CFG.isInFormableCivs(tagsSPLITED_ED.get(i))) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED_ED.get(i))) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED_ED.get(i)));
                  lTempTags.add(tagsSPLITED_ED.get(i));
               }
            }
         }
         int nPosY = 0;
         while (lTempNames.size() > 0) {
            int toAddID = 0;
            for (int k = 1; k < lTempNames.size(); ++k) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                  toAddID = k;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
            menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
            ++nPosY;
         }
      }
      else if (CFG.chosen_AlphabetCharachter == null) {
         for (int i = 0, iSize = tagsSPLITED.length; i < iSize; ++i) {
            if (!CFG.isInFormableCivs(tagsSPLITED[i])) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED[i])) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED[i]));
                  lTempTags.add(tagsSPLITED[i]);
               }
            }
         }
         for (int i = 0, iSize = tagsSPLITED_ED.size(); i < iSize; ++i) {
            if (!CFG.isInFormableCivs(tagsSPLITED_ED.get(i))) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED_ED.get(i))) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED_ED.get(i)));
                  lTempTags.add(tagsSPLITED_ED.get(i));
               }
            }
         }
         int nPosY = 0;
         while (lTempNames.size() > 0) {
            int toAddID = 0;
            for (int k = 1; k < lTempNames.size(); ++k) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                  toAddID = k;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
            menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
            ++nPosY;
         }
      }
      else {
         for (int i = 0, iSize = tagsSPLITED.length; i < iSize; ++i) {
            if (CFG.langManager.getCiv(tagsSPLITED[i]).charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0) && !CFG.isInFormableCivs(tagsSPLITED[i])) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED[i])) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED[i]));
                  lTempTags.add(tagsSPLITED[i]);
               }
            }
         }
         for (int i = 0, iSize = tagsSPLITED_ED.size(); i < iSize; ++i) {
            if (CFG.langManager.getCiv(tagsSPLITED_ED.get(i)).charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0) && !CFG.isInFormableCivs(tagsSPLITED_ED.get(i))) {
               boolean add = true;
               for (int j = 0; j < tagsSPLITED_ADDED.size(); ++j) {
                  if (tagsSPLITED_ADDED.get(j).equals(tagsSPLITED_ED.get(i))) {
                     add = false;
                     break;
                  }
               }
               if (add) {
                  lTempNames.add(CFG.langManager.getCiv(tagsSPLITED_ED.get(i)));
                  lTempTags.add(tagsSPLITED_ED.get(i));
               }
            }
         }
         int nPosY = 0;
         while (lTempNames.size() > 0) {
            int toAddID = 0;
            for (int k = 1; k < lTempNames.size(); ++k) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                  toAddID = k;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
            menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
            ++nPosY;
         }
      }
      this.initMenu(null, 0, CFG.BUTTON_HEIGHT * 3 / 4 + CFG.BUTTON_HEIGHT + CFG.PADDING, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4 - CFG.BUTTON_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2, menuElements, true, false);
      this.updateLanguage();
   }

   @Override
   protected void updateLanguage() {
   }

   @Override
   protected void updateMenuElements_IsInView() {
      super.updateMenuElements_IsInView();
      int i;
      for (int tempRandomButton = i = 0; i < this.getMenuElementsSize(); i += 2) {
         final int tempTagID = this.getIsLoaded(this.lCivsTags.get((i - tempRandomButton) / 2));
         if (this.getMenuElement(i).getIsInView()) {
            if (tempTagID < 0) {
               this.loadFlag((i - tempRandomButton) / 2);
            }
         }
         else if (tempTagID >= 0) {
            this.lFlags.get(tempTagID).getTexture().dispose();
            this.lFlags.set(tempTagID, null);
            this.lFlags.remove(tempTagID);
            this.lLoadedFlags_TagsIDs.remove(tempTagID);
         }
      }
   }

   private final int getIsLoaded(final String nCivTag) {
      for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
         if (this.lCivsTags.get(this.lLoadedFlags_TagsIDs.get(i)).equals(nCivTag)) {
            return i;
         }
      }
      return -1;
   }

   private final int getFlagID(final int nCivTagID) {
      for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
         if (this.lLoadedFlags_TagsIDs.get(i) == nCivTagID) {
            return i;
         }
      }
      return 0;
   }

   private final void loadFlag(final int nCivTagID) {
      try {
         Label_0459: {
            try {
               this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + this.lCivsTags.get(nCivTagID) + ".png")), Texture.TextureFilter.Nearest));
            }
            catch (final GdxRuntimeException e) {
               try {
                  this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(this.lCivsTags.get(nCivTagID)) + ".png")), Texture.TextureFilter.Nearest));
               }
               catch (final GdxRuntimeException ex) {
                  if (CFG.isAndroid()) {
                     try {
                        this.lFlags.add(new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + this.lCivsTags.get(nCivTagID) + "/" + this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
                     }
                     catch (final GdxRuntimeException erq) {
                        this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + this.lCivsTags.get(nCivTagID) + "/" + this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
                     }
                     break Label_0459;
                  }
                  this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + this.lCivsTags.get(nCivTagID) + "/" + this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
               }
            }
         }
      }
      catch (final GdxRuntimeException e) {
         this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest));
      }
      this.lLoadedFlags_TagsIDs.add(nCivTagID);
   }

   @Override
   protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      try {
         for (int i = 0; i < this.getMenuElementsSize(); i += 2) {
            if (this.getMenuElement(i).getIsInView()) {
               this.lFlags.get(this.getFlagID(i / 2)).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() - this.lFlags.get(this.getFlagID(i / 2)).getHeight() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
            }
         }
      }
      catch (final IndexOutOfBoundsException ex) {}
      catch (final NullPointerException ex2) {}
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   @Override
   protected final void actionElement(final int iID) {
      if (iID % 2 == 0) {
         CFG.formableCivs_GameData.setFormableCivTag(this.lCivsTags.get(iID / 2));
         this.onBack();
         CFG.chosen_AlphabetCharachter = null;
         CFG.sSearch = null;
         this.onBackPressed();
      }
      else {
         CFG.wikiInormationsLink(this.lCivsTags.get(iID / 2));
      }
   }

   private final void onBack() {
      CFG.menuManager.setViewID(Menu.eMAP_EDITOR_FORMABLE_CIVS_EDIT);
   }

   @Override
   protected void onBackPressed() {
      for (int i = 0; i < this.lFlags.size(); ++i) {
         this.lFlags.get(i).getTexture().dispose();
      }
      this.lFlags.clear();
      this.lLoadedFlags_TagsIDs.clear();
      this.lCivsTags.clear();
   }
}
