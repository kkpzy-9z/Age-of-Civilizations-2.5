package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_Editor_Civilizations extends SliderMenu {
   private List lCivsTags = null;
   private List lFlags = new ArrayList();
   private List lLoadedFlags_TagsIDs = new ArrayList();

   protected Menu_Editor_Civilizations() {
      super();
      this.lCivsTags = new ArrayList();
      this.lFlags = new ArrayList<Image>();
      this.lLoadedFlags_TagsIDs = new ArrayList<Integer>();
      this.lCivsTags = new ArrayList<String>();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      menuElements.add(new Button_Menu_LR_Line(null, -1, 0 + AoCGame.LEFT, 0, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_LR_Line(null, -1, 0 + AoCGame.LEFT, CFG.PADDING, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));

      int i = 0;
      //fixed by making internal and local scans seperate but conjunctive, used to be local read, bug fix change//
      try {
         //fixed by making internal, used to be local read, bug fix change//
         FileHandle tempFileT = null;
         tempFileT = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
         String tempT = tempFileT.readString();
         String[] tagsSPLITED = tempT.split(";");
         int i1 = 0;
         for(int iSize = tagsSPLITED.length; i1 < iSize; ++i1) {
            try {
               FileHandle file;
               file = Gdx.files.internal("game/civilizations_editor/" + tagsSPLITED[i1] + "/" + tagsSPLITED[i1] + "_NM");
               menuElements.add(new Button_Menu(file.readString(), (int)(50.0F * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * (i + 1) + CFG.PADDING * (i + 2), CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
               this.lCivsTags.add(tagsSPLITED[i1]);
               i += 1;
            } catch (GdxRuntimeException var11) {
               continue;
            }
         }
      } catch (GdxRuntimeException var11) {
         CFG.toast.setInView(var11.getMessage(), CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
      }
      if (CFG.isAndroid()) {
         try {
            FileHandle tempFileT = null;
            tempFileT = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
            String tempT = tempFileT.readString();
            String[] tagsSPLITED = tempT.split(";");
            int i2 = 0;
            for (int iSize = tagsSPLITED.length; i2 < iSize; ++i2) {
               try {
                  FileHandle file;
                  file = Gdx.files.local("game/civilizations_editor/" + tagsSPLITED[i2] + "/" + tagsSPLITED[i2] + "_NM");
                  menuElements.add(new Button_Menu(file.readString(), (int) (50.0F * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * (i + 1) + CFG.PADDING * (i + 2), CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
                  this.lCivsTags.add(tagsSPLITED[i2]);
                  i += 1;
               } catch (GdxRuntimeException var11) {
                  continue;
               }
            }
         } catch (GdxRuntimeException var11) {
            CFG.toast.setInView(var11.getMessage(), CFG.COLOR_TEXT_MODIFIER_NEGATIVE);
         }
      }

      this.initMenuWithBackButton(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getMenuElement(1).setText(CFG.langManager.get("CreateNewCivilization"));
      this.getTitle().setText(CFG.langManager.get("CivilizationEditor"));
   }

   protected void updateMenuElements_IsInView() {
      super.updateMenuElements_IsInView();
      int tempRandomButton = 2;

      for(int i = tempRandomButton; i < this.getMenuElementsSize(); ++i) {
         int tempTagID = this.getIsLoaded((String)this.lCivsTags.get(i - tempRandomButton));
         if (this.getMenuElement(i).getIsInView()) {
            if (tempTagID < 0) {
               this.loadFlag(i - tempRandomButton);
            }
         } else if (tempTagID >= 0) {
            ((Image)this.lFlags.get(tempTagID)).getTexture().dispose();
            this.lFlags.set(tempTagID, (Object)null);
            this.lFlags.remove(tempTagID);
            this.lLoadedFlags_TagsIDs.remove(tempTagID);
         }
      }

   }

   private final int getIsLoaded(String nCivTag) {
      for(int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
         if (((String)this.lCivsTags.get((Integer)this.lLoadedFlags_TagsIDs.get(i))).equals(nCivTag)) {
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
         //cumulative load, change//
         try {
            this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + (String)this.lCivsTags.get(nCivTagID) + "/" + (String)this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
         } catch (GdxRuntimeException var3) {
            this.lFlags.add(new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + (String)this.lCivsTags.get(nCivTagID) + "/" + (String)this.lCivsTags.get(nCivTagID) + "_FL.png")), Texture.TextureFilter.Nearest));
         }
      } catch (GdxRuntimeException var4) {
         this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest));
      }

      this.lLoadedFlags_TagsIDs.add(nCivTagID);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      int tempRandomButton = 2;

      try {
         for(int i = tempRandomButton; i < this.getMenuElementsSize(); ++i) {
            if (this.getMenuElement(i).getIsInView()) {
               ((Image)this.lFlags.get(this.getFlagID(i - tempRandomButton))).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() - ((Image)this.lFlags.get(this.getFlagID(i - tempRandomButton))).getHeight() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
            }
         }
      } catch (IndexOutOfBoundsException var7) {
      } catch (NullPointerException var8) {
      }

      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            this.onBackPressed();
            return;
         case 1:
            CFG.backToMenu = Menu.eEDITOR_CIVILIZATIONS;
            CFG.menuManager.getColorPicker().setPosX(CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4 + CFG.PADDING * 4);
            CFG.flagManager.loadData();
            CFG.flagManager.initFlagEdit();
            CFG.EDITOR_ACTIVE_GAMEDATA_TAG = "" + System.currentTimeMillis() + CFG.extraRandomTag();
            CFG.editorCivilization_GameData = new Civilization_GameData3();
            CFG.menuManager.setViewID(Menu.eCREATE_CIVILIZATION);
            Game_Render_Province.updateDrawProvinces();
            return;
         default:
            CFG.backToMenu = Menu.eEDITOR_CIVILIZATIONS;
            CFG.menuManager.getColorPicker().setPosX(CFG.CIV_INFO_MENU_WIDTH + CFG.CIV_INFO_MENU_WIDTH * 3 / 4 + CFG.PADDING * 4);
            CFG.EDITOR_ACTIVE_GAMEDATA_TAG = (String)this.lCivsTags.get(iID - 2);
            CFG.flagManager.loadData();
            CFG.flagManager.loadFlagEdit();
            CFG.menuManager.setViewID(Menu.eCREATE_CIVILIZATION);
            Game_Render_Province.updateDrawProvinces();
      }
   }

   protected void onBackPressed() {
      CFG.menuManager.setViewID(Menu.eEDITOR);
      CFG.menuManager.setBackAnimation(true);
      this.disposeData();
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         this.disposeData();
      }

   }

   protected void disposeData() {
      for(int i = 0; i < this.lFlags.size(); ++i) {
         ((Image)this.lFlags.get(i)).getTexture().dispose();
      }

      this.lFlags.clear();
      this.lLoadedFlags_TagsIDs.clear();
      this.lCivsTags.clear();
   }
}
