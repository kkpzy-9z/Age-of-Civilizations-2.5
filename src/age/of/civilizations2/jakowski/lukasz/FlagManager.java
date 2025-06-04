package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class FlagManager {
   protected List<Flag_Division> lDivisions = null;
   protected List<Flag_Overlay> lOverlays = null;
   protected static final int FLAG_WIDTH = 68;
   protected static final int FLAG_HEIGHT = 44;
   protected static final int FLAG_WIDTH_MIN = 27;
   protected static final int FLAG_HEIGHT_MIN = 18;
   protected Flag_GameData flagEdit;
   private List<Image> divisionLayers = new ArrayList<>();
   private List<Flag_OverlayImage> lOverlaysImages = new ArrayList<>();

   protected final void drawFlag(SpriteBatch oSB, int nPosX, int nPosY) {
      this.drawDivision(oSB, nPosX, nPosY);
      //load flag lord overlay
      if (!Objects.equals(flagEdit.overlayTag, "")) {
         drawLordFlag(oSB, nPosX, nPosY);
      }

      for(int i = 0; i < this.flagEdit.lOverlays.size(); ++i) {
         this.drawOverlay(oSB, nPosX, nPosY, i);
      }
   }

   protected final void drawFlag_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      this.drawDivision_FlagFrameSize(oSB, nPosX, nPosY);
      //load flag lord overlay
      if (!Objects.equals(flagEdit.overlayTag, "")) {
         drawLordFlag_FlagFrameSize(oSB, nPosX, nPosY);
      }

      for(int i = 0; i < this.flagEdit.lOverlays.size(); ++i) {
         this.drawOverlay_FlagFrameSize(oSB, nPosX, nPosY, i);
      }
   }

   protected final void drawDivision(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(this.flagEdit.lDivisionColors.get(0).getR(), this.flagEdit.lDivisionColors.get(0).getG(), this.flagEdit.lDivisionColors.get(0).getB(), 1.0F)
      );
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), 68, 44);

      for(int i = 0; i < this.divisionLayers.size(); ++i) {
         oSB.setColor(
            new Color(
               this.flagEdit.lDivisionColors.get(i + 1).getR(),
               this.flagEdit.lDivisionColors.get(i + 1).getG(),
               this.flagEdit.lDivisionColors.get(i + 1).getB(),
               1.0F
            )
         );
         this.divisionLayers.get(i).draw(oSB, nPosX, nPosY - this.divisionLayers.get(i).getHeight(), 68, 44);
      }

      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawDivision_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(this.flagEdit.lDivisionColors.get(0).getR(), this.flagEdit.lDivisionColors.get(0).getG(), this.flagEdit.lDivisionColors.get(0).getB(), 1.0F)
      );
      ImageManager.getImage(Images.pix255_255_255)
         .draw(
            oSB,
            nPosX,
            nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(),
            ImageManager.getImage(Images.top_flag_frame).getWidth(),
            ImageManager.getImage(Images.top_flag_frame).getHeight()
         );

      for(int i = 0; i < this.divisionLayers.size(); ++i) {
         oSB.setColor(
            new Color(
               this.flagEdit.lDivisionColors.get(i + 1).getR(),
               this.flagEdit.lDivisionColors.get(i + 1).getG(),
               this.flagEdit.lDivisionColors.get(i + 1).getB(),
               1.0F
            )
         );
         this.divisionLayers
            .get(i)
            .draw(
               oSB,
               nPosX,
               nPosY - this.divisionLayers.get(i).getHeight(),
               ImageManager.getImage(Images.top_flag_frame).getWidth(),
               ImageManager.getImage(Images.top_flag_frame).getHeight()
            );
      }

      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawDivisionBG(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(this.flagEdit.lDivisionColors.get(0).getR(), this.flagEdit.lDivisionColors.get(0).getG(), this.flagEdit.lDivisionColors.get(0).getB(), 1.0F)
      );
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), 68, 44);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawDivision(SpriteBatch oSB, int nPosX, int nPosY, int nID) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(this.flagEdit.lDivisionColors.get(0).getR(), this.flagEdit.lDivisionColors.get(0).getG(), this.flagEdit.lDivisionColors.get(0).getB(), 1.0F)
      );
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), 68, 44);
      oSB.setColor(
         new Color(
            this.flagEdit.lDivisionColors.get(nID).getR(), this.flagEdit.lDivisionColors.get(nID).getG(), this.flagEdit.lDivisionColors.get(nID).getB(), 1.0F
         )
      );
      this.divisionLayers.get(nID - 1).draw(oSB, nPosX, nPosY - this.divisionLayers.get(nID - 1).getHeight(), 68, 44);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawDivision_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY, int nID) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(this.flagEdit.lDivisionColors.get(0).getR(), this.flagEdit.lDivisionColors.get(0).getG(), this.flagEdit.lDivisionColors.get(0).getB(), 1.0F)
      );
      ImageManager.getImage(Images.pix255_255_255)
         .draw(
            oSB,
            nPosX,
            nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(),
            ImageManager.getImage(Images.top_flag_frame).getWidth(),
            ImageManager.getImage(Images.top_flag_frame).getHeight()
         );
      oSB.setColor(
         new Color(
            this.flagEdit.lDivisionColors.get(nID).getR(), this.flagEdit.lDivisionColors.get(nID).getG(), this.flagEdit.lDivisionColors.get(nID).getB(), 1.0F
         )
      );
      this.divisionLayers
         .get(nID - 1)
         .draw(
            oSB,
            nPosX,
            nPosY - this.divisionLayers.get(nID - 1).getHeight(),
            ImageManager.getImage(Images.top_flag_frame).getWidth(),
            ImageManager.getImage(Images.top_flag_frame).getHeight()
         );
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawOverlay(SpriteBatch oSB, int nPosX, int nPosY, int id) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(
         new Color(
            this.flagEdit.lOverlays.get(id).oColor.getR(), this.flagEdit.lOverlays.get(id).oColor.getG(), this.flagEdit.lOverlays.get(id).oColor.getB(), 1.0F
         )
      );
      this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID)
         .draw(
            oSB,
            nPosX + this.flagEdit.lOverlays.get(id).iPosX,
            nPosY + this.flagEdit.lOverlays.get(id).iPosY - this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID).getHeight(),
            this.flagEdit.lOverlays.get(id).iWidth,
            this.flagEdit.lOverlays.get(id).iHeight
         );
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawOverlay_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY, int id) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      float tScale = (float)ImageManager.getImage(Images.top_flag_frame).getWidth() / 68.0F;
      oSB.setColor(
         new Color(
            this.flagEdit.lOverlays.get(id).oColor.getR(), this.flagEdit.lOverlays.get(id).oColor.getG(), this.flagEdit.lOverlays.get(id).oColor.getB(), 1.0F
         )
      );
      this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID)
         .draw(
            oSB,
            nPosX + (int)((float)this.flagEdit.lOverlays.get(id).iPosX * tScale),
            nPosY + (int)((float)this.flagEdit.lOverlays.get(id).iPosY * tScale) - this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID).getHeight(),
            (int)((float)this.flagEdit.lOverlays.get(id).iWidth * tScale),
            (int)((float)this.flagEdit.lOverlays.get(id).iHeight * tScale)
         );
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   protected final void drawLordFlag(SpriteBatch oSB, int nPosX, int nPosY) {
      for(int i = 0; i < CFG.game.getCivsSize(); ++i) {
         if (CFG.game.getCiv(i).getCivTag().equals(flagEdit.overlayTag)) {
            this.beginClip(oSB, nPosX, nPosY);
            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(i).getFlag().draw(oSB, nPosX, nPosY - CFG.game.getCiv(i).getFlag().getHeight(), 34, 22);
            //oSB.setColor(Color.WHITE);
            this.endClip(oSB);
         }
      }
   }

   protected final void drawLordFlag_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      for(int i = 0; i < CFG.game.getCivsSize(); ++i) {
         if (CFG.game.getCiv(i).getCivTag().equals(flagEdit.overlayTag)) {
            this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(i).getFlag().draw(oSB, nPosX, nPosY - CFG.game.getCiv(i).getFlag().getHeight(), 34, 22);
            //oSB.setColor(Color.WHITE);
            this.endClip(oSB);
         }
      }
   }

   protected final void drawLordFlag(SpriteBatch oSB, int xD, int yD, int id) {
      Rectangle clipBounds = new Rectangle(
              (float)0,
              (float)(CFG.GAME_HEIGHT),
              xD,
              -yD);
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);

      oSB.setColor(Color.WHITE);
      CFG.game.getCiv(id).getFlag().draw(
              oSB,
              0,
              (yD / 2) + 3,
              xD / 2,
              -yD / 2);
      //oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   private final void beginClip(SpriteBatch oSB, int nPosX, int nPosY) {
      Rectangle clipBounds = new Rectangle((float)nPosX, (float)(CFG.GAME_HEIGHT - nPosY), 68.0F, -44.0F);
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);
   }

   private final void beginClip_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      Rectangle clipBounds = new Rectangle(
         (float)nPosX,
         (float)(CFG.GAME_HEIGHT - nPosY),
         (float)ImageManager.getImage(Images.top_flag_frame).getWidth(),
         (float)(-ImageManager.getImage(Images.top_flag_frame).getHeight())
      );
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);
   }

   private final void endClip(SpriteBatch oSB) {
      try {
         oSB.flush();
         ScissorStack.popScissors();
      } catch (IllegalStateException var3) {
      }
   }

   protected final void initFlagEdit() {
      this.flagEdit = new Flag_GameData();
      Random oR = new Random();
      this.flagEdit.iDivisionID = oR.nextInt(this.lDivisions.size());
      this.loadDivision();
      this.loadOverlays();
   }

   protected final void loadFlagEdit() {
      FileHandle file = null;
      FileHandle fileSR = null;
      FileHandle fileFlag = null;

      try {
         try {
            file = Gdx.files.internal("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG);
            fileFlag = Gdx.files.internal("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FD");

            this.flagEdit = (Flag_GameData)CFG.deserialize(fileFlag.readBytes());
            CFG.editorCivilization_GameData = (Civilization_GameData3)CFG.deserialize(file.readBytes());
            CFG.menuManager.setViewID(Menu.eEDITOR_GAME_CIVS_EDIT);
         } catch (GdxRuntimeException var12) {
            file = Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG);
            fileFlag = Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FD");

            this.flagEdit = (Flag_GameData)CFG.deserialize(fileFlag.readBytes());
            CFG.editorCivilization_GameData = (Civilization_GameData3)CFG.deserialize(file.readBytes());
            CFG.menuManager.setViewID(Menu.eEDITOR_GAME_CIVS_EDIT);
         }
      } catch (ClassNotFoundException | IOException | GdxRuntimeException var8) {}


        this.loadDivision();
      this.loadOverlays();
   }

   protected final void updateDivision(boolean add) {
      this.flagEdit.iDivisionID += add ? 1 : -1;
      if (this.flagEdit.iDivisionID < 0) {
         this.flagEdit.iDivisionID = this.lDivisions.size() - 1;
      } else if (this.flagEdit.iDivisionID >= this.lDivisions.size()) {
         this.flagEdit.iDivisionID = 0;
      }

      this.loadDivision();
   }

   protected final void loadDivision() {
      for(int i = 0; i < this.divisionLayers.size(); ++i) {
         this.divisionLayers.get(i).getTexture().dispose();
      }

      this.divisionLayers.clear();

      for(int i = 0; i < this.lDivisions.get(this.flagEdit.iDivisionID).iLayers - 1; ++i) {
         this.divisionLayers
            .add(
               new Image(
                  new Texture(Gdx.files.internal("game/flags_editor/divisions/" + this.lDivisions.get(this.flagEdit.iDivisionID).sName + "_" + i + ".png")),
                  Texture.TextureFilter.Nearest
               )
            );
      }

      for(int i = this.flagEdit.lDivisionColors.size(); i < this.lDivisions.get(this.flagEdit.iDivisionID).iLayers; ++i) {
         if (i == 0) {
            this.flagEdit.lDivisionColors.add(new Color_GameData(1.0F, 1.0F, 1.0F));
         } else if (i == 1) {
            this.flagEdit.lDivisionColors.add(new Color_GameData(0.9843137F, 0.0F, 0.2F));
         } else if (i == 2) {
            this.flagEdit.lDivisionColors.add(new Color_GameData(0.0F, 0.19607843F, 0.39607844F));
         } else if (i == 3) {
            this.flagEdit.lDivisionColors.add(new Color_GameData(1.0F, 0.80784315F, 0.0F));
         } else {
            Color tempColor = CFG.getRandomColor();
            this.flagEdit.lDivisionColors.add(new Color_GameData(tempColor.r, tempColor.g, tempColor.b));
         }
      }
   }

   protected final void updateOverlay(int nID, boolean add) {
      int tempOver = this.flagEdit.lOverlays.get(nID).iOverlayID;
      Flag_Overlay_GameData var10000 = this.flagEdit.lOverlays.get(nID);
      var10000.iOverlayID += add ? 1 : -1;
      if (this.flagEdit.lOverlays.get(nID).iOverlayID < 0) {
         this.flagEdit.lOverlays.get(nID).iOverlayID = this.lOverlays.size() - 1;
      } else if (this.flagEdit.lOverlays.get(nID).iOverlayID >= this.lOverlays.size()) {
         this.flagEdit.lOverlays.get(nID).iOverlayID = 0;
      }

      this.tryRemoveOverlay(tempOver);
      this.loadOverlayImage(this.flagEdit.lOverlays.get(nID).iOverlayID);
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth = (int)Math.abs(
         (float)this.getOverlay(this.flagEdit.lOverlays.get(nID).iOverlayID).getWidth()
            * this.lOverlays.get(this.flagEdit.lOverlays.get(nID).iOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight = (int)Math.abs(
         (float)this.getOverlay(this.flagEdit.lOverlays.get(nID).iOverlayID).getHeight()
            * this.lOverlays.get(this.flagEdit.lOverlays.get(nID).iOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosX = 34 - this.flagEdit.lOverlays.get(nID).iWidth / 2;
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosY = 22 - this.flagEdit.lOverlays.get(nID).iHeight / 2;
   }

   protected final void addOverlay() {
      int tempOverlayID = 0;
      this.flagEdit.lOverlays.add(new Flag_Overlay_GameData(tempOverlayID));
      this.loadOverlayImage(tempOverlayID);
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth = (int)Math.abs(
         (float)this.getOverlay(tempOverlayID).getWidth() * this.lOverlays.get(tempOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight = (int)Math.abs(
         (float)this.getOverlay(tempOverlayID).getHeight() * this.lOverlays.get(tempOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosX = 34 - this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth / 2;
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosY = 22 - this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight / 2;
   }

   protected final void toggleLordOverlay(int iCivID) {
      //if already set, reset, else set to civ
      if (!Objects.equals(this.flagEdit.overlayTag, "")) {
         this.flagEdit.overlayTag = "";
      } else {
         this.flagEdit.overlayTag = CFG.game.getCiv(iCivID).getCivTag();
      }
   }

   protected final void removeOverlay(int nID) {
      int tempOverlayID = this.flagEdit.lOverlays.get(nID).iOverlayID;
      this.flagEdit.lOverlays.remove(nID);
      this.tryRemoveOverlay(tempOverlayID);
   }

   protected final void moveOverlayUp(int nID) {
      if (nID > 0) {
         Flag_Overlay_GameData tempD = this.flagEdit.lOverlays.get(nID);
         this.flagEdit.lOverlays.set(nID, this.flagEdit.lOverlays.get(nID - 1));
         this.flagEdit.lOverlays.set(nID - 1, tempD);
      }
   }

   protected final void loadOverlayImage(int iOverlayID) {
      for(int i = 0; i < this.lOverlaysImages.size(); ++i) {
         if (iOverlayID == this.lOverlaysImages.get(i).iOverlayID) {
            return;
         }
      }

      this.lOverlaysImages.add(new Flag_OverlayImage(iOverlayID));
   }

   protected final void tryRemoveOverlay(int iOverlayID) {
      for(int i = 0; i < this.flagEdit.lOverlays.size(); ++i) {
         if (this.flagEdit.lOverlays.get(i).iOverlayID == iOverlayID) {
            return;
         }
      }

      for(int i = 0; i < this.lOverlaysImages.size(); ++i) {
         if (iOverlayID == this.lOverlaysImages.get(i).iOverlayID) {
            this.lOverlaysImages.get(i).imageOverlay.getTexture().dispose();
            this.lOverlaysImages.remove(i);
            return;
         }
      }
   }

   protected final Image getOverlay(int iOverlayID) {
      for(int i = 0; i < this.lOverlaysImages.size(); ++i) {
         if (iOverlayID == this.lOverlaysImages.get(i).iOverlayID) {
            return this.lOverlaysImages.get(i).imageOverlay;
         }
      }

      return ImageManager.getImage(Images.new_game_box_hover);
   }

   protected final void loadDivisions() {
      if (this.lDivisions != null) {
         this.lDivisions.clear();
      }

      this.lDivisions = new ArrayList<>();

      try {
         FileHandle fileList = Gdx.files.internal("game/flags_editor/divisions.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(ConfigDivisionsData.class, "Division", Data_Divisions.class);
         new ConfigDivisionsData();
         ConfigDivisionsData data = json.fromJson(ConfigDivisionsData.class, fileContent);

         for(Object e : data.Division) {
            Data_Divisions tempData = (Data_Divisions)e;
            this.lDivisions.add(new Flag_Division(tempData.Name, tempData.Layers));
         }
      } catch (GdxRuntimeException var8) {
      }
   }

   protected final void loadOverlays() {
      if (this.lOverlays != null) {
         this.lOverlays.clear();
      }

      this.lOverlays = new ArrayList<>();

      try {
         FileHandle fileList = Gdx.files.internal("game/flags_editor/overlays.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(ConfigOverlayData.class, "Overlay", Data_Overlays.class);
         new ConfigOverlayData();
         ConfigOverlayData data = json.fromJson(ConfigOverlayData.class, fileContent);

         for(Object e : data.Overlay) {
            Data_Overlays tempData = (Data_Overlays)e;
            this.lOverlays.add(new Flag_Overlay(tempData.Name, tempData.Scale));
         }

         for(int i = 0; i < this.flagEdit.lOverlays.size(); ++i) {
            this.loadOverlayImage(this.flagEdit.lOverlays.get(i).iOverlayID);
         }
      } catch (GdxRuntimeException var8) {
      }
   }

   protected final void loadData() {
      this.clearData();
      this.loadDivisions();
   }

   protected final void clearData() {
      if (this.lDivisions != null) {
         this.lDivisions.clear();
      }

      if (this.lOverlays != null) {
         this.lOverlays.clear();
      }

      for(int i = 0; i < this.divisionLayers.size(); ++i) {
         this.divisionLayers.get(i).getTexture().dispose();
      }

      this.divisionLayers.clear();

      for(int i = 0; i < this.lOverlaysImages.size(); ++i) {
         this.lOverlaysImages.get(i).imageOverlay.getTexture().dispose();
      }

      this.lOverlaysImages.clear();
   }

   protected final void saveFlagTexture(SpriteBatch oSB) {
      this.drawFlag(oSB, 0, 0);
      Image tempFlagImage = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - 44, 68, 44)));

      try {
         tempFlagImage.getTexture().getTextureData().prepare();
      } catch (GdxRuntimeException var12) {
      }

      PixmapIO.writePNG(
         Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FLH.png"),
         tempFlagImage.getTexture().getTextureData().consumePixmap()
      );
      oSB.setColor(Color.BLACK);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, 0, -ImageManager.getImage(Images.pix255_255_255).getHeight(), 68, 44);
      oSB.setColor(Color.WHITE);
      CFG.setRender_3(true);
      tempFlagImage.getTexture().dispose();
      tempFlagImage = null;
      Image tempImage;
      if (CFG.isAndroid()) {
         tempImage = new Image(
            new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FLH.png")),
            Texture.TextureFilter.Linear
         );
      } else {
         tempImage = new Image(
            new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FLH.png")),
            Texture.TextureFilter.Linear
         );
      }

      tempImage.draw(oSB, 0, 0);

      try {
         oSB.flush();
         ScissorStack.popScissors();
      } catch (IllegalStateException var11) {
      }

      oSB.end();
      oSB.begin();
      oSB.setColor(Color.WHITE);

      try {
         Image tempFlagImage2 = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - 44, 68, 44)));

         try {
            tempFlagImage2.getTexture().getTextureData().prepare();
         } catch (GdxRuntimeException var9) {
         }

         PixmapIO.writePNG(
            Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FLH.png"),
            tempFlagImage2.getTexture().getTextureData().consumePixmap()
         );
         tempFlagImage2.getTexture().dispose();
         tempFlagImage2 = null;
      } catch (GdxRuntimeException var10) {
      }

      tempImage.draw(oSB, 0, -tempImage.getHeight(), 27, 18);

      try {
         oSB.flush();
         ScissorStack.popScissors();
      } catch (IllegalStateException var8) {
      }

      oSB.end();
      oSB.begin();
      oSB.setColor(Color.WHITE);

      try {
         Image tempFlagImage2 = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - 18, 27, 18)));

         try {
            tempFlagImage2.getTexture().getTextureData().prepare();
         } catch (GdxRuntimeException var6) {
         }

         PixmapIO.writePNG(
            Gdx.files.local("game/civilizations_editor/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "/" + CFG.EDITOR_ACTIVE_GAMEDATA_TAG + "_FL.png"),
            tempFlagImage2.getTexture().getTextureData().consumePixmap()
         );
         tempFlagImage2.getTexture().dispose();
         tempFlagImage2 = null;
      } catch (GdxRuntimeException var7) {
      }

      oSB.setColor(Color.BLACK);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, 0, -ImageManager.getImage(Images.pix255_255_255).getHeight(), 68, 44);
      oSB.setColor(Color.WHITE);
      CFG.setRender_3(true);
      tempImage.getTexture().dispose();
      Image var14 = null;
   }

   protected static class ConfigDivisionsData {
      protected String Age_of_Civilizations;
      protected ArrayList Division;
   }

   protected static class ConfigOverlayData {
      protected String Age_of_Civilizations;
      protected ArrayList Overlay;
   }

   protected static class Data_Divisions {
      protected String Name;
      protected int Layers;
   }

   protected static class Data_Overlays {
      protected String Name;
      protected float Scale;
   }
}
