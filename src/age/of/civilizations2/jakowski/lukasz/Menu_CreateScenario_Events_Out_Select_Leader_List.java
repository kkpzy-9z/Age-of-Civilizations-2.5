package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

class Menu_CreateScenario_Events_Out_Select_Leader_List extends SliderMenu
{
   private List<String> lTags;
   private List<String> lCivsTags;
   private List<Image> lFlags;
   private List<Integer> lLoadedFlags_TagsIDs;

   protected Menu_CreateScenario_Events_Out_Select_Leader_List() {
      super();
      this.lTags = null;
      this.lCivsTags = null;
      this.lFlags = new ArrayList<Image>();
      this.lLoadedFlags_TagsIDs = new ArrayList<Integer>();
      this.lCivsTags = new ArrayList<String>();
      this.lTags = new ArrayList<String>();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();

      try {
         String[] tagsSPLITED = null;
         if (CFG.isDesktop()) {
            final List<String> tempFiles = CFG.getFileNames("game/leaders/");
            for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
               if (tempFiles.get(i).equals("Age_of_Civilizations")) {
                  tempFiles.remove(i);
                  break;
               }
            }
            tagsSPLITED = new String[tempFiles.size()];
            for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
               tagsSPLITED[i] = tempFiles.get(i);
            }
         }
         else {
            final FileHandle tempFileT = Gdx.files.internal("game/leaders/Age_of_Civilizations");
            final String tempT = tempFileT.readString();
            tagsSPLITED = tempT.split(";");
         }
         final List<String> lTempNames = new ArrayList<String>();
         final List<String> lTempTags = new ArrayList<String>();
         final List<String> lTempCivsTags = new ArrayList<String>();
         if (CFG.sSearch != null && !CFG.sSearch.isEmpty()) {
            for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
               try {
                  try {
                     final FileHandle file = Gdx.files.local("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
                  catch (final GdxRuntimeException ex) {
                     final FileHandle file = Gdx.files.internal("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
               }
               catch (final ClassNotFoundException | IOException ex2) {}
                if (CFG.leader_GameData.getLeaderOfCiv().getName().toLowerCase().contains(CFG.sSearch.toLowerCase())) {
                  lTempNames.add(CFG.leader_GameData.getLeaderOfCiv().getName());
                  lTempTags.add(tagsSPLITED[j]);
                  lTempCivsTags.add(CFG.leader_GameData.getCiv(0));
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
               menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
               menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
               this.lCivsTags.add(lTempCivsTags.get(toAddID));
               this.lTags.add(lTempTags.get(toAddID));
               lTempNames.remove(toAddID);
               lTempTags.remove(toAddID);
               lTempCivsTags.remove(toAddID);
               ++nPosY;
            }
         }
         else if (CFG.chosen_AlphabetCharachter == null) {
            for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
               try {
                  try {
                     final FileHandle file = Gdx.files.local("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
                  catch (final GdxRuntimeException ex) {
                     final FileHandle file = Gdx.files.internal("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
               }
               catch (final ClassNotFoundException e) {
                  CFG.exceptionStack(e);
               }
               catch (final IOException e2) {
                  CFG.exceptionStack(e2);
               }
               lTempNames.add(CFG.leader_GameData.getLeaderOfCiv().getName());
               lTempTags.add(tagsSPLITED[j]);
               lTempCivsTags.add(CFG.leader_GameData.getCiv(0));
            }
            int nPosY = 0;
            while (lTempNames.size() > 0) {
               int toAddID = 0;
               for (int k = 1; k < lTempNames.size(); ++k) {
                  if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                     toAddID = k;
                  }
               }
               menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
               menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
               this.lCivsTags.add(lTempCivsTags.get(toAddID));
               this.lTags.add(lTempTags.get(toAddID));
               lTempNames.remove(toAddID);
               lTempTags.remove(toAddID);
               lTempCivsTags.remove(toAddID);
               ++nPosY;
            }
         }
         else {
            for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
               try {
                  try {
                     final FileHandle file = Gdx.files.local("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
                  catch (final GdxRuntimeException ex) {
                     final FileHandle file = Gdx.files.internal("game/leaders/" + tagsSPLITED[j]);
                     CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
                  }
               }
               catch (final ClassNotFoundException ex4) {}
               catch (final IOException ex5) {}
               if (CFG.leader_GameData.getLeaderOfCiv().getName().charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0)) {
                  lTempNames.add(CFG.leader_GameData.getLeaderOfCiv().getName());
                  lTempTags.add(tagsSPLITED[j]);
                  lTempCivsTags.add(CFG.leader_GameData.getCiv(0));
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
               menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
               menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
               this.lCivsTags.add(lTempCivsTags.get(toAddID));
               this.lTags.add(lTempTags.get(toAddID));
               lTempNames.remove(toAddID);
               lTempTags.remove(toAddID);
               lTempCivsTags.remove(toAddID);
               ++nPosY;
            }
         }
      }
      catch (final GdxRuntimeException ex6) {}
      this.initMenu(null, 0, CFG.PADDING, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.PADDING * 2, menuElements);
      this.updateLanguage();
   }

   @Override
   protected void updateMenuElements_IsInView() {
      super.updateMenuElements_IsInView();
      for (int i = 1; i < this.getMenuElementsSize(); i += 2) {
         final int tempTagID = this.getIsLoaded(this.lCivsTags.get((i - 1) / 2));
         if (this.getMenuElement(i).getIsInView()) {
            if (tempTagID < 0) {
               this.loadFlag((i - 1) / 2);
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
         try {
            this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + this.lCivsTags.get(nCivTagID) + ".png")), Texture.TextureFilter.Nearest));
         }
         catch (final GdxRuntimeException e) {
            this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(this.lCivsTags.get(nCivTagID)) + ".png")), Texture.TextureFilter.Nearest));
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
      final int tempRandomButton = 1;
      try {
         for (int i = tempRandomButton; i < this.getMenuElementsSize(); i += 2) {
            if (this.getMenuElement(i).getIsInView()) {
               this.lFlags.get(this.getFlagID((i - tempRandomButton) / 2)).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() - this.lFlags.get(this.getFlagID((i - tempRandomButton) / 2)).getHeight() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
            }
         }
      }
      catch (final IndexOutOfBoundsException | NullPointerException ex) {}
       super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   @Override
   protected final void actionElement(final int iID) {
       if (iID % 2 == 0) {
           CFG.chosen_AlphabetCharachter = null;
           CFG.sSearch = null;
           try {
               try {
                   final FileHandle file = Gdx.files.local("game/leaders/" + this.lTags.get((iID) / 2));
                   ((Event_Outcome_ChangeLeader) ((Event_Decision) CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setLeader(((Leader_GameData) CFG.deserialize(file.readBytes())).getLeaderOfCiv());
               } catch (final GdxRuntimeException ex) {
                   final FileHandle file = Gdx.files.internal("game/leaders/" + this.lTags.get((iID) / 2));
                   ((Event_Outcome_ChangeLeader) ((Event_Decision) CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setLeader(((Leader_GameData) CFG.deserialize(file.readBytes())).getLeaderOfCiv());
               }

           } catch (final ClassNotFoundException | IOException ex2) {
           }

           this.onBackPressed();
       } else {
           try {
               try {
                   final FileHandle file = Gdx.files.local("game/leaders/" + this.lTags.get((iID) / 2));
                   CFG.leader_GameData = (Leader_GameData) CFG.deserialize(file.readBytes());
               } catch (final GdxRuntimeException ex) {
                   final FileHandle file = Gdx.files.internal("game/leaders/" + this.lTags.get((iID) / 2));
                   CFG.leader_GameData = (Leader_GameData) CFG.deserialize(file.readBytes());
               }
           } catch (final ClassNotFoundException | IOException ex4) {
           }
           if (!CFG.leader_GameData.getLeaderOfCiv().getWiki().isEmpty()) {
               try {
                   Gdx.net.openURI("https://en.wikipedia.org/wiki/" + CFG.leader_GameData.getLeaderOfCiv().getWiki());
               } catch (final GdxRuntimeException ex6) {
               }
           } else {
               CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
           }
       }
   }

   @Override
   protected void onBackPressed() {
      CFG.eventsManager.selectCivBack();
      this.disposeData();
   }

   @Override
   protected void setVisible(final boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         this.disposeData();
      }
   }

   protected void disposeData() {
      for (int i = 0; i < this.lFlags.size(); ++i) {
         this.lFlags.get(i).getTexture().dispose();
      }
      this.lFlags.clear();
      this.lLoadedFlags_TagsIDs.clear();
      this.lCivsTags.clear();
   }
}
