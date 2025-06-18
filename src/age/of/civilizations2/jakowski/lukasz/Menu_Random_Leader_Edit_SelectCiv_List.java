package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_SelectCiv_List extends SliderMenu {
   private List<String> lCivsTags;
   private List<Image> lFlags;
   private List<Integer> lLoadedFlags_TagsIDs;

   protected Menu_Random_Leader_Edit_SelectCiv_List() {
      super();
      this.lCivsTags = null;
      this.lFlags = new ArrayList<Image>();
      this.lLoadedFlags_TagsIDs = new ArrayList<Integer>();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      final FileHandle tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
      final String tempT = tempFileT.readString();
      final String[] tagsSPLITED = tempT.split(";");
      String[] tagsSPLITED_ED = new String[0];

      try {
         FileHandle tempFileT_ED = null;
         if (CFG.isAndroid()) {
            tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
         } else {
            tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
         }

         String tempT_ED = tempFileT_ED.readString();
         tagsSPLITED_ED = tempT_ED.split(";");
      } catch (GdxRuntimeException var11) {
      }

      this.lCivsTags = new ArrayList<String>();
      final List<String> lTempNames = new ArrayList<String>();
      final List<String> lTempTags = new ArrayList<String>();
      if (CFG.sSearch != null && !CFG.sSearch.isEmpty()) {
          for (String s : tagsSPLITED) {
              if (CFG.langManager.getCiv(s).toLowerCase().contains(CFG.sSearch.toLowerCase()) && !CFG.isInLeaderCivs(s)) {
                  lTempNames.add(CFG.langManager.getCiv(s));
                  lTempTags.add(s);
              }
          }
          for (String s : tagsSPLITED_ED) {
              if (CFG.langManager.getCiv(s).toLowerCase().contains(CFG.sSearch.toLowerCase()) && !CFG.isInLeaderCivs(s)) {
                  lTempNames.add(CFG.langManager.getCiv(s));
                  lTempTags.add(s);
              }
          }
         int nPosY = 0;
         while (!lTempNames.isEmpty()) {
            int toAddID = 0;
            for (int j = 1; j < lTempNames.size(); ++j) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(j))) {
                  toAddID = j;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
            menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
            ++nPosY;
         }
      } else if (CFG.chosen_AlphabetCharachter == null) {
          for (String string : tagsSPLITED) {
              if (!CFG.isInLeaderCivs(string)) {
                  lTempNames.add(CFG.langManager.getCiv(string));
                  lTempTags.add(string);
              }
          }
          for (String s : tagsSPLITED_ED) {
              if (!CFG.isInLeaderCivs(s)) {
                  lTempNames.add(CFG.langManager.getCiv(s));
                  lTempTags.add(s);
              }
          }
         int nPosY = 0;
         while (!lTempNames.isEmpty()) {
            int toAddID = 0;
            for (int j = 1; j < lTempNames.size(); ++j) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(j))) {
                  toAddID = j;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
            menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
            ++nPosY;
         }
      } else {
          for (String string : tagsSPLITED) {
              if (CFG.langManager.getCiv(string).charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0) && !CFG.isInLeaderCivs(string)) {
                  lTempNames.add(CFG.langManager.getCiv(string));
                  lTempTags.add(string);
              }
          }
          for (String s : tagsSPLITED_ED) {
              if (CFG.langManager.getCiv(s).charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0) && !CFG.isInLeaderCivs(s)) {
                  lTempNames.add(CFG.langManager.getCiv(s));
                  lTempTags.add(s);
              }
          }
         int nPosY = 0;
         while (!lTempNames.isEmpty()) {
            int toAddID = 0;
            for (int j = 1; j < lTempNames.size(); ++j) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(j))) {
                  toAddID = j;
               }
            }
            menuElements.add(new Button_Menu(CFG.langManager.getCiv(lTempTags.get(toAddID)), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
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

   private final int getFlagID(int nCivTagID) {
      for(int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
         if ((Integer)this.lLoadedFlags_TagsIDs.get(i) == nCivTagID) {
            return i;
         }
      }

      return 0;
   }

   private final void loadFlag(int nCivTagID) {
      try {
         try {
            this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + (String)this.lCivsTags.get(nCivTagID) + ".png")), Texture.TextureFilter.Nearest));
         } catch (GdxRuntimeException var7) {
            try {
               this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag((String)this.lCivsTags.get(nCivTagID)) + ".png")), Texture.TextureFilter.Nearest));
            } catch (GdxRuntimeException var6) {
               if (CFG.isAndroid()) {
                  try {
                     this.lFlags.add(new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + (String)this.lCivsTags.get(nCivTagID) + "/" + (String)this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
                  } catch (GdxRuntimeException var5) {
                     this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + (String)this.lCivsTags.get(nCivTagID) + "/" + (String)this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
                  }
               } else {
                  this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + (String)this.lCivsTags.get(nCivTagID) + "/" + (String)this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
               }
            }
         }
      } catch (GdxRuntimeException var8) {
         this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest));
      }

      this.lLoadedFlags_TagsIDs.add(nCivTagID);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);

      try {
         for(int i = 0; i < this.getMenuElementsSize(); i += 2) {
            if (this.getMenuElement(i).getIsInView()) {
               ((Image)this.lFlags.get(this.getFlagID(i / 2))).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() - ((Image)this.lFlags.get(this.getFlagID(i / 2))).getHeight() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
            }
         }
      } catch (IndexOutOfBoundsException | NullPointerException var6) {
      }

       super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected final void actionElement(int iID) {
      if (iID % 2 == 0) {
         CFG.leader_GameData.addCiv((String)this.lCivsTags.get(iID / 2));
         this.onBack();
         CFG.chosen_AlphabetCharachter = null;
         CFG.sSearch = null;
         this.onBackPressed();
      } else {
         CFG.wikiInormationsLink((String)this.lCivsTags.get(iID / 2));
      }

   }

   private final void onBack() {
      CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT);
   }

   protected void onBackPressed() {
      for(int i = 0; i < this.lFlags.size(); ++i) {
         ((Image)this.lFlags.get(i)).getTexture().dispose();
      }

      this.lFlags.clear();
      this.lLoadedFlags_TagsIDs.clear();
      this.lCivsTags.clear();
   }
}
