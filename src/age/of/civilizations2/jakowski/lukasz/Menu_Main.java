package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu_Main extends SliderMenu {
   private int iTitleOffset = 0;
   private long lTime = 0L;
   private int ANIMATION_TIME = 425;
   protected static float ICONS_ALPHA_PC = 0.15F;
   protected static float ICONS_ALPHA = 0.125F;
   protected static final float LOGO_APLHA_DEFAULT = 0.95F;
   protected static boolean RATE_THE_GAME = false;
   protected static boolean AOH3 = false;

   protected Menu_Main() {
      List menuElements = new ArrayList();
      RATE_THE_GAME = false;
      int numOfProvinces = 0;

      FileHandle file;
      try {
         if (CFG.readLocalFiles()) {
            file = Gdx.files.local("AoH3.txt");
         } else {
            file = Gdx.files.internal("AoH3.txt");
         }

         AOH3 = Boolean.parseBoolean(file.readString());
         if (CFG.isAndroid()) {
            AOH3 = true;
         }
      } catch (Exception var8) {
      }

      if (!CFG.settingsManager.gameRated && CFG.isAndroid()) {
         try {
            file = Gdx.files.local("saves/stats/civ/Age_of_Civilizations");
            String tempTags = file.readString();
            String[] tData = tempTags.split(";");

            for(int i = 0; i < tData.length; ++i) {
               try {
                  Statistics_Civ_GameData tempData = (Statistics_Civ_GameData)CFG.deserialize(Gdx.files.local("saves/stats/civ/" + tData[i]).readBytes());
                  numOfProvinces += tempData.getConqueredProvinces();
                  if (numOfProvinces >= 50) {
                     break;
                  }
               } catch (GdxRuntimeException var9) {
               } catch (ClassNotFoundException var10) {
               } catch (IOException var11) {
               }
            }
         } catch (GdxRuntimeException var12) {
            RATE_THE_GAME = false;
         }

         RATE_THE_GAME = numOfProvinces >= 50;
      } else {
         RATE_THE_GAME = false;
      }

      int tempH = CFG.GAME_HEIGHT / 2 - (CFG.BUTTON_HEIGHT * 6 + CFG.PADDING * 7) / 2 + (CFG.BUTTON_HEIGHT + CFG.PADDING * 2) / 2;
      menuElements.add(new Button_Menu_LR_MainMenu_Games((String)null, -1, CFG.GAME_WIDTH / 10, tempH, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_LR_MainMenu((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT + CFG.PADDING, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_LR_MainMenu((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 2, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true));
      if (RATE_THE_GAME) {
         menuElements.add(new Button_Menu_LR_MainMenu_Rate((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 3, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true) {
            protected void actionElement(int iID) {
               try {
                  CFG.settingsManager.gameRated = true;
                  CFG.saveSettings();
                  Gdx.net.openURI("https://play.google.com/store/apps/details?id=age.of.civilizations2.jakowski.lukasz");
               } catch (GdxRuntimeException var3) {
                  CFG.toast.setInView(CFG.langManager.get("NoData"));
               }

            }
         });
      } else {
         menuElements.add(new Button_Menu_LR_MainMenu((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 3, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true) {
            protected void actionElement(int iID) {
               CFG.setDialogType(Dialog.START_TUTORIAL);
            }
         });
      }

      menuElements.add(new Button_Menu_LR_MainMenu((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT * 4 + CFG.PADDING * 4, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Button_Menu_LR_MainMenu((String)null, -1, CFG.GAME_WIDTH / 10, tempH + CFG.BUTTON_HEIGHT * 5 + CFG.PADDING * 5, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 5, CFG.BUTTON_HEIGHT, true));
      menuElements.add(new Text("", -1, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 2, ((MenuElement)menuElements.get(0)).getPosY() - CFG.PADDING - ImageManager.getImage(Images.main_menu_edge).getHeight() - CFG.BUTTON_HEIGHT, ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 4, CFG.BUTTON_HEIGHT) {
         protected int getPosX() {
            return CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3;
         }

         protected int getWidth() {
            return ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6;
         }

         protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            CFG.setRender_3(true);
            if (isActive) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
            } else if (this.getIsHovered()) {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
            } else {
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.95F));
            }

            ImageManager.getImage(Images.gameLogo).draw(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.gameLogo).getHeight() / 2 + iTranslateY);
            oSB.setColor(Color.WHITE);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("Age of History II", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      int tRebuildPosY = 0;

      int i;
      for(i = 0; i < menuElements.size(); ++i) {
         if (tRebuildPosY > ((MenuElement)menuElements.get(i)).getPosY()) {
            tRebuildPosY = ((MenuElement)menuElements.get(i)).getPosY();
         }
      }

      if (tRebuildPosY < 0) {
         for(i = 0; i < menuElements.size(); ++i) {
            ((MenuElement)menuElements.get(i)).setPosY(((MenuElement)menuElements.get(i)).getPosY() - tRebuildPosY + CFG.PADDING * 4);
         }
      }


      this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
      this.updateLanguage();
      if (CFG.XXXXHDPI) {
         this.iTitleOffset = 7;
      } else if (CFG.XXXHDPI) {
         this.iTitleOffset = 7;
      } else if (CFG.XXHDPI) {
         this.iTitleOffset = 7;
      } else if (CFG.XHDPI) {
         this.iTitleOffset = 7;
      } else {
         this.iTitleOffset = 7;
      }

   }

   protected final void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Games"));
      this.getMenuElement(1).setText(CFG.langManager.get("Editor"));
      this.getMenuElement(2).setText(CFG.langManager.get("Settings"));
      this.getMenuElement(3).setText(RATE_THE_GAME ? CFG.langManager.get("Rate") + " " + "Age of History 2.5" : CFG.langManager.get("Tutorial"));
      this.getMenuElement(4).setText(CFG.langManager.get("About"));
      this.getMenuElement(5).setText(CFG.langManager.get("ExitGame"));
      CFG.sTOTAL = CFG.langManager.get("Total");
      CFG.sTOTAL_WORLDS_POPULATION = CFG.langManager.get("WorldsPopulation");
      CFG.glyphLayout.setText(CFG.fontMain, CFG.sLoading);
      CFG.iLoadingWidth = (int)CFG.glyphLayout.width;
      CFG.glyphLayout.setText(CFG.fontMain, CFG.getLukaszJakowskiGames());
      CFG.iJakowskiGamesWidth = (int)CFG.glyphLayout.width;
      CFG.glyphLayout.setText(CFG.fontMain, "presents");
      CFG.iJakowskiGames_PresentsWidth = (int)CFG.glyphLayout.width;
      CFG.glyphLayout.setText(CFG.fontMain, "Age of History 2.5");
      CFG.iAgeOfCivilizationsWidth = (int)CFG.glyphLayout.width;
      this.getMenuElement(6).setText("Age of History 2.5");
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void beginClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (this.lTime + (long)this.ANIMATION_TIME > System.currentTimeMillis()) {
         Rectangle clipBounds = new Rectangle((float)(this.getPosX() + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)this.getWidth(), (float)(-((int)((float)this.getHeight() * ((float)(System.currentTimeMillis() - this.lTime) / (float)this.ANIMATION_TIME)))));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         CFG.setRender_3(true);
      } else {
         super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Images.backgrounds != null && Images.backgrounds.size() > 0) {
         //if backgrounds detected, draw
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 255 + iTranslateX / 2 < 0 ? 0.0F : (float)(255 + iTranslateX / 2)));
         ImageManager.getImage((Integer)Images.backgrounds.get(Images.backgroundLast)).draw2(oSB, 0 + iTranslateX, this.getMenuPosY(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(1.0F, 1.0F, 1.0F, Images.backgroundAlpha);
         ImageManager.getImage((Integer)Images.backgrounds.get(Images.background)).draw2(oSB, 0 + iTranslateX, this.getMenuPosY(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 255 + iTranslateX / 2 < 0 ? 0.0F : (float)(255 + iTranslateX / 2)));
         oSB.draw(ImageManager.getImage((Integer)Images.backgrounds.get(Images.backgroundLast)).getTexture(), 0.0F, (float)(0 - CFG.GAME_HEIGHT + iTranslateY), (float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT);
         oSB.setColor(1.0F, 1.0F, 1.0F, Images.backgroundAlpha);
         oSB.draw(ImageManager.getImage((Integer)Images.backgrounds.get(Images.background)).getTexture(), 0.0F, (float)(0 - CFG.GAME_HEIGHT + iTranslateY), (float)CFG.GAME_WIDTH, (float)CFG.GAME_HEIGHT);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
         ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
         ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.1F));
         ImageManager.getImage(Images.patt2).draw(oSB, iTranslateX, -ImageManager.getImage(Images.patt2).getHeight(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT, 0.0F, 0);
         this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(6).getPosX() - ImageManager.getImage(Images.main_menu_edge).getWidth() + iTranslateX, this.getMenuElement(6).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() - CFG.PADDING + iTranslateY, this.getMenuElement(6).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth(), this.getMenuElement(0).getPosY() - this.getMenuElement(6).getPosY() + CFG.PADDING);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(6).getPosX() + this.getMenuElement(6).getWidth() + iTranslateX, this.getMenuElement(6).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() - CFG.PADDING + iTranslateY, ImageManager.getImage(Images.main_menu_edge).getWidth(), this.getMenuElement(0).getPosY() - this.getMenuElement(6).getPosY() + CFG.PADDING, true);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() - ImageManager.getImage(Images.main_menu_edge).getWidth() + iTranslateX, this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() + iTranslateY, this.getMenuElement(0).getWidth() + ImageManager.getImage(Images.main_menu_edge2).getWidth() - 2, this.getMenuElement(5).getPosY() + this.getMenuElement(5).getHeight() - this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight());
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() + iTranslateX, this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.main_menu_edge2).getWidth(), this.getMenuElement(5).getPosY() + this.getMenuElement(5).getHeight() - this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight(), true);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() - ImageManager.getImage(Images.main_menu_edge).getWidth() + iTranslateX, this.getMenuElement(5).getPosY() + this.getMenuElement(5).getHeight() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 + iTranslateY, this.getMenuElement(0).getWidth() + ImageManager.getImage(Images.main_menu_edge2).getWidth() - 2, ImageManager.getImage(Images.main_menu_edge).getHeight(), false, true);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() + iTranslateX, this.getMenuElement(5).getPosY() + this.getMenuElement(5).getHeight() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 + iTranslateY, ImageManager.getImage(Images.main_menu_edge2).getWidth(), ImageManager.getImage(Images.main_menu_edge).getHeight(), true, true);
      } else {
         //if not, do normal loading
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
         ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
         ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.1F));
         ImageManager.getImage(Images.patt2).draw(oSB, iTranslateX, -ImageManager.getImage(Images.patt2).getHeight(), CFG.GAME_WIDTH, CFG.GAME_HEIGHT, 0.0F, 0);
         this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() - 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING + iTranslateY, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() - this.iTitleOffset - (this.getMenuElement(0).getPosX() - 2), this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge).getHeight() + CFG.PADDING + CFG.PADDING * 2);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING - CFG.BUTTON_HEIGHT + iTranslateY, ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6, CFG.BUTTON_HEIGHT - this.iTitleOffset);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING - CFG.BUTTON_HEIGHT + iTranslateY, ImageManager.getImage(Images.main_menu_edge).getWidth(), CFG.BUTTON_HEIGHT - this.iTitleOffset, true);
         ImageManager.getImage(Images.main_menu_edge2).draw2(oSB, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() - this.iTitleOffset + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() - ImageManager.getImage(Images.main_menu_edge2).getHeight() - CFG.PADDING - this.iTitleOffset + iTranslateY, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth() + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()) + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6 - (CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() - this.iTitleOffset), this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge2).getHeight() + CFG.PADDING + CFG.PADDING * 2 + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()));
         ImageManager.getImage(Images.main_menu_edge2).draw2(oSB, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth() + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()) + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() - ImageManager.getImage(Images.main_menu_edge2).getHeight() - CFG.PADDING - this.iTitleOffset + iTranslateY, ImageManager.getImage(Images.main_menu_edge2).getWidth(), this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge2).getHeight() + CFG.PADDING + CFG.PADDING * 2 + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()), true);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth() + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()) + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6 + ImageManager.getImage(Images.main_menu_edge2).getWidth() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING + iTranslateY, this.getMenuElement(0).getPosX() + this.getMenuElement(0).getWidth() + 2 - (CFG.GAME_WIDTH / 2 - ImageManager.getImage(Images.gameLogo).getWidth() / 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.main_menu_edge).getWidth() + ImageManager.getImage(Images.main_menu_edge).getWidth() + this.iTitleOffset - (ImageManager.getImage(Images.main_menu_edge2).getWidth() - ImageManager.getImage(Images.main_menu_edge).getWidth()) + ImageManager.getImage(Images.gameLogo).getWidth() + CFG.PADDING * 6 + ImageManager.getImage(Images.main_menu_edge2).getWidth()), this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge).getHeight() + CFG.PADDING + CFG.PADDING * 2, true);
         ImageManager.getImage(Images.main_menu_edge).draw2(oSB, this.getMenuElement(0).getPosX() - 2 + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING + this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge).getHeight() + CFG.PADDING + CFG.PADDING * 2 + iTranslateY, this.getMenuElement(0).getWidth() + 4 - ImageManager.getImage(Images.main_menu_edge).getWidth(), ImageManager.getImage(Images.main_menu_edge).getHeight(), false, true);
         ImageManager.getImage(Images.main_menu_edge).draw(oSB, this.getMenuElement(0).getPosX() - 2 + this.getMenuElement(0).getWidth() + 4 - ImageManager.getImage(Images.main_menu_edge).getWidth() + iTranslateX, this.getMenuPosY() + this.getMenuElement(0).getPosY() - ImageManager.getImage(Images.main_menu_edge).getHeight() * 2 - CFG.PADDING + this.getMenuElement(5).getPosY() - this.getMenuElement(1).getPosY() + this.getMenuElement(5).getHeight() * 2 + ImageManager.getImage(Images.main_menu_edge).getHeight() + CFG.PADDING + CFG.PADDING * 2 + ImageManager.getImage(Images.main_menu_edge).getHeight() + iTranslateY, true, true);
      }

      this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      CFG.drawVersion_LEFT_BOT(oSB, iTranslateX);
      oSB.setColor(Color.WHITE);
      CFG.setRender_3(true);
      if ((Map.GAME_CRASHED_LOADED_MIN_SCALE || CFG.map.getMapBG().getMapScale() <= 1) && CFG.map.getMapBG().getMapScale() == 1 && !CFG.toast.getInView()) {
         List nMess = new ArrayList();
         List nCol = new ArrayList();
         nMess.add("Game crashed while loading");
         nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         nMess.add("-- Loaded minimum scale of map --");
         nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
         nMess.add("Go to: Games -> Map: XX -> Earth: -> Scale X5");
         nCol.add(Color.WHITE);
         CFG.toast.setInView((List)nMess, (List)nCol);
         CFG.toast.setTimeInView(6000);
      }

   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.menuManager.setViewID(Menu.eGAMES);
            CFG.menuManager.setOrderOfMenu_Games();
            break;
         case 1:
            SaveManager.gameCanBeContinued = false;
            CFG.menuManager.setViewID(Menu.eEDITOR);
            break;
         case 2:
            CFG.goToMenu2 = Menu.eMAINMENU;
            CFG.menuManager.setViewID(Menu.eSETTINGS);
            break;
         case 3:
            this.getMenuElement(iID).actionElement(iID);
            return;
         case 4:
         case 6:
            CFG.menuManager.setViewID(Menu.eABOUT);
            CFG.map.getMapScale().setNewCurrentScaleByButton2(0.175F);
            return;
         case 5:
            CFG.setDialogType(Dialog.EXIT_GAME);
            return;
      }

      for(int i = 0; i < CFG.game.getProvincesSize(); ++i) {
         if (!CFG.game.getProvince(i).getSeaProvince()) {
            CFG.game.getProvince(i).setFromCivID(0);
         }
      }

      CFG.map.getMapCoordinates().centerToRandomMapPosition();
   }

   protected final void onBackPressed() {
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      this.lTime = System.currentTimeMillis();
   }
}
